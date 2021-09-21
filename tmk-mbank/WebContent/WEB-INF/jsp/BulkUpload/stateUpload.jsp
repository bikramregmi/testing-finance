<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spr:page header1="Upload Bulk">
<input type="hidden" ng-model="uploadUrl"
		ng-init="uploadUrl='${pageContext.request.contextPath}/bulkStateUpload'" />
	<div id="page-wrapper">
		<div class="container-fluid">

			<form method="post" enctype="multipart/form-data">

				<div class="file-upload">
					<div class="file-select">
						<div class="file-select-button" id="fileName">Choose File</div>
						<div class="file-select-name" id="noFile">No file chosen...</div>
						<input type="file" name="file" file-model="fileModel"
							id="chooseFile">
					</div>
					<br /> <input type="submit" ng-click="uploadBulkFile(uploadUrl)"
						class="btn  btn-default" style="margin-left: 0px;" value="upload">
				</div>
			</form>

		</div>
	</div>
</spr:page>
