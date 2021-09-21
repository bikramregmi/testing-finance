<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<!-- <script src ="../js/jquery.min.js"></script> -->
<!-- <script src ="../js/bootstrap.js"></script> -->


<spr:page header1="Add Commission" >
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/bank/commission/add"
				method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				
				<div class="form-group" id="merchantDiv">
					<label>Merchant</label>
					${commission.merchant}
				</div>
				<div class="form-group" id="serviceSelect">
					<label>Service</label>
					${commission.service}
				</div>
				<c:if test="${commission.sameForAll eq true}">
					<c:forEach var="ca" items="${commissionAmount}">
						
					</c:forEach>	
				</c:if>
				<c:if test="${commission.sameForAll eq false}">
				
				</c:if>
				
	           <div class="bulkCommissionAmount">
	              <table id="commissionAmountList" class="table">
	                 <thead>
	                   <tr>
	                      <td>From Amount</td>
	                      <td>To Amount</td>
	                      <td>Commission Flat</td>
	                      <td>Commission Percentage</td>
	                      <td>Fee Amount Flat</td>
	                      <td>Fee Amount Percentage</td>
	                   </tr>
	                 </thead>
	                 <tbody>
	                 </tbody>
	              </table>
	           </div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						Commission</button>
				</div>
			</form>
		</div>
		</div>
</spr:page>
<style type="text/css">
 #serviceFrom {  
   width: 100px;  
   height: 80px;  
  }
  #serviceTo {
  	width: 100px;
  	height: 80px;
  }
</style>
<script
	src="${pageContext.request.contextPath}/resources/js/commission.js"></script>
<script>
	$(document).ready(function() {		
		init("${pageContext.request.contextPath}");
	});
</script>

<script>
         $(document).ready(function(){
        	 var rowCount = $('table#commissionAmountList tr:last').index();
        	 console.log("Last Row No : "+rowCount);

        	 $("#addCommissionAmount").click(function(){
                   createRow(rowCount);
                   rowCount=rowCount+1;
            	 });

        	 function createRow(num){
                    var row=$("<tr class='commission-row'>");
                    var cell=$("<td class='commission-col'>");
                    var fromAmount=createAmountInput("fromAmount",num);
                    cell.append(fromAmount);
                    row.append(cell);

                    var cell=$("<td class='commission-col'>");
                    var toAmount=createAmountInput("toAmount",num);
                    cell.append(toAmount);
                    row.append(cell);

                    var cell=$("<td class='commission-col'>");
                    var toAmount=createAmountInput("commissionFlat",num);
                    cell.append(toAmount);
                    row.append(cell);

                    var cell=$("<td class='commission-col'>");
                    var toAmount=createAmountInput("commissionPercentage",num);
                    cell.append(toAmount);
                    row.append(cell);

                    var cell=$("<td class='commission-col'>");
                    var commissionAmount=createAmountInput("feeFlat",num);
                    cell.append(commissionAmount);
                    row.append(cell);

                    var cell=$("<td class='commission-col'>");
                    var commissionAmount=createAmountInput("feePercentage",num);
                    cell.append(commissionAmount);
                    row.append(cell);
                    
                    $("#commissionAmountList").append(row);
            	 }

        	 function createAmountInput(name,num){
                     var inputElement=$("<input type='text' class='form-control input-sm' name='commissionAmounts["+num+"]."+name+"'/>");
                     return inputElement;
            	 }

             });
	</script>	
