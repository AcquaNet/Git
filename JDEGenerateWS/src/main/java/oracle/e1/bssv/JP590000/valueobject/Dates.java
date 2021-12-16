package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import java.util.Calendar;

import java.util.Date;

import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssvfoundation.util.MathNumeric;

public class Dates extends ValueObject implements Serializable {
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
    private Calendar dateBatch = null;

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
    private Calendar dateBatchSystem = null;

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
    private Calendar dateCheck = null;

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
    private Calendar dateCheckCleared = null;

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
    private Calendar dateInvoice = null;

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
    private Calendar dateServiceTax = null;

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
    private Integer dateGLMonth = null;

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
    private Integer dateGLDay = null;

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
    private Integer dateGLYear = null;

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
    private Integer dateGLCentury = null;

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
    private Integer dateBatchMonth = null;

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
    private Integer dateBatchDay = null;

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
    private Integer dateBatchYear = null;

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
    private Integer dateBatchCentury = null;

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
     * EnterpriseOne Edit Rule: RANGE 01 12<br>
     */
    private Integer dateBatchSystemMonth = null;

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
     * EnterpriseOne Edit Rule: RANGE 01 31<br>
     */
    private Integer dateBatchSystemDay = null;

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
    private Integer dateBatchSystemYear = null;

    /**
     * Date - Batch System Date - CTRY<br>
     * <p>
     * Date - Batch System Date - CTRY<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: DSY# <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne decimal places: 0 <br>
     * EnterpriseOne Edit Rule: RANGE 01 31<br>
     */
    private Integer dateBatchSystemCentury = null;

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
    private Integer dateCheckMonth = null;

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
    private Integer dateCheckDay = null;

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
    private Integer dateCheckYear = null;

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
    private Integer dateCheckCentury = null;

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
    private Integer dateServiceTaxMonth = null;

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
    private Integer dateServiceTaxDay = null;

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
    private Integer dateServiceTaxYear = null;

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
    private Integer dateServiceTaxCentury = null;

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
    private Integer dateHistoricalMonth = null;

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
    private Integer dateHistoricalDay = null;

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
    private Integer dateHistoricalYear = null;

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
    private Integer dateHistoricalCentury = null;

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
    private Integer dateCheckClearedMonth = null;

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
    private Integer dateCheckClearedDay = null;

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
    private Integer dateCheckClearedYear = null;

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
    private Integer dateCheckClearedCentury = null;

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
    private Integer dateInvoiceMonth = null;

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
    private Integer dateInvoiceDay = null;

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
    private Integer dateInvoiceYear = null;

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
    private Integer dateInvoiceCentury = null;
    public Dates() {
    }

    public void setDateBatch(Calendar dateBatch) {
        this.dateBatch = dateBatch;
    }
    
    public void setDateBatch(Date dateBatch) {       
        if(dateBatch != null){
            Calendar effDate = Calendar.getInstance();
            effDate.setTime(dateBatch);          
          this.dateBatch = effDate;
      }
    }

    public Calendar getDateBatch() {
        return dateBatch;
    }

    public void setDateBatchSystem(Calendar dateBatchSystem) {
        this.dateBatchSystem = dateBatchSystem;
    }

    public void setDateBatchSystem(Date dateBatchSystem) {       
        if(dateBatchSystem != null){
            Calendar effDate = Calendar.getInstance();
            effDate.setTime(dateBatchSystem);          
          this.dateBatchSystem = effDate;
      }
    }    

    public Calendar getDateBatchSystem() {
        return dateBatchSystem;
    }

    public void setDateCheck(Calendar dateCheck) {
        this.dateCheck = dateCheck;
    }

    public void setDateCheck(Date dateCheck) {       
        if(dateCheck != null){
            Calendar effDate = Calendar.getInstance();
            effDate.setTime(dateCheck);          
          this.dateCheck = effDate;
      }
    }

    public Calendar getDateCheck() {
        return dateCheck;
    }

    public void setDateCheckCleared(Calendar dateCheckCleared) {
        this.dateCheckCleared = dateCheckCleared;
    }

    public void setDateCheckCleared(Date dateCheckCleared) {       
        if(dateCheckCleared != null){
            Calendar effDate = Calendar.getInstance();
            effDate.setTime(dateCheckCleared);          
          this.dateCheckCleared = effDate;
      }
    }

    public Calendar getDateCheckCleared() {
        return dateCheckCleared;
    }

    public void setDateInvoice(Calendar dateInvoice) {
        this.dateInvoice = dateInvoice;
    }

    public void setDateInvoice(Date dateInvoice) {       
        if(dateInvoice != null){
            Calendar effDate = Calendar.getInstance();
            effDate.setTime(dateInvoice);          
          this.dateInvoice = effDate;
      }
    }

    public Calendar getDateInvoice() {
        return dateInvoice;
    }

    public void setDateServiceTax(Calendar dateServiceTax) {
        this.dateServiceTax = dateServiceTax;
    }

    public void setDateServiceTax(Date dateServiceTax) {       
        if(dateServiceTax != null){
            Calendar effDate = Calendar.getInstance();
            effDate.setTime(dateServiceTax);          
          this.dateServiceTax = effDate;
      }
    }

    public Calendar getDateServiceTax() {
        return dateServiceTax;
    }

    public void setDateGLMonth(Integer dateGLMonth) {
        this.dateGLMonth = dateGLMonth;
    }

    public void setDateGLMonth(MathNumeric dateGLMonth) {
        this.dateGLMonth = new Integer(dateGLMonth.intValue());
    }

    public Integer getDateGLMonth() {
        return dateGLMonth;
    }

    public void setDateGLDay(Integer dateGLDay) {
        this.dateGLDay = dateGLDay;
    }

    public void setDateGLDay(MathNumeric dateGLDay) {
        this.dateGLDay = new Integer(dateGLDay.intValue());
    }

    public Integer getDateGLDay() {
        return dateGLDay;
    }

    public void setDateGLYear(Integer dateGLYear) {
        this.dateGLYear = dateGLYear;
    }

    public void setDateGLYear(MathNumeric dateGLYear) {
        this.dateGLYear = new Integer(dateGLYear.intValue());
    }

    public Integer getDateGLYear() {
        return dateGLYear;
    }

    public void setDateGLCentury(Integer dateGLCentury) {
        this.dateGLCentury = dateGLCentury;
    }

    public void setDateGLCentury(MathNumeric dateGLCentury) {
        this.dateGLCentury = new Integer(dateGLCentury.intValue());
    }

    public Integer getDateGLCentury() {
        return dateGLCentury;
    }

    public void setDateBatchMonth(Integer dateBatchMonth) {
        this.dateBatchMonth = dateBatchMonth;
    }

    public void setDateBatchMonth(MathNumeric dateBatchMonth) {
        this.dateBatchMonth = new Integer(dateBatchMonth.intValue());
    }

    public Integer getDateBatchMonth() {
        return dateBatchMonth;
    }

    public void setDateBatchDay(Integer dateBatchDay) {
        this.dateBatchDay = dateBatchDay;
    }

    public void setDateBatchDay(MathNumeric dateBatchDay) {
        this.dateBatchDay = new Integer(dateBatchDay.intValue());
    }

    public Integer getDateBatchDay() {
        return dateBatchDay;
    }

    public void setDateBatchYear(Integer dateBatchYear) {
        this.dateBatchYear = dateBatchYear;
    }

    public void setDateBatchYear(MathNumeric dateBatchYear) {
        this.dateBatchYear = new Integer(dateBatchYear.intValue());
    }

    public Integer getDateBatchYear() {
        return dateBatchYear;
    }

    public void setDateBatchCentury(Integer dateBatchCentury) {
        this.dateBatchCentury = dateBatchCentury;
    }

    public void setDateBatchCentury(MathNumeric dateBatchCentury) {
        this.dateBatchCentury = new Integer(dateBatchCentury.intValue());
    }

    public Integer getDateBatchCentury() {
        return dateBatchCentury;
    }

    public void setDateBatchSystemMonth(Integer dateBatchSystemMonth) {
        this.dateBatchSystemMonth = dateBatchSystemMonth;
    }

    public void setDateBatchSystemMonth(MathNumeric dateBatchSystemMonth) {
        this.dateBatchSystemMonth = new Integer(dateBatchSystemMonth.intValue());
    }

    public Integer getDateBatchSystemMonth() {
        return dateBatchSystemMonth;
    }

    public void setDateBatchSystemDay(Integer dateBatchSystemDay) {
        this.dateBatchSystemDay = dateBatchSystemDay;
    }

    public void setDateBatchSystemDay(MathNumeric dateBatchSystemDay) {
        this.dateBatchSystemDay = new Integer(dateBatchSystemDay.intValue());
    }

    public Integer getDateBatchSystemDay() {
        return dateBatchSystemDay;
    }

    public void setDateBatchSystemYear(Integer dateBatchSystemYear) {
        this.dateBatchSystemYear = dateBatchSystemYear;
    }

    public void setDateBatchSystemYear(MathNumeric dateBatchSystemYear) {
        this.dateBatchSystemYear = new Integer(dateBatchSystemYear.intValue());
    }

    public Integer getDateBatchSystemYear() {
        return dateBatchSystemYear;
    }

    public void setDateBatchSystemCentury(Integer dateBatchSystemCentury) {
        this.dateBatchSystemCentury = dateBatchSystemCentury;
    }

    public void setDateBatchSystemCentury(MathNumeric dateBatchSystemCentury) {
        this.dateBatchSystemCentury = new Integer(dateBatchSystemCentury.intValue());
    }

    public Integer getDateBatchSystemCentury() {
        return dateBatchSystemCentury;
    }

    public void setDateCheckMonth(Integer dateCheckMonth) {
        this.dateCheckMonth = dateCheckMonth;
    }

    public void setDateCheckMonth(MathNumeric dateCheckMonth) {
        this.dateCheckMonth = new Integer(dateCheckMonth.intValue());
    }

    public Integer getDateCheckMonth() {
        return dateCheckMonth;
    }

    public void setDateCheckDay(Integer dateCheckDay) {
        this.dateCheckDay = dateCheckDay;
    }

    public void setDateCheckDay(MathNumeric dateCheckDay) {
        this.dateCheckDay = new Integer(dateCheckDay.intValue());
    }

    public Integer getDateCheckDay() {
        return dateCheckDay;
    }

    public void setDateCheckYear(Integer dateCheckYear) {
        this.dateCheckYear = dateCheckYear;
    }

    public void setDateCheckYear(MathNumeric dateCheckYear) {
        this.dateCheckYear = new Integer(dateCheckYear.intValue());
    }

    public Integer getDateCheckYear() {
        return dateCheckYear;
    }

    public void setDateCheckCentury(Integer dateCheckCentury) {
        this.dateCheckCentury = dateCheckCentury;
    }

    public void setDateCheckCentury(MathNumeric dateCheckCentury) {
        this.dateCheckCentury = new Integer(dateCheckCentury.intValue());
    }

    public Integer getDateCheckCentury() {
        return dateCheckCentury;
    }

    public void setDateServiceTaxMonth(Integer dateServiceTaxMonth) {
        this.dateServiceTaxMonth = dateServiceTaxMonth;
    }

    public void setDateServiceTaxMonth(MathNumeric dateServiceTaxMonth) {
        this.dateServiceTaxMonth = new Integer(dateServiceTaxMonth.intValue());
    }

    public Integer getDateServiceTaxMonth() {
        return dateServiceTaxMonth;
    }

    public void setDateServiceTaxDay(Integer dateServiceTaxDay) {
        this.dateServiceTaxDay = dateServiceTaxDay;
    }

    public void setDateServiceTaxDay(MathNumeric dateServiceTaxDay) {
        this.dateServiceTaxDay = new Integer(dateServiceTaxDay.intValue());
    }

    public Integer getDateServiceTaxDay() {
        return dateServiceTaxDay;
    }

    public void setDateServiceTaxYear(Integer dateServiceTaxYear) {
        this.dateServiceTaxYear = dateServiceTaxYear;
    }

    public void setDateServiceTaxYear(MathNumeric dateServiceTaxYear) {
        this.dateServiceTaxYear = new Integer(dateServiceTaxYear.intValue());
    }

    public Integer getDateServiceTaxYear() {
        return dateServiceTaxYear;
    }

    public void setDateServiceTaxCentury(Integer dateServiceTaxCentury) {
        this.dateServiceTaxCentury = dateServiceTaxCentury;
    }

    public void setDateServiceTaxCentury(MathNumeric dateServiceTaxCentury) {
        this.dateServiceTaxCentury = new Integer(dateServiceTaxCentury.intValue());
    }

    public Integer getDateServiceTaxCentury() {
        return dateServiceTaxCentury;
    }

    public void setDateHistoricalMonth(Integer dateHistoricalMonth) {
        this.dateHistoricalMonth = dateHistoricalMonth;
    }

    public void setDateHistoricalMonth(MathNumeric dateHistoricalMonth) {
        this.dateHistoricalMonth = new Integer(dateHistoricalMonth.intValue());
    }

    public Integer getDateHistoricalMonth() {
        return dateHistoricalMonth;
    }

    public void setDateHistoricalDay(Integer dateHistoricalDay) {
        this.dateHistoricalDay = dateHistoricalDay;
    }

    public void setDateHistoricalDay(MathNumeric dateHistoricalDay) {
        this.dateHistoricalDay = new Integer(dateHistoricalDay.intValue());
    }

    public Integer getDateHistoricalDay() {
        return dateHistoricalDay;
    }

    public void setDateHistoricalYear(Integer dateHistoricalYear) {
        this.dateHistoricalYear = dateHistoricalYear;
    }

    public void setDateHistoricalYear(MathNumeric dateHistoricalYear) {
        this.dateHistoricalYear = new Integer(dateHistoricalYear.intValue());
    }

    public Integer getDateHistoricalYear() {
        return dateHistoricalYear;
    }

    public void setDateHistoricalCentury(Integer dateHistoricalCentury) {
        this.dateHistoricalCentury = dateHistoricalCentury;
    }

    public void setDateHistoricalCentury(MathNumeric dateHistoricalCentury) {
        this.dateHistoricalCentury = new Integer(dateHistoricalCentury.intValue());
    }

    public Integer getDateHistoricalCentury() {
        return dateHistoricalCentury;
    }

    public void setDateCheckClearedMonth(Integer dateCheckClearedMonth) {
        this.dateCheckClearedMonth = dateCheckClearedMonth;
    }

    public void setDateCheckClearedMonth(MathNumeric dateCheckClearedMonth) {
        this.dateCheckClearedMonth = new Integer(dateCheckClearedMonth.intValue());
    }

    public Integer getDateCheckClearedMonth() {
        return dateCheckClearedMonth;
    }

    public void setDateCheckClearedDay(Integer dateCheckClearedDay) {
        this.dateCheckClearedDay = dateCheckClearedDay;
    }

    public void setDateCheckClearedDay(MathNumeric dateCheckClearedDay) {
        this.dateCheckClearedDay = new Integer(dateCheckClearedDay.intValue());
    }

    public Integer getDateCheckClearedDay() {
        return dateCheckClearedDay;
    }

    public void setDateCheckClearedYear(Integer dateCheckClearedYear) {
        this.dateCheckClearedYear = dateCheckClearedYear;
    }

    public void setDateCheckClearedYear(MathNumeric dateCheckClearedYear) {
        this.dateCheckClearedYear = new Integer(dateCheckClearedYear.intValue());
    }

    public Integer getDateCheckClearedYear() {
        return dateCheckClearedYear;
    }

    public void setDateCheckClearedCentury(Integer dateCheckClearedCentury) {
        this.dateCheckClearedCentury = dateCheckClearedCentury;
    }

    public void setDateCheckClearedCentury(MathNumeric dateCheckClearedCentury) {
        this.dateCheckClearedCentury = new Integer(dateCheckClearedCentury.intValue());
    }

    public Integer getDateCheckClearedCentury() {
        return dateCheckClearedCentury;
    }

    public void setDateInvoiceMonth(Integer dateInvoiceMonth) {
        this.dateInvoiceMonth = dateInvoiceMonth;
    }

    public void setDateInvoiceMonth(MathNumeric dateInvoiceMonth) {
        this.dateInvoiceMonth = new Integer(dateInvoiceMonth.intValue());
    }

    public Integer getDateInvoiceMonth() {
        return dateInvoiceMonth;
    }

    public void setDateInvoiceDay(Integer dateInvoiceDay) {
        this.dateInvoiceDay = dateInvoiceDay;
    }

    public void setDateInvoiceDay(MathNumeric dateInvoiceDay) {
        this.dateInvoiceDay = new Integer(dateInvoiceDay.intValue());
    }

    public Integer getDateInvoiceDay() {
        return dateInvoiceDay;
    }

    public void setDateInvoiceYear(Integer dateInvoiceYear) {
        this.dateInvoiceYear = dateInvoiceYear;
    }

    public void setDateInvoiceYear(MathNumeric dateInvoiceYear) {
        this.dateInvoiceYear = new Integer(dateInvoiceYear.intValue());
    }

    public Integer getDateInvoiceYear() {
        return dateInvoiceYear;
    }

    public void setDateInvoiceCentury(Integer dateInvoiceCentury) {
        this.dateInvoiceCentury = dateInvoiceCentury;
    }

    public void setDateInvoiceCentury(MathNumeric dateInvoiceCentury) {
        this.dateInvoiceCentury = new Integer(dateInvoiceCentury.intValue());
    }

    public Integer getDateInvoiceCentury() {
        return dateInvoiceCentury;
    }
}
