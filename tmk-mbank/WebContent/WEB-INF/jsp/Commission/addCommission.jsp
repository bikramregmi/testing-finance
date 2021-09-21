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
		<div class="row">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/commission/add"
				method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
<%-- 				<div class="form-group">			
					<label>Bank</label>
					<select id="bankSelect"	name="bank" class="form-control input-sm" required="required">
						<option>Select Bank</option>
						<c:if test="${fn:length(bankList) gt 0}">
							<c:forEach var="bank" items="${bankList}">
								<option value="${bank.id}">${bank.name}</option>
								
							</c:forEach>
						</c:if>
					</select> 
					<p class="error">${error.bank}</p>
				</div> --%>
				<div class="col-md-4">
				<div class="form-group" id="merchantDiv">
					<label>Merchant</label>
					<select id="merchantSelect" name="merchant" class="form-control input-sm" required="required">	
						<option selected="selected" disabled="disabled">Select Merchant</option>
						<c:if test="${fn:length(merchantList) gt 0}">
							<c:forEach var="merchant" items="${merchantList}">
								<option value="${merchant.id}">${merchant.name}</option>
								
							</c:forEach>
						</c:if>
					</select>
				</div>
				<div class="form-group" id="serviceSelect">
					<label>Service</label>
					<select id="service" name="service" class="form-control input-sm" required="required">
						<option>Select Service</option>	
					</select>
				</div>
				<div class="form-group" id="serviceSelect">
					<label>Transaction Type</label>
					<select id="transactionType" name="transactionType" class="form-control input-sm" required="required">
						<option>Select Transaction Type</option>	
						<c:forEach var="transaction" items="${transactionType}">
							<option value="${transaction}">${transaction}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group" id="sameForAllDiv">
					<label>Same for all Amount Range</label>
					<input type="checkbox" name="sameForAll" id="sameForAll">
				</div>
				<div class="form-group" id="commissionTypeDiv">
					<label>Commission/Fee</label>
					<select id="commissionType" name="commissionType" class="form-control input-sm" required="required">
					<option value="" selected disabled>Select Commission/Fee</option>
					<c:if test="${fn:length(commissionType) gt 0}">
						<c:forEach var="commission" items="${commissionType}">
							<option value="${commission}">${commission}</option>
						</c:forEach>
					</c:if>
					</select>
				</div>
				<div  id="addCommissionAmountDiv" class="form-group">
	               <button type="button" class="btn btn-primary btn-block" id="addCommissionAmount" name="addCommissionAmount" value="add" >Add Commission Amount</button>				
	           </div>
	           </div>
	           <div id="first" class="bulkCommissionAmount">
	              <table id="commissionAmountList" class="table">
	                 <thead>
	                   <tr>
	                      <td>From Amount</td>
	                      <td>To Amount</td>
	                      <td id="commissionFlatTd">Commission Flat</td>
	                      <td id="commissionPercentageTd">Commission Percentage</td>
	                      <td id="feeFlatTd">Fee Amount Flat</td>
	                      <td id ="feePercentageTd">Fee Amount Percentage</td>
	                   </tr>
	                 </thead>
	                 <tbody>
	                 <tr class="commission-row">
	                 <td class="commission-col"><input type="text" class="form-control input-sm" name="commissionAmounts[0].fromAmount" value="0"></td>
	                 <td class="commission-col"><input type="text" class="form-control input-sm" name="commissionAmounts[0].toAmount" value="0"></td>
	                 <td class="commission-col"><input type="text" class="form-control input-sm" name="commissionAmounts[0].flat" value="0"></td>
	                 <td class="commission-col"><input type="text" class="form-control input-sm" name="commissionAmounts[0].percentage" value="0"></td>
	                 </tr>
	                 </tbody>
	              </table>
	           </div>
	           <div id="second" class="bulkCommissionAmount">
	              <table id="commissionAmountListSecond" class="table">
	                 <thead >
	                 	<tr></tr>
	                   <tr id="commissionTable">
	                      <td>Commission Flat</td>
	                      <td>Commission Percentage</td>
	                   </tr>
	                    <tr id="feeTable">
	                      <td>Fee Amount Flat</td>
	                      <td>Fee Amount Percentage</td>
	                   </tr>
	                 </thead>
	                 <tbody id="feeCommissionTable">
	                 	<tr>
	                   		<td>
	                   			<input type="text" class='form-control input-sm' name="commissionAmounts[0].flat" value="0"/>
	                   		</td>
	                   		<td>
	                   			<input type="text" class='form-control input-sm' name="commissionAmounts[0].percentage" value="0"/>
	                   		</td>
	                   </tr>
	                 </tbody>
	              </table>
	           </div>
	           <div class="col-md-12"></div>
	           <div class="col-md-4">
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						Commission</button>
				</div>
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