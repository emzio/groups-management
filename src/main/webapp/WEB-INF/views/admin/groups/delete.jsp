<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../header.jsp" %>
<link rel="stylesheet" href="../../../../resources/bootstrap.css" />


<div class="col-12">

            <form:form method="post" modelAttribute="toDelete">
                <form:hidden path="id"/>
                <input type="submit" value="Confirm Delete" class="btn btn-danger">
            </form:form>

</div>
<div class="col-12">

    <a href="http://localhost:8080/" type="button" class="btn btn-success" >main</a>
    <a href="http://localhost:8080/" type="button" class="btn btn-info" onClick="history.go(-1)">back</a>

</div>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<script src="../../../../resources/bootstrap.js"></script>

<%@ include file="../../footer.jsp" %>


