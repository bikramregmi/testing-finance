<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Edit Agent">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<c:if test="${not empty message}">
				<p class="bg-success">
					<c:out value="${message}"></c:out>
				</p>
			</c:if>
			<form action="editAgent" modelAttribute="agent" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Agency Name</label> <input type="hidden" name="id"
						value="${agent.id}"> <input type="text" name="agencyName"
						class="form-control input-sm" required="required"
						value="${agent.agencyName}">
					<p class="error">${error.agencyName}</p>
				</div>
				<div class="form-group">
					<label>Agent Code</label> <input type="text" name="agentCode"
						class="form-control input-sm" value="${agent.agentCode}">
					<p class="error">${error.agentCode}</p>
				</div>
				<div class="form-group">
					<label> Agent Type</label> <select name="agentType"
						class="form-control input-sm">
						<c:choose>
							<c:when test="${agent.agentType== 'parentAgent'}">
								<option value="PARENT_AGENT" selected>Parent Agent</option>
								<option value="CHILD_AGENT">Child Agent</option>
								<option value="ALL">All</option>
							</c:when>
							<c:when test="${agent.agentType== 'childAgent'}">
								<option value="PARENT_AGENT">Parent Agent</option>
								<option value="CHILD_AGENT" selected>Child Agent</option>
								<option value="ALL">All</option>
							</c:when>
							<c:otherwise>
								<option value="PARENT_AGENT">Parent Agent</option>
								<option value="CHILD_AGENT">Child Agent</option>
								<option value="ALL" selected>All</option>
							</c:otherwise>

						</c:choose>

						<p class="error">${error.agentType}</p>
					</select>

				</div>
				<div class="form-group">
					<label>Address </label> <input type="text" name="address"
						class="form-control input-sm" required="required"
						value="${agent.address}">
					<p class="error">${error.address}</p>
				</div>
				<div class="form-group">
					<label>Landline</label> <input type="text" name="landline"
						class="form-control input-sm" required="required"
						value="${agent.landline}">
					<p class="error">${error.landline}</p>
				</div>
				<div class="form-group">
					<label>Mobile No</label> <input type="text" name="mobileNo"
						class="form-control input-sm" required="required"
						value="${agent.mobileNo}">
					<p class="error">${error.mobileNo}</p>
				</div>
				

				<div class="form-group">
					<label>TimeZone</label> <select name="timeZone" id="timeZone"
						class="form-control input-sm" required="required">
						<c:forEach items="${timeZoneList}" var="element">

							<c:choose>
								<c:when test="${agent.timeZone == element}">
									<option value="${element}" selected>${element}</option>
								</c:when>
								<c:otherwise>
									<option value="${element}">${element}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					<p class="error">${error.timeZone}</p>
				</div>
				<div class="form-group">
					<table>
						<tr>
							<td><label>Paying</label></td>
							<td>&nbsp&nbsp&nbsp <c:choose>
									<c:when test="${agent.paying=='true'}">
										<input type="checkbox" name="paying" checked="checked" />
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="paying" />
									</c:otherwise>
								</c:choose>
							</td>
							<td>&nbsp&nbsp&nbsp<label> Receiving </label></td>
							<td>&nbsp&nbsp&nbsp
									<c:choose>
										<c:when test="${agent.receiving=='true'}">
											<input type="checkbox" name="receiving" checked="checked" />
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="receiving" />
										</c:otherwise>
									</c:choose>
									
							
							</td>
						</tr>

					</table>
					<!-- <input type="checkbox" name="receiving" value="True"/> -->
					<p class="error">${error.paying}</p>
				</div>
				<div class="form-group">
					<label>Destination Country List</label> <select multiple="multiple"
						name="multiSelectDTO" id="destinationCountryList"
						class="form-control input-sm" required="required">

						<c:forEach items="${agent.destinationCountryList}"
							var="destCountry">

							<option value="${destcountry.name}" id="${destCountry.name}"
								selected>${destCountry.name}</option>
						</c:forEach>
						<c:forEach items="${countryList}" var="country">
							<option value="${country}" id="${country}">${country}</option>
						</c:forEach>
					</select>
				</div>

				<div clas="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Edit
						Agent</button>
				</div>
			</form>
		</div>
</spr:page>