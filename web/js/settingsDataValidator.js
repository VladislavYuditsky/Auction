import {validateEmail} from "./emailValidator";
import {validatePassword} from "./passwordValidator";

document.getElementById("form").addEventListener('submit',
    function validate(e) {
        let isValid = true;

        let password = document.getElementById("password").value;
        let email = document.getElementById("email").value;

        if (validatePassword(password)) {
            isValid = false;
            document.getElementById('passwordError').style.display = 'block';
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