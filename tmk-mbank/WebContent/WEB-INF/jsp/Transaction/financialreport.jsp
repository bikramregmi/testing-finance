<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link href="/css/pagination.css" rel="stylesheet"></link>
<input id="firsrLogin" type="hidden" value="${firstLogin}" />
<spr:page header1="Financial Report">
	<div class="dropdown pull-right">
		<a class="btn btn-info" data-toggle="dropdown" ng-click="generateReportForTransaction()"><i class="fa fa-download small" title="View As PDF"></i></a>
		<ul class="dropdown-menu">
			<li><a href="" onclick="downloadReport('EXCEL','financialReport','reportTable')" ng-show="reportDataLoad"> <i class="fa fa-file-excel-o fa-2x"
					style="color: green;"></i> Excel
			</a></li>
			<li ng-hide="reportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
			<li class="divider new-divider"></li>
			<li><a href="" onclick="downloadReport('PDF','financialReport','reportTable')" ng-show="reportDataLoad"> <i class="fa fa-file-pdf-o fa-2x" style="color: red;"></i>
					PDF
			</a></li>
			<li ng-hide="reportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
			<li class="divider new-divider"></li>
			<li style="text-align: center; color: grey;">Reversal Report</li>
			<li class="divider new-divider"></li>
			<li><a href="" onclick="downloadReport('EXCEL','reversedReport','reversalReportTable')" ng-show="reversedReportDataLoad"> <i class="fa fa-file-excel-o fa-2x"
					style="color: green;"></i> Excel
			</a></li>
			<li ng-hide="reversedReportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
		</ul>
		<%@include file="/WEB-INF/jsp/reports/financialTransactionReport.jsp"%>
	</div>
	<div class="row tile_count">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
						<form action="/transaction/report/financial" method="get" id="filterForm">
							<c:choose>
								<c:when test="${isAdmin}">
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Bank Code </label> <select class="form-control selectpicker" data-live-search="true" name="bank" id="bankFilter">
												<option value="" selected>Bank Code</option>
												<c:forEach var="bank" items="${bankList}">
													<c:choose>
														<c:when test="${bankp eq bank.id}">
															<option selected value="${bank.id}">${bank.swiftCode}</option>
														</c:when>
														<c:otherwise>
															<option value="${bank.id}">${bank.swiftCode}</option>
														</c:otherwise>
													</c:choose>

												</c:forEach>
											</select>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-lg-2 col-md-2">
										<div class="form group">
											<label>Branch Code </label> <select class="form-control selectpicker" id="branchFilter" data-live-search="true" name="bank">
												<option value="" selected>Branch Code</option>
												<c:forEach var="branch" items="${branchList}">
													<c:choose>
														<c:when test="${bankp eq branch.id}">
															<option selected value="${branch.id}">${branch.branchCode}</option>
														</c:when>
														<c:otherwise>
															<option value="${branch.id}">${branch.branchCode}</option>
														</c:otherwise>
													</c:choose>

												</c:forEach>
											</select>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>From</label> <input type="date" class="form-control" id="fromDateFilter" value="${fromDatep}" name="fromDate"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>To</label> <input type="date" value="${toDatep}" id="toDateFilter" class="form-control" name="toDate"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>Identifier<c:if test="${isAdmin}">/Refstan</c:if></label> <input type="text" class="form-control" value="${identifierp}"
										placeholder="Identifier<c:if test="${isAdmin}">/Refstan</c:if>" id="identifierFilter" name="identifier"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">

									<label>Status</label> <select class="form-control" name="status" id="statusFilter">
										<option value="" selected disabled>Status</option>
										<c:forEach var="status" items="${transactionStatus}">
											<c:choose>
												<c:when test="${statusp eq status}">
													<option selected value="${status}">${status}</option>
												</c:when>
												<c:otherwise>
													<option value="${status}">${status}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>Mobile Number</label> <input type="text" class="form-control" id="mobileNumberFilter" value="${mobileNop}" placeholder="Mobile Number" name="mobileNo"></input>
								</div>
							</div>

							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>Target Number</label> <input type="text" class="form-control" id="targetNumberFilter" value="${targetNo}" placeholder="Target Number" name="target_no"></input>
								</div>
							</div>

							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>Sevice</label> <select class="form-control" name="serviceType" id="serviceTypeFilter">
										<option value="">Select a Service</option>
										<c:forEach var="service" items="${serviceList}">
											<c:choose>
												<c:when test="${serviceTypep eq service.uniqueIdentifier}">
													<option value="${service.uniqueIdentifier}" selected>${service.service}</option>
												</c:when>
												<c:otherwise>
													<option value="${service.uniqueIdentifier}">${service.service}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<option value="fundTransfer" <c:if test="${serviceTypep eq 'fundTransfer'}">selected</c:if>>Fund Transfer</option>
									</select>
								</div>
							</div>

							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a onclick="postForm()"><i class="fa fa-search small" title="Search"></i></a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<table id="transactionList" class="table table-striped table-bordered table-condensed" style="font-size: 12px !important;">
						<thead>
							<tr>
								<th>Date</th>
								<th>Customer Name</th>
								<th>Customer Mobile No</th>
								<th>Account No.</th>
								<c:choose>
									<c:when test="${isAdmin}">
										<th>Bank Code</th>
									</c:when>
									<c:otherwise>
										<th>Branch Code</th>
									</c:otherwise>
								</c:choose>
								<th>Target No.</th>
								<th>Transaction Id</th>
								<th>Amount</th>
								<th>Transaction Type</th>
								<th>Status</th>
								<th>Settlement Status</th>
								<th>Message</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(transactionList.object) gt 0}">
								<c:forEach var="transaction" items="${transactionList.object}">
									<tr>
										<td>${transaction.createdDate}</td>
										<td>${transaction.originatorName}</td>
										<td>${transaction.originatorMobileNo}</td>
										<td>${transaction.sourceAccount}</td>
										<c:choose>
											<c:when test="${isAdmin}">
												<td>${transaction.bankCode}</td>
											</c:when>
											<c:otherwise>
												<td>${transaction.branchCode}</td>
											</c:otherwise>
										</c:choose>
										<td>${transaction.destination}</td>
										<td><a style="overflow: hidden; color: blue;"
											href="${pageContext.request.contextPath}/transactiondetail?transactionId=${transaction.transactionIdentifier}">${transaction.transactionIdentifier}</a></td>
										<td>${transaction.amount}</td>
										<td>${transaction.service}</td>
										<td>${transaction.transactionStatus}</td>
										<td>${transaction.settlementStatus}</td>
										<td>${transaction.responseMessage}</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>

					<div class="row">
						<c:if test="${fn:length(transactionList.pageList) gt 1}">
							<div class="pagination-block pull-left">

								<ul class="pagination pagination-sm no-margin pagingclass">
									<li><c:if test="${transactionList.currentPage > 1}">
											<a
												href="/transaction/report/financial?pageNo=1&fromDate=${fromDatep}&toDate=${toDatep}&status=${statusp}&mobileNo=${mobileNop}&bank=${bankp}&identifier=${identifierp}&serviceType=${serviceTypep}&target_no=${targetNo}"
												class="pn prev mypgactive"><<</a>
											<a
												href="/transaction/report/financial?pageNo=${transactionList.currentPage-1}&fromDate=${fromDatep}&toDate=${toDatep}&status=${statusp}&mobileNo=${mobileNop}&bank=${bankp}&identifier=${identifierp}&serviceType=${serviceTypep}&target_no=${targetNo}"
												class="pn prev mypgactive">Prev</a>
										</c:if></li>

									<c:forEach var="pagelist" items="${transactionList.pageList}">
										<li><c:choose>

												<c:when test="${pagelist == transactionList.currentPage}">

													<span>${pagelist}</span>

												</c:when>
												<c:otherwise>

													<a
														href="/transaction/report/financial?pageNo=${pagelist}&fromDate=${fromDatep}&toDate=${toDatep}&status=${statusp}&mobileNo=${mobileNop}&bank=${bankp}&identifier=${identifierp}&serviceType=${serviceTypep}&target_no=${targetNo}"
														class="mypgactive">${pagelist}</a>

												</c:otherwise>
											</c:choose></li>
									</c:forEach>
									<li><c:if test="${transactionList.currentPage + 1 <= transactionList.lastpage}">
											<a
												href="/transaction/report/financial?pageNo=${transactionList.currentPage+1}&fromDate=${fromDatep}&toDate=${toDatep}&status=${statusp}&mobileNo=${mobileNop}&bank=${bankp}&identifier=${identifierp}&serviceType=${serviceTypep}&target_no=${targetNo}"
												class="pn next mypgactive">Next</a>
												<a
												href="/transaction/report/financial?pageNo=${transactionList.lastpage}&fromDate=${fromDatep}&toDate=${toDatep}&status=${statusp}&mobileNo=${mobileNop}&bank=${bankp}&identifier=${identifierp}&serviceType=${serviceTypep}&target_no=${targetNo}"
												class="pn next mypgactive">>></a>
										</c:if></li>
								</ul>
							</div>

						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="errorModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<p style="color: red; font-size: 15px;">{{warnMessage}}</p>
				</div>
			</div>
		</div>
	</div>

</spr:page>
<!-- <script>
	$(document).ready(function() {
		var today = new Date();
		document.getElementById("fromDateFilter").valueAsDate = today;
		document.getElementById('toDateFilter').valueAsDate = today;
	});
</script> -->
<script>
	function postForm() {

		/* if($("#fromDateFilter").val() == "" || $("#toDateFilter").val() == ""){
			alert("Please select the date range !!");
		}
		else{ */
		$("#filterForm").submit();
		//}
	};

	function downloadReport(fileType, fileName, tableName) {
		var date = new Date();
		if (fileType === "PDF") {
			$('#' + tableName).tableExport({
				fileName : fileName + date.getTime(),
				type : 'pdf',
				jspdf : {
					orientation : 'l',
					format : 'a2',
					margins : {
						left : 10,
						right : 10,
						top : 20,
						bottom : 20
					},
					autotable : {
						styles : {
							fillColor : 'inherit',
							textColor : 'inherit'
						},
						tableWidth : 'auto'
					}
				}
			});
		} else if (fileType === "EXCEL") {
			$("#" + tableName).tableExport({
				fileName : fileName + date.getTime(),
				worksheetName : 'Report',
				exportHiddenCells : true,
				type : "excel"
			});
		}
	};
</script>
<style>
.reportHiddenTable {
	position: absolute;
	top: -5000px;
	left: -5000px;
}

table {
	table-layout: fixed;
}

td {
	word-wrap: break-word;
}

.new-divider {
	margin: 0px 0px !important;
	border-bottom: 5px solid #ddd;
}
</style>