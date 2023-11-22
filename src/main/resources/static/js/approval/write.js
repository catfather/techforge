// Quill 에디터 초기화 코드
var quill = new Quill('#editor', {
    theme: 'snow' // 에디터 테마 설정 ('snow' 또는 'bubble' 등)
});

function connectBtn2() {


    // Quill 에디터 컨텐츠 가져오기
    const editorContent = document.querySelector('.ql-editor').innerHTML;
    console.log('에디터 컨텐츠:', editorContent);

    // 검증: 제목과 내용이 제대로 가져와지는지 확인
    const title = document.getElementById('title').value;
    console.log('제목:', title);
    console.log('내용:', editorContent);

    // 나머지 코드
    // ...

    // AJAX를 통한 데이터 전송
    const postData = {
        title: title,
        content: editorContent
    };

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8095/api/v1/approval/create', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                console.log('게시글이 성공적으로 게시되었습니다.');
            } else {
                console.error('게시글 게시 실패. 상태 코드:', xhr.status);
            }
        }
    };

    xhr.send(JSON.stringify(postData));
}
