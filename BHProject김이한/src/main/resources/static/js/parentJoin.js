// 아이디 중복체크
let pId = $('#pId');            // 입력받은 아이디
let check1 = $('#check1');      // 확인할 아이디
let check_id = false;           // 존재여부


// ajax로 id 가입여부 확이하기
pId.keyup(() => {
    $.ajax({
        type: "POST",
        url: "idCheck",
        data: {"pId": pId.val()},
        dataType: "text",
        success: function (result) {
            if (result == "OK") {
                check1.html("사용가능한 아이디입니다");
                check1.css('color', 'green');
                check_id = true;
            } else {
                check1.html("사용할 수 없는 아이디입니다");
                check1.css('color', 'red');
                check_id = false;
            }
        },
        error: function () {
            alert('idCheck 통신 실패')
        }
    });
});

// 비밀번호 정규화
let pPass = $('#pPass');
let check2 = $('#check2');
let check_pw1 = false;

pPass.keyup(() => {
    let pwVal = pPass.val();
    let num = pwVal.search(/[0-9]/);         // 숫자여부 확인
    let eng = pwVal.search(/[a-z]/);         // 소문자
    let eng1 = pwVal.search(/[A-Z]/);        // 소문자
    let spe = pwVal.search(/[~!@#$%^&*]/)    // 특수문자
    let spc = pwVal.search(/\s/)             // 공백

    console.log(`num : ${num}, eng : ${eng}, eng1 : ${eng1}, spe : ${spe}, spc : ${spc}`)

    if (pwVal.length < 1 || pwVal.length > 100) {
        check2.html('8-16자리 입력해주세요');
        check2.css('color', 'red');
        check_pw1 = false;
        // } else if (spc != -1) {
        //     check2.html('공백없이 입력해주세요');
        //     check2.css('color', 'red');
        //     check_pw1 = false;
        // } else if (num == -1 || eng == -1 || eng1 == -1 || spe == -1) {
        //     check2.html('숫자, 특수문자, 대소문자를 혼합해서 입력해주세요');
        //     check2.css('color', 'red');
        //     check_pw1 = false;
    } else {
        check2.html('사용가능한 비밀번호입니다.');
        check2.css('color', 'green');
        check_pw1 = true;
    }

});

// 비밀번호 확인
let pwCheck = $('#pwCheck');
let check3 = $('#check3');
let check_pw2 = false;

pwCheck.keyup(() => {

    let pwVal = pPass.val();
    let pwCheckVal = pwCheck.val();

    if (pwCheckVal != pwVal) {
        check3.html('비밀번호가 일치하지 않습니다');
        check3.css('color', 'red');
        check_pw2 = false;
    } else {
        check3.html('비밀번호가 일치합니다');
        check3.css('color', 'green');
        check_pw2 = true;
    }
    console.log(`pwVal : ${pwVal}, pwCheckVal : ${pwCheckVal}`)

});

// 이메일 인증하기
let pEmail = $('#pEmail');
let checkEmail = $('#checkEmail');
let check4 = $('#check4');
let check_email = false;

checkEmail.click(() => {
    $.ajax({
        type: "POST",
        url: "emailCheck",
        data: {"pEmail": pEmail.val()},
        dataType: "text",
        success: (uuid) => {
            console.log(uuid);
            check4.empty();
            check4.append(`<input type="text" id="uuid1" size="25"/>`);
            check4.append(`<input type="button" value="인증" id="btnUUID" data-value="${uuid}"/>`);
        },
        error: () => {
            alert('emailCheck 통신 실패');
        }
    });
});

// 인증번호 확인하기
$(document).on('click', '#btnUUID', function () {
    // <input type="button" value="인증" id="btnUUID" data-value="${uuid}"/>
    // 인증 버튼 클릭했을 때 해당하는 요소에 데이터로 지정한 값을 가져온다
    let uuid = $(this).data("value");

    // <input type="text" id="uuid1" />
    // 입력한 인증번호
    let uuid1 = $('#uuid1').val();

    if (uuid == uuid1) {
        alert('이메일이 인증되었습니다');
        check4.hide();
        pEmail.attr('readonly', true);
        check_email = true;
    } else {
        alert('이메일 인증 실패했습니다. 인증번호를 확인해주세요.');
        $('#uuid').val("");
        check_email = false;
    }
});

// 다음 주소 api
// function sample6_execDaumPostcode() {
//     new daum.Postcode({
//         oncomplete: function (data) {
//             // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
//
//             // 각 주소의 노출 규칙에 따라 주소를 조합한다.
//             // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
//             var addr = ''; // 주소 변수
//             var extraAddr = ''; // 참고항목 변수
//
//             //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
//             if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
//                 addr = data.roadAddress;
//             } else { // 사용자가 지번 주소를 선택했을 경우(J)
//                 addr = data.jibunAddress;
//             }
//
//             // 우편번호와 주소 정보를 해당 필드에 넣는다.
//             // document.getElementById('sample6_postcode').value = data.zonecode;
//             document.getElementById("sample6_address").value = addr;
//             // 커서를 상세주소 필드로 이동한다.
//             document.getElementById("sample6_detailAddress").focus();
//         }
//     }).open();
// }

// // 프로필 사진 미리보기
// $('#mProfile').on('change', function(event){
//     let file = event.target.files[0];
//     let reader = new FileReader();
//
//     // 미리보기 경로 설정
//     reader.onload = function(e) {
//         $('#preImage').attr('src', e.target.result);
//     }
//
//     reader.readAsDataURL(file);
// })

// $('#submitForm').click(() => {
//     // 주소 합체
//     let pAddress = $('#pAddress');
//
//     let addr1 = $('#sample6_address').val();
//     let addr2 = $('#sample6_detailAddress').val();
//
//     pAddress.val(`${addr1}, ${addr2}`);
//     // 인천시 미추홀구 매소홀로 6032, 태승빌딩 5층
//
//     if (!check_id) {
//         alert('아이디 중복체크를 진행해주세요');
//         pId.focus();
//         // } else if (!check_pw1 || !check_pw2) {
//         //     alert('비밀번호를 확인해주세요');
//         //     mPw.focus();
//     } else if (!check_email) {
//         alert('이메일 인증을 진행해주세요');
//         pEmail.focus();
//     } else if (addr2 == null || addr2 === "") {
//         alert('상세주소 입력해주세요');
//     } else {
//         pJoinForm.submit();
//     }
// });
//
//


// 다음 주소 API
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            let addr = (data.userSelectedType === 'R') ? data.roadAddress : data.jibunAddress;
            $('#sample6_address').val(addr);
            $('#pAddress').val(addr); // Hidden 필드에 기본 주소 저장
            $('#sample6_detailAddress').focus();
            console.log("주소 저장됨:", $('#pAddress').val());
        }
    }).open();
}

// 회원가입 제출
$('#submitForm').click(() => {
    let pAddress = $('#pAddress');
    let addr1 = $('#sample6_address').val().trim();
    let addr2 = $('#sample6_detailAddress').val().trim();

    // Hidden 필드에 최종 주소 저장
    pAddress.val(`${addr1}, ${addr2}`);
    console.log("입력된 주소:", addr1);
    console.log("입력된 상세주소:", addr2);
    console.log("최종 저장된 주소:", pAddress.val());

    // 필수 항목 확인
    if (!check_id) {
        alert('아이디 중복체크를 진행해주세요');
        $('#pId').focus();
        return;
    }
    if (!check_email) {
        alert('이메일 인증을 진행해주세요');
        $('#pEmail').focus();
        return;
    }
    if (!addr1 || addr1 === "") {
        alert('주소를 입력해주세요');
        $('#sample6_address').focus();
        return;
    }
    if (!addr2 || addr2 === "") {
        alert('상세주소를 입력해주세요');
        $('#sample6_detailAddress').focus();
        return;
    }

    // 폼 제출
    $('form[name="pJoinForm"]').submit();
});
