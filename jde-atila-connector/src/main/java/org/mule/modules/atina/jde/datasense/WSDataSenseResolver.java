package org.mule.modules.atina.jde.datasense;

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
import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.mule.modules.atina.jde.utils.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;

/**
 * Category which can differentiate between input or output MetaDataRetriever
 */
@MetaDataCategory
public class WSDataSenseResolver {

    private static final Logger logger = LoggerFactory.getLogger(WSDataSenseResolver.class);

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

        logger.info("JDE Atina - WSDataSenseResolver - getMetaDataKeys ...");

        logger.info("JDE Atina - WSDataSenseResolver - Getting WS's ...");

        List<MetaDataKey> keys = new ArrayList<MetaDataKey>();

        // Generate the keys

        Map<String, String> operationsAsObject = connector.getConfig()
                .getService()
                .getMetadataOperations(connector.getConfig()
                        .getStub(), connector.getConfig()
                        .getConfiguracion());

        for (String key : operationsAsObject.keySet()) {
            logger.info("JDE Atina - WSDataSenseResolver -                  WS: " + key + " Valor Operacion: "
                    + operationsAsObject.get(key));
            keys.add(new DefaultMetaDataKey(key, operationsAsObject.get(key)));
        }

        logger.info("JDE Atina - WSDataSenseResolver - There is "
                + keys.size() + " WS available.");

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

        logger.info("JDE Atina - WSDataSenseResolver - GetMetaData for key: [" + key.getId() + "] Display Name: ["
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

        logger.info("JDE Atina - DataSenseResolver - getOutputMetaData for key: [" + key.getId() + "] Display Name: ["
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

 
        String nombreParametro = field.getNombreDelParametro();

        String tipoDelParametroJava = field.getTipoDelParametroJava();

        logger.debug("JDE Atina - DataSenseResolver - addMetaDataField for : [" + nombreParametro + "] Java Type: [" + tipoDelParametroJava + "");

        if (field.getRepeatedParameter()) {

            DynamicObjectBuilder<?> metadataList = ((DynamicObjectBuilder<?>) objectBuilder).addList(nombreParametro)
                    .ofDynamicObject(nombreParametro);

            for (TipoDelParametroInput subParametro : field.getSubParametroList()) {
                addMetaDataField(metadataList, subParametro);
            }

            metadataList.endDynamicObject();

        } else if (field.getIsObject() && (!tipoDelParametroJava
                .equals("java.math.BigDecimal") && !tipoDelParametroJava
                .equals("java.math.BigInteger"))) {

            DynamicObjectBuilder<?> innerObject = ((DynamicObjectBuilder<?>) objectBuilder)
                    .addDynamicObjectField(nombreParametro);

            for (TipoDelParametroInput subParametro : field.getSubParametroList()) {
                addMetaDataField(innerObject, subParametro);
            }

            innerObject.endDynamicObject();

        } else if (tipoDelParametroJava
                .equals("java.math.BigDecimal")) {

            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(nombreParametro, DataType.DOUBLE);

        } else if (field.getTipoDelParametroJava()
                .equals("java.math.BigInteger")) {

            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(nombreParametro, DataType.LONG);

        } else {

            getType(objectBuilder, tipoDelParametroJava, nombreParametro);

        }

    }

    protected void addOutputMetaDataField(final MetaDataBuilder<?> objectBuilder,
            final TipoDelParametroOutput field) throws Exception {

        String nombreParametro = field.getNombreDelParametro();

        String tipoDelParametroJava = field.getTipoDelParametroJava();

        if (field.getRepeatedParameter()) {

            DynamicObjectBuilder<?> metadataList = ((DynamicObjectBuilder<?>) objectBuilder).addList(nombreParametro)
                    .ofDynamicObject(nombreParametro);

            for (TipoDelParametroOutput subParametro : field.getSubParametroList()) {
                addOutputMetaDataField(metadataList, subParametro);
            }

            metadataList.endDynamicObject();

        } else if (field.getIsObject() && (!tipoDelParametroJava
                .equals("java.math.BigDecimal") && !tipoDelParametroJava
                .equals("java.math.BigInteger"))) {

            DynamicObjectBuilder<?> innerObject = ((DynamicObjectBuilder<?>) objectBuilder)
                    .addDynamicObjectField(nombreParametro);

            for (TipoDelParametroOutput subParametro : field.getSubParametroList()) {
                addOutputMetaDataField(innerObject, subParametro);
            }

            innerObject.endDynamicObject();

        } else if (field.getTipoDelParametroJava()
                .equals("java.math.BigDecimal")) {

            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(nombreParametro, DataType.DOUBLE);

        } else if (field.getTipoDelParametroJava()
                .equals("java.math.BigInteger")) {

            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(nombreParametro, DataType.LONG);

        } else {

            getType(objectBuilder, tipoDelParametroJava, nombreParametro);

        }

    }

    private void getType(final MetaDataBuilder<?> objectBuilder, String type, String name) {
    	
    	 logger.info("JDE Atina - DataSenseResolver - Get Type for : [" + type + "] Name: [" + name + "");
    	   

    	 switch (type)  
    	 {
    	 	case  "java.lang.Integer":
    	 		type = DataType.INTEGER.name();
    	 		break;
    	 	
    	 	case  "java.lang.String":
    	 		type = DataType.STRING.name();
    	 		break;
    	 		
    	 	case  "java.lang.Calendar":
    	 		type = DataType.DATE.name();
    	 		break;
    	 		
    	 	case  "java.lang.Boolean":
    	 		type = DataType.BOOLEAN.name();
    	 		break;
    	 	
    	 	case  "java.lang.Byte":
    	 		type = DataType.BYTE.name();
    	 		break;
    	 		
    	 	case  "java.lang.Long":
    	 		type = DataType.LONG.name();
    	 		break;
    	 		
    	 	case  "java.lang.Float":
    	 		type = DataType.FLOAT.name();
    	 		break;
    	 		
    	 	case  "java.util.Date":
    	 		type = DataType.DATE.name();
    	 		break;
    	 
    	 	default:
    	 		type = DataType.STRING.name();
    	 		break;
    	 } 
    	  
    	 
        MoreObjects.firstNonNull(EnumUtils.getEnumFromString(Type.class, type), Type.STRING)
                .addField(objectBuilder,
                        name);
    }

}
