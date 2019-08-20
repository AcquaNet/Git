/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeconnectorserver.model;
    
 
public class Configuracion {

	private String serverName;
	private String portNumber;
	private String codigoConfCliente;
	private String clavePrivadaConfCliente;
	private String algoritmo;
	private String user;
	private String password;
	private Integer expiracion;
	private String servidorServicio;
	private Integer puertoServicio;
	private String tocken;

	public Configuracion() {
		super();
	} 

	public Configuracion(String serverName, String portNumber, String codigoConfCliente, String clavePrivadaConfCliente,
			String algoritmo, String user, String password, Integer expiracion, String servidorServicio,
			Integer puertoServicio) {
		super();
		this.serverName = serverName;
		this.portNumber = portNumber;
		this.codigoConfCliente = codigoConfCliente;
		this.clavePrivadaConfCliente = clavePrivadaConfCliente;
		this.algoritmo = algoritmo;
		this.user = user;
		this.password = password;
		this.expiracion = expiracion;
		this.servidorServicio = servidorServicio;
		this.puertoServicio = puertoServicio;
	}
 
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
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
		return "Configuracion [serverName=" + serverName + ", portNumber=" + portNumber + ", codigoConfCliente="
				+ codigoConfCliente + ", clavePrivadaConfCliente=" + clavePrivadaConfCliente + ", algoritmo="
				+ algoritmo + ", user=" + user + ", password=" + password + ", expiracion=" + expiracion + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serverName == null) ? 0 : serverName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuracion other = (Configuracion) obj;
		if (serverName == null) {
			if (other.serverName != null)
				return false;
		} else if (!serverName.equals(other.serverName))
			return false;
		return true;
	}

}
