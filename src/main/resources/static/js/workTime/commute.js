var memberId = $('#memberId').val();

var workInButton = document.querySelector('#workInBtn');
        workInButton.addEventListener("click", function () {
            $.ajax({
                url: "/work/workin/" + memberId,
                method: "POST",
                 // 이렇게 데이터를 전달합니다.
                async: false,
            }).done(function (data) {
                console.log(data.result);
                if (data.result == 0) {
                    alert("이미 출근 했습니다!")
                } else {
                    alert("출근되었습니다. 화이팅!")
                }
            }).fail(function (xhr, status, errorThrown) {
                console.log("에러");
            });
        });

var workOutButton = document.querySelector('#workOutBtn');
        workOutButton.addEventListener("click", function () {
            $.ajax({
                url: "/work/workout/" + memberId,
                method: "POST",
                 // 이렇게 데이터를 전달합니다.
                async: false,
            }).done(function (data) {
                console.log(data.result);
                if (data.result == 0) {
                    alert("이미 퇴근 했습니다!")
                } else {
                    alert("퇴근되었습니다!")
                }
            }).fail(function (xhr, status, errorThrown) {
                console.log("에러");
            });
        });