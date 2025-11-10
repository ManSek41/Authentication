/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.margins.authentication.beans;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import com.margins.authentication.entity.UserEntity;
import com.margins.authentication.services.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

// browser session 
import jakarta.servlet.http.HttpSession;


/**
 *
 * @author HerbertSekpey
 */


@Named("userBean")
@RequestScoped
public class UserBean implements Serializable{
    
    @Inject
    private UserService userService;
    
    @Inject
    private SessionBean sessionUser;
    @Inject
    private projectBean projectBean;
    
    private UserEntity user = new UserEntity();
    
    private String confirmPassword;
    
    private String loginPassword;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public SessionBean getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(SessionBean sessionUser) {
        this.sessionUser = sessionUser;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }
    
    FacesContext context = FacesContext.getCurrentInstance();
  

    public String createUser() {
    
    
//    List<UserEntity> allUsers = userService.getAllUsers();
//    UserEntity eachUser = userService.findUserById(user.getId());
    
    String currentUser = user.getUserName();
    String email = user.getEmail();
  
//    boolean usernameExists = allUsers.stream()
//    .anyMatch(u -> u.getUserName().equalsIgnoreCase(currentUser));
   if(userService.emailExists(email) ){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already Exists!", null));
       return null; 
    }

   else if (user.getPassword().isBlank() || getConfirmPassword().isBlank() || user.getEmail().isBlank() || user.getUserName().isBlank()) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No field should be empty", null));
        return null; // stay on the same page
    }
   
   else if(user.getPassword().length() < 6){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password must be more than 6 letters/numbers", null));
        return null; // stay on the same page
    }

   else if (!user.getPassword().equals(getConfirmPassword())) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match", null));
        return null; // stay on the same page
    }
    
   else if(userService.usernameExists(currentUser) ){
       context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User already Exists!", null));
       return null; 
    }
   else if(!user.getEmail().contains("@")){
       context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email must have @", null));
       return null; 
   }
   else {  
    user.setPassword(hashPassword(user.getPassword()));
    userService.createUser(user);
    
    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User created successfully!", null));
     user = new UserEntity();
    confirmPassword = null;
    
  
    return "login.xhtml"; // navigation to another page if needed
    }
       
}

public String loginUser() {

    UserEntity loggedInUser = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
    
    if (loggedInUser != null) {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                                                    .getExternalContext()
                                                    .getSession(true);
    session.setAttribute("user", loggedInUser);
        // Working on sessions 
        sessionUser.setUser(loggedInUser); // first session used here. would implement HttpServletRequest
        sessionUser.getUserProjects(session);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User successfully logged in", null));
        return "app/dashboard.xhtml?faces-redirect=true";
//          return null;   // for now
    } else {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email or password", null));
        return null;
    }
}


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
   
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity User) {
        this.user = User;
    }

    public String userSignup(){
        return "index.xhtml";
    }
   
  public String hashPassword(String plainPassword) {
    return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
}

}
