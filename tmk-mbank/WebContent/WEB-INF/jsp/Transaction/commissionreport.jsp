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
<spr:page header1="Commission Report">
	<div class="dropdown pull-right"  ng-controller="reportController">
		<a class="btn btn-info" data-toggle="dropdown" ng-click="generateReportForCommission()"><i class="fa fa-download small" title="View As PDF"></i></a>
		<ul class="dropdown-menu">
			<li style="text-align: center; color: grey;">Commission Report</li>
			<li class="divider new-divider"></li>
			<li><a href="" onclick="downloadReport('EXCEL','commission','reportTable')" ng-show="reportDataLoad"> <i class="fa fa-file-excel-o fa-2x" style="color: green;"></i> Excel
			</a></li>
			<li ng-hide="reportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
			<li class="divider new-divider"></li>
			<li><a href="" onclick="downloadReport('PDF','commission','reportTable')" ng-show="reportDataLoad"> <i class="fa fa-file-pdf-o fa-2x" style="color: red;"></i> PDF
			</a></li>
			<li ng-hide="reportDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
			<li class="divider new-divider"></li>
			<li style="text-align: center; color: grey;">Revenue Report</li>
			<li class="divider new-divider"></li>
			<li><a href="" onclick="downloadReport('EXCEL','revenue','revenueReportTable')" ng-show="revenueDataLoad"> <i class="fa fa-file-excel-o fa-2x" style="color: green;"></i> Excel
			</a></li>
			<li ng-hide="revenueDataLoad" style="text-align: center;"><i class="fa fa-spinner fa-2x fa-spin"></i></li>
		</ul>
		<%@include file="/WEB-INF/jsp/reports/commissionReport.jsp"%>
	</div>
	<div class="row tile_count">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
						<form action="/transaction/report/commission" method="get" id="filterForm">
							<div class="col-lg-4 col-md-4">
								<div class="form group">
									<label>Bank </label> <select class="form-control selectpicker" id="bankFilter" data-live-search="true" name="bank">
										<option value="" selected disabled>Select A Bank</option>
										<c:forEach var="newbank" items="${bankList}">
											<c:choose>
												<c:when test="${bank eq newbank.id}">
													<option value="${newbank.id}" selected>${newbank.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${newbank.id}">${newbank.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>Merchant </label> <select class="form-control selectpicker" id="merchantFilter" data-live-search="true" name="merchant">
										<option value="" selected disabled>Select A Merchant</option>
										<c:forEach var="newMerchant" items="${merchantList}">
											<c:choose>
												<c:when test="${merchant eq newMerchant.id}">
													<option value="${newMerchant.id}" selected>${newMerchant.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${newMerchant.id}">${newMerchant.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>From</label> <input type="date" id="fromDateFilter" class="form-control" name="fromDate" value="${fromDate}"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>To</label> <input type="date" id="toDateFilter" class="form-control" name="toDate" value="${toDate}"></input>
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
								<th>Initiator</th>
								<th>Bank Code</th>
								<th>Transaction Id</th>
								<th>Amount</th>
								<th>Transaction Type</th>
								<th>Total Commission</th>
								<th>Bank Commission</th>
								<th>Operator Commission</th>
								<th>Channel Partner Commission</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(transactionList.object) gt 0}">
								<c:forEach var="transaction" items="${transactionList.object}">
									<tr>
										<td>${transaction.createdDate}</td>
										<td>${transaction.originatorName}</td>
										<td>${transaction.bankCode}</td>
										<td>${transaction.transactionIdentifier}</td>
										<td>${transaction.amount}</td>
										<td>${transaction.service}</td>
										<td>${transaction.totalCommission}</td>
										<td>${transaction.bankCommission}</td>
										<td>${transaction.operatorCommissionAmount}</td>
										<td>${transaction.channelPartnerCommissionAmount}</td>
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
											<a href="/transaction/report/commission?pageNo=${transactionList.currentPage-1}&fromDate=${fromDate}&toDate=${toDate}&bank=${bank}" class="pn prev mypgactive">Prev</a>
										</c:if></li>
									<c:forEach var="pagelist" items="${transactionList.pageList}">
										<li><c:choose>
												<c:when test="${pagelist == transactionList.currentPage}">
													<span>${pagelist}</span>
												</c:when>
												<c:otherwise>
													<a href="/transaction/report/commission?pageNo=${pagelist}&fromDate=${fromDate}&toDate=${toDate}&bank=${bank}" class="mypgactive">${pagelist}</a>
												</c:otherwise>
											</c:choose></li>
									</c:forEach>
									<li><c:if test="${transactionList.currentPage + 1 <= transactionList.lastpage}">
											<a href="/transaction/report/commission?pageNo=${transactionList.currentPage+1}&fromDate=${fromDate}&toDate=${toDate}&bank=${bank}" class="pn next mypgactive">Next</a>
										</c:if></li>
								</ul>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</spr:page>
<script>
$(document).ready(function(){
	var today=new Date();
	
	document.getElementById('fromDateFilter').valueAsDate = today;
	document.getElementById('toDateFilter').valueAsDate = today;
	
});
</script>
<script>
	function postForm() {
		$("#filterForm").submit();
	};
	function downloadReport(fileType, reportType) {
		var date = new Date();
		if (reportType === "commission") {
			if (fileType === "PDF") {
				$('#reportTable').tableExport({
					fileName : 'commissionReport' + date.getTime(),
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
				$("#reportTable").tableExport({
					fileName : 'commissionReport' + date.getTime(),
					worksheetName : 'Report',
					exportHiddenCells : true,
					type : "excel"
				});
			}
		} else if (reportType === "revenue") {
			if (fileType === "EXCEL") {
				$("#revenueReportTable").tableExport({
					fileName : 'commissionReport' + date.getTime(),
					worksheetName : 'Report',
					exportHiddenCells : true,
					type : "excel"
				});
			}
		}
	};
</script>
<script src="${pageContext.request.contextPath}/js/Angular/report.js"></script>