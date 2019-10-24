/**
 * Copyright (C) 2019 ModusBox, Inc. All rights reserved.
 */
package com.acqua.atina.jdeconnector.internal;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdedwards.base.logging.LogOutputHandler;

public class JDEHandler extends StreamHandler implements LogOutputHandler {

    private static final Logger logger = LoggerFactory.getLogger(JDEHandler.class);

    private String handlerName = "";

    public JDEHandler() {
        super();
        logger.debug("JDE Mule Handler Started");
    }

    @Override
    public void closeHandler() {
        close();
    }

    public void publish(LogRecord paramLogRecord) {

        switch (paramLogRecord.getLevel()
            .getName()) {
        case "SEVERE":
            logger.error(paramLogRecord.getMessage());
            break;
        case "APP":
            logger.info(paramLogRecord.getMessage());
            break;
        case "DEBUG":
            logger.debug(paramLogRecord.getMessage()); 
            break;
        case "MANDATORY":
            logger.error(paramLogRecord.getMessage());
            break;
        case "WARN":
            logger.warn(paramLogRecord.getMessage());
            break;
        default:
            logger.trace(paramLogRecord.getMessage());
            break;
        }

    }

    @Override
    public String getName() {
        return this.handlerName;
    }

    @Override
    public void setName(String paramString) {
        this.handlerName = paramString;

    }

}
