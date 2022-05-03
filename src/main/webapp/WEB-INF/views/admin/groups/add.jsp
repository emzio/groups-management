

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
<%--                    <form:hidden path="localTime" />--%>

                    <div class="form-label">Size: </div>
                    <form:input path="size" cssClass="form-control"/><form:errors path="size"/>
                </div>


<%--                <label for="hour" class="form-label">hour</label>--%>
<%--                <select id="hour" name="hour">--%>
<%--                    <c:forEach items="${hours}" var="singleHour">--%>
<%--                        <option>${singleHour}</option>--%>
<%--                    </c:forEach>--%>
<%--                </select>--%>
<%--                <label for="minutes" class="form-label">minutes</label>--%>
<%--                <select id="minutes" name="minute">--%>
<%--                    <c:forEach items="${minutes}" var="singleMinute">--%>
<%--                        <option>${singleMinute}</option>--%>
<%--                    </c:forEach>--%>
<%--                </select>--%>
                <label for="time" class="form-label">time:</label>
                <input type="time" id="time" name="time">


                <input type="submit" value="Add Group" class="btn btn-primary">
            </form:form>


            Hours: ${hours}
            <br>
            Minutes: ${minutes}
<%--            <form:form method="post" modelAttribute="userToUpdate">--%>
<%--                <div class="flex-container">--%>
<%--                    <div class="form-label"> Login:</div>--%>
<%--                    <form:input path="username" cssClass="form-control"/>--%>
<%--                    <div class="form-label">Password:  </div>--%>
<%--                    <form:input path="password" cssClass="form-control"/>--%>
<%--                    <div class="form-label">Name:  </div>--%>
<%--                    <form:input path="name" cssClass="form-control"/>--%>
<%--                    <div class="form-label">Lastname: </div>--%>
<%--                    <form:input path="lastName" cssClass="form-control"/>--%>
<%--                    <div class="form-label">Email:  </div>--%>
<%--                    <form:input path="email" cssClass="form-control"/>--%>
<%--                    <div class="form-label">Groups:  </div>--%>
<%--                    <form:select path="groups" items="${groups}" itemLabel="name" itemValue="id" cssClass="form-select"/>--%>
<%--                </div>--%>
<%--                <input type="submit" value="Confirm update" class="btn btn-primary">--%>
<%--            </form:form>--%>

        </div>
    </div>
</div>

<script src="../../../../resources/bootstrap.js"></script>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<%@ include file="../../footer.jsp" %>


