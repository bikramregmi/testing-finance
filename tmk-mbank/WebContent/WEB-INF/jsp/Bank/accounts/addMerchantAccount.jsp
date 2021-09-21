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


<spr:page header1="Add Merchant Account">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/bank/addmerchantaccounts" method="post" class="col-md-12"style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="bank" value="${bank.swiftCode}">
				<div class="form-group">
					<label>Merchant</label>
					<select name="merchantId" class="form-control input-sm">
					<option value="merchant" selected disabled>Select A Merchant</option>
					<c:forEach var="merchant" items="${merchantList}">
						<option value="${merchant.id}">${merchant.name}</option>
					</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Merchant Account</label>
					<input type="text" name="accountNumber" class="form-control input-sm">
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add Merchant Account</button>
				</div>
			</form>

		</div>
		</div>
		
		
		
</spr:page>
