
package com.atina.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "errors"
})
public class JdeAtinaErrorResponse {

    @JsonProperty("errors")
    private Errors errors;

    @JsonProperty("errors")
    public Errors getErrors() {
        return errors;
    }

    @JsonProperty("errors")
    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(JdeAtinaErrorResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("errors");
        sb.append('=');
        sb.append(((this.errors == null)?"<null>":this.errors));
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
        result = ((result* 31)+((this.errors == null)? 0 :this.errors.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JdeAtinaErrorResponse) == false) {
            return false;
        }
        JdeAtinaErrorResponse rhs = ((JdeAtinaErrorResponse) other);
        return ((this.errors == rhs.errors)||((this.errors!= null)&&this.errors.equals(rhs.errors)));
    }

}
