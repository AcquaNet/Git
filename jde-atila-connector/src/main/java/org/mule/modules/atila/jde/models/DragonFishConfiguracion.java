package org.mule.modules.atila.jde.models;

public class DragonFishConfiguracion {

    private String urlBase;
    private String codigoConfCliente;
    private String clavePrivadaConfCliente;
    private String algoritmo;
    private String user;
    private String password;
    private Integer expiracion;
    private String servidorServicio;
    private Integer puertoServicio;
    private String tocken;

    public DragonFishConfiguracion() {
        super();
    }

    public DragonFishConfiguracion(String urlBase, String codigoConfCliente, String clavePrivadaConfCliente,
            String algoritmo, String user, String password, Integer expiracion, String servidorServicio,
            Integer puertoServicio) {
        super();
        this.urlBase = urlBase;
        this.codigoConfCliente = codigoConfCliente;
        this.clavePrivadaConfCliente = clavePrivadaConfCliente;
        this.algoritmo = algoritmo;
        this.user = user;
        this.password = password;
        this.expiracion = expiracion;
        this.servidorServicio = servidorServicio;
        this.puertoServicio = puertoServicio;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public String getCodigoConfCliente() {
        return codigoConfCliente;
    }

    public void setCodigoConfCliente(String codigoConfCliente) {
        this.codigoConfCliente = codigoConfCliente;
    }

    public String getClavePrivadaConfCliente() {
        return clavePrivadaConfCliente;
    }

    public void setClavePrivadaConfCliente(String clavePrivadaConfCliente) {
        this.clavePrivadaConfCliente = clavePrivadaConfCliente;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
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

    public Integer getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(Integer expiracion) {
        this.expiracion = expiracion;
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

    public String getTocken() {
        return tocken;
    }

    public void setTocken(String tocken) {
        this.tocken = tocken;
    }

    @Override
    public String toString() {
        return "Configuracion [urlBase=" + urlBase + ", codigoConfCliente="
                + codigoConfCliente + ", clavePrivadaConfCliente=" + clavePrivadaConfCliente + ", algoritmo="
                + algoritmo + ", user=" + user + ", expiracion=" + expiracion + "]";
    }

}
