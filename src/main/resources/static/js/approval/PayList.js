let currentPage; // currentPage is initially undefined
// 여기에서 모달을 열고 상세 내용을 표시하는 로직을 추가하면 됩니다.
const modal = document.getElementById('modal');
const modalTitle = document.getElementById('modalTitle');
const modalContent = document.getElementById('modalContent');
const modalId = document.getElementById('modalId');
const modalTime = document.getElementById('modalTime')


function updatePagination() {
    const currentPageElement = document.getElementById('currentPage');
    currentPageElement.innerText = currentPage !== undefined ? currentPage + 1 : 1; // Display page number starting from 1 if currentPage is defined, otherwise 1
}

function changePage(offset) {
    const nextPage = currentPage !== undefined ? currentPage + offset : offset;
    if (nextPage >= 0) { // Ensure page is non-negative
        currentPage = nextPage;
        updatePagination();
        searchApprovals();
    }
}

// Call this function to perform the initial search when the page loads
function performInitialSearch() {
    currentPage = 0; // Set currentPage to 0 initially
    searchApprovals();
}

// Attach the event listener to DOMContentLoaded to execute the initial search
document.addEventListener('DOMContentLoaded', performInitialSearch);

function displayResults(approvals) {
    const resultElement = document.getElementById('result');
    resultElement.innerHTML = ''; // Clear previous content

    const table = document.createElement('table');
    table.innerHTML = `
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>대상</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    `;

    const tbody = table.querySelector('tbody');
    approvals.forEach(approval => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${approval.id}</td>
            <td>
                <a href="#" onclick="viewPost(${approval.id}); return false;">
                    <span>${approval.title}</span>
                </a>
            </td>
            <td>${approval.type}</td>
        `;
        tbody.appendChild(row);
    });

    resultElement.appendChild(table);
}

function searchApprovals() {
    const page = currentPage !== undefined ? currentPage : null;
    const size = 10;

    const xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:8095/api/v1/pay?page=${page}&size=${size}`, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const responseData = JSON.parse(xhr.responseText);
                console.log('검색 결과:', responseData);
                displayResults(responseData);
            } else {
                console.error('검색 실패. 상태 코드:', xhr.status);
            }
        }
    };

    xhr.send();
}

function viewPost(postId) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:8095/api/v1/approval/${postId}`, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const post = JSON.parse(xhr.responseText);
                console.log('게시글 상세 내용:', post);


                modalTitle.innerText = post.title;
                modalContent.innerText = post.content;
                modalId.innerText = post.id.toString();
                modalTime.innerText = post.start.toString();

                modal.style.display = 'block';
            } else {
                console.error('게시글 조회 실패. 상태 코드:', xhr.status);
            }
        }
    };

    xhr.send();
}

// 모달을 닫는 함수
function closeModal() {
    const modal = document.getElementById('modal');
    modal.style.display = 'none';
}


// 모달 닫기 버튼에 클릭 이벤트 리스너 추가
const closeBtn = document.querySelector('.close');
closeBtn.addEventListener('click', closeModal);

function pay() {

    const xhr = new XMLHttpRequest();
    xhr.open('POST', `http://localhost:8095/pay/free/${modalId.innerText}`, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("정산처리 되었습니다.");
            } else {
                console.error('실패:', xhr.status);
            }
        }
    };

    xhr.send();
}