// var payCalMonthlyBtn = document.querySelector('.payCalMonthlyBtn');
// let memberId  = document.querySelector('.payCalMonthlyBtn');
// let workMonth = $("#workMonth").val();

// payCalMonthlyBtn.addEventListener('click', function() {

   

// })


// 모든 .payCalMonthlyBtn 클래스를 가진 요소를 선택
var buttons = document.querySelectorAll('.payCalMonthlyBtn');


// 버튼을 클릭할 때 이벤트 핸들러를 추가
buttons.forEach(function(button) {
    button.addEventListener('click', function() {
        // 클릭된 버튼의 값을 가져옴
        var staffId = this.getAttribute('value'); // name 속성을 이용
        console.log('Clicked on staff with ID: ' + staffId);
        console.log('this.previousSibling: ' + this.previousSibling.value);// 주석
        console.log('this.previousSibling: ' + this.previousElementSibling .value);
        console.log('this.previousSibling: ' + this.parentElement.children[0].value);
        let month = this.previousElementSibling.value

        $.ajax({
            url: "/pay/"+ staffId,
            method: "POST",
            async: false,
            data : {
                workMonth : month
            }
        }).done(function (data) {
            console.log(data.result);
            if (data.result == 0) {
                alert("이미 정산 되었습니다")
            } else {
                alert("정산 완료!")
            }
        }).fail(function (xhr, status, errorThrown) {
            console.log("에러");
        });
    });
});






