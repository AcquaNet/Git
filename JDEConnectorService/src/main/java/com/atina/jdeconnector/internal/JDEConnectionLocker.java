/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.Semaphore;

/**
 *
 * @author jgodi
 */
public class JDEConnectionLocker {
    
      private static final Logger logger = LoggerFactory.getLogger(JDEConnectionLocker.class);

    private static Semaphore writeLock = new Semaphore(1, false);

    public static JDEConnectionLocker getInstance() {
        return JDEObjectLockerHolder.INSTANCE;
    }

    private static class JDEObjectLockerHolder {

        private JDEObjectLockerHolder() {
        }

        private static final JDEConnectionLocker INSTANCE = new JDEConnectionLocker();
    }

    public void getWriteLock() throws InterruptedException {
        logger.debug("Locker: Acquiring lock...");
        writeLock.acquire();
        logger.debug("Locker: Lock Acquired");
    }

    public void releaseWriteLock() {
        logger.debug("Locker: Releasing lock...");
        writeLock.release();
        logger.debug("Locker: Lock released");
    }
    
}
