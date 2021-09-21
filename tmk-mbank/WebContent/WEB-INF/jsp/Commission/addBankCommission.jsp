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


<spr:page header1="Add Bank Commission" >
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<p class="error" > ${error.errorMessage }</p>
			<form:form commandName="commissionAmountForm" method="POST" action="${pageContext.request.contextPath}/bankCommissionAmount">
			
				<input type="hidden" value="${commission.bankId }" name ="bankId"/>
				
				<div class="form-group" id="merchantDiv">
					<label>Merchant</label>
					<select id="merchantSelect"  class="form-control input-sm" >	
						<option value="" selected="selected" disabled="disabled">${commission.merchantName}</option>
					</select>
				</div>
				<div class="form-group" id="serviceSelect" >
					<label>Service</label>
					<select id="merchantServiceSelect"  class="form-control input-sm" >	
						<option value="" selected="selected" disabled="disabled">${commission.serviceName}</option>
					</select>
				</div>
				
	           <div class="bulkCommissionAmount">
	              <table id="commissionAmountList" class="table table-striped">
	                 <thead>
	                   <tr>
	                      <th style="width: 100px;">Commission Flat</th>
	                      <th>Commission Percentage</th>
	                      <th>Fee Amount Flat</th>
	                      <th>Fee Amount Percentage</th>
	                      <th>Channel Partner Commission Flat</th>
	                      <th>Channel Partner Commission Percentage</th>
	                      <th>Operator Commission Flat</th>
	                      <th>Operator Commission Percentage</th>
	                       <th>Cash Back Percentage</th>
	                   </tr>
	                 </thead>
	                 <tbody>
	                 
               <c:forEach items="${commissionAmountList}" varStatus="i" var="commissionamount">
               <c:choose>
               <c:when test="${bankCommisssionEmpty eq 'true'}">
    <tr>
       <td><div class="form-group">
       <input class="form-control input-sm" name ="commissionAmounts[${i.index}].commissionFlat" value="0" placeholder = "Enter Commission Flat"/>
       </div></td>
       
       <td><div class="form-group">
       <input class="form-control input-sm" name ="commissionAmounts[${i.index}].commissionPercentage" value="0" placeholder = "Enter Commission Percentage"/>
       </div></td>
       <td><div class="form-group">
       <input class="form-control input-sm" name ="commissionAmounts[${i.index}].feeFlat" value="0" placeholder = "Enter Fee Flat"/>
       </div></td>
        <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].feePercentage" value="0" placeholder = "Enter Fee Percentage"/>
        </div></td>
        <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].channelPartnerCommissionFlat" value="0" placeholder = "Enter Commisssion Flat"/>
        </div></td>
         <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].channelPartnerCommissionPercentage" value="0" placeholder = "Enter Commisssion Percenatage"/>
        </div></td>
         <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].operatorCommissionFlat" value="0" placeholder = "Enter Commisssion Flat"/>
        </div></td>
        <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].operatorCommissionPercentage" value="0" placeholder = "Enter Commisssion Percentage"/>
        </div></td>
        <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].cashBack" value="0" placeholder = "Enter Cash Back Percentage"/>
        </div></td>
   		<td>
   			<input type="hidden" value="${commissionamount.id}" name="commissionAmounts[${i.index}].commissionAmountId"/>
   		</td>
    </tr>
    </c:when>
     <c:otherwise>
      <c:forEach items="${bankCommissionList}" var="bankCommission">
      <c:if test="${commissionamount.id eq bankCommission.commissionAmountId}">
   <tr>
       <td><div class="form-group">
       <input class="form-control input-sm" name ="commissionAmounts[${i.index}].commissionFlat" value="${bankCommission.commissionFlat}" placeholder = "Enter Commission Flat"/>
       </div></td>
       <td><div class="form-group">
       <input class="form-control input-sm" name ="commissionAmounts[${i.index}].commissionPercentage" value="${bankCommission.commissionPercentage}" placeholder = "Enter Commission Percentage"/>
       </div></td>
       <td><div class="form-group">
       <input class="form-control input-sm" name ="commissionAmounts[${i.index}].feeFlat" value="${bankCommission.feeFlat}" placeholder = "Enter Fee Flat"/>
       </div></td>
        <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].feePercentage" value="${bankCommission.feePercentage}" placeholder = "Enter Fee Percentage"/>
        </div></td>
        <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].channelPartnerCommissionFlat" value="${bankCommission.channelPartnerCommissionFlat}" placeholder = "Enter Commisssion Flat"/>
        </div></td>
         <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].channelPartnerCommissionPercentage" value="${bankCommission.channelPartnerCommissionPercentage}" placeholder = "Enter Commisssion Percenatage"/>
        </div></td>
         <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].operatorCommissionFlat" value="${bankCommission.operatorCommissionFlat}" placeholder = "Enter Commisssion Flat"/>
        </div></td>
        <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].operatorCommissionPercentage" value="${bankCommission.operatorCommissionPercentage}" placeholder = "Enter Commisssion Percentage"/>
        </div></td>
        <td><div class="form-group">
        <input class="form-control input-sm" name ="commissionAmounts[${i.index}].cashBack" value="${bankCommission.cashBack}" placeholder = "Enter Cash Back Percentage"/>
        </div></td>
   <td>
   <input type="hidden" value="${commissionamount.id}" name="commissionAmounts[${i.index}].commissionAmountId"/>
   </td>
    </tr>
    </c:if>
    </c:forEach>
    </c:otherwise>
    </c:choose>
</c:forEach>


	                 </tbody>
	              </table>
	           </div>
	           <div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						Commission</button>
				</div>
				</form:form>
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
<

	
