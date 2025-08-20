// chat_dropdown.js

// DOM이 완전히 로드된 후 실행됩니다.
$(document).ready(function() {
    
    const $chatList = $('#chatDropdown + .dropdown-menu');

    function loadChatRooms() {
        fetch('/api/chat/rooms')
            .then(response => {
                if (response.status === 401) return null;
                if (!response.ok) throw new Error('네트워크 응답 오류');
                return response.json();
            })
            .then(chatRooms => {
                $chatList.find(".dynamic-chat-item").remove();

                if (chatRooms && chatRooms.length > 0) {
                    chatRooms.forEach(room => addOrUpdateChatItem(room));
                } else if (chatRooms) {
                    const noChatHtml = `<li class="dynamic-chat-item"><span class="dropdown-item-text text-muted text-center">채팅방이 없습니다.</span></li>`;
                    $chatList.find('li:last-child').before(noChatHtml);
                }
            })
            .catch(error => {
                console.error('채팅방 목록 로드 오류:', error);
                $chatList.find(".dynamic-chat-item").remove();
                const errorHtml = `<li class="dynamic-chat-item"><span class="dropdown-item-text text-danger">목록 로드 실패</span></li>`;
                $chatList.find('li:last-child').before(errorHtml);
            });
    }

    // 전체 안 읽음 상태를 확인하고 메인 아이콘의 점을 업데이트하는 함수
    function checkOverallUnreadStatus() {
        const hasUnread = $chatList.find(".unread-dot").length > 0;
        $("#main-chat-unread-dot").toggle(hasUnread);
    }

    function addOrUpdateChatItem(roomData) {
        const { chatRoomId, otherUserId, lastMessage, unreadCount } = roomData;
        const $existingItem = $chatList.find(`[data-chatroom-id="${chatRoomId}"]`);

        const unreadDotHtml = unreadCount > 0 ? '<span class="unread-dot"></span>' : '';

        const newItemHtml = `
            <a class="dropdown-item" href="/chat?partnerId=${otherUserId}">
                <span class="fw-bold">${otherUserId}</span> 님과 대화 ${unreadDotHtml}
                <br>
                <small class="text-muted text-truncate last-message">${lastMessage || '메시지 없음'}</small>
            </a>
        `;

        if ($existingItem.length > 0) {
            $existingItem.html(newItemHtml);
        } else {
            const newListItem = $("<li></li>")
                .addClass("dynamic-chat-item")
                .attr("data-chatroom-id", chatRoomId)
                .html(newItemHtml);
            $chatList.find('li:last-child').before(newListItem);
        }
        checkOverallUnreadStatus(); // 아이템 추가/업데이트 후 전체 상태 확인
    }

    // 전역 소켓으로부터 실시간 업데이트 이벤트를 수신
    document.addEventListener('chatUpdateReceived', function(e) {
        console.log('드롭다운: 실시간 업데이트 이벤트 수신', e.detail);
        addOrUpdateChatItem(e.detail);
    });

    const $chatDropdown = $('#chatDropdown');
    if ($chatDropdown.length) {
        loadChatRooms(); // 페이지 로드 시 즉시 채팅방 목록을 로드하여 상태를 확인
        $chatDropdown.on('show.bs.dropdown', loadChatRooms); // 드롭다운을 열 때도 목록을 새로고침
    }
});
