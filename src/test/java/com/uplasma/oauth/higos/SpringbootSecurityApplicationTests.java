package com.uplasma.oauth.higos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

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

}
