
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "citySupplier",
    "addressLine2Supplier",
    "stateCodeSupplier",
    "addressLine1Supplier",
    "countyCodeShipTo",
    "approvalRoutingCodeHeldOrders",
    "addressLine4Supplier",
    "documentCompanyKeyOrderNo",
    "dateRequested",
    "documentSuffix",
    "originator",
    "addressLine3ShipTo",
    "buyerNumber",
    "amountGross",
    "addressLine3Supplier",
    "holdCodePurchaseOrder",
    "countryCodeShipTo",
    "postalCodeSupplier",
    "currencyCodeForeignDecimals",
    "currencyCodeForeign",
    "supplier",
    "holdCodeHeldOrders",
    "businessUnitCode",
    "amountForeign",
    "approver",
    "documentOrderTypeCode",
    "addressLine2ShipTo",
    "currencyCodeBaseDecimals",
    "entityNameSupplier",
    "documentOrderInvoiceNumber",
    "statusApproval",
    "descriptionOrder",
    "addressLine4ShipTo",
    "entityNameApprover",
    "entityNameOriginator",
    "dateTransaction",
    "countryCodeSupplier",
    "currencyCodeBase",
    "postalCodeShipTo",
    "approvalRoutingCodePurchaseOrder",
    "countyCodeSupplier",
    "entityNameShipTo",
    "addressLine1ShipTo",
    "currencyMode",
    "cityShipTo",
    "stateCodeShipTo",
    "shipTo"
})
public class PurchaseOrdersForApproverResult {

    @JsonProperty("citySupplier")
    private String citySupplier;
    @JsonProperty("addressLine2Supplier")
    private String addressLine2Supplier;
    @JsonProperty("stateCodeSupplier")
    private String stateCodeSupplier;
    @JsonProperty("addressLine1Supplier")
    private String addressLine1Supplier;
    @JsonProperty("countyCodeShipTo")
    private String countyCodeShipTo;
    @JsonProperty("approvalRoutingCodeHeldOrders")
    private String approvalRoutingCodeHeldOrders;
    @JsonProperty("addressLine4Supplier")
    private String addressLine4Supplier;
    @JsonProperty("documentCompanyKeyOrderNo")
    private String documentCompanyKeyOrderNo;
    @JsonProperty("dateRequested")
    private String dateRequested;
    @JsonProperty("documentSuffix")
    private String documentSuffix;
    @JsonProperty("originator")
    private Originator originator;
    @JsonProperty("addressLine3ShipTo")
    private String addressLine3ShipTo;
    @JsonProperty("buyerNumber")
    private BuyerNumber buyerNumber;
    @JsonProperty("amountGross")
    private Double amountGross;
    @JsonProperty("addressLine3Supplier")
    private String addressLine3Supplier;
    @JsonProperty("holdCodePurchaseOrder")
    private String holdCodePurchaseOrder;
    @JsonProperty("countryCodeShipTo")
    private String countryCodeShipTo;
    @JsonProperty("postalCodeSupplier")
    private String postalCodeSupplier;
    @JsonProperty("currencyCodeForeignDecimals")
    private Integer currencyCodeForeignDecimals;
    @JsonProperty("currencyCodeForeign")
    private String currencyCodeForeign;
    @JsonProperty("supplier")
    private Supplier supplier;
    @JsonProperty("holdCodeHeldOrders")
    private String holdCodeHeldOrders;
    @JsonProperty("businessUnitCode")
    private String businessUnitCode;
    @JsonProperty("amountForeign")
    private Double amountForeign;
    @JsonProperty("approver")
    private Approver approver;
    @JsonProperty("documentOrderTypeCode")
    private String documentOrderTypeCode;
    @JsonProperty("addressLine2ShipTo")
    private String addressLine2ShipTo;
    @JsonProperty("currencyCodeBaseDecimals")
    private Integer currencyCodeBaseDecimals;
    @JsonProperty("entityNameSupplier")
    private String entityNameSupplier;
    @JsonProperty("documentOrderInvoiceNumber")
    private Integer documentOrderInvoiceNumber;
    @JsonProperty("statusApproval")
    private String statusApproval;
    @JsonProperty("descriptionOrder")
    private String descriptionOrder;
    @JsonProperty("addressLine4ShipTo")
    private String addressLine4ShipTo;
    @JsonProperty("entityNameApprover")
    private String entityNameApprover;
    @JsonProperty("entityNameOriginator")
    private String entityNameOriginator;
    @JsonProperty("dateTransaction")
    private String dateTransaction;
    @JsonProperty("countryCodeSupplier")
    private String countryCodeSupplier;
    @JsonProperty("currencyCodeBase")
    private String currencyCodeBase;
    @JsonProperty("postalCodeShipTo")
    private String postalCodeShipTo;
    @JsonProperty("approvalRoutingCodePurchaseOrder")
    private String approvalRoutingCodePurchaseOrder;
    @JsonProperty("countyCodeSupplier")
    private String countyCodeSupplier;
    @JsonProperty("entityNameShipTo")
    private String entityNameShipTo;
    @JsonProperty("addressLine1ShipTo")
    private String addressLine1ShipTo;
    @JsonProperty("currencyMode")
    private String currencyMode;
    @JsonProperty("cityShipTo")
    private String cityShipTo;
    @JsonProperty("stateCodeShipTo")
    private String stateCodeShipTo;
    @JsonProperty("shipTo")
    private ShipTo shipTo;

    @JsonProperty("citySupplier")
    public String getCitySupplier() {
        return citySupplier;
    }

    @JsonProperty("citySupplier")
    public void setCitySupplier(String citySupplier) {
        this.citySupplier = citySupplier;
    }

    @JsonProperty("addressLine2Supplier")
    public String getAddressLine2Supplier() {
        return addressLine2Supplier;
    }

    @JsonProperty("addressLine2Supplier")
    public void setAddressLine2Supplier(String addressLine2Supplier) {
        this.addressLine2Supplier = addressLine2Supplier;
    }

    @JsonProperty("stateCodeSupplier")
    public String getStateCodeSupplier() {
        return stateCodeSupplier;
    }

    @JsonProperty("stateCodeSupplier")
    public void setStateCodeSupplier(String stateCodeSupplier) {
        this.stateCodeSupplier = stateCodeSupplier;
    }

    @JsonProperty("addressLine1Supplier")
    public String getAddressLine1Supplier() {
        return addressLine1Supplier;
    }

    @JsonProperty("addressLine1Supplier")
    public void setAddressLine1Supplier(String addressLine1Supplier) {
        this.addressLine1Supplier = addressLine1Supplier;
    }

    @JsonProperty("countyCodeShipTo")
    public String getCountyCodeShipTo() {
        return countyCodeShipTo;
    }

    @JsonProperty("countyCodeShipTo")
    public void setCountyCodeShipTo(String countyCodeShipTo) {
        this.countyCodeShipTo = countyCodeShipTo;
    }

    @JsonProperty("approvalRoutingCodeHeldOrders")
    public String getApprovalRoutingCodeHeldOrders() {
        return approvalRoutingCodeHeldOrders;
    }

    @JsonProperty("approvalRoutingCodeHeldOrders")
    public void setApprovalRoutingCodeHeldOrders(String approvalRoutingCodeHeldOrders) {
        this.approvalRoutingCodeHeldOrders = approvalRoutingCodeHeldOrders;
    }

    @JsonProperty("addressLine4Supplier")
    public String getAddressLine4Supplier() {
        return addressLine4Supplier;
    }

    @JsonProperty("addressLine4Supplier")
    public void setAddressLine4Supplier(String addressLine4Supplier) {
        this.addressLine4Supplier = addressLine4Supplier;
    }

    @JsonProperty("documentCompanyKeyOrderNo")
    public String getDocumentCompanyKeyOrderNo() {
        return documentCompanyKeyOrderNo;
    }

    @JsonProperty("documentCompanyKeyOrderNo")
    public void setDocumentCompanyKeyOrderNo(String documentCompanyKeyOrderNo) {
        this.documentCompanyKeyOrderNo = documentCompanyKeyOrderNo;
    }

    @JsonProperty("dateRequested")
    public String getDateRequested() {
        return dateRequested;
    }

    @JsonProperty("dateRequested")
    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    @JsonProperty("documentSuffix")
    public String getDocumentSuffix() {
        return documentSuffix;
    }

    @JsonProperty("documentSuffix")
    public void setDocumentSuffix(String documentSuffix) {
        this.documentSuffix = documentSuffix;
    }

    @JsonProperty("originator")
    public Originator getOriginator() {
        return originator;
    }

    @JsonProperty("originator")
    public void setOriginator(Originator originator) {
        this.originator = originator;
    }

    @JsonProperty("addressLine3ShipTo")
    public String getAddressLine3ShipTo() {
        return addressLine3ShipTo;
    }

    @JsonProperty("addressLine3ShipTo")
    public void setAddressLine3ShipTo(String addressLine3ShipTo) {
        this.addressLine3ShipTo = addressLine3ShipTo;
    }

    @JsonProperty("buyerNumber")
    public BuyerNumber getBuyerNumber() {
        return buyerNumber;
    }

    @JsonProperty("buyerNumber")
    public void setBuyerNumber(BuyerNumber buyerNumber) {
        this.buyerNumber = buyerNumber;
    }

    @JsonProperty("amountGross")
    public Double getAmountGross() {
        return amountGross;
    }

    @JsonProperty("amountGross")
    public void setAmountGross(Double amountGross) {
        this.amountGross = amountGross;
    }

    @JsonProperty("addressLine3Supplier")
    public String getAddressLine3Supplier() {
        return addressLine3Supplier;
    }

    @JsonProperty("addressLine3Supplier")
    public void setAddressLine3Supplier(String addressLine3Supplier) {
        this.addressLine3Supplier = addressLine3Supplier;
    }

    @JsonProperty("holdCodePurchaseOrder")
    public String getHoldCodePurchaseOrder() {
        return holdCodePurchaseOrder;
    }

    @JsonProperty("holdCodePurchaseOrder")
    public void setHoldCodePurchaseOrder(String holdCodePurchaseOrder) {
        this.holdCodePurchaseOrder = holdCodePurchaseOrder;
    }

    @JsonProperty("countryCodeShipTo")
    public String getCountryCodeShipTo() {
        return countryCodeShipTo;
    }

    @JsonProperty("countryCodeShipTo")
    public void setCountryCodeShipTo(String countryCodeShipTo) {
        this.countryCodeShipTo = countryCodeShipTo;
    }

    @JsonProperty("postalCodeSupplier")
    public String getPostalCodeSupplier() {
        return postalCodeSupplier;
    }

    @JsonProperty("postalCodeSupplier")
    public void setPostalCodeSupplier(String postalCodeSupplier) {
        this.postalCodeSupplier = postalCodeSupplier;
    }

    @JsonProperty("currencyCodeForeignDecimals")
    public Integer getCurrencyCodeForeignDecimals() {
        return currencyCodeForeignDecimals;
    }

    @JsonProperty("currencyCodeForeignDecimals")
    public void setCurrencyCodeForeignDecimals(Integer currencyCodeForeignDecimals) {
        this.currencyCodeForeignDecimals = currencyCodeForeignDecimals;
    }

    @JsonProperty("currencyCodeForeign")
    public String getCurrencyCodeForeign() {
        return currencyCodeForeign;
    }

    @JsonProperty("currencyCodeForeign")
    public void setCurrencyCodeForeign(String currencyCodeForeign) {
        this.currencyCodeForeign = currencyCodeForeign;
    }

    @JsonProperty("supplier")
    public Supplier getSupplier() {
        return supplier;
    }

    @JsonProperty("supplier")
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @JsonProperty("holdCodeHeldOrders")
    public String getHoldCodeHeldOrders() {
        return holdCodeHeldOrders;
    }

    @JsonProperty("holdCodeHeldOrders")
    public void setHoldCodeHeldOrders(String holdCodeHeldOrders) {
        this.holdCodeHeldOrders = holdCodeHeldOrders;
    }

    @JsonProperty("businessUnitCode")
    public String getBusinessUnitCode() {
        return businessUnitCode;
    }

    @JsonProperty("businessUnitCode")
    public void setBusinessUnitCode(String businessUnitCode) {
        this.businessUnitCode = businessUnitCode;
    }

    @JsonProperty("amountForeign")
    public Double getAmountForeign() {
        return amountForeign;
    }

    @JsonProperty("amountForeign")
    public void setAmountForeign(Double amountForeign) {
        this.amountForeign = amountForeign;
    }

    @JsonProperty("approver")
    public Approver getApprover() {
        return approver;
    }

    @JsonProperty("approver")
    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    @JsonProperty("documentOrderTypeCode")
    public String getDocumentOrderTypeCode() {
        return documentOrderTypeCode;
    }

    @JsonProperty("documentOrderTypeCode")
    public void setDocumentOrderTypeCode(String documentOrderTypeCode) {
        this.documentOrderTypeCode = documentOrderTypeCode;
    }

    @JsonProperty("addressLine2ShipTo")
    public String getAddressLine2ShipTo() {
        return addressLine2ShipTo;
    }

    @JsonProperty("addressLine2ShipTo")
    public void setAddressLine2ShipTo(String addressLine2ShipTo) {
        this.addressLine2ShipTo = addressLine2ShipTo;
    }

    @JsonProperty("currencyCodeBaseDecimals")
    public Integer getCurrencyCodeBaseDecimals() {
        return currencyCodeBaseDecimals;
    }

    @JsonProperty("currencyCodeBaseDecimals")
    public void setCurrencyCodeBaseDecimals(Integer currencyCodeBaseDecimals) {
        this.currencyCodeBaseDecimals = currencyCodeBaseDecimals;
    }

    @JsonProperty("entityNameSupplier")
    public String getEntityNameSupplier() {
        return entityNameSupplier;
    }

    @JsonProperty("entityNameSupplier")
    public void setEntityNameSupplier(String entityNameSupplier) {
        this.entityNameSupplier = entityNameSupplier;
    }

    @JsonProperty("documentOrderInvoiceNumber")
    public Integer getDocumentOrderInvoiceNumber() {
        return documentOrderInvoiceNumber;
    }

    @JsonProperty("documentOrderInvoiceNumber")
    public void setDocumentOrderInvoiceNumber(Integer documentOrderInvoiceNumber) {
        this.documentOrderInvoiceNumber = documentOrderInvoiceNumber;
    }

    @JsonProperty("statusApproval")
    public String getStatusApproval() {
        return statusApproval;
    }

    @JsonProperty("statusApproval")
    public void setStatusApproval(String statusApproval) {
        this.statusApproval = statusApproval;
    }

    @JsonProperty("descriptionOrder")
    public String getDescriptionOrder() {
        return descriptionOrder;
    }

    @JsonProperty("descriptionOrder")
    public void setDescriptionOrder(String descriptionOrder) {
        this.descriptionOrder = descriptionOrder;
    }

    @JsonProperty("addressLine4ShipTo")
    public String getAddressLine4ShipTo() {
        return addressLine4ShipTo;
    }

    @JsonProperty("addressLine4ShipTo")
    public void setAddressLine4ShipTo(String addressLine4ShipTo) {
        this.addressLine4ShipTo = addressLine4ShipTo;
    }

    @JsonProperty("entityNameApprover")
    public String getEntityNameApprover() {
        return entityNameApprover;
    }

    @JsonProperty("entityNameApprover")
    public void setEntityNameApprover(String entityNameApprover) {
        this.entityNameApprover = entityNameApprover;
    }

    @JsonProperty("entityNameOriginator")
    public String getEntityNameOriginator() {
        return entityNameOriginator;
    }

    @JsonProperty("entityNameOriginator")
    public void setEntityNameOriginator(String entityNameOriginator) {
        this.entityNameOriginator = entityNameOriginator;
    }

    @JsonProperty("dateTransaction")
    public String getDateTransaction() {
        return dateTransaction;
    }

    @JsonProperty("dateTransaction")
    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    @JsonProperty("countryCodeSupplier")
    public String getCountryCodeSupplier() {
        return countryCodeSupplier;
    }

    @JsonProperty("countryCodeSupplier")
    public void setCountryCodeSupplier(String countryCodeSupplier) {
        this.countryCodeSupplier = countryCodeSupplier;
    }

    @JsonProperty("currencyCodeBase")
    public String getCurrencyCodeBase() {
        return currencyCodeBase;
    }

    @JsonProperty("currencyCodeBase")
    public void setCurrencyCodeBase(String currencyCodeBase) {
        this.currencyCodeBase = currencyCodeBase;
    }

    @JsonProperty("postalCodeShipTo")
    public String getPostalCodeShipTo() {
        return postalCodeShipTo;
    }

    @JsonProperty("postalCodeShipTo")
    public void setPostalCodeShipTo(String postalCodeShipTo) {
        this.postalCodeShipTo = postalCodeShipTo;
    }

    @JsonProperty("approvalRoutingCodePurchaseOrder")
    public String getApprovalRoutingCodePurchaseOrder() {
        return approvalRoutingCodePurchaseOrder;
    }

    @JsonProperty("approvalRoutingCodePurchaseOrder")
    public void setApprovalRoutingCodePurchaseOrder(String approvalRoutingCodePurchaseOrder) {
        this.approvalRoutingCodePurchaseOrder = approvalRoutingCodePurchaseOrder;
    }

    @JsonProperty("countyCodeSupplier")
    public String getCountyCodeSupplier() {
        return countyCodeSupplier;
    }

    @JsonProperty("countyCodeSupplier")
    public void setCountyCodeSupplier(String countyCodeSupplier) {
        this.countyCodeSupplier = countyCodeSupplier;
    }

    @JsonProperty("entityNameShipTo")
    public String getEntityNameShipTo() {
        return entityNameShipTo;
    }

    @JsonProperty("entityNameShipTo")
    public void setEntityNameShipTo(String entityNameShipTo) {
        this.entityNameShipTo = entityNameShipTo;
    }

    @JsonProperty("addressLine1ShipTo")
    public String getAddressLine1ShipTo() {
        return addressLine1ShipTo;
    }

    @JsonProperty("addressLine1ShipTo")
    public void setAddressLine1ShipTo(String addressLine1ShipTo) {
        this.addressLine1ShipTo = addressLine1ShipTo;
    }

    @JsonProperty("currencyMode")
    public String getCurrencyMode() {
        return currencyMode;
    }

    @JsonProperty("currencyMode")
    public void setCurrencyMode(String currencyMode) {
        this.currencyMode = currencyMode;
    }

    @JsonProperty("cityShipTo")
    public String getCityShipTo() {
        return cityShipTo;
    }

    @JsonProperty("cityShipTo")
    public void setCityShipTo(String cityShipTo) {
        this.cityShipTo = cityShipTo;
    }

    @JsonProperty("stateCodeShipTo")
    public String getStateCodeShipTo() {
        return stateCodeShipTo;
    }

    @JsonProperty("stateCodeShipTo")
    public void setStateCodeShipTo(String stateCodeShipTo) {
        this.stateCodeShipTo = stateCodeShipTo;
    }

    @JsonProperty("shipTo")
    public ShipTo getShipTo() {
        return shipTo;
    }

    @JsonProperty("shipTo")
    public void setShipTo(ShipTo shipTo) {
        this.shipTo = shipTo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PurchaseOrdersForApproverResult.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("citySupplier");
        sb.append('=');
        sb.append(((this.citySupplier == null)?"<null>":this.citySupplier));
        sb.append(',');
        sb.append("addressLine2Supplier");
        sb.append('=');
        sb.append(((this.addressLine2Supplier == null)?"<null>":this.addressLine2Supplier));
        sb.append(',');
        sb.append("stateCodeSupplier");
        sb.append('=');
        sb.append(((this.stateCodeSupplier == null)?"<null>":this.stateCodeSupplier));
        sb.append(',');
        sb.append("addressLine1Supplier");
        sb.append('=');
        sb.append(((this.addressLine1Supplier == null)?"<null>":this.addressLine1Supplier));
        sb.append(',');
        sb.append("countyCodeShipTo");
        sb.append('=');
        sb.append(((this.countyCodeShipTo == null)?"<null>":this.countyCodeShipTo));
        sb.append(',');
        sb.append("approvalRoutingCodeHeldOrders");
        sb.append('=');
        sb.append(((this.approvalRoutingCodeHeldOrders == null)?"<null>":this.approvalRoutingCodeHeldOrders));
        sb.append(',');
        sb.append("addressLine4Supplier");
        sb.append('=');
        sb.append(((this.addressLine4Supplier == null)?"<null>":this.addressLine4Supplier));
        sb.append(',');
        sb.append("documentCompanyKeyOrderNo");
        sb.append('=');
        sb.append(((this.documentCompanyKeyOrderNo == null)?"<null>":this.documentCompanyKeyOrderNo));
        sb.append(',');
        sb.append("dateRequested");
        sb.append('=');
        sb.append(((this.dateRequested == null)?"<null>":this.dateRequested));
        sb.append(',');
        sb.append("documentSuffix");
        sb.append('=');
        sb.append(((this.documentSuffix == null)?"<null>":this.documentSuffix));
        sb.append(',');
        sb.append("originator");
        sb.append('=');
        sb.append(((this.originator == null)?"<null>":this.originator));
        sb.append(',');
        sb.append("addressLine3ShipTo");
        sb.append('=');
        sb.append(((this.addressLine3ShipTo == null)?"<null>":this.addressLine3ShipTo));
        sb.append(',');
        sb.append("buyerNumber");
        sb.append('=');
        sb.append(((this.buyerNumber == null)?"<null>":this.buyerNumber));
        sb.append(',');
        sb.append("amountGross");
        sb.append('=');
        sb.append(((this.amountGross == null)?"<null>":this.amountGross));
        sb.append(',');
        sb.append("addressLine3Supplier");
        sb.append('=');
        sb.append(((this.addressLine3Supplier == null)?"<null>":this.addressLine3Supplier));
        sb.append(',');
        sb.append("holdCodePurchaseOrder");
        sb.append('=');
        sb.append(((this.holdCodePurchaseOrder == null)?"<null>":this.holdCodePurchaseOrder));
        sb.append(',');
        sb.append("countryCodeShipTo");
        sb.append('=');
        sb.append(((this.countryCodeShipTo == null)?"<null>":this.countryCodeShipTo));
        sb.append(',');
        sb.append("postalCodeSupplier");
        sb.append('=');
        sb.append(((this.postalCodeSupplier == null)?"<null>":this.postalCodeSupplier));
        sb.append(',');
        sb.append("currencyCodeForeignDecimals");
        sb.append('=');
        sb.append(((this.currencyCodeForeignDecimals == null)?"<null>":this.currencyCodeForeignDecimals));
        sb.append(',');
        sb.append("currencyCodeForeign");
        sb.append('=');
        sb.append(((this.currencyCodeForeign == null)?"<null>":this.currencyCodeForeign));
        sb.append(',');
        sb.append("supplier");
        sb.append('=');
        sb.append(((this.supplier == null)?"<null>":this.supplier));
        sb.append(',');
        sb.append("holdCodeHeldOrders");
        sb.append('=');
        sb.append(((this.holdCodeHeldOrders == null)?"<null>":this.holdCodeHeldOrders));
        sb.append(',');
        sb.append("businessUnitCode");
        sb.append('=');
        sb.append(((this.businessUnitCode == null)?"<null>":this.businessUnitCode));
        sb.append(',');
        sb.append("amountForeign");
        sb.append('=');
        sb.append(((this.amountForeign == null)?"<null>":this.amountForeign));
        sb.append(',');
        sb.append("approver");
        sb.append('=');
        sb.append(((this.approver == null)?"<null>":this.approver));
        sb.append(',');
        sb.append("documentOrderTypeCode");
        sb.append('=');
        sb.append(((this.documentOrderTypeCode == null)?"<null>":this.documentOrderTypeCode));
        sb.append(',');
        sb.append("addressLine2ShipTo");
        sb.append('=');
        sb.append(((this.addressLine2ShipTo == null)?"<null>":this.addressLine2ShipTo));
        sb.append(',');
        sb.append("currencyCodeBaseDecimals");
        sb.append('=');
        sb.append(((this.currencyCodeBaseDecimals == null)?"<null>":this.currencyCodeBaseDecimals));
        sb.append(',');
        sb.append("entityNameSupplier");
        sb.append('=');
        sb.append(((this.entityNameSupplier == null)?"<null>":this.entityNameSupplier));
        sb.append(',');
        sb.append("documentOrderInvoiceNumber");
        sb.append('=');
        sb.append(((this.documentOrderInvoiceNumber == null)?"<null>":this.documentOrderInvoiceNumber));
        sb.append(',');
        sb.append("statusApproval");
        sb.append('=');
        sb.append(((this.statusApproval == null)?"<null>":this.statusApproval));
        sb.append(',');
        sb.append("descriptionOrder");
        sb.append('=');
        sb.append(((this.descriptionOrder == null)?"<null>":this.descriptionOrder));
        sb.append(',');
        sb.append("addressLine4ShipTo");
        sb.append('=');
        sb.append(((this.addressLine4ShipTo == null)?"<null>":this.addressLine4ShipTo));
        sb.append(',');
        sb.append("entityNameApprover");
        sb.append('=');
        sb.append(((this.entityNameApprover == null)?"<null>":this.entityNameApprover));
        sb.append(',');
        sb.append("entityNameOriginator");
        sb.append('=');
        sb.append(((this.entityNameOriginator == null)?"<null>":this.entityNameOriginator));
        sb.append(',');
        sb.append("dateTransaction");
        sb.append('=');
        sb.append(((this.dateTransaction == null)?"<null>":this.dateTransaction));
        sb.append(',');
        sb.append("countryCodeSupplier");
        sb.append('=');
        sb.append(((this.countryCodeSupplier == null)?"<null>":this.countryCodeSupplier));
        sb.append(',');
        sb.append("currencyCodeBase");
        sb.append('=');
        sb.append(((this.currencyCodeBase == null)?"<null>":this.currencyCodeBase));
        sb.append(',');
        sb.append("postalCodeShipTo");
        sb.append('=');
        sb.append(((this.postalCodeShipTo == null)?"<null>":this.postalCodeShipTo));
        sb.append(',');
        sb.append("approvalRoutingCodePurchaseOrder");
        sb.append('=');
        sb.append(((this.approvalRoutingCodePurchaseOrder == null)?"<null>":this.approvalRoutingCodePurchaseOrder));
        sb.append(',');
        sb.append("countyCodeSupplier");
        sb.append('=');
        sb.append(((this.countyCodeSupplier == null)?"<null>":this.countyCodeSupplier));
        sb.append(',');
        sb.append("entityNameShipTo");
        sb.append('=');
        sb.append(((this.entityNameShipTo == null)?"<null>":this.entityNameShipTo));
        sb.append(',');
        sb.append("addressLine1ShipTo");
        sb.append('=');
        sb.append(((this.addressLine1ShipTo == null)?"<null>":this.addressLine1ShipTo));
        sb.append(',');
        sb.append("currencyMode");
        sb.append('=');
        sb.append(((this.currencyMode == null)?"<null>":this.currencyMode));
        sb.append(',');
        sb.append("cityShipTo");
        sb.append('=');
        sb.append(((this.cityShipTo == null)?"<null>":this.cityShipTo));
        sb.append(',');
        sb.append("stateCodeShipTo");
        sb.append('=');
        sb.append(((this.stateCodeShipTo == null)?"<null>":this.stateCodeShipTo));
        sb.append(',');
        sb.append("shipTo");
        sb.append('=');
        sb.append(((this.shipTo == null)?"<null>":this.shipTo));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.citySupplier == null)? 0 :this.citySupplier.hashCode()));
        result = ((result* 31)+((this.addressLine2Supplier == null)? 0 :this.addressLine2Supplier.hashCode()));
        result = ((result* 31)+((this.stateCodeSupplier == null)? 0 :this.stateCodeSupplier.hashCode()));
        result = ((result* 31)+((this.addressLine1Supplier == null)? 0 :this.addressLine1Supplier.hashCode()));
        result = ((result* 31)+((this.countyCodeShipTo == null)? 0 :this.countyCodeShipTo.hashCode()));
        result = ((result* 31)+((this.approvalRoutingCodeHeldOrders == null)? 0 :this.approvalRoutingCodeHeldOrders.hashCode()));
        result = ((result* 31)+((this.addressLine4Supplier == null)? 0 :this.addressLine4Supplier.hashCode()));
        result = ((result* 31)+((this.documentCompanyKeyOrderNo == null)? 0 :this.documentCompanyKeyOrderNo.hashCode()));
        result = ((result* 31)+((this.dateRequested == null)? 0 :this.dateRequested.hashCode()));
        result = ((result* 31)+((this.documentSuffix == null)? 0 :this.documentSuffix.hashCode()));
        result = ((result* 31)+((this.originator == null)? 0 :this.originator.hashCode()));
        result = ((result* 31)+((this.addressLine3ShipTo == null)? 0 :this.addressLine3ShipTo.hashCode()));
        result = ((result* 31)+((this.buyerNumber == null)? 0 :this.buyerNumber.hashCode()));
        result = ((result* 31)+((this.amountGross == null)? 0 :this.amountGross.hashCode()));
        result = ((result* 31)+((this.addressLine3Supplier == null)? 0 :this.addressLine3Supplier.hashCode()));
        result = ((result* 31)+((this.holdCodePurchaseOrder == null)? 0 :this.holdCodePurchaseOrder.hashCode()));
        result = ((result* 31)+((this.countryCodeShipTo == null)? 0 :this.countryCodeShipTo.hashCode()));
        result = ((result* 31)+((this.postalCodeSupplier == null)? 0 :this.postalCodeSupplier.hashCode()));
        result = ((result* 31)+((this.currencyCodeForeignDecimals == null)? 0 :this.currencyCodeForeignDecimals.hashCode()));
        result = ((result* 31)+((this.currencyCodeForeign == null)? 0 :this.currencyCodeForeign.hashCode()));
        result = ((result* 31)+((this.supplier == null)? 0 :this.supplier.hashCode()));
        result = ((result* 31)+((this.holdCodeHeldOrders == null)? 0 :this.holdCodeHeldOrders.hashCode()));
        result = ((result* 31)+((this.businessUnitCode == null)? 0 :this.businessUnitCode.hashCode()));
        result = ((result* 31)+((this.amountForeign == null)? 0 :this.amountForeign.hashCode()));
        result = ((result* 31)+((this.approver == null)? 0 :this.approver.hashCode()));
        result = ((result* 31)+((this.documentOrderTypeCode == null)? 0 :this.documentOrderTypeCode.hashCode()));
        result = ((result* 31)+((this.addressLine2ShipTo == null)? 0 :this.addressLine2ShipTo.hashCode()));
        result = ((result* 31)+((this.currencyCodeBaseDecimals == null)? 0 :this.currencyCodeBaseDecimals.hashCode()));
        result = ((result* 31)+((this.entityNameSupplier == null)? 0 :this.entityNameSupplier.hashCode()));
        result = ((result* 31)+((this.documentOrderInvoiceNumber == null)? 0 :this.documentOrderInvoiceNumber.hashCode()));
        result = ((result* 31)+((this.statusApproval == null)? 0 :this.statusApproval.hashCode()));
        result = ((result* 31)+((this.descriptionOrder == null)? 0 :this.descriptionOrder.hashCode()));
        result = ((result* 31)+((this.addressLine4ShipTo == null)? 0 :this.addressLine4ShipTo.hashCode()));
        result = ((result* 31)+((this.entityNameApprover == null)? 0 :this.entityNameApprover.hashCode()));
        result = ((result* 31)+((this.entityNameOriginator == null)? 0 :this.entityNameOriginator.hashCode()));
        result = ((result* 31)+((this.dateTransaction == null)? 0 :this.dateTransaction.hashCode()));
        result = ((result* 31)+((this.countryCodeSupplier == null)? 0 :this.countryCodeSupplier.hashCode()));
        result = ((result* 31)+((this.currencyCodeBase == null)? 0 :this.currencyCodeBase.hashCode()));
        result = ((result* 31)+((this.postalCodeShipTo == null)? 0 :this.postalCodeShipTo.hashCode()));
        result = ((result* 31)+((this.approvalRoutingCodePurchaseOrder == null)? 0 :this.approvalRoutingCodePurchaseOrder.hashCode()));
        result = ((result* 31)+((this.countyCodeSupplier == null)? 0 :this.countyCodeSupplier.hashCode()));
        result = ((result* 31)+((this.entityNameShipTo == null)? 0 :this.entityNameShipTo.hashCode()));
        result = ((result* 31)+((this.addressLine1ShipTo == null)? 0 :this.addressLine1ShipTo.hashCode()));
        result = ((result* 31)+((this.currencyMode == null)? 0 :this.currencyMode.hashCode()));
        result = ((result* 31)+((this.cityShipTo == null)? 0 :this.cityShipTo.hashCode()));
        result = ((result* 31)+((this.stateCodeShipTo == null)? 0 :this.stateCodeShipTo.hashCode()));
        result = ((result* 31)+((this.shipTo == null)? 0 :this.shipTo.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PurchaseOrdersForApproverResult) == false) {
            return false;
        }
        PurchaseOrdersForApproverResult rhs = ((PurchaseOrdersForApproverResult) other);
        return ((((((((((((((((((((((((((((((((((((((((((((((((this.citySupplier == rhs.citySupplier)||((this.citySupplier!= null)&&this.citySupplier.equals(rhs.citySupplier)))&&((this.addressLine2Supplier == rhs.addressLine2Supplier)||((this.addressLine2Supplier!= null)&&this.addressLine2Supplier.equals(rhs.addressLine2Supplier))))&&((this.stateCodeSupplier == rhs.stateCodeSupplier)||((this.stateCodeSupplier!= null)&&this.stateCodeSupplier.equals(rhs.stateCodeSupplier))))&&((this.addressLine1Supplier == rhs.addressLine1Supplier)||((this.addressLine1Supplier!= null)&&this.addressLine1Supplier.equals(rhs.addressLine1Supplier))))&&((this.countyCodeShipTo == rhs.countyCodeShipTo)||((this.countyCodeShipTo!= null)&&this.countyCodeShipTo.equals(rhs.countyCodeShipTo))))&&((this.approvalRoutingCodeHeldOrders == rhs.approvalRoutingCodeHeldOrders)||((this.approvalRoutingCodeHeldOrders!= null)&&this.approvalRoutingCodeHeldOrders.equals(rhs.approvalRoutingCodeHeldOrders))))&&((this.addressLine4Supplier == rhs.addressLine4Supplier)||((this.addressLine4Supplier!= null)&&this.addressLine4Supplier.equals(rhs.addressLine4Supplier))))&&((this.documentCompanyKeyOrderNo == rhs.documentCompanyKeyOrderNo)||((this.documentCompanyKeyOrderNo!= null)&&this.documentCompanyKeyOrderNo.equals(rhs.documentCompanyKeyOrderNo))))&&((this.dateRequested == rhs.dateRequested)||((this.dateRequested!= null)&&this.dateRequested.equals(rhs.dateRequested))))&&((this.documentSuffix == rhs.documentSuffix)||((this.documentSuffix!= null)&&this.documentSuffix.equals(rhs.documentSuffix))))&&((this.originator == rhs.originator)||((this.originator!= null)&&this.originator.equals(rhs.originator))))&&((this.addressLine3ShipTo == rhs.addressLine3ShipTo)||((this.addressLine3ShipTo!= null)&&this.addressLine3ShipTo.equals(rhs.addressLine3ShipTo))))&&((this.buyerNumber == rhs.buyerNumber)||((this.buyerNumber!= null)&&this.buyerNumber.equals(rhs.buyerNumber))))&&((this.amountGross == rhs.amountGross)||((this.amountGross!= null)&&this.amountGross.equals(rhs.amountGross))))&&((this.addressLine3Supplier == rhs.addressLine3Supplier)||((this.addressLine3Supplier!= null)&&this.addressLine3Supplier.equals(rhs.addressLine3Supplier))))&&((this.holdCodePurchaseOrder == rhs.holdCodePurchaseOrder)||((this.holdCodePurchaseOrder!= null)&&this.holdCodePurchaseOrder.equals(rhs.holdCodePurchaseOrder))))&&((this.countryCodeShipTo == rhs.countryCodeShipTo)||((this.countryCodeShipTo!= null)&&this.countryCodeShipTo.equals(rhs.countryCodeShipTo))))&&((this.postalCodeSupplier == rhs.postalCodeSupplier)||((this.postalCodeSupplier!= null)&&this.postalCodeSupplier.equals(rhs.postalCodeSupplier))))&&((this.currencyCodeForeignDecimals == rhs.currencyCodeForeignDecimals)||((this.currencyCodeForeignDecimals!= null)&&this.currencyCodeForeignDecimals.equals(rhs.currencyCodeForeignDecimals))))&&((this.currencyCodeForeign == rhs.currencyCodeForeign)||((this.currencyCodeForeign!= null)&&this.currencyCodeForeign.equals(rhs.currencyCodeForeign))))&&((this.supplier == rhs.supplier)||((this.supplier!= null)&&this.supplier.equals(rhs.supplier))))&&((this.holdCodeHeldOrders == rhs.holdCodeHeldOrders)||((this.holdCodeHeldOrders!= null)&&this.holdCodeHeldOrders.equals(rhs.holdCodeHeldOrders))))&&((this.businessUnitCode == rhs.businessUnitCode)||((this.businessUnitCode!= null)&&this.businessUnitCode.equals(rhs.businessUnitCode))))&&((this.amountForeign == rhs.amountForeign)||((this.amountForeign!= null)&&this.amountForeign.equals(rhs.amountForeign))))&&((this.approver == rhs.approver)||((this.approver!= null)&&this.approver.equals(rhs.approver))))&&((this.documentOrderTypeCode == rhs.documentOrderTypeCode)||((this.documentOrderTypeCode!= null)&&this.documentOrderTypeCode.equals(rhs.documentOrderTypeCode))))&&((this.addressLine2ShipTo == rhs.addressLine2ShipTo)||((this.addressLine2ShipTo!= null)&&this.addressLine2ShipTo.equals(rhs.addressLine2ShipTo))))&&((this.currencyCodeBaseDecimals == rhs.currencyCodeBaseDecimals)||((this.currencyCodeBaseDecimals!= null)&&this.currencyCodeBaseDecimals.equals(rhs.currencyCodeBaseDecimals))))&&((this.entityNameSupplier == rhs.entityNameSupplier)||((this.entityNameSupplier!= null)&&this.entityNameSupplier.equals(rhs.entityNameSupplier))))&&((this.documentOrderInvoiceNumber == rhs.documentOrderInvoiceNumber)||((this.documentOrderInvoiceNumber!= null)&&this.documentOrderInvoiceNumber.equals(rhs.documentOrderInvoiceNumber))))&&((this.statusApproval == rhs.statusApproval)||((this.statusApproval!= null)&&this.statusApproval.equals(rhs.statusApproval))))&&((this.descriptionOrder == rhs.descriptionOrder)||((this.descriptionOrder!= null)&&this.descriptionOrder.equals(rhs.descriptionOrder))))&&((this.addressLine4ShipTo == rhs.addressLine4ShipTo)||((this.addressLine4ShipTo!= null)&&this.addressLine4ShipTo.equals(rhs.addressLine4ShipTo))))&&((this.entityNameApprover == rhs.entityNameApprover)||((this.entityNameApprover!= null)&&this.entityNameApprover.equals(rhs.entityNameApprover))))&&((this.entityNameOriginator == rhs.entityNameOriginator)||((this.entityNameOriginator!= null)&&this.entityNameOriginator.equals(rhs.entityNameOriginator))))&&((this.dateTransaction == rhs.dateTransaction)||((this.dateTransaction!= null)&&this.dateTransaction.equals(rhs.dateTransaction))))&&((this.countryCodeSupplier == rhs.countryCodeSupplier)||((this.countryCodeSupplier!= null)&&this.countryCodeSupplier.equals(rhs.countryCodeSupplier))))&&((this.currencyCodeBase == rhs.currencyCodeBase)||((this.currencyCodeBase!= null)&&this.currencyCodeBase.equals(rhs.currencyCodeBase))))&&((this.postalCodeShipTo == rhs.postalCodeShipTo)||((this.postalCodeShipTo!= null)&&this.postalCodeShipTo.equals(rhs.postalCodeShipTo))))&&((this.approvalRoutingCodePurchaseOrder == rhs.approvalRoutingCodePurchaseOrder)||((this.approvalRoutingCodePurchaseOrder!= null)&&this.approvalRoutingCodePurchaseOrder.equals(rhs.approvalRoutingCodePurchaseOrder))))&&((this.countyCodeSupplier == rhs.countyCodeSupplier)||((this.countyCodeSupplier!= null)&&this.countyCodeSupplier.equals(rhs.countyCodeSupplier))))&&((this.entityNameShipTo == rhs.entityNameShipTo)||((this.entityNameShipTo!= null)&&this.entityNameShipTo.equals(rhs.entityNameShipTo))))&&((this.addressLine1ShipTo == rhs.addressLine1ShipTo)||((this.addressLine1ShipTo!= null)&&this.addressLine1ShipTo.equals(rhs.addressLine1ShipTo))))&&((this.currencyMode == rhs.currencyMode)||((this.currencyMode!= null)&&this.currencyMode.equals(rhs.currencyMode))))&&((this.cityShipTo == rhs.cityShipTo)||((this.cityShipTo!= null)&&this.cityShipTo.equals(rhs.cityShipTo))))&&((this.stateCodeShipTo == rhs.stateCodeShipTo)||((this.stateCodeShipTo!= null)&&this.stateCodeShipTo.equals(rhs.stateCodeShipTo))))&&((this.shipTo == rhs.shipTo)||((this.shipTo!= null)&&this.shipTo.equals(rhs.shipTo))));
    }

}
