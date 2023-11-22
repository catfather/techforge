const radioButtons = document.querySelectorAll('input[name="boardType"]');

    radioButtons.forEach(button => {
        button.addEventListener('change', () => {
            const selectedBoardType = document.querySelector('input[name="boardType"]:checked').value;
            const currentURL = window.location.href;
            let newURL;

            // 현재 URL에서 기존의 boardType 매개변수를 제거
            const urlParts = currentURL.split('?');
            if (urlParts.length > 1) {
                const baseUrl = urlParts[0];
                const queryParams = new URLSearchParams(urlParts[1]);
                queryParams.delete("boardType");
                newURL = baseUrl + '?' + queryParams.toString();
            } else {
                newURL = currentURL;
            }

            // 선택한 게시판 유형을 추가
            if (newURL.indexOf('?') !== -1) {
                newURL += "&boardType=" + selectedBoardType;
            } else {
                newURL += "?boardType=" + selectedBoardType;
            }

            // 새 URL로 이동
            window.location.href = newURL;
        });
    });