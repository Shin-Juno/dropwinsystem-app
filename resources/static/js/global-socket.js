// global-socket.js

document.addEventListener("DOMContentLoaded", function() {
    // 세션에서 로그인 정보를 확인하여 myUserId를 설정합니다.
    // 이 예제에서는 header.html의 Thymeleaf 변수를 통해 myUserId를 가져옵니다.
    if (typeof myUserId !== 'undefined' && myUserId) {
        connectGlobalSocket();
    }
});

function connectGlobalSocket() {
    const socket = new SockJS('/ws'); // 웹소켓 엔드포인트
    const stompClient = new StompJs.Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000, // 5초마다 재연결 시도
        debug: function(str) {
            // console.log('STOMP (Global): ' + str);
        }
    });

    stompClient.onConnect = function(frame) {
        console.log('전역 웹소켓 연결 성공.');

        // 현재 로그인한 사용자의 개인 채널을 구독합니다.
        // 서버의 SimpMessagingTemplate.convertAndSendToUser가 이 주소로 메시지를 보냅니다.
        stompClient.subscribe(`/user/topic/chat-updates`, function(message) {
            const updateData = JSON.parse(message.body);
            console.log('채팅방 업데이트 알림 수신:', updateData);

            // 드롭다운 UI를 업데이트하는 커스텀 이벤트를 발생시킵니다.
            // chat_dropdown.js에서 이 이벤트를 수신하여 처리합니다.
            document.dispatchEvent(new CustomEvent('chatUpdateReceived', { detail: updateData }));
        });
    };

    stompClient.onStompError = function(frame) {
        console.error('전역 웹소켓 STOMP 오류:', frame);
    };

    stompClient.activate(); // 웹소켓 연결 활성화
}
