<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spr:page header1="List Merchants">
	<div id="page-wrapper">
		<div class="container-fluid">
			<div class="row">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<span class="panel-title">${merchantService.service}</span>
					</div>
					<div class="panel-body">
						<div class="row data-list-title-container">
							<div class="col-md-1">ID</div>
							<div class="col-md-3">NAME</div>
							<div class="col-md-3">STATUS</div>
							<div class="col-md-4 text-right">Action</div>
						</div>
						<!-- DATA ROW -->
						<ul class="list-group" id="merchant-list">
						<c:if test="${fn:length(merchantManagerList) gt 0}">
							<c:forEach var="merchantManager" items="${merchantManagerList}" varStatus="loop" begin="0">
								<div class="row data-list-row">
									<div class="col-md-1">${loop.index + 1}</div>
									<div class="col-md-3 name">${merchantManager.merchantName }</div>
									<div class="col-md-3 status">${merchantManager.status }</div>
									<div class="col-md-4 text-right">
									
									<input type="radio" ng-click="multiServiceSelected(${merchantManager.merchantServiceId},${merchantManager.merchantId})" whatTocall="multiServiceSelected" style="float:left;" name="myprovider" value="${multiService.id}" 
										 myurl="/merchantpriority?serviceId=${merchantManager.merchantServiceId}&merchantId=${merchantManager.merchantId}"
										<c:if test="${merchantManager.selected}">
											checked='checked'
										</c:if> 
										/>
										<!-- <a href="#" class="nav-blue"><i class="fa fa-info"></i></a> -->
										<!-- <a href="#" data-toggle="modal" data-target="#editMultiService"
									 	class="nav-blue"><i class="fa fa-pencil"></i></a>
										<a href="#" class="nav-blue"><i class="fa fa-trash-o"></i></a> -->
										<a href="${pageContext.request.contextPath}/changestatus?serviceProviderId=${merchantManager.merchantId}&serviceId=${merchantManager.merchantServiceId}" class="nav-blue">Change Status</a>
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
