/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeconnectorserververificador;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.slf4j.LoggerFactory;
 
/**
 *
 * @author jgodi
 */
public class Licenciador {

     private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Licenciador.class);
     
    public Licenciador() {
    }
    
    public boolean verificarLicencia(String fileClavePublica, String licencia) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, Exception {
    
        AsymmetricCryptography ac = new AsymmetricCryptography();
        
        // ------------------------------------
        // Leer Clave Publica
        // ------------------------------------
        
        PublicKey clavePublica = ac.getPublic(fileClavePublica);
         
        // ------------------------------------
        // Leer Licencia
        // ------------------------------------
        
        String desencriptado = ac.decryptText(licencia, clavePublica);
         
        // --------------------------------------------
        // Descerializacion
        // --------------------------------------------
        
        ObjectMapper mapper = new ObjectMapper();
         
        Licencia jsonInString = mapper.readValue(desencriptado, Licencia.class);
        
        logger.info("Licencia: " + licencia);
        logger.info("Vencimiento: " + jsonInString.getFechaDeVigenciaFormateada());
        
        // --------------------------------------------
        // Crea la fecha hoy
        // --------------------------------------------
        
         Calendar hoy = Calendar.getInstance();
          
         return hoy.compareTo(jsonInString.getFechaDeVigencia())<0;
           
    }
     
    private String getFechaDeVigenciaFormateada(Calendar fechaDeVigencia) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(fechaDeVigencia.getTime());
    }

}
