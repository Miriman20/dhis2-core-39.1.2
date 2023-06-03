package org.hisp.dhis.pbf.model;

/*
 * Copyright (c) 2004-2011, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the HISP project nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hisp.dhis.common.BaseIdentifiableObject;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.period.Period;

/**
 * @author Latifov Murodillo Abdusamadovich
 * @version $Id:
 */
public class PbfAnalyticsReportDetails extends BaseIdentifiableObject implements Serializable{

	/**
	 * Primary keys
	 */
	private static final long serialVersionUID = 1L;

	private OrganisationUnit orgUnit;
	
	private DataElement dataElement;
	
	private CategoryOptionCombo optionCombo;
	
	private Period periodQuarter;

	/**
	 * Other properties
	 */
	
	private String periodName;
	
	private List<PbfAnalyticsReportDetails> indicators = new ArrayList<PbfAnalyticsReportDetails>();
	
	private int sortOrder;
	
	private Period periodOne;
	
	private Period periodTwo;
	
	private Period periodThree;
	
	private String facilityName;
	
	private String districtName;
	
	private String provinceName;
	
	private String countryName;
	
	private String indicatorName;
	
	private String facilityNameEn;
	
	private String districtNameEn;
	
	private String provinceNameEn;
	
	private String countryNameEn;
	
	private String indicatorNameEn;
	
	private String comboName;
	
	private String facilityType;

	private String facilityTypeEn;
	
	private String monthOne;
	
	private String monthTwo;
	
	private String monthThree;
	
	//Facility actual quantity
	
	private Double monthOneQtyFacility; //de quantity value 
	private Double monthOneQtyFacilityMale; //de quantity value 
	private Double monthOneQtyFacilityFemale; //de quantity value
	
	private Double monthTwoQtyFacility; //de quantity value 
	private Double monthTwoQtyFacilityMale; //de quantity value
	private Double monthTwoQtyFacilityFemale; //de quantity value
	
	private Double monthThreeQtyFacility; //de quantity value 
	private Double monthThreeQtyFacilityMale; //de quantity value
	private Double monthThreeQtyFacilitFemale; //de quantity value
	
	private Double quarterQtyFacility; //de total per quarter quantity value
	private Double quarterQtyFacilityMale; //de total per quarter quantity value
	private Double quarterQtyFacilityFemale; //de total per quarter quantity value

	
	//Internal verification actual quantity
	
	
	private Double monthOneQtyIntVer; //de quantity value 
	private Double monthOneQtyIntVerMale; //de quantity value 
	private Double monthOneQtyIntVerFemale; //de quantity value
	
	private Double monthTwoQtyIntVer; //de quantity value 
	private Double monthTwoQtyIntVerMale; //de quantity value
	private Double monthTwoQtyIntVerFemale; //de quantity value
	
	private Double monthThreeQtyIntVer; //de quantity value 
	private Double monthThreeQtyIntVerMale; //de quantity value
	private Double monthThreeQtyIntVerFemale; //de quantity value
	
	private Double quarterQtyIntVer; //de total per quarter quantity value
	private Double quarterQtyIntVerMale; //de total per quarter quantity value
	private Double quarterQtyIntVerFemale; //de total per quarter quantity value
	
	
	//External verification actual quantity
	
	
	private Double monthOneQtyExtVer; //de quantity value 
	private Double monthOneQtyExtVerMale; //de quantity value 
	private Double monthOneQtyExtVerFemale; //de quantity value
	
	private Double monthTwoQtyExtVer; //de quantity value 
	private Double monthTwoQtyExtVerMale; //de quantity value
	private Double monthTwoQtyExtVerFemale; //de quantity value
	
	private Double monthThreeQtyExtVer; //de quantity value 
	private Double monthThreeQtyExtVerMale; //de quantity value
	private Double monthThreeQtyExtVerFemale; //de quantity value
	
	private Double quarterQtyExtVer; //de total per quarter quantity value
	private Double quarterQtyExtVerMale; //de total per quarter quantity value
	private Double quarterQtyExtVerFemale; //de total per quarter quantity value
	
	
	//Facility quantity with threshold
	
	private Double monthOneQtyFacilityThreshold; //de quantity value with threshold adjustments
	private Double monthOneQtyFacilityThresholdMale; //de quantity value with threshold adjustments
	private Double monthOneQtyFacilityThresholdFemale; //de quantity value with threshold adjustments
	
	private Double monthTwoQtyFacilityThreshold; //de quantity value with threshold adjustments 
	private Double monthTwoQtyFacilityThresholdMale; //de quantity value with threshold adjustments
	private Double monthTwoQtyFacilityThresholdFemale; //de quantity value with threshold adjustments
	
	private Double monthThreeQtyFacilityThreshold; //de quantity value with threshold adjustments
	private Double monthThreeQtyFacilityThresholdMale; //de quantity value with threshold adjustments
	private Double monthThreeQtyFacilityThresholdFemale; //de quantity value with threshold adjustments
	
	private Double quarterQtyFacilityThreshold; //de total per quarter quantity value with threshold adjustments 
	private Double quarterQtyFacilityThresholdMale; //de total per quarter quantity value with threshold adjustments
	private Double quarterQtyFacilityThresholdFemale; //de total per quarter quantity value with threshold adjustments
	
	
	//External verification quantity with threshold
	
	
	private Double monthOneQtyIntVerThreshold; //de quantity value with threshold adjustments
	private Double monthOneQtyIntVerThresholdMale; //de quantity value with threshold adjustments
	private Double monthOneQtyIntVerThresholdFemale; //de quantity value with threshold adjustments
	
	private Double monthTwoQtyIntVerThreshold; //de quantity value with threshold adjustments 
	private Double monthTwoQtyIntVerThresholdMale; //de quantity value with threshold adjustments
	private Double monthTwoQtyIntVerThresholdFemale; //de quantity value with threshold adjustments
	
	private Double monthThreeQtyIntVerThreshold; //de quantity value with threshold adjustments
	private Double monthThreeQtyIntVerThresholdMale; //de quantity value with threshold adjustments
	private Double monthThreeQtyIntVerThresholdFemale; //de quantity value with threshold adjustments
	
	private Double quarterQtyIntVerThreshold; //de total per quarter quantity value with threshold adjustments 
	private Double quarterQtyIntVerThresholdMale; //de total per quarter quantity value with threshold adjustments
	private Double quarterQtyIntVerThresholdFemale; //de total per quarter quantity value with threshold adjustments
	
	
	//External verification quantity with threshold
	
	
	private Double monthOneQtyExtVerThreshold; //de quantity value with threshold adjustments
	private Double monthOneQtyExtVerThresholdMale; //de quantity value with threshold adjustments
	private Double monthOneQtyExtVerThresholdFemale; //de quantity value with threshold adjustments
	
	private Double monthTwoQtyExtVerThreshold; //de quantity value with threshold adjustments 
	private Double monthTwoQtyExtVerThresholdMale; //de quantity value with threshold adjustments
	private Double monthTwoQtyExtVerThresholdFemale; //de quantity value with threshold adjustments
	
	private Double monthThreeQtyExtVerThreshold; //de quantity value with threshold adjustments
	private Double monthThreeQtyExtVerThresholdMale; //de quantity value with threshold adjustments
	private Double monthThreeQtyExtVerThresholdFemale; //de quantity value with threshold adjustments
	
	private Double quarterQtyExtVerThreshold; //de total per quarter quantity value with threshold adjustments 
	private Double quarterQtyExtVerThresholdMale; //de total per quarter quantity value with threshold adjustments
	private Double quarterQtyExtVerThresholdFemale; //de total per quarter quantity value with threshold adjustments
	
	
	
	private Double facilityMonthOneValue; //de quantity value facility
	
	private Double facilityMonthTwoValue; //de quantity value facility 
	
	private Double facilityMonthThreeValue; //de quantity value facility

	private Double facilityQuarterValue; //de total per quarter quantity value facility
	
	
	private Double diffQuarterValue;
	
	private Double thresholdValue;
	
	private Double basisValue;

	private Double qualityScore;
	
	private Double totalQualityScore;
	

	private Double currationAmount; 
	
	
	//demographics data
	
	private Double population;
	private Double childrenUnderOne;
	private Double childrenOneFive;
	private Double pregWoman;
	private Double reprodWoman;
	
	
	private Double unitPrice; //? this should come from formulae FCALC \\15
	
	private Double monthOneAmount; // monthOneValue * unitPrice
	
	private Double monthTwoAmount; // monthTwoValue * unitPrice
	
	private Double monthThreeAmount; // monthThreeValue * unitPrice
	
	private Double quarterAmount; // total(monthOneTwoThreeValue * unitPrice)
	
	private Double totalAmount; //same as above
	
	private Double discountRate; // discount score depending quality indicator \\(GETVALUE(DE,OC,OU,PD)+GETVALUE(DE,OC,OU,PD)+...N+GETVALUE(DE,OC,OU,PD))/NUMBEROFGETVALUES 
	
	private Double discountAmount; 
	
	private Double totalAmountWithDiscount;
	
	private Double auditAmount;
	
	private Double totalAmountWithDiscountPlusAudit;
	
	private Double facilityAmount;
	
	private Double salaryAmount;
	
	private Double taxPercent; // \\GETVALUE(DE.OC,OU,PD)
	
	private Double socialPercent; // \\GETVALUE(DE.OC,OU,PD)
	
	// FORMULAE \\15\\(GETVALUE(DE,OC,OU,PD)+GETVALUE(DE,OC,OU,PD)+...N+GETVALUE(DE,OC,OU,PD))/NUMBEROFGETVALUES\\GETVALUE(DE.OC,OU,PD)\\GETVALUE(DE.OC,OU,PD)
	
	private Double taxAmount;
	
	private Double socialTaxAmount;
	
	private Double netGross;

	private Date created; 
	
    private String storedBy;
	
    private String updatedBy;
    
    private Date lastUpdated;

	public Double getCurrationAmount() {
		return currationAmount;
	}

	public void setCurrationAmount(Double currationAmount) {
		this.currationAmount = currationAmount;
	}

	public String getFacilityNameEn() {
		return facilityNameEn;
	}

	public void setFacilityNameEn(String facilityNameEn) {
		this.facilityNameEn = facilityNameEn;
	}

	public String getDistrictNameEn() {
		return districtNameEn;
	}

	public void setDistrictNameEn(String districtNameEn) {
		this.districtNameEn = districtNameEn;
	}

	public String getProvinceNameEn() {
		return provinceNameEn;
	}

	public void setProvinceNameEn(String provinceNameEn) {
		this.provinceNameEn = provinceNameEn;
	}

	public String getCountryNameEn() {
		return countryNameEn;
	}

	public void setCountryNameEn(String countryNameEn) {
		this.countryNameEn = countryNameEn;
	}

	public String getIndicatorNameEn() {
		return indicatorNameEn;
	}

	public void setIndicatorNameEn(String indicatorNameEn) {
		this.indicatorNameEn = indicatorNameEn;
	}

	public String getFacilityTypeEn() {
		return facilityTypeEn;
	}

	public void setFacilityTypeEn(String facilityTypeEn) {
		this.facilityTypeEn = facilityTypeEn;
	}

	public Double getPopulation() {
		return population;
	}

	public void setPopulation(Double population) {
		this.population = population;
	}

	public Double getChildrenUnderOne() {
		return childrenUnderOne;
	}

	public void setChildrenUnderOne(Double childrenUnderOne) {
		this.childrenUnderOne = childrenUnderOne;
	}

	public Double getChildrenOneFive() {
		return childrenOneFive;
	}

	public void setChildrenOneFive(Double childrenOneFive) {
		this.childrenOneFive = childrenOneFive;
	}

	public Double getPregWoman() {
		return pregWoman;
	}

	public void setPregWoman(Double pregWoman) {
		this.pregWoman = pregWoman;
	}

	public Double getReprodWoman() {
		return reprodWoman;
	}

	public void setReprodWoman(Double reprodWoman) {
		this.reprodWoman = reprodWoman;
	}

	public List<PbfAnalyticsReportDetails> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<PbfAnalyticsReportDetails> indicators) {
		this.indicators = indicators;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public OrganisationUnit getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(OrganisationUnit orgUnit) {
		this.orgUnit = orgUnit;
	}

	public DataElement getDataElement() {
		return dataElement;
	}

	public void setDataElement(DataElement dataElement) {
		this.dataElement = dataElement;
	}

	public CategoryOptionCombo getOptionCombo() {
		return optionCombo;
	}

	public void setOptionCombo(CategoryOptionCombo optionCombo) {
		this.optionCombo = optionCombo;
	}

	public Period getPeriodQuarter() {
		return periodQuarter;
	}

	public void setPeriodQuarter(Period periodQuarter) {
		this.periodQuarter = periodQuarter;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public Period getPeriodOne() {
		return periodOne;
	}

	public void setPeriodOne(Period periodOne) {
		this.periodOne = periodOne;
	}

	public Period getPeriodTwo() {
		return periodTwo;
	}

	public void setPeriodTwo(Period periodTwo) {
		this.periodTwo = periodTwo;
	}

	public Period getPeriodThree() {
		return periodThree;
	}

	public void setPeriodThree(Period periodThree) {
		this.periodThree = periodThree;
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

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getMonthOne() {
		return monthOne;
	}

	public void setMonthOne(String monthOne) {
		this.monthOne = monthOne;
	}

	public String getMonthTwo() {
		return monthTwo;
	}

	public void setMonthTwo(String monthTwo) {
		this.monthTwo = monthTwo;
	}

	public String getMonthThree() {
		return monthThree;
	}

	public void setMonthThree(String monthThree) {
		this.monthThree = monthThree;
	}

	public Double getMonthOneQtyFacility() {
		return monthOneQtyFacility;
	}

	public void setMonthOneQtyFacility(Double monthOneQtyFacility) {
		this.monthOneQtyFacility = monthOneQtyFacility;
	}

	public Double getMonthOneQtyFacilityMale() {
		return monthOneQtyFacilityMale;
	}

	public void setMonthOneQtyFacilityMale(Double monthOneQtyFacilityMale) {
		this.monthOneQtyFacilityMale = monthOneQtyFacilityMale;
	}

	public Double getMonthOneQtyFacilityFemale() {
		return monthOneQtyFacilityFemale;
	}

	public void setMonthOneQtyFacilityFemale(Double monthOneQtyFacilityFemale) {
		this.monthOneQtyFacilityFemale = monthOneQtyFacilityFemale;
	}

	public Double getMonthTwoQtyFacility() {
		return monthTwoQtyFacility;
	}

	public void setMonthTwoQtyFacility(Double monthTwoQtyFacility) {
		this.monthTwoQtyFacility = monthTwoQtyFacility;
	}

	public Double getMonthTwoQtyFacilityMale() {
		return monthTwoQtyFacilityMale;
	}

	public void setMonthTwoQtyFacilityMale(Double monthTwoQtyFacilityMale) {
		this.monthTwoQtyFacilityMale = monthTwoQtyFacilityMale;
	}

	public Double getMonthTwoQtyFacilityFemale() {
		return monthTwoQtyFacilityFemale;
	}

	public void setMonthTwoQtyFacilityFemale(Double monthTwoQtyFacilityFemale) {
		this.monthTwoQtyFacilityFemale = monthTwoQtyFacilityFemale;
	}

	public Double getMonthThreeQtyFacility() {
		return monthThreeQtyFacility;
	}

	public void setMonthThreeQtyFacility(Double monthThreeQtyFacility) {
		this.monthThreeQtyFacility = monthThreeQtyFacility;
	}

	public Double getMonthThreeQtyFacilityMale() {
		return monthThreeQtyFacilityMale;
	}

	public void setMonthThreeQtyFacilityMale(Double monthThreeQtyFacilityMale) {
		this.monthThreeQtyFacilityMale = monthThreeQtyFacilityMale;
	}

	public Double getMonthThreeQtyFacilitFemale() {
		return monthThreeQtyFacilitFemale;
	}

	public void setMonthThreeQtyFacilitFemale(Double monthThreeQtyFacilitFemale) {
		this.monthThreeQtyFacilitFemale = monthThreeQtyFacilitFemale;
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

	public Double getMonthOneQtyIntVer() {
		return monthOneQtyIntVer;
	}

	public void setMonthOneQtyIntVer(Double monthOneQtyIntVer) {
		this.monthOneQtyIntVer = monthOneQtyIntVer;
	}

	public Double getMonthOneQtyIntVerMale() {
		return monthOneQtyIntVerMale;
	}

	public void setMonthOneQtyIntVerMale(Double monthOneQtyIntVerMale) {
		this.monthOneQtyIntVerMale = monthOneQtyIntVerMale;
	}

	public Double getMonthOneQtyIntVerFemale() {
		return monthOneQtyIntVerFemale;
	}

	public void setMonthOneQtyIntVerFemale(Double monthOneQtyIntVerFemale) {
		this.monthOneQtyIntVerFemale = monthOneQtyIntVerFemale;
	}

	public Double getMonthTwoQtyIntVer() {
		return monthTwoQtyIntVer;
	}

	public void setMonthTwoQtyIntVer(Double monthTwoQtyIntVer) {
		this.monthTwoQtyIntVer = monthTwoQtyIntVer;
	}

	public Double getMonthTwoQtyIntVerMale() {
		return monthTwoQtyIntVerMale;
	}

	public void setMonthTwoQtyIntVerMale(Double monthTwoQtyIntVerMale) {
		this.monthTwoQtyIntVerMale = monthTwoQtyIntVerMale;
	}

	public Double getMonthTwoQtyIntVerFemale() {
		return monthTwoQtyIntVerFemale;
	}

	public void setMonthTwoQtyIntVerFemale(Double monthTwoQtyIntVerFemale) {
		this.monthTwoQtyIntVerFemale = monthTwoQtyIntVerFemale;
	}

	public Double getMonthThreeQtyIntVer() {
		return monthThreeQtyIntVer;
	}

	public void setMonthThreeQtyIntVer(Double monthThreeQtyIntVer) {
		this.monthThreeQtyIntVer = monthThreeQtyIntVer;
	}

	public Double getMonthThreeQtyIntVerMale() {
		return monthThreeQtyIntVerMale;
	}

	public void setMonthThreeQtyIntVerMale(Double monthThreeQtyIntVerMale) {
		this.monthThreeQtyIntVerMale = monthThreeQtyIntVerMale;
	}

	public Double getMonthThreeQtyIntVerFemale() {
		return monthThreeQtyIntVerFemale;
	}

	public void setMonthThreeQtyIntVerFemale(Double monthThreeQtyIntVerFemale) {
		this.monthThreeQtyIntVerFemale = monthThreeQtyIntVerFemale;
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

	public Double getMonthOneQtyExtVer() {
		return monthOneQtyExtVer;
	}

	public void setMonthOneQtyExtVer(Double monthOneQtyExtVer) {
		this.monthOneQtyExtVer = monthOneQtyExtVer;
	}

	public Double getMonthOneQtyExtVerMale() {
		return monthOneQtyExtVerMale;
	}

	public void setMonthOneQtyExtVerMale(Double monthOneQtyExtVerMale) {
		this.monthOneQtyExtVerMale = monthOneQtyExtVerMale;
	}

	public Double getMonthOneQtyExtVerFemale() {
		return monthOneQtyExtVerFemale;
	}

	public void setMonthOneQtyExtVerFemale(Double monthOneQtyExtVerFemale) {
		this.monthOneQtyExtVerFemale = monthOneQtyExtVerFemale;
	}

	public Double getMonthTwoQtyExtVer() {
		return monthTwoQtyExtVer;
	}

	public void setMonthTwoQtyExtVer(Double monthTwoQtyExtVer) {
		this.monthTwoQtyExtVer = monthTwoQtyExtVer;
	}

	public Double getMonthTwoQtyExtVerMale() {
		return monthTwoQtyExtVerMale;
	}

	public void setMonthTwoQtyExtVerMale(Double monthTwoQtyExtVerMale) {
		this.monthTwoQtyExtVerMale = monthTwoQtyExtVerMale;
	}

	public Double getMonthTwoQtyExtVerFemale() {
		return monthTwoQtyExtVerFemale;
	}

	public void setMonthTwoQtyExtVerFemale(Double monthTwoQtyExtVerFemale) {
		this.monthTwoQtyExtVerFemale = monthTwoQtyExtVerFemale;
	}

	public Double getMonthThreeQtyExtVer() {
		return monthThreeQtyExtVer;
	}

	public void setMonthThreeQtyExtVer(Double monthThreeQtyExtVer) {
		this.monthThreeQtyExtVer = monthThreeQtyExtVer;
	}

	public Double getMonthThreeQtyExtVerMale() {
		return monthThreeQtyExtVerMale;
	}

	public void setMonthThreeQtyExtVerMale(Double monthThreeQtyExtVerMale) {
		this.monthThreeQtyExtVerMale = monthThreeQtyExtVerMale;
	}

	public Double getMonthThreeQtyExtVerFemale() {
		return monthThreeQtyExtVerFemale;
	}

	public void setMonthThreeQtyExtVerFemale(Double monthThreeQtyExtVerFemale) {
		this.monthThreeQtyExtVerFemale = monthThreeQtyExtVerFemale;
	}

	public Double getQuarterQtyExtVer() {
		return quarterQtyExtVer;
	}

	public void setQuarterQtyExtVer(Double quarterQtyExtVer) {
		this.quarterQtyExtVer = quarterQtyExtVer;
	}

	public Double getQuarterQtyExtVerMale() {
		return quarterQtyExtVerMale;
	}

	public void setQuarterQtyExtVerMale(Double quarterQtyExtVerMale) {
		this.quarterQtyExtVerMale = quarterQtyExtVerMale;
	}

	public Double getQuarterQtyExtVerFemale() {
		return quarterQtyExtVerFemale;
	}

	public void setQuarterQtyExtVerFemale(Double quarterQtyExtVerFemale) {
		this.quarterQtyExtVerFemale = quarterQtyExtVerFemale;
	}

	public Double getMonthOneQtyFacilityThreshold() {
		return monthOneQtyFacilityThreshold;
	}

	public void setMonthOneQtyFacilityThreshold(Double monthOneQtyFacilityThreshold) {
		this.monthOneQtyFacilityThreshold = monthOneQtyFacilityThreshold;
	}

	public Double getMonthOneQtyFacilityThresholdMale() {
		return monthOneQtyFacilityThresholdMale;
	}

	public void setMonthOneQtyFacilityThresholdMale(Double monthOneQtyFacilityThresholdMale) {
		this.monthOneQtyFacilityThresholdMale = monthOneQtyFacilityThresholdMale;
	}

	public Double getMonthOneQtyFacilityThresholdFemale() {
		return monthOneQtyFacilityThresholdFemale;
	}

	public void setMonthOneQtyFacilityThresholdFemale(Double monthOneQtyFacilityThresholdFemale) {
		this.monthOneQtyFacilityThresholdFemale = monthOneQtyFacilityThresholdFemale;
	}

	public Double getMonthTwoQtyFacilityThreshold() {
		return monthTwoQtyFacilityThreshold;
	}

	public void setMonthTwoQtyFacilityThreshold(Double monthTwoQtyFacilityThreshold) {
		this.monthTwoQtyFacilityThreshold = monthTwoQtyFacilityThreshold;
	}

	public Double getMonthTwoQtyFacilityThresholdMale() {
		return monthTwoQtyFacilityThresholdMale;
	}

	public void setMonthTwoQtyFacilityThresholdMale(Double monthTwoQtyFacilityThresholdMale) {
		this.monthTwoQtyFacilityThresholdMale = monthTwoQtyFacilityThresholdMale;
	}

	public Double getMonthTwoQtyFacilityThresholdFemale() {
		return monthTwoQtyFacilityThresholdFemale;
	}

	public void setMonthTwoQtyFacilityThresholdFemale(Double monthTwoQtyFacilityThresholdFemale) {
		this.monthTwoQtyFacilityThresholdFemale = monthTwoQtyFacilityThresholdFemale;
	}

	public Double getMonthThreeQtyFacilityThreshold() {
		return monthThreeQtyFacilityThreshold;
	}

	public void setMonthThreeQtyFacilityThreshold(Double monthThreeQtyFacilityThreshold) {
		this.monthThreeQtyFacilityThreshold = monthThreeQtyFacilityThreshold;
	}

	public Double getMonthThreeQtyFacilityThresholdMale() {
		return monthThreeQtyFacilityThresholdMale;
	}

	public void setMonthThreeQtyFacilityThresholdMale(Double monthThreeQtyFacilityThresholdMale) {
		this.monthThreeQtyFacilityThresholdMale = monthThreeQtyFacilityThresholdMale;
	}

	public Double getMonthThreeQtyFacilityThresholdFemale() {
		return monthThreeQtyFacilityThresholdFemale;
	}

	public void setMonthThreeQtyFacilityThresholdFemale(Double monthThreeQtyFacilityThresholdFemale) {
		this.monthThreeQtyFacilityThresholdFemale = monthThreeQtyFacilityThresholdFemale;
	}

	public Double getQuarterQtyFacilityThreshold() {
		return quarterQtyFacilityThreshold;
	}

	public void setQuarterQtyFacilityThreshold(Double quarterQtyFacilityThreshold) {
		this.quarterQtyFacilityThreshold = quarterQtyFacilityThreshold;
	}

	public Double getQuarterQtyFacilityThresholdMale() {
		return quarterQtyFacilityThresholdMale;
	}

	public void setQuarterQtyFacilityThresholdMale(Double quarterQtyFacilityThresholdMale) {
		this.quarterQtyFacilityThresholdMale = quarterQtyFacilityThresholdMale;
	}

	public Double getQuarterQtyFacilityThresholdFemale() {
		return quarterQtyFacilityThresholdFemale;
	}

	public void setQuarterQtyFacilityThresholdFemale(Double quarterQtyFacilityThresholdFemale) {
		this.quarterQtyFacilityThresholdFemale = quarterQtyFacilityThresholdFemale;
	}

	public Double getMonthOneQtyIntVerThreshold() {
		return monthOneQtyIntVerThreshold;
	}

	public void setMonthOneQtyIntVerThreshold(Double monthOneQtyIntVerThreshold) {
		this.monthOneQtyIntVerThreshold = monthOneQtyIntVerThreshold;
	}

	public Double getMonthOneQtyIntVerThresholdMale() {
		return monthOneQtyIntVerThresholdMale;
	}

	public void setMonthOneQtyIntVerThresholdMale(Double monthOneQtyIntVerThresholdMale) {
		this.monthOneQtyIntVerThresholdMale = monthOneQtyIntVerThresholdMale;
	}

	public Double getMonthOneQtyIntVerThresholdFemale() {
		return monthOneQtyIntVerThresholdFemale;
	}

	public void setMonthOneQtyIntVerThresholdFemale(Double monthOneQtyIntVerThresholdFemale) {
		this.monthOneQtyIntVerThresholdFemale = monthOneQtyIntVerThresholdFemale;
	}

	public Double getMonthTwoQtyIntVerThreshold() {
		return monthTwoQtyIntVerThreshold;
	}

	public void setMonthTwoQtyIntVerThreshold(Double monthTwoQtyIntVerThreshold) {
		this.monthTwoQtyIntVerThreshold = monthTwoQtyIntVerThreshold;
	}

	public Double getMonthTwoQtyIntVerThresholdMale() {
		return monthTwoQtyIntVerThresholdMale;
	}

	public void setMonthTwoQtyIntVerThresholdMale(Double monthTwoQtyIntVerThresholdMale) {
		this.monthTwoQtyIntVerThresholdMale = monthTwoQtyIntVerThresholdMale;
	}

	public Double getMonthTwoQtyIntVerThresholdFemale() {
		return monthTwoQtyIntVerThresholdFemale;
	}

	public void setMonthTwoQtyIntVerThresholdFemale(Double monthTwoQtyIntVerThresholdFemale) {
		this.monthTwoQtyIntVerThresholdFemale = monthTwoQtyIntVerThresholdFemale;
	}

	public Double getMonthThreeQtyIntVerThreshold() {
		return monthThreeQtyIntVerThreshold;
	}

	public void setMonthThreeQtyIntVerThreshold(Double monthThreeQtyIntVerThreshold) {
		this.monthThreeQtyIntVerThreshold = monthThreeQtyIntVerThreshold;
	}

	public Double getMonthThreeQtyIntVerThresholdMale() {
		return monthThreeQtyIntVerThresholdMale;
	}

	public void setMonthThreeQtyIntVerThresholdMale(Double monthThreeQtyIntVerThresholdMale) {
		this.monthThreeQtyIntVerThresholdMale = monthThreeQtyIntVerThresholdMale;
	}

	public Double getMonthThreeQtyIntVerThresholdFemale() {
		return monthThreeQtyIntVerThresholdFemale;
	}

	public void setMonthThreeQtyIntVerThresholdFemale(Double monthThreeQtyIntVerThresholdFemale) {
		this.monthThreeQtyIntVerThresholdFemale = monthThreeQtyIntVerThresholdFemale;
	}

	public Double getQuarterQtyIntVerThreshold() {
		return quarterQtyIntVerThreshold;
	}

	public void setQuarterQtyIntVerThreshold(Double quarterQtyIntVerThreshold) {
		this.quarterQtyIntVerThreshold = quarterQtyIntVerThreshold;
	}

	public Double getQuarterQtyIntVerThresholdMale() {
		return quarterQtyIntVerThresholdMale;
	}

	public void setQuarterQtyIntVerThresholdMale(Double quarterQtyIntVerThresholdMale) {
		this.quarterQtyIntVerThresholdMale = quarterQtyIntVerThresholdMale;
	}

	public Double getQuarterQtyIntVerThresholdFemale() {
		return quarterQtyIntVerThresholdFemale;
	}

	public void setQuarterQtyIntVerThresholdFemale(Double quarterQtyIntVerThresholdFemale) {
		this.quarterQtyIntVerThresholdFemale = quarterQtyIntVerThresholdFemale;
	}

	public Double getMonthOneQtyExtVerThreshold() {
		return monthOneQtyExtVerThreshold;
	}

	public void setMonthOneQtyExtVerThreshold(Double monthOneQtyExtVerThreshold) {
		this.monthOneQtyExtVerThreshold = monthOneQtyExtVerThreshold;
	}

	public Double getMonthOneQtyExtVerThresholdMale() {
		return monthOneQtyExtVerThresholdMale;
	}

	public void setMonthOneQtyExtVerThresholdMale(Double monthOneQtyExtVerThresholdMale) {
		this.monthOneQtyExtVerThresholdMale = monthOneQtyExtVerThresholdMale;
	}

	public Double getMonthOneQtyExtVerThresholdFemale() {
		return monthOneQtyExtVerThresholdFemale;
	}

	public void setMonthOneQtyExtVerThresholdFemale(Double monthOneQtyExtVerThresholdFemale) {
		this.monthOneQtyExtVerThresholdFemale = monthOneQtyExtVerThresholdFemale;
	}

	public Double getMonthTwoQtyExtVerThreshold() {
		return monthTwoQtyExtVerThreshold;
	}

	public void setMonthTwoQtyExtVerThreshold(Double monthTwoQtyExtVerThreshold) {
		this.monthTwoQtyExtVerThreshold = monthTwoQtyExtVerThreshold;
	}

	public Double getMonthTwoQtyExtVerThresholdMale() {
		return monthTwoQtyExtVerThresholdMale;
	}

	public void setMonthTwoQtyExtVerThresholdMale(Double monthTwoQtyExtVerThresholdMale) {
		this.monthTwoQtyExtVerThresholdMale = monthTwoQtyExtVerThresholdMale;
	}

	public Double getMonthTwoQtyExtVerThresholdFemale() {
		return monthTwoQtyExtVerThresholdFemale;
	}

	public void setMonthTwoQtyExtVerThresholdFemale(Double monthTwoQtyExtVerThresholdFemale) {
		this.monthTwoQtyExtVerThresholdFemale = monthTwoQtyExtVerThresholdFemale;
	}

	public Double getMonthThreeQtyExtVerThreshold() {
		return monthThreeQtyExtVerThreshold;
	}

	public void setMonthThreeQtyExtVerThreshold(Double monthThreeQtyExtVerThreshold) {
		this.monthThreeQtyExtVerThreshold = monthThreeQtyExtVerThreshold;
	}

	public Double getMonthThreeQtyExtVerThresholdMale() {
		return monthThreeQtyExtVerThresholdMale;
	}

	public void setMonthThreeQtyExtVerThresholdMale(Double monthThreeQtyExtVerThresholdMale) {
		this.monthThreeQtyExtVerThresholdMale = monthThreeQtyExtVerThresholdMale;
	}

	public Double getMonthThreeQtyExtVerThresholdFemale() {
		return monthThreeQtyExtVerThresholdFemale;
	}

	public void setMonthThreeQtyExtVerThresholdFemale(Double monthThreeQtyExtVerThresholdFemale) {
		this.monthThreeQtyExtVerThresholdFemale = monthThreeQtyExtVerThresholdFemale;
	}

	public Double getQuarterQtyExtVerThreshold() {
		return quarterQtyExtVerThreshold;
	}

	public void setQuarterQtyExtVerThreshold(Double quarterQtyExtVerThreshold) {
		this.quarterQtyExtVerThreshold = quarterQtyExtVerThreshold;
	}

	public Double getQuarterQtyExtVerThresholdMale() {
		return quarterQtyExtVerThresholdMale;
	}

	public void setQuarterQtyExtVerThresholdMale(Double quarterQtyExtVerThresholdMale) {
		this.quarterQtyExtVerThresholdMale = quarterQtyExtVerThresholdMale;
	}

	public Double getQuarterQtyExtVerThresholdFemale() {
		return quarterQtyExtVerThresholdFemale;
	}

	public void setQuarterQtyExtVerThresholdFemale(Double quarterQtyExtVerThresholdFemale) {
		this.quarterQtyExtVerThresholdFemale = quarterQtyExtVerThresholdFemale;
	}

	public Double getFacilityMonthOneValue() {
		return facilityMonthOneValue;
	}

	public void setFacilityMonthOneValue(Double facilityMonthOneValue) {
		this.facilityMonthOneValue = facilityMonthOneValue;
	}

	public Double getFacilityMonthTwoValue() {
		return facilityMonthTwoValue;
	}

	public void setFacilityMonthTwoValue(Double facilityMonthTwoValue) {
		this.facilityMonthTwoValue = facilityMonthTwoValue;
	}

	public Double getFacilityMonthThreeValue() {
		return facilityMonthThreeValue;
	}

	public void setFacilityMonthThreeValue(Double facilityMonthThreeValue) {
		this.facilityMonthThreeValue = facilityMonthThreeValue;
	}

	public Double getFacilityQuarterValue() {
		return facilityQuarterValue;
	}

	public void setFacilityQuarterValue(Double facilityQuarterValue) {
		this.facilityQuarterValue = facilityQuarterValue;
	}

	public Double getDiffQuarterValue() {
		return diffQuarterValue;
	}

	public void setDiffQuarterValue(Double diffQuarterValue) {
		this.diffQuarterValue = diffQuarterValue;
	}

	public Double getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(Double thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	public Double getBasisValue() {
		return basisValue;
	}

	public void setBasisValue(Double basisValue) {
		this.basisValue = basisValue;
	}

	public Double getQualityScore() {
		return qualityScore;
	}

	public void setQualityScore(Double qualityScore) {
		this.qualityScore = qualityScore;
	}

	public Double getTotalQualityScore() {
		return totalQualityScore;
	}

	public void setTotalQualityScore(Double totalQualityScore) {
		this.totalQualityScore = totalQualityScore;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getMonthOneAmount() {
		return monthOneAmount;
	}

	public void setMonthOneAmount(Double monthOneAmount) {
		this.monthOneAmount = monthOneAmount;
	}

	public Double getMonthTwoAmount() {
		return monthTwoAmount;
	}

	public void setMonthTwoAmount(Double monthTwoAmount) {
		this.monthTwoAmount = monthTwoAmount;
	}

	public Double getMonthThreeAmount() {
		return monthThreeAmount;
	}

	public void setMonthThreeAmount(Double monthThreeAmount) {
		this.monthThreeAmount = monthThreeAmount;
	}

	public Double getQuarterAmount() {
		return quarterAmount;
	}

	public void setQuarterAmount(Double quarterAmount) {
		this.quarterAmount = quarterAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getTotalAmountWithDiscount() {
		return totalAmountWithDiscount;
	}

	public void setTotalAmountWithDiscount(Double totalAmountWithDiscount) {
		this.totalAmountWithDiscount = totalAmountWithDiscount;
	}

	public Double getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(Double auditAmount) {
		this.auditAmount = auditAmount;
	}

	public Double getTotalAmountWithDiscountPlusAudit() {
		return totalAmountWithDiscountPlusAudit;
	}

	public void setTotalAmountWithDiscountPlusAudit(Double totalAmountWithDiscountPlusAudit) {
		this.totalAmountWithDiscountPlusAudit = totalAmountWithDiscountPlusAudit;
	}

	public Double getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(Double facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public Double getSalaryAmount() {
		return salaryAmount;
	}

	public void setSalaryAmount(Double salaryAmount) {
		this.salaryAmount = salaryAmount;
	}

	public Double getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(Double taxPercent) {
		this.taxPercent = taxPercent;
	}

	public Double getSocialPercent() {
		return socialPercent;
	}

	public void setSocialPercent(Double socialPercent) {
		this.socialPercent = socialPercent;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getSocialTaxAmount() {
		return socialTaxAmount;
	}

	public void setSocialTaxAmount(Double socialTaxAmount) {
		this.socialTaxAmount = socialTaxAmount;
	}

	public Double getNetGross() {
		return netGross;
	}

	public void setNetGross(Double netGross) {
		this.netGross = netGross;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getStoredBy() {
		return storedBy;
	}

	public void setStoredBy(String storedBy) {
		this.storedBy = storedBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((facilityMonthOneValue == null) ? 0 : facilityMonthOneValue.hashCode());
		result = prime * result + ((facilityMonthThreeValue == null) ? 0 : facilityMonthThreeValue.hashCode());
		result = prime * result + ((facilityMonthTwoValue == null) ? 0 : facilityMonthTwoValue.hashCode());
		result = prime * result + ((monthOneQtyFacility == null) ? 0 : monthOneQtyFacility.hashCode());
		result = prime * result + ((monthOneQtyFacilityFemale == null) ? 0 : monthOneQtyFacilityFemale.hashCode());
		result = prime * result + ((monthOneQtyFacilityMale == null) ? 0 : monthOneQtyFacilityMale.hashCode());
		result = prime * result
				+ ((monthOneQtyFacilityThreshold == null) ? 0 : monthOneQtyFacilityThreshold.hashCode());
		result = prime * result
				+ ((monthOneQtyFacilityThresholdFemale == null) ? 0 : monthOneQtyFacilityThresholdFemale.hashCode());
		result = prime * result
				+ ((monthOneQtyFacilityThresholdMale == null) ? 0 : monthOneQtyFacilityThresholdMale.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PbfAnalyticsReportDetails other = (PbfAnalyticsReportDetails) obj;
		if (facilityMonthOneValue == null) {
			if (other.facilityMonthOneValue != null)
				return false;
		} else if (!facilityMonthOneValue.equals(other.facilityMonthOneValue))
			return false;
		if (facilityMonthThreeValue == null) {
			if (other.facilityMonthThreeValue != null)
				return false;
		} else if (!facilityMonthThreeValue.equals(other.facilityMonthThreeValue))
			return false;
		if (facilityMonthTwoValue == null) {
			if (other.facilityMonthTwoValue != null)
				return false;
		} else if (!facilityMonthTwoValue.equals(other.facilityMonthTwoValue))
			return false;
		if (monthOneQtyFacility == null) {
			if (other.monthOneQtyFacility != null)
				return false;
		} else if (!monthOneQtyFacility.equals(other.monthOneQtyFacility))
			return false;
		if (monthOneQtyFacilityFemale == null) {
			if (other.monthOneQtyFacilityFemale != null)
				return false;
		} else if (!monthOneQtyFacilityFemale.equals(other.monthOneQtyFacilityFemale))
			return false;
		if (monthOneQtyFacilityMale == null) {
			if (other.monthOneQtyFacilityMale != null)
				return false;
		} else if (!monthOneQtyFacilityMale.equals(other.monthOneQtyFacilityMale))
			return false;
		if (monthOneQtyFacilityThreshold == null) {
			if (other.monthOneQtyFacilityThreshold != null)
				return false;
		} else if (!monthOneQtyFacilityThreshold.equals(other.monthOneQtyFacilityThreshold))
			return false;
		if (monthOneQtyFacilityThresholdFemale == null) {
			if (other.monthOneQtyFacilityThresholdFemale != null)
				return false;
		} else if (!monthOneQtyFacilityThresholdFemale.equals(other.monthOneQtyFacilityThresholdFemale))
			return false;
		if (monthOneQtyFacilityThresholdMale == null) {
			if (other.monthOneQtyFacilityThresholdMale != null)
				return false;
		} else if (!monthOneQtyFacilityThresholdMale.equals(other.monthOneQtyFacilityThresholdMale))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PbfAnalyticsReportDetails [facilityName=" + facilityName + ", districtName=" + districtName
				+ ", provinceName=" + provinceName + ", countryName=" + countryName + ", indicatorName="
				+ indicatorName + ", comboName=" + comboName + "]";
	}
   
}
