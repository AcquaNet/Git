/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.service;

import com.atina.cliente.connector.JDEAtinaConfigDriver;
import com.atina.cliente.connector.JDEAtinaConnector;
import com.atina.cliente.exception.ConnectionException;
import com.atina.model.ChannelKey;
import java.util.HashMap;

/**
 *
 * @author jgodi
 */
public class ConnectionPool {
    
    private HashMap<Integer,JDEAtinaConnector> pool = new HashMap<Integer,JDEAtinaConnector>();
    
    private ConnectionPool() {
    }
    
    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.INSTANCE;
    }
    
    private static class ConnectionPoolHolder {

        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }
    
    public JDEAtinaConnector getConnectorChannel(Integer session)
    {
        if(pool.containsKey(session))
        {
            return pool.get(session);
            
        } else
        {
            throw new ConnectionException("Invalid Session ID");
        }
        
    }
    
    public void removeConnectorChannel(Integer session)
    {
        pool.remove(session);
    }
    
    public JDEAtinaConnector getConnectorChannel(String servidorName, Integer servidorPort, ChannelKey key)
    {
        
        JDEAtinaConnector jdeAtinaConnector = null;
        
        int hashCode = key.hashCode();
        
        if(pool.containsKey(hashCode))
        {
            jdeAtinaConnector = pool.get(hashCode);
            
        } else
        {
            JDEAtinaConfigDriver configure = new JDEAtinaConfigDriver();

            configure.setJdeUser(key.getUser());
            configure.setJdePassword(key.getPassword());
            configure.setJdeEnvironment(key.getEnvironment());
            configure.setJdeRole(key.getRole());
            configure.setMicroServiceName(servidorName);
            configure.setMicroServicePort(servidorPort);
            configure.setWsConnection(true);

            // =========================================================
            // Connect
            // =========================================================
            configure.connect(  configure.getJdeUser(),
                                configure.getJdePassword(),
                                configure.getJdeEnvironment(),
                                configure.getJdeRole(),
                                configure.getWsConnection(),
                                configure.getMicroServiceName(),
                                configure.getMicroServicePort());

            // =========================================================
            // Create JD Atina Connector
            // =========================================================

            jdeAtinaConnector = new JDEAtinaConnector();

            jdeAtinaConnector.setConfig(configure);
            
            pool.put(hashCode, jdeAtinaConnector);
            
        }
         
        return jdeAtinaConnector;
    }
    
    
    
    
}
