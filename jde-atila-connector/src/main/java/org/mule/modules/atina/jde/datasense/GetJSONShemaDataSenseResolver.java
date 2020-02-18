package org.mule.modules.atina.jde.datasense;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
import org.mule.common.metadata.datatype.DataType;
import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Category which can differentiate between input or output MetaDataRetriever
 */
@MetaDataCategory
public class GetJSONShemaDataSenseResolver {

    private static final Logger logger = LoggerFactory.getLogger(JDEAtinaConnector.class);

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
        // Genera Metadata Input
        // ==================================================
        //

        DefaultMetaDataBuilder builder = new DefaultMetaDataBuilder();

        DynamicObjectBuilder<?> objectBuilder = builder.createDynamicObject(key.getDisplayName());

        objectBuilder.addSimpleField("JDE Token", DataType.STRING);

        objectBuilder.endDynamicObject();

        // ==================================================
        // Genera Metadata Output
        // ==================================================
        //

        metadataOutput = generateOutputMetaData(key);

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

        DefaultMetaDataBuilder builder = new DefaultMetaDataBuilder();

        DynamicObjectBuilder<?> objectBuilder = builder.createDynamicObject(key.getDisplayName());

        objectBuilder.addSimpleField("JDE Token", DataType.STRING);
        objectBuilder.addSimpleField("JSON Schema", DataType.STRING);

        objectBuilder.endDynamicObject();

        return new DefaultMetaData(builder.build());

    }

}
