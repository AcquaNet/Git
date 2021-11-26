/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.model;

import java.util.Objects;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author jgodi
 */
public class ChannelKey {

    private String user; 
    private String environment;
    private String password;
    private String role;

    public ChannelKey(String user, String password, String environment, String role) {
        this.user = user; 
        this.password = password;
        this.environment = environment;
        this.role = role;
    }

    @Override
    public int hashCode() {

        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.user);
        builder.append(this.environment);
        builder.append(this.role);
        return builder.toHashCode();

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChannelKey other = (ChannelKey) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        } 
        if (!Objects.equals(this.environment, other.environment)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        return true;
    }

    public String getUser() {
        return user;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    
    
}
