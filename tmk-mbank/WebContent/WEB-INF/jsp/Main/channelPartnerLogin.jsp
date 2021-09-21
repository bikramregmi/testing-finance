<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sprt" tagdir="/WEB-INF/tags/channelPartner"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<sprt:page header1="DashBoard">
	<div class="container">
		<div class="row">
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="rad-info-box rad-txt-primary">
					<a href="/listBank"> <i class="fa fa-university large"></i> <span class="heading">BANKS</span> <span class="value"><span>${totalBanks}</span></span>
					</a>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="rad-info-box rad-txt-primary">
					<a href="/transaction_alert_report"> <i class="fa fa-bell-o large"></i> <span class="heading">TRANSACTION ALERT</span> <span class="value"><span>${totaltransactionAlert}</span></span>
					</a>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="rad-info-box rad-txt-primary">
					<a href="/transaction/report/commission"> <i class="fa fa-money large"></i> <span class="heading">COMMISSION</span> <span class="value"><span>${totalCommission}</span></span>
					</a>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="rad-info-box rad-txt-success">
					<a href="/transaction/report/commission"> <i class="fa fa-money large"></i> <span class="heading">Transaction</span> <span class="value"><span>${totalTransaction}0</span></span>
					</a>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="rad-info-box rad-txt-info">
					<i class="fa fa-money large"></i> <span class="heading">Total balance enquiry</span> <span class="value"><span>${totalBalanceEnquiry}0</span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="rad-info-box rad-txt-info">
					<i class="fa fa-table large"></i> <span class="heading">Mini-statement check</span> <span class="value"><span>${totalMiniStatement}0</span></span>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box rad-txt-primary">
					<i class="fa fa-users large"></i> <span class="heading">Customer</span> <span class="value"><span>${totalMiniStatement}0</span></span>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" ng-model="username" ng-init="username='${username}'" />
</sprt:page>
<c:if test="${firstLogin}">
	<script>
		$(document).ready(function() {
			$('#myModal').modal("show");
		});
	</script>
</c:if>