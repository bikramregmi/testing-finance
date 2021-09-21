<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<%@page import="org.slf4j.Logger"%>
<%@page  import="org.slf4j.LoggerFactory"%>
<%@page  import="org.slf4j.Logger"%>

<spr:page header1="Edit Menu Template" >
<div class="col-md-12">
	<div class="row col-md-4">
		<div class="break"></div>
	<form  action="editMenuTemplate" modelAttribute="MenuTemplate" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
			<div class="form-group">
				<label>Menu Name</label>
				<input type="text" name="name" class="form-control input-sm" required="required" value="${menuTemplate.name}">
				<label class="control-label" for="inputError1">${error.name}</label>
				
			</div>
			<input type="hidden" name="id"  value="${menuTemplate.id}">
		
			
			
			
						<div class="form-group">
				<button class="btn btn-primary btn-md btn-block btncu">Edit Menu Template</button>
			</div>
		</form>
	</div>

</spr:page>	