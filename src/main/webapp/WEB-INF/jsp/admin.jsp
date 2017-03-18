<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin page </title>
    <jsp:include page="styles.jsp"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="/static/app/js/admin.js"></script>
</head>
<body>
<div class="page-header">
    <h1 style="text-align: center">Banned users</h1>
<div class="container" id="allUsersId">
    <script>
        getUsers("${sessionScope.linkToAdminPage}");
    </script>
</div>
</body>
</html>
