package org.mule.modules.jde.atina.automation.functional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataBuilder {
 
    private static final String GET_PURCHASE_ORDER_FOR_APPROVER = "oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover";
    
    
    public static String getPurchaseOrdersForApproverEntityType() {
        return GET_PURCHASE_ORDER_FOR_APPROVER;
    }
    
    public static Map<String, Object> getPurchaseOrdersForApproverEntityData() {
    	
    	final Map<String, Object> returnValue = new HashMap<String, Object>();

    	
    	Map<String, Object> tmp1 = new java.util.HashMap();
    	tmp1.put("entityId", new Integer(533095));
    	
    	returnValue.put("approver", tmp1);
    	
    	returnValue.put("orderTypeCode", "OP");
    	
    	returnValue.put("businessUnitCode", "         30");
    	
    	returnValue.put("statusCodeNext", "230");
    	
    	returnValue.put("statusApproval", "2N");
    	  
        return returnValue;
 
    }
     
}
