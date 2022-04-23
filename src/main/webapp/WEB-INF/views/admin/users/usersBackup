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
        <div class="col-2">
            <h2 class="text-dark">Add Group</h2>
            <br>
            <a href="/registry"> Create new user</a>
            <br>
            <a href="/admin/users/addadmin"> Create new admin </a>
            <br>
            <a href="/user/start"> Back to main </a>

        </div>
        <div class="col-10">
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
                    <td>
                        Actions
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
                        <td>
                            <a href="/admin/users/update/${singleUser.getId()}"> Update </a>
                            <a href="/admin/users/delete/${singleUser.getId()}"> Delete </a>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
        </div>
    </div>
</div>
</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="../../resources/bootstrap.js"></script>
</body>
</html>

