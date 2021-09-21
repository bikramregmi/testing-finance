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
		<div class="row">
		<div class="row col-md-5">
			<div class="break"></div>
			<div class="form-group">
				<label>Merchant</label>
				${commission.merchant}
			</div>
			<div class="form-group">
				<label>Merchant Service</label>
				${commission.service}
			</div>
			<div class="form-group">
				<label>Status</label>
				${commission.status}
			</div>
			
			<div class="form-group">
				<label>Commission Details</label>
			</div>
			</div>
			</div>
			<div class="row">
			<div class="col-md-8">
			<div>
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<c:if test="${commission.sameForAll eq true}">
								<c:if test="${commission.commissionType eq 'COMMISSION'}">
								<th>Commission Flat</th>
								<th>Commission Percentage</th>
								</c:if>
								<c:if test="${commission.commissionType eq 'FEE'}">
								<th>Fee Flat</th>
								<th>Fee Percentage</th>
								</c:if>
							</c:if>
							<c:if test="${commission.sameForAll eq false}">
								<th>From Amount</th>
								<th>To Amount</th>
								<c:if test="${commission.commissionType eq 'COMMISSION'}">
								<th>Commission Flat</th>
								<th>Commission Percentage</th>
								</c:if>
								<c:if test="${commission.commissionType eq 'FEE'}">
								<th>Fee Flat</th>
								<th>Fee Percentage</th>
								</c:if>
							</c:if>
						</tr>
						</thead>
						<tbody>
						
							<c:if test="${commission.sameForAll eq true}">
								<c:if test="${fn:length(commissionAmounts) gt 0}">
									<c:forEach var="ca" items="${commissionAmounts}">
									<tr>
										<c:if test="${commission.commissionType eq 'COMMISSION'}">
										<td>${ca.commissionFlat}</td>
										<td>${ca.commissionPercentage}</td>
										</c:if>
										<c:if test="${commission.commissionType eq 'FEE'}">
										<td>${ca.feeFlat }</td>
										<td>${ca.feePercentage}</td>
										</c:if>
									</tr>
									</c:forEach>
								</c:if>
							</c:if>
							<c:if test="${commission.sameForAll eq false}">
								<c:if test="${fn:length(commissionAmounts) gt 0}">
									<c:forEach var="ca" items="${commissionAmounts}">
									<tr>
										<td>${ca.fromAmount}</td>
										<td>${ca.toAmount}</td>
										<c:if test="${commission.commissionType eq 'COMMISSION'}">
										<td>${ca.commissionFlat}</td>
										<td>${ca.commissionPercentage}</td>
										</c:if>
										<c:if test="${commission.commissionType eq 'FEE'}">
										<td>${ca.feeFlat}</td>
										<td>${ca.feePercentage	}</td>
										</c:if>
									</tr>
									</c:forEach>
								</c:if>
							</c:if>
					</tbody>
				</table>
			</div>
			</div>
		</div>
	</div>
</spr:page>
<style>
table{
 table-layout: fixed; 
 font-size: 12px;
}
td{
 word-wrap: break-word;
}
</style>