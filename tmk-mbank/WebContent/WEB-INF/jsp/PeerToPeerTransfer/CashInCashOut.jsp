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
						<h2>${cashType}</h2>
						<div class="col-lg-12">
						<input type="hidden"  ng-model="casType"  ng-init="casType='${cashType}'"   />
						<input type="hidden" ng-model="CICOfirst" value="false" />
						<h4>{{cncomessage}}</h4>
							<div class="well form-horizontal"  ng-hide="CICOfirst">

								
								<div class="form-group">
									<label class="col-md-4 control-label">Wallet ID: </label>
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input
												name="destinationEmail" ng-model="cicowalletId"
												class="form-control" type="text">
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label">Amount: </label>
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input name="cicoamount"
												ng-model="cicoamount" class="form-control" type="text">

										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label"></label>
									<div class="col-md-4">
										<button type="submit" class="btn btn-warning"
											ng-click="cncoOperation()">
											Submit <span class="glyphicon glyphicon-send"></span>
										</button>
									</div>
								</div>
							</div>
							
							<div class="well form-horizontal"  ng-show="CICOfirst">

								
								<input type="hidden" class="cid" ng-model="cid" />
								<div class="form-group">
									<label class="col-md-4 control-label">Confirmation Code: </label>
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input name="cicoCode"
												ng-model="cicoCode" class="form-control" type="text">

										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label"></label>
									<div class="col-md-4">
										<button type="submit" class="btn btn-warning"
											ng-click="cncoCodeOperation()">
											Transfer Money <span class="glyphicon glyphicon-send"></span>
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>

			<div class="clearfix"></div>
		</div>
	</div>

</sprcustomer:page>

