$(function() {
	
	// 게시글 수정 폼 요청 처리
	$("#noDetailUpdate").on("click", function() {
		$("#checkForm").attr("action", "noUpdateForm");
		$("#checkForm").attr("method", "post");
		$("#checkForm").submit();
	});

	/* 게시글 상세보기에서 게시글 삭제 요청 처리 
	 * 확인창(alert → confirm)으로 실제 삭제 여부 묻기
	 */
	$("#noDetailDelete").on("click", function() {
		if (!confirm("정말 삭제하시겠습니까?")) {
			return false;
		}
		
		$("#checkForm").attr("action", "delete");
		$("#checkForm").attr("method", "post");
		$("#checkForm").submit();
	});

	// 게시글 쓰기 폼 유효성 검사
	$("#noWriteForm").on("submit", function() {
		if($("#writer").val().length <= 0) {
			alert("작성자가 입력되지 않았습니다.\n작성자를 입력해주세요");
			$("#writer").focus();			
			return false;
		}
		if($("#title").val().length <= 0) {
			alert("제목이 입력되지 않았습니다.\n제목을 입력해주세요");
			$("#title").focus();
			return false;
		}
		if($("#content").val().length <= 0) {
			alert("내용이 입력되지 않았습니다.\n내용을 입력해주세요");
			$("#content").focus();
			return false;
		}
	});

	// 게시글 수정 폼 유효성 검사
	$("#noUpdateForm").on("submit", function() {
		if($("#writer").val().length <= 0) {
			alert("작성자가 입력되지 않았습니다.\n작성자를 입력해주세요");
			$("#writer").focus();			
			return false;
		}
		if($("#title").val().length <= 0) {
			alert("제목이 입력되지 않았습니다.\n제목을 입력해주세요");
			$("#title").focus();
			return false;
		}
		if($("#content").val().length <= 0) {
			alert("내용이 입력되지 않았습니다.\n내용을 입력해주세요");
			$("#content").focus();
			return false;
		}
	});
});
