package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Calendar;

import java.util.Date;

import oracle.e1.bssv.J0900002.valueobject.InternalInsertBatchJournaleEntryStagingFields;
import oracle.e1.bssv.util.J0100010.EntityProcessor;
import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssv.util.J0100010.valueobject.Entity;
import oracle.e1.bssv.util.J0900010.valueobject.GLAccount;
import oracle.e1.bssv.util.J0900010.valueobject.GLAccountKey;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.connection.IConnection;
import oracle.e1.bssvfoundation.exception.BSSVDataFormatterException;
import oracle.e1.bssvfoundation.util.E1Message;
import oracle.e1.bssvfoundation.util.E1MessageList;
import oracle.e1.bssvfoundation.util.MathNumeric;
/**
 * Published Input object for inserting records to the Batch File ZTable F0911Z1.
 */
public class InsertBatchJournalEntryStagingFields extends ValueObject implements Serializable {
   
    private static String ACCOUNT_MODE_SHORT = "1";
    private static String ACCOUNT_MODE_LONG = "2";
    private static String ACCOUNT_MODE_ALTERNATE = "3";
    private static String ACCOUNT_MODE_BU_OBJ_SUB = "5";
   
    /**
     * EDITransaction()
     */
    private EDITransaction eDITransaction = new EDITransaction();

    /**
     * GeneralLedgerKey()
     */
    private GeneralLedgerKey generalLedgerKey = new GeneralLedgerKey();

    /**
     * GLAccountKey()
     */
    private GLAccountKey gLAccountKey = new GLAccountKey();

    /**
     * GLAccount()
     */
    private GLAccount gLAccount = new GLAccount();

    /**
     * Subledger()
     */
    private Subledger subledger = new Subledger();

    /**
     * Entity()
     */
    private Entity entity = new Entity();

    /**
     * OriginalOrderKey()
     */
    private OriginalOrderKey originalOrderKey = new OriginalOrderKey();

    /**
     * PurchaseOrderKey()
     */
    private PurchaseOrderKey purchaseOrderKey = new PurchaseOrderKey();

    /**
     * GLAlternatePostCodes()
     */
    private GLAlternatePostCodes gLAlternatePostCodes = 
        new GLAlternatePostCodes();

    /**
     * Dates()
     */
    private Dates dates = new Dates();

    /**
     * GLCostObjects()
     */
    private GLCostObjects gLCostObjects = new GLCostObjects();

    /**
     * GLPostCodes()
     */
    private GLPostCodes gLPostCodes = new GLPostCodes();

    /**
     * Tax()
     */
    private Tax tax = new Tax();

 
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
    private String glPostedCode = null;

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
     * EnterpriseOne Next Number: 00 1<br>
     */
    private Integer batchNumber = null;

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
     * EnterpriseOne User Defined Code: 98/IT<br>
     */
    private String batchTypeCode = null;

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
    private Integer timeBatch = null;

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
    private String company = null;

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
    private Integer periodNumber= null;

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
     * EnterpriseOne Edit Rule: RANGE 19 21<br>
     */
    private Integer century = null;

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
    private Integer fiscalYear = null;

    /**
     * Fiscal Quarter (Obsolete)<br>
     * <p>
     * A data field reserved for future use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: FQ <br>
     * EnterpriseOne field length:  4 <br>
     * EnterpriseOne User Defined Code: H09/FQ<br>
     */
    private String fiscalQuarter = null;

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
    private String currencyCodeFrom = null;

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
    private BigDecimal rateExchange = null;

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
    private BigDecimal rateExchangeHistorical = null;

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
    private Calendar dateHistorical = null;

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
    private BigDecimal amountDomestic = null;

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
    private BigDecimal numberOfUnits = null;

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
     * EnterpriseOne User Defined Code: 00/UM<br>
     */
    private String unitOfMeasure = null;

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
    private String glOffsetCode = null;

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
     * EnterpriseOne User Defined Code: H09/RE<br>
     */
    private String isReverseOrVoid = null;

    /**
     * Name - Alpha Explanation<br>
     * <p>
     * A description, remark, explanation, name, or address.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EXA <br>
     * EnterpriseOne field length:  30 <br>
     */
    private String nameExplanationAlpha = null;

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
    private String nameExplanationRemark = null;

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
    private String reference1 = null;

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
    private String reference2 = null;

    /**
     * Reference 3 - Account Reconciliation<br>
     * <p>
     * Number that provides an audit trail for specific transactions.  Used<br>
     * primarily in the G/L Account Reconciliation program (P09131).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: R3 <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne Next Number: 09 6<br>
     */
    private String reference3 = null;

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
    private String checkNumber = null;

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
    private String serialNumber = null;

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
     * EnterpriseOne User Defined Code: 09/BR<br>
     */
    private String postCodeBatchRearEnd = null;

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
     * EnterpriseOne User Defined Code: 09/RC<br>
     */
    private String reconciledCode = null;

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
    private String isSummarized = null;

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
    private String purgeCode = null;

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
     * EnterpriseOne Edit Rule: VALUE YN10 <br>
     */
    private String flag1099 = null;

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
     * EnterpriseOne Edit Rule: VALUE YN10 <br>
     */
    private String deleteNotAllowed = null;

    /**
     * Client Free Form - Alternate 1<br>
     * <p>
     * Free form for client use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CFF1 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String clientFreeForm1 = null;

    /**
     * Client Free Form - Alternate 2<br>
     * <p>
     * Free form for client use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CFF2 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String clientFreeForm2 = null;

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
    private String postCodeLeaseCostLedger = null;

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
     * EnterpriseOne User Defined Code: 98/BC<br>
     */
    private String billCode = null;

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
    private String supplierInvoiceNumber = null;

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
     * EnterpriseOne User Defined Code: 00/W1<br>
     */
    private String categoryWorkOrderCode  = null;

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
     * EnterpriseOne User Defined Code: 00/DT<br>
     */
    private String orderTypeCode = null;

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
    private Integer fiscalYearWeekly = null;

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
    private Integer fiscalPeriodWeekly = null;

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
    private String paymentFinal = null;

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
    private BigDecimal sequenceNumber = null;

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
     * EnterpriseOne User Defined Code: 06/G<br>
     */
    private String jobCategory = null;

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
     * EnterpriseOne User Defined Code: 06/GS<br>
     */
    private String jobStep = null;

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
     * EnterpriseOne Edit Rule: SERVER X0006 <br>
     */
    private String businessUnitHome = null;

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
    private Integer divisionOfInterest = null;

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
     * EnterpriseOne Edit Rule: NE  <br>
     */
    private String leaseNumberAlternate= null;

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
     * EnterpriseOne User Defined Code: 00/AL<br>
     */
    private String leaseTypeAlternateCode = null;

    /**
     * Transaction Originator<br>
     * <p>
     * The person who originally entered the transaction.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TORG <br>
     * EnterpriseOne field length:  10 <br>
     */
    private String transactionOriginator = null;

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
    private Integer registrationNumber = null;

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
    private Integer paymentId = null;

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
     * EnterpriseOne User Defined Code: H00/CY<br>
     * EnterpriseOne Default Value: F<br>
     */
    private String currencyModeCode = null;

    /**
     * Amount - Foreign Currency<br>
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
    private BigDecimal amountForeign = null;

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
    private Integer itemId = null;

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
    private String currencyCodeBase = null;

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
    private String costingActivityBasedCode = null;

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
    private BigDecimal amountGrossDomestic = null;

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
    private BigDecimal amountGrossForeign = null;

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
    private BigDecimal distributionLineNumber = null;

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
    private String receiptNumber = null;

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
    private String postedBurdeningCode = null;

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
    private String businessUnitHomeAlternate = null;

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
     * EnterpriseOne User Defined Code: 00/12<br>
     */
    private String employeePoolGroupingCode = null;

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
     * EnterpriseOne User Defined Code: 00/12<br>
     */
    private String jobPoolGroupingCode = null;


    public InsertBatchJournalEntryStagingFields() {
    }

    public void setEDITransaction(EDITransaction eDITransaction) {
        if (eDITransaction != null)
            this.eDITransaction = eDITransaction;
        else
            this.eDITransaction = new EDITransaction();
    }

    public EDITransaction getEDITransaction() {
        return eDITransaction;
    }

    public void setGeneralLedgerKey(GeneralLedgerKey generalLedgerKey) {
        if (generalLedgerKey != null)
            this.generalLedgerKey = generalLedgerKey;
        else
            this.generalLedgerKey = new GeneralLedgerKey();
    }

    public GeneralLedgerKey getGeneralLedgerKey() {
        return generalLedgerKey;
    }

    public void setGLAccountKey(GLAccountKey gLAccountKey) {
        if (gLAccountKey != null)
            this.gLAccountKey = gLAccountKey;
        else
            this.gLAccountKey = new GLAccountKey();
    }

    public GLAccountKey getGLAccountKey() {
        return gLAccountKey;
    }

    public void setGLAccount(GLAccount gLAccount) {
        if (gLAccount != null)
            this.gLAccount = gLAccount;
        else
            this.gLAccount = new GLAccount();
    }

    public GLAccount getGLAccount() {
        return gLAccount;
    }

    public void setSubledger(Subledger subledger) {
        if (subledger != null)
            this.subledger = subledger;
        else
            this.subledger = new Subledger();
    }

    public Subledger getSubledger() {
        return subledger;
    }

    public void setEntity(Entity entity) {
        if (entity != null)
            this.entity = entity;
        else
            this.entity = new Entity();
    }

    public Entity getEntity() {
        return entity;
    }

    public void setOriginalOrderKey(OriginalOrderKey originalOrderKey) {
        if (originalOrderKey != null)
            this.originalOrderKey = originalOrderKey;
        else
            this.originalOrderKey = new OriginalOrderKey();
    }

    public OriginalOrderKey getOriginalOrderKey() {
        return originalOrderKey;
    }

    public void setPurchaseOrderKey(PurchaseOrderKey purchaseOrderKey) {
        if (purchaseOrderKey != null)
            this.purchaseOrderKey = purchaseOrderKey;
        else
            this.purchaseOrderKey = new PurchaseOrderKey();
    }

    public PurchaseOrderKey getPurchaseOrderKey() {
        return purchaseOrderKey;
    }

    public void setGLAlternatePostCodes(GLAlternatePostCodes gLAlternatePostCodes) {
        if (gLAlternatePostCodes != null)
            this.gLAlternatePostCodes = gLAlternatePostCodes;
        else
            this.gLAlternatePostCodes = new GLAlternatePostCodes();
    }

    public GLAlternatePostCodes getGLAlternatePostCodes() {
        return gLAlternatePostCodes;
    }

    public void setDates(Dates dates) {
        if (dates != null)
            this.dates = dates;
        else
            this.dates = new Dates();
    }

    public Dates getDates() {
        return dates;
    }

    public void setGLCostObjects(GLCostObjects gLCostObjects) {
        if (gLCostObjects != null)
            this.gLCostObjects = gLCostObjects;
        else
            this.gLCostObjects = new GLCostObjects();
    }

    public GLCostObjects getGLCostObjects() {
        return gLCostObjects;
    }

    public void setGLPostCodes(GLPostCodes gLPostCodes) {
        if (gLPostCodes != null)
            this.gLPostCodes = gLPostCodes;
        else
            this.gLPostCodes = new GLPostCodes();
    }

    public GLPostCodes getGLPostCodes() {
        return gLPostCodes;
    }

    public void setTax(Tax tax) {
        if (tax != null)
            this.tax = tax;
        else
            this.tax = new Tax();
    }

    public Tax getTax() {
        return tax;
    }


    public void setGlPostedCode(String glPostedCode) {
        this.glPostedCode = glPostedCode;
    }

    public String getGlPostedCode() {
        return glPostedCode;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setBatchNumber(MathNumeric batchNumber) {
        if(batchNumber != null)
            this.batchNumber = new Integer(batchNumber.intValue());
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchTypeCode(String batchTypeCode) {
        this.batchTypeCode = batchTypeCode;
    }

    public String getBatchTypeCode() {
        return batchTypeCode;
    }

    public void setTimeBatch(Integer timeBatch) {
        this.timeBatch = timeBatch;
    }

    public void setTimeBatch(MathNumeric timeBatch) {
        this.timeBatch = new Integer(timeBatch.intValue());
    }

    public Integer getTimeBatch() {
        return timeBatch;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setPeriodNumber(Integer periodNumber) {
        this.periodNumber = periodNumber;
    }

    public void setPeriodNumber(MathNumeric periodNumber) {
        this.periodNumber = new Integer(periodNumber.intValue());
    }

    public Integer getPeriodNumber() {
        return periodNumber;
    }

    public void setCentury(Integer century) {
        this.century = century;
    }

    public void setCentury(MathNumeric century) {
        this.century = new Integer(century.intValue());
    }

    public Integer getCentury() {
        return century;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public void setFiscalYear(MathNumeric fiscalYear) {
        this.fiscalYear = new Integer(fiscalYear.intValue());
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalQuarter(String fiscalQuarter) {
        this.fiscalQuarter = fiscalQuarter;
    }

    public String getFiscalQuarter() {
        return fiscalQuarter;
    }

    public void setCurrencyCodeFrom(String currencyCodeFrom) {
        this.currencyCodeFrom = currencyCodeFrom;
    }

    public String getCurrencyCodeFrom() {
        return currencyCodeFrom;
    }

    public void setRateExchange(BigDecimal rateExchange) {
        this.rateExchange = rateExchange;
    }

    public void setRateExchange(MathNumeric rateExchange) {
        this.rateExchange = rateExchange.asBigDecimal();
    }


    public BigDecimal getRateExchange() {
        return rateExchange;
    }

    public void setRateExchangeHistorical(BigDecimal rateExchangeHistorical) {
        this.rateExchangeHistorical = rateExchangeHistorical;
    }

    public void setRateExchangeHistorical(MathNumeric rateExchangeHistorical) {
        this.rateExchangeHistorical = rateExchangeHistorical.asBigDecimal();
    }

    public BigDecimal getRateExchangeHistorical() {
        return rateExchangeHistorical;
    }

    public void setDateHistorical(Calendar dateHistorical) {
        this.dateHistorical = dateHistorical;
    }

    public void setDateHistorical(Date dateHistorical) {       
        if(dateHistorical != null){
            Calendar effDate = Calendar.getInstance();
            effDate.setTime(dateHistorical);          
          this.dateHistorical = effDate;
      }
    }

    public Calendar getDateHistorical() {
        return dateHistorical;
    }

    public void setAmountDomestic(BigDecimal amountDomestic) {
        this.amountDomestic = amountDomestic;
    }

    public void setAmountDomestic(MathNumeric amountDomestic) {
        this.amountDomestic = amountDomestic.asBigDecimal();
    }
        
    public BigDecimal getAmountDomestic() {
        return amountDomestic;
    }

    public void setNumberOfUnits(BigDecimal numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public void setNumberOfUnits(MathNumeric numberOfUnits) {
        this.numberOfUnits = numberOfUnits.asBigDecimal();
    }

    public BigDecimal getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setGlOffsetCode(String glOffsetCode) {
        this.glOffsetCode = glOffsetCode;
    }

    public String getGlOffsetCode() {
        return glOffsetCode;
    }

    public void setIsReverseOrVoid(String isReverseOrVoid) {
        this.isReverseOrVoid = isReverseOrVoid;
    }

    public String getIsReverseOrVoid() {
        return isReverseOrVoid;
    }

    public void setNameExplanationAlpha(String nameExplanationAlpha) {
        this.nameExplanationAlpha = nameExplanationAlpha;
    }

    public String getNameExplanationAlpha() {
        return nameExplanationAlpha;
    }

    public void setNameExplanationRemark(String nameExplanationRemark) {
        this.nameExplanationRemark = nameExplanationRemark;
    }

    public String getNameExplanationRemark() {
        return nameExplanationRemark;
    }

    public void setReference1(String reference1) {
        this.reference1 = reference1;
    }

    public String getReference1() {
        return reference1;
    }

    public void setReference2(String reference2) {
        this.reference2 = reference2;
    }

    public String getReference2() {
        return reference2;
    }

    public void setReference3(String reference3) {
        this.reference3 = reference3;
    }

    public String getReference3() {
        return reference3;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setPostCodeBatchRearEnd(String postCodeBatchRearEnd) {
        this.postCodeBatchRearEnd = postCodeBatchRearEnd;
    }

    public String getPostCodeBatchRearEnd() {
        return postCodeBatchRearEnd;
    }

    public void setReconciledCode(String reconciledCode) {
        this.reconciledCode = reconciledCode;
    }

    public String getReconciledCode() {
        return reconciledCode;
    }

    public void setIsSummarized(String isSummarized) {
        this.isSummarized = isSummarized;
    }

    public String getIsSummarized() {
        return isSummarized;
    }

    public void setPurgeCode(String purgeCode) {
        this.purgeCode = purgeCode;
    }

    public String getPurgeCode() {
        return purgeCode;
    }

    public void setFlag1099(String flag1099) {
        this.flag1099 = flag1099;
    }

    public String getFlag1099() {
        return flag1099;
    }

    public void setDeleteNotAllowed(String deleteNotAllowed) {
        this.deleteNotAllowed = deleteNotAllowed;
    }

    public String getDeleteNotAllowed() {
        return deleteNotAllowed;
    }

    public void setClientFreeForm1(String clientFreeForm1) {
        this.clientFreeForm1 = clientFreeForm1;
    }

    public String getClientFreeForm1() {
        return clientFreeForm1;
    }

    public void setClientFreeForm2(String clientFreeForm2) {
        this.clientFreeForm2 = clientFreeForm2;
    }

    public String getClientFreeForm2() {
        return clientFreeForm2;
    }

    public void setPostCodeLeaseCostLedger(String postCodeLeaseCostLedger) {
        this.postCodeLeaseCostLedger = postCodeLeaseCostLedger;
    }

    public String getPostCodeLeaseCostLedger() {
        return postCodeLeaseCostLedger;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setSupplierInvoiceNumber(String supplierInvoiceNumber) {
        this.supplierInvoiceNumber = supplierInvoiceNumber;
    }

    public String getSupplierInvoiceNumber() {
        return supplierInvoiceNumber;
    }

    public void setCategoryWorkOrderCode(String categoryWorkOrderCode) {
        this.categoryWorkOrderCode = categoryWorkOrderCode;
    }

    public String getCategoryWorkOrderCode() {
        return categoryWorkOrderCode;
    }

    public void setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
    }

    public String getOrderTypeCode() {
        return orderTypeCode;
    }

    public void setFiscalYearWeekly(Integer fiscalYearWeekly) {
        this.fiscalYearWeekly = fiscalYearWeekly;
    }

    public void setFiscalYearWeekly(MathNumeric fiscalYearWeekly) {
        this.fiscalYearWeekly = new Integer(fiscalYearWeekly.intValue());
    }

    public Integer getFiscalYearWeekly() {
        return fiscalYearWeekly;
    }

    public void setFiscalPeriodWeekly(Integer fiscalPeriodWeekly) {
        this.fiscalPeriodWeekly = fiscalPeriodWeekly;
    }

    public void setFiscalPeriodWeekly(MathNumeric fiscalPeriodWeekly) {
        this.fiscalPeriodWeekly = new Integer(fiscalPeriodWeekly.intValue());
    }

    public Integer getFiscalPeriodWeekly() {
        return fiscalPeriodWeekly;
    }

    public void setPaymentFinal(String paymentFinal) {
        this.paymentFinal = paymentFinal;
    }

    public String getPaymentFinal() {
        return paymentFinal;
    }

    public void setSequenceNumber(BigDecimal sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setSequenceNumber(MathNumeric sequenceNumber) {
        this.sequenceNumber = sequenceNumber.asBigDecimal();
    }

    public BigDecimal getSequenceNumber() {
        return sequenceNumber;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobStep(String jobStep) {
        this.jobStep = jobStep;
    }

    public String getJobStep() {
        return jobStep;
    }

    public void setBusinessUnitHome(String businessUnitHome) {
        this.businessUnitHome = businessUnitHome;
    }

    public String getBusinessUnitHome() {
        return businessUnitHome;
    }

    public void setDivisionOfInterest(Integer divisionOfInterest) {
        this.divisionOfInterest = divisionOfInterest;
    }

    public void setDivisionOfInterest(MathNumeric divisionOfInterest) {
        this.divisionOfInterest = new Integer(divisionOfInterest.intValue());
    }

    public Integer getDivisionOfInterest() {
        return divisionOfInterest;
    }

    public void setLeaseNumberAlternate(String leaseNumberAlternate) {
        this.leaseNumberAlternate = leaseNumberAlternate;
    }

    public String getLeaseNumberAlternate() {
        return leaseNumberAlternate;
    }

    public void setLeaseTypeAlternateCode(String leaseTypeAlternateCode) {
        this.leaseTypeAlternateCode = leaseTypeAlternateCode;
    }

    public String getLeaseTypeAlternateCode() {
        return leaseTypeAlternateCode;
    }

    public void setTransactionOriginator(String transactionOriginator) {
        this.transactionOriginator = transactionOriginator;
    }

    public String getTransactionOriginator() {
        return transactionOriginator;
    }

    public void setRegistrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setRegistrationNumber(MathNumeric registrationNumber) {
        this.registrationNumber = new Integer(registrationNumber.intValue());
    }

    public Integer getRegistrationNumber() {
        return registrationNumber;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public void setPaymentId(MathNumeric paymentId) {
        this.paymentId = new Integer(paymentId.intValue());
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setCurrencyModeCode(String currencyModeCode) {
        this.currencyModeCode = currencyModeCode;
    }

    public String getCurrencyModeCode() {
        return currencyModeCode;
    }

    public void setAmountForeign(BigDecimal amountForeign) {
        this.amountForeign = amountForeign;
    }

    public void setAmountForeign(MathNumeric amountForeign) {
        this.amountForeign = amountForeign.asBigDecimal();
    }

    public BigDecimal getAmountForeign() {
        return amountForeign;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setItemId(MathNumeric itemId) {
        this.itemId = new Integer(itemId.intValue());
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setCurrencyCodeBase(String currencyCodeBase) {
        this.currencyCodeBase = currencyCodeBase;
    }

    public String getCurrencyCodeBase() {
        return currencyCodeBase;
    }

    public void setCostingActivityBasedCode(String costingActivityBasedCode) {
        this.costingActivityBasedCode = costingActivityBasedCode;
    }

    public String getCostingActivityBasedCode() {
        return costingActivityBasedCode;
    }

    public void setAmountGrossDomestic(BigDecimal amountGrossDomestic) {
        this.amountGrossDomestic = amountGrossDomestic;
    }

    public void setAmountGrossDomestic(MathNumeric amountGrossDomestic) {
        this.amountGrossDomestic = amountGrossDomestic.asBigDecimal();
    }

    public BigDecimal getAmountGrossDomestic() {
        return amountGrossDomestic;
    }

    public void setAmountGrossForeign(BigDecimal amountGrossForeign) {
        this.amountGrossForeign = amountGrossForeign;
    }

    public void setAmountGrossForeign(MathNumeric amountGrossForeign) {
        this.amountGrossForeign = amountGrossForeign.asBigDecimal();
    }

    public BigDecimal getAmountGrossForeign() {
        return amountGrossForeign;
    }

    public void setDistributionLineNumber(BigDecimal distributionLineNumber) {
        this.distributionLineNumber = distributionLineNumber;
    }

    public void setDistributionLineNumber(MathNumeric distributionLineNumber) {
        this.distributionLineNumber = distributionLineNumber.asBigDecimal();
    }

    public BigDecimal getDistributionLineNumber() {
        return distributionLineNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setPostedBurdeningCode(String postedBurdeningCode) {
        this.postedBurdeningCode = postedBurdeningCode;
    }

    public String getPostedBurdeningCode() {
        return postedBurdeningCode;
    }

    public void setBusinessUnitHomeAlternate(String businessUnitHomeAlternate) {
        this.businessUnitHomeAlternate = businessUnitHomeAlternate;
    }

    public String getBusinessUnitHomeAlternate() {
        return businessUnitHomeAlternate;
    }

    public void setEmployeePoolGroupingCode(String employeePoolGroupingCode) {
        this.employeePoolGroupingCode = employeePoolGroupingCode;
    }

    public String getEmployeePoolGroupingCode() {
        return employeePoolGroupingCode;
    }

    public void setJobPoolGroupingCode(String jobPoolGroupingCode) {
        this.jobPoolGroupingCode = jobPoolGroupingCode;
    }

    public String getJobPoolGroupingCode() {
        return jobPoolGroupingCode;
    }
    
    public E1MessageList mapFromPublished(IContext context, 
                                            IConnection connection, 
                                            InternalInsertBatchJournaleEntryStagingFields vo) {
        E1MessageList messages = null;

        vo.setF0911Z1_POST(this.glPostedCode);
        vo.setF0911Z1_ICU(this.batchNumber);
        vo.setF0911Z1_ICUT(this.batchTypeCode);
        vo.setF0911Z1_TICU(this.timeBatch);

        //Format Company
        if (this.company != null) {
            String company = null;
            try {
                company = 
                        context.getBSSVDataFormatter().format(this.company, 
                                                              "CO");
                vo.setF0911Z1_CO(company);
            } catch (BSSVDataFormatterException e) {
                context.getBSSVLogger().app(context, 
                                            "Error when formatting Company.", 
                                            null, this, e);
                //Create new E1 Message with error from exception
                messages.addMessage(new E1Message(context, "002FIS", 
                                                  this.company));
            }
        }

        vo.setF0911Z1_PN(this.periodNumber);
        vo.setF0911Z1_CTRY(this.century);
        vo.setF0911Z1_FY(this.fiscalYear);
        vo.setF0911Z1_FQ(this.fiscalQuarter);
        vo.setF0911Z1_CRCD(this.currencyCodeFrom);
        vo.setF0911Z1_CRR(this.rateExchange);
        vo.setF0911Z1_HCRR(this.rateExchangeHistorical);
        vo.setF0911Z1_HDGJ(this.dateHistorical);
        vo.setF0911Z1_AA(this.amountDomestic);
        vo.setF0911Z1_U(this.numberOfUnits);
        vo.setF0911Z1_UM(this.unitOfMeasure);
        vo.setF0911Z1_GLC(this.glOffsetCode);
        vo.setF0911Z1_RE(this.isReverseOrVoid);
        vo.setF0911Z1_EXA(this.nameExplanationAlpha);
        vo.setF0911Z1_EXR(this.nameExplanationRemark);
        vo.setF0911Z1_R1(this.reference1);
        vo.setF0911Z1_R2(this.reference2);
        vo.setF0911Z1_R3(this.reference3);
        vo.setF0911Z1_CN(this.checkNumber);
        vo.setF0911Z1_DCTO(this.orderTypeCode);
        vo.setF0911Z1_ASID(this.serialNumber);
        vo.setF0911Z1_BRE(this.postCodeBatchRearEnd);
        vo.setF0911Z1_RCND(this.reconciledCode);
        vo.setF0911Z1_SUMM(this.isSummarized);
        vo.setF0911Z1_PRGE(this.purgeCode);
        vo.setF0911Z1_TNN(this.flag1099);
        vo.setF0911Z1_DLNA(this.deleteNotAllowed);
        vo.setF0911Z1_CFF1(this.clientFreeForm1);
        vo.setF0911Z1_CFF2(this.clientFreeForm2);
        vo.setF0911Z1_ASM(this.postCodeLeaseCostLedger);
        vo.setF0911Z1_BC(this.billCode);
        vo.setF0911Z1_VINV(this.supplierInvoiceNumber);
        vo.setF0911Z1_WR01(this.categoryWorkOrderCode);
        vo.setF0911Z1_WY(this.fiscalYearWeekly);
        vo.setF0911Z1_WN(this.fiscalPeriodWeekly);
        vo.setF0911Z1_FNLP(this.paymentFinal);
        vo.setF0911Z1_OPSQ(this.sequenceNumber);
        vo.setF0911Z1_JBCD(this.jobCategory);
        vo.setF0911Z1_JBST(this.jobStep);

        //Format Home Business Unit
        if (this.businessUnitHome != null) {
            String homeBusinessUnit = null;
            try {
                homeBusinessUnit = 
                        context.getBSSVDataFormatter().format(this.businessUnitHome, 
                                                              "HMCU");
                vo.setF0911Z1_HMCU(homeBusinessUnit);
            } catch (BSSVDataFormatterException e) {
                context.getBSSVLogger().app(context, 
                                            "Error when formatting businessUnit.", 
                                            null, this, e);
                //Create new E1 Message with error from exception
                messages.addMessage(new E1Message(context, "002FIS", 
                                                  this.businessUnitHome));
            }
        }

        vo.setF0911Z1_DOI(this.divisionOfInterest);
        vo.setF0911Z1_ALID(this.leaseNumberAlternate);
        vo.setF0911Z1_ALTY(this.leaseTypeAlternateCode);
        vo.setF0911Z1_TORG(this.transactionOriginator);
        vo.setF0911Z1_REG_Pound(this.registrationNumber);
        vo.setF0911Z1_PYID(this.paymentId);
        vo.setF0911Z1_CRRM(this.currencyModeCode);
        vo.setF0911Z1_ACR(this.amountForeign);
        vo.setF0911Z1_BCRC(this.currencyCodeBase);
        vo.setF0911Z1_AG(this.amountGrossDomestic);
        vo.setF0911Z1_AGF(this.amountGrossForeign);
        vo.setF0911Z1_DLNID(this.distributionLineNumber);
        vo.setF0911Z1_CKNU(this.receiptNumber);
        vo.setF0911Z1_BUPC(this.postedBurdeningCode);

        //Format Alternate Business Unit
        if (this.businessUnitHomeAlternate != null) {
            String altBusinessUnit = null;
            try {
                altBusinessUnit = 
                        context.getBSSVDataFormatter().format(this.businessUnitHomeAlternate, 
                                                              "MCU");
                vo.setF0911Z1_AHBU(altBusinessUnit);
            } catch (BSSVDataFormatterException e) {
                context.getBSSVLogger().app(context, 
                                            "Error when formatting alternate businessUnit.", 
                                            null, this, e);
                //Create new E1 Message with error from exception
                messages.addMessage(new E1Message(context, "002FIS", 
                                                  this.businessUnitHomeAlternate));
            }
        }
        vo.setF0911Z1_EPGC(this.employeePoolGroupingCode);
        vo.setF0911Z1_JPGC(this.jobPoolGroupingCode);
        vo.setF0911Z1_ACTB(this.costingActivityBasedCode);
        vo.setF0911Z1_ITM(this.itemId);

        //EDITransaction
        EDITransaction eDITT = this.eDITransaction;

        vo.setF0911Z1_EDUS(eDITT.getEdiUserId());
        vo.setF0911Z1_EDTY(eDITT.getEdiRecordType());
        vo.setF0911Z1_EDSQ(eDITT.getEdiRecordSequence());
        vo.setF0911Z1_EDTN(eDITT.getEdiTransactionNumber());
        vo.setF0911Z1_EDCT(eDITT.getEdiDocumentType());
        vo.setF0911Z1_EDLN(eDITT.getEdiLineNumber());
        vo.setF0911Z1_EDTS(eDITT.getEdiTransactionSetNumber());
        vo.setF0911Z1_EDFT(eDITT.getEdiTranslationFormat());
        vo.setF0911Z1_EDDT(eDITT.getEdiTransmissionDate());
        vo.setF0911Z1_EDER(eDITT.getEdiSendReceiveIndicator());
        vo.setF0911Z1_EDDL(eDITT.getEdiDetailLinesProcessed());
        vo.setF0911Z1_EDSP(eDITT.getEdiSuccessfullyProcessed());
        vo.setF0911Z1_EDTC(eDITT.getEdiTransactionAction());
        vo.setF0911Z1_EDTR(eDITT.getEdiTransactionType());
        vo.setF0911Z1_EDBT(eDITT.getEdiBatchNumber());
        vo.setF0911Z1_EDGL(eDITT.getEdiBatchFileCreateGLRecord());
        vo.setF0911Z1_EDAN(eDITT.getEdiIdDuser());

        //GeneralLedgerKey
        GeneralLedgerKey gLK = this.generalLedgerKey;
        //Format Document Company
        if (gLK.getDocumentCompany() != null) {
            String company = null;
            try {
                company = 
                        context.getBSSVDataFormatter().format(gLK.getDocumentCompany(), 
                                                              "KCO");
                vo.setF0911Z1_KCO(company);
            } catch (BSSVDataFormatterException e) {
                context.getBSSVLogger().app(context, 
                                            "Error when formatting GLK Document Company.", 
                                            null, this, e);
                //Create new E1 Message with error from exception
                messages.addMessage(new E1Message(context, "002FIS", 
                                                  gLK.getDocumentCompany()));
            }
        }
        
        vo.setF0911Z1_DCT(gLK.getDocumentTypeCode());
        vo.setF0911Z1_DOC(gLK.getDocumentNumber());
        vo.setF0911Z1_DGJ(gLK.getDateAccounting());
        vo.setF0911Z1_JELN(gLK.getDocumentLineNumber());
        vo.setF0911Z1_EXTL(gLK.getLineExtensionCode());
        vo.setF0911Z1_LT(gLK.getLedgerTypeCode());
        vo.setF0911Z1_SFX(gLK.getDocumentPayItem());

        //GLAccountKey
        GLAccountKey gLAK = this.gLAccountKey;

        //Formate Account Id
        if (gLAK.getAccountId() != null) {
            String aID = null;
            try {
                aID = 
                        context.getBSSVDataFormatter().format(gLAK.getAccountId(), 
                                                              "AID");
                vo.setF0911Z1_AID(aID);
            } catch (BSSVDataFormatterException e) {
                context.getBSSVLogger().app(context, 
                                            "Error when formatting Account Id.", 
                                            null, this, e);
                //Create new E1 Message with error from exception
                messages.addMessage(new E1Message(context, "002FIS", 
                                                  gLAK.getAccountId()));
            }
        }
        //this.setF0911Z1_AID(gLAK.getAccountId());
        
        if(gLAK.getAccountLongId() != null)
            vo.setF0911Z1_ANI(gLAK.getAccountLongId());
        else
            vo.setF0911Z1_ANI(gLAK.getAccountAlternate());

        //GLAccount
        GLAccount gLA = this.gLAccount;
        //Formate GL Account Business Unit
        if (gLA.getBusinessUnit() != null) {
            String businessUnit = null;
            try {
                businessUnit = 
                        context.getBSSVDataFormatter().format(gLA.getBusinessUnit(), 
                                                              "MCU");
                vo.setF0911Z1_MCU(businessUnit);
            } catch (BSSVDataFormatterException e) {
                context.getBSSVLogger().app(context, 
                                            "Error when formatting businessUnit.", 
                                            null, this, e);
                //Create new E1 Message with error from exception
                messages.addMessage(new E1Message(context, "002FIS", 
                                                  gLA.getBusinessUnit()));
            }
        }
        vo.setF0911Z1_OBJ(gLA.getObjectAccount());
        vo.setF0911Z1_SUB(gLA.getSubsidiary());

        /*
         * Set Account Mode
         * Input of accountId set mode=1,
         * longId set mode =2,
         * alternate set mode = 3 (pass into long field),
         * business unit, and object set mode = 5.
         */
    
    
     if (gLAK.getAccountId() != null)
            vo.setF0911Z1_AM(ACCOUNT_MODE_SHORT);
        else if (gLAK.getAccountLongId() != null)
            vo.setF0911Z1_AM(ACCOUNT_MODE_LONG);
        else if (gLAK.getAccountAlternate() != null)
            vo.setF0911Z1_AM(ACCOUNT_MODE_ALTERNATE);
        else if (gLA.getBusinessUnit() != null && 
                 gLA.getObjectAccount() != null)
            vo.setF0911Z1_AM(ACCOUNT_MODE_BU_OBJ_SUB);

        //Subledger
        vo.setF0911Z1_SBL(this.subledger.getSubledger());
        vo.setF0911Z1_SBLT(this.subledger.getSubledgerTypeCode());

        //Entity
         //Call Entity Processor
         if (this.entity.getEntityLongId() != null || this.entity.getEntityTaxId() != null) {
             messages = EntityProcessor.processEntity(context, connection, this.entity);
             if (messages != null && messages.getE1Messages().length > 0) {
                 messages.setMessagePrefix("Entity Processor");
             }
         }
        vo.setF0911Z1_AN8(this.entity.getEntityId());

        //OriginalOrderKey
        OriginalOrderKey oOK = this.originalOrderKey;

        vo.setF0911Z1_ODOC(oOK.getDocumentNumber());
        vo.setF0911Z1_ODCT(oOK.getDocumentTypeCode());
        vo.setF0911Z1_OSFX(oOK.getDocumentSuffix());
        vo.setF0911Z1_OKCO(oOK.getDocumentCompany());
        
        //PurchaseOrderKey
        PurchaseOrderKey pOK = this.purchaseOrderKey;

        //Format Document Company
        if (pOK.getDocumentCompany() != null) {
            String company = null;
            try {
                company = 
                        context.getBSSVDataFormatter().format(pOK.getDocumentCompany(), 
                                                              "PKCO");
                vo.setF0911Z1_PKCO(company);
            } catch (BSSVDataFormatterException e) {
                context.getBSSVLogger().app(context, 
                                            "Error when formatting POK Document Company.", 
                                            null, this, e);
                //Create new E1 Message with error from exception
                messages.addMessage(new E1Message(context, "002FIS", 
                                                  pOK.getDocumentCompany()));
            }
        }
        
        //SAR 8451207  PDCT and PO need to be swapped.
        //vo.setF0911Z1_PDCT(pOK.getDocumentNumber());
        //vo.setF0911Z1_PO(pOK.getDocumentTypeCode());
        vo.setF0911Z1_PDCT(pOK.getDocumentTypeCode());
        vo.setF0911Z1_PO(pOK.getDocumentNumber());
        //End SAR 8451207
        
        vo.setF0911Z1_PSFX(pOK.getDocumentSuffix());
        vo.setF0911Z1_LNID(pOK.getDocumentLineNumber());

        //GLAlternatePostCodes
        GLAlternatePostCodes gLAPC = this.gLAlternatePostCodes;

        vo.setF0911Z1_ALT1(gLAPC.getPostCodeAlternate1());
        vo.setF0911Z1_ALT2(gLAPC.getPostCodeAlternate2());
        vo.setF0911Z1_ALT3(gLAPC.getPostCodeAlternate3());
        vo.setF0911Z1_ALT4(gLAPC.getPostCodeAlternate4());
        vo.setF0911Z1_ALT5(gLAPC.getPostCodeAlternate5());
        vo.setF0911Z1_ALT6(gLAPC.getPostCodeAlternate6());
        vo.setF0911Z1_ALT7(gLAPC.getPostCodeAlternate7());
        vo.setF0911Z1_ALT8(gLAPC.getPostCodeAlternate8());
        vo.setF0911Z1_ALT9(gLAPC.getPostCodeAlternate9());
        vo.setF0911Z1_ALT0(gLAPC.getPostCodeAlternate0());
        vo.setF0911Z1_ALTT(gLAPC.getPostCodeAlternateT());
        vo.setF0911Z1_ALTU(gLAPC.getPostCodeAlternateU());
        vo.setF0911Z1_ALTV(gLAPC.getPostCodeAlternateV());
        vo.setF0911Z1_ALTW(gLAPC.getPostCodeAlternateW());
        vo.setF0911Z1_ALTX(gLAPC.getPostCodeAlternateX());
        vo.setF0911Z1_ALTZ(gLAPC.getPostCodeAlternateZ());

        //Dates
        Dates dates = this.dates;

        vo.setF0911Z1_DKJ(dates.getDateCheck());
        vo.setF0911Z1_DKC(dates.getDateCheckCleared());
        vo.setF0911Z1_DSVJ(dates.getDateServiceTax());
        vo.setF0911Z1_IVD(dates.getDateInvoice());
        vo.setF0911Z1_DICJ(dates.getDateBatch());
        vo.setF0911Z1_DSYJ(dates.getDateBatchSystem());
        vo.setF0911Z1_DGM(dates.getDateGLMonth());
        vo.setF0911Z1_DGD(dates.getDateGLDay());
        vo.setF0911Z1_DGY(dates.getDateGLYear());
        //renamed variable from # to pound
        vo.setF0911Z1_DG_Pound(dates.getDateGLCentury());
        vo.setF0911Z1_DICM(dates.getDateBatchMonth());
        vo.setF0911Z1_DICD(dates.getDateBatchDay());
        vo.setF0911Z1_DICY(dates.getDateBatchYear());
        //renamed variable from # to pound
        vo.setF0911Z1_DIC_Pound(dates.getDateBatchCentury());
        vo.setF0911Z1_DSYM(dates.getDateBatchSystemMonth());
        vo.setF0911Z1_DSYD(dates.getDateBatchSystemDay());
        vo.setF0911Z1_DSYY(dates.getDateBatchSystemYear());
        //renamed variable from # to pound
        vo.setF0911Z1_DSY_Pound(dates.getDateBatchSystemCentury());
        vo.setF0911Z1_DKM(dates.getDateCheckMonth());
        vo.setF0911Z1_DKD(dates.getDateCheckDay());
        vo.setF0911Z1_DKY(dates.getDateCheckYear());
        //renamed variable from # to pound
        vo.setF0911Z1_DK_Pound(dates.getDateCheckCentury());
        vo.setF0911Z1_DSVM(dates.getDateServiceTaxMonth());
        vo.setF0911Z1_DSVD(dates.getDateServiceTaxDay());
        vo.setF0911Z1_DSVY(dates.getDateServiceTaxYear());
        //renamed variable from # to pound
        vo.setF0911Z1_DSV_Pound(dates.getDateServiceTaxCentury());
        vo.setF0911Z1_HDGM(dates.getDateHistoricalMonth());
        vo.setF0911Z1_HDGD(dates.getDateHistoricalDay());
        vo.setF0911Z1_HDGY(dates.getDateHistoricalYear());
        //renamed variable from # to pound
        vo.setF0911Z1_HDG_Pound(dates.getDateHistoricalCentury());
        vo.setF0911Z1_DKCM(dates.getDateCheckClearedMonth());
        vo.setF0911Z1_DKCD(dates.getDateCheckClearedDay());
        vo.setF0911Z1_DKCY(dates.getDateCheckClearedYear());
        // Integer not a date, it is looking at DCK
        vo.setF0911Z1_DKC_Pound(dates.getDateCheckClearedCentury());
        vo.setF0911Z1_IVDM(dates.getDateInvoiceMonth());
        vo.setF0911Z1_IVDD(dates.getDateInvoiceDay());
        vo.setF0911Z1_IVDY(dates.getDateInvoiceYear());
        // Integer not a date, it is looking at IVD
        vo.setF0911Z1_IVD_Pound(dates.getDateInvoiceCentury());

        //GLCostObjects
        GLCostObjects gLCO = this.gLCostObjects;

        vo.setF0911Z1_ABR1(gLCO.getCostObjectCode1());
        vo.setF0911Z1_ABR2(gLCO.getCostObjectCode2());
        vo.setF0911Z1_ABR3(gLCO.getCostObjectCode3());
        vo.setF0911Z1_ABR4(gLCO.getCostObjectCode4());
        vo.setF0911Z1_ABT1(gLCO.getCostObjectTypeCode1());
        vo.setF0911Z1_ABT2(gLCO.getCostObjectTypeCode2());
        vo.setF0911Z1_ABT3(gLCO.getCostObjectTypeCode3());
        vo.setF0911Z1_ABT4(gLCO.getCostObjectTypeCode4());

        //GLPostCodes
        GLPostCodes gLPC = this.gLPostCodes;

        vo.setF0911Z1_PM01(gLPC.getPostingCode1());
        vo.setF0911Z1_PM02(gLPC.getPostingCode2());
        vo.setF0911Z1_PM03(gLPC.getPostingCode3());
        vo.setF0911Z1_PM04(gLPC.getPostingCode4());
        vo.setF0911Z1_PM05(gLPC.getPostingCode5());
        vo.setF0911Z1_PM06(gLPC.getPostingCode6());
        vo.setF0911Z1_PM07(gLPC.getPostingCode7());
        vo.setF0911Z1_PM08(gLPC.getPostingCode8());
        vo.setF0911Z1_PM09(gLPC.getPostingCode9());
        vo.setF0911Z1_PM10(gLPC.getPostingCode10());

        //Tax
        Tax tax = this.tax;

        vo.setF0911Z1_EXR1(tax.getTaxExplanationCode());
        vo.setF0911Z1_TXA1(tax.getTaxRateAreaCode());
        vo.setF0911Z1_TXITM(tax.getItemIdTax());
        vo.setF0911Z1_STAM(tax.getAmountTaxDomestic());
        vo.setF0911Z1_CTAM(tax.getAmountTaxForeign());
        vo.setF0911Z1_TKTX(tax.getTrackTaxes());

        return messages;
    }
}
