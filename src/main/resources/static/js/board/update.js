// Quill 초기화
var quill = new Quill('#editor', {
  theme: 'snow' // 에디터 테마 설정 (예: 'snow' 또는 'bubble')
});

// 폼 제출 시 에디터 내용 저장
document.querySelector('form').addEventListener('submit', function() {
  var editorContent = document.querySelector('.ql-editor').innerHTML;
  document.querySelector('#content').value = editorContent;
});
