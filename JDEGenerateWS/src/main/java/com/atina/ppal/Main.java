/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.ppal;
  
import com.acqua.atina.jdeconnectorservice.service.poolconnection.JDEPoolConnections;
import com.acqua.atina.jdeconnectorservice.wsservice.JDESingleWSConnection;
import com.acqua.jde.jdeconnectorserver.model.Configuracion;
import com.atina.buildjdebundle.exceptions.MetadataServerException;
import java.io.IOException;
import oracle.e1.bssv.JP010000.AddressBookManager;
import oracle.e1.bssv.JP010000.valueobject.GetAddressBook;
import oracle.e1.bssv.JP010000.valueobject.ShowAddressBook;
import oracle.e1.bssv.JP590000.FinancialsManager;
import oracle.e1.bssv.JP590000.valueobject.ConfirmInsertBatchJournalEntryStaging;
import oracle.e1.bssv.JP590000.valueobject.InsertBatchJournalEntry;
import oracle.e1.bssv.JP590000.valueobject.InsertBatchJournalEntryStagingFields;
import oracle.e1.bssv.util.J0100010.valueobject.Entity;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.exception.BusinessServiceException;
import oracle.e1.bssvfoundation.impl.base.Context;
import oracle.e1.bssvfoundation.impl.security.E1Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
/**
 *
 * @author jgodi
 */
public class Main {

     private static final Logger logger = LoggerFactory.getLogger(Main.class);
     
 
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws MetadataServerException, IOException, BusinessServiceException   {
       
        Configuracion config = new Configuracion();
        
        config.setUser("JDE");

        config.setPassword("Modus2020!");

        config.setEnvironment("JDV920");

        config.setRole("*ALL");

        config.setSessionId(0);
        
        config.setTokenExpiration(2400000);
        
        config.setMocking(0);
        
        int sessionID = JDEPoolConnections.getInstance().createConnection(  config.getUser(), 
                                                                                config.getPassword(), 
                                                                                config.getEnvironment(), 
                                                                                config.getRole(), 
                                                                                config.getSessionIdAsInt(),
                                                                                true);
        
        
        
        config.setSessionId(sessionID);
         
        logger.info("              Request Received: " + config.toString());
        
        String addressBookNumber = JDEPoolConnections.getInstance().getSingleConnection(sessionID).getUserPreference().getAddrNum();
        
        logger.info("              Address Book No: " + addressBookNumber);
        
        
        // ==============================================================
        // Get Connection
        // ==============================================================
        
        JDESingleWSConnection singleConnection = (JDESingleWSConnection) JDEPoolConnections.getInstance().getSingleConnection(sessionID);
         
        // ==============================================================
        // Preapring Call Operation
        // ==============================================================
 
        E1Principal e1ppal = singleConnection.getClient().getE1ppal();
         
        
        // ==============================================================
         // Set Context
         // ==============================================================
         
//        IContext context = new oracle.e1.bssvfoundation.impl.base.Context(true,e1ppal,"JP010000", "getAddressBook"); 
//        
//        context.setApplicationID("JP010000");
//        context.setGUID("AddressBookManager");
//        context.setWorkstationName("WS");
//        
//        ((Context) context).incrementPublishedMethodCounter();
        
         // ==============================================================
         // Call Operation
         // ==============================================================
         // String operation = "oracle.e1.bssv.JP590000.FinancialsManager.getGLAccount";
         // oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook
         
//        AddressBookManager objectToCall = new oracle.e1.bssv.JP010000.AddressBookManager();
//        
//        GetAddressBook account = new oracle.e1.bssv.JP010000.valueobject.GetAddressBook();
//        
//        Entity entity = new Entity();
//        entity.setEntityId(28);
//        
//        account.setEntity(entity);
//         
//        ShowAddressBook returnValue = objectToCall.getAddressBook(context, null, account);
//        
        
        // ==============================================================
         // Set Context
         // ==============================================================
         
        IContext context = new oracle.e1.bssvfoundation.impl.base.Context(true,e1ppal,"JP090000", "insertBatchJournalEntry"); 
        
        context.setApplicationID("JP090000");
        context.setGUID("AddressBookManager");
        context.setWorkstationName("WS");
        
        ((Context) context).incrementPublishedMethodCounter();
        
         // ==============================================================
         // Call Operation
         // ==============================================================
         // String operation = "oracle.e1.bssv.JP590000.FinancialsManager.getGLAccount";
         // oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook
         
        oracle.e1.bssv.JP590000.FinancialsManager objectToCall = new oracle.e1.bssv.JP590000.FinancialsManager();
        
        oracle.e1.bssv.JP590000.valueobject.InsertBatchJournalEntry vo = new oracle.e1.bssv.JP590000.valueobject.InsertBatchJournalEntry();
          
        vo.setInsertFields(new InsertBatchJournalEntryStagingFields[1]);
        
        vo.getInsertFields()[0] = new InsertBatchJournalEntryStagingFields();
        
        vo.getInsertFields(0).getEDITransaction().setEdiUserId("039");
        vo.getInsertFields(0).getEDITransaction().setEdiTransactionNumber("1");
        vo.getInsertFields(0).getGLAccount().setBusinessUnit("1");
        vo.getInsertFields(0).getGLAccount().setObjectAccount("4206");
        vo.getInsertFields(0).getEntity().setEntityId(6001);
         
                
         ConfirmInsertBatchJournalEntryStaging returnValue = objectToCall.insertBatchJournalEntry(context, null, vo);
        
        
        
        
        
        // ==============================================================
        // Logout
        // ==============================================================
        
        JDEPoolConnections.getInstance().disconnect(config.getSessionId());
        
        
        
    } 
     
      
     
  
}
