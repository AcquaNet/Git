
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "businessUnit",
    "companyKeyOrderNumber",
    "documentType",
    "documentNumber",
    "orderSuffix",
    "remark",
    "statusApproval",
    "amountToApprove",
    "approveReject",
    "approverAddressNumber",
    "lineNumber",
    "approvalRouteCode"
})
public class JdeAtinaProcessPurchaseOrderApproveRejectRequest {

    @JsonProperty("businessUnit")
    private String businessUnit;
    @JsonProperty("companyKeyOrderNumber")
    private String companyKeyOrderNumber;
    @JsonProperty("documentType")
    private String documentType;
    @JsonProperty("documentNumber")
    private String documentNumber;
    @JsonProperty("orderSuffix")
    private String orderSuffix;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("statusApproval")
    private String statusApproval;
    @JsonProperty("amountToApprove")
    private String amountToApprove;
    @JsonProperty("approveReject")
    private String approveReject;
    @JsonProperty("approverAddressNumber")
    private String approverAddressNumber;
    @JsonProperty("lineNumber")
    private String lineNumber;
    @JsonProperty("approvalRouteCode")
    private String approvalRouteCode;

    @JsonProperty("businessUnit")
    public String getBusinessUnit() {
        return businessUnit;
    }

    @JsonProperty("businessUnit")
    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    @JsonProperty("companyKeyOrderNumber")
    public String getCompanyKeyOrderNumber() {
        return companyKeyOrderNumber;
    }

    @JsonProperty("companyKeyOrderNumber")
    public void setCompanyKeyOrderNumber(String companyKeyOrderNumber) {
        this.companyKeyOrderNumber = companyKeyOrderNumber;
    }

    @JsonProperty("documentType")
    public String getDocumentType() {
        return documentType;
    }

    @JsonProperty("documentType")
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @JsonProperty("documentNumber")
    public String getDocumentNumber() {
        return documentNumber;
    }

    @JsonProperty("documentNumber")
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @JsonProperty("orderSuffix")
    public String getOrderSuffix() {
        return orderSuffix;
    }

    @JsonProperty("orderSuffix")
    public void setOrderSuffix(String orderSuffix) {
        this.orderSuffix = orderSuffix;
    }

    @JsonProperty("remark")
    public String getRemark() {
        return remark;
    }

    @JsonProperty("remark")
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonProperty("statusApproval")
    public String getStatusApproval() {
        return statusApproval;
    }

    @JsonProperty("statusApproval")
    public void setStatusApproval(String statusApproval) {
        this.statusApproval = statusApproval;
    }

    @JsonProperty("amountToApprove")
    public String getAmountToApprove() {
        return amountToApprove;
    }

    @JsonProperty("amountToApprove")
    public void setAmountToApprove(String amountToApprove) {
        this.amountToApprove = amountToApprove;
    }

    @JsonProperty("approveReject")
    public String getApproveReject() {
        return approveReject;
    }

    @JsonProperty("approveReject")
    public void setApproveReject(String approveReject) {
        this.approveReject = approveReject;
    }

    @JsonProperty("approverAddressNumber")
    public String getApproverAddressNumber() {
        return approverAddressNumber;
    }

    @JsonProperty("approverAddressNumber")
    public void setApproverAddressNumber(String approverAddressNumber) {
        this.approverAddressNumber = approverAddressNumber;
    }

    @JsonProperty("lineNumber")
    public String getLineNumber() {
        return lineNumber;
    }

    @JsonProperty("lineNumber")
    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    @JsonProperty("approvalRouteCode")
    public String getApprovalRouteCode() {
        return approvalRouteCode;
    }

    @JsonProperty("approvalRouteCode")
    public void setApprovalRouteCode(String approvalRouteCode) {
        this.approvalRouteCode = approvalRouteCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(JdeAtinaProcessPurchaseOrderApproveRejectRequest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("businessUnit");
        sb.append('=');
        sb.append(((this.businessUnit == null)?"<null>":this.businessUnit));
        sb.append(',');
        sb.append("companyKeyOrderNumber");
        sb.append('=');
        sb.append(((this.companyKeyOrderNumber == null)?"<null>":this.companyKeyOrderNumber));
        sb.append(',');
        sb.append("documentType");
        sb.append('=');
        sb.append(((this.documentType == null)?"<null>":this.documentType));
        sb.append(',');
        sb.append("documentNumber");
        sb.append('=');
        sb.append(((this.documentNumber == null)?"<null>":this.documentNumber));
        sb.append(',');
        sb.append("orderSuffix");
        sb.append('=');
        sb.append(((this.orderSuffix == null)?"<null>":this.orderSuffix));
        sb.append(',');
        sb.append("remark");
        sb.append('=');
        sb.append(((this.remark == null)?"<null>":this.remark));
        sb.append(',');
        sb.append("statusApproval");
        sb.append('=');
        sb.append(((this.statusApproval == null)?"<null>":this.statusApproval));
        sb.append(',');
        sb.append("amountToApprove");
        sb.append('=');
        sb.append(((this.amountToApprove == null)?"<null>":this.amountToApprove));
        sb.append(',');
        sb.append("approveReject");
        sb.append('=');
        sb.append(((this.approveReject == null)?"<null>":this.approveReject));
        sb.append(',');
        sb.append("approverAddressNumber");
        sb.append('=');
        sb.append(((this.approverAddressNumber == null)?"<null>":this.approverAddressNumber));
        sb.append(',');
        sb.append("lineNumber");
        sb.append('=');
        sb.append(((this.lineNumber == null)?"<null>":this.lineNumber));
        sb.append(',');
        sb.append("approvalRouteCode");
        sb.append('=');
        sb.append(((this.approvalRouteCode == null)?"<null>":this.approvalRouteCode));
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
        result = ((result* 31)+((this.businessUnit == null)? 0 :this.businessUnit.hashCode()));
        result = ((result* 31)+((this.companyKeyOrderNumber == null)? 0 :this.companyKeyOrderNumber.hashCode()));
        result = ((result* 31)+((this.documentType == null)? 0 :this.documentType.hashCode()));
        result = ((result* 31)+((this.documentNumber == null)? 0 :this.documentNumber.hashCode()));
        result = ((result* 31)+((this.orderSuffix == null)? 0 :this.orderSuffix.hashCode()));
        result = ((result* 31)+((this.remark == null)? 0 :this.remark.hashCode()));
        result = ((result* 31)+((this.statusApproval == null)? 0 :this.statusApproval.hashCode()));
        result = ((result* 31)+((this.amountToApprove == null)? 0 :this.amountToApprove.hashCode()));
        result = ((result* 31)+((this.approveReject == null)? 0 :this.approveReject.hashCode()));
        result = ((result* 31)+((this.approverAddressNumber == null)? 0 :this.approverAddressNumber.hashCode()));
        result = ((result* 31)+((this.lineNumber == null)? 0 :this.lineNumber.hashCode()));
        result = ((result* 31)+((this.approvalRouteCode == null)? 0 :this.approvalRouteCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JdeAtinaProcessPurchaseOrderApproveRejectRequest) == false) {
            return false;
        }
        JdeAtinaProcessPurchaseOrderApproveRejectRequest rhs = ((JdeAtinaProcessPurchaseOrderApproveRejectRequest) other);
        return (((((((((((((this.businessUnit == rhs.businessUnit)||((this.businessUnit!= null)&&this.businessUnit.equals(rhs.businessUnit)))&&((this.companyKeyOrderNumber == rhs.companyKeyOrderNumber)||((this.companyKeyOrderNumber!= null)&&this.companyKeyOrderNumber.equals(rhs.companyKeyOrderNumber))))&&((this.documentType == rhs.documentType)||((this.documentType!= null)&&this.documentType.equals(rhs.documentType))))&&((this.documentNumber == rhs.documentNumber)||((this.documentNumber!= null)&&this.documentNumber.equals(rhs.documentNumber))))&&((this.orderSuffix == rhs.orderSuffix)||((this.orderSuffix!= null)&&this.orderSuffix.equals(rhs.orderSuffix))))&&((this.remark == rhs.remark)||((this.remark!= null)&&this.remark.equals(rhs.remark))))&&((this.statusApproval == rhs.statusApproval)||((this.statusApproval!= null)&&this.statusApproval.equals(rhs.statusApproval))))&&((this.amountToApprove == rhs.amountToApprove)||((this.amountToApprove!= null)&&this.amountToApprove.equals(rhs.amountToApprove))))&&((this.approveReject == rhs.approveReject)||((this.approveReject!= null)&&this.approveReject.equals(rhs.approveReject))))&&((this.approverAddressNumber == rhs.approverAddressNumber)||((this.approverAddressNumber!= null)&&this.approverAddressNumber.equals(rhs.approverAddressNumber))))&&((this.lineNumber == rhs.lineNumber)||((this.lineNumber!= null)&&this.lineNumber.equals(rhs.lineNumber))))&&((this.approvalRouteCode == rhs.approvalRouteCode)||((this.approvalRouteCode!= null)&&this.approvalRouteCode.equals(rhs.approvalRouteCode))));
    }

}
