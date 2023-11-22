
let webSocket;
let connectBtn = document.querySelector('#connectBtn');
let url = "localhost:8095";

connectBtn.addEventListener('click', ()=>{
    // 웹소켓 접속
    webSocket = new WebSocket("ws://"+url+"/messenger");
       webSocket.onopen = function(event) {
           const message = "게시글 작성 완료";
           webSocket.send(message);
       };
});

requestNotificationPermission();

function requestNotificationPermission() {
    if (Notification.permission !== 'granted') {
        Notification.requestPermission().then(function (permission) {
            if (permission === 'granted') {
            } else if (permission === 'denied') {
                console.log("알림 권한이 거부되었습니다. 알림을 표시할 수 없습니다.");
            } else if (permission === 'default') {
                console.log("알림 권한이 설정되지 않았습니다. 사용자에게 물어보세요.");
            }
        });
    }
}

function showNotification(title, message2) {
    if (Notification.permission === 'granted') {
        const notification = new Notification(title, {body: message2});
    }
}

