package org.hisp.dhis.pbf.calcs.extver;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.pbf.model.PbfAnalyticsQualityDetails;

public class AnalyticsQualityIndicatorHolder {

	private int sectionId;
	
	private int sortOrder;
	
	private String sectionName;
	
	private int sectionSize;
	
	private int indicatorsSize;
	
	private List<AnalyticsQualityIndicatorHolder> sections = new ArrayList<AnalyticsQualityIndicatorHolder>();

	private List<PbfAnalyticsQualityDetails> indicators = new ArrayList<PbfAnalyticsQualityDetails>();
	
	public AnalyticsQualityIndicatorHolder(int sectionId, String sectionName, int sortOrder) {
		super();
		this.sectionId = sectionId;
		this.sectionName = sectionName;
		this.sortOrder = sortOrder;
	}

	public int getSectionSize() {
		return sectionSize;
	}

	public void setSectionSize(int sectionSize) {
		this.sectionSize = sectionSize;
	}

	public int getIndicatorsSize() {
		return indicatorsSize;
	}

	public void setIndicatorsSize(int indicatorsSize) {
		this.indicatorsSize = indicatorsSize;
	}

	public List<AnalyticsQualityIndicatorHolder> getSections() {
		return sections;
	}

	public void setSections(List<AnalyticsQualityIndicatorHolder> sections) {
		this.sections = sections;
	}

	public AnalyticsQualityIndicatorHolder() {
		// TODO Auto-generated constructor stub
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List<PbfAnalyticsQualityDetails> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<PbfAnalyticsQualityDetails> indicators) {
		this.indicators = indicators;
	}

}
