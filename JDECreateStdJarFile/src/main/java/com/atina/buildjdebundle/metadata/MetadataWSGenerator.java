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
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import static com.github.javaparser.ast.internal.Utils.isNullOrEmpty;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    private static String MSG_VALUE_OBJECT = "MessageValueObject"; 
    private static String SERIALIZABLE = "Serializable"; 
     private static String PACKAGE_VALUE_OBJECT = "valueobject"; 
    
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
    private int parameterSequence;
    
    private ArrayList<String> invalidModels;
    
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
        primtiveLookup.put("ArrayList" , ArrayList.class.getName());
        
    }

    public MetadataWSGenerator() {
        
        operaciones = new Operations();
        
         modelos = new Models(); 
        
    }
      
    public void generateMetadataE1MessageList() throws MetadataServerException {
        
        
        // ----------------------------------------
        // Creata CallObjectErrorItem
        // ----------------------------------------
        
        Model modelCallObjectErrorItem = new Model();
        modelCallObjectErrorItem.setModelName("CallObjectErrorItem");
        modelCallObjectErrorItem.setModelPackage("com.jdedwards.system.kernel");
          
        /*  
                private int mErrorId;
                private String mDDItem;
                private int mLineNumber;
                private String mFileName;
                private String mSubText;
                private String mAlphaDescription;
                private String mGlossaryText;
                private int mErrorLevel;
        
        */
        
        ModelType mtCallObjectErrorItemmErrorId = new ModelType();
        mtCallObjectErrorItemmErrorId.setParameterName("errorId");
        mtCallObjectErrorItemmErrorId.setParameterSequence(0);
        mtCallObjectErrorItemmErrorId.setParameterType("java.lang.Integer");
        mtCallObjectErrorItemmErrorId.setRepetead(false);
        
        modelCallObjectErrorItem.addTipoDelModelo(mtCallObjectErrorItemmErrorId);
        
        ModelType mtCallObjectErrorItemmDDItem = new ModelType();
        mtCallObjectErrorItemmDDItem.setParameterName("dditem");
        mtCallObjectErrorItemmDDItem.setParameterSequence(0);
        mtCallObjectErrorItemmDDItem.setParameterType("java.lang.String");
        mtCallObjectErrorItemmDDItem.setRepetead(false);
        
        modelCallObjectErrorItem.addTipoDelModelo(mtCallObjectErrorItemmDDItem);
        
        ModelType mtCallObjectErrorItemmLineNumber = new ModelType();
        mtCallObjectErrorItemmLineNumber.setParameterName("lineNumber");
        mtCallObjectErrorItemmLineNumber.setParameterSequence(0);
        mtCallObjectErrorItemmLineNumber.setParameterType("java.lang.Integer");
        mtCallObjectErrorItemmLineNumber.setRepetead(false);
        
        modelCallObjectErrorItem.addTipoDelModelo(mtCallObjectErrorItemmLineNumber);
        
        ModelType mtCallObjectErrorItemmFileName = new ModelType();
        mtCallObjectErrorItemmFileName.setParameterName("fileName");
        mtCallObjectErrorItemmFileName.setParameterSequence(0);
        mtCallObjectErrorItemmFileName.setParameterType("java.lang.String");
        mtCallObjectErrorItemmFileName.setRepetead(false);
        
        modelCallObjectErrorItem.addTipoDelModelo(mtCallObjectErrorItemmFileName);
        
        ModelType mtCallObjectErrorItem = new ModelType();
        mtCallObjectErrorItem.setParameterName("errorID");
        mtCallObjectErrorItem.setParameterSequence(0);
        mtCallObjectErrorItem.setParameterType("java.lang.Integer");
        mtCallObjectErrorItem.setRepetead(false);
        
        modelCallObjectErrorItem.addTipoDelModelo(mtCallObjectErrorItem);
        
        ModelType mtCallObjectErrorItemmSubText = new ModelType();
        mtCallObjectErrorItemmSubText.setParameterName("subText");
        mtCallObjectErrorItemmSubText.setParameterSequence(0);
        mtCallObjectErrorItemmSubText.setParameterType("java.lang.String");
        mtCallObjectErrorItemmSubText.setRepetead(false);
        
        modelCallObjectErrorItem.addTipoDelModelo(mtCallObjectErrorItemmSubText);
        
        ModelType mtCallObjectErrorItemmAlphaDescription = new ModelType();
        mtCallObjectErrorItemmAlphaDescription.setParameterName("alphaDescription");
        mtCallObjectErrorItemmAlphaDescription.setParameterSequence(0);
        mtCallObjectErrorItemmAlphaDescription.setParameterType("java.lang.String");
        mtCallObjectErrorItemmAlphaDescription.setRepetead(false);
        
        modelCallObjectErrorItem.addTipoDelModelo(mtCallObjectErrorItemmAlphaDescription);
        
        ModelType mtCallObjectErrorItemmGlossaryText = new ModelType();
        mtCallObjectErrorItemmGlossaryText.setParameterName("glossaryText");
        mtCallObjectErrorItemmGlossaryText.setParameterSequence(0);
        mtCallObjectErrorItemmGlossaryText.setParameterType("java.lang.String");
        mtCallObjectErrorItemmGlossaryText.setRepetead(false);
        
        modelCallObjectErrorItem.addTipoDelModelo(mtCallObjectErrorItemmGlossaryText);
        
        ModelType mtCallObjectErrorItemmErrorLevel = new ModelType();
        mtCallObjectErrorItemmErrorLevel.setParameterName("errorLevel");
        mtCallObjectErrorItemmErrorLevel.setParameterSequence(0);
        mtCallObjectErrorItemmErrorLevel.setParameterType("java.lang.Integer");
        mtCallObjectErrorItemmErrorLevel.setRepetead(false);
        
        modelCallObjectErrorItem.addTipoDelModelo(mtCallObjectErrorItemmErrorLevel);
        
        
        modelos.AddModelo(modelCallObjectErrorItem);
         
        
        // ----------------------------------------
        // Creata E1Message
        // ----------------------------------------
        
        Model modelE1Message = new Model();
        modelE1Message.setModelName("E1Message");
        modelE1Message.setModelPackage("oracle.e1.bssvfoundation.util");
        
        /*
        
            private String mMessagePrefix;
            private CallObjectErrorItem mCallObjError;
        
        */
        
        ModelType mtCallObjectErrorItemmmessage = new ModelType();
        mtCallObjectErrorItemmmessage.setParameterName("message");
        mtCallObjectErrorItemmmessage.setParameterSequence(0);
        mtCallObjectErrorItemmmessage.setParameterType("java.lang.String");
        mtCallObjectErrorItemmmessage.setRepetead(false);
        
        modelE1Message.addTipoDelModelo(mtCallObjectErrorItemmmessage);
        
        ModelType mtCallObjectErrorItemmmessageType = new ModelType();
        mtCallObjectErrorItemmmessageType.setParameterName("messageType");
        mtCallObjectErrorItemmmessageType.setParameterSequence(1);
        mtCallObjectErrorItemmmessageType.setParameterType("java.lang.Integer");
        mtCallObjectErrorItemmmessageType.setRepetead(false);
        
        modelE1Message.addTipoDelModelo(mtCallObjectErrorItemmmessageType);
        
        ModelType mtCallObjectErrorItemmessagePrefix = new ModelType();
        mtCallObjectErrorItemmessagePrefix.setParameterName("messagePrefix");
        mtCallObjectErrorItemmessagePrefix.setParameterSequence(2);
        mtCallObjectErrorItemmessagePrefix.setParameterType("java.lang.String");
        mtCallObjectErrorItemmessagePrefix.setRepetead(false);
        
        modelE1Message.addTipoDelModelo(mtCallObjectErrorItemmessagePrefix);
        
        ModelType mtCallObjectErrorItemcallObjError = new ModelType();
        mtCallObjectErrorItemcallObjError.setParameterName("callObjError");
        mtCallObjectErrorItemcallObjError.setParameterSequence(3);
        mtCallObjectErrorItemcallObjError.setParameterType("com.jdedwards.system.kernel.CallObjectErrorItem");
        mtCallObjectErrorItemcallObjError.setRepetead(false);
        
        modelE1Message.addTipoDelModelo(mtCallObjectErrorItemcallObjError);
        
        modelos.AddModelo(modelE1Message);
        
        // ----------------------------------------
        // Creata E1MessageList
        // ----------------------------------------
        
        Model modelE1MessageList = new Model();
        modelE1MessageList.setModelName("E1MessageList");
        modelE1MessageList.setModelPackage("oracle.e1.bssvfoundation.util");
        
        /*
        
            private ArrayList E1Messages = new ArrayList(); 
        
        */
        
        ModelType modelE1MessageListE1Messages = new ModelType();
        modelE1MessageListE1Messages.setParameterName("E1Messages");
        modelE1MessageListE1Messages.setParameterSequence(0);
        modelE1MessageListE1Messages.setParameterType("oracle.e1.bssvfoundation.util.E1Message");
        modelE1MessageListE1Messages.setRepetead(true); 
        
        modelE1MessageList.addTipoDelModelo(modelE1MessageListE1Messages);
        
        ModelType modelE1MessageListmessagesAsString = new ModelType();
        modelE1MessageListmessagesAsString.setParameterName("messagesAsString");
        modelE1MessageListmessagesAsString.setParameterSequence(0);
        modelE1MessageListmessagesAsString.setParameterType("java.lang.String");
        modelE1MessageListmessagesAsString.setRepetead(false); 
        
        modelE1MessageList.addTipoDelModelo(modelE1MessageListmessagesAsString);
          
        // ----------------------------------------
        // Adding new Model
        // ----------------------------------------
        //
        modelos.AddModelo(modelE1MessageList);
        
    }
    
    public void generateMetadata(String classFile) throws MetadataServerException {
        
        
        logger.info("Metadata Generartor for: " + classFile);
        
        // -------------------------------
        // Reset variables
        // ------------------------------- 
        
        imports = new ArrayList();
        transaction = null;
        modelo = null;
        srcFile = new File(classFile);
        bPublishedBusinessService = false;
        bValueObject = false;
        packageName = "";
        parameterSequence = 0;
        invalidModels = new ArrayList();
        
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
            
            // parameters for ValueObjects
            cu.accept(new FieldVisitor(), null);
            
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
            
            if(!bValueObject)
            {
                bValueObject = packageName.contains(PACKAGE_VALUE_OBJECT);
            } 
            
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
             
            if (!isNullOrEmpty(n.getExtends())) {
                
                logger.info(" extends ");
                
                for (final Iterator<ClassOrInterfaceType> i = n.getExtends().iterator(); i.hasNext();) {
                    
                    final ClassOrInterfaceType c = i.next();
                    
                    logger.info("    " + c.getName());
                    
                    if(!bPublishedBusinessService)
                    {
                        bPublishedBusinessService = c.getName().equals(PUBLISHED_WS);
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
                    
                    String fqName = getQuantified(imports,n.getType().toString()); 
                    if(fqName.isEmpty())
                    {
                                fqName = primtiveLookup.getOrDefault(n.getType().toString(), n.getType().toString());
                     }
                     
                    operacion.setOperationReturnType(fqName);
                    

                    if (n.getParameters() != null) {

                        int secuencia = 0;

                        for (final Iterator<com.github.javaparser.ast.body.Parameter> i = n.getParameters().iterator(); i.hasNext();) {

                            final com.github.javaparser.ast.body.Parameter p = i.next();

                            logger.info("                          Parametro: (" + secuencia + ") " + p.getId() + " Type: " + p.getType());

                            Parameter parametro = new Parameter();

                            parametro.setParameterName(p.getId().getName());
                            parametro.setParameterSequence(secuencia);
                            
                            fqName = getQuantified(imports,p.getType().toString()); 
                            if(fqName.isEmpty())
                            {
                                fqName = getJAVAClassName(p.getType().toString());
                            }
                              
                            parametro.setParameterType(fqName);  
                            operacion.getParameters().getParameters().put(p.getId().getName(),parametro);

                            secuencia++;

                        }

                    }

                    operaciones.addOperacion(operacion);

                }

            }
            

        }

    }
    
    private class FieldVisitor extends DFDumpVisitor {

        private FieldVisitor() {
            super(false); 
        } 
        
        @Override
        public void visit(final FieldDeclaration n, final Object arg) {
   
            if (bValueObject) {
                
                if (ModifierSet.isPrivate(n.getModifiers()) && !ModifierSet.isStatic(n.getModifiers())) {
                     
                    ModelType tipoDelModelo = new ModelType();
                    
                    
                    String varType = n.getType().toString();
                    
                    List<VariableDeclarator> vars = n.getVariables();
                    String varName = null;
                    
                    if(vars.size()==1)
                    {
                        varName = ((VariableDeclarator) vars.get(0)).getId().toString(); 
                    }
                    
                    if(varType.contains("[]"))
                    {
                        varType = varType.substring(0, varType.length() - 2);
                        tipoDelModelo.setRepetead(true);
                    } else if(varName.contains("[]"))
                    {
                        tipoDelModelo.setRepetead(true);
                    }
                    else if (varType.contains("<"))
                    {
                        varType = varType.substring(varType.indexOf("<") + 1, varType.indexOf(">"));
                        tipoDelModelo.setRepetead(true);
                    }
                    else
                    {
                        tipoDelModelo.setRepetead(false);
                    }
                    
                    String fqName = getQuantified(imports,varType);
                    
                    if(fqName.isEmpty())
                    {
                        fqName = getJAVAClassName(varType);
                          
                    }
                    
                    tipoDelModelo.setParameterType(fqName);
                    
                    tipoDelModelo.setParameterName(((List<VariableDeclarator>) n.getVariables()).get(0).getId().getName().toString());
                     
                    tipoDelModelo.setParameterSequence(parameterSequence);

                    modelo.getParametersType().put(tipoDelModelo.getParameterName(),tipoDelModelo);
                    
                    parameterSequence++;
                    
                }
                 
                
            }

        }

    }
    
    public void saveMetadata(File metadataDir) throws MetadataServerException {

        
        logger.info("Validating Metadata:");
        
        validateModels();
        
        addingE1MessageList();
        
        if(this.invalidModels.size()>0)
        {
            for(String classModel: this.invalidModels)
            {
                logger.info("       Invalid Model:" + classModel);
            }
        }
 
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
    
    private void addingE1MessageList() {
         
        
        // ------------------------------------------------
        // Replacing operation ReturnValues for new Model
        // ------------------------------------------------
          
        
        Iterator it = this.operaciones.getOperations().entrySet().iterator();
        
        while (it.hasNext()) {
            
            Map.Entry pair = (Map.Entry) it.next();
            
            Operation operation = (Operation) pair.getValue();
            
            logger.info("Adding E1MessaList to operation: " + pair.getKey());
             
            String returnModelV = operation.getOperationReturnType();
            
            if (!returnModelV.isEmpty()) {

                if(returnModelV.equals("void"))
                {
                    logger.error("Void");
                }
                else
                {
                Model currentReturnModel = this.modelos.getModelo(returnModelV);

                if (currentReturnModel != null) {
                    
                    ModelType errorList = new ModelType();
                    errorList.setParameterName("e1MessageList");
                    errorList.setParameterSequence(1);
                    errorList.setParameterType("oracle.e1.bssvfoundation.util.E1MessageList");
                    errorList.setRepetead(false);

                    currentReturnModel.addTipoDelModelo(errorList);

                } else {
                        logger.error("Error getting model: " + returnModelV);
                }
                
                }

            }
             
        }
         
        
    }
    
    
    private boolean validateModels() {
         
        Iterator it = this.modelos.getModels().entrySet().iterator();

        while (it.hasNext()) {
            
            Map.Entry pair = (Map.Entry) it.next();
            
            Model model = (Model) pair.getValue();
            
            Collection<ModelType> parameters = model.getParametersType().values();
            
            for(ModelType mdType:parameters)
            {
                if(mdType.getParameterType().endsWith("%"))
                {
                    try
                    {
                        mdType.setParameterType(getFQN(mdType.getParameterType()));
                        
                    } catch (MetadataServerException e)
                    {
                        this.invalidModels.add(mdType.getParameterType());
                    }
                    
                }
            }
             
        }

        return true;
         
    }
    
    private String getFQN(String simple) throws MetadataServerException {
        
        String findObject = simple.substring(0, simple.length() - 1);
        
        Iterator it = this.modelos.getModels().entrySet().iterator();
        
        while (it.hasNext()) {
            
            Map.Entry pair = (Map.Entry) it.next();
            
            Model model = (Model) pair.getValue();
            
            if(model.getModelName().equals(findObject))
            {
                return (String) pair.getKey();
            }
            
        }
        
        throw new MetadataServerException("Missing Type: " + simple);
 
    }
    
     

    private String getJAVAClassName(String simple) {
         
        if(primtiveLookup.containsKey(simple))
        {
            return primtiveLookup.get(simple);
        }
        else
        {
            logger.info("Java Class Name without prefix: " + simple);
            return this.packageName + "." + simple;
        } 
         
    }

}
