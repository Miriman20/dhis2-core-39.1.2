package org.hisp.dhis.pbf.calcs.extver;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.pbf.model.PbfAnalyticsReportDetails;

public class AnalyticsHolder {

	private int facilityId;
	
	private String facilityName;
	
	private String districtName;
	
	private String provinceName;
	
	private String countryName;
	
	private String periodQuarter;

	private String type;

	private List<PbfAnalyticsReportDetails> indicators = new ArrayList<PbfAnalyticsReportDetails>();

	public AnalyticsHolder(int facilityId, String facilityName, String districtName, String provinceName,
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

	public List<PbfAnalyticsReportDetails> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<PbfAnalyticsReportDetails> indicators) {
		this.indicators = indicators;
	}


}
