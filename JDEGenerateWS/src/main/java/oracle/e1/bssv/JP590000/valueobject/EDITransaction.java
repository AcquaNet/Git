package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Calendar;

import java.util.Date;

import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssvfoundation.util.MathNumeric;

public class EDITransaction extends ValueObject implements Serializable {
    /**
     * EDI - User ID<br>
     * <p>
     * The source of the transaction. This can be a user ID, a workstation, the<br>
     * address of an external system, a node on a network, and so on. This<br>
     * field helps identify both the transaction and its point of origin.<br>
     * </p>
     * EnterpriseOne Key Field: true <br>
     * EnterpriseOne Alias: EDUS <br>
     * EnterpriseOne field length:  10 <br>
     */
    private String ediUserId = null;

    /**
     * Type Record<br>
     * <p>
     * The identifier used to mark EDI transaction records as header and detail<br>
     * information. This is an EDI function only.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDTY <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: 00/RD<br>
     */
    private String ediRecordType = null;

    /**
     * Record Sequence<br>
     * <p>
     * An identifier that is used to assign the relative position within the<br>
     * header or detail information of an EDI transaction-- for example, H01,<br>
     * H02, D01, and so on.<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDSQ <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private Integer ediRecordSequence = null;

    /**
     * EDI - Transaction Number<br>
     * <p>
     * The number that an Electronic Data Interchange (EDI) transmitter assigns<br>
     * to a transaction. In a non-EDI environment, you can assign any number<br>
     * that is meaningful to you to identify a transaction within a batch. It<br>
     * can be the same as a J.D. Edwards document number.<br>
     * </p>
     * EnterpriseOne Key Field: true <br>
     * EnterpriseOne Alias: EDTN <br>
     * EnterpriseOne field length:  22 <br>
     */
    private String ediTransactionNumber = null;

    /**
     * EDI - Document Type<br>
     * <p>
     * The document type that is assigned by the transmitter in an EDI<br>
     * transaction.<br>
     * <br>
     * In a non EDI environment, this would be consistent with the order type<br>
     * (DCTO) assigned at order entry time, an invoice document type, a voucher<br>
     * document type, and so on.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDCT <br>
     * EnterpriseOne field length:  2 <br>
     */
    private String ediDocumentType = null;

    /**
     * EDI - Line Number<br>
     * <p>
     * This is the line number you assign when originating an EDI transaction.<br>
     * This number can represent an order line number (applicable for any order<br>
     * type), an invoice pay item, a journal entry line number, and so on.<br>
     * </p>
     * EnterpriseOne Key Field: true <br>
     * EnterpriseOne Alias: EDLN <br>
     * EnterpriseOne field length:  7 <br>
     * EnterpriseOne decimal places: 3 <br>
     */
    private BigDecimal ediLineNumber = null;

    /**
     * EDI - Transaction Set Number<br>
     * <p>
     * The qualifier used to identify a specific type of EDI transaction.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDTS <br>
     * EnterpriseOne field length:  6 <br>
     */
    private String ediTransactionSetNumber = null;

    /**
     * EDI - Translation Format<br>
     * <p>
     * The qualifier used to identify a specific mapping structure used to<br>
     * process both inbound and outbound EDI transactions.  This does not apply<br>
     * to non-EDI transactions.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDFT <br>
     * EnterpriseOne field length:  10 <br>
     */
    private String ediTranslationFormat = null;

    /**
     * EDI - Transmission Date<br>
     * <p>
     * The specific date that an EDI transaction was either transmitted or<br>
     * received.<br>
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
     * EnterpriseOne Alias: EDDT <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Calendar ediTransmissionDate = null;

    /**
     * EDI - Send/Receive Indicator<br>
     * <p>
     * Indicator used to identify if a specific transaction set can be sent,<br>
     * received or both.  Valid values are:<br>
     * <br>
     *    S Send<br>
     *    R Receive<br>
     *    B Both<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDER <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: H00/ED<br>
     * EnterpriseOne Default Value: B<br>
     */
    private String ediSendReceiveIndicator = null;

    /**
     * EDI - Detail Lines Processed<br>
     * <p>
     * The number of detail lines transmitted in an EDI transaction.  This is<br>
     * the total number of lines on a per order basis, number of lines included<br>
     * on an invoice, and so on.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDDL <br>
     * EnterpriseOne field length:  5 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private Integer ediDetailLinesProcessed = null;

    /**
     * EDI - Successfully Processed<br>
     * <p>
     * An option that indicates whether a record has been successfully<br>
     * processed. Depending on the application, the system updates the EDSP<br>
     * field in a table with one of the following values:<br>
     * <br>
     * 1<br>
     * Successfully processed<br>
     * <br>
     * Blank (or N or 0)<br>
     * Not processed<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDSP <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String ediSuccessfullyProcessed = null;

    /**
     * EDI - Transaction Action<br>
     * <p>
     * Code that identifies what the system should do with a transaction during<br>
     * final processing.  Valid codes are:<br>
     * <br>
     *    A Add a new transaction<br>
     *    D Delete an unprocessed transaction <br>
     * <br>
     * This field controls what happens to an address in the F0101 file when<br>
     * you run the Batch Address Processing program.<br>
     * <br>
     * There are two action code fields on this screen. Note that the action<br>
     * code field under Transaction Information is different from the action<br>
     * code under Batch Control.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDTC <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String ediTransactionAction = null;

    /**
     * EDI - Transaction Type<br>
     * <p>
     * Code that identifies a particular kind of transaction. The originator<br>
     * assigns this code to specify a voucher (V), invoice (I), journal entry<br>
     * (J) and so on.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDTR <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String ediTransactionType = null;

    /**
     * EDI - Batch Number<br>
     * <p>
     * The number that the transmitter assigns to the batch. During batch<br>
     * processing,the system assigns a new batch number to the J.D. Edwards<br>
     * transactions for each control (user) batch number it finds.<br>
     * </p>
     * EnterpriseOne Key Field: true <br>
     * EnterpriseOne Alias: EDBT <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne Next Number: 00 6<br>
     */
    private String ediBatchNumber = null;

    /**
     * Batch File Create G/L Record<br>
     * <p>
     * A flag in the batch file that indicates whether you want the system to<br>
     * create a general ledger record at the time the invoice or voucher is<br>
     * processed. The valid code is 1 to create a G/L record. If you set this<br>
     * flag to 1, you must also enter the object and subsidiary account numbers<br>
     * for the G/L account number.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDGL <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String ediBatchFileCreateGLRecord = null;

    /**
     * User Address Number<br>
     * <p>
     * An address number assigned by the transmitter.  This is used primarily<br>
     * for connecting a new Address Book number on the PC with associated A/R<br>
     * Invoices and A/P Vouchers being sent from the PC.  Once an Address Book<br>
     * number is assigned by the Address Book batch process using next numbers,<br>
     * the address number will be modified in the associated A/R invoices and<br>
     * A/P vouchers in the batch files by using the User Address Number to<br>
     * match transactions to the new address number.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDAN <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne decimal places: 0 <br>
     * EnterpriseOne Next Number: 00 1<br>
     */
    private Integer ediIdDuser = null;
    
    public EDITransaction() {
    }

    public void setEdiUserId(String ediUserId) {
        this.ediUserId = ediUserId;
    }

    public String getEdiUserId() {
        return ediUserId;
    }

    public void setEdiRecordType(String ediRecordType) {
        this.ediRecordType = ediRecordType;
    }

    public String getEdiRecordType() {
        return ediRecordType;
    }

    public void setEdiRecordSequence(Integer ediRecordSequence) {
        this.ediRecordSequence = ediRecordSequence;
    }

    public void setEdiRecordSequence(MathNumeric ediRecordSequence) {
        this.ediRecordSequence = new Integer(ediRecordSequence.intValue());
    }

    public Integer getEdiRecordSequence() {
        return ediRecordSequence;
    }

    public void setEdiTransactionNumber(String ediTransactionNumber) {
        this.ediTransactionNumber = ediTransactionNumber;
    }

    public String getEdiTransactionNumber() {
        return ediTransactionNumber;
    }

    public void setEdiDocumentType(String ediDocumentType) {
        this.ediDocumentType = ediDocumentType;
    }

    public String getEdiDocumentType() {
        return ediDocumentType;
    }

    public void setEdiLineNumber(BigDecimal ediLineNumber) {
        this.ediLineNumber = ediLineNumber;
    }

    public void setEdiLineNumber(MathNumeric ediLineNumber) {
        this.ediLineNumber = ediLineNumber.asBigDecimal();
    }

    public BigDecimal getEdiLineNumber() {
        return ediLineNumber;
    }

    public void setEdiTransactionSetNumber(String ediTransactionSetNumber) {
        this.ediTransactionSetNumber = ediTransactionSetNumber;
    }

    public String getEdiTransactionSetNumber() {
        return ediTransactionSetNumber;
    }

    public void setEdiTranslationFormat(String ediTranslationFormat) {
        this.ediTranslationFormat = ediTranslationFormat;
    }

    public String getEdiTranslationFormat() {
        return ediTranslationFormat;
    }

    public void setEdiTransmissionDate(Calendar ediTransmissionDate) {
        this.ediTransmissionDate = ediTransmissionDate;
    }

    public void setEdiTransmissionDate(Date ediTransmissionDate) {       
        if(ediTransmissionDate != null){
            Calendar effDate = Calendar.getInstance();
            effDate.setTime(ediTransmissionDate);          
          this.ediTransmissionDate = effDate;
      }
    }


    public Calendar getEdiTransmissionDate() {
        return ediTransmissionDate;
    }

    public void setEdiSendReceiveIndicator(String ediSendReceiveIndicator) {
        this.ediSendReceiveIndicator = ediSendReceiveIndicator;
    }

    public String getEdiSendReceiveIndicator() {
        return ediSendReceiveIndicator;
    }

    public void setEdiDetailLinesProcessed(Integer ediDetailLinesProcessed) {
        this.ediDetailLinesProcessed = ediDetailLinesProcessed;
    }

    public void setEdiDetailLinesProcessed(MathNumeric ediDetailLinesProcessed) {
        this.ediDetailLinesProcessed = new Integer(ediDetailLinesProcessed.intValue());
    }

    public Integer getEdiDetailLinesProcessed() {
        return ediDetailLinesProcessed;
    }

    public void setEdiSuccessfullyProcessed(String ediSuccessfullyProcessed) {
        this.ediSuccessfullyProcessed = ediSuccessfullyProcessed;
    }

    public String getEdiSuccessfullyProcessed() {
        return ediSuccessfullyProcessed;
    }

    public void setEdiTransactionAction(String ediTransactionAction) {
        this.ediTransactionAction = ediTransactionAction;
    }

    public String getEdiTransactionAction() {
        return ediTransactionAction;
    }

    public void setEdiTransactionType(String ediTransactionType) {
        this.ediTransactionType = ediTransactionType;
    }

    public String getEdiTransactionType() {
        return ediTransactionType;
    }

    public void setEdiBatchNumber(String ediBatchNumber) {
        this.ediBatchNumber = ediBatchNumber;
    }

    public String getEdiBatchNumber() {
        return ediBatchNumber;
    }

    public void setEdiBatchFileCreateGLRecord(String ediBatchFileCreateGLRecord) {
        this.ediBatchFileCreateGLRecord = ediBatchFileCreateGLRecord;
    }

    public String getEdiBatchFileCreateGLRecord() {
        return ediBatchFileCreateGLRecord;
    }

    public void setEdiIdDuser(Integer ediIdDuser) {
        this.ediIdDuser = ediIdDuser;
    }

    public void setEdiIdDuser(MathNumeric ediIdDuser) {
        this.ediIdDuser = new Integer(ediIdDuser.intValue());
    }

    public Integer getEdiIdDuser() {
        return ediIdDuser;
    }
}
