// src/main/resources/static/js/employe/vente.js
document.addEventListener('DOMContentLoaded', function() {
    const produitsSelect = document.getElementById('produit');
    const quantiteInput = document.getElementById('quantite');
    const ajouterBtn = document.getElementById('ajouterProduit');
    const produitsTable = document.getElementById('produitsTable').getElementsByTagName('tbody')[0];
    const totalGeneralElement = document.getElementById('totalGeneral');
    const produitsJsonInput = document.getElementById('produitsJson');
    const commandeForm = document.getElementById('commandeForm');
    
    let produitsSelectionnes = [];
    let totalGeneral = 0;
    
    // Charger les produits depuis le serveur
    let produits = [];
    fetch('/api/produits')
        .then(response => response.json())
        .then(data => {
            produits = data;
        });
    
    // Ajouter un produit
    ajouterBtn.addEventListener('click', function() {
        const produitId = produitsSelect.value;
        const quantite = parseFloat(quantiteInput.value);
        
        if (!produitId || quantite <= 0) {
            alert('Veuillez sélectionner un produit et une quantité valide');
            return;
        }
        
        const produit = produits.find(p => p.id == produitId);
        const total = produit.prix * quantite;
        
        // Ajouter au tableau
        produitsSelectionnes.push({
            produitId: produit.id,
            nom: produit.nom,
            quantite: quantite,
            prix: produit.prix,
            total: total
        });
        
        // Mettre à jour l'affichage
        updateTable();
        updateTotalGeneral();
        
        // Réinitialiser les champs
        produitsSelect.value = '';
        quantiteInput.value = 1;
    });
    
    // Supprimer un produit
    produitsTable.addEventListener('click', function(e) {
        if (e.target.classList.contains('btn-supprimer')) {
            const index = e.target.getAttribute('data-index');
            produitsSelectionnes.splice(index, 1);
            updateTable();
            updateTotalGeneral();
        }
    });
    
    // Valider la commande
    commandeForm.addEventListener('submit', function(e) {
        if (produitsSelectionnes.length === 0) {
            e.preventDefault();
            alert('Veuillez ajouter au moins un produit');
            return;
        }
        
        // Préparer les données pour le serveur
        const produitsPourServeur = produitsSelectionnes.map(p => ({
            produitId: p.produitId,
            quantite: p.quantite
        }));
        
        produitsJsonInput.value = JSON.stringify(produitsPourServeur);
    });
    
    function updateTable() {
        produitsTable.innerHTML = '';
        produitsSelectionnes.forEach((produit, index) => {
            const row = produitsTable.insertRow();
            
            row.innerHTML = `
                <td>${produit.nom}</td>
                <td>${produit.quantite}</td>
                <td>${produit.prix} Ar</td>
                <td>${produit.total} Ar</td>
                <td>
                    <button class="btn btn-danger btn-sm btn-supprimer" data-index="${index}">
                        Annuler
                    </button>
                </td>
            `;
        });
    }
    
    function updateTotalGeneral() {
        totalGeneral = produitsSelectionnes.reduce((sum, produit) => sum + produit.total, 0);
        totalGeneralElement.textContent = `${totalGeneral} Ar`;
    }
});