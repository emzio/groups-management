<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<head>
    <meta charset="UTF-8">
    <title>Add Group</title>
    <style>
        [class*="col"] {
            padding: 1rem;
            background-color: mistyrose;
            border: 2px solid #fff;
            color: #fff;
            text-align: center;
        }
    </style>

    <link rel="stylesheet" href="../../resources/bootstrap.css">
</head>
<body>
<div class="container-lg  ">
    <div class="row">
        <!--        <div class="row g-5">-->
        <div class="m-5">
            <h2 class="text-center m-6">management in the organization of group activities</h2>
        </div>
    </div>
</div>

<h2>Users: ${users}</h2>
<h2>Group details: ${groupForUser}</h2>
<form:form method="post" modelAttribute="groupForUser">
    User:<form:select path="users" items="${users}" itemLabel="name" itemValue="id"/>
    <form:hidden path="name"/>
    <form:hidden path="dayOfWeek"/>
    <form:hidden path="localTime"/>
    <form:hidden path="size"/>
    <input type="submit" value="Add Group">
</form:form>



<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="../../resources/bootstrap.js"></script>
</body>
</html>

