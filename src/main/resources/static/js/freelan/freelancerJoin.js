
$(document).ready(function () {
    $('#submit-button').prop('disabled', true);
    var now_utc = Date.now()
    var timeOff = new Date().getTimezoneOffset() * 60000;
    var today = new Date(now_utc - timeOff).toISOString().split("T")[0];
    document.getElementById("birth").setAttribute("max", today);
    // 체크한 결과 확인을 위한 변수
    var emailValidated = false;
    var phoneValidated = false;
    var passwordValidated = false;
    var addressValidated = false;
    var emailCheckValidated = false;

    // 다른 필드에 대한 변수들 추가
    var emailInput = $('#mail');
    var phoneInput = $('#phone');
    var passwordInput = $('#password');
    var passCheckInput = $('#checkPw');
    var addressInput = $('#address');
    var detailAddressInput = $('#detailAddress');
    var emailCheck = $('#emailCheck');
    var certification = $('#certificationBtn');
    var emailCheckNum = $('#certificationNumber');

    // 다른 필드에 대한 oninput 이벤트 핸들러 추가
    emailInput.on('input', function () {
        memberEmail();
    });
    phoneInput.on('input', function () {
        phoneCheck();
    });
    passwordInput.on('input', function () {
        passwordCheck();
    });
    passCheckInput.on('input', function () {
        passwordCheck();
    });
    addressInput.on('input', function () {
        addressCheck();
    });
    detailAddressInput.on('input', function () {
        addressCheck();
    });
    emailCheck.on('click', function (){
        emailSend2();
    });
    certification.on('click', function (){
        emailCertification();
    });



    // 이메일 유효성 검사
    function memberEmail() {
        var email = emailInput.val();
        const regExpEm = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        // 이메일 유효성 검사 로직을 추가
        if (!regExpEm.test(email)) {
            $('.email_expression').css("display", "inline-block");
            $('.member_ok').css("display", "none");
            $('.member_already').css("display", "none");
        } else {
            $('.email_expression').css("display", "none");
            $.ajax({
                url: "/join/emailCheck",
                type: 'post',
                data: { "email": email },
                success: function (cnt) {
                    if (cnt != 0) {
                        $('.member_already').css("display", "inline-block");
                        $('.member_ok').css("display", "none");
                        emailValidated = false;
                        checkAllFields();
                    } else {
                        $('.member_ok').css("display", "inline-block");
                        $('.member_already').css("display", "none");
                        emailValidated = true;
                        checkAllFields();
                    }
                },
                error: function () {
                    alert("에러입니다");
                }
            });
        }
    }

    // 다른 필드에 대한 유효성 검사 함수 추가
    // 전화번호 유효성 검사 (이하 생략)
    function phoneCheck() {
        var phone = phoneInput.val();
        var validatePone = /^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$/;
        if (!validatePone.test(phone)) {
            $('.phone_expression').css("display", "inline-block");
            $('.phone_already').css("display", "none");
            $('.phone_ok').css("display", "none");
        } else {
            $('.phone_expression').css("display", "none");
            $.ajax({
                url: "/join/phoneCheck",
                type: 'post',
                data: { "phone": phone },
                success: function (cnt) {
                    if (cnt != 0) {
                        // 유효할 경우 emailValidated를 true로 설정, 그렇지 않으면 false로 설정
                        //                        phoneValidated = false; // 예제에서는 간단히 true로 설정
                        //                        checkAllFields();
                        $('.phone_already').css("display", "inline-block");
                        $('.phone_ok').css("display", "none");
                    } else {
                        // 유효할 경우 emailValidated를 true로 설정, 그렇지 않으면 false로 설정
                        phoneValidated = true; // 예제에서는 간단히 true로 설정
                        checkAllFields();
                        $('.phone_ok').css("display", "inline-block");
                        $('.phone_already').css("display", "none");
                    }

                },
                error: function () {
                    alert("에러입니다");
                }
            });
        }
    }
    function passwordCheck() {
        var email = emailInput.val();
        var password = passwordInput.val();
        var passCheck = passCheckInput.val();
        const checkNumber = password.search(/[0-9]/g);
        const checkEnglish = password.search(/[a-z]/ig);
        var passwordRules = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
        if (!passwordRules.test(password)) {
            $('.pw_string').css("display", "inline-block");
        } else {
            $('.pw_string').css("display", "none");
            if (checkNumber < 0 || checkEnglish < 0) {
                $('.pw_length').css("display", "inline-block");
            } else {
                $('.pw_length').css("display", "none");
                if (/(\w)\1\1/.test(password)) {
                    $('.pw_true').css("display", "inline-block");
                } else {
                    $('.pw_true').css("display", "none");
                    if (password.search(email) > 0) {
                        $('.pw_email').css("display", "inline-block");

                    } else {
                        $('.pw_email').css("display", "none");
                        if (password != passCheck) {
                            $('.password_already').css("display", "inline-block");
                            passwordValidated = false;
                        } else {
                            $('.password_already').css("display", "none");
                            // 유효할 경우 emailValidated를 true로 설정, 그렇지 않으면 false로 설정
                            passwordValidated = true; // 예제에서는 간단히 true로 설정
                            checkAllFields();
                        }
                    }
                }
            }
        }
    }
    function addressCheck() {
        var address = addressInput.val();
        var detailAddress = detailAddressInput.val();

        $('.post_already').css("display", "none");
        if (address === "") {
            $('.address_already').css("display", "inline-block");
        } else {
            $('.address_already').css("display", "none");
            if (detailAddress === "") {
                $('.detail_already').css("display", "inline-block");
            } else {
                $('.detail_already').css("display", "none");
                // 유효할 경우 emailValidated를 true로 설정, 그렇지 않으면 false로 설정
                addressValidated = true; // 예제에서는 간단히 true로 설정
                checkAllFields();
            }
        }
    }

    function emailSend2() {

        const clientEmail = emailInput.val();

        $.ajax({
            type: "post",
            url: "/member/check",
            data: {email: clientEmail},
            success: function (data) {
                alert('인증번호를 보냈습니다.');
            }, error: function (e) {
                alert('오류입니다. 잠시 후 다시 시도해주세요.');
            }

        });


    }

    function emailCertification() {

        let clientEmail = emailInput.val();

        let inputCode = emailCheckNum.val();

        $.ajax({
                type: "post",
                url: "/member/emailCheck",
                data: {email: clientEmail, inputCode: inputCode},
                success: function (result) {
                    console.log(result);
                    if (result == true) {
                        alert("인증완료");
                        emailCheckValidated = true;
                        checkAllFields();

                    } else {
                        alert('인증번호가 틀립니다.');
                        emailCheckValidated = false;
                        checkAllFields();
                    }
                }
            }
        );
    }

    function checkAllFields() {
        if (emailValidated && emailCheckValidated && phoneValidated && passwordValidated && addressValidated) {
            $('#submit-button').prop('disabled', false);
        } else {
            $('#submit-button').prop('disabled', true);
        }
    }
});