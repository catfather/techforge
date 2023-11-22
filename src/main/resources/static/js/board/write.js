function toggleDateFields() {
    const boardType = document.getElementById("boardType").value;
    const dateFields = document.getElementById("dateFields");
    const startDateInput = document.getElementById("startDate");
    const endDateInput = document.getElementById("endDate");

    if (boardType === "GENERAL") {
        dateFields.style.display = "block";
        const today = new Date().toISOString().split('T')[0]; // Calculate today's date
        startDateInput.value = today;
        endDateInput.value = today;

        startDateInput.setAttribute("min", today);
        startDateInput.setAttribute("max", today);
        endDateInput.setAttribute("min", today);
    } else {
        dateFields.style.display = "none";
        startDateInput.removeAttribute("min");
        startDateInput.removeAttribute("max");
        endDateInput.removeAttribute("min");
    }
}

function toggleCalendar() {
    const addEventRadio = document.getElementById("addEventCheckbox");
    const dateCalendar = document.getElementById("dateCalendar");

    if (addEventRadio.checked) {
        dateCalendar.style.display = "block";
    } else {
        dateCalendar.style.display = "none";
    }
}

document.addEventListener("DOMContentLoaded", function() {
    toggleDateFields();
});

// Initialize Quill and handle form submission as you've already done.
var quill = new Quill('#editor', {
    theme: 'snow'
});

document.querySelector('form').addEventListener('submit', function() {
    var editorContent = document.querySelector('.ql-editor').innerHTML;
    document.querySelector('#content').value = editorContent;
});
