package com.bqt.newspaper.security.jwt;


import com.bqt.newspaper.exception.ErrorResponse;
import com.bqt.newspaper.exception.GlobalCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(GlobalCode.ERROR_PASSWORD, "Mật khẩu không chính xác");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(json);

    }
}