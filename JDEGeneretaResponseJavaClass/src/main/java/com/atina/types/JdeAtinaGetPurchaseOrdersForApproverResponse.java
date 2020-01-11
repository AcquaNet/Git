
package com.atina.types;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "e1MessageList",
    "purchaseOrdersForApproverResults"
})
public class JdeAtinaGetPurchaseOrdersForApproverResponse {

    @JsonProperty("e1MessageList")
    private E1MessageList e1MessageList;
    @JsonProperty("purchaseOrdersForApproverResults")
    private List<PurchaseOrdersForApproverResult> purchaseOrdersForApproverResults = new ArrayList<PurchaseOrdersForApproverResult>();

    @JsonProperty("e1MessageList")
    public E1MessageList getE1MessageList() {
        return e1MessageList;
    }

    @JsonProperty("e1MessageList")
    public void setE1MessageList(E1MessageList e1MessageList) {
        this.e1MessageList = e1MessageList;
    }

    @JsonProperty("purchaseOrdersForApproverResults")
    public List<PurchaseOrdersForApproverResult> getPurchaseOrdersForApproverResults() {
        return purchaseOrdersForApproverResults;
    }

    @JsonProperty("purchaseOrdersForApproverResults")
    public void setPurchaseOrdersForApproverResults(List<PurchaseOrdersForApproverResult> purchaseOrdersForApproverResults) {
        this.purchaseOrdersForApproverResults = purchaseOrdersForApproverResults;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(JdeAtinaGetPurchaseOrdersForApproverResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("e1MessageList");
        sb.append('=');
        sb.append(((this.e1MessageList == null)?"<null>":this.e1MessageList));
        sb.append(',');
        sb.append("purchaseOrdersForApproverResults");
        sb.append('=');
        sb.append(((this.purchaseOrdersForApproverResults == null)?"<null>":this.purchaseOrdersForApproverResults));
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
        result = ((result* 31)+((this.purchaseOrdersForApproverResults == null)? 0 :this.purchaseOrdersForApproverResults.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JdeAtinaGetPurchaseOrdersForApproverResponse) == false) {
            return false;
        }
        JdeAtinaGetPurchaseOrdersForApproverResponse rhs = ((JdeAtinaGetPurchaseOrdersForApproverResponse) other);
        return (((this.e1MessageList == rhs.e1MessageList)||((this.e1MessageList!= null)&&this.e1MessageList.equals(rhs.e1MessageList)))&&((this.purchaseOrdersForApproverResults == rhs.purchaseOrdersForApproverResults)||((this.purchaseOrdersForApproverResults!= null)&&this.purchaseOrdersForApproverResults.equals(rhs.purchaseOrdersForApproverResults))));
    }

}
