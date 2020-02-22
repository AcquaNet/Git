/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeconnectorwsservice;

import com.jdedwards.database.base.JDBException;
import oracle.e1.bssvfoundation.exception.BusinessServiceException;

import com.jdedwards.system.connector.dynamic.Connector;
import com.jdedwards.system.connector.dynamic.ServerFailureException;
import com.jdedwards.system.connector.dynamic.UserSession;
import com.jdedwards.system.security.SecurityToken;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import oracle.e1.bssv.JP430000.ProcurementManager;
import oracle.e1.bssv.JP430000.valueobject.ConfirmPurchaseOrderApproveReject;
import oracle.e1.bssv.JP430000.valueobject.GetPurchaseOrderDetailForApprover;
import oracle.e1.bssv.JP430000.valueobject.GetPurchaseOrdersForApprover;
import oracle.e1.bssv.JP430000.valueobject.ProcessPurchaseOrderApproveReject;
import oracle.e1.bssv.JP430000.valueobject.PurchaseOrderDetailForApproverResults;
import oracle.e1.bssv.JP430000.valueobject.PurchaseOrderRemarksDetail;
import oracle.e1.bssv.JP430000.valueobject.PurchaseOrdersForApproverResults;
import oracle.e1.bssv.JP430000.valueobject.ShowPurchaseOrderDetailForApprover;
import oracle.e1.bssv.JP430000.valueobject.ShowPurchaseOrdersForApprover;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.impl.base.Context;
import oracle.e1.bssvfoundation.impl.connection.SBFConnection;
import oracle.e1.bssvfoundation.impl.connection.SBFConnectionManager;
import oracle.e1.bssvfoundation.impl.security.E1Principal;
 

/**
 * @author jgodi
 */
public class Main {

    public static void testPendingPO() throws BusinessServiceException, ServerFailureException, JDBException {
        
         
        // =====================================================
        // LOGIN
        // =====================================================
         
        // -----------------------------------------
        // Obtener Session ID
        // ----------------------------------------- 
        
        int sessionId = Connector.getInstance().loginBase("JDE", "Modus2017!", "JDV920", "*ALL",true,true);
        
        // -----------------------------------------
        // GET User Session
        // ----------------------------------------- 
        
        UserSession userSession = Connector.getInstance().getUserSession(sessionId);
        userSession.setSbfConnectorMode(true);
         
        // -----------------------------------------
        // GET Token
        // -----------------------------------------
        
        SecurityToken secToken = userSession.getSecurityToken();
        
        // -----------------------------------------
        // Creacion del Context
        // -----------------------------------------
        
        E1Principal e1ppal = new E1Principal("JDE",secToken,"JDV920", "*ALL",sessionId);
         
        IContext context = new oracle.e1.bssvfoundation.impl.base.Context(true,e1ppal,"JP430000", "getPurchaseOrdersForApprover"); 
        
        context.setApplicationID("JP430000");
        context.setGUID("PurchaseOrdersForApprover");
        context.setWorkstationName("DEMO");
        
        ((Context) context).incrementPublishedMethodCounter(); 
        
        // -----------------------------------------
        // Obtencion del SBFConnection
        // -----------------------------------------
        
        SBFConnectionManager valor = SBFConnectionManager.getInstance();
         
        SBFConnection defaultConnection = valor.getDefaultConnection((Context) context, true);
          
        // =====================================================
        // Recuperar Ordenes Pendientes de Aprobacion
        // =====================================================
         
        // -----------------------------------------
        // Creacion de Parametros de Input
        // -----------------------------------------
         
        GetPurchaseOrdersForApprover vo = new GetPurchaseOrdersForApprover();
        
        // -----------------------------------------
        // Creacion de Parametros de Output
        // -----------------------------------------
        
        ShowPurchaseOrdersForApprover conadd = null;

        // -----------------------------------------
        // Ingreso de Parametros de Input
        // -----------------------------------------
        
        oracle.e1.bssv.util.J0100010.valueobject.Entity aprover = new oracle.e1.bssv.util.J0100010.valueobject.Entity();
         
        aprover.setEntityId(533095); 
      
        vo.setApprover(aprover);   // RPER
        vo.setOrderTypeCode("OP"); // DCTO
        vo.setBusinessUnitCode("          30");  //MCU
        vo.setStatusApproval("2N"); // ' O' '2N'    ASTS Status - Approval 
        vo.setStatusCodeNext("230"); 
              
        // -----------------------------------------
        // Invoca WS
        // -----------------------------------------
        
         try { 
             conadd = new ProcurementManager().getPurchaseOrdersForApprover(context, defaultConnection, vo); 
        } catch (BusinessServiceException ex) {
            System.out.println(ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
          
         
         
        // -----------------------------------------
        // Mostrar Valores
        // ----------------------------------------- 
         
        for(PurchaseOrdersForApproverResults value:conadd.getPurchaseOrdersForApproverResults())
        {
            
            System.out.println("============================================================================================");
            System.out.println("Document Order Invoice Number: " + Integer.toString( value.getDocumentOrderInvoiceNumber()));
            System.out.println("Document Order Type Code: " + value.getDocumentOrderTypeCode());
            System.out.println("Document Company Key Order No: " + value.getDocumentCompanyKeyOrderNo());
            System.out.println("Suffix: " + value.getDocumentSuffix()); 
            
            System.out.println("Entity Name Originator: " + value.getEntityNameOriginator()); 
            System.out.println("Entity Name Supplier: " + value.getEntityNameSupplier());
            System.out.println("Amount Gross: " + value.getAmountGross());
            System.out.println("Currency Code Base: " + value.getCurrencyCodeBase());
            
            Date date = value.getDateRequested().getTime();          
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");      
            System.out.println("Date Requested: " + format1.format(date)); 
            
            System.out.println("Hold Code Purchase Orders: " + value.getHoldCodePurchaseOrder());
            System.out.println("Approval Routing Code Purchase Order: " + value.getApprovalRoutingCodePurchaseOrder());
            
            
            System.out.println("---------- Supplier -------------------------");
            System.out.println("Id: " + value.getSupplier().getEntityId());
            System.out.println("AddressLine1Supplier: " + value.getAddressLine1Supplier());
            System.out.println("AddressLine2Supplier: " + value.getAddressLine2Supplier());
            System.out.println("AddressLine3Supplier: " + value.getAddressLine3Supplier());
            System.out.println("AddressLine4Supplier: " + value.getAddressLine4Supplier());
            System.out.println("AddressLine4Supplier: " + value.getCitySupplier());
            System.out.println("CitySupplier: " + value.getCitySupplier());
            System.out.println("CountyCodeSupplier: " + value.getCountyCodeSupplier());
            System.out.println("PostalCodeSupplier: " + value.getPostalCodeSupplier());
            System.out.println("StateCodeSupplier: " + value.getStateCodeSupplier()); 
            
            System.out.println("---------- Ship To -------------------------");
            System.out.println("Id: " + value.getShipTo().getEntityId());
            System.out.println("AddressLine1Supplier: " + value.getAddressLine1ShipTo());
            System.out.println("AddressLine2Supplier: " + value.getAddressLine2ShipTo());
            System.out.println("AddressLine3Supplier: " + value.getAddressLine3ShipTo());
            System.out.println("AddressLine4Supplier: " + value.getAddressLine4ShipTo());
            System.out.println("AddressLine4Supplier: " + value.getCityShipTo());
            System.out.println("CitySupplier: " + value.getCityShipTo());
            System.out.println("CountyCodeSupplier: " + value.getCountyCodeShipTo());
            System.out.println("PostalCodeSupplier: " + value.getPostalCodeShipTo());
            System.out.println("StateCodeSupplier: " + value.getStateCodeShipTo()); 
              
        }
         
        // =====================================================
        // Recuperar Detalle de Orden Pendientes de Aprobacion
        // =====================================================
        
        // -----------------------------------------
        // Creacion de Parametros de Input
        // -----------------------------------------
         
        GetPurchaseOrderDetailForApprover voDetail = new GetPurchaseOrderDetailForApprover();
        
        // -----------------------------------------
        // Creacion de Parametros de Output
        // -----------------------------------------
        
        ShowPurchaseOrderDetailForApprover outputDetail = null;

        // -----------------------------------------
        // Ingreso de Parametros de Input
        // -----------------------------------------
        
        voDetail.setCompanyKeyOrderNo("00001");
        voDetail.setDocumentOrderInvoiceNumber(4915);
        voDetail.setDocumentOrderTypeCode("OP");
        voDetail.setDocumentSuffix("000");
        voDetail.setStatusCodeNext("230");
        
        
        // -----------------------------------------
        // Invoca WS
        // -----------------------------------------
        
         try { 
             outputDetail = new ProcurementManager().getPurchaseOrderDetailForApprover(context, defaultConnection, voDetail); 
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
           
         
        System.out.println("============================================================================================");
        System.out.println(" Orden 4915/OP/00001 ");
        System.out.println("============================================================================================"); 
        
         for(PurchaseOrderDetailForApproverResults value:outputDetail.getPurchaseOrderDetailForApproverResults())
        {
            System.out.println("============================================================================================");
            System.out.println("DocumentLineNumber(): " + value.getDocumentLineNumber());
            System.out.println("DescriptionLine1: " + value.getDescriptionLine1());
            System.out.println("Units Transaction Qty: " + value.getUnitsTransactionQty());
            System.out.println("Purchasing Unit Price: " + value.getPurchasingUnitPrice());
            System.out.println("Amount Extended Price: " + value.getAmountExtendedPrice()); 
            
            Date date = value.getDateRequested().getTime();          
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");      
            System.out.println("Date Requested: " + format1.format(date));
            
            date = value.getDateTransaction().getTime();           
            System.out.println("Date Transaction: " + format1.format(date));
             
            System.out.println("Line Type Code: " + value.getLineTypeCode()); 
            System.out.println("Branch Plant: " + value.getBusinessUnitCode()); 
            System.out.println("Foreing Price: " + value.getForeignPurchasingPrice()); 
            System.out.println("Foreing Total: " + value.getAmountForeignExtendPrice()); 
            System.out.println("Account: " + value.getAcctNumber()); 
             
            
            
        }
         
        System.out.println("============================================================================================");
        System.out.println(" Aprobacion de la Orden ");
        System.out.println("============================================================================================"); 
        
        // -----------------------------------------
        // Creacion de Parametros de Input
        // -----------------------------------------
         
        ProcessPurchaseOrderApproveReject voArproveReject = new ProcessPurchaseOrderApproveReject();
        
        // -----------------------------------------
        // Creacion de Parametros de Output
        // -----------------------------------------
        
        @SuppressWarnings("unused")
		ConfirmPurchaseOrderApproveReject outputArproveReject = null;

        // -----------------------------------------
        // Ingreso de Parametros de Input
        // -----------------------------------------
        
        // Configuracion
        
        voArproveReject.setP43081Version("ZJDE0002");
        
        // Transaccion
         
        voArproveReject.setDocumentNumber(4915);
        voArproveReject.setDocumentType("OP");
        voArproveReject.setCompanyKeyOrderNumber("00001");
        voArproveReject.setOrderSuffix("000");
        voArproveReject.setLineNumber(new BigDecimal(7.0));
        voArproveReject.setHoldCode("A1");
        voArproveReject.setApprovalRouteCode("MPAIT1");
        
        voArproveReject.setAmountToApprove(new BigDecimal(150.0));
        voArproveReject.setApproverAddressNumber(533095);
        voArproveReject.setBusinessUnit("          30");
        
        voArproveReject.setApproveReject("A");
        voArproveReject.setStatusApproval("3A"); 
        
        voArproveReject.setBudgetPassword("");
        
        voArproveReject.setRemark("REJECT 1");
        
        
        PurchaseOrderRemarksDetail[] detail = null;
      
        voArproveReject.setDetail(detail);
        
        // -----------------------------------------
        // Invoca WS
        // -----------------------------------------
        
         try { 
             outputArproveReject = new ProcurementManager().processPurchaseOrderApproveReject(context, defaultConnection, voArproveReject); 
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
           
        // -----------------------------------------
        // Cerrar conexion
        // -----------------------------------------
         
        
          Connector.getInstance().logoff(sessionId);
          Connector.getInstance().shutDown();

    }
    
    public static void testProcessPurchaseOrderApproveReject() throws BusinessServiceException, ServerFailureException, JDBException {
       
        
    }
     

    public static void main(String[] args) throws BusinessServiceException, ServerFailureException, JDBException {

         
       testPendingPO();

    }

}
