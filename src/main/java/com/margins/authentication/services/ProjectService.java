/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.margins.authentication.services;

import com.margins.authentication.entity.ProjectEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.transaction.*;
import java.io.Serializable;
import java.util.List;
/**
 *
 * @author HerbertSekpey
 */

@Transactional
public class ProjectService  implements Serializable{
    
    @PersistenceContext
    EntityManager projectManager;

   
   public void createProject(ProjectEntity project){
       projectManager.persist(project);
   }
   
   public ProjectEntity findProject(Long id){
        return projectManager.find(ProjectEntity.class, id);
   }
   
   public ProjectEntity updateProject(Long id){
    ProjectEntity myproject = findProject(id);
    return projectManager.merge(myproject);
   }
   
   
   public List<ProjectEntity> viewProject(Long id){
    return projectManager.createQuery("SELECT t FROM ProjectEntity t WHERE t.user.id = :id")
            .setParameter("id", id)
            .getResultList();
   } 
   
   
   public void deleteProject(Long id){
    ProjectEntity getProject = findProject(id);
    projectManager.remove(getProject);
   }
   
}
