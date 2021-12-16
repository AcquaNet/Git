package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import oracle.e1.bssvfoundation.base.ValueObject;


public class GetGLCostObjects  extends ValueObject implements Serializable {
    public GetGLCostObjects() {
    }
    
    /**
     * Cost Object Edit Code 02<br>
     * <p>
     * A code that specifies the cost object type that users must enter into the <br>
     *  Cost Object Type 1 field when entering transactions for this account. <br>
     *  Use the search button to locate and select a valid value. <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: CEC1<br>
     * EnterpriseOne field length: 1<br>
     */
    private String costObjectEditCode1; 
    
    /**
     * Cost Object Edit Code 02<br>
     * <p>
     * A code that specifies the cost object type that users must enter into the <br>
     *  Cost Object Type 2 field when entering transactions for this account. <br>
     *  Use the search button to locate and select a valid value. <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: CEC1<br>
     * EnterpriseOne field length: 1<br>
     */
    private String costObjectEditCode2; 

    /**
     * Cost Object Edit Code 03<br>
     * <p>
     * A code that specifies the cost object type that users must enter into the <br>
     *  Cost Object Type 3 field when entering transactions for this account. <br>
     *  Use the search button to locate and select a valid value. <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: CEC1<br>
     * EnterpriseOne field length: 1<br>
     */
    private String costObjectEditCode3; 
    
    /**
     * Cost Object Edit Code 04<br>
     * <p>
     * A code that specifies the cost object type that users must enter into the <br>
     *  Cost Object Type 4 field when entering transactions for this account. <br>
     *  Use the search button to locate and select a valid value. <br>
     * <br>
     * </p>
     * EnterpriseOne Key Field: false<br>
     * EnterpriseOne Alias: CEC4<br>
     * EnterpriseOne field length: 1<br>
     */
    private String costObjectEditCode4;

    public void setCostObjectEditCode1(String costObjectEditCode1) {
        this.costObjectEditCode1 = costObjectEditCode1;
    }

    public String getCostObjectEditCode1() {
        return costObjectEditCode1;
    }

    public void setCostObjectEditCode2(String costObjectEditCode2) {
        this.costObjectEditCode2 = costObjectEditCode2;
    }

    public String getCostObjectEditCode2() {
        return costObjectEditCode2;
    }

    public void setCostObjectEditCode3(String costObjectEditCode3) {
        this.costObjectEditCode3 = costObjectEditCode3;
    }

    public String getCostObjectEditCode3() {
        return costObjectEditCode3;
    }

    public void setCostObjectEditCode4(String costObjectEditCode4) {
        this.costObjectEditCode4 = costObjectEditCode4;
    }

    public String getCostObjectEditCode4() {
        return costObjectEditCode4;
    }
}

