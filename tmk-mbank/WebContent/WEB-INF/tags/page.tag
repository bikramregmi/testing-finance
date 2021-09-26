<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@tag import="java.util.Calendar"%>
<%@tag import="java.util.List"%>
<%@ tag body-content="scriptless"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
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

<%
	User currentUser = (User) session.getAttribute("currentUser");
	List<MerchantServiceDTO> serviceDto = (List) session.getAttribute("serviceDto");
	if (currentUser.getUserName().equals("admin") || currentUser.getUserName().equals("sysadmin")) {
%>

<c:set var="isAdmin" scope="page" value="true" />
<c:set var="isUser" scope="page" value="false" />
<c:set var="isCustomer" scope="page" value="false" />
<c:set var="isBank" scope="page" value="false" />
<c:set var="isBankBranch" scope="page" value="false" />
<c:set var="isChannelPartner" scope="page" value="false" />

<%
	}
%>

<%
	User currentUsr = (User) session.getAttribute("currentUser");
	if (currentUsr.getAuthority().equalsIgnoreCase(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)
			|| currentUsr.getAuthority()
					.equalsIgnoreCase(Authorities.SENDER_CUSTOMER + "," + Authorities.AUTHENTICATED)) {
%>

<c:set var="isAdmin" scope="page" value="false" />
<c:set var="isUser" scope="page" value="false" />
<c:set var="isCustomer" scope="page" value="true" />
<c:set var="isBank" scope="page" value="false" />
<c:set var="isBankBranch" scope="page" value="false" />
<c:set var="isChannelPartner" scope="page" value="false" />
<%
	}

	if (currentUser.getAuthority().equalsIgnoreCase(Authorities.BANK + "," + Authorities.AUTHENTICATED)
			|| currentUser.getAuthority()
					.equalsIgnoreCase(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
%>

<c:set var="isAdmin" scope="page" value="false" />
<c:set var="isUser" scope="page" value="false" />
<c:set var="isCustomer" scope="page" value="false" />
<c:set var="isBank" scope="page" value="true" />
<c:set var="isBankBranch" scope="page" value="false" />
<c:set var="isBankAdmin" scope="page" value="true" />
<c:set var="isChannelPartner" scope="page" value="false" />
<%
	}
%>

<%
	User merchant = (User) session.getAttribute("currentUser");
	if (currentUsr.getAuthority().equalsIgnoreCase(Authorities.MERCHANT + "," + Authorities.AUTHENTICATED)) {
%>

<c:set var="isAdmin" scope="page" value="false" />
<c:set var="isUser" scope="page" value="false" />
<c:set var="isCustomer" scope="page" value="false" />
<c:set var="isAgent" scope="page" value="false" />
<c:set var="isMerchant" scope="page" value="true" />
<c:set var="isBank" scope="page" value="false" />
<c:set var="isBankBranch" scope="page" value="false" />
<c:set var="isChannelPartner" scope="page" value="false" />

<%
	}
%>
<c:set var="isChecker" scope="page" value="false" />
<c:set var="isMaker" scope="page" value="false" />
<%
	if (currentUsr.getAuthority().equalsIgnoreCase(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED)
			|| currentUsr.getAuthority().equalsIgnoreCase(Authorities.BANK + "," + Authorities.AUTHENTICATED)) {
		if (currentUsr.getChecker() != null) {
			if (currentUsr.getChecker()) {
%>
<c:set var="isChecker" scope="page" value="true" />
<%
	}
		}
		if (currentUsr.getMaker() != null) {
			if (currentUsr.getMaker()) {
%>
<c:set var="isMaker" scope="page" value="true" />
<%
	}
		}
%>
<c:set var="isAdmin" scope="page" value="false" />
<c:set var="isUser" scope="page" value="false" />
<c:set var="isCustomer" scope="page" value="false" />
<c:set var="isAgent" scope="page" value="false" />
<c:set var="isMerchant" scope="page" value="false" />
<c:set var="isBank" scope="page" value="false" />
<c:set var="isBankBranch" scope="page" value="true" />
<c:set var="isChannelPartner" scope="page" value="false" />
<%
	}
	if (currentUsr.getAuthority().equalsIgnoreCase(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
%>

<c:set var="isAdmin" scope="page" value="false" />
<c:set var="isUser" scope="page" value="false" />
<c:set var="isCustomer" scope="page" value="false" />
<c:set var="isBank" scope="page" value="true" />
<c:set var="isBankBranch" scope="page" value="false" />
<c:set var="isBankAdmin" scope="page" value="true" />
<c:set var="isBankBranchAdmin" scope="page" value="false" />
<c:set var="isChannelPartner" scope="page" value="false" />
<%
	}
	if (currentUsr.getAuthority()
			.equalsIgnoreCase(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)) {
%>

<c:set var="isAdmin" scope="page" value="false" />
<c:set var="isUser" scope="page" value="false" />
<c:set var="isCustomer" scope="page" value="false" />
<c:set var="isBank" scope="page" value="false" />
<c:set var="isBankBranch" scope="page" value="false" />
<c:set var="isBankAdmin" scope="page" value="false" />
<c:set var="isBankBranchAdmin" scope="page" value="true" />
<c:set var="isChannelPartner" scope="page" value="false" />
<%
	}

	if (currentUsr.getAuthority()
			.equalsIgnoreCase(Authorities.CHANNELPARTNER + "," + Authorities.AUTHENTICATED)) {
%>

<c:set var="isAdmin" scope="page" value="false" />
<c:set var="isUser" scope="page" value="false" />
<c:set var="isCustomer" scope="page" value="false" />
<c:set var="isBank" scope="page" value="false" />
<c:set var="isBankBranch" scope="page" value="false" />
<c:set var="isBankAdmin" scope="page" value="false" />
<c:set var="isBankBranchAdmin" scope="page" value="false" />
<c:set var="isChannelPartner" scope="page" value="true" />
<%
	}
%>






<!-- <!DOCTYPE html> -->
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


<script src="/js/jquery.min.js"></script>
<script src="/js/jquery-ui.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="/js/moment.js"></script>
<script src="/js/dataTables.bootstrap.min.js"></script>
<script src="/js/angular.min.js"></script>
<script src="/js/sortable.min.js"></script>
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
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="/" class="site_title"> <span><img src="/assets/img/hand.png" height="60" width="120" alt="" /></span></a>
					</div>
					<div class="clearfix"></div>
					<div id="sidebar-menu" class="main_menu_side hidden-print main_menu" style="margin-top: 10px;">
						<div class="menu_section">
							<ul class="nav side-menu">
								<li><a><i class="fa fa-home"></i> Home <span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li><a href="${pageContext.request.contextPath}/">Dashboard</a></li>
									</ul></li>
								<jsp:useBean id="AuthUtil" class="com.mobilebanking.util.AuthenticationUtil" />
								<c:set var="menupage" scope="page" value="${AuthUtil.getCurrentUser().userTemplate.menuTemplate.menu}" />
								<c:forEach items="${menupage}" var="menupage">
									<c:if test="${menupage.menuType  eq 'SuperMenu'}">
										<li><a><i class="fa fa-circle-o"></i> ${menupage.name} <span class="fa fa-chevron-down"></span></a>
											<ul class="nav child_menu" style="display: none">
												<c:set var="submenupage" scope="page" value="${AuthUtil.getCurrentUser().userTemplate.menuTemplate.menu}" />
												<c:forEach items="${submenupage}" var="submenupage">
													<c:if test="${submenupage.menuType eq 'SubMenu' }">
														<c:if test="${submenupage.superId eq menupage.id }">
															<c:choose>
																<c:when test="${isBankBranch or isBank}">
																	<c:choose>
																		<c:when test="${!isMaker}">
																			<c:if test="${submenupage.url ne 'customer/add'}">
																				<li><a href="/${submenupage.url}">${submenupage.name}</a></li>
																			</c:if>
																		</c:when>
																		<c:otherwise>
																			<li><a href="/${submenupage.url}">${submenupage.name}</a></li>
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<li><a href="/${submenupage.url}">${submenupage.name}</a></li>
																</c:otherwise>
															</c:choose>
														</c:if>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:if>
								</c:forEach>
								<c:if test="${isMerchant}">
								</c:if>
								<c:if test="${isCustomer}">
									<li><a><i class="fa fa-user-circle-o"></i> Profile <span class="fa fa-chevron-down"></span></a>
										<ul class="nav child_menu" style="display: none">
											<li><a href="${pageContext.request.contextPath}/online/profile?user=${uuid}">View Profile</a></li>
											<li><a href="${pageContext.request.contextPath}/online/profile/edit?user=${uuid}">Edit Profile</a></li>
										</ul></li>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="top_nav">
				<div class="nav_menu">
					<nav class="" role="navigation">
					<div class="nav toggle">
						<a id="menu_toggle"><i class="fa fa-bars"></i></a>
					</div>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false"> <img src="/images/user.png" height="40px"
								width="38px"></img> <c:out value="${currentUser.userName}" />&nbsp; <span class=" fa fa-angle-down"></span>
						</a>

							<ul class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
								<li><a href="" data-toggle="modal" data-target="#myModal" ng-click="loadUsername('${currentUser.userName}')"> <i class="fa fa-key"></i>&nbsp;Change
										Password
								</a></li>
								<c:if test="${isAdmin}">
									<li><a href="" data-toggle="modal" data-target="#sparrowsmscreditmodal"> <i class="fa fa-envelope"></i>&nbsp;Sparrow SMS Credit
									</a></li>
									<li><a href="" ng-click="getSparrowSettings()"> <i class="fa fa-cog"></i>&nbsp;Sparrow SMS Setting
									</a></li>
								</c:if>
								<c:if test="${isBank || isBankAdmin}">
									<li><a href="${pageContext.request.contextPath}/bank/profileaccounts"> <i class="fa fa-cog"></i>&nbsp;Profile Account Setting
									</a></li>

								</c:if>
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/logout"> <i class="fa fa-sign-out"></i>&nbsp;Logout
								</a></li>
							</ul></li>
					</ul>
					</nav>
				</div>
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
	<div id="sparrowsmscreditmodal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Sparrow SMS Credit</h4>
				</div>
				<div class="modal-body">
					<%-- <h6><b>Remaining SMS Credit: </b>${smsCount}</h6>
       								<br/><br/> --%>
					<div class="row">
						<div class="col-md-5">
							<div class="form-group">
								<label>Add SMS Credit</label> <input type="text" name="smsCredit" ng-model="smscount" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary pull-left" ng-click="addSparrowSMSCredit()">Add</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div id="sparrowsmssettingsmodal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title text-center">Sparrow SMS Settings</h4>
				</div>
				<div class="modal-body">
					<div id="sparrowSettingsBody"></div>
					<div class="row">
						<div class="col-sm-11 form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-2" for="url">URL:</label>
								<div class="col-sm-10">
									<input type="text" id="url" class="form-control" ng-model="sparrow.url" ng-show="editSparrowSettings" />
									<p class="form-control-static" ng-hide="editSparrowSettings">{{sparrow.url}}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="from">From:</label>
								<div class="col-sm-10">
									<input type="text" id="from" class="form-control" ng-model="sparrow.from" ng-show="editSparrowSettings" />
									<p class="form-control-static" ng-hide="editSparrowSettings">{{sparrow.from}}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="token">Token:</label>
								<div class="col-sm-10">
									<input type="text" id="token" class="form-control" ng-model="sparrow.token" ng-show="editSparrowSettings" />
									<p class="form-control-static" ng-hide="editSparrowSettings">{{sparrow.token}}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary pull-left" ng-hide="editSparrowSettings" ng-click="editSparrowSettings = true">Edit</button>
					<button type="submit" class="btn btn-primary pull-left" ng-show="editSparrowSettings" ng-click="updateSparrowSettings()">Update</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
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