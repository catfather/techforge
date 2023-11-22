$(document).ready(function () {
    $('#submit-button').prop('disabled', false);

    var phoneInput = $('#phone');
    var addressInput = $('#address');
    var detailAddressInput = $('#detailAddress');
    var nameInput = $('#name');
    var emailInput = $('#mail');
    var emailClick = $('#emailCheck');
    var cerNumInput = $('#certificationNumber');
    var carClick = $('#certificationBtn');
    var birthInput = $('#birth');
    // th:data-birth 속성에서 날짜 값을 가져옵니다.
    var dateString = birthInput.data('birth');
    // 날짜 값을 "YYYYMMDD"에서 "YYYY-MM-DD" 형식으로 변환합니다.
    var formattedDate = dateString.replace(/(\d{4})(\d{2})(\d{2})/, "$1-$2-$3");
    // 변환된 날짜를 input 요소의 value 속성에 설정합니다.
    birthInput.val(formattedDate);

    var phoneValidated = true;
    var addressValidated = true;
    var nameValidated = true;
    var emailValidated = true;
    var emailCheckValidated = true;
    var birthValidated = true;


    var initialValues = {
        email: emailInput.val(),
        phone: phoneInput.val(),
        address: addressInput.val(),
        detailAddress: detailAddressInput.val(),
        name: nameInput.val(),
        birth: birthInput.val()
    };

    //   변한게있느냐

    //   email =>  email

    //    check =>
    {email ,a }

    emailInput.on('input', function () {

        if (emailInput.val() !== initialValues.email) {
            emailValidated = false;
            emailCheckValidated = false;

            emailChange();
        } else {
            emailValidated = true;
            emailCheckValidated = true;

        }
         checkAllFields();
    });
    emailClick.on('click', function () {
        if (emailInput.val() !== initialValues.email) {
            emailValidated = false;
            emailCheckValidated = false;
            checkAllFields();
            emailSend2();
        } else {
            emailValidated = true;
            emailCheckValidated = true;
            checkAllFields();
        }
    });
    carClick.on('click', function () {
        if (emailInput.val() !== initialValues.email) {
            emailValidated = false;
            emailCheckValidated = false;
            checkAllFields();
            emailCertification();
        } else {
            emailValidated = true;
            emailCheckValidated = true;
            checkAllFields();
        }
    });

    phoneInput.on('input', function () {
        if (phoneInput.val() !== initialValues.phone) {
            phoneValidated = false;
            checkAllFields();
            phoneCheck();
        } else {
            phoneValidated = true;
            checkAllFields();
        }
    });

    addressInput.on('input', function () {
        if (addressInput.val() !== initialValues.address) {
            addressValidated = false;
            addressCheck();
        } else {
            addressValidated = true;
            checkAllFields();
        }
    });

    detailAddressInput.on('input', function () {
        if (detailAddressInput.val() !== initialValues.detailAddress) {
            addressValidated = false;
            addressCheck();
        } else {
            addressValidated = true;
            checkAllFields();
        }
    });

    nameInput.on('input', function () {
        if (nameInput.val() !== initialValues.name) {
            nameValidated = false;
            nameCheck();
        } else {
            nameValidated = true;
            checkAllFields();
        }
    });

    birthInput.on('input', function () {
        if (birthInput.val() !== initialValue.birth) {
            birthValidated = false;
            birtCheck();
        } else {
            birthValidated = true;
            checkAllFields();
        }
    });


    // 이메일 유효성 검사
    function emailChange() {
        var email = emailInput.val();
        const regExpEm = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        // 이메일 유효성 검사 로직을 추가
        if (!regExpEm.test(email)) {
            $('.email_expression').css("display", "inline-block");
            $('.member_ok').css("display", "none");
            $('.member_already').css("display", "none");
            emailCheckValidated = false;
            checkAllFields();
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

    function emailSend2() {

        const clientEmail = emailInput.val();
        $.ajax({
            type: "post",
            url: "/member/check",
            data: { email: clientEmail },
            success: function (data) {
                alert('인증번호를 보냈습니다.');
            }, error: function (e) {
                alert('오류입니다. 잠시 후 다시 시도해주세요.');
            }
        });
    }
    function emailCertification() {

        let clientEmail = emailInput.val();
        let inputCode = cerNumInput.val();

        $.ajax({
            type: "post",
            url: "/member/emailCheck",
            data: { email: clientEmail, inputCode: inputCode },
            success: function (result) {
                console.log(result);
                if (result == true) {
                    alert("인증완료");
                    emailValidated = true;
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


    function phoneCheck() {
        var phone = phoneInput.val();
        var validatePone = /^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$/;
        if (!validatePone.test(phone)) {
            $('.phone_expression').css("display", "inline-block");
            $('.phone_already').css("display", "none");
            $('.phone_ok').css("display", "none");
            phoneValidated = false
            checkAllFields();
        } else {
            $('.phone_expression').css("display", "none");
            $.ajax({
                url: "/join/phoneCheck",
                type: 'post',
                data: { "phone": phone },
                success: function (cnt) {
                    if (cnt != 0) {
                        $('.phone_already').css("display", "inline-block");
                        $('.phone_ok').css("display", "none");
                        phoneValidated = false;
                        checkAllFields();
                    } else {
                        phoneValidated = true;
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

    function addressCheck() {
        var address = addressInput.val();
        var detailAddress = detailAddressInput.val();

        $('.post_already').css("display", "none");
        if (address === "") {
            $('.address_already').css("display", "inline-block");
            addressValidated = false;
            checkAllFields();
        } else {
            $('.address_already').css("display", "none");
            if (detailAddress === "") {
                $('.detail_already').css("display", "inline-block");
                addressValidated = false;
                checkAllFields();
            } else {
                $('.detail_already').css("display", "none");
                addressValidated = true;
                checkAllFields();
            }
        }
    }
    function nameCheck() {
        if (nameInput.val().trim() === "") {
            nameValidated = false;
            checkAllFields();
        } else {
            nameValidated = true;
            checkAllFields();
        }
    }

    function birtCheck() {
        if (birthInput.val().trim() === "") {
            birthValidated = false;
            checkAllFields();
        } else {
            birthValidated = true;
            checkAllFields();
        }
    }

    function checkAllFields() {
        if (emailValidated && emailCheckValidated && phoneValidated && addressValidated && nameValidated && birthValidated) {
            $('#submit-button').prop('disabled', false);
        } else {
            $('#submit-button').prop('disabled', true);
        }
    }
});