$(document).ready(function() {
    // 일단 버튼 비활성화
    $('#submit-button').prop('disabled', true);

    var initialFieldValues = {};

    $('input.input-field').each(function () {
        var fieldName = $(this).attr('name');
        initialFieldValues[fieldName] = $(this).val().trim();
    })

    // 필드가 변경될 때마다 버튼 상태 업데이트
    $('input.input-field').on('input', function() {
        checkAndUpdateButtonState(initialFieldValues);
    });
});

// 버튼 상태를 확인하고 업데이트하는 함수
function checkAndUpdateButtonState(initialFieldValues) {
    var allFieldsValid = true;


    // 필드 수정되었나
    $('input.input-field').each(function () {
       var fieldName = $(this).attr('name');
       if ($(this).val().trim() !== initialFieldValues[fieldName]){
           allFieldsValid = false;
           return false;
       }
    });


    // 버튼 상태 업데이트
    if (allFieldsValid) {
        $('#submit-button').prop('disabled', true); // 버튼 활성화
    } else {
        $('#submit-button').prop('disabled', false); // 버튼 비활성화
    }
}