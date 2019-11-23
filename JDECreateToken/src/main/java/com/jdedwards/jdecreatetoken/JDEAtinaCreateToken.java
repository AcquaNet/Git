/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jdedwards.jdecreatetoken;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.awt.Toolkit;
import java.io.Console;
import java.security.Key;
import java.util.Date;
import java.util.Scanner; 
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.awt.datatransfer.*;
 

/**
 *
 * @author jgodi
 */
public class JDEAtinaCreateToken {
 

    public void start(String[] args) throws Exception {

        // Create POJO
        Configuracion conf = new Configuracion();

        // Start Scanner
        Scanner scanner = new Scanner(System.in);

        // USER
        System.out.print("Enter JDE User: ");

        conf.setUser(scanner.next());

        // PWD
        Console console = System.console();

        String enteredPassword
                = new String(console.readPassword("Please enter JDE password: "));

        conf.setPassword(enteredPassword);

        // Environment
        System.out.print("Enter JDE Envirnment: ");

        conf.setEnvironment(scanner.next());

        // Role
        System.out.print("Enter JDE Role: ");

        conf.setRole(scanner.next());
  
        // Token
        String token = "";

        String secretKey = ".";
        
        System.out.println("---------------------------------------------------");
        System.out.println(conf.toString());
        System.out.println(conf.getPassword());
        System.out.println("---------------------------------------------------");
        
        boolean exit = false;

        while (!exit) {
            
            System.out.print("Enter Secret Key (Control + C to exit) (More than 40 characters): ");

            secretKey = scanner.next();
            
            System.out.println("Creating token....");

            if (secretKey.length() == 1) {

                secretKey = "123456789012345678901234567890123456789012345678901234567890";
                
                System.out.println("Creating token with default secret key: ");
                System.out.println(secretKey);
                 
            }
            else
            {
                System.out.println("Creating token with secret key: ");
                 System.out.println(secretKey);
            }

            if (secretKey.length() >= 40) {
                 
                
                token = getJWT(conf, secretKey);

                System.out.println("Token: [");
                System.out.println(token);
                System.out.println("]");
                
                Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
                
                StringSelection testData;
                
                testData = new StringSelection(token);
                
                c.setContents(testData, testData);
                
                exit = true;

            }

        }

        System.out.println("Done!");

    }

    private String getJWT(Configuracion config, String secretKey) {

        long ttlMillis = 0;

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret 
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId("1231231")
                .setIssuedAt(now)
                .setSubject("Subject")
                .setIssuer("Issue")
                .claim("user", config.getUser())
                .claim("password", config.getPassword())
                .claim("environment", config.getEnvironment())
                .claim("role", config.getRole())
                .claim("sessionId", config.getSessionId())
                .signWith(signingKey, signatureAlgorithm);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();

    }

    public static void main(String[] args) {

        try {
            
            JDEAtinaCreateToken mainApp = new JDEAtinaCreateToken();
            
            mainApp.start(args);
            
        } catch (Exception ex) {
            
            System.out.println(ex.getMessage());
            
        }

    }

}
