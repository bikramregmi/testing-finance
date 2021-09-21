<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">

<spr:page header1="Add Bank">
	<input type="hidden" ng-model="uploadUrl"
		ng-init="uploadUrl='${pageContext.request.contextPath}/bankBulkUpload'" />
	<button class="btn btn-primary" style="float: right;"
		data-toggle="modal" data-target="#bankUpload">Bulk Upload</button>
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/addBank"
				modelAttribute="bank" method="post" class="col-md-12"
				style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Bank Name</label> <input type="text" name="name"
						class="form-control input-sm" required="required"
						value="${bank.name}">
					<h6>
						<font color="red">${error.name}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Bank Address</label> <input type="text" name="address"
						class="form-control input-sm" required="required"
						value="${bank.address}">
					<h6>
						<font color="red">${error.address}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Bank Code</label> <input type="text" name="swiftCode"
						class="form-control input-sm" required="required"
						value="${bank.swiftCode}">
					<h6>
						<font color="red">${error.swiftCode}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Channel Partner</label> <select name="channelPartner"
						class="form-control input-sm" id="channelPartner">
						<c:if test="${fn:length(channelPartners) gt 0}">
							<option value="0" selected="selected" disabled="disabled">Select
								Channel Partner</option>
							<c:forEach var="channelPartner" items="${channelPartners}">
								<option value="${channelPartner.id}">${channelPartner.name}
									-- ${channelPartner.owner}</option>
							</c:forEach>
						</c:if>
					</select>
					<h6>
						<font color="red">${error.channelPartner}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>State</label> <select name="state"
						class="form-control input-sm" required="required" id="state">
						<c:if test="${fn:length(statesList) gt 0}">
							<option value="">Select State</option>
							<c:forEach var="state" items="${statesList}">
								<option value="${state.name}">${state.name}</option>
							</c:forEach>
						</c:if>
					</select>
					<h6>
						<font color="red">${error.state}</font>
					</h6>
				</div>
				
				<div class="form-group">
					<label>City</label> <select name="city"
						class="form-control input-sm" required="required" id="city">
					</select>
					<h6>
						<font color="red">${error.city}</font>
					</h6>
				</div>

				<div class="form-group">
					<label>Mobile Number</label> <input type="text" name="mobileNumber"
						id="mobileNumber" class="form-control input-sm"
						required="required" value="${bank.mobileNumber}">
					<h6>
						<font color="red">${error.mobileNumber}</font>
					</h6>
				</div>

				<div class="form-group">
					<label>Email Id</label> <input type="text" name="email" id="email"
						class="form-control input-sm" required="required" value="${bank.email}">
					<h6>
						<font color="red">${error.email}</font>
					</h6>
				</div>

				<div class="form-group">
					<label>SMS Count</label> <input type="text" name="smsCount"
						class="form-control input-sm" required="required" value="${bank.smsCount}">
					<h6>
						<font color="red">${error.smsCount}</font>
					</h6>
				</div>

				<div class="form-group">
					<label>License Count</label> <input type="text" name="licenseCount"
						class="form-control input-sm" required="required" value="${bank.licenseCount}">
					<h6>
						<font color="red">${error.licenseCount}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Expiry Date</label> <input type="text"
						name="licenseExpiryDate" id="expiryDate"
						class="form-control input-sm" required="required" value="${bank.licenseExpiryDate}">
					<h6>
						<font color="red">${error.licenseExpiryDate}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Select Merchant(s)</label> <select class="form-control"
						multiple="multiple" id="fromSelect">
						<c:if test="${fn:length(merchantList) gt 0}">
							<c:forEach var="merchant" items="${merchantList}">
								<option value="${merchant.id}">${merchant.name}</option>
							</c:forEach>
						</c:if>
					</select> <select name="merchants" class="form-control" multiple
						id="toSelect"></select>
				</div>
				<div class="form-group">
					<label>Bank Transfer Account</label> <input type="text"
						name="bankTranferAccount" class="form-control input-sm" required="required" value="${bank.bankTranferAccount}">
					<h6>
						<font color="red">${error.bankTranferAccount}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>ISO URL</label> <input type="text" name="isoUrl"
						class="form-control input-sm" required="required" value="${bank.isoUrl}">
					<h6>
						<font color="red">${error.isoUrl}</font>
					</h6>
				</div>

				<div class="form-group">
					<label>Port Number</label> <input type="text" name="portNumber"
						class="form-control input-sm" required="required" value="${bank.portNumber}">
					<h6>
						<font color="red">${error.portNumber}</font>
					</h6>
				</div>

				<div class="form-group">
					<label>Card Acceptor Terminal Identification</label> <input
						type="text" name="cardAcceptorTerminalIdentification"
						class="form-control input-sm" required="required" value="${bank.cardAcceptorTerminalIdentification}">
					<h6>
						<font color="red">${error.cardAcceptorTerminalIdentification}</font>
					</h6>
				</div>
				<div class="form-group">
					<label>Acquiring Institution Identification Code</label> <input
						type="text" name="acquiringInstitutionIdentificationCode"
						class="form-control input-sm" required="required" value="${bank.acquiringInstitutionIdentificationCode}">
					<h6>
						<font color="red">${error.acquiringInstitutionIdentificationCode}</font>
					</h6>
				</div>
				<%--<div class="form-group">--%>
					<%--<label>Merchant Type</label> <input type="text" name="merchantType"--%>
						<%--class="form-control input-sm" required="required" value="${bank.merchantType}">--%>
					<%--<h6>--%>
						<%--<font color="red">${error.merchantType}</font>--%>
					<%--</h6>--%>
				<%--</div>--%>
				
				<div class="form-group">
					<label>Desc 1</label>
					<input type="text" name="desc1" class="form-control input-sm" value="${bank.desc1}" required="required">
					<h6><font color="red">${error.desc1}</font></h6>
				</div>
				
				<div id="form-group " class="usertemp">
					<label>Role</label> <select name="userTemplate"
						class="form-control input-sm" id="userTemplate">
						<c:forEach var="userTemplate" items="${userTemplateList}">
							<option value="${userTemplate.userTemplateName}">${userTemplate.userTemplateName}</option>
						</c:forEach>
					</select>
					<h6>
						<font color="red">${error.role}</font>
					</h6>
				</div>

				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add
						Bank</button>
				</div>
			</form>

		</div>
	</div>

	<!-- Modal -->
	<div id=bankUpload class="modal fade" role="dialog" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Bulk Upload</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div id="page-wrapper">
						<div class="container-fluid">
							<form method="post" enctype="multipart/form-data">
								<div class="file-upload">
									<div class="file-select">
										<div class="file-select-button" id="fileName">Choose
											File</div>
										<div class="file-select-name" id="noFile">No file
											chosen...</div>
										<input type="file" name="file" file-model="fileModel"
											id="chooseFile">
									</div>
									<br /> <input type="submit"
										ng-click="uploadBulkFile(uploadUrl)" class="btn  btn-default"
										style="margin-left: 0px;" value="upload">
								</div>
							</form>

						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</spr:page>
<script>
	$(document).ready(function() {
		$(function() {
			$( "#expiryDate" ).datepicker();
		});

		$('#fromSelect').click(function() {  
			return !$('#fromSelect option:selected').remove().appendTo('#toSelect');  
		});  
		$('#toSelect').click(function() {  
			return !$('#toSelect option:selected').remove().appendTo('#fromSelect');  
		}); 
		$("#state").change(function() {
			var stateName = $("#state").find("option:selected").val();
			$("#city").find("option").remove();
			var option = '';
			var option = '<option value="'+0+'">Select City</option>'
			$.ajax({
				type : "GET",
				url : "/ajax/state/getCitiesByState?state=" + stateName,
				success : function(data) {
					$.each(data.citiesList,function(index,city) {
						option += '<option value="'+city.id + '">' + city.name + '</option>';
						$("#city").append(option);
						option = '';
					});
				}
			});
		});
	});
</script>
