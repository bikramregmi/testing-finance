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
<spr:page header1="List Service">
	<div class="row tile_count">
		<div class="row">
			<c:forEach var="services" items="${serviceDTO }">
				<c:if test="${services.webView}">
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
						<div class="rad-info-box rad-txt-primary">
							<a href="${pageContext.request.contextPath}/payment?serviceId=${services.uniqueIdentifier}">
								<img src="/mbank/serviceIcon/${services.icon}" class="icon"></img>
								<span class="heading"><b>${services.service}</b></span> <span
								class="value"><span>&nbsp;</span></span>
							</a>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
</spr:page>