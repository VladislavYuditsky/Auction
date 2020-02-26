export function validatePassword(password) {
    const passwordPattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{5,10}$/;
    return passwordPattern.exec(password);
}