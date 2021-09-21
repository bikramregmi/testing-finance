<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="col-lg-12 col-md-12  slidesection machlist">
	<h4>Account Statement</h4>
</div>

<table id="btable" class="responstable" width="100%">
	<tbody>
		<c:choose>
			<c:when test="${fn:length(statementList) eq 0}">
				<div class="col-lg-12 col-md-12  slidesection machlist">NO
					TRANSACTION DONE TILL NOW!!</div>
			</c:when>
			<c:when test="${fn:length(statementList) gt 0}">
				<tr class="even">
					<th height="25"><span><b>S.N.</b></span></th>
					<th width="110">&nbsp;DATE AND TIME</th>
					<th width="200"><span><b>REMARKS</b></span></th>
					<th><span><b>CR</b></span></th>
					<th><span><b>DR</b></span></th>
					<th><span><b>Balance</b></span></th>
					
				</tr>

				<c:forEach var="statement" items="${statementList}" varStatus="loop">
					<tr class="odd">
						<td height="20"><b>${loop.index + 1}</b></td>
						<td align="left" width="110">&nbsp;${statement.date }</td>
						<td width="300">${statement.remarks }</td>
						<td><c:if test="${statement.toAccount eq myAccount}">${statement.amount }</c:if></td>
						<td><c:if test="${statement.fromAccount eq myAccount}">${statement.amount }</c:if></td>
						<td>
						<c:choose>
						<c:when test="${statement.toAccount eq myAccount}">${statement.toBalance }</c:when>
						<c:otherwise>${statement.fromBalance }</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</tbody>
</table>