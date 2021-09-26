<!doctype html>
<!--[if lt IE 7]><html lang="en" class="no-js ie6"><![endif]-->
<!--[if IE 7]><html lang="en" class="no-js ie7"><![endif]-->
<!--[if IE 8]><html lang="en" class="no-js ie8"><![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
<meta charset="UTF-8">
<title>Finance Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link rel="shortcut icon" href="/images/favicon.ico">

<!-- Bootstrap 3.3.2 -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css">

<link rel="stylesheet" href="assets/css/animate.css">
<link rel="stylesheet" href="assets/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/slick.css">
<link rel="stylesheet" href="assets/js/rs-plugin/css/settings.css">

<link rel="stylesheet" href="assets/css/login.css">

<script type="text/javascript" src="assets/js/modernizr.custom.32033.js"></script>

<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<script type="text/javascript">
	//<![CDATA[ 
	var tlJsHost = ((window.location.protocol == "https:") ? "https://secure.comodo.com/"
			: "http://www.trustlogo.com/");
	document
			.write(unescape("%3Cscript src='"
					+ tlJsHost
					+ "trustlogo/javascript/trustlogo.js' type='text/javascript'%3E%3C/script%3E"));
	//]]>
</script>
</head>
<body>
	<div class="pre-loader">
		<div class="load-con">
			<img src="assets/img/hand.png" class="animated fadeInDown" alt="">
			<div class="spinner">
				<div class="bounce1"></div>
				<div class="bounce2"></div>
				<div class="bounce3"></div>
			</div>
		</div>
	</div>
	<header class="loginheader">
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="fa fa-bars fa-lg"></span>
					</button>

				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="${pageContext.request.contextPath}/main#about">finance</a></li>
						<li><a href="${pageContext.request.contextPath}/main#features">features</a></li>
						<li><a href="${pageContext.request.contextPath}/main#reviews">Testimonials</a></li>
						<li><a href="${pageContext.request.contextPath}/main#demo">demo</a></li>
						<li><a href="${pageContext.request.contextPath}/main#support">Contact Us</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-->
		</nav>


		<!--RevSlider-->
		<div class="tp-banner-container">
			<div class="tp-banner">
				<ul>
					<!-- SLIDE  -->
					<li data-transition="fade" data-slotamount="7" data-masterspeed="1500">
						<!-- MAIN IMAGE --> <img src="assets/img/transparent.png" alt="slidebg1" data-bgfit="cover" data-bgposition="left top" data-bgrepeat="no-repeat"> <!-- LAYERS -->
						<!-- LAYER NR. 1 -->
						<div class="tp-caption lfl fadeout" data-x="left" data-y="bottom" data-hoffset="30" data-voffset="0" data-speed="500" data-start="700" data-easing="Power4.easeOut ">
<%--							<img src="assets/img/hand-login.png" alt="">--%>
						</div>


						<div class="tp-caption sfb tp-image" data-x="650" data-y="center" data-hoffset="0" data-voffset="-100" data-speed="900" data-start="1700"
							data-easing="Power4.easeOut">
							<a><img src="assets/img/hand.png" width="100" alt=""></a>
							<h3 class="text-white">Please Log In</h3>
							<br>
						</div>
						<form:form action="/j_spring_security_check" method="POST" autocomplete="on">
						<div class="tp-caption tp-user" data-x="650" data-y="center" data-hoffset="0" data-voffset="0" data-speed="900" data-start="1800" data-easing="Power4.easeOut">
							<input type="text" class="form-control col-md-3" placeholder="Username" name='j_username'>
						</div>

						<div class="tp-caption tp-password" data-x="650" data-y="center" data-hoffset="0" data-voffset="50" data-speed="900" data-start="1900" data-easing="Power4.easeOut">
							<input type="password" class="form-control col-md-3" placeholder="Password" name='j_password'>
						</div>

						<div class="tp-caption tp-log" data-x="650" data-y="center" data-hoffset="0" data-voffset="120" data-speed="900" data-start="2000" data-easing="Power4.easeOut">
							<input type="submit" class="btn btn-default inverse btn-lg" value="LOG IN">
						</div>
						</form:form>
					</li>
				</ul>
			</div>
		</div>
	</header>
	<script src="assets/js/jquery-1.11.1.min.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/slick.min.js"></script>
	<script src="assets/js/placeholdem.min.js"></script>
	<script src="assets/js/rs-plugin/js/jquery.themepunch.plugins.min.js"></script>
	<script src="assets/js/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>
	<script src="assets/js/waypoints.min.js"></script>
	<script src="assets/js/scripts.js"></script>
	<script>
		$(document).ready(function() {
			appMaster.preLoader();
		});
	</script>
	<div style="z-index: 1000; position: fixed; bottom: 0; right: 0">
		<script language="JavaScript" type="text/javascript">
			TrustLogo("https://mbank.com.np/images/comodo_secure_seal.png",
					"CL1", "none");
		</script>
		<a href="https://ssl.comodo.com/wildcard-ssl-certificates.php" id="comodoTL">Wildcard SSL</a>
	</div>

</body>
<style>
header .navbar-default ul.navbar-nav {
	padding-top: 0;
}

.form-control {
	margin-bottom: 10px !important;
}

@media screen and (max-width: 992px) {
	.tp-user {
		position: absolute !important;
		top: 295px !important;
	}
	.tp-password {
		position: absolute !important;
		top: 360px !important;
	}
	.tp-log {
		position: absolute !important;
		top: 425px !important;
	}
}

@media screen and (max-width: 767px) {
	header .navbar-default .navbar-collapse {
		margin-top: 6px !important;
		background: #336799;
	}
}

@media screen and (max-width: 480px) {
	.tp-user input {
		width: 80% !important;
	}
	.tp-caption img {
		left: 10px !important;
	}
	.tp-password input {
		width: 80%;
	}
	.tp-caption.sfb.tp-image {
		left: 190px !important;
		top: 205px !important;
	}
	.tp-caption.tp-user {
		left: 190px !important;
	}
	.tp-caption.tp-password {
		left: 190px !important;
		top : 335px !important;
	}
	.tp-caption.tp-log {
		left: 190px !important;
		top : 380px !important;
	}
}
</style>

</html>
