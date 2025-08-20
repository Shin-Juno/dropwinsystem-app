/*

TemplateMo 559 Zay Shop

https://templatemo.com/tm-559-zay-shop

*/

'use strict';
$(document).ready(function() {

	// Accordion
	var all_panels = $('.templatemo-accordion > li > ul').hide();

	$('.templatemo-accordion a').each(function() {
		var linkHref = $(this).attr('href');

		// Hover Dropdown (추가)
		$('.templatemo-accordion > li').hover(
			function() {
				var target = $(this).find('ul');
				all_panels.not(target).removeClass('active').slideUp();
				if (!target.hasClass('active')) {
					target.addClass('active').slideDown();
				}
			},
			function() {
				var target = $(this).find('ul');
				target.removeClass('active').slideUp();
			}
		);

		// Product detail
		$('.product-links-wap a').click(function() {
			var this_src = $(this).children('img').attr('src');
			$('#product-detail').attr('src', this_src);
			return false;
		});
		$('#btn-minus').click(function() {
			var val = $("#var-value").html();
			val = (val == '1') ? val : val - 1;
			$("#var-value").html(val);
			$("#product-quanity").val(val);
			return false;
		});
		$('#btn-plus').click(function() {
			var val = $("#var-value").html();
			val++;
			$("#var-value").html(val);
			$("#product-quanity").val(val);
			return false;
		});
		$('.btn-size').click(function() {
			var this_val = $(this).html();
			$("#product-size").val(this_val);
			$(".btn-size").removeClass('btn-secondary');
			$(".btn-size").addClass('btn-success');
			$(this).removeClass('btn-success');
			$(this).addClass('btn-secondary');
			return false;
		});
		// End roduct detail

		// TOP버튼 
		$('#scrollToTopBtn').click(function(e) {
			e.preventDefault();
			$('html, body').stop().animate({
				scrollTop: 0
			}, 0);
		});

		// 스크롤 시 경매절차 탭 활성화
		$(window).scroll(function() {
			var scrollPos = $(window).scrollTop();
			var purchaseProcessSection = $('#purchase-process-start');
			var bidGuideSection = $('#bid-guide-section');
			var auctionProcessTab = $('.tab-sub[data-target="#purchase-process-start"]');
			var purchaseProcessSectionTop = purchaseProcessSection.offset().top - 10;
			var bidGuideSectionTop = bidGuideSection.offset().top - 10;


			if (scrollPos >= purchaseProcessSectionTop && scrollPos < bidGuideSectionTop) {
				if (!auctionProcessTab.hasClass('active')) {
					$('.tab-sub').removeClass('active');
					auctionProcessTab.addClass('active');
				}
			}
		});


		// 탭 클릭 시 스크롤 이벤트 (경매절차, 입찰안내, 수수료)
		$('.tab-sub[data-target]').on('click', function(e) {
			e.preventDefault();

			$('.tab-sub').removeClass('active');
			$(this).addClass('active');

			var targetId = $(this).data('target');

			if ($(targetId).length) {
				var targetOffset = $(targetId).offset().top;

				$('html, body').stop().animate({
					scrollTop: targetOffset - 10
				}, 0);
			}
		});

		// 게시글 쓰기 폼 
		$("#writeForm").off('submit').on("submit", function() {
			if ($("#title").val().trim().length <= 0) {
				alert("제목을 입력해주세요.");
				$("#title").focus();
				return false;
			}
			if ($("#content").val().trim().length <= 0) {
				alert("내용을 입력해주세요.");
				$("#content").focus();
				return false;
			}
		});

		// 게시글 수정 폼
		$("#updateForm").off('submit').on("submit", function(event) {
			if ($("#title").val().trim().length <= 0) {
				alert("제목이 입력되지 않았습니다.\n제목을 입력해주세요");
				$("#title").focus();
				event.preventDefault();
				return false;
			}
			if ($("#content").val().trim().length <= 0) {
				alert("내용이 입력되지 않았습니다.\n내용을 입력해주세요");
				$("#content").focus();
				event.preventDefault();
				return false;
			}
		});


		// 삭제 버튼 클릭 이벤트 처리 
		$("#deleteBtn").off('click').on("click", function() {
			console.log("삭제 버튼이 클릭되었습니다.");

			var boardNo = $("input[name='no']").val();

			if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
				console.log("삭제 확인됨. AJAX 요청 시작...");
				$.ajax({
					url: "/deleteBoard",
					type: "POST",
					data: {
						no: boardNo
					},
					success: function(response) {
						console.log("AJAX 성공 응답:", response);
						if (response.success) {
							alert("게시글이 성공적으로 삭제되었습니다.");
							window.location.href = "/board";
						} else {
							alert("삭제 실패: " + response.message);
						}
					},
					error: function(xhr, status, error) {
						console.error("AJAX 오류 발생:", status, error);
						alert("삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
					}
				});
			} else {
				console.log("삭제 취소됨.");
			}
		});

	});
	// End accordion

	// Hover Dropdown (추가)
	$('.templatemo-accordion > li').hover(
	    function () {
	        var target = $(this).find('ul');
	        all_panels.not(target).removeClass('active').slideUp();
	        if (!target.hasClass('active')) {
	            target.addClass('active').slideDown();
	        }
	    },
	    function () {
	        var target = $(this).find('ul');
	        target.removeClass('active').slideUp();
	    }
	);

    // Product detail
    $('.product-links-wap a').click(function(){
      var this_src = $(this).children('img').attr('src');
      $('#product-detail').attr('src',this_src);
      return false;
    });
    $('#btn-minus').click(function(){
      var val = $("#var-value").html();
      val = (val=='1')?val:val-1;
      $("#var-value").html(val);
      $("#product-quanity").val(val);
      return false;
    });
    $('#btn-plus').click(function(){
      var val = $("#var-value").html();
      val++;
      $("#var-value").html(val);
      $("#product-quanity").val(val);
      return false;
    });
    $('.btn-size').click(function(){
      var this_val = $(this).html();
      $("#product-size").val(this_val);
      $(".btn-size").removeClass('btn-secondary');
      $(".btn-size").addClass('btn-success');
      $(this).removeClass('btn-success');
      $(this).addClass('btn-secondary');
      return false;
    });
    // End roduct detail
	
});
