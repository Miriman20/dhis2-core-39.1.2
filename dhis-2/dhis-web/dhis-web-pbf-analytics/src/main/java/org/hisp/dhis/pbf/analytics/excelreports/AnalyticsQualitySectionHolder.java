package org.hisp.dhis.pbf.analytics.excelreports;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsQualitySectionHolder {

	private int facilityId;
	
	private String facilityName;
	
	private String districtName;
	
	private String provinceName;
	
	private String countryName;
	
	private String facilityNameEn;
	
	private String districtNameEn;
	
	private String provinceNameEn;
	
	private String countryNameEn;
	
	private String periodQuarter;

	private String type;

	private List<AnalyticsQualitySectionHolder> sections = new ArrayList<AnalyticsQualitySectionHolder>();

	public AnalyticsQualitySectionHolder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnalyticsQualitySectionHolder(int facilityId, String facilityName, String districtName, String provinceName,
			String countryName, String periodQuarter, String type, List<AnalyticsQualitySectionHolder> sections) {
		super();
		this.facilityId = facilityId;
		this.facilityName = facilityName;
		this.districtName = districtName;
		this.provinceName = provinceName;
		this.countryName = countryName;
		this.periodQuarter = periodQuarter;
		this.type = type;
		this.sections = sections;
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

	public List<AnalyticsQualitySectionHolder> getSections() {
		return sections;
	}

	public void setSections(List<AnalyticsQualitySectionHolder> sections) {
		this.sections = sections;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
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

	public List<AnalyticsQualitySectionHolder> getIndicators() {
		return sections;
	}

	public void setIndicators(List<AnalyticsQualitySectionHolder> sections) {
		this.sections = sections;
	}


}
