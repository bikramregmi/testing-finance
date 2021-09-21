<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sprcustomer" tagdir="/WEB-INF/tags/customer"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<sprcustomer:page header1="Home">
	 <div class="container-fluid container-main">

      <div class="col-lg-4 block">
        <div class="block green">
          <div style="padding-top: 15px; padding-left: 15px;">
            <a href="#" class="block-link-sm"><img src="images/customer/icon-add.png" width="25" alt="" class="icon-padding-sm">Add Money</a></div>
            <div class="current-balance text-center"><span class="block-text-sm">NPR. </span>{{myBalance}}<span class="block-text-sm"> Available</span></div>
          </div>
        </div>
      <a href="#">  
      <div class="col-lg-4 block">
        <div class="block blue text-center" style="padding-top: 45px">
          <img src="/images/customer/icon-upgrade.png" width="130" alt=""><br><span class="block-text-hero">
          Upgrade to <br>TMK Wallet Pro</span>
        </div>
      </div>
      </a>
      <div class="">
        <div>
          <div class="swiper-container block orange col-lg-4 bloc">
          <div class="text-center" style="margin-top: -10px;"><span class="block-text-hero" style="line-height: 25pt">Favorite<br>Transactions</span></div>
        <div class="swiper-wrapper">
            

            <div class="swiper-slide">
              <a href="#">
                <img src="/images/customer/logo-ncell.png" width="165" alt=""><br><br>
              </a>
              <span class="fav-transaction-title">Ncell Prepaid Recharge Card</span><br>
              <span class="fav-transaction-amount">NRs. 500</span>
              
            </div>

            <div class="swiper-slide">
              <img src="/images/customer/logo-ntc.png" width="165" alt=""><br><br>
              <span class="fav-transaction-title">Namaste Prepaid Recharge Card</span><br>
              <span class="fav-transaction-amount">NRs. 200</span>
              <span>
            </div>

            <div class="swiper-slide">
              <img src="/images/customer/logo-subisu.png" width="165" alt=""><br><br>
              <span class="fav-transaction-title">Subisu Premium Package</span><br>
              <span class="fav-transaction-amount">NRs. 2032</span>
              <span>
            </div>
            
        </div>

        <!-- Add Pagination -->
        <!-- <div class="swiper-pagination"></div> -->

        <!-- Add Arrows -->
        <div class="swiper-button-prev"></div>
        <div class="swiper-button-next"></div>
    </div>
        </div>
      </div>
      <div class="col-lg-4 block"><div class="block pink">Balance</div></div>
      <div class="col-lg-4 block"><div class="block lime">Upgrade</div></div>
      <div class="col-lg-4 block"><div class="block grey"><img src="/images/customer/banner-app.png" alt=""></div></div>
    </div>
    
</sprcustomer:page>

