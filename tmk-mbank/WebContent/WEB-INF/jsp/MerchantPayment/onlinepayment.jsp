<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>TMK Wallet - Differentiate Your Payment Methodology</title>
<link href='https://fonts.googleapis.com/css?family=Oswald'
	rel='stylesheet' type='text/css'>
<!-- Bootstrap -->
<link href="../css/usercss/bootstrap.css" rel="stylesheet">
<link href="../css/usercss/bootstrap-theme.css" rel="stylesheet"
	type="text/css">
<link href="../css/usercss/custom.css" rel="stylesheet" type="text/css">
<link href='https://fonts.googleapis.com/css?family=Slabo+27px'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Roboto+Condensed'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Ubuntu:400,500'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Montserrat'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Fjalla+One'
	rel='stylesheet' type='text/css'>
<link
	href='https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Assistant:700'
	rel='stylesheet' type='text/css'>

<link type="text/css" rel="stylesheet"
	href="../css/featherlight.min.css" />

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="../js/userjs/jquery-1.11.2.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/userjs/bootstrap.js"></script>
<script src="../js/featherlight.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="../js/userjs/custom.js"></script>
<script>
	function myFunction() {
		document.getElementsByClassName("topnav")[0].classList
				.toggle("responsive");
	}
	/* $(document).ready(function(){
		$('.spops').click(function(e){
			e.preventDefault();
			$('.pop').show();
		});
	}); */
</script>
</head>
<body>
	<div class="container">
		<%@include file="../Main/header.jsp"%>
		<div class="row">
			<div class="body">
				<div class="col-lg-12">
					<div class="row">
						<div class="col-lg-2 col-md-2">
							<%@include file="../Main/leftpanel.jsp"%>
						</div>
						<div class="col-lg-8 col-md-8 ">
							<div class="row">
								<div class="col-lg-12">
									<h2>ONLINE PAYMENT</h2>
									<div class="col-lg-12">
										<ul>
											<li><a href="/payment/online/wallet">PAY THROUGH WALLET</a></li>
										</ul>

									</div>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
						<div class="col-lg-2 col-md-2  ">
							<%@include file="../Main/leftpanel.jsp"%>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>

		</div>
	</div>

	<div style="display: none;">
		<div class="lightbox" id="forgetpassword">
			<%-- 			<%@include file="changepassword.jsp"%> --%>
		</div>
	</div>


	<div class="pop">
		<span>X</span>

		<form action="/cusChangePass" method="post">
			<div class="form-group">
				<label for="pwd">Password:</label> <input type="password"
					class="form-control" id="pwd">
			</div>
		</form>

	</div>
</body>
</html>