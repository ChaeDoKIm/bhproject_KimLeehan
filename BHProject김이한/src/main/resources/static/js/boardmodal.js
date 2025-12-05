
let loginId = $("#loginId").val();
$("#check-my-faq").click(() => {
    location.href = `/board/myfaq/${loginId}`;
});

document.addEventListener("DOMContentLoaded", () => {
    const modal = $("#applyModal");
    const openModal = $("#control-modal");
    const closeModal = $("#closeBtn");

    openModal.click(() => {
        modal.css("display", "block");
    });

    closeModal.click(() => {
        modal.css("display", "none");
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});


let searchBtn = $("#searchBtn");

searchBtn.click(() => {
    let applyPass = $("#applyPass").val();
    let loginId = $("#applyId").val();

    $.ajax({
        type: "POST",  // Change to POST
        url: "/board/noMemFAQ",
        contentType: "application/x-www-form-urlencoded", // Default for form data
        data: {
            "qId": loginId,
            "qPass": applyPass
        },
        dataType: "text", // Expecting a plain text response
        success: function (result) {
            if (result === "OK") {
                console.log(result);
                window.location.href = `/board/myfaq/${loginId}`; // Fixed redirection
            } else if (result === "NO") {
                alert("이메일 또는 비밀번호가 틀렸습니다");
            } else {
                alert("작성된 글이 없습니다");
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
});
