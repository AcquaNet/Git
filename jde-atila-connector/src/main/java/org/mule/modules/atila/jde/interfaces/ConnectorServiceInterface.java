package org.mule.modules.atila.jde.interfaces;

import java.util.List;
import java.util.Map;

import org.mule.modules.atila.jde.models.JDEAtilaConfiguracion;
import org.mule.modules.connector.exceptions.InternalConnectorException;

import com.acqua.dragonfishserverwp.servicios.DragonFishServiceGrpc.DragonFishServiceBlockingStub;
import com.acqua.dragonfishserverwp.servicios.TipoDelParametroInput;
import com.acqua.dragonfishserverwp.servicios.TipoDelParametroOutput;

public interface ConnectorServiceInterface {

    public abstract void login(DragonFishServiceBlockingStub stub, JDEAtilaConfiguracion configuracion) throws InternalConnectorException;

    public abstract String getToken() throws InternalConnectorException;

    public abstract Map<String, String> getMetadataOperations(DragonFishServiceBlockingStub stub, JDEAtilaConfiguracion configuracion) throws InternalConnectorException;

    public abstract List<TipoDelParametroInput> getInputMetadataForOperation(DragonFishServiceBlockingStub stub, JDEAtilaConfiguracion configuracion, String operation)
            throws InternalConnectorException;

    public abstract List<TipoDelParametroOutput> getOutputMetadataForOperation(DragonFishServiceBlockingStub stub, JDEAtilaConfiguracion configuracion,
            String operation) throws InternalConnectorException;

    public Object ejecutarServicio(DragonFishServiceBlockingStub stub, JDEAtilaConfiguracion configuracion, String entityType, Map<String, Object> entityData);

}
