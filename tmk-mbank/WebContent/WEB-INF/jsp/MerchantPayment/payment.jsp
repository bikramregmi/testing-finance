<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sprcustomer" tagdir="/WEB-INF/tags/customer"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<sprcustomer:page header1="Home">
	 <div class="container-fluid container-main">
			<div class="row">
						
						<div class="col-lg-8 col-md-8 ">
							<div class="row">
								<div class="col-lg-12">
									<h2>PAYMENTS</h2>
									<div class="col-lg-12">
										<ul>
											<c:if test="${fn:length(paymentCatagory) gt 0}">
												<c:forEach var="paymentCatagory" items="${paymentCatagory}">
													<li><a href="/ServiceListByMethod?id=${paymentCatagory.id }">${paymentCatagory.name 	 }</a></li>
												</c:forEach>
											</c:if>
										</ul>

									</div>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
    </div>
    
</sprcustomer:page>

