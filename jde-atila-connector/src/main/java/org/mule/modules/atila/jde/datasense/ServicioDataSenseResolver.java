package org.mule.modules.atila.jde.datasense;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.mule.api.annotations.MetaDataKeyRetriever;
import org.mule.api.annotations.MetaDataOutputRetriever;
import org.mule.api.annotations.MetaDataRetriever;
import org.mule.api.annotations.components.MetaDataCategory;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.common.metadata.builder.DynamicObjectBuilder;
import org.mule.common.metadata.builder.MetaDataBuilder;
import org.mule.common.metadata.datatype.DataType;
import org.mule.modules.atila.jde.JDEAtinaConnector;
import org.mule.modules.atila.jde.utils.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;

/**
 * Category which can differentiate between input or output MetaDataRetriever
 */
@MetaDataCategory
public class ServicioDataSenseResolver {

    private static final Logger logger = LoggerFactory.getLogger(ServicioDataSenseResolver.class);

    @Inject
    protected JDEAtinaConnector connector;

    public void setConnector(final JDEAtinaConnector connector) {
        this.connector = connector;
    }

    private MetaData metadataOutput;

    /**
     * Retrieves the list of keys
     */
    @MetaDataKeyRetriever
    public List<MetaDataKey> getMetaDataKeys() throws Exception {

        logger.info("DRAGONFISH - DataSenseResolver - getMetaDataKeys ...");

        logger.info("DragonFish Service - Recuperando Operaciones Disponibles de DragonFish");

        List<MetaDataKey> keys = new ArrayList<MetaDataKey>();

        // Generate the keys

        Map<String, String> operationsAsObject = connector.getConfig()
                .getService()
                .getMetadataOperations(connector.getConfig()
                        .getStub(), connector.getConfig()
                        .getConfiguracion());

        for (String key : operationsAsObject.keySet()) {
            logger.info("DragonFish Service -                  Clave Operacion: " + key + " Valor Operacion: "
                    + operationsAsObject.get(key));
            keys.add(new DefaultMetaDataKey(key, operationsAsObject.get(key)));
        }

        logger.info("DragonFish Service - Recuperando Operaciones Disponibles de DragonFish ha recuperado "
                + keys.size() + " operaciones.");

        return keys;

    }

    /**
     * Get MetaData given the Key the user selects
     * 
     * @param key
     *            The key selected from the list of valid keys
     * @return The MetaData model of that corresponds to the key
     * @throws Exception
     *             If anything fails
     */
    @MetaDataRetriever
    public MetaData getMetaData(MetaDataKey key) throws Exception {

        logger.info("DRAGONFISH - DataSenseResolver - getMetaData for key: [" + key.getId() + "] Display Name: ["
                + key.getDisplayName() + "]");

        metadataOutput = null;

        // ==================================================
        // Recupera Metadata del Servicio
        // ==================================================
        //

        List<TipoDelParametroInput> description = connector.getConfig()
                .getService()
                .getInputMetadataForOperation(
                        connector.getConfig()
                                .getStub(), connector.getConfig()
                                .getConfiguracion(), key.getId());

        // ==================================================
        // Genera Metadata Input
        // ==================================================
        //

        DefaultMetaDataBuilder builder = new DefaultMetaDataBuilder();

        DynamicObjectBuilder<?> objectBuilder = builder.createDynamicObject(key.getDisplayName());

        for (TipoDelParametroInput field : description) {

            addMetaDataField(objectBuilder, field);

        }

        objectBuilder.endDynamicObject();

        // ==================================================
        // Genera Metadata Output
        // ==================================================
        //

        metadataOutput = generateOutputMetaData(key);

        FileUtils.writeStringToFile(new File("/tmp/FileNameToWriteInput.txt"), "INPUT:" + metadataOutput.toString());

        return new DefaultMetaData(builder.build());

    }

    @MetaDataOutputRetriever
    public MetaData getOutputMetaData(final MetaDataKey key) throws Exception {

        if (metadataOutput != null) {
            return metadataOutput;
        } else {
            return generateOutputMetaData(key);
        }

    }

    private MetaData generateOutputMetaData(final MetaDataKey key) throws Exception {

        logger.info("DRAGONFISH - DataSenseResolver - getOutputMetaData for key: [" + key.getId() + "] Display Name: ["
                + key.getDisplayName() + "]");

        // ==================================================
        // Recupera Metadata del Servicio
        // ==================================================

        List<TipoDelParametroOutput> description = connector.getConfig()
                .getService()
                .getOutputMetadataForOperation(connector.getConfig()
                        .getStub(),
                        connector.getConfig()
                                .getConfiguracion(), key.getId());

        DefaultMetaDataBuilder builder = new DefaultMetaDataBuilder();

        DynamicObjectBuilder<?> objectBuilder = builder.createDynamicObject(key.getDisplayName());

        for (TipoDelParametroOutput field2 : description) {

            addOutputMetaDataField(objectBuilder, field2);

        }

        objectBuilder.endDynamicObject();

        return new DefaultMetaData(builder.build());

    }

    protected void addMetaDataField(final MetaDataBuilder<?> objectBuilder, final TipoDelParametroInput field)
            throws Exception {

        // String dataType = field.getTipoDelParametroMule();
        String dataType = "";

        String nombreParametro = field.getNombreDelParametro();

        String tipoDelParametroJava = field.getTipoDelParametroJava();

        logger.debug("DRAGONFISH - DataSenseResolver - addMetaDataField for : [" + nombreParametro + "] Type: ["
                + dataType + "] Java Type: [" + tipoDelParametroJava + "");

        if (dataType.equalsIgnoreCase("LIST")) {

            DynamicObjectBuilder<?> metadataList = ((DynamicObjectBuilder<?>) objectBuilder).addList(nombreParametro)
                    .ofDynamicObject(nombreParametro);

            for (TipoDelParametroInput subParametro : field.getSubParametroList()) {
                addMetaDataField(metadataList, subParametro);
            }

            metadataList.endDynamicObject();

        } else if (dataType.equalsIgnoreCase("MAP") && (!tipoDelParametroJava
                .equals("BDecimal") && !tipoDelParametroJava
                .equals("BInteger"))) {

            DynamicObjectBuilder<?> innerObject = ((DynamicObjectBuilder<?>) objectBuilder)
                    .addDynamicObjectField(nombreParametro);

            for (TipoDelParametroInput subParametro : field.getSubParametroList()) {
                addMetaDataField(innerObject, subParametro);
            }

            innerObject.endDynamicObject();

        } else if (tipoDelParametroJava
                .equals("BDecimal")) {

            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(nombreParametro, DataType.DOUBLE);

        } else if (field.getTipoDelParametroJava()
                .equals("BInteger")) {

            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(nombreParametro, DataType.LONG);

        } else {

            getType(objectBuilder, dataType, nombreParametro);

        }

    }

    protected void addOutputMetaDataField(final MetaDataBuilder<?> objectBuilder,
            final TipoDelParametroOutput field) throws Exception {

        // String dataType = field.getTipoDelParametroMule();
        String dataType = "";

        String nombreParametro = field.getNombreDelParametro();

        String tipoDelParametroJava = field.getTipoDelParametroJava();

        if (dataType.equalsIgnoreCase("LIST")) {

            DynamicObjectBuilder<?> metadataList = ((DynamicObjectBuilder<?>) objectBuilder).addList(nombreParametro)
                    .ofDynamicObject(nombreParametro);

            for (TipoDelParametroOutput subParametro : field.getSubParametroList()) {
                addOutputMetaDataField(metadataList, subParametro);
            }

            metadataList.endDynamicObject();

        } else if (dataType.equalsIgnoreCase("MAP") && (!tipoDelParametroJava
                .equals("BDecimal") && !tipoDelParametroJava
                .equals("BInteger"))) {

            DynamicObjectBuilder<?> innerObject = ((DynamicObjectBuilder<?>) objectBuilder)
                    .addDynamicObjectField(nombreParametro);

            for (TipoDelParametroOutput subParametro : field.getSubParametroList()) {
                addOutputMetaDataField(innerObject, subParametro);
            }

            innerObject.endDynamicObject();

        } else if (field.getTipoDelParametroJava()
                .equals("BDecimal")) {

            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(nombreParametro, DataType.DOUBLE);

        } else if (field.getTipoDelParametroJava()
                .equals("BInteger")) {

            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(nombreParametro, DataType.LONG);

        } else {

            getType(objectBuilder, dataType, nombreParametro);

        }

    }

    private void getType(final MetaDataBuilder<?> objectBuilder, String type, String name) {
        MoreObjects.firstNonNull(EnumUtils.getEnumFromString(Type.class, type), Type.STRING)
                .addField(objectBuilder,
                        name);
    }

}
