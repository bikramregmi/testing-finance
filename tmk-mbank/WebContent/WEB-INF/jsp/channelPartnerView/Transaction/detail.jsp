<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags/channelPartner"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spr:page header1="Detail">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
	</div>
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="rad-info-box">
			<div class="row">
				<form action="/transaction_alert_report" method="get" id="filterForm">
					<div class="col-lg-3 col-md-3">
						<div class="form group">
							<label>Bank Name</label> <select class="form-control selectpicker" name="bank" data-live-search="true">
								<option value="" selected disabled>Bank</option>
								<c:forEach var="banks" items="${bankList}">
									<option value="${banks.swiftCode}">${banks.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-4 col-md-4">
						<div class="form group">
							<label>From</label> <input type="date" class="form-control" id="fromDate" name="fromDate" value="${fromDate}"></input>
						</div>
					</div>
					<div class="col-lg-4 col-md-4">
						<div class="form group">
							<label>To</label> <input type="date" class="form-control" id="toDate" name="toDate" value="${toDate}"></input>
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
	<div class="col-xs-12">
		<div class="rad-info-box">
			<div class="row"></div>
		</div>l,
	</div>
</spr:page>
<script>
	$(document).ready(function() {
		$("#bankList").dataTable();
	});
</script>
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