package org.mule.modules.atila.jde.config;

import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.display.Summary;

import java.util.concurrent.TimeUnit;

import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.modules.atila.jde.implementations.ConnectorServiceImpl;
import org.mule.modules.atila.jde.interfaces.ConnectorServiceInterface;
import org.mule.modules.atila.jde.models.JDEAtilaConfiguracion;
import org.mule.modules.connector.exceptions.InternalConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acqua.dragonfishserverwp.servicios.DragonFishServiceGrpc;
import com.acqua.dragonfishserverwp.servicios.DragonFishServiceGrpc.DragonFishServiceBlockingStub;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@ConnectionManagement(friendlyName = "Configuration", configElementName = "config")
public class ConnectorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ConnectorConfig.class);

    /**
     * Nombre del Servidor de DragonFish
     */
    @Placement(group = "Servidor Magento", order = 1)
    @FriendlyName("URL base")
    private String urlBase;

    /**
     * Codigo del Cliente
     */
    @Placement(group = "Configuracion de Cliente", order = 3)
    @FriendlyName("Codigo")
    private String codigoConfCliente;

    /**
     * Clave Privada
     */
    @Placement(group = "Configuracion de Cliente", order = 4)
    @FriendlyName("Clave Privada")
    private String clavePrivadaConfCliente;

    /**
     * Uusario
     */
    @Placement(group = "Configuracion de Login", order = 6)
    @FriendlyName("Usuario")
    private String user;

    /**
     * Clave
     */
    @Placement(group = "Configuracion de Login", order = 7)
    @Password
    @FriendlyName("Password")
    private String password;

    /**
     * Expiracion
     */
    @Placement(group = "Configuracion de Login", order = 8)
    @FriendlyName("Expiracion del Token")
    @Summary("Tiempo en minutos de vigencia del token")
    private Integer expiracion;

    /**
     * Service Server Name
     */
    @Placement(tab = "Servicio", group = "Servidor", order = 0)
    @FriendlyName("Servidor")
    private String servidorServicio;

    /**
     * Service Server Name
     */
    @Placement(tab = "Servicio", group = "Servidor", order = 1)
    @FriendlyName("puertoServicio")
    private Integer puertoServicio;

    private ConnectorServiceInterface service;
    private JDEAtilaConfiguracion configuracion;
    private ManagedChannel channel;
    private DragonFishServiceBlockingStub stub;

    private long startLogin = 0L;

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public String getCodigoConfCliente() {
        return codigoConfCliente;
    }

    public void setCodigoConfCliente(String codigoConfCliente) {
        this.codigoConfCliente = codigoConfCliente;
    }

    public String getClavePrivadaConfCliente() {
        return clavePrivadaConfCliente;
    }

    public void setClavePrivadaConfCliente(String clavePrivadaConfCliente) {
        this.clavePrivadaConfCliente = clavePrivadaConfCliente;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(Integer expiracion) {
        this.expiracion = expiracion;
    }

    public String getServidorServicio() {
        return servidorServicio;
    }

    public void setServidorServicio(String servidorServicio) {
        this.servidorServicio = servidorServicio;
    }

    public Integer getPuertoServicio() {
        return puertoServicio;
    }

    public void setPuertoServicio(Integer puertoServicio) {
        this.puertoServicio = puertoServicio;
    }

    @TestConnectivity
    public void testConnect(@ConnectionKey final String urlBase,
            @ConnectionKey final String codigoConfCliente, @ConnectionKey final String clavePrivadaConfCliente,
            @ConnectionKey final String algoritmo, @ConnectionKey final String user, @Password final String password,
            @ConnectionKey final Integer expiracion, @ConnectionKey final String servidorServicio,
            @ConnectionKey final Integer puertoServicio) throws ConnectionException {

        logger.debug("DRAGONFISH Connector - Config - testConnect() - Begin: Testing connection...");

        this.connect(urlBase, codigoConfCliente, clavePrivadaConfCliente, algoritmo, user, password, expiracion, servidorServicio, puertoServicio);

        this.disconnect();

        logger.debug("DragonFish Connector - Config - testConnect() - End: Testing connection.");

    }

    @Connect
    public void connect(@ConnectionKey final String urlBase,
            @ConnectionKey final String codigoConfCliente, @ConnectionKey final String clavePrivadaConfCliente,
            @ConnectionKey final String algoritmo, @ConnectionKey final String user, @Password final String password,
            @ConnectionKey final Integer expiracion, @ConnectionKey final String servidorServicio,
            @ConnectionKey final Integer puertoServicio) throws ConnectionException {

        logger.debug("DragonFish Connector - Inicio: connect ...");

        // ----------------------------------------------
        // Check Input Parameters
        // ----------------------------------------------

        this.configuracion = new JDEAtilaConfiguracion(urlBase, codigoConfCliente, clavePrivadaConfCliente,
                algoritmo, user, password, expiracion, servidorServicio, puertoServicio);

        logger.info("DragonFish Connector - Configuracion: " + this.configuracion.toString());

        try {

            startLogin = System.currentTimeMillis();

            logger.info("DragonFish Connector - Connectando a DragonFish Service Server");

            channel = ManagedChannelBuilder
                    .forAddress(configuracion.getServidorServicio(), configuracion.getPuertoServicio())
                    .usePlaintext()
                    .build();

            stub = DragonFishServiceGrpc.newBlockingStub(channel);

            logger.info("DragonFish Connector - Logeando a DragonFish");

            ConnectorServiceImpl servicio = (ConnectorServiceImpl) getService();

            servicio.login(stub, this.configuracion);

            logger.info("DragonFish Connector - Logeado en DragonFish");

        } catch (InternalConnectorException e) {

            startLogin = 0L;

            logger.error("DragonFish Connector - Error Conectando a DragonServer ..." + e.getMessage(), e);

            throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "DragonFish Error en Conexion", e.getMessage(), e);
        }

        logger.debug("DragonFish Connector - Fin: connect.");

    }

    @Disconnect
    public void disconnect() throws RuntimeException {

        logger.debug("DragonFish Connector - Begin: disconnect.");

        try {

            channel.shutdown()
                    .awaitTermination(10, TimeUnit.SECONDS);

        } catch (InterruptedException ex) {

            channel.shutdownNow();

        }

        logger.debug("DragonFish Connector - Fin: disconnect.");

    }

    @ValidateConnection
    public boolean isConnected() {

        logger.debug("DragonFish Connector - Begin: isConnected.");

        if (channel == null) {

            logger.debug("DragonFish Connector - End: isConnected. Channel is null");

            return false;
        }

        // Verificar si el token sigue vigente

        if (this.startLogin > 0) {

            long current = System.currentTimeMillis();

            double calc = (current - this.startLogin) / 1000d;

            Integer seconds = new Integer((int) calc);

            if (seconds.compareTo(configuracion.getExpiracion()) > 0) {

                logger.debug("DragonFish Connector - isConnected. Falso. Token expiro despues de " + Integer.toString(seconds) + " segundos");

                return false;

            }
            else
            {
                logger.debug("DragonFish Connector - isConnected. Token aun activo");
            }

        }

        ConnectivityState st = channel.getState(true);

        logger.debug("DragonFish Connector - isConnected: " + st.toString());

        logger.debug("DragonFish Connector - End: isConnected.");

        return st == ConnectivityState.READY;

    }

    @ConnectionIdentifier
    public String connectionId() {

        return this.configuracion.getUrlBase(); // one channel per host.

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

    public DragonFishServiceBlockingStub getStub() {
        return stub;
    }

}