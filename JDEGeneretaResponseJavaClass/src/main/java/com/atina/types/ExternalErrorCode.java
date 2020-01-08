
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "apiName",
    "result",
    "result_namespace",
    "errorMessage",
    "httpStatus",
    "request",
    "response",
    "layer"
})
public class ExternalErrorCode {

    @JsonProperty("apiName")
    private String apiName;
    @JsonProperty("result")
    private String result;
    @JsonProperty("result_namespace")
    private String resultNamespace;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("httpStatus")
    private Integer httpStatus;
    @JsonProperty("request")
    private String request;
    @JsonProperty("response")
    private String response;
    @JsonProperty("layer")
    private String layer;

    @JsonProperty("apiName")
    public String getApiName() {
        return apiName;
    }

    @JsonProperty("apiName")
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(String result) {
        this.result = result;
    }

    @JsonProperty("result_namespace")
    public String getResultNamespace() {
        return resultNamespace;
    }

    @JsonProperty("result_namespace")
    public void setResultNamespace(String resultNamespace) {
        this.resultNamespace = resultNamespace;
    }

    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("httpStatus")
    public Integer getHttpStatus() {
        return httpStatus;
    }

    @JsonProperty("httpStatus")
    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    @JsonProperty("request")
    public String getRequest() {
        return request;
    }

    @JsonProperty("request")
    public void setRequest(String request) {
        this.request = request;
    }

    @JsonProperty("response")
    public String getResponse() {
        return response;
    }

    @JsonProperty("response")
    public void setResponse(String response) {
        this.response = response;
    }

    @JsonProperty("layer")
    public String getLayer() {
        return layer;
    }

    @JsonProperty("layer")
    public void setLayer(String layer) {
        this.layer = layer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ExternalErrorCode.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("apiName");
        sb.append('=');
        sb.append(((this.apiName == null)?"<null>":this.apiName));
        sb.append(',');
        sb.append("result");
        sb.append('=');
        sb.append(((this.result == null)?"<null>":this.result));
        sb.append(',');
        sb.append("resultNamespace");
        sb.append('=');
        sb.append(((this.resultNamespace == null)?"<null>":this.resultNamespace));
        sb.append(',');
        sb.append("errorMessage");
        sb.append('=');
        sb.append(((this.errorMessage == null)?"<null>":this.errorMessage));
        sb.append(',');
        sb.append("httpStatus");
        sb.append('=');
        sb.append(((this.httpStatus == null)?"<null>":this.httpStatus));
        sb.append(',');
        sb.append("request");
        sb.append('=');
        sb.append(((this.request == null)?"<null>":this.request));
        sb.append(',');
        sb.append("response");
        sb.append('=');
        sb.append(((this.response == null)?"<null>":this.response));
        sb.append(',');
        sb.append("layer");
        sb.append('=');
        sb.append(((this.layer == null)?"<null>":this.layer));
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
        result = ((result* 31)+((this.result == null)? 0 :this.result.hashCode()));
        result = ((result* 31)+((this.request == null)? 0 :this.request.hashCode()));
        result = ((result* 31)+((this.apiName == null)? 0 :this.apiName.hashCode()));
        result = ((result* 31)+((this.resultNamespace == null)? 0 :this.resultNamespace.hashCode()));
        result = ((result* 31)+((this.response == null)? 0 :this.response.hashCode()));
        result = ((result* 31)+((this.httpStatus == null)? 0 :this.httpStatus.hashCode()));
        result = ((result* 31)+((this.errorMessage == null)? 0 :this.errorMessage.hashCode()));
        result = ((result* 31)+((this.layer == null)? 0 :this.layer.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ExternalErrorCode) == false) {
            return false;
        }
        ExternalErrorCode rhs = ((ExternalErrorCode) other);
        return (((((((((this.result == rhs.result)||((this.result!= null)&&this.result.equals(rhs.result)))&&((this.request == rhs.request)||((this.request!= null)&&this.request.equals(rhs.request))))&&((this.apiName == rhs.apiName)||((this.apiName!= null)&&this.apiName.equals(rhs.apiName))))&&((this.resultNamespace == rhs.resultNamespace)||((this.resultNamespace!= null)&&this.resultNamespace.equals(rhs.resultNamespace))))&&((this.response == rhs.response)||((this.response!= null)&&this.response.equals(rhs.response))))&&((this.httpStatus == rhs.httpStatus)||((this.httpStatus!= null)&&this.httpStatus.equals(rhs.httpStatus))))&&((this.errorMessage == rhs.errorMessage)||((this.errorMessage!= null)&&this.errorMessage.equals(rhs.errorMessage))))&&((this.layer == rhs.layer)||((this.layer!= null)&&this.layer.equals(rhs.layer))));
    }

}
