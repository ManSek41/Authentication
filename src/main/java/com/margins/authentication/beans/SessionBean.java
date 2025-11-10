/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.margins.authentication.beans;

import com.margins.authentication.entity.ProjectEntity;
import com.margins.authentication.entity.UserEntity;
import com.margins.authentication.services.ProjectService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author HerbertSekpey
 */



@Named("sessionUser")
@SessionScoped
public class SessionBean implements Serializable{
   
    private UserEntity user;  
     @Inject
   ProjectService myproject;
    
    private List<ProjectEntity> projects;

    public ProjectService getMyproject() {
        return myproject;
    }

    public void setMyproject(ProjectService myproject) {
        this.myproject = myproject;
    }

    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }
    
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

      
    public String logout() throws IOException {
    
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    
    if (session != null) {
        user = null;
        session.invalidate(); // removes all session attributes, including "user"
        facesContext.getExternalContext().redirect("../index.xhtml");
         // Optional feedback message (appears on the login page)
        facesContext.addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO,
            "You have been logged out successfully",
            null));
        // Redirect to login page
    return "../index.xhtml?faces-redirect=true";
    }
   
    return null;
}
  

   
public List<ProjectEntity> getUserProjects(HttpSession session) {
    // FacesContext facesContext = FacesContext.getCurrentInstance();
    // HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    if(session != null) {
        UserEntity user1 = (UserEntity) session.getAttribute("user");
        if(user1 != null) {
            projects = myproject.viewProject(user1.getId());
            return projects;
        }
    }
    return null;
}
    
}
