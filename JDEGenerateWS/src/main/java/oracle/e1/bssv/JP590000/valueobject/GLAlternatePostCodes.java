package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import oracle.e1.bssvfoundation.base.ValueObject;

public class GLAlternatePostCodes extends ValueObject implements Serializable {

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
    private String postCodeAlternate1 = null;

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
    private String postCodeAlternate2 = null;

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
    private String postCodeAlternate3 = null;

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
    private String postCodeAlternate4 = null;

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
    private String postCodeAlternate5 = null;

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
    private String postCodeAlternate6 = null;

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
    private String postCodeAlternate7 = null;

    /**
     * Billing Control<br>
     * <p>
     * Billing Control for Service Billing.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALT8 <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String postCodeAlternate8 = null;

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
    private String postCodeAlternate9 = null;

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
    private String postCodeAlternate0 = null;

    /**
     * G/L Posting Code - Alternate T<br>
     * <p>
     * G/L Posting Code - Alternative T - Reserved for future use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTT <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String postCodeAlternateT = null;

    /**
     * G/L Posting Code - Alternate U<br>
     * <p>
     * G/L Posting Code - Alternative U - Reserved for future use.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTU <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String postCodeAlternateU = null;

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
    private String postCodeAlternateV = null;

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
    private String postCodeAlternateW = null;

    /**
     * Consumption Tax Cross Reference<br>
     * <p>
     * Field used to denote tax.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ALTX <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String postCodeAlternateX = null;

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
    private String postCodeAlternateZ = null;
    
    public GLAlternatePostCodes() {
    }

    public void setPostCodeAlternate1(String postCodeAlternate1) {
        this.postCodeAlternate1 = postCodeAlternate1;
    }

    public String getPostCodeAlternate1() {
        return postCodeAlternate1;
    }

    public void setPostCodeAlternate2(String postCodeAlternate2) {
        this.postCodeAlternate2 = postCodeAlternate2;
    }

    public String getPostCodeAlternate2() {
        return postCodeAlternate2;
    }

    public void setPostCodeAlternate3(String postCodeAlternate3) {
        this.postCodeAlternate3 = postCodeAlternate3;
    }

    public String getPostCodeAlternate3() {
        return postCodeAlternate3;
    }

    public void setPostCodeAlternate4(String postCodeAlternate4) {
        this.postCodeAlternate4 = postCodeAlternate4;
    }

    public String getPostCodeAlternate4() {
        return postCodeAlternate4;
    }

    public void setPostCodeAlternate5(String postCodeAlternate5) {
        this.postCodeAlternate5 = postCodeAlternate5;
    }

    public String getPostCodeAlternate5() {
        return postCodeAlternate5;
    }

    public void setPostCodeAlternate6(String postCodeAlternate6) {
        this.postCodeAlternate6 = postCodeAlternate6;
    }

    public String getPostCodeAlternate6() {
        return postCodeAlternate6;
    }

    public void setPostCodeAlternate7(String postCodeAlternate7) {
        this.postCodeAlternate7 = postCodeAlternate7;
    }

    public String getPostCodeAlternate7() {
        return postCodeAlternate7;
    }

    public void setPostCodeAlternate8(String postCodeAlternate8) {
        this.postCodeAlternate8 = postCodeAlternate8;
    }

    public String getPostCodeAlternate8() {
        return postCodeAlternate8;
    }

    public void setPostCodeAlternate9(String postCodeAlternate9) {
        this.postCodeAlternate9 = postCodeAlternate9;
    }

    public String getPostCodeAlternate9() {
        return postCodeAlternate9;
    }

    public void setPostCodeAlternate0(String postCodeAlternate0) {
        this.postCodeAlternate0 = postCodeAlternate0;
    }

    public String getPostCodeAlternate0() {
        return postCodeAlternate0;
    }

    public void setPostCodeAlternateT(String postCodeAlternateT) {
        this.postCodeAlternateT = postCodeAlternateT;
    }

    public String getPostCodeAlternateT() {
        return postCodeAlternateT;
    }

    public void setPostCodeAlternateU(String postCodeAlternateU) {
        this.postCodeAlternateU = postCodeAlternateU;
    }

    public String getPostCodeAlternateU() {
        return postCodeAlternateU;
    }

    public void setPostCodeAlternateV(String postCodeAlternateV) {
        this.postCodeAlternateV = postCodeAlternateV;
    }

    public String getPostCodeAlternateV() {
        return postCodeAlternateV;
    }

    public void setPostCodeAlternateW(String postCodeAlternateW) {
        this.postCodeAlternateW = postCodeAlternateW;
    }

    public String getPostCodeAlternateW() {
        return postCodeAlternateW;
    }

    public void setPostCodeAlternateX(String postCodeAlternateX) {
        this.postCodeAlternateX = postCodeAlternateX;
    }

    public String getPostCodeAlternateX() {
        return postCodeAlternateX;
    }

    public void setPostCodeAlternateZ(String postCodeAlternateZ) {
        this.postCodeAlternateZ = postCodeAlternateZ;
    }

    public String getPostCodeAlternateZ() {
        return postCodeAlternateZ;
    }
}
