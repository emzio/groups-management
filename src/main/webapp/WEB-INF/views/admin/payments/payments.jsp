

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../header.jsp" %>

<link rel="stylesheet" href="../../../../resources/bootstrap.css" />

<div class="col-12">
    <a href="/admin/users" class="btn btn-success">Users menu</a>
</div>

<div class="col-12">
    <div class="card">
        <div class="card-body, text-black-50">
            <hr class="my-3">
            <a href="/admin/addPayment/${userId}" class="btn btn-danger"> Add payment </a>
            <hr class="my-3">
            <table class="table">
                <thead>
                <tr>
                    <td>
                        Date of Payment
                    <td>
                        Payment Code
                    </td>
                    <td>
                        Amount
                    </td>
                    <td>
                        Actions
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${paymentsForUser}" var="singlePayment">
                    <tr>
                        <td>
                                ${singlePayment.getDateOfPayment()}
                        </td>
                        <td>
                                ${singlePayment.getPaymentCode()}
                        </td>
                        <td>
                                ${singlePayment.getAmount()}
                        </td>
                        <td>
                            <a href="/admin/deletePayment/${userId}/${singlePayment.getId()}"> Delete payment </a>
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
            <h2>NumberOfClasses: ${numberOfClasses}</h2>
            <h2>PaymentAmount: ${paymentAmount}</h2>
            <hr class="my-3">
            <a href="/admin/user/month/${userId}" class="btn btn-primary"> Another month </a>
            <hr class="my-3">
            <p>Month: ${month}</p>
            <p>Year: ${year}</p>
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
                                    ${cell.getDate().getDayOfMonth()}
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
</div>

<script src="../../../../resources/bootstrap.js"></script>
<script src="https://unpkg.com/@popperjs/core@2.4.0/dist/umd/popper.min.js"></script>
<%@ include file="../../footer.jsp" %>


