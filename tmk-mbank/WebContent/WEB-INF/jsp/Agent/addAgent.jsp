<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<script src ="../js/jquery.min.js"></script> 
<script src ="../js/bootstrap.js"></script>
<script src ="../js/custom.js"></script>
<script>
	$(document).ready(function(){
		$("#search").autocomplete({     
		      source : function(request, response) {
		           $.ajax({
		                url : "/getallcountries",
		                type : "GET",
		                data : {
		                       term : request.term
		                },
		                dataType : "json",
		                success : function(data) {
		                      response(data);
		                }
		         });
		      },
		      select: function( event, ui ) {
		         alert( ui.item.value );
		         // Your code
		         return false;
		      }
		});

	});

</script>
<spr:page header1="Add Agent">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addAgent" modelAttribute="agent" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">			
					<label>Super Agent</label> 
					<select	name="parentId" class="form-control input-sm" required="required" id="agentSuperAgent">
						<c:if test="${fn:length(superAgentList) gt 0}">
							<c:forEach var="superAgent" items="${superAgentList}">
								<c:choose>
									<c:when test="${superAgent.agencyName == agent.agencyName}">
										<option value="${superAgent.id}" selected>${superAgent.agencyName}</option>
									</c:when>
									<c:otherwise>
										<option value="${superAgent.id}">${superAgent.agencyName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select> 
					<p class="error">${error.name}</p>
				</div>
				<!-- <div class="form-group">
					<label>Is Online</label>
					<div class="form-group">
						<div class="form-group"><label>True</label><input type="radio" name="isOnline" value="true"/>
						<label>False</label><input type="radio" name="isOnline" value="false"/></div>
						<div class="form-group"><label>False</label></div><input type="radio" name="isOnine" value="false"/></div>
					</div>
				</div> -->
				
				<div class="form-group">
					<label>Agency Name</label> <input type="text" name="agencyName"
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
					<label> Agent Type</label> 
					<select name="agentType" class="form-control input-sm">
						<option value="PARENT_AGENT">Parent Agent</option>
						<option value="CHILD_AGENT">Child Agent </option>
						<option value="ALL">All</option>
						<p class="error">${error.agentType}</p>
					</select>
					<%-- <input type="text" name="agentType"
						class="form-control input-sm" required="required"
						value="${agent.agentType}">
					<p class="error">${error.agentType}</p> --%>
				</div>
				<div class="form-group">
					<label>Address </label> <input type="text" name="address"
						class="form-control input-sm" required="required"
						value="${agent.address}">
					<p class="error">${error.address}</p>
				</div>
					<div class="form-group">
					<label>Mobile No</label> <input type="text" name="mobileNo"
						class="form-control input-sm" required="required"
						value="${agent.mobileNo}">
					<p class="error">${error.mobileNo}</p>
				</div>
				<div class="form-group">
					<label>Landline</label> <input type="text" name="landline"
						class="form-control input-sm" required="required"
						value="${agent.landline}">
					<p class="error">${error.landline}</p>
				</div>
				
				 <div class="form-group">
					<label>City</label> 
					<select name="city"
						class="form-control input-sm" required="required">
						<c:if test="${fn:length(cityList) gt 0}">
						<c:forEach var="city" items="${cityList}">
								<c:choose>
									<c:when
										test="${city.name == agent.city.name}">
										<option value="${city.name}" selected>${city.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${city.name}">${city.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</c:if>
					</select> 
					<p class="error">${error.city}</p>
				</div>
				<div class="form-group">
					<label>Owner Address </label> <input type="text" name="ownerAddress"
						class="form-control input-sm" required="required"
						value="${agent.ownerAddress}">
					<p class="error">${error.ownerAddress}</p>
				</div>
				
				<div class="form-group">
					<label>Owner Name</label> <input type="text" name="ownerName"
						class="form-control input-sm" required="required"
						value="${agent.ownerName}">
					<p class="error">${error.ownerName}</p>
				</div>
				
				<div class="form-group">
					<label>TimeZone</label> <select name="timeZone" id="timeZone"
						class="form-control input-sm" required="required">
						<c:forEach items="${timeZoneList}" var="element">
							<option value="${element}">${element}</option>
						</c:forEach>
					</select>
					<p class="error">${error.timeZone}</p>
				</div>
				
			
				
				<%-- <div class="form-group">
					<table>
						<tr><td><label>Paying</label></td><td>&nbsp&nbsp&nbsp<input type="checkbox" name="paying" /></td>
						<td>&nbsp&nbsp&nbsp<label> Receiving </label></td><td >&nbsp&nbsp&nbsp<input type="checkbox" name="receiving" /></td></tr>
						
					</table>
					<!-- <input type="checkbox" name="receiving" value="True"/> -->
					<p class="error">${error.paying}</p>
				</div>
				 --%>
				 <div class="form-group">
					<label>Destination Country List</label>
					 <select multiple="multiple" name="multiSelectDTO" id="destinationCountryList"
						class="form-control input-sm" required="required">
						<c:forEach items="${destinationCountryList}" var="country">
							<option value="${country.name}" id="${country.name}">${country.name}</option>
						</c:forEach>
					</select>
					
					<!-- <div class="form-group ui-widget">
               			 <input type="text" id="search" name="search" class="search" />
        			</div> -->
					
				</div>
				
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add Agent</button>
				</div>
			</form>
		</div>
</spr:page>

