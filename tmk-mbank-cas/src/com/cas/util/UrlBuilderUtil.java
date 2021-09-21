package com.cas.util;


public class UrlBuilderUtil {

	private static String webHost;

	public UrlBuilderUtil(String webHost) {
		UrlBuilderUtil.webHost = webHost;
	}

	public static String getWebHost() {
		return UrlBuilderUtil.webHost;
	}

	public static String getHomeURL() {
		return webHost;
	}

	public static String getActivationURL(String activationCode) {
		return webHost + "activate/p/" + activationCode;
	}

	public static String getAddEmailActivationURL(String activationCode) {
		return webHost + "activate/s/" + activationCode;
	}

	public static String getForgotPasswordURL(String recoveryCode) {
		return webHost + "forgotpassword/rl/" + recoveryCode;
	}

	public static String getUploadURL(String username) {
		return "/upload/" + username + "/";
	}

	public static String getDownloadLink(String username) {
		return "/getfile/" + username + "/";
	}
	

}
