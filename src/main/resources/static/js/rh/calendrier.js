const months = [
  "janvier", "février", "mars", "avril", "mai", "juin",
  "juillet", "août", "septembre", "octobre", "novembre", "décembre"
];

const days = ["Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"];
const yearSelect = document.getElementById('yearSelect');
const calendarContainer = document.getElementById('calendar');
const currentYear = new Date().getFullYear();

let selectedDates = new Set();

// Remplir le menu d'années
for (let y = currentYear - 10; y <= currentYear + 10; y++) {
  const option = document.createElement('option');
  option.value = y;
  option.textContent = y;
  if (y === currentYear) option.selected = true;
  yearSelect.appendChild(option);
}

yearSelect.addEventListener('change', () => {
  generateCalendar(parseInt(yearSelect.value));
  selectedDates.clear();
});

function generateCalendar(year) {
  calendarContainer.innerHTML = '';

  for (let m = 0; m < 12; m++) {
    const monthDiv = document.createElement('div');
    monthDiv.className = 'month';

    const title = document.createElement('h3');
    title.textContent = `${months[m][0].toUpperCase() + months[m].slice(1)} ${year}`;
    monthDiv.appendChild(title);

    const header = document.createElement('div');
    header.className = 'days-header';
    for (const d of days) {
      const dayDiv = document.createElement('div');
      dayDiv.textContent = d;
      header.appendChild(dayDiv);
    }
    monthDiv.appendChild(header);

    const grid = document.createElement('div');
    grid.className = 'days-grid';

    const firstDayDate = new Date(year, m, 1);
    let startDay = firstDayDate.getDay(); // 0 = Dim
    startDay = (startDay === 0) ? 6 : startDay - 1;

    const daysInMonth = new Date(year, m + 1, 0).getDate();

    for (let i = 0; i < startDay; i++) {
      const empty = document.createElement('div');
      empty.className = 'empty';
      grid.appendChild(empty);
    }

    for (let d = 1; d <= daysInMonth; d++) {
      const btn = document.createElement('button');
      btn.type = 'button';
      btn.textContent = d;
      btn.className = 'day-button';

      const dateValue = `${d}_${months[m]}_${year}`;
      btn.dataset.date = dateValue;

      btn.addEventListener('click', () => {
        if (selectedDates.has(dateValue)) {
          selectedDates.delete(dateValue);
          btn.classList.remove('selected');
        } else {
          selectedDates.add(dateValue);
          btn.classList.add('selected');
        }
      });

      grid.appendChild(btn);
    }

    monthDiv.appendChild(grid);
    calendarContainer.appendChild(monthDiv);
  }
}

const form = document.getElementById('calendarForm');
form.addEventListener('submit', (e) => {
  // Supprimer anciens inputs
  const oldInputs = form.querySelectorAll('input[name="dates"]');
  oldInputs.forEach(i => i.remove());

  if (selectedDates.size === 0) {
    e.preventDefault();
    alert("Veuillez sélectionner au moins un jour.");
    return;
  }

  selectedDates.forEach(date => {
    const hiddenInput = document.createElement('input');
    hiddenInput.type = 'hidden';
    hiddenInput.name = 'dates';
    hiddenInput.value = date;
    form.appendChild(hiddenInput);
  });
});

// Générer calendrier au chargement
generateCalendar(currentYear);
