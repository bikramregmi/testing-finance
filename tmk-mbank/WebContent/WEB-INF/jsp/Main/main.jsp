<%@ include file="/WEB-INF/jsp/layout/taglib.jsp" %>

    <html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
        <meta name="description" content="A Bootstrap based app landing page template">
        <meta name="author" content="">

        <title>Hamro Technologies Pvt. Ltd </title>

    </head>

    <body>

        <div class="col-md-6 col-md-offset-3 text-center logo-login" style="margin-bottom: 70px"><img src="images/logo-mbank.png" width="350" height="170" alt=""></div>

        <div class="container">
            <form:form name="f" class="form-signin" action="/j_spring_security_check" method="POST" autocomplete="on">

                    

                <h2 class="form-signin-heading text-white text-center">Please sign in</h2>

                <label for="recipient-name" class="sr-only">UserName:</label>
                <input type="text" name='j_username' required="required" autocomplete="on" autofocus="autofocus" tabindex="1" class="form-control" id="recipient-name">

                <label for="message-text" class="control-label sr-only">Password:</label>
                <input class="form-control" name='j_password' fh:formhistory="off" required="required" autocomplete="on" tabindex="2" data-val="true" data-val-required="*" id="Password" name="Password" placeholder="Enter Your Password Here" type="password">
 <c:if test="${not empty errormessage}">
                <div id="alert" class="alert alert-danger" role="alert">
                        <span class="fa fa-exclamation-triangle" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                        
                            <c:out value="${errormessage}" />
                         
                    </div>
                </c:if>
                <input class="btn btn-lg btn-inverse btn-dark-glass btn-block" type="submit" value="Submit" value="Login" tabindex="3">

            </form:form>
        </div>

        <footer class="login-footer text-center">
            
             <span class="copyright">© 2017  All Rights Reserved.</span>
    <a href="#" target="_blank" class="text-black text-sm">Powered by : Hamro Technologies Pvt. Ltd.</a>
  <p>  <span><img src="images/ht_logo.png" height="50" width="100" alt="" /></span></p>
        </footer>

    </body>
</html>
