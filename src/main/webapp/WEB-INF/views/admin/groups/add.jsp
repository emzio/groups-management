

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../../header.jsp" %>

<link rel="stylesheet" href="../../../../resources/bootstrap.css" />

<div class="col-12">
    <a href="/user/start" class="btn btn-success">Groups menu</a>
    <hr class="my-3">
    <a type="button" class="btn btn-secondary" onClick="history.go(-1)">back</a>
</div>

<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">

            <form:form method="post" modelAttribute="groupModel">
                <div class="flex-container">
                    <div class="form-label">Name: </div>
                    <form:input path="name" cssClass="form-control"/><form:errors path="name"/>

                    <div class="form-label">Day:</div>
                    <form:radiobuttons path="dayOfWeek" items="${daysOfWeek}"></form:radiobuttons>

                    <div class="form-label">Time: </div>
                    <form:input type="time" path="localTime" cssClass="form-control"/><form:errors path="localTime"/>

                    <div class="form-label">Size: </div>
                    <form:input path="size" cssClass="form-control"/><form:errors path="size"/>
                </div>
                <input type="submit" value="Add Group" class="btn btn-primary">
            </form:form>

        </div>
    </div>
</div>

<script src="../../../../resources/bootstrap.js"></script>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<%@ include file="../../footer.jsp" %>


