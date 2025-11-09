/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.margins.authentication;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpFilter;
import java.io.IOException;
/**
 *
 * @author HerbertSekpey
 */
@WebFilter("/app/*") // Apply to all URLs
public class AuthFilter extends HttpFilter{
    
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        
        HttpSession session = request.getSession(false); // don't create a new one
        Object user = (session != null) ? session.getAttribute("user") : null;

        String loginURI = request.getContextPath() + "/login.xhtml";

        boolean loggedIn = (user != null);
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + "/javax.faces.resource");

        if (loggedIn || loginRequest || resourceRequest) {
            // user is authenticated or it's the login/resource request
            chain.doFilter(request, response);
        } else {
            // redirect to login page
            response.sendRedirect(loginURI);
        }
    }
    
}
