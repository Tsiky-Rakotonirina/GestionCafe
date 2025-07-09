package com.gestioncafe.service.rh;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestioncafe.model.Avance;
import com.gestioncafe.model.Commission;
import com.gestioncafe.model.CotisationSociale;
import com.gestioncafe.model.Employe;
import com.gestioncafe.model.FicheDePaie;
import com.gestioncafe.model.Irsa;
import com.gestioncafe.model.Payement;
import com.gestioncafe.model.Presence;
import com.gestioncafe.model.RaisonAvance;
import com.gestioncafe.model.RaisonCommission;
import com.gestioncafe.model.StatutEmploye;
import com.gestioncafe.repository.AvanceRepository;
import com.gestioncafe.repository.CommissionRepository;
import com.gestioncafe.repository.CotisationSocialeRepository;
import com.gestioncafe.repository.EmployeRepository;
import com.gestioncafe.repository.GradeEmployeRepository;
import com.gestioncafe.repository.IrsaRepository;
import com.gestioncafe.repository.PayementRepository;
import com.gestioncafe.repository.PresenceRepository;
import com.gestioncafe.repository.RaisonAvanceRepository;
import com.gestioncafe.repository.RaisonCommissionRepository;
import com.gestioncafe.repository.StatutEmployeRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class RhSalaireService {

    private final EmployeRepository employeRepository;
    private final CommissionRepository commissionRepository;
    private final AvanceRepository avanceRepository;
    private final RaisonCommissionRepository raisonCommissionRepository;
    private final RaisonAvanceRepository raisonAvanceRepository;
    private final IrsaRepository irsaRepository;
    private final CotisationSocialeRepository cotisationSocialeRepository;
    private final GradeEmployeRepository gradeEmployeRepository;
    private final PayementRepository payementRepository;
    private final PresenceRepository presenceRepository;
    private final StatutEmployeRepository statutEmployeRepository;

    public RhSalaireService(IrsaRepository irsaRepository, EmployeRepository employeRepository,
                            CommissionRepository commissionRepository, AvanceRepository avanceRepository,
                            RaisonCommissionRepository raisonCommissionRepository, RaisonAvanceRepository raisonAvanceRepository,
                            CotisationSocialeRepository cotisationSocialeRepository, GradeEmployeRepository gradeEmployeRepository,
                            PayementRepository payementRepository, PresenceRepository presenceRepository,
                            StatutEmployeRepository statutEmployeRepository) {
        this.irsaRepository = irsaRepository;
        this.employeRepository = employeRepository;
        this.commissionRepository = commissionRepository;
        this.avanceRepository = avanceRepository;
        this.raisonCommissionRepository = raisonCommissionRepository;
        this.raisonAvanceRepository = raisonAvanceRepository;
        this.cotisationSocialeRepository = cotisationSocialeRepository;
        this.gradeEmployeRepository = gradeEmployeRepository;
        this.payementRepository = payementRepository;
        this.presenceRepository = presenceRepository;
        this.statutEmployeRepository = statutEmployeRepository;
    }

    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID: " + id));
    }

    public List<Payement> getPayementsByEmployeId(Long idEmploye) {
        return payementRepository.findByIdEmployeOrderByMoisReferenceDesc(idEmploye);
    }

    public List<FicheDePaie> getFicheDePaiesByEmployeId(Long idEmploye) {
        List<FicheDePaie> ficheDePaies = new ArrayList<>();
        try {
            Payement dernierPayement = payementRepository.findTopByIdEmployeOrderByMoisReferenceDesc(idEmploye);
            LocalDate dateApaye = dernierPayement.getMoisReference().toLocalDate().plusMonths(1);
            LocalDate dateRepere = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

            List<Date> dates = new ArrayList<>();
            while (!dateApaye.isAfter(dateRepere)) {
                dates.add(Date.valueOf(dateApaye));
                dateApaye = dateApaye.plusMonths(1);
            }

            List<CotisationSociale> cotisationSociales = cotisationSocialeRepository.findAll();
            double tauxCotisationSociale = cotisationSociales.stream()
                .mapToDouble(CotisationSociale::getTaux)
                .sum();

            List<Irsa> irsas = irsaRepository.findAll();

            for (Date date : dates) {
                StatutEmploye statutEmploye = statutEmployeRepository
                    .findTopByEmploye_IdAndDateStatutLessThanEqualOrderByDateStatutDesc(idEmploye, date.toLocalDate().atStartOfDay())
                    .orElse(null);

                if (statutEmploye == null || statutEmploye.getStatut().getId() != 1) {
                    break;
                }

                LocalDate localDateDebut = date.toLocalDate().withDayOfMonth(1);
                Date dateDebut = Date.valueOf(localDateDebut);

                Double salaireDeBase = gradeEmployeRepository.findSalaireByEmployeAndDate(idEmploye, date.toLocalDate());
                if (salaireDeBase == null) {
                    continue;
                }

                double salaire = salaireDeBase;

                // Absences
                List<Presence> abscence = presenceRepository
                    .findByEmploye_IdAndDatePresenceBetweenAndEstPresentFalse(idEmploye, dateDebut, date);
                double abscences = abscence.size() * salaire / 22;
                salaire -= abscences;

                // Commissions
                double totalCommission = commissionRepository
                    .findByIdEmployeAndDateCommissionBetween(idEmploye, dateDebut, date)
                    .stream()
                    .mapToDouble(Commission::getMontant)
                    .sum();
                salaire += totalCommission;

                // Retenues sociales
                double retenuesSociales = salaire * (tauxCotisationSociale / 100);
                salaire -= retenuesSociales;

                // IRSA
                double impots = 0;
                for (Irsa irsa : irsas) {
                    if (salaire >= irsa.getSalaireMin()
                        && (irsa.getSalaireMax() == 0 || salaire <= irsa.getSalaireMax())) {
                        impots = (salaire - irsa.getSalaireMin()) * (irsa.getTaux() / 100);
                        break;
                    }
                }
                salaire -= impots;

                // Retenue avance
                double retenuPourAvance = this.retenuPourAvance(idEmploye);
                salaire -= retenuPourAvance;

                // Empêche salaire négatif

                // DEBUG : Affichage des détails
                System.out.println("---- Fiche de paie pour le mois de " + dateDebut + " ----");
                System.out.println("Salaire de base       : " + salaireDeBase);
                System.out.println("Absences (jours)      : " + abscence.size() + ", Montant = " + abscences);
                System.out.println("Commissions           : " + totalCommission);
                System.out.println("Retenues sociales     : " + retenuesSociales);
                System.out.println("Impôts IRSA           : " + impots);
                System.out.println("Retenue avance        : " + retenuPourAvance);
                System.out.println("=> Salaire net final  : " + salaire);
                System.out.println("--------------------------------------------");

                ficheDePaies.add(new FicheDePaie(dateDebut, salaireDeBase, abscences, totalCommission,
                    retenuesSociales, impots, retenuPourAvance));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ficheDePaies;
    }

    public Page<FicheDePaie> getFicheDePaiesByEmployeId(Long idEmploye, Pageable pageable) {
        List<FicheDePaie> fullList = getFicheDePaiesByEmployeId(idEmploye); // ta méthode actuelle

        int total = fullList.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);

        List<FicheDePaie> pageContent = fullList.subList(start, end);

        return new PageImpl<>(pageContent, pageable, total);
    }

    public Double prochainSalaire(Long idEmploye) {
        Double prochainSalaire = 0.0;
        try {
            LocalDate ceJour = LocalDate.now().withDayOfMonth(1);
            Date dateRepere = Date.valueOf(ceJour);
            Double tmp = gradeEmployeRepository.findSalaireByEmployeAndDate(idEmploye, ceJour);
            if (tmp != null) {
                prochainSalaire = tmp;
            }
            List<CotisationSociale> cotisationSociales = cotisationSocialeRepository.findAll();
            double tauxCotisationSociale = 0;
            for (CotisationSociale cotisationSociale : cotisationSociales) {
                tauxCotisationSociale += cotisationSociale.getTaux();
            }
            prochainSalaire -= (prochainSalaire * (tauxCotisationSociale / 100));
            List<Irsa> irsas = irsaRepository.findAll();
            double impots = 0;
            for (Irsa irsa : irsas) {
                if (prochainSalaire >= irsa.getSalaireMin()
                    && (irsa.getSalaireMax() == 0 || prochainSalaire <= irsa.getSalaireMax())) {
                    impots = (prochainSalaire - irsa.getSalaireMin()) * (irsa.getTaux() / 100);
                    break;
                }
            }
            prochainSalaire -= impots;
            double retenuPourAvance = this.retenuPourAvance(idEmploye);
            prochainSalaire -= retenuPourAvance;
        } catch (Exception e) {
            System.out.println("prochainSalaire: " + e.getLocalizedMessage());
        }

        return prochainSalaire;
    }

    public Double retenuPourAvance(Long idEmploye) {
        Double totalAvances = 0.0;
        try {
            LocalDate ceJour = LocalDate.now();
            ceJour = ceJour.withDayOfMonth(1);
            Date dateRepere = Date.valueOf(ceJour);
            List<Avance> avances = avanceRepository.findByIdEmployeAndDateAvanceAfter(idEmploye, dateRepere);

            for (Avance avance : avances) {
                totalAvances += avance.getMontant();
            }
        } catch (Exception e) {
            System.out.println("retenuPourAvance: " + e.getLocalizedMessage());
        }

        return totalAvances;
    }

    public List<RaisonAvance> getAllRaisonAvances() {
        return raisonAvanceRepository.findAll();
    }

    public List<Avance> getAvancesByEmployeId(Long idEmploye) {
        return avanceRepository.findByIdEmployeOrderByDateAvanceDesc(idEmploye);
    }

    public List<RaisonCommission> getAllRaisonCommissions() {
        return raisonCommissionRepository.findAll();
    }

    public List<Commission> getCommissionsByEmployeId(Long idEmploye) {
        return commissionRepository.findByIdEmployeOrderByDateCommissionDesc(idEmploye);
    }

    @Transactional
    public void ajoutPayement(Long idEmploye, double salaireDeBase, double abscences, double commissions,
                              double retenuesSociales, double impots, double salaireBrut, double salaireNetImposable,
                              double salaireNet, double retenueAvance, double netAPayer, Date moisReference) {
        try {
            Date datePayement = Date.valueOf(LocalDate.now());
            String pdfNom = (datePayement.getTime() / 1000) + ".pdf";
            String referencePayement = "/uploads/ficheDePaie/" + pdfNom;
            payementRepository.save(new Payement(idEmploye, netAPayer, moisReference, datePayement, referencePayement,
                impots, retenuesSociales));
            Employe employe = employeRepository.findById(idEmploye)
                .orElseThrow(() -> new RuntimeException("Employé introuvable"));

            Document document = new Document();
            File file = new File(referencePayement);
            file.getParentFile().mkdirs();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph("Fiche de Paie"));
            document.add(new Paragraph("Mois concerne : " + moisReference));
            document.add(new Paragraph("Id Employé: " + employe.getId()));
            document.add(new Paragraph("Nom : " + employe.getNom()));
            document.add(new Paragraph("Genre : " + employe.getGenre().getNom()));
            document.add(new Paragraph("Recrutement : " + employe.getDateRecrutement()));
            document.add(new Paragraph("Contact : " + employe.getContact()));
            document.add(new Paragraph("------------------------------------------------------------------"));
            document.add(new Paragraph("Salaire de base : " + salaireDeBase + " Ar"));
            document.add(new Paragraph("Absences : " + abscences + " Ar"));
            document.add(new Paragraph("Commissions : " + commissions + " Ar"));
            document.add(new Paragraph("Retenues sociales : " + retenuesSociales + " Ar"));
            document.add(new Paragraph("Impôts : " + impots + " Ar"));
            document.add(new Paragraph("Salaire brut : " + salaireBrut + " Ar"));
            document.add(new Paragraph("Salaire net imposable : " + salaireNetImposable + " Ar"));
            document.add(new Paragraph("Salaire net : " + salaireNet + " Ar"));
            document.add(new Paragraph("Retenue avance : " + retenueAvance + " Ar"));
            document.add(new Paragraph("Net à payer : " + netAPayer + " Ar"));
            document.add(new Paragraph("Ce : " + datePayement));
        } catch (Exception e) {
            throw new RuntimeException("Échec du paiement : " + e.getMessage(), e);
        }
    }

    public Payement getPayementById(Long id) {
        return payementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payement non trouvé avec l'ID: " + id));
    }

    public void ajoutAvance(Long idEmploye, Long idRaison, double montant) throws Exception {
        if (montant <= 0) {
            throw new Exception("Erreur dans l'ajout : Le montant doit etre strictement positif");
        }
        StatutEmploye statutEmploye = statutEmployeRepository.findTopByEmploye_IdOrderByDateStatutDesc(idEmploye)
            .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID: " + idEmploye));
        if (statutEmploye.getStatut().getId() != 1) {
            throw new Exception("Erreur dans l'ajout : employe inactif");
        }
        double prochainSalaire = this.prochainSalaire(idEmploye);
        if (montant > prochainSalaire) {
            throw new Exception("Erreur dans l'ajout : le montant doit etre inferieur au disponible");
        }
        Date dateAvance = Date.valueOf(LocalDate.now());
        RaisonAvance raisonAvance = raisonAvanceRepository.findById(idRaison)
            .orElseThrow(() -> new Exception("Raison non trouvée"));
        avanceRepository.save(new Avance(raisonAvance, idEmploye, montant, dateAvance));
    }

    public void ajoutCommission(Long idEmploye, Long idRaison, double montant) throws Exception {
        if (montant <= 0) {
            throw new Exception("Erreur dans l'ajout : Le montant doit etre strictement positif");
        }
        LocalDate localDate = LocalDate.now();
        Date dateCommission = Date.valueOf(localDate);
        RaisonCommission raisonCommission = raisonCommissionRepository.findById(idRaison)
            .orElseThrow(() -> new Exception("Raison non trouvée"));
        commissionRepository.save(new Commission(raisonCommission, idEmploye, montant, dateCommission));
    }

}
