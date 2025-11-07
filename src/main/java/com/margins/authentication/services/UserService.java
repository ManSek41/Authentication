/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.margins.authentication.services;

import com.margins.authentication.entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HerbertSekpey
 */
@Transactional
public class UserService implements Serializable{
    
    @PersistenceContext
    EntityManager entityManager;
    
    
    public void createUser(UserEntity user){
   
        entityManager.persist(user);
        entityManager.flush();
    }
    
    public UserEntity getUser(Long id){
    return entityManager.find(UserEntity.class, id);   
    }
   
    public List<UserEntity> getAllUsers(){
     return entityManager.createQuery("SELECT u from UserEntity u", UserEntity.class).getResultList();
    }
    
    public UserEntity findUserById(Long id){
        return entityManager.find(UserEntity.class, id);
    }
    
    public boolean usernameExists(String username) {
    Long count = entityManager.createQuery(
        "SELECT COUNT(u) FROM UserEntity u WHERE LOWER(u.userName) = LOWER(:username)", Long.class)
        .setParameter("username", username)
        .getSingleResult();
    return count > 0;
}
    
    public boolean emailExists(String email) {
    Long count = entityManager.createQuery(
        "SELECT COUNT(u) FROM UserEntity u WHERE LOWER(u.email) = LOWER(:email)", Long.class)
        .setParameter("email", email)
        .getSingleResult();
    return count > 0;
}
    
     public boolean passwordExists(String password) {
       
    Long count = entityManager.createQuery(
        "SELECT COUNT(u) FROM UserEntity u WHERE LOWER(u.password) = LOWER(:password)", Long.class)
        .setParameter("password", password)
        .getSingleResult();
  
    return count > 0;
}
     
    public UserEntity findByEmailAndPassword(String email, String password) {
     try{
    UserEntity results = entityManager.createQuery(
        "SELECT u FROM UserEntity u WHERE LOWER(u.email) = LOWER(:email)",
        UserEntity.class)
        .setParameter("email", email)
        .getSingleResult();
    
    
    if(BCrypt.checkpw(password, results.getPassword())){
         return results;
    }
    else{
       return null; 
    }
    
     }
     catch(Exception e){
      return null;   
     }
   
}
    
    public boolean findByEmailAndUsername(String email, String username){
        return !username.isEmpty() && !email.isEmpty();
    }
    
//    
//    public boolean checkPassword(String email, String password){
//        UserEntity pass = entityManager.createQuery("SELECT u FROM UserEntity u WHERE LOWER(u.email) = LOWER(:email)", UserEntity.class)
//               .setParameter("email", email)
//               .getSingleResult();
//       
//      
//        String myPass = pass.getPassword();
//        return BCrypt.checkpw(password, myPass);
//    }
//       
 
    public LocalDate getDate(long id){
     
   LocalDate myDate = entityManager.createQuery("SELECT t.dateCreated from userEntity t WHERE t.id = :id", LocalDate.class)
                 .setParameter("id", id)
                 .getSingleResult();
   if(myDate != null){
      return myDate;
   }
        return null;
    }
 
    
}
