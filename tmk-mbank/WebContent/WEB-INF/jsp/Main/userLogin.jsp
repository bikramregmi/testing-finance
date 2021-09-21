<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sprt" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link href="/css/pagination.css" rel="stylesheet"></link>
<input id="firsrLogin" type="hidden" value="${firstLogin}" />

<sprt:page header1="DashBoard">
	<!-- added by amrit -->
	<ul class="nav nav-tabs">
		<li class="${overall}" style="color: #0000FF"><a href="/">Overall</a></li>
		<li class="${daily }" style="color: #0000FF"><a href="${pageContext.request.contextPath}/today_dashboard">Daily</a></li>

	</ul>

	<!-- end added by amrit -->
	<%-- <c:if test="${isBank eq true || isBankAdmin eq true}"></c:if> --%>
	<div class="row tile_count">
		<div class="row">
			<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
				<div class="rad-info-box rad-txt-primary">
					<i class="fa fa-user large"></i> <span class="heading">Total <spaceh>Customer Last</spaceh> <spaceh>Month Last</spaceh> Week
					</span> <span class="value "><span><spacev>${totalCustomer} ${lastMonthCustomer} ${lastWeekCustomer}</spacev></span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="rad-info-box rad-txt-danger">
					<a href="/customer/inactive/list"> <i class="fa fa-user large"></i> <span class="heading">INACTIVE CUSTOMER</span> <span class="value"><span>${inactiveCustomer}</span></span>
					</a>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="rad-info-box rad-txt-success">
					<i class="fa fa-money large"></i> <span class="heading">Available Balance</span> <span class="value"><span> <fmt:formatNumber type="number"
								maxFractionDigits="2" value="${availableBalance}" /></span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="rad-info-box rad-txt-success">
					<i class="fa fa-money large"></i> <span class="heading">Remaining Balance</span> <span class="value"><span> <fmt:formatNumber type="number"
								maxFractionDigits="2" value="${remainingBalance}" /></span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="rad-info-box rad-txt-success">
					<i class="fa fa-money large"></i> <span class="heading">Credit Balance</span> <span class="value"><span> <fmt:formatNumber type="number"
								maxFractionDigits="2" value="${creditBalance}" /></span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4" ng-mouseenter="options=true" ng-mouseleave="options=false">
				<div class="rad-info-box rad-txt-info" ng-hide="options">
					<i class="fa fa-envelope large"></i> <span class="heading">TOTAL SMS</span> <span class="value"><span>${totalCount}</span></span>
				</div>

				<div class="rad-info-box rad-txt-info" ng-show="options">
					<i class="fa fa-envelope large"></i> <span class="heading"><spaceh>INCOMING OUTGOING</spaceh></span> <span class="value word-space-value"><span><spacev>${totalIncoming}
							${totalOutgoing}</spacev></span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="rad-info-box rad-txt-warning">
					<i class="fa fa-envelope large"></i> <span class="heading">Remaining SMS Count</span> <span class="value"><span>${totalSmsCount}</span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="rad-info-box rad-txt-success">
					<i class="fa fa-money large"></i> <span class="heading">TRANSACTION</span> <span class="value"><span>${totalTransaction}</span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="rad-info-box rad-txt-info">
					<i class="fa fa-money large"></i> <span class="heading">Total balance enquiry</span> <span class="value"><span>${totalBalanceEnquiry}</span></span>
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
				<div class="rad-info-box rad-txt-info">
					<i class="fa fa-table large"></i> <span class="heading">Mini-statement check</span> <span class="value"><span>${totalMiniStatement}</span></span>
				</div>
			</div>

			<c:forEach var="service" items="${serviceList}">
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
					<div class="rad-info-box">
						<img src="/mbank/serviceIcon/${service.icon}" class="icon"></img> <span class="heading">${service.service}</span> <span class="value"><span>${service.count}</span></span>
					</div>
				</div>
			</c:forEach>

		</div>
	</div>
	<input type="hidden" ng-model="saveUrl" ng-init="saveUrl='${pageContext.request.contextPath}/changeuserpassword'" />

	<input type="hidden" ng-model="username" ng-init="username='${username}'" />

	<!-- Modal -->
	<div id="myfirstLoginModal" class="modal fade" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Change Password</h4>
				</div>
				<div class="modal-body" ng-hide="passwordChangeSuccess">
					<div class="input-group col-lg-12">
						<h4>{{myMessage}}</h4>
						<label>Old Password</label> <input type="password" class="form-control" ng-model="oldPassword" placeholder="Old Password" autocomplete="current-password" />
						<h6 style="color: red">{{userError.oldPassword}}</h6>

					</div>
					<div class="input-group col-lg-12">
						<label>New Password</label> <input type="password" class="form-control" ng-model="password" placeholder="New Password" autocomplete="new-password" />
						<h6 style="color: red">{{userError.password}}</h6>
					</div>
					<div class="input-group col-lg-12">
						<label>Re-Enter Password</label> <input type="password" class="form-control" ng-model="repassword" placeholder="Re-Enter Password" autocomplete="new-password" />
						<h6 style="color: red">{{userError.repassword}}</h6>
					</div>
				</div>
				<div class="modal-body" ng-show="passwordChangeSuccess">
					<p style="color: black; font-size: 20px; text-align: center;">Password Changed Successfully.</p>
					<p style="color: black; font-size: 12px; text-align: center;">
						You will be logged out in <span class="fade in" style="font-size: 20px; font-weight: bold;" ng-bind="logoutSecCounter"></span> seconds. Please use your new password to
						login.
					</p>
				</div>
				<div class="modal-footer" ng-hide="changePasswordLoading">
					<button type="button" ng-click="changePassword(saveUrl)" class="btn btn-primary btn-modal pull-left">Change Password</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
				<div class="modal-footer" ng-show="changePasswordLoading">
					<i class="fa fa-cog fa-spin fa-2x"></i>
				</div>
			</div>
		</div>
	</div>


</sprt:page>
<script>
	if ($('#firsrLogin').val() == 'true') {
		$('#myfirstLoginModal').modal({
			backdrop : 'static',
			keyboard : false,
			show : true
		});
	}
</script>
