package org.mule.modules.jde.atina.automation.functional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestDataBuilder {

    private static final String GET_PURCHASE_ORDER_FOR_APPROVER = "oracle.e1.bssv.JP430000.ProcurementManager.getPurchaseOrdersForApprover";
    private static final String GET_ITEM_PRICE = "oracle.e1.bssv.JP410000.InventoryManager.getItemPrice";
    private static final String GET_WO_PO = "oracle.e1.bssv.JP000040.FinancialComplianceManager.getWriteOffProcessingOptions";
    private static final String GET_ITEM_PRICE_AVA = "oracle.e1.bssv.JP420000.SalesOrderManager.getItemPriceAndAvailabilityV3";
    private static final String GET_AB_MO = "oracle.e1.bssv.JPR01MO1.RI_AddressBookMediaObjectManager.getAddressBookMO";

    private static final String AUTH_FROM_TOKEN = "FromTokenData";
    private static final String AUTH_FROM_USER = "FromUserData";
    private static final String AUTH_LOGOUT = "LogoutTokenData";

    public static String getAuthorizationFromTokenEntityType() {
        return AUTH_FROM_TOKEN;
    }

    public static String getAuthorizationFromUserEntityType() {
        return AUTH_FROM_USER;
    }

    public static String getAuthorizationLogoutEntityType() {
        return AUTH_LOGOUT;
    }

    public static Map<String, Object> getAuthorizationFromTokenEntityData(String token) {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        returnValue.put("JDE Token", token);

        return returnValue;

    }

    public static Map<String, Object> getAuthorizationFromUserEntityData(String token) {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        returnValue.put("JDE Token", token);

        return returnValue;

    }

    public static Map<String, Object> getJSONSchemaEntityData(String token) {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        returnValue.put("JDE Token", token);

        return returnValue;

    }

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
        product.put("quantityOrdered", BigDecimal.valueOf(50));

        returnValue.put("product", product);

        returnValue.put("businessUnit", "          30");

        returnValue.put("currencyCode", "USD");

        returnValue.put("adjustmentScheduleCode", "NATIONAL");

        Map<String, Object> entityId = new java.util.HashMap<String, Object>();
        entityId.put("entityId", new Integer(4242));

        returnValue.put("customer", entityId);

        return returnValue;

    }
    
    public static String getABMediaObjectEntityType() {
        return GET_AB_MO;
    }

    public static Map<String, Object> getABMediaObjectWithImageAndURLEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();
        
        ArrayList<Map<String, Object>> moItems = new ArrayList<Map<String, Object>>();
 
        Map<String, Object> moItem = new java.util.HashMap<String, Object>();
         
        moItem.put("szItemName", "");
        moItem.put("szData", new ArrayList<Object>());
        moItem.put("szMoType", "");
        moItem.put("moSeqNo", new Integer(0));
        
        moItems.add(moItem);
        
        
        Map<String, Object> mediaObject = new java.util.HashMap<String, Object>();
        mediaObject.put("mnAddressNumber", new Integer(7701));
        mediaObject.put("moItems", moItems);
         

        Map<String, Object> entityId = new java.util.HashMap<String, Object>();
        entityId.put("entityId", new Integer(7701));
        entityId.put("entityTaxId", "256136888");
        entityId.put("entityLongId", "");

        returnValue.put("mediaObject", mediaObject);
        returnValue.put("entity", entityId);

        return returnValue;

    }
    
    public static Map<String, Object> getABMediaObjectWithTextEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();
        
        ArrayList<Map<String, Object>> moItems = new ArrayList<Map<String, Object>>();
 
        Map<String, Object> moItem = new java.util.HashMap<String, Object>();
         
        moItem.put("szItemName", "");
        moItem.put("szData", new ArrayList<Object>());
        moItem.put("szMoType", "");
        moItem.put("moSeqNo", new Integer(0));
        
        moItems.add(moItem);
        
        
        Map<String, Object> mediaObject = new java.util.HashMap<String, Object>();
        mediaObject.put("mnAddressNumber", new Integer(3333));
        mediaObject.put("moItems", moItems);
         

        Map<String, Object> entityId = new java.util.HashMap<String, Object>();
        entityId.put("entityId", new Integer(3333));
        entityId.put("entityTaxId", "");
        entityId.put("entityLongId", "");

        returnValue.put("mediaObject", mediaObject);
        returnValue.put("entity", entityId);

        return returnValue;

    }
    
    public static Map<String, Object> getABMediaObjectWithImageJPGEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();
        
        ArrayList<Map<String, Object>> moItems = new ArrayList<Map<String, Object>>();
 
        Map<String, Object> moItem = new java.util.HashMap<String, Object>();
         
        moItem.put("szItemName", "");
        moItem.put("szData", new ArrayList<Object>());
        moItem.put("szMoType", "");
        moItem.put("moSeqNo", new Integer(0));
        
        moItems.add(moItem);
        
        
        Map<String, Object> mediaObject = new java.util.HashMap<String, Object>();
        mediaObject.put("mnAddressNumber", new Integer(7703));
        mediaObject.put("moItems", moItems);
         

        Map<String, Object> entityId = new java.util.HashMap<String, Object>();
        entityId.put("entityId", new Integer(7703));
        entityId.put("entityTaxId", "481560670");
        entityId.put("entityLongId", "");

        returnValue.put("mediaObject", mediaObject);
        returnValue.put("entity", entityId);

        return returnValue;

    }

}
