<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="${pageContext.request.contextPath}/js/State/state.js"></script>
<script src="${pageContext.request.contextPath}/js/City/city.js"></script>
<spr:page header1="Customer Profile" >
<div class="container container-main">    
    <div class="navigationBar">
	    <nav class="navbar navbar-default" role="navigation">   
	        <div class="navbar-header">
	           <span class="navbar-brand" style="color:#042251;">Customer Summary</span>
	        </div>
	    </nav>
    </div>
    
    <div class="container-fluid" style="margin-top:0px;">
	    <div class="row" style="border:1px solid #b2b8c1;">
	       <div class="col-md-4" style="border-right:1px solid #b2b8c1;">
	       	<a href="${pageContext.request.contextPath}/online/profile/edit?user=${uuid}"><i class="fa fa-pencil" 
								data-toggle="tooltip" data-placement="top" title="Edit Customer"></i></a>
	          <div class="table" style="margin:10px;">
	             <table class="table table-striped">
	                <tbody>
	                  <tr>
	                    <td>Name</td>
	                    <td>${customer.fullName}</td>
	                  </tr>
	                  <tr>
	                  	<td>Address</td>
	                  	<td>${customer.addressOne}</td>
	                  </tr>
	                  <tr>
	                    <td>City</td>
	                    <td>${customer.city}</td>
	                  </tr>
	                  <tr>
	                     <td>State</td>
	                     <td>${customer.state}</td>
	                  </tr>
	                  <tr>
	                     <td>Contact</td>
	                     <td>${customer.mobileNumber}</td>
	                  </tr>
	                </tbody>
	             </table>
	          </div>  	          
	       </div>
<%-- 	       <div class="col-md-6">
	          <h2 align="center">Your Transaction Till Now</h2>
	          <div class="transactionAsSender">
	             <h4 style="border-bottom:1px solid #b2b8c1">Transaction Made As a Sender</h4>
	            <div class="trnxSender">
	             <table class="table table-striped">
	                <thead>
	                    <tr>
	                      <td>Receiver</td>
	                      <td>Sending Amount</td>
	                      <td>Receiving Amount</td>
	                      <td>Date</td>
	                    </tr>
	                </thead>
	                <tbody>
	                  <c:forEach items="${transactionAsSender}" var="asSender">
	                    <tr>
	                      <td>${asSender.beneficiary}</td>
	                      <td>${asSender.totalSendingAmount}</td>
	                      <td>${asSender.receivingAmount}</td>
	                      <td>${asSender.localCreated}</td>
	                    </tr>
	                  </c:forEach>
	                </tbody>
	             </table>
	            </div>
	          </div>
	          
	          <div class="transactionAsReceiver">
	             <h4 style="border-bottom:1px solid #b2b8c1">Transaction Made As a Receiver</h4>
	             <div class="trnxReceiver">
	                <table class="table table-striped">
	                   <thead>
	                      <tr>
	                        <td>Sender</td>
	                        <td>Receiving Amount</td>
	                        <td>Date</td>
	                      </tr>
	                   </thead>
	                   <tbody>
	                      <c:forEach items="${transactionAsReceiver}" var="asReceiver">
	                         <tr>
	                           <td>${asReceiver.sender}</td>
	                           <td>${asReceiver.receivingAmount}</td>
	                           <td>${asReceiver.localCreated}</td>
	                         </tr>
	                      </c:forEach>
	                   </tbody>
	                </table>
	             </div>
	          </div>
	       </div> --%>
	    </div>
	 </div>
    
</div>
</spr:page>