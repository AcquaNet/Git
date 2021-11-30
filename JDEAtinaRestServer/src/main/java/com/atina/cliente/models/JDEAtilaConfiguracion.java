package com.atina.cliente.models;
  
public class JDEAtilaConfiguracion {

    private String jdeUser;
    private String jdePassword;
    private String jdeEnvironment;
    private String jdeRole;
    private Boolean wsConnection;
    private String microServiceName;
    private Integer microServicePort;
    private long sessionID;
    private String token;
    private String addressBookNumber;

    public JDEAtilaConfiguracion() {
        super();
    }

    public JDEAtilaConfiguracion(String jdeUser, String jdePassword, String jdeEnvironment, String jdeRole, String token,
            Boolean wsConnection, String microServiceName, Integer microServicePort) {
        super();
        this.jdeUser = jdeUser;
        this.jdePassword = jdePassword;
        this.jdeEnvironment = jdeEnvironment;
        this.jdeRole = jdeRole;
        this.token = token;
        this.wsConnection = wsConnection;
        this.microServiceName = microServiceName;
        this.microServicePort = microServicePort;
    }

    public String getJdeUser() {
        return jdeUser;
    }

    public void setJdeUser(String jdeUser) {
        this.jdeUser = jdeUser;
    }

    public String getJdePassword() {
        return jdePassword;
    }

    public void setJdePassword(String jdePassword) {
        this.jdePassword = jdePassword;
    }

    public String getJdeEnvironment() {
        return jdeEnvironment;
    }

    public void setJdeEnvironment(String jdeEnvironment) {
        this.jdeEnvironment = jdeEnvironment;
    }

    public String getJdeRole() {
        return jdeRole;
    }

    public void setJdeRole(String jdeRole) {
        this.jdeRole = jdeRole;
    }

    public Boolean getWsConnection() {
        return wsConnection;
    }

    public void setWsConnection(Boolean wsConnection) {
        this.wsConnection = wsConnection;
    }

    public String getMicroServiceName() {
        return microServiceName;
    }

    public void setMicroServiceName(String microServiceName) {
        this.microServiceName = microServiceName;
    }

    public Integer getMicroServicePort() {
        return microServicePort;
    }

    public void setMicroServicePort(Integer microServicePort) {
        this.microServicePort = microServicePort;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JDEAtilaConfiguracion(String jdeUser, String jdeEnvironment, String jdeRole, Boolean wsConnection,
            String microServiceName, Integer microServicePort, long sessionID, String token) {
        super();
        this.jdeUser = jdeUser;
        this.jdeEnvironment = jdeEnvironment;
        this.jdeRole = jdeRole;
        this.wsConnection = wsConnection;
        this.microServiceName = microServiceName;
        this.microServicePort = microServicePort;
        this.sessionID = sessionID;
        this.token = token;
    }

    public String getAddressBookNumber() {
        return addressBookNumber;
    }

    public void setAddressBookNumber(String addressBookNumber) {
        this.addressBookNumber = addressBookNumber;
    }

    @Override
    public String toString() {
        return "JDEAtilaConfiguracion [jdeUser=" + jdeUser + ", jdeEnvironment=" + jdeEnvironment + ", jdeRole="
                + jdeRole + ", wsConnection=" + wsConnection + ", microServiceName=" + microServiceName
                + ", microServicePort=" + microServicePort + ", sessionID=" + sessionID + ", token=" + token
                + ", addressBookNumber=" + addressBookNumber + "]";
    }

}
