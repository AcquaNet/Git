package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import oracle.e1.bssvfoundation.base.ValueObject;

public class Subledger extends ValueObject implements Serializable {
    /**
     * Subledger - G/L<br>
     * <P>
     * A code that identifies a detailed, auxiliary account within a general ledger<br>
     * account. A subledger can be an equipment item number or an address book number.<br>
     * If you enter a subledger, you must also specify the subledger type.<br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: SBL<br>
     * EnterpriseOne field length: 8<br>
     */    
    private String subledger = null;
    /**
     * Subledger Type<br>
     * <P>
     * A user defined code (00/ST) that is used with the Subledger field to identify<br>
     * the subledger type and how the system performs subledger editing. On the User<br>
     * Defined Codes form, the second line of the description controls how the system<br>
     * performs editing. This is either hard-coded or user defined. Valid values<br>
     * include:<br>
     * <br>
     * A<br>
     * Alphanumeric field, do not edit  <br>
     * <br>
     * N<br>
     * Numeric field, right justify and zero fill  <br>
     * <br>
     * C<br>
     * Alphanumeric field, right justify and blank fill  <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: SBLT<br>
     * EnterpriseOne field length: 1<br>
     * EnterpriseOne User Defined Code:  00/ST<br>
     */
    private String subledgerTypeCode = null;
    
    public Subledger() {
    }

    public void setSubledger(String subledger) {
        this.subledger = subledger;
    }

    public String getSubledger() {
        return subledger;
    }

    public void setSubledgerTypeCode(String subledgerTypeCode) {
        this.subledgerTypeCode = subledgerTypeCode;
    }

    public String getSubledgerTypeCode() {
        return subledgerTypeCode;
    }
}
