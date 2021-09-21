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
			
				<div class="form-group">
					<label>Message*</label>
					<textarea rows="4" cols="50" ng-model="message">
					</textarea>
				</div>

			<div class="form-group">
				<button class="btn btn-primary btn-md btn-block btncu" ng-click ="sendBulkSms()">SEND</button>
			</div>
		</form>
</div>

<table id="customerList" class="table table-striped" > 
		<thead>  
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Mobile No.</th>
				<th>Status</th>
				<th>Action</th>						
			</tr>  
		</thead>
		<tbody>   
			<c:if test="${fn:length(customerList) gt 0}">
				<c:forEach var="customer" items="${customerList}">
					<tr>
						<td>${customer.firstName}</td>
						<td>${customer.lastName}</td>
						<td>${customer.mobileNumber}</td>
						<td>${customer.status }</td>
						
						<td>
							<input type="checkbox" 
       ng-model="checkbox[$index]" 
       ng-change="pushOrRemoveCustomerId($index,${customer.id})"> 
						</td>
					<tr>
				  
				</c:forEach>
			</c:if>
		</tbody>  
	</table>  

</spr:page>