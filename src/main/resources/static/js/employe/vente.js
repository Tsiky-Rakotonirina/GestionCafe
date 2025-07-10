// Script de gestion de la commande (ajout/suppression de produits, calcul total, préparation JSON)

document.addEventListener('DOMContentLoaded', function () {
    const produitsTable = document.getElementById('produitsTable').getElementsByTagName('tbody')[0];
    const totalGeneral = document.getElementById('totalGeneral');
    const produitsJson = document.getElementById('produitsJson');
    const ajouterBtn = document.getElementById('ajouterProduit');
    const produitSelect = document.getElementById('produit');
    const quantiteInput = document.getElementById('quantite');

    let produitsCommande = [];

    function updateTable() {
        produitsTable.innerHTML = '';
        let total = 0;
        produitsCommande.forEach((item, idx) => {
            const row = produitsTable.insertRow();
            row.insertCell().textContent = item.nom;
            row.insertCell().textContent = item.quantite;
            row.insertCell().textContent = item.prixUnitaire + ' Ar';
            row.insertCell().textContent = (item.quantite * item.prixUnitaire) + ' Ar';
            const actionCell = row.insertCell();
            const btn = document.createElement('button');
            btn.className = 'btn btn-danger btn-sm';
            btn.textContent = 'Supprimer';
            btn.onclick = function () {
                produitsCommande.splice(idx, 1);
                updateTable();
            };
            actionCell.appendChild(btn);
            total += item.quantite * item.prixUnitaire;
        });
        totalGeneral.textContent = total + ' Ar';
        produitsJson.value = JSON.stringify(produitsCommande);
    }

    ajouterBtn.addEventListener('click', function (e) {
        e.preventDefault();
        const selectedOption = produitSelect.options[produitSelect.selectedIndex];
        if (!produitSelect.value || !quantiteInput.value || quantiteInput.value <= 0) {
            alert('Veuillez sélectionner un produit et une quantité valide.');
            return;
        }
        // Extraire le nom et le prix depuis le texte de l'option
        const regex = /(.+) - (\d+(?:\.\d+)?) Ar/;
        const match = selectedOption.text.match(regex);
        let nom = selectedOption.text;
        let prixUnitaire = 0;
        if (match) {
            nom = match[1].trim();
            prixUnitaire = parseFloat(match[2]);
        }
        produitsCommande.push({
            id: produitSelect.value,
            nom: nom,
            quantite: parseInt(quantiteInput.value),
            prixUnitaire: prixUnitaire
        });
        updateTable();
    });

    // Préparation du JSON avant soumission
    const form = document.getElementById('commandeForm');
    if (form) {
        form.addEventListener('submit', function () {
            // On envoie un objet { idProduit: quantite, ... } pour correspondre au backend
            const map = {};
            produitsCommande.forEach(item => {
                map[item.id] = item.quantite;
            });
            produitsJson.value = JSON.stringify(map);
        });
    }
});
