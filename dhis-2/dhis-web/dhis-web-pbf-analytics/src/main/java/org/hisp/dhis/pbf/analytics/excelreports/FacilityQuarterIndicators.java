package org.hisp.dhis.pbf.analytics.excelreports;

public class FacilityQuarterIndicators {

	private int sortOrder;
	
	private String indicatorName;
	
	private Double quarterQtyFacility; //de total per quarter quantity value
	private Double quarterQtyFacilityMale; //de total per quarter quantity value
	private Double quarterQtyFacilityFemale; //de total per quarter quantity value
	
	private Double quarterQtyIntVer; //de total per quarter quantity value
	private Double quarterQtyIntVerMale; //de total per quarter quantity value
	private Double quarterQtyIntVerFemale; //de total per quarter quantity value
	
	public FacilityQuarterIndicators(int sortOrder, String indicatorName, Double quarterQtyFacility,
			Double quarterQtyFacilityMale, Double quarterQtyFacilityFemale, Double quarterQtyIntVer,
			Double quarterQtyIntVerMale, Double quarterQtyIntVerFemale) {
		super();
		this.sortOrder = sortOrder;
		this.indicatorName = indicatorName;
		this.quarterQtyFacility = quarterQtyFacility;
		this.quarterQtyFacilityMale = quarterQtyFacilityMale;
		this.quarterQtyFacilityFemale = quarterQtyFacilityFemale;
		this.quarterQtyIntVer = quarterQtyIntVer;
		this.quarterQtyIntVerMale = quarterQtyIntVerMale;
		this.quarterQtyIntVerFemale = quarterQtyIntVerFemale;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public Double getQuarterQtyFacility() {
		return quarterQtyFacility;
	}
	public void setQuarterQtyFacility(Double quarterQtyFacility) {
		this.quarterQtyFacility = quarterQtyFacility;
	}
	public Double getQuarterQtyFacilityMale() {
		return quarterQtyFacilityMale;
	}
	public void setQuarterQtyFacilityMale(Double quarterQtyFacilityMale) {
		this.quarterQtyFacilityMale = quarterQtyFacilityMale;
	}
	public Double getQuarterQtyFacilityFemale() {
		return quarterQtyFacilityFemale;
	}
	public void setQuarterQtyFacilityFemale(Double quarterQtyFacilityFemale) {
		this.quarterQtyFacilityFemale = quarterQtyFacilityFemale;
	}
	public Double getQuarterQtyIntVer() {
		return quarterQtyIntVer;
	}
	public void setQuarterQtyIntVer(Double quarterQtyIntVer) {
		this.quarterQtyIntVer = quarterQtyIntVer;
	}
	public Double getQuarterQtyIntVerMale() {
		return quarterQtyIntVerMale;
	}
	public void setQuarterQtyIntVerMale(Double quarterQtyIntVerMale) {
		this.quarterQtyIntVerMale = quarterQtyIntVerMale;
	}
	public Double getQuarterQtyIntVerFemale() {
		return quarterQtyIntVerFemale;
	}
	public void setQuarterQtyIntVerFemale(Double quarterQtyIntVerFemale) {
		this.quarterQtyIntVerFemale = quarterQtyIntVerFemale;
	}
	
	
}
