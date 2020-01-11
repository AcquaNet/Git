
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "entityId"
})
public class JdeAtinaGetPurchaseOrdersForApproverRequest {

    @JsonProperty("entityId")
    private Integer entityId;

    @JsonProperty("entityId")
    public Integer getEntityId() {
        return entityId;
    }

    @JsonProperty("entityId")
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(JdeAtinaGetPurchaseOrdersForApproverRequest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("entityId");
        sb.append('=');
        sb.append(((this.entityId == null)?"<null>":this.entityId));
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
        result = ((result* 31)+((this.entityId == null)? 0 :this.entityId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JdeAtinaGetPurchaseOrdersForApproverRequest) == false) {
            return false;
        }
        JdeAtinaGetPurchaseOrdersForApproverRequest rhs = ((JdeAtinaGetPurchaseOrdersForApproverRequest) other);
        return ((this.entityId == rhs.entityId)||((this.entityId!= null)&&this.entityId.equals(rhs.entityId)));
    }

}
