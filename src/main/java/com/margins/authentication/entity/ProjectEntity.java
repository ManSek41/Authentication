/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.margins.authentication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

// import lombok.Data;

/**
 *
 * @author HerbertSekpey
 */


@Entity
@Table(name = "Project")
public class ProjectEntity implements Serializable{
    
    
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       private Long id;
       
       private String name;
       
       private String description;
       
       private LocalDate dateCreated;
       
 
       @OneToMany(mappedBy = "project")
       private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
       
 
       @ManyToOne
       @JoinColumn(name="user_id")
       private UserEntity user;

    public void setUser(UserEntity user) {
        this.user = user;
    }

       
    public UserEntity getUser() {
        return user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
    
       
    
}
