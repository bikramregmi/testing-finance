<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spr:page header1="Add OAuth Client">
		<div class="well well-sm">
		<div class="row">
			<div class="col-md-4 col-md-offset-4 clientDetails">
				<form action="/oauth/registerclient" method="POST" class="form-group">
					<div class="form-group">
						<label for="redirectUrl">Redirect URL</label> <input type="text" name="redirect_uri" id="redirectUrl" class="form-control" />
						<h6 style="color: red;">${error.redirectUrl}</h6>
					</div>
					<div class="form-group">
						<label for="bank">Bank</label> <select name="bank" class="form-control">
							<option value="0">Select Bank</option>
							<c:if test="${fn:length(bankList) gt 0}">
								<c:forEach var="bank" items="${bankList}">
									<option value="${bank.id}">${bank.name}</option>
								</c:forEach>
							</c:if>
						</select>
						<h6 style="color: red;">${error.bank}</h6>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-success btn-block">Register</button>
					</div>

				</form>
				<div class="clientIdSecret">
					<h4>${messsage}</h4>
					<c:if test="${not empty oAuthClient.clientId}">
						<h4>Client ID : ${oAuthClient.clientId}</h4>
					</c:if>
					<c:if test="${not empty oAuthClient.clientSecret}">
						<h4>Client Secret : ${oAuthClient.clientSecret}</h4>
					</c:if>
				</div>
			</div>
			</div>
		</div>
</spr:page>

