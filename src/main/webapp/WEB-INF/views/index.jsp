<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
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
<div class="container-lg my-5">
    <div class="row">


        <div class="col-2">
            <p><sec:authorize access="isAnonymous()"><a href="/login">Login</a></sec:authorize></p>
            <p><sec:authorize access="isAnonymous()"><a href="/registry">Registry</a></sec:authorize></p>
        </div>
        <div class="col-10">
            <h2 class="text-dark">WELCOME
            <sec:authorize access="isAuthenticated()">
                <p> <sec:authentication property="principal.username"/></p>
                <p>Uprawnienia: <sec:authentication property="authorities"/></p>
            </sec:authorize>

            </h2>
            <h4 class="text-info">
                <sec:authorize url="/admin/infoadmin">
                    DOSTĘP DO EDYCJI
                </sec:authorize>
            </h4>
        </div>

    </div>
    <div class="row">
        <div class="col">
            <sec:authorize access="isAuthenticated()">
                <form action="<c:url value="/logout"/>" method="post">
                    <input type="submit" value="Wyloguj">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </sec:authorize>

        </div>
        <div class="col">
<%--            <sec:authorize access="isAuthenticated()">--%>
                <a href="/user/start">More</a></div>
<%--            </sec:authorize>--%>
        </div>

    </div>

</div>

<h2 class="text-center t">other</h2>
<div class="container-lg">

        <div class="col">1</div>
        <div class="col">

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
    <div class="row">
        </div>
        <div class="col">3</div>
    </div>

    <!--na sztywno:-->

</div>

<h2 class="text-center t my-5">other - Gutter</h2>
<!--odstępy między kolumnami do ustawiania x i y-->
<div class="container ">
    <div class="row g-5">
        <div class="col-6">1</div>
        <div class="col-6">2</div>
        <div class="col-6">3</div>
        <div class="col-6">4</div>
    </div>
</div>



<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="../../../resources/bootstrap.js"></script>
</body>
</html>