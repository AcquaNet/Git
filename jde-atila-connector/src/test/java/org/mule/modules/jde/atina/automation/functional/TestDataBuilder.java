package org.mule.modules.jde.atina.automation.functional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestDataBuilder {

    private static final String GET_PURCHASE_ORDER_FOR_APPROVER = "oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover";
    private static final String GET_ITEM_PRICE = "oracle.e1.bssv.JP410000.InventoryManager.getItemPrice";
    private static final String GET_WO_PO = "oracle.e1.bssv.JP000040.FinancialComplianceManager.getWriteOffProcessingOptions";
    private static final String GET_ITEM_PRICE_AVA = "oracle.e1.bssv.JP420000.SalesOrderManager.getItemPriceAndAvailabilityV3";

    public static String getPurchaseOrdersForApproverEntityType() {
        return GET_PURCHASE_ORDER_FOR_APPROVER;
    }

    public static Map<String, Object> getPurchaseOrdersForApproverEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        Map<String, Object> tmp1 = new java.util.HashMap<String, Object>();
        tmp1.put("entityId", new Integer(533095));

        returnValue.put("approver", tmp1);

        returnValue.put("orderTypeCode", "OP");

        returnValue.put("businessUnitCode", "         30");

        returnValue.put("statusCodeNext", "230");

        returnValue.put("statusApproval", "2N");

        return returnValue;

    }

    public static String getItemPriceEntityType() {
        return GET_ITEM_PRICE;
    }

    public static Map<String, Object> getItemPriceEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        Map<String, Object> tmp1 = new java.util.HashMap<String, Object>();
        tmp1.put("itemId", new Integer(60003));

        returnValue.put("item", tmp1);

        returnValue.put("branchPlantList", "          10");

        return returnValue;

    }

    public static Map<String, Object> getItemPriceInvalidItemEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        Map<String, Object> tmp1 = new java.util.HashMap<String, Object>();
        tmp1.put("itemId", new Integer(6000311));

        returnValue.put("item", tmp1);

        returnValue.put("branchPlantList", "          10");

        return returnValue;

    }

    public static String getWriteOffProcessingOptionsEntityType() {
        return GET_WO_PO;
    }

    public static Map<String, Object> getWriteOffProcessingOptionsEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        ArrayList<String> tmp1 = new java.util.ArrayList<String>();
        tmp1.add("ZJDE0001");

        returnValue.put("processingVersionStandardReceiptEntry", tmp1);

        return returnValue;

    }
    
    public static String getItemPriceAvaEntityType() {
        return GET_ITEM_PRICE_AVA;
    }

    public static Map<String, Object> getItemPriceAvaEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        Map<String, Object> product = new java.util.HashMap<String, Object>();
       
        Map<String, Object> itemId = new java.util.HashMap<String, Object>();
        itemId.put("itemId", new Integer(60290));
         
        
        product.put("item", itemId); 
        product.put("quantityOrdered", BigDecimal.valueOf( 50 ));
         
        returnValue.put("product", product);

        returnValue.put("businessUnit", "          30");
        
        returnValue.put("currencyCode", "USD");
        
        returnValue.put("adjustmentScheduleCode", "NATIONAL");
        
        Map<String, Object> entityId = new java.util.HashMap<String, Object>();
        entityId.put("entityId", new Integer(4242));
        
        returnValue.put("customer", entityId);

        return returnValue;

    }

}
