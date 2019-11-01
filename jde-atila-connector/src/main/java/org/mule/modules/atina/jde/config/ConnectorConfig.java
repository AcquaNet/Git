package org.mule.modules.atina.jde.config;

import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;

import java.util.concurrent.TimeUnit;

import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.extension.annotations.param.Optional;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.atina.jde.exceptions.InternalConnectorException;
import org.mule.modules.atina.jde.implementations.ConnectorServiceImpl;
import org.mule.modules.atina.jde.interfaces.ConnectorServiceInterface;
import org.mule.modules.atina.jde.models.JDEAtilaConfiguracion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc.JDEServiceBlockingStub;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@ConnectionManagement(friendlyName = "Configuration", configElementName = "config")
public class ConnectorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ConnectorConfig.class);

    /**
     * JDE User
     */
    @Placement(group = "JDE Configuration", order = 1)
    @FriendlyName("JDE User")
    private String jdeUser;

    /**
     * JDE Password
     */
    @Placement(group = "Configuracion de Login", order = 7)
    @Password
    @FriendlyName("JDE Password")
    private String jdePassword;

    /**
     * JDE Environment
     */
    @Placement(group = "JDE Configuration", order = 3)
    @FriendlyName("JDE Environment")
    private String jdeEnvironment;

    /**
     * JDE Environment
     */
    @Placement(group = "JDE Configuration", order = 4)
    @FriendlyName("JDE Role")
    private String jdeRole;

    /**
     * JDE WS Connection
     */
    @Placement(group = "JDE Configuration", order = 6)
    @FriendlyName("WS Connection")
    private Boolean wsConnection;

    /**
     * Service Server Name
     */
    @Placement(tab = "Service", group = "Service", order = 0)
    @FriendlyName("ServerName")
    private String microServiceName;

    /**
     * Service Server Name
     */
    @Placement(tab = "Service", group = "Service", order = 1)
    @FriendlyName("ServerPort")
    private Integer microServicePort;

    private ConnectorServiceInterface service;
    private JDEAtilaConfiguracion configuracion;
    private ManagedChannel channel;
    private JDEServiceBlockingStub stub;

    private long startLogin = 0L;

    public String getJdeUser() {
        return jdeUser;
    }

    public void setJdeUser(String jdeUser) {
        this.jdeUser = jdeUser;
    }

    public String getJdePassword() {
        return jdePassword;
    }

    public void setJdePassword(String jdePassword) {
        this.jdePassword = jdePassword;
    }

    public String getJdeEnvironment() {
        return jdeEnvironment;
    }

    public void setJdeEnvironment(String jdeEnvironment) {
        this.jdeEnvironment = jdeEnvironment;
    }

    public String getJdeRole() {
        return jdeRole;
    }

    public void setJdeRole(String jdeRole) {
        this.jdeRole = jdeRole;
    }

    public Boolean getWsConnection() {
        return wsConnection;
    }

    public void setWsConnection(Boolean wsConnection) {
        this.wsConnection = wsConnection;
    }

    public String getMicroServiceName() {
        return microServiceName;
    }

    public void setMicroServiceName(String microServiceName) {
        this.microServiceName = microServiceName;
    }

    public Integer getMicroServicePort() {
        return microServicePort;
    }

    public void setMicroServicePort(Integer microServicePort) {
        this.microServicePort = microServicePort;
    }

    @TestConnectivity
    public void testConnect(@ConnectionKey final String jdeUser,
            @Password final String jdePassword,
            @ConnectionKey final String jdeEnvironment,
            @ConnectionKey final String jdeRole,
            @ConnectionKey final Boolean wsConnection,
            @ConnectionKey final String microServiceName,
            @ConnectionKey final Integer microServicePort) throws ConnectionException {

        logger.debug("JDE ATILA Connector - Config - testConnect() - Begin: Testing connection...");

        this.connect(jdeUser, jdePassword, jdeEnvironment, jdeRole, wsConnection, microServiceName, microServicePort);

        this.disconnect();

        logger.debug("JDE ATILA Connector - Config - testConnect() - End: Testing connection.");

    }

    @Connect
    public void connect(@ConnectionKey final String jdeUser,
            @Password final String jdePassword,
            @ConnectionKey final String jdeEnvironment,
            @ConnectionKey final String jdeRole,
            @ConnectionKey final Boolean wsConnection,
            @ConnectionKey final String microServiceName,
            @ConnectionKey final Integer microServicePort) throws ConnectionException {

        logger.debug("JDE ATILA Connector - Begin");

        // ----------------------------------------------
        // Check Input Parameters
        // ----------------------------------------------

        if (this.configuracion == null) {

            this.configuracion = new JDEAtilaConfiguracion(jdeUser, jdePassword, jdeEnvironment, jdeRole, "", wsConnection,
                    microServiceName, microServicePort);

        }

        logger.info("JDE ATILA Connector - Configuracion: " + this.configuracion.toString());

        try {

            startLogin = System.currentTimeMillis();

            logger.info("JDE ATILA Connector - Connectando a DragonFish Service Server");

            channel = ManagedChannelBuilder
                    .forAddress(configuracion.getMicroServiceName(), configuracion.getMicroServicePort())
                    .usePlaintext()
                    .build();

            stub = JDEServiceGrpc.newBlockingStub(channel);

            logger.info("JDE ATILA Connector - Login to JDE...");

            ConnectorServiceImpl servicio = (ConnectorServiceImpl) getService();

            servicio.login(stub, this.configuracion);

            logger.info("JDE ATILA Connector - JDE Logged");

        } catch (ExternalConnectorException e) {

            startLogin = 0L;

            logger.error("JDE ATILA Connector - Error Connecting ..." + e.getMessage(), e);

            logger.debug("JDE ATILA Connector - End.");

            throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error connection: ", e.getMessage(), e);

        } catch (InternalConnectorException e) {

            startLogin = 0L;

            logger.error("JDE ATILA Connector - Error Connecting ..." + e.getMessage(), e);

            logger.debug("JDE ATILA Connector - End.");

            throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error connection: ", e.getMessage(), e);

        } catch (Exception e) {

            startLogin = 0L;

            logger.error("JDE ATILA Connector - Error Connecting ..." + e.getMessage(), e);

            logger.debug("JDE ATILA Connector - End.");

            throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error Connection: ", e.getMessage(), e);
        }

        logger.debug("JDE ATILA Connector - End.");

    }

    @Disconnect
    public void disconnect() {

        logger.debug("JDE ATILA Connector - disconnecting...");

        // ----------------------------------------------
        // Check Input Parameters
        // ----------------------------------------------

        if (this.configuracion == null) {

            InternalConnectorException exception = new InternalConnectorException("No configuration available");

            throw new RuntimeException(new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error disconnecting: ", exception.getErrorMessage(), exception));

        }

        logger.info("JDE ATILA Connector - Current Configuration: " + this.configuracion.toString());

        if (configuracion.getSessionID() != 0) {

            try {

                channel = ManagedChannelBuilder
                        .forAddress(configuracion.getMicroServiceName(), configuracion.getMicroServicePort())
                        .usePlaintext()
                        .build();

                stub = JDEServiceGrpc.newBlockingStub(channel);

                logger.info("JDE ATILA Connector - Disconnecting from JDE...");

                ConnectorServiceImpl servicio = (ConnectorServiceImpl) getService();

                servicio.logout(stub, this.configuracion);

                logger.info("JDE ATILA Connector - JDE Disconnected");

            } catch (ExternalConnectorException e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error Disconnecting ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Connector - End.");

                throw new RuntimeException(new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error Disconnecting: ",
                        e.getMessage(), e));

            } catch (InternalConnectorException e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error Disconnecting ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Connector - End.");

                throw new RuntimeException(new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error Disconnecting: ",
                        e.getMessage(), e));

            } catch (Exception e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error Disconnecting ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Disconnecting - End.");

                throw new RuntimeException(new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error Disconnecting: ",
                        e.getMessage(), e));
            }

        }

        logger.debug("JDE ATILA Connector - Begin: Disconnecting Channel.");

        try {

            channel.shutdown()
                    .awaitTermination(10, TimeUnit.SECONDS);

        } catch (InterruptedException ex) {

            channel.shutdownNow();

        }

        logger.debug("JDE ATILA Connector - End: disconnect.");

    }

    @ValidateConnection
    public boolean isConnected() {

        logger.debug("JDE ATILA Connector - Begin: isConnected.");

        if (channel == null) {

            logger.debug("JDE ATILA Connector - End: isConnected. Channel is null");

            return false;
        }

        // Verificar si el token sigue vigente

        if (this.startLogin > 0) {

            logger.debug("JDE ATILA Connector - isConnected...");

            // ----------------------------------------------
            // Check Input Parameters
            // ----------------------------------------------

            if (this.configuracion == null) {

                InternalConnectorException exception = new InternalConnectorException("No configuration available");

                throw new RuntimeException(new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error checking is session is still connected: ",
                        exception.getErrorMessage(), exception));

            }

            logger.info("JDE ATILA Connector - Current Configuration: " + this.configuracion.toString());

            try {

                channel = ManagedChannelBuilder
                        .forAddress(configuracion.getMicroServiceName(), configuracion.getMicroServicePort())
                        .usePlaintext()
                        .build();

                stub = JDEServiceGrpc.newBlockingStub(channel);

                logger.info("JDE ATILA Connector - Checking is session is still connected...");

                ConnectorServiceImpl servicio = (ConnectorServiceImpl) getService();

                logger.info("JDE ATILA Connector - Session is still connected Done");

                return servicio.isConnected(stub, this.configuracion);

            } catch (ExternalConnectorException e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error checking is session is still connected ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Connector - End.");

                throw new RuntimeException(new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error checking is session is still connected: ",
                        e.getMessage(), e));

            } catch (InternalConnectorException e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error checking is session is still connected ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Connector - End.");

                throw new RuntimeException(new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error checking is session is still connected: ",
                        e.getMessage(), e));

            } catch (Exception e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error checking is session is still connected ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Disconnecting - End.");

                throw new RuntimeException(new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "JDE ATILA Error checking is session is still connected: ",
                        e.getMessage(), e));
            }

        }

        ConnectivityState st = channel.getState(true);

        logger.debug("JDE ATILA Connector - isConnected: " + st.toString());

        logger.debug("JDE ATILA Connector - End: isConnected.");

        return st == ConnectivityState.READY;

    }

    @ConnectionIdentifier
    public String connectionId() {

        return Long.toString(this.configuracion.getSessionID()); // one channel per host.

    }

    public JDEAtilaConfiguracion getConfiguracion() {
        return configuracion;
    }

    /**
     * @return the service
     */
    public ConnectorServiceInterface getService() {

        if (service == null) {

            service = new ConnectorServiceImpl();
        }

        return service;
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public JDEServiceBlockingStub getStub() {
        return stub;
    }

    public void setConfiguracion(JDEAtilaConfiguracion configuracion) {
        this.configuracion = configuracion;
    }

}