var app = angular.module('web-chat', []);
app.controller('web-chat-controller', function ($scope) {
    $scope.applyUsers = function (users) {
        $scope.users = users;
    };
    $scope.selectUser = function (user) {
        document.getElementById('inputMessage').value = user + ':';
    }
});

var socket = new SockJS(sockPath);
var flag = null;
socket.onopen = function () {
    registerUser();
    flag = window.setInterval(sendList, 2000);
};
socket.onclose = function (event) {
    window.clearInterval(flag);
    if (event.wasClean) {
        console.log('close clean');
    }
    else {
        console.log(event);
        console.log('close error');
    }
};
socket.onerror = function (event) {
    console.log(event);
};

socket.onmessage = function (event) {
    var message = JSON.parse(event.data);
    if (typeof message.list !== 'undefined') {
        var myScope = angular.element(document.getElementById('web-chat-id')).scope();
        myScope.$apply(function () {
            myScope.applyUsers(message.list);
        });
    }
    if (typeof message.auth !== 'undefined' && message.auth === 'no') {
        console.log('authorization failed');
    }
    if (typeof message.auth !== 'undefined' && message.auth === 'yes') {
        console.log('authorization success');
    }
    if (typeof message.name !== 'undefined') {
        document.getElementById('output-message').value = message.name + ' : ' + message.message;
    }
};
function sendList() {
    socket.send(JSON.stringify({"list":""}));
}
function send() {
    var message = document.getElementById('inputMessage').value;
    var arrayMessage = message.split(':');
    var data = {};
    data[arrayMessage[0]] = arrayMessage[1];
    alert(data);
    socket.send(JSON.stringify(data));
}

function registerUser() {
    var sessionId = getCookie('JSESSIONID');
    socket.send(JSON.stringify({sessionId: sessionId}));
}

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}