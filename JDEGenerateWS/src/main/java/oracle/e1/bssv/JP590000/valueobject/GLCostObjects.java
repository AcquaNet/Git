package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import oracle.e1.bssvfoundation.base.ValueObject;

public class GLCostObjects extends ValueObject implements Serializable {

    /**
     * Cost Object Code 1<br>
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
    private String costObjectCode1 = null;

    /**
     * Cost Object Code 2<br>
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
    private String costObjectCode2 = null;

    /**
     * Cost Object Code 3<br>
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
    private String costObjectCode3 = null;

    /**
     * Cost Object Code 4<br>
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
    private String costObjectCode4 = null;

    /**
     * Cost Object Type Code 1<br>
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
    private String costObjectTypeCode1 = null;

    /**
     * Cost Object Type Code 2<br>
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
    private String costObjectTypeCode2 = null;

    /**
     * Cost Object Type Code 3<br>
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
    private String costObjectTypeCode3 = null;

    /**
     * Cost Object Type Code 4<br>
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
    private String costObjectTypeCode4 = null;
    
    public GLCostObjects() {
    }

    public void setCostObjectCode1(String costObjectCode1) {
        this.costObjectCode1 = costObjectCode1;
    }

    public String getCostObjectCode1() {
        return costObjectCode1;
    }

    public void setCostObjectCode2(String costObjectCode2) {
        this.costObjectCode2 = costObjectCode2;
    }

    public String getCostObjectCode2() {
        return costObjectCode2;
    }

    public void setCostObjectCode3(String costObjectCode3) {
        this.costObjectCode3 = costObjectCode3;
    }

    public String getCostObjectCode3() {
        return costObjectCode3;
    }

    public void setCostObjectCode4(String costObjectCode4) {
        this.costObjectCode4 = costObjectCode4;
    }

    public String getCostObjectCode4() {
        return costObjectCode4;
    }

    public void setCostObjectTypeCode1(String costObjectTypeCode1) {
        this.costObjectTypeCode1 = costObjectTypeCode1;
    }

    public String getCostObjectTypeCode1() {
        return costObjectTypeCode1;
    }

    public void setCostObjectTypeCode2(String costObjectTypeCode2) {
        this.costObjectTypeCode2 = costObjectTypeCode2;
    }

    public String getCostObjectTypeCode2() {
        return costObjectTypeCode2;
    }

    public void setCostObjectTypeCode3(String costObjectTypeCode3) {
        this.costObjectTypeCode3 = costObjectTypeCode3;
    }

    public String getCostObjectTypeCode3() {
        return costObjectTypeCode3;
    }

    public void setCostObjectTypeCode4(String costObjectTypeCode4) {
        this.costObjectTypeCode4 = costObjectTypeCode4;
    }

    public String getCostObjectTypeCode4() {
        return costObjectTypeCode4;
    }
}
