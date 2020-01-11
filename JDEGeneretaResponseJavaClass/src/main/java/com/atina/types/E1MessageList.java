
package com.atina.types;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "messagesAsString",
    "E1Messages"
})
public class E1MessageList {

    @JsonProperty("messagesAsString")
    private String messagesAsString;
    @JsonProperty("E1Messages")
    private List<Object> e1Messages = new ArrayList<Object>();

    @JsonProperty("messagesAsString")
    public String getMessagesAsString() {
        return messagesAsString;
    }

    @JsonProperty("messagesAsString")
    public void setMessagesAsString(String messagesAsString) {
        this.messagesAsString = messagesAsString;
    }

    @JsonProperty("E1Messages")
    public List<Object> getE1Messages() {
        return e1Messages;
    }

    @JsonProperty("E1Messages")
    public void setE1Messages(List<Object> e1Messages) {
        this.e1Messages = e1Messages;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(E1MessageList.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("messagesAsString");
        sb.append('=');
        sb.append(((this.messagesAsString == null)?"<null>":this.messagesAsString));
        sb.append(',');
        sb.append("e1Messages");
        sb.append('=');
        sb.append(((this.e1Messages == null)?"<null>":this.e1Messages));
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
        result = ((result* 31)+((this.e1Messages == null)? 0 :this.e1Messages.hashCode()));
        result = ((result* 31)+((this.messagesAsString == null)? 0 :this.messagesAsString.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof E1MessageList) == false) {
            return false;
        }
        E1MessageList rhs = ((E1MessageList) other);
        return (((this.e1Messages == rhs.e1Messages)||((this.e1Messages!= null)&&this.e1Messages.equals(rhs.e1Messages)))&&((this.messagesAsString == rhs.messagesAsString)||((this.messagesAsString!= null)&&this.messagesAsString.equals(rhs.messagesAsString))));
    }

}
