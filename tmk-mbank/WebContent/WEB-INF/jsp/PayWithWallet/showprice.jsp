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
								<h2>PAY WITH WALLET</h2>
									<div class="col-lg-12">
										<%@include file="showpriceform.jsp"%>
									</div>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
						
						<div class="clearfix"></div>
					</div>
    </div>
    
</sprcustomer:page>

