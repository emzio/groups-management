<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../header.jsp" %>
<link rel="stylesheet" href="../../../../resources/bootstrap.css" />


<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">
<%--            <form method="post" action="/admin/groups/monthtest" >--%>
                <form method="post">
                <div class="form-label">Month:</div>
                <input type="number"  name="month">
                <div class="form-label">Year:</div>
                <input type="number" name="year">

                <input type="hidden" name="id" value="${id}">

                <input type="submit" value="Select" class="btn btn-primary">
            </form>
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


