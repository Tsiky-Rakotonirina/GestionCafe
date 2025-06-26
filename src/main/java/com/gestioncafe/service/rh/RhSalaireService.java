package com.gestioncafe.service.rh;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class RhSalaireService {

    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private CommissionRepository commissionRepository;
    @Autowired
    private AvanceRepository avanceRepository;
    @Autowired
    private RaisonCommissionRepository raisonCommissionRepository;
    @Autowired
    private RaisonAvanceRepository raisonAvanceRepository;
    @Autowired
    private IrsaRepository irsaRepository;
    @Autowired
    private CotisationSocialeRepository cotisationSocialeRepository;
    @Autowired 
    private GradeEmployeRepository gradeEmployeRepository;
    @Autowired
    private PayementRepository payementRepository;
    @Autowired
    private PresenceRepository presenceRepository;

    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID: " + id));
    }

    public List<Commission> getCommissionsByEmployeId(Long idEmploye) {
        return commissionRepository.findByIdEmployeOrderByDateCommissionDesc(idEmploye);
    }

    public List<Avance> getAvancesByEmployeId(Long idEmploye) {
        return avanceRepository.findByIdEmployeOrderByDateAvanceDesc(idEmploye);
    }
    
    public List<RaisonCommission> getAllRaisonCommissions() {
        return raisonCommissionRepository.findAll();
    }

    public List<RaisonAvance> getAllRaisonAvances() {
        return raisonAvanceRepository.findAll();
    }

    public void ajoutCommission(Long idEmploye, Long idRaison, double montant) throws Exception{
        if(montant<=0) {
            throw new Exception("Erreur dans l'ajout : Le montant doit etre strictement positif");
        }
        LocalDate localDate = LocalDate.now();
        Date dateCommission = Date.valueOf(localDate);
        RaisonCommission raisonCommission = raisonCommissionRepository.findById(idRaison)
                .orElseThrow(() -> new Exception("Raison non trouvée"));
        commissionRepository.save(new Commission(raisonCommission, idEmploye, montant, dateCommission));
    }

    public double retenuPourAvance(Long idEmploye) {
        LocalDate ceJour = LocalDate.now();
        ceJour = ceJour.withDayOfMonth(1);  
        Date dateRepere = Date.valueOf(ceJour);  
        List<Avance> avances = avanceRepository.findByIdEmployeAndDateAvanceAfter(idEmploye, dateRepere);
        double totalAvances = 0;
        for (Avance avance : avances) {
            totalAvances += avance.getMontant();  
        }
        return totalAvances;
    }

    public double prochainSalaire(Long idEmploye) {
        LocalDate ceJour  = LocalDate.now().withDayOfMonth(1);
        Date dateRepere = Date.valueOf(ceJour);
        double prochainSalaire = gradeEmployeRepository.findSalaireByEmployeAndDate(idEmploye, dateRepere);
        List<CotisationSociale> cotisationSociales = cotisationSocialeRepository.findAll();
        double tauxCotisationSociale = 0;
        for (CotisationSociale cotisationSociale : cotisationSociales) {
            tauxCotisationSociale += cotisationSociale.getTaux();  
        }
        prochainSalaire -= (prochainSalaire * tauxCotisationSociale);
        List<Irsa>irsas = irsaRepository.findAll();
        double impots = 0;
        for (Irsa irsa : irsas) {
            if (prochainSalaire >= irsa.getSalaireMin() && (irsa.getSalaireMax() == 0 || prochainSalaire <= irsa.getSalaireMax())) {
                impots = (prochainSalaire - irsa.getSalaireMin()) * irsa.getTaux();
                break;
            }
        }
        prochainSalaire -= impots;
        double retenuPourAvance = this.retenuPourAvance(idEmploye);
        prochainSalaire -= retenuPourAvance;
        return prochainSalaire;
    } 

    public void ajoutAvance(Long idEmploye, Long idRaison, double montant) throws Exception{
        if(montant<=0) {
            throw new Exception("Erreur dans l'ajout : Le montant doit etre strictement positif");
        }
        double prochainSalaire = this.prochainSalaire(idEmploye);
        if(montant > prochainSalaire) {
            throw new Exception("Erreur dans l'ajout : le montant doit etre inferieur au disponible");
        }
        Date dateAvance = Date.valueOf(LocalDate.now());
        RaisonAvance raisonAvance = raisonAvanceRepository.findById(idRaison)
                .orElseThrow(() -> new Exception("Raison non trouvée"));
        avanceRepository.save(new Avance(raisonAvance, idEmploye, montant, dateAvance));
    }

    public List<Payement> getPayementsByEmployeId(Long idEmploye) {
        return payementRepository.findByIdEmployeOrderByMoisReferenceDesc(idEmploye);
    }

    public List<FicheDePaie> getFicheDePaiesByEmployeId(Long idEmploye) {
        List<FicheDePaie> ficheDePaies= new ArrayList<>();
        Payement dernierPayement = payementRepository.findLatestPayementByEmployeId(idEmploye);
        LocalDate dateApaye = dernierPayement.getMoisReference().toLocalDate().plusMonths(1);
        LocalDate dateRepere = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        List<Date> dates = new ArrayList<>();
        while (!dateApaye.isAfter(dateRepere)) {
            dates.add(Date.valueOf(dateApaye)); 
            dateApaye = dateApaye.plusMonths(1); 
        }
        List<CotisationSociale> cotisationSociales = cotisationSocialeRepository.findAll();
        double tauxCotisationSociale = 0;
        for (CotisationSociale cotisationSociale : cotisationSociales) {
            tauxCotisationSociale += cotisationSociale.getTaux();  
        }
        List<Irsa>irsas = irsaRepository.findAll();
        for(Date date :  dates) {
            LocalDate localDateDebut = date.toLocalDate().withDayOfMonth(1);
            Date dateDebut = Date.valueOf(localDateDebut); 
            double salaire = 0;
            double salaireDeBase = gradeEmployeRepository.findSalaireByEmployeAndDate(idEmploye, date);
            salaire = salaireDeBase;
            List<Presence> abscence = presenceRepository.findByIdEmployeAndDatePresenceBetweenAndEstPresentFalse(idEmploye, dateDebut, date);
            double abscences = abscence.size() * salaire / 12;
            salaire -= abscences;
            double totalCommission = 0;
            List<Commission> commissions = commissionRepository.findByIdEmployeAndDateCommissionBetween(idEmploye, dateDebut, date);
            for (Commission commission : commissions) {
                totalCommission += commission.getMontant();  
            }
            salaire += totalCommission;
            double retenuesSociales = salaire * tauxCotisationSociale;
            salaire -= (salaire * tauxCotisationSociale);
            double impots = 0;
            for (Irsa irsa : irsas) {
                if (salaire >= irsa.getSalaireMin() && (irsa.getSalaireMax() == 0 || salaire <= irsa.getSalaireMax())) {
                    impots = (salaire - irsa.getSalaireMin()) * irsa.getTaux();
                    break;
                }
            }
            salaire -= impots;
            double retenuPourAvance = this.retenuPourAvance(idEmploye);
            salaire -= retenuPourAvance;
            ficheDePaies.add(new FicheDePaie(dateDebut, salaireDeBase, abscences, totalCommission, retenuesSociales, impots, retenuPourAvance));
        }
        return ficheDePaies;
    }

    public void ajoutPayement(Long idEmploye, double montant, Date moisReference) {
        Date datePayement = Date.valueOf(LocalDate.now());
        String referencePayement = "/uploads/ficheDePaie/"+(datePayement.getTime() / 1000)+".pdf";
        payementRepository.save(new Payement(idEmploye, montant, moisReference, datePayement, referencePayement));
    }
}
