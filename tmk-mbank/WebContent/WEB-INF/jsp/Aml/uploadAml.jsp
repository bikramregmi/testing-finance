<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link rel="stylesheet" href="../css/jquery.dataTables.min.css">
</style>
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="Upload Aml">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>












		




		<table id="amlUpload" class="table table-striped ">
			<thead>

				<tr>
					<th>
						<div class="form-group">

							<select class="form-control input-sm" required="required">

								<c:forEach items="${amllists}" var="amltypelist">
									<option value="${amltypelist.name}">${amltypelist}</option>
								</c:forEach>

							</select>

						</div>
					</th>
				</tr>
			</thead>

			<tbody>

			<c:forEach items="${amllists}" var="amltypelist">
									
							

				<tr>
					<td>
						<h4>${amltypelist}</h4>
						<div class="amluplist form-group">
									
							<center>
								<h4>${amltypelist.name} only</h4>
								<p style="color: red;">
									<c:if test="${amltypelist eq error.source}">
									   <p class="erms">File miss Match. Upload proper ${amltypelist} ${amltypelist.name} file <p>
									  
									</c:if>
								</p>

							</center>
							<form method="post" action="uploadAml"
								enctype="multipart/form-data">

								<div class="file-upload">
									<div class="file-select">
										<div class="file-select-button" id="fileName">Choose
											File</div>
										<div class="file-select-name" id="noFile">No file
											chosen...</div>
										<input type="file" name="file" id="chooseFile">
										<input type="hidden" name="filetype" value="${amltypelist.name}" />
										<input type="hidden" name="filename" value="${amltypelist}" />
									</div>
									<br /> <input type="submit" class="btn  btn-default"
										style="margin-left: 0px;" value="upload">
								</div>
							</form>
						</div>
					</td>
				</tr>
				
				
					</c:forEach>
				
			</tbody>
		</table>
</spr:page>