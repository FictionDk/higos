package com.uplasma.oauth.higos.handler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);

        httpServletResponse.setHeader("Content-Type","application/json;charset=utf-8");
        Map<String,Object> result = new HashMap();
        result.put("status","error");
        result.put("message","权限不足,请联系管理员");
        String json = new ObjectMapper().writeValueAsString(result);
        log.debug("AccessDeniedHandler: {}",json);

        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
