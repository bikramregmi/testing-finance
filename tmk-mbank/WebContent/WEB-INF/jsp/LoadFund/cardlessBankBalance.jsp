<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<spr:page header1="Cardless Bank Balance">
	<form class="well form-horizontal">
		<div class="currentBalance" data-ng-show="currentBalance!=''&&currentBalance!=undefined">
			Current Credit <span class="badge">{{currentBalance}}</span>
		</div>
		<h4></h4>
		<div class="form-group">
			<label class="col-md-4 control-label">Bank:</label>
			<div class="col-md-4 input GroupContainer">
				<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-university"></i></span> <select data-ng-model="bank" class="form-control" data-ng-disabled="${bankSelected}"
						data-ng-change="getCardlessBankBalance()">
						<c:choose>
							<c:when test="${bankSelected}">
								<option value="${bank.id}" data-ng-init="bank='${bank.id}'" data-ng-selected="true">${bank.name}</option>
							</c:when>
							<c:otherwise>
								<option selected disabled value="">Select Bank</option>
								<c:if test="${fn:length(banklist) gt 0}">
									<c:forEach items="${banklist}" var="bank">
										<option value="${bank.id}">${bank.name}--${bank.address}</option>
									</c:forEach>
								</c:if>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
				<h6 style="color: red;">{{bankError}}</h6>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label">Cardless Bank:</label>
			<div class="col-md-4 input GroupContainer">
				<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-university"></i></span> <select data-ng-model="cardlessBank" class="form-control" data-ng-change="getCardlessBankBalance()"
						data-ng-disabled="${bankSelected}">
						<c:choose>
							<c:when test="${bankSelected}">
								<option value="${cardless.id}" data-ng-init="cardlessBank='${cardless.id}'" data-ng-selected="true">${cardless.bank}</option>
							</c:when>
							<c:otherwise>
								<option selected disabled value="">Select Cardless Bank</option>
								<c:if test="${fn:length(cardlessBankList) gt 0}">
									<c:forEach items="${cardlessBankList}" var="cardless">
										<option value="${cardless.id}">${cardless.bank}</option>
									</c:forEach>
								</c:if>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
				<h6 style="color: red;">{{cardlessBankError}}</h6>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-4 control-label">Balance: </label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-money"></i></span> <input data-ng-model="amount" class="form-control" type="text" data-ng-change="getCardlessBankBalance()">
				</div>
				<h6 style="color: red;">{{amountError}}</h6>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-4">
				<button data-ng-click="loadCardlessBalance()" class="btn btn-warning">
					Load Balance <span class="glyphicon glyphicon-send"></span>
				</button>
			</div>
		</div>
	</form>
</spr:page>

<div id="successModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Success</h4>
			</div>
			<div class="modal-body">
				<h5>Balance Updated Successfully.</h5>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>

<div id="errorModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">
					<i style="color: red;" class="fa fa-exclamation-triangle"></i> Failed
				</h4>
			</div>
			<div class="modal-body">
				<h5 style="color: red;">Something Went Wrong. Please Try Again Later.</h5>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>
<c:if test="${bankSelected}">
<div data-ng-init="getCardlessBankBalance()"></div>
</c:if>
<script>
	$('#successModal').on('hidden.bs.modal', function() {
		location.reload();
	});
	$('#errorModal').on('hidden.bs.modal', function() {
		location.reload();
	})
</script>
<script src="../js/customer/controller.js"></script>
<style>
.currentBalance {
	background-color: white;
	float: right;
	border: 1px solid #ddd;
	border-radius: 10px;
	padding: 2px 5px 2px 5px;
}

.currentBalance span{
margin-bottom: 1px;
}
</style>
