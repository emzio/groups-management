<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../header.jsp" %>
<link rel="stylesheet" href="../../../../resources/bootstrap.css" />


<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">
            <table class="table">
                <thead>
                <tr>
                    <td>
                        Date
                    </td>
                    <td>
                        Actions
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${allCanceled}" var="single">
                    <tr>
                        <td>
                                ${single.getLocalDate()}
                        </td>
                        <td>
                            <a href="/admin/canceled/delete/${single.getId()}"> Delete </a>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <hr class="my-3">
    <a href="/admin/canceled/add" class="btn btn-info"> Add new Date </a>
</div>


<div class="col-12">

    <a href="http://localhost:8080/" type="button" class="btn btn-success" >main</a>
    <a href="http://localhost:8080/" type="button" class="btn btn-info" onClick="history.go(-1)">back</a>

</div>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<script src="../../../../resources/bootstrap.js"></script>

<%@ include file="../../footer.jsp" %>


