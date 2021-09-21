<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags/channelPartner"%>
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

		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<div class="row">
						<form action="/transaction_alert_report" method="get" id="filterForm">
							<c:if test="${not empty user}">
								<div class="col-lg-2 col-md-2">
									<div class="form group">
										<label>Bank Name</label> <select class="form-control selectpicker" name="swift-code">
											<option value="" selected disabled>swift code</option>
											<c:forEach var="banks" items="${bankList}">
												<option value="${banks.swiftCode}">${banks.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>

							<div class="col-lg-4 col-md-4">
								<div class="form group">
									<label>From</label> <input type="date" class="form-control" id="from-date" name="from-date" value="${fromDate}"></input>
								</div>
							</div>
							<div class="col-lg-4 col-md-4">
								<div class="form group">
									<label>To</label> <input type="date" class="form-control" id="to-date" name="to-date" value="${toDate}"></input>
								</div>
							</div>
							<div class="col-lg-3 col-md-3">
								<div class="form group">
									<label>Mobile Number</label> <input type="text" class="form-control" placeholder="Mobile Number" name="mobile-no" value="${mobileNo}"></input>
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

		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Date</th>
					<th>Mobile No</th>
					<th>Swift Code</th>
					<th>Status</th>
					<th>Message</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="talist" items="${transactionAlertList.object}">
					<tr>
						<td>${talist.dateTime}</td>
						<td>${talist.mobileNumber}</td>
						<td>${talist.swiftCode}</td>
						<td>${talist.smsStatus}</td>
						<td style="text-align: left">${talist.message}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${fn:length(transactionAlertList.pageList) gt 1}">
			<div class="pagination-block pull-left">

				<ul class="pagination pagination-sm no-margin pagingclass">
					<li><c:if test="${transactionAlertList.currentPage > 1}">
							<a
								href="/transaction_alert_report?pageNo=${transactionAlertList.currentPage-1}&from-date=${fromDate}&to-date=${toDate}&mobile-no=${mobileNo}&channel-partner=${channelPartner}&swift-code=${swiftCode}"
								class="pn prev mypgactive">Prev</a>
						</c:if></li>

					<c:forEach var="pagelist" items="${transactionAlertList.pageList}">
						<li><c:choose>

								<c:when test="${pagelist == transactionAlertList.currentPage}">

									<span>${pagelist}</span>

								</c:when>
								<c:otherwise>

									<a
										href="/transaction_alert_report?pageNo=${pagelist}&from-date=${fromDate}&to-date=${toDate}&mobile-no=${mobileNo}&channel-partner=${channelPartner}&swift-code=${swiftCode}"
										class="mypgactive">${pagelist}</a>

								</c:otherwise>
							</c:choose></li>
					</c:forEach>
					<li><c:if test="${transactionAlertList.currentPage + 1 <= transactionAlertList.lastpage}">
							<a
								href="/transaction_alert_report?pageNo=${transactionAlertList.currentPage+1}&from-date=${fromDate}&to-date=${toDate}&mobile-no=${mobileNo}&channel-partner=${channelPartner}&swift-code=${swiftCode}"
								class="pn next mypgactive">Next</a>
						</c:if></li>
				</ul>
			</div>

		</c:if>
	</div>
</spr:page>
<style>
table {
	font-size: 12px;
}

td {
	word-wrap: break-word;
}

.btn-xs {
	font-size: 11px;
}
</style>