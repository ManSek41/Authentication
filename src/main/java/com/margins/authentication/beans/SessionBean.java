/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.margins.authentication.beans;

import com.margins.authentication.entity.UserEntity;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author HerbertSekpey
 */



@Named("sessionUser")
@SessionScoped
public class SessionBean implements Serializable{
    
    private UserEntity user;  
    
    
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    
    public String logout() throws IOException {
    
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    
    if (session != null) {
        user = null;
        session.invalidate(); // removes all session attributes, including "user"
        facesContext.getExternalContext().redirect("../login.xhtml");
         // Optional feedback message (appears on the login page)
        facesContext.addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO,
            "You have been logged out successfully",
            null));
        // Redirect to login page
    return "../login.xhtml?faces-redirect=true";
    }
   
    return null;
}

    
}
