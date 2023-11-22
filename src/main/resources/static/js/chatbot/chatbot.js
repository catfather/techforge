$(function(){
	$("#question").keyup(questionKeyuped);
});

function openChat(){
	setConnectStated(true); // 챗창을 보이게 처리
	connect();
}

function showMessage(message) {
    $("#chat-content").append(message);
	// 대화창 스크롤을 항상 최하위로 배치
    $("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}

function setConnectStated(isTrue){
	if(isTrue){
		$("#chat-container").show();
	}else{
		$("#chat-container").hide();
	}
	// 챗봇창 화면 클리어
	$("#chat-content").html("");
}

function disconnect() {
    setConnectStated(false);
    console.log("연결 종료");
}

function connect() {
	sendMessage("안녕");
}

function help(){
    sendMessage("도움");
}

function sendMessage(message){
	$.ajax({
		url:"/chatbot/bot",
		type:"post",
		data:{message: message},
		success:function(responsedHtml){
			showMessage(responsedHtml);
		}
	});
}

function inputTagString(text){
	var now = new Date();
	var ampm = (now.getHours() > 11) ? "오후" : "오전";
	var time = ampm + now.getHours() % 12 + ":" + now.getMinutes();
	var message = `
		<div class="user-message">
			<div class="message">
				<div class="part">
					<p>${text}</p>
				</div>
				<div class="time">${time}</div>
			</div>
		</div>
	`;
	return message;
}

function questionKeyuped(event){
	if(event.keyCode != 13) return;
	sendBtnClicked();
}

function sendBtnClicked(){
	var question = $("#question").val().trim();
	if (question == "" || question.length < 2) return;
	// 메세지를 서버로 전달
	sendMessage(question);

	var message = inputTagString(question);
	showMessage(message); // 사용자가 입력한 메세지를 채팅창에 출력
	$("#question").val(""); // 질문 input 초기화
}
