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

	public void setMediaIdInSession(HttpServletRequest httpRequest, String id) {
		HttpSession session = getSession(httpRequest);
		session.setAttribute("mediaId", id);
	}

	public String getTargetPageFromSession(HttpServletRequest httpRequest) {
		HttpSession session = getSession(httpRequest);
		return "redirect:"+ session.getAttribute("targetPage");
	}

	public String getMediaIdFromSession(HttpServletRequest httpRequest) {
		HttpSession session = getSession(httpRequest);
		return "redirect:"+ session.getAttribute("mediaId");
	}

	private HttpSession getSession(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getSession();
	}
}
