package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Calendar;

import java.util.Date;

import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssvfoundation.util.MathNumeric;

public class GeneralLedgerKey extends ValueObject implements Serializable{
    /**
     * Document Company<br>
     * <p>
     * A number that, with the document number, document type and G/L date,<br>
     * uniquely identifies an original document, such as invoice, voucher, or<br>
     * journal entry.<br>
     * <br>
     * If you use the Next Numbers by Company/Fiscal Year feature, the<br>
     * Automatic Next Numbers program (X0010) uses the document company to<br>
     * retrieve the correct next number for that company.<br>
     * <br>
     * If two or more original documents have the same document number and<br>
     * document type, you can use the document company to locate the desired<br>
     * document.<br>
     * &NOTE: Data for Company and Business Unit fields may be entered without concern<br>
     * for special E1 formatting and necessary formatting will take place during processing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: KCO <br>
     * EnterpriseOne field length:  5 <br>
     */
    private String documentCompany = null;

    /**
     * Document Type<br>
     * <p>
     * A user defined code (00/DT) that identifies the origin and purpose of<br>
     * the transaction. J.D. Edwards reserves several prefixes for document<br>
     * types, such as vouchers, invoices, receipts, and timesheets. The<br>
     * reserved document type prefixes for codes are:<br>
     * <br>
     * P<br>
     * Accounts payable documents  <br>
     * <br>
     * R<br>
     * Accounts receivable documents  <br>
     * <br>
     * T<br>
     * Time and Pay documents  <br>
     * <br>
     * I<br>
     * Inventory documents  <br>
     * <br>
     * O<br>
     * Purchase order documents  <br>
     * <br>
     * S<br>
     * Sales order documents<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DCT <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 00/DT<br>
     */
    private String documentTypeCode = null;

    /**
     * Document (Voucher, Invoice, etc.)<br>
     * <p>
     * A number that identifies the original document, such as a voucher,<br>
     * invoice, or journal entry. On entry forms, you can assign the document<br>
     * number or let the system assign it using the Next Numbers program<br>
     * (P0002). Matching document numbers (DOCM) identify related documents in<br>
     * the Accounts Receivable and Accounts Payable systems. Examples of<br>
     * original and matching documents are:<br>
     * <br>
     * Accounts Payable  <br>
     * <br>
     *   o Original document - voucher  <br>
     * <br>
     *   o Matching document - payment <br>
     * <br>
     * Accounts Receivable  <br>
     * <br>
     *   o Original document - invoice  <br>
     * <br>
     *   o Matching document - receipt <br>
     * <br>
     * Note: In the Accounts Receivable system, the following transactions<br>
     * simultaneously generate original and matching documents: deductions,<br>
     * unapplied receipts, chargebacks, and drafts.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DOC <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private Integer documentNumber = null;

    /**
     * Date - For G/L (and Voucher) - Julian<br>
     * <p>
     * A date that identifies the financial period to which the transaction<br>
     * will be posted. You define financial periods for a date pattern code<br>
     * that you assign to the company record. The system compares the date that<br>
     * you enter on the transaction to the fiscal date pattern assigned to the<br>
     * company to retrieve the appropriate fiscal period number, as well as to<br>
     * perform date validations.<br>
     * <br>
     * @NOTE: The date time stamp entered is adjusted to Coordinated Universal Time (UTC),<br>
     * which is determined using the timezone on your SBF server, unless an offset is used.<br>
     * For example, if the SBF server were GMT-7, entering 2007-01-18T00:00:00.000 will<br>
     * result in an adjustment to this value of -7 hours, and the actual value that is<br>
     * passed in to E1 will become 2007-01-17T17:00:00.000.  An Offest can be passed in<br>
     * with the orginal value so that these adjustments do not occur, such as<br>
     * 2007-01-18T00:00:00.000-7:00, which offsets by 7 hours from UTC, will result<br>
     * in the date being passed into E1 as 2007-01-18T00:00:00.000.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DGJ <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Calendar dateAccounting = null;

    /**
     * Journal Entry Line Number<br>
     * <p>
     * A number that designates a line within a journal entry. The system uses<br>
     * this field to sequence the journal entry lines for inquiry purposes.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: JELN <br>
     * EnterpriseOne field length:  7 <br>
     * EnterpriseOne decimal places: 1 <br>
     */
    private BigDecimal documentLineNumber = null;

    /**
     * Line Extension Code<br>
     * <p>
     * Line Extension Code<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EXTL <br>
     * EnterpriseOne field length:  2 <br>
     */
    private String lineExtensionCode = null;

    /**
     * Ledger Types<br>
     * <p>
     * A user defined code (09/LT) that specifies the type of ledger, such as<br>
     * AA (Actual Amounts), BA (Budget Amount), or AU (Actual Units). You can<br>
     * set up multiple, concurrent accounting ledgers within the general ledger<br>
     * to establish an audit trail for all transactions.<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: LT <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 09/LT<br>
     * EnterpriseOne Default Value: AA<br>
     */
    private String ledgerTypeCode = null;

    /**
     * Document Pay Item<br>
     * <p>
     * A number that identifies the pay item for a voucher or an invoice. The<br>
     * system assigns the pay item number. If the voucher or invoice has<br>
     * multiple pay items, the numbers are sequential.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: SFX <br>
     * EnterpriseOne field length:  3 <br>
     */
    private String documentPayItem = null;
    
    public GeneralLedgerKey() {
    }

    public void setDocumentCompany(String documentCompany) {
        this.documentCompany = documentCompany;
    }

    public String getDocumentCompany() {
        return documentCompany;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public String getDocumentTypeCode() {
        return documentTypeCode;
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

    public void setDateAccounting(Calendar dateAccounting) {
        this.dateAccounting = dateAccounting;
    }

    public void setDateAccounting(Date dateAccounting) {       
        if(dateAccounting != null){
            Calendar effDate = Calendar.getInstance();
            effDate.setTime(dateAccounting);          
          this.dateAccounting = effDate;
      }
    }

    public Calendar getDateAccounting() {
        return dateAccounting;
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

    public void setLineExtensionCode(String lineExtensionCode) {
        this.lineExtensionCode = lineExtensionCode;
    }

    public String getLineExtensionCode() {
        return lineExtensionCode;
    }

    public void setLedgerTypeCode(String ledgerTypeCode) {
        this.ledgerTypeCode = ledgerTypeCode;
    }

    public String getLedgerTypeCode() {
        return ledgerTypeCode;
    }

    public void setDocumentPayItem(String documentPayItem) {
        this.documentPayItem = documentPayItem;
    }

    public String getDocumentPayItem() {
        return documentPayItem;
    }
}
