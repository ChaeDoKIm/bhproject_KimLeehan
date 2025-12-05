// let percentage = 0;
// const percentageElement = document.getElementById('loading-percentage');
//
// const updatePercentage = () => {
//     if (percentage < 100) {
//         percentage += 1;
//         percentageElement.textContent = `${percentage}%`;
//         setTimeout(updatePercentage, 50);
//     }
// };
//
// updatePercentage();



let percentage = 0;
const percentageElement = document.getElementById('loading-percentage');
const mainLoad = document.getElementById("main-loading");

// 이미지 경로
const images = ["/images/로딩딩가1.png", "/images/로딩딩가2.png"];
let currentImageIndex = 0;

// 이미지 교체 함수
const updateImage = () => {
    currentImageIndex = (currentImageIndex + 1) % images.length; // 이미지 인덱스 순환
    mainLoad.src = images[currentImageIndex]; // 이미지 교체
};

// 퍼센트 업데이트 함수
const updatePercentage = () => {
    if (percentage < 100) {
        percentage += 1;
        percentageElement.textContent = `${percentage}%`;

        // 10%마다 이미지 변경
        if (percentage % 10 === 0) {
            updateImage();
        }

        // 속도 조정 (50ms 추천)
        setTimeout(updatePercentage, 50);
    } else {
        // 100%가 되면 main으로 이동
        window.location.href = '/main';
    }
};

// 실행
updatePercentage();
