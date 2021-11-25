/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.internal.model;

/**
 *
 * @author jgodi
 */
public class ConnectionDetail {
    
    private String type; 
    private String user; 
    private String environment;
    private String role; 
    private String tmpFolder; 
    private String tmpFolderCache;
    private int iSessionID;
    private boolean active;

    public ConnectionDetail(String type, String user, String environment, String role, String tmpFolder, String tmpFolderCache, int iSessionID, boolean active) {
        this.type = type;
        this.user = user;
        this.environment = environment;
        this.role = role;
        this.tmpFolder = tmpFolder;
        this.tmpFolderCache = tmpFolderCache;
        this.iSessionID = iSessionID;
        this.active = active;
    } 
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
 
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTmpFolder() {
        return tmpFolder;
    }

    public void setTmpFolder(String tmpFolder) {
        this.tmpFolder = tmpFolder;
    }

    public String getTmpFolderCache() {
        return tmpFolderCache;
    }

    public void setTmpFolderCache(String tmpFolderCache) {
        this.tmpFolderCache = tmpFolderCache;
    }

    public int getiSessionID() {
        return iSessionID;
    }

    public void setiSessionID(int iSessionID) {
        this.iSessionID = iSessionID;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ConnectionDetail{" + "type=" + type + ", user=" + user + ", environment=" + environment + ", role=" + role + ", tmpFolder=" + tmpFolder + ", tmpFolderCache=" + tmpFolderCache + ", iSessionID=" + iSessionID + ", active=" + active + '}';
    } 
  
}
