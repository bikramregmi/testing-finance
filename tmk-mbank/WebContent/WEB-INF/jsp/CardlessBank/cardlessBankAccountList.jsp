<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spr:page header1="List Bank">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<a href="${pageContext.request.contextPath}/cardless/account/add?bank=${bank.swiftCode}" class="btn btn-primary addCardlessAccount">Add Cardless Bank Account</a>
		<table id="bankList" class="table table-striped table-bordered tablewrapper">
			<thead>
				<tr>
					<th>Cardless Bank</th>
					<th>Cardless Bank Account</th>
					<th>Debit Their Ref</th>
					<th>Status</th>
					<th colspan="2">Action</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="account" items="${cardlessBankAccountList}">
				<tr>
					<td>${account.cardlessBank}</td>
					<td>${account.accountNumber}</td>
					<td>${account.debitTheirRef}</td>
					<td>${account.status}</td>
					<td><a href="/cardless/account/edit?cardlessbank=${account.id}"><i class="fa fa-pencil" data-toggle="tooltiip" data-placement="top" title="Edit Account"></i></a></td>
					<td><a href="${pageContext.request.contextPath}/cardless/credit?bank=${bank.swiftCode}&cardless=${account.cardlessBankId}" class="btn btn-primary btn-xs">Add
							Credit</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</spr:page>
<style>
.addCardlessAccount,.addCardlessAccount:hover,.addCardlessAccount:active,.addCardlessAccount:visited,.addCardlessAccount:focus,.addCardlessAccount:active:hover {
	float: right;
	background-color: #18964f;
	border: none;
	border-radius: 0px;
}

table {
	font-size: 12px;
}

th {
	background-color: #18964f;
	font-size: 15px;
	color: white;
}

td {
	word-wrap: break-word;
}

.btn-xs {
	font-size: 11px;
}
</style>