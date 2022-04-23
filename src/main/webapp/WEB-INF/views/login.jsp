<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="header.jsp" %>


<%--<div class="col-12">--%>
<p>
    Lorem ipsum dolor sit amet, consectetur adipisicing elit.
    Voluptatum unde incidunt nobis doloremque exercitationem nihil
    accusantium voluptatibus? Doloribus optio doloremque nihil
    veritatis, magni, blanditiis modi dicta temporibus voluptates
    suscipit ab.
</p>

    <div class="card">
        <div class="card-body, text-black-50">


            <form method="post">
                <label for="name" class="form-label"> User Name : </label>
                <input class="form-control" id="name" type="text" name="username"/>
                <label for="password" class="form-label"> Password: </label>
                <input class="form-control" id="password" type="password" name="password"/>
                <div><input type="submit" class="btn btn-primary" value="Sign In"/></div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <hr class="my-3">
            <a href="/registry"> Register </a>
        </div>
    </div>
<%--</div>--%>

<%@ include file="footer.jsp" %>
