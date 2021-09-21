<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->

<spr:page header1="Payout Transaction">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/online/transaction/search" modelAttribute="transaction"
				method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Unique Number</label> <input type="text" name="uniqueNumber"
						class="form-control input-sm" value="${transaction.uniqueNumber}">
					<p class="error">${error.uniqueNumber}</p>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">
						Search Remit</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>