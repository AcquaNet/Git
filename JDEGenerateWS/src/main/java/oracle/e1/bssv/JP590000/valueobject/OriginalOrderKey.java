package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssvfoundation.util.MathNumeric;

public class OriginalOrderKey extends ValueObject implements Serializable {
    /**
     * Document - Original<br>
     * <P>
     * A number that is used in conjunction with the values in the ODCT and OKCO<br>
     * fields to identify a transaction.<br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: ODOC<br>
     * EnterpriseOne field length: 8<br>
     * EnterpriseOne decimal places: 0<br>
     */
    private Integer documentNumber = null;
    /**
     * Document Type - Original<br>
     * <P>
     * A user defined code (system 00, type DT) that identifies the origin and purpose<br>
     * of the original document.<br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: ODCT<br>
     * EnterpriseOne field length: 2<br>
     * EnterpriseOne User Defined Code:  00/DT<br>
     */
    private String documentTypeCode = null;
    /**
     * Document Pay Item - Original<br>
     * <P>
     * The number that identifies a specific pay item associated with the document.<br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: OSFX<br>
     * EnterpriseOne field length: 3<br>
     */
    private String documentSuffix = null;
    /**
     * Document Company (Original Order)<br>
     * <P>
     * A number that is used in conjunction with the values in the ODOC and ODCT<br>
     * fields to identify a transaction.<br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: OKCO<br>
     * EnterpriseOne field length: 5<br>
     */
    private String documentCompany = null;
    
    public OriginalOrderKey() {
    }

    public void setDocumentNumber(Integer documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setDocumentNumber(MathNumeric documentNumber) {
        this.documentNumber = new Integer(documentNumber.intValue());
    }

    public Integer getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentSuffix(String documentSuffix) {
        this.documentSuffix = documentSuffix;
    }

    public String getDocumentSuffix() {
        return documentSuffix;
    }

    public void setDocumentCompany(String documentCompany) {
        this.documentCompany = documentCompany;
    }

    public String getDocumentCompany() {
        return documentCompany;
    }
}
