/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.createlicense;

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

 

/**
 *
 * @author jgodi
 */
public class Licenciador {

    public Licenciador() {
    }
    
    public Licencia verificarLicencia(String fileClavePublica, String licencia) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, Exception {
    
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
        
        return jsonInString;
         
        
    }
    
    
    public static void main(String[] args){
        
        try {
            new Licenciador().verificarLicencia("KeyPair/publicKey", "iThTNvJVCNOpPvM8zz2kBXZArtM8Uiy8nULKVy/L3b0WhDV23tg7uEp/TjxxuPV5ZRWgiQjYlb9z1Pb02t0DNZ+x3eeskq3e9qWMFUzJH/fQK0meMVUFinayQWCfhuPUkcuvrKtAEHprA8qKlYCCjSnyGEQPLPc3fqZeQjGgm5Q=");
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Licenciador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Licenciador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Licenciador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Licenciador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Licenciador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Licenciador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       
    private String getFechaDeVigenciaFormateada(Calendar fechaDeVigencia) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(fechaDeVigencia.getTime());
    }

}
