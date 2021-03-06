

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../header.jsp" %>

<link rel="stylesheet" href="../../../resources/bootstrap.css" />
<div class="col-12">
    <a href="/admin/groups/add/" class="btn btn-dark">add Group</a>
    <a href="/admin/canceled/" class="btn btn-success">Canceled Classes Menu</a>
    <a href="/admin/users" class="btn btn-info">Users Menu</a>
    <a href="http://localhost:8080/" type="button" class="btn btn-secondary" onClick="history.go(-1)">back</a>
</div>


<div class="col-6">
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
                        Details
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
                        <td>
                            <a href="/admin/groups/${singleGroup.getId()}"> More </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
    </div>
</div>

<div class="col-6">
    <div class="card">
        <div class="card-body, text-black-50">

            <h2> Still have free places: </h2>
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
                        Details
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${freeGroups}" var="singleGroup">
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
                        <td>
                            <a href="/admin/groups/${singleGroup.getId()}"> More </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>

        </div>
    </div>
</div>

<script src="../../../resources/bootstrap.js"></script>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<%@ include file="../footer.jsp" %>


