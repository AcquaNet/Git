/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.buildjdebundle;

import com.atila.metadata.utils.UnzipJar;
import com.atina.metadata.models.JarsClassFile;
import com.atina.metadata.models.JarClassFile;
import com.atina.buildjdebundle.exceptions.MetadataServerException;
import com.atina.buildjdebundle.metadata.MetadataWSGenerator;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.base.Throwables;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.maven.cli.MavenCli;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.IOUtil;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import com.google.devtools.common.options.OptionsParser;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.Resource;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author jgodi
 */
public class MainBuilder {

    private static final Logger logger = LoggerFactory.getLogger(MainBuilder.class);

    private static final String PROPERTYFILE = "packageToShade.properties";
    private static final String JDE_JARS = "JarsToCopy.properties";
    private static final String WS_JARS = "WSToCopy.properties";
    private static final String JAR_SELECTED = "/tmp/jarselected";
    private static final String JAR_DESTINATION = "/tmp/wrapped"; 
    private static final String JAR_SBF = "/tmp/sbfjars";
    private static final String JAR_METADATA = "/tmp/metadata";
    private static final String METADATA_DRIVER_TXT = "MetadataWSDriver.txt";
    private static final String METADATA_DRIVER_JAVA = "/tmp/metadata/MetadataWSDriver.java";
    
    private static final String STEP_1 = "Defining bundle descriptor";
    private static final String STEP_2 = "Defining bundle assembly";
    private static final String STEP_3 = "Performing bundle creation";
    private static final String STEP_4 = "Installing bundle in local repository";
    private static final String STEP_4a = "Building Building shaded";
    private static final String STEP_5 = "Cleaning up";
    private static final Boolean SHADE = false;
     private static final Boolean DEBUG = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        
        if(DEBUG)
        {
            MetadataWSGenerator mt = new MetadataWSGenerator();

            try {
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP430000\\ProcurementManager.java");
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP010000\\valueobject\\ProcessAddressBookV3.java");
                 
                mt.generateMetadata("C:\\tmp\\sbfjars\\JH90I01\\valueobject\\InternalGetTaskMaster.java");
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP03B000\\valueobject\\ShowInvoiceCurrency.java");
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP03B000\\valueobject\\GLPurchaseOrderData.java");
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP03B000\\valueobject\\ConfirmARInvoiceCurrency.java"); 
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP03B000\\valueobject\\GetInvoiceKey.java");
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP420000\\valueobject\\ShowPriceHistoryDetail.java");
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP430000\\valueobject\\PurchaseOrdersForApproverResults.java");
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP430000\\valueobject\\ShowPurchaseOrdersForApprover.java");
                
                mt.generateMetadata("C:\\tmp\\sbfjars\\JP430000\\valueobject\\GetPurchaseOrdersForApprover.java");
                 
                mt.saveMetadata(new File("C:\\tmp\\merge"));
            
            } catch (MetadataServerException ex) {
                logger.error("DEBUG " + ": " + ex.getMessage());
            }
 

        }
        
        // -----------------------------------------------
        // create the command line parser
        // -----------------------------------------------
        OptionsParser parser = OptionsParser.newOptionsParser(Options.class);

        parser.parseAndExitUponError(args);

        // -----------------------------------------------
        // create the Options
        // -----------------------------------------------
        Options options = parser.getOptions(Options.class);

        if (JAR_DESTINATION.isEmpty()
                || options.jdbcDriver.isEmpty()
                || options.version.isEmpty()
                || options.localRepo.isEmpty()
                || options.outputName.isEmpty()
                || options.jdeInstallPath.isEmpty()) {

            printUsage(parser);

            return;
        }

        try {

            // -----------------------------------------------
            // Setting Logging 
            // -----------------------------------------------
            //
            setupLogging(JAR_DESTINATION);

            // -----------------------------------------------
            // Setting Environment Variables 
            // -----------------------------------------------
            //
            setupEnvironmentVariables(JAR_DESTINATION);

            try {
                
                 FileUtils.deleteDirectory(JAR_SELECTED);
                 FileUtils.deleteDirectory(JAR_DESTINATION);
                 FileUtils.deleteDirectory(JAR_SBF);
                  
            } catch (Exception ex) {
                logger.error("Nothing to clean " + ": " + ex.getMessage());
            }
            
            // -----------------------------------------------
            // Create 
            // -----------------------------------------------
            //
             
            ArrayList<String> summary = new ArrayList<String>();
            
            // -----------------------------------------------
            // Obtener Lista de JARs de JDE
            // -----------------------------------------------
            //
            logger.info("Getting JDE Jars...");

            Map<String, String> jdeJars = getPackageToShade(JDE_JARS);

            copyFileFromDeployment(jdeJars, options);
            
            summary.add("Se copiaron los Jars de JDEdwards");
            
            // -----------------------------------------------
            // Obtener Lista de WS de JDE
            // -----------------------------------------------
            //
            logger.info("Getting JDE Jars...");

            Map<String, String> jdeWSJars = getPackageToShade(WS_JARS);

            copyWSFromDeployment(jdeWSJars, options); 
            
            summary.add("Se copiaron los WS de JDEdwards");
             
            // -----------------------------------------------
            // Get Jars File To Shade
            // -----------------------------------------------
            
            HashSet<String> jarsToShade = new HashSet();
            Map<String, String> packages = new HashMap<String, String> ();
            
            if (SHADE) {

                // -----------------------------------------------
                // Get Package To Shade
                // -----------------------------------------------
                //
                logger.info("Getting Package to Shade...");

                packages = getPackageToShade(PROPERTYFILE);

                logger.info("Getting JARS Files to Shade...");

                jarsToShade = getJarsToShade(packages.keySet(), JAR_SELECTED);

                logger.info("JARS Files to Shade...");

                for (String jarfile : jarsToShade) {
                    logger.info("        " + jarfile);
                }

            }

            // -----------------------------------------------
            // Preparing Maven
            // -----------------------------------------------
            
            logger.info("Creacion del POM en el directorio: " + JAR_DESTINATION);
 
            prepareMvn(jarsToShade, JAR_SELECTED, options.jdbcDriver, JAR_DESTINATION, options.version, options.outputName);
            
            summary.add("POM creado en " + JAR_DESTINATION );
            

            // -----------------------------------------------
            // Copy Assembly
            // -----------------------------------------------
            logger.info("Descarga del Assembly en " + JAR_DESTINATION);

            copyAssembly(JAR_DESTINATION);

            summary.add("Assembly descargado en " + JAR_DESTINATION );
            
            
            // -----------------------------------------------
            // Executing Maven to Assembly Bundle
            // -----------------------------------------------
            //
            logger.info("Creando JAR File con librerias de JDE");
            
            ExecutorService executor = Executors.newSingleThreadExecutor();
            
            Callable<Integer> callableTask = () -> {
                int result = executeMvnAssembly(JAR_DESTINATION);
                summary.add("Libreria creada en " + JAR_DESTINATION );
                return result;
            };
                 
            Future<Integer> future = executor.submit(callableTask);

            int result = future.get();
            
            executor.shutdown();
            
            if(result!=0)
            {
                throw new Exception("Error creando JAR File con librerias de JDE");
            }
            
            // -----------------------------------------------
            // Executing Extra Install
            // -----------------------------------------------
            //
             if (SHADE) {
                 
                if (isSuccessful(result)) {

                    // Adding Jars Files to Shade to Local Repo
                    for (String fileToRename : jarsToShade) {
                        executeExtraInstall(JAR_SELECTED, fileToRename.substring(0, fileToRename.lastIndexOf(".")), "1.0.0", options.localRepo);
                    }

                    logger.info(STEP_4);

                    ExecutorService executor2 = Executors.newSingleThreadExecutor();

                    Callable<Integer> callableTask2 = () -> {
                        executeMvnInstall(JAR_DESTINATION, "1.0.0", options.localRepo, options.outputName);
                        return 0;
                    };

                    Future<Integer> future2 = executor2.submit(callableTask2);

                    executor2.shutdown();

                }

            }

            // -----------------------------------------------
            // Executing Maven to Assembly Bundle
            // -----------------------------------------------
            
            logger.info("Ejecutando Clean UP");
            
            cleanUp(JAR_DESTINATION, options.outputName);
            
            summary.add("CleanUp ejecutado en " + JAR_DESTINATION );
 
            // -----------------------------------------------
            // Creating Shaded
            // -----------------------------------------------
            
            if (SHADE) {

                logger.info(STEP_4a);

                result = 0;

                try {

                    logger.info("Copying Java Dummmy...");

                    copyJavaDummy(JAR_DESTINATION);

                    logger.info("Java Dummmy has been copied.");

                } catch (IOException ex) {
                    
                    result = 1;
                    
                    logger.error("Error Copying Java Dummmy" + ": " + ex.getMessage());
                    
                } catch (URISyntaxException ex) {
                    
                    result = 1;
                    
                    logger.error("Error Copying Java Dummmy" + ": " + ex.getMessage());
                    
                }

                // -----------------------------------------------
                // Prepare POM to build  Java Dummy
                // -----------------------------------------------
                if (isSuccessful(result)) {

                    logger.info("Preparing POM to build JDE Connector Shaded");

                    result = prepareMvnForDummy(jarsToShade, JAR_SELECTED, options.jdbcDriver, JAR_DESTINATION, options.version, packages, options.outputName);

                    logger.info("P POM to build JDE Connector Shaded has been wrote.");

                }

                // -----------------------------------------------
                // Executing Building with Shaded option
                // -----------------------------------------------
                if (isSuccessful(result)) {

                    logger.info("Executing Building with Shaded option");

                    ExecutorService executor3 = Executors.newSingleThreadExecutor();

                    Callable<Integer> callableTask3 = () -> {
                        int result2 = executeMvnShade(JAR_DESTINATION, options.version, options.localRepo);
                        return result2;
                    };

                    Future<Integer> future3 = executor3.submit(callableTask3);

                    result = future3.get();

                    executor3.shutdown();

                }

                // -----------------------------------------------
                // Copying Jar Shaded
                // -----------------------------------------------
                if (isSuccessful(result)) {

                    File source = new File(JAR_DESTINATION + File.separator + "target/" + options.outputName + "-" + options.version + "-shaded.jar");
                    File dest = new File(JAR_DESTINATION + File.separator + options.outputName + "-" + options.version + ".jar");

                    logger.info("Copying Jar File " + source.getAbsolutePath() + " to " + dest.getAbsolutePath());

                    try {
                        FileUtils.copyFile(source, dest);

                        logger.info("Jar File " + source.getName() + " to " + dest.getName() + " has been copied.");

                    } catch (IOException ex) {
                        result = 1;
                        logger.error("Error copying Jar Shaded. Message" + ": " + ex.getMessage());
                    }

                }

                // -----------------------------------------------
                // Cleanning Shading process
                // -----------------------------------------------
                if (isSuccessful(result)) {

                    try {

                        executeMvnShadeClean(JAR_DESTINATION, options.localRepo);

                        logger.info("Cleaning Shading process ... ");

                        FileUtils.deleteDirectory(JAR_DESTINATION + File.separator + "target");

                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(MainBuilder.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        logger.info("Shading process has been cleaned");

                    } catch (IOException ex) {
                        //result = 1;
                        logger.error("Error Cleanning Shading process. Message" + ": " + ex.getMessage());
                    }

                    cleanUpShade(JAR_DESTINATION);

                }

                

            }
            
            // -----------------------------------------------
            // Installing
            // -----------------------------------------------
            //
            if (isSuccessful(result)) {

                logger.info("Installing JDE Connector Shaded in local repository...");

                result = executeMvnInstall(JAR_DESTINATION, options.version, options.localRepo, options.outputName);

                logger.info("JDE Connector Shaded jhas been installed in local repository.");

                if (!isSuccessful(result)) {

                    logger.error("Error Installing JDE Connector in Local Repository");
                    
                    throw new Exception("Error Installing JDE Connector in Local Repository");
                }
                
                summary.add("Jar Instalado en el Repositorio " + JAR_DESTINATION );
                
            }
            
            // -----------------------------------------------
            // Unzipping WS and Creating Metadata
            // -----------------------------------------------
            //
            
            logger.info("Descomprimiendo WS..");

            MetadataWSGenerator mt = new MetadataWSGenerator();
            
            JarsClassFile jarsToUnzip = new JarsClassFile();

            jarsToUnzip = getJarsToUnzip(JAR_SBF);

            logger.info("JARS Files to unzip...");

            for (JarClassFile jarfile : jarsToUnzip.getJars()) {

                UnzipJar.unzipJar(JAR_SBF, jarfile.getJarFile().getAbsolutePath());
                
                logger.info("        " + jarfile.getNameWithoutExtension() + " descomprimido");
            }
             
            summary.add("WS descomprimidos en " + JAR_SBF );
            
            // -----------------------------------------------
            // Remover Protected 
            // -----------------------------------------------
            //
            logger.info("Reemplazando Protected");
             
            Stream<Path> walk = Files.walk(Paths.get(JAR_SBF));
            
            List<String> resultList = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".java")).collect(Collectors.toList());

            for (String file : resultList) {
                
                try {
                    mt.generateMetadata(file);
                } catch (MetadataServerException ex) {
                    logger.error(ex.getMessage(), ex);
                }

                modifyFile(file,"protected ", "public ");

            }
            
            // -----------------------------------------------
            // Persist Metadata
            // -----------------------------------------------
            //
             
            try {
                mt.saveMetadata(new File(JAR_METADATA));
            } catch (MetadataServerException ex) {
                logger.error(ex.getMessage(), ex);
            } 
            
            // -----------------------------------------------
            // Copiar Clase de Metadata de Resource a /tmp
            // -----------------------------------------------
            //
            
            URL url = Thread.currentThread().getContextClassLoader().getResource(METADATA_DRIVER_TXT);

            InputStream inputStream = url.openConnection().getInputStream();

            IOUtil.copy(inputStream, new FileOutputStream(new File(METADATA_DRIVER_JAVA)));
            

            // -----------------------------------------------
            // Prepare Maven 
            // -----------------------------------------------
            //
            logger.info("Preparando POM para compilar WS...");

            prepareWSMvn(JAR_METADATA, JAR_METADATA, jarsToUnzip, JAR_SBF, JAR_SBF, options.version);
            
            if (!isSuccessful(result)) {

                logger.error("Error creando POM para la compilacion de WS");

                throw new Exception("Error creando POM para la compilacion de WS");
            }
             
            summary.add("POM de Ws crado en " + JAR_SBF );
 
            logger.info("Compilando WS..");

            ExecutorService executorWS = Executors.newSingleThreadExecutor();

            Callable<Integer> callableTaskWS = () -> {
                int result4 = executeMvnWS(JAR_SBF, options.localRepo);
                return result4;
            };

            Future<Integer> futureWS = executorWS.submit(callableTaskWS);

            result = futureWS.get();

            executorWS.shutdown();
             
            if (!isSuccessful(result)) {

                logger.error("Error Installing JDE Connector in Local Repository");

                throw new Exception("Error Installing JDE Connector in Local Repository");
            }
         
            summary.add("WS instalado en Repositorio");
           
            logger.info("=======================================================");
            logger.info(" Summary ");
            logger.info("=======================================================");
            
            for (String line : summary) {
                logger.info(line);
            }
            

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        
        
    }

    private static void printUsage(OptionsParser parser) {

        System.out.println("Usage: java -jar server.jar OPTIONS");

        System.out.println(parser.describeOptions(Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG));

    }

    private static void setupLogging(String destDir) {

        MDC.put("fileName", destDir + File.separator + "log.txt");
    }

    private static void setupEnvironmentVariables(String destDir) {
        System.setProperty("maven.repo.local", destDir + File.separator + "repo");
        System.setProperty("maven.multiModuleProjectDirectory", "$FOO");
    }

    private static void prepareMvn(Set<String> JarsToShade, String libDir, String jdbcDriver, String destDir, String version, String name) {
        try {

            Model pom = new Model();
            pom.setGroupId("com.jdedwards");
            pom.setArtifactId(name); 
            pom.setVersion("1.0.0");
            pom.setModelVersion("4.0.0");

            Plugin plugin = new Plugin();
            plugin.setArtifactId("maven-assembly-plugin");
            plugin.setVersion("3.1.0");

            Xpp3Dom config = new Xpp3Dom("configuration");
            Xpp3Dom descriptors = new Xpp3Dom("descriptors");
            Xpp3Dom descriptor = new Xpp3Dom("descriptor");
            descriptor.setValue("assembly.xml");
            descriptors.addChild(descriptor);
            config.addChild(descriptors);

            Xpp3Dom appendAssemblyId = new Xpp3Dom("appendAssemblyId");
            appendAssemblyId.setValue("false");
            config.addChild(appendAssemblyId);
            plugin.setConfiguration(config);
            pom.setBuild(new Build());
            pom.getBuild().addPlugin(plugin);
            pom.getBuild().setDirectory(destDir);

            // source folder processing
            File[] files = allJarsInLibDir(libDir);

            for (File file : files) {

                if (!JarsToShade.contains(file.getName())) {
                    addFileToPom(pom, file);
                }

            }

            // jdbc driver processing
            File jdbcDriverFile = new File(jdbcDriver);
            addFileToPom(pom, jdbcDriverFile);

            File pomFile = new File(destDir, "pom.xml");

            MavenXpp3Writer writer = new MavenXpp3Writer();
            FileWriter w;
            w = new FileWriter(pomFile);
            writer.write(w, pom);
        } catch (IOException e) {
            logger.error(STEP_1 + ": " + e.getMessage());
        }
    }

    private static File[] allJarsInLibDir(String libDir) {
        File[] files = new File(libDir).listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });
        return files;
    }

    private static void addFileToPom(Model pom, File file) {
        Dependency dependency = new Dependency();
        dependency.setArtifactId(file.getName());
        dependency.setGroupId("com.jdedwards");
        dependency.setScope("system");
        dependency.setVersion("1.0");
        dependency.setSystemPath(file.getAbsolutePath());
        pom.addDependency(dependency);
    }

    private static void addMavenAssemblyPluginToPom(Model pom) {
        Dependency dependency = new Dependency();
        dependency.setArtifactId("maven-assembly-plugin");
        dependency.setGroupId("org.apache.maven.plugins");
        dependency.setScope("system");
        dependency.setVersion("3.1.0");
        pom.addDependency(dependency);
    }

    private static void copyAssembly(String destDir) throws IOException, URISyntaxException {

        try {

            URL url = Thread.currentThread().getContextClassLoader().getResource("assembly.xml");

            InputStream inputStream = url.openConnection().getInputStream();

            IOUtil.copy(inputStream, new FileOutputStream(new File(destDir + File.separator + "assembly.xml")));

        } catch (IOException e) {
            logger.error(STEP_2 + ": " + e.getMessage());
        }

    }

    private static int executeMvnAssembly(String destDir) {
        
        int result = 0;
        
        List<String> executionOutput = null;
                 
        try {

            MavenCli cli = new MavenCli();

            String[] params = new String[]{"assembly:help"};

            final ByteArrayOutputStream stdOutStream = new ByteArrayOutputStream();
            final ByteArrayOutputStream stdErrStream = new ByteArrayOutputStream();

            result = cli.doMain(params, destDir, printStream(stdOutStream), printStream(stdErrStream));

            executionOutput = toString(stdOutStream);

            for (String line : executionOutput) {
                logger.info(line);
            }

            params = new String[]{"assembly:single", "-o"};

            result = cli.doMain(params, destDir, printStream(stdOutStream), printStream(stdErrStream));

            executionOutput = toString(stdOutStream);

            for (String line : executionOutput) {
                logger.info(line);
            }

        } catch (Exception e) {

            logger.info(STEP_3 + ": " + e.getMessage());

            if (executionOutput != null) {
                for (String line : executionOutput) {
                    logger.info(line);
                }

            }
        }
        return result;
    }

    private static List<String> toString(final ByteArrayOutputStream byteArrayOutputStream) {
        final String result = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        if (result.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(result.split(System.lineSeparator()));
    }

    private static PrintStream printStream(final OutputStream outputStream) {
        try {
            return new PrintStream(outputStream, true, StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException exception) {
            throw Throwables.propagate(exception);
        }
    }

    private static boolean isSuccessful(int result) {
        return result == 0;
    }

    private static int executeExtraInstall(String destDir, String jarFile, String version, String localRepo) {

        System.setProperty("maven.repo.local", localRepo);

        final ByteArrayOutputStream stdOutStream = new ByteArrayOutputStream();

        final ByteArrayOutputStream stdErrStream = new ByteArrayOutputStream();

        int result = 0;

        try {

            MavenCli cli = new MavenCli();

            String[] params = new String[]{"install:install-file", "-Dfile=" + destDir + File.separator + jarFile + ".jar", "-DgroupId=com.jdedwards", "-DartifactId=" + jarFile, "-Dversion=" + version, "-Dpackaging=jar"};

            result = cli.doMain(params, destDir, printStream(stdOutStream), printStream(stdErrStream));

            List<String> executionOutput = toString(stdOutStream);

            for (String line : executionOutput) {
                logger.info(line);
            }

        } catch (Exception e) {
            logger.error(STEP_4 + ": " + e.getMessage());

        }
        return result;

    }

    private static int executeMvnInstall(String destDir, String version, String localRepo, String name) {

        System.setProperty("maven.repo.local", localRepo);

        final ByteArrayOutputStream stdOutStream = new ByteArrayOutputStream();

        final ByteArrayOutputStream stdErrStream = new ByteArrayOutputStream();

        int result = 0;
        try {

            MavenCli cli = new MavenCli();

            String[] params = new String[]{"install:install-file", "-Dfile=" + destDir + File.separator + name + "-" + version + ".jar", "-DgroupId=com.jdedwards", "-DartifactId=" + name + "", "-Dversion=" + version, "-Dpackaging=jar"};

            result = cli.doMain(params, destDir, printStream(stdOutStream), printStream(stdErrStream));

            List<String> executionOutput = toString(stdOutStream);

            for (String line : executionOutput) {
                logger.info(line);
            }

        } catch (Exception e) {
            logger.error(STEP_4 + ": " + e.getMessage());

        }
        return result;

    }

    private static int executeMvnShade(String destDir, String version, String localRepo) {

        System.setProperty("maven.repo.local", localRepo);

        int result = 0;

        final ByteArrayOutputStream stdOutStream = new ByteArrayOutputStream();

        final ByteArrayOutputStream stdErrStream = new ByteArrayOutputStream();

        try {

            MavenCli cli = new MavenCli();

            String[] params = new String[]{"clean", "package"};

            result = cli.doMain(params, destDir, printStream(stdOutStream), printStream(stdErrStream));

            List<String> executionOutput = toString(stdOutStream);

            for (String line : executionOutput) {
                logger.info(line);
            }

            stdOutStream.close();
            stdErrStream.close();

        } catch (Exception e) {

            result = 1;

            logger.error("Error Executing Maven Shaded. Message" + ": " + e.getMessage());

        }
        return result;

    }
    
    private static int executeMvnWS(String destDir, String localRepo) {

        System.setProperty("maven.repo.local", localRepo);

        int result = 0;

        final ByteArrayOutputStream stdOutStream = new ByteArrayOutputStream();

        final ByteArrayOutputStream stdErrStream = new ByteArrayOutputStream();
        
        List<String> executionOutput = null;

        try {

            MavenCli cli = new MavenCli();

            String[] params = new String[]{"clean", "install"};

            result = cli.doMain(params, destDir, printStream(stdOutStream), printStream(stdErrStream));

            executionOutput = toString(stdOutStream);

            for (String line : executionOutput) {
                logger.info(line);
            }

            stdOutStream.close();
            stdErrStream.close();

        } catch (Exception e) {

            result = 1;

            logger.error("Error Executing Maven Shaded. Message" + ": " + e.getMessage());
            
            if (executionOutput != null) {
                for (String line : executionOutput) {
                    logger.info(line);
                }
            }

        }
        
        return result;

    }

    private static int executeMvnShadeClean(String destDir, String localRepo) {

        System.setProperty("maven.repo.local", localRepo);

        int result = 0;
        try {
            MavenCli cli = new MavenCli();
            String[] params = new String[]{"clean"};
            result = cli.doMain(params, destDir, System.out, System.err);
        } catch (Exception e) {
            logger.error(STEP_4 + ": " + e.getMessage());

        }
        return result;

    }

    private static void cleanUp(String destDir, String name) {
        
        try {
            
            File[] temp = null;
            
            if(SHADE)
            {
                temp = new File[]{new File(destDir, "pom.xml"), new File(destDir, "assembly.xml"), new File(destDir, "archive-tmp"), new File(destDir, name + "-1.0.0.jar")};
            }
            else
            {
                temp = new File[]{new File(destDir, "pom.xml"), new File(destDir, "assembly.xml"), new File(destDir, "archive-tmp")};
            }
            
            for (File file : temp) {
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            logger.error(STEP_4 + ": " + e.getMessage());
        }
        
    }

    private static void cleanUpShade(String destDir) {
        try {
            File[] temp = new File[]{new File(destDir, "pom.xml"), new File(destDir, "dependency-reduced-pom.xml"), new File(destDir, "BuildBundleDummy.java")};
            for (File file : temp) {
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            logger.error(STEP_4 + ": " + e.getMessage());
        }
    }

    private static Map<String, String> getPackageToShade(String propertyFile) {

        Properties properties = new Properties();

        Map<String, String> map = new HashMap<String, String>();

        String fileName = File.separator + "tmp" + File.separator + propertyFile;

        boolean check = new File(fileName).exists();

        if (check) {

            try {

                FileInputStream inputStream = new FileInputStream(fileName);

                properties.load(inputStream);

            } catch (Exception e) {

                logger.error("Some issue finding or loading file....!!! " + e.getMessage());

            }

        } else {

            try {

                InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile);

                properties.load(input);

            } catch (IOException e) {

                logger.error("Some issue finding or loading file....!!! " + e.getMessage());

            }

        }

        for (final Entry<Object, Object> entry : properties.entrySet()) {

            map.put((String) entry.getKey(), (String) entry.getValue());

            logger.info("Packages to Shade: " + entry.getKey());
        }

        return map;

    }

    private static Map<String, String> getJDEJars(String propertyFile) {

        Properties properties = new Properties();

        Map<String, String> map = new HashMap<String, String>();

        String fileName = File.separator + "tmp" + File.separator + propertyFile;

        boolean check = new File(fileName).exists();

        if (check) {

            try {

                FileInputStream inputStream = new FileInputStream(fileName);

                properties.load(inputStream);

            } catch (Exception e) {

                logger.error("Some issue finding or loading file....!!! " + e.getMessage());

            }

        } else {

            try {

                InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile);

                properties.load(input);

            } catch (IOException e) {

                logger.error("Some issue finding or loading file....!!! " + e.getMessage());

            }

        }

        for (final Entry<Object, Object> entry : properties.entrySet()) {

            map.put((String) entry.getKey(), (String) entry.getValue());

            logger.info("Packages to Shade: " + entry.getKey());
        }

        return map;

    }

    private static HashSet<String> getJarsToShade(Set packages, String libDir) throws IOException {

        HashSet<String> jarsToShade = new HashSet();

        Set packagesPending = new HashSet();
        packagesPending.addAll(packages);

        // source folder processing
        File[] files = allJarsInLibDir(libDir);

        for (File file : files) {

            logger.info("Reading Jar File:" + file.getName());

            JarFile srcJar = new JarFile(file);

            for (JarEntry je : Collections.list(srcJar.entries())) {

                if (je.isDirectory()) {

                    String packageName = je.getName().substring(0, je.getName().length() - 1).replaceAll("/", ".");

                    logger.info("                 [" + packageName + "]");

                    if (packages.contains(packageName)) {
                        jarsToShade.add(file.getName());
                        logger.info("                 >>" + packageName + " has been found in " + file.getName());
                        packagesPending.remove(packageName);
                    }

                }

            }

        }

        jarsToShade.add("castor.jar");

        boolean isTherePending = false;

        Iterator packagesPendingIterator = packagesPending.iterator();

        while (packagesPendingIterator.hasNext()) {
            logger.info(packagesPendingIterator.next() + " has not been found");
            isTherePending = true;
        }

        if (isTherePending) {
            logger.info("WARNING: There is package pending");
        }

        return jarsToShade;

    }

    private static void copyJavaDummy(String destDir) throws IOException, URISyntaxException {

        URL url = Thread.currentThread().getContextClassLoader().getResource("BuildBundleDummy.txt");

        InputStream inputStream = url.openConnection().getInputStream();

        IOUtil.copy(inputStream, new FileOutputStream(new File(destDir + File.separator + "BuildBundleDummy.java")));

    }

    private static int prepareMvnForDummy(Set<String> JarsToShade, String libDir, String jdbcDriver, String destDir, String version, Map<String, String> packages, String name) {

        int result = 0;

        try {

            Model pom = new Model();
            pom.setGroupId("com.jdedwards");
            pom.setArtifactId(name);
            pom.setVersion(version);
            pom.setModelVersion("4.0.0");

            pom.addProperty("project.build.sourceEncoding", "UTF-8");
            pom.addProperty("maven.compiler.source", "1.8");
            pom.addProperty("maven.compiler.target", "1.8");

            addFileToPomForDummy(pom, name, "com.jdedwards", "1.0.0");

            for (String jarFile : JarsToShade) {
                addFileToPomForDummy(pom, jarFile.substring(0, jarFile.lastIndexOf('.')), "com.jdedwards", "1.0.0");
            }

            Build build = new Build();

            pom.setBuild(build);

            // -------------------------------------------
            // BEGIN <resources>
            // -------------------------------------------
            buildResources(pom);

            // -------------------------------------------
            // PLUGIN   <groupId>org.apache.maven.plugins</groupId>
            //          <artifactId>maven-compiler-plugin</artifactId>
            // -------------------------------------------
            buildMavenCompilerPlugin(pom);

            // -------------------------------------------
            // PLUGIN    <groupId>org.apache.maven.plugins</groupId>
            //           <artifactId>maven-assembly-plugin</artifactId>
            // -------------------------------------------
            buildMavenAssemblyPlugin(pom);

            // -------------------------------------------------------------
            // PLUGIN   <groupId>com.google.code.maven-replacer-plugin</groupId>
            //          <artifactId>replacer</artifactId>
            // -------------------------------------------------------------
            buildReplacer(pom, destDir, packages);

            // ----------------------------------------------------------
            // PLUGIN   <groupId>org.codehaus.mojo</groupId>
            //          <artifactId>build-helper-maven-plugin</artifactId>
            // ----------------------------------------------------------
            buildMavenBuildHelperMavenPlugin(pom);

            // ----------------------------------------------------------
            // PLUGIN   <groupId>org.apache.maven.plugins</groupId>
            //          <artifactId>maven-dependency-plugin</artifactId>
            // ----------------------------------------------------------
            buildMavenDependencyPlugin(pom);

            // ----------------------------------------------------------
            // PLUGIN   <groupId>org.apache.maven.plugins</groupId>
            //          <artifactId>maven-shade-plugin</artifactId>
            // ----------------------------------------------------------
            buildMavenShadePlugin(pom, JarsToShade, packages, name);

            // -------------------------------------------
            // WRITTING POM FILE
            // -------------------------------------------
            File pomFile = new File(destDir, "pom.xml");

            MavenXpp3Writer writer = new MavenXpp3Writer();

            FileWriter w;

            w = new FileWriter(pomFile);

            writer.write(w, pom);

        } catch (Exception e) {

            result = 1;

            logger.error("Error creating POM file to build JDE Connector with shaded. Error:" + ": " + e.getMessage());

        }

        return result;

    }

    private static void buildResources(Model pom) {

        /* ------------------------------------------
            
        <resources>
		<resource>
			<filtering>false</filtering>
			<directory>${project.build.directory}/castor-resources</directory>
		</resource>
		<resource>
			<filtering>false</filtering>
			<directory>${basedir}/src/main/resources</directory>
		</resource>
	</resources>
             
         */
        try {

            // BEGIN 
            /*
           <resource>
			<filtering>false</filtering>
			<directory>${project.build.directory}/castor-resources</directory>
            </resource>
        
             */
            Resource resource1 = new Resource();

            resource1.setFiltering(false);
            resource1.setDirectory("${project.build.directory}/castor-resources");

            /* 
                <resource>
			<filtering>false</filtering>
			<directory>${basedir}/src/main/resources</directory>
		</resource>
             */
            Resource resource2 = new Resource();
            resource2.setFiltering(false);
            resource2.setDirectory("${basedir}/castor-resources");

            pom.setBuild(new Build());

            pom.getBuild().addResource(resource1);
            pom.getBuild().addResource(resource2);

        } catch (Exception e) {
            logger.error("Build Resources" + ": " + e.getMessage());
        }

    }

    private static void buildMavenAssemblyPlugin(Model pom) {

        Plugin plugin = new Plugin();
        plugin.setGroupId("org.apache.maven.plugins");
        plugin.setArtifactId("maven-assembly-plugin");
        plugin.setVersion("2.4.1");

        Xpp3Dom plugin_config = new Xpp3Dom("configuration");
        Xpp3Dom plugin_config_value = null;

        try {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<descriptorRefs>");
            stringBuilder.append("<descriptorRef>jar-with-dependencies</descriptorRef>");
            stringBuilder.append("</descriptorRefs>");

            plugin_config_value = Xpp3DomBuilder.build(new StringReader(stringBuilder.toString()));
            plugin_config.addChild(plugin_config_value);

            stringBuilder = new StringBuilder();
            stringBuilder.append("<archive>");
            stringBuilder.append("<manifest>");
            stringBuilder.append("<mainClass>BuildBundleDummy</mainClass>");
            stringBuilder.append("</manifest>");
            stringBuilder.append("</archive> ");

            plugin_config_value = Xpp3DomBuilder.build(new StringReader(stringBuilder.toString()));
            plugin_config.addChild(plugin_config_value);

        } catch (XmlPullParserException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        plugin.setConfiguration(plugin_config);

        // BEGIN <executions>
        PluginExecution execution = new PluginExecution();
        execution.setPhase("package");
        execution.setId("make-assembly");
        ArrayList<String> goals = new ArrayList();
        goals.add("single");
        execution.setGoals(goals);
        ArrayList<PluginExecution> executions = new ArrayList();
        executions.add(execution);
        plugin.setExecutions(executions);

        pom.getBuild().addPlugin(plugin);

    }

    private static void buildMavenCompilerPlugin(Model pom) {

        Plugin plugin = new Plugin();
        plugin.setArtifactId("maven-compiler-plugin");
        plugin.setGroupId("org.apache.maven.plugins");
        plugin.setVersion("2.3.2");

        Xpp3Dom plugin1_config = new Xpp3Dom("configuration");
        Xpp3Dom plugin1_source = new Xpp3Dom("source");
        Xpp3Dom plugin1_target = new Xpp3Dom("target");
        plugin1_source.setValue("1.8");
        plugin1_target.setValue("1.8");
        plugin1_config.addChild(plugin1_source);
        plugin1_config.addChild(plugin1_target);

        plugin.setConfiguration(plugin1_config);

        pom.getBuild().addPlugin(plugin);

    }

    private static void buildReplacer(Model pom, String dest, Map<String, String> packages) {

        /* ------------------------------------------
            
            <plugin>
                <groupId>com.google.code.maven-replacer-plugin</groupId>
                <artifactId>replacer</artifactId>
                <version>1.5.3</version>
                <executions>
                    <execution>
                        <phase>generate-resources</phase>
                        <goals>
                            <goal>replace</goal>
                        </goals>
                        <inherited>false</inherited>
                    </execution>  
                </executions> 
                <configuration>
                    <file>target/castor-resources/org/castor/xml/castor.xml.properties</file>  
                    <replacements>
                        <replacement>
                            <token>org.apache.xerces</token>
                            <value>org.apache.shade.xerces</value>
                        </replacement>
                    </replacements>
                </configuration>
            </plugin> 
             
         */
        Plugin plugin = new Plugin();

        plugin.setGroupId("com.google.code.maven-replacer-plugin");
        plugin.setArtifactId("replacer");
        plugin.setVersion("1.5.3");

        // Begin <configuration>
        // <configuration>
        Xpp3Dom config = new Xpp3Dom("configuration");
        Xpp3Dom config_value = null;

        try {

            StringBuilder stringBuilder = new StringBuilder();
            //stringBuilder.append("<file>");
            //stringBuilder.append(dest);
            //stringBuilder.append(File.separator);
            //stringBuilder.append("target/castor-resources/org/castor/xml/castor.xml.properties</file>");

            stringBuilder.append("<filesToInclude>");
            stringBuilder.append(dest);
            stringBuilder.append(File.separator);
            stringBuilder.append("target/**/*.properties</filesToInclude>");

            config_value = Xpp3DomBuilder.build(new StringReader(stringBuilder.toString()));
            config.addChild(config_value);

            stringBuilder = new StringBuilder();
            stringBuilder.append("<replacements>");

            for (Entry<String, String> pkg : packages.entrySet()) {

                stringBuilder.append("<replacement>");

                stringBuilder.append("<token>");
                stringBuilder.append(pkg.getKey());
                stringBuilder.append("</token>");

                stringBuilder.append("<value>");
                stringBuilder.append(pkg.getValue());
                stringBuilder.append("</value>");

                stringBuilder.append("</replacement>");

            }
            stringBuilder.append("</replacements>");

            config_value = Xpp3DomBuilder.build(new StringReader(stringBuilder.toString()));
            config.addChild(config_value);

        } catch (XmlPullParserException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        plugin.setConfiguration(config);

        // BEGIN <executions>
        PluginExecution execution = new PluginExecution();

        //<phase>
        execution.setPhase("generate-resources");

        // <goals>
        ArrayList<String> goals = new ArrayList();
        goals.add("replace");
        execution.setGoals(goals);

        //<inherited>
        execution.setInherited(false);

        ArrayList<PluginExecution> executions = new ArrayList();

        executions.add(execution);

        plugin.setExecutions(executions);

        // END <executions>
        pom.getBuild().addPlugin(plugin);

    }

    private static void buildMavenBuildHelperMavenPlugin(Model pom) {

        /* ------------------------------------------
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <version>3.0.0</version>
                <executions>
                    <execution>
                        <id>addExternalAllClientsConfigDirectories</id>
                        <phase>process-resources</phase>
                        <goals>
                            <goal>add-resource</goal>
                        </goals>
                        <configuration>
                            <resources>
                                <resource>
                                    <directory>${project.basedir}/target/castor-resources</directory>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
			------------------------------------------ */
        Plugin plugin = new Plugin();

        plugin.setGroupId("org.codehaus.mojo");
        plugin.setArtifactId("build-helper-maven-plugin");
        plugin.setVersion("3.0.0");

        // BEGIN <executions>
        PluginExecution execution = new PluginExecution();

        // <id>
        execution.setId("addExternalAllClientsConfigDirectories");

        // <phase>
        execution.setPhase("generate-resources");

        // <goals>
        ArrayList<String> goals = new ArrayList();
        goals.add("add-resource");
        execution.setGoals(goals);

        // <configuration>
        Xpp3Dom config = new Xpp3Dom("configuration");
        Xpp3Dom config_value = null;

        try {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<resources>");
            stringBuilder.append("<resource>");
            stringBuilder.append("<directory>${project.basedir}/target/castor-resources</directory>");
            stringBuilder.append("</resource>");
            stringBuilder.append("</resources>");

            config_value = Xpp3DomBuilder.build(new StringReader(stringBuilder.toString()));

        } catch (XmlPullParserException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        config.addChild(config_value);

        execution.setConfiguration(config);

        ArrayList<PluginExecution> executions = new ArrayList();

        executions.add(execution);

        // END </executions>
        plugin.setExecutions(executions);

        pom.getBuild().addPlugin(plugin);

    }

    private static void buildMavenDependencyPlugin(Model pom) {

        //    <plugin>
        //            <groupId>org.apache.maven.plugins</groupId>
        //            <artifactId>maven-dependency-plugin</artifactId>
        //            <version>2.2</version>
        //            <executions>
        //               <execution>
        //                    <id>unpack</id>
        //                    <phase>initialize</phase>
        //                    <goals>
        //                        <goal>unpack-dependencies</goal>
        //                    </goals>
        //                    <configuration>
        //                        <includeGroupIds>com.jdedwards</includeGroupIds>
        //                        <includeArtifactIds>castor</includeArtifactIds>
        //                        <overWrite>true</overWrite>
        //                        <outputDirectory>${project.build.directory}/castor-resources</outputDirectory>
        //                       <excludes>types/**,**/*.class,META-INF/**</excludes>
        //                        <overWriteReleases>true</overWriteReleases>
        //                        <overWriteSnapshots>true</overWriteSnapshots>
        //                    </configuration>
        //                </execution>
        //            </executions>
        //        </plugin>
        Plugin plugin = new Plugin();

        plugin.setArtifactId("maven-dependency-plugin");
        plugin.setGroupId("org.apache.maven.plugins");
        plugin.setVersion("2.2");

        // BEGIN <executions>
        PluginExecution execution = new PluginExecution();

        // <id>
        execution.setId("unpack");

        // <phase>
        execution.setPhase("initialize");

        // <goals>
        ArrayList<String> goals = new ArrayList();
        goals.add("unpack-dependencies");
        execution.setGoals(goals);

        // Begin <configuration>
        InputStream input = MainBuilder.class.getClassLoader().getResourceAsStream("unpack-configuration.xml");

        Reader reader = new InputStreamReader(input);

        Xpp3Dom Xpp3DomObject = null;

        try {

            Xpp3DomObject = Xpp3DomBuilder.build(reader);

        } catch (XmlPullParserException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        execution.setConfiguration(Xpp3DomObject);

        ArrayList<PluginExecution> executions = new ArrayList();

        executions.add(execution);

        // END </executions>
        plugin.setExecutions(executions);

        pom.getBuild().addPlugin(plugin);

    }

    private static void addFileToPomForDummy(Model pom, String artifactID, String groupId, String version) {
        Dependency dependency = new Dependency();
        dependency.setArtifactId(artifactID);
        dependency.setGroupId(groupId);
        dependency.setVersion(version);
        pom.addDependency(dependency);
    }

    private static void buildMavenShadePlugin(Model pom, Set<String> JarsToShade, Map<String, String> packages, String name) {

        /* ------------------------------------------
            
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.1.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals> 
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>org.kordamp.shade</groupId>
                        <artifactId>maven-shade-ext-transformers</artifactId>
                        <version>1.0.3</version>
                    </dependency>
                </dependencies>
                <configuration>
                    <transformers>
                        <transformer implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer"/>
                    </transformers> 
                    <minimizeJar>false</minimizeJar>
                    <artifactSet>
                        <includes>
                            <include>com.jdedwards:jde-lib-bundle</include>
                            <include>com.jdedwards:xerces</include>
                            <include>com.jdedwards:xmlparserv2</include>       
                            <include>com.jdedwards:j2ee1_3</include> 
                            <include>com.jdedwards:castor</include>                          
                        </includes>
                    </artifactSet>
                    <relocations>  
                        <relocation>
                            <pattern>javax.xml.parsers</pattern>
                            <shadedPattern>javax.xml.shade.parsers</shadedPattern>
                        </relocation>   
                        <relocation>
                            <pattern>org.apache.xerces</pattern>
                            <shadedPattern>org.apache.shade.xerces</shadedPattern>
                        </relocation> 
                        <relocation>
                            <pattern>oracle.xml.parser</pattern>
                            <shadedPattern>oracle.xml.shade.parser</shadedPattern>
                        </relocation> 
                        <relocation>
                            <pattern>oracle.xml.jaxp</pattern>
                            <shadedPattern>oracle.xml.shade.jaxp</shadedPattern>
                        </relocation>
                        <relocation>
                            <pattern>org.apache.xalan</pattern>
                            <shadedPattern>org.apache.shade.xalan</shadedPattern>
                        </relocation>
                        <relocation>
                            <pattern>org.apache.crimson</pattern>
                            <shadedPattern>org.apache.shade.crimson</shadedPattern>
                        </relocation>
                    </relocations> 
                </configuration>
            </plugin>
             
         */
        Plugin plugin = new Plugin();

        plugin.setGroupId("org.apache.maven.plugins");
        plugin.setArtifactId("maven-shade-plugin");
        plugin.setVersion("3.1.1");

        // BEGIN <executions>
        ArrayList<PluginExecution> pluginExecutions = new ArrayList();

        PluginExecution pluginExecution = new PluginExecution();

        pluginExecution.setPhase("package");

        ArrayList goals = new ArrayList();
        goals.add("shade");

        pluginExecution.setGoals(goals);

        pluginExecutions.add(pluginExecution);

        plugin.setExecutions(pluginExecutions);

        // END <executions>
        // BEGIN <dependencies>
        ArrayList<Dependency> pluginDependecies = new ArrayList();

        Dependency dependency = new Dependency();
        dependency.setArtifactId("maven-shade-ext-transformers");
        dependency.setGroupId("org.kordamp.shade");
        dependency.setVersion("1.0.3");

        pluginDependecies.add(dependency);

        plugin.setDependencies(pluginDependecies);

        // END <dependencies>
        // BEGIN <configuration>
        Xpp3Dom plugin_config = new Xpp3Dom("configuration");

        Xpp3Dom plugin_config_value = null;

        try {

            // transformers
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<transformers>");
            stringBuilder.append("<transformer implementation=\"org.apache.maven.plugins.shade.resource.ServicesResourceTransformer\"/>");
            stringBuilder.append("</transformers>");

            plugin_config_value = Xpp3DomBuilder.build(new StringReader(stringBuilder.toString()));

            plugin_config.addChild(plugin_config_value);

            // minimizeJar
            stringBuilder = new StringBuilder();
            stringBuilder.append("<minimizeJar>false</minimizeJar>");
            plugin_config_value = Xpp3DomBuilder.build(new StringReader(stringBuilder.toString()));
            plugin_config.addChild(plugin_config_value);

            // artifactSet
            stringBuilder = new StringBuilder();
            stringBuilder.append("<artifactSet>");
            stringBuilder.append("<includes>");

            stringBuilder.append("<include>com.jdedwards:" + name + "</include>");

            for (String file : JarsToShade) {

                stringBuilder.append("<include>com.jdedwards:");
                stringBuilder.append(file.substring(0, file.lastIndexOf('.')));
                stringBuilder.append("</include>");
            }

            stringBuilder.append("</includes>");
            stringBuilder.append("</artifactSet>");

            plugin_config_value = Xpp3DomBuilder.build(new StringReader(stringBuilder.toString()));

            plugin_config.addChild(plugin_config_value);

            // relocations
            stringBuilder = new StringBuilder();

            stringBuilder.append("<relocations>");

            for (String key : packages.keySet()) {

                stringBuilder.append("<relocation>");
                stringBuilder.append("<pattern>");
                stringBuilder.append(key);
                stringBuilder.append("</pattern>");
                stringBuilder.append("<shadedPattern>");
                stringBuilder.append(packages.get(key));
                stringBuilder.append("</shadedPattern>");
                stringBuilder.append("</relocation>");
            }

            stringBuilder.append("</relocations>");

            plugin_config_value = Xpp3DomBuilder.build(new StringReader(stringBuilder.toString()));

            plugin_config.addChild(plugin_config_value);

            plugin.setConfiguration(plugin_config);

        } catch (XmlPullParserException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        // END <configuration>
        pom.getBuild().addPlugin(plugin);

    }

    private static void cleanUpShading(String destDir, String name) {

        try {

            File[] temp = new File[]{new File(destDir, "pom.xml"), new File(destDir, "assembly.xml"), new File(destDir, "archive-tmp"), new File(destDir, name + "-1.0.0.jar")};

            for (File file : temp) {

                if (file.exists()) {

                    file.delete();

                    logger.info("Cleanning Shading process. File " + file.getName() + " has been deleted");
                }

            }

        } catch (Exception e) {

            logger.error("Error Cleanning Shading process." + ": " + e.getMessage());

        }
    }

    private static void copyFileFromDeployment(Map<String, String> jdeJars, Options options) throws IOException {

        for (Entry<String, String> lista : jdeJars.entrySet()) {

            File source = new File(options.jdeInstallPath + File.separator + lista.getValue() + File.separator + lista.getKey() + ".jar");

            File dest = new File(JAR_SELECTED + File.separator + lista.getKey() + ".jar");

            logger.info("Copying Jar File " + source.getAbsolutePath() + " to " + dest.getAbsolutePath());

            FileUtils.copyFile(source, dest);

            logger.info("Jar File " + source.getName() + " has been copied.");

        }
        

    }
    
    private static void copyWSFromDeployment(Map<String, String> jdeJars, Options options) throws IOException {

        for (Entry<String, String> lista : jdeJars.entrySet()) {

            File source = new File(options.jdeInstallPath + File.separator + lista.getValue() + File.separator + lista.getKey() + ".jar");

            File dest = new File(JAR_SBF + File.separator + lista.getKey() + ".jar");

            logger.info("Copying Jar File " + source.getAbsolutePath() + " to " + dest.getAbsolutePath());

            FileUtils.copyFile(source, dest);

            logger.info("Jar File " + source.getName() + " has been copied.");

        }
      

    }
    
    private static JarsClassFile getJarsToUnzip(String libDir) throws IOException {

       JarsClassFile jarsToShade = new JarsClassFile();
  
        // source folder processing
        File[] files = allJarsInLibDir(libDir);

        for (File file : files) { 
            
            JarClassFile jarFile = new JarClassFile(file);
             
            jarsToShade.addJar(jarFile);
  
        } 

        return jarsToShade;

    }
    
    private static int prepareWSMvn(String jarMetadata, String metadataDriver, JarsClassFile JarsToShade, String libDir, String destDir, String version) {
        
        int returnValue = 0;
        
        JarsToShade.setMetadataFolder(jarMetadata);
        JarsToShade.setMetadataDriver(metadataDriver);
        
        try {

             // -----------------------------------------------
            // Iniciando Motor
            // -----------------------------------------------
            MustacheFactory mf = new DefaultMustacheFactory();

            Mustache m = mf.compile("pom-ws-base.xml");

            FileWriter writer;

            writer = new FileWriter(destDir + "/pom.xml");
             
            // -----------------------------------------------
            // GENERAR
            // -----------------------------------------------
             
            m.execute(writer, JarsToShade).flush();
            
            
            
        } catch (Exception e) {
            logger.error(STEP_1 + ": " + e.getMessage());
            returnValue = 1;
        }
        
        return returnValue;
    }

    static void modifyFile(String filePath, String oldString, String newString) throws IOException
    {
        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        reader = new BufferedReader(new FileReader(fileToBeModified));

        //Reading all the lines of input text file into oldContent
        String line = reader.readLine();

        while (line != null) {
            oldContent = oldContent + line + System.lineSeparator();

            line = reader.readLine();
        }

        //Replacing oldString with newString in the oldContent
        String newContent = oldContent.replaceAll(oldString, newString);

        //Rewriting the input text file with newContent
        writer = new FileWriter(fileToBeModified);

        writer.write(newContent);

        reader.close();

        writer.close();
         
         
    }

}
