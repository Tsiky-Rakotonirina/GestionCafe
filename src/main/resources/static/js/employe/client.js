// src/main/resources/static/js/employe/client.js
document.addEventListener('DOMContentLoaded', function() {
    const rechercheClient = document.getElementById('rechercheClient');
    const resultatsRecherche = document.getElementById('resultatsRecherche');
    const selectClient = document.getElementById('selectClient');
    const ficheClientContainer = document.getElementById('ficheClientContainer');
    const datalist = document.getElementById('clients');

    // Recherche dynamique
    rechercheClient.addEventListener('input', function() {
        const term = this.value.trim();
        if (term.length < 2) {
            resultatsRecherche.innerHTML = '';
            return;
        }

        fetch(`/administratif/client/search?term=${encodeURIComponent(term)}`)
            .then(response => response.json())
            .then(clients => {
                resultatsRecherche.innerHTML = '';
                clients.forEach(client => {
                    const div = document.createElement('div');
                    div.className = 'list-group-item list-group-item-action';
                    div.textContent = `${client.nom} ${client.prenom} - ${client.contact || ''}`;
                    div.addEventListener('click', () => loadClientFiche(client.id));
                    resultatsRecherche.appendChild(div);
                });
            });
    });

    // Sélection depuis le datalist
    selectClient.addEventListener('change', function() {
        const selectedOption = Array.from(datalist.options).find(
            option => option.value === this.value
        );
        if (selectedOption) {
            const clientId = selectedOption.getAttribute('data-id');
            loadClientFiche(clientId);
        }
    });

    // Charger la fiche client
    function loadClientFiche(clientId) {
        fetch(`/administratif/client/fiche/${clientId}`)
            .then(response => response.text())
            .then(html => {
                ficheClientContainer.innerHTML = html;
            });
    }
});