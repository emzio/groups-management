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



<!--my-5 - marginesy-->
<div class="container-lg my-5">
    <div class="row">
        <div class="col-2"><h2 class="text-dark">Add Group</h2>
        </div>
        <div class="col-10">
            <form:form method="post" modelAttribute="groupModel">
                <div class="flex-container">

                    Name:<form:input path="name"/>
                        <%--<span>Rating: <form:input path="rating"/></span><form:errors path="rating"></form:errors>--%>
                        <%--                    <form:checkboxes items="${programmingSkills}" path="programmingSkills"></form:checkboxes>--%>
                    Day:
                    <form:radiobuttons path="dayOfWeek" items="${daysOfWeek}"></form:radiobuttons>
                    Time: <form:input path="localTime"/>
                    Size: <form:input path="size"/>
                    User:<form:select path="users" items="${users}" itemLabel="name" itemValue="id"/>
                </div>
                <input type="submit" value="Add Group">
            </form:form>
        </div>
    </div>
</div>
</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="../../resources/bootstrap.js"></script>
</body>
</html>
