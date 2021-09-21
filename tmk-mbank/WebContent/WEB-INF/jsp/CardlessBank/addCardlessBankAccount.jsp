<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spr:page header1="Add Cardless Account">
	<div class="col-md-12" ng-init="getCardlessbankToAdd('${bank.swiftCode}')">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/cardless/account/add/save" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<input type="hidden" name="bank" value="${bank.swiftCode}">
				<div class="form-group">
					<label>Cardless Bank</label>
					<input type="hidden" id="cardlessBankId" name="cardlessBankId">
					<select  class="form-control input-sm" ng-options="cardless as cardless.bank for cardless in cardlessBankList track by cardless.id"
						ng-model="selectedcardlessBank" ng-change="cardlessBankSelected(selectedcardlessBank)">
						<option value="">Select a cardless Bank</option>
					</select>
				</div>
				<div ng-show="cardlessSelected">
					<div class="form-group">
						<label>{{selectedCardlessBankName}} Account on ${bank.name}</label> <input type="text" name="accountNumber" class="form-control input-sm">
					</div>
					<div class="form-group">
						<label>${bank.name} Account on {{selectedCardlessBankName}}</label> <input type="text" name="bankAccountNo" class="form-control input-sm" required="required"
							value="${cardlessBank.bankAccountNo}">
						<h6>
							<font color="red">${error.bankAccountNo}</font>
						</h6>
					</div>
					<div class="form-group">
						<label>{{selectedCardlessBankName}} Account on {{selectedCardlessBankName}}</label> <input type="text" name="cardlessBankAccountNo" class="form-control input-sm"
							required="required" value="${cardlessBank.cardlessBankAccountNo}">
						<h6>
							<font color="red">${error.cardlessBankAccountNo}</font>
						</h6>
					</div>
					<div class="form-group">
						<label>Debit Their Ref</label> <input type="text" name="debitTheirRef" class="form-control input-sm" required="required" value="${cardlessBank.debitTheirRef}">
						<h6>
							<font color="red">${error.debitTheirRef}</font>
						</h6>
					</div>
					<div class="form-group">
						<label>ATM Bin Number</label> <input type="text" name="atmBinNo" class="form-control input-sm" required="required" value="${cardlessBank.atmBinNo}">
						<h6>
							<font color="red">${error.atmBinNo}</font>
						</h6>
					</div>
					<div class="form-group">
						<label>ATM Term Number</label> <input type="text" name="atmTermNo" class="form-control input-sm" required="required" value="${cardlessBank.atmTermNo}">
						<h6>
							<font color="red">${error.atmTermNo}</font>
						</h6>
					</div>
					<div class="form-group">
						<button class="btn btn-primary btn-md btn-block btncu">Add Cardless Bank Account</button>
					</div>
				</div>
			</form>

		</div>
	</div>
</spr:page>