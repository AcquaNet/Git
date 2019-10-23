/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.createlicense;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.NoSuchProviderException;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class JDECreateLicense {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JDECreateLicense.class);

    private AsymmetricCryptography ac;
    private GenerateKeys gk;
    private Licencia nuevaLicencia;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    @Option(name = "-help", usage = "Ayuda")
    public boolean help;

    @Option(name = "-codCliente", usage = "Codigo Del Cliente")
    public String codCliente;

    @Option(name = "-fechaVencimiento", usage = "Fecha de Vencimiento")
    public String fechaVencimiento;

    public JDECreateLicense() throws NoSuchAlgorithmException, NoSuchPaddingException {

        ac = new AsymmetricCryptography();

    }

    public void cargarPublicaPrivada() throws NoSuchAlgorithmException, NoSuchProviderException, IOException {

        boolean existeClave = true;
        try {
            privateKey = ac.getPrivate("KeyPair/privateKey");
        } catch (Exception ex) {
            existeClave = false;
        }

        try {
            publicKey = ac.getPublic("KeyPair/swagger.dat");
        } catch (Exception ex) {
            existeClave = false;
        }

        // --------------------------------------------
        // Generacion de Claves
        // --------------------------------------------
        //
        if (!existeClave) {

            gk = new GenerateKeys(1024);

            gk.createKeys();

            gk.writeToFile("KeyPair/publickey.dat", gk.getPublicKey().getEncoded());

            gk.writeToFile("KeyPair/privateKey", gk.getPrivateKey().getEncoded());

        }

    }

    public String createLicense(String codCliente, String fechaVencimiento, String nota) throws ParseException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, Exception {

        // --------------------------------------------
        // Creacion de la Fecha de Vencimiento
        // --------------------------------------------
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        String dateInString = fechaVencimiento;

        Date fechaVencimientoAsDate = null;

        fechaVencimientoAsDate = formatter.parse(dateInString);

        // --------------------------------------------
        // Creacion del Calendar
        // --------------------------------------------
        Calendar cal = Calendar.getInstance();

        cal.setTime(fechaVencimientoAsDate);

        // --------------------------------------------
        // Creacion del Objecto Licencia
        // --------------------------------------------
        nuevaLicencia = new Licencia(cal);

        // --------------------------------------------
        // Serializacion de la Licencia
        // --------------------------------------------
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(nuevaLicencia);

        // --------------------------------------------
        // Encriptacion con la clave privada
        // --------------------------------------------
        AsymmetricCryptography ac = new AsymmetricCryptography();

        PrivateKey privateKey = ac.getPrivate("KeyPair/privateKey");
        PublicKey publicKey = ac.getPublic("KeyPair/publickey.dat");

        String encrypted_msg = ac.encryptText(jsonInString, privateKey);

        return encrypted_msg;

    }

    public boolean verificarLicencia(String mensajeEncriptado) throws NoSuchAlgorithmException, NoSuchPaddingException, Exception {

        AsymmetricCryptography ac = new AsymmetricCryptography();

        PublicKey publicKey = null;

        publicKey = ac.getPublic("KeyPair/publicKey");

        String decrypted_msg = ac.decryptText(mensajeEncriptado, publicKey);

        ac.writeToFile(new File("KeyPair/jdelicencia.lic"), decrypted_msg.getBytes());

        return true;
    }

    public String procesar(String[] args) throws FileNotFoundException, IOException {
        
        
        OutputStream output = new FileOutputStream("jdelicence.env");

        Properties prop = new Properties();
 
        
        // ================================================
        // Parseo de Comandos
        // ================================================
        //

        String claveEncriptada = "";

        CmdLineParser parser = new CmdLineParser(this);

        // parse the arguments.
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            mostrarAyuda();
            logger.debug("Error leyendo parametros" + e.getMessage());
            System.exit(0);
        }

        if (help) {
            mostrarAyuda();
            System.exit(0);
        }

        if (codCliente == null) {
            mostrarAyuda();
            System.exit(0);
        }

        logger.debug("----------------------------------------------------------------");
        logger.debug("Generando Clave:");
        logger.debug("   Codigo de Cliente: " + codCliente);
        
        prop.setProperty("SWAGGER_SERVER_LICENCIA_CLIENTE", codCliente);

        if (fechaVencimiento == null) {
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DATE, 30);
            Date d = c.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            fechaVencimiento = formatter.format(d);
            
            prop.setProperty("SWAGGER_SERVER_LICENCIA_VTO", fechaVencimiento);

        }

        logger.debug("   Fecha de Vencimiento: " + fechaVencimiento);

        try {

            cargarPublicaPrivada();

            claveEncriptada = createLicense(codCliente, fechaVencimiento, "Clave Creada");

            logger.debug("Clave: [" + claveEncriptada + "]");

        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (NoSuchPaddingException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (NoSuchProviderException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        Licencia claveDesencriptada = null;

        Licenciador lic = new Licenciador();

        try {

            claveDesencriptada = lic.verificarLicencia("KeyPair/publickey.dat", claveEncriptada);

            logger.debug("Clave Desencriptada: [" + claveDesencriptada.toString() + "]");

        } catch (NoSuchPaddingException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (InvalidKeyException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (IllegalBlockSizeException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (BadPaddingException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        // set the properties value
        prop.setProperty("SWAGGER_SERVER_LICENCE_KEY", claveEncriptada);

        // save properties to project root folder
        prop.store(output, null);

        
        return claveEncriptada;

    }

    private void mostrarAyuda() {

        logger.debug("----------------------------------------------------------------");
        logger.debug("Usar: ");
        logger.debug(" java -jar CreadorDeLicencias-1.0.0.jar -help -codCliente 'CLIENTE' [-fechaVencimiento 'dd-MM-yyyy']");
        logger.debug("----------------------------------------------------------------");

    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, FileNotFoundException, IOException {

        JDECreateLicense licMaker = new JDECreateLicense();

        String clave = licMaker.procesar(args);

        System.out.println(clave);  

    }

}
