package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;


public class GLAccountResult  extends GetGLAccount implements Serializable  {
 
    public GLAccountResult() {
    }
    
    GetGLCostObjects getGlCostObjects = new GetGLCostObjects() ;
    
    /**
     * Budget Pattern Code<br>
     * <p>
     * A unique three-character code that identifies a seasonal pattern. The<br>
     * system uses this code to calculate budget amounts for an accounting<br>
     * period. For example:<br>
     * <br>
     * DNS<br>
     * Do not spread annual budget among the months. You cannot set up or<br>
     * change this code, defined as part of the system.<br>
     * <br>
     * Blank<br>
     * Spread annual budget evenly across all months. (Blank works this way<br>
     * unless your company changes it to mean otherwise.)  <br>
     * <br>
     *    ***        Represents a blank value.<br>
     *    SUM Spread according to percentages shown below.<br>
     *    WIN Spread according to percentages shown below.<br>
     * <br>
     * SUM (Summer)<br>
     *    Jan.   0%<br>
     *    Feb.   2%<br>
     *    ....   48%<br>
     *    ....   50%<br>
     *    Dec.   0%<br>
     *    TOTAL - 100%<br>
     * <br>
     * WIN (Winter)<br>
     *    Jan.   30%<br>
     *    Feb.   30%<br>
     *    ...    0%<br>
     *    ...    0%<br>
     *    Dec.   40%<br>
     *    TOTAL - 100%<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: BPC <br>
     * EnterpriseOne field length:  3 <br>
     */
    private String budgetPatternCode = null;

    /**
     * Billable (Y/N)<br>
     * <p>
     * Specifies whether a general ledger account should be billed. Valid codes<br>
     * are:<br>
     * <br>
     *    Y Yes, the account should be billed.<br>
     *    N No, it should not be billed.<br>
     *    1   It is eligible only for invoicing.<br>
     *    2   It is eligible only for revenue recognition.<br>
     *    4   It is eligible only for cost.<br>
     * <br>
     * Note: Codes 1, 2, and 4 relate only to the Service Billing system.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: BILL <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String billableCode = null;

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
     */
    private String unitOfMeasureCode = null;

    /**
     * Object Account - Alternate<br>
     * <p>
     * This alternate object account is occasionally used to comply with a<br>
     * regulatory chart of accounts, parent company requirements, or<br>
     * third-party coding scheme.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: OBJA <br>
     * EnterpriseOne field length:  6 <br>
     */
    private String objectAccountAlternate = null;

    /**
     * Subsidiary - Alternate<br>
     * <p>
     * An alternate subsidiary account that allows you to comply with a<br>
     * regulatory chart of accounts, parent company requirements, or third<br>
     * party coding scheme.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: SUBA <br>
     * EnterpriseOne field length:  8 <br>
     */
    private String subsidiaryAlternate = null;

    /**
     * Worker's Comp. Insurance Code<br>
     * <p>
     * A user defined code (00/W) that represents a workers' compensation<br>
     * insurance (WCI) code. This code should correspond to the classifications<br>
     * on your periodic workers' compensation insurance reports.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: WCMP <br>
     * EnterpriseOne field length:  4 <br>
     */
    private String workersCompensationInsuranceCode = null;

    /**
     * Method of Computation<br>
     * <p>
     * The method the system uses to calculate the percent complete, the<br>
     * projected final cost, and unit quantity for an account. Method of<br>
     * computation is a user defined code (51/MC).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CCT <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String computationMethod = null;

    /**
     * Equipment Rate Code<br>
     * <p>
     * A user defined code (00/RC) that indicates a billing rate, such as DY<br>
     * for daily, MO for monthly, and WK for weekly. You can set up multiple<br>
     * billing rates for a piece of equipment.<br>
     * <br>
     * If you leave this field blank, the system searches for a valid billing<br>
     * rate in the following sequence:<br>
     * <br>
     * 1.  Account Ledger Master (F0901)<br>
     * <br>
     * This table contains the most detailed rate information. You can assign<br>
     * multiple rates for a job. For example, you can set up separate rates for<br>
     * different equipment working conditions.<br>
     * <br>
     * 2.  Job or Business Unit Master (F0006)<br>
     * <br>
     * This table contains less detailed rate information than the Account<br>
     * Ledger Master. You can only set up a single rate for a job based on this<br>
     * table.<br>
     * <br>
     * 3.  Rental Rules (F1302)<br>
     * <br>
     * This table contains the least detailed rate code information. The system<br>
     * searches this table according to the criteria you establish when setting<br>
     * up the table.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: ERC <br>
     * EnterpriseOne field length:  2 <br>
     */
    private String equipmentRateCode = null;

    /**
     * Header Type Code<br>
     * <p>
     * Specifies the type of sequence to which a header account is related.<br>
     * Such an account, which appears on inquiry screens and reports when the<br>
     * respective sequence is used, provides an alternate account description<br>
     * and total line for that sequence.<br>
     * <br>
     * Valid codes are:<br>
     * <br>
     * Blank<br>
     *     Cost code and cost type <br>
     * <br>
     * A<br>
     *     Alternate cost code<br>
     * <br>
     * 1<br>
     *     Category code 01<br>
     * <br>
     * 2<br>
     *     Category code 02<br>
     * <br>
     * 3<br>
     *     Category code 03  <br>
     * <br>
     * Without such a header account, the relationship of the accounts may not<br>
     * be as apparent when the accounts appear in a sequence other than the<br>
     * cost code and cost type sequence.<br>
     * <br>
     * The following are true about header accounts and the HT field:<br>
     * <br>
     *     o The cost code must be unique among the header accounts regardless<br>
     * of the sequence assigned to it.<br>
     *     o If the HT field is blank, the system treats the account as a<br>
     * standard header account.<br>
     *     o If the HT field is not blank, the account can be only<br>
     * informational and must be a non-posting account.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: HTC <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String sequenceTypeCode = null;

    /**
     * Quantity Roll-Up Level<br>
     * <p>
     * Controls whether the unit quantity for an account can be summarized<br>
     * above that level of detail with other quantities of the same unit of<br>
     * measure. This field, which only affects the Job Status Inquiry screen<br>
     * (P512000), prevents the totaling of the same quantity contained in more<br>
     * than one account. Valid codes are 1-9 from least detailed 1 to most<br>
     * detailed 9.<br>
     * <br>
     * For example, suppose Concrete is a header account (level of detail 6)<br>
     * that includes two detail accounts, Forming and Stripping. To form 50<br>
     * linear feet of concrete, you form 50 linear feet and strip 50. If the<br>
     * summary for concrete includes both detail accounts, therefore, the total<br>
     * quantity is 100 linear feet instead of the actual 50. To prevent this,<br>
     * you could assign 9 to the QL field for forming and 6 for stripping. The<br>
     * system would include only the quantity for stripping in the unit summary<br>
     * for concrete.<br>
     * <br>
     * NOTE: Within the same account, the QL field is independent of the LD<br>
     * field.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: QLDA <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String quantityRollupLevel = null;

    /**
     * Cost Code Complete (Y/N)<br>
     * <p>
     * Designates that the work being performed for this cost code (subsidiary)<br>
     * is complete.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CCC <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String costCodeComplete = null;

    /**
     * Item Edit Code<br>
     * <p>
     * A code that controls what information you can enter into the Item Number<br>
     * field for this account.<br>
     * <br>
     * Valid values are:<br>
     * <br>
     *    blank No edit will be performed.<br>
     *    1       A valid value must be entered in item number for this<br>
     * account.
<br>
     *    0       Item number must be blank for this account.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: IEC <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String itemEditCode = null;

    /**
     * Fixed Asset Posting Edit Code<br>
     * <p>
     * A code that specifies whether a Fixed Asset ID is required on journal<br>
     * entries to this account and whether the entries will be posted at the<br>
     * subledger level in the Item Balance file (F1202). Valid values are:<br>
     * <br>
     * Blank<br>
     * No Fixed Asset ID is required.<br>
     * <br>
     * 1<br>
     * Fixed Asset ID is required on all transactions. The transaction will be<br>
     * posted to the Item Balance file (F1202) with the subledger and type<br>
     * contained in the transaction.<br>
     * <br>
     * 2<br>
     * Fixed Asset ID is required on all transactions. If any transaction<br>
     * contains a subledger and type, then the transaction will be posted to<br>
     * the Item Balance file (F1202) with a BLANK subledger and type.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: FPEC <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String postingEditCodeFixedAsset = null;

    /**
     * Type Code<br>
     * <p>
     * If you enter a subledger type in this field for an account with a<br>
     * posting edit code of S or L, the system will require the subledger type<br>
     * during journal entry. The system uses this field only if the posting<br>
     * edit code is S or L.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: STPC <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String subledgerProcessingTypeCode = null;

    /**
     * Target Object Account<br>
     * <p>
     * The object account that the system uses for mapping in multi-site<br>
     * consolidations when the target object is different from the source<br>
     * object.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TOBJ <br>
     * EnterpriseOne field length:  6 <br>
     */
    private String objectAccountTarget = null;

    /**
     * Target Subsidiary<br>
     * <p>
     * The subsidiary account that the system uses for mapping in multi-site<br>
     * consolidations when the target subsidiary is different from the source<br>
     * subsidiary.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TSUB <br>
     * EnterpriseOne field length:  8 <br>
     */
    private String subsidiaryTarget = null;

    /**
     * Purge Flag<br>
     * <p>
     * Reserved by J.D. Edwards.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PRGF <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String purgeCode = null;


    public void setGetGlCostObjects(GetGLCostObjects getGlCostObjects) {
        if (getGlCostObjects != null)
        this.getGlCostObjects = getGlCostObjects;
    }
    public GetGLCostObjects getGetGlCostObjects() {
        return getGlCostObjects;
    }

    public void setBudgetPatternCode(String budgetPatternCode) {
        this.budgetPatternCode = budgetPatternCode;
    }
    public String getBudgetPatternCode() {
        return budgetPatternCode;
    }

    public void setBillableCode(String billableCode) {
        this.billableCode = billableCode;
    }
    public String getBillableCode() {
        return billableCode;
    }

    public void setUnitOfMeasureCode(String unitOfMeasureCode) {
        this.unitOfMeasureCode = unitOfMeasureCode;
    }

    public String getUnitOfMeasureCode() {
        return unitOfMeasureCode;
    }

    public void setObjectAccountAlternate(String objectAccountAlternate) {
        this.objectAccountAlternate = objectAccountAlternate;
    }
    public String getObjectAccountAlternate() {
        return objectAccountAlternate;
    }

    public void setSubsidiaryAlternate(String subsidiaryAlternate) {
        this.subsidiaryAlternate = subsidiaryAlternate;
    }
    public String getSubsidiaryAlternate() {
        return subsidiaryAlternate;
    }

    public void setWorkersCompensationInsuranceCode(String workersCompensationInsurCode) {
        this.workersCompensationInsuranceCode = workersCompensationInsurCode;
    }
    public String getWorkersCompensationInsuranceCode() {
        return workersCompensationInsuranceCode;
    }

    public void setComputationMethod(String computationMethod) {
        this.computationMethod = computationMethod;
    }
    public String getComputationMethod() {
        return computationMethod;
    }

    public void setEquipmentRateCode(String equipmentRateCode) {
        this.equipmentRateCode = equipmentRateCode;
    }
    public String getEquipmentRateCode() {
        return equipmentRateCode;
    }

    public void setSequenceTypeCode(String sequenceTypeCode) {
        this.sequenceTypeCode = sequenceTypeCode;
    }
    public String getSequenceTypeCode() {
        return sequenceTypeCode;
    }

    public void setQuantityRollupLevel(String quantityRollupLevel) {
        this.quantityRollupLevel = quantityRollupLevel;
    }
    public String getQuantityRollupLevel() {
        return quantityRollupLevel;
    }

    public void setCostCodeComplete(String costCodeComplete) {
        this.costCodeComplete = costCodeComplete;
    }
    public String getCostCodeComplete() {
        return costCodeComplete;
    }

    public void setItemEditCode(String itemEditCode) {
        this.itemEditCode = itemEditCode;
    }
    public String getItemEditCode() {
        return itemEditCode;
    }

    public void setPostingEditCodeFixedAsset(String postingEditCodeFixedAsset) {
        this.postingEditCodeFixedAsset = postingEditCodeFixedAsset;
    }
    public String getPostingEditCodeFixedAsset() {
        return postingEditCodeFixedAsset;
    }

    public void setSubledgerProcessingTypeCode(String subledgerProcessingTypeCode) {
        this.subledgerProcessingTypeCode = subledgerProcessingTypeCode;
    }
    public String getSubledgerProcessingTypeCode() {
        return subledgerProcessingTypeCode;
    }

    public void setObjectAccountTarget(String objectAccountTarget) {
        this.objectAccountTarget = objectAccountTarget;
    }
    public String getObjectAccountTarget() {
        return objectAccountTarget;
    }

    public void setSubsidiaryTarget(String subsidiaryTarget) {
        this.subsidiaryTarget = subsidiaryTarget;
    }
    public String getSubsidiaryTarget() {
        return subsidiaryTarget;
    }

    public void setPurgeCode(String purgeCode) {
        this.purgeCode = purgeCode;
    }
    public String getPurgeCode() {
        return purgeCode;
    }
}

