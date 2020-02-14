
package com.atina.types;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "e1MessageList",
    "purchaseOrderDetailForApproverResults"
})
public class JdeAtinaGetPurchaseOrderDetailForApproverResponse {

    @JsonProperty("e1MessageList")
    private E1MessageList e1MessageList;
    @JsonProperty("purchaseOrderDetailForApproverResults")
    private List<PurchaseOrderDetailForApproverResult> purchaseOrderDetailForApproverResults = new ArrayList<PurchaseOrderDetailForApproverResult>();

    @JsonProperty("e1MessageList")
    public E1MessageList getE1MessageList() {
        return e1MessageList;
    }

    @JsonProperty("e1MessageList")
    public void setE1MessageList(E1MessageList e1MessageList) {
        this.e1MessageList = e1MessageList;
    }

    @JsonProperty("purchaseOrderDetailForApproverResults")
    public List<PurchaseOrderDetailForApproverResult> getPurchaseOrderDetailForApproverResults() {
        return purchaseOrderDetailForApproverResults;
    }

    @JsonProperty("purchaseOrderDetailForApproverResults")
    public void setPurchaseOrderDetailForApproverResults(List<PurchaseOrderDetailForApproverResult> purchaseOrderDetailForApproverResults) {
        this.purchaseOrderDetailForApproverResults = purchaseOrderDetailForApproverResults;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(JdeAtinaGetPurchaseOrderDetailForApproverResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("e1MessageList");
        sb.append('=');
        sb.append(((this.e1MessageList == null)?"<null>":this.e1MessageList));
        sb.append(',');
        sb.append("purchaseOrderDetailForApproverResults");
        sb.append('=');
        sb.append(((this.purchaseOrderDetailForApproverResults == null)?"<null>":this.purchaseOrderDetailForApproverResults));
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
        result = ((result* 31)+((this.e1MessageList == null)? 0 :this.e1MessageList.hashCode()));
        result = ((result* 31)+((this.purchaseOrderDetailForApproverResults == null)? 0 :this.purchaseOrderDetailForApproverResults.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JdeAtinaGetPurchaseOrderDetailForApproverResponse) == false) {
            return false;
        }
        JdeAtinaGetPurchaseOrderDetailForApproverResponse rhs = ((JdeAtinaGetPurchaseOrderDetailForApproverResponse) other);
        return (((this.e1MessageList == rhs.e1MessageList)||((this.e1MessageList!= null)&&this.e1MessageList.equals(rhs.e1MessageList)))&&((this.purchaseOrderDetailForApproverResults == rhs.purchaseOrderDetailForApproverResults)||((this.purchaseOrderDetailForApproverResults!= null)&&this.purchaseOrderDetailForApproverResults.equals(rhs.purchaseOrderDetailForApproverResults))));
    }

}
