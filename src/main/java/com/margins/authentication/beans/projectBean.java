/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.margins.authentication.beans;

import com.margins.authentication.entity.ProjectEntity;
import com.margins.authentication.entity.UserEntity;
import com.margins.authentication.services.ProjectService;
import com.margins.authentication.services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
// import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author HerbertSekpey
 */


@Named("projectBean")
 @RequestScoped
public class projectBean {
    
    private ProjectEntity project =new ProjectEntity();
   
   @Inject
   ProjectService myproject;
  
   @Inject
   private SessionBean myUser;
   
    
   private List<ProjectEntity> projects;

    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }

    public SessionBean getMyUser() {
        return myUser;
    }

    public void setMyUser(SessionBean myUser) {
        this.myUser = myUser;
    }

   
    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }
    
    
    
    
   
    public ProjectService getMyproject(){
    return myproject;
    }

    
    
    public void setMyproject(ProjectService myproject) {
        this.myproject = myproject;
    }
    
//
    public String createProject() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if(session != null){
            UserEntity user = (UserEntity) session.getAttribute("user");
           
            if(user != null){
                project.setUser(user);
                project.setDateCreated(LocalDate.now());
                myproject.createProject(project);
                
                // Refresh the list immediately
               myUser.getUserProjects(session);
                
                
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Project created successfully!", null));
                
                project = null;
                return null;
            }
            
        }
        return null;
    }
    
    public void deleteProject(Long id) {
         FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if(session != null){
            UserEntity user = (UserEntity) session.getAttribute("user");
            
            if(user != null){
             myproject.deleteProject(id);
            // myproject.viewProject(user.getId());
          //  myUser.getUserProjects(session);

            }
        }
    
}

    
    
    }
    
   
   

