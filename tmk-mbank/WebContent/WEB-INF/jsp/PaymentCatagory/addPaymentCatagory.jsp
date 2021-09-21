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
			<form action="addPaymentCatagory" modelAttribute="paymentCatagory" method="post"
				class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				
				
				<div class="form-group">
					<label>PaymentCatagory Name</label> <input type="text" name="name"
						class="form-control input-sm" value="${paymentCatagory.name}">
					<p class="error">${error.name}</p>
				</div>
				
				
				
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add PaymentCatagory</button>
				</div>
			</form>
		</div>
</spr:page>

