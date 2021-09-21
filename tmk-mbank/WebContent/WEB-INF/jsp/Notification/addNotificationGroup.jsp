<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spr:page header1="Add Notification Group">
	<div class="col-md-12">
		<div class="row col-md-4">
			<div class="break"></div>
			<form action="${pageContext.request.contextPath}/notificationGroup/add" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
						<c:if test="${userType eq 'Admin'}">
								<div class="form-group">
									<label>Bank</label> <select name="bankId"
										class="form-control input-sm" required="required"
										ng-model="$parent.bankId">
										<c:if test="${fn:length(bankList) gt 0}">
											<option selected disabled>Select Bank</option>
											<c:forEach var="bank" items="${bankList}">
												<option value="${bank.id}">${bank.name}</option>
											</c:forEach>
										</c:if>
									</select>
									<p class="error">
										<font color="red">${error.bank}</font>
									</p>
								</div>
							</c:if>
				
					<label>Notification Group Name</label>
					<input type="text" name="name" class="form-control input-sm" required value="${notificationGroup.name}">
					<h6 style="color:red;">${error.name}</h6>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add Notification Group</button>
				</div>
			</form>
		</div>
	</div>
</spr:page>
