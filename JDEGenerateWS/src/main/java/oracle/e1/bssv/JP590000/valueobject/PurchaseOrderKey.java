package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import java.math.BigDecimal;

import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssvfoundation.util.MathNumeric;

public class PurchaseOrderKey extends ValueObject implements Serializable {
    /**
     * Document Company (Purchase Order)<br>
     * <P>
     * For purchase orders, a code that differentiates a record from another with the<br>
     * same document number, document type, and G/L date. <br>
     * <br>
     * If you are using the Next Numbers by Company/Fiscal Year feature of the<br>
     * software, the Next Numbers program (X0010) uses the document company to<br>
     * retrieve the next number. If two or more original documents have the same<br>
     * document number and type, you can use the document company to locate the<br>
     * correct record.<br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: PKCO<br>
     * EnterpriseOne field length: 5<br>
     */
    private String documentCompany = null;
    /**
     * Document Type - Purchase Order<br>
     * <P>
     * A value that is hard-coded in the originating programs and passed to the<br>
     * Accounts Payable system.<br>
     * <br>
     * In Accounts Receivable, the document type on the purchase order issued by the<br>
     * customer can be entered directly into the Accounts Receivable Ledger (F03B11)<br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: PDCT<br>
     * EnterpriseOne field length: 2<br>
     * EnterpriseOne User Defined Code:  00/DT<br>
     */    
    private String documentNumber = null;
    /**
     * Purchase Order<br>
     * <P>
     * A document that authorizes the delivery of specified merchandise or the<br>
     * rendering of certain services.<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: PO<br>
     * EnterpriseOne field length: 8<br>
     */    
    private String documentTypeCode = null;
    /**
     * Purchase Order Suffix<br>
     * <P>
     * The purchase order suffix is used to distinguish between multiple occurrences<br>
     * of a single purchase order.  It is frequently used in the case of blanket<br>
     * purchase orders.<br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: PSFX<br>
     * EnterpriseOne field length: 3<br>
     */
    private String documentSuffix = null;
    /**
     * Line Number<br>
     * <P>
     * A number that identifies multiple occurrences, such as line numbers on a<br>
     * purchase order or other document. Generally, the system assigns this number,but<br>
     * in some cases you can override it.<br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: LNID<br>
     * EnterpriseOne field length: 6<br>
     * EnterpriseOne decimal places: 3<br>
     */
    private BigDecimal documentLineNumber = null;
    
    public PurchaseOrderKey() {
    }

    public void setDocumentCompany(String documentCompany) {
        this.documentCompany = documentCompany;
    }

    public String getDocumentCompany() {
        return documentCompany;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() {
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

    public void setDocumentLineNumber(BigDecimal documentLineNumber) {
        this.documentLineNumber = documentLineNumber;
    }

    public void setDocumentLineNumber(MathNumeric documentLineNumber) {
        this.documentLineNumber = documentLineNumber.asBigDecimal();
    }

    public BigDecimal getDocumentLineNumber() {
        return documentLineNumber;
    }
}
