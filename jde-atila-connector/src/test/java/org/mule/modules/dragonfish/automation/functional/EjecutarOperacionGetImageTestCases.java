package org.mule.modules.dragonfish.automation.functional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

import org.mule.modules.dragonfish.automation.functional.TestDataBuilder;

public class EjecutarOperacionGetImageTestCases extends AbstractTestCases {

    protected final Logger logger = LoggerFactory.getLogger(EjecutarOperacionGetImageTestCases.class);

    @Test
    public void testGetImage() throws Exception {

        logger.info("MULESOFT - FUNCTIONAL_TEST EjecutarOperacionGetImageTestCases: testGetImage() BEGIN ");

        // ======================
        // Get Connector Instance
        // ======================

        String entityType = TestDataBuilder.getImageEntityType();

        Map<String, Object> entityData = TestDataBuilder.getImageEntityData();

        byte[] result = (byte[]) ejecucionInterna("CallObjectTestCases: testEjecutarOperacionValoresGetValorId: ", entityType, entityData);

        writeBytesToFileNio(result, "/tmp/file.png");

        logger.info("MULESOFT - FUNCTIONAL_TEST EjecutarOperacionGetImageTestCases: testGetImage() END ");

    }

    private static void writeBytesToFileNio(byte[] bFile, String fileDest) {

        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
