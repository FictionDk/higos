package com.uplasma.oauth.higos.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private String url;
    public MyAuthenticationFailureHandler(String url){
        this.url = url;
    }
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        log.debug("MyAuthenticationFailureHandler.onAuthenticationFailure.AuthenticationException:{}",e.toString());
        httpServletResponse.sendRedirect(url);
    }
}
