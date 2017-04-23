<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin page </title>
    <jsp:include page="styles.jsp"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="/static/app/js/admin.js"></script>
</head>
<body>
<h2>Admin</h2>
<div class="container" id="allUsersId">
    <script>
        getUsers("${sessionScope.linkToAdminPage}");
    </script>
</div>
</body>
</html>
