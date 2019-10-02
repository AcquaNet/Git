/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.buildjdebundle.metadata;

import com.atina.buildjdebundle.exceptions.MetadataServerException;
import com.atina.metadata.models.Model;
import com.atina.metadata.models.Models;
import com.atina.metadata.models.Operation;
import com.atina.metadata.models.Operations;
import com.atina.metadata.models.Parameter;
import com.atina.metadata.models.ModelType; 
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import static com.github.javaparser.ast.internal.Utils.isNullOrEmpty;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author jgodi
 */
public class MetadataWSGenerator {
    
    private static final Logger logger = LoggerFactory.getLogger(MetadataWSGenerator.class);
    
    private static String PUBLISHED_WS = "PublishedBusinessService";
    private static String VALUE_OBJECT = "ValueObject"; 
    
    private static String WS_JSON = "ws.json";
    private static String VO_JSON = "vo.json"; 
     
    private final static Map<String, String> primtiveLookup = new HashMap<>();
    
    private boolean bPublishedBusinessService;
    private boolean bValueObject;
    private static Operations operaciones;
    private static Models modelos;
    private String transaction;
    private Model modelo;
    private File srcFile; 
    private String packageName; 
    private List<ImportDeclaration> imports;
    
    static {
        // Hack for including primtive arrays but I'm lazy and this should work fine.
        primtiveLookup.put("int" , int.class.getName());
        primtiveLookup.put("short" , short.class.getName());
        primtiveLookup.put("float" , float.class.getName());
        primtiveLookup.put("double" , double.class.getName());
        primtiveLookup.put("long" , long.class.getName());
        primtiveLookup.put("boolean" , boolean.class.getName());
        primtiveLookup.put("char"  , char.class.getName());
        primtiveLookup.put("byte"  , byte.class.getName());
        primtiveLookup.put("Integer" , Integer.class.getName());
        primtiveLookup.put("Short" , Short.class.getName());
        primtiveLookup.put("Float" , Float.class.getName());
        primtiveLookup.put("Double" , Double.class.getName());
        primtiveLookup.put("Long" , Long.class.getName());
        primtiveLookup.put("Boolean" , Boolean.class.getName());
        primtiveLookup.put("Character"  , Character.class.getName());
        primtiveLookup.put("Byte"  , Byte.class.getName());
        primtiveLookup.put("String" , String.class.getName());
        
    }

    public MetadataWSGenerator() {
        
        operaciones = new Operations();
        
         modelos = new Models(); 
        
    }
      
    public void generateMetadata(String classFile) throws MetadataServerException {
        
        
        logger.info("Metadata Generartor for: " + classFile);
        
        // -------------------------------
        // Reset variables
        // ------------------------------- 
        
        imports = null;
        transaction = null;
        modelo = null;
        srcFile = new File(classFile);
        bPublishedBusinessService = false;
        bValueObject = false;
        packageName = "";
        
        // -------------------------------
        // Process File
        // ------------------------------- 
          
        processJDESrcFile(srcFile, operaciones,  modelos);
        
        logger.info("      File Processed: " + classFile);
        
        
    }
    
    public void processJDESrcFile(File srcFile, Operations operaciones, Models modelos) throws MetadataServerException {

        logger.info("****************************************************************");
        logger.info("* PROCESSING SOURCE FILE                                       *");
        logger.info("****************************************************************");
        logger.info("Source File:" + srcFile.getAbsolutePath());
        
        try {
            
            // Read Src Code
            FileInputStream in = new FileInputStream(srcFile);

            // Parse the file
            CompilationUnit cu = JavaParser.parse(in);

            // visit class declaration
            cu.accept(new packageVisitor(), null);
            
            // visit class declaration
            cu.accept(new ClassVisitor(), null);

            // visit methods names
            cu.accept(new MethodVisitor(), null);
            
            if(modelo != null)
            {
                modelos.AddModelo(modelo);
            }
             
            
        }  catch (Exception ex) {

            logger.error("Error getting metadata. Class :" + srcFile.getName());

            throw new MetadataServerException("Error getting metadata. Class :" + srcFile.getName(), ex);

        }
    
        
    } 
    
    private class packageVisitor extends DFDumpVisitor {
 
        private packageVisitor() {
            super(false);  
        }
        
        @Override
        public void visit(final CompilationUnit n, final Object arg) {
 
            packageName = n.getPackage().getName().toString();
            
            logger.info("Package: " + packageName);
            
            if (n.getImports() != null) {
                
                imports = n.getImports();
                  
            }

        } 

    }

    private class ClassVisitor extends DFDumpVisitor {
 
        private ClassVisitor() {
            super(false);  
        }
        
        @Override
        public void visit(final ClassOrInterfaceDeclaration n, final Object arg) {

            logger.info("Processing Class: " + n.getName());

            bPublishedBusinessService = false;
            bValueObject = false;
                    
            if (!isNullOrEmpty(n.getExtends())) {
                
                logger.info(" extends ");
                
                for (final Iterator<ClassOrInterfaceType> i = n.getExtends().iterator(); i.hasNext();) {
                    
                    final ClassOrInterfaceType c = i.next();
                    
                    logger.info("    " + c.getName());
                    
                    if(!bPublishedBusinessService)
                    {
                        bPublishedBusinessService = c.getName().equals(PUBLISHED_WS);
                    } 
                    
                    if(!bValueObject)
                    {
                        bValueObject = c.getName().equals(VALUE_OBJECT);
                    } 
                    
                   c.accept(this, arg);
                    
                }
            }
            
            if(bPublishedBusinessService)
            {
                transaction = n.getName().toString(); 
            }
            
            if(bValueObject)
            {
                modelo = new Model();
                modelo.setModelPackage(packageName);
                modelo.setModelName(n.getName());
                
            }
            

        } 

    }
    
    private class MethodVisitor extends DFDumpVisitor {

        private MethodVisitor() {
            super(false); 
        } 
        
        @Override
        public void visit(final MethodDeclaration n, final Object arg) {
 
            if (bPublishedBusinessService) {
                 
                if (ModifierSet.isProtected(n.getModifiers())) {
                    
                    logger.info("Processing Method: " + srcFile.getName() + "_" + n.getName());
                    logger.info("     Return Value: " + n.getType());
                    logger.info("     Modiffiers: " + ModifierSet.getAccessSpecifier(n.getModifiers()).getCodeRepresenation());

                    Operation operacion = new Operation();
 
                    operacion.setOperationModelPackage(packageName);
                    operacion.setOperationClass(transaction);
                    operacion.setOperationMethod(n.getName());
                    operacion.setOperationReturnType(n.getType().toString());
                    

                    if (n.getParameters() != null) {

                        int secuencia = 0;

                        for (final Iterator<com.github.javaparser.ast.body.Parameter> i = n.getParameters().iterator(); i.hasNext();) {

                            final com.github.javaparser.ast.body.Parameter p = i.next();

                            logger.info("                          Parametro: (" + secuencia + ") " + p.getId() + " Type: " + p.getType());

                            Parameter parametro = new Parameter();

                            parametro.setParameterName(p.getId().getName());
                            parametro.setParameterSequence(secuencia);
                            
                            String fqName = getQuantified(imports,p.getType().toString()); 
                            if(fqName.isEmpty())
                            {
                                fqName = primtiveLookup.getOrDefault(p.getType().toString(), p.getType().toString());
                            }
                            
                            parametro.setJavaClass(checkJavaClass(fqName));
                            
                            parametro.setParameterType(fqName);  
                            operacion.getParameters().getParameters().add(parametro);

                            secuencia++;

                        }

                    }

                    operaciones.addOperacion(operacion);

                }

            }
            
            if (bValueObject) {
                
                if (ModifierSet.isPublic(n.getModifiers())) {
                    
                    if (n.getName().startsWith("set")) {
                        
                        logger.info("Processing Method: " + srcFile.getName() + "_" + n.getName());
                        
                        if (n.getParameters() != null) {

                            int secuencia = 0;

                            for (final Iterator<com.github.javaparser.ast.body.Parameter> i = n.getParameters().iterator(); i.hasNext();) {

                                final com.github.javaparser.ast.body.Parameter p = i.next();

                                logger.info("                          Parametro: (" + secuencia + ") " + p.getId() + " Type: " + p.getType());
                                
                                ModelType tipoDelModelo = new ModelType();
                                  
                                String fqName = getQuantified(imports,p.getType().toString());
                                 
                                if(fqName.isEmpty())
                                {
                                    fqName = primtiveLookup.getOrDefault(p.getType().toString(), p.getType().toString());
                                }
                                
                                tipoDelModelo.setJavaClass(checkJavaClass(fqName));
                                 
                                tipoDelModelo.setModelType(fqName);
                                
                                tipoDelModelo.setVariableName(p.getId().toString());
  
                                modelo.getParametersType().add(tipoDelModelo);

                                secuencia++;

                            }

                        }
                         
                        
                    }
                    
                    
                }
                 
                
            }

        }

    }
    
    public void saveMetadata(File metadataDir) throws MetadataServerException {

        logger.info("Serializing Metadata:");
 
        try {

            if (!metadataDir.exists()) {
                metadataDir.mkdir();
            }

            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writeValue(new File(metadataDir.getAbsolutePath() + File.separator + WS_JSON), this.operaciones);

            objectMapper.writeValue(new File(metadataDir.getAbsolutePath() + File.separator + VO_JSON), this.modelos); 

            logger.info("Serializado");

        } catch (Exception ex) {

            logger.error("Error Serializing Metadata: " + ex.getMessage());

            throw new MetadataServerException("Error Serializing Metadata: " + ex.getMessage(), ex);

        }

    }
    
    public void loadMetadata(File metadataDir) throws MetadataServerException {
 
        loadOperacionesMetadata(metadataDir);
        loadModelosMetadata(metadataDir);
         
    }
      
    
    public void loadOperacionesMetadata(File metadataDir) throws MetadataServerException {

        ObjectMapper objectMapper = new ObjectMapper();
        
        try {

            this.operaciones = objectMapper.readValue(new File(metadataDir + File.separator + WS_JSON), Operations.class);

        } catch (IOException ex) {

            logger.error("Error leyendo metadata de transacciones: " + ex.getMessage());

            throw new MetadataServerException("Error leyendo metadata de transacciones: " + ex.getMessage(), ex);

        }
 
    }
    
    public void loadModelosMetadata(File metadataDir) throws MetadataServerException {

        ObjectMapper objectMapper = new ObjectMapper();
        
        try {

            this.modelos = objectMapper.readValue(new File(metadataDir + File.separator + VO_JSON), Models.class);

        } catch (IOException ex) {

            logger.error("Error leyendo metadata de transacciones: " + ex.getMessage());

            throw new MetadataServerException("Error leyendo metadata de transacciones: " + ex.getMessage(), ex);

        }
 
    }
    
    
    private String getQuantified(List<ImportDeclaration> imports, String simple) {
        
        for (ImportDeclaration i : imports) {
            
            String name = i.getName().toString();
            
            if (name.substring(name.lastIndexOf(".") + 1).equals(simple)) {
                return name;
            }
            
        }

        return "";
    }
    
    private Boolean checkJavaClass(String simple) {
        
        Boolean returnValue = Boolean.FALSE;
        try
        {
            Class.forName(simple);
            returnValue = Boolean.TRUE;
        }
        catch(ClassNotFoundException ex)
        {
            logger.info("Not Java Class");
        }
         
        return returnValue;
    }


}
