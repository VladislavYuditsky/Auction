export function validateLogin(login) {
    const loginPattern = /^[a-zA-Z0-9]{3,10}$/;
    return loginPattern.exec(login);
}