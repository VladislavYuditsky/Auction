document.getElementById("formWithNumber").addEventListener('submit',
    function validate(e) {
        let isValid = true;

        const numberPattern = /^[0-9.]+$/;

        let number = document.getElementById("number").value;

        if (!numberPattern.exec(number)) {
            isValid = false;
            document.getElementById('numberError').style.display = 'block';
        }

        if (!isValid) {
            e.preventDefault();
        }
    }
);