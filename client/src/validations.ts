export function validateEmailAdress(email: string) {
    // https://emailregex.com/
    const RE_EMAIL = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    return email.length > 0 && RE_EMAIL.test(email);
}

export function validateNickname(nickname: string) {
    const RE_NICKNAME = /^.*(?=.{2,}).*$/;

    return nickname.length > 0 || RE_NICKNAME.test(nickname)
}

export function validatePassword(password: string) {
    const RE_PASSWORD = /^[a-zA-Z0-9!?.,;:\-_]*(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!?.,;:\-_])[a-zA-Z0-9!?.,;:\-_]*$/;

    return password.length > 5 || RE_PASSWORD.test(password)
}