<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <meta charset="UTF-8">
    <title>User Home</title>
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


<!--my-5 - marginesy-->
<div class="container-sm my-5">
    <div class="row">
        <div class="col-2">
            <sec:authorize access="isAuthenticated()">
                <p> <sec:authentication property="principal.username"/></p>
                <p>Uprawnienia: <sec:authentication property="authorities"/></p>
            </sec:authorize>
            <a href="/logout">LogOut</a>
        </div>
        <div class="col-10">
            <div class="row">
                <h2 class="text-dark">WELCOME</h2>
            </div>
            <div class="row">
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

                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </div>
        </div>

    </div>
    <div class="row">
        <div class="col">
            1
        </div>
        <div class="col">
            2
        </div>
        <div class="col">3</div>
    </div>
    <div class="row">
        <div class="col">
            1
        </div>
        <div class="col">
            2
        </div>
    </div>
</div>

<h2 class="text-center t">Canceled Classes</h2>
<div class="container-lg">
    <div class="row">
        <div class="col">1</div>
        <div class="col">2</div>
        <div class="col">3</div>
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