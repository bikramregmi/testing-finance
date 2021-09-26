<!doctype html>
<!--[if lt IE 7]><html lang="en" class="no-js ie6"><![endif]-->
<!--[if IE 7]><html lang="en" class="no-js ie7"><![endif]-->
<!--[if IE 8]><html lang="en" class="no-js ie8"><![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->

<head>
    <meta charset="UTF-8">
    <title>Finance Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="shortcut icon" href="assets/favicon.png">

    <!-- Bootstrap 3.3.2 -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/slick.css">
    <link rel="stylesheet" href="assets/js/rs-plugin/css/settings.css">

    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/styles2.css">

    <script type="text/javascript" src="assets/js/modernizr.custom.32033.js"></script>

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
        //<![CDATA[
        var tlJsHost = ((window.location.protocol == "https:") ? "https://secure.comodo.com/"
            : "http://www.trustlogo.com/");
        document
            .write(unescape("%3Cscript src='"
                + tlJsHost
                + "trustlogo/javascript/trustlogo.js' type='text/javascript'%3E%3C/script%3E"));
        //]]>
    </script>
</head>

<body>
<%--<div class="pre-loader">
    <div class="load-con">
        <img src="assets/img/hand.png" class="animated fadeInDown" alt="">
        <div class="spinner">
            <div class="bounce1"></div>
            <div class="bounce2"></div>
            <div class="bounce3"></div>
        </div>
    </div>
</div>--%>

<header>

    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="fa fa-bars fa-lg"></span>
                </button>
                <a class="navbar-brand" href="/"> finance </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#about">Finance</a></li>
                    <li><a href="#features">features</a></li>
                    <li><a href="#reviews">Testimonials</a></li>
                    <!-- <li><a href="#screens">screens</a> -->
                    </li>
<%--                    <li><a href="#demo">demo</a></li>--%>

                    </li>
                    <li><a href="#support">Contact Us</a>
                    <li class="head-get"><a class="getApp" href="#getApp" style="margin-right: 20px;">get app</a>
                    <li class="head-login">
                        <form action="/login">
                            <button class="btn btn-warning">Log In</button>
                        </form>
                    </li>
                </ul>
                <div class="clearfix"></div>
            </div>
            <!-- /.navbar-collapse -->
            <div class="login navbar-center">
                <li><a class="getApp" href="#getApp" style="margin-right: 20px;">get app</a>
                <li>
                    <form action="/login">
                        <button class="btn btn-success">Log In</button>
                    </form>
                </li>
            </div>
        </div>
        <!-- /.container-->
    </nav>


    <!--RevSlider-->
    <div class="tp-banner-container">
        <div class="tp-banner">
            <ul>
                <!-- SLIDE  -->
                <li data-transition="fade" data-slotamount="7" data-masterspeed="1500">
                    <!-- MAIN IMAGE --> <img src="assets/img/transparent.png" alt="slidebg1" data-bgfit="cover"
                                             data-bgposition="left top" data-bgrepeat="no-repeat"> <!-- LAYERS -->
                    <!-- LAYER NR. 1 -->
                    <div class="tp-caption lfl fadeout hidden-xs" data-x="left" data-y="bottom" data-hoffset="-10"
                         data-voffset="0" data-speed="1000" data-start="700"
                         data-easing="Power4.easeOut">
<%--                        <img src="assets/img/hand.png" alt="">--%>
                    </div>

                    <div class="tp-caption lfl fadeout visible-xs" data-x="left" data-y="center" data-hoffset="700"
                         data-voffset="0" data-speed="500" data-start="700"
                         data-easing="Power4.easeOut">
<%--                        <img src="assets/img/freeze/iphone-freeze.png" alt="">--%>
                    </div>

                    <div class="tp-caption large_white_bold sft" data-x="550" data-y="center" data-hoffset="0"
                         data-voffset="-80" data-speed="500" data-start="1200"
                         data-easing="Power4.easeOut">
                        e-Finance For all <br>
                    </div>
                   <%-- <div class="tp-caption large_white_light sfr" data-x="770" data-y="center" data-hoffset="0"
                         data-voffset="-80" data-speed="500" data-start="1400"
                         data-easing="Power4.easeOut">For all
                    </div>--%>
                    <div class="tp-caption large_white_light sfb" data-x="550" data-y="center" data-hoffset="0"
                         data-voffset="0" data-speed="1000" data-start="1500"
                         data-easing="Power4.easeOut">Get Into It
                    </div>


                    <div class="tp-caption sfb hidden-xs" data-x="550" data-y="center" data-hoffset="0"
                         data-voffset="85" data-speed="1000" data-start="1700"
                         data-easing="Power4.easeOut">
                        <a href="#about" class="btn btn-primary inverse btn-lg">Explore Here</a>
                    </div>
                    <div class="tp-caption sfr hidden-xs" data-x="730" data-y="center" data-hoffset="0"
                         data-voffset="85" data-speed="1500" data-start="1900"
                         data-easing="Power4.easeOut">
                        <a href="#getApp" class="btn btn-default btn-lg">GET APP</a>
                    </div>

                </li>
                <!-- SLIDE 2 -->
                <li data-transition="zoomout" data-slotamount="7" data-masterspeed="1000">
                    <!-- MAIN IMAGE --> <img src="assets/img/transparent.png" alt="slidebg1" data-bgfit="cover"
                                             data-bgposition="left top" data-bgrepeat="no-repeat"> <!-- LAYERS -->
                    <!-- LAYER NR. 1 -->
                    <div class="tp-caption lfb fadeout hidden-xs" data-x="center" data-y="bottom" data-hoffset="-300"
                         data-voffset="80" data-speed="1000" data-start="700"
                         data-easing="Power4.easeOut">
<%--                        <img src="assets/img/hand-2.png" width="480" alt="">--%>
                    </div>


                    <div class="tp-caption mediumlarge_light_white sfl hidden-xs" data-x="left" data-y="center"
                         data-hoffset="580" data-voffset="0" data-speed="1000" data-start="1000"
                         data-easing="Power4.easeOut">

                        Dedicated For Our Customer<br>

                    </div>


                </li>


            </ul>
        </div>
    </div>


</header>


<div class="wrapper">


    <section id="about">
        <div class="container">

            <div class="section-heading scrollpoint sp-effect3">
                <h1>About Us</h1>
                <div class="divider"></div>
                <p>Finance is a broad term that describes activities associated with banking, leverage or debt, credit,
                    capital markets, money, and investments. ... Finance also encompasses the oversight, creation, and
                    study of money, banking, credit, investments, assets, and liabilities that make up financial
                    systems.Finance is a broad term that describes activities associated with banking, leverage or debt,
                    credit, capital markets, money, and investments. ... Finance also encompasses the oversight,
                    creation, and study of money, banking, credit, investments, assets, and liabilities that make up
                    financial systems.<>
            </div>

            <div class="row">
                <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="about-item scrollpoint sp-effect2">
                        <i class="fa fa-download fa-2x"></i>
                        <h3>Easy setup</h3>
                        <p>Easy setup with financial institutions via built-in middleware, ISO 8583 or any other
                            protocols</p>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="about-item scrollpoint sp-effect5">
                        <i class="fa fa-mobile fa-2x"></i>
                        <h3>Lowest TCO</h3>
                        <p>Being a completely managed service mBank's SaaS provides you the lowest TCO (total cost of
                            ownership) and the highest ROI on your IT investments.</p>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="about-item scrollpoint sp-effect5">
                        <i class="fa fa-users fa-2x"></i>
                        <h3>Full Featured</h3>
                        <p>mBank SaaS platform comes with an exhaustive set of features and plug-ins that ensure your
                            services are always ahead of the curve.</p>
                    </div>
                </div>
                <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="about-item scrollpoint sp-effect1">
                        <i class="fa fa-sliders fa-2x"></i>
                        <h3>Secured</h3>
                        <p>Data and transaction security are at the core of our services. Compliant to the latest
                            security guidelines we offer to you the most secure channel to reach
                            out to your customers.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section id="features">
        <div class="container">
            <div class="section-heading scrollpoint sp-effect3">
                <h1>Features</h1>
                <div class="divider"></div>
                <p>Learn more about this feature packed App</p>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-4 scrollpoint sp-effect1">
                    <div class="media  feature">
                        <a class="right-side" href="#"> <i class="fa fa-signal fa-2x" aria-hidden="true"></i>
                        </a>
                        <div class="media-body">
                            <h3 class="media-heading">Wi-Fi, GPRS &amp; SMS Enabled</h3>
                            App works on multiple channels.
                        </div>
                    </div>
                    <div class="media  feature">
                        <a class="pull-right" href="#"> <i class="fa fa-university fa-2x"></i>
                        </a>
                        <div class="media-body">
                            <h3 class="media-heading">Financial Services</h3>
                            Balance, Statement, Loan &amp; more
                        </div>
                    </div>
                    <div class="media  feature">
                        <a class="pull-right" href="#"> <i class="fa fa-credit-card-alt fa-2x" aria-hidden="true"></i>
                        </a>
                        <div class="media-body">
                            <h3 class="media-heading">Cardless ATM</h3>
                            Get cash without card.
                        </div>
                    </div>
                    <div class="media  feature">
                        <a class="pull-right" href="#"> <i class="fa fa-pie-chart fa-2x"></i>
                        </a>
                        <div class="media-body">
                            <h3 class="media-heading">Graphical Reporting</h3>
                            Dashboard &amp; graphical reports.
                        </div>
                    </div>
                    <div class="media  feature">
                        <a class="pull-right" href="#"> <i class="fa fa-star fa-2x"></i>
                        </a>
                        <div class="media-body">
                            <h3 class="media-heading">Your Favorite List</h3>
                            Customize your favorite transactions.
                        </div>
                    </div>
                </div>
                <div class="col-md-4 col-sm-4">
<%--                    <img src="assets/img/iphone-freeze.png" class="img-responsive scrollpoint sp-effect5" alt="">--%>
                </div>
                <div class="col-md-4 col-sm-4 scrollpoint sp-effect2">
                    <div class="media feature">
                        <a class="pull-left" href="#"> <i class="fa fa-mobile fa-2x" aria-hidden="true"></i>
                        </a>
                        <div class="media-body text-right">
                            <h3 class="media-heading ">Android &amp; iOS Platform</h3>
                            Android &amp; iOS apps avairightlable
                        </div>
                    </div>
                    <div class="media feature">
                        <a class="pull-left" href="#"> <i class="fa fa-usd fa-2x" aria-hidden="true"></i>
                        </a>
                        <div class="media-body text-right">
                            <h3 class="media-heading">Utility Payment</h3>
                            Online payment for Mobile, TV, Internet &amp; more
                        </div>
                    </div>
                    <div class="media feature">
                        <a class="pull-left" href="#"> <i class="fa fa-map-marker fa-2x"></i>
                        </a>
                        <div class="media-body text-right">
                            <h3 class="media-heading">Branches &amp; ATM</h3>
                            Locations of all branches &amp; ATM.
                        </div>
                    </div>
                    <div class="media feature">
                        <a class="pull-left" href="#"> <i class="fa fa-globe fa-2x"></i>
                        </a>
                        <div class="media-body text-right">
                            <h3 class="media-heading">Multi-Language UI</h3>
                            Lorem ipsum dolor sit amet.
                        </div>
                    </div>
                    <div class="media active feature">
                        <a class="pull-left" href="#"> <i class="fa fa-plus fa-2x"></i>
                        </a>
                        <div class="media-body text-right">
                            <h3 class="media-heading">And much more!</h3>
                            PIN Change, Channel Switch, Language &amp; more
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section id="reviews">
        <div class="container">
            <div class="section-heading inverse scrollpoint sp-effect3">
                <h1>What Our Client Says</h1>
                <div class="divider"></div>
                <p>Read What's The People Are Saying About Us</p>
            </div>
            <div class="row">
                <div class="col-md-10 col-md-push-1 scrollpoint sp-effect3">
                    <div class="review-filtering">
                        <div class="review">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="review-person">
                                        <img src="/assets/img/portfolio.png" alt="" class="img-responsive">
                                    </div>
                                </div>
                                <div class="col-md-10">
                                    <div class="review-comment">
                                        <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam animi asperiores aut consequatur distinctio dolorem, doloremque earum eveniet illo iusto minima, nemo nisi odio porro quam ratione temporibus tenetur voluptates!</h3>
                                        <p>
                                            - XXX
                                            Ltd. <span> <i class="fa fa-star fa-lg"></i> <i
                                                class="fa fa-star fa-lg"></i>
													<i class="fa fa-star fa-lg"></i> <i class="fa fa-star fa-lg"></i> <i
                                                    class="fa fa-star-o fa-lg"></i>
												</span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="review rollitin">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="review-person">
                                        <img src="assets/img/portfolio.png" alt="" class="img-responsive">
                                    </div>
                                </div>
                                <div class="col-md-10">
                                    <div class="review-comment">
                                        <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aperiam asperiores
                                            aspernatur consequuntur illo impedit perspiciatis quia rem similique sint
                                            voluptate. Ipsa, praesentium, vitae. Cupiditate earum eum ipsam, laboriosam
                                            magnam minima!>
                                            <p>
                                                Finance is a broad term that describes activities associated with
                                                banking, leverage or debt, credit, capital markets, money, and
                                                investments. ... Finance also encompasses the oversight, creation, and
                                                study of money, banking, credit, investments, assets, and liabilities
                                                that make up financial systems. <span> <i class="fa fa-star fa-lg"></i> <i
                                                    class="fa fa-star fa-lg"></i> <i
                                                    class="fa fa-star fa-lg"></i> <i
                                                    class="fa fa-star-half-o fa-lg"></i> <i
                                                    class="fa fa-star-o fa-lg"></i>
												</span>
                                            </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="review rollitin">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="review-person">
                                        <img src="assets/img/portfolio.png" alt="" class="img-responsive">
                                    </div>
                                </div>
                                <div class="col-md-10">
                                    <div class="review-comment">
                                        <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab consectetur
                                            doloribus dolorum enim est hic id inventore iure laboriosam magni maxime
                                            nobis pariatur, quasi rem sit temporibus vel vero voluptatibus.</h3>
                                        <p>
                                            Finance is a broad term that describes activities associated with banking,
                                            leverage or debt, credit, capital markets, money, and investments. ...
                                            Finance also encompasses the oversight, creation, and study of money,
                                            banking, credit, investments, assets, and liabilities that make up financial
                                            systems.d <span> <i class="fa fa-star fa-lg"></i> <i
                                                class="fa fa-star fa-lg"></i> <i
                                                class="fa fa-star fa-lg"></i> <i class="fa fa-star-half-o fa-lg"></i> <i
                                                class="fa fa-star-o fa-lg"></i>
												</span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="review rollitin">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="review-person">
                                        <img src="assets/img/portfolio.png" alt="" class="img-responsive">
                                    </div>
                                </div>
                                <div class="col-md-10">
                                    <div class="review-comment">
                                        <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab dicta, ducimus est ipsam magni numquam, porro recusandae rem sint, suscipit veniam voluptatibus? Beatae deleniti dicta dolores non placeat quam voluptatem.</h3>
                                        <p>
                                            - chandra p. <span> <i
                                                class="fa fa-star fa-lg"></i> <i class="fa fa-star fa-lg"></i> <i
                                                class="fa fa-star fa-lg"></i>
													<i class="fa fa-star-half-o fa-lg"></i> <i
                                                    class="fa fa-star-o fa-lg"></i>
												</span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="review rollitin">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="review-person">
                                        <img src="assets/img/portfolio.png" alt="" class="img-responsive">
                                    </div>
                                </div>
                                <div class="col-md-10">
                                    <div class="review-comment">
                                        <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Delectus nulla omnis sunt. Beatae blanditiis consequuntur deserunt dolore illo, impedit ipsam minima modi nesciunt quae quis sit sunt tenetur veniam voluptas!</h3>
                                        <p>
                                            - Hastinapur Multipurpose Cooperative Ltd. <span> <i
                                                class="fa fa-star fa-lg"></i> <i class="fa fa-star fa-lg"></i> <i
                                                class="fa fa-star fa-lg"></i> <i
                                                class="fa fa-star-half-o fa-lg"></i> <i class="fa fa-star-o fa-lg"></i>
												</span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- <section id="screens">
        <div class="container">

            <div class="section-heading scrollpoint sp-effect3">
                <h1>Screens</h1>
                <div class="divider"></div>
                <p>See what’s included in the App</p>
            </div>

            <div class="filter scrollpoint sp-effect3">
                <a href="javascript:void(0)" class="button js-filter-all active">All Screens</a>
                <a href="javascript:void(0)" class="button js-filter-one">User Access</a>
                <a href="javascript:void(0)" class="button js-filter-two">Social Network</a>
                <a href="javascript:void(0)" class="button js-filter-three">Media Players</a>
            </div>
            <div class="slider filtering scrollpoint sp-effect5" >
                <div class="one">
                    <img src="assets/img/freeze/screens/profile.jpg" alt="">
                    <h4>Profile Page</h4>
                </div>
                <div class="two">
                    <img src="assets/img/freeze/screens/menu.jpg" alt="">
                    <h4>Toggel Menu</h4>
                </div>
                <div class="three">
                    <img src="assets/img/freeze/screens/weather.jpg" alt="">
                    <h4>Weather Forcast</h4>
                </div>
                <div class="one">
                    <img src="assets/img/freeze/screens/signup.jpg" alt="">
                    <h4>Sign Up</h4>
                </div>
                <div class="one">
                    <img src="assets/img/freeze/screens/calendar.jpg" alt="">
                    <h4>Event Calendar</h4>
                </div>
                <div class="two">
                    <img src="assets/img/freeze/screens/options.jpg" alt="">
                    <h4>Some Options</h4>
                </div>
                <div class="three">
                    <img src="assets/img/freeze/screens/sales.jpg" alt="">
                    <h4>Sales Analysis</h4>
                </div>
            </div>
        </div>
    </section>
-->
   <%-- <section id="demo" class="doublediagonal">
        <div class="container">
            <div class="section-heading scrollpoint sp-effect3">
                <h1>Request Demo</h1>
                <div class="divider"></div>
                <p>For more info, contact us!</p>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-8 col-sm-8 col-md-offset-2 scrollpoint sp-effect1">
                            <form role="form">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Your name">
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="Your email">
                                </div>

                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="Your mobile">
                                </div>

                                <div class="form-group">
                                    <textarea cols="30" rows="10" class="form-control"
                                              placeholder="Your message"></textarea>
                                </div>
                                <button type="submit" class="btn btn-success btn-lg">Submit</button>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section>--%>

    <section id="getApp">
        <div class="container-fluid">
            <div class="section-heading inverse scrollpoint sp-effect3">
                <h1>Get App</h1>
                <div class="divider"></div>
                <p>Choose your native platform and get started!</p>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="hanging-phone scrollpoint sp-effect2 hidden-xs">
<%--                        <img src="assets/img/freeze/freeze-angled2.png" alt="">--%>
                    </div>
                    <div class="platforms">
                        <a href="https://play.google.com/store/apps/details?id=com.hamrotech.mbank" target="_blank"
                           class="btn btn-primary inverse scrollpoint sp-effect1"> <i
                                class="fa fa-android fa-3x pull-left"></i> <span>Download for</span><br> <b>Android</b>
                        </a> <a href="#" class="btn btn-primary inverse scrollpoint sp-effect2"> <i
                            class="fa fa-apple fa-3x pull-left"></i> <span>Download for</span><br> <b>Apple
                        IOS</b>
                    </a>
                    </div>

                </div>
            </div>


        </div>
    </section>

    <section id="support" class="doublediagonal">
        <div class="container">
            <div class="section-heading scrollpoint sp-effect3">
                <h1>Contact Us</h1>
                <div class="divider"></div>
                <p>For more info and support, contact us!</p>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="row">

                        <div class="col-md-4 col-sm-4 contact-details scrollpoint sp-effect2">
                            <div class="media">
                                <a class="pull-left" href="#"> <i class="fa fa-map-marker fa-2x"></i>
                                </a>
                                <div class="media-body">
                                    <h4 class="media-heading">Mid Baneshwor - 10, Kathmandu, Nepal</h4>
                                </div>
                            </div>
                            <div class="media">
                                <a class="pull-left" href="#"> <i class="fa fa-envelope fa-2x"></i>
                                </a>
                                <div class="media-body">
                                    <h4 class="media-heading">
                                        <a href="mailto:bikramregmi13@gmail.com">finance@gmail.com</a>
                                    </h4>

                                    <h4 class="media-heading">
                                        <a href="mailto:bikramregmi13@gmail.com">finance@gmail.com</a>
                                    </h4>


                                </div>
                            </div>
                            <div class="media">
                                <a class="pull-left" href="#"> <i class="fa fa-phone fa-2x"></i>
                                </a>
                                <div class="media-body">
                                    <h4 class="media-heading">+977-1-234567</h4>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-8">

                            <iframe
                                    src="https://www.google.com/maps/embed?pb=!1m10!1m8!1m3!1d1766.3549209975113!2d85.33747113685963!3d27.69536153305764!3m2!1i1024!2i768!4f13.1!5e0!3m2!1sen!2snp!4v1499681727264"
                                    width="100%" height="450" frameborder="0" style="border: 0"
                                    allowfullscreen></iframe>


                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <div class="container">
            <a href="#" class="scrollpoint sp-effect3"> <img src="assets/img/hand.png" width="100" alt=""
                                                             class="logo">
            </a>
            <div class="social">
                <a href="#" class="scrollpoint sp-effect3"><i class="fa fa-twitter fa-lg"></i></a> <a href="#"
                                                                                                      class="scrollpoint sp-effect3"><i
                    class="fa fa-google-plus fa-lg"></i></a>
                <a href="https://www.facebook.com/" target="_blank" class="scrollpoint sp-effect3"><i
                        class="fa fa-facebook fa-lg"></i></a>
            </div>
            <div class="rights">
                <p>&copy; Copyright 2021 e-finance Group. - e-finance Group</p>
                <!-- <p>Template by <a href="http://www.scoopthemes.com" target="_blank">ScoopThemes</a></p> -->
            </div>
        </div>
    </footer>


</div>
<script src="assets/js/jquery-1.11.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/slick.min.js"></script>
<script src="assets/js/placeholdem.min.js"></script>
<script src="assets/js/rs-plugin/js/jquery.themepunch.plugins.min.js"></script>
<script src="assets/js/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>
<script src="assets/js/waypoints.min.js"></script>
<script src="assets/js/scripts.js"></script>
<script>
    $(document).ready(function () {
        appMaster.preLoader();
    });
</script>
<div style="z-index: 1000; position: fixed; bottom: 0; right: 0">
    <script language="JavaScript" type="text/javascript">
        TrustLogo("https://mbank.com.np/images/comodo_secure_seal.png",
            "CL1", "none");
    </script>
    <a href="https://ssl.comodo.com/wildcard-ssl-certificates.php" id="comodoTL">Wildcard SSL</a>
</div>

<style>
    header .login li {
        list-style: none;
        display: inline-block;
    }

    header .login {
        display: none !important;
    }

    section#features .media.feature .right-side {
        float: right !important;
    }

    @media screen and (max-width: 991px) {
        header .navbar-default .navbar-brand {
            position: absolute;
            left: 8%;
        }

        header .navbar-default ul.navbar-nav {
            top: -17px;
            position: absolute;
            left: 38%;
        }

        header .navbar-default ul.navbar-nav li a {
            padding: 7px 8px;
            font-size: 13px !important;
        }

        .btn.btn-default {
            display: none !important;
        }

        .tp-caption.large_white_bold {
            font-size: 30px !important;
            left: 72% !important;

        }

        .tp-caption.large_white_light {
            font-size: 30px !important;
            left: 47% !important;
        }

    }

    @media screen and (max-width: 767px) {
        header .navbar-default ul.navbar-nav {
            position: static;
        }

        header .head-login {
            display: none !important;

        }

        header .login {
            display: block !important;
            text-align: center;
        }

        header .head-get {
            display: none !important;
        }

        header .login li a.getApp {
            color: #3c79b4;
            background: #ffffff;
            /* Old browsers */
            background: -moz-linear-gradient(180deg, #ffffff 0%, #e0e0e0 100%);
            /* FF3.6+ */
            background: -webkit-gradient(linear, left top, right bottom, color-stop(0%, #ffffff), color-stop(100%, #e0e0e0));
            /* Chrome,Safari4+ */
            background: -webkit-linear-gradient(180deg, #ffffff 0%, #e0e0e0 100%);
            /* Chrome10+,Safari5.1+ */
            background: -o-linear-gradient(180deg, #ffffff 0%, #e0e0e0 100%);
            /* Opera 11.10+ */
            background: -ms-linear-gradient(180deg, #ffffff 0%, #e0e0e0 100%);
            /* IE10+ */
            background: linear-gradient(180deg, #ffffff 0%, #e0e0e0 100%);
            /* W3C */
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#ffffff, endColorstr=#e0e0e0, GradientType=1);
            /* IE6-9 fallback on horizontal gradient */
            webkit-border-radius: 5em;
            -moz-border-radius: 5em;
            -ms-border-radius: 5em;
            -o-border-radius: 5em;
            border-radius: 5em;
            -webkit-transition: all 0.25s ease-in-out;
            -moz-transition: all 0.25s ease-in-out;
            -ms-transition: all 0.25s ease-in-out;
            -o-transition: all 0.25s ease-in-out;
            transition: all 0.25s ease-in-out;
            padding: 5px 17px;
        }

        header {
            position: relative !important;
        }

        header .login {
            position: absolute;
            top: 62%;
            left: 60%;
        }

        header .navbar-default.scrolled .login {
            top: 22%;
        }

        header .navbar-default .navbar-brand {
            position: absolute;
            left: 8%;
            top: 5%;
        }

        header .navbar-default .navbar-toggle {
            display: none;
        }

        header .navbar-default .navbar-collapse {
            margin-top: 70px;
            background: #336799;
        }

        .tp-simpleresponsive .tp-caption {
            left: 46px !important;
        }

        .tp-caption.large_white_bold {
            font-size: 30px !important;
            left: 37% !important;
            top: 55% !important;

        }

        .tp-caption.large_white_light {
            font-size: 30px !important;
            left: 37% !important;
        }

        .tp-caption .tp-simpleresponsive img {
            margin-top: 30px !important;
        }

        header .navbar-default .navbar-toggle {
            margin-top: 0px;
            margin-right: 60px;
            background: none;
            border: 2px solid #ffffff;
            color: #ffffff;
            -webkit-border-radius: 5em;
            -moz-border-radius: 5em;
            -ms-border-radius: 5em;
            -o-border-radius: 5em;
            border-radius: 5em;
        }

        header .navbar-default ul.navbar-nav {
            padding-top: 1px;
        }

        .navbar {
            min-height: 70px !important;
        }

    }

    @media screen and (max-width: 480px) {
        header .login {
            left: 69%;
        }

        header .navbar-default .navbar-toggle {
            margin-right: 0;
        }

        header .login li a.getApp {
            display: none;
        }

    }

    @media screen and (max-width: 375px) {
        header .login li a.getApp {
            display: none;
        }
    }

</style>
</body>

</html>