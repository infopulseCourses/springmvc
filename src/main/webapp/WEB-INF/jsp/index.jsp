<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
<head>
    <title>Welcome</title>
    <jsp:include page="styles.jsp"/>
</head>
<body>
<div class="container">
    <div class="col-md-3 col-md-offset-4">
        <a class="btn btn-primary btn-lg btn-block" href="${s:mvcUrl('getLogin').build()}">Login</a>
        <a class="btn btn-default btn-lg btn-block" href="${s:mvcUrl('getRegistration').build()}">Registration</a>
    </div>
</div>
<jsp:include page="scripts.jsp"/>
</body>
</html>
