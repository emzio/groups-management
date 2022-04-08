<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div class="col-2"><h2 class="text-dark">WELCOME</h2>
        </div>
        <div class="col-10">
            <form method="post">
                <div><label> User Name : <input type="text" name="username"/> </label></div>
                <div><label> Password: <input type="password" name="password"/> </label></div>
                <div><input type="submit" value="Sign In"/></div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form></div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="../../resources/bootstrap.js"></script>
</body>
</html>
