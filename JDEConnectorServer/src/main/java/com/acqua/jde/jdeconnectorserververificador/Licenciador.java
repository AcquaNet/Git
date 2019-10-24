/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.jde.jdeconnectorserververificador;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
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
    
    public String getLicense(String codCliente) {
         
        String crunchifyResponse = "";
        
        String link = "https://raw.githubusercontent.com/AcquaNet/licenses/master/" + codCliente + ".dat";
        
        URL crunchifyUrl = null;
        
         try {
             
             crunchifyUrl = new URL(link);

             HttpURLConnection crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();

             Map<String, List<String>> crunchifyHeader = crunchifyHttp.getHeaderFields();

             // If URL is getting 301 and 302 redirection HTTP code then get new URL link.
             // This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
             for (String header : crunchifyHeader.get(null)) {
                 if (header.contains(" 302 ") || header.contains(" 301 ")) {
                     link = crunchifyHeader.get("Location").get(0);
                     crunchifyUrl = new URL(link);
                     crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
                     crunchifyHeader = crunchifyHttp.getHeaderFields();
                 }
             }
             InputStream crunchifyStream = crunchifyHttp.getInputStream();
             
             crunchifyResponse = crunchifyGetStringFromStream(crunchifyStream);
               
             
         } catch (MalformedURLException ex) {
             Logger.getLogger(Licenciador.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(Licenciador.class.getName()).log(Level.SEVERE, null, ex);
         }
		 
         return crunchifyResponse;
         
    }
    
    
    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
		if (crunchifyStream != null) {
			Writer crunchifyWriter = new StringWriter();
 
			char[] crunchifyBuffer = new char[2048];
			try {
				Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
				int counter;
				while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
					crunchifyWriter.write(crunchifyBuffer, 0, counter);
				}
			} finally {
				crunchifyStream.close();
			}
			return crunchifyWriter.toString();
		} else {
			return "No Contents";
		}
	}
    
    

}
