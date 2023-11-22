// Quill 초기화
var quill = new Quill('#editor', {
  theme: 'snow' // 에디터 테마 설정 (예: 'snow' 또는 'bubble')
});

const content = document.querySelector('#content');
const boardId = document.querySelector('#boardId');
const replyWriteBtn = document.querySelector('#replyWriteBtn');


replyWriteBtn.addEventListener('click', ()=>{

    var editorContent = document.querySelector('.ql-editor').innerHTML;
    document.querySelector('#content').value = editorContent;

    const reply_data={
        'content' : content.value,
        'boardId' : boardId.value,
    };

    const url = "/reply/write";

       fetch(url, {
               method: "POST",
               body: JSON.stringify(reply_data),
               headers: {
                 "Content-Type": "application/json",
               },
             })
             .then((response) => response.json())
             .then((data) => {
               alert("댓글작성 성공");
               console.log(data);
               replyListFn(boardId.value);

             }).catch((error) => {
               console.log(error);
             });
});

function formatDate(dateString) {
    const date = new Date(dateString);
        const day = date.getDate().toString().padStart(2, '0'); // 일
        const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 월 (0부터 시작하므로 1을 더해줍니다)
        const year = date.getFullYear();
        let hour = date.getHours();
        const minute = date.getMinutes().toString().padStart(2, '0');
        const ampm = hour >= 12 ? 'PM' : 'AM';
        hour = hour % 12 || 12;

        return `${month}월 ${day}일 ${ampm}${hour}:${minute}`;
}

function replyListFn(boardIdVal){

        const url = "/reply/list/"+boardIdVal;

         fetch(url, {
               method: "GET",
               headers: {
                 "Content-Type": "application/json",
               },
             })
             .then((response) => response.json())
             .then((data) => {
              // alert("리스트 성공");
               console.log(data);

               let replyData=document.querySelector('#replyData');

               let innerData=``;

               data.forEach(el=>{

                const contentHTML = (new DOMParser()).parseFromString(el.content, 'text/html').body.textContent;
               innerData+=`<tr class="reply${el.id}">
                           <td>
                                <input type="hidden" value='${el.id}' name="replyId2" id="replyId2" readonly>
                               <input type="hidden" value='${el.boardId}' readonly><br>
                               <input type="text" value='${el.name}' readonly class="replyName">
                               <input type="text" value='${el.approType}' readonly class="replyAppro"><br>
                               <span class="replyTime">${formatDate(el.createTime)}</span><br>
                               <input type="text" value="${contentHTML}"  class="updateContent" readonly><br>
                               <div class="list">
                                   <span>
                                       <button type="button" value="${el.id}" onclick="showUpdate(event, ${el.id})" class="reply-button">수정</button>
                                       <button type="button" value="${el.id}" class="replyDeleteBtn" onclick="replyDelete(${el.id})">삭제</button>
                                   </span>
                               </div>
                               <div class="update" style="display: none;">
                                  <input type="button" value="수정완료" class="replyUpdateBtn" onclick="replyUpdate(${el.id})" >
                                   <button type="button" value="${el.id}" onclick="hideUpdate(event,${el.id})" class="reply-button">취소</button>
                               </div>
                              <hr class="divide">
                           </td>
                        </tr>`;
               });

               replyData.innerHTML=innerData;

             }).catch((error) => {
               console.log(error);
             });
}




(()=>{
replyListFn(boardId.value)

})();




// 수정 버튼 클릭 시 호출되는 함수
function showUpdate(event, replyId) {
    // replyId를 사용하여 list 클래스를 숨기고 update 클래스를 보이게 합니다.
    const replyClass = document.querySelector('.reply' + replyId);
    const replyRead = replyClass.querySelector('.list');
    const replyUpdate = replyClass.querySelector('.update');

     //css -> 클래스 .hide{diplya:none}
        //css -> 클래스 .show{diplya:block}
       // replyRead.classList.add('hide');
       // replyUpdate.classList.add('show');

    replyRead.style.display = 'none';
    replyUpdate.style.display = 'block';
    replyClass.querySelector('.updateContent').readOnly = false;
}


// 취소 버튼 클릭 시 호출되는 함수
function hideUpdate(event, replyId) {
    // replyId를 사용하여 update 클래스를 숨기고 list 클래스를 보이게 합니다.
    const replyClass = document.querySelector('.reply' + replyId);
    const replyRead = replyClass.querySelector('.list');
    const replyUpdate = replyClass.querySelector('.update');

 //css -> 클래스 .hide{diplya:none}
    //css -> 클래스 .show{diplya:block}
   // replyRead.classList.remove('hide');
   // replyUpdate.classList.remove('show');

    replyRead.style.display = 'block';
    replyUpdate.style.display = 'none';
     replyClass.querySelector('.updateContent').readOnly=true;

}


function replyDelete(replyId) {



     if (confirm("댓글을 삭제하시겠습니까?")) {

         const url = "/reply/delete/"+replyId;

          fetch(url, {
            method: "DELETE",
            headers: {
              "Content-Type": "application/json",
            }
            })
             .then(response => {
                 if (response.ok) {
                     return response.text();
                 } else {
                     throw new Error("댓글 삭제에 실패했습니다.");
                 }
             })
             .then(data => {
                 if (data === "success") {
                    replyListFn(boardId.value);
                 } else {
                     alert("댓글 삭제에 실패했습니다.");
                 }
             })
             .catch(error => {
                 alert("댓글 삭제 요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
             });

         }
 }



function replyUpdate(replyId) {



    const content = document.querySelector('.updateContent').value;

    console.log("댓글 수정 데이터: ", {
        id: replyId,
        content: content,
    });

    if (confirm("댓글을 수정하시겠습니까?")) {
        fetch("/reply/update/" + replyId, {
            method: "PATCH",
            body: JSON.stringify({
                id: replyId,
                content: content,
            }),
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("댓글 수정에 실패했습니다.");
            }
        })
        .then(data => {
            if (data === "success") {
                alert("댓글 수정 완료했습니다.");
                replyListFn(boardId.value);
            } else {
                alert("댓글 수정에 실패했습니다.");
            }
        })
        .catch(error => {
            alert("댓글 수정 요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
        });
    }
}





