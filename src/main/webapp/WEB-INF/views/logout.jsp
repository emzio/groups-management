<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="header.jsp" %>


<%--<div class="col-12">--%>


<div class="card">
    <div class="card-body, text-black-50">
        <p>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit.
            Voluptatum unde incidunt nobis doloremque exercitationem nihil
            accusantium voluptatibus? Doloribus optio doloremque nihil
            veritatis, magni, blanditiis modi dicta temporibus voluptates
            suscipit ab.
        </p>
    </div>
</div>
<form action="<c:url value="/logout"/>" method="post" class="form-control">
    <input class="fa fa-id-badge" type="submit" value="Wyloguj" class="btn btn-primary">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<%--</div>--%>

<%@ include file="footer.jsp" %>
