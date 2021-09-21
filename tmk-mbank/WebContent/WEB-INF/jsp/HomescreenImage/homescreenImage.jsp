<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="../css/custom.css" rel="stylesheet">
<link href="../css/starter-template.css" rel="stylesheet">
<link href="/css/radinfo.css" rel="stylesheet"></link>
<spr:page header1="Home Screen Images">
	<div class="col-md-12">
		<div class="break"></div>
		<button type="button" class="btn btn-success btn-sm pull-left" ng-click="updateImage()" ng-show="edited">Save</button>
		<div id="alertDiv" class="col-md-10">
			<c:if test="${not empty message}">
				<div class="alert alert-success alert-dismissible" style="padding: 5px; margin-bottom: 0px;">
					<a href="#" class="close" data-dismiss="alert" aria-label="close" style="right: 5px;">&times;</a>
					<c:out value="${message}"></c:out>
				</div>
			</c:if>
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger alert-dismissible" style="padding: 5px; margin-bottom: 0px;">
					<a href="#" class="close" data-dismiss="alert" aria-label="close" style="right: 5px;">&times;</a>
					<c:out value="${errorMessage}"></c:out>
				</div>
			</c:if>
		</div>
		<button type="button" class="btn btn-info btn-sm pull-right" data-toggle="modal" data-target="#addImageModal">Add Image</button>
	</div>
	<div class="col-md-12" ng-init="getHomeScreenImages()">
		<div class="row" ui-sortable="homeScreenImageSortableOptions" ng-model="homeScreenImageList">
			<div class="col-xs-3" ng-repeat="homeScreenImage in homeScreenImageList">
				<div class="rad-info-box">
					<div class="row">
						<span style="float: left; margin-top: -19px; margin-left: -6px; color: #8d8b8b;">{{$index+1}}</span>

						<div class="col-xs-12" style="margin-bottom: 10px;">
							<img alt="Home Screen Image" src="/homescreen/images/{{homeScreenImage.image}}" style="width: 100%; height: 15%;">
						</div>
						<button class="btn btn-danger btn-xs btn-block" style="margin-bottom: -5px;" ng-click="deleteHomeScreenImageConfirm(homeScreenImage.id)">Remove</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="addImageModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Upload New Image for Home Screen</h4>
				</div>
				<form method="post" enctype="multipart/form-data" action="/homescreenimage/add" style="margin-bottom: 0px;">
					<div class="modal-body">
						<div class="file-upload" style="margin-bottom: 10px;">
							<div class="file-select">
								<div class="file-select-button" id="fileName">Choose File</div>
								<div class="file-select-name" id="noFile">No file chosen...</div>
								<input type="file" name="file" id="chooseFile">
							</div>
						</div>
						<span style="font-size:10px;float:left;">Please select a jpg image with resolution of 1024x500.</span>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-default pull-left" value="upload">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div id="deleteModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Delete Home Screen Image</h4>
				</div>
				<div class="modal-body">
					<span>Are you sure you want to remove this image?</span>
				</div>
				<div class="modal-footer">
					<a href="/homescreenimage/remove?image={{selectedId}}" class="btn btn-danger pull-left">Remove</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</spr:page>