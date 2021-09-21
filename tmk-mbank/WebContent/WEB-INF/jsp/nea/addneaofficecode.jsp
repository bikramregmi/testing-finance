<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spr:page header1="Add Nea Office">
	<div class="col-md-12">
		<button class="btn btn-primary pull-right btn-sm" data-toggle="modal" data-target="#officeCodeUploadModal">Bulk Upload</button>
		<div class="row col-md-4">
			<c:if test="${not empty message}">
				<p style="color: red;">
					sdashjgdhja asdjhgashdg
					<c:out value="${message}"></c:out>
				</p>
			</c:if>
			<div class="break"></div>
			<form action="/officecode/add" method="post" class="col-md-12" style="float: none; margin-left: auto; margin-right: auto;">
				<div class="form-group">
					<label>Office Code</label> <input type="text" name="officeCode" class="form-control input-sm" value="${officeCodeDto.officeCode}">
					<h6 style="color: red;">${error.officeCode}</h6>
				</div>
				<div class="form-group">
					<label>Office</label> <input type="text" name="office" class="form-control input-sm" value="${officeCodeDto.office}">
					<h6 style="color: red;">${error.office}</h6>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-md btn-block btncu">Add Nea Office</button>
				</div>
			</form>
		</div>
	</div>

	<div id="officeCodeUploadModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Upload Office Code</h4>
				</div>
				<form action="/officecode/add/bulk" method="POSt" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<input type="file" name="officeCodeFile">
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">Upload Format</div>
							<div class="panel-body">
								<img src="/images/officeCodeBulkExample.png" width="100%"/>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="pull-left btn btn-primary">Submit</button>
						<a type="button" class="btn btn-default" data-dismiss="modal">Close</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</spr:page>