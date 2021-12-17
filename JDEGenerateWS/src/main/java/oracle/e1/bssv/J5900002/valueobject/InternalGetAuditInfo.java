package oracle.e1.bssv.J5900002.valueobject;

import java.util.Calendar;
import java.util.Date;

import oracle.e1.bssvfoundation.util.MathNumeric;

public class InternalGetAuditInfo {
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
    private String User_USER = null;

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
    private String ProgramId_PID = null;

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
    private String WorkStationId_JOBN = null;

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
    private Date Date_UPMJ = null;

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
    private MathNumeric Time_UPMT = null;
    
    public InternalGetAuditInfo() {
    }

    public void setUser_USER(String user_USER) {
        this.User_USER = user_USER;
    }

    public String getUser_USER() {
        return User_USER;
    }

    public void setProgramId_PID(String programId_PID) {
        this.ProgramId_PID = programId_PID;
    }

    public String getProgramId_PID() {
        return ProgramId_PID;
    }

    public void setWorkStationId_JOBN(String workStationId_JOBN) {
        this.WorkStationId_JOBN = workStationId_JOBN;
    }

    public String getWorkStationId_JOBN() {
        return WorkStationId_JOBN;
    }

    public void setDate_UPMJ(Date date_UPMJ) {
        this.Date_UPMJ = date_UPMJ;
    }

    public void setDate_UPMJ(Calendar date_UPMJ) {
        if (date_UPMJ != null)
            this.Date_UPMJ = date_UPMJ.getTime();
    }

    public Date getDate_UPMJ() {
        return Date_UPMJ;
    }

    public void setTime_UPMT(MathNumeric time_UPMT) {
        if(time_UPMT != null)
            this.Time_UPMT = time_UPMT;
    }

    public void setTime_UPMT(Integer time_UPMT) {
            this.Time_UPMT = new MathNumeric(time_UPMT);
    }

    public MathNumeric getTime_UPMT() {
        return Time_UPMT;
    }
}
