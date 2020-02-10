function post() {
    var quesitonId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": quesitonId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#comment_section").hide();
            } else {
                alert(response.message);
            }
        },
        dataType: "json",
        contentType: "application/json"
    });
}