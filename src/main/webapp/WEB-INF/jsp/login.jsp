<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
${requestScope.errorMessage}
<h2>Login</h2>
<form action="/login" method="POST">
    Login
    <input type="text" name="login"/>
    <br>
    Password
    <input type="password" name="password"/>
    <br>
    <input type="submit" value="Login"/>
</form>
</body>
</html>
