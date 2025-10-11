package com.bk.config;

import jakarta.servlet.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class FilterOne implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("🔥 First Filter - Trước request");
        chain.doFilter(request, response);
        System.out.println("🔥 First Filter - Sau response");
    }
}
