
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "companyKeyOrderNo",
    "documentOrderTypeCode",
    "statusCodeNext",
    "documentOrderInvoiceNumber",
    "documentSuffix"
})
public class JdeAtinaGetPurchaseOrderDetailForApproverRequest {

    @JsonProperty("companyKeyOrderNo")
    private String companyKeyOrderNo;
    @JsonProperty("documentOrderTypeCode")
    private String documentOrderTypeCode;
    @JsonProperty("statusCodeNext")
    private String statusCodeNext;
    @JsonProperty("documentOrderInvoiceNumber")
    private Integer documentOrderInvoiceNumber;
    @JsonProperty("documentSuffix")
    private String documentSuffix;

    @JsonProperty("companyKeyOrderNo")
    public String getCompanyKeyOrderNo() {
        return companyKeyOrderNo;
    }

    @JsonProperty("companyKeyOrderNo")
    public void setCompanyKeyOrderNo(String companyKeyOrderNo) {
        this.companyKeyOrderNo = companyKeyOrderNo;
    }

    @JsonProperty("documentOrderTypeCode")
    public String getDocumentOrderTypeCode() {
        return documentOrderTypeCode;
    }

    @JsonProperty("documentOrderTypeCode")
    public void setDocumentOrderTypeCode(String documentOrderTypeCode) {
        this.documentOrderTypeCode = documentOrderTypeCode;
    }

    @JsonProperty("statusCodeNext")
    public String getStatusCodeNext() {
        return statusCodeNext;
    }

    @JsonProperty("statusCodeNext")
    public void setStatusCodeNext(String statusCodeNext) {
        this.statusCodeNext = statusCodeNext;
    }

    @JsonProperty("documentOrderInvoiceNumber")
    public Integer getDocumentOrderInvoiceNumber() {
        return documentOrderInvoiceNumber;
    }

    @JsonProperty("documentOrderInvoiceNumber")
    public void setDocumentOrderInvoiceNumber(Integer documentOrderInvoiceNumber) {
        this.documentOrderInvoiceNumber = documentOrderInvoiceNumber;
    }

    @JsonProperty("documentSuffix")
    public String getDocumentSuffix() {
        return documentSuffix;
    }

    @JsonProperty("documentSuffix")
    public void setDocumentSuffix(String documentSuffix) {
        this.documentSuffix = documentSuffix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(JdeAtinaGetPurchaseOrderDetailForApproverRequest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("companyKeyOrderNo");
        sb.append('=');
        sb.append(((this.companyKeyOrderNo == null)?"<null>":this.companyKeyOrderNo));
        sb.append(',');
        sb.append("documentOrderTypeCode");
        sb.append('=');
        sb.append(((this.documentOrderTypeCode == null)?"<null>":this.documentOrderTypeCode));
        sb.append(',');
        sb.append("statusCodeNext");
        sb.append('=');
        sb.append(((this.statusCodeNext == null)?"<null>":this.statusCodeNext));
        sb.append(',');
        sb.append("documentOrderInvoiceNumber");
        sb.append('=');
        sb.append(((this.documentOrderInvoiceNumber == null)?"<null>":this.documentOrderInvoiceNumber));
        sb.append(',');
        sb.append("documentSuffix");
        sb.append('=');
        sb.append(((this.documentSuffix == null)?"<null>":this.documentSuffix));
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
        result = ((result* 31)+((this.statusCodeNext == null)? 0 :this.statusCodeNext.hashCode()));
        result = ((result* 31)+((this.companyKeyOrderNo == null)? 0 :this.companyKeyOrderNo.hashCode()));
        result = ((result* 31)+((this.documentOrderInvoiceNumber == null)? 0 :this.documentOrderInvoiceNumber.hashCode()));
        result = ((result* 31)+((this.documentOrderTypeCode == null)? 0 :this.documentOrderTypeCode.hashCode()));
        result = ((result* 31)+((this.documentSuffix == null)? 0 :this.documentSuffix.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JdeAtinaGetPurchaseOrderDetailForApproverRequest) == false) {
            return false;
        }
        JdeAtinaGetPurchaseOrderDetailForApproverRequest rhs = ((JdeAtinaGetPurchaseOrderDetailForApproverRequest) other);
        return ((((((this.statusCodeNext == rhs.statusCodeNext)||((this.statusCodeNext!= null)&&this.statusCodeNext.equals(rhs.statusCodeNext)))&&((this.companyKeyOrderNo == rhs.companyKeyOrderNo)||((this.companyKeyOrderNo!= null)&&this.companyKeyOrderNo.equals(rhs.companyKeyOrderNo))))&&((this.documentOrderInvoiceNumber == rhs.documentOrderInvoiceNumber)||((this.documentOrderInvoiceNumber!= null)&&this.documentOrderInvoiceNumber.equals(rhs.documentOrderInvoiceNumber))))&&((this.documentOrderTypeCode == rhs.documentOrderTypeCode)||((this.documentOrderTypeCode!= null)&&this.documentOrderTypeCode.equals(rhs.documentOrderTypeCode))))&&((this.documentSuffix == rhs.documentSuffix)||((this.documentSuffix!= null)&&this.documentSuffix.equals(rhs.documentSuffix))));
    }

}
