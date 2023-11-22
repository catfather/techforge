
let webSocket;
let url = "localhost:8095";


// 웹소켓 접속
webSocket = new WebSocket("ws://"+url+"/messenger");


webSocket.onmessage = function(event) {
    const message = event.data;
    if (message.startsWith("notification:")) {
        // 웹 알림을 표시
        const notification = message.replace("notification:", "");
        showNotification("Sole Manager🔔", notification);
    }
};



function requestNotificationPermission() {
    if (Notification.permission !== 'granted') {
        Notification.requestPermission().then(function (permission) {
            if (permission === 'granted') {
                // 알림 권한이 부여됨
            } else if (permission === 'denied') {
                // 알림 권한이 거부됨
                console.log("알림 권한이 거부되었습니다. 알림을 표시할 수 없습니다.");
            } else if (permission === 'default') {
                // 사용자가 알림 권한을 설정하지 않음
                console.log("알림 권한이 설정되지 않았습니다. 사용자에게 물어보세요.");
            }
        });
    }
}


function showNotification(title, message) {
    const iconPath = "/images/logo2.png"; // 원하는 아이콘 이미지 URL로 대체
    if (Notification.permission === 'granted') {
        // 알림 권한이 부여된 경우 알림 생성
        const notification = new Notification(title, {
            body: message,
            icon: iconPath // 아이콘 URL을 설정
        });

        // 크롬에서는 자동으로 닫히지 않기 때문에 4초후 자동으로 닫히도록 설정
        setTimeout(notification.close.bind(notification), 10000);

        notification.onclick = function () {
            window.open('localhost:8095/board/list?boardType=GENERAL');
        };
    }
}

// 호출 시 알림 권한 확인
requestNotificationPermission();
