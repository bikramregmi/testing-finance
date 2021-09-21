<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->
<%@page import="org.slf4j.Logger"%>
<%@page  import="org.slf4j.LoggerFactory"%>
<%@page  import="org.slf4j.Logger"%>

<spr:page header1="Edit Service" >
<div class="col-md-12">
	<div class="row col-md-4">
		<div class="break"></div>
	<form action="/merchant/service/edit" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
			<div class="form-group">
					<label>Service Name*</label>
					<input type="text" name="service" class="form-control input-sm" required="required" value="${service.service}">
					<p class="error">${error.service}</p>
				</div>
				
				<div class="form-group" >
					<label>Minimum Value *</label> <input type="text" name="minValue"
						class="form-control input-sm" placeholder=""
						value="${service.minValue}">
					<p class="error">${error.minValue}</p>
				</div>
				
				<div class="form-group" >
					<label>Maximum Value *</label>
					<input type="text" name="maxValue" class="form-control input-sm" value="${service.maxValue}">
					<p class="error">${error.maximumValue}</p>
				</div>
				
				<div class="form-group">
					<label>Price Input *</label> <input type="checkbox" id="priceInput" name="priceInput"
						onclick="oncheck()" value="true">
				</div>
				<div class="form-group" id="priceRangeDiv" hidden>
					<label>Price Range *</label> <input type="text" name="priceRange"
						class="form-control input-sm" placeholder="eg 10,20,30"
						value="${service.priceRange}">
					<p class="error">${error.priceRange}</p>
				</div>
				
				<div class="form-group"  >
					<label>Label Name *</label> <input type="text" name="labelName"
						class="form-control input-sm" required="required"
						value="${service.labelName}">
					<p class="error">${error.labelName}</p>
				</div>
					<div class="form-group" >
					<label>Fixed Label Size *</label> <input type="checkbox" name="fixedlabelSize"
						id="labelSize" onclick="onchecksize()" value="true">
				</div>
				<div class="form-group" id="fixedlabelSize" hidden>
					<label>Label Fixed Size *</label> <input type="text" name="labelSize"
						class="form-control input-sm" 
						value="${service.labelSize}">
					<p class="error">${error.labelSize}</p>
				</div>
				<div class="form-group" id="labelLength">
					<label>Label Min Length *</label> <input type="text" name="labelMinLength"
						class="form-control input-sm" 
						value="${service.labelMinLength}">
					<p class="error">${error.labelMinLength}</p>
				
					<label>Label Max Length *</label> <input type="text" name="labelMaxLength"
						class="form-control input-sm" 
						value="${service.labelMaxLength}">
					<p class="error">${error.labelMaxLength}</p>
				</div>
				
				<div class="form-group">
					<label>Label Sample *</label> <input type="text" name="labelSample"
						class="form-control input-sm" required="required"
						value="${service.labelSample}">
					<p class="error">${error.labelSample}</p>
				</div>
				
				<div class="form-group">
					<label>Label Prefix *</label> <input type="text" name="labelPrefix"
						class="form-control input-sm" placeholder="eg 10,20,30"
						value="${service.labelPrefix}">
					<p class="error">${error.labelPrefix}</p>
				</div>
				<div class="form-group">
					<label>Instructions *</label> <textarea rows="" cols=""  name="instructions"
						class="form-control input-sm" required="required" >${service.instructions}</textarea>
					<p class="error">${error.instructions}</p>
				</div>
				<div class="form-group">
					<label>Status *</label>
					<select  name="status" class="form-control input-sm" required="required" >
					<c:if test="${service.status eq 'Active'}">
						<option value="Active" selected>Active</option>
						<option value="Inactive">Inactive</option>
					</c:if>
					<c:if test="${service.status eq 'Inactive'}">
						<option value="Active">Active</option>
						<option value="Inactive" selected>Inactive</option>
					</c:if>
					</select>
					<p class="error">${error.instructions}</p>
				</div>
				<div class="form-group">
					<label>Web View *</label>
					<input type="checkbox" id="webView" name="webView" value="true">
					<p class="error"></p>
				</div>
			<input type="hidden" name="id"  value="${service.id}">
			<div class="form-group">
				<button class="btn btn-primary btn-md btn-block btncu">Edit Service</button>
			</div>
		</form>
	</div>
	</div>

</spr:page>	
<c:if test="${service.webView eq 'true'}">
<script>
	$('#webView').prop('checked', true);
</script>
</c:if>
<c:if test="${service.priceInput eq 'true'}">
<script>
$('#priceRangeDiv').show();
$('#priceInput').prop('checked', true);
</script>
</c:if>
<c:if test="${service.fixedlabelSize eq 'true'}">
<script>
$('#labelSize').prop('checked', true);
$('#fixedlabelSize').show();
$('#labelLength').hide();
</script>
</c:if>
<script type="text/javascript">
function oncheck(){
	if(document.getElementsByName('priceInput')[0].checked){
		$('#priceRangeDiv').show();
	}else{
		$('#priceRangeDiv').hide();
	}
}

function onchecksize(){
	if(document.getElementsByName('fixedlabelSize')[0].checked){
		$('#fixedlabelSize').show();
		$('#labelLength').hide();
	}else{
		$('#labelLength').show();
		$('#fixedlabelSize').hide();
	}
}
</script>
