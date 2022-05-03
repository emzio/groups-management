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
                <label for="select" class="form-label" >Month:</label>
<%--                <input type="number"  name="month">--%>
                    <select id="select" name="month">
                        <c:forEach items="${months}" var="singleMonth">
                            <option>${singleMonth}</option>
                        </c:forEach>
                    </select>


                <label for="year" class="form-label">Year:</label>
                <input id="year" type="number" step="1" value="${actualYear}" name="year">

                <input type="hidden" name="id" value="${id}">

                <input type="submit" value="Select" class="btn btn-primary">
            </form>
            Months: ${months}
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


