<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spr:page header1="List Service">
	<div id="page-wrapper">
		<div class="container-fluid">
<input type="hidden" ng-model=" merchantId"
		ng-init=" merchantId='${ merchantId}'" />
			<div class="row">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<span class="panel-title">Add Service to ${merchantDto.name}
					</div>
					<div class="panel-body">
						<form action="addservicetomerchant" method="post"
							class="col-md-12"
							style="float: none; margin-left: auto; margin-right: auto;">
							<div id="form-group">
								<input type="hidden" name="merchantId"
									value="${merchantId }" /> <label>Service List</label> <select
									name="merchantServiceId" class="form-control input-sm">
									<c:if test="${fn:length(merchantServiceList) gt 0}">
										<c:forEach var="serviceList" items="${merchantServiceList}">
											<option value="${serviceList.id}" selected>${serviceList.service}</option>
										</c:forEach>
									</c:if>
								</select>
								<span class="text-danger">${error.userTemplate}</span>
							</div>
							<div class="form-group">
								<button class="btn btn-primary btn-md btn-block btncu">Add
									Service To Merchant</button>
							</div>
						</form>
						<div class="row data-list-title-container">
							<div class="col-md-1">ID</div>
							<div class="col-md-5">NAME</div>
							<div class="col-md-4">UNIQUE IDENTIFIER</div>
							<div class="col-md-2">ACTION</div>
						</div>
						<!-- DATA ROW -->
						<ul class="list-group" id="merchant-list">
							<c:if test="${fn:length(merchantServiceDtoList) gt 0}">
								<c:forEach var="serviceList"
									items="${merchantServiceDtoList}" varStatus="loop"
									begin="0">
									<div class="row data-list-row">
										<div class="col-md-1">${loop.index + 1}</div>
										<div class="col-md-5">${serviceList.service}</div>
										<div class="col-md-4">${serviceList.uniqueIdentifier}</div>
										<div  class="col-md-2 text-right">
										<a href="${pageContext.request.contextPath}/deleteservicefromserviceprovider?merchantId=${merchantId}&serviceId=${serviceList.id}"
										 class="nav-blue" <%-- ng-click="loadmerchantServiceId(${serviceList.id }) --%>">Remove</a>
												</div>
									</div>
								</c:forEach>
							</c:if>
						</ul>
						<!-- DATA ROW END -->
					</div>
				</div>
			</div>
		</div>
	</div>
</spr:page>