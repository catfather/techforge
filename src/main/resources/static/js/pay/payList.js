$(document).ready(function(){

    var date = new Date();
    var year = date.getFullYear();

    // 현재 년도를 기준으로 호출
    getYears(year);

    // 현재 년도를 select
    $('#workYear').val(year);

    // 바뀐 년도를 기준으로 다시 option 세팅
    $('#workYear').change(function(){
        var changeYear = $(this).val();
        getYears(changeYear);
        $('#workYear').val(changeYear);
    });
});

function getYears(getYear){

    // 기존 option 삭제
    $('#workYear option').remove();

    // 올해 기준으로 -10 +5
    var stYear = Number(getYear)-5;
    var enYear = Number(getYear)+5;
for(var y = stYear; y<= enYear; y++){
    $('#workYear').append("<option value='"+ y +"'>"+ y + " 년" + "</option>");
}
}