$(function() {
  // 챗봇 버튼 클릭 시 모달 열기 + 버튼 비활성화 + iframe 새로고침
  $("#chatbot-btn").on("click", function() {
    $("#chatbot-modal").show();
    $(this).prop("disabled", true);
    // iframe 새로고침
    $("#chatbot-iframe").attr("src", function(i, val) { return val; });
  });

  // 닫기 버튼 클릭 시 모달 닫기 + 버튼 활성화
  $("#chatbot-close-btn").on("click", function() {
    $("#chatbot-modal").hide();
    $("#chatbot-btn").prop("disabled", false);
  });

  // 모달 밖 클릭 시 모달 닫기 + 버튼 활성화
  $(document).on("click", function(event) {
    if (!$(event.target).closest("#chatbot-modal, #chatbot-btn").length) {
      $("#chatbot-modal").hide();
      $("#chatbot-btn").prop("disabled", false);
    }
  });

  // ESC 키 누르면 모달 닫기 + 버튼 활성화
  $(document).on('keydown', function(e) {
    if(e.key === "Escape") {
      $("#chatbot-modal").hide();
      $("#chatbot-btn").prop("disabled", false);
    }
  });
});
