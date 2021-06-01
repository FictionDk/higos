package com.uplasma.oauth.higos.service;

import com.uplasma.oauth.higos.entity.User;
import com.uplasma.oauth.higos.mapper.ParamMap;
import com.uplasma.oauth.higos.mapper.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    private static Set<String> roles;
    private static Set<String> users;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("UserDetailsServerImpl.loadUserByUsername({});",s);
        List<User> users = userDao.queryList(ParamMap.buildParam("username",s));
        if(users==null || users.isEmpty()) throw new UsernameNotFoundException("用户名不存在");
        if(users.size() != 1) throw new RuntimeException("用户名异常");
        User u = users.get(0);
        u.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_adb,ROLE_ADMIN"));
        return u;
    }

    public List<User> getUsers(){
        return userDao.queryList(null);
    }
}
