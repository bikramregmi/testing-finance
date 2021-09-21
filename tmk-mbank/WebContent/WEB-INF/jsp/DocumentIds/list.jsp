<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spr:page header1="List of Document Setting">
<div class="container-fluid container-main" style="background-color:white;">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
	  <div class="col-md-8 col-md-offset-2" style="margin-top:5%;">
		<table id="documentSetting" class="table table-striped">
			<thead>
				<tr>
					<th>Document Type</th>
					<th>Issued Date</th>
					<th>Expiry Date</th>
					<th>Issued State</th>
					<th>Issued City</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
			  <c:if test="${fn:length(documentsList) gt 0}">
				<c:forEach var="document" items="${documentsList}">
				  <tr>
					<td>${document.documentType}</td>
					<c:choose>
					   <c:when test="${document.issuedDateRequired eq true}">
					      <td>Yes</td>
					   </c:when>
					   <c:otherwise>
					      <td>No</td>
					   </c:otherwise>
					</c:choose>
					<c:choose>
					   <c:when test="${document.expiryDateRequired eq true}">
					      <td>Yes</td>
					   </c:when>
					   <c:otherwise>
					      <td>No</td>
					   </c:otherwise>
					</c:choose>
					<c:choose>
					   <c:when test="${document.issuedStateRequired eq true}">
					      <td>Yes</td>
					   </c:when>
					   <c:otherwise>
					      <td>No</td>
					   </c:otherwise>
					</c:choose>
					<c:choose>
					   <c:when test="${document.issuedCityRequired eq true}">
					      <td>Yes</td>
					   </c:when>
					   <c:otherwise>
					      <td>No</td>
					   </c:otherwise>
					</c:choose>
					<td>${document.status}</td>
					<td>
						<a href="${pageContext.request.contextPath}/docuemntids/edit?document=${document.id}"><i class="fa fa-pencil" 
								data-toggle="tooltip" data-placement="top" title="Edit Document Id"></i></a>&nbsp;
					</td>
				  </tr>
				</c:forEach>
			 </c:if>
			</tbody>
		</table>
	   </div>
   </div>
</spr:page>
<script>
  $(document).ready(function(){
	  $("#documentSetting").DataTable({
          "pagingType":"full_numbers",
          "pageLength":"5",
          "lengthMenu":[[5,10,15,-1],[5,10,15,"All"]],
          "language":{
               "emptyTable":"No City is available in the table"
              }
	   });
	  });
</script>