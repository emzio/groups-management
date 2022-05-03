<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../header.jsp" %>
<link rel="stylesheet" href="../../../../resources/bootstrap.css" />


<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">
            <h3 class="text-danger"><c:if test="${oversize}">
                To many users for group size: ${groupModel.getSize()}
            </c:if></h3>

            <form:form method="post" modelAttribute="groupModel">
                <div class="flex-container">
                    <div class="form-label">Name:</div>
                    <form:input path="name"/><form:errors path="name"/>
                    <div class="form-label">Day:</div>
                    <form:radiobuttons path="dayOfWeek" items="${daysOfWeek}"></form:radiobuttons>
                    <div class="form-label">Time: </div>
                    <form:input type="time" path="localTime"/><form:errors path="localTime"/>
                    <div class="form-label">Size: </div>
                    <form:input path="size"/><form:errors path="size"/>
                    User:
                    <form:select path="users" multiple="true">
                    <c:forEach items="${users}" var="u">
                        <c:choose>
                            <c:when test="${groupModel.userListId.contains(u.id)}">
                                <form:option value="${u.id}" label="${u.name}" selected="true" />
                            </c:when>
                            <c:otherwise>
                                <form:option value="${u.id}" label="${u.name}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
                </div>
                <input type="submit" value="Update Group" class="btn btn-primary">
            </form:form>
        </div>
    </div>
</div>
<div class="col-12">

    <a href="http://localhost:8080/" type="button" class="btn btn-success" >main</a>
    <a href="http://localhost:8080/" type="button" class="btn btn-info" onClick="history.go(-1)">back</a>

</div>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<script src="../../../../resources/bootstrap.js"></script>

<%@ include file="../../footer.jsp" %>


