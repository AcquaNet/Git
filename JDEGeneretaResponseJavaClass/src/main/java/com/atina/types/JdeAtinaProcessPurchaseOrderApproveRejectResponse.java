
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "numberOfDetailRemarksProcessed",
    "e1MessageList",
    "confirmApproveRejectSucess",
    "numberOfPurchaseOrdersProcessed"
})
public class JdeAtinaProcessPurchaseOrderApproveRejectResponse {

    @JsonProperty("numberOfDetailRemarksProcessed")
    private Integer numberOfDetailRemarksProcessed;
    @JsonProperty("e1MessageList")
    private E1MessageList__2 e1MessageList;
    @JsonProperty("confirmApproveRejectSucess")
    private String confirmApproveRejectSucess;
    @JsonProperty("numberOfPurchaseOrdersProcessed")
    private Integer numberOfPurchaseOrdersProcessed;

    @JsonProperty("numberOfDetailRemarksProcessed")
    public Integer getNumberOfDetailRemarksProcessed() {
        return numberOfDetailRemarksProcessed;
    }

    @JsonProperty("numberOfDetailRemarksProcessed")
    public void setNumberOfDetailRemarksProcessed(Integer numberOfDetailRemarksProcessed) {
        this.numberOfDetailRemarksProcessed = numberOfDetailRemarksProcessed;
    }

    @JsonProperty("e1MessageList")
    public E1MessageList__2 getE1MessageList() {
        return e1MessageList;
    }

    @JsonProperty("e1MessageList")
    public void setE1MessageList(E1MessageList__2 e1MessageList) {
        this.e1MessageList = e1MessageList;
    }

    @JsonProperty("confirmApproveRejectSucess")
    public String getConfirmApproveRejectSucess() {
        return confirmApproveRejectSucess;
    }

    @JsonProperty("confirmApproveRejectSucess")
    public void setConfirmApproveRejectSucess(String confirmApproveRejectSucess) {
        this.confirmApproveRejectSucess = confirmApproveRejectSucess;
    }

    @JsonProperty("numberOfPurchaseOrdersProcessed")
    public Integer getNumberOfPurchaseOrdersProcessed() {
        return numberOfPurchaseOrdersProcessed;
    }

    @JsonProperty("numberOfPurchaseOrdersProcessed")
    public void setNumberOfPurchaseOrdersProcessed(Integer numberOfPurchaseOrdersProcessed) {
        this.numberOfPurchaseOrdersProcessed = numberOfPurchaseOrdersProcessed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(JdeAtinaProcessPurchaseOrderApproveRejectResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("numberOfDetailRemarksProcessed");
        sb.append('=');
        sb.append(((this.numberOfDetailRemarksProcessed == null)?"<null>":this.numberOfDetailRemarksProcessed));
        sb.append(',');
        sb.append("e1MessageList");
        sb.append('=');
        sb.append(((this.e1MessageList == null)?"<null>":this.e1MessageList));
        sb.append(',');
        sb.append("confirmApproveRejectSucess");
        sb.append('=');
        sb.append(((this.confirmApproveRejectSucess == null)?"<null>":this.confirmApproveRejectSucess));
        sb.append(',');
        sb.append("numberOfPurchaseOrdersProcessed");
        sb.append('=');
        sb.append(((this.numberOfPurchaseOrdersProcessed == null)?"<null>":this.numberOfPurchaseOrdersProcessed));
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
        result = ((result* 31)+((this.confirmApproveRejectSucess == null)? 0 :this.confirmApproveRejectSucess.hashCode()));
        result = ((result* 31)+((this.numberOfDetailRemarksProcessed == null)? 0 :this.numberOfDetailRemarksProcessed.hashCode()));
        result = ((result* 31)+((this.numberOfPurchaseOrdersProcessed == null)? 0 :this.numberOfPurchaseOrdersProcessed.hashCode()));
        result = ((result* 31)+((this.e1MessageList == null)? 0 :this.e1MessageList.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JdeAtinaProcessPurchaseOrderApproveRejectResponse) == false) {
            return false;
        }
        JdeAtinaProcessPurchaseOrderApproveRejectResponse rhs = ((JdeAtinaProcessPurchaseOrderApproveRejectResponse) other);
        return (((((this.confirmApproveRejectSucess == rhs.confirmApproveRejectSucess)||((this.confirmApproveRejectSucess!= null)&&this.confirmApproveRejectSucess.equals(rhs.confirmApproveRejectSucess)))&&((this.numberOfDetailRemarksProcessed == rhs.numberOfDetailRemarksProcessed)||((this.numberOfDetailRemarksProcessed!= null)&&this.numberOfDetailRemarksProcessed.equals(rhs.numberOfDetailRemarksProcessed))))&&((this.numberOfPurchaseOrdersProcessed == rhs.numberOfPurchaseOrdersProcessed)||((this.numberOfPurchaseOrdersProcessed!= null)&&this.numberOfPurchaseOrdersProcessed.equals(rhs.numberOfPurchaseOrdersProcessed))))&&((this.e1MessageList == rhs.e1MessageList)||((this.e1MessageList!= null)&&this.e1MessageList.equals(rhs.e1MessageList))));
    }

}
