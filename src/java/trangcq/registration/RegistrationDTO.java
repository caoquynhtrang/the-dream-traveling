/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.registration;

import java.io.Serializable;
import trangcq.role.RoleDTO;
import trangcq.status.StatusDTO;

/**
 *
 * @author USER
 */
public class RegistrationDTO implements Serializable{
    private String username, password, name, facebookId, facebookLink;
    private int id;
    private StatusDTO status;
    private RoleDTO role;


    public RegistrationDTO() {
    }

    
    public RegistrationDTO(int id, String username, String password, String name, StatusDTO status, RoleDTO role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.status = status;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    


   

    
    
    
    
}
