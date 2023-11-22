
      //브라우저가 dom를 읽고 객체를 생성하는 데로 실행 = 페이지 로딩되면 실행
      document.addEventListener('DOMContentLoaded', function() {
      let calendarEl = document.getElementById('calendar');
      let memberId = $("#memberId").val();
      let Calendar = FullCalendar.Calendar;

      let calendar = new FullCalendar.Calendar(calendarEl,{
        initialView: 'dayGridMonth',
        defaultDate: new Date(),
        customButtons:{
          prev :{
            text: 'prev',
            click:function(){
              calendar.prev();

            }
          },
          next :{
            text: 'next',
            click:function(){
              calendar.next();

            }
          },
          today :{
            text: 'today',
            click:function(){
              calendar.today();
            }
          },

          workAddButton:{
            text : '근무 추가',
            click:function(){
              $('.modal').fadeIn();

              $('.btn-add').click(function(){

                let work_type = $("#calendar_work_type").val();
                let start_date = $("#calendar_start_date").val();
                let end_date = $("#calendar_end_date").val();

                if(start_date == "" || end_date == ""){
                  alert("날짜를 선택해주세요!");
                } else if(end_date - start_date < 0 ){
                  alert("종료 시간이 시작 날짜보다 먼저입니다.");
                } else{
                  setCalendar(work_type,start_date,end_date,memberId);
                  getCalendar(calendar.getDate() ,memberId)
                  calendar.render();
                  location.reload()
                  

                }
              
              })

              $('.btn-close').click(function(){
                $('.modal').fadeOut();
              })
            }
          }
          
        },
        
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'workUpdateButton workAddButton'
        }

      });

      function setCalendar(work_type,start,end,memberId){

        location.reload(location.href);

        let jsonData = {
          workTimeStart : start,
          workTimeEnd : end,
          workType : work_type,
          memberId: memberId
        }

        $.ajax({
          url: "/work/" + memberId + "/update", 
          method:"POST",
          dataType:"json",
          contentType: "application/json",
          async: false, //기다림
          data: JSON.stringify(jsonData) 
        }).done(function (data){
          console.log("완료");
          
        })
        .fail(function( xhr, status, errorThrown) {
          console.log("에러");
        })

      }

      function getCalendar(date,memberId){
          calendar.removeAllEvents();          
        $.ajax({
          url: "/work/" + memberId+"/list",
          method:"GET",
          dataType:"json",
          contentType: "application/json",
          async: false //기다림
        }).done(function (data){
          console.log(data);
          $.each(data.worklist, function(index, element){
            console.log(changeDay(element.workTimeStart))
            console.log(changeDay(element.workTimeEnd))
            calendar.addEvent({
              title: element.title,
              start: changeDay(element.workTimeStart),
              end: changeDay(element.workTimeEnd)
            })
          })
        })
        .fail(function( xhr, status, errorThrown) {
          console.log("에러 :" +status);
        })
      }
      // YYYY-MM-DD로 바꿔주는
      function changeDay(date){
        const inputDate = new Date(date);
        const year = inputDate.getFullYear();
        const month = String(inputDate.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 2자리로 포맷
        const day = String(inputDate.getDate()).padStart(2, '0'); // 일자를 2자리로 포맷

        const formattedDate = `${year}-${month}-${day}`;
        console.log(formattedDate);

        return formattedDate;
      }
      calendar.render();
      getCalendar(new Date().getMonth()+1,memberId);

    });
