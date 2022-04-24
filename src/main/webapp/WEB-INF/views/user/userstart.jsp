

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../header.jsp" %>

<link rel="stylesheet" href="../../../resources/bootstrap.css" />
<div class="col-12">
    <a href="/user/start" class="btn btn-success">More</a>
</div>

<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">
            <h2>Your Groups</h2>

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
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${groupsForUser}" var="singleGroup">
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
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">
            <h2>Your Classes</h2>

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
                <c:forEach items="${weeksForUser}" var="week">
                    <tr>
                        <c:forEach items="${week}" var="cell">
                            <td>
                                    ${cell.getDate()}
                                    ${cell.getDay()}
                                    ${cell.getDescription()}
                            </td>
                        </c:forEach>
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


