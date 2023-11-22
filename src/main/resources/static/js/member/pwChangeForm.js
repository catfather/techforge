function passChangeForm() {

    function validatePassword(password) {
        // 최소 8자리 이상, 알파벳, 특수문자, 숫자가 각각 하나 이상 포함되어야 함
        var regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$/;
        return regex.test(password);
    }
    $('#pwChangeForm').submit(function (event) {
        const pwCheck = document.getElementById('password');
        var newPassword = $('#newPassword').val();
        var checkPassword = $('#checkPassword').val();
        var existingPassword = $('#password').val();
        if (pwCheck.readonly != ture) {
            alert("비밀번호 확인을 해주세요");
            $('#password').focus();
            // 폼 전송 중단
            event.preventDefault();
            return false;
        } else if (!validatePassword(newPassword)) {
            alert("비밀번호는 알파벳, 특수문자, 숫자가 최소 하나씩 포함되어야하고 8자리 이상이어야합니다");
            $('#newPassword').focus();
            // 폼 전송 중단
            event.preventDefault();
            return false;
        } else if (newPassword !== checkPassword) {
            alert("비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
            $('#newPassword').focus();
            // 폼 전송 중단
            event.preventDefault();
            return false;
        } else if (newPassword == existingPassword) {
            alert("기존 비밀번호와 동일합니다.");
            $('#newPassword').focus();
            // 폼 전송 중단
            event.preventDefault();
            return false;
        } else if (existingPassword == ""){
            alert("비밀번호를 체크를 해주세요");
                        $('#password').focus();
                        // 폼 전송 중단
                        event.preventDefault();
                        return false;
        }
        $('#pwChangeForm').submit();
    });
}