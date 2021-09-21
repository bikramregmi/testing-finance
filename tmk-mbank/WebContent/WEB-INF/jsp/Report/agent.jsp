<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../../css/bootstrap.min.css" rel="stylesheet">
<link href="../../css/custom.css" rel="stylesheet">
<link href="../../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<spr:page header1="Agent Wise Report">
		<div class="break"></div>
		<form  action="agentReport" modelAttribute="menu" method="post" class="col-md-offset-4 col-md-4" style="float: none; margin-left: auto; margin-right: auto;">
			<div class="form-group">
				<label>Agent Name</label>
				<c:choose>
					<c:when test="${empty agentList}">
						<select name="id" class="form-control input-sm" readonly="readonly" required="required">
						</select>
					</c:when>
					<c:otherwise>
						<select name="id" class="form-control input-sm" required="required">
							<c:forEach var="agent" items="${agentList}">
								<c:choose>
									<c:when test="${search.id == agent.id}">
										<option value="${agent.id}" selected>${agent.name} | ${agent.mobileNo}</option>
									</c:when>
									<c:otherwise>
										<option value="${agent.id}">${agent.name} | ${agent.mobileNo}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
				<label class="control-label" for="inputError1">${error.id}</label>
			</div>
			<div class="form-group">
				<label>From</label>
				<input type="text" id="fromDate" name="fromDate" class="form-control input-sm" required="required" value="${search.fromDate}" >
				<label class="control-label" for="inputError1">${error.fromDate}</label>
			</div>
			<div class="form-group">
				<label>To</label>
				<input type="text" id="toDate" name="toDate" class="form-control input-sm" required="required" value="${search.toDate}">
				<label class="control-label" for="inputError1">${error.toDate}</label>
			</div>
			<div class="form-group">
				<button class="btn btn-primary btn-md btn-block btncu">Submit</button>
			</div>
		</form>
</div>
<script>
	$( "#fromDate" ).datepicker({
		dateFormat: 'yy-mm-dd'
	});
	$( "#toDate" ).datepicker({ 
		dateFormat: 'yy-mm-dd' 
	});
</script>
</spr:page>