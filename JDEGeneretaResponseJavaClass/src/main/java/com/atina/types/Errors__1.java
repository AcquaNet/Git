
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "flowName",
    "errorCode",
    "category",
    "httpStatus",
    "errorMessage",
    "externalErrorCode"
})
public class Errors__1 {

    @JsonProperty("flowName")
    private String flowName;
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("category")
    private String category;
    @JsonProperty("httpStatus")
    private Integer httpStatus;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("externalErrorCode")
    private ExternalErrorCode externalErrorCode;

    @JsonProperty("flowName")
    public String getFlowName() {
        return flowName;
    }

    @JsonProperty("flowName")
    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    @JsonProperty("errorCode")
    public String getErrorCode() {
        return errorCode;
    }

    @JsonProperty("errorCode")
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("httpStatus")
    public Integer getHttpStatus() {
        return httpStatus;
    }

    @JsonProperty("httpStatus")
    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("externalErrorCode")
    public ExternalErrorCode getExternalErrorCode() {
        return externalErrorCode;
    }

    @JsonProperty("externalErrorCode")
    public void setExternalErrorCode(ExternalErrorCode externalErrorCode) {
        this.externalErrorCode = externalErrorCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Errors__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("flowName");
        sb.append('=');
        sb.append(((this.flowName == null)?"<null>":this.flowName));
        sb.append(',');
        sb.append("errorCode");
        sb.append('=');
        sb.append(((this.errorCode == null)?"<null>":this.errorCode));
        sb.append(',');
        sb.append("category");
        sb.append('=');
        sb.append(((this.category == null)?"<null>":this.category));
        sb.append(',');
        sb.append("httpStatus");
        sb.append('=');
        sb.append(((this.httpStatus == null)?"<null>":this.httpStatus));
        sb.append(',');
        sb.append("errorMessage");
        sb.append('=');
        sb.append(((this.errorMessage == null)?"<null>":this.errorMessage));
        sb.append(',');
        sb.append("externalErrorCode");
        sb.append('=');
        sb.append(((this.externalErrorCode == null)?"<null>":this.externalErrorCode));
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
        result = ((result* 31)+((this.httpStatus == null)? 0 :this.httpStatus.hashCode()));
        result = ((result* 31)+((this.errorMessage == null)? 0 :this.errorMessage.hashCode()));
        result = ((result* 31)+((this.errorCode == null)? 0 :this.errorCode.hashCode()));
        result = ((result* 31)+((this.externalErrorCode == null)? 0 :this.externalErrorCode.hashCode()));
        result = ((result* 31)+((this.category == null)? 0 :this.category.hashCode()));
        result = ((result* 31)+((this.flowName == null)? 0 :this.flowName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Errors__1) == false) {
            return false;
        }
        Errors__1 rhs = ((Errors__1) other);
        return (((((((this.httpStatus == rhs.httpStatus)||((this.httpStatus!= null)&&this.httpStatus.equals(rhs.httpStatus)))&&((this.errorMessage == rhs.errorMessage)||((this.errorMessage!= null)&&this.errorMessage.equals(rhs.errorMessage))))&&((this.errorCode == rhs.errorCode)||((this.errorCode!= null)&&this.errorCode.equals(rhs.errorCode))))&&((this.externalErrorCode == rhs.externalErrorCode)||((this.externalErrorCode!= null)&&this.externalErrorCode.equals(rhs.externalErrorCode))))&&((this.category == rhs.category)||((this.category!= null)&&this.category.equals(rhs.category))))&&((this.flowName == rhs.flowName)||((this.flowName!= null)&&this.flowName.equals(rhs.flowName))));
    }

}
