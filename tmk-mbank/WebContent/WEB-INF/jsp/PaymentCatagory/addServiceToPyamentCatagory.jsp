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
<spr:page header1="Service Catagory">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="addServiceTOPaymentCatagory" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				
				
				<div id="form-group">
			
					<input type="hidden" name="paymentCatagoryId" value="${paymentCatagoryId }" />
					<label>Service List</label> <select name="serviceId"  
							class="form-control input-sm">
							<c:if test="${fn:length(serviceList) gt 0}">
								<c:forEach var="serviceList" items="${serviceList}">
											<option value="${serviceList.id}" selected>${serviceList.name}</option>
								</c:forEach>
							</c:if>
						</select>
						<p class="error">${error.userTemplate}</p>
				</div>
				
				
				
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add Service To PaymentCatagory</button>
				</div>
			</form>
		</div>
		
		<div class="col-lg-12">
			<table id="userList" class="table table-striped" > 
		<thead>  
			<tr>
				<th>id</th>
				<th>name</th>
				<th>uniqueIdentifier</th>		
			</tr>  
		</thead>
		<tbody>   
			<c:if test="${fn:length(paymentCatagoryDTO.serviceDTO) gt 0}">
				<c:forEach var="serviceList" items="${paymentCatagoryDTO.serviceDTO}">
					<tr>
						<td>${serviceList.id}</td>
						<td>${serviceList.name}</td>
						<td>${serviceList.uniqueIdentifier}</td>
						
					
						
						
						
					</tr>
				</c:forEach>
			</c:if>
		</tbody>  
	</table>  
		</div>
		
</spr:page>

