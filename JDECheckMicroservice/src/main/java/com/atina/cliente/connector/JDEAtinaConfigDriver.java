/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.cliente.connector;

import com.atina.cliente.exceptions.ConnectionException;
import com.atina.cliente.exceptions.ExternalConnectorException;
import com.atina.cliente.exceptions.InternalConnectorException;
import com.atina.cliente.service.ConnectorServiceImpl;
import com.atina.cliente.service.ConnectorServiceInterface; 
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc.JDEServiceBlockingStub;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class JDEAtinaConfigDriver {
    
    private static final Logger logger = LoggerFactory.getLogger(JDEAtinaConnector.class);
    
    private String jdeUser;
    private String jdePassword;
    private String jdeEnvironment;
    private String jdeRole;
    private Boolean wsConnection;
    private String microServiceName;
    private Integer microServicePort;
    
    private ConnectorServiceInterface service;
    private JDEAtinaConfiguracion configuracion;
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

    public void testConnect(final String jdeUser,
            final String jdePassword,
             final String jdeEnvironment,
             final String jdeRole,
             final Boolean wsConnection,
            final String microServiceName,
            final Integer microServicePort) throws ConnectionException {

        logger.debug("JDE ATILA Connector - Config - testConnect() - Begin: Testing connection...");

        this.connect(jdeUser, jdePassword, jdeEnvironment, jdeRole, wsConnection, microServiceName, microServicePort);

        this.disconnect();

        logger.debug("JDE ATILA Connector - Config - testConnect() - End: Testing connection.");

    }
    public void connect(final String jdeUser,
            final String jdePassword,
            final String jdeEnvironment,
            final String jdeRole,
            final Boolean wsConnection,
            final String microServiceName,
            final Integer microServicePort) throws ConnectionException {

        logger.debug("JDE ATILA Connector - Begin");

        // ----------------------------------------------
        // Check Input Parameters
        // ----------------------------------------------

        if (this.configuracion == null) {

            this.configuracion = new JDEAtinaConfiguracion(jdeUser, jdePassword, jdeEnvironment, jdeRole, "", wsConnection,
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

            //servicio.login(stub, this.configuracion, 0L);

            logger.info("JDE ATILA Connector - JDE Logged");

        } catch (ExternalConnectorException e) {

            startLogin = 0L;

            logger.error("JDE ATILA Connector - Error Connecting ..." + e.getMessage(), e);

            logger.debug("JDE ATILA Connector - End.");

            throw new ConnectionException("JDE ATILA Error connection: ", e);

        } catch (InternalConnectorException e) {

            startLogin = 0L;

            logger.error("JDE ATILA Connector - Error Connecting ..." + e.getMessage(), e);

            logger.debug("JDE ATILA Connector - End.");

            throw new ConnectionException("JDE ATILA Error connection: ", e);

        } catch (Exception e) {

            startLogin = 0L;

            logger.error("JDE ATILA Connector - Error Connecting ..." + e.getMessage(), e);

            logger.debug("JDE ATILA Connector - End.");

            throw new ConnectionException("JDE ATILA Error connection: ", e);
        }

        logger.debug("JDE ATILA Connector - End.");

    }
 
    public void disconnect() {

        logger.debug("JDE ATILA Connector - disconnecting...");

        // ----------------------------------------------
        // Check Input Parameters
        // ----------------------------------------------

        if (this.configuracion == null) {

            InternalConnectorException exception = new InternalConnectorException("No configuration available");

            throw new RuntimeException(new ConnectionException("JDE ATILA Error disconnecting: ", exception));

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

                //ConnectorServiceImpl servicio = (ConnectorServiceImpl) getService();

                //servicio.logout(stub, this.configuracion, 0L);

                logger.info("JDE ATILA Connector - JDE Disconnected");

            } catch (ExternalConnectorException e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error Disconnecting ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Connector - End.");

                throw new RuntimeException(new ConnectionException("JDE ATILA Error Disconnecting: ",
                         e));

            } catch (InternalConnectorException e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error Disconnecting ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Connector - End.");

                throw new RuntimeException(new ConnectionException("JDE ATILA Error Disconnecting: ",
                        e));

            } catch (Exception e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error Disconnecting ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Disconnecting - End.");

                throw new RuntimeException(new ConnectionException("JDE ATILA Error Disconnecting: ",
                         e));
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

                throw new RuntimeException(new ConnectionException("JDE ATILA Error checking is session is still connected: ",
                        exception));

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

                return servicio.isConnected(stub, this.configuracion, 0L);

            } catch (ExternalConnectorException e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error checking is session is still connected ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Connector - End.");

                throw new RuntimeException(new ConnectionException("JDE ATILA Error checking is session is still connected: ",
                         e));

            } catch (InternalConnectorException e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error checking is session is still connected ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Connector - End.");

                throw new RuntimeException(new ConnectionException("JDE ATILA Error checking is session is still connected: ",
                        e));

            } catch (Exception e) {

                startLogin = 0L;

                logger.error("JDE ATILA Connector - Error checking is session is still connected ..." + e.getMessage(), e);

                logger.debug("JDE ATILA Disconnecting - End.");

                throw new RuntimeException(new ConnectionException("JDE ATILA Error checking is session is still connected: ",
                        e));
            }

        }

        ConnectivityState st = channel.getState(true);

        logger.debug("JDE ATILA Connector - isConnected: " + st.toString());

        logger.debug("JDE ATILA Connector - End: isConnected.");

        return st == ConnectivityState.READY;

    } 
     
    public String connectionId() {

        return Long.toString(this.configuracion.getSessionID()); // one channel per host.

    }

    public JDEAtinaConfiguracion getConfiguracion() {
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

    public void setConfiguracion(JDEAtinaConfiguracion configuracion) {
        this.configuracion = configuracion;
    }

}
