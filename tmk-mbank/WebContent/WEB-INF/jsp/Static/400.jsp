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
	<h1>400</h1>
	<div class="container">
		<h3>Something went wrong.</h3>
		<hr>
		<p>Could not get the resource that you are attempting to access.</p>
		<p>Make sure the address is correct.</p>
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