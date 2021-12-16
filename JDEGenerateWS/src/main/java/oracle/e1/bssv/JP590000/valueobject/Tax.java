package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import java.math.BigDecimal;

import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssvfoundation.util.MathNumeric;

public class Tax extends ValueObject implements Serializable {

    /**
     * Tax Explanation Code 1<br>
     * <p>
     * A hard-coded user defined code (00/EX) that controls the algorithm that<br>
     * the system uses to calculate tax and G/L distribution amounts. The<br>
     * system uses the tax explanation code in conjunction with the tax rate<br>
     * area and tax rules to determine how the tax is calculated. Each<br>
     * transaction pay item can be defined with a different tax explanation<br>
     * code.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: EXR1 <br>
     * EnterpriseOne field length:  2 <br>
     * EnterpriseOne User Defined Code: 00/EX<br>
     */
    private String taxExplanationCode = null;

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
     * Tax - Short Item Number<br>
     * <p>
     * A code that identifies a group of items or a single item for tax<br>
     * purposes. Items that have a group code are usually assessed a VAT. Items<br>
     * that have a specific item code are usually assessed a luxury tax.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TXITM <br>
     * EnterpriseOne field length:  8 <br>
     * EnterpriseOne decimal places: 0 <br>
     */
    private Integer itemIdTax = null;

    /**
     * Amount - Tax Domestic<br>
     * <p>
     * The amount assessed and payable to tax authorities. It is the total of<br>
     * the VAT, use, and sales taxes (PST).<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: STAM <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private BigDecimal amountTaxDomestic = null;

    /**
     * Amount - Foreign Tax<br>
     * <p>
     * The tax amount in foreign currency.  This can be either the sales, use,<br>
     * or VAT tax.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: CTAM <br>
     * EnterpriseOne field length:  15 <br>
     * EnterpriseOne decimal places: 2 <br>
     */
    private BigDecimal amountTaxForeign = null;

    /**
     * Track Taxes Flag<br>
     * <p>
     * A code that specifies whether to update the General Ledger table (F0911)<br>
     * with tax information in order to track taxes for a general ledger<br>
     * account. Valid values are:<br>
     * <br>
     * 1<br>
     * Update the tax explanation code field, tax rate area field, and tax item<br>
     * number field in the General Ledger table (F0911) to track taxes.<br>
     * <br>
     * 0<br>
     * Do not update the tax explanation code field, tax rate area field, or<br>
     * tax item number field in the General Ledger table (F0911) to track<br>
     * taxes.<br>
     * </p>
     * EnterpriseOne Key Field: false <br>
     * EnterpriseOne Alias: TKTX <br>
     * EnterpriseOne field length:  1 <br>
     */
    private String trackTaxes = null;
    
    public Tax() {
    }

    public void setTaxExplanationCode(String taxExplanationCode) {
        this.taxExplanationCode = taxExplanationCode;
    }

    public String getTaxExplanationCode() {
        return taxExplanationCode;
    }

    public void setTaxRateAreaCode(String taxRateAreaCode) {
        this.taxRateAreaCode = taxRateAreaCode;
    }

    public String getTaxRateAreaCode() {
        return taxRateAreaCode;
    }

    public void setItemIdTax(Integer itemIdTax) {
        this.itemIdTax = itemIdTax;
    }

    public void setItemIdTax(MathNumeric itemIdTax) {
        this.itemIdTax = new Integer(itemIdTax.intValue());
    }

    public Integer getItemIdTax() {
        return itemIdTax;
    }

    public void setAmountTaxDomestic(BigDecimal amountTaxDomestic) {
        this.amountTaxDomestic = amountTaxDomestic;
    }

    public void setAmountTaxDomestic(MathNumeric amountTaxDomestic) {
        this.amountTaxDomestic = amountTaxDomestic.asBigDecimal();
    }

    public BigDecimal getAmountTaxDomestic() {
        return amountTaxDomestic;
    }

    public void setAmountTaxForeign(BigDecimal amountTaxForeign) {
        this.amountTaxForeign = amountTaxForeign;
    }

    public void setAmountTaxForeign(MathNumeric amountTaxForeign) {
        this.amountTaxForeign = amountTaxForeign.asBigDecimal();
    }

    public BigDecimal getAmountTaxForeign() {
        return amountTaxForeign;
    }

    public void setTrackTaxes(String trackTaxes) {
        this.trackTaxes = trackTaxes;
    }

    public String getTrackTaxes() {
        return trackTaxes;
    }
}
