<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/State/state.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/City/city.js"></script>

<spr:page header1="Your Document Details is here">
<div class="container-fluid container-main" id="main-container">
	<div class="break"></div>
	<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<a href="${pageContext.request.contextPath}/customer/document/add?customerId=${uuid}" class="btn btn-primary pull-right">+ ADD</a>
		<br><br><br>
	<table id="documentDetails" class="table table-div data-table">
	  <thead class="thead-inverse">
		 <tr>
			<th>Customer Name</th>
			<th>Document Name</th>
			<th>Document Number</th>
			<th>Issued Date</th>
			<th>Expire Date</th>
			<th>Issued State</th>
			<th>Issued City</th>
			<th>Action</th>
		 </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${customerKyc}" var="cKyc">
	    <tr>
	       <th>${cKyc.customer}</th>
	       <th>${cKyc.documentId}</th>
	       <th>${cKyc.documentNumber}</th>
	       <th>${cKyc.issuedDate}</th>
	       <th>${cKyc.expiryDate}</th>
	       <th>${cKyc.issuedState}</th>
	      <%--  <th><a href="viewDocument?customerDocumentId=${customerDocument.id}" class="btn btn-success btn-xs"><i class="fa fa-pencil"></i>View Doc</a></a> --%> 
	    </tr>
	    </c:forEach>
	  </tbody>
	</table>
</div>
</spr:page>
<script>
  $(document).ready(function(){
	  $("#documentDetails").DataTable({
          "pagingType":"full_numbers",
          "pageLength":"5",
          "lengthMenu":[[5,10,15,-1],[5,10,15,"All"]],
          "language":{
               "emptyTable":"No Document is available in the table"
              }
	   });
	  });
</script>