<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat</title>
    <jsp:include page="styles.jsp"/>
    <jsp:include page="scripts.jsp"/>
</head>

<body>
<h2>Chat</h2>
<div class="container">
    <form>
        <div ng-app="web-chat" ng-controller="web-chat-controller" id="web-chat-id">
            <div class="row">
                <div class="col-lg-4">
                    <ul class="list-group">
                        <li class="list-group-item" ng-repeat="user in users" ng-click="selectUser(user)">{{user}}</li>
                    </ul>
                </div>
                <div class="col-lg-8">
                    <br>
                    <label for="output-message">Chat</label>
                    <textarea class="form-control" id="output-message"></textarea>
                    <br>

                    <label for="inputMessage">Message</label>
                    <input type="text" id="inputMessage"/>
                    <input type="button" class="btn btn-default" onclick="send()" value="Send"/>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
