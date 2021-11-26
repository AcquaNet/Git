/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.scheduler;

import com.atina.facade.CleanerManager;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

/**
 *
 * @author jgodi
 */
@ApplicationScoped
public class CleanBean {

    private static final Logger LOG = Logger.getLogger(CleanBean.class);
 
    @ConfigProperty(name = "DIAS_DE_ALMACENAMIENTO_DE_LOGS")
    Long diasLogsAlmacenados;

    @ConfigProperty(name = "cron.expr.clean.actived")
    Boolean actived;
  
    @Scheduled(cron = "{cron.expr.clean}")
    void cronJobCleanMessage() {

        if (actived) {
            
            LOG.info("Cleaning Logs...");
            
            CleanerManager.getInstance().cleanTmpLogs(diasLogsAlmacenados);

        } else
        {
            LOG.debug("Cron Clean No Activo");
        }
    }

}
