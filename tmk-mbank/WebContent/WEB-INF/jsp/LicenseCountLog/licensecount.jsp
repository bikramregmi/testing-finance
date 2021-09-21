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
<spr:page header1="License Count Log">
    <div class="col-md-12">
    <div class="break"></div>
    <c:if test="${not empty message}">
        <p class="bg-success">
        <c:out value="${message}"></c:out>
        </p>
    </c:if>
    <table id="licenseCountLogList" class="table table-striped">
    <thead>
    <tr>
    <th>Bank Name</th>
    <th>Added By</th>
    <th>Count</th>
    <th>Remarks</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${fn:length(licenseCountLogList) gt 0}">
        <c:forEach var="statementRequest" items="${licenseCountLogList}">
            <tr>
            <td>${statementRequest.name}</td>
            <td>${statementRequest.userName}</td>
            <td>${statementRequest.licenseCount}</td>
            <td>${statementRequest.remarks}</td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
    </table>
</spr:page>
