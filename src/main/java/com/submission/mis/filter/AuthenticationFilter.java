package com.submission.mis.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        boolean isLoginPage = path.equals("/login");
        boolean isRegisterPage = path.equals("/register");
        boolean isStaticResource = path.startsWith("/static/");
        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
        
        if (isLoggedIn && (isLoginPage || isRegisterPage)) {
            // Redirect to appropriate dashboard if already logged in
            String userType = (String) session.getAttribute("userType");
            httpResponse.sendRedirect(httpRequest.getContextPath() + 
                    ("student".equals(userType) ? "/student/dashboard" : "/instructor/dashboard"));
        } else if (!isLoggedIn && !isLoginPage && !isRegisterPage && !isStaticResource) {
            // Redirect to login page if not logged in
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        } else {
            chain.doFilter(request, response);
        }
    }
} 