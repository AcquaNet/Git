
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "entityTaxId",
    "entityLongId",
    "entityId"
})
public class Approver__1 {

    @JsonProperty("entityTaxId")
    private String entityTaxId;
    @JsonProperty("entityLongId")
    private String entityLongId;
    @JsonProperty("entityId")
    private Integer entityId;

    @JsonProperty("entityTaxId")
    public String getEntityTaxId() {
        return entityTaxId;
    }

    @JsonProperty("entityTaxId")
    public void setEntityTaxId(String entityTaxId) {
        this.entityTaxId = entityTaxId;
    }

    @JsonProperty("entityLongId")
    public String getEntityLongId() {
        return entityLongId;
    }

    @JsonProperty("entityLongId")
    public void setEntityLongId(String entityLongId) {
        this.entityLongId = entityLongId;
    }

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
        sb.append(Approver__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("entityTaxId");
        sb.append('=');
        sb.append(((this.entityTaxId == null)?"<null>":this.entityTaxId));
        sb.append(',');
        sb.append("entityLongId");
        sb.append('=');
        sb.append(((this.entityLongId == null)?"<null>":this.entityLongId));
        sb.append(',');
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
        result = ((result* 31)+((this.entityTaxId == null)? 0 :this.entityTaxId.hashCode()));
        result = ((result* 31)+((this.entityId == null)? 0 :this.entityId.hashCode()));
        result = ((result* 31)+((this.entityLongId == null)? 0 :this.entityLongId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Approver__1) == false) {
            return false;
        }
        Approver__1 rhs = ((Approver__1) other);
        return ((((this.entityTaxId == rhs.entityTaxId)||((this.entityTaxId!= null)&&this.entityTaxId.equals(rhs.entityTaxId)))&&((this.entityId == rhs.entityId)||((this.entityId!= null)&&this.entityId.equals(rhs.entityId))))&&((this.entityLongId == rhs.entityLongId)||((this.entityLongId!= null)&&this.entityLongId.equals(rhs.entityLongId))));
    }

}
