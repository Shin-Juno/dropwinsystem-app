$(function() {
	
	$(".btnCount").click(function() {
		
		var com = $(this).attr("id");
		console.log("com : " + com);
		
		$.ajax({			
			url: "likeCount.ajax",
			type: "post",
			data : { likeCount: com, noticeId : $("#noticeId").val()},
			dataType: "json",
			success: function(data) {	

				var msg = com == 'likeCount' ? "추천이" : "비추천이";
				alert(msg + " 반영 되었습니다.");
				$("#likeCount > .likeCount").text(" (" + data.likeCount + ")");
				$("#dislikeCount > .likeCount").text(" (" + data.dislikeCount + ")");				
			},
			error: function(xhr, status, error) {
				alert("error : " + xhr.statusText + ", " + status + ", " + error);
			}
		});
	});		
});