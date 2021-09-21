<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spr:page header1="List Cardless Bank">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<table id="bankList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Bank Name</th>
					<th>Host</th>
					<th>Port</th>
					<th>User Sign</th>
					<th>User Password</th>
					<th colspan="2">Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(cardlessBankList) gt 0}">
					<c:forEach var="cardlessBank" items="${cardlessBankList}">
						<tr>
							<td>${cardlessBank.bank}</td>
							<td>${cardlessBank.host}</td>
							<td>${cardlessBank.port}</td>
							<td>${cardlessBank.userSign}</td>
							<td>${cardlessBank.userPassword}</td>
							<td><a href="/cardlessbank/edit?cardlessbank=${cardlessBank.id}"><i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Cardless Bank"></i></a></td>
							<td><a href="/cardlessbank/fee?cardlessbank=${cardlessBank.id}"><i class="fa fa-plus" data-toggle="tooltiip" data-placement="top" title="Add Fee"></i></a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</spr:page>

<style>
table {
	font-size: 12px;
}

td {
	word-wrap: break-word;
}
</style>