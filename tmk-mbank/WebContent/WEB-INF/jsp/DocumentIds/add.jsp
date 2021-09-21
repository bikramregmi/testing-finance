<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spr:page header1="Set Document Requirements">
<div class="container-fluid container-main" id="main-container">
	<h4 class="col-md-4 col-md-offset-5">Add Document Info</h4>
	<div class="col-md-4 col-md-offset-4" id="form-sub-container">
	 <form action="${pageContext.request.contextPath}/documentids/add" method="post" modelAttribute="document" class="form-group">
		<div class="form-group">
		   <label for="documentType">Document Type</label>
		   <input type="text" name="documentType" id="documentType" class="form-control"/>
		   <p>${error.documentType}</p>
		</div>
	    <div class="checkbox checkbox-info">
	      <c:choose>
	       <c:when test="${document.issuedDate eq true}">
	         <label><input type="checkbox" name="issuedDateRequired" id="issuedDate" checked>Issued Date</label>
	       </c:when>
	       <c:otherwise>
	         <label><input type="checkbox" name="issuedDateRequired" id="issuedDate">Issued Date</label>
	       </c:otherwise>
	      </c:choose>
	    </div>
	    <div class="checkbox checkbox-info">
	      <c:choose>
	       <c:when test="${document.expiryDate eq true}">
	         <label><input type="checkbox" name="expiryDateRequired" id="expiryDate" checked>Expire Date</label>
	       </c:when>
	       <c:otherwise>
	         <label><input type="checkbox" name="expiryDateRequired" id="expiryDate">Expire Date</label>
	       </c:otherwise>
	      </c:choose>
	    </div>
	    <div class="checkbox checkbox-info">
	      <c:choose>
	       <c:when test="">
	        <label><input type="checkbox" name="issuedStateRequired" id="issuedState" checked>Issued State</label>
	       </c:when>
	       <c:otherwise>
	        <label><input type="checkbox" name="issuedStateRequired" id="issuedState">Issued State</label>
	       </c:otherwise>
	      </c:choose>
	    </div>
	    <div class="checkbox checkbox-info">
	    <c:choose>
	      <c:when test="${document.issuedState eq true}">
	       <label><input type="checkbox" name="issuedCityRequired" id="issuedCity" checked>Issued City</label>
	      </c:when>
	      <c:otherwise>
	       <label><input type="checkbox" name="issuedCityRequired" id="issuedCity">Issued City</label>
	      </c:otherwise>
	    </c:choose>
	    </div>
	    
	    <button type="submit" class="btn btn-block btn-primary">Add Document Ids</button>
	 </form>
   </div>
</div>
</spr:page>
