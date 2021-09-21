<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link href="/css/pagination.css" rel="stylesheet"></link>
<input id="firsrLogin" type="hidden" value="${firstLogin}" />
<spr:page header1="Non-Financial Report">
<a href="/transaction/report/nonfinancial/export" target="_blank" class="btn btn-info pull-right"><i class="fa fa-download small" title="View As PDF"></i></a>
	<div class="row tile_count">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
						<form action="/transaction/report/nonfinancial" method="get"
							id="filterForm">
							<div class="col-lg-2 col-md-2">
								<div class="form group">
								<c:choose>
								<c:when test="${isAdmin}">
									<label>Bank Code</label>
									<select class="form-control selectpicker" name="bank" data-live-search="true">
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
									</c:when>
									<c:otherwise>
									<label>Branch Code</label>
									<select class="form-control selectpicker" name="bank" data-live-search="true">
										<option value="" selected>Branch Code</option>
										<c:forEach var="branch" items="${branchList}">
											<c:choose>
												<c:when test="${bankp eq branch.id}">
													<option value="${branch.id}" selected>${branch.branchCode}</option>
												</c:when>
												<c:otherwise>
													<option value="${branch.id}">${branch.branchCode}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</select>
									</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>From</label> <input value="${fromDatep}" type="date" id="fromDateFilter"
										class="form-control" name="fromDate"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>To</label> <input type="date" value="${toDatep}" id="toDateFilter"
										class="form-control" name="toDate"></input>
								</div>
							</div>
							<div class="col-lg-2 col-md-2">
								<div class="form group">
									<label>Identifier</label> <input type="text"
										value="${identifierp}" class="form-control"
										placeholder="Transaction Identifier" name="identifier"></input>
								</div>
							</div>
							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<label>Status</label> <select class="form-control"
										name="status">
										<option value="" selected disabled>Status</option>
										

										<c:forEach var="status" items="${isoStatus}">
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
									<label>Mobile Number</label> <input type="text" value="${mobileNop}"
										class="form-control" placeholder="Mobile Number"
										name="mobileNo"></input>
								</div>
							</div>

							<div class="col-lg-1 col-md-1">
								<div class="form group">
									<a onclick="postForm()"><i class="fa fa-search small"
										title="Search"></i></a>
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
					<table id="transactionList"
						class="table table-striped table-bordered table-condensed"
						style="font-size: 12px !important;">
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
								<th>Transaction Id.</th>
								<th>Transaction Type</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(transactionList.object) gt 0}">
								<c:forEach var="transaction" items="${transactionList.object}">
									<tr>
										<td>${transaction.date}</td>
										<td>${transaction.customerName}</td>
										<td>${transaction.mobileNumber}</td>
										<td>${transaction.accountNumber}</td>
										<c:choose>
											<c:when test="${isAdmin}">
												<td>${transaction.bankCode}</td>
											</c:when>
											<c:otherwise>
												<td>${transaction.branchCode}</td>
											</c:otherwise>
										</c:choose>
										<td>${transaction.transactionIdentifier}</td>
										<td>${transaction.transactionType}</td>
										<td>${transaction.status}</td>
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
															href="/transaction/report/nonfinancial?pageNo=${transactionList.currentPage-1}&fromDate=${fromDatep}&toDate=${toDatep}&status=${statusp}&mobileNo=${mobileNop}&bank=${bankp}&identifier=${identifierp}"
															class="pn prev mypgactive">Prev</a>
													</c:if></li>

												<c:forEach var="pagelist"
													items="${transactionList.pageList}">
													<li><c:choose>

															<c:when test="${pagelist == transactionList.currentPage}">

																<span>${pagelist}</span>

															</c:when>
															<c:otherwise>

																<a
																	href="/transaction/report/nonfinancial?pageNo=${pagelist}&fromDate=${fromDatep}&toDate=${toDatep}&status=${statusp}&mobileNo=${mobileNop}&bank=${bankp}&identifier=${identifierp}"
																	class="mypgactive">${pagelist}</a>

															</c:otherwise>
														</c:choose></li>
												</c:forEach>
												<li><c:if
														test="${transactionList.currentPage + 1 <= transactionList.lastpage}">
														<a
															href="/transaction/report/nonfinancial?pageNo=${transactionList.currentPage+1}&fromDate=${fromDatep}&toDate=${toDatep}&status=${statusp}&mobileNo=${mobileNop}&bank=${bankp}&identifier=${identifierp}"
															class="pn next mypgactive">Next</a>
													</c:if></li>
											</ul>
										</div>

									</c:if>
								</div>
					<%-- <c:if test="${fromFilter eq 'false'}">
				<div class="pagination">
       		 		<ul>
          				<li><a href="/transaction/get?pageNo=first" title="First Page">«</a></li>
          				<c:choose>
          					<c:when test="${currentPage eq 1}">
          						<li class="active"><a href="/transaction/get?pageNo=${currentPage}">${currentPage}</a></li>
          						<li><a href="/transaction/get?pageNo=${currentPage + 1}">${currentPage + 1}</a></li>
          						<li><a href="/transaction/get?pageNo=${currentPage + 2}">${currentPage + 2}</a></li>
          					</c:when>
          					<c:when test="${currentPage eq lastPage}">
          						<li><a href="/transaction/get?pageNo=${currentPage - 2}">${currentPage -2}</a></li>
          						<li><a href="/transaction/get?pageNo=${currentPage - 1}">${currentPage - 1}</a></li>
          						<li class="active"><a href="/transaction/get?pageNo=${currentPage}">${currentPage}</a></li>
          					</c:when>
          					<c:otherwise>
          						<li><a href="/transaction/get?pageNo=${currentPage - 1}">${currentPage - 1}</a></li>
          						<li class="active"><a href="/transaction/get?pageNo=${currentPage}">${currentPage}</a></li>
          						<li><a href="/transaction/get?pageNo=${currentPage + 1}">${currentPage + 1}</a></li>
          					</c:otherwise>
          				</c:choose>
          				<li><a href="/transaction/get?pageNo=last" title="Last Page">»</a></li>
        			</ul>
				</div>
				</c:if> --%>
				</div>
			</div>
		</div>

	</div>

</spr:page>
<!-- <script>
$(document).ready(function(){
	var today=new Date();
	document.getElementById('fromDateFilter').valueAsDate=today;
	document.getElementById('toDateFilter').valueAsDate=today;
});
</script> -->
<script>
	function postForm() {
		$("#filterForm").submit();
	}
</script>