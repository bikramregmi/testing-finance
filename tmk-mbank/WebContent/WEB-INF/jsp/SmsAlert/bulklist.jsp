<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spr:page header1="List Bulk SMS Alert">
	<div class="col-md-12">
		<div class="break"></div>
		<c:if test="${not empty message}">
			<div class="alert alert-success alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${message}</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>${errorMessage}</div>
		</c:if>
	</div>
	<div class="col-md-12">
		<table id="bulkList" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>Created Date</th>
					<th>Count</th>
					<th>Action</th>

				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(batchList) gt 0}">
					<c:forEach var="batch" items="${batchList}">
						<tr>
							<td>${batch.createdDate}</td>
							<td>${batch.smsCount}</td>
							<td><a href="/smsalert/bulk/detail?batch=${batch.id}"><i class="fa fa-arrow-right" data-toggle="tooltiip" data-placement="top"
									title="Edit Channel Partner"></i></a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</spr:page>
<script>
$(document).ready(function(){
	$('#bulkList').DataTable();
})
</script>