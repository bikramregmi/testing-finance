<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" >
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="/css/radinfo.css" rel="stylesheet"></link>
<link href="/css/pagination.css" rel="stylesheet"></link>
<spr:page header1="Balance Report">
	<div class="row tile_count">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="rad-info-box">
					<table id="transactionList" class="table table-striped table-bordered table-condensed" style="font-size: 12px !important;">
						<thead>
							<tr>
								<th>Bank</th>
								<th>Remaining Balance</th>
								<th>Credit Limit</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(bankList) gt 0}">
								<c:forEach var="bank" items="${bankList}">
									<tr>
										<td>${bank.swiftCode}</td>
										<td>${bank.remainingBalance}</td>
										<td>${bank.creditLimit}</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</spr:page>