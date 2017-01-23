package com.lli.mp.service;

import javax.servlet.http.HttpServletRequest;

public interface UserAuthService {
    void userLogin(String code, HttpServletRequest httpRequest) throws Exception;
}
