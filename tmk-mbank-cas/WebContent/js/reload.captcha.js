function reloadCaptcha() {
	document.getElementById("captchaImage").src = "/captcha?"
			+ Math.floor(Math.random() * 100);
}