<html>
<head>
<meta content="width=device-width, initial-scale=1, maximum-scale=1" name="viewport">
<title>MBank</title>
<link rel="icon" href="/images/favicon.ico" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/static-error-page.css">
</head>
<body>
	<img src="/assets/img/logo-white.png" alt="MBank Logo">
	<h1>404</h1>
	<div class="container">
		<h3>The page could not be found or you don't have permission to view it.</h3>
		<hr>
		<p>The resource that you are attempting to access does not exist or you don't have the necessary permissions to view it.</p>
		<p>Make sure the address is correct and that the page hasn't moved.</p>
		<p>Please contact your administrator if you think this is a mistake.</p>
		<a href="javascript:history.back()" class="js-go-back go-back" style="display: inline;"><i class="fa fa-arrow-left"></i> Go back</a>
	</div>
	<script>
		(function() {
			var goBack = document.querySelector('.js-go-back');

			if (history.length > 1) {
				goBack.style.display = 'inline';
			}
		})();
	</script>
</body>
</html>
