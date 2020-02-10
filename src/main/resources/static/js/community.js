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
                if (response.code == 2003) {
                    var isAccept = confirm(response.message);
                    if (isAccept) {
                        window.open("https://github.com/login/oauth/authorize?client_id=53cd0606229ddef3e6e0&redirect_uri=http://localhost:8887/callback&scope=user&state=edcvfr");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }

            }
        },
        dataType: "json",
        contentType: "application/json"
    });
}