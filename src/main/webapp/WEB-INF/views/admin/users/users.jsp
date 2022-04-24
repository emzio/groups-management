

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../header.jsp" %>

<link rel="stylesheet" href="../../../../resources/bootstrap.css" />

<div class="col-12">
    <a href="/user/start" class="btn btn-success">Groups menu</a>
    <a href="/registry" class="btn btn-secondary"> Create new user</a>
    <a href="/admin/users/addadmin" class="btn btn-danger"> Create new admin </a>
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
                    <td>
                        Groups
                    </td>
                    <td>
                        Actions
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="singleUser">
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
                        <td>
                                ${singleUser.getGroups()}
                        </td>
                        <td>
                            <a href="/admin/users/update/${singleUser.getId()}"> Update </a>
                            <a href="/admin/users/delete/${singleUser.getId()}"> Delete </a>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>

            </table>

        </div>
    </div>
</div>

<script src="../../../../resources/bootstrap.js"></script>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<%@ include file="../../footer.jsp" %>


