/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.cliente.service;

import com.atina.cliente.exceptions.ExternalConnectorException;
import com.atina.cliente.exceptions.InternalConnectorException;
import com.atina.cliente.models.JDEAtilaConfiguracion;
import java.util.List;
import java.util.Map;
 

import com.jde.jdeserverwp.servicios.JDEServiceGrpc.JDEServiceBlockingStub;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;

/**
 *
 * @author jgodi
 */
public interface ConnectorServiceInterface {
    
    public void login(JDEServiceBlockingStub stub, com.atina.cliente.connector.JDEAtinaConfiguracion configuracion, Long transactionID)
            throws InternalConnectorException, ExternalConnectorException;
    
    public void logout(JDEServiceBlockingStub stub, com.atina.cliente.connector.JDEAtinaConfiguracion configuracion, Long transactionID)
            throws InternalConnectorException, ExternalConnectorException;

    public boolean isConnected(JDEServiceBlockingStub stub, com.atina.cliente.connector.JDEAtinaConfiguracion configuracion, Long transactionID)
            throws InternalConnectorException, ExternalConnectorException;

    public Map<String, String> getMetadataOperations(JDEServiceBlockingStub stub, com.atina.cliente.connector.JDEAtinaConfiguracion configuracion)
            throws InternalConnectorException;

    public List<TipoDelParametroInput> getInputMetadataForOperation(JDEServiceBlockingStub stub, com.atina.cliente.connector.JDEAtinaConfiguracion configuracion, String operation)
            throws InternalConnectorException;

    public List<TipoDelParametroOutput> getOutputMetadataForOperation(JDEServiceBlockingStub stub, com.atina.cliente.connector.JDEAtinaConfiguracion configuracion, String operation)
            throws InternalConnectorException;

    public Object ejecutarServicio(JDEServiceBlockingStub stub,
            com.atina.cliente.connector.JDEAtinaConfiguracion configuracion, String entityType, Map<String, Object> entityData)
            throws InternalConnectorException, ExternalConnectorException;

    public Object getJsonFromOperations(JDEServiceBlockingStub stub, com.atina.cliente.connector.JDEAtinaConfiguracion configuracion, String entityType, Map<String, Object> entityData)
            throws InternalConnectorException;
    
}
