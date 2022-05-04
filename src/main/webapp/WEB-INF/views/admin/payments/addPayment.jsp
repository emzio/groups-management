

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../header.jsp" %>

<link rel="stylesheet" href="../../../../resources/bootstrap.css" />

<div class="col-12">
    <a href="/admin/payments/${userId}" class="btn btn-success">User payments</a>
    <hr class="my-3">
    <a type="button" class="btn btn-secondary" onClick="history.go(-1)">back</a>
</div>

<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">

            <form:form method="post" modelAttribute="payment">
                <div class="flex-container">
                    <div class="form-label">Payment Code: </div>
                    <form:input path="paymentCode" cssClass="form-control" pattern="^([0-9]{4}-)([0][1-9]|[1][0-2])$"/><form:errors path="paymentCode"/>

                    <div class="form-label">Amount:</div>
                    <form:input type="number" step=".01" path="amount" cssClass="form-control"/><form:errors path="amount"/>

                    <div class="form-label">Date of payment:</div>
                    <form:input type="date" path="dateOfPayment" cssClass="form-control"/><form:errors path="dateOfPayment"/>
                </div>
                <input type="submit" value="Add Payment" class="btn btn-primary">
                <input type="hidden" name="userId" value="${userId}">
            </form:form>

        </div>
    </div>
</div>

<script src="../../../../resources/bootstrap.js"></script>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<%@ include file="../../footer.jsp" %>


