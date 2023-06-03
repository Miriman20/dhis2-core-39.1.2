package org.hisp.dhis.pbf.excelreports;

public class CalculationsHolder {

	private long facilityId;
	
	private String facilityName;
	
	private String districtName;
	
	private String provinceName;
	
	private String countryName;
	
	private String periodQuarter;

	private String type;
	
	private Double indOneAmount;

	private Double indTwoAmount;
	
	private Double indThreeAmount;
	
	private Double indFourAmount;
	
	private Double indFiveAmount;
	
	private Double indSixAmount;
	
	private Double indSevenAmount;
	
	private Double indOneQtyF;

	private Double indTwoQtyF;
	
	private Double indThreeQtyF;
	
	private Double indFourQtyF;
	
	private Double indFiveQtyF;
	
	private Double indSixQtyF;
	
	private Double indSevenQtyF;
	
	private Double indOneQtyV;

	private Double indTwoQtyV;
	
	private Double indThreeQtyV;
	
	private Double indFourQtyV;
	
	private Double indFiveQtyV;
	
	private Double indSixQtyV;
	
	private Double indSevenQtyV;
	
	private Double indOneThreV;

	private Double indTwoThreV;
	
	private Double indThreeThreV;
	
	private Double indFourThreV;
	
	private Double indFiveThreV;
	
	private Double indSixThreV;
	
	private Double indSevenThreV;
	
	private Double indOneBasV;

	private Double indTwoBasV;
	
	private Double indThreeBasV;
	
	private Double indFourBasV;
	
	private Double indFiveBasV;
	
	private Double indSixBasV;
	
	private Double indSevenBasV;
	
	private Double indTotalQtyF;
	
	private Double indTotalQtyV;
	
	private Double bonusAmount;
	
	private Double bonusPercent;
	
	private Double paymentWithoutBonusAmount;
	
	private Double paymentWithBonusAmount;
	
	
	public CalculationsHolder(int facilityId, String facilityName, String districtName, String provinceName,
			String countryName, String periodQuarter, String type) {
		super();
		this.facilityId = facilityId;
		this.facilityName = facilityName;
		this.districtName = districtName;
		this.provinceName = provinceName;
		this.countryName = countryName;
		this.periodQuarter = periodQuarter;
		this.type = type;
		
	}

	public CalculationsHolder() {
		// TODO Auto-generated constructor stub
	}

	public long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(long facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getPeriodQuarter() {
		return periodQuarter;
	}

	public void setPeriodQuarter(String periodQuarter) {
		this.periodQuarter = periodQuarter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getIndOneAmount() {
		return indOneAmount;
	}

	public void setIndOneAmount(Double indOneAmount) {
		this.indOneAmount = indOneAmount;
	}

	public Double getIndTwoAmount() {
		return indTwoAmount;
	}

	public void setIndTwoAmount(Double indTwoAmount) {
		this.indTwoAmount = indTwoAmount;
	}

	public Double getIndThreeAmount() {
		return indThreeAmount;
	}

	public void setIndThreeAmount(Double indThreeAmount) {
		this.indThreeAmount = indThreeAmount;
	}

	public Double getIndFourAmount() {
		return indFourAmount;
	}

	public void setIndFourAmount(Double indFourAmount) {
		this.indFourAmount = indFourAmount;
	}

	public Double getIndFiveAmount() {
		return indFiveAmount;
	}

	public void setIndFiveAmount(Double indFiveAmount) {
		this.indFiveAmount = indFiveAmount;
	}

	public Double getIndSixAmount() {
		return indSixAmount;
	}

	public void setIndSixAmount(Double indSixAmount) {
		this.indSixAmount = indSixAmount;
	}

	public Double getIndSevenAmount() {
		return indSevenAmount;
	}

	public void setIndSevenAmount(Double indSevenAmount) {
		this.indSevenAmount = indSevenAmount;
	}

	public Double getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public Double getPaymentWithoutBonusAmount() {
		return paymentWithoutBonusAmount;
	}

	public void setPaymentWithoutBonusAmount(Double paymentWithoutBonusAmount) {
		this.paymentWithoutBonusAmount = paymentWithoutBonusAmount;
	}

	public Double getPaymentWithBonusAmount() {
		return paymentWithBonusAmount;
	}

	public void setPaymentWithBonusAmount(Double paymentWithBonusAmount) {
		this.paymentWithBonusAmount = paymentWithBonusAmount;
	}

	public Double getIndOneQtyF() {
		return indOneQtyF;
	}

	public void setIndOneQtyF(Double indOneQtyF) {
		this.indOneQtyF = indOneQtyF;
	}

	public Double getIndTwoQtyF() {
		return indTwoQtyF;
	}

	public void setIndTwoQtyF(Double indTwoQtyF) {
		this.indTwoQtyF = indTwoQtyF;
	}

	public Double getIndThreeQtyF() {
		return indThreeQtyF;
	}

	public void setIndThreeQtyF(Double indThreeQtyF) {
		this.indThreeQtyF = indThreeQtyF;
	}

	public Double getIndFourQtyF() {
		return indFourQtyF;
	}

	public void setIndFourQtyF(Double indFourQtyF) {
		this.indFourQtyF = indFourQtyF;
	}

	public Double getIndFiveQtyF() {
		return indFiveQtyF;
	}

	public void setIndFiveQtyF(Double indFiveQtyF) {
		this.indFiveQtyF = indFiveQtyF;
	}

	public Double getIndSixQtyF() {
		return indSixQtyF;
	}

	public void setIndSixQtyF(Double indSixQtyF) {
		this.indSixQtyF = indSixQtyF;
	}

	public Double getIndSevenQtyF() {
		return indSevenQtyF;
	}

	public void setIndSevenQtyF(Double indSevenQtyF) {
		this.indSevenQtyF = indSevenQtyF;
	}

	public Double getIndOneQtyV() {
		return indOneQtyV;
	}

	public void setIndOneQtyV(Double indOneQtyV) {
		this.indOneQtyV = indOneQtyV;
	}

	public Double getIndTwoQtyV() {
		return indTwoQtyV;
	}

	public void setIndTwoQtyV(Double indTwoQtyV) {
		this.indTwoQtyV = indTwoQtyV;
	}

	public Double getIndThreeQtyV() {
		return indThreeQtyV;
	}

	public void setIndThreeQtyV(Double indThreeQtyV) {
		this.indThreeQtyV = indThreeQtyV;
	}

	public Double getIndFourQtyV() {
		return indFourQtyV;
	}

	public void setIndFourQtyV(Double indFourQtyV) {
		this.indFourQtyV = indFourQtyV;
	}

	public Double getIndFiveQtyV() {
		return indFiveQtyV;
	}

	public void setIndFiveQtyV(Double indFiveQtyV) {
		this.indFiveQtyV = indFiveQtyV;
	}

	public Double getIndSixQtyV() {
		return indSixQtyV;
	}

	public void setIndSixQtyV(Double indSixQtyV) {
		this.indSixQtyV = indSixQtyV;
	}

	public Double getIndSevenQtyV() {
		return indSevenQtyV;
	}

	public void setIndSevenQtyV(Double indSevenQtyV) {
		this.indSevenQtyV = indSevenQtyV;
	}

	public Double getBonusPercent() {
		return bonusPercent;
	}

	public void setBonusPercent(Double bonusPercent) {
		this.bonusPercent = bonusPercent;
	}

	public Double getIndTotalQtyF() {
		return indTotalQtyF;
	}

	public void setIndTotalQtyF(Double indTotalQtyF) {
		this.indTotalQtyF = indTotalQtyF;
	}

	public Double getIndTotalQtyV() {
		return indTotalQtyV;
	}

	public void setIndTotalQtyV(Double indTotalQtyV) {
		this.indTotalQtyV = indTotalQtyV;
	}

	public Double getIndOneThreV() {
		return indOneThreV;
	}

	public void setIndOneThreV(Double indOneThreV) {
		this.indOneThreV = indOneThreV;
	}

	public Double getIndTwoThreV() {
		return indTwoThreV;
	}

	public void setIndTwoThreV(Double indTwoThreV) {
		this.indTwoThreV = indTwoThreV;
	}

	public Double getIndThreeThreV() {
		return indThreeThreV;
	}

	public void setIndThreeThreV(Double indThreeThreV) {
		this.indThreeThreV = indThreeThreV;
	}

	public Double getIndFourThreV() {
		return indFourThreV;
	}

	public void setIndFourThreV(Double indFourThreV) {
		this.indFourThreV = indFourThreV;
	}

	public Double getIndFiveThreV() {
		return indFiveThreV;
	}

	public void setIndFiveThreV(Double indFiveThreV) {
		this.indFiveThreV = indFiveThreV;
	}

	public Double getIndSixThreV() {
		return indSixThreV;
	}

	public void setIndSixThreV(Double indSixThreV) {
		this.indSixThreV = indSixThreV;
	}

	public Double getIndSevenThreV() {
		return indSevenThreV;
	}

	public void setIndSevenThreV(Double indSevenThreV) {
		this.indSevenThreV = indSevenThreV;
	}

	public Double getIndOneBasV() {
		return indOneBasV;
	}

	public void setIndOneBasV(Double indOneBasV) {
		this.indOneBasV = indOneBasV;
	}

	public Double getIndTwoBasV() {
		return indTwoBasV;
	}

	public void setIndTwoBasV(Double indTwoBasV) {
		this.indTwoBasV = indTwoBasV;
	}

	public Double getIndThreeBasV() {
		return indThreeBasV;
	}

	public void setIndThreeBasV(Double indThreeBasV) {
		this.indThreeBasV = indThreeBasV;
	}

	public Double getIndFourBasV() {
		return indFourBasV;
	}

	public void setIndFourBasV(Double indFourBasV) {
		this.indFourBasV = indFourBasV;
	}

	public Double getIndFiveBasV() {
		return indFiveBasV;
	}

	public void setIndFiveBasV(Double indFiveBasV) {
		this.indFiveBasV = indFiveBasV;
	}

	public Double getIndSixBasV() {
		return indSixBasV;
	}

	public void setIndSixBasV(Double indSixBasV) {
		this.indSixBasV = indSixBasV;
	}

	public Double getIndSevenBasV() {
		return indSevenBasV;
	}

	public void setIndSevenBasV(Double indSevenBasV) {
		this.indSevenBasV = indSevenBasV;
	}

}
