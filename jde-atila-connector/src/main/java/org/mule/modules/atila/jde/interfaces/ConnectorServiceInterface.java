package org.mule.modules.atila.jde.interfaces;

import java.util.List;
import java.util.Map;

import org.mule.modules.atila.jde.exceptions.InternalConnectorException;
import org.mule.modules.atila.jde.models.JDEAtilaConfiguracion;

import com.jde.jdeserverwp.servicios.JDEServiceGrpc.JDEServiceBlockingStub;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;

public interface ConnectorServiceInterface {

    public abstract void login(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion) throws InternalConnectorException;

    public abstract void logout(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion) throws InternalConnectorException;

    public abstract boolean isConnected(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion) throws InternalConnectorException;
 
    public abstract Map<String, String> getMetadataOperations(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion) throws InternalConnectorException;

    public abstract List<TipoDelParametroInput> getInputMetadataForOperation(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion, String operation)
            throws InternalConnectorException;

    public abstract List<TipoDelParametroOutput> getOutputMetadataForOperation(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion,
            String operation) throws InternalConnectorException;

    public Object ejecutarServicio(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion, String entityType, Map<String, Object> entityData);

}
