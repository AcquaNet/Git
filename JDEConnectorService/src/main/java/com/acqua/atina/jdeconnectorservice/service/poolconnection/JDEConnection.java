/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnectorservice.service.poolconnection;

import com.acqua.atina.jdeconnector.internal.model.ConnectionDetail;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectionException;
import com.acqua.atina.jdeconnectorservice.exception.JDESingleConnectorException;
import com.jdedwards.base.resource.UserPreference;
import java.util.HashMap; 
import java.util.Set;

/**
 *
 * @author jgodi
 */
public interface JDEConnection {
    
    public int isJDEConnected();
    public int connect() throws JDESingleConnectionException;
    public void disconnect();
    public boolean isWSConnection();
    public Set<String> getOperationList() throws JDESingleConnectorException;
    public HashMap<String, Object> callJDEOperation(String bsfnName, HashMap<String, Object> inputObject, Integer transactionID) throws JDESingleConnectorException;
    public UserPreference getUserPreference();
    public ConnectionDetail getConnectionInfo();
    public Integer startTransaction() throws JDESingleConnectorException;
    public Integer commitTransaction(Integer transactionID) throws JDESingleConnectorException;
    public Integer rollbackTransaction(Integer transactionID) throws JDESingleConnectorException;
    
}
