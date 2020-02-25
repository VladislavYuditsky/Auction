document.getElementById("form").addEventListener('submit',
    function validate(e) {
        let isValid = true;
        
        const loginPattern = /^[a-zA-Z1-9]{3,10}$/;
        const passwordPattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{5,10}$/;
        
        let password = document.getElementById("password").value;
        let login = document.getElementById("login").value;

        if (!passwordPattern.exec(password)) {
            isValid = false;
            document.getElementById('passwordError').style.display = 'block';
        }

        if (!loginPattern.exec(login)) {
            isValid = false;
            document.getElementById('loginError').style.display = 'block';
        }
        
        if(!isValid){
            e.preventDefault();
        }
    }
);