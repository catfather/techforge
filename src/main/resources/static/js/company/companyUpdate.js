$(document).ready(function () {
    // 초기에는 버튼 비활성화
    $('#submit-button').prop('disabled', false);

    var phoneValidated = true;
    var addressValidated = true;
    var nameValidated = true;
    var emailValidated = true;
    var emailCheckValidated = true;
    var companyValidated = true;
    var busNumValidated = true;

    // 가져올것들
    var phoneInput = $('#phone');
    var addressInput = $('#address');
    var detailAddressInput = $('#detailAddress');
    var nameInput = $('#name');
    var emailInput = $('#mail');
    var emailClick = $('#emailCheck');
    var cerNumInput = $('#certificationNumber');
    var carClick = $('#certificationBtn');
    var companyInput = $('#companyName');
    var busNumInput = $('#businessNumber');

    // 초기 값 저장
    var initialValues = {
        email: emailInput.val(),
        phone: phoneInput.val(),
        address: addressInput.val(),
        detailAddress: detailAddressInput.val(),
        name: nameInput.val(),
        company: companyInput.val(),
        busNum: busNumInput.val()
    };

    emailInput.on('input', function () {
        if (emailInput.val() !== initialValues.email) {
            emailValidated = false;
            emailCheckValidated = false;
            checkAllFields();
            emailChange();
        } else {
            emailValidated = true;
            emailCheckValidated = true;
            checkAllFields();
        }
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

    companyInput.on('input', function(){
        if (companyInput.val() !== initialValues.company){
            companyValidated = false;
            companyCheck();
        }else{
            companyValidated = true;
            checkAllFields();
        }
    });

    busNumInput.on('input', function (){
        if (busNumInput.val() !== initialValues.busNum){
            busNumValidated = false;
            busNumCheck();
        }else {
            busNumValidated = true;
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
        var validatePone = /^(0(2|3[1-3]|4[1-4]|5[1-5]|6[1-4]))-?\d{3,4}-?\d{4}$/;
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
    function companyCheck(){
        var company = companyInput.val();
                $.ajax({
                    url: "/join/companyCheck",
                    type: 'post',
                    data: { "companyName": company },
                    success: function (cnt) {
                        if (cnt == 0) {
                            $('.company_ok').css("display", "inline-block");
                            $('.company_already').css("display", "none");
                            companyValidated = true;
                        } else {
                            $('.company_already').css("display", "inline-block");
                            $('.company_ok').css("display", "none");
                            companyValidated = false;
                        }
                        checkAllFields();
                    }
                });
    }

    function busNumCheck(){
        var busNum = busNumInput.val();
                var businessNumberEx = /^\d{3}-?\d{2}-?\d{5}$/;
                if (businessNumberEx.test(busNum)) {
                    $('.businessNumber_expression').css("display", "none");
                    $.ajax({
                        url: "/join/businessNumberCheck",
                        type: 'post',
                        data: { "businessNumber": busNum },
                        success: function (cnt) {
                            if (cnt == 0) {
                                $('.businessNumber_ok').css("display", "inline-block");
                                $('.businessNumber_already').css("display", "none");
                                busNumValidated = true;
                                checkAllFields();
                            } else {
                                $('.businessNumber_already').css("display", "inline-block");
                                $('.businessNumber_ok').css("display", "none");
                                busNumValidated = true;
                                checkAllFields();
                            }
                        }
                    });

                } else {
                    $('.businessNumber_expression').css("display", "inline-block");
                    $('.businessNumber_ok').css("display", "none");
                    $('.businessNumber_already').css("display", "none");
                    busNumValidated = false;
                    checkAllFields();
                }
    }

    function checkAllFields() {

        if (emailValidated && emailCheckValidated && phoneValidated && addressValidated && nameValidated && companyValidated && busNumValidated) {
            $('#submit-button').prop('disabled', false);
        } else {
            $('#submit-button').prop('disabled', true);
        }
    }
});
