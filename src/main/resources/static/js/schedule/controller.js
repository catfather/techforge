// URL 설정
var url = 'http://localhost:8023/worktime/1';

// fetch를 사용하여 데이터 가져오기
fetch(url)
  .then(function(response) {
    // HTTP 응답을 JSON으로 파싱
    return response.json();
  })
  .then(function(data) {
    // JSON 데이터를 사용
    console.log(data); // 받은 JSON 데이터 출력
  })
  .catch(function(error) {
    // 오류 처리
    console.error('데이터를 가져오는 중 오류 발생:', error);
  });
