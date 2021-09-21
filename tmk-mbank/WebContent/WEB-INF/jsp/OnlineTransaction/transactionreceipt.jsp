<!-- %@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%-->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">

    <linll rel="stylesheet" href="css/bootstrap.css">
    <style>
      @import url(http://fonts.googleapis.com/css?family=Bree+Serif);
      body, h1, h2, h3, h4, h5, h6{
      font-family: 'Bree Serif', serif;
      }
    </style>
  </head>
  <spr:page header1="Payout Transaction">
	<div class="col-md-12">
		<div class="row col-md-12">
			<div class="break"></div>
    <div class="container">
      <div class="row">
        <div class="col-xs-6 text-right">
          <h1>INVOICE</h1>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-6">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4>Sender Details </h4>
              <h6>Unique Number: ${transaction.uniqueNumber}</h6>
            </div>
            <div class="panel-body">
              
              <table class="table table-striped table-bordered table-condensed">
				<tr>
					<td>Remit Country</td>
					<td>${transaction.remitCountry}</td>
				</tr>
				<%-- <tr>
					<td>Agency Name</td>
					<td>${transaction.remitAgent}</td>
				</tr> --%>
				<tr>
					<td>Sender Name</td>
					<td>${transaction.sender}</td>
				</tr>
				         
               
               </table> 
            
            </div>
          </div>
        </div>
        <div class="col-xs-6  text-right">
          <div class="panel panel-default">
            <div class="panel-heading">
               <h4>Receiver Details </h4>
              <h6>MTCN: ${transaction.trackingNumber}</h6>
            </div>
            <div class="panel-body">
                   <table class="table table-striped table-bordered table-condensed">
				<tr>
					<td>Payout Country</td>
					<td>${transaction.payoutCountry}</td>
				</tr>
				<%-- <tr>
					<td>Payout Agency Name</td>
					<td>${transaction.payoutAgent}</td>
				</tr> --%>
				<tr>
					<td>Receiver Name</td>
					<td>${transaction.beneficiary}</td>
				</tr>
				            
               
               </table>
            </div>
          </div>
        </div>
      </div>
      <!-- / end client details section -->
       <div class="row">
        <div class="col-xs-6">
          <div class="panel panel-info">
            <div class="panel-heading">
              <h4>Payout Channel</h4>
              <h5>Agent</h5>
            </div>
           <!--  <div class="panel-body">
              <p>Your Name</p>
              <p>Bank Name</p>
              <p>SWIFT : --------</p>
              <p>Account Number : --------</p>
              <p>IBAN : --------</p>
            </div> -->
          </div>
        </div>
        </div>
      <table class="table table-striped table-bordered table-condensed-bordered">
        <thead>
          <tr>
          	<th>
              <h4>Total Sending money</h4>
            </th>
            <th>
              <h4>Total Receiving money</h4>
            </th>
            <th>
              <h4>Commission</h4>
            </th>
            <th>
              <h4>Discount</h4>
            </th>
            <th>
              <h4>ExchangeRate</h4>
            </th>
           
          </tr>
        </thead>
        <tbody>
          <tr>
           	<td>${transaction.sendingCurrency}.${transaction.totalSendingAmount}</td>
           	<td>${transaction.receivingCurrency}.${transaction.receivingAmount}</td>
            <td class="text-right">${transaction.transactionFee}</td>
            <td class="text-right">${transaction.discountAmount}</td>
            <td class="text-right">${transaction.exchangeRate}</td>
            
          </tr>
        </tbody>
      </table>
 
      <div class="row">
        <div class="col-xs-6">
          <div class="panel panel-info">
            <div class="panel-heading">
              <h4>Sender's Signature</h4>
            </div>
            <div class="panel-body">
              <!-- <p>Your Name</p>
              <p>Bank Name</p>
              <p>SWIFT : --------</p>
              <p>Account Number : --------</p>
              <p>IBAN : --------</p> -->
            </div>
          </div>
        </div>
        <!-- <div class="col-xs-3"><div class="span3"/></div> -->
        <div class="col-xs-6">
          <div class="span6">
            <div class="panel panel-info">
              <div class="panel-heading">
                <h4>Receiver's Signature</h4>
              </div>
              <div class="panel-body">
                <!-- <p>
                  Email : you@example.com <br><br>
                  Mobile : -------- <br> <br>
                  Twitter : <a href="https://twitter.com/tahirtaous">@TahirTaous</a>
                </p>
                <h4>Payment should be made by Bank Transfer</h4> -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>
    	<form action="${pageContext.request.contextPath}/online/transaction/receipt" method="post">
    	<div class="form-group">
			<button class="btn btn-primary btn-md btn-block btncu">
				Done
			</button>
		</div> 
		</form>
    </div>
    </spr:page>
