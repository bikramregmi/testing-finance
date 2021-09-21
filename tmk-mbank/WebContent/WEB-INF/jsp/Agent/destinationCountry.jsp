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

<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="Assign Destination Country">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<form action="addDestinationCountry" method="post"
			modelAttribute="multiSelectDTO">

			<input type="hidden" name="agentId" value="${agentId}">
			<div class="row">
				<div class="col-sm-5">
					<select class="form-control pickListSelect" id="pickData"
						name="pickData" multiple="">
						<c:forEach items="${unselectedCountryList}" var="element">
							<option id="${element.id}">${element.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2 pickListButtons">
					<p id="padd" class="btn btn-primary btn-sm btnfloat ">Add</p>
					<br />
					<p id="paddAll" class="btn btn-primary btn-sm btnfloat ">Add
						All</p>
					<br />
					<p id="premove" class="btn btn-primary btn-sm btnfloat">Remove</p>
					<br />
					<p id="premoveAll" class="btn btn-primary btn-sm btnfloat">Remove
						All</p>
				</div>
				<div class="col-sm-5">
					<select class="form-control pickListSelect" id="pickListResult"
						name="pickListResult" multiple="">
						<c:forEach items="${selectedCountryList}" var="element">
							<option id="${element.id}">${element.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<br /> <br /> <input type="hidden" id="ids" name="ids"
				value="<c:forEach items="${selectedCountryList}" var="element" varStatus="loop">
		${element.id}<c:if test="${!loop.last}">,</c:if> </c:forEach>" />
			<input type="submit" class="btn btn-primary" value="Save" />

		</form>
</spr:page>