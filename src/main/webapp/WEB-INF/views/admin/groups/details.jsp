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


<!--my-5 - marginesy-->
<div class="container-lg my-5">
    <div class="row">
        <div class="col-2"><h2 class="text-dark">Add Group</h2>
        </div>
        <div class="col-10">

            <table class="table">
                <thead>
                <tr>
                    <td>
                        Name
                    </td>
                    <td>
                        Day
                    </td>
                    <td>
                        Hour
                    </td>
                    <td>
                        Size
                    </td>
                    <td>
                        Actions
                    </td>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                                ${group.getName()}
                        </td>
                        <td>
                                ${group.getDayOfWeek()}
                        </td>
                        <td>
                                ${group.getLocalTime()}
                        </td>
                        <td>
                            ${group.getSize()}
                        </td>

                        <td>
                            <a href="/admin/groups/addUser/${group.getId()}" > add User </a>
                            <a href="/admin/groups/update/${group.getId()}"> update </a>
                            <a href="/admin/groups/delete/${group.getId()}" > delete </a>
                        </td>
                    </tr>
                </tbody>

            </table>



            <table class="table">
                <thead>
                <tr>
                    <td>
                        Name
                    </td>
                    <td>
                        Lastname
                    </td>
                    <td>
                        Email
                    </td>
                    <td>
                        Groups
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${group.getUsers()}" var="singleUser">
                    <tr>
                        <td>
                                ${singleUser.getName()}
                        </td>
                        <td>
                                ${singleUser.getLastName()}
                        </td>
                        <td>
                                ${singleUser.getEmail()}
                        </td>
                        <td>
                                ${singleUser.getGroups()}
                        </td>

                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
    </div>
<%--    </div>--%>

    <div class="row">
        <div class="col-12">
            <div class="link-info">
                <a href="/admin/groups/month/${group.getId()}"> Another month </a>
            </div>

            <table class="table-bordered">
                <thead>
                <tr>
                    <c:forEach items="${daysOfWeek}" var="day">
                        <td>
                                ${day}
                        </td>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${weeks}" var="week">
                    <tr>
                        <c:forEach items="${week}" var="cell">
                            <td>
                                    ${cell.getDate()}
                                    ${cell.getDay()}
                                    ${cell.getDescription()}
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="../../resources/bootstrap.js"></script>
</body>
</html>

