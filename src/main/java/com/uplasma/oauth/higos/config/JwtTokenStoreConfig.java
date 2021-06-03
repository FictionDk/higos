package com.uplasma.oauth.higos.config;

import com.uplasma.oauth.higos.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class JwtTokenStoreConfig {

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean("jwtAccessTokenConverter")
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("hi_gos");
        return converter;
    }

    @Bean("jwtTokenEnhancer")
    public TokenEnhancer tokenEnhancer(){
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                log.debug("jwt token enhancer: auth = {}",authentication);
                String id = null,username = null;
                if(authentication.getPrincipal() instanceof User){
                    User u = (User)authentication.getPrincipal();
                    id = String.valueOf(u.getId());
                    username = u.getUsername();
                }
                Map<String,Object> map = new HashMap<>();
                map.put("id",id);
                map.put("username",username);
                ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(map);
                return accessToken;
            }
        };
    }
}
