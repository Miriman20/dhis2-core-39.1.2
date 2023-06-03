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
import java.util.List;

import org.hisp.dhis.common.BaseIdentifiableObject;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.dataset.Section;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.period.Period;

/**
 * @author Latifov Murodillo Abdusamadovich
 * @version $Id:
 */
public class PbfAnalyticsQualityDetails extends BaseIdentifiableObject implements Serializable{

	/**
	 * Primary keys
	 */
	private static final long serialVersionUID = 1L;

	private OrganisationUnit orgUnit;
	
	private DataElement dataElement;
	
	private CategoryOptionCombo optionCombo;
	
	private Period periodQuarter;
	
	private DataSet dataSetMax;
	
	private Section sectionMax;
	
	private List<PbfAnalyticsQualityDetails> indicators = new ArrayList<PbfAnalyticsQualityDetails>();

	/**
	 * Other properties
	 */

	private int sectionSortOrder;
	
	private int sortOrder;
	
	private String indicatorName;

	private String sectionName;

	private String subSectionName;
	
	private String facilityName;
	
	private String districtName;
	
	private String provinceName;
	
	private String countryName;
	
	private String indicatorNameEn;

	private String sectionNameEn;

	private String subSectionNameEn;
	
	private String facilityNameEn;
	
	private String districtNameEn;
	
	private String provinceNameEn;
	
	private String countryNameEn;
	
	private String facilityType;
	
	private String periodName;
	
	private String indicatorValueInt;
	
	private String indicatorValueExt;
	
	//Max score value
	private Double quarterQtyMax = 0d; 
	
	//Internal verification actual quantity
	private Double quarterQtyIntVer = 0d; 
	
	//External verification actual quantity
	private Double quarterQtyExtVer = 0d; 

	public String getIndicatorValueExt() {
		return indicatorValueExt;
	}

	public void setIndicatorValueExt(String indicatorValueExt) {
		this.indicatorValueExt = indicatorValueExt;
	}
	
	public String getIndicatorValueInt() {
		return indicatorValueInt;
	}

	public void setIndicatorValueInt(String indicatorValueInt) {
		this.indicatorValueInt = indicatorValueInt;
	}

	public String getSubSectionName() {
		return subSectionName;
	}

	public void setSubSectionName(String subSectionName) {
		this.subSectionName = subSectionName;
	}

	public String getSubSectionNameEn() {
		return subSectionNameEn;
	}

	public void setSubSectionNameEn(String subSectionNameEn) {
		this.subSectionNameEn = subSectionNameEn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIndicatorNameEn() {
		return indicatorNameEn;
	}

	public void setIndicatorNameEn(String indicatorNameEn) {
		this.indicatorNameEn = indicatorNameEn;
	}

	public String getSectionNameEn() {
		return sectionNameEn;
	}

	public void setSectionNameEn(String sectionNameEn) {
		this.sectionNameEn = sectionNameEn;
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

	public List<PbfAnalyticsQualityDetails> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<PbfAnalyticsQualityDetails> indicators) {
		this.indicators = indicators;
	}

	public int getSectionSortOrder() {
		return sectionSortOrder;
	}

	public void setSectionSortOrder(int sectionSortOrder) {
		this.sectionSortOrder = sectionSortOrder;
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

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public Period getPeriodQuarter() {
		return periodQuarter;
	}

	public void setPeriodQuarter(Period periodQuarter) {
		this.periodQuarter = periodQuarter;
	}

	public DataSet getDataSetMax() {
		return dataSetMax;
	}

	public void setDataSetMax(DataSet dataSetMax) {
		this.dataSetMax = dataSetMax;
	}

	public Section getSectionMax() {
		return sectionMax;
	}

	public void setSectionMax(Section sectionMax) {
		this.sectionMax = sectionMax;
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

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public Double getQuarterQtyMax() {
		return quarterQtyMax;
	}

	public void setQuarterQtyMax(Double quarterQtyMax) {
		this.quarterQtyMax = quarterQtyMax;
	}

	public Double getQuarterQtyIntVer() {
		return quarterQtyIntVer;
	}

	public void setQuarterQtyIntVer(Double quarterQtyIntVer) {
		this.quarterQtyIntVer = quarterQtyIntVer;
	}

	public Double getQuarterQtyExtVer() {
		return quarterQtyExtVer;
	}

	public void setQuarterQtyExtVer(Double quarterQtyExtVer) {
		this.quarterQtyExtVer = quarterQtyExtVer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((dataElement == null) ? 0 : dataElement.hashCode());
		result = prime * result + ((dataSetMax == null) ? 0 : dataSetMax.hashCode());
		result = prime * result + ((districtName == null) ? 0 : districtName.hashCode());
		result = prime * result + ((facilityName == null) ? 0 : facilityName.hashCode());
		result = prime * result + ((facilityType == null) ? 0 : facilityType.hashCode());
		result = prime * result + ((indicatorName == null) ? 0 : indicatorName.hashCode());
		result = prime * result + ((optionCombo == null) ? 0 : optionCombo.hashCode());
		result = prime * result + ((orgUnit == null) ? 0 : orgUnit.hashCode());
		result = prime * result + ((periodQuarter == null) ? 0 : periodQuarter.hashCode());
		result = prime * result + ((provinceName == null) ? 0 : provinceName.hashCode());
		result = prime * result + ((quarterQtyExtVer == null) ? 0 : quarterQtyExtVer.hashCode());
		result = prime * result + ((quarterQtyIntVer == null) ? 0 : quarterQtyIntVer.hashCode());
		result = prime * result + ((quarterQtyMax == null) ? 0 : quarterQtyMax.hashCode());
		result = prime * result + ((sectionMax == null) ? 0 : sectionMax.hashCode());
		result = prime * result + ((sectionName == null) ? 0 : sectionName.hashCode());
		result = prime * result + sortOrder;
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
		PbfAnalyticsQualityDetails other = (PbfAnalyticsQualityDetails) obj;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (dataElement == null) {
			if (other.dataElement != null)
				return false;
		} else if (!dataElement.equals(other.dataElement))
			return false;
		if (dataSetMax == null) {
			if (other.dataSetMax != null)
				return false;
		} else if (!dataSetMax.equals(other.dataSetMax))
			return false;
		if (districtName == null) {
			if (other.districtName != null)
				return false;
		} else if (!districtName.equals(other.districtName))
			return false;
		if (facilityName == null) {
			if (other.facilityName != null)
				return false;
		} else if (!facilityName.equals(other.facilityName))
			return false;
		if (facilityType == null) {
			if (other.facilityType != null)
				return false;
		} else if (!facilityType.equals(other.facilityType))
			return false;
		if (indicatorName == null) {
			if (other.indicatorName != null)
				return false;
		} else if (!indicatorName.equals(other.indicatorName))
			return false;
		if (optionCombo == null) {
			if (other.optionCombo != null)
				return false;
		} else if (!optionCombo.equals(other.optionCombo))
			return false;
		if (orgUnit == null) {
			if (other.orgUnit != null)
				return false;
		} else if (!orgUnit.equals(other.orgUnit))
			return false;
		if (periodQuarter == null) {
			if (other.periodQuarter != null)
				return false;
		} else if (!periodQuarter.equals(other.periodQuarter))
			return false;
		if (provinceName == null) {
			if (other.provinceName != null)
				return false;
		} else if (!provinceName.equals(other.provinceName))
			return false;
		if (quarterQtyExtVer == null) {
			if (other.quarterQtyExtVer != null)
				return false;
		} else if (!quarterQtyExtVer.equals(other.quarterQtyExtVer))
			return false;
		if (quarterQtyIntVer == null) {
			if (other.quarterQtyIntVer != null)
				return false;
		} else if (!quarterQtyIntVer.equals(other.quarterQtyIntVer))
			return false;
		if (quarterQtyMax == null) {
			if (other.quarterQtyMax != null)
				return false;
		} else if (!quarterQtyMax.equals(other.quarterQtyMax))
			return false;
		if (sectionMax == null) {
			if (other.sectionMax != null)
				return false;
		} else if (!sectionMax.equals(other.sectionMax))
			return false;
		if (sectionName == null) {
			if (other.sectionName != null)
				return false;
		} else if (!sectionName.equals(other.sectionName))
			return false;
		if (sortOrder != other.sortOrder)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PbfAnalyticsQualityDetails [indicatorName=" + indicatorName + ", sectionName=" + sectionName
				+ ", facilityName=" + facilityName + ", districtName=" + districtName + ", provinceName="
				+ provinceName + ", countryName=" + countryName + ", facilityType=" + facilityType + "]";
	}

   
}
