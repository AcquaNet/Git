package oracle.e1.bssv.J5900002;

import java.util.Date;

import oracle.e1.bssv.J5900002.valueobject.InternalGetAuditInfo;
import oracle.e1.bssv.J5900002.valueobject.InternalInsertBatchJournalEntryStaging;
import oracle.e1.bssv.J5900002.valueobject.InternalInsertBatchJournaleEntryStagingFields;
import oracle.e1.bssvfoundation.base.BSFNParameters;
import oracle.e1.bssvfoundation.base.BusinessService;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.connection.IConnection;
import oracle.e1.bssvfoundation.exception.BSFNServiceInvalidArgException;
import oracle.e1.bssvfoundation.exception.BSFNServiceSystemException;
import oracle.e1.bssvfoundation.exception.DBServiceException;
import oracle.e1.bssvfoundation.exception.ServicePropertyException;
import oracle.e1.bssvfoundation.services.BSSVDBField;
import oracle.e1.bssvfoundation.services.IBSFNService;
import oracle.e1.bssvfoundation.services.IDBService;
import oracle.e1.bssvfoundation.util.E1Message;
import oracle.e1.bssvfoundation.util.E1MessageList;
import oracle.e1.bssvfoundation.util.MathNumeric;
import oracle.e1.bssvfoundation.util.ServicePropertyAccess;

/**
 * This Service Business Function exposes database functionality for the F0911Z1 table.
 */
public class BatchJournalEntryInsertProcessor extends BusinessService {

    private static String PROGRAM_ID = "J0900002";

    public BatchJournalEntryInsertProcessor() {
    }

    public static E1MessageList insertBatchJournalEntryStaging(IContext context, 
                                                               IConnection connection, 
                                                               InternalInsertBatchJournalEntryStaging internalVO) {

        //Call start internal method, passing the context (which was passed from Published Business Service).
        startInternalMethod(context, "InsertBatchJournalEntryZTable", 
                            internalVO);
        //Create new message list for BSSV processing.
        E1MessageList messages = new E1MessageList();
        long numRowsInserted = 0;
        if (internalVO.getInsertFields() != null) {

            InternalGetAuditInfo internalVOGetAuditInfo = 
                new InternalGetAuditInfo();
            //Get Audit information to pass to insertToF0911Z1
            messages.addMessages(getAuditInfo(context, connection, internalVOGetAuditInfo));
            
            int size = internalVO.getInsertFields().size();
            for (int i = 0; i < size; i++) {
                //call method (created by the wizard), which then executes Business Function or Database operation
                E1MessageList insertMessages = 
                    insertToF0911Z1(context, connection, 
                                    internalVO.getInsertFields(i), 
                                    internalVOGetAuditInfo);

                //if no errors occur while inserting, add to counter.
                if (!insertMessages.hasErrors()) {
                    numRowsInserted++;
                }else{
                    //Add prefix to identify which record failed to insert
                    //ediUserId, ediTransactionNumber, ediLineNumber, ediBatchNumber
                    String prefix = "Insert Failed For ediUserId = " + 
                        internalVO.getInsertFields(i).getF0911Z1_EDUS() +
                        " ediTransactionNumber = " +
                        internalVO.getInsertFields(i).getF0911Z1_EDTN() +
                        " ediLineNumber = " + 
                        internalVO.getInsertFields(i).getF0911Z1_EDLN() +
                        " ediBatchNumber = " +
                        internalVO.getInsertFields(i).getF0911Z1_EDBT();
                        
                    insertMessages.setMessagePrefix(prefix);
                    //add messages returned from E1 processing to BSSV message list.
                    messages.addMessages(insertMessages); 
                    
                    //exit the for loop
                    i = size;
                }
            }
            internalVO.setNumberRecordsInserted(numRowsInserted);
        }

        //Call finish internal method passing context.
        finishInternalMethod(context, "insertAddressBookZTable");

        //Return E1MessageList containing errors and warnings that occurred during processing BSSV.
        return messages;
    }

    /**
     * Inserts a record into the F0911Z1 table.
     * <p>Following SQL statement can be produced by this generated code
     * <blockquote><pre><code>
     * INSERT INTO F0911Z1
     * 
     * ( F0911Z1.EDUS=?, F0911Z1.EDTY=?, F0911Z1.EDSQ=?, F0911Z1.EDTN=?, F0911Z1.EDCT=?, F0911Z1.EDLN=?, F0911Z1.EDTS=?, F0911Z1.EDFT=?, F0911Z1.EDDT=?, F0911Z1.EDER=?, F0911Z1.EDDL=?, F0911Z1.EDSP=?, F0911Z1.EDTC=?, F0911Z1.EDTR=?, F0911Z1.EDBT=?, F0911Z1.EDGL=?, F0911Z1.EDAN=?, F0911Z1.KCO=?, F0911Z1.DCT=?, F0911Z1.DOC=?, F0911Z1.DGJ=?, F0911Z1.JELN=?, F0911Z1.EXTL=?, F0911Z1.POST=?, F0911Z1.ICU=?, F0911Z1.ICUT=?, F0911Z1.DICJ=?, F0911Z1.DSYJ=?, F0911Z1.TICU=?, F0911Z1.CO=?, F0911Z1.ANI=?, F0911Z1.AM=?, F0911Z1.AID=?, F0911Z1.MCU=?, F0911Z1.OBJ=?, F0911Z1.SUB=?, F0911Z1.SBL=?, F0911Z1.SBLT=?, F0911Z1.LT=?, F0911Z1.PN=?, F0911Z1.CTRY=?, F0911Z1.FY=?, F0911Z1.FQ=?, F0911Z1.CRCD=?, F0911Z1.CRR=?, F0911Z1.HCRR=?, F0911Z1.HDGJ=?, F0911Z1.AA=?, F0911Z1.U=?, F0911Z1.UM=?, F0911Z1.GLC=?, F0911Z1.RE=?, F0911Z1.EXA=?, F0911Z1.EXR=?, F0911Z1.R1=?, F0911Z1.R2=?, F0911Z1.R3=?, F0911Z1.SFX=?, F0911Z1.ODOC=?, F0911Z1.ODCT=?, F0911Z1.OSFX=?, F0911Z1.PKCO=?, F0911Z1.OKCO=?, F0911Z1.PDCT=?, F0911Z1.AN8=?, F0911Z1.CN=?, F0911Z1.DKJ=?, F0911Z1.DKC=?, F0911Z1.ASID=?, F0911Z1.BRE=?, F0911Z1.RCND=?, F0911Z1.SUMM=?, F0911Z1.PRGE=?, F0911Z1.TNN=?, F0911Z1.ALT1=?, F0911Z1.ALT2=?, F0911Z1.ALT3=?, F0911Z1.ALT4=?, F0911Z1.ALT5=?, F0911Z1.ALT6=?, F0911Z1.ALT7=?, F0911Z1.ALT8=?, F0911Z1.ALT9=?, F0911Z1.ALT0=?, F0911Z1.ALTT=?, F0911Z1.ALTU=?, F0911Z1.ALTV=?, F0911Z1.ALTW=?, F0911Z1.ALTX=?, F0911Z1.ALTZ=?, F0911Z1.DLNA=?, F0911Z1.CFF1=?, F0911Z1.CFF2=?, F0911Z1.ASM=?, F0911Z1.BC=?, F0911Z1.VINV=?, F0911Z1.IVD=?, F0911Z1.WR01=?, F0911Z1.PO=?, F0911Z1.PSFX=?, F0911Z1.DCTO=?, F0911Z1.LNID=?, F0911Z1.WY=?, F0911Z1.WN=?, F0911Z1.FNLP=?, F0911Z1.OPSQ=?, F0911Z1.JBCD=?, F0911Z1.JBST=?, F0911Z1.HMCU=?, F0911Z1.DOI=?, F0911Z1.ALID=?, F0911Z1.ALTY=?, F0911Z1.DSVJ=?, F0911Z1.TORG=?, F0911Z1.REG#=?, F0911Z1.PYID=?, F0911Z1.USER=?, F0911Z1.PID=?, F0911Z1.JOBN=?, F0911Z1.UPMJ=?, F0911Z1.UPMT=?, F0911Z1.CRRM=?, F0911Z1.ACR=?, F0911Z1.DGM=?, F0911Z1.DGD=?, F0911Z1.DGY=?, F0911Z1.DG#=?, F0911Z1.DICM=?, F0911Z1.DICD=?, F0911Z1.DICY=?, F0911Z1.DIC#=?, F0911Z1.DSYM=?, F0911Z1.DSYD=?, F0911Z1.DSYY=?, F0911Z1.DSY#=?, F0911Z1.DKM=?, F0911Z1.DKD=?, F0911Z1.DKY=?, F0911Z1.DK#=?, F0911Z1.DSVM=?, F0911Z1.DSVD=?, F0911Z1.DSVY=?, F0911Z1.DSV#=?, F0911Z1.HDGM=?, F0911Z1.HDGD=?, F0911Z1.HDGY=?, F0911Z1.HDG#=?, F0911Z1.DKCM=?, F0911Z1.DKCD=?, F0911Z1.DKCY=?, F0911Z1.DKC#=?, F0911Z1.IVDM=?, F0911Z1.IVDD=?, F0911Z1.IVDY=?, F0911Z1.IVD#=?, F0911Z1.ABR1=?, F0911Z1.ABR2=?, F0911Z1.ABR3=?, F0911Z1.ABR4=?, F0911Z1.ABT1=?, F0911Z1.ABT2=?, F0911Z1.ABT3=?, F0911Z1.ABT4=?, F0911Z1.ITM=?, F0911Z1.PM01=?, F0911Z1.PM02=?, F0911Z1.PM03=?, F0911Z1.PM04=?, F0911Z1.PM05=?, F0911Z1.PM06=?, F0911Z1.PM07=?, F0911Z1.PM08=?, F0911Z1.PM09=?, F0911Z1.PM10=?, F0911Z1.BCRC=?, F0911Z1.EXR1=?, F0911Z1.TXA1=?, F0911Z1.TXITM=?, F0911Z1.ACTB=?, F0911Z1.STAM=?, F0911Z1.CTAM=?, F0911Z1.AG=?, F0911Z1.AGF=?, F0911Z1.TKTX=?, F0911Z1.DLNID=?, F0911Z1.CKNU=?, F0911Z1.BUPC=?, F0911Z1.AHBU=?, F0911Z1.EPGC=?, F0911Z1.JPGC=?)
     * </code></pre></blockquote>
     * @param context conditionally provides the connection for the database operation and logging information
     * @param connection can either be an explicit connection or null. If null the default connection is used.
     * @param internalVO InternalInsertBatchJournaleEntryStagingFields 
     * @param internalVOGetAuditInfo
     * @return an E1Message containing the text of any database exceptions that may have occurred
     */
    private static E1MessageList insertToF0911Z1(IContext context, 
                                                 IConnection connection, 
                                                 InternalInsertBatchJournaleEntryStagingFields internalVO, 
                                                 InternalGetAuditInfo internalVOGetAuditInfo) {
        //create return object
        E1MessageList returnMessages = new E1MessageList();

        //specify columns to insert
        BSSVDBField[] insertFields = // String - EdiUserId

        { new BSSVDBField("F0911Z1.EDUS"), new BSSVDBField("F0911Z1.EDTY"), 
          new BSSVDBField("F0911Z1.EDSQ"), new BSSVDBField("F0911Z1.EDTN"), 
          new BSSVDBField("F0911Z1.EDCT"), new BSSVDBField("F0911Z1.EDLN"), 
          new BSSVDBField("F0911Z1.EDTS"), new BSSVDBField("F0911Z1.EDFT"), 
          new BSSVDBField("F0911Z1.EDDT"), new BSSVDBField("F0911Z1.EDER"), 
          new BSSVDBField("F0911Z1.EDDL"), new BSSVDBField("F0911Z1.EDSP"), 
          new BSSVDBField("F0911Z1.EDTC"), new BSSVDBField("F0911Z1.EDTR"), 
          new BSSVDBField("F0911Z1.EDBT"), new BSSVDBField("F0911Z1.EDGL"), 
          new BSSVDBField("F0911Z1.EDAN"), new BSSVDBField("F0911Z1.KCO"), 
          new BSSVDBField("F0911Z1.DCT"), new BSSVDBField("F0911Z1.DOC"), 
          new BSSVDBField("F0911Z1.DGJ"), new BSSVDBField("F0911Z1.JELN"), 
          new BSSVDBField("F0911Z1.EXTL"), new BSSVDBField("F0911Z1.POST"), 
          new BSSVDBField("F0911Z1.ICU"), new BSSVDBField("F0911Z1.ICUT"), 
          new BSSVDBField("F0911Z1.DICJ"), new BSSVDBField("F0911Z1.DSYJ"), 
          new BSSVDBField("F0911Z1.TICU"), new BSSVDBField("F0911Z1.CO"), 
          new BSSVDBField("F0911Z1.ANI"), new BSSVDBField("F0911Z1.AM"), 
          new BSSVDBField("F0911Z1.AID"), new BSSVDBField("F0911Z1.MCU"), 
          new BSSVDBField("F0911Z1.OBJ"), new BSSVDBField("F0911Z1.SUB"), 
          new BSSVDBField("F0911Z1.SBL"), new BSSVDBField("F0911Z1.SBLT"), 
          new BSSVDBField("F0911Z1.LT"), new BSSVDBField("F0911Z1.PN"), 
          new BSSVDBField("F0911Z1.CTRY"), new BSSVDBField("F0911Z1.FY"), 
          new BSSVDBField("F0911Z1.FQ"), new BSSVDBField("F0911Z1.CRCD"), 
          new BSSVDBField("F0911Z1.CRR"), new BSSVDBField("F0911Z1.HCRR"), 
          new BSSVDBField("F0911Z1.HDGJ"), new BSSVDBField("F0911Z1.AA"), 
          new BSSVDBField("F0911Z1.U"), new BSSVDBField("F0911Z1.UM"), 
          new BSSVDBField("F0911Z1.GLC"), new BSSVDBField("F0911Z1.RE"), 
          new BSSVDBField("F0911Z1.EXA"), new BSSVDBField("F0911Z1.EXR"), 
          new BSSVDBField("F0911Z1.R1"), new BSSVDBField("F0911Z1.R2"), 
          new BSSVDBField("F0911Z1.R3"), new BSSVDBField("F0911Z1.SFX"), 
          new BSSVDBField("F0911Z1.ODOC"), new BSSVDBField("F0911Z1.ODCT"), 
          new BSSVDBField("F0911Z1.OSFX"), new BSSVDBField("F0911Z1.PKCO"), 
          new BSSVDBField("F0911Z1.OKCO"), new BSSVDBField("F0911Z1.PDCT"), 
          new BSSVDBField("F0911Z1.AN8"), new BSSVDBField("F0911Z1.CN"), 
          new BSSVDBField("F0911Z1.DKJ"), new BSSVDBField("F0911Z1.DKC"), 
          new BSSVDBField("F0911Z1.ASID"), new BSSVDBField("F0911Z1.BRE"), 
          new BSSVDBField("F0911Z1.RCND"), new BSSVDBField("F0911Z1.SUMM"), 
          new BSSVDBField("F0911Z1.PRGE"), new BSSVDBField("F0911Z1.TNN"), 
          new BSSVDBField("F0911Z1.ALT1"), new BSSVDBField("F0911Z1.ALT2"), 
          new BSSVDBField("F0911Z1.ALT3"), new BSSVDBField("F0911Z1.ALT4"), 
          new BSSVDBField("F0911Z1.ALT5"), new BSSVDBField("F0911Z1.ALT6"), 
          new BSSVDBField("F0911Z1.ALT7"), new BSSVDBField("F0911Z1.ALT8"), 
          new BSSVDBField("F0911Z1.ALT9"), new BSSVDBField("F0911Z1.ALT0"), 
          new BSSVDBField("F0911Z1.ALTT"), new BSSVDBField("F0911Z1.ALTU"), 
          new BSSVDBField("F0911Z1.ALTV"), new BSSVDBField("F0911Z1.ALTW"), 
          new BSSVDBField("F0911Z1.ALTX"), new BSSVDBField("F0911Z1.ALTZ"), 
          new BSSVDBField("F0911Z1.DLNA"), new BSSVDBField("F0911Z1.CFF1"), 
          new BSSVDBField("F0911Z1.CFF2"), new BSSVDBField("F0911Z1.ASM"), 
          new BSSVDBField("F0911Z1.BC"), new BSSVDBField("F0911Z1.VINV"), 
          new BSSVDBField("F0911Z1.IVD"), new BSSVDBField("F0911Z1.WR01"), 
          new BSSVDBField("F0911Z1.PO"), new BSSVDBField("F0911Z1.PSFX"), 
          new BSSVDBField("F0911Z1.DCTO"), new BSSVDBField("F0911Z1.LNID"), 
          new BSSVDBField("F0911Z1.WY"), new BSSVDBField("F0911Z1.WN"), 
          new BSSVDBField("F0911Z1.FNLP"), new BSSVDBField("F0911Z1.OPSQ"), 
          new BSSVDBField("F0911Z1.JBCD"), new BSSVDBField("F0911Z1.JBST"), 
          new BSSVDBField("F0911Z1.HMCU"), new BSSVDBField("F0911Z1.DOI"), 
          new BSSVDBField("F0911Z1.ALID"), new BSSVDBField("F0911Z1.ALTY"), 
          new BSSVDBField("F0911Z1.DSVJ"), new BSSVDBField("F0911Z1.TORG"), 
          new BSSVDBField("F0911Z1.REG#"), new BSSVDBField("F0911Z1.PYID"), 
          new BSSVDBField("F0911Z1.USER"), new BSSVDBField("F0911Z1.PID"), 
          new BSSVDBField("F0911Z1.JOBN"), new BSSVDBField("F0911Z1.UPMJ"), 
          new BSSVDBField("F0911Z1.UPMT"), new BSSVDBField("F0911Z1.CRRM"), 
          new BSSVDBField("F0911Z1.ACR"), new BSSVDBField("F0911Z1.DGM"), 
          new BSSVDBField("F0911Z1.DGD"), new BSSVDBField("F0911Z1.DGY"), 
          new BSSVDBField("F0911Z1.DG#"), new BSSVDBField("F0911Z1.DICM"), 
          new BSSVDBField("F0911Z1.DICD"), new BSSVDBField("F0911Z1.DICY"), 
          new BSSVDBField("F0911Z1.DIC#"), new BSSVDBField("F0911Z1.DSYM"), 
          new BSSVDBField("F0911Z1.DSYD"), new BSSVDBField("F0911Z1.DSYY"), 
          new BSSVDBField("F0911Z1.DSY#"), new BSSVDBField("F0911Z1.DKM"), 
          new BSSVDBField("F0911Z1.DKD"), new BSSVDBField("F0911Z1.DKY"), 
          new BSSVDBField("F0911Z1.DK#"), new BSSVDBField("F0911Z1.DSVM"), 
          new BSSVDBField("F0911Z1.DSVD"), new BSSVDBField("F0911Z1.DSVY"), 
          new BSSVDBField("F0911Z1.DSV#"), new BSSVDBField("F0911Z1.HDGM"), 
          new BSSVDBField("F0911Z1.HDGD"), new BSSVDBField("F0911Z1.HDGY"), 
          new BSSVDBField("F0911Z1.HDG#"), new BSSVDBField("F0911Z1.DKCM"), 
          new BSSVDBField("F0911Z1.DKCD"), new BSSVDBField("F0911Z1.DKCY"), 
          new BSSVDBField("F0911Z1.DKC#"), new BSSVDBField("F0911Z1.IVDM"), 
          new BSSVDBField("F0911Z1.IVDD"), new BSSVDBField("F0911Z1.IVDY"), 
          new BSSVDBField("F0911Z1.IVD#"), new BSSVDBField("F0911Z1.ABR1"), 
          new BSSVDBField("F0911Z1.ABR2"), new BSSVDBField("F0911Z1.ABR3"), 
          new BSSVDBField("F0911Z1.ABR4"), new BSSVDBField("F0911Z1.ABT1"), 
          new BSSVDBField("F0911Z1.ABT2"), new BSSVDBField("F0911Z1.ABT3"), 
          new BSSVDBField("F0911Z1.ABT4"), new BSSVDBField("F0911Z1.ITM"), 
          new BSSVDBField("F0911Z1.PM01"), new BSSVDBField("F0911Z1.PM02"), 
          new BSSVDBField("F0911Z1.PM03"), new BSSVDBField("F0911Z1.PM04"), 
          new BSSVDBField("F0911Z1.PM05"), new BSSVDBField("F0911Z1.PM06"), 
          new BSSVDBField("F0911Z1.PM07"), new BSSVDBField("F0911Z1.PM08"), 
          new BSSVDBField("F0911Z1.PM09"), new BSSVDBField("F0911Z1.PM10"), 
          new BSSVDBField("F0911Z1.BCRC"), new BSSVDBField("F0911Z1.EXR1"), 
          new BSSVDBField("F0911Z1.TXA1"), new BSSVDBField("F0911Z1.TXITM"), 
          new BSSVDBField("F0911Z1.ACTB"), new BSSVDBField("F0911Z1.STAM"), 
          new BSSVDBField("F0911Z1.CTAM"), new BSSVDBField("F0911Z1.AG"), 
          new BSSVDBField("F0911Z1.AGF"), new BSSVDBField("F0911Z1.TKTX"), 
          new BSSVDBField("F0911Z1.DLNID"), new BSSVDBField("F0911Z1.CKNU"), 
          new BSSVDBField("F0911Z1.BUPC"), new BSSVDBField("F0911Z1.AHBU"), 
          new BSSVDBField("F0911Z1.EPGC"), new BSSVDBField("F0911Z1.JPGC") };

        //specify insert values
        Object[] insertValues = 
        { internalVO.getF0911Z1_EDUS(), internalVO.getF0911Z1_EDTY(), 
          internalVO.getF0911Z1_EDSQ(), internalVO.getF0911Z1_EDTN(), 
          internalVO.getF0911Z1_EDCT(), internalVO.getF0911Z1_EDLN(), 
          internalVO.getF0911Z1_EDTS(), internalVO.getF0911Z1_EDFT(), 
          internalVO.getF0911Z1_EDDT(), internalVO.getF0911Z1_EDER(), 
          internalVO.getF0911Z1_EDDL(), internalVO.getF0911Z1_EDSP(), 
          internalVO.getF0911Z1_EDTC(), internalVO.getF0911Z1_EDTR(), 
          internalVO.getF0911Z1_EDBT(), internalVO.getF0911Z1_EDGL(), 
          internalVO.getF0911Z1_EDAN(), internalVO.getF0911Z1_KCO(), 
          internalVO.getF0911Z1_DCT(), internalVO.getF0911Z1_DOC(), 
          internalVO.getF0911Z1_DGJ(), internalVO.getF0911Z1_JELN(), 
          internalVO.getF0911Z1_EXTL(), internalVO.getF0911Z1_POST(), 
          internalVO.getF0911Z1_ICU(), internalVO.getF0911Z1_ICUT(), 
          internalVO.getF0911Z1_DICJ(), internalVO.getF0911Z1_DSYJ(), 
          internalVO.getF0911Z1_TICU(), internalVO.getF0911Z1_CO(), 
          internalVO.getF0911Z1_ANI(), internalVO.getF0911Z1_AM(), 
          internalVO.getF0911Z1_AID(), internalVO.getF0911Z1_MCU(), 
          internalVO.getF0911Z1_OBJ(), internalVO.getF0911Z1_SUB(), 
          internalVO.getF0911Z1_SBL(), internalVO.getF0911Z1_SBLT(), 
          internalVO.getF0911Z1_LT(), internalVO.getF0911Z1_PN(), 
          internalVO.getF0911Z1_CTRY(), internalVO.getF0911Z1_FY(), 
          internalVO.getF0911Z1_FQ(), internalVO.getF0911Z1_CRCD(), 
          internalVO.getF0911Z1_CRR(), internalVO.getF0911Z1_HCRR(), 
          internalVO.getF0911Z1_HDGJ(), internalVO.getF0911Z1_AA(), 
          internalVO.getF0911Z1_U(), internalVO.getF0911Z1_UM(), 
          internalVO.getF0911Z1_GLC(), internalVO.getF0911Z1_RE(), 
          internalVO.getF0911Z1_EXA(), internalVO.getF0911Z1_EXR(), 
          internalVO.getF0911Z1_R1(), internalVO.getF0911Z1_R2(), 
          internalVO.getF0911Z1_R3(), internalVO.getF0911Z1_SFX(), 
          internalVO.getF0911Z1_ODOC(), internalVO.getF0911Z1_ODCT(), 
          internalVO.getF0911Z1_OSFX(), internalVO.getF0911Z1_PKCO(), 
          internalVO.getF0911Z1_OKCO(), internalVO.getF0911Z1_PDCT(), 
          internalVO.getF0911Z1_AN8(), internalVO.getF0911Z1_CN(), 
          internalVO.getF0911Z1_DKJ(), internalVO.getF0911Z1_DKC(), 
          internalVO.getF0911Z1_ASID(), internalVO.getF0911Z1_BRE(), 
          internalVO.getF0911Z1_RCND(), internalVO.getF0911Z1_SUMM(), 
          internalVO.getF0911Z1_PRGE(), internalVO.getF0911Z1_TNN(), 
          internalVO.getF0911Z1_ALT1(), internalVO.getF0911Z1_ALT2(), 
          internalVO.getF0911Z1_ALT3(), internalVO.getF0911Z1_ALT4(), 
          internalVO.getF0911Z1_ALT5(), internalVO.getF0911Z1_ALT6(), 
          internalVO.getF0911Z1_ALT7(), internalVO.getF0911Z1_ALT8(), 
          internalVO.getF0911Z1_ALT9(), internalVO.getF0911Z1_ALT0(), 
          internalVO.getF0911Z1_ALTT(), internalVO.getF0911Z1_ALTU(), 
          internalVO.getF0911Z1_ALTV(), internalVO.getF0911Z1_ALTW(), 
          internalVO.getF0911Z1_ALTX(), internalVO.getF0911Z1_ALTZ(), 
          internalVO.getF0911Z1_DLNA(), internalVO.getF0911Z1_CFF1(), 
          internalVO.getF0911Z1_CFF2(), internalVO.getF0911Z1_ASM(), 
          internalVO.getF0911Z1_BC(), internalVO.getF0911Z1_VINV(), 
          internalVO.getF0911Z1_IVD(), internalVO.getF0911Z1_WR01(), 
          internalVO.getF0911Z1_PO(), internalVO.getF0911Z1_PSFX(), 
          internalVO.getF0911Z1_DCTO(), internalVO.getF0911Z1_LNID(), 
          internalVO.getF0911Z1_WY(), internalVO.getF0911Z1_WN(), 
          internalVO.getF0911Z1_FNLP(), internalVO.getF0911Z1_OPSQ(), 
          internalVO.getF0911Z1_JBCD(), internalVO.getF0911Z1_JBST(), 
          internalVO.getF0911Z1_HMCU(), internalVO.getF0911Z1_DOI(), 
          internalVO.getF0911Z1_ALID(), internalVO.getF0911Z1_ALTY(), 
          internalVO.getF0911Z1_DSVJ(), internalVO.getF0911Z1_TORG(), 
          internalVO.getF0911Z1_REG_Pound(), internalVO.getF0911Z1_PYID(), 
          internalVOGetAuditInfo.getUser_USER(), 
          internalVOGetAuditInfo.getProgramId_PID(), 
          internalVOGetAuditInfo.getWorkStationId_JOBN(), 
          internalVOGetAuditInfo.getDate_UPMJ(), 
          internalVOGetAuditInfo.getTime_UPMT(), internalVO.getF0911Z1_CRRM(), 
          internalVO.getF0911Z1_ACR(), internalVO.getF0911Z1_DGM(), 
          internalVO.getF0911Z1_DGD(), internalVO.getF0911Z1_DGY(), 
          internalVO.getF0911Z1_DG_Pound(), internalVO.getF0911Z1_DICM(), 
          internalVO.getF0911Z1_DICD(), internalVO.getF0911Z1_DICY(), 
          internalVO.getF0911Z1_DIC_Pound(), internalVO.getF0911Z1_DSYM(), 
          internalVO.getF0911Z1_DSYD(), internalVO.getF0911Z1_DSYY(), 
          internalVO.getF0911Z1_DSY_Pound(), internalVO.getF0911Z1_DKM(), 
          internalVO.getF0911Z1_DKD(), internalVO.getF0911Z1_DKY(), 
          internalVO.getF0911Z1_DK_Pound(), internalVO.getF0911Z1_DSVM(), 
          internalVO.getF0911Z1_DSVD(), internalVO.getF0911Z1_DSVY(), 
          internalVO.getF0911Z1_DSV_Pound(), internalVO.getF0911Z1_HDGM(), 
          internalVO.getF0911Z1_HDGD(), internalVO.getF0911Z1_HDGY(), 
          internalVO.getF0911Z1_HDG_Pound(), internalVO.getF0911Z1_DKCM(), 
          internalVO.getF0911Z1_DKCD(), internalVO.getF0911Z1_DKCY(), 
          internalVO.getF0911Z1_DKC_Pound(), internalVO.getF0911Z1_IVDM(), 
          internalVO.getF0911Z1_IVDD(), internalVO.getF0911Z1_IVDY(), 
          internalVO.getF0911Z1_IVD_Pound(), internalVO.getF0911Z1_ABR1(), 
          internalVO.getF0911Z1_ABR2(), internalVO.getF0911Z1_ABR3(), 
          internalVO.getF0911Z1_ABR4(), internalVO.getF0911Z1_ABT1(), 
          internalVO.getF0911Z1_ABT2(), internalVO.getF0911Z1_ABT3(), 
          internalVO.getF0911Z1_ABT4(), internalVO.getF0911Z1_ITM(), 
          internalVO.getF0911Z1_PM01(), internalVO.getF0911Z1_PM02(), 
          internalVO.getF0911Z1_PM03(), internalVO.getF0911Z1_PM04(), 
          internalVO.getF0911Z1_PM05(), internalVO.getF0911Z1_PM06(), 
          internalVO.getF0911Z1_PM07(), internalVO.getF0911Z1_PM08(), 
          internalVO.getF0911Z1_PM09(), internalVO.getF0911Z1_PM10(), 
          internalVO.getF0911Z1_BCRC(), internalVO.getF0911Z1_EXR1(), 
          internalVO.getF0911Z1_TXA1(), internalVO.getF0911Z1_TXITM(), 
          internalVO.getF0911Z1_ACTB(), internalVO.getF0911Z1_STAM(), 
          internalVO.getF0911Z1_CTAM(), internalVO.getF0911Z1_AG(), 
          internalVO.getF0911Z1_AGF(), internalVO.getF0911Z1_TKTX(), 
          internalVO.getF0911Z1_DLNID(), internalVO.getF0911Z1_CKNU(), 
          internalVO.getF0911Z1_BUPC(), internalVO.getF0911Z1_AHBU(), 
          internalVO.getF0911Z1_EPGC(), internalVO.getF0911Z1_JPGC() };

        try {
            //get dbService from context
            IDBService dbService = context.getDBService();
            //execute db insert operation
            dbService.insert(context, connection, "F0911Z1", 
                             IDBService.DB_TABLE, insertFields, insertValues);
        } catch (DBServiceException e) {
            returnMessages.addMessage(new E1Message(context, "005FIS", 
                                                    e.getMessage()));
        }

        return returnMessages;
    }

    /**
     * Calls the GetAuditInfo(B9800100) business function which has the D9800100 datastructure and gets
     * the program id from system constants
     * @param context conditionally provides the connection for the business function call and logging information
     * @param connection can either be an explicit connection or null. If null the default connection is used.
     * @param internalVO InternalGetAuditInfo
     * @return A list of messages if there were application errors, warnings, or informational 
     * messages. Returns null if there were no messages.
     */
    private static E1MessageList getAuditInfo(IContext context, 
                                              IConnection connection, 
                                              InternalGetAuditInfo internalVO) {
        BSFNParameters bsfnParams = new BSFNParameters();
        E1MessageList returnMessages = new E1MessageList();
        // map input parameters from input value object

        try {
            //get bsfnService from context
            IBSFNService bsfnService = context.getBSFNService();

            //execute business function
            bsfnService.execute(context, connection, "GetAuditInfo", 
                                bsfnParams);
        } catch (BSFNServiceInvalidArgException invalidArgEx) {
            //Create new E1 Message using DD item for service constant exception.
            E1Message message = 
                new E1Message(context, "018FIS", invalidArgEx.getMessage());
            //Change message type from error to warning - don't want BSSV to fail as result.
            message.changeMessageType(E1Message.WARNING_MSG_TYPE);
            //Add messages to final message list to be returned.
            returnMessages.addMessage(message);
        } catch (BSFNServiceSystemException systemEx) {

            //Create new E1 Message using DD item for service constant exception.
            E1Message message = 
                new E1Message(context, "019FIS", systemEx.getMessage());
            //Change message type from error to warning - don't want BSSV to fail as result.
            message.changeMessageType(E1Message.WARNING_MSG_TYPE);
            //Add messages to final message list to be returned.
            returnMessages.addMessage(message);

        }

        //map output parameters to output value object
        internalVO.setUser_USER((String)bsfnParams.getValue("szUserName"));
        internalVO.setDate_UPMJ((Date)bsfnParams.getValue("jdDate"));
        internalVO.setTime_UPMT((MathNumeric)bsfnParams.getValue("mnTime"));
        internalVO.setWorkStationId_JOBN((String)bsfnParams.getValue("szWorkstation_UserId"));

        internalVO.setProgramId_PID(PROGRAM_ID);

        //return any errors, warnings, or informational messages to the caller
        return returnMessages;
    }
}
