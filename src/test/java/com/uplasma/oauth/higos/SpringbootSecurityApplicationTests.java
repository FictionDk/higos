package com.uplasma.oauth.higos;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootSecurityApplicationTests {

    @Test
    public void contextLoads() {
        PasswordEncoder pw = new BCryptPasswordEncoder();
        String encode = pw.encode("123");
        System.out.println(encode);
        boolean matches = pw.matches("123",encode);
    }

    @Test
    public void jwtTests(){
        Algorithm algorithm = Algorithm.HMAC256("hi_gos");
        String token = JWT.create().withClaim("user","Tom")
                .withIssuer("st").sign(algorithm);
        log.debug("token={}",token);
    }


}
