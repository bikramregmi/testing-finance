<%@ page contentType="text/html; charset=utf-8" language="java"
         import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">
</style>
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="Mini Statement Request List">
    <div class="col-md-12">
    <div class="break"></div>
    <c:if test="${not empty message}">
        <p class="bg-success">
        <c:out value="${message}"></c:out>
        </p>
    </c:if>
    <table id="StatementRequestList" class="table table-striped">
    <thead>
    <tr>
    <th>Customer Name</th>
    <th>Mobile Number</th>
    <th>Email</th>
    <th>StartDate</th>
    <th>EndDate</th>
    <th>AccountNumber</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${fn:length(statementRequestList) gt 0}">
        <c:forEach var="statementRequest" items="${statementRequestList}">
            <tr>
            <td>${statementRequest.customerName}</td>
            <td>${statementRequest.mobileNumber}</td>
            <td>${statementRequest.email}</td>
            <td>${statementRequest.startDate}</td>
            <td>${statementRequest.endDate}</td>
            <td>${statementRequest.accountNumber}</td>
            <td>
        </c:forEach>
    </c:if>
    </tbody>
    </table>
</spr:page>
