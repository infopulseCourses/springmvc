<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
<a href="${s:mvcUrl('getLogin').build()}">Login</a>
<br/>
<a href="${s:mvcUrl('getRegistration').build()}">Registration</a>
${errorMessage}
</body>
</html>
