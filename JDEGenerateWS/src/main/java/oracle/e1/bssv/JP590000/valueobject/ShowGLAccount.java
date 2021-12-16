package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import oracle.e1.bssv.J0900001.valueobject.InternalGetGLAccount;
import oracle.e1.bssv.J0900001.valueobject.InternalShowGLAccount;
import oracle.e1.bssvfoundation.base.MessageValueObject;


/**
 * ShowContact Output VO <br>
 */
public class ShowGLAccount extends MessageValueObject implements Serializable {

    /**
     * Array of GLAccountResult objects<br>
     */
    private GLAccountResult glAccountResult[];
    
    /**
     * public default constructor<br>
     */
    public ShowGLAccount() {
    }
    
    
    /**
     * Overridden public constructor for instantiating: ShowGLAccount<br>
     */   
    public ShowGLAccount(InternalGetGLAccount internalVO) {

        if (internalVO.getQueryResults() != null) {



            this.glAccountResult = 
                    new GLAccountResult[internalVO.getQueryResults().size()]; /* create a new array big enough to hold all records in the result set */

            for (int i = 0; i < internalVO.getQueryResults().size(); 
                 i++) /* loop through all records in the result set */
            {
                this.getGlAccountResult()[i] = 
                        new GLAccountResult(); /* create a new object in array */

            
                InternalShowGLAccount qryResults = 
                        internalVO.getQueryResults(i);
                this.getGlAccountResult()[i].setDescription1               (qryResults.getF0901_DL01());
                this.getGlAccountResult()[i].setCompany                    (qryResults.getF0901_CO());
                this.getGlAccountResult()[i].setLevelOfDetail              (qryResults.getF0901_LDA());
                this.getGlAccountResult()[i].setPostingEditCode            (qryResults.getF0901_PEC());
                this.getGlAccountResult()[i].setCurrencyCode               (qryResults.getF0901_CRCD());
                this.getGlAccountResult()[i].setModelOrConsolidationCode   (qryResults.getF0901_FMOD());
                this.getGlAccountResult()[i].setIsTaxable                  (qryResults.getF0901_TXGL());
                this.getGlAccountResult()[i].setTaxRateAreaCode            (qryResults.getF0901_TXA1());
                this.getGlAccountResult()[i].setObjectAccountTarget        (qryResults.getF0901_TOBJ());
                this.getGlAccountResult()[i].setSubsidiaryTarget           (qryResults.getF0901_TSUB());
                this.getGlAccountResult()[i].setPurgeCode                  (qryResults.getF0901_PRGF());
                this.getGlAccountResult()[i].setBudgetPatternCode          (qryResults.getF0901_BPC());
                this.getGlAccountResult()[i].setBillableCode               (qryResults.getF0901_BILL());
                this.getGlAccountResult()[i].setUnitOfMeasureCode          (qryResults.getF0901_UM());
                this.getGlAccountResult()[i].setObjectAccountAlternate     (qryResults.getF0901_OBJA());
                this.getGlAccountResult()[i].setSubsidiaryAlternate        (qryResults.getF0901_SUBA());
                this.getGlAccountResult()[i].setWorkersCompensationInsuranceCode(qryResults.getF0901_WCMP());
                this.getGlAccountResult()[i].setComputationMethod          (qryResults.getF0901_CCT());
                this.getGlAccountResult()[i].setEquipmentRateCode          (qryResults.getF0901_ERC());
                this.getGlAccountResult()[i].setSequenceTypeCode           (qryResults.getF0901_HTC());
                this.getGlAccountResult()[i].setQuantityRollupLevel        (qryResults.getF0901_QLDA());
                this.getGlAccountResult()[i].setCostCodeComplete           (qryResults.getF0901_CCC());
                this.getGlAccountResult()[i].setItemEditCode               (qryResults.getF0901_IEC());
                this.getGlAccountResult()[i].setPostingEditCodeFixedAsset  (qryResults.getF0901_FPEC());
                this.getGlAccountResult()[i].setSubledgerProcessingTypeCode(qryResults.getF0901_STPC());

                //GL Account Key
                this.getGlAccountResult()[i].getGlAccountKey().setAccountId     (qryResults.getF0901_AID());
                // NOTE: long account id not stored in F0901, just account id and alternate and 3rd account number
                this.getGlAccountResult()[i].getGlAccountKey().setAccountLongId (qryResults.getF0901_ANI());
                this.getGlAccountResult()[i].getGlAccountKey().setAccountAlternate(qryResults.getF0901_ANS());

                //GL Account
                this.getGlAccountResult()[i].getGlAccount().setBusinessUnit (qryResults.getF0901_MCU());
                this.getGlAccountResult()[i].getGlAccount().setObjectAccount(qryResults.getF0901_OBJ());
                this.getGlAccountResult()[i].getGlAccount().setSubsidiary   (qryResults.getF0901_SUB());
                
                //Get GL Cost Objects  **can't be shared with ProcessVoucher because that GLCostAccount has different fields
                this.getGlAccountResult()[i].getGetGlCostObjects().setCostObjectEditCode1(qryResults.getF0901_CEC1());
                this.getGlAccountResult()[i].getGetGlCostObjects().setCostObjectEditCode2(qryResults.getF0901_CEC2());
                this.getGlAccountResult()[i].getGetGlCostObjects().setCostObjectEditCode3(qryResults.getF0901_CEC3());
                this.getGlAccountResult()[i].getGetGlCostObjects().setCostObjectEditCode4(qryResults.getF0901_CEC4());
                
                //Category Codes GL Account
                CategoryCodesGLAccount catCodes =  this.getGlAccountResult()[i].getCategoryCodesGLAccount();
                catCodes.setCategoryCode001(qryResults.getF0901_R001());
                catCodes.setCategoryCode002(qryResults.getF0901_R002());
                catCodes.setCategoryCode003(qryResults.getF0901_R003());
                catCodes.setCategoryCode004(qryResults.getF0901_R004());
                catCodes.setCategoryCode005(qryResults.getF0901_R005());
                catCodes.setCategoryCode006(qryResults.getF0901_R006());
                catCodes.setCategoryCode007(qryResults.getF0901_R007());
                catCodes.setCategoryCode008(qryResults.getF0901_R008());
                catCodes.setCategoryCode009(qryResults.getF0901_R009());
                catCodes.setCategoryCode010(qryResults.getF0901_R010());
                catCodes.setCategoryCode011(qryResults.getF0901_R011());
                catCodes.setCategoryCode012(qryResults.getF0901_R012());
                catCodes.setCategoryCode013(qryResults.getF0901_R013());
                catCodes.setCategoryCode014(qryResults.getF0901_R014());
                catCodes.setCategoryCode015(qryResults.getF0901_R015());
                catCodes.setCategoryCode016(qryResults.getF0901_R016());
                catCodes.setCategoryCode017(qryResults.getF0901_R017());
                catCodes.setCategoryCode018(qryResults.getF0901_R018());
                catCodes.setCategoryCode019(qryResults.getF0901_R019());
                catCodes.setCategoryCode020(qryResults.getF0901_R020());
                catCodes.setCategoryCode021(qryResults.getF0901_R021());
                catCodes.setCategoryCode022(qryResults.getF0901_R022());
                catCodes.setCategoryCode023(qryResults.getF0901_R023());
                catCodes.setCategoryCode024(qryResults.getF0901_R024());
                catCodes.setCategoryCode025(qryResults.getF0901_R025());
                catCodes.setCategoryCode026(qryResults.getF0901_R026());
                catCodes.setCategoryCode027(qryResults.getF0901_R027());
                catCodes.setCategoryCode028(qryResults.getF0901_R028());
                catCodes.setCategoryCode029(qryResults.getF0901_R029());
                catCodes.setCategoryCode030(qryResults.getF0901_R030());
                catCodes.setCategoryCode031(qryResults.getF0901_R031());
                catCodes.setCategoryCode032(qryResults.getF0901_R032());
                catCodes.setCategoryCode033(qryResults.getF0901_R033());
                catCodes.setCategoryCode034(qryResults.getF0901_R034());
                catCodes.setCategoryCode035(qryResults.getF0901_R035());
                catCodes.setCategoryCode036(qryResults.getF0901_R036());
                catCodes.setCategoryCode037(qryResults.getF0901_R037());
                catCodes.setCategoryCode038(qryResults.getF0901_R038());
                catCodes.setCategoryCode039(qryResults.getF0901_R039());
                catCodes.setCategoryCode040(qryResults.getF0901_R040());
                catCodes.setCategoryCode041(qryResults.getF0901_R041());
                catCodes.setCategoryCode042(qryResults.getF0901_R042());
                catCodes.setCategoryCode043(qryResults.getF0901_R043());
                
            }
        }
    }





    public void setGlAccountResult(GLAccountResult[] glAccountResult) {
        this.glAccountResult = glAccountResult;
    }

    public GLAccountResult[] getGlAccountResult() {
        return glAccountResult;
    }

    public GLAccountResult[] getGlAccountResult(int i) {
        if (i >= 0 && i < glAccountResult.length)
            return glAccountResult;
        return null;
    }
}

