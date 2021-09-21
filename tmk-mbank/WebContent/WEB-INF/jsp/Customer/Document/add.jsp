<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spr:page header1="Upload Your document here">
<div class="container-fluid container-main" id="main-container">
	<h4 class="col-md-4 col-md-offset-5">Add Your Document</h4>
	 <div class="col-md-4 col-md-offset-4" id="form-sub-container">
	 <form action="${pageContext.request.contextPath}/customer/document/add" method="post" modelAttribute="customerKyc" enctype="multipart/form-data" class="form-group">
	    <div class="form-group">
	       <label for="documentType">Document Type</label>
	       <select name="documentId" id="documentType" class="form-control">
	          <c:forEach items="${documentIds}" var="document">
	               <option value="${document.documentType}">${document.documentType}</option>
	          </c:forEach>
	       </select>
	       <p>${error.documentType}</p>
	    </div>
	    <div class="form-group">
	       <label for="issuedDate">Document Number</label>
	       <input type="text" name="documentNumber" class="form-control" value="${customerDocument.documentNumber}"/>
	       <p>${error.issuedDate}</p>
	    </div>
	    <div class="form-group">
	       <label for="issuedDate">Issued Date</label>
	       <input type="text" name="issuedDate" id="issuedDate" class="form-control" value="${customerDocument.issuedDate}"/>
	       <p>${error.issuedDate}</p>
	    </div>
	    <div class="form-group">
	       <label for="expiryDate">Expire Date</label>
	       <input type="text" name="expiryDate" id="expiryDate" class="form-control" value="${customerDocument.expiryDate}">
	       <p>${error.expiryDate}</p>
	    </div>
	    
	    <div class="form-group">
	       <label for="issuedState">Issued State</label>
	       <select name="issuedState" id="state" class="form-control" onchange="populateCity()">
	         <option value="0">---Select---</option>
	         <c:forEach items="${statesList}" var="state">
	           <option value="${state.name}">${state.name}</option>
	         </c:forEach>
	       </select>
	       <p>${error.issuedState}</p>
	    </div>
	    <div class="form-group">
	       <label for="issuedCity">Issued City</label>
	       <select name="issuedCity" id="city" class="form-control">
	       </select>
	       <!-- <input type="text" name="issuedCity" id="issuedCity" class="form-control"> -->
	       <p>${error.issuedCity}</p>
	    </div>
	    <div class="form-group">
	       <label for="multipartFile">Upload Document</label>
	       <input type="file" name="multiPartFile" id="multipartFile" class="btn btn-info btn-file form-control">
	       
	    </div>
	    <div class="form-group">
	    	<input type="hidden" name="customer" value="${uuid}">
	    </div>
	    <button class="btn btn-block btn-primary">Submit</button>
	 </form>
	</div>
</div>
</spr:page>

<script>
$(document).ready(function() {
	  $(function() {
		  $( "#expiryDate" ).datepicker();
	  });
	  
	  $(function() {
		  $( "#issuedDate" ).datepicker();
	  });
 
	$("#state").change(function() {
		var stateName = $("#state").find("option:selected").val();
		$("#city").find("option").remove();
		var option = '';
		var option = '<option value="'+0+'">Select City</option>'
		$.ajax({
			type : "GET",
			url : "/ajax/state/getCitiesByState?state="
					+ stateName,
			success : function(data) {
			$.each(
				data.citiesList,function(index,city) {
					option += '<option value="'+city.id + '">'
							+ city.name
							+ '</option>';
					$("#city").append(option);
					option = '';
				});
					}
		});
	});
});
</script>

