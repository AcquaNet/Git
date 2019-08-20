/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeclient.configuracion;

public class Configuracion {

    private String servidorServicio;
    private Integer puertoServicio;
    private String user;
    private String password;
    private String environment;
    private String role;
    private Integer session;

    public Configuracion() {
        super();
    }

    public Configuracion(String servidorServicio, Integer puertoServicio, String user, String password, String environment, String role, Integer session) {
        this.servidorServicio = servidorServicio;
        this.puertoServicio = puertoServicio;
        this.user = user;
        this.password = password;
        this.environment = environment;
        this.role = role;
        this.session = session;
    }

    public String getServidorServicio() {
        return servidorServicio;
    }

    public void setServidorServicio(String servidorServicio) {
        this.servidorServicio = servidorServicio;
    }

    public Integer getPuertoServicio() {
        return puertoServicio;
    }

    public void setPuertoServicio(Integer puertoServicio) {
        this.puertoServicio = puertoServicio;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Configuracion{" + "servidorServicio=" + servidorServicio + ", puertoServicio=" + puertoServicio + ", user=" + user + ", password=" + password + ", environment=" + environment + ", role=" + role + ", session=" + session + '}';
    }

}
