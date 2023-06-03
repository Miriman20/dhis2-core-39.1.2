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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.dataset.DataSetService;
import org.hisp.dhis.datavalue.DataValue;
import org.hisp.dhis.datavalue.DataValueService;
import org.hisp.dhis.i18n.I18n;
import org.hisp.dhis.i18n.I18nFormat;
import org.hisp.dhis.indicator.IndicatorService;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.pbf.analytics.math.Expression;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfAnalyticsReport;
import org.hisp.dhis.pbf.model.PbfAnalyticsReportDetails;
import org.hisp.dhis.pbf.model.PbfDataElement;
import org.hisp.dhis.pbf.model.PbfReportPeriod;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.period.QuarterlyPeriodType;
import org.hisp.dhis.user.CurrentUserService;
import org.hisp.dhis.user.User;

import com.opensymphony.xwork2.Action;

/**
 * @author Murodillo Latifov Abdusamadovich
 */
public class QuantityAnalyticsDetailsSingleUnitAction
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

	//helper strings
	
	private String countryName;
	private String provinceName;
	private String districtName;
	private String facilityName;

	
	private String countryNameEn;
	private String provinceNameEn;
	private String districtNameEn;
	private String facilityNameEn;
	
	// -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

	public String execute()
    {
		User user = currentUserService.getCurrentUser();
//System.out.println(user.hasOrganisationUnit());
		//Collection<OrganisationUnit> organisationUnits = user.getOrganisationUnits();

		Collection<OrganisationUnit> organisationUnits = new HashSet();

		OrganisationUnit oun = organisationUnitService.getOrganisationUnit(ouId);
				
		organisationUnits.add(oun);
		
		PbfReportPeriod pbfReportPeriod = pbfService.getPbfReportPeriod(Long.valueOf(repId));

		List<PbfDataElement> dataElements = pbfService.getAllPbfDataElements();

		PbfAnalyticsReportDetails pbfAnalyticalReportDetails = null;
		int coun = 0;
		
		for(OrganisationUnit ou: organisationUnits){
			PbfAnalyticsReport pbfAnalyticsReport = pbfService.getAnalyticsReport(pbfReportPeriod, ou, user);
			
			for(PbfDataElement pde: dataElements){
				String[] tokens = null;
				//parse formulae			
				if(ou.getPhoneNumber().equalsIgnoreCase("0")){
					tokens = pde.getCalculationFormulaPhc().split("\\\\\\\\");	
				}else if(ou.getPhoneNumber().equalsIgnoreCase("1")){
					 tokens = pde.getCalculationFormulaRphc().split("\\\\\\\\");
				}
				
				// internal verification data element	
				DataElement intVerDataElement = pde.getIntVerDataElement();
				// external verification data element	
				DataElement extVerDataElement = pde.getExtVerDataElement();
				
				//optioncombos for male/female by months
				CategoryOptionCombo momoptionCombo = pde.getOptionComboMom();
				CategoryOptionCombo mofoptionCombo = pde.getOptionComboMof();
				
				CategoryOptionCombo mtmoptionCombo = pde.getOptionComboMtm();
				CategoryOptionCombo mtfoptionCombo = pde.getOptionComboMtf();
				
				CategoryOptionCombo mhmoptionCombo = pde.getOptionComboMhm();
				CategoryOptionCombo mhfoptionCombo = pde.getOptionComboMhf();
				
				//facility data element
				DataElement facilityDataElement = pde.getFacilityDataElement();
				//default optioncombo
				CategoryOptionCombo optionCombo = pde.getOptionCombo();
				
				//audit data element goes with default optioncombo
				DataElement auditDataElement = pde.getAuditDataElement();
				//quality data element goes with default optioncombo				
				DataElement quailityDataElement = pde.getQualityScoreDataElement();
				//control amount data element goes with default optioncombo				
				DataElement currationAmountDataElement = pde.getCurrationAmountDataElement();
				//facility control data element goes with default optioncombo				
				DataElement currationDataElement = pde.getCurrationDataElement();
			
//get or create pbfanalytics object
				pbfAnalyticalReportDetails = pbfService.getPbfAnalyticalReportDetailsByPrimaryKeys(intVerDataElement, optionCombo, ou, pbfAnalyticsReport.getReportPeriod());
				if(pbfAnalyticalReportDetails==null)
					pbfAnalyticalReportDetails = new PbfAnalyticsReportDetails();
				pbfAnalyticalReportDetails.setDataElement(intVerDataElement);
				pbfAnalyticalReportDetails.setOptionCombo(optionCombo);
				
				pbfAnalyticalReportDetails.setIndicatorName(intVerDataElement.getShortName());
				pbfAnalyticalReportDetails.setIndicatorNameEn(intVerDataElement.getDescription());
				
				pbfAnalyticalReportDetails.setComboName(optionCombo.getDisplayName());

				pbfAnalyticalReportDetails.setOrgUnit(ou);

				pbfAnalyticalReportDetails.setSortOrder(pde.getSortOrder());
				
				unitName(ou);
				
				pbfAnalyticalReportDetails.setFacilityName(facilityName);
				pbfAnalyticalReportDetails.setDistrictName(districtName);
				pbfAnalyticalReportDetails.setProvinceName(provinceName);
				pbfAnalyticalReportDetails.setCountryName(countryName);
				
				pbfAnalyticalReportDetails.setFacilityNameEn(facilityNameEn);
				pbfAnalyticalReportDetails.setDistrictNameEn(districtNameEn);
				pbfAnalyticalReportDetails.setProvinceNameEn(provinceNameEn);
				pbfAnalyticalReportDetails.setCountryNameEn(countryNameEn);

				pbfAnalyticalReportDetails.setPeriodName(pbfAnalyticsReport.getReportPeriod().getQuarterPeriod().getStartDateString()+" - "+pbfAnalyticsReport.getReportPeriod().getQuarterPeriod().getEndDateString());
				
				pbfAnalyticalReportDetails.setPeriodQuarter(pbfAnalyticsReport.getReportPeriod().getQuarterPeriod());
				pbfAnalyticalReportDetails.setFacilityType(ou.getDescription());
				pbfAnalyticalReportDetails.setFacilityTypeEn(ou.getComment());
				
				pbfAnalyticalReportDetails.setThresholdValue(0d);
				pbfAnalyticalReportDetails.setBasisValue(0d);
				
//set month 1 value				
				pbfAnalyticalReportDetails.setMonthOne(pbfReportPeriod.getMonthOne().getDisplayName());
				pbfAnalyticalReportDetails.setPeriodOne(pbfReportPeriod.getMonthOne());

				//facility data
				DataValue dvfmom = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, momoptionCombo );
				DataValue dvfmof = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mofoptionCombo );

				
				int momfv = 0;
				try{
					momfv = Integer.valueOf(dvfmom.getValue());
				}catch (NullPointerException npe){
					momfv =0;
				}
				//set facility month 1 male qty 
				pbfAnalyticalReportDetails.setMonthOneQtyFacilityMale(Double.valueOf(momfv));
				int moffv = 0;
				try{
					moffv = Integer.valueOf(dvfmof.getValue());
				}catch (NullPointerException npe){
					moffv =0;
				}
				//set facility month 1 female qty
				pbfAnalyticalReportDetails.setMonthOneQtyFacilityFemale(Double.valueOf(moffv));
				int movf = momfv+moffv;
				pbfAnalyticalReportDetails.setMonthOneQtyFacility(Double.valueOf(movf));
				
				
				//internal verification data
				DataValue dvmom = dataValueService.getDataValue( intVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, momoptionCombo );
				DataValue dvmof = dataValueService.getDataValue( intVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mofoptionCombo );

				int momv = 0;
				try{
					momv = Integer.valueOf(dvmom.getValue());
				}catch (NullPointerException npe){
					momv =0;
				}
				//set facility month 1 male qty 
				pbfAnalyticalReportDetails.setMonthOneQtyIntVerMale(Double.valueOf(momv));
				int mofv = 0;
				try{
					mofv = Integer.valueOf(dvmof.getValue());
				}catch (NullPointerException npe){
					mofv =0;
				}
				//set facility month 1 female qty
				pbfAnalyticalReportDetails.setMonthOneQtyIntVerFemale(Double.valueOf(mofv));
				int mov = momv+mofv;
				pbfAnalyticalReportDetails.setMonthOneQtyIntVer(Double.valueOf(mov));
				try{
				
				tokens[6].toString();
				
Double finalValue = getThresholdValue(tokens[6], pbfReportPeriod.getQuarterPeriod().getId(), ou.getId(), mov );
				
		//pbfAnalyticalReportDetails.setMonthOneQtyFacility(Double.valueOf(mov));
				//pbfAnalyticalReportDetails.setMonthOneQtyIntVer(finalValue);
				try{
				if(pbfAnalyticalReportDetails.getThresholdValue()<thresholdValue){
				pbfAnalyticalReportDetails.setThresholdValue(thresholdValue);
				}
				}catch(NullPointerException npe){
					pbfAnalyticalReportDetails.setThresholdValue(0d);
				}
				
				try{	
				
				if(pbfAnalyticalReportDetails.getBasisValue()<basisValue){
				pbfAnalyticalReportDetails.setBasisValue(basisValue);
				}
				}catch(NullPointerException npe){
					pbfAnalyticalReportDetails.setBasisValue(0d);
				}
				
				}catch (NullPointerException npe){
					if(mov!=0){
					//System.out.println("DV value 1 catch null " + dv.getValue());
					npe.printStackTrace();
					//pbfAnalyticalReportDetails.setMonthOneQtyFacility(Double.valueOf(mov));
					//pbfAnalyticalReportDetails.setMonthOneQtyIntVer(Double.valueOf(mov));
					}else{
						//pbfAnalyticalReportDetails.setMonthOneQtyFacility(0d);
						//pbfAnalyticalReportDetails.setMonthOneQtyIntVer(0d);
					}
				
				}catch (ArrayIndexOutOfBoundsException aiobe){
					if(mov!=0){
					//System.out.println("DV value 2 null " + dv.getValue());
					//pbfAnalyticalReportDetails.setMonthOneQtyFacility(Double.valueOf(mov));
					//pbfAnalyticalReportDetails.setMonthOneQtyIntVer(Double.valueOf(mov));
					}else{
						//pbfAnalyticalReportDetails.setMonthOneQtyFacility(0d);
						//pbfAnalyticalReportDetails.setMonthOneQtyIntVer(0d);
					}
				}
				
				//external verification data
				DataValue dvemom = dataValueService.getDataValue( extVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, momoptionCombo );
				DataValue dvemof = dataValueService.getDataValue( extVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mofoptionCombo );
				
				int momev = 0;
				try{
					momev = Integer.valueOf(dvemom.getValue());
				}catch (NullPointerException npe){
					momev =0;
				}
				//set facility month 1 male qty 
				pbfAnalyticalReportDetails.setMonthOneQtyExtVerMale(Double.valueOf(momev));
				int mofev = 0;
				try{
					mofev = Integer.valueOf(dvemof.getValue());
				}catch (NullPointerException npe){
					mofev =0;
				}
				//set facility month 1 female qty
				pbfAnalyticalReportDetails.setMonthOneQtyExtVerFemale(Double.valueOf(mofev));
				int move = momev+mofev;
				pbfAnalyticalReportDetails.setMonthOneQtyExtVer(Double.valueOf(move));
				
				
//set month 2 value
				pbfAnalyticalReportDetails.setMonthTwo(pbfReportPeriod.getMonthTwo().getDisplayName());
				pbfAnalyticalReportDetails.setPeriodTwo(pbfReportPeriod.getMonthTwo());
				
				//facility data
				DataValue dvfmtm = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtmoptionCombo );
				DataValue dvfmtf = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtfoptionCombo );

				
				int mtmfv = 0;
				try{
					mtmfv = Integer.valueOf(dvfmtm.getValue());
				}catch (NullPointerException npe){
					mtmfv =0;
				}
				//set facility month 1 male qty 
				pbfAnalyticalReportDetails.setMonthTwoQtyFacilityMale(Double.valueOf(mtmfv));
				int mtffv = 0;
				try{
					mtffv = Integer.valueOf(dvfmtf.getValue());
				}catch (NullPointerException npe){
					mtffv =0;
				}
				//set facility month 1 female qty
				pbfAnalyticalReportDetails.setMonthTwoQtyFacilityFemale(Double.valueOf(mtffv));
				int mtvf = mtmfv+mtffv;
				pbfAnalyticalReportDetails.setMonthTwoQtyFacility(Double.valueOf(mtvf));
				
				//dv = dataValueService.getDataValue(ou, intVerDataElement, pbfReportPeriod.getMonthTwo(), optionCombo);
				//dv = dataValueService.getDataValue( intVerDataElement.getId(), pbfReportPeriod.getMonthTwo().getId(), ou.getId(), optionCombo.getId() );
				
				DataValue dvmtm = dataValueService.getDataValue( intVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtmoptionCombo );
				DataValue dvmtf = dataValueService.getDataValue( intVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtfoptionCombo );
				
				int mtmv = 0;
				try{
					mtmv = Integer.valueOf(dvmtm.getValue());
				}catch (NullPointerException npe){
					mtmv =0;
				}
				//set facility month 2 male qty
				pbfAnalyticalReportDetails.setMonthTwoQtyIntVerMale(Double.valueOf(mtmv));
				int mtfv = 0;
				try{
					mtfv = Integer.valueOf(dvmtf.getValue());
				}catch (NullPointerException npe){
					mtfv =0;
				}
				//set facility month 2 female qty
				pbfAnalyticalReportDetails.setMonthTwoQtyIntVerFemale(Double.valueOf(mtfv));
				int mtv = mtmv+mtfv;
				pbfAnalyticalReportDetails.setMonthTwoQtyIntVer(Double.valueOf(mtv));
				//System.out.println(" Month two value " + mtv);
				
				try{
				
				tokens[6].toString();
				
Double finalValue = getThresholdValue(tokens[6], pbfReportPeriod.getQuarterPeriod().getId(), ou.getId(), mtv );

	//pbfAnalyticalReportDetails.setMonthTwoQtyFacility(Double.valueOf(mtv));
				//pbfAnalyticalReportDetails.setMonthTwoQtyIntVer(finalValue);
				try{
				if(pbfAnalyticalReportDetails.getThresholdValue()<thresholdValue){
				pbfAnalyticalReportDetails.setThresholdValue(thresholdValue);
				}
				}catch(NullPointerException npe){
					pbfAnalyticalReportDetails.setThresholdValue(0d);
				}
				
				try{	
				
				if(pbfAnalyticalReportDetails.getBasisValue()<basisValue){
				pbfAnalyticalReportDetails.setBasisValue(basisValue);
				}
				}catch(NullPointerException npe){
					pbfAnalyticalReportDetails.setBasisValue(0d);
				}
				
				}catch (NullPointerException npe){
					if(mtv!=0){
					//System.out.println("DV value 3 catch null " + dv.getValue());
					//pbfAnalyticalReportDetails.setMonthTwoQtyFacility(Double.valueOf(mtv));
					//pbfAnalyticalReportDetails.setMonthTwoQtyIntVer(Double.valueOf(mtv));
					}else{
						//pbfAnalyticalReportDetails.setMonthTwoQtyFacility(0d);
						//pbfAnalyticalReportDetails.setMonthTwoQtyIntVer(0d);
					}
	
				}catch (ArrayIndexOutOfBoundsException aiobe){
					if(mtv!=0){
					//System.out.println("DV value 4 null " + dv.getValue());
					//pbfAnalyticalReportDetails.setMonthTwoQtyFacility(Double.valueOf(mtv));
					//pbfAnalyticalReportDetails.setMonthTwoQtyIntVer(Double.valueOf(mtv));
					}else{
						//pbfAnalyticalReportDetails.setMonthTwoQtyFacility(0d);
						//pbfAnalyticalReportDetails.setMonthTwoQtyIntVer(0d);
					}
				}
				
				//external verification data
				DataValue dvemtm = dataValueService.getDataValue( extVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtmoptionCombo );
				DataValue dvemtf = dataValueService.getDataValue( extVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtfoptionCombo );
				
				int mtmev = 0;
				try{
					mtmev = Integer.valueOf(dvemtm.getValue());
				}catch (NullPointerException npe){
					mtmev =0;
				}
				//set facility month 1 male qty 
				pbfAnalyticalReportDetails.setMonthTwoQtyExtVerMale(Double.valueOf(mtmev));
				int mtfev = 0;
				try{
					mtfev = Integer.valueOf(dvemtf.getValue());
				}catch (NullPointerException npe){
					mtfev =0;
				}
				//set facility month 1 female qty
				pbfAnalyticalReportDetails.setMonthTwoQtyExtVerFemale(Double.valueOf(mtfev));
				int mtve = mtmev+mtfev;
				pbfAnalyticalReportDetails.setMonthTwoQtyExtVer(Double.valueOf(mtve));
				
//set month 3 value
				pbfAnalyticalReportDetails.setMonthThree(pbfReportPeriod.getMonthThree().getDisplayName());
				pbfAnalyticalReportDetails.setPeriodThree(pbfReportPeriod.getMonthThree());
				
				//facility data
				DataValue dvfmhm = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhmoptionCombo );
				DataValue dvfmhf = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhfoptionCombo );

				
				int mhmfv = 0;
				try{
					mhmfv = Integer.valueOf(dvfmhm.getValue());
				}catch (NullPointerException npe){
					mhmfv =0;
				}
				//set facility month 1 male qty 
				pbfAnalyticalReportDetails.setMonthThreeQtyFacilityMale(Double.valueOf(mhmfv));
				int mhffv = 0;
				try{
					mhffv = Integer.valueOf(dvfmhf.getValue());
				}catch (NullPointerException npe){
					mhffv =0;
				}
				//set facility month 1 female qty
				pbfAnalyticalReportDetails.setMonthThreeQtyFacilitFemale(Double.valueOf(mhffv));
				int mhvf = mtmfv+mtffv;
				pbfAnalyticalReportDetails.setMonthThreeQtyFacility(Double.valueOf(mhvf));
				
				//dv = dataValueService.getDataValue(ou, intVerDataElement, pbfReportPeriod.getMonthThree(), optionCombo);
				//dv = dataValueService.getDataValue( intVerDataElement.getId(), pbfReportPeriod.getMonthThree().getId(), ou.getId(), optionCombo.getId() );
				
				DataValue dvmhm = dataValueService.getDataValue( intVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhmoptionCombo );
				DataValue dvmhf = dataValueService.getDataValue( intVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhfoptionCombo );
				
				int mhmv = 0;
				try{
					mhmv = Integer.valueOf(dvmhm.getValue());
				}catch (NullPointerException npe){
					mhmv =0;
				}
				//set facility month 3 male qty
				pbfAnalyticalReportDetails.setMonthThreeQtyIntVerMale(Double.valueOf(mhmv));
				int mhfv = 0;
				try{
					mhfv = Integer.valueOf(dvmhf.getValue());
				}catch (NullPointerException npe){
					mhfv =0;
				}
				//set facility month 3 female qty
				pbfAnalyticalReportDetails.setMonthThreeQtyIntVerFemale(Double.valueOf(mhfv));
				int mhv = mhmv+mhfv;
				pbfAnalyticalReportDetails.setMonthThreeQtyIntVer(Double.valueOf(mhv));
				//System.out.println(" Month three value " + mhv);
				
				try{
				
				tokens[6].toString();
				
Double finalValue = getThresholdValue(tokens[6], pbfReportPeriod.getQuarterPeriod().getId(), ou.getId(), mhv );
				
//pbfAnalyticalReportDetails.setMonthThreeQtyFacility(Double.valueOf(mhv));
				//pbfAnalyticalReportDetails.setMonthThreeQtyIntVer(finalValue);
				try{
					if(pbfAnalyticalReportDetails.getThresholdValue()<thresholdValue){
					pbfAnalyticalReportDetails.setThresholdValue(thresholdValue);
					}
					}catch(NullPointerException npe){
						pbfAnalyticalReportDetails.setThresholdValue(0d);
					}
					
					try{	
					
					if(pbfAnalyticalReportDetails.getBasisValue()<basisValue){
					pbfAnalyticalReportDetails.setBasisValue(basisValue);
					}
					}catch(NullPointerException npe){
						pbfAnalyticalReportDetails.setBasisValue(0d);
					}
				
				}catch (NullPointerException npe){
					if(mhv!=0){
					//System.out.println("DV value 5 catch null " + dv.getValue());
					//pbfAnalyticalReportDetails.setMonthThreeQtyFacility(Double.valueOf(mhv));
					//pbfAnalyticalReportDetails.setMonthThreeQtyIntVer(Double.valueOf(mhv));
					}else{
						//pbfAnalyticalReportDetails.setMonthThreeQtyFacility(0d);
						//pbfAnalyticalReportDetails.setMonthThreeQtyIntVer(0d);
					}
				
				}catch (ArrayIndexOutOfBoundsException aiobe){
					if(mhv!=0){
					//System.out.println("DV value 6 null " + dv.getValue());
					//pbfAnalyticalReportDetails.setMonthThreeQtyFacility(Double.valueOf(mhv));
					//pbfAnalyticalReportDetails.setMonthThreeQtyIntVer(Double.valueOf(mhv));
					}else{
						//pbfAnalyticalReportDetails.setMonthThreeQtyFacility(0d);
						//pbfAnalyticalReportDetails.setMonthThreeQtyIntVer(0d);
					}
				}
				
				
				//external verification data
				DataValue dvemhm = dataValueService.getDataValue( extVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhmoptionCombo );
				DataValue dvemhf = dataValueService.getDataValue( extVerDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhfoptionCombo );
				
				int mhmev = 0;
				try{
					mhmev = Integer.valueOf(dvemhm.getValue());
				}catch (NullPointerException npe){
					mhmev =0;
				}
				//set facility month 3 male qty 
				pbfAnalyticalReportDetails.setMonthThreeQtyExtVerMale(Double.valueOf(mhmev));
				int mhfev = 0;
				try{
					mhfev = Integer.valueOf(dvemhf.getValue());
				}catch (NullPointerException npe){
					mhfev =0;
				}
				//set facility month 1 female qty
				pbfAnalyticalReportDetails.setMonthThreeQtyExtVerFemale(Double.valueOf(mhfev));
				int mhve = mtmev+mtfev;
				pbfAnalyticalReportDetails.setMonthThreeQtyExtVer(Double.valueOf(mhve));
				
				
//set quarter value
				try{
					pbfAnalyticalReportDetails.setQuarterQtyExtVerFemale(pbfAnalyticalReportDetails.getMonthOneQtyExtVerFemale()+pbfAnalyticalReportDetails.getMonthTwoQtyExtVerFemale()+pbfAnalyticalReportDetails.getMonthThreeQtyExtVerFemale());
					}
					catch(Exception e){
						
					}
					try{
					pbfAnalyticalReportDetails.setQuarterQtyExtVerMale(pbfAnalyticalReportDetails.getMonthOneQtyExtVerMale()+pbfAnalyticalReportDetails.getMonthTwoQtyExtVerMale()+pbfAnalyticalReportDetails.getMonthThreeQtyExtVerMale());
					}
					catch(Exception e){
						
					}
					try{
					pbfAnalyticalReportDetails.setQuarterQtyExtVer(pbfAnalyticalReportDetails.getMonthOneQtyExtVer()+pbfAnalyticalReportDetails.getMonthTwoQtyExtVer()+pbfAnalyticalReportDetails.getMonthThreeQtyExtVer());
					}
					catch(Exception e){
						
					}
			
				Double v1 =0d;
				Double v2 =0d;
				Double v3 =0d;
				try{
					v1 = Double.valueOf(pbfAnalyticalReportDetails.getMonthOneQtyFacility());
				}catch(NullPointerException npe) {
					v1=0d;
				}
				
				try{
					v2 = Double.valueOf(pbfAnalyticalReportDetails.getMonthTwoQtyFacility());
				}catch(NullPointerException npe) {
					v2=0d;
				}
				
				try{
					v3 = Double.valueOf(pbfAnalyticalReportDetails.getMonthThreeQtyFacility());
				}catch(NullPointerException npe) {
					v3=0d;
				}
				try{
				pbfAnalyticalReportDetails.setQuarterQtyFacilityFemale(pbfAnalyticalReportDetails.getMonthOneQtyFacilityFemale()+pbfAnalyticalReportDetails.getMonthTwoQtyFacilityFemale()+pbfAnalyticalReportDetails.getMonthThreeQtyFacilitFemale());
				}
				catch(Exception e){
					System.out.println("Ex happ2");	
				}
				try{
				pbfAnalyticalReportDetails.setQuarterQtyFacilityMale(pbfAnalyticalReportDetails.getMonthOneQtyFacilityMale()+pbfAnalyticalReportDetails.getMonthTwoQtyFacilityMale()+pbfAnalyticalReportDetails.getMonthThreeQtyFacilityMale());
				}
				catch(Exception e){
				System.out.println("Ex happ");	
				}
				pbfAnalyticalReportDetails.setQuarterQtyFacility(v1+v2+v3);
				
//set quarter value with threshold applied
				//works while calculating threshold for each month
				pbfAnalyticalReportDetails.setQuarterQtyIntVer(pbfAnalyticalReportDetails.getMonthOneQtyIntVer()+pbfAnalyticalReportDetails.getMonthTwoQtyIntVer()+pbfAnalyticalReportDetails.getMonthThreeQtyIntVer());
				
				pbfAnalyticalReportDetails.setQuarterQtyIntVerFemale(pbfAnalyticalReportDetails.getMonthOneQtyIntVerFemale()+pbfAnalyticalReportDetails.getMonthTwoQtyIntVerFemale()+pbfAnalyticalReportDetails.getMonthThreeQtyIntVerFemale());
				
				pbfAnalyticalReportDetails.setQuarterQtyIntVerMale(pbfAnalyticalReportDetails.getMonthOneQtyIntVerMale()+pbfAnalyticalReportDetails.getMonthTwoQtyIntVerMale()+pbfAnalyticalReportDetails.getMonthThreeQtyIntVerMale());
				
				//set all to quarter qty int ver
				 pbfAnalyticalReportDetails.setQuarterQtyIntVerThreshold(pbfAnalyticalReportDetails.getQuarterQtyIntVer());
				 
				//works for sum of quarter months value
				try{
				 int[] ids = new int[4];
						int  count=0;
						  Pattern pGetT = Pattern.compile("getT\\(([\\d,]+\\d)\\)");
						  Pattern pGetT2 = Pattern.compile("([\\d]+)");
						  Matcher mGetT = pGetT.matcher(tokens[6]);
						  String matched = "";
						  String finalValue = "0"; 
						  int count2=0;
						  while (mGetT.find()) {
						         count++;
					
						         Matcher mGetT2 = pGetT2.matcher(mGetT.group());
						         matched = mGetT.group();
						         //System.out.println("matched "+ matched);
						         count2=0;
						         while (mGetT2.find()) {
						        	 count2++;

						        	 ids[count2-1]= Integer.valueOf(mGetT2.group());
						         }
						  }
						  
				 DataValue thresholddv = dataValueService.getDataValue(dataElementService.getDataElement(ids[0]), pbfReportPeriod.getQuarterPeriod(), ou, categoryService.getCategoryOptionCombo(ids[1]));
						  //System.out.println("qtyval " + pbfAnalyticalReportDetails.getQuarterQtyFacility() );

				 
				 
						  Double threshold = Double.valueOf(pbfAnalyticalReportDetails.getQuarterQtyIntVer())-((Double.valueOf(thresholddv.getValue())*ids[2]))/100;
						  
						  if(threshold > 0){
							  pbfAnalyticalReportDetails.setQuarterQtyIntVerThreshold(threshold);
						  }
						  
						  
						  
				//System.out.println(ou.getId() + " per " + pbfReportPeriod.getQuarterPeriod().getId()+ " thresholddv " + thresholddv.getValue());
				//pbfAnalyticalReportDetails.setQuarterQtyIntVer(threshold);
				} catch (NullPointerException npe){
				
				}
				catch (ArrayIndexOutOfBoundsException aioub){
				
				}
				
//set facility quarter values
				//month one
				DataValue dvMonthOnem = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, momoptionCombo );
				try{
					dvMonthOnem.getValue();
				}catch (NullPointerException npe){
					dvMonthOnem = new DataValue();
					dvMonthOnem.setValue("0");
				}
				
				DataValue dvMonthOnef = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mofoptionCombo );
				try{
					dvMonthOnef.getValue();
				}catch (NullPointerException npe){
					dvMonthOnef = new DataValue();
					dvMonthOnef.setValue("0");
				}
				
				DataValue dvMonthOne = new DataValue();
				int val = (Integer.valueOf(dvMonthOnem.getValue()))+(Integer.valueOf(dvMonthOnef.getValue()));
				dvMonthOne.setValue(String.valueOf(val));
				
				//month two
				DataValue dvMonthTwom = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtmoptionCombo );
				try{
					dvMonthTwom.getValue();
				}catch (NullPointerException npe){
					dvMonthTwom = new DataValue();
					dvMonthTwom.setValue("0");
				}
				
				DataValue dvMonthTwof = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtfoptionCombo );
				try{
					dvMonthTwof.getValue();
				}catch (NullPointerException npe){
					dvMonthTwof = new DataValue();
					dvMonthTwof.setValue("0");
				}
				
				DataValue dvMonthTwo = new DataValue();
				val = (Integer.valueOf(dvMonthTwom.getValue()))+(Integer.valueOf(dvMonthTwof.getValue()));
				dvMonthTwo.setValue(String.valueOf(val));

				//month three
				DataValue dvMonthThreem = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhmoptionCombo );
				try{
					dvMonthThreem.getValue();
				}catch (NullPointerException npe){
					dvMonthThreem = new DataValue();
					dvMonthThreem.setValue("0");
				}
				
				DataValue dvMonthThreef = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhfoptionCombo );
				try{
					dvMonthThreef.getValue();
				}catch (NullPointerException npe){
					dvMonthThreef = new DataValue();
					dvMonthThreef.setValue("0");
				}
				
				DataValue dvMonthThree = new DataValue();
				val = (Integer.valueOf(dvMonthThreem.getValue()))+(Integer.valueOf(dvMonthThreef.getValue()));
				dvMonthThree.setValue(String.valueOf(val));
				
				
				 v1 =0d;
				 v2 =0d;
				 v3 =0d;
				try{
					v1 = Double.valueOf(dvMonthOne.getValue());
				}catch(NullPointerException npe) {
					v1=0d;
				}
				
				try{
					v2 = Double.valueOf(dvMonthTwo.getValue());
				}catch(NullPointerException npe) {
					v2=0d;
				}
				
				try{
					v3 = Double.valueOf(dvMonthThree.getValue());
				}catch(NullPointerException npe) {
					v3=0d;
				}
				
				pbfAnalyticalReportDetails.setFacilityMonthOneValue(v1);
				pbfAnalyticalReportDetails.setFacilityMonthTwoValue(v2);
				pbfAnalyticalReportDetails.setFacilityMonthThreeValue(v3);
				
				pbfAnalyticalReportDetails.setFacilityQuarterValue(pbfAnalyticalReportDetails.getFacilityMonthOneValue()+pbfAnalyticalReportDetails.getFacilityMonthTwoValue()+pbfAnalyticalReportDetails.getFacilityMonthThreeValue());
				
				try{
					pbfAnalyticalReportDetails.setDiffQuarterValue(pbfAnalyticalReportDetails.getFacilityQuarterValue()-pbfAnalyticalReportDetails.getQuarterQtyFacility());
				}catch (NullPointerException npe){
				
				}
				
				
				
				
				
				
				
				
				
				
				//System.out.println(Double.valueOf(dvMonthOne.getValue())+Double.valueOf(dvMonthTwo.getValue())+Double.valueOf(dvMonthThree.getValue()));
//set unit price			
				pbfAnalyticalReportDetails.setUnitPrice(Double.valueOf(tokens[0]));
//set month 1 amount
				pbfAnalyticalReportDetails.setMonthOneAmount(pbfAnalyticalReportDetails.getMonthOneQtyIntVer()*pbfAnalyticalReportDetails.getUnitPrice());
//set month 2 amount
				pbfAnalyticalReportDetails.setMonthTwoAmount(pbfAnalyticalReportDetails.getMonthTwoQtyIntVer()*pbfAnalyticalReportDetails.getUnitPrice());
//set month 3 amount
				pbfAnalyticalReportDetails.setMonthThreeAmount(pbfAnalyticalReportDetails.getMonthThreeQtyIntVer()*pbfAnalyticalReportDetails.getUnitPrice());
//set quarter amount
				pbfAnalyticalReportDetails.setQuarterAmount(pbfAnalyticalReportDetails.getQuarterQtyIntVerThreshold()*pbfAnalyticalReportDetails.getUnitPrice());
//set total amount
				pbfAnalyticalReportDetails.setTotalAmount(pbfAnalyticalReportDetails.getQuarterAmount());
//set discount rate
				//BigDecimal value = formulaValues(tokens[1], ou, pbfReportPeriod.getQuarterPeriod().getId(), Double.valueOf(0));
				
				
				
				
				
				
				
				

				
				
				//here we change old style quality score calculation with new one. we get data set of quality score data elements and get value of each data element to count total score
				// and apply the tail of formulae (final value)*100/percent(300,180)
				
				//get data set of quality score
				
				DataSet qualityDataSet = null;
				
				Integer qualityValueHolder = 0;
				
				if(ou.getPhoneNumber().equalsIgnoreCase("1")){
					qualityDataSet = pde.getHhDataSetIntVer();	
				}else if(ou.getPhoneNumber().equalsIgnoreCase("0")){
					qualityDataSet = pde.getHcDataSetIntVer();
				}
				
				for(DataElement de : qualityDataSet.getDataElements()){
					
					Set<CategoryOptionCombo> decocs = de.getCategoryOptionCombos();
					Iterator it = decocs.iterator();
					while(it.hasNext()){
						CategoryOptionCombo catcombo = (CategoryOptionCombo) it.next();
					try{
					//String qval = dataValueService.getDataValue(de, pbfReportPeriod.getQuarterPeriod(), ou, catcombo).getValue();
						
						DataValue dval = dataValueService.getDataValue(de, pbfReportPeriod.getQuarterPeriod(), ou, catcombo);
								dval.getValue();
								//System.out.println("de: " + de.getId() + "catco: " + catcombo.getId() + "dv: " +dval.getValue());
						qualityValueHolder+=1;
						
						if(de.isZeroIsSignificant()){
							qualityValueHolder+=1;
						}
					}catch (NullPointerException npe){
						//System.out.println("de: " + de.getId() + "catco: " + catcombo.getId() + "NPE");
					}
					//System.out.println("dval holder: " +qualityValueHolder);
				}
				}

				
				
				//apply percentage
				
				int[] ids = new int[4];
				  int count=0;
				  Pattern percent = Pattern.compile("percent\\(([\\d,]+\\d)\\)");
				  Pattern percent2 = Pattern.compile("([\\d]+)");
				  Matcher mPercent = percent.matcher(tokens[1]);
				  while (mPercent.find()) {
				         count++;
				         //System.out.println("Match number "+count);
				         //System.out.println("start(): "+mPercent.start());
				         //System.out.println("end(): "+mPercent.end());
				         Matcher mPercent2 = percent2.matcher(mPercent.group());
				         String matched = mPercent.group();
				         //System.out.println("matched "+ matched);
				         //matched.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
				         //System.out.println("matched "+ matched);
				        int count2=0;
				         while (mPercent2.find()) {
				        	 count2++;
				        	 //System.out.println("Matched whole string "+count2+" "+ m2.group());
				        	 ids[count2-1]= Integer.valueOf(mPercent2.group());
				         }
				         //System.out.println("Groupcount "+ mPercent2.groupCount());
				         try{
				   if(ou.getPhoneNumber().equalsIgnoreCase("0")){
					   tokens[1] = tokens[1].replace(matched, String.valueOf(ids[0]));
					   qualityScore = (double) ids[0];
				  }else if(ou.getPhoneNumber().equalsIgnoreCase("1")){
					  tokens[1] = tokens[1].replace(matched, String.valueOf(ids[1]));
					  qualityScore = (double) ids[1];

					  //System.out.println("Phone number yak nest " );
				  }else{
					  tokens[1] = tokens[1].replace(matched, "0");
					  qualityScore = 0d;
				  }
				         } catch (NullPointerException npe) {
				        	 tokens[1] = tokens[1].replace(matched, "0");
				        	 System.out.println("ERROR " );
				         }
				  }
				  //System.out.println("Top Rank selected: "+ token );
				  

				
				
				String qvalToken = "("+qualityValueHolder.toString()+")"+tokens[1];
				
				Expression e = new Expression(qvalToken);

				
				
				//set discount rate
				
				
				
				//this line was replaced with the next one 
				//BigDecimal value = formulaValues(tokens[1], ou, pbfReportPeriod.getQuarterPeriod().getId(), Double.valueOf(0));
				BigDecimal value = e.eval(); 
										
				
				
				
				//get and set demographics data
				DataValue population = 
						dataValueService.getDataValue(pde.getPoulationDataElement(), pbfReportPeriod.getAnualPeriod(), ou, pde.getOptionCombo());
				try{
					pbfAnalyticalReportDetails.setPopulation(Double.valueOf(population.getValue()));
				}catch(NullPointerException npe){
					pbfAnalyticalReportDetails.setPopulation(0d);
				}
				DataValue childrenOne = 
						dataValueService.getDataValue(pde.getChildrenUnderOneDataElement(), pbfReportPeriod.getAnualPeriod(), ou, pde.getOptionCombo());
				try{
					pbfAnalyticalReportDetails.setChildrenUnderOne(Double.valueOf(childrenOne.getValue()));
				}catch(NullPointerException npe){
					pbfAnalyticalReportDetails.setChildrenUnderOne(0d);
				}
				DataValue childrenOneFive = 
						dataValueService.getDataValue(pde.getChildrenOneFiveDataElement(), pbfReportPeriod.getAnualPeriod(), ou, pde.getOptionCombo());
				try{
					pbfAnalyticalReportDetails.setChildrenOneFive(Double.valueOf(childrenOneFive.getValue()));
				}catch(NullPointerException npe){
					pbfAnalyticalReportDetails.setChildrenOneFive(0d);
				}
				DataValue pregWoman = 
						dataValueService.getDataValue(pde.getPregWomanDataElement(), pbfReportPeriod.getAnualPeriod(), ou, pde.getOptionCombo());
				try{
					pbfAnalyticalReportDetails.setPregWoman(Double.valueOf(pregWoman.getValue()));
				}catch(NullPointerException npe){
					pbfAnalyticalReportDetails.setPregWoman(0d);
				}
				DataValue reprodWoman = 
						dataValueService.getDataValue(pde.getReprodWomanDataElement(), pbfReportPeriod.getAnualPeriod(), ou, pde.getOptionCombo());
				try{
					pbfAnalyticalReportDetails.setReprodWoman(Double.valueOf(reprodWoman.getValue()));
				}catch(NullPointerException npe){
					pbfAnalyticalReportDetails.setReprodWoman(0d);
				}
				
				
				
				
				pbfAnalyticalReportDetails.setDiscountRate(value.doubleValue());
				pbfAnalyticalReportDetails.setQualityScore(qualityScore);				
				pbfAnalyticalReportDetails.setTotalQualityScore(pbfAnalyticalReportDetails.getDiscountRate()*pbfAnalyticalReportDetails.getQualityScore()/100);
//set quality score datavalue
				if(coun==0){
					DataValue dvqsh = new DataValue();
					
						dvqsh = dataValueService.getDataValue(quailityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, optionCombo);
					
					try{
						dvqsh.getValue();
						dvqsh.setValue(String.valueOf(qualityScore.intValue()));
						dvqsh.setLastUpdated(new Date());
						dvqsh.setStoredBy(user.getName());
						dataValueService.updateDataValue(dvqsh);
						
					}catch(NullPointerException npe) {
						dvqsh = new DataValue();
						
						dvqsh.setCategoryOptionCombo(optionCombo);
						dvqsh.setPeriod(pbfReportPeriod.getQuarterPeriod());
						dvqsh.setSource(ou);
						dvqsh.setDataElement(quailityDataElement);
						dvqsh.setValue(String.valueOf(qualityScore.intValue()));
						dvqsh.setCreated(new Date());
						dvqsh.setComment("Арзиш автоматӣ ҳисоб шудааст");
						dvqsh.setStoredBy(user.getName());

						dataValueService.addDataValue(dvqsh);
						
					}
//set amount for quality datavalue if selected
					
					DataValue currdv = dataValueService.getDataValue(currationDataElement, pbfReportPeriod.getQuarterPeriod(), ou, optionCombo);
					try{
						currdv.getValue();
						QuarterlyPeriodType qpt = new QuarterlyPeriodType();
						Period prevQuarter = qpt.getPreviousPeriod(pbfReportPeriod.getQuarterPeriod());
						//System.out.println(prevQuarter);
						if(currdv.getValue().equalsIgnoreCase("Ҳа")){
							DataValue prevcurrdv = null;
						try{
							prevcurrdv = dataValueService.getDataValue(quailityDataElement, prevQuarter, ou, optionCombo);
							prevcurrdv.getValue();
						} catch (NullPointerException npe){
							prevcurrdv = new DataValue();
							prevcurrdv.setValue("0");
						}
						DataValue curramntdv = dataValueService.getDataValue(currationAmountDataElement, pbfReportPeriod.getQuarterPeriod(), ou, optionCombo);
						try{
							curramntdv.getValue();
							curramntdv.setStoredBy(user.getName());
							curramntdv.setLastUpdated(new Date());
							if(Integer.valueOf(dvqsh.getValue())-Integer.valueOf(prevcurrdv.getValue())>=4){
								curramntdv.setValue("288");
							}else{
								curramntdv.setValue("0");
							}
							dataValueService.updateDataValue(curramntdv);
						} catch (NullPointerException npe){
							curramntdv = new DataValue();
							curramntdv.setDataElement(currationAmountDataElement);
							curramntdv.setSource(ou);
							curramntdv.setCategoryOptionCombo(optionCombo);
							curramntdv.setCreated(new Date());
							curramntdv.setComment("Арзишро система ҳисоб намудааст");
							curramntdv.setStoredBy(user.getName());
							if(Integer.valueOf(dvqsh.getValue())-Integer.valueOf(prevcurrdv.getValue())>=4){
								curramntdv.setValue("288");
							}else{
								curramntdv.setValue("0");
							}
							dataValueService.addDataValue(curramntdv);
						}
						
						}
						
					}catch (NullPointerException npe){
						
					}
					
					
				}
				coun++;
//set discount amount
				value = formulaValues(tokens[4], ou, pbfReportPeriod.getMonthOne().getId(), pbfAnalyticalReportDetails.getDiscountRate());
				pbfAnalyticalReportDetails.setDiscountAmount(pbfAnalyticalReportDetails.getTotalAmount()*value.doubleValue()/100);
//set total with discount
				pbfAnalyticalReportDetails.setTotalAmountWithDiscount(pbfAnalyticalReportDetails.getTotalAmount()+pbfAnalyticalReportDetails.getDiscountAmount());
//set audit amount
			
				DataValue auditDv = dataValueService.getDataValue( auditDataElement, pbfReportPeriod.getQuarterPeriod(), ou, optionCombo );
				try{
					auditDv.getValue();
				}catch (NullPointerException npe){
					auditDv = new DataValue();
					auditDv.setValue("0");
				}
				pbfAnalyticalReportDetails.setAuditAmount(Double.valueOf(auditDv.getValue()));
			
//set total plus audit amount				
				pbfAnalyticalReportDetails.setTotalAmountWithDiscountPlusAudit(pbfAnalyticalReportDetails.getTotalAmountWithDiscount()+pbfAnalyticalReportDetails.getAuditAmount());
				
//set facility amount
				value = formulaValues(tokens[5], ou, pbfReportPeriod.getMonthOne().getId(), Double.valueOf(0));
				pbfAnalyticalReportDetails.setFacilityAmount(pbfAnalyticalReportDetails.getTotalAmountWithDiscountPlusAudit()*value.doubleValue()/100);
//set salary amount				
				pbfAnalyticalReportDetails.setSalaryAmount(pbfAnalyticalReportDetails.getTotalAmountWithDiscountPlusAudit()-pbfAnalyticalReportDetails.getFacilityAmount());
//set tax % value
				value = formulaValues(tokens[2], ou, pbfReportPeriod.getMonthOne().getId(), Double.valueOf(0));
				pbfAnalyticalReportDetails.setTaxPercent(value.doubleValue());
//set social tax % value
				value = formulaValues(tokens[3], ou, pbfReportPeriod.getMonthOne().getId(), Double.valueOf(0));
				pbfAnalyticalReportDetails.setSocialPercent(value.doubleValue());
//set tax amount
				pbfAnalyticalReportDetails.setTaxAmount(pbfAnalyticalReportDetails.getSalaryAmount()*pbfAnalyticalReportDetails.getTaxPercent()/100);
//set social tax amount
				pbfAnalyticalReportDetails.setSocialTaxAmount(pbfAnalyticalReportDetails.getSalaryAmount()*pbfAnalyticalReportDetails.getSocialPercent()/100);
//set net gross
				pbfAnalyticalReportDetails.setNetGross(pbfAnalyticalReportDetails.getSalaryAmount()-pbfAnalyticalReportDetails.getTaxAmount()-pbfAnalyticalReportDetails.getSocialTaxAmount());

				
				pbfAnalyticalReportDetails.setStoredBy(currentUserService.getCurrentUsername());
				pbfAnalyticalReportDetails.setCreated(new Date());

				pbfService.saveOrUpdate(pbfAnalyticalReportDetails);

				pbfAnalyticsReport.setReportCalcDetails("calculated");
				pbfService.updatePbfAnalyticsReport(pbfAnalyticsReport);
			}

		}

		pbfAnalyticsReports = pbfService.getPbfAnalyticsReportsByUserOrgUnits(pbfReportPeriod, organisationUnits);

        memberCount = pbfAnalyticsReports.size();

        return SUCCESS;
    }
	
	
	private BigDecimal formulaValues(String token, OrganisationUnit ou, Long pdId, Double discountRate){

			//  System.out.println("Formulae parts -" + token + "-");
			  int count=0;
			  int count2=0;
			  int[] ids = new int[4];
			  String[] formulae = new String[100];
			  String matched = "";
			  Pattern pStatic = Pattern.compile("static\\(([\\d,]+\\d)\\)");
			  Pattern pStatic2 = Pattern.compile("([\\d]+)");
			  Matcher mStatic = pStatic.matcher(token);
			  while (mStatic.find()) {
			         count++;
			         //System.out.println("Match number "+count);
			         //System.out.println("start(): "+mStatic.start());
			         //System.out.println("end(): "+mStatic.end());
			         Matcher mStatic2 = pStatic2.matcher(mStatic.group());
			         matched = mStatic.group();
			         //System.out.println("matched "+ matched);
			         count2=0;
			         while (mStatic2.find()) {
			        	 count2++;
			        	 ids[count2-1]= Integer.valueOf(mStatic2.group());
			         }
			         //System.out.println("Groupcount "+ mStatic2.groupCount());

			         DataValue dav = dataValueService.getDataValue(dataElementService.getDataElement(ids[0]), periodService.getPeriod(ids[2]), organisationUnitService.getOrganisationUnit(ids[3]), categoryService.getCategoryOptionCombo(ids[1]));
			         if(dav!=null){
			        	 System.out.println("Dav Value "+ dav.getValue());

			        	 token = token.replace(matched, dav.getValue());
			   // System.out.println("Formulae string: "+ token );

			         }else{
			        	 token = token.replace(matched, "0");
						    //System.out.println("Formulae string: "+ token );
			         }					
			  }
			  
//get value of dataelement
			  ids = new int[4];
			  count=0;
			  Pattern pGet = Pattern.compile("get\\(([\\d,]+\\d)\\)");
			  Pattern pGet2 = Pattern.compile("([\\d]+)");
			  Matcher mGet = pGet.matcher(token);
			  while (mGet.find()) {
			         count++;
			         //System.out.println("Match number "+count);
			         //System.out.println("start(): "+mGet.start());
			         //System.out.println("end(): "+mGet.end());
			         Matcher mGet2 = pGet2.matcher(mGet.group());
			         matched = mGet.group();
			         //System.out.println("matched "+ matched);
			         count2=0;
			         while (mGet2.find()) {
			        	 count2++;

			        	 ids[count2-1]= Integer.valueOf(mGet2.group());
			         }
			         //System.out.println("Groupcount "+ mGet2.groupCount());
			         
			         //System.out.println(" getdv ids[0] "+ ids[0] +" ids[1]"+ ids[1] +" ou.getId() "+ ou.getId() +" pdid "+ pdId);
			         
			         DataValue dav = dataValueService.getDataValue(dataElementService.getDataElement(ids[0]), periodService.getPeriod(pdId), ou, categoryService.getCategoryOptionCombo(ids[1]));

			         if(dav!=null){
			        	 //System.out.println("Dav Value "+ dav.getValue());
			        	 token = token.replace(matched, dav.getValue());
			    //System.out.println("Formulae string: "+ token );

			         }else{
			        	 token = token.replace(matched, "0");
					//	    System.out.println("Formulae string: "+ token );
			         }
			  }
			  
			  
			  ids = new int[4];
			  count=0;
			  Pattern pGetO = Pattern.compile("geto\\(([\\d,]+\\d)\\)");
			  Pattern pGetO2 = Pattern.compile("([\\d]+)");
			  Matcher mGetO = pGetO.matcher(token);
			  while (mGetO.find()) {
			         count++;
			         //System.out.println("Match number "+count);
			         //System.out.println("start(): "+mGetO.start());
			         //System.out.println("end(): "+mGetO.end());
			         Matcher mGetO2 = pGetO2.matcher(mGetO.group());
			         matched = mGetO.group();
			         //System.out.println("matched "+ matched);
			         //matched.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
			         //System.out.println("matched "+ matched);
			         count2=0;
			         while (mGetO2.find()) {
			        	 count2++;
			        	 //System.out.println("Matched whole string "+count2+" "+ m2.group());
			        	 ids[count2-1]= Integer.valueOf(mGetO2.group());
			         }
			         //System.out.println("Groupcount "+ mGetO2.groupCount());

			   if(ou.getPhoneNumber()== null || !ou.getPhoneNumber().equalsIgnoreCase("1")){
			         DataValue dav = dataValueService.getDataValue(dataElementService.getDataElement(ids[0]), periodService.getPeriod(pdId), ou, categoryService.getCategoryOptionCombo(ids[1]));
			         if(dav!=null){
			        	// System.out.println("Dav Value "+ dav.getValue());

			    //System.out.println("Matched whole string "+ m.group());
			        	 token = token.replace(matched, dav.getValue());
			   // System.out.println("Formulae string: "+ token );

			         }else{
			        	 token = token.replace(matched, "0");
						   // System.out.println("Formulae string: "+ token );
			         }
			         
			  }else{
		        	 token = token.replace(matched, "0");
					   // System.out.println("Formulae string: "+ token );
			  }
			  }

			  ids = new int[4];
			  count=0;
			  Pattern percent = Pattern.compile("percent\\(([\\d,]+\\d)\\)");
			  Pattern percent2 = Pattern.compile("([\\d]+)");
			  Matcher mPercent = percent.matcher(token);
			  while (mPercent.find()) {
			         count++;
			         //System.out.println("Match number "+count);
			         //System.out.println("start(): "+mPercent.start());
			         //System.out.println("end(): "+mPercent.end());
			         Matcher mPercent2 = percent2.matcher(mPercent.group());
			         matched = mPercent.group();
			         //System.out.println("matched "+ matched);
			         //matched.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
			         //System.out.println("matched "+ matched);
			         count2=0;
			         while (mPercent2.find()) {
			        	 count2++;
			        	 //System.out.println("Matched whole string "+count2+" "+ m2.group());
			        	 ids[count2-1]= Integer.valueOf(mPercent2.group());
			         }
			         //System.out.println("Groupcount "+ mPercent2.groupCount());
			         try{
			   if(ou.getPhoneNumber().equalsIgnoreCase("0")){
				   token = token.replace(matched, String.valueOf(ids[0]));
				   qualityScore = (double) ids[0];
			  }else if(ou.getPhoneNumber().equalsIgnoreCase("1")){
				  token = token.replace(matched, String.valueOf(ids[1]));
				  qualityScore = (double) ids[1];

				  //System.out.println("Phone number yak nest " );
			  }else{
				  token = token.replace(matched, "0");
				  qualityScore = 0d;
			  }
			         } catch (NullPointerException npe) {
			        	 token = token.replace(matched, "0");
			        	 System.out.println("ERROR " );
			         }
			  }
			  //System.out.println("Top Rank selected: "+ token );
			  
			  ids = new int[4];
			  count=0;
			  Pattern minmax = Pattern.compile("minmax\\([\\d+,{1}]+-?\\d+\\)");
			  Pattern minmax2 = Pattern.compile("([-?\\d]+)");
			  Matcher mMinmax = minmax.matcher(token);
			  while (mMinmax.find()) {
			         count++;
			         //System.out.println("Match number "+count);
			         //System.out.println("start(): "+mMinmax.start());
			         //System.out.println("end(): "+mMinmax.end());
			         Matcher mMinmax2 = minmax2.matcher(mMinmax.group());
			         matched = mMinmax.group();
			         //System.out.println("matched "+ matched);
			         //matched.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
			         //System.out.println("matched "+ matched);
			         count2=0;
			         while (mMinmax2.find()) {
			        	 count2++;

			        	 //System.out.println("Matched whole string "+count2+" "+ m2.group());
			        	 ids[count2-1]= Integer.valueOf(mMinmax2.group());
			         }
			         //System.out.println("Groupcount "+ mMinmax2.groupCount());

			   if(discountRate>= ids[0] && discountRate<ids[1]){
				   token = token.replace(matched, String.valueOf(ids[2]));
			         
			  }else{
				  token = token.replace(matched, String.valueOf("0"));
					    //System.out.println("Formulae string: "+ token );
			  
			  }
			  }

			  ids = new int[4];
			  count=0;
			  Pattern fac = Pattern.compile("fac\\(\\d+\\)");
			  Pattern fac2 = Pattern.compile("([-?\\d]+)");
			  Matcher mFac = fac.matcher(token);
			  while (mFac.find()) {
			         count++;
			         //System.out.println("Match number "+count);
			         //System.out.println("start(): "+mFac.start());
			         //System.out.println("end(): "+mFac.end());
			         Matcher mFac2 = fac2.matcher(mFac.group());
			         matched = mFac.group();
			         //System.out.println("matched "+ matched);
			         //matched.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
			         //System.out.println("matched "+ matched);
			         count2=0;
			         while (mFac2.find()) {
			        	 count2++;

			        	 //System.out.println("Matched whole string "+count2+" "+ m2.group());
			        	 ids[count2-1]= Integer.valueOf(mFac2.group());
			         }
			         //System.out.println("Groupcount "+ mFac2.groupCount());

				   token = token.replace(matched, String.valueOf(ids[0]));
			  }
			  
				Expression e = new Expression(token);

				BigDecimal finalValue = e.eval();
				
				//System.out.println("formula "+ token +" value: " + finalValue); 				  

		return finalValue;
	}
	
	
 private Double getThresholdValue(String token,  Long pdId, Long ouId, int val){
	 //Get values with threshould variance
			
	 int[] ids = new int[4];
			int  count=0;
			  Pattern pGetT = Pattern.compile("getT\\(([\\d,]+\\d)\\)");
			  Pattern pGetT2 = Pattern.compile("([\\d]+)");
			  Matcher mGetT = pGetT.matcher(token);
			  String matched = "";
			  String finalValue = "0"; 
			  int count2=0;
			  while (mGetT.find()) {
			         count++;
		
			         Matcher mGetT2 = pGetT2.matcher(mGetT.group());
			         matched = mGetT.group();
			         //System.out.println("matched "+ matched);
			         count2=0;
			         while (mGetT2.find()) {
			        	 count2++;

			        	 ids[count2-1]= Integer.valueOf(mGetT2.group());
			         }
			  }
			  DataValue thresholddv = dataValueService.getDataValue(dataElementService.getDataElement(ids[0]), periodService.getPeriod(pdId), organisationUnitService.getOrganisationUnit(ouId), categoryService.getCategoryOptionCombo(ids[1]));
			  
			  try{			 
			  thresholdValue = Double.valueOf(thresholddv.getValue());
			  basisValue = Double.valueOf(ids[2]);
			  
			  // System.out.println("Threshold value: "+thresholddv.getValue());

			  Double threshold = (Double.valueOf(thresholddv.getValue())*ids[2])/100;
			
			  if(threshold<Double.valueOf(val)){
				  return Double.valueOf(val)-threshold; 
			  }
			  }catch (NullPointerException npe){
				  System.out.println("null in calcs");
			  }
			  
			  return  0d;
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