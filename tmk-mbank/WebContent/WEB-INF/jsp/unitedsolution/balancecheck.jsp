<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sprcustomer" tagdir="/WEB-INF/tags/customer"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<sprcustomer:page header1="Home">
	<div class="container-fluid container-main">
		<div class="row">

			<div class="col-lg-8 col-md-8 ">
				<div class="row">
					<div class="col-lg-12">
						<h2>${ServicesDTO.name }</h2>
							
						<input type="hidden" ng-init="balanceurl='unitedsolutionbalanceenquiry'"  ng-model="unitedsolutionbalanceenquiry" >
						<input type="hidden" name="serviceUnique" ng-init="generalserviceUnique='${ServicesDTO.uniqueIdentifier}'"  ng-model="generalserviceUnique">
							<p>Your balance is : {{generalMessage.responseDetail.Balance}}</p>
						<div class="col-lg-12">
							</div>
						</div>

					</div>
					<div class="clearfix"></div>
					<div class="form-group">
					<button ng-click="unitedSolutionBalancecheck(balanceurl)" class="btn btn-primary btn-md btn-block btncu">Check Balance</button>
				</div>
				</div>
			</div>
		</div>
	</div>

</sprcustomer:page>

