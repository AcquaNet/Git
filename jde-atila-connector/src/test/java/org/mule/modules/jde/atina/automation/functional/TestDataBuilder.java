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
    private static final String ADD_AB_MO = "oracle.e1.bssv.JPR01MO1.RI_AddressBookMediaObjectManager.addAddressBookMO";
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
        returnValue.put("Transaction ID", 0L);

        return returnValue;

    }

    public static Map<String, Object> getAuthorizationFromUserEntityData(String token) {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        returnValue.put("JDE Token", token);
        returnValue.put("Transaction ID", 0L);

        return returnValue;

    }

    public static Map<String, Object> getJSONSchemaEntityData(String token) {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        returnValue.put("JDE Token", token);
        returnValue.put("Transaction ID", 0L);

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

    public static Map<String, Object> getABMediaObjectWithInvalidEntityEntityData() {

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
        entityId.put("entityLongId", "INVALID");

        returnValue.put("mediaObject", mediaObject);
        returnValue.put("entity", entityId);

        return returnValue;

    }

    public static String addABMediaObjectEntityType() {
        return ADD_AB_MO;
    }

    public static Map<String, Object> addABMediaObjectImageEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        // mediaObject

        ArrayList<Map<String, Object>> moItems = new ArrayList<Map<String, Object>>();

        Map<String, Object> moItem = new java.util.HashMap<String, Object>();

        String imageAsBase64 = "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";

        moItem.put("szItemName", "Image.GIF");
        moItem.put("szData", imageAsBase64);
        moItem.put("szMoType", "IMAGE");
        moItem.put("moSeqNo", new Integer(1));

        moItems.add(moItem);

        Map<String, Object> mediaObject = new java.util.HashMap<String, Object>();
        mediaObject.put("mnAddressNumber", new Integer(0));
        mediaObject.put("moItems", moItems);

        returnValue.put("mediaObject", mediaObject);

        // entityAddress

        Map<String, Object> entityAddress = new java.util.HashMap<String, Object>();
        Map<String, Object> address = new java.util.HashMap<String, Object>();

        address.put("countyCode", "");
        address.put("city", "Denver");
        address.put("countryCode", "US");
        address.put("postalCode", "");
        address.put("addressLine1", "addressLine1");
        address.put("addressLine2", "addressLine2");
        address.put("stateCode", "CO");
        address.put("addressLine3", "");
        address.put("addressLine4", "");
        address.put("mailingName", "DEMO 66");

        entityAddress.put("address", address);

        Map<String, Object> entity = new java.util.HashMap<String, Object>();
        entity.put("entityTaxId", "");
        entity.put("entityLongId", "");
        entity.put("entityId", new Integer(0));

        entityAddress.put("entity", entity);

        returnValue.put("entityAddress", entityAddress);

        // entityTypeCode

        returnValue.put("entityTypeCode", "E");

        // entityName

        returnValue.put("entityName", "DEMO 66");

        // version

        returnValue.put("version", "ZJDE0001");

        return returnValue;

    }

}
