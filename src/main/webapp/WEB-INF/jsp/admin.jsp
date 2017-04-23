<html>
<head>
    <title>Admin</title>
    <base href="/">
    <jsp:include page="styles.jsp"/>
</head>
<body>
<div class="page-header">
    <h1 style="text-align: center">Banned users</h1>
</div>
<div class="container" id="allUsersId">
    <script>
        getUsers("${sessionScope.linkToAdminPage}");
    </script>
</div>
<my-app>Loading</my-app>
<script type="text/javascript" src="/static/app/js/polyfills.js"></script>
<script type="text/javascript" src="/static/app/js/vendor.js"></script>
<script type="text/javascript" src="/static/app/js/app1.js"></script>
</body>
</html>