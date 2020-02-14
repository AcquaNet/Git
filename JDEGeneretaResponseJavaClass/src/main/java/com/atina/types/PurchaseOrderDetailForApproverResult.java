
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "companyKeyOrderNo",
    "documentLineNumber",
    "identifierShortItem",
    "dateRequested",
    "identifier2ndItem",
    "descriptionLine1",
    "subledgerTypeCode",
    "companyKeyRelated",
    "poSoLineNoRelated",
    "documentSuffix",
    "lineTypeCode",
    "orderTypeCodeRelated",
    "poSoNumberRelated",
    "acctNumber",
    "amountForeignExtendPrice",
    "currencyCodeForeign",
    "supplier",
    "unitOfMeasurePurchasing",
    "businessUnitCode",
    "statusLastCode",
    "unitsTransactionQty",
    "documentOrderTypeCode",
    "statusCodeNextCode",
    "documentOrderInvoiceNumber",
    "foreignPurchasingPrice",
    "dateTransaction",
    "amountExtendedPrice",
    "assetIDNumber",
    "purchasingUnitPrice",
    "discountFactor",
    "subledger",
    "unitOfMeasureCode",
    "shipTo"
})
public class PurchaseOrderDetailForApproverResult {

    @JsonProperty("companyKeyOrderNo")
    private String companyKeyOrderNo;
    @JsonProperty("documentLineNumber")
    private Double documentLineNumber;
    @JsonProperty("identifierShortItem")
    private Integer identifierShortItem;
    @JsonProperty("dateRequested")
    private String dateRequested;
    @JsonProperty("identifier2ndItem")
    private String identifier2ndItem;
    @JsonProperty("descriptionLine1")
    private String descriptionLine1;
    @JsonProperty("subledgerTypeCode")
    private String subledgerTypeCode;
    @JsonProperty("companyKeyRelated")
    private String companyKeyRelated;
    @JsonProperty("poSoLineNoRelated")
    private Double poSoLineNoRelated;
    @JsonProperty("documentSuffix")
    private String documentSuffix;
    @JsonProperty("lineTypeCode")
    private String lineTypeCode;
    @JsonProperty("orderTypeCodeRelated")
    private String orderTypeCodeRelated;
    @JsonProperty("poSoNumberRelated")
    private String poSoNumberRelated;
    @JsonProperty("acctNumber")
    private String acctNumber;
    @JsonProperty("amountForeignExtendPrice")
    private Double amountForeignExtendPrice;
    @JsonProperty("currencyCodeForeign")
    private String currencyCodeForeign;
    @JsonProperty("supplier")
    private Supplier supplier;
    @JsonProperty("unitOfMeasurePurchasing")
    private String unitOfMeasurePurchasing;
    @JsonProperty("businessUnitCode")
    private String businessUnitCode;
    @JsonProperty("statusLastCode")
    private String statusLastCode;
    @JsonProperty("unitsTransactionQty")
    private Double unitsTransactionQty;
    @JsonProperty("documentOrderTypeCode")
    private String documentOrderTypeCode;
    @JsonProperty("statusCodeNextCode")
    private String statusCodeNextCode;
    @JsonProperty("documentOrderInvoiceNumber")
    private Integer documentOrderInvoiceNumber;
    @JsonProperty("foreignPurchasingPrice")
    private Double foreignPurchasingPrice;
    @JsonProperty("dateTransaction")
    private String dateTransaction;
    @JsonProperty("amountExtendedPrice")
    private Double amountExtendedPrice;
    @JsonProperty("assetIDNumber")
    private String assetIDNumber;
    @JsonProperty("purchasingUnitPrice")
    private Double purchasingUnitPrice;
    @JsonProperty("discountFactor")
    private Double discountFactor;
    @JsonProperty("subledger")
    private String subledger;
    @JsonProperty("unitOfMeasureCode")
    private String unitOfMeasureCode;
    @JsonProperty("shipTo")
    private ShipTo shipTo;

    @JsonProperty("companyKeyOrderNo")
    public String getCompanyKeyOrderNo() {
        return companyKeyOrderNo;
    }

    @JsonProperty("companyKeyOrderNo")
    public void setCompanyKeyOrderNo(String companyKeyOrderNo) {
        this.companyKeyOrderNo = companyKeyOrderNo;
    }

    @JsonProperty("documentLineNumber")
    public Double getDocumentLineNumber() {
        return documentLineNumber;
    }

    @JsonProperty("documentLineNumber")
    public void setDocumentLineNumber(Double documentLineNumber) {
        this.documentLineNumber = documentLineNumber;
    }

    @JsonProperty("identifierShortItem")
    public Integer getIdentifierShortItem() {
        return identifierShortItem;
    }

    @JsonProperty("identifierShortItem")
    public void setIdentifierShortItem(Integer identifierShortItem) {
        this.identifierShortItem = identifierShortItem;
    }

    @JsonProperty("dateRequested")
    public String getDateRequested() {
        return dateRequested;
    }

    @JsonProperty("dateRequested")
    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    @JsonProperty("identifier2ndItem")
    public String getIdentifier2ndItem() {
        return identifier2ndItem;
    }

    @JsonProperty("identifier2ndItem")
    public void setIdentifier2ndItem(String identifier2ndItem) {
        this.identifier2ndItem = identifier2ndItem;
    }

    @JsonProperty("descriptionLine1")
    public String getDescriptionLine1() {
        return descriptionLine1;
    }

    @JsonProperty("descriptionLine1")
    public void setDescriptionLine1(String descriptionLine1) {
        this.descriptionLine1 = descriptionLine1;
    }

    @JsonProperty("subledgerTypeCode")
    public String getSubledgerTypeCode() {
        return subledgerTypeCode;
    }

    @JsonProperty("subledgerTypeCode")
    public void setSubledgerTypeCode(String subledgerTypeCode) {
        this.subledgerTypeCode = subledgerTypeCode;
    }

    @JsonProperty("companyKeyRelated")
    public String getCompanyKeyRelated() {
        return companyKeyRelated;
    }

    @JsonProperty("companyKeyRelated")
    public void setCompanyKeyRelated(String companyKeyRelated) {
        this.companyKeyRelated = companyKeyRelated;
    }

    @JsonProperty("poSoLineNoRelated")
    public Double getPoSoLineNoRelated() {
        return poSoLineNoRelated;
    }

    @JsonProperty("poSoLineNoRelated")
    public void setPoSoLineNoRelated(Double poSoLineNoRelated) {
        this.poSoLineNoRelated = poSoLineNoRelated;
    }

    @JsonProperty("documentSuffix")
    public String getDocumentSuffix() {
        return documentSuffix;
    }

    @JsonProperty("documentSuffix")
    public void setDocumentSuffix(String documentSuffix) {
        this.documentSuffix = documentSuffix;
    }

    @JsonProperty("lineTypeCode")
    public String getLineTypeCode() {
        return lineTypeCode;
    }

    @JsonProperty("lineTypeCode")
    public void setLineTypeCode(String lineTypeCode) {
        this.lineTypeCode = lineTypeCode;
    }

    @JsonProperty("orderTypeCodeRelated")
    public String getOrderTypeCodeRelated() {
        return orderTypeCodeRelated;
    }

    @JsonProperty("orderTypeCodeRelated")
    public void setOrderTypeCodeRelated(String orderTypeCodeRelated) {
        this.orderTypeCodeRelated = orderTypeCodeRelated;
    }

    @JsonProperty("poSoNumberRelated")
    public String getPoSoNumberRelated() {
        return poSoNumberRelated;
    }

    @JsonProperty("poSoNumberRelated")
    public void setPoSoNumberRelated(String poSoNumberRelated) {
        this.poSoNumberRelated = poSoNumberRelated;
    }

    @JsonProperty("acctNumber")
    public String getAcctNumber() {
        return acctNumber;
    }

    @JsonProperty("acctNumber")
    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }

    @JsonProperty("amountForeignExtendPrice")
    public Double getAmountForeignExtendPrice() {
        return amountForeignExtendPrice;
    }

    @JsonProperty("amountForeignExtendPrice")
    public void setAmountForeignExtendPrice(Double amountForeignExtendPrice) {
        this.amountForeignExtendPrice = amountForeignExtendPrice;
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

    @JsonProperty("unitOfMeasurePurchasing")
    public String getUnitOfMeasurePurchasing() {
        return unitOfMeasurePurchasing;
    }

    @JsonProperty("unitOfMeasurePurchasing")
    public void setUnitOfMeasurePurchasing(String unitOfMeasurePurchasing) {
        this.unitOfMeasurePurchasing = unitOfMeasurePurchasing;
    }

    @JsonProperty("businessUnitCode")
    public String getBusinessUnitCode() {
        return businessUnitCode;
    }

    @JsonProperty("businessUnitCode")
    public void setBusinessUnitCode(String businessUnitCode) {
        this.businessUnitCode = businessUnitCode;
    }

    @JsonProperty("statusLastCode")
    public String getStatusLastCode() {
        return statusLastCode;
    }

    @JsonProperty("statusLastCode")
    public void setStatusLastCode(String statusLastCode) {
        this.statusLastCode = statusLastCode;
    }

    @JsonProperty("unitsTransactionQty")
    public Double getUnitsTransactionQty() {
        return unitsTransactionQty;
    }

    @JsonProperty("unitsTransactionQty")
    public void setUnitsTransactionQty(Double unitsTransactionQty) {
        this.unitsTransactionQty = unitsTransactionQty;
    }

    @JsonProperty("documentOrderTypeCode")
    public String getDocumentOrderTypeCode() {
        return documentOrderTypeCode;
    }

    @JsonProperty("documentOrderTypeCode")
    public void setDocumentOrderTypeCode(String documentOrderTypeCode) {
        this.documentOrderTypeCode = documentOrderTypeCode;
    }

    @JsonProperty("statusCodeNextCode")
    public String getStatusCodeNextCode() {
        return statusCodeNextCode;
    }

    @JsonProperty("statusCodeNextCode")
    public void setStatusCodeNextCode(String statusCodeNextCode) {
        this.statusCodeNextCode = statusCodeNextCode;
    }

    @JsonProperty("documentOrderInvoiceNumber")
    public Integer getDocumentOrderInvoiceNumber() {
        return documentOrderInvoiceNumber;
    }

    @JsonProperty("documentOrderInvoiceNumber")
    public void setDocumentOrderInvoiceNumber(Integer documentOrderInvoiceNumber) {
        this.documentOrderInvoiceNumber = documentOrderInvoiceNumber;
    }

    @JsonProperty("foreignPurchasingPrice")
    public Double getForeignPurchasingPrice() {
        return foreignPurchasingPrice;
    }

    @JsonProperty("foreignPurchasingPrice")
    public void setForeignPurchasingPrice(Double foreignPurchasingPrice) {
        this.foreignPurchasingPrice = foreignPurchasingPrice;
    }

    @JsonProperty("dateTransaction")
    public String getDateTransaction() {
        return dateTransaction;
    }

    @JsonProperty("dateTransaction")
    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    @JsonProperty("amountExtendedPrice")
    public Double getAmountExtendedPrice() {
        return amountExtendedPrice;
    }

    @JsonProperty("amountExtendedPrice")
    public void setAmountExtendedPrice(Double amountExtendedPrice) {
        this.amountExtendedPrice = amountExtendedPrice;
    }

    @JsonProperty("assetIDNumber")
    public String getAssetIDNumber() {
        return assetIDNumber;
    }

    @JsonProperty("assetIDNumber")
    public void setAssetIDNumber(String assetIDNumber) {
        this.assetIDNumber = assetIDNumber;
    }

    @JsonProperty("purchasingUnitPrice")
    public Double getPurchasingUnitPrice() {
        return purchasingUnitPrice;
    }

    @JsonProperty("purchasingUnitPrice")
    public void setPurchasingUnitPrice(Double purchasingUnitPrice) {
        this.purchasingUnitPrice = purchasingUnitPrice;
    }

    @JsonProperty("discountFactor")
    public Double getDiscountFactor() {
        return discountFactor;
    }

    @JsonProperty("discountFactor")
    public void setDiscountFactor(Double discountFactor) {
        this.discountFactor = discountFactor;
    }

    @JsonProperty("subledger")
    public String getSubledger() {
        return subledger;
    }

    @JsonProperty("subledger")
    public void setSubledger(String subledger) {
        this.subledger = subledger;
    }

    @JsonProperty("unitOfMeasureCode")
    public String getUnitOfMeasureCode() {
        return unitOfMeasureCode;
    }

    @JsonProperty("unitOfMeasureCode")
    public void setUnitOfMeasureCode(String unitOfMeasureCode) {
        this.unitOfMeasureCode = unitOfMeasureCode;
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
        sb.append(PurchaseOrderDetailForApproverResult.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("companyKeyOrderNo");
        sb.append('=');
        sb.append(((this.companyKeyOrderNo == null)?"<null>":this.companyKeyOrderNo));
        sb.append(',');
        sb.append("documentLineNumber");
        sb.append('=');
        sb.append(((this.documentLineNumber == null)?"<null>":this.documentLineNumber));
        sb.append(',');
        sb.append("identifierShortItem");
        sb.append('=');
        sb.append(((this.identifierShortItem == null)?"<null>":this.identifierShortItem));
        sb.append(',');
        sb.append("dateRequested");
        sb.append('=');
        sb.append(((this.dateRequested == null)?"<null>":this.dateRequested));
        sb.append(',');
        sb.append("identifier2ndItem");
        sb.append('=');
        sb.append(((this.identifier2ndItem == null)?"<null>":this.identifier2ndItem));
        sb.append(',');
        sb.append("descriptionLine1");
        sb.append('=');
        sb.append(((this.descriptionLine1 == null)?"<null>":this.descriptionLine1));
        sb.append(',');
        sb.append("subledgerTypeCode");
        sb.append('=');
        sb.append(((this.subledgerTypeCode == null)?"<null>":this.subledgerTypeCode));
        sb.append(',');
        sb.append("companyKeyRelated");
        sb.append('=');
        sb.append(((this.companyKeyRelated == null)?"<null>":this.companyKeyRelated));
        sb.append(',');
        sb.append("poSoLineNoRelated");
        sb.append('=');
        sb.append(((this.poSoLineNoRelated == null)?"<null>":this.poSoLineNoRelated));
        sb.append(',');
        sb.append("documentSuffix");
        sb.append('=');
        sb.append(((this.documentSuffix == null)?"<null>":this.documentSuffix));
        sb.append(',');
        sb.append("lineTypeCode");
        sb.append('=');
        sb.append(((this.lineTypeCode == null)?"<null>":this.lineTypeCode));
        sb.append(',');
        sb.append("orderTypeCodeRelated");
        sb.append('=');
        sb.append(((this.orderTypeCodeRelated == null)?"<null>":this.orderTypeCodeRelated));
        sb.append(',');
        sb.append("poSoNumberRelated");
        sb.append('=');
        sb.append(((this.poSoNumberRelated == null)?"<null>":this.poSoNumberRelated));
        sb.append(',');
        sb.append("acctNumber");
        sb.append('=');
        sb.append(((this.acctNumber == null)?"<null>":this.acctNumber));
        sb.append(',');
        sb.append("amountForeignExtendPrice");
        sb.append('=');
        sb.append(((this.amountForeignExtendPrice == null)?"<null>":this.amountForeignExtendPrice));
        sb.append(',');
        sb.append("currencyCodeForeign");
        sb.append('=');
        sb.append(((this.currencyCodeForeign == null)?"<null>":this.currencyCodeForeign));
        sb.append(',');
        sb.append("supplier");
        sb.append('=');
        sb.append(((this.supplier == null)?"<null>":this.supplier));
        sb.append(',');
        sb.append("unitOfMeasurePurchasing");
        sb.append('=');
        sb.append(((this.unitOfMeasurePurchasing == null)?"<null>":this.unitOfMeasurePurchasing));
        sb.append(',');
        sb.append("businessUnitCode");
        sb.append('=');
        sb.append(((this.businessUnitCode == null)?"<null>":this.businessUnitCode));
        sb.append(',');
        sb.append("statusLastCode");
        sb.append('=');
        sb.append(((this.statusLastCode == null)?"<null>":this.statusLastCode));
        sb.append(',');
        sb.append("unitsTransactionQty");
        sb.append('=');
        sb.append(((this.unitsTransactionQty == null)?"<null>":this.unitsTransactionQty));
        sb.append(',');
        sb.append("documentOrderTypeCode");
        sb.append('=');
        sb.append(((this.documentOrderTypeCode == null)?"<null>":this.documentOrderTypeCode));
        sb.append(',');
        sb.append("statusCodeNextCode");
        sb.append('=');
        sb.append(((this.statusCodeNextCode == null)?"<null>":this.statusCodeNextCode));
        sb.append(',');
        sb.append("documentOrderInvoiceNumber");
        sb.append('=');
        sb.append(((this.documentOrderInvoiceNumber == null)?"<null>":this.documentOrderInvoiceNumber));
        sb.append(',');
        sb.append("foreignPurchasingPrice");
        sb.append('=');
        sb.append(((this.foreignPurchasingPrice == null)?"<null>":this.foreignPurchasingPrice));
        sb.append(',');
        sb.append("dateTransaction");
        sb.append('=');
        sb.append(((this.dateTransaction == null)?"<null>":this.dateTransaction));
        sb.append(',');
        sb.append("amountExtendedPrice");
        sb.append('=');
        sb.append(((this.amountExtendedPrice == null)?"<null>":this.amountExtendedPrice));
        sb.append(',');
        sb.append("assetIDNumber");
        sb.append('=');
        sb.append(((this.assetIDNumber == null)?"<null>":this.assetIDNumber));
        sb.append(',');
        sb.append("purchasingUnitPrice");
        sb.append('=');
        sb.append(((this.purchasingUnitPrice == null)?"<null>":this.purchasingUnitPrice));
        sb.append(',');
        sb.append("discountFactor");
        sb.append('=');
        sb.append(((this.discountFactor == null)?"<null>":this.discountFactor));
        sb.append(',');
        sb.append("subledger");
        sb.append('=');
        sb.append(((this.subledger == null)?"<null>":this.subledger));
        sb.append(',');
        sb.append("unitOfMeasureCode");
        sb.append('=');
        sb.append(((this.unitOfMeasureCode == null)?"<null>":this.unitOfMeasureCode));
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
        result = ((result* 31)+((this.companyKeyOrderNo == null)? 0 :this.companyKeyOrderNo.hashCode()));
        result = ((result* 31)+((this.documentLineNumber == null)? 0 :this.documentLineNumber.hashCode()));
        result = ((result* 31)+((this.identifierShortItem == null)? 0 :this.identifierShortItem.hashCode()));
        result = ((result* 31)+((this.dateRequested == null)? 0 :this.dateRequested.hashCode()));
        result = ((result* 31)+((this.identifier2ndItem == null)? 0 :this.identifier2ndItem.hashCode()));
        result = ((result* 31)+((this.descriptionLine1 == null)? 0 :this.descriptionLine1 .hashCode()));
        result = ((result* 31)+((this.subledgerTypeCode == null)? 0 :this.subledgerTypeCode.hashCode()));
        result = ((result* 31)+((this.companyKeyRelated == null)? 0 :this.companyKeyRelated.hashCode()));
        result = ((result* 31)+((this.poSoLineNoRelated == null)? 0 :this.poSoLineNoRelated.hashCode()));
        result = ((result* 31)+((this.documentSuffix == null)? 0 :this.documentSuffix.hashCode()));
        result = ((result* 31)+((this.lineTypeCode == null)? 0 :this.lineTypeCode.hashCode()));
        result = ((result* 31)+((this.orderTypeCodeRelated == null)? 0 :this.orderTypeCodeRelated.hashCode()));
        result = ((result* 31)+((this.poSoNumberRelated == null)? 0 :this.poSoNumberRelated.hashCode()));
        result = ((result* 31)+((this.acctNumber == null)? 0 :this.acctNumber.hashCode()));
        result = ((result* 31)+((this.amountForeignExtendPrice == null)? 0 :this.amountForeignExtendPrice.hashCode()));
        result = ((result* 31)+((this.currencyCodeForeign == null)? 0 :this.currencyCodeForeign.hashCode()));
        result = ((result* 31)+((this.supplier == null)? 0 :this.supplier.hashCode()));
        result = ((result* 31)+((this.unitOfMeasurePurchasing == null)? 0 :this.unitOfMeasurePurchasing.hashCode()));
        result = ((result* 31)+((this.businessUnitCode == null)? 0 :this.businessUnitCode.hashCode()));
        result = ((result* 31)+((this.statusLastCode == null)? 0 :this.statusLastCode.hashCode()));
        result = ((result* 31)+((this.unitsTransactionQty == null)? 0 :this.unitsTransactionQty.hashCode()));
        result = ((result* 31)+((this.documentOrderTypeCode == null)? 0 :this.documentOrderTypeCode.hashCode()));
        result = ((result* 31)+((this.statusCodeNextCode == null)? 0 :this.statusCodeNextCode.hashCode()));
        result = ((result* 31)+((this.documentOrderInvoiceNumber == null)? 0 :this.documentOrderInvoiceNumber.hashCode()));
        result = ((result* 31)+((this.foreignPurchasingPrice == null)? 0 :this.foreignPurchasingPrice.hashCode()));
        result = ((result* 31)+((this.dateTransaction == null)? 0 :this.dateTransaction.hashCode()));
        result = ((result* 31)+((this.amountExtendedPrice == null)? 0 :this.amountExtendedPrice.hashCode()));
        result = ((result* 31)+((this.assetIDNumber == null)? 0 :this.assetIDNumber.hashCode()));
        result = ((result* 31)+((this.purchasingUnitPrice == null)? 0 :this.purchasingUnitPrice.hashCode()));
        result = ((result* 31)+((this.discountFactor == null)? 0 :this.discountFactor.hashCode()));
        result = ((result* 31)+((this.subledger == null)? 0 :this.subledger.hashCode()));
        result = ((result* 31)+((this.unitOfMeasureCode == null)? 0 :this.unitOfMeasureCode.hashCode()));
        result = ((result* 31)+((this.shipTo == null)? 0 :this.shipTo.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PurchaseOrderDetailForApproverResult) == false) {
            return false;
        }
        PurchaseOrderDetailForApproverResult rhs = ((PurchaseOrderDetailForApproverResult) other);
        return ((((((((((((((((((((((((((((((((((this.companyKeyOrderNo == rhs.companyKeyOrderNo)||((this.companyKeyOrderNo!= null)&&this.companyKeyOrderNo.equals(rhs.companyKeyOrderNo)))&&((this.documentLineNumber == rhs.documentLineNumber)||((this.documentLineNumber!= null)&&this.documentLineNumber.equals(rhs.documentLineNumber))))&&((this.identifierShortItem == rhs.identifierShortItem)||((this.identifierShortItem!= null)&&this.identifierShortItem.equals(rhs.identifierShortItem))))&&((this.dateRequested == rhs.dateRequested)||((this.dateRequested!= null)&&this.dateRequested.equals(rhs.dateRequested))))&&((this.identifier2ndItem == rhs.identifier2ndItem)||((this.identifier2ndItem!= null)&&this.identifier2ndItem.equals(rhs.identifier2ndItem))))&&((this.descriptionLine1 == rhs.descriptionLine1)||((this.descriptionLine1 != null)&&this.descriptionLine1 .equals(rhs.descriptionLine1))))&&((this.subledgerTypeCode == rhs.subledgerTypeCode)||((this.subledgerTypeCode!= null)&&this.subledgerTypeCode.equals(rhs.subledgerTypeCode))))&&((this.companyKeyRelated == rhs.companyKeyRelated)||((this.companyKeyRelated!= null)&&this.companyKeyRelated.equals(rhs.companyKeyRelated))))&&((this.poSoLineNoRelated == rhs.poSoLineNoRelated)||((this.poSoLineNoRelated!= null)&&this.poSoLineNoRelated.equals(rhs.poSoLineNoRelated))))&&((this.documentSuffix == rhs.documentSuffix)||((this.documentSuffix!= null)&&this.documentSuffix.equals(rhs.documentSuffix))))&&((this.lineTypeCode == rhs.lineTypeCode)||((this.lineTypeCode!= null)&&this.lineTypeCode.equals(rhs.lineTypeCode))))&&((this.orderTypeCodeRelated == rhs.orderTypeCodeRelated)||((this.orderTypeCodeRelated!= null)&&this.orderTypeCodeRelated.equals(rhs.orderTypeCodeRelated))))&&((this.poSoNumberRelated == rhs.poSoNumberRelated)||((this.poSoNumberRelated!= null)&&this.poSoNumberRelated.equals(rhs.poSoNumberRelated))))&&((this.acctNumber == rhs.acctNumber)||((this.acctNumber!= null)&&this.acctNumber.equals(rhs.acctNumber))))&&((this.amountForeignExtendPrice == rhs.amountForeignExtendPrice)||((this.amountForeignExtendPrice!= null)&&this.amountForeignExtendPrice.equals(rhs.amountForeignExtendPrice))))&&((this.currencyCodeForeign == rhs.currencyCodeForeign)||((this.currencyCodeForeign!= null)&&this.currencyCodeForeign.equals(rhs.currencyCodeForeign))))&&((this.supplier == rhs.supplier)||((this.supplier!= null)&&this.supplier.equals(rhs.supplier))))&&((this.unitOfMeasurePurchasing == rhs.unitOfMeasurePurchasing)||((this.unitOfMeasurePurchasing!= null)&&this.unitOfMeasurePurchasing.equals(rhs.unitOfMeasurePurchasing))))&&((this.businessUnitCode == rhs.businessUnitCode)||((this.businessUnitCode!= null)&&this.businessUnitCode.equals(rhs.businessUnitCode))))&&((this.statusLastCode == rhs.statusLastCode)||((this.statusLastCode!= null)&&this.statusLastCode.equals(rhs.statusLastCode))))&&((this.unitsTransactionQty == rhs.unitsTransactionQty)||((this.unitsTransactionQty!= null)&&this.unitsTransactionQty.equals(rhs.unitsTransactionQty))))&&((this.documentOrderTypeCode == rhs.documentOrderTypeCode)||((this.documentOrderTypeCode!= null)&&this.documentOrderTypeCode.equals(rhs.documentOrderTypeCode))))&&((this.statusCodeNextCode == rhs.statusCodeNextCode)||((this.statusCodeNextCode!= null)&&this.statusCodeNextCode.equals(rhs.statusCodeNextCode))))&&((this.documentOrderInvoiceNumber == rhs.documentOrderInvoiceNumber)||((this.documentOrderInvoiceNumber!= null)&&this.documentOrderInvoiceNumber.equals(rhs.documentOrderInvoiceNumber))))&&((this.foreignPurchasingPrice == rhs.foreignPurchasingPrice)||((this.foreignPurchasingPrice!= null)&&this.foreignPurchasingPrice.equals(rhs.foreignPurchasingPrice))))&&((this.dateTransaction == rhs.dateTransaction)||((this.dateTransaction!= null)&&this.dateTransaction.equals(rhs.dateTransaction))))&&((this.amountExtendedPrice == rhs.amountExtendedPrice)||((this.amountExtendedPrice!= null)&&this.amountExtendedPrice.equals(rhs.amountExtendedPrice))))&&((this.assetIDNumber == rhs.assetIDNumber)||((this.assetIDNumber!= null)&&this.assetIDNumber.equals(rhs.assetIDNumber))))&&((this.purchasingUnitPrice == rhs.purchasingUnitPrice)||((this.purchasingUnitPrice!= null)&&this.purchasingUnitPrice.equals(rhs.purchasingUnitPrice))))&&((this.discountFactor == rhs.discountFactor)||((this.discountFactor!= null)&&this.discountFactor.equals(rhs.discountFactor))))&&((this.subledger == rhs.subledger)||((this.subledger!= null)&&this.subledger.equals(rhs.subledger))))&&((this.unitOfMeasureCode == rhs.unitOfMeasureCode)||((this.unitOfMeasureCode!= null)&&this.unitOfMeasureCode.equals(rhs.unitOfMeasureCode))))&&((this.shipTo == rhs.shipTo)||((this.shipTo!= null)&&this.shipTo.equals(rhs.shipTo))));
    }

}
