<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../header.jsp" %>
<link rel="stylesheet" href="../../../../resources/bootstrap.css" />


<div class="col-12">
    <h2>Users: ${users}</h2>
    <h2>Group details: ${groupForUser}</h2>
    <div class="card">
        <div class="card-body, text-black-50">
            <form:form method="post" modelAttribute="groupForUser">
                User:<form:select path="users" items="${users}" itemLabel="name" itemValue="id"/>
                <form:hidden path="name"/>
                <form:hidden path="dayOfWeek"/>
                <form:hidden path="localTime"/>
                <form:hidden path="size"/>
                <input type="submit" value="Add Group">
            </form:form>
        </div>
    </div>
</div>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<script src="../../../../resources/bootstrap.js"></script>

<%@ include file="../../footer.jsp" %>


