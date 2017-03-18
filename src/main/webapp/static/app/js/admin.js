function getUsers(url) {
    $.ajax({
        type: "GET",
        url: url,
        contentType: "application/json",
        dataType: "json",
        success: function (data, textstatus, jqxhr) {
            var allUser = data.users;
            var output = "<form>";

            for (i = 0; i < allUser.length; i++) {
                var user = allUser[i];
                var login = user.login;
                var operation = user.links[0].rel;
                var link = user.links[0].href;
                output += login + "&nbsp"
                    + "<input type='button' class='ban'"
                    + " name = " + link
                    + " id = " + login
                    + " value = " + operation + " />"
                    + " <br> "
            }
            output += "</form>";
            document.getElementById('allUsersId').innerHTML = output;

            var buttons = document.getElementsByClassName("ban");
            for (i = 0; i < buttons.length; i++) {
                buttons[i].addEventListener("click", function () {
                    executeOperation($(this).attr('id'), $(this).attr('name'), url);
                });
            }
        }
    });
}

function executeOperation(login, link, url) {
    $.ajax({
        type: "POST",
        url: link,
        contentType: "application/json",
        data: JSON.stringify({
            "login": login
        }),
        success: function () {
            getUsers(url);
        }
    });
}

