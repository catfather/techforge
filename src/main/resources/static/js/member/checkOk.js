const passwordInput = document.getElementById('certificationNumber');
const confirmPasswordInput = document.getElementById('certificationNumber1');
const pwRightMsg = document.querySelector('.pw_right');
const pwStringMsg = document.querySelector('.pw_string');
const pwStringMsg1 = document.querySelector('.pw_string1');
const pwNotMsg = document.querySelector('.pw_not');
const btn = document.querySelector('.btn')


confirmPasswordInput.addEventListener('input', () => {
    const password = passwordInput.value;
    const confirmPassword = confirmPasswordInput.value;

    // 비밀번호가 일치하면 pw_right 표시, 아니면 숨김
    if (password === confirmPassword || confirmPassword === password) {
        pwRightMsg.style.display = 'inline';
        pwNotMsg.style.display = 'none';
        passwordCheck();  // 비밀번호 규칙 체크 함수 호출
    } else {
        pwRightMsg.style.display = 'none';
        pwNotMsg.style.display = 'inline';
        btn.disabled = true;
    }
});


function passwordCheck() {
    const password = passwordInput.value;

    // 비밀번호 규칙에 맞으면 pw_string 표시, 아니면 숨김
    const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/;
    if (passwordPattern.test(password)) {
        pwStringMsg.style.display = 'none';
        pwStringMsg1.style.display = 'inline';
        btn.disabled = false;
    } else {
        pwStringMsg.style.display = 'inline';
        pwStringMsg1.style.display = 'none';
        btn.disabled = true;
    }
}

// 추가: 비밀번호 입력란에 직접 입력할 때도 비밀번호 규칙 체크 수행
if (passwordInput) {
    passwordInput.addEventListener('input', passwordCheck);
}


passwordInput.addEventListener('input', () => {
    const password = passwordInput.value;
    const confirmPassword = confirmPasswordInput.value;

    // 비밀번호가 일치하면 pw_right 표시, 아니면 숨김
    if (password === confirmPassword || confirmPassword === password) {
        pwRightMsg.style.display = 'inline';
        pwNotMsg.style.display = 'none';
        passwordCheck();  // 비밀번호 규칙 체크 함수 호출
    } else {
        pwRightMsg.style.display = 'none';
        pwNotMsg.style.display = 'inline';
        btn.disabled = true;
    }
});


