<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <meta charset="UTF-8">
    <title>Admin Home</title>
    <style>
        [class*="col"] {
            padding: 1rem;
            background-color: mistyrose;
            border: 2px solid #fff;
            color: #fff;
            text-align: center;
        }
    </style>

    <link rel="stylesheet" href="../../../resources/bootstrap.css">
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

<h2 class="text-center t my-5">other - Gutter</h2>
<!--odstępy między kolumnami do ustawiania x i y-->
<div class="container ">
    <div class="row g-5">
        <div class="col-6">

<%--       GRUPY:--%>

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
                        Details
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${groups}" var="singleGroup">
                    <tr>
                        <td>
                                ${singleGroup.getName()}
                        </td>
                        <td>
                                ${singleGroup.getDayOfWeek()}
                        </td>
                        <td>
                                ${singleGroup.getLocalTime()}
                        </td>
                        <td>
                                <a href="/admin/groups/${singleGroup.getId()}"> More </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>

<%--        Groups with Free Places--%>

        <div class="col-6">
            <h2> Still have free places: </h2>
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
                        Details
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${freeGroups}" var="singleGroup">
                    <tr>
                        <td>
                                ${singleGroup.getName()}
                        </td>
                        <td>
                                ${singleGroup.getDayOfWeek()}
                        </td>
                        <td>
                                ${singleGroup.getLocalTime()}
                        </td>
                        <td>
                            <a href="/admin/groups/${singleGroup.getId()}"> More </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>

<%--        Link do Canceled i Users --%>
        <div class="col-6">
                <a href="/admin/canceled/">Canceled Classes Menu</a>
            <a href="/admin/users">Users Menu</a>
        <div class="col-6">4</div>
    </div>
</div>
<!--my-5 - marginesy-->
<div class="container-lg my-5">
    <div class="row">
        <div class="col-2"><a href="/logout">LogOut</a></div>
        <div class="col-10">

            <%--        USERS--%>

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
                <c:forEach items="${users}" var="singleUser">
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
    <div class="row">
        <div class="col">
            <a href="/admin/users/">Users</a>
        </div>
        <div class="col">
            <a href="/create-user/addedLogin">add User</a>
        </div>
        <div class="col">3</div>
    </div>
    <div class="row">
        <div class="col">
            <a href="/admin/groups">Groups</a>
        </div>
        <div class="col">
            <a href="/admin/groups/add/">add Group</a>
        </div>
    </div>
</div>

<h2 class="text-center t">Canceled Classes</h2>
<div class="container-lg">
    <div class="row">
        <div class="col"><a href="/admin/canceled/add/">Add Canceled Class</a></div>
        <div class="col"><a href="/admin/canceled/">All Canceled Class</a></div>
        <div class="col"><a href="/admin/groups/fcd/1">Canceled Class for group 1</a></div>
    </div>
    <div class="row">
        <div class="col-md-8">1</div>
        <div class="col-md-4">2</div>
    </div>
    <!--na sztywno:-->
    <div class="row">
        <div class="col-8">1</div>
        <div class="col-4">2</div>
    </div>
</div>





<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="../../../resources/bootstrap.js"></script>
</body>
</html>