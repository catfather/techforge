function emailSend2() {

    const clientEmail = $("#mail").val();

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

    let clientEmail = document.getElementById('mail').value;

    let inputCode = document.getElementById('certificationNumber').value;

    $.ajax({
            type: "post",
            url: "/member/emailCheck",
            data: {email: clientEmail, inputCode: inputCode},
            success: function (result) {
                console.log(result);
                if (result == true) {
                    alert("인증완료");
                    document.getElementById('certificationYN').value = "true";

                    clientEmail.onchange = function () {
                        document.getElementById('certificationYN').value = "false";
                    }

                } else {
                    alert('인증번호가 틀립니다.');
                }
            }
        }
    );
}



