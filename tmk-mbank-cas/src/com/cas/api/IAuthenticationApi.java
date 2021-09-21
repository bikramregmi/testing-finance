package com.cas.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cas.model.UserDTO;

public interface IAuthenticationApi {

	boolean Authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException, Exception;


}
