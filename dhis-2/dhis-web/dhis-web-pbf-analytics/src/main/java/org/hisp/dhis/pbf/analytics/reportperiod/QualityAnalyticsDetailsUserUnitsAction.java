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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.pbf.analytics.excelreports.AnalyticsQualitySectionHolder;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfAnalyticsQualityDetails;
import org.hisp.dhis.pbf.model.PbfAnalyticsReport;
import org.hisp.dhis.pbf.model.PbfDataElement;
import org.hisp.dhis.pbf.model.PbfReportPeriod;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.user.CurrentUserService;
import org.hisp.dhis.user.User;

import com.opensymphony.xwork2.Action;

/**
 * @author Murodillo Latifov Abdusamadovich
 */
public class QualityAnalyticsDetailsUserUnitsAction
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
	
	private String countryNameEn;
	private String provinceNameEn;
	private String districtNameEn;
	private String facilityNameEn;
	
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

	private String repId;
	
	public String getRepId() {
		return repId;
	}

	public void setRepId(String repId) {
		this.repId = repId;
	}

	private String ouId;
	
	public String getOuId() {
		return ouId;
	}

	public void setOuId(String ouId) {
		this.ouId = ouId;
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

		//Collection<OrganisationUnit> organisationUnits = new HashSet<OrganisationUnit>();

		//OrganisationUnit oun = organisationUnitService.getOrganisationUnit(ouId);
				
		//organisationUnits.add(oun);
		
		PbfReportPeriod pbfReportPeriod = pbfService.getPbfReportPeriod(Long.valueOf(repId));

		List<PbfDataElement> pbfDataElements = pbfService.getAllPbfDataElements();

		PbfDataElement pbfDataElement = pbfDataElements.get(0);
		
		//OrganisationUnit providerOrgUnit = pbfDataElement.getProviderOrgUnit();
		//Period providerPeriod = pbfDataElement.getPeriodQuarterMax();
		
		//CategoryOptionCombo deoc = pbfDataElement.getOptionCombo();
		
/*		CategoryOptionCombo qScoreDeoc = pbfDataElement.getqScoreOptionCombo();
		CategoryOptionCombo qIntDeoc = pbfDataElement.getqIntOptionCombo();
		CategoryOptionCombo qExtDeoc = pbfDataElement.getqExtOptionCombo();*/
		
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

				
				aQualitySecHolder.setFacilityNameEn(facilityNameEn);
				aQualitySecHolder.setDistrictNameEn(districtNameEn);
				aQualitySecHolder.setProvinceNameEn(provinceNameEn);
				aQualitySecHolder.setCountryNameEn(countryNameEn);
				
				//aQualitySecHolder.setFacilityType(ou.getDescription());
				
				//internal verification data set sections
				List<Section> intVerSections = new ArrayList<>( dataSetIntVer.getSections() );
				//external verification data set sections
				//List<Section> extVerSections = new ArrayList<>( dataSetExtVer.getSections() );
				
				Collections.sort( intVerSections, new SectionOrderComparator() );
				//Collections.sort( extVerSections, new SectionOrderComparator() );
				
				for(Section section : intVerSections){
						int i=1;
					

					System.out.println(section.getSortOrder() + " - " +intVerSections.get(section.getSortOrder()-1).getSortOrder());
					
					for(DataElement dataElement : section.getDataElements()){
						
						System.out.println(section.getSortOrder() + " - de " +dataElement.getId());
						
						Set<CategoryOptionCombo> decocs = dataElement.getCategoryOptionCombos();
						for(CategoryOptionCombo deco : decocs){
							
							System.out.println(section.getSortOrder() + " - deco " +deco.getId());
							
							//get or create pbfcalulation object
							pbfAnQuDetails = pbfService.getPbfAnalyticsQualityDetailsByPrimaryKeys(dataElement, deco,  ou, pbfAnalyticsReport.getReportPeriod());
							if(pbfAnQuDetails==null)
								pbfAnQuDetails = new PbfAnalyticsQualityDetails();
							pbfAnQuDetails.setDataElement(dataElement);
							pbfAnQuDetails.setIndicatorName(dataElement.getFormName() + " " + deco.getDisplayName());
							pbfAnQuDetails.setIndicatorNameEn(dataElement.getDescription());	
							//pbfAnQuDetails.setOptionCombo(deoc);
							pbfAnQuDetails.setOrgUnit(ou);
							pbfAnQuDetails.setOptionCombo(deco);
							pbfAnQuDetails.setSortOrder(i);
							pbfAnQuDetails.setSectionSortOrder(section.getSortOrder());
			
							pbfAnQuDetails.setFacilityName(facilityName);
							pbfAnQuDetails.setDistrictName(districtName);
							pbfAnQuDetails.setProvinceName(provinceName);
							pbfAnQuDetails.setCountryName(countryName);
							
							pbfAnQuDetails.setFacilityNameEn(facilityNameEn);
							pbfAnQuDetails.setDistrictNameEn(districtNameEn);
							pbfAnQuDetails.setProvinceNameEn(provinceNameEn);
							pbfAnQuDetails.setCountryNameEn(countryNameEn);
							
							pbfAnQuDetails.setFacilityType(ou.getPhoneNumber());

							pbfAnQuDetails.setPeriodName(pbfAnalyticsReport.getReportPeriod().getQuarterPeriod().getStartDateString() + " - " + pbfAnalyticsReport.getReportPeriod().getQuarterPeriod().getEndDateString());
							
							pbfAnQuDetails.setPeriodQuarter(pbfAnalyticsReport.getReportPeriod().getQuarterPeriod());
							pbfAnQuDetails.setDataSetMax(dataSetIntVer);
							pbfAnQuDetails.setSectionMax(section);
							pbfAnQuDetails.setSectionName(section.getName());
							pbfAnQuDetails.setSectionNameEn(section.getDescription());
							pbfAnQuDetails.setIndicatorName(dataElement.getShortName());
							
							
							if(dataElement.isZeroIsSignificant()){
								pbfAnQuDetails.setQuarterQtyMax(Double.valueOf(2));
							}else{
								pbfAnQuDetails.setQuarterQtyMax(Double.valueOf(1));
							}
							
							
							
			//set internal verification score	
							try{
								//int deId = Integer.valueOf(qIntDv.getValue());
								DataValue qIntValue = dataValueService.getDataValue( dataElement, pbfReportPeriod.getQuarterPeriod(), ou, deco ); 	
								System.out.println("internal " + dataElement.getId() +" "+ pbfReportPeriod.getQuarterPeriod().getId()+" "+  ou.getId()+" "+ deco.getId()+" " );
								if(qIntValue.getValue().equalsIgnoreCase("true")){

									if(dataElement.isZeroIsSignificant()){
										pbfAnQuDetails.setQuarterQtyIntVer(Double.valueOf(2));
									}else{
										pbfAnQuDetails.setQuarterQtyIntVer(Double.valueOf(1));
									}
								}
								

							}catch(NullPointerException npe){
							System.out.println("Null point at int value set");
							}catch(NumberFormatException nfe){
								
							}catch(ArrayIndexOutOfBoundsException aiob){
								System.out.println("number and order of data elements for internal and external quality do not match");
							}
							
			//set external verification score				
							
							
							try{

								DataElement extVerDataElement = pbfService.getExtVerDataElementbyIntVerId(dataElement);
								
								//DataElement extVerDataElement = dataElementService.getDataElement(Long.valueOf(dataElement.getAggregateExportCategoryOptionCombo()));
								
								DataValue qExtValue = dataValueService.getDataValue( extVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, deco ); 	
								System.out.println("external " + extVerDataElement.getId() +" "+ pbfReportPeriod.getQuarterPeriod().getId()+" "+  ou.getId()+" "+ deco.getId()+" " );

								if(qExtValue.getValue().equalsIgnoreCase("true")){

									if(dataElement.isZeroIsSignificant()){
										pbfAnQuDetails.setQuarterQtyExtVer(Double.valueOf(2));
									}else{
										pbfAnQuDetails.setQuarterQtyExtVer(Double.valueOf(1));
									}
								}
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
			}


		pbfAnalyticsReports = pbfService.getPbfAnalyticsReportsByUserOrgUnits(pbfReportPeriod, organisationUnits);

        memberCount = pbfAnalyticsReports.size();

        return SUCCESS;
    }
	
	private void unitName(OrganisationUnit ou){
		int count =0;
		if(ou.getLevel()==1){
			countryName = ou.getName();
			countryNameEn = ou.getUrl();
		}else if(ou.getLevel()==2){
			provinceName = ou.getName();
			provinceNameEn = ou.getUrl();
		}else if(ou.getLevel()==3){
			districtName = ou.getName();
			districtNameEn = ou.getUrl();
		}else if(ou.getLevel()==4){
			facilityName = ou.getName();
			facilityNameEn = ou.getUrl();
		}else{
			
		}
		if (ou.getLevel()>1) {
			unitName(ou.getParent());
		}
	}
}