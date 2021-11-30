/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.service;

import com.atina.cliente.connector.JDEAtinaConfigDriver;
import com.atina.cliente.connector.JDEAtinaConnector;
import com.atina.cliente.exception.ConnectionException;
import java.util.HashMap;
import java.util.Random;

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
 
    public void removeConnectorChannel(Integer session)
    {
        pool.remove(session);
    }
    
    public Integer getAvailableChannel()
    { 
        
        if(pool.size()>0)
        {
            if(pool.size() == 1)
            {
                return (Integer) pool.keySet().toArray()[0];
                
            } else
            {
                Random rand = new Random();
                int randomNum = rand.nextInt((pool.size()-1 - 0) + 1) + 0; 
                return (Integer) pool.keySet().toArray()[randomNum];
            }
            
        } else
        {
            return GRPCConnection.getChannelId();
        }
         
    }
    
    
    public JDEAtinaConnector getConnectorChannel(int channelId)
    {
        if(channelId == 0 || !pool.containsKey(channelId) )
        {
            throw new ConnectionException("Invalid Channel Id");
        }
        
        return pool.get(channelId);
        
    }
    
    public JDEAtinaConnector createConnectorChannel(String servidorName, Integer servidorPort, int channelId)
    {
 
        JDEAtinaConnector jdeAtinaConnector = null;

        JDEAtinaConfigDriver configure = new JDEAtinaConfigDriver();
        configure.setMicroServiceName(servidorName);
        configure.setMicroServicePort(servidorPort);
        configure.setWsConnection(true);

        // =========================================================
        // Connect
        // =========================================================
        configure.connect(configure.getWsConnection(),
                configure.getMicroServiceName(),
                configure.getMicroServicePort());

        // =========================================================
        // Create JD Atina Connector
        // =========================================================
        jdeAtinaConnector = new JDEAtinaConnector();

        jdeAtinaConnector.setConfig(configure);

        pool.put(channelId, jdeAtinaConnector);
         
        return jdeAtinaConnector;
    }
     
}
