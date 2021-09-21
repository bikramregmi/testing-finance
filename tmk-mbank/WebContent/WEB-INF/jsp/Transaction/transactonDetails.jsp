<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spr:page header1="Transaction Detail">

	<div id="page-wrapper">
		<div class="container-fluid" ng-init="showTransactionDetails(${transaction})">
			<div class="row">
				<div class="panel" style="border-color: #18964f;">
					<div class="panel-heading" style="border-color: #18964f; background-color: #18964f; color: white;">
						<span class="panel-title">TRANSACTION DETAIL</span> <span class="pull-right text-right"> <c:if test="${userType eq 'Admin'}">
								&nbsp;
						 	<a ng-if="transaction.transactionStatus=='Complete'" href="" ng-click="reverseTransaction(${transaction})" class="small" style="color: white;"><i
									class="fa fa-dot-circle-o"></i>&nbsp;Check Transaction</a>&nbsp;
							

							</c:if>
							<button ng-if="transaction.transactionStatus=='Complete'" ng-click="print('printDiv')" style="background-color: white; color: #18964f;">
								<i class="fa fa-print"></i> Print
							</button>
						</span>
					</div>
					<div class="panel-body">

						<div class="col-md-12 nogutter">

							<div class="col-md-12">
								<div id="transactionDetail_table" ng-model="td_table">
									<div class="col-md-12" style="color: #18964f">
										<h4>
											<strong>{{transaction.service}}</strong>
										</h4>
									</div>
									<div class="clearfix"></div>
									<br />

									<!-- topups , transfer by admin -->
									<div class="col-md-12">
										<div class="col-md-6 form-group" style="color: #18964f">
											Transaction Type : <span style="color: #212121">{{transaction.transactionType}}</span>
										</div>

										<div class="col-md-6 form-group" style="color: #18964f">
											Service : <span style="color: #212121">{{transaction.service }}</span>
										</div>

										<!-- <div class="col-md-6 form-group" style="color: #18964f">
										Channel : <span style="color: #212121">{{transaction.medium}}</span>
									</div> -->

										<div class="col-md-6 form-group" style="color: #18964f">
											Request Date : <span style="color: #212121">{{transaction.createdDate}}</span>
										</div>
										<div class="col-md-6 form-group" style="color: #18964f">
											Processed By : <span style="color: #212121">{{transaction.sourceOwnerName}}</span>
										</div>
										<div class="col-md-6 form-group" style="color: #18964f">
											Account Number : <span style="color: #212121">{{transaction.sourceAccount}}</span>
										</div>
										<div class="col-md-6 form-group" style="color: #18964f">
											Transaction Identifier : <span style="color: #212121">{{transaction.transactionIdentifier}}</span>
										</div>

										<div class="col-md-6 form-group" style="color: #18964f">
											Amount : <span style="color: #212121">{{transaction.amount | number:2}}</span>
										</div>

										<div class="col-md-6 form-group" style="color: #18964f">
											Transaction Status : <span style="color: #212121"> <span>{{transaction.transactionStatus}}</span>
											</span>
										</div>

										<div class="col-md-6 form-group" style="color: #18964f">
											Merchant RefStan : <span style="color: #212121"> <span>{{transaction.merchantRefstan}}</span>
											</span>
										</div>
										<div class="col-md-6 form-group" style="color: #18964f">
											ISO Response Code : <span style="color: #212121"> <span>{{transaction.isoCode}}</span>
											</span>
										</div>

										<div class="col-md-6 form-group" style="color: #18964f" ng-if="transaction.transactionStatus=='ReversalWithRefund'">
											Linked Transaction : <span style="color: #212121"> <span><a style="overflow: hidden; white-space: nowrap; color: blue;"
													href="${pageContext.request.contextPath}/transactiondetail?transactionId={{transaction.previousTranId}}">{{transaction.previousTranId}}</a></span>
											</span>
										</div>


										<!-- <div class="col-md-12 form-group" ng-show="transaction.transactionStatus == 'CANCELLED_WITH_REFUND' || transaction.transactionStatus == 'CANCELLED_WITHOUT_REFUND' || transaction.transactionStatus == 'AMBIGUOUS'"> -->
										<div class="col-md-12 form-group" ng-show="transaction.responseMessage != null">
											<span style="color: #18964f">Message :</span> <span style="color: #212121">{{transaction.responseMessage}}</span>
										</div>
										<c:if test="${userType eq 'Admin'}">
											<div class="col-md-12 form-group" ng-show="transaction.agencyCommission != null">
												<span style="color: #18964f">Agency Commission :</span> <span style="color: #212121">{{transaction.agencyCommission}}</span>
											</div>
										</c:if>
									</div>

								</div>

								<c:if test="${userType eq 'Admin'}">

									<div class="clearfix"></div>
									<br />
									<br />
									<div class="col-md-12" ng-if="transaction.transactionStatus=='Complete'" style="color: #18964f">
										<table class="table">
											<thead class="text-center">
												<tr>
													<th>Operator Commission</th>
													<th>Channel Partner Commission</th>
													<th>Bank Commission</th>
													<th>Total Commission</th>
												</tr>
											</thead>
											<tbody class="text-center">
												<tr>
													<td>{{transaction.operatorCommissionAmount }}</td>
													<td>{{transaction.channelPartnerCommissionAmount}}</td>
													<td>{{transaction.bankCommission}}</td>
													<td>{{transaction.totalCommission}}</td>
												</tr>
											</tbody>
										</table>
									</div>
								</c:if>

							</div>

						</div>
						<div class="clearfix"></div>

					</div>
				</div>
				<div class="panel" style="border-color: #18964f;">
					<div class="panel-heading" style="border-color: #18964f; background-color: #18964f; color: white;">
						<span class="panel-title">SETTLEMENT LOG</span>
					</div>
					<div class="panel-body">
						<div class="col-md-12 nogutter">
							<div class="col-md-12" style="color: #18964f">
								<table class="table">
									<thead>
										<tr>
											<th>Date</th>
											<th>Settlement Type</th>
											<th>Settlement Status</th>
											<th>Response Code</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="settlementLog" items="${settlementLogList}">
											<tr>
												<td>${settlementLog.settlementDate}</td>
												<td>${settlementLog.settlementType}</td>
												<td>${settlementLog.settlementStatus}</td>
												<td>${settlementLog.responseCode}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="confirmReversal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Reversal Confirmation</h4>
				</div>
				<div class="modal-body">
					<p>{{transactionmessage}}</p>
				</div>
				<div class="modal-footer">
					<a href="${pageContext.request.contextPath}/transaction/reverserTransaction?transactionIdentifier={{transactionId}}" type="button" class="btn btn-danger pull-left">Confirm</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				</div>
			</div>

		</div>
	</div>
	<div id="transactionComplete" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Status Completion</h4>
				</div>
				<div class="modal-body">
					<p>{{transactionmessage}}</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
				</div>
			</div>

		</div>
	</div>
	 <%@include file="print.jsp" %>
</spr:page>