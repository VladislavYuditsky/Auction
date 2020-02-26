import {validateEmail} from "./emailValidator";
import {validateLogin} from "./loginValidator";
import {validatePassword} from "./passwordValidator";

document.getElementById("form").addEventListener('submit',
    function validate(e) {
        let isValid = true;

        let password = document.getElementById("password").value;
        let confPassword = document.getElementById("confPassword").value;
        let login = document.getElementById("login").value;
        let email = document.getElementById("email").value;

        if (validatePassword(password)) {
            isValid = false;
            document.getElementById('passwordError').style.display = 'block';
        } else {
            if (password !== confPassword) {
                isValid = false;
                document.getElementById('passwordsNotMatch').style.display = 'block';
            }
        }

        if (validateLogin(login)) {
            isValid = false;
            document.getElementById('loginError').style.display = 'block';
        }

        if(!validateEmail(email)){
            isValid = false;
            document.getElementById('emailError').style.display = 'block';
        }

        if (!isValid) {
            e.preventDefault();
        }
    }
);