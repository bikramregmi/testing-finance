<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spr:page header1="Add Notification Group">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form
				action="${pageContext.request.contextPath}/fcmServerSetting/add"
				method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">

					<label>Server Key</label> <input type="text" name="serverKey"
						value="${serverSettingDto.serverKey}"
						class="form-control input-sm" required>
					<h6 style="color: red;">${error.serverKey}</h6>
				</div>

				<div class="form-group">

					<label>Server ID</label> <input type="text" name="serverId"
						value="${serverSettingDto.serverId}" class="form-control input-sm"
						required>
					<h6 style="color: red;">${error.serverId}</h6>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						Server Setting</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>
