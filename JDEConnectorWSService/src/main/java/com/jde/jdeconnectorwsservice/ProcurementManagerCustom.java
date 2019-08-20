/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeconnectorwsservice;

import oracle.e1.bssv.JP430000.ProcurementManager;
import oracle.e1.bssv.JP430000.valueobject.ConfirmPurchaseOrderApproveReject;
import oracle.e1.bssv.JP430000.valueobject.GetPurchaseOrderDetailForApprover;
import oracle.e1.bssv.JP430000.valueobject.GetPurchaseOrdersForApprover;
import oracle.e1.bssv.JP430000.valueobject.ProcessPurchaseOrderApproveReject;
import oracle.e1.bssv.JP430000.valueobject.ShowPurchaseOrderDetailForApprover;
import oracle.e1.bssv.JP430000.valueobject.ShowPurchaseOrdersForApprover;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.exception.BusinessServiceException;
import oracle.e1.bssvfoundation.impl.connection.SBFConnection;

/**
 *
 * @author jgodi
 */
public class ProcurementManagerCustom extends ProcurementManager{
    
    public ShowPurchaseOrdersForApprover getPurchaseOrdersForApprover(IContext context, SBFConnection defaultConnection, GetPurchaseOrdersForApprover vo) throws BusinessServiceException {
        
        // -----------------------------------------
        // Llamado al WS
        // -----------------------------------------
  
        ShowPurchaseOrdersForApprover conadd = super.getPurchaseOrdersForApprover(context,defaultConnection , vo );
       
        
        return conadd;
        
    }
    
    public ShowPurchaseOrderDetailForApprover getPurchaseOrderDetailForApprover(IContext context, SBFConnection defaultConnection, GetPurchaseOrderDetailForApprover vo) throws BusinessServiceException {
        
        // -----------------------------------------
        // Llamado al WS
        // -----------------------------------------
  
        ShowPurchaseOrderDetailForApprover conadd = super.getPurchaseOrderDetailForApprover(context,defaultConnection , vo );
       
        
        return conadd;
        
    }
    
     public ConfirmPurchaseOrderApproveReject processPurchaseOrderApproveReject(IContext context, SBFConnection defaultConnection, ProcessPurchaseOrderApproveReject vo) throws BusinessServiceException {
        
        // -----------------------------------------
        // Llamado al WS
        // -----------------------------------------
  
        ConfirmPurchaseOrderApproveReject conadd = super.processPurchaseOrderApproveReject(context,defaultConnection , vo );
       
        
        return conadd;
        
    }
    
    
}
