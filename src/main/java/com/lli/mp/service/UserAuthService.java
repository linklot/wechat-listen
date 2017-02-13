package com.lli.mp.service;

import com.lli.mp.controller.model.UserUiModel;

import javax.servlet.http.HttpServletRequest;

public interface UserAuthService {
    void userLogin(String code, HttpServletRequest httpRequest) throws Exception;
    boolean isUserSignedIn(HttpServletRequest httpRequest);
    String getUserIdFromSession(HttpServletRequest httpRequest);
    UserUiModel getCurrentUser(HttpServletRequest httpRequest);
    boolean isUserSubscribed(HttpServletRequest httpRequest) throws Exception;
}
