<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->

<spr:page header1="Add SMS Mode" >
<div class="col-md-12">
	<div class="row col-md-4">
		<div class="break"></div>
		<form  action="${pageContext.request.contextPath}/notice/edit" modelAttribute="notice" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
			<input type="hidden" readonly="readonly" name="id" value="${notice.id}">
			
			<div class="form-group">
				<label>Title*</label>
				<input type="text"  name="title" value = " ${notice.title}">
				</input>
			</div>
			
			<div class="form-group">
				<label>Notice*</label>
				<textarea rows="4" cols="50" name="notice"> ${notice.description}</textarea>
			</div>

			<div class="form-group">
				<button class="btn btn-primary btn-md btn-block btncu">Edit</button>
			</div>
		</form>
</div>
</spr:page>