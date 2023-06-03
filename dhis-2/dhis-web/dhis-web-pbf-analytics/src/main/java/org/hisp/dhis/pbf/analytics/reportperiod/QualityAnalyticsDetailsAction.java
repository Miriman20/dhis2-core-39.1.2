package org.hisp.dhis.pbf.analytics.reportperiod;

/*
 * Copyright (c) 2004-2012, University of Oslo
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.dataset.DataSetService;
import org.hisp.dhis.dataset.Section;
import org.hisp.dhis.dataset.comparator.SectionOrderComparator;
import org.hisp.dhis.datavalue.DataValue;
import org.hisp.dhis.datavalue.DataValueService;
import org.hisp.dhis.i18n.I18n;
import org.hisp.dhis.i18n.I18nFormat;
import org.hisp.dhis.indicator.IndicatorService;
import org.hisp.dhis.option.OptionSet;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.pbf.analytics.excelreports.AnalyticsQualitySectionHolder;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfAnalyticsQualityDetails;
import org.hisp.dhis.pbf.model.PbfAnalyticsReport;
import org.hisp.dhis.pbf.model.PbfDataElement;
import org.hisp.dhis.pbf.model.PbfReportPeriod;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.user.CurrentUserService;
import org.hisp.dhis.user.User;

import com.opensymphony.xwork2.Action;

/**
 * @author Murodillo Latifov Abdusamadovich
 */
public class QualityAnalyticsDetailsAction
    implements Action
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private PbfService pbfService;

    public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
	}

    private I18nFormat format;

    public void setFormat( I18nFormat format )
    {
        this.format = format;
    }

    private I18n i18n;

    public void setI18n( I18n i18n )
    {
        this.i18n = i18n;
    }
    
    private CurrentUserService currentUserService;

    public void setCurrentUserService( CurrentUserService currentUserService )
    {
        this.currentUserService = currentUserService;
    }

    private DataElementService dataElementService;

    public void setDataElementService( DataElementService dataElementService )
    {
        this.dataElementService = dataElementService;
    }

    private IndicatorService indicatorService;

    public void setIndicatorService( IndicatorService indicatorService )
    {
        this.indicatorService = indicatorService;
    }
    
    private OrganisationUnitService organisationUnitService;

    public void setOrganisationUnitService( OrganisationUnitService organisationUnitService )
    {
        this.organisationUnitService = organisationUnitService;
    }

    private DataValueService dataValueService;

    public void setDataValueService( DataValueService dataValueService )
    {
        this.dataValueService = dataValueService;
    }

    private CategoryService categoryService;

    public void setCategoryService( CategoryService categoryService )
    {
        this.categoryService = categoryService;
    }

    private PeriodService periodService;
    
    public void setPeriodService( PeriodService periodService )
    {
        this.periodService = periodService;
    }

    private DataSetService dataSetService;

    public void setDataSetService( DataSetService dataSetService )
    {
        this.dataSetService = dataSetService;
    }

    // -------------------------------------------------------------------------
    // Input/output
    // -------------------------------------------------------------------------


	//helper strings
	
	private String countryName;
	private String provinceName;
	private String districtName;
	private String facilityName;
    
	private Double qualityScore = 0d;
	
	public Double getQualityScore() {
		return qualityScore;
	}

	public void setQualityScore(Double qualityScore) {
		this.qualityScore = qualityScore;
	}

	private Double totalQualityScore = 0d;
	
	public Double getTotalQualityScore() {
		return totalQualityScore;
	}

	public void setTotalQualityScore(Double totalQualityScore) {
		this.totalQualityScore = totalQualityScore;
	}

    private Double basisValue = 0d;
    
    public Double getBasisValue() {
		return basisValue;
	}

	public void setBasisValue(Double basisValue) {
		this.basisValue = basisValue;
	}

	private Double thresholdValue = 0d;
    
    public Double getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(Double thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	private int memberCount;

    public int getMemberCount()
    {
        return memberCount;
    }
    
    private String id;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private Collection<PbfAnalyticsReport> pbfAnalyticsReports;

	public Collection<PbfAnalyticsReport> getPbfAnalyticsReports() {
		return pbfAnalyticsReports;
	}

	public void setPbfAnalyticsReports(List<PbfAnalyticsReport> pbfAnalyticsReports) {
		this.pbfAnalyticsReports = pbfAnalyticsReports;
	}
	
	// -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

	public String execute()
    {
		User user = currentUserService.getCurrentUser();
//System.out.println(user.hasOrganisationUnit());
		Collection<OrganisationUnit> organisationUnits = user.getOrganisationUnits();

		PbfReportPeriod pbfReportPeriod = pbfService.getPbfReportPeriod(Long.valueOf(id));

		List<PbfDataElement> pbfDataElements = pbfService.getAllPbfDataElements();

		PbfDataElement pbfDataElement = pbfDataElements.get(0);
		
		OrganisationUnit providerOrgUnit = pbfDataElement.getProviderOrgUnit();
		Period providerPeriod = pbfDataElement.getPeriodQuarterMax();
		
		CategoryOptionCombo deoc = pbfDataElement.getOptionCombo();
		
		CategoryOptionCombo qScoreDeoc = pbfDataElement.getqScoreOptionCombo();
		CategoryOptionCombo qIntDeoc = pbfDataElement.getqIntOptionCombo();
		CategoryOptionCombo qExtDeoc = pbfDataElement.getqExtOptionCombo();
		
		DataSet dataSetIntVer = null;
		DataSet dataSetExtVer = null;

		PbfAnalyticsQualityDetails pbfAnQuDetails = null;
	
		
		for(OrganisationUnit ou: organisationUnits){
			PbfAnalyticsReport pbfAnalyticsReport = pbfService.getAnalyticsReport(pbfReportPeriod, ou, user);
			
				//get facility type and its dataset			
				if(ou.getPhoneNumber().equalsIgnoreCase("0")){
					dataSetIntVer = pbfDataElement.getHcDataSetIntVer();
					dataSetExtVer = pbfDataElement.getHcDataSetExtVer();
				}else if(ou.getPhoneNumber().equalsIgnoreCase("1")){
					dataSetIntVer = pbfDataElement.getHhDataSetIntVer();
					dataSetExtVer = pbfDataElement.getHhDataSetExtVer();
				}
				
				AnalyticsQualitySectionHolder aQualitySecHolder = new AnalyticsQualitySectionHolder();
				
				unitName(ou);
				
				aQualitySecHolder.setFacilityName(facilityName);
				aQualitySecHolder.setDistrictName(districtName);
				aQualitySecHolder.setProvinceName(provinceName);
				aQualitySecHolder.setCountryName(countryName);
				//internal verification data set sections
				List<Section> intVerSections = new ArrayList<>( dataSetIntVer.getSections() );
				//external verification data set sections
				List<Section> extVerSections = new ArrayList<>( dataSetExtVer.getSections() );
				
				Collections.sort( intVerSections, new SectionOrderComparator() );
				Collections.sort( extVerSections, new SectionOrderComparator() );
				
				for(Section section : intVerSections){
					int i=1;
					
					Section extSection = extVerSections.get(section.getSortOrder()-1);
					List<DataElement> extDataElements = extSection.getDataElements();
					
					System.out.println(section.getSortOrder() + " - " +extVerSections.get(section.getSortOrder()-1).getSortOrder());
					
					for(DataElement dataElement : section.getDataElements()){
						
						//get or create pbfcalulation object
										pbfAnQuDetails = pbfService.getPbfAnalyticsQualityDetailsByPrimaryKeys(dataElement, deoc,  ou, pbfAnalyticsReport.getReportPeriod());
										if(pbfAnQuDetails==null)
											pbfAnQuDetails = new PbfAnalyticsQualityDetails();
										pbfAnQuDetails.setDataElement(dataElement);
										pbfAnQuDetails.setIndicatorName(dataElement.getName());	
										//pbfAnQuDetails.setOptionCombo(deoc);
										pbfAnQuDetails.setOrgUnit(ou);
										pbfAnQuDetails.setOptionCombo(deoc);
										pbfAnQuDetails.setSortOrder(i);
										pbfAnQuDetails.setSectionSortOrder(section.getSortOrder());
									
										pbfAnQuDetails.setFacilityName(facilityName);
										pbfAnQuDetails.setDistrictName(districtName);
										pbfAnQuDetails.setProvinceName(provinceName);
										pbfAnQuDetails.setCountryName(countryName);
										
										//pbfAnQuDetails.setFacilityType(ou.getPhoneNumber());
										pbfAnQuDetails.setFacilityType(ou.getDescription());
										pbfAnQuDetails.setPeriodQuarter(pbfAnalyticsReport.getReportPeriod().getQuarterPeriod());
										
										pbfAnQuDetails.setPeriodName(pbfAnalyticsReport.getReportPeriod().getQuarterPeriod().getStartDateString() + " - " + pbfAnalyticsReport.getReportPeriod().getQuarterPeriod().getEndDateString());
										
										pbfAnQuDetails.setDataSetMax(dataSetIntVer);
										pbfAnQuDetails.setSectionMax(section);
										pbfAnQuDetails.setSectionName(section.getName());
										pbfAnQuDetails.setIndicatorName(dataElement.getName());
										
						//set max score of quality indicator
										DataValue maxScoreDv = dataValueService.getDataValue( dataElement, providerPeriod, providerOrgUnit, qScoreDeoc );
										OptionSet optionSet = dataElement.getOptionSet();
										OptionalInt max = null;
										try{
										Collection<String> values = optionSet.getOptionCodes();
										max = values.stream().mapToInt((x) -> Integer.valueOf(x)).max();
										System.out.println("max value " + max.getAsInt());
										}
										catch(NullPointerException npe){
											System.out.println("NULL option value ");
										}
										
										//System.out.println(dataElement.getId() +" "+ providerPeriod.getId()+" "+  providerOrgUnit.getId()+" "+ qScoreDeoc.getId()+" "+ scoreDv.getValue() );
										try{
											pbfAnQuDetails.setQuarterQtyMax(Double.valueOf(max.getAsInt()));
											//pbfAnQuDetails.setDataElement(dataElement);
										
										}catch(NullPointerException npe){
											
										}catch(NumberFormatException nfe){
											
										}
										
						//set internal verification score	
										 
										//DataValue qIntDv = dataValueService.getDataValue( dataElement.getId(), providerPeriod.getId(), providerOrgUnit.getId(), qIntDeoc.getId() );
										//System.out.println("INT " + dataElement.getId() +" "+ providerPeriod.getId()+" "+  providerOrgUnit.getId()+" "+ qScoreDeoc.getId()+" "+ qIntDv.getValue() );
										try{
											//int deId = Integer.valueOf(qIntDv.getValue());
											DataValue qIntValue = dataValueService.getDataValue( dataElement, pbfReportPeriod.getQuarterPeriod(), ou, qIntDeoc ); 	
											System.out.println("internal " + dataElement.getId() +" "+ pbfReportPeriod.getQuarterPeriod().getId()+" "+  ou.getId()+" "+ qIntDeoc.getId()+" " );
											pbfAnQuDetails.setQuarterQtyIntVer(Double.valueOf(qIntValue.getValue()));
											//pbfAnQuDetails.setDataElement(dataElementService.getDataElement(deId));
										}catch(NullPointerException npe){
										System.out.println("Null point at int value set");
										}catch(NumberFormatException nfe){
											
										}
										
						//set external verification score				
										
										
										try{
										DataElement extVerDataElement = extDataElements.get(i-1);
										System.out.println(dataElement.getName() + " extd-e " +extVerDataElement.getName());
										
										//DataValue qExtDv = dataValueService.getDataValue( dataElement.getId(), providerPeriod.getId(), providerOrgUnit.getId(), qExtDeoc.getId() );
										//System.out.println("EXT " + dataElement.getId() +" "+ providerPeriod.getId()+" "+  providerOrgUnit.getId()+" "+ qScoreDeoc.getId()+" "+ qExtDv.getValue() );
										
											//int deId = Integer.valueOf(qExtDv.getValue());
											DataValue qExtValue = dataValueService.getDataValue( extVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, qExtDeoc ); 	
											System.out.println("external " + dataElement.getId() +" "+ pbfReportPeriod.getQuarterPeriod().getId()+" "+  ou.getId()+" "+ qExtDeoc.getId()+" " );
											pbfAnQuDetails.setQuarterQtyExtVer(Double.valueOf(qExtValue.getValue()));
											//pbfAnQuDetails.setDataElement(dataElementService.getDataElement(deId));
										}catch(NullPointerException npe){
											System.out.println("Null point at ext value set");
										}catch(NumberFormatException nfe){
											
										}catch(ArrayIndexOutOfBoundsException aiob){
											System.out.println("number and order of data elements for internal and external quality do not match");
										}
						//save analytics details				
										pbfService.saveOrUpdate(pbfAnQuDetails);
								i++;
					}
				}
			}

		pbfAnalyticsReports = pbfService.getPbfAnalyticsReportsByUserOrgUnits(pbfReportPeriod, organisationUnits);

        memberCount = pbfAnalyticsReports.size();

        return SUCCESS;
    }

	private void unitName(OrganisationUnit ou){
		int count =0;
		if(ou.getLevel()==1){
			countryName = ou.getName();
		}else if(ou.getLevel()==2){
			provinceName = ou.getName();
		}else if(ou.getLevel()==3){
			districtName = ou.getName();
		}else if(ou.getLevel()==4){
			facilityName = ou.getName();	
		}else{
			
		}
		if (ou.getLevel()>1) {
			unitName(ou.getParent());
		}
	}
}