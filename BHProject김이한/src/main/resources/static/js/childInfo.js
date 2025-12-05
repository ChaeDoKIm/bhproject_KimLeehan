
$('#add_child_form').hide();

$('#addChild').click(() => {
    $('#add_child_form').show();
});

$('#submitButton').click(function (event) {
    event.preventDefault();  // 폼 제출을 방지

    // 라디오 버튼에서 선택된 성별 값을 가져옵니다.
    var sGender = $('input[name="sGender"]:checked').val();

    // 선택되지 않은 경우, 기본값을 설정할 수 있습니다. 예: '여'
    if (!sGender) {
        sGender = '여'; // 기본값 설정 (필요에 따라 변경)
    }

    var studentData = {
        sName: $('#sName').val(),
        sGrade: $('#sGrade').val(),
        sGender: sGender,
        pId: $('#pId').val()
    };

    $.ajax({
        url: '/sAddChild',
        type: 'POST',
        data: studentData,
        success: function (response) {
            alert('학생이 성공적으로 등록되었습니다!');
            // 학생 등록 후 원래 페이지로 돌아가도록 리다이렉트
            window.location.href = "/myChildP/" + $('#pId').val(); // 부모 로그인 ID로 돌아가기
        },
        error: function (xhr, status, error) {
            alert('학생 등록에 실패했습니다.');
        }
    });
});

let loginId = $('#parentLoginId').val();

let showComments = $('#showComments');

showComments.click(() => {
    const $commentList = $('#commentList');
    let clProgId = $('#clProgId').val();

    if ($commentList.children().length > 0) {
        $commentList.empty();  // 코멘트 숨기기
        showComments.empty();
        showComments.text('이전 코멘트 보기');
        return;
    }

    $.ajax({
        url: `/showComments/${clProgId}`,
        type: 'POST',
        dataType: "json",
        success: function (list) {
            showComments.empty();
            showComments.text('숨기기');

            console.log(list);
            let html = '';
            html += `
                <div id="teacherCommentList">
                    <table>
                `;
            if (list && list.length > 0) {
                html += `
                    <tr>
                        <th>회차</th>
                        <th style="text-align: center">코멘트</th>
                    </tr>
                    `;
                for (let i = 1; i < list.length; i++) {
                    html += `
                            <tr>
                                <td><strong>${list[i].cprogress}</strong></td>
                                <td>
                                    <div><strong>진도:</strong> ${list[i].cpage}<br/>
                                    <strong>수업 내용:</strong> ${list[i].cclassContents}<br/>
                                    ${list[i].ccontents}</div>
                                </td>
                            </tr>
                        `;
                }
            } else {
                html += `<tr><td colspan="2">코멘트가 없습니다.</td></tr>`;
            }
            html += `</table>
                    </div>`;
            console.log(list);
            $commentList.html(html);
        },
        error: function (xhr, status, error) {
            console.error(error);
            alert("코멘트 불러오기 실패했습니다.");
        }
    });

});