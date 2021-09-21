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


<spr:page header1="Edit Account">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/bank/editcardlessbankaccount" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="id" value="${cardlessBankAccount.id}">
				<div class="form-group">
					<label>Cardless Bank Account</label>
					<input type="text" name="accountNumber" class="form-control input-sm" value="${cardlessBankAccount.accountNumber}">
				</div>
				<div class="form-group">
					<label>Status</label>
					<select name="status" class="form-control input-sm">
						<c:choose>
							<c:when test="${cardlessBankAccount.status eq 'Active'}">
								<option value="Active" selected>Active</option>
								<option value="Inactive">Inactive</option>
							</c:when>
							<c:when test="${cardlessBankAccount.status eq 'Inactive'}">
								<option value="Active">Active</option>
								<option value="Inactive" selected>Inactive</option>
							</c:when>
						</c:choose>
					</select>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Save Cardless Bank Account</button>
				</div>
			</form>

		</div>
		</div>
		
		
		
</spr:page>
