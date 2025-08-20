document.addEventListener('DOMContentLoaded', function() {
    const confirmBidBtn = document.getElementById('confirmBidBtn');
    if (confirmBidBtn) {
        confirmBidBtn.addEventListener('click', function() {
            // 모달 닫는 로직 (선택 사항)
            const bidModalElement = document.getElementById('bidModal');
            const bidModal = bootstrap.Modal.getInstance(bidModalElement);
            if (bidModal) {
                bidModal.hide();
            }

            // 페이지 이동
            window.location.href = "http://localhost:8080/reservation";
        });
    }
});


$(document).ready(function() {
    // product.price 값은 HTML에서 hidden input으로 전달받거나, 
    // 페이지 로드 시 어딘가에서 설정된 값을 읽어와야 합니다.
    // 여기서는 HTML의 initial-price hidden input에서 값을 가져오는 방식으로 수정합니다.
    const BID_UNIT = 1000; // 입찰 단위
    const COMMISSION_RATE = 0.099; // 구매 수수료율 (9.9%)

    // HTML의 hidden input에서 현재가(product.price) 값을 가져옵니다.
    // 값이 없을 경우 기본값 0으로 설정합니다.
    let currentPrice = parseInt($('#initial-price').val()) || 0; 
    let bidPrice;

    // 숫자 포맷팅 함수 (예: 100000 -> 100,000)
    function formatNumber(number) {
        return new Intl.NumberFormat('ko-KR').format(number);
    }

    // 한글로 숫자 읽는 함수 (간단 버전, 큰 금액은 복잡해질 수 있음)
    function convertNumberToKorean(num) {
        const units = ["", "만", "억", "조"];
        const numStr = String(num);
        let result = "";
        let unitIndex = 0;

        for (let i = numStr.length; i > 0; i -= 4) {
            let part = numStr.substring(Math.max(0, i - 4), i);
            let partNum = parseInt(part, 10);
            if (partNum > 0) {
                // '일'이 붙는 경우를 제외 (예: 일십만 -> 십만)
                let tempPart = formatNumber(partNum);
                result = tempPart + units[unitIndex] + " " + result;
            }
            unitIndex++;
        }
        return result.trim() + " 원";
    }
    
    // 추가: '일' 단위 보정 함수 (예: 100000 -> 일십만 -> 십만)
    function convertNumberToKoreanAccurate(num) {
        if (num === 0) return "영 원";

        const units = ["", "만", "억", "조"];
        const numUnits = ["", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"];
        const tenUnits = ["", "십", "백", "천"];

        let numStr = String(num);
        let result = [];
        let unitIndex = 0; // 억, 만, 단위

        // 4자리씩 끊어서 처리
        while (numStr.length > 0) {
            let fourDigits = numStr.slice(-4);
            numStr = numStr.slice(0, -4);

            let partResult = "";
            for (let i = 0; i < fourDigits.length; i++) {
                let digit = parseInt(fourDigits[fourDigits.length - 1 - i]);
                if (digit > 0) {
                    partResult = numUnits[digit] + tenUnits[i] + partResult;
                }
            }
            
            if (partResult) {
                // "일" 십, 백, 천 앞의 "일" 제거 (예: 일십 -> 십, 일백 -> 백)
                partResult = partResult.replace(/일(십|백|천)/g, "$1");
                result.unshift(partResult + units[unitIndex]);
            }
            unitIndex++;
        }

        // 최종 결과에 "원" 붙이고 공백 제거
        let finalResult = result.join(' ').trim() + " 원";
        // '일만' 대신 '만', '일억' 대신 '억'
        finalResult = finalResult.replace(/일(만|억|조)/g, "$1");

        return finalResult;
    }


    // 입찰가 및 예상 구매가 업데이트 함수
    function updateBidPrices() {
        // 입찰가 업데이트
        $('#bid-price').val(formatNumber(bidPrice) + ' 원');

        // 구매 수수료 계산
        const commission = Math.floor(bidPrice * COMMISSION_RATE);
        const estimatedPurchasePrice = bidPrice + commission;

        // 예상 구매가 및 세부 정보 업데이트
        $('#estimated-purchase-price').text(formatNumber(estimatedPurchasePrice) + '원');
        $('#bid-price-calc').text(formatNumber(bidPrice) + '원');
        $('#commission-price-calc').text(formatNumber(commission) + '원');

        // 모달 내 입찰 희망 금액 업데이트
        $('#modal-bid-price').text(formatNumber(bidPrice) + '원');
        // 수정된 한글 변환 함수 사용
        $('#modal-bid-price-korean').text('(' + convertNumberToKoreanAccurate(bidPrice) + ')');
    }

    // 초기 입찰가 설정: 현재가 + 입찰 단위
    // currentPrice가 0이 아닌 경우에만 초기 설정 (Thymeleaf에서 값이 제대로 넘어왔을 때)
    if (currentPrice > 0) {
        bidPrice = currentPrice + BID_UNIT;
    } else {
        // product.price가 0이거나 없을 경우를 대비한 기본 시작 입찰가
        // 예를 들어 1,000원부터 시작하거나, 다른 로직을 따를 수 있습니다.
        // 여기서는 예시로 1,000원을 기본으로 설정합니다.
        bidPrice = BID_UNIT; 
        // 만약 현재가가 반드시 있어야 한다면, 이 else 블록은 에러 처리 또는 알림으로 대체할 수 있습니다.
    }
    
    updateBidPrices(); // 초기 값으로 UI 업데이트

    // '+' 버튼 클릭 이벤트
    $('#btn-plus').on('click', function() {
        bidPrice += BID_UNIT;
        updateBidPrices();
    });

    // '-' 버튼 클릭 이벤트
    $('#btn-minus').on('click', function() {
        // 최소 입찰 금액은 currentPrice + BID_UNIT 입니다.
        // 예를 들어 currentPrice가 20000원이고 BID_UNIT이 1000원이면, 최소 bidPrice는 21000원입니다.
        if (bidPrice > (currentPrice + BID_UNIT - 1)) { 
            bidPrice -= BID_UNIT;
            updateBidPrices();
        } else if (bidPrice === (currentPrice + BID_UNIT)) {
            alert('최소 입찰 금액은 현재가보다 ' + formatNumber(BID_UNIT) + '원 높아야 합니다.');
        }
    });

    // 모달이 열릴 때 최신 입찰가를 반영
    $('#bidModal').on('show.bs.modal', function () {
        updateBidPrices();
    });
	
	$(document).off('click', '.toggle-favorite-btn').on('click', '.toggle-favorite-btn', function() {
		console.log('Favorite button clicked!');
		const button = $(this);
		const itemId = button.data('item-id');
		const isFavorited = button.data('favorited'); // 현재 즐겨찾기 상태 (true/false)
		const token = $("meta[name='_csrf']").attr("content");
		const header = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
			url: '/favorites/toggle', // FavoriteController의 toggleFavorite 엔드포인트
			type: 'POST',
			data: {
				itemId: itemId
			},
			beforeSend: function(xhr) {
				if (header && token) {
					xhr.setRequestHeader(header, token);
				}
			},
			success: function(response) {
				if (response.status === 'added' || response.status === 'removed') {
					// UI 업데이트: data-favorited 속성 토글 및 아이콘 변경
					const newFavoritedStatus = (response.status === 'added');
					button.data('favorited', newFavoritedStatus);
					if (newFavoritedStatus) {
						button.find('i').removeClass('far').addClass('fas'); // 채워진 하트
					} else {
						button.find('i').removeClass('fas').addClass('far'); // 빈 하트
					}

					// 모달 메시지 변경
					const modalTitle = $('#favoriteModalLabel');
					const modalBody = $('#favoriteModal .modal-body p.fw-bold');
					const modalSubText = $('#favoriteModal .modal-body p.text-muted');

					if (newFavoritedStatus) {
						modalTitle.text('관심경매 등록');
						modalBody.text('관심경매 등록이 완료되었습니다.');
						modalSubText.text('마이페이지 > 관심경매 목록에서 확인할 수 있습니다.');
					} else {
						modalTitle.text('관심경매 해제');
						modalBody.text('관심경매가 해제되었습니다.');
						modalSubText.text('마이페이지 > 관심경매 목록에서 더 이상 확인할 수 없습니다.');
					}
					$('#favoriteModal').modal('show');
				} else {
					alert(response.message);
				}
			},
			error: function(xhr, status, error) {
				if (xhr.status == 401) {
					alert('로그인이 필요합니다.');
				} else {
					alert('관심 상품 처리 중 오류가 발생했습니다: ' + (xhr.responseJSON ? xhr.responseJSON.message : error));
				}
			}
		});
	});
});