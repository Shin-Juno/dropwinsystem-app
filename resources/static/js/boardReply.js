// DOM(Document Object Model)이 준비되면
$(function() {
    $(".btnCommend").click(function() {
        var com = $(this).attr("id");
        console.log("com : " + com);

        $.ajax({
            url: "recommend.ajax",
            type: "post",
            data: { 
                recommend: com, 
                no: $("#no").val() 
            },
            dataType: "json",
            success: function(data) {
                alert("추천이 반영 되었습니다.");
                $("#commend > .recommend").text(" (" + data.recommend + ")");
            },
            error: function(xhr, status, error) {
                alert("error : " + xhr.statusText + ", " + status + ", " + error);
            }
        });
    });
});
