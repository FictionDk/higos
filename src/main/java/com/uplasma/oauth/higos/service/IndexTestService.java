package com.uplasma.oauth.higos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Slf4j
@Service
public class IndexTestService {

    public boolean hasPermission(HttpServletRequest req, Authentication auth){
        log.debug("IndexTestService.has({},{})",req,auth);
        Object obj = auth.getPrincipal();
        if(obj instanceof UserDetails){
            UserDetails u = (UserDetails) obj;
            Collection<? extends GrantedAuthority> auths = u.getAuthorities();
            return auths.contains(new SimpleGrantedAuthority(req.getRequestURI()));
        }
        return false;
    }
}
