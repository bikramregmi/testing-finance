<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@tag import="java.util.Calendar"%>
<%@tag import="java.util.List"%>
<%@ tag body-content="scriptless"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags/channelPartner"%>
<%@tag import="org.slf4j.Logger"%>
<%@tag import="org.slf4j.LoggerFactory"%>
<%@tag import="org.slf4j.Logger"%>
<%@tag import="com.mobilebanking.entity.User"%>
<%@tag import="com.mobilebanking.model.MerchantServiceDTO"%>
<%@tag import="com.mobilebanking.util.Authorities"%>
<%@ attribute name="header1" required="true" type="java.lang.String"%>

<%
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en" ng-app="tmkmobilebanking" ng-controller="tmkmobilebankingController">

<head>
<link href="/js/jquery-ui.min.css" rel="stylesheet" />
<link rel="stylesheet" href="/css/bootstrap-select.min.css" />
<link href="/css/bootstrap.min.css" rel="stylesheet" />
<link href="/css/font-awesome.css" rel="stylesheet" />
<link href="/css/animate.min.css" rel="stylesheet" />
<link href="/css/customNew.css" rel="stylesheet" />
<link href="/css/jquery-ui.css" rel="stylesheet" />
<!-- <link href="/css/jquery.dataTables.min.css" rel="stylesheet"></link> -->
<link href="/css/dataTables.bootstrap.min.css"/>
<!-- added by amrit for the dashboard tab --><!-- 

<link rel="stylesheet" href="/css/bootstrap3.3.7-bootstap.min.css" /> -->
<link rel="stylesheet" href="/css/nepaliDate/nepali.datepicker.v2.2.min.css"/>
<link href="/css/print.css" rel="stylesheet" media="all"/>
<!-- end of added -->
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link href="/css/channelPartner.css" rel="stylesheet" />

<script src="/js/jquery.min.js"></script>
<script src="/js/jquery-ui.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="/js/dataTables.bootstrap.min.js"></script>
<script src="/js/angular.min.js"></script>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.js"></script> -->
<script src="/js/modernizr.custom.32033.js"></script>
<script src="/js/nprogress.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/bootstrap-select.min.js"></script>
<script src="/js/jFormslider.js"></script>
<script src="/js/custom.js"></script>
<script src="/js/Admin/controller.js"></script>
<!-- added js by amrit -->
<script src="/js/jquery.PrintArea.js"></script>
<script src="/js/jsDate.js"></script>
<script src="/js/nepalidate/nepali.datepicker.v2.2.min.js"></script>
<!-- end of print plugin  -->

<!-- To save the generated export files on client side -->
<script type="text/javascript" src="/js/export/FileSaver/FileSaver.min.js"></script>
<!-- To export the table in XLSX (Excel 2007+ XML Format) format -->
<script type="text/javascript" src="/js/export/js-xlsx/xlsx.core.min.js"></script>
<!-- To export the table as a PDF file  -->
<script type="text/javascript" src="/js/export/jsPDF/jspdf.min.js"></script>
<script type="text/javascript" src="/js/export/jsPDF-AutoTable/jspdf.plugin.autotable.js"></script>
<!-- To export the table in PNG format -->
<script type="text/javascript" src="/js/export/html2canvas/html2canvas.min.js"></script>

<script type="text/javascript" src="/js/export/tableExport.min.js"></script>
<script>
	$(document).ready(function() {

		var options = {
			width : 500,//width of slider
			next_prev : true,//will show next and prev links
			next_class : 'btn btn-primary btn-xs',//class for next link
			prev_class : 'btn btn-primary btn-xs',//class for prev link
			error_class : 'alert alert-danger',//class for validation errors
			texts : {
				next : 'next',//verbiage for next link
				prev : 'back'//verbiage for prev link
			},
			speed : 600,//slider speed

		};

		$('#slider').jFormslider(options);

	})

	function last_slide() {
		/* alert("you are going to reach last slide if this function retuned true"); */
		return true;
	}
</script>
<script type="text/javascript" src="/js/channelPartner.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8 ;" />
<title>Mobile Banking</title>
</head>
<body class="nav-md" ng-app>
	<input type="hidden" ng-model="saveUrl" ng-init="saveUrl='${pageContext.request.contextPath}/changeuserpassword'" />
	<%
		logger.info("iname is " + this.getClass().getName());
	%>
	<div class="container body">
		<div class="main_container">
			<div class="top_nav">
				<div class="nav_menu">
					<nav class="" role="navigation">
					<ul class="nav navbar-nav navbar-right">
						<li class="mbank-logo">
							<a class="site_title" id="titleBar"><span><i class="fa fa-bars" id="sideBarKey"></i></span></a>
						</li>
						<li><a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false"> <img src="/images/user.png" height="40px"
								width="38px"></img> <c:out value="${currentUser.userName}" />&nbsp; <span class=" fa fa-angle-down"></span>
						</a>

							<ul class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
								<li><a href="" data-toggle="modal" data-target="#myModal" ng-click="loadUsername('${currentUser.userName}')"> <i class="fa fa-key"></i>&nbsp;Change
										Password
								</a></li>
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/logout"> <i class="fa fa-sign-out"></i>&nbsp;Logout
								</a></li>
							</ul></li>
					</ul>
					</nav>
				</div>
			</div>
			<div class="sidebar" id="channelSideBar">
			<span class="sidebar-close" id="sidebarClose"><i class="fa fa-times" id="sideBarKey"></i></span>
			<span class="mbank-logo"><img src="/assets/img/logo-white.png" height="60" width="120" alt=""/></span>
			<ul class="menu-list">
				<li class="menu"><a href="/"><i class="fa fa-home"></i> Dashboard</a></li>
				<li class="menu"><a href="/listBank"><i class="fa fa-university"></i> Bank</a></li>
				<li class="menu"><a href="/transaction_alert_report"><i class="fa fa-money"></i> Transaction Alert</a></li>
				<li class="menu"><a href="/customer/summary"><i class="fa fa-users"></i> Customer</a></li>
				<li class="menu"><a href="/transaction/commission/summary"><i class="fa fa-money"></i> Commission</a></li>
				<!-- <li class="menu"><a href="#"><i class="fa fa-money"></i> Transaction <i class="fa fa-angle-down"></i></a>
					<ul class="sub-menu-list">
						<li class="sub-menu"><a href="/transaction/financial/summary"><i class="fa fa-money"></i> Financial</a></li>
						<li class="sub-menu"><a href="/transaction/nonfinancial/summary"><i class="fa fa-money"></i> Non-Financial</a></li>
					</ul>
				</li> -->
				<li class="menu"><a href="/transaction/financial/summary"><i class="fa fa-money"></i>  Transaction</a></li>
				<li class="menu"><a href="/transaction/nonfinancial/summary"><i class="fa fa-money"></i> Non-Financial Transaction</a></li>
			</ul>
			</div>
			<div class="right_col" role="main">
				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3>${header1}</h3>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="x_title_two" style="min-height: 600px;">
									<div class="clearfix"></div>
									<jsp:doBody />
								</div>
							</div>
						</div>
					</div>
				</div>
				<spr:footer />
			</div>
		</div>
	</div>
	<div id="myModal" class="modal fade" role="dialog" tabindex="-1">
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
	<script>
		$(function() {
			if (!Modernizr.inputtypes.date) {
				// If not native HTML5 support, fallback to jQuery datePicker
				$('input[type=date]').datepicker({
					// Consistent format with the HTML5 picker
					dateFormat : 'yy-mm-dd'
				},
				// Localization
				$.datepicker.regional['it']);
			}
		});
	</script>
</body>
</html>