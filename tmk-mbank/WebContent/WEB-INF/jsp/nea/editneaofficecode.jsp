<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spr:page header1="Edit Nea Office">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="/officecode/edit" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="id" value="${officeCodeDto.id}">
				<div class="form-group">
					<label>Office Code</label> <input type="text" name="officeCode" class="form-control input-sm" value="${officeCodeDto.officeCode}">
					<h6 style="color: red;">${error.officeCode}</h6>
				</div>
				<div class="form-group">
					<label>Office</label> <input type="text" name="office" class="form-control input-sm" value="${officeCodeDto.office}">
					<h6 style="color: red;">${error.office}</h6>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Update Nea Office</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>