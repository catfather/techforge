$(document).ready(function(){
    const gender = $('#gender').val();
    let genderView = "";
    if(gender != "F"){
        genderView += "<span>남자</span>";
        $('#genderView').append(genderView);
    } else if (gender != "M"){
        genderView += "<span>여자</span>";
        $('#genderView').append(genderView);
    }

});