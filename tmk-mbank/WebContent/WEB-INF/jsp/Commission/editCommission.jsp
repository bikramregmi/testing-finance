<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->

<spr:page header1="Edit Commission">
	<div class="col-md-12">
		<div class="row col-md-12">
			<div class="break"></div>
			<form action="/commission/edit" modelAttribute="commission"
				method="post"
				style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="id" value="${commission.id}">
				<div class="col-md-4">
					<div class="form-group" id="sameForAllDiv">
						<label>Same for all Amount Range</label> <input type="checkbox"
							name="sameForAll" id="sameForAll" value=${commission.sameForAll}>
					</div>
					<div id="addCommissionAmountDiv" class="form-group">
						<button type="button" class="btn btn-primary btn-block"
							id="addCommissionAmount" name="addCommissionAmount" value="add">Add
							Commission Amount</button>
					</div>
				</div>
				<div id="first" class="bulkCommissionAmount">
					<table id="commissionAmountList" class="table">
						<thead>
							<tr>
								<td>From Amount</td>
								<td>To Amount</td>
								<c:if test="${commission.commissionType eq 'COMMISSION'}">
									<td id="commissionFlatTd">Commission Flat</td>
									<td id="commissionPercentageTd">Commission Percentage</td>
								</c:if>
								<c:if test="${commission.commissionType eq 'FEE'}">
									<td id="feeFlatTd">Fee Amount Flat</td>
									<td id="feePercentageTd">Fee Amount Percentage</td>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<tr class="commission-row">
								<td class="commission-col"><input type="text"
									class="form-control input-sm"
									name="commissionAmounts[0].fromAmount" value="0"></td>
								<td class="commission-col"><input type="text"
									class="form-control input-sm"
									name="commissionAmounts[0].toAmount" value="0"></td>
								<td class="commission-col"><input type="text"
									class="form-control input-sm" name="commissionAmounts[0].flat"
									value="0"></td>
								<td class="commission-col"><input type="text"
									class="form-control input-sm"
									name="commissionAmounts[0].percentage" value="0"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="second" class="bulkCommissionAmount">
					<table id="commissionAmountListSecond" class="table">
						<thead>
							<tr></tr>
							<c:if test="${commission.commissionType eq 'COMMISSION'}">
								<tr id="commissionTable">
									<td>Commission Flat</td>
									<td>Commission Percentage</td>
								</tr>
							</c:if>
							<c:if test="${commission.commissionType eq 'FEE'}">
								<tr id="feeTable">
									<td>Fee Amount Flat</td>
									<td>Fee Amount Percentage</td>
								</tr>
							</c:if>
						</thead>
						<tbody id="feeCommissionTable">
							<tr>
								<td><input type="text" class='form-control input-sm'
									name="commissionAmounts[0].flat" value="0" /></td>
								<td><input type="text" class='form-control input-sm'
									name="commissionAmounts[0].percentage" value="0" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="col-md-12"></div>
				<div class="col-md-4">
					<div class="form-group">
						<button class="btn btn-primary btn-md btn-block btncu">Save
							Commission</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/commission.js"></script>
	<script>
		var commissionType = "${commission.commissionType}";
		var isSameForAll = "${commission.sameForAll}";
		var commissionAmountList = new Array();
		<c:forEach items="${commissionAmounts}" var="comAmt">
		var commissionAmt = new Object();
		if (isSameForAll==="true") {
			var commissionAmt = new Object();
			if (commissionType === "FEE") {
				commissionAmt.flat = '${comAmt.feeFlat}';
				commissionAmt.percentage = '${comAmt.feePercentage}';
			} else if (commissionType === "COMMISSION") {
				commissionAmt.flat = '${comAmt.commissionFlat}';
				commissionAmt.percentage = '${comAmt.commissionPercentage}';
			}
		} else {
			commissionAmt.fromAmount = '${comAmt.fromAmount}';
			commissionAmt.toAmount = '${comAmt.toAmount}';
			if (commissionType === "FEE") {
				commissionAmt.flat = '${comAmt.feeFlat}';
				commissionAmt.percentage = '${comAmt.feePercentage}';
			} else if (commissionType === "COMMISSION") {
				commissionAmt.flat = '${comAmt.commissionFlat}';
				commissionAmt.percentage = '${comAmt.commissionPercentage}';
			}
		}
		commissionAmountList.push(commissionAmt);
		</c:forEach>
		console.log(commissionAmountList);
	</script>
	<script>
	$(document).ready(function() {
		editInit("${pageContext.request.contextPath}");
	});
	</script>
</spr:page>
