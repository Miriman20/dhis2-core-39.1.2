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
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.period.Period;

/**
 * @author Latifov Murodillo Abdusamadovich
 * @version $Id:
 */
public class PbfCalculation extends BaseIdentifiableObject implements Serializable{

	/**
	 * Primary keys
	 */
	private static final long serialVersionUID = 1L;

	private OrganisationUnit orgUnit;
	
	private DataElement dataElement;
	
	//private DataElementCategoryOptionCombo optionCombo;
	
	private Period periodQuarter;

	private List<PbfCalculation> indicators = new ArrayList<PbfCalculation>();
	
	
	public List<PbfCalculation> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<PbfCalculation> indicators) {
		this.indicators = indicators;
	}


	/**
	 * Other properties
	 */
	
	private Integer sortOrder;
	
	private String indicatorName;
	
	private String facilityName;
	
	private String districtName;
	
	private String provinceName;
	
	private String countryName;
	
	private String indicatorNameEn;
	
	private String facilityNameEn;
	
	private String districtNameEn;
	
	private String provinceNameEn;
	
	private String countryNameEn;
	
	private String monthOne;
	
	private String monthTwo;
	
	private String monthThree;
	
	private Period periodOne;
	
	private Period periodTwo;
	
	private Period periodThree;
	
	private Double monthOneValueOrig; //de quantity value 
	
	private Double monthTwoValueOrig; //de quantity value 
	
	private Double monthThreeValueOrig; //de quantity value 
	
	private Double quarterValueOrig; //de total per quarter quantity value
	
	
	private Double monthOneValue; //de quantity value with threshold adjustments
	
	private Double monthTwoValue; //de quantity value with threshold adjustments 
	
	private Double monthThreeValue; //de quantity value with threshold adjustments

	private Double quarterValue; //de total per quarter quantity value with threshold adjustments 
	
	
	
	private Double facilityMonthOneValue; //de quantity value facility
	
	private Double facilityMonthTwoValue; //de quantity value facility 
	
	private Double facilityMonthThreeValue; //de quantity value facility

	private Double facilityQuarterValue; //de total per quarter quantity value facility
	
	
	private Double diffQuarterValue;
	
	private Double thresholdValue;
	
	private Double basisValue;

	private Double qualityScore;
	
	private Double totalQualityScore;
	
	private Double totalQualityScorePrevQuar;
	
	private Double unitPrice; //? this should come from formulae FCALC \\15
	
	private Double monthOneAmount; // monthOneValue * unitPrice
	
	private Double monthTwoAmount; // monthTwoValue * unitPrice
	
	private Double monthThreeAmount; // monthThreeValue * unitPrice
	
	private Double quarterAmount; // total(monthOneTwoThreeValue * unitPrice)
	
	private Double totalAmount; //same as above
	
	private Double discountRate; // discount score depending quality indicator \\(GETVALUE(DE,OC,OU,PD)+GETVALUE(DE,OC,OU,PD)+...N+GETVALUE(DE,OC,OU,PD))/NUMBEROFGETVALUES 
	
	private Double discountAmount; 

	private Double currationAmount;
	
	private int currationPerformed; 
	
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

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public int getCurrationPerformed() {
		return currationPerformed;
	}

	public void setCurrationPerformed(int currationPerformed) {
		this.currationPerformed = currationPerformed;
	}

	public Double getTotalQualityScorePrevQuar() {
		return totalQualityScorePrevQuar;
	}

	public void setTotalQualityScorePrevQuar(Double totalQualityScorePrevQuar) {
		this.totalQualityScorePrevQuar = totalQualityScorePrevQuar;
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

	public String getIndicatorNameEn() {
		return indicatorNameEn;
	}

	public void setIndicatorNameEn(String indicatorNameEn) {
		this.indicatorNameEn = indicatorNameEn;
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

	public Double getCurrationAmount() {
		return currationAmount;
	}

	public void setCurrationAmount(Double currationAmount) {
		this.currationAmount = currationAmount;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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

	public Double getDiffQuarterValue() {
		return diffQuarterValue;
	}

	public void setDiffQuarterValue(Double diffQuarterValue) {
		this.diffQuarterValue = diffQuarterValue;
	}

	public Double getBasisValue() {
		return basisValue;
	}

	public void setBasisValue(Double basisValue) {
		this.basisValue = basisValue;
	}

	public Double getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(Double thresholdValue) {
		this.thresholdValue = thresholdValue;
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

	public Double getQuarterValueOrig() {
		return quarterValueOrig;
	}

	public void setQuarterValueOrig(Double quarterValueOrig) {
		this.quarterValueOrig = quarterValueOrig;
	}

	public Double getMonthOneValueOrig() {
		return monthOneValueOrig;
	}

	public void setMonthOneValueOrig(Double monthOneValueOrig) {
		this.monthOneValueOrig = monthOneValueOrig;
	}

	public Double getMonthTwoValueOrig() {
		return monthTwoValueOrig;
	}

	public void setMonthTwoValueOrig(Double monthTwoValueOrig) {
		this.monthTwoValueOrig = monthTwoValueOrig;
	}

	public Double getMonthThreeValueOrig() {
		return monthThreeValueOrig;
	}

	public void setMonthThreeValueOrig(Double monthThreeValueOrig) {
		this.monthThreeValueOrig = monthThreeValueOrig;
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

	public Double getFacilityQuarterValue() {
		return facilityQuarterValue;
	}

	public void setFacilityQuarterValue(Double facilityQuarterValue) {
		this.facilityQuarterValue = facilityQuarterValue;
	}

	public void setSalaryAmount(Double salaryAmount) {
		this.salaryAmount = salaryAmount;
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

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
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

	public DataElement getDataElement() {
		return dataElement;
	}

	public void setDataElement(DataElement dataElement) {
		this.dataElement = dataElement;
	}

/*	public DataElementCategoryOptionCombo getOptionCombo() {
		return optionCombo;
	}

	public void setOptionCombo(DataElementCategoryOptionCombo optionCombo) {
		this.optionCombo = optionCombo;
	}*/

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
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

	public Period getPeriodQuarter() {
		return periodQuarter;
	}

	public void setPeriodQuarter(Period periodQuarter) {
		this.periodQuarter = periodQuarter;
	}

	public Double getMonthOneValue() {
		return monthOneValue;
	}

	public void setMonthOneValue(Double monthOneValue) {
		this.monthOneValue = monthOneValue;
	}

	public Double getMonthTwoValue() {
		return monthTwoValue;
	}

	public void setMonthTwoValue(Double monthTwoValue) {
		this.monthTwoValue = monthTwoValue;
	}

	public Double getMonthThreeValue() {
		return monthThreeValue;
	}

	public void setMonthThreeValue(Double monthThreeValue) {
		this.monthThreeValue = monthThreeValue;
	}

	
	private String stringQuarterValue;

	public String getStringQuarterValue() {
		return String.format("%.2f", quarterValue);
	}

	public void setStringQuarterValue(String stringQuarterValue) {
		this.stringQuarterValue = stringQuarterValue;
	}

	private String stringQuarterAmount;
	
	private String stringDiffQuarterValue;
	
	public String getStringQuarterAmount() {
		return String.format("%.2f", quarterAmount);
	}

	public void setStringQuarterAmount(String stringQuarterAmount) {
		this.stringQuarterAmount = stringQuarterAmount;
	}

	public String getStringDiffQuarterValue() {
		return String.format("%.2f", diffQuarterValue);
	}

	public void setStringDiffQuarterValue(String stringDiffQuarterValue) {
		this.stringDiffQuarterValue = stringDiffQuarterValue;
	}

	public Double getQuarterValue() {
		return quarterValue;
	}

	public void setQuarterValue(Double quarterValue) {
		this.quarterValue = quarterValue;
	}

	public OrganisationUnit getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(OrganisationUnit orgUnit) {
		this.orgUnit = orgUnit;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((dataElement == null) ? 0 : dataElement.hashCode());
		result = prime * result
				+ ((indicatorName == null) ? 0 : indicatorName.hashCode());
		result = prime * result
				+ ((monthOne == null) ? 0 : monthOne.hashCode());
		result = prime * result
				+ ((monthOneValue == null) ? 0 : monthOneValue.hashCode());
		result = prime * result
				+ ((monthThree == null) ? 0 : monthThree.hashCode());
		result = prime * result
				+ ((monthThreeValue == null) ? 0 : monthThreeValue.hashCode());
		result = prime * result
				+ ((monthTwo == null) ? 0 : monthTwo.hashCode());
		result = prime * result
				+ ((monthTwoValue == null) ? 0 : monthTwoValue.hashCode());

		result = prime * result
				+ ((periodOne == null) ? 0 : periodOne.hashCode());
		result = prime * result
				+ ((periodQuarter == null) ? 0 : periodQuarter.hashCode());
		result = prime * result
				+ ((periodThree == null) ? 0 : periodThree.hashCode());
		result = prime * result
				+ ((periodTwo == null) ? 0 : periodTwo.hashCode());
		result = prime * result
				+ ((quarterValue == null) ? 0 : quarterValue.hashCode());
		
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
		PbfCalculation other = (PbfCalculation) obj;
		if (dataElement == null) {
			if (other.dataElement != null)
				return false;
		} else if (!dataElement.equals(other.dataElement))
			return false;
		if (indicatorName == null) {
			if (other.indicatorName != null)
				return false;
		} else if (!indicatorName.equals(other.indicatorName))
			return false;
		if (monthOne == null) {
			if (other.monthOne != null)
				return false;
		} else if (!monthOne.equals(other.monthOne))
			return false;
		if (monthOneValue == null) {
			if (other.monthOneValue != null)
				return false;
		} else if (!monthOneValue.equals(other.monthOneValue))
			return false;
		if (monthThree == null) {
			if (other.monthThree != null)
				return false;
		} else if (!monthThree.equals(other.monthThree))
			return false;
		if (monthThreeValue == null) {
			if (other.monthThreeValue != null)
				return false;
		} else if (!monthThreeValue.equals(other.monthThreeValue))
			return false;
		if (monthTwo == null) {
			if (other.monthTwo != null)
				return false;
		} else if (!monthTwo.equals(other.monthTwo))
			return false;
		if (monthTwoValue == null) {
			if (other.monthTwoValue != null)
				return false;
		} else if (!monthTwoValue.equals(other.monthTwoValue))
			return false;

		
		if (periodOne == null) {
			if (other.periodOne != null)
				return false;
		} else if (!periodOne.equals(other.periodOne))
			return false;
		if (periodQuarter == null) {
			if (other.periodQuarter != null)
				return false;
		} else if (!periodQuarter.equals(other.periodQuarter))
			return false;
		if (periodThree == null) {
			if (other.periodThree != null)
				return false;
		} else if (!periodThree.equals(other.periodThree))
			return false;
		if (periodTwo == null) {
			if (other.periodTwo != null)
				return false;
		} else if (!periodTwo.equals(other.periodTwo))
			return false;
		if (quarterValue == null) {
			if (other.quarterValue != null)
				return false;
		} else if (!quarterValue.equals(other.quarterValue))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "PbfCalculation [dataElement=" + dataElement
				+ ", optionCombo=" + ", indicatorName="
				+ indicatorName + ", monthOne=" + monthOne + ", monthTwo="
				+ monthTwo + ", monthThree=" + monthThree + ", periodOne="
				+ periodOne + ", periodTwo=" + periodTwo + ", periodThree="
				+ periodThree + ", periodQuarter=" + periodQuarter
				+ ", monthOneValue=" + monthOneValue + ", monthTwoValue="
				+ monthTwoValue + ", monthThreeValue=" + monthThreeValue
				+ ", quarterValue=" + quarterValue + "]";
	}

}
