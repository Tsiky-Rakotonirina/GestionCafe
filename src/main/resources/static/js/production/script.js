// Script AJAX pour filtrer les produits vendus sans recharger la page
const filtreForm = document.querySelector('form[action*="stats-filtre"]');
const tableBody = document.querySelector('table tbody');

if (filtreForm && tableBody) {
    filtreForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const params = new URLSearchParams(new FormData(filtreForm)).toString();
        fetch(filtreForm.action + '?' + params, {
            headers: {'X-Requested-With': 'XMLHttpRequest'}
        })
            .then(response => response.text())
            .then(html => {
                // Extraire le tbody du HTML retourné
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                const newTbody = doc.querySelector('table tbody');
                if (newTbody) {
                    tableBody.innerHTML = newTbody.innerHTML;
                }
            });
    });
}

// Script AJAX pour mettre à jour la courbe sans recharger la page
const courbeForm = document.getElementById('courbeForm');
const chartCanvas = document.getElementById('courbeTotalChart');
let chartInstance = Chart.getChart(chartCanvas);

if (courbeForm && chartCanvas) {
    courbeForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const params = new URLSearchParams(new FormData(courbeForm)).toString();
        fetch('/administratif/production/stats-json?' + params, {
            headers: {'X-Requested-With': 'XMLHttpRequest'}
        })
            .then(response => response.json())
            .then(json => {
                if (!chartInstance) chartInstance = Chart.getChart(chartCanvas);
                if (chartInstance) {
                    chartInstance.data.labels = json.labels;
                    chartInstance.data.datasets[0].data = json.data;
                    chartInstance.update();
                }
            });
    });
}