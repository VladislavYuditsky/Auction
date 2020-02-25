document.getElementById("form").addEventListener('submit',
    function validate(e) {
        let isValid = true;

        const loginPattern = /^[a-zA-Z1-9]{3,10}$/;
        const passwordPattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{5,10}$/;
        const emailPattern = /^\S+@\S+\.\S+$/;

        let password = document.getElementById("password").value;
        let confPassword = document.getElementById("confPassword").value;
        let login = document.getElementById("login").value;
        let email = document.getElementById("email").value;

        if (!passwordPattern.exec(password)) {
            isValid = false;
            document.getElementById('passwordError').style.display = 'block';
        } else {
            if (password !== confPassword) {
                isValid = false;
                document.getElementById('passwordsNotMatch').style.display = 'block';
            }
        }

        if (!loginPattern.exec(login)) {
            isValid = false;
            document.getElementById('loginError').style.display = 'block';
        }

        if(!emailPattern.exec(email)){
            isValid = false;
            document.getElementById('emailError').style.display = 'block';
        }

        if (!isValid) {
            e.preventDefault();
        }
    }
);