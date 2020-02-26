package org.mule.modules.jde.atina.automation.functional;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;

import org.junit.Ignore;
import org.junit.Test;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.jde.atina.automation.functional.TestDataBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;

public class GetABMediaObjectTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(GetABMediaObjectTestCases.class);

    @SuppressWarnings({
            "unchecked",
            "unused"
    })
    @Test
    @Ignore
    public void validateMediaImageAndURL() throws Exception {

        String entityType = TestDataBuilder.getABMediaObjectEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getABMediaObjectWithImageAndURLEntityData();

        try {
            Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetABMediaObjectTestCases: ",
                    entityType, entityData);

            Map<String, Object> images = (Map<String, Object>) result.get("mediaObject");

            ArrayList<Map<String, Object>> moItems = (ArrayList<Map<String, Object>>) images.get("moItems");

            for (Map<String, Object> imageObject : moItems) {

                logger.info(
                        "MULESOFT - FUNCTIONAL_TEST   ----------------------------------------------------------------");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szItemName: " + imageObject.get("szItemName") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szData: " + imageObject.get("szData") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szMoType: " + imageObject.get("szMoType") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   moSeqNo: " + imageObject.get("moSeqNo") + "  ");

                String dataType = (String) imageObject.get("szMoType");

                if (dataType.equals("IMAGE")) {
                    decoder((String) imageObject.get("szData"), "/tmp/image.gif");

                    if (ImageIO.read(new File("/tmp/image.gif")) == null) {
                        fail("File is not a image");
                    }

                }

                if (dataType.equals("TEXT")) {
                    fail("File is not a image");

                }

                if (dataType.equals("URL") && !isValidURL((String) imageObject.get("szItemName"))) {
                    fail("URL not valid");
                }

            }

            logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

        } catch (ExternalConnectorException e) {
            logger.error(e.getE1Message());
            throw new Exception(e.getMessage(), e);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage(), e);
        }

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

    @SuppressWarnings({
            "unchecked",
            "unused"
    })
    @Test
    @Ignore
    public void validateMediaText() throws Exception {

        String entityType = TestDataBuilder.getABMediaObjectEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getABMediaObjectWithTextEntityData();

        try {
            Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetABMediaObjectTestCases: ",
                    entityType, entityData);

            Map<String, Object> images = (Map<String, Object>) result.get("mediaObject");

            ArrayList<Map<String, Object>> moItems = (ArrayList<Map<String, Object>>) images.get("moItems");

            for (Map<String, Object> imageObject : moItems) {

                logger.info(
                        "MULESOFT - FUNCTIONAL_TEST   ----------------------------------------------------------------");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szItemName: " + imageObject.get("szItemName") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szData: " + imageObject.get("szData") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szMoType: " + imageObject.get("szMoType") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   moSeqNo: " + imageObject.get("moSeqNo") + "  ");

                String dataType = (String) imageObject.get("szMoType");

                if (dataType.equals("IMAGE")) {

                    decoder((String) imageObject.get("szData"), "/tmp/" + imageObject.get("szItemName"));

                    if (ImageIO.read(new File((String) imageObject.get("szItemName"))) == null) {
                        fail("File is not a image");
                    }

                }

            }

            logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

        } catch (ExternalConnectorException e) {
            logger.error(e.getE1Message());
            throw new Exception(e.getMessage(), e);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage(), e);
        }

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

    @SuppressWarnings({
            "unchecked",
            "unused"
    })
    @Test
    @Ignore
    public void validateMediaTextJPG() throws Exception {

        String entityType = TestDataBuilder.getABMediaObjectEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getABMediaObjectWithImageJPGEntityData();

        try {
            Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetABMediaObjectTestCases: ",
                    entityType, entityData);

            Map<String, Object> images = (Map<String, Object>) result.get("mediaObject");

            ArrayList<Map<String, Object>> moItems = (ArrayList<Map<String, Object>>) images.get("moItems");

            for (Map<String, Object> imageObject : moItems) {

                logger.info(
                        "MULESOFT - FUNCTIONAL_TEST   ----------------------------------------------------------------");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szItemName: " + imageObject.get("szItemName") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szData: " + imageObject.get("szData") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szMoType: " + imageObject.get("szMoType") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   moSeqNo: " + imageObject.get("moSeqNo") + "  ");

                String dataType = (String) imageObject.get("szMoType");

                if (dataType.equals("IMAGE")) {

                    decoder((String) imageObject.get("szData"), "/tmp/" + imageObject.get("szItemName"));

                    if (ImageIO.read(new File((String) imageObject.get("szItemName"))) == null) {
                        fail("File is not a image");
                    }

                }

            }

            logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

        } catch (ExternalConnectorException e) {
            logger.error(e.getE1Message());
            throw new Exception(e.getMessage(), e);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage(), e);
        }

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }
    
    @Test 
    public void validateMediaInvalidEntityAndValidMediaObject() throws Exception {

        String entityType = TestDataBuilder.getABMediaObjectEntityType();

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        Map<String, Object> entityData = TestDataBuilder.getABMediaObjectWithInvalidEntityEntityData();

        try {
            Map<String, Object> result = (Map<String, Object>) ejecucionInterna("GetABMediaObjectTestCases: ",
                    entityType, entityData);

            Map<String, Object> addressBook = (Map<String, Object>) result.get("addressBook");
            
            assertTrue (addressBook.isEmpty());
            
            
            Map<String, Object> images = (Map<String, Object>) result.get("mediaObject");

            ArrayList<Map<String, Object>> moItems = (ArrayList<Map<String, Object>>) images.get("moItems");

            for (Map<String, Object> imageObject : moItems) {

                logger.info(
                        "MULESOFT - FUNCTIONAL_TEST   ----------------------------------------------------------------");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szItemName: " + imageObject.get("szItemName") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szData: " + imageObject.get("szData") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   szMoType: " + imageObject.get("szMoType") + "  ");
                logger.info("MULESOFT - FUNCTIONAL_TEST   moSeqNo: " + imageObject.get("moSeqNo") + "  ");

                String dataType = (String) imageObject.get("szMoType");

                if (dataType.equals("IMAGE")) {

                    decoder((String) imageObject.get("szData"), "/tmp/" + imageObject.get("szItemName"));

                    if (ImageIO.read(new File((String) imageObject.get("szItemName"))) == null) {
                        fail("File is not a image");
                    }

                }

            }

            logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

        } catch (ExternalConnectorException e) {
            logger.error(e.getE1Message());
            throw new Exception(e.getMessage(), e);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage(), e);
        }

        logger.info("MULESOFT - FUNCTIONAL_TEST " + entityType + " END ");

    }

    public static void decoder(String base64Image, String pathFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder()
                    .decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }

    public static boolean isValidURL(String urlString) {
        try {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static String rtfToHtml(Reader rtf) throws IOException {
        JEditorPane p = new JEditorPane();
        p.setContentType("text/rtf");
        EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
        try {
            kitRtf.read(rtf, p.getDocument(), 0);
            kitRtf = null;
            EditorKit kitHtml = p.getEditorKitForContentType("text/html");
            Writer writer = new StringWriter();
            kitHtml.write(writer, p.getDocument(), 0, p.getDocument()
                    .getLength());
            return writer.toString();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return null;
    }

}
