package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import oracle.e1.bssv.J0900001.valueobject.InternalGetGLAccount;
import oracle.e1.bssv.J0900001.valueobject.InternalGetGLAccountWhereFields;
import oracle.e1.bssv.util.J0900010.GLAccountProcessor;
import oracle.e1.bssv.util.J0900010.valueobject.GLAccount;
import oracle.e1.bssv.util.J0900010.valueobject.GLAccountKey;
import oracle.e1.bssv.util.J0900010.valueobject.ProcessGLAccount;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssvfoundation.connection.IConnection;
import oracle.e1.bssvfoundation.exception.BSSVDataFormatterException;
import oracle.e1.bssvfoundation.util.E1Message;
import oracle.e1.bssvfoundation.util.E1MessageList;


/**
 * Exposed Input Value Object that provides input fields used to query the contact table. <br>
 */
public class GetGLAccount  extends ValueObject implements Serializable {
    public GetGLAccount() {
    }
    
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
     * Description<br>
     * <p>
     * A user defined name or remark.<br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DL01 <br>
     * EnterpriseOne field length:  30 <br>
     */
    private String description1 = null;

    /**
     * Account Level of Detail<br>
     * <p>
     * A number used to summarize and classify accounts in the general ledger<br>
     * by level of detail. Level 9 is the most detailed and Level 1 is the<br>
     * least detailed. Levels 1 and 2 are reserved for company and business<br>
     * unit totals. Levels 8 and 9 are reserved for job cost posting accounts<br>
     * in the Job Cost system. Examples of the other levels are:<br>
     * <br>
     * 3<br>
     * Assets, Liabilities, Revenues, Expenses  <br>
     * <br>
     * 4<br>
     * Current Assets, Fixed Assets, Current Liabilities, and so on  <br>
     * <br>
     * 5<br>
     * Cash, Accounts Receivable, Inventories, Salaries, and so on<br>
     * <br>
     * 6<br>
     * Petty Cash, Cash in Banks, Trade Accounts Receivable, and so on  <br>
     * <br>
     * 7<br>
     * Petty Cash - Dallas, Petty Cash - Houston, and so on<br>
     * <br>
     * 8<br>
     * More Detail  <br>
     * <br>
     * 9<br>
     * More Detail <br>
     * <br>
     * Do not skip levels of detail when you assign a level of detail to an<br>
     * account. Nonsequential levels of detail cause rollup errors in financial<br>
     * reports.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: LDA <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String levelOfDetail = null;

    /**
     * Posting Edit<br>
     * <p>
     * A code that controls G/L posting and account balance updates in the<br>
     * Account Master table (F0901). Valid values are:<br>
     * <br>
     * Blank<br>
     * Allows all posting. Posts subledgers in detailed format for every<br>
     * account transaction. Does not require subledger entry.<br>
     * <br>
     * B<br>
     * Only allows posting to budget ledger types starting with B or J.<br>
     * <br>
     * I<br>
     * Inactive account. No posting allowed.<br>
     * <br>
     * L<br>
     * Subledger and type are required for all transactions. Posts subledgers<br>
     * in detailed format for every account. The system stores the subledger<br>
     * and type in the Account Ledger (F0911) and Account Balances (F0902)<br>
     * tables. If you want to report on subledgers in the Financial Reporting<br>
     * feature, use this code.<br>
     * <br>
     * M<br>
     * Machine-generated transactions only (post program creates offsets).<br>
     * <br>
     * N <br>
     * Non-posting. Does not allow any post or account balance updates. In the<br>
     * Job Cost system, you can still post budget quantities.<br>
     * <br>
     * S<br>
     * Subledger and type are required for all transactions. Posts subledgers<br>
     * in summary format for every transaction. The system stores the subledger<br>
     * detail in the Account Ledger table. This code is not valid for budget<br>
     * entry programs.<br>
     * <br>
     * U<br>
     * Unit quantities are required for all transactions.<br>
     * <br>
     * X<br>
     * Subledger and type must be blank for all transactions. Does not allow<br>
     * subledger entry for the account.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: PEC <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String postingEditCode = null;

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
    private String currencyCode = null;

    /**
     * Model Accounts and Consolidation Flag<br>
     * <p>
     * A code that indicates either a model/consolidated account or a<br>
     * model/consolidated business unit. Valid values are:<br>
     * <br>
     * Blank<br>
     * Non-model business unit or account.<br>
     * <br>
     * M<br>
     * Model business unit or account.<br>
     * <br>
     * C<br>
     * Consolidated business unit or account. This is a program-generated<br>
     * (P10862) value and is not user-accessible.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: FMOD <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String modelOrConsolidationCode = null;

    /**
     * GL Account Taxable Flag  . . . . . . . .<br>
     * <p>
     * An option that specifies whether an account is taxable. When the option<br>
     * is turned on, the account is taxable. The system updates the record in<br>
     * the Account Master table (F0901) with 1 when the option is turned on.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TXGL <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String isTaxable = null;

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
    private String taxRateAreaCode = null;

    /**
     * reused compoung object for GL Account Key<br>
     */
    private GLAccountKey glAccountKey = new GLAccountKey();
    
    /**
     * reused compoung object for GL Account<br>
     */
    private GLAccount glAccount = new GLAccount();
    
    /**
     * reused compoung object for CategoryCodesGLAccount<br>
     */
    private CategoryCodesGLAccount categoryCodesGLAccount = new CategoryCodesGLAccount();



    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return company;
    }


    public void setDescription1(String description1) {
        this.description1 = description1;
    }
    public String getDescription1() {
        return description1;
    }


    public void setLevelOfDetail(String levelOfDetail) {
        this.levelOfDetail = levelOfDetail;
    }
    public String getLevelOfDetail() {
        return levelOfDetail;
    }


    public void setPostingEditCode(String postingEditCode) {
        this.postingEditCode = postingEditCode;
    }
    public String getPostingEditCode() {
        return postingEditCode;
    }


    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }


    public void setModelOrConsolidationCode(String modelOrConsolidationCode) {
        this.modelOrConsolidationCode = modelOrConsolidationCode;
    }
    public String getModelOrConsolidationCode() {
        return modelOrConsolidationCode;
    }


    public void setIsTaxable(String isTaxable) {
        this.isTaxable = isTaxable;
    }
    public String getIsTaxable() {
        return isTaxable;
    }


    public void setTaxRateAreaCode(String taxRateAreaCode) {
        this.taxRateAreaCode = taxRateAreaCode;
    }
    public String getTaxRateAreaCode() {
        return taxRateAreaCode;
    }


    public void setGlAccountKey(GLAccountKey glAccountKey) {
        if (glAccountKey != null)
            this.glAccountKey = glAccountKey;
        else
            this.glAccountKey = new GLAccountKey();
    }
    public GLAccountKey getGlAccountKey() {
        return glAccountKey;
    }


    public void setGlAccount(GLAccount glAccount) {
    //SAR 8684865 changed if statement, no longer glAccountKey
        if (glAccount != null)
            this.glAccount = glAccount;
        else
            this.glAccount = new GLAccount();
    }
    public GLAccount getGlAccount() {
        return glAccount;
    }


    public void setCategoryCodesGLAccount(CategoryCodesGLAccount categoryCodesGLAccount) {
        if (categoryCodesGLAccount != null)
            this.categoryCodesGLAccount = categoryCodesGLAccount;
        else
            this.categoryCodesGLAccount = new CategoryCodesGLAccount();
    }
    public CategoryCodesGLAccount getCategoryCodesGLAccount() {
        return categoryCodesGLAccount;
    }
    
    public E1MessageList mapFromPublished(IContext context, 
                                          IConnection connection, 
                                          InternalGetGLAccount vo) {
        InternalGetGLAccountWhereFields queryWhereFields = vo.getQueryWhereFields();                           
        
        E1MessageList messages = null;
        
        if(this.company!= null){
            try {
              String formattedCO = null;
              formattedCO = context.getBSSVDataFormatter().format(this.company,"CO");
              // set internal vo
              queryWhereFields.setF0901_CO(formattedCO);
            }
            catch (BSSVDataFormatterException e) {
              context.getBSSVLogger().app(context,"Error when formatting Company.",null,this,e);
              //Create new E1 Message with error from exception
              messages.addMessage(new E1Message(context, "002FIS",this.glAccount.getBusinessUnit()));
           }
        } 
        
        if(this.glAccount.getBusinessUnit()!= null){
            try {
              String formattedMCU = null;
              formattedMCU = context.getBSSVDataFormatter().format(this.glAccount.getBusinessUnit(),"MCU");
              // set internal vo
              queryWhereFields.setF0901_MCU(formattedMCU);
            }
            catch (BSSVDataFormatterException e) {
              context.getBSSVLogger().app(context,"Error when formatting Business Unit.",null,this,e);
              //Create new E1 Message with error from exception
              messages.addMessage(new E1Message(context, "002FIS",this.glAccount.getBusinessUnit()));
           }
        } 
        
        ProcessGLAccount processGlAccount = null; 
        GLAccount glAccount = null;
        GLAccountKey glAccountKey = null;
        if (messages == null || !messages.hasErrors()) {
            //setup to call glAccountProcessor   
            if (this.glAccount!=null)
                glAccount      = new GLAccount(this.glAccount.getObjectAccount(),
                                            queryWhereFields.getF0901_MCU(),
                                            this.glAccount.getSubsidiary());
            if (this.glAccountKey!=null)                                        
                glAccountKey   = new GLAccountKey(this.glAccountKey.getAccountId(),
                                               this.glAccountKey.getAccountLongId(),
                                               this.glAccountKey.getAccountAlternate());
            
            processGlAccount = new ProcessGLAccount(glAccount, glAccountKey);
        
            if ((this.glAccountKey.getAccountId()        != null && this.glAccountKey.getAccountId().length()         > 0) ||
                (this.glAccountKey.getAccountLongId()    != null && this.glAccountKey.getAccountLongId().length()     > 0) ||
                (this.glAccountKey.getAccountAlternate() != null &&  this.glAccountKey.getAccountAlternate().length() > 0) )  {      
              
                messages = GLAccountProcessor.processGLAccount(context, connection, processGlAccount); 
                      
                }  
                      
        }
        
        if (messages == null || !messages.hasErrors()) {      
        
            queryWhereFields.setF0901_AID(glAccountKey.getAccountId());
            //queryWhereFields.setF0901_ANI(glAccountKey.getAccountLongId());  
            queryWhereFields.setF0901_ANS(glAccountKey.getAccountAlternate());
            
            //queryWhereFields.setF0901_MCU(glAccount.getBusinessUnit());
            queryWhereFields.setF0901_OBJ(glAccount.getObjectAccount());
            queryWhereFields.setF0901_SUB(glAccount.getSubsidiary());
            
            queryWhereFields.setF0901_DL01(this.description1);
            //queryWhereFields.setF0901_CO  (this.company));
            queryWhereFields.setF0901_LDA (this.levelOfDetail);
            queryWhereFields.setF0901_PEC (this.postingEditCode);
            queryWhereFields.setF0901_CRCD(this.currencyCode);
            queryWhereFields.setF0901_FMOD(this.modelOrConsolidationCode);
            queryWhereFields.setF0901_TXGL(this.isTaxable);
            queryWhereFields.setF0901_TXA1(this.taxRateAreaCode);
        
            queryWhereFields.setF0901_R001(this.categoryCodesGLAccount.getCategoryCode001());
            queryWhereFields.setF0901_R002(this.categoryCodesGLAccount.getCategoryCode002());
            queryWhereFields.setF0901_R003(this.categoryCodesGLAccount.getCategoryCode003());
            queryWhereFields.setF0901_R004(this.categoryCodesGLAccount.getCategoryCode004());
            queryWhereFields.setF0901_R005(this.categoryCodesGLAccount.getCategoryCode005());
            queryWhereFields.setF0901_R006(this.categoryCodesGLAccount.getCategoryCode006());
            queryWhereFields.setF0901_R007(this.categoryCodesGLAccount.getCategoryCode007());
            queryWhereFields.setF0901_R008(this.categoryCodesGLAccount.getCategoryCode008());        
            queryWhereFields.setF0901_R009(this.categoryCodesGLAccount.getCategoryCode009());
            queryWhereFields.setF0901_R010(this.categoryCodesGLAccount.getCategoryCode010());
            queryWhereFields.setF0901_R011(this.categoryCodesGLAccount.getCategoryCode011());
            queryWhereFields.setF0901_R012(this.categoryCodesGLAccount.getCategoryCode012());
            queryWhereFields.setF0901_R013(this.categoryCodesGLAccount.getCategoryCode013());
            queryWhereFields.setF0901_R014(this.categoryCodesGLAccount.getCategoryCode014());
            queryWhereFields.setF0901_R015(this.categoryCodesGLAccount.getCategoryCode015());
            queryWhereFields.setF0901_R016(this.categoryCodesGLAccount.getCategoryCode016());
            queryWhereFields.setF0901_R017(this.categoryCodesGLAccount.getCategoryCode017());
            queryWhereFields.setF0901_R018(this.categoryCodesGLAccount.getCategoryCode018());
            queryWhereFields.setF0901_R019(this.categoryCodesGLAccount.getCategoryCode019());
            queryWhereFields.setF0901_R020(this.categoryCodesGLAccount.getCategoryCode020());
            queryWhereFields.setF0901_R021(this.categoryCodesGLAccount.getCategoryCode021());
            queryWhereFields.setF0901_R022(this.categoryCodesGLAccount.getCategoryCode022());
            queryWhereFields.setF0901_R023(this.categoryCodesGLAccount.getCategoryCode023());
            queryWhereFields.setF0901_R024(this.categoryCodesGLAccount.getCategoryCode024());
            queryWhereFields.setF0901_R025(this.categoryCodesGLAccount.getCategoryCode025());
            queryWhereFields.setF0901_R026(this.categoryCodesGLAccount.getCategoryCode026());
            queryWhereFields.setF0901_R027(this.categoryCodesGLAccount.getCategoryCode027());
            queryWhereFields.setF0901_R028(this.categoryCodesGLAccount.getCategoryCode028());
            queryWhereFields.setF0901_R029(this.categoryCodesGLAccount.getCategoryCode029());
            queryWhereFields.setF0901_R030(this.categoryCodesGLAccount.getCategoryCode030());
            queryWhereFields.setF0901_R031(this.categoryCodesGLAccount.getCategoryCode031());
            queryWhereFields.setF0901_R032(this.categoryCodesGLAccount.getCategoryCode032());
            queryWhereFields.setF0901_R033(this.categoryCodesGLAccount.getCategoryCode033());
            queryWhereFields.setF0901_R034(this.categoryCodesGLAccount.getCategoryCode034());
            queryWhereFields.setF0901_R035(this.categoryCodesGLAccount.getCategoryCode035());
            queryWhereFields.setF0901_R036(this.categoryCodesGLAccount.getCategoryCode036());
            queryWhereFields.setF0901_R037(this.categoryCodesGLAccount.getCategoryCode037());
            queryWhereFields.setF0901_R038(this.categoryCodesGLAccount.getCategoryCode038());
            queryWhereFields.setF0901_R039(this.categoryCodesGLAccount.getCategoryCode039());
            queryWhereFields.setF0901_R040(this.categoryCodesGLAccount.getCategoryCode040());        
            queryWhereFields.setF0901_R041(this.categoryCodesGLAccount.getCategoryCode041());
            queryWhereFields.setF0901_R042(this.categoryCodesGLAccount.getCategoryCode042());
            queryWhereFields.setF0901_R043(this.categoryCodesGLAccount.getCategoryCode043());        
            
           
        }
        
        return messages;
       
        
                                       
    }
    
}

