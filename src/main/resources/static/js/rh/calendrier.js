const months = [
  "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
  "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
];

const days = ["Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"];
const calendarContainer = document.getElementById('calendar');
const selectedDates = new Set();
const year = 2025; // fixé comme dans <h1>Calendrier : 2025</h1>

function generateCalendar(year) {
  calendarContainer.innerHTML = '';

  for (let m = 0; m < 12; m++) {
    const monthDiv = document.createElement('div');
    monthDiv.className = 'month';

    const title = document.createElement('h3');
    title.textContent = `${months[m]} ${year}`;
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

    const firstDay = new Date(year, m, 1).getDay();
    let startDay = (firstDay === 0) ? 6 : firstDay - 1;
    const daysInMonth = new Date(year, m + 1, 0).getDate();

    for (let i = 0; i < startDay; i++) {
      const empty = document.createElement('div');
      empty.className = 'empty';
      grid.appendChild(empty);
    }

    for (let d = 1; d <= daysInMonth; d++) {
      const btn = document.createElement('button');
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
        console.log("Dates sélectionnées :", Array.from(selectedDates));
      });

      grid.appendChild(btn);
    }

    monthDiv.appendChild(grid);
    calendarContainer.appendChild(monthDiv);
  }
}

generateCalendar(year);
