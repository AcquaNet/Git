package org.mule.modules.atina.jde.datasense;
 
import java.util.ArrayList;
import java.util.HashMap;
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
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.common.metadata.datatype.DataType;
import org.mule.modules.atina.jde.JDEAtinaConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Category which can differentiate between input or output MetaDataRetriever
 */
@MetaDataCategory
public class AuthenticateDataSenseResolver {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticateDataSenseResolver.class);

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

        logger.info("JDE Atina - AuthenticateDataSenseResolver - getMetaDataKeys ...");

        List<MetaDataKey> keys = new ArrayList<MetaDataKey>();

        // Generate the keys

        Map<String, String> operationsAsObject = new HashMap<String, String>();
        operationsAsObject.put("FromUserData", "Login Using User and Password");
        operationsAsObject.put("FromTokenData", "Login Using Token");

        for (String key : operationsAsObject.keySet()) {
            logger.info("JDE Atina - WSDataSenseResolver -                  WS: " + key + " Valor Operacion: "
                    + operationsAsObject.get(key));
            keys.add(new DefaultMetaDataKey(key, operationsAsObject.get(key)));
        }

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
    public MetaData getMetaData(MetaDataKey entityKey) throws Exception {

        logger.info("JDE Atina - WSDataSenseResolver - GetMetaData for key: [" + entityKey.getId() + "] Display Name: ["
                + entityKey.getDisplayName() + "]");

        metadataOutput = null;

        // ==================================================
        // Recupera Metadata del Servicio
        // ==================================================
        //

        if ("FromUserData".equals(entityKey.getId())) {

            MetaDataModel authorModel = new DefaultMetaDataBuilder().createDynamicObject("Login")
                    .addSimpleField("JDE User", DataType.STRING)
                    .addSimpleField("JDE Password", DataType.STRING)
                    .addSimpleField("JDE Environment", DataType.STRING)
                    .addSimpleField("JDE Role", DataType.STRING)
                    .addSimpleField("Session Id", DataType.INTEGER)
                    .build();
            return new DefaultMetaData(authorModel);

        }

        if ("FromTokenData".equals(entityKey.getId())) {
            MetaDataModel authorModel = new DefaultMetaDataBuilder().createDynamicObject("Login")
                    .addSimpleField("JDE Token", DataType.STRING)
                    .build();
            return new DefaultMetaData(authorModel);

        }

        metadataOutput = generateOutputMetaData(entityKey);
        
        return null;

    }

    @MetaDataOutputRetriever
    public MetaData getOutputMetaData(final MetaDataKey entityKey) throws Exception {

    	if (metadataOutput != null) {
            return metadataOutput;
        } else {
            return generateOutputMetaData(entityKey);
        }

    }
    
	private MetaData generateOutputMetaData(final MetaDataKey entityKey) throws Exception {

		logger.info("JDE Atina - WSDataSenseResolver - GetOutputMetaData for key: [" + entityKey.getId()
				+ "] Display Name: [" + entityKey.getDisplayName() + "]");

		metadataOutput = null;

		// ==================================================
		// Recupera Metadata del Servicio
		// ==================================================
		//

		MetaDataModel authorModel = new DefaultMetaDataBuilder().createDynamicObject("Login")
				.addSimpleField("Token", DataType.STRING).build();

		return new DefaultMetaData(authorModel);

	}

}
