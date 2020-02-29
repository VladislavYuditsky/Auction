import {validateLogin} from "./loginValidator";
import {validatePassword} from "./passwordValidator";

document.getElementById("form").addEventListener('submit',
    function validate(e) {
        let isValid = true;

        let password = document.getElementById("password").value;
        let login = document.getElementById("login").value;

        if (validatePassword(password)) {
            isValid = false;
            document.getElementById('passwordError').style.display = 'block';
        }

        if (validateLogin(login)) {
            isValid = false;
            document.getElementById('loginError').style.display = 'block';
        }

        if(!isValid){
            e.preventDefault();
        }
    }
);