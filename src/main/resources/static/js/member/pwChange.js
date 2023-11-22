function passCheck() {
    const pw = $('#password').val();
    const id = $('#id').val();
    $.ajax({
        url: "/member/passCheck",
        type: 'post',
        data: { "id": id, "password": pw },
        success: function (cnt) {
            if (cnt == true) {
                alert("비밀번호가 일치합니다.");
                $('#password').attr('readonly', true);
            } else {
                alert("비밀번호가 일치하지 않습니다.");
                $('#password').focus();
            }
        }
    });
}
function checkPws() {
    const pw = $('#newPassword').val();
    const email = $('#email').val();
    const pw_check = $('#checkPassword').val();
    var passwordRules = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
    if (!passwordRules.test(pw)) {
        $('.pw_string').css("display", "inline-block");
    } else {
        $('.pw_string').css("display", "none");
    }
    const checkNumber = pw.search(/[0-9]/g);
    const checkEnglish = pw.search(/[a-z]/ig);

    if (checkNumber < 0 || checkEnglish < 0) {
        $('.pw_length').css("display", "inline-block");
    } else {
        $('.pw_length').css("display", "none");
    }
    if (/(\w)\1\1/.test(pw)) {
        $('.pw_true').css("display", "inline-block");
    } else {
        $('.pw_true').css("display", "none");
    }
    if (pw.search(email) > 0) {
        $('.pw_email').css("display", "inline-block");

    } else {
        $('.pw_email').css("display", "none");
    }
    if (pw_check != "") {
        pw_check();
    }

}
function pw_check() {
    const pw = $('#newPassword').val();
    const pw_check = $('#checkPassword').val();

    if (pw != pw_check) {
        $('.password_already').css("display", "inline-block");
    } else {
        $('.password_already').css("display", "none");
    }
}

function check_pw() {
    pw_check();
}
$(function () {
    function validatePassword(password) {
        // 최소 8자리 이상, 알파벳, 특수문자, 숫자가 각각 하나 이상 포함되어야 함
        var regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$/;
        return regex.test(password);
    }

    $("#pwChangeForm").submit(function(event){
        var newPassword = $('#newPassword').val();
        var checkPassword = $('#checkPassword').val();
        var existingPassword = $('#password').val();
        if(!validatePassword(newPassword)){
            alert("비밀번호는 알파벳, 특수문자, 숫자가 최소 하나씩 포함되어야하고 8자리 이상이어야합니다 ");
            $('#newPassword').focus();
            event.preventDefault();
            return false;
        } else if(newPassword !== checkPassword){
            alert("비밀번호가 다릅니다.");
            $('#newPassword').focus();
            event.preventDefault();
            return false;
        } else if(existingPassword == newPassword){
            alert("예전비밀번호와 동일합니다.");
            $('#newPassword').focus();
            event.preventDefault();
            return false;
        }else if(existingPassword == ""){
            alert("비밀번호를 입력해주세요");
            $('#password').focus();
            event.preventDefault();
            return false;
        }else if(newPassword == ""){
            alert("비밀번호를 입력해주세요");
            $('#newPassword').focus();
            event.preventDefault();
            return false;
        }else if(checkPassword == ""){
            alert("비밀번호를 입력해주세요");
            $('#checkPassword').focus();
            event.preventDefault();
            return false;
        }
        $("#pwChangeForm").submit();
    });
});
