package com.uplasma.oauth.higos.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String url;
    public MyAuthenticationSuccessHandler(String url){
        this.url = url;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        User u = (User) authentication.getPrincipal();
        log.debug("MyAuthenticationSuccessHandler.onAuthenticationSuccess.authentication.principal{}",u);
        httpServletResponse.sendRedirect(url);
    }
}
