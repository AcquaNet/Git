package oracle.e1.bssv.J5900002.valueobject;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Date;
import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssvfoundation.util.MathNumeric;


public class InternalInsertBatchJournaleEntryStagingFields extends ValueObject {
 
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
    private String F0911Z1_EDUS = null;

    /**
     * Type Record<br>
     * <p>
     * The identifier used to mark EDI transaction records as header and detail<br>
     * information. This is an EDI function only.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDTY <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: 00/RD <br>
     */
    private String F0911Z1_EDTY = null;

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
    private MathNumeric F0911Z1_EDSQ = null;

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
    private String F0911Z1_EDTN = null;

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
    private String F0911Z1_EDCT = null;

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
    private MathNumeric F0911Z1_EDLN = null;

    /**
     * EDI - Transaction Set Number<br>
     * <p>
     * The qualifier used to identify a specific type of EDI transaction.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EDTS <br>
     * EnterpriseOne field length:  6 <br>
     */
    private String F0911Z1_EDTS = null;

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
    private String F0911Z1_EDFT = null;

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
    private Date F0911Z1_EDDT = null;

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
     * EnterpriseOne User Defined Code: H00/ED <br>
     * EnterpriseOne Default Value: B<br>
     */
    private String F0911Z1_EDER = null;

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
    private MathNumeric F0911Z1_EDDL = null;

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
    private String F0911Z1_EDSP = null;

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
    private String F0911Z1_EDTC = null;

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
    private String F0911Z1_EDTR = null;

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
     * EnterpriseOne Next Number: 00/6 <br>
     */
    private String F0911Z1_EDBT = null;

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
    private String F0911Z1_EDGL = null;

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
     * EnterpriseOne Next Number: 00/1 <br>
     */
    private MathNumeric F0911Z1_EDAN = null;

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
    private String F0911Z1_KCO = null;

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
     * EnterpriseOne User Defined Code: 00/DT <br>
     */
    private String F0911Z1_DCT = null;

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
    private MathNumeric F0911Z1_DOC = null;

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
    private Date F0911Z1_DGJ = null;

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
    private MathNumeric F0911Z1_JELN = null;

    /**
     * Line Extension Code<br>
     * <p>
     * Line Extension Code<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EXTL <br>
     * EnterpriseOne field length:  2 <br>
     */
    private String F0911Z1_EXTL = null;

    /**
     * G/L Posted Code<br>
     * <p>
     * A code the system uses to determine whether a transaction is available<br>
     * for the post process. Valid codes are:<br>
     * <br>
     * Blank<br>
     * Unposted.<br>
     * <br>
     * D<br>
     * Posted.<br>
     * <br>
     * P<br>
     * Posted or posting. Depending on the type of transaction, this code has<br>
     * different meanings. If the code is assigned to an Account Ledger record<br>
     * (F0911), it indicates a posted status. If the code is assigned to any<br>
     * other transaction, it indicates that the system attempted to post the<br>
     * record but failed, due to an error it encountered.   <br>
     * <br>
     * M<br>
     * Model. The transaciton is a model journal entry.<br>
     * <br>
     *     <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: POST <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_POST = null;

    /**
     * Batch Number<br>
     * <p>
     * A number that identifies a group of transactions that the system<br>
     * processes and balances as a unit. When you enter a batch, you can either<br>
     * assign a batch number or let the system assign it using the Next Numbers<br>
     * program.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ICU <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne decimal places: 0 <br>
     * EnterpriseOne Next Number: 00/1 <br>
     */
    private MathNumeric F0911Z1_ICU = null;

    /**
     * Batch Type<br>
     * <p>
     * A code that specifies the system and type of transactions entered in a<br>
     * batch. The system assigns the value when you enter a transaction. Valid<br>
     * values are set up in user defined code table 98/IT. You cannot assign<br>
     * new values. All batch types are hard coded.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ICUT <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 98/IT <br>
     */
    private String F0911Z1_ICUT = null;

    /**
     * Date - Batch (Julian)<br>
     * <p>
     * The date of the batch. If you leave this field blank, the system date is<br>
     * used.<br>
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
     * EnterpriseOne Alias: DICJ <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Date F0911Z1_DICJ = null;

    /**
     * Date - Batch System Date<br>
     * <p>
     * This is the date the batch was entered to the system.<br>
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
     * EnterpriseOne Alias: DSYJ <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Date F0911Z1_DSYJ = null;

    /**
     * Batch Time<br>
     * <p>
     * The system time a batch was created.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TICU <br>
     * EnterpriseOne field length:  6 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_TICU = null;

    /**
     * Company<br>
     * <p>
     * A code that identifies a specific organization, fund, or other reporting<br>
     * entity. The company code must already exist in the Company Constants<br>
     * table (F0010) and must identify a reporting entity that has a complete<br>
     * balance sheet. At this level, you can have intercompany transactions.<br>
     * <br>
     * Note: You can use company 00000 for default values such as dates and<br>
     * automatic accounting instructions. You cannot use company 00000 for<br>
     * transaction entries.<br>
     * <br>
     * &NOTE: Data for Company and Business Unit fields may be entered without concern<br>
     * for special E1 formatting and necessary formatting will take place during processing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CO <br>
     * EnterpriseOne field length:  5 <br>
     */
    private String F0911Z1_CO = null;

    /**
     * Account Number - Input (Mode Unknown)<br>
     * <p>
     * A value that identifies an account in the general ledger. Use one of the<br>
     * following formats to enter account numbers:<br>
     * <br>
     *   o  Standard account number (business unit.object.subsidiary or flex<br>
     * format).<br>
     * <br>
     *   o  Third G/L number (maximum of 25 digits).<br>
     * <br>
     *   o  Account ID number. The number is eight digits long.  <br>
     *  <br>
     *   o  Speed code, which is a two-character code that you concatenate to<br>
     * the AAI item SP. You can then enter the code instead of an account<br>
     * number.<br>
     * <br>
     * The first character of the account number indicates its format. You<br>
     * define the account format in the General Accounting constants. <br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ANI <br>
     * EnterpriseOne field length:  29 <br>
     */
    private String F0911Z1_ANI = null;

    /**
     * Account Mode - G/L<br>
     * <p>
     * A code that indicates which of the three general ledger account numbers<br>
     * is being used for data entry. Valid codes are:<br>
     * <br>
     *    1   The short account ID number<br>
     * <br>
     *    2   The standard long account number<br>
     * <br>
     *    3   The long (unstructured, 24-byte) account number<br>
     * <br>
     *    7   The first character of the account number indicates the format of<br>
     * the account number You can also define special characters in the<br>
     * Accounting Constants file (F0009) to facilitate data entry. For example:<br>
     * <br>
     *    /       For the unstructured account number<br>
     * <br>
     *    *       For the short account ID number<br>
     * <br>
     *    blank For the business unit.object.subsidiary number For example, if<br>
     * the account mode is 7 and the first character of the account number is<br>
     * *, the account number was entered as the short account ID number.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: AM <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: H09/AM <br>
     * EnterpriseOne Default Value: 1<br>
     */
    private String F0911Z1_AM = null;

    /**
     * Account ID<br>
     * <p>
     * A number that the system assigns to each general ledger account in the<br>
     * Account Master table (F0901) to uniquely identify it. <br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: AID <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne Next Number: 09/1 <br>
     */
    private String F0911Z1_AID = null;

    /**
     * Business Unit<br>
     * <p>
     * An alphanumeric code that identifies a separate entity within a business<br>
     * for which you want to track costs. For example, a business unit might be<br>
     * a warehouse location, job, project, work center, branch, or plant.<br>
     * <br>
     * You can assign a business unit to a document, entity, or person for<br>
     * purposes of responsibility reporting. For example, the system provides<br>
     * reports of open accounts payable and accounts receivable by business<br>
     * unit to track equipment by responsible department.<br>
     * <br>
     * Business unit security might prevent you from viewing information about<br>
     * business units for which you have no authority.<br>
     * <br>
     * &NOTE: Data for Company and Business Unit fields may be entered without concern<br>
     * for special E1 formatting and necessary formatting will take place during processing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: MCU <br>
     * EnterpriseOne field length:  12 <br>
     */
    private String F0911Z1_MCU = null;

    /**
     * Object Account<br>
     * <p>
     * The portion of a general ledger account that refers to the division of<br>
     * the Cost Code (for example, labor, materials, and equipment) into<br>
     * subcategories. For example, you can divide the Cost Code for labor into<br>
     * regular time, premium time, and burden.<br>
     * <br>
     * Note: If you use a flexible chart of accounts and the object account is<br>
     * set to 6 digits, J.D. Edwards recommends that you use all 6 digits. For<br>
     * example, entering 000456 is not the same as entering 456 because if you<br>
     * enter 456 the system enters three blank spaces to fill a 6-digit object.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: OBJ <br>
     * EnterpriseOne field length:  6 <br>
     */
    private String F0911Z1_OBJ = null;

    /**
     * Subsidiary<br>
     * <p>
     * A subset of an object account. Subsidiary accounts include detailed<br>
     * records of the accounting activity for an object account.<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: SUB <br>
     * EnterpriseOne field length:  8 <br>
     */
    private String F0911Z1_SUB = null;

    /**
     * Subledger - G/L<br>
     * <p>
     * A code that identifies a detailed, auxiliary account within a general<br>
     * ledger account. A subledger can be an equipment item number or an<br>
     * address book number. If you enter a subledger, you must also specify the<br>
     * subledger type.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: SBL <br>
     * EnterpriseOne field length:  8 <br>
     */
    private String F0911Z1_SBL = null;

    /**
     * Subledger Type<br>
     * <p>
     * A user defined code (00/ST) that is used with the Subledger field to<br>
     * identify the subledger type and how the system performs subledger<br>
     * editing. On the User Defined Codes form, the second line of the<br>
     * description controls how the system performs editing. This is either<br>
     * hard-coded or user defined. Valid values include:<br>
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
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: SBLT <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: 00/ST <br>
     */
    private String F0911Z1_SBLT = null;

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
     * EnterpriseOne User Defined Code: 09/LT <br>
     * EnterpriseOne Default Value: AA<br>
     */
    private String F0911Z1_LT = null;

    /**
     * Period Number - General Ledger<br>
     * <p>
     * A number indicating the current accounting period.  This number, used in<br>
     * conjunction with the Company Constants table (F0010) and the General<br>
     * Constants table (F0009), allows the user to define up to 14 accounting<br>
     * periods. See General Ledger Date.  The current period number is used to<br>
     * determine posted before and posted after cut off warning messages.  It<br>
     * is also used as the default accounting period in the preparation of<br>
     * financial reports.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PN <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_PN = null;

    /**
     * Century<br>
     * <p>
     * The calendar century associated with the year. Enter is the first two<br>
     * digits of<br>
     * the year. For example, 19 indicates any year beginning with 19 (1998,<br>
     * 1999),<br>
     * 20 indicates any year beginning with 20 (2000, 2001), and so on.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CTRY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     * EnterpriseOne Edit Rule:RANGE <br>
     */
    private MathNumeric F0911Z1_CTRY = null;

    /**
     * Fiscal Year<br>
     * <p>
     * A number that identifies the fiscal year. Generally, you can either<br>
     * enter a number in this field or leave it blank to indicate the current<br>
     * fiscal year (as defined on the Company Setup form).<br>
     * <br>
     * Specify the year at the end of the first period rather than the year at<br>
     * the end of the fiscal period. For example, a fiscal year begins October<br>
     * 1, 1998 and ends September 30, 1999. The end of the first period is<br>
     * October 31, 1998. Specify the year 98 rather than 99.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: FY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_FY = null;

    /**
     * Fiscal Quarter (Obsolete)<br>
     * <p>
     * A data field reserved for future use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: FQ <br>
     * EnterpriseOne field length:  4 <br>
     * EnterpriseOne User Defined Code: H09/FQ <br>
     */
    private String F0911Z1_FQ = null;

    /**
     * Currency Code - From<br>
     * <p>
     * A code that identifies the currency of a transaction. <br>
     * <br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CRCD <br>
     * EnterpriseOne field length:  3 <br>
     */
    private String F0911Z1_CRCD = null;

    /**
     * Currency Conversion Rate - Spot Rate<br>
     * <p>
     * A number (exchange rate) that a foreign currency amount is multiplied by<br>
     * to calculate a domestic currency amount. <br>
     * The number in this field can have a maximum of seven decimal positions.<br>
     * If more are entered, the system adjusts to the nearest seven decimal<br>
     * positions. <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CRR <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 7 <br>
     */
    private MathNumeric F0911Z1_CRR = null;

    /**
     * Historical Currency Conversion Rate<br>
     * <p>
     * The exchange rate entered for a journal entry. During multiple currency<br>
     * processing, the system uses this rate for the associated journal entry<br>
     * instead of the rate in the Currency Exchange Rates table (F0015). This<br>
     * rate can be the average or historical rate.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: HCRR <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 7 <br>
     */
    private MathNumeric F0911Z1_HCRR = null;

    /**
     * Historical Date - Julian<br>
     * <p>
     * This is a historical date which is used when processing Dual Currency.<br>
     * This date is used to retrieve rates from the Daily Transaction File<br>
     * (F0015) instead of the general ledger date of the transaction. <br>
     * Transactions that carry an historical date include voids of vouchers,<br>
     * invoices, or payments.<br>
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
     * EnterpriseOne Alias: HDGJ <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Date F0911Z1_HDGJ = null;

    /**
     * Amount<br>
     * <p>
     * A number that identifies the amount that the system will add to the<br>
     * account balance of the associated account number. Enter credits with a<br>
     * minus sign (-) either before or after the amount. <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: AA <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private MathNumeric F0911Z1_AA = null;

    /**
     * Units<br>
     * <p>
     * The quantity of something that is identified by a unit of measure. For<br>
     * example, it can be the number of barrels, boxes, cubic yards, gallons,<br>
     * hours, and so on.<br>
     * &NOTE: Data for Company and Business Unit fields may be entered without concern<br>
     * for special E1 formatting and necessary formatting will take place during processing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: U <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private MathNumeric F0911Z1_U = null;

    /**
     * Unit of Measure<br>
     * <p>
     * A user defined code (00/UM) that identifies the unit of measurement for<br>
     * an amount or quantity. For example, it can represent a barrel, box,<br>
     * cubic meter, liter, hour, and so on.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: UM <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 00/UM <br>
     */
    private String F0911Z1_UM = null;

    /**
     * G/L Offset<br>
     * <p>
     * A code that determines the trade account that the system uses as the<br>
     * offset when you post invoices or vouchers. The system concatenates the<br>
     * value that you enter to the AAI item RC (for Accounts Receivable) or PC<br>
     * (for Accounts Payable) to locate the trade account. For example, if you<br>
     * enter TRAD, the system searches for the AAI item RCTRAD (for<br>
     * receivables) or PCTRAD (for payables). <br>
     * <br>
     * You can assign up to four alphanumeric characters to represent the G/L<br>
     * offset or you can assign the three-character currency code (if you enter<br>
     * transactions in a multicurrency environment). You must, however, set up<br>
     * the corresponding AAI item for the system to use; otherwise, the system<br>
     * ignores the G/L offset and uses the account that is set up for PC or RC<br>
     * for the company specified. <br>
     * <br>
     * If you set up a default value in the G/L Offset field of the customer or<br>
     * supplier record, the system uses the value during transaction entry<br>
     * unless you override it. <br>
     * <br>
     * Note: Do not use code 9999. It is reserved for the post program and<br>
     * indicates that offsets should not be created.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: GLC <br>
     * EnterpriseOne field length:  4 <br>
     */
    private String F0911Z1_GLC = null;

    /**
     * Reverse or Void (R/V)<br>
     * <p>
     * An option that specifies whether the system automatically creates<br>
     * reversing entries for a transaction. If you turn on this option, the<br>
     * system creates reversing entries when you post the original transaction.<br>
     *  Based on the setting of the "Use End of Period Reversal Date" constant<br>
     * in the General Accounting Constants (P0000) application,  either the<br>
     * first day or the last day of the next period will be used for the G/L<br>
     * date.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: RE <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: H09/RE <br>
     */
    private String F0911Z1_RE = null;

    /**
     * Name - Alpha Explanation<br>
     * <p>
     * A description, remark, explanation, name, or address.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EXA <br>
     * EnterpriseOne field length:  30 <br>
     */
    private String F0911Z1_EXA = null;

    /**
     * Name - Remark Explanation<br>
     * <p>
     * A name or remark that describes the purpose for using an account or<br>
     * conveys any other information that the user wants about the transaction.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EXR <br>
     * EnterpriseOne field length:  30 <br>
     */
    private String F0911Z1_EXR = null;

    /**
     * Reference 1 - JE, Voucher, Invoice, etc.<br>
     * <p>
     * A number that provides an audit trail for specific transactions, such as<br>
     * a payment number for payment processing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: R1 <br>
     * EnterpriseOne field length:  8 <br>
     */
    private String F0911Z1_R1 = null;

    /**
     * Reference 2<br>
     * <p>
     * A number that provides an audit trail for specific transactions, such as<br>
     * an asset, supplier number, or document number.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: R2 <br>
     * EnterpriseOne field length:  8 <br>
     */
    private String F0911Z1_R2 = null;

    /**
     * Reference 3 - Account Reconciliation<br>
     * <p>
     * Number that provides an audit trail for specific transactions.  Used<br>
     * primarily in the G/L Account Reconciliation program (P09131).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: R3 <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne Next Number: 09/6 <br>
     */
    private String F0911Z1_R3 = null;

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
    private String F0911Z1_SFX = null;

    /**
     * Document - Original<br>
     * <p>
     * A number that is used in conjunction with the values in the ODCT and<br>
     * OKCO fields to identify a transaction.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ODOC <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_ODOC = null;

    /**
     * Document Type - Original<br>
     * <p>
     * A user defined code (system 00, type DT) that identifies the origin and<br>
     * purpose of the original document.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ODCT <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 00/DT <br>
     */
    private String F0911Z1_ODCT = null;

    /**
     * Document Pay Item - Original<br>
     * <p>
     * The number that identifies a specific pay item associated with the<br>
     * document.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: OSFX <br>
     * EnterpriseOne field length:  3 <br>
     */
    private String F0911Z1_OSFX = null;

    /**
     * Document Company (Purchase Order)<br>
     * <p>
     * For purchase orders, a code that differentiates a record from another<br>
     * with the same document number, document type, and G/L date. <br>
     * <br>
     * If you are using the Next Numbers by Company/Fiscal Year feature of the<br>
     * software, the Next Numbers program (X0010) uses the document company to<br>
     * retrieve the next number. If two or more original documents have the<br>
     * same document number and type, you can use the document company to<br>
     * locate the correct record.<br>
     * &NOTE: Data for Company and Business Unit fields may be entered without concern<br>
     * for special E1 formatting and necessary formatting will take place during processing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PKCO <br>
     * EnterpriseOne field length:  5 <br>
     */
    private String F0911Z1_PKCO = null;

    /**
     * Document Company (Original Order)<br>
     * <p>
     * A number that is used in conjunction with the values in the ODOC and<br>
     * ODCT fields to identify a transaction.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: OKCO <br>
     * EnterpriseOne field length:  5 <br>
     */
    private String F0911Z1_OKCO = null;

    /**
     * Document Type - Purchase Order<br>
     * <p>
     * A value that is hard-coded in the originating programs and passed to the<br>
     * Accounts Payable system.<br>
     * <br>
     * In Accounts Receivable, the document type on the purchase order issued<br>
     * by the customer can be entered directly into the Accounts Receivable<br>
     * Ledger (F03B11)<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PDCT <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 00/DT <br>
     */
    private String F0911Z1_PDCT = null;

    /**
     * Address Number<br>
     * <p>
     * A number that identifies an entry in the Address Book system, such as<br>
     * employee, applicant, participant, customer, supplier, tenant, or<br>
     * location. <br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: AN8 <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne decimal places: 0 <br>
     * EnterpriseOne Next Number: 01/1 <br>
     */
    private MathNumeric F0911Z1_AN8 = null;

    /**
     * Payment Number<br>
     * <p>
     * The number of the payment. It is assumed to be numeric and 8 digits or<br>
     * less.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CN <br>
     * EnterpriseOne field length:  8 <br>
     */
    private String F0911Z1_CN = null;

    /**
     * Date - Check - Julian<br>
     * <p>
     * The check or item date. This might be the accounts payable check date,<br>
     * the accounts receivable check receipt date, and so on.<br>
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
     * EnterpriseOne Alias: DKJ <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Date F0911Z1_DKJ = null;

    /**
     * Date - Check Cleared<br>
     * <p>
     * The date the check was debited to the bank account. This will be updated<br>
     * during the tape bank reconciliation.<br>
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
     * EnterpriseOne Alias: DKC <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Date F0911Z1_DKC = null;

    /**
     * Serial Number<br>
     * <p>
     * A 25-character alphanumeric number that you can use as an alternate<br>
     * asset identification number. You might use this number to track assets<br>
     * by the manufacturer's serial number. You are not required to use a<br>
     * serial number to identify an asset. Every serial number that you enter<br>
     * must be unique.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ASID <br>
     * EnterpriseOne field length:  25 <br>
     */
    private String F0911Z1_ASID = null;

    /**
     * Batch Rear End Posted Code<br>
     * <p>
     * The valid post codes for fixed asset transactions are as follows:<br>
     * <br>
     * Blank<br>
     * Unposted. Transaction has not yet been posted to the Asset Account<br>
     * Balances File table (F1202).<br>
     * <br>
     * P<br>
     * Pass. Transaction does not fall within the FX range of accounts as set<br>
     * up in automatic accounting instructions (AAIs) and will not post to<br>
     * fixed assets. You can manually update this field to P through the Revise<br>
     * Unposted Entries program (P12102). Use P in this field when the account<br>
     * number is within the fixed asset range of accounts, but you do not want<br>
     * the transaction to post to fixed assets. You can change this field from<br>
     * blank to P or from P to blank.<br>
     * <br>
     *  *<br>
     * Posted. Transaction has been posted to the Item Balances table. You<br>
     * cannot change this value.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: BRE <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: 09/BR <br>
     */
    private String F0911Z1_BRE = null;

    /**
     * Reconciled Code<br>
     * <p>
     * A code that indicates whether a transaction in the Account Ledger table<br>
     * (F0911) is reconciled. The codes are defined in User Defined Code table<br>
     * under system 09 and code RC. A blank character is defined to be the<br>
     * un-reconciled character.  To set up a valid reconciled code make sure<br>
     * the following is done in the User Defined Code table:<br>
     * <br>
     *    1.   The special handling code in fold area of the UDC screen must<br>
     * contain a 01.<br>
     * <br>
     *    2.   The reconciled code must be only one character long and can be a<br>
     * numeric or alpha character.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: RCND <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: 09/RC <br>
     */
    private String F0911Z1_RCND = null;

    /**
     * Summarized Code<br>
     * <p>
     * A code that indicates whether the system has summarized this record.<br>
     * When you run the purge program, the system purges summarized records.<br>
     * Valid values are:<br>
     * <br>
     *    Y        Yes, this record has been summarized<br>
     * <br>
     *    blank No, this record has not been summarized <br>
     * <br>
     * NOTE: When you run the summarize program, it creates records with a BF<br>
     * (Balance Forward) ledger type. When you run the purge program, these<br>
     * records are not deleted.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: SUMM <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_SUMM = null;

    /**
     * Purge Code<br>
     * <p>
     * A Y code designates that this record has been purged to the Purge File<br>
     * but could not actually be deleted for one reason or another (i.e.,<br>
     * reconcilable account).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PRGE <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PRGE = null;

    /**
     * Flag for 1099<br>
     * <p>
     * A code that indicates whether to include the pay item in 1099 processing<br>
     * (A/P Ledger method only).<br>
     * <br>
     * Valid values are:<br>
     * <br>
     * Blank <br>
     * Do not include in 1099 processing.<br>
     * <br>
     * 1       <br>
     * Include in 1099 processing.<br>
     * <br>
     * If you use the G/L method of 1099 processing, do not flag vouchers with<br>
     * distribution entries to G/L accounts that use Autmatic Accounting<br>
     * Instruction (AAI) ranges for PX; these vouchers will be included<br>
     * automatically in the G/L method of 1099 processing.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TNN <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne Edit Rule:VALUE <br>
     */
    private String F0911Z1_TNN = null;

    /**
     * G/L Posting Code - Alternate 1<br>
     * <p>
     * The alternate G/L posting codes are used for transactional posting other<br>
     * than the normal G/L posting.  Examples include service date ledger<br>
     * posting, tax ledger posting, alternate currency ledger posting, and so<br>
     * forth.<br>
     * <br>
     * This post code is used as a flag to denote that a corresponding record<br>
     * with the same document number and type and G/L date but with a J.E. Line<br>
     * Extension of "AM" has been written to the Account Ledger file (F0911).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT1 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT1 = null;

    /**
     * G/L Posting Code - Alternate 2<br>
     * <p>
     * The alternate G/L posting codes are used for transactional posting other<br>
     * than the normal G/L posting.  Examples include service date ledger<br>
     * posting, tax ledger posting, alternate currency ledger posting, and so<br>
     * forth.<br>
     * <br>
     * This post code is used in conjunction with 52 period accounting. If this<br>
     * post code is blank for any particular transaction record, then it will<br>
     * be considered for posting to the 52 period Account Balance File (F0902B)<br>
     * during the G/L Post procedure. This post code will then be updated to a<br>
     * "P". If a repost to the F0902B file is ever required, only those records<br>
     * whose alternate 2 post code status is a "P" will be reposted.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT2 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT2 = null;

    /**
     * G/L Posting Code - Alternate 3<br>
     * <p>
     * The Alternate G/L Posting Codes are used for transactional posting other<br>
     * than the normal G/L posting.<br>
     * <br>
     * This hold code is used in conjunction with the F/A system. Only those<br>
     * records with a "batch rear end" value of blank, G/L post code of P and<br>
     * hold code value of blank will be selected in the Post Unposted F/A<br>
     * Entries program.<br>
     * <br>
     * If there are records that have been posted to G/L, but should not be<br>
     * posted to F/A, you can update this hold code to any character other than<br>
     * X or *. The X code is reserved for F/A Time Entry. The * code is<br>
     * reserved for selection of all hold codes in Revise Unposted Entries<br>
     * (P12102).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT3 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT3 = null;

    /**
     * G/L Posting Code - Alternate 4<br>
     * <p>
     * The alternate G/L posting codes are used for transactional posting other<br>
     * than the normal G/L posting.  Examples include service date ledger<br>
     * posting, tax ledger posting, alternate currency ledger posting, etc.<br>
     * This code is used to designate job cost projections and/or commitment<br>
     * relief updates have been completed.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT4 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT4 = null;

    /**
     * G/L Posting Code - Alternate 5<br>
     * <p>
     * This code is used to distinguish entries made through alternate journal<br>
     * entry program.  The valid codes are as follows:<br>
     * <br>
     *    M     Multicurrency Journal Entry T, V, and O. Tax Journal Entry Changes<br>
     * may only be made through these alternate journal         entry programs.<br>
     * <br>
     * This code is also used to distinguish entries made through alternate<br>
     * voucher entry programs, The valid codes are as follows:<br>
     * <br>
     *    C     Multi-Company Voucher Entry<br>
     * <br>
     *    S     Sub-Contract Voucher Entry<br>
     * <br>
     *    P     Invoice Logging Voucher Entry Changes for codes C and S may only<br>
     * be made through these alternate Voucher Entry    entry programs.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT5 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT5 = null;

    /**
     * G/L Posting Code - Cash Basis Acct<br>
     * <p>
     * The alternate G/L posting codes are used for transactional posting other<br>
     * than the normal G/L posting.  Examples include service date ledger<br>
     * posting, tax ledger posting, alternate currency ledger posting, and so<br>
     * forth.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT6 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT6 = null;

    /**
     * Commitment Relief Flag<br>
     * <p>
     * This field determines how the journal entry should impact a commitment<br>
     * made in the Purchasing and/or Contract Management systems.  Possible<br>
     * values are:<br>
     * <br>
     *    blank         The commitment should be relieved.<br>
     * <br>
     *    N             The commitment relief has already been performed and should not<br>
     * be performed for this journal entry.<br>
     * <br>
     *    X             The commitment should be relieved; however if the receipt/payment<br>
     * is for a different exchange rate than the                        purchase order or contract,<br>
     * omit any exchange rate conversion.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT7 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT7 = null;

    /**
     * Billing Control<br>
     * <p>
     * Billing Control for Service Billing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT8 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT8 = null;

    /**
     * Currency Update<br>
     * <p>
     * A value that is used in detailed currency processing to indicate the<br>
     * status of records in the Account Ledger table (F0911). Valid values are:<br>
     * <br>
     * Blank<br>
     * The record has not been processed by detailed currency restatement.<br>
     * <br>
     * P<br>
     * The record has been processed by detailed currency restatement, and the<br>
     * corresponding XA ledger record (and optionally, the YA and ZA ledger<br>
     * records) has been created.<br>
     * <br>
     * N<br>
     * The record has been processed by detailed currency restatement, but<br>
     * ignored because the account is not within the account ranges for AAI<br>
     * item CRxx.  The program does not create corresponding record in the XA<br>
     * ledger, but instead updates the AA ledger with N (not applicable).<br>
     * <br>
     * R   <br>
     * Automatic reversing entry created by General ledger post program. The<br>
     * record will not be processed by detailed currency restatement.<br>
     * <br>
     * Y<br>
     * The original journal entry was flagged to not create an XA ledger record<br>
     * when processed by detailed currency restatement. <br>
     * <br>
     * X<br>
     * The original journal entry was flagged to not create an XA ledger record<br>
     * when processed by detailed currency restatement, and it was subsequently<br>
     * voided.<br>
     * <br>
     * <br>
     * <br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT9 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT9 = null;

    /**
     * G/L Posting Code - Alternate 0<br>
     * <p>
     * This posting flag is reserved for future use in the area of labor<br>
     * costing in the AEC product group.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT0 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALT0 = null;

    /**
     * G/L Posting Code - Alternate T<br>
     * <p>
     * G/L Posting Code - Alternative T - Reserved for future use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTT <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALTT = null;

    /**
     * G/L Posting Code - Alternate U<br>
     * <p>
     * G/L Posting Code - Alternative U - Reserved for future use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTU <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALTU = null;

    /**
     * Stocked Inventory Commitment<br>
     * <p>
     * A code that determines how the journal entry should impact a commitment<br>
     * made for a stocked item.  The valid values are:<br>
     * <br>
     *     Blank        The record will not be used to relieve a stocked item<br>
     * commitment<br>
     * <br>
     *     1            The record will be used to relieve a stocked item commitment<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTV <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne Default Value: 0<br>
     */
    private String F0911Z1_ALTV = null;

    /**
     * G/L Posting Code - Alternate W<br>
     * <p>
     * A valuse of  "W"  in this field  indicates the journal entry was created<br>
     * from a voucher under the Pay When Paid schema. This value is used by<br>
     * Contract Service Billing processes to identify the transactions that<br>
     * have to be processed under pay when paid process.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTW <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALTW = null;

    /**
     * Consumption Tax Cross Reference<br>
     * <p>
     * Field used to denote tax.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTX <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALTX = null;

    /**
     * G/L Posting Code - Account Debit Balance<br>
     * <p>
     * A value that is used by the Process Account Debit Balances program<br>
     * (R0902A) to indicate the status of records in the Account Ledger table<br>
     * (F0911). Valid values are:<br>
     * <br>
     * Blank<br>
     * The record has not been processed by the Process Account Debit Balances<br>
     * program.<br>
     * <br>
     * 1<br>
     * The record has been processed by the Process Account Debit Balances<br>
     * program.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTZ <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ALTZ = null;

    /**
     * Delete Not Allowed<br>
     * <p>
     * Any non-blank value in this field indicates that the Account Ledger<br>
     * record cannot be deleted even if it has not yet been posted.  This is<br>
     * typically required in cases where integrity between Account Ledger<br>
     * records and associated data in another system must be upheld.<br>
     * <br>
     * The following codes represent valid values for this field:<br>
     * <br>
     *    blank Deletion is allowed (unless the record is posted or otherwise<br>
     * ineligible for deletion)<br>
     *    J Deletion is not allowed - Job Cost Profit Recognition<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DLNA <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne Edit Rule:VALUE <br>
     */
    private String F0911Z1_DLNA = null;

    /**
     * Client Free Form - Alternate 1<br>
     * <p>
     * Free form for client use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CFF1 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_CFF1 = null;

    /**
     * Client Free Form - Alternate 2<br>
     * <p>
     * Free form for client use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CFF2 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_CFF2 = null;

    /**
     * Lease Cost Ledger Posted Code<br>
     * <p>
     * An "*" in this field indicates that this transaction has been posted to<br>
     * Lease Cost Ledger File (F2111).  Once there is an asterisk in this<br>
     * field, the record cannot be changed.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ASM <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ASM = null;

    /**
     * Bill Code<br>
     * <p>
     * A user defined code (98/BC) that identifies the billing status for the<br>
     * Joint Interest Billing (JIB) system. Valid codes include:<br>
     * <br>
     *    blank Blank (the default value) indicates transactions that are<br>
     * billable if a valid Division of Interest (DOI) exists for the business<br>
     * unit, and if the object account is within the billable range of<br>
     * accounts.
    <br>
     * <br>
     *    N Specifies a transaction is not billable regardless of the business<br>
     * unit, DOI, or account range.<br>
     * <br>
     *    D Direct charges the owner specified in the subledger field at 100%.<br>
     * <br>
     *    H Holds a billable transaction until the user wants to manually<br>
     * release the transaction by changing the Bill Code.<br>
     * <br>
     *    M Manual DOI code assignment requires a valid DOI code to be input.<br>
     * <br>
     *       During JIB Cost Allocations and Billing, this code is changed<br>
     * based on the processing status. G/L file purging uses this code.<br>
     * <br>
     * NOTE: Direct charges are only allowed for entity type O (outsider).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: BC <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: 98/BC <br>
     */
    private String F0911Z1_BC = null;

    /**
     * Supplier Invoice Number<br>
     * <p>
     * The supplier's invoice number that is used for voucher entry. Voucher<br>
     * entry allows only one invoice per voucher number. If multiple invoice<br>
     * numbers exist on a voucher, you must set them up as multiple vouchers or<br>
     * combine the invoices and enter them as one voucher. Depending on how you<br>
     * have your accounts payable constants set, the system can do one of the<br>
     * following:<br>
     * <br>
     *   o  Accept a duplicate invoice number without warning or error<br>
     * <br>
     *   o  Generate a warning message in which the duplicate invoice number<br>
     * can still be accepted<br>
     * <br>
     *   o  Generate an error message<br>
     * <br>
     * Blank values are treated in the same manner as any other invoice number.<br>
     * Two blank invoice numbers are treated as duplicates.<br>
     * <br>
     * To test for duplicate invoice numbers that might have been entered in<br>
     * error, run the Suspected Duplicate Payments report (R04601).<br>
     * <br>
     * Note: The duplicate invoice number validation is not run for vouchers<br>
     * with document type NO. These vouchers are created by the Generate<br>
     * Reimbursements program (R03B610).<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: VINV <br>
     * EnterpriseOne field length:  25 <br>
     */
    private String F0911Z1_VINV = null;

    /**
     * Date - Invoice<br>
     * <p>
     * The date the invoice was printed. The system updates this date when you<br>
     * run the invoice print program in the Sales Order Management system.<br>
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
     * EnterpriseOne Alias: IVD <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Date F0911Z1_IVD = null;

    /**
     * Categories - Work Order 01<br>
     * <p>
     * A user defined code (00/W1) that indicates the current stage or phase of<br>
     * development for a work order. You can assign a work order to only one<br>
     * phase code at a time.<br>
     * <br>
     * Note: Certain forms contain a processing option that allows you to enter<br>
     * a default value for this field. If you enter a default value on a form<br>
     * for which you have set this processing option, the system displays the<br>
     * value in the appropriate fields on any work orders that you create. The<br>
     * system also displays the value on the Project Setup form. You can either<br>
     * accept or override the default value.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: WR01 <br>
     * EnterpriseOne field length:  4 <br>
     * EnterpriseOne User Defined Code: 00/W1 <br>
     */
    private String F0911Z1_WR01 = null;

    /**
     * Purchase Order<br>
     * <p>
     * A document that authorizes the delivery of specified merchandise or the<br>
     * rendering of certain services.<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PO <br>
     * EnterpriseOne field length:  8 <br>
     */
    private String F0911Z1_PO = null;

    /**
     * Purchase Order Suffix<br>
     * <p>
     * The purchase order suffix is used to distinguish between multiple<br>
     * occurrences of a single purchase order.  It is frequently used in the<br>
     * case of blanket purchase orders.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PSFX <br>
     * EnterpriseOne field length:  3 <br>
     */
    private String F0911Z1_PSFX = null;

    /**
     * Order Type<br>
     * <p>
     * A user defined code (00/DT) that identifies the type of document. This<br>
     * code also indicates the origin of the transaction. J.D. Edwards has<br>
     * reserved document type codes for vouchers, invoices, receipts, and time<br>
     * sheets, which create automatic offset entries during the post program.<br>
     * (These entries are not self-balancing when you originally enter them.)<br>
     * <br>
     * The following document types are defined by J.D. Edwards and should not<br>
     * be changed:<br>
     * <br>
     * P<br>
     * Accounts Payable documents  <br>
     * <br>
     * R<br>
     * Accounts Receivable documents  <br>
     * <br>
     * T<br>
     * Payroll documents  <br>
     * <br>
     * I<br>
     * Inventory documents  <br>
     * <br>
     * O<br>
     * Purchase Order Processing documents  <br>
     * <br>
     * J<br>
     * General Accounting/Joint Interest Billing documents  <br>
     * <br>
     * S<br>
     * Sales Order Processing documents<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DCTO <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 00/DT <br>
     */
    private String F0911Z1_DCTO = null;

    /**
     * Line Number<br>
     * <p>
     * A number that identifies multiple occurrences, such as line numbers on a<br>
     * purchase order or other document. Generally, the system assigns this<br>
     * number,but in some cases you can override it.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: LNID <br>
     * EnterpriseOne field length:  6 <br>
     * EnterpriseOne decimal places: 3 <br>
     */
    private MathNumeric F0911Z1_LNID = null;

    /**
     * Fiscal Year - Weekly<br>
     * <p>
     * Fiscal year - weekly.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: WY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_WY = null;

    /**
     * Fiscal Period - Weekly<br>
     * <p>
     * Fiscal period - weekly.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: WN <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_WN = null;

    /**
     * Closed Item - As Of Processing<br>
     * <p>
     * A code that indicates that an item is totally closed on the As Of date<br>
     * and will be ignored for future rebuilds of the As Of table.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: FNLP <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_FNLP = null;

    /**
     * Sequence Number - Operations<br>
     * <p>
     * A number used to indicate an order of succession.<br>
     * <br>
     * In routing instructions, a number that sequences the fabrication or<br>
     * assembly steps in the manufacture of an item. You can track costs and<br>
     * charge time by operation.<br>
     * <br>
     * In bills of material, a number that designates the routing step in the<br>
     * fabrication or assembly process that requires a specified component<br>
     * part. You define the operation sequence after you create the routing<br>
     * instructions for the item. The Shop Floor Management system uses this<br>
     * number in the backflush/preflush by operation process.<br>
     * <br>
     * In engineering change orders, a number that sequences the assembly steps<br>
     * for the engineering change.<br>
     * <br>
     * For repetitive manufacturing, a number that identifies the sequence in<br>
     * which an item is scheduled to be produced.<br>
     * <br>
     * Skip To fields allow you to enter an operation sequence that you want to<br>
     * begin the display of information.<br>
     * <br>
     * You can use decimals to add steps between existing steps. For example,<br>
     * use 12.5 to add a step between steps 12 and 13.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: OPSQ <br>
     * EnterpriseOne field length:  5 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private MathNumeric F0911Z1_OPSQ = null;

    /**
     * Job Type (Craft) Code<br>
     * <p>
     * A user defined code (06|G) that defines the jobs within your<br>
     * organization. You can associate pay and benefit information with a job<br>
     * type and apply that information to the employees who are linked to that<br>
     * job type.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: JBCD <br>
     * EnterpriseOne field length:  6 <br>
     * EnterpriseOne User Defined Code: 06/G <br>
     */
    private String F0911Z1_JBCD = null;

    /**
     * Job Step<br>
     * <p>
     * A user defined code (06|GS) that designates a specific level within a<br>
     * particular job type. The system uses this code in conjunction with job<br>
     * type to determine pay rates by job in the Pay Rates table.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: JBST <br>
     * EnterpriseOne field length:  4 <br>
     * EnterpriseOne User Defined Code: 06/GS <br>
     */
    private String F0911Z1_JBST = null;

    /**
     * Business Unit - Home<br>
     * <p>
     * The number of the business unit in which the employee generally resides.<br>
     * &NOTE: Data for Company and Business Unit fields may be entered without concern<br>
     * for special E1 formatting and necessary formatting will take place during processing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: HMCU <br>
     * EnterpriseOne field length:  12 <br>
     * EnterpriseOne Edit Rule:SERVER <br>
     */
    private String F0911Z1_HMCU = null;

    /**
     * DOI Sub<br>
     * <p>
     * A number that identifies a specific Division of Interest (DOI) for a<br>
     * business unit. You can have up to 99 revenue and 99 billing DOIs per<br>
     * business unit.<br>
     * <br>
     * Multiple DOIs can occur for a variety of reasons. For billing, multiple<br>
     * DOIs let you bill different account ranges or change ownership as of a<br>
     * specific date. For revenue distribution, multiple DOIs can be used for<br>
     * different products, purchase contracts, ownership changes, or owner<br>
     * certification changes as of a particular date. For land, multiple DOIs<br>
     * can be used to identify different ownerships for different tracts on a<br>
     * lease or BPO/APO (Before Payout/After Payout) working interest changes.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DOI <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DOI = null;

    /**
     * Outsider Lease or Well ID<br>
     * <p>
     * An alternate lease or well number as assigned by an outsider<br>
     * (governmental body or a purchaser). This number will often correspond to<br>
     * a product and/or purchaser combination for a particular sale. This field<br>
     * is also used as a generic cross reference to the lease master table for<br>
     * such items as unit numbers, contract numbers, well numbers, old lease<br>
     * numbers and so on.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALID <br>
     * EnterpriseOne field length:  25 <br>
     * EnterpriseOne Edit Rule:NE <br>
     */
    private String F0911Z1_ALID = null;

    /**
     * ID Type<br>
     * <p>
     * A code to indicate what type of alternate lease/well number is being<br>
     * used.<br>
     * <br>
     * Examples include API well numbers, purchaser lease/well numbers, unit to<br>
     * lease cross references, old to new lease number cross references, and so<br>
     * forth.<br>
     * <br>
     * Certain codes have a hard coded meaning as follows:<br>
     * <br>
     *    P Represents a purchaser's alternate property number and is used in<br>
     * editing an alternate number for revenue check stub entry.<br>
     *    F Represents a purchaser's alternate facility number and is used in<br>
     * run ticket entry and gas volume statement entry.<br>
     *    C Represents alternate business units for one property. This occurs<br>
     * when multiple AFEs are set up for one property.<br>
     *    U Used for cross referencing multiple leases to multiple units.<br>
     *    W000 Used for cross-referencing wells to leases or lease tracts.<br>
     *    CC Used in contract cross-referencing (to leases, wells, and so<br>
     * forth.)<br>
     *    CL Used in contract cross-referencing (to leases, wells, and so<br>
     * forth.)<br>
     *    CW Used in contract cross-referencing (to leases, wells, and so<br>
     * forth.)<br>
     * <br>
     * Additional codes may be added using Descriptive Titles (System - 20,<br>
     * Record Type - AL).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 00/AL <br>
     */
    private String F0911Z1_ALTY = null;

    /**
     * Date - Service/Tax<br>
     * <p>
     * A date that indicates when you purchased goods or services, or when you<br>
     * incurred a tax liability. <br>
     * <br>
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
     * EnterpriseOne Alias: DSVJ <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Date F0911Z1_DSVJ = null;

    /**
     * Transaction Originator<br>
     * <p>
     * The person who originally entered the transaction.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TORG <br>
     * EnterpriseOne field length:  10 <br>
     */
    private String F0911Z1_TORG = null;

    /**
     * Registration Number<br>
     * <p>
     * This is a generic registration number used in some countries for<br>
     * national reporting purposes.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: REG# <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_REG_Pound = null;

    /**
     * Payment ID (Internal)<br>
     * <p>
     * A number that the system assigns from Next Numbers to identify and track<br>
     * payment records.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PYID <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_PYID = null;

    /**
     * User ID<br>
     * <p>
     * The code that identifies a user profile.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: USER <br>
     * EnterpriseOne field length:  10 <br>
     */
    private String F0911Z1_USER = null;

    /**
     * Program ID<br>
     * <p>
     * The number that identifies the batch or interactive program (batch or<br>
     * interactive object). For example, the number of the Sales Order Entry<br>
     * interactive program is P4210, and the number of the Print Invoices batch<br>
     * process report is R42565.<br>
     * <br>
     * The program ID is a variable length value. It is assigned according to a<br>
     * structured syntax in the form TSSXXX, where:<br>
     * <br>
     * T<br>
     * The first character of the number is alphabetic and identifies the type,<br>
     * such as P for Program, R for Report, and so on. For example, the value P<br>
     * in the number P4210 indicates that the object is a program.<br>
     * <br>
     * SS<br>
     * The second and third characters of the number are numeric and identify<br>
     * the system code. For example, the value 42 in the number P4210 indicates<br>
     * that this program belongs to system 42, which is the Sales Order<br>
     * Processing system.<br>
     * <br>
     * XXX<br>
     * The remaining characters of the numer are numeric and identify a unique<br>
     * program or report. For example, the value 10 in the number P4210<br>
     * indicates that this is the Sales Order Entry program.<br>
     * <br>
     * <br>
     * <br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PID <br>
     * EnterpriseOne field length:  10 <br>
     * EnterpriseOne Edit Rule:CHKOBJ <br>
     */
    private String F0911Z1_PID = null;

    /**
     * Work Station ID<br>
     * <p>
     * The code that identifies the work station ID that executed a particular<br>
     * job.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: JOBN <br>
     * EnterpriseOne field length:  10 <br>
     */
    private String F0911Z1_JOBN = null;

    /**
     * Date - Updated<br>
     * <p>
     * The date that specifies the last update to the file record.<br>
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
     * EnterpriseOne Alias: UPMJ <br>
     * EnterpriseOne field length:  6 <br>
     */
    private Date F0911Z1_UPMJ = null;

    /**
     * Time - Last Updated<br>
     * <p>
     * The time that specifies when the program executed the last update to<br>
     * this record.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: UPMT <br>
     * EnterpriseOne field length:  6 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_UPMT = null;

    /**
     * Currency Mode-Foreign or Domestic Entry<br>
     * <p>
     * An option that specifies whether the system displays amounts in the<br>
     * domestic or foreign currency.<br>
     * <br>
     * On <br>
     * The system displays amounts in the foreign currency of the transaction.<br>
     * <br>
     * Off <br>
     * The system displays amounts in the domestic currency of the transaction.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CRRM <br>
     * EnterpriseOne field length:  1 <br>
     * EnterpriseOne User Defined Code: H00/CY <br>
     * EnterpriseOne Default Value: F<br>
     */
    private String F0911Z1_CRRM = null;

    /**
     * Amount - Currency<br>
     * <p>
     * The foreign currency amount entered on the transaction. If the<br>
     * Multi-Currency Conversion option on the General Accounting constants is<br>
     * set to Y, the foreign amount is multiplied by the exchange rate to<br>
     * arrive at the domestic amount. If the Multi-Currency Conversion option<br>
     * is set to Z, the foreign amount is divided by the exchange rate.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ACR <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private MathNumeric F0911Z1_ACR = null;

    /**
     * Date - For G/L (and Voucher) - MO<br>
     * <p>
     * The date used for posting to the proper General Ledger fiscal period. <br>
     * The Company Constants file for the General Accounting system provides a<br>
     * table which defines date ranges for each accounting period. Accounting<br>
     * period 13 is used for 13 period accounting. Accounting period 14 is<br>
     * provided for those who do not want to use an extra accounting period for<br>
     * posting audit adjustments.<br>
     * <br>
     * In all transaction processing, this date is edited for 'PBCO', 'PACO'<br>
     * and 'WACO'date warnings.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DGM <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DGM = null;

    /**
     * Date - For G/L (and Voucher) - DA<br>
     * <p>
     * The date used for posting to the proper General Ledger fiscal period. <br>
     * The Company Constants file for the General Accounting system provides a<br>
     * table which defines date ranges for each accounting period. Accounting<br>
     * period 13 is used for 13 period accounting. Accounting period 14 is<br>
     * provided for those who do not want to use an extra accounting period for<br>
     * posting audit adjustments.<br>
     * <br>
     * In all transaction processing, this date is edited for 'PBCO', 'PACO'<br>
     * and 'WACO'date warnings.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DGD <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DGD = null;

    /**
     * Date - For G/L (and Voucher) - YR<br>
     * <p>
     * The date used for posting to the proper General Ledger fiscal period. <br>
     * The Company Constants file for the General Accounting system provides a<br>
     * table which defines date ranges for each accounting period. Accounting<br>
     * period 13 is used for 13 period accounting. Accounting period 14 is<br>
     * provided for those who do not want to use an extra accounting period for<br>
     * posting audit adjustments.<br>
     * <br>
     * In all transaction processing, this date is edited for 'PBCO', 'PACO'<br>
     * and 'WACO'date warnings.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DGY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DGY = null;

    /**
     * Date - For G/L (and Voucher) - CTRY<br>
     * <p>
     * The date used for posting to the proper General Ledger fiscal period. <br>
     * The Company Constants file for the General Accounting system provides a<br>
     * table which defines date ranges for each accounting period. Accounting<br>
     * period 13 is used for 13 period accounting. Accounting period 14 is<br>
     * provided for those who do not want to use an extra accounting period for<br>
     * posting audit adjustments.<br>
     * <br>
     * In all transaction processing, this date is edited for 'PBCO', 'PACO'<br>
     * and 'WACO'date warnings.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DG# <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DG_Pound = null;

    /**
     * Date - Batch - MO<br>
     * <p>
     * The date of the batch.  The system date will be used if this field is<br>
     * left blank.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DICM <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DICM = null;

    /**
     * Date - Batch - DA<br>
     * <p>
     * The date of the batch.  The system date will be used if this field is<br>
     * left blank.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DICD <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DICD = null;

    /**
     * Date - Batch - YR<br>
     * <p>
     * The date of the batch.  The system date will be used if this field is<br>
     * left blank.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DICY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DICY = null;

    /**
     * Date - Batch - CTRY<br>
     * <p>
     * Date - Batch - CTRY<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DIC# <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DIC_Pound = null;

    /**
     * Date - Batch System Date - MO<br>
     * <p>
     * You may enter dates with or without imbedded slashes or dashes. If the<br>
     * date is left blank, in most instances the system date will automatically<br>
     * be inserted.<br>
     * <br>
     * Exceptions to this rule will result in an error condition. Dates may be<br>
     * entered in MM/DD/YY format, DD/MM/YY format, or YY/MM/DD format, based<br>
     * upon the configuration system value. The month must be 01 through 12.<br>
     * The days must be appropriate to the particular month.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DSYM <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     * EnterpriseOne Edit Rule:RANGE <br>
     */
    private MathNumeric F0911Z1_DSYM = null;

    /**
     * Date - Batch System Date - DA<br>
     * <p>
     * You may enter dates with or without imbedded slashes or dashes. If the<br>
     * date is left blank, in most instances the system date will automatically<br>
     * be inserted.<br>
     * <br>
     * Exceptions to this rule will result in an error condition. Dates may be<br>
     * entered in MM/DD/YY format, DD/MM/YY format, or YY/MM/DD format, based<br>
     * upon the configuration system value. The month must be 01 through 12.<br>
     * The days must be appropriate to the particular month.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DSYD <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     * EnterpriseOne Edit Rule:RANGE <br>
     */
    private MathNumeric F0911Z1_DSYD = null;

    /**
     * Date - Batch System Date - YR<br>
     * <p>
     * You may enter dates with or without imbedded slashes or dashes. If the<br>
     * date is left blank, in most instances the system date will automatically<br>
     * be inserted.<br>
     * <br>
     * Exceptions to this rule will result in an error condition. Dates may be<br>
     * entered in MM/DD/YY format, DD/MM/YY format, or YY/MM/DD format, based<br>
     * upon the configuration system value. The month must be 01 through 12.<br>
     * The days must be appropriate to the particular month.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DSYY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DSYY = null;

    /**
     * Date - Batch System Date - CTRY<br>
     * <p>
     * Date - Batch System Date - CTRY<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DSY# <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     * EnterpriseOne Edit Rule:RANGE <br>
     */
    private MathNumeric F0911Z1_DSY_Pound = null;

    /**
     * Date - Check - MO<br>
     * <p>
     * The check or item date.  This may be the accounts payable check date,<br>
     * the accounts receivable check receipt date and so on.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DKM <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DKM = null;

    /**
     * Date - Check - DA<br>
     * <p>
     * The check or item date.  This may be the accounts payable check date,<br>
     * the accounts receivable check receipt date and so on.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DKD <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DKD = null;

    /**
     * Date - Check - YR<br>
     * <p>
     * The check or item date.  This may be the accounts payable check date,<br>
     * the accounts receivable check receipt date and so on.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DKY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DKY = null;

    /**
     * Date - Check - CTRY<br>
     * <p>
     * Date - Check - CTRY<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DK# <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DK_Pound = null;

    /**
     * Date - Service/Tax - MO<br>
     * <p>
     * The service date is a memorandum date to record the date of actual field<br>
     * service or activity.  If the date is left blank, in most instances the<br>
     * system will default it to the G/L date.  Exceptions to this rule will<br>
     * result in an error message.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DSVM <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DSVM = null;

    /**
     * Date - Service/Tax - DA<br>
     * <p>
     * The service date is a memorandum date to record the date of actual field<br>
     * service or activity. If the date is left blank, in most instances the<br>
     * system will default it to the G/L date. Exceptions to this rule will<br>
     * result in an error message.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DSVD <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DSVD = null;

    /**
     * Date - Service/Tax - YR<br>
     * <p>
     * The service date is a memorandum date to record the date of actual field<br>
     * service or activity. If the date is left blank, in most instances the<br>
     * system will default it to the G/L date. Exceptions to this rule will<br>
     * result in an error message.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DSVY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DSVY = null;

    /**
     * Date - Service/Tax - CTRY<br>
     * <p>
     * Date - Service/Tax - CTRY<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DSV# <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DSV_Pound = null;

    /**
     * Historical Date - MO<br>
     * <p>
     * Historical Date - MO<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: HDGM <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_HDGM = null;

    /**
     * Historical Date - DA<br>
     * <p>
     * Historical Date - DA<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: HDGD <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_HDGD = null;

    /**
     * Historical Date - YR<br>
     * <p>
     * Historical Date - YR<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: HDGY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_HDGY = null;

    /**
     * Historical Date - CTRY<br>
     * <p>
     * Historical Date - CTRY<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: HDG# <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_HDG_Pound = null;

    /**
     * Date - Check Cleared - MO<br>
     * <p>
     * Date - Check Cleared - MO<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DKCM <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DKCM = null;

    /**
     * Date - Check Cleared - DA<br>
     * <p>
     * Date - Check Cleared - DA<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DKCD <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DKCD = null;

    /**
     * Date - Check Cleared - YR<br>
     * <p>
     * Date - Check Cleared - YR<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DKCY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DKCY = null;

    /**
     * Date - Check Cleared - CTRY<br>
     * <p>
     * Date - Check Cleared - CTRY<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DKC# <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_DKC_Pound = null;

    /**
     * Date - Invoice - MO<br>
     * <p>
     * Date - Invoice - MO<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: IVDM <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_IVDM = null;

    /**
     * Date - Invoice - DA<br>
     * <p>
     * Date - Invoice - DA<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: IVDD <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_IVDD = null;

    /**
     * Date - Invoice - YR<br>
     * <p>
     * Date - Invoice - YR<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: IVDY <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_IVDY = null;

    /**
     * Date - Invoice - CTRY<br>
     * <p>
     * Date - Invoice - CTRY<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: IVD# <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_IVD_Pound = null;

    /**
     * Managerial Analysis Code 1<br>
     * <p>
     * A cost object code, such as an equipment item number or an address book<br>
     * number. If you enter a cost object code, you must also specify the cost<br>
     * object type. This field functions the same and is validated the same as<br>
     * the subledger field, but the system does not post the data in the cost<br>
     * object code field to the Account Balances file (F0902).<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ABR1 <br>
     * EnterpriseOne field length:  12 <br>
     */
    private String F0911Z1_ABR1 = null;

    /**
     * Managerial Analysis Code 2<br>
     * <p>
     * A cost object code, such as an equipment item number or an address book<br>
     * number. If you enter a cost object code, you must also specify the cost<br>
     * object type. This field functions the same and is validated the same as<br>
     * the subledger field, but the system does not post the data in the cost<br>
     * object code field to the Account Balances file (F0902).<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ABR2 <br>
     * EnterpriseOne field length:  12 <br>
     */
    private String F0911Z1_ABR2 = null;

    /**
     * Managerial Analysis Code 3<br>
     * <p>
     * A cost object code, such as an equipment item number or an address book<br>
     * number. If you enter a cost object code, you must also specify the cost<br>
     * object type. This field functions the same and is validated the same as<br>
     * the subledger field, but the system does not post the data in the cost<br>
     * object code field to the Account Balances file (F0902).<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ABR3 <br>
     * EnterpriseOne field length:  12 <br>
     */
    private String F0911Z1_ABR3 = null;

    /**
     * Managerial Analysis Code 4<br>
     * <p>
     * A cost object code, such as an equipment item number or an address book<br>
     * number. If you enter a cost object code, you must also specify the cost<br>
     * object type. This field functions the same and is validated the same as<br>
     * the subledger field, but the system does not post the data in the cost<br>
     * object code field to the Account Balances file (F0902).<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ABR4 <br>
     * EnterpriseOne field length:  12 <br>
     */
    private String F0911Z1_ABR4 = null;

    /**
     * Managerial Analysis Type 1<br>
     * <p>
     * A code that specifies the type of cost object and the type of editing.<br>
     * Valid types are stored in the Cost Object Types table (F1620) and can be<br>
     * added or modified using the Cost Object Types program (P1620).<br>
     * <br>
     * <br>
     * <br>
     * <br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ABT1 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ABT1 = null;

    /**
     * Managerial Analysis Type 2<br>
     * <p>
     * A code that specifies the type of cost object and the type of editing.<br>
     * Valid types are stored in the Cost Object Types table (F1620) and can be<br>
     * added or modified using the Cost Object Types program (P1620).<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ABT2 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ABT2 = null;

    /**
     * Managerial Analysis Type 3<br>
     * <p>
     * A code that specifies the type of cost object and the type of editing.<br>
     * Valid types are stored in the Cost Object Types table (F1620) and can be<br>
     * added or modified using the Cost Object Types program (P1620).<br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ABT3 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ABT3 = null;

    /**
     * Managerial Analysis Type 4<br>
     * <p>
     * A code that specifies the type of cost object and the type of editing.<br>
     * Valid types are stored in the Cost Object Types table (F1620) and can be<br>
     * added or modified using the Cost Object Types program (P1620).<br>
     * <br>
     * <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ABT4 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_ABT4 = null;

    /**
     * Item Number - Short<br>
     * <p>
     * An inventory item number. The system provides three separate item<br>
     * numbers plus an extensive cross-reference capability to other item<br>
     * numbers (see data item XRT) to accommodate substitute item numbers,<br>
     * replacements, bar codes, customer numbers, supplier numbers, and so<br>
     * forth. The item numbers are as follows:<br>
     * <br>
     *   o Item Number (short) - An eight-digit, computer-assigned item number <br>
     * 
    <br>
     * <br>
     *   o 2nd Item Number - The 25-digit, free-form, user defined alphanumeric<br>
     * item number  <br>
     * <br>
     *   o 3rd Item Number - Another 25-digit, free-form, user defined<br>
     * alphanumeric item number<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ITM <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_ITM = null;

    /**
     * Posting Code 1 - Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM01 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM01 = null;

    /**
     * Posting Code 2 - Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM02 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM02 = null;

    /**
     * Posting Code 3 - Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM03 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM03 = null;

    /**
     * Posting Code 4 - Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM04 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM04 = null;

    /**
     * Posting Code 5 - Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM05 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM05 = null;

    /**
     * Posting Code 6 - Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM06 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM06 = null;

    /**
     * Posting Code 7 - Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM07 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM07 = null;

    /**
     * Posting Code 8 - Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM08 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM08 = null;

    /**
     * Posting Code 9 - Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM09 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM09 = null;

    /**
     * Posting Code 10- Managerial Accounting .<br>
     * <p>
     * Reserved for Financial Systems Future Use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PM10 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_PM10 = null;

    /**
     * Currency Code - Base<br>
     * <p>
     * A code that represents the currency of the company for a transaction.<br>
     * For a foreign currency transaction, this is the currency code of the<br>
     * domestic side of the transaction.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: BCRC <br>
     * EnterpriseOne field length:  3 <br>
     */
    private String F0911Z1_BCRC = null;

    /**
     * Tax Expl Code 1<br>
     * <p>
     * A hard-coded user defined code (00/EX) that controls the algorithm that<br>
     * the system uses to calculate tax and G/L distribution amounts. The<br>
     * system uses the tax explanation code in conjunction with the tax rate<br>
     * area and tax rules to determine how the tax is calculated. Each<br>
     * transaction pay item can be defined with a different tax explanation<br>
     * code.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EXR1 <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 00/EX <br>
     */
    private String F0911Z1_EXR1 = null;

    /**
     * Tax Rate/Area<br>
     * <p>
     * A code that identifies a tax or geographic area that has common tax<br>
     * rates and tax authorities. The system validates the code you enter<br>
     * against the Tax Areas table (F4008). The system uses the tax rate area<br>
     * in conjunction with the tax explanation code and tax rules to calculate<br>
     * tax and G/L distribution amounts when you create an invoice or voucher.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TXA1 <br>
     * EnterpriseOne field length:  10 <br>
     */
    private String F0911Z1_TXA1 = null;

    /**
     * Tax - Short Item Number<br>
     * <p>
     * A code that identifies a group of items or a single item for tax<br>
     * purposes. Items that have a group code are usually assessed a VAT. Items<br>
     * that have a specific item code are usually assessed a luxury tax.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TXITM <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private MathNumeric F0911Z1_TXITM = null;

    /**
     * Activity-Based Costing Activity Code<br>
     * <p>
     * The aggregation of actions performed within an organization that are<br>
     * useful for purposes of activity-based costing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ACTB <br>
     * EnterpriseOne field length:  10 <br>
     */
    private String F0911Z1_ACTB = null;

    /**
     * Amount - Tax<br>
     * <p>
     * The amount assessed and payable to tax authorities. It is the total of<br>
     * the VAT, use, and sales taxes (PST).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: STAM <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private MathNumeric F0911Z1_STAM = null;

    /**
     * Amount - Foreign Tax<br>
     * <p>
     * The tax amount in foreign currency.  This can be either the sales, use,<br>
     * or VAT tax.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CTAM <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private MathNumeric F0911Z1_CTAM = null;

    /**
     * Amount - Gross<br>
     * <p>
     * A value that specifies the total amount of the invoice or voucher pay<br>
     * item. The gross amount might include the tax amount, depending on the<br>
     * tax explanation code. The system does not decrease the gross amount when<br>
     * payments are applied. When you void a transaction, the system clears the<br>
     * gross amount field.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: AG <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private MathNumeric F0911Z1_AG = null;

    /**
     * Amount - Foreign Gross<br>
     * <p>
     * A number that specifies the gross amount in foreign currency.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: AGF <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private MathNumeric F0911Z1_AGF = null;

    /**
     * Track Taxes Flag<br>
     * <p>
     * A code that specifies whether to update the General Ledger table (F0911)<br>
     * with tax information in order to track taxes for a general ledger<br>
     * account. Valid values are:<br>
     * <br>
     * 1<br>
     * Update the tax explanation code field, tax rate area field, and tax item<br>
     * number field in the General Ledger table (F0911) to track taxes.<br>
     * <br>
     * 0<br>
     * Do not update the tax explanation code field, tax rate area field, or<br>
     * tax item number field in the General Ledger table (F0911) to track<br>
     * taxes.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TKTX <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_TKTX = null;

    /**
     * DistributionLine Number<br>
     * <p>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DLNID <br>
     * EnterpriseOne field length:  6 <br>
     * EnterpriseOne decimal places: 3 <br>
     */
    private MathNumeric F0911Z1_DLNID = null;

    /**
     * Receipt Number<br>
     * <p>
     * For Auto Bank Statement, this code identifies the payment/receipt or<br>
     * reference number. <br>
     * <br>
     * NOTE:  <br>
     * <br>
     * Bank Statement Detail section<br>
     * The payment/receipt number was originally stored in the DOCM field of<br>
     * F09617.  Due to size limitations of DOCM, it is now also stored in CKNU.<br>
     *  For display purposes, the data item used for payment/receipt number on<br>
     * this grid is CKNU.<br>
     * <br>
     * Account Ledger section<br>
     * This reference number was originally stored in the R1 field of F0911. <br>
     * Due to size limitations, it is now also stored in CKNU. For display<br>
     * purposes, the data item used for Reference1 on this grid is CKNU. <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CKNU <br>
     * EnterpriseOne field length:  25 <br>
     */
    private String F0911Z1_CKNU = null;

    /**
     * Burdening Posted Code<br>
     * <p>
     * A code the system uses to identify if a transaction has been processed<br>
     * for burdening.  A value of '1' indicates the transaction has been<br>
     * processed for burdening. <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: BUPC <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String F0911Z1_BUPC = null;

    /**
     * Alternate Home Business Unit<br>
     * <p>
     * The number of the business unit in which the employee generally resides.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: AHBU <br>
     * EnterpriseOne field length:  12 <br>
     */
    private String F0911Z1_AHBU = null;

    /**
     * Employee Pool Grouping Code<br>
     * <p>
     * A user defined code (system 00, type 12) that defines the pool grouping<br>
     * code.  The employee pool grouping code is derived from category code 12<br>
     * on the employee’   s home business unit.  <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EPGC <br>
     * EnterpriseOne field length:  3 <br>
     * EnterpriseOne User Defined Code: 00/12 <br>
     */
    private String F0911Z1_EPGC = null;

    /**
     * Job Pool Grouping Code<br>
     * <p>
     * A user defined code (system 00, type 12) that defines the pool grouping<br>
     * code.  The code is specified in category code 12 on the job.  The system<br>
     * retrieves category code 12 from the job during workfile generation and<br>
     * assigns the value to the job pool grouping code on the billing workfile<br>
     * transaction.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: JPGC <br>
     * EnterpriseOne field length:  3 <br>
     * EnterpriseOne User Defined Code: 00/12 <br>
     */
    private String F0911Z1_JPGC = null;

    public InternalInsertBatchJournaleEntryStagingFields() {
    }

    public void setF0911Z1_EDUS(String f0911Z1_EDUS) {
        this.F0911Z1_EDUS = f0911Z1_EDUS;
    }

    public String getF0911Z1_EDUS() {
        return F0911Z1_EDUS;
    }

    public void setF0911Z1_EDTY(String f0911Z1_EDTY) {
        this.F0911Z1_EDTY = f0911Z1_EDTY;
    }

    public String getF0911Z1_EDTY() {
        return F0911Z1_EDTY;
    }

    public void setF0911Z1_EDSQ(MathNumeric f0911Z1_EDSQ) {
        if (f0911Z1_EDSQ != null)
            this.F0911Z1_EDSQ = f0911Z1_EDSQ;
    }

    public void setF0911Z1_EDSQ(Integer f0911Z1_EDSQ) {
        if (f0911Z1_EDSQ != null)
            this.F0911Z1_EDSQ = new MathNumeric(f0911Z1_EDSQ);
    }


    public MathNumeric getF0911Z1_EDSQ() {
        return F0911Z1_EDSQ;
    }

    public void setF0911Z1_EDTN(String f0911Z1_EDTN) {
        this.F0911Z1_EDTN = f0911Z1_EDTN;
    }

    public String getF0911Z1_EDTN() {
        return F0911Z1_EDTN;
    }

    public void setF0911Z1_EDCT(String f0911Z1_EDCT) {
        this.F0911Z1_EDCT = f0911Z1_EDCT;
    }

    public String getF0911Z1_EDCT() {
        return F0911Z1_EDCT;
    }

    public void setF0911Z1_EDLN(MathNumeric f0911Z1_EDLN) {
        if (f0911Z1_EDLN != null)
            this.F0911Z1_EDLN = f0911Z1_EDLN;
    }

    public void setF0911Z1_EDLN(BigDecimal f0911Z1_EDLN) {
        if (f0911Z1_EDLN != null)
            this.F0911Z1_EDLN = new MathNumeric(f0911Z1_EDLN);
    }

    public MathNumeric getF0911Z1_EDLN() {
        return F0911Z1_EDLN;
    }

    public void setF0911Z1_EDTS(String f0911Z1_EDTS) {
        this.F0911Z1_EDTS = f0911Z1_EDTS;
    }

    public String getF0911Z1_EDTS() {
        return F0911Z1_EDTS;
    }

    public void setF0911Z1_EDFT(String f0911Z1_EDFT) {
        this.F0911Z1_EDFT = f0911Z1_EDFT;
    }

    public String getF0911Z1_EDFT() {
        return F0911Z1_EDFT;
    }

    public void setF0911Z1_EDDT(Date f0911Z1_EDDT) {
        this.F0911Z1_EDDT = f0911Z1_EDDT;
    }

    public void setF0911Z1_EDDT(Calendar f0911Z1_EDDT) {
        if (f0911Z1_EDDT != null)
            this.F0911Z1_EDDT = f0911Z1_EDDT.getTime();
    }

    public Date getF0911Z1_EDDT() {
        return F0911Z1_EDDT;
    }

    public void setF0911Z1_EDER(String f0911Z1_EDER) {
        this.F0911Z1_EDER = f0911Z1_EDER;
    }

    public String getF0911Z1_EDER() {
        return F0911Z1_EDER;
    }

    public void setF0911Z1_EDDL(MathNumeric f0911Z1_EDDL) {
        if (f0911Z1_EDDL != null)
            this.F0911Z1_EDDL = f0911Z1_EDDL;
    }

    public void setF0911Z1_EDDL(Integer f0911Z1_EDDL) {
        if (f0911Z1_EDDL != null)
            this.F0911Z1_EDDL = new MathNumeric(f0911Z1_EDDL);
    }

    public MathNumeric getF0911Z1_EDDL() {
        return F0911Z1_EDDL;
    }

    public void setF0911Z1_EDSP(String f0911Z1_EDSP) {
        this.F0911Z1_EDSP = f0911Z1_EDSP;
    }

    public String getF0911Z1_EDSP() {
        return F0911Z1_EDSP;
    }

    public void setF0911Z1_EDTC(String f0911Z1_EDTC) {
        this.F0911Z1_EDTC = f0911Z1_EDTC;
    }

    public String getF0911Z1_EDTC() {
        return F0911Z1_EDTC;
    }

    public void setF0911Z1_EDTR(String f0911Z1_EDTR) {
        this.F0911Z1_EDTR = f0911Z1_EDTR;
    }

    public String getF0911Z1_EDTR() {
        return F0911Z1_EDTR;
    }

    public void setF0911Z1_EDBT(String f0911Z1_EDBT) {
        this.F0911Z1_EDBT = f0911Z1_EDBT;
    }

    public String getF0911Z1_EDBT() {
        return F0911Z1_EDBT;
    }

    public void setF0911Z1_EDGL(String f0911Z1_EDGL) {
        this.F0911Z1_EDGL = f0911Z1_EDGL;
    }

    public String getF0911Z1_EDGL() {
        return F0911Z1_EDGL;
    }

    public void setF0911Z1_EDAN(MathNumeric f0911Z1_EDAN) {
        if (f0911Z1_EDAN != null)
            this.F0911Z1_EDAN = f0911Z1_EDAN;
    }

    public void setF0911Z1_EDAN(Integer f0911Z1_EDAN) {
        if (f0911Z1_EDAN != null)
            this.F0911Z1_EDAN = new MathNumeric(f0911Z1_EDAN);
    }

    public MathNumeric getF0911Z1_EDAN() {
        return F0911Z1_EDAN;
    }

    public void setF0911Z1_KCO(String f0911Z1_KCO) {
        this.F0911Z1_KCO = f0911Z1_KCO;
    }

    public String getF0911Z1_KCO() {
        return F0911Z1_KCO;
    }

    public void setF0911Z1_DCT(String f0911Z1_DCT) {
        this.F0911Z1_DCT = f0911Z1_DCT;
    }

    public String getF0911Z1_DCT() {
        return F0911Z1_DCT;
    }

    public void setF0911Z1_DOC(MathNumeric f0911Z1_DOC) {
        if (f0911Z1_DOC != null)
            this.F0911Z1_DOC = f0911Z1_DOC;
    }

    public void setF0911Z1_DOC(Integer f0911Z1_DOC) {
        if (f0911Z1_DOC != null)
            this.F0911Z1_DOC = new MathNumeric(f0911Z1_DOC);
    }

    public MathNumeric getF0911Z1_DOC() {
        return F0911Z1_DOC;
    }

    public void setF0911Z1_DGJ(Date f0911Z1_DGJ) {
        this.F0911Z1_DGJ = f0911Z1_DGJ;
    }

    public void setF0911Z1_DGJ(Calendar f0911Z1_DGJ) {
        if (f0911Z1_DGJ != null)
            this.F0911Z1_DGJ = f0911Z1_DGJ.getTime();
    }

    public Date getF0911Z1_DGJ() {
        return F0911Z1_DGJ;
    }

    public void setF0911Z1_JELN(MathNumeric f0911Z1_JELN) {
        if (f0911Z1_JELN != null)
            this.F0911Z1_JELN = f0911Z1_JELN;
    }

    public void setF0911Z1_JELN(BigDecimal f0911Z1_JELN) {
        if (f0911Z1_JELN != null)
            this.F0911Z1_JELN = new MathNumeric(f0911Z1_JELN);
    }

    public MathNumeric getF0911Z1_JELN() {
        return F0911Z1_JELN;
    }

    public void setF0911Z1_EXTL(String f0911Z1_EXTL) {
        this.F0911Z1_EXTL = f0911Z1_EXTL;
    }

    public String getF0911Z1_EXTL() {
        return F0911Z1_EXTL;
    }

    public void setF0911Z1_POST(String f0911Z1_POST) {
        this.F0911Z1_POST = f0911Z1_POST;
    }

    public String getF0911Z1_POST() {
        return F0911Z1_POST;
    }

    public void setF0911Z1_ICU(MathNumeric f0911Z1_ICU) {
        if (f0911Z1_ICU != null)
            this.F0911Z1_ICU = f0911Z1_ICU;
    }

    public void setF0911Z1_ICU(Integer f0911Z1_ICU) {
        if (f0911Z1_ICU != null)
            this.F0911Z1_ICU = new MathNumeric(f0911Z1_ICU);
    }

    public MathNumeric getF0911Z1_ICU() {
        return F0911Z1_ICU;
    }

    public void setF0911Z1_ICUT(String f0911Z1_ICUT) {
        this.F0911Z1_ICUT = f0911Z1_ICUT;
    }

    public String getF0911Z1_ICUT() {
        return F0911Z1_ICUT;
    }

    public void setF0911Z1_DICJ(Date f0911Z1_DICJ) {
        this.F0911Z1_DICJ = f0911Z1_DICJ;
    }

    public void setF0911Z1_DICJ(Calendar f0911Z1_DICJ) {
        if (f0911Z1_DICJ != null)
            this.F0911Z1_DICJ = f0911Z1_DICJ.getTime();
    }

    public Date getF0911Z1_DICJ() {
        return F0911Z1_DICJ;
    }

    public void setF0911Z1_DSYJ(Date f0911Z1_DSYJ) {
        this.F0911Z1_DSYJ = f0911Z1_DSYJ;
    }

    public void setF0911Z1_DSYJ(Calendar f0911Z1_DSYJ) {
        if (f0911Z1_DSYJ != null)
            this.F0911Z1_DSYJ = f0911Z1_DSYJ.getTime();
    }

    public Date getF0911Z1_DSYJ() {
        return F0911Z1_DSYJ;
    }

    public void setF0911Z1_TICU(MathNumeric f0911Z1_TICU) {
        if (f0911Z1_TICU != null)
            this.F0911Z1_TICU = f0911Z1_TICU;
    }

    public void setF0911Z1_TICU(Integer f0911Z1_TICU) {
        if (f0911Z1_TICU != null)
            this.F0911Z1_TICU = new MathNumeric(f0911Z1_TICU);
    }

    public MathNumeric getF0911Z1_TICU() {
        return F0911Z1_TICU;
    }

    public void setF0911Z1_CO(String f0911Z1_CO) {
        this.F0911Z1_CO = f0911Z1_CO;
    }

    public String getF0911Z1_CO() {
        return F0911Z1_CO;
    }

    public void setF0911Z1_ANI(String f0911Z1_ANI) {
        this.F0911Z1_ANI = f0911Z1_ANI;
    }

    public String getF0911Z1_ANI() {
        return F0911Z1_ANI;
    }

    public void setF0911Z1_AM(String f0911Z1_AM) {
        this.F0911Z1_AM = f0911Z1_AM;
    }

    public String getF0911Z1_AM() {
        return F0911Z1_AM;
    }

    public void setF0911Z1_AID(String f0911Z1_AID) {
        this.F0911Z1_AID = f0911Z1_AID;
    }

    public String getF0911Z1_AID() {
        return F0911Z1_AID;
    }

    public void setF0911Z1_MCU(String f0911Z1_MCU) {
        this.F0911Z1_MCU = f0911Z1_MCU;
    }

    public String getF0911Z1_MCU() {
        return F0911Z1_MCU;
    }

    public void setF0911Z1_OBJ(String f0911Z1_OBJ) {
        this.F0911Z1_OBJ = f0911Z1_OBJ;
    }

    public String getF0911Z1_OBJ() {
        return F0911Z1_OBJ;
    }

    public void setF0911Z1_SUB(String f0911Z1_SUB) {
        this.F0911Z1_SUB = f0911Z1_SUB;
    }

    public String getF0911Z1_SUB() {
        return F0911Z1_SUB;
    }

    public void setF0911Z1_SBL(String f0911Z1_SBL) {
        this.F0911Z1_SBL = f0911Z1_SBL;
    }

    public String getF0911Z1_SBL() {
        return F0911Z1_SBL;
    }

    public void setF0911Z1_SBLT(String f0911Z1_SBLT) {
        this.F0911Z1_SBLT = f0911Z1_SBLT;
    }

    public String getF0911Z1_SBLT() {
        return F0911Z1_SBLT;
    }

    public void setF0911Z1_LT(String f0911Z1_LT) {
        this.F0911Z1_LT = f0911Z1_LT;
    }

    public String getF0911Z1_LT() {
        return F0911Z1_LT;
    }

    public void setF0911Z1_PN(MathNumeric f0911Z1_PN) {
        if (f0911Z1_PN != null)
            this.F0911Z1_PN = f0911Z1_PN;
    }

    public void setF0911Z1_PN(Integer f0911Z1_PN) {
        if (f0911Z1_PN != null)
            this.F0911Z1_PN = new MathNumeric(f0911Z1_PN);
    }

    public MathNumeric getF0911Z1_PN() {
        return F0911Z1_PN;
    }

    public void setF0911Z1_CTRY(MathNumeric f0911Z1_CTRY) {
        if (f0911Z1_CTRY != null)
            this.F0911Z1_CTRY = f0911Z1_CTRY;
    }

    public void setF0911Z1_CTRY(Integer f0911Z1_CTRY) {
        if (f0911Z1_CTRY != null)
            this.F0911Z1_CTRY = new MathNumeric(f0911Z1_CTRY);
    }

    public MathNumeric getF0911Z1_CTRY() {
        return F0911Z1_CTRY;
    }

    public void setF0911Z1_FY(MathNumeric f0911Z1_FY) {
        if (f0911Z1_FY != null)
            this.F0911Z1_FY = f0911Z1_FY;
    }

    public void setF0911Z1_FY(Integer f0911Z1_FY) {
        if (f0911Z1_FY != null)
            this.F0911Z1_FY = new MathNumeric(f0911Z1_FY);
    }

    public MathNumeric getF0911Z1_FY() {
        return F0911Z1_FY;
    }

    public void setF0911Z1_FQ(String f0911Z1_FQ) {
        this.F0911Z1_FQ = f0911Z1_FQ;
    }

    public String getF0911Z1_FQ() {
        return F0911Z1_FQ;
    }

    public void setF0911Z1_CRCD(String f0911Z1_CRCD) {
        this.F0911Z1_CRCD = f0911Z1_CRCD;
    }

    public String getF0911Z1_CRCD() {
        return F0911Z1_CRCD;
    }

    public void setF0911Z1_CRR(MathNumeric f0911Z1_CRR) {
        if (f0911Z1_CRR != null)
            this.F0911Z1_CRR = f0911Z1_CRR;
    }

    public void setF0911Z1_CRR(BigDecimal f0911Z1_CRR) {
        if (f0911Z1_CRR != null)
            this.F0911Z1_CRR = new MathNumeric(f0911Z1_CRR);
    }

    public MathNumeric getF0911Z1_CRR() {
        return F0911Z1_CRR;
    }

    public void setF0911Z1_HCRR(MathNumeric f0911Z1_HCRR) {
        if (f0911Z1_HCRR != null)
            this.F0911Z1_HCRR = f0911Z1_HCRR;
    }

    public void setF0911Z1_HCRR(BigDecimal f0911Z1_HCRR) {
        if (f0911Z1_HCRR != null)
            this.F0911Z1_HCRR = new MathNumeric(f0911Z1_HCRR);
    }

    public MathNumeric getF0911Z1_HCRR() {
        return F0911Z1_HCRR;
    }

    public void setF0911Z1_HDGJ(Date f0911Z1_HDGJ) {
        this.F0911Z1_HDGJ = f0911Z1_HDGJ;
    }

    public void setF0911Z1_HDGJ(Calendar f0911Z1_HDGJ) {
        if (f0911Z1_HDGJ != null)
            this.F0911Z1_HDGJ = f0911Z1_HDGJ.getTime();
    }

    public Date getF0911Z1_HDGJ() {
        return F0911Z1_HDGJ;
    }

    public void setF0911Z1_AA(MathNumeric f0911Z1_AA) {
        if (f0911Z1_AA != null)
            this.F0911Z1_AA = f0911Z1_AA;
    }

    public void setF0911Z1_AA(BigDecimal f0911Z1_AA) {
        if (f0911Z1_AA != null)
            this.F0911Z1_AA = new MathNumeric(f0911Z1_AA);
    }

    public MathNumeric getF0911Z1_AA() {
        return F0911Z1_AA;
    }

    public void setF0911Z1_U(MathNumeric f0911Z1_U) {
        if (f0911Z1_U != null)
            this.F0911Z1_U = f0911Z1_U;
    }

    public void setF0911Z1_U(BigDecimal f0911Z1_U) {
        if (f0911Z1_U != null)
            this.F0911Z1_U = new MathNumeric(f0911Z1_U);
    }

    public MathNumeric getF0911Z1_U() {
        return F0911Z1_U;
    }

    public void setF0911Z1_UM(String f0911Z1_UM) {
        this.F0911Z1_UM = f0911Z1_UM;
    }

    public String getF0911Z1_UM() {
        return F0911Z1_UM;
    }

    public void setF0911Z1_GLC(String f0911Z1_GLC) {
        this.F0911Z1_GLC = f0911Z1_GLC;
    }

    public String getF0911Z1_GLC() {
        return F0911Z1_GLC;
    }

    public void setF0911Z1_RE(String f0911Z1_RE) {
        this.F0911Z1_RE = f0911Z1_RE;
    }

    public String getF0911Z1_RE() {
        return F0911Z1_RE;
    }

    public void setF0911Z1_EXA(String f0911Z1_EXA) {
        this.F0911Z1_EXA = f0911Z1_EXA;
    }

    public String getF0911Z1_EXA() {
        return F0911Z1_EXA;
    }

    public void setF0911Z1_EXR(String f0911Z1_EXR) {
        this.F0911Z1_EXR = f0911Z1_EXR;
    }

    public String getF0911Z1_EXR() {
        return F0911Z1_EXR;
    }

    public void setF0911Z1_R1(String f0911Z1_R1) {
        this.F0911Z1_R1 = f0911Z1_R1;
    }

    public String getF0911Z1_R1() {
        return F0911Z1_R1;
    }

    public void setF0911Z1_R2(String f0911Z1_R2) {
        this.F0911Z1_R2 = f0911Z1_R2;
    }

    public String getF0911Z1_R2() {
        return F0911Z1_R2;
    }

    public void setF0911Z1_R3(String f0911Z1_R3) {
        this.F0911Z1_R3 = f0911Z1_R3;
    }

    public String getF0911Z1_R3() {
        return F0911Z1_R3;
    }

    public void setF0911Z1_SFX(String f0911Z1_SFX) {
        this.F0911Z1_SFX = f0911Z1_SFX;
    }

    public String getF0911Z1_SFX() {
        return F0911Z1_SFX;
    }

    public void setF0911Z1_ODOC(MathNumeric f0911Z1_ODOC) {
        if (f0911Z1_ODOC != null)
            this.F0911Z1_ODOC = f0911Z1_ODOC;
    }

    public void setF0911Z1_ODOC(Integer f0911Z1_ODOC) {
        if (f0911Z1_ODOC != null)
            this.F0911Z1_ODOC = new MathNumeric(f0911Z1_ODOC);
    }

    public MathNumeric getF0911Z1_ODOC() {
        return F0911Z1_ODOC;
    }

    public void setF0911Z1_ODCT(String f0911Z1_ODCT) {
        this.F0911Z1_ODCT = f0911Z1_ODCT;
    }

    public String getF0911Z1_ODCT() {
        return F0911Z1_ODCT;
    }

    public void setF0911Z1_OSFX(String f0911Z1_OSFX) {
        this.F0911Z1_OSFX = f0911Z1_OSFX;
    }

    public String getF0911Z1_OSFX() {
        return F0911Z1_OSFX;
    }

    public void setF0911Z1_PKCO(String f0911Z1_PKCO) {
        this.F0911Z1_PKCO = f0911Z1_PKCO;
    }

    public String getF0911Z1_PKCO() {
        return F0911Z1_PKCO;
    }

    public void setF0911Z1_OKCO(String f0911Z1_OKCO) {
        this.F0911Z1_OKCO = f0911Z1_OKCO;
    }

    public String getF0911Z1_OKCO() {
        return F0911Z1_OKCO;
    }

    public void setF0911Z1_PDCT(String f0911Z1_PDCT) {
        this.F0911Z1_PDCT = f0911Z1_PDCT;
    }

    public String getF0911Z1_PDCT() {
        return F0911Z1_PDCT;
    }

    public void setF0911Z1_AN8(MathNumeric f0911Z1_AN8) {
        if (f0911Z1_AN8 != null)
            this.F0911Z1_AN8 = f0911Z1_AN8;
    }

    public void setF0911Z1_AN8(Integer f0911Z1_AN8) {
        if (f0911Z1_AN8 != null)
            this.F0911Z1_AN8 = new MathNumeric(f0911Z1_AN8);
    }

    public MathNumeric getF0911Z1_AN8() {
        return F0911Z1_AN8;
    }

    public void setF0911Z1_CN(String f0911Z1_CN) {
        this.F0911Z1_CN = f0911Z1_CN;
    }

    public String getF0911Z1_CN() {
        return F0911Z1_CN;
    }

    public void setF0911Z1_DKJ(Date f0911Z1_DKJ) {
        this.F0911Z1_DKJ = f0911Z1_DKJ;
    }

    public void setF0911Z1_DKJ(Calendar f0911Z1_DKJ) {
        if (f0911Z1_DKJ != null)
            this.F0911Z1_DKJ = f0911Z1_DKJ.getTime();
    }

    public Date getF0911Z1_DKJ() {
        return F0911Z1_DKJ;
    }

    public void setF0911Z1_DKC(Date f0911Z1_DKC) {
        this.F0911Z1_DKC = f0911Z1_DKC;
    }

    public void setF0911Z1_DKC(Calendar f0911Z1_DKC) {
        if (f0911Z1_DKC != null)
            this.F0911Z1_DKC = f0911Z1_DKC.getTime();
    }

    public Date getF0911Z1_DKC() {
        return F0911Z1_DKC;
    }

    public void setF0911Z1_ASID(String f0911Z1_ASID) {
        this.F0911Z1_ASID = f0911Z1_ASID;
    }


    public String getF0911Z1_ASID() {
        return F0911Z1_ASID;
    }

    public void setF0911Z1_BRE(String f0911Z1_BRE) {
        this.F0911Z1_BRE = f0911Z1_BRE;
    }

    public String getF0911Z1_BRE() {
        return F0911Z1_BRE;
    }

    public void setF0911Z1_RCND(String f0911Z1_RCND) {
        this.F0911Z1_RCND = f0911Z1_RCND;
    }

    public String getF0911Z1_RCND() {
        return F0911Z1_RCND;
    }

    public void setF0911Z1_SUMM(String f0911Z1_SUMM) {
        this.F0911Z1_SUMM = f0911Z1_SUMM;
    }

    public String getF0911Z1_SUMM() {
        return F0911Z1_SUMM;
    }

    public void setF0911Z1_PRGE(String f0911Z1_PRGE) {
        this.F0911Z1_PRGE = f0911Z1_PRGE;
    }

    public String getF0911Z1_PRGE() {
        return F0911Z1_PRGE;
    }

    public void setF0911Z1_TNN(String f0911Z1_TNN) {
        this.F0911Z1_TNN = f0911Z1_TNN;
    }

    public String getF0911Z1_TNN() {
        return F0911Z1_TNN;
    }

    public void setF0911Z1_ALT1(String f0911Z1_ALT1) {
        this.F0911Z1_ALT1 = f0911Z1_ALT1;
    }

    public String getF0911Z1_ALT1() {
        return F0911Z1_ALT1;
    }

    public void setF0911Z1_ALT2(String f0911Z1_ALT2) {
        this.F0911Z1_ALT2 = f0911Z1_ALT2;
    }

    public String getF0911Z1_ALT2() {
        return F0911Z1_ALT2;
    }

    public void setF0911Z1_ALT3(String f0911Z1_ALT3) {
        this.F0911Z1_ALT3 = f0911Z1_ALT3;
    }

    public String getF0911Z1_ALT3() {
        return F0911Z1_ALT3;
    }

    public void setF0911Z1_ALT4(String f0911Z1_ALT4) {
        this.F0911Z1_ALT4 = f0911Z1_ALT4;
    }

    public String getF0911Z1_ALT4() {
        return F0911Z1_ALT4;
    }

    public void setF0911Z1_ALT5(String f0911Z1_ALT5) {
        this.F0911Z1_ALT5 = f0911Z1_ALT5;
    }

    public String getF0911Z1_ALT5() {
        return F0911Z1_ALT5;
    }

    public void setF0911Z1_ALT6(String f0911Z1_ALT6) {
        this.F0911Z1_ALT6 = f0911Z1_ALT6;
    }

    public String getF0911Z1_ALT6() {
        return F0911Z1_ALT6;
    }

    public void setF0911Z1_ALT7(String f0911Z1_ALT7) {
        this.F0911Z1_ALT7 = f0911Z1_ALT7;
    }

    public String getF0911Z1_ALT7() {
        return F0911Z1_ALT7;
    }

    public void setF0911Z1_ALT8(String f0911Z1_ALT8) {
        this.F0911Z1_ALT8 = f0911Z1_ALT8;
    }

    public String getF0911Z1_ALT8() {
        return F0911Z1_ALT8;
    }

    public void setF0911Z1_ALT9(String f0911Z1_ALT9) {
        this.F0911Z1_ALT9 = f0911Z1_ALT9;
    }

    public String getF0911Z1_ALT9() {
        return F0911Z1_ALT9;
    }

    public void setF0911Z1_ALT0(String f0911Z1_ALT0) {
        this.F0911Z1_ALT0 = f0911Z1_ALT0;
    }

    public String getF0911Z1_ALT0() {
        return F0911Z1_ALT0;
    }

    public void setF0911Z1_ALTT(String f0911Z1_ALTT) {
        this.F0911Z1_ALTT = f0911Z1_ALTT;
    }

    public String getF0911Z1_ALTT() {
        return F0911Z1_ALTT;
    }

    public void setF0911Z1_ALTU(String f0911Z1_ALTU) {
        this.F0911Z1_ALTU = f0911Z1_ALTU;
    }

    public String getF0911Z1_ALTU() {
        return F0911Z1_ALTU;
    }

    public void setF0911Z1_ALTV(String f0911Z1_ALTV) {
        this.F0911Z1_ALTV = f0911Z1_ALTV;
    }

    public String getF0911Z1_ALTV() {
        return F0911Z1_ALTV;
    }

    public void setF0911Z1_ALTW(String f0911Z1_ALTW) {
        this.F0911Z1_ALTW = f0911Z1_ALTW;
    }

    public String getF0911Z1_ALTW() {
        return F0911Z1_ALTW;
    }

    public void setF0911Z1_ALTX(String f0911Z1_ALTX) {
        this.F0911Z1_ALTX = f0911Z1_ALTX;
    }

    public String getF0911Z1_ALTX() {
        return F0911Z1_ALTX;
    }

    public void setF0911Z1_ALTZ(String f0911Z1_ALTZ) {
        this.F0911Z1_ALTZ = f0911Z1_ALTZ;
    }

    public String getF0911Z1_ALTZ() {
        return F0911Z1_ALTZ;
    }

    public void setF0911Z1_DLNA(String f0911Z1_DLNA) {
        this.F0911Z1_DLNA = f0911Z1_DLNA;
    }

    public String getF0911Z1_DLNA() {
        return F0911Z1_DLNA;
    }

    public void setF0911Z1_CFF1(String f0911Z1_CFF1) {
        this.F0911Z1_CFF1 = f0911Z1_CFF1;
    }

    public String getF0911Z1_CFF1() {
        return F0911Z1_CFF1;
    }

    public void setF0911Z1_CFF2(String f0911Z1_CFF2) {
        this.F0911Z1_CFF2 = f0911Z1_CFF2;
    }

    public String getF0911Z1_CFF2() {
        return F0911Z1_CFF2;
    }

    public void setF0911Z1_ASM(String f0911Z1_ASM) {
        this.F0911Z1_ASM = f0911Z1_ASM;
    }

    public String getF0911Z1_ASM() {
        return F0911Z1_ASM;
    }

    public void setF0911Z1_BC(String f0911Z1_BC) {
        this.F0911Z1_BC = f0911Z1_BC;
    }

    public String getF0911Z1_BC() {
        return F0911Z1_BC;
    }

    public void setF0911Z1_VINV(String f0911Z1_VINV) {
        this.F0911Z1_VINV = f0911Z1_VINV;
    }

    public String getF0911Z1_VINV() {
        return F0911Z1_VINV;
    }

    public void setF0911Z1_IVD(Date f0911Z1_IVD) {
        this.F0911Z1_IVD = f0911Z1_IVD;
    }

    public void setF0911Z1_IVD(Calendar f0911Z1_IVD) {
        if (f0911Z1_IVD != null)
            this.F0911Z1_IVD = f0911Z1_IVD.getTime();
    }

    public Date getF0911Z1_IVD() {
        return F0911Z1_IVD;
    }

    public void setF0911Z1_WR01(String f0911Z1_WR01) {
        this.F0911Z1_WR01 = f0911Z1_WR01;
    }

    public String getF0911Z1_WR01() {
        return F0911Z1_WR01;
    }

    public void setF0911Z1_PO(String f0911Z1_PO) {
        this.F0911Z1_PO = f0911Z1_PO;
    }

    public String getF0911Z1_PO() {
        return F0911Z1_PO;
    }

    public void setF0911Z1_PSFX(String f0911Z1_PSFX) {
        this.F0911Z1_PSFX = f0911Z1_PSFX;
    }

    public String getF0911Z1_PSFX() {
        return F0911Z1_PSFX;
    }

    public void setF0911Z1_DCTO(String f0911Z1_DCTO) {
        this.F0911Z1_DCTO = f0911Z1_DCTO;
    }

    public String getF0911Z1_DCTO() {
        return F0911Z1_DCTO;
    }

    public void setF0911Z1_LNID(MathNumeric f0911Z1_LNID) {
        if (f0911Z1_LNID != null)
            this.F0911Z1_LNID = f0911Z1_LNID;
    }

    public void setF0911Z1_LNID(BigDecimal f0911Z1_LNID) {
        if (f0911Z1_LNID != null)
            this.F0911Z1_LNID = new MathNumeric(f0911Z1_LNID);
    }

    public MathNumeric getF0911Z1_LNID() {
        return F0911Z1_LNID;
    }

    public void setF0911Z1_WY(MathNumeric f0911Z1_WY) {
        if (f0911Z1_WY != null)
            this.F0911Z1_WY = f0911Z1_WY;
    }

    public void setF0911Z1_WY(Integer f0911Z1_WY) {
        if (f0911Z1_WY != null)
            this.F0911Z1_WY = new MathNumeric(f0911Z1_WY);
    }

    public MathNumeric getF0911Z1_WY() {
        return F0911Z1_WY;
    }

    public void setF0911Z1_WN(MathNumeric f0911Z1_WN) {
        if (f0911Z1_WN != null)
            this.F0911Z1_WN = f0911Z1_WN;
    }

    public void setF0911Z1_WN(Integer f0911Z1_WN) {
        if (f0911Z1_WN != null)
            this.F0911Z1_WN = new MathNumeric(f0911Z1_WN);
    }

    public MathNumeric getF0911Z1_WN() {
        return F0911Z1_WN;
    }

    public void setF0911Z1_FNLP(String f0911Z1_FNLP) {
        this.F0911Z1_FNLP = f0911Z1_FNLP;
    }

    public String getF0911Z1_FNLP() {
        return F0911Z1_FNLP;
    }

    public void setF0911Z1_OPSQ(MathNumeric f0911Z1_OPSQ) {
        if (f0911Z1_OPSQ != null)
            this.F0911Z1_OPSQ = f0911Z1_OPSQ;
    }

    public void setF0911Z1_OPSQ(BigDecimal f0911Z1_OPSQ) {
        if (f0911Z1_OPSQ != null)
            this.F0911Z1_OPSQ = new MathNumeric(f0911Z1_OPSQ);
    }

    public MathNumeric getF0911Z1_OPSQ() {
        return F0911Z1_OPSQ;
    }

    public void setF0911Z1_JBCD(String f0911Z1_JBCD) {
        this.F0911Z1_JBCD = f0911Z1_JBCD;
    }

    public String getF0911Z1_JBCD() {
        return F0911Z1_JBCD;
    }

    public void setF0911Z1_JBST(String f0911Z1_JBST) {
        this.F0911Z1_JBST = f0911Z1_JBST;
    }

    public String getF0911Z1_JBST() {
        return F0911Z1_JBST;
    }

    public void setF0911Z1_HMCU(String f0911Z1_HMCU) {
        this.F0911Z1_HMCU = f0911Z1_HMCU;
    }

    public String getF0911Z1_HMCU() {
        return F0911Z1_HMCU;
    }

    public void setF0911Z1_DOI(MathNumeric f0911Z1_DOI) {
        if (f0911Z1_DOI != null)
            this.F0911Z1_DOI = f0911Z1_DOI;
    }

    public void setF0911Z1_DOI(Integer f0911Z1_DOI) {
        if (f0911Z1_DOI != null)
            this.F0911Z1_DOI = new MathNumeric(f0911Z1_DOI);
    }

    public MathNumeric getF0911Z1_DOI() {
        return F0911Z1_DOI;
    }

    public void setF0911Z1_ALID(String f0911Z1_ALID) {
        this.F0911Z1_ALID = f0911Z1_ALID;
    }

    public String getF0911Z1_ALID() {
        return F0911Z1_ALID;
    }

    public void setF0911Z1_ALTY(String f0911Z1_ALTY) {
        this.F0911Z1_ALTY = f0911Z1_ALTY;
    }

    public String getF0911Z1_ALTY() {
        return F0911Z1_ALTY;
    }

    public void setF0911Z1_DSVJ(Date f0911Z1_DSVJ) {
        this.F0911Z1_DSVJ = f0911Z1_DSVJ;
    }

    public void setF0911Z1_DSVJ(Calendar f0911Z1_DSVJ) {
        if (f0911Z1_DSVJ != null)
            this.F0911Z1_DSVJ = f0911Z1_DSVJ.getTime();
    }

    public Date getF0911Z1_DSVJ() {
        return F0911Z1_DSVJ;
    }

    public void setF0911Z1_TORG(String f0911Z1_TORG) {
        this.F0911Z1_TORG = f0911Z1_TORG;
    }

    public String getF0911Z1_TORG() {
        return F0911Z1_TORG;
    }


    public void setF0911Z1_PYID(MathNumeric f0911Z1_PYID) {
        if (f0911Z1_PYID != null)
            this.F0911Z1_PYID = f0911Z1_PYID;
    }

    public void setF0911Z1_PYID(Integer f0911Z1_PYID) {
        if (f0911Z1_PYID != null)
            this.F0911Z1_PYID = new MathNumeric(f0911Z1_PYID);
    }

    public MathNumeric getF0911Z1_PYID() {
        return F0911Z1_PYID;
    }

    public void setF0911Z1_USER(String f0911Z1_USER) {
        this.F0911Z1_USER = f0911Z1_USER;
    }

    public String getF0911Z1_USER() {
        return F0911Z1_USER;
    }

    public void setF0911Z1_PID(String f0911Z1_PID) {
        this.F0911Z1_PID = f0911Z1_PID;
    }

    public String getF0911Z1_PID() {
        return F0911Z1_PID;
    }

    public void setF0911Z1_JOBN(String f0911Z1_JOBN) {
        this.F0911Z1_JOBN = f0911Z1_JOBN;
    }

    public String getF0911Z1_JOBN() {
        return F0911Z1_JOBN;
    }

    public void setF0911Z1_UPMJ(Date f0911Z1_UPMJ) {
        this.F0911Z1_UPMJ = f0911Z1_UPMJ;
    }

    public void setF0911Z1_UPMJ(Calendar f0911Z1_UPMJ) {
        if (f0911Z1_UPMJ != null)
            this.F0911Z1_UPMJ = f0911Z1_UPMJ.getTime();
    }

    public Date getF0911Z1_UPMJ() {
        return F0911Z1_UPMJ;
    }

    public void setF0911Z1_UPMT(MathNumeric f0911Z1_UPMT) {
        if (f0911Z1_UPMT != null)
            this.F0911Z1_UPMT = f0911Z1_UPMT;
    }

    public void setF0911Z1_UPMT(Integer f0911Z1_UPMT) {
        if (f0911Z1_UPMT != null)
            this.F0911Z1_UPMT = new MathNumeric(f0911Z1_UPMT);
    }

    public MathNumeric getF0911Z1_UPMT() {
        return F0911Z1_UPMT;
    }

    public void setF0911Z1_CRRM(String f0911Z1_CRRM) {
        this.F0911Z1_CRRM = f0911Z1_CRRM;
    }

    public String getF0911Z1_CRRM() {
        return F0911Z1_CRRM;
    }

    public void setF0911Z1_ACR(MathNumeric f0911Z1_ACR) {
        if (f0911Z1_ACR != null)
            this.F0911Z1_ACR = f0911Z1_ACR;
    }

    public void setF0911Z1_ACR(BigDecimal f0911Z1_ACR) {
        if (f0911Z1_ACR != null)
            this.F0911Z1_ACR = new MathNumeric(f0911Z1_ACR);
    }

    public MathNumeric getF0911Z1_ACR() {
        return F0911Z1_ACR;
    }

    public void setF0911Z1_DGM(MathNumeric f0911Z1_DGM) {
        if (f0911Z1_DGM != null)
            this.F0911Z1_DGM = f0911Z1_DGM;
    }

    public void setF0911Z1_DGM(Integer f0911Z1_DGM) {
        if (f0911Z1_DGM != null)
            this.F0911Z1_DGM = new MathNumeric(f0911Z1_DGM);
    }

    public MathNumeric getF0911Z1_DGM() {
        return F0911Z1_DGM;
    }

    public void setF0911Z1_DGD(MathNumeric f0911Z1_DGD) {
        if (f0911Z1_DGD != null)
            this.F0911Z1_DGD = f0911Z1_DGD;
    }

    public void setF0911Z1_DGD(Integer f0911Z1_DGD) {
        if (f0911Z1_DGD != null)
            this.F0911Z1_DGD = new MathNumeric(f0911Z1_DGD);
    }

    public MathNumeric getF0911Z1_DGD() {
        return F0911Z1_DGD;
    }

    public void setF0911Z1_DGY(MathNumeric f0911Z1_DGY) {
        if (f0911Z1_DGY != null)
            this.F0911Z1_DGY = f0911Z1_DGY;
    }

    public void setF0911Z1_DGY(Integer f0911Z1_DGY) {
        if (f0911Z1_DGY != null)
            this.F0911Z1_DGY = new MathNumeric(f0911Z1_DGY.intValue());
    }

    public MathNumeric getF0911Z1_DGY() {
        return F0911Z1_DGY;
    }

    public void setF0911Z1_DICM(MathNumeric f0911Z1_DICM) {
        if (f0911Z1_DICM != null)
            this.F0911Z1_DICM = f0911Z1_DICM;
    }

    public void setF0911Z1_DICM(Integer f0911Z1_DICM) {
        if (f0911Z1_DICM != null)
            this.F0911Z1_DICM = new MathNumeric(f0911Z1_DICM);
    }

    public MathNumeric getF0911Z1_DICM() {
        return F0911Z1_DICM;
    }

    public void setF0911Z1_DICD(MathNumeric f0911Z1_DICD) {
        if (f0911Z1_DICD != null)
            this.F0911Z1_DICD = f0911Z1_DICD;
    }

    public void setF0911Z1_DICD(Integer f0911Z1_DICD) {
        if (f0911Z1_DICD != null)
            this.F0911Z1_DICD = new MathNumeric(f0911Z1_DICD);
    }

    public MathNumeric getF0911Z1_DICD() {
        return F0911Z1_DICD;
    }

    public void setF0911Z1_DICY(MathNumeric f0911Z1_DICY) {
        if (f0911Z1_DICY != null)
            this.F0911Z1_DICY = f0911Z1_DICY;
    }

    public void setF0911Z1_DICY(Integer f0911Z1_DICY) {
        if (f0911Z1_DICY != null)
            this.F0911Z1_DICY = new MathNumeric(f0911Z1_DICY);
    }

    public MathNumeric getF0911Z1_DICY() {
        return F0911Z1_DICY;
    }

    public void setF0911Z1_DIC_Pound(MathNumeric f0911Z1_DIC_Pound) {
        if (f0911Z1_DIC_Pound != null)
            this.F0911Z1_DIC_Pound = f0911Z1_DIC_Pound;
    }

    public void setF0911Z1_DIC_Pound(Integer f0911Z1_DIC_Pound) {
        if (f0911Z1_DIC_Pound != null)
            this.F0911Z1_DIC_Pound = new MathNumeric(f0911Z1_DIC_Pound);
    }

    public MathNumeric getF0911Z1_DIC_Pound() {
        return F0911Z1_DIC_Pound;
    }

    public void setF0911Z1_DSYM(MathNumeric f0911Z1_DSYM) {
        if (f0911Z1_DSYM != null)
            this.F0911Z1_DSYM = f0911Z1_DSYM;
    }

    public void setF0911Z1_DSYM(Integer f0911Z1_DSYM) {
        if (f0911Z1_DSYM != null)
            this.F0911Z1_DSYM = new MathNumeric(f0911Z1_DSYM);
    }

    public MathNumeric getF0911Z1_DSYM() {
        return F0911Z1_DSYM;
    }

    public void setF0911Z1_DSYD(MathNumeric f0911Z1_DSYD) {
        if (f0911Z1_DSYD != null)
            this.F0911Z1_DSYD = f0911Z1_DSYD;
    }

    public void setF0911Z1_DSYD(Integer f0911Z1_DSYD) {
        if (f0911Z1_DSYD != null)
            this.F0911Z1_DSYD = new MathNumeric(f0911Z1_DSYD);
    }

    public MathNumeric getF0911Z1_DSYD() {
        return F0911Z1_DSYD;
    }

    public void setF0911Z1_DSYY(MathNumeric f0911Z1_DSYY) {
        if (f0911Z1_DSYY != null)
            this.F0911Z1_DSYY = f0911Z1_DSYY;
    }

    public void setF0911Z1_DSYY(Integer f0911Z1_DSYY) {
        if (f0911Z1_DSYY != null)
            this.F0911Z1_DSYY = new MathNumeric(f0911Z1_DSYY);
    }

    public MathNumeric getF0911Z1_DSYY() {
        return F0911Z1_DSYY;
    }


    public void setF0911Z1_DKM(MathNumeric f0911Z1_DKM) {
        if (f0911Z1_DKM != null)
            this.F0911Z1_DKM = f0911Z1_DKM;
    }

    public void setF0911Z1_DKM(Integer f0911Z1_DKM) {
        if (f0911Z1_DKM != null)
            this.F0911Z1_DKM = new MathNumeric(f0911Z1_DKM);
    }


    public MathNumeric getF0911Z1_DKM() {
        return F0911Z1_DKM;
    }

    public void setF0911Z1_DKD(MathNumeric f0911Z1_DKD) {
        if (f0911Z1_DKD != null)
            this.F0911Z1_DKD = f0911Z1_DKD;
    }

    public void setF0911Z1_DKD(Integer f0911Z1_DKD) {
        if (f0911Z1_DKD != null)
            this.F0911Z1_DKD = new MathNumeric(f0911Z1_DKD);
    }

    public MathNumeric getF0911Z1_DKD() {
        return F0911Z1_DKD;
    }

    public void setF0911Z1_DKY(MathNumeric f0911Z1_DKY) {
        if (f0911Z1_DKY != null)
            this.F0911Z1_DKY = f0911Z1_DKY;
    }

    public void setF0911Z1_DKY(Integer f0911Z1_DKY) {
        if (f0911Z1_DKY != null)
            this.F0911Z1_DKY = new MathNumeric(f0911Z1_DKY);
    }

    public MathNumeric getF0911Z1_DKY() {
        return F0911Z1_DKY;
    }


    public void setF0911Z1_DSVM(MathNumeric f0911Z1_DSVM) {
        if (f0911Z1_DSVM != null)
            this.F0911Z1_DSVM = f0911Z1_DSVM;
    }

    public void setF0911Z1_DSVM(Integer f0911Z1_DSVM) {
        if (f0911Z1_DSVM != null)
            this.F0911Z1_DSVM = new MathNumeric(f0911Z1_DSVM);
    }

    public MathNumeric getF0911Z1_DSVM() {
        return F0911Z1_DSVM;
    }

    public void setF0911Z1_DSVD(MathNumeric f0911Z1_DSVD) {
        if (f0911Z1_DSVD != null)
            this.F0911Z1_DSVD = f0911Z1_DSVD;
    }

    public void setF0911Z1_DSVD(Integer f0911Z1_DSVD) {
        if (f0911Z1_DSVD != null)
            this.F0911Z1_DSVD = new MathNumeric(f0911Z1_DSVD);
    }

    public MathNumeric getF0911Z1_DSVD() {
        return F0911Z1_DSVD;
    }

    public void setF0911Z1_DSVY(MathNumeric f0911Z1_DSVY) {
        if (f0911Z1_DSVY != null)
            this.F0911Z1_DSVY = f0911Z1_DSVY;
    }

    public void setF0911Z1_DSVY(Integer f0911Z1_DSVY) {
        if (f0911Z1_DSVY != null)
            this.F0911Z1_DSVY = new MathNumeric(f0911Z1_DSVY);
    }

    public MathNumeric getF0911Z1_DSVY() {
        return F0911Z1_DSVY;
    }


    public void setF0911Z1_HDGM(MathNumeric f0911Z1_HDGM) {
        if (f0911Z1_HDGM != null)
            this.F0911Z1_HDGM = f0911Z1_HDGM;
    }

    public void setF0911Z1_HDGM(Integer f0911Z1_HDGM) {
        if (f0911Z1_HDGM != null)
            this.F0911Z1_HDGM = new MathNumeric(f0911Z1_HDGM);
    }

    public MathNumeric getF0911Z1_HDGM() {
        return F0911Z1_HDGM;
    }

    public void setF0911Z1_HDGD(MathNumeric f0911Z1_HDGD) {
        if (f0911Z1_HDGD != null)
            this.F0911Z1_HDGD = f0911Z1_HDGD;
    }

    public void setF0911Z1_HDGD(Integer f0911Z1_HDGD) {
        if (f0911Z1_HDGD != null)
            this.F0911Z1_HDGD = new MathNumeric(f0911Z1_HDGD);
    }

    public MathNumeric getF0911Z1_HDGD() {
        return F0911Z1_HDGD;
    }

    public void setF0911Z1_HDGY(MathNumeric f0911Z1_HDGY) {
        if (f0911Z1_HDGY != null)
            this.F0911Z1_HDGY = f0911Z1_HDGY;
    }

    public void setF0911Z1_HDGY(Integer f0911Z1_HDGY) {
        if (f0911Z1_HDGY != null)
            this.F0911Z1_HDGY = new MathNumeric(f0911Z1_HDGY);
    }

    public MathNumeric getF0911Z1_HDGY() {
        return F0911Z1_HDGY;
    }

    public void setF0911Z1_DKCM(MathNumeric f0911Z1_DKCM) {
        if (f0911Z1_DKCM != null)
            this.F0911Z1_DKCM = f0911Z1_DKCM;
    }

    public void setF0911Z1_DKCM(Integer f0911Z1_DKCM) {
        if (f0911Z1_DKCM != null)
            this.F0911Z1_DKCM = new MathNumeric(f0911Z1_DKCM);
    }

    public MathNumeric getF0911Z1_DKCM() {
        return F0911Z1_DKCM;
    }

    public void setF0911Z1_DKCD(MathNumeric f0911Z1_DKCD) {
        if (f0911Z1_DKCD != null)
            this.F0911Z1_DKCD = f0911Z1_DKCD;
    }

    public void setF0911Z1_DKCD(Integer f0911Z1_DKCD) {
        if (f0911Z1_DKCD != null)
            this.F0911Z1_DKCD = new MathNumeric(f0911Z1_DKCD);
    }

    public MathNumeric getF0911Z1_DKCD() {
        return F0911Z1_DKCD;
    }

    public void setF0911Z1_DKCY(MathNumeric f0911Z1_DKCY) {
        if (f0911Z1_DKCY != null)
            this.F0911Z1_DKCY = f0911Z1_DKCY;
    }

    public void setF0911Z1_DKCY(Integer f0911Z1_DKCY) {
        if (f0911Z1_DKCY != null)
            this.F0911Z1_DKCY = new MathNumeric(f0911Z1_DKCY);
    }

    public MathNumeric getF0911Z1_DKCY() {
        return F0911Z1_DKCY;
    }

    public void setF0911Z1_IVDM(MathNumeric f0911Z1_IVDM) {
        if (f0911Z1_IVDM != null)
            this.F0911Z1_IVDM = f0911Z1_IVDM;
    }

    public void setF0911Z1_IVDM(Integer f0911Z1_IVDM) {
        if (f0911Z1_IVDM != null)
            this.F0911Z1_IVDM = new MathNumeric(f0911Z1_IVDM);
    }

    public MathNumeric getF0911Z1_IVDM() {
        return F0911Z1_IVDM;
    }

    public void setF0911Z1_IVDD(MathNumeric f0911Z1_IVDD) {
        if (f0911Z1_IVDD != null)
            this.F0911Z1_IVDD = f0911Z1_IVDD;
    }

    public void setF0911Z1_IVDD(Integer f0911Z1_IVDD) {
        if (f0911Z1_IVDD != null)
            this.F0911Z1_IVDD = new MathNumeric(f0911Z1_IVDD);
    }

    public MathNumeric getF0911Z1_IVDD() {
        return F0911Z1_IVDD;
    }

    public void setF0911Z1_IVDY(MathNumeric f0911Z1_IVDY) {
        if (f0911Z1_IVDY != null)
            this.F0911Z1_IVDY = f0911Z1_IVDY;
    }

    public void setF0911Z1_IVDY(Integer f0911Z1_IVDY) {
        if (f0911Z1_IVDY != null)
            this.F0911Z1_IVDY = new MathNumeric(f0911Z1_IVDY);
    }

    public MathNumeric getF0911Z1_IVDY() {
        return F0911Z1_IVDY;
    }

    public void setF0911Z1_ABR1(String f0911Z1_ABR1) {
        this.F0911Z1_ABR1 = f0911Z1_ABR1;
    }

    public String getF0911Z1_ABR1() {
        return F0911Z1_ABR1;
    }

    public void setF0911Z1_ABR2(String f0911Z1_ABR2) {
        this.F0911Z1_ABR2 = f0911Z1_ABR2;
    }

    public String getF0911Z1_ABR2() {
        return F0911Z1_ABR2;
    }

    public void setF0911Z1_ABR3(String f0911Z1_ABR3) {
        this.F0911Z1_ABR3 = f0911Z1_ABR3;
    }

    public String getF0911Z1_ABR3() {
        return F0911Z1_ABR3;
    }

    public void setF0911Z1_ABR4(String f0911Z1_ABR4) {
        this.F0911Z1_ABR4 = f0911Z1_ABR4;
    }

    public String getF0911Z1_ABR4() {
        return F0911Z1_ABR4;
    }

    public void setF0911Z1_ABT1(String f0911Z1_ABT1) {
        this.F0911Z1_ABT1 = f0911Z1_ABT1;
    }

    public String getF0911Z1_ABT1() {
        return F0911Z1_ABT1;
    }

    public void setF0911Z1_ABT2(String f0911Z1_ABT2) {
        this.F0911Z1_ABT2 = f0911Z1_ABT2;
    }

    public String getF0911Z1_ABT2() {
        return F0911Z1_ABT2;
    }

    public void setF0911Z1_ABT3(String f0911Z1_ABT3) {
        this.F0911Z1_ABT3 = f0911Z1_ABT3;
    }

    public String getF0911Z1_ABT3() {
        return F0911Z1_ABT3;
    }

    public void setF0911Z1_ABT4(String f0911Z1_ABT4) {
        this.F0911Z1_ABT4 = f0911Z1_ABT4;
    }

    public String getF0911Z1_ABT4() {
        return F0911Z1_ABT4;
    }

    public void setF0911Z1_ITM(MathNumeric f0911Z1_ITM) {
        if (f0911Z1_ITM != null)
            this.F0911Z1_ITM = f0911Z1_ITM;
    }

    public void setF0911Z1_ITM(Integer f0911Z1_ITM) {
        if (f0911Z1_ITM != null)
            this.F0911Z1_ITM = new MathNumeric(f0911Z1_ITM);
    }

    public MathNumeric getF0911Z1_ITM() {
        return F0911Z1_ITM;
    }

    public void setF0911Z1_PM01(String f0911Z1_PM01) {
        this.F0911Z1_PM01 = f0911Z1_PM01;
    }

    public String getF0911Z1_PM01() {
        return F0911Z1_PM01;
    }

    public void setF0911Z1_PM02(String f0911Z1_PM02) {
        this.F0911Z1_PM02 = f0911Z1_PM02;
    }

    public String getF0911Z1_PM02() {
        return F0911Z1_PM02;
    }

    public void setF0911Z1_PM03(String f0911Z1_PM03) {
        this.F0911Z1_PM03 = f0911Z1_PM03;
    }

    public String getF0911Z1_PM03() {
        return F0911Z1_PM03;
    }

    public void setF0911Z1_PM04(String f0911Z1_PM04) {
        this.F0911Z1_PM04 = f0911Z1_PM04;
    }

    public String getF0911Z1_PM04() {
        return F0911Z1_PM04;
    }

    public void setF0911Z1_PM05(String f0911Z1_PM05) {
        this.F0911Z1_PM05 = f0911Z1_PM05;
    }

    public String getF0911Z1_PM05() {
        return F0911Z1_PM05;
    }

    public void setF0911Z1_PM06(String f0911Z1_PM06) {
        this.F0911Z1_PM06 = f0911Z1_PM06;
    }

    public String getF0911Z1_PM06() {
        return F0911Z1_PM06;
    }

    public void setF0911Z1_PM07(String f0911Z1_PM07) {
        this.F0911Z1_PM07 = f0911Z1_PM07;
    }

    public String getF0911Z1_PM07() {
        return F0911Z1_PM07;
    }

    public void setF0911Z1_PM08(String f0911Z1_PM08) {
        this.F0911Z1_PM08 = f0911Z1_PM08;
    }

    public String getF0911Z1_PM08() {
        return F0911Z1_PM08;
    }

    public void setF0911Z1_PM09(String f0911Z1_PM09) {
        this.F0911Z1_PM09 = f0911Z1_PM09;
    }

    public String getF0911Z1_PM09() {
        return F0911Z1_PM09;
    }

    public void setF0911Z1_PM10(String f0911Z1_PM10) {
        this.F0911Z1_PM10 = f0911Z1_PM10;
    }

    public String getF0911Z1_PM10() {
        return F0911Z1_PM10;
    }

    public void setF0911Z1_BCRC(String f0911Z1_BCRC) {
        this.F0911Z1_BCRC = f0911Z1_BCRC;
    }

    public String getF0911Z1_BCRC() {
        return F0911Z1_BCRC;
    }

    public void setF0911Z1_EXR1(String f0911Z1_EXR1) {
        this.F0911Z1_EXR1 = f0911Z1_EXR1;
    }

    public String getF0911Z1_EXR1() {
        return F0911Z1_EXR1;
    }

    public void setF0911Z1_TXA1(String f0911Z1_TXA1) {
        this.F0911Z1_TXA1 = f0911Z1_TXA1;
    }

    public String getF0911Z1_TXA1() {
        return F0911Z1_TXA1;
    }

    public void setF0911Z1_TXITM(MathNumeric f0911Z1_TXITM) {
        if (f0911Z1_TXITM != null)
            this.F0911Z1_TXITM = f0911Z1_TXITM;
    }

    public void setF0911Z1_TXITM(Integer f0911Z1_TXITM) {
        if (f0911Z1_TXITM != null)
            this.F0911Z1_TXITM = new MathNumeric(f0911Z1_TXITM);
    }

    public MathNumeric getF0911Z1_TXITM() {
        return F0911Z1_TXITM;
    }

    public void setF0911Z1_ACTB(String f0911Z1_ACTB) {
        this.F0911Z1_ACTB = f0911Z1_ACTB;
    }

    public String getF0911Z1_ACTB() {
        return F0911Z1_ACTB;
    }

    public void setF0911Z1_STAM(MathNumeric f0911Z1_STAM) {
        if (f0911Z1_STAM != null)
            this.F0911Z1_STAM = f0911Z1_STAM;
    }

    public void setF0911Z1_STAM(BigDecimal f0911Z1_STAM) {
        if (f0911Z1_STAM != null)
            this.F0911Z1_STAM = new MathNumeric(f0911Z1_STAM);
    }

    public MathNumeric getF0911Z1_STAM() {
        return F0911Z1_STAM;
    }

    public void setF0911Z1_CTAM(MathNumeric f0911Z1_CTAM) {
        if (f0911Z1_CTAM != null)
            this.F0911Z1_CTAM = f0911Z1_CTAM;
    }

    public void setF0911Z1_CTAM(BigDecimal f0911Z1_CTAM) {
        if (f0911Z1_CTAM != null)
            this.F0911Z1_CTAM = new MathNumeric(f0911Z1_CTAM);
    }

    public MathNumeric getF0911Z1_CTAM() {
        return F0911Z1_CTAM;
    }

    public void setF0911Z1_AG(MathNumeric f0911Z1_AG) {
        if (f0911Z1_AG != null)
            this.F0911Z1_AG = f0911Z1_AG;
    }

    public void setF0911Z1_AG(BigDecimal f0911Z1_AG) {
        if (f0911Z1_AG != null)
            this.F0911Z1_AG = new MathNumeric(f0911Z1_AG);
    }

    public MathNumeric getF0911Z1_AG() {
        return F0911Z1_AG;
    }

    public void setF0911Z1_AGF(MathNumeric f0911Z1_AGF) {
        if (f0911Z1_AGF != null)
            this.F0911Z1_AGF = f0911Z1_AGF;
    }

    public void setF0911Z1_AGF(BigDecimal f0911Z1_AGF) {
        if (f0911Z1_AGF != null)
            this.F0911Z1_AGF = new MathNumeric(f0911Z1_AGF);
    }

    public MathNumeric getF0911Z1_AGF() {
        return F0911Z1_AGF;
    }

    public void setF0911Z1_TKTX(String f0911Z1_TKTX) {
        this.F0911Z1_TKTX = f0911Z1_TKTX;
    }

    public String getF0911Z1_TKTX() {
        return F0911Z1_TKTX;
    }

    public void setF0911Z1_DLNID(MathNumeric f0911Z1_DLNID) {
        if (f0911Z1_DLNID != null)
            this.F0911Z1_DLNID = f0911Z1_DLNID;
    }

    public void setF0911Z1_DLNID(BigDecimal f0911Z1_DLNID) {
        if (f0911Z1_DLNID != null)
            this.F0911Z1_DLNID = new MathNumeric(f0911Z1_DLNID);
    }

    public MathNumeric getF0911Z1_DLNID() {
        return F0911Z1_DLNID;
    }

    public void setF0911Z1_CKNU(String f0911Z1_CKNU) {
        this.F0911Z1_CKNU = f0911Z1_CKNU;
    }

    public String getF0911Z1_CKNU() {
        return F0911Z1_CKNU;
    }

    public void setF0911Z1_BUPC(String f0911Z1_BUPC) {
        this.F0911Z1_BUPC = f0911Z1_BUPC;
    }

    public String getF0911Z1_BUPC() {
        return F0911Z1_BUPC;
    }

    public void setF0911Z1_AHBU(String f0911Z1_AHBU) {
        this.F0911Z1_AHBU = f0911Z1_AHBU;
    }

    public String getF0911Z1_AHBU() {
        return F0911Z1_AHBU;
    }

    public void setF0911Z1_EPGC(String f0911Z1_EPGC) {
        this.F0911Z1_EPGC = f0911Z1_EPGC;
    }

    public String getF0911Z1_EPGC() {
        return F0911Z1_EPGC;
    }

    public void setF0911Z1_JPGC(String f0911Z1_JPGC) {
        this.F0911Z1_JPGC = f0911Z1_JPGC;
    }

    public String getF0911Z1_JPGC() {
        return F0911Z1_JPGC;
    }

    public void setF0911Z1_REG_Pound(MathNumeric f0911Z1_REG_Pound) {
        if (f0911Z1_REG_Pound != null)
            this.F0911Z1_REG_Pound = f0911Z1_REG_Pound;
    }

    public void setF0911Z1_REG_Pound(Integer f0911Z1_REG_Pound) {
        if (f0911Z1_REG_Pound != null)
            this.F0911Z1_REG_Pound = new MathNumeric(f0911Z1_REG_Pound);
    }

    public MathNumeric getF0911Z1_REG_Pound() {
        return F0911Z1_REG_Pound;
    }

    public void setF0911Z1_DG_Pound(MathNumeric f0911Z1_DG_Pound) {
        if (f0911Z1_DG_Pound != null)
            this.F0911Z1_DG_Pound = f0911Z1_DG_Pound;
    }

    public void setF0911Z1_DG_Pound(Integer f0911Z1_DG_Pound) {
        if (f0911Z1_DG_Pound != null)
            this.F0911Z1_DG_Pound = new MathNumeric(f0911Z1_DG_Pound);
    }

    public MathNumeric getF0911Z1_DG_Pound() {
        return F0911Z1_DG_Pound;
    }

    public void setF0911Z1_DSY_Pound(MathNumeric f0911Z1_DSY_Pound) {
        if (f0911Z1_DSY_Pound != null)
            this.F0911Z1_DSY_Pound = f0911Z1_DSY_Pound;
    }

    public void setF0911Z1_DSY_Pound(Integer f0911Z1_DSY_Pound) {
        if (f0911Z1_DSY_Pound != null)
            this.F0911Z1_DSY_Pound = new MathNumeric(f0911Z1_DSY_Pound);
    }

    public MathNumeric getF0911Z1_DSY_Pound() {
        return F0911Z1_DSY_Pound;
    }

    public void setF0911Z1_DK_Pound(MathNumeric f0911Z1_DK_Pound) {
        if (f0911Z1_DK_Pound != null)
            this.F0911Z1_DK_Pound = f0911Z1_DK_Pound;
    }

    public void setF0911Z1_DK_Pound(Integer f0911Z1_DK_Pound) {
        if (f0911Z1_DK_Pound != null)
            this.F0911Z1_DK_Pound = new MathNumeric(f0911Z1_DK_Pound);
    }

    public MathNumeric getF0911Z1_DK_Pound() {
        return F0911Z1_DK_Pound;
    }

    public void setF0911Z1_DSV_Pound(MathNumeric f0911Z1_DSV_Pound) {
        if (f0911Z1_DSV_Pound != null)
            this.F0911Z1_DSV_Pound = f0911Z1_DSV_Pound;
    }

    public void setF0911Z1_DSV_Pound(Integer f0911Z1_DSV_Pound) {
        if (f0911Z1_DSV_Pound != null)
            this.F0911Z1_DSV_Pound = new MathNumeric(f0911Z1_DSV_Pound);
    }

    public MathNumeric getF0911Z1_DSV_Pound() {
        return F0911Z1_DSV_Pound;
    }

    public void setF0911Z1_HDG_Pound(MathNumeric f0911Z1_HDG_Pound) {
        if (f0911Z1_HDG_Pound != null)
            this.F0911Z1_HDG_Pound = f0911Z1_HDG_Pound;
    }

    public void setF0911Z1_HDG_Pound(Integer f0911Z1_HDG_Pound) {
        if (f0911Z1_HDG_Pound != null)
            this.F0911Z1_HDG_Pound = new MathNumeric(f0911Z1_HDG_Pound);
    }

    public MathNumeric getF0911Z1_HDG_Pound() {
        return F0911Z1_HDG_Pound;
    }

    public void setF0911Z1_DKC_Pound(MathNumeric f0911Z1_DKC_Pound) {
        if (f0911Z1_DKC_Pound != null)
            this.F0911Z1_DKC_Pound = f0911Z1_DKC_Pound;
    }

    public void setF0911Z1_DKC_Pound(Integer f0911Z1_DKC_Pound) {
        if (f0911Z1_DKC_Pound != null)
            this.F0911Z1_DKC_Pound = new MathNumeric(f0911Z1_DKC_Pound);
    }

    public MathNumeric getF0911Z1_DKC_Pound() {
        return F0911Z1_DKC_Pound;
    }

    public void setF0911Z1_IVD_Pound(MathNumeric f0911Z1_IVD_Pound) {
        if (f0911Z1_IVD_Pound != null)
            this.F0911Z1_IVD_Pound = f0911Z1_IVD_Pound;
    }

    public void setF0911Z1_IVD_Pound(Integer f0911Z1_IVD_Pound) {
        if (f0911Z1_IVD_Pound != null)
            this.F0911Z1_IVD_Pound = new MathNumeric(f0911Z1_IVD_Pound);
    }

    public MathNumeric getF0911Z1_IVD_Pound() {
        return F0911Z1_IVD_Pound;
    }
}
