export function validateEmail(email) {
    const emailPattern = /^\S+@\S+\.\S+$/;
    return emailPattern.exec(email);
}