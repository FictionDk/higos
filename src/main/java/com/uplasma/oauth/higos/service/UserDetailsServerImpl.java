package com.uplasma.oauth.higos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UserDetailsServerImpl implements UserDetailsService {

    private static Set<String> roles;
    private static Set<String> users;

    static {
        users = new HashSet<>();
        users.add("tom");
        users.add("guest");
        users.add("test");
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("UserDetailsServerImpl.loadUserByUsername({});",s);
        if(!users.contains(s)) throw new UsernameNotFoundException("用户名不存在");
        String pwd = passwordEncoder.encode("st1234");
        UserDetails u = null;
        if("test".equals(s))
            u = new User(s,pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_adb"));
        else
            u = new User(s,pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("normal,/index"));
        return u;
    }
}
