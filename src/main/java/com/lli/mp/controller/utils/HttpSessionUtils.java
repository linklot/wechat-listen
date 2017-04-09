package com.lli.mp.controller.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class HttpSessionUtils {
	public void setTargetPageInSession(HttpServletRequest httpRequest, String targetPage) {
		HttpSession session = getSession(httpRequest);
		session.setAttribute("targetPage", targetPage);
	}

	public String getTargetPageFromSession(HttpServletRequest httpRequest) {
		HttpSession session = getSession(httpRequest);
		return "redirect:"+ session.getAttribute("targetPage");
	}

	private HttpSession getSession(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getSession();
	}
}
