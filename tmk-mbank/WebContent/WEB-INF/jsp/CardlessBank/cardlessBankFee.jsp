<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">
<spr:page header1="Cardless Bank Fee">
	<div class="col-md-12">
		<div class="break"></div>
		<button id="addFeeButton" class="btn btn-primary pull-right" ng-if="!sameforall" style="margin-top: 65px; background-color: #18964f; border-color: #18964f;">Add
			Fee</button>
		<form method="post" action="/cardlessbank/fee/save">
			<div class="row">
				<input type="hidden" name="cardlessBankId" value="${cardlessBank.id}">
				<div class="col-lg-4">
					<ul class="list-group">
						<li class="list-group-item"><b>Cardless Bank: </b> ${cardlessBank.bank}</li>
						<li class="list-group-item"><b>Same for all: </b> <input type="checkbox" id="sameforall" name="sameforall" style="margin-left: 18px;" ng-model="sameforall"></li>
						<li class="list-group-item" ng-if="sameforall">
							<div class="row">
								<div class="col-lg-2">
									<b>Fee : </b>
								</div>
								<div class="col-lg-10">
									<input type="text" id="fee" name="fee" class="form-control">
									<h6 style="color: red;">${error.fee}</h6>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-lg-12">
					<table id="bankList" class="table table-striped table-bordered table-condensed" ng-if="!sameforall">
						<thead>
							<tr>
								<th>From Amount</th>
								<th>To Amount</th>
								<th>Fee</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:choose>
								<c:when test="${feeList ne null and feeList[0].sameForAll eq false}">
									<c:forEach var="fee" items="${feeList}" varStatus="loop" begin="0">
										<tr>
											<td><input type="text" class="form-control" name="feeList[${loop.index}].fromAmount" value="${fee.fromAmount}"></td>
											<td><input type="text" class="form-control" name="feeList[${loop.index}].toAmount" value="${fee.toAmount}"></td>
											<td><input type="text" class="form-control" name="feeList[${loop.index}].fee" value="${fee.fee}"></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td><input type="text" class="form-control" name="feeList[0].fromAmount"></td>
										<td><input type="text" class="form-control" name="feeList[0].toAmount"></td>
										<td><input type="text" class="form-control" name="feeList[0].fee"></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<h6 class="pull-right" style="color: red; margin-top: -10px;">${error.tableData}</h6>
					<button class="btn btn-primary">Save</button>
				</div>
			</div>
		</form>
	</div>
</spr:page>
<c:if test="${feeList[0].sameForAll}">
	<p ng-init="sameforall=true">
		<script>
			$('#sameforall').prop('checked', true);
			$('#fee').val('${feeList[0].fee}')
		</script>
</c:if>
<style>
td .form-control {
	border: 0px;
	box-shadow: none;
	text-align: center;
	height: 30px;
}

table {
	font-size: 12px;
}

td {
	word-wrap: break-word;
	background-color: #f9f9f9;
}
</style>
<script>
	$(document).ready(function(e) {
		var i = '${feeList}'.split(",").length;
		$('#addFeeButton').click(function(e) {
			var form = '<tr>'
					+ '<td><input type="text" class="form-control" name="feeList['+i+'].fromAmount"></td>'
					+ '<td><input type="text" class="form-control" name="feeList['+i+'].toAmount"></td>'
					+ '<td><input type="text" class="form-control" name="feeList['+i+'].fee"></td>'
					+ '</tr>';
			$("#tbody").append(form);
			i++;
		});
	})
</script>