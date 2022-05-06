<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../header.jsp" %>
<link rel="stylesheet" href="../../../../resources/bootstrap.css" />

<div class="col-12">
    <a href="/user/start" class="btn btn-success">Groups menu</a>
</div>
<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">
            <h2>Classes</h2>
            <h2>NumberOfClasses: ${numberOfClasses}</h2>
            <h2>PaymentAmount: ${paymentAmount}</h2>
            <hr class="my-3">
            <p>Month: ${month}</p>
            <p>Year: ${year}</p>
            <hr class="my-3">
            <table class="table-bordered">
                <thead>
                <tr>
                    <c:forEach items="${daysOfWeek}" var="day">
                        <td>
                                ${day}
                        </td>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${weeks}" var="week">
                    <tr>
                        <c:forEach items="${week}" var="cell">
                            <td>
                                    ${cell.getDate()}
                                    ${cell.getAddToFee()}
                                    ${cell.getDescription()}
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <hr class="my-3">
    <a href="/admin/groups/month/${group.getId()}" class="btn btn-primary"> Another month </a>
</div>

<div class="col-12">


    <div class="card">
        <div class="card-body, text-black-50">
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
                    <td>
                        Size
                    </td>
                    <td>
                        Payment Rate
                    </td>
                    <td>
                        Actions
                    </td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        ${group.getName()}
                    </td>
                    <td>
                        ${group.getDayOfWeek()}
                    </td>
                    <td>
                        ${group.getLocalTime()}
                    </td>
                    <td>
                        ${group.getSize()}
                    </td>
                    <td>
                        ${group.getPaymentRate()}
                    </td>
                    <td>
                        <a href="/admin/groups/addUser/${group.getId()}" > add User </a>
                        <a href="/admin/groups/update/${group.getId()}"> update </a>
                        <a href="/admin/groups/delete/${group.getId()}" > delete </a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">
            <table class="table">
                <thead>
                <tr>
                    <td>
                        Name
                    </td>
                    <td>
                        Lastname
                    </td>
                    <td>
                        Email
                    </td>
<%--                    <td>--%>
<%--                        Groups--%>
<%--                    </td>--%>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${group.getUsers()}" var="singleUser">
                    <tr>
                        <td>
                                ${singleUser.getName()}
                        </td>
                        <td>
                                ${singleUser.getLastName()}
                        </td>
                        <td>
                                ${singleUser.getEmail()}
                        </td>
<%--                        <td>--%>
<%--                                ${singleUser.getGroups()}--%>
<%--                        </td>--%>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
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


