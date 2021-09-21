<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<spr:page header1="Add Cardless Bank">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/cardlessbank/edit/save" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="id" value="${cardlessBank.id }">
				<div class="form-group">
					<label>Bank Name</label>
					<input type="text" name="bank" class="form-control input-sm" required="required" value="${cardlessBank.bank}">
					<h6>
						<font color="red">${error.bank}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Address</label>
					<input type="text" name="address" class="form-control input-sm" required="required" value="${cardlessBank.address}">
					<h6>
						<font color="red">${error.address}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Host</label>
					<input type="text" name="host" class="form-control input-sm" required="required" value="${cardlessBank.host}">
					<h6>
						<font color="red">${error.host}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Port</label>
					<input type="text" name="port" class="form-control input-sm" required="required" value="${cardlessBank.port}">
					<h6>
						<font color="red">${error.port}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>User Sign</label>
					<input type="text" name="userSign" class="form-control input-sm" required="required" value="${cardlessBank.userSign}">
					<h6>
						<font color="red">${error.userSign}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>User Password</label>
					<input type="text" name="userPassword" class="form-control input-sm" required="required" value="${cardlessBank.userPassword}">
					<h6>
						<font color="red">${error.userPassword}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Company Code</label>
					<input type="text" name="companyCode" class="form-control input-sm" required="required" value="${cardlessBank.companyCode}">
					<h6>
						<font color="red">${error.companyCode}</font>
					</h6>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Save Cardless Bank</button>
				</div>
			</form>

		</div>
	</div>
</spr:page>