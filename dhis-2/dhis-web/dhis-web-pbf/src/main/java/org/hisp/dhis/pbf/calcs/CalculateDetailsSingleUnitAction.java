package org.hisp.dhis.pbf.calcs;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hisp.dhis.dataapproval.DataApproval;
import org.hisp.dhis.dataapproval.DataApprovalLevel;
import org.hisp.dhis.dataapproval.DataApprovalLevelService;
import org.hisp.dhis.dataapproval.DataApprovalService;
import org.hisp.dhis.dataapproval.DataApprovalState;
import org.hisp.dhis.dataapproval.DataApprovalStatus;
import org.hisp.dhis.dataapproval.DataApprovalWorkflow;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.category.CategoryService;
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
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.math.Expression;
import org.hisp.dhis.pbf.model.PbfCalculation;
import org.hisp.dhis.pbf.model.PbfDataElement;
import org.hisp.dhis.pbf.model.PbfReport;
import org.hisp.dhis.pbf.model.PbfReportPeriod;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.period.QuarterlyPeriodType;
import org.hisp.dhis.user.CurrentUserService;
import org.hisp.dhis.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

/**
 * @author Murodillo Latifov Abdusamadovich
 */
public class CalculateDetailsSingleUnitAction
    implements Action
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    @Autowired
    private DataApprovalService dataApprovalService;

    @Autowired
    private DataApprovalLevelService dataApprovalLevelService;

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
	
	private String countryName;
	private String provinceName;
	private String districtName;
	private String facilityName;
	
	private String countryNameEn;
	private String provinceNameEn;
	private String districtNameEn;
	private String facilityNameEn;
	
	private Double qualityScore = 0d;
	private BigDecimal discountRate = null;
	private DataValue curramntdv = null;
	
	private DataValue prevQualdv;
	
	private int currPer;
	
	CategoryOptionCombo momdeoc = null;
	CategoryOptionCombo mofdeoc = null;
	
	CategoryOptionCombo mtmdeoc = null;
	CategoryOptionCombo mtfdeoc = null;
	
	CategoryOptionCombo mhmdeoc = null;
	CategoryOptionCombo mhfdeoc = null;
	
	
	
	DataElement auditDataElement = null;
	DataElement quailityDataElement = null;
	DataElement currationAmountDataElement = null;
	DataElement currationDataElement = null;
	
	CategoryOptionCombo deoc = null;
	
	
	public void setDataApprovalService(DataApprovalService dataApprovalService) {
		this.dataApprovalService = dataApprovalService;
	}

	public void setDataApprovalLevelService(DataApprovalLevelService dataApprovalLevelService) {
		this.dataApprovalLevelService = dataApprovalLevelService;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setCountryNameEn(String countryNameEn) {
		this.countryNameEn = countryNameEn;
	}

	public void setProvinceNameEn(String provinceNameEn) {
		this.provinceNameEn = provinceNameEn;
	}

	public void setDistrictNameEn(String districtNameEn) {
		this.districtNameEn = districtNameEn;
	}

	public void setFacilityNameEn(String facilityNameEn) {
		this.facilityNameEn = facilityNameEn;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	public void setCurramntdv(DataValue curramntdv) {
		this.curramntdv = curramntdv;
	}

	public void setMomdeoc(CategoryOptionCombo momdeoc) {
		this.momdeoc = momdeoc;
	}

	public void setMofdeoc(CategoryOptionCombo mofdeoc) {
		this.mofdeoc = mofdeoc;
	}

	public void setMtmdeoc(CategoryOptionCombo mtmdeoc) {
		this.mtmdeoc = mtmdeoc;
	}

	public void setMtfdeoc(CategoryOptionCombo mtfdeoc) {
		this.mtfdeoc = mtfdeoc;
	}

	public void setMhmdeoc(CategoryOptionCombo mhmdeoc) {
		this.mhmdeoc = mhmdeoc;
	}

	public void setMhfdeoc(CategoryOptionCombo mhfdeoc) {
		this.mhfdeoc = mhfdeoc;
	}

	public void setAuditDataElement(DataElement auditDataElement) {
		this.auditDataElement = auditDataElement;
	}

	public void setQuailityDataElement(DataElement quailityDataElement) {
		this.quailityDataElement = quailityDataElement;
	}

	public void setCurrationAmountDataElement(DataElement currationAmountDataElement) {
		this.currationAmountDataElement = currationAmountDataElement;
	}

	public void setCurrationDataElement(DataElement currationDataElement) {
		this.currationDataElement = currationDataElement;
	}

	public void setDeoc(CategoryOptionCombo deoc) {
		this.deoc = deoc;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public void setPbfReports(Collection<PbfReport> pbfReports) {
		this.pbfReports = pbfReports;
	}

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

	private Collection<PbfReport> pbfReports;

	public Collection<PbfReport> getPbfReports() {
		return pbfReports;
	}

	public void setPbfReports(List<PbfReport> pbfReports) {
		this.pbfReports = pbfReports;
	}

	
	private BigDecimal formulaValues(String token, OrganisationUnit ou, long pdId, Double discountRate){

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

			         DataValue dav = dataValueService.getDataValue(dataElementService.getDataElement(ids[0]), periodService.getPeriod(ids[3]), organisationUnitService.getOrganisationUnit(ids[2]), categoryService.getCategoryOptionCombo(ids[1]));
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
	
	
 private Double getThresholdValue(String token,  long pdId, long ouId, int val){
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
			  }catch(NullPointerException npe){
				  thresholdValue=0d;
			  }
			  
			  basisValue = Double.valueOf(ids[2]);
			  
			  // System.out.println("Threshold value: "+thresholddv.getValue());
			  try{
			  Double threshold = (Double.valueOf(thresholddv.getValue())*ids[2])/100;
			
			  if(threshold<Double.valueOf(val)){
				  return Double.valueOf(val)-threshold; 
			  }
			  }catch (NullPointerException npe){
				 // System.out.println("null in calcs threshold");
			  }
			  
			  return  0d;
 }
	
	// -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

	public String execute()
    {
		User user = currentUserService.getCurrentUser();
//System.out.println(ouId);
		Collection<OrganisationUnit> organisationUnits = new HashSet<OrganisationUnit>();

		OrganisationUnit oun = organisationUnitService.getOrganisationUnit(ouId);

		organisationUnits.add(oun);

		PbfReportPeriod pbfReportPeriod = pbfService.getPbfReportPeriod(Long.valueOf(repId));

		List<PbfDataElement> dataElements = pbfService.getAllPbfDataElements();

		PbfCalculation pbfCalculation = null;
		int coun = 0;

		for(OrganisationUnit ou: organisationUnits){
			PbfReport pbfReport = pbfService.getReport(pbfReportPeriod, ou, user);

			currPer = 0;
			
			for(PbfDataElement pde: dataElements){
				String[] tokens = null;
				//parse formulae			
				/*
				 * if(ou.getPhoneNumber().equalsIgnoreCase("0")){ tokens =
				 * pde.getCalculationFormulaPhc().split("\\\\\\\\"); }else
				 * if(ou.getPhoneNumber().equalsIgnoreCase("1")){ tokens =
				 * pde.getCalculationFormulaRphc().split("\\\\\\\\"); }
				 */
				if(ou.getComment().equalsIgnoreCase("N")){
					tokens = pde.getCalculationFormulaPhc().split("\\\\\\\\");	
				}else {
					 tokens = pde.getCalculationFormulaRphc().split("\\\\\\\\");
				}

				DataElement dataElement = pde.getIntVerDataElement();
				DataElement facilityDataElement = pde.getFacilityDataElement();

				// start data approval
				if(coun==0){
		
					unitName(ou);
					
				//Locking internal verification quality data values
				
					DataApprovalLevel dataApprovalLevel = dataApprovalLevelService.getHighestDataApprovalLevel( ou );
					Period period = pbfReportPeriod.getQuarterPeriod();
					
/*					DataSet dataSet = null;
					
				if(ou.getPhoneNumber().equalsIgnoreCase("1")){
					dataSet = pde.getHhDataSetIntVer();	
				}else if(ou.getPhoneNumber().equalsIgnoreCase("0")){
					dataSet = pde.getHcDataSetIntVer();
				}
				
				

				

		        List<DataApproval> dataApprovalList = getApprovalsAsList( dataApprovalLevel, dataSet,
		            period, ou, false, new Date(), user );

		        DataApprovalWorkflow workflow = dataSet.getWorkflow();
		        
		        try{
		        DataApprovalStatus das = dataApprovalService.getDataApprovalStatus(workflow, period, ou, null);
		       
		        DataApprovalState dass = das.getState();
		        
		        if(!dass.isApproved()){
		        dataApprovalService.approveData( dataApprovalList );
		        }
		        }catch (NullPointerException npe){
		        	System.out.println("dude no approval");
		        }*/

		        
		        //locking internal verification quantity data values
		        
		        DataSet idataSet = null;

				idataSet = pde.getIntVerQtyDataSet();	

		        //List<DataApproval> idataApprovalList = getApprovalsAsList( dataApprovalLevel, idataSet,
		        //    iperiod, ou, false, new Date(), user ); 

		        List<DataApproval> approvals = new ArrayList<>();

		        CategoryOptionCombo combo = categoryService.getDefaultCategoryOptionCombo();
		        period = periodService.reloadPeriod( period );
		        DataApprovalWorkflow iworkflow = idataSet.getWorkflow();
		        approvals.add( new DataApproval( dataApprovalLevel, iworkflow, period, ou, combo, false, new Date(), user ) );
		        
		        DataApprovalStatus idas = dataApprovalService.getDataApprovalStatus(iworkflow, period, ou, null);
		        
		        DataApprovalState idass = idas.getState();
		        
		        if(!idass.isApproved()){
		        dataApprovalService.approveData( approvals );
		        }
		        
		        //locking facility quantity data values
		        
		        DataSet fdataSet = null;

				fdataSet = pde.getFacQtyDataSet();	

		        List<DataApproval> fdataApprovalList = getApprovalsAsList( dataApprovalLevel, fdataSet,
		            period, ou, false, new Date(), user ); 

		        DataApprovalWorkflow fworkflow = fdataSet.getWorkflow();
		        DataApprovalStatus fdas = dataApprovalService.getDataApprovalStatus(fworkflow, period, ou, null);
		        
		        DataApprovalState fdass = fdas.getState();
		        
		        if(!fdass.isApproved()){
		        dataApprovalService.approveData( fdataApprovalList );
		        }
		        
//locking internal verification quality data values
		        
/*		        DataSet intverqdataSet = null;


				if(ou.getPhoneNumber().equalsIgnoreCase("1")){
					intverqdataSet = pde.getHhDataSetIntVer();	
				}else if(ou.getPhoneNumber().equalsIgnoreCase("0")){
					intverqdataSet = pde.getHcDataSetIntVer();
				}	

				Period intverqperiod = pbfReportPeriod.getQuarterPeriod();

		        List<DataApproval> intverqdataApprovalList = getApprovalsAsList( dataApprovalLevel, intverqdataSet,
		        		intverqperiod, ou, false, new Date(), user ); 

		        DataApprovalWorkflow intverqworkflow = intverqdataSet.getWorkflow();
		        DataApprovalStatus intverqdas = dataApprovalService.getDataApprovalStatus(intverqworkflow, period, ou, null);
		        
		        DataApprovalState intverqdass = intverqdas.getState();
		        
		        if(!intverqdass.isApproved()){
		        dataApprovalService.approveData( intverqdataApprovalList );
		        }*/
		        
		        
		        
		        //things to be done once before this curve
				}
				
		        //end data approval
				
				if(coun==0){
				
				momdeoc = pde.getOptionComboMom();
				mofdeoc = pde.getOptionComboMof();
				
				mtmdeoc = pde.getOptionComboMtm();
				mtfdeoc = pde.getOptionComboMtf();
				
				mhmdeoc = pde.getOptionComboMhm();
				mhfdeoc = pde.getOptionComboMhf();
				
				
				
				auditDataElement = pde.getAuditDataElement();
				quailityDataElement = pde.getQualityScoreDataElement();
				currationAmountDataElement = pde.getCurrationAmountDataElement();
				currationDataElement = pde.getCurrationDataElement();
				
				deoc = pde.getOptionCombo();
				}
				
//get or create pbfcalulation object
				pbfCalculation = pbfService.getPbfCalculationByPrimaryKeys(dataElement, ou, pbfReport.getReportPeriod());
				if(pbfCalculation==null)
					pbfCalculation = new PbfCalculation();
				pbfCalculation.setDataElement(dataElement);
				pbfCalculation.setIndicatorName(dataElement.getFormName());
				pbfCalculation.setIndicatorNameEn(dataElement.getDescription());
				pbfCalculation.setSortOrder(pde.getSortOrder());
				//pbfCalculation.setOptionCombo(deoc);
				pbfCalculation.setOrgUnit(ou);
				pbfCalculation.setPeriodQuarter(pbfReport.getReportPeriod().getQuarterPeriod());
				
				
				
				pbfCalculation.setFacilityName(facilityName);
				pbfCalculation.setDistrictName(districtName);
				pbfCalculation.setProvinceName(provinceName);
				pbfCalculation.setCountryName(countryName);

				
				pbfCalculation.setFacilityNameEn(facilityNameEn);
				pbfCalculation.setDistrictNameEn(districtNameEn);
				pbfCalculation.setProvinceNameEn(provinceNameEn);
				pbfCalculation.setCountryNameEn(countryNameEn);

				
				pbfCalculation.setThresholdValue(0d);
				pbfCalculation.setBasisValue(0d);
				pbfCalculation.setCurrationAmount(0d);
				
//set month 1 value				
				pbfCalculation.setMonthOne(pbfReportPeriod.getMonthOne().getDisplayName());
				pbfCalculation.setPeriodOne(pbfReportPeriod.getMonthOne());
				//DataValue dv = dataValueService.getDataValue(ou, dataElement, pbfReportPeriod.getMonthOne(), deoc);
				DataValue dvmom = dataValueService.getDataValue( dataElement, pbfReportPeriod.getQuarterPeriod(), ou, momdeoc );
				DataValue dvmof = dataValueService.getDataValue( dataElement, pbfReportPeriod.getQuarterPeriod(), ou, mofdeoc );
				
				int momv = 0;
				try{
					momv = Integer.valueOf(dvmom.getValue());
				}catch (NullPointerException npe){
					momv =0;
				}
				
				int mofv = 0;
				try{
					mofv = Integer.valueOf(dvmof.getValue());
				}catch (NullPointerException npe){
					mofv =0;
				}
				
				int mov = momv+mofv;
				
				//System.out.println(" Month one value " + mov);
				
				//System.out.println( "service value de: " +dataElement.getId() + " cc: " + deoc.getId() + " mid: " +pbfReportPeriod.getMonthOne().getId() +" ouid: "+ ou.getId());
				
				try{
				
				tokens[6].toString();
				
Double finalValue = getThresholdValue(tokens[6], pbfReportPeriod.getQuarterPeriod().getId(), ou.getId(), mov );
				
		pbfCalculation.setMonthOneValueOrig(Double.valueOf(mov));
				pbfCalculation.setMonthOneValue(finalValue);
				try{
				if(pbfCalculation.getThresholdValue()<thresholdValue){
				pbfCalculation.setThresholdValue(thresholdValue);
				}
				}catch(NullPointerException npe){
					pbfCalculation.setThresholdValue(0d);
				}
				
				try{	
				
				if(pbfCalculation.getBasisValue()<basisValue){
				pbfCalculation.setBasisValue(basisValue);
				}
				}catch(NullPointerException npe){
					pbfCalculation.setBasisValue(0d);
				}
				
				}catch (NullPointerException npe){
					if(mov!=0){
					//System.out.println("DV value 1 catch null " + dv.getValue());
					npe.printStackTrace();
					pbfCalculation.setMonthOneValueOrig(Double.valueOf(mov));
					pbfCalculation.setMonthOneValue(Double.valueOf(mov));
					}else{
						pbfCalculation.setMonthOneValueOrig(0d);
						pbfCalculation.setMonthOneValue(0d);
					}
				
				}catch (ArrayIndexOutOfBoundsException aiobe){
					if(mov!=0){
					//System.out.println("DV value 2 null " + dv.getValue());
					pbfCalculation.setMonthOneValueOrig(Double.valueOf(mov));
					pbfCalculation.setMonthOneValue(Double.valueOf(mov));
					}else{
						pbfCalculation.setMonthOneValueOrig(0d);
						pbfCalculation.setMonthOneValue(0d);
					}
				}
				
				//mov=0;
				
//set month 2 value
				pbfCalculation.setMonthTwo(pbfReportPeriod.getMonthTwo().getDisplayName());
				pbfCalculation.setPeriodTwo(pbfReportPeriod.getMonthTwo());
				//dv = dataValueService.getDataValue(ou, dataElement, pbfReportPeriod.getMonthTwo(), deoc);
				//dv = dataValueService.getDataValue( dataElement.getId(), pbfReportPeriod.getMonthTwo().getId(), ou.getId(), deoc.getId() );
				
				DataValue dvmtm = dataValueService.getDataValue( dataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtmdeoc );
				DataValue dvmtf = dataValueService.getDataValue( dataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtfdeoc );
				
				int mtmv = 0;
				try{
					mtmv = Integer.valueOf(dvmtm.getValue());
				}catch (NullPointerException npe){
					mtmv =0;
				}
				
				int mtfv = 0;
				try{
					mtfv = Integer.valueOf(dvmtf.getValue());
				}catch (NullPointerException npe){
					mtfv =0;
				}
				
				int mtv = mtmv+mtfv;
				
				//System.out.println(" Month two value " + mtv);
				
				try{
				
				tokens[6].toString();
				
Double finalValue = getThresholdValue(tokens[6], pbfReportPeriod.getQuarterPeriod().getId(), ou.getId(), mtv );

pbfCalculation.setMonthTwoValueOrig(Double.valueOf(mtv));
				pbfCalculation.setMonthTwoValue(finalValue);
				try{
				if(pbfCalculation.getThresholdValue()<thresholdValue){
				pbfCalculation.setThresholdValue(thresholdValue);
				}
				}catch(NullPointerException npe){
					pbfCalculation.setThresholdValue(0d);
				}
				
				try{	
				
				if(pbfCalculation.getBasisValue()<basisValue){
				pbfCalculation.setBasisValue(basisValue);
				}
				}catch(NullPointerException npe){
					pbfCalculation.setBasisValue(0d);
				}
				
				}catch (NullPointerException npe){
					if(mtv!=0){
					//System.out.println("DV value 3 catch null " + dv.getValue());
					pbfCalculation.setMonthTwoValueOrig(Double.valueOf(mtv));
					pbfCalculation.setMonthTwoValue(Double.valueOf(mtv));
					}else{
						pbfCalculation.setMonthTwoValueOrig(0d);
						pbfCalculation.setMonthTwoValue(0d);
					}
	
				}catch (ArrayIndexOutOfBoundsException aiobe){
					if(mtv!=0){
					//System.out.println("DV value 4 null " + dv.getValue());
					pbfCalculation.setMonthTwoValueOrig(Double.valueOf(mtv));
					pbfCalculation.setMonthTwoValue(Double.valueOf(mtv));
					}else{
						pbfCalculation.setMonthTwoValueOrig(0d);
						pbfCalculation.setMonthTwoValue(0d);
					}
				}
				
				
				
//set month 3 value
				pbfCalculation.setMonthThree(pbfReportPeriod.getMonthThree().getDisplayName());
				pbfCalculation.setPeriodThree(pbfReportPeriod.getMonthThree());
				//dv = dataValueService.getDataValue(ou, dataElement, pbfReportPeriod.getMonthThree(), deoc);
				//dv = dataValueService.getDataValue( dataElement.getId(), pbfReportPeriod.getMonthThree().getId(), ou.getId(), deoc.getId() );
				
				DataValue dvmhm = dataValueService.getDataValue( dataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhmdeoc );
				DataValue dvmhf = dataValueService.getDataValue( dataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhfdeoc );
				
				int mhmv = 0;
				try{
					mhmv = Integer.valueOf(dvmhm.getValue());
				}catch (NullPointerException npe){
					mhmv =0;
				}
				
				int mhfv = 0;
				try{
					mhfv = Integer.valueOf(dvmhf.getValue());
				}catch (NullPointerException npe){
					mhfv =0;
				}
				
				int mhv = mhmv+mhfv;
				
				//System.out.println(" Month three value " + mhv);
				
				try{
				
				tokens[6].toString();
				
Double finalValue = getThresholdValue(tokens[6], pbfReportPeriod.getQuarterPeriod().getId(), ou.getId(), mhv );
				
pbfCalculation.setMonthThreeValueOrig(Double.valueOf(mhv));
				pbfCalculation.setMonthThreeValue(finalValue);
				try{
					if(pbfCalculation.getThresholdValue()<thresholdValue){
					pbfCalculation.setThresholdValue(thresholdValue);
					}
					}catch(NullPointerException npe){
						pbfCalculation.setThresholdValue(0d);
					}
					
					try{	
					
					if(pbfCalculation.getBasisValue()<basisValue){
					pbfCalculation.setBasisValue(basisValue);
					}
					}catch(NullPointerException npe){
						pbfCalculation.setBasisValue(0d);
					}
				
				}catch (NullPointerException npe){
					if(mhv!=0){
					//System.out.println("DV value 5 catch null " + dv.getValue());
					pbfCalculation.setMonthThreeValueOrig(Double.valueOf(mhv));
					pbfCalculation.setMonthThreeValue(Double.valueOf(mhv));
					}else{
						pbfCalculation.setMonthThreeValueOrig(0d);
						pbfCalculation.setMonthThreeValue(0d);
					}
				
				}catch (ArrayIndexOutOfBoundsException aiobe){
					if(mhv!=0){
					//System.out.println("DV value 6 null " + dv.getValue());
					pbfCalculation.setMonthThreeValueOrig(Double.valueOf(mhv));
					pbfCalculation.setMonthThreeValue(Double.valueOf(mhv));
					}else{
						pbfCalculation.setMonthThreeValueOrig(0d);
						pbfCalculation.setMonthThreeValue(0d);
					}
				}
				
//set quarter value
				Double v1 =0d;
				Double v2 =0d;
				Double v3 =0d;
				try{
					v1 = Double.valueOf(pbfCalculation.getMonthOneValueOrig());
				}catch(NullPointerException npe) {
					v1=0d;
				}
				
				try{
					v2 = Double.valueOf(pbfCalculation.getMonthTwoValueOrig());
				}catch(NullPointerException npe) {
					v2=0d;
				}
				
				try{
					v3 = Double.valueOf(pbfCalculation.getMonthThreeValueOrig());
				}catch(NullPointerException npe) {
					v3=0d;
				}
				
				pbfCalculation.setQuarterValueOrig(v1+v2+v3);
				
//set quarter value with threshold applied
				//works while calculating threshold for each month
				pbfCalculation.setQuarterValue(pbfCalculation.getMonthOneValue()+pbfCalculation.getMonthTwoValue()+pbfCalculation.getMonthThreeValue());
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
						  //System.out.println("qtyval " + pbfCalculation.getQuarterValueOrig() );

						  Double threshold = Double.valueOf(pbfCalculation.getQuarterValueOrig())-((Double.valueOf(thresholddv.getValue())*ids[2]))/100;
						  
						  if(threshold < 0){
							  threshold = 0d;
						  }
						  
				//System.out.println(ou.getId() + " per " + pbfReportPeriod.getQuarterPeriod().getId()+ " thresholddv " + thresholddv.getValue());
				pbfCalculation.setQuarterValue(threshold);
				} catch (NullPointerException npe){
				
				}
				catch (ArrayIndexOutOfBoundsException aioub){
				
				}
				
//set facility quarter values
				//month one
				DataValue dvMonthOnem = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, momdeoc );
				try{
					dvMonthOnem.getValue();
				}catch (NullPointerException npe){
					dvMonthOnem = new DataValue();
					dvMonthOnem.setValue("0");
				}
				
				DataValue dvMonthOnef = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mofdeoc );
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
				DataValue dvMonthTwom = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtmdeoc );
				try{
					dvMonthTwom.getValue();
				}catch (NullPointerException npe){
					dvMonthTwom = new DataValue();
					dvMonthTwom.setValue("0");
				}
				
				DataValue dvMonthTwof = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mtfdeoc );
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
				DataValue dvMonthThreem = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhmdeoc );
				try{
					dvMonthThreem.getValue();
				}catch (NullPointerException npe){
					dvMonthThreem = new DataValue();
					dvMonthThreem.setValue("0");
				}
				
				DataValue dvMonthThreef = dataValueService.getDataValue( facilityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, mhfdeoc );
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
				
				pbfCalculation.setFacilityMonthOneValue(v1);
				pbfCalculation.setFacilityMonthTwoValue(v2);
				pbfCalculation.setFacilityMonthThreeValue(v3);
				
				pbfCalculation.setFacilityQuarterValue(pbfCalculation.getFacilityMonthOneValue()+pbfCalculation.getFacilityMonthTwoValue()+pbfCalculation.getFacilityMonthThreeValue());
				
				try{
					pbfCalculation.setDiffQuarterValue(pbfCalculation.getFacilityQuarterValue()-pbfCalculation.getQuarterValueOrig());
				}catch (NullPointerException npe){
				
				}
				
				//System.out.println(Double.valueOf(dvMonthOne.getValue())+Double.valueOf(dvMonthTwo.getValue())+Double.valueOf(dvMonthThree.getValue()));
//set unit price			
				pbfCalculation.setUnitPrice(Double.valueOf(tokens[0]));
//set month 1 amount
				pbfCalculation.setMonthOneAmount(pbfCalculation.getMonthOneValue()*pbfCalculation.getUnitPrice());
//set month 2 amount
				pbfCalculation.setMonthTwoAmount(pbfCalculation.getMonthTwoValue()*pbfCalculation.getUnitPrice());
//set month 3 amount
				pbfCalculation.setMonthThreeAmount(pbfCalculation.getMonthThreeValue()*pbfCalculation.getUnitPrice());
//set quarter amount
				pbfCalculation.setQuarterAmount(pbfCalculation.getQuarterValue()*pbfCalculation.getUnitPrice());
//set total amount
				pbfCalculation.setTotalAmount(pbfCalculation.getQuarterAmount());

				
				if(coun==0){
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
								//dval.getValue();
						if(dval.getValue().equalsIgnoreCase("true")){		
								//System.out.println("de: " + de.getId() + "catco: " + catcombo.getId() + "dv: " +dval.getValue());
						qualityValueHolder+=1;
						
						if(de.isZeroIsSignificant()){
							qualityValueHolder+=1;
						}
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
				discountRate = e.eval(); 
				}

				pbfCalculation.setDiscountRate(discountRate.doubleValue());
				pbfCalculation.setQualityScore(qualityScore);				
				pbfCalculation.setTotalQualityScore(pbfCalculation.getDiscountRate()*pbfCalculation.getQualityScore()/100);
				
				
//set quality score datavalue
				if(coun==0){
					DataValue dvqsh = new DataValue();
					
						dvqsh = dataValueService.getDataValue(quailityDataElement, pbfReportPeriod.getQuarterPeriod(), ou, deoc);
					
					try{
						dvqsh.getValue();
						dvqsh.setValue(pbfCalculation.getTotalQualityScore().toString());
						dvqsh.setLastUpdated(new Date());
						dvqsh.setStoredBy(user.getName());
						dataValueService.updateDataValue(dvqsh);
						
					}catch(NullPointerException npe) {
						dvqsh = new DataValue();
						
						dvqsh.setCategoryOptionCombo(deoc);
						dvqsh.setAttributeOptionCombo(categoryService.getDefaultCategoryOptionCombo());
						dvqsh.setPeriod(pbfReportPeriod.getQuarterPeriod());
						dvqsh.setSource(ou);
						dvqsh.setDataElement(quailityDataElement);
						dvqsh.setValue(pbfCalculation.getTotalQualityScore().toString());
						dvqsh.setCreated(new Date());
						dvqsh.setComment("Арзиш автоматӣ ҳисоб шудааст");
						dvqsh.setStoredBy(user.getName());

						dataValueService.addDataValue(dvqsh);
						
					}
//set monitoring amount for quality datavalue if dhc made control at least once
					
					DataValue currdv = dataValueService.getDataValue(currationDataElement, pbfReportPeriod.getQuarterPeriod(), ou, deoc);
					try{
						
						
						DataValue curramdv = dataValueService.getDataValue(currationAmountDataElement, pbfReportPeriod.getQuarterPeriod(), ou, deoc);
						
						//curramntdv = new DataValue();
						curramdv.setDataElement(currationAmountDataElement);
						curramdv.setPeriod(pbfReportPeriod.getQuarterPeriod());
						curramdv.setSource(ou);
						curramdv.setCategoryOptionCombo(deoc);
						curramdv.setAttributeOptionCombo(categoryService.getDefaultCategoryOptionCombo());
						curramdv.setCreated(new Date());
						curramdv.setComment("Арзишро система ҳисоб намудааст");
						curramdv.setStoredBy(user.getName());
						curramdv.setValue("100");
							//currPer=1;
						pbfCalculation.setCurrationAmount(Double.valueOf(curramdv.getValue()));
						dataValueService.updateDataValue(curramdv);
					}catch (NullPointerException npe){
						curramntdv = new DataValue();
						curramntdv.setDataElement(currationAmountDataElement);
						curramntdv.setSource(ou);
						curramntdv.setPeriod(pbfReportPeriod.getQuarterPeriod());
						curramntdv.setCategoryOptionCombo(deoc);
						curramntdv.setAttributeOptionCombo(categoryService.getDefaultCategoryOptionCombo());
						curramntdv.setCreated(new Date());
						curramntdv.setComment("Арзишро система ҳисоб намудааст");
						curramntdv.setStoredBy(user.getName());
							curramntdv.setValue("100");
							//currPer=1;
							pbfCalculation.setCurrationAmount(Double.valueOf(curramntdv.getValue()));
						dataValueService.addDataValue(curramntdv);
					}
						try{
						if(currdv.getValue().equalsIgnoreCase("true")){
						//System.out.println(prevQuarter);
						//достиг порога 85% по качеству, кураторская команда РЦЗ выезжала в данный СЦЗ/ДЗ в предыдущем квартале минимум один раз, и по итогам надзорного выезда составлен отчет.

						if(pbfCalculation.getTotalQualityScore()*100/qualityScore>=85){
							
							System.out.println("score 85 " + pbfCalculation.getTotalQualityScore()*100/qualityScore);
							
							curramntdv = dataValueService.getDataValue(currationAmountDataElement, pbfReportPeriod.getQuarterPeriod(), ou, deoc);
							try{
								curramntdv.getValue();
								curramntdv.setStoredBy(user.getName());
								curramntdv.setLastUpdated(new Date());
								curramntdv.setComment("Арзишро система ҳисоб намудааст");
									curramntdv.setValue("288");
									currPer=1;
								dataValueService.updateDataValue(curramntdv);
							} catch (Exception ex){
								curramntdv = new DataValue();
								curramntdv.setDataElement(currationAmountDataElement);
								curramntdv.setPeriod(pbfReportPeriod.getQuarterPeriod());
								curramntdv.setSource(ou);
								curramntdv.setCategoryOptionCombo(deoc);
								curramntdv.setAttributeOptionCombo(categoryService.getDefaultCategoryOptionCombo());
								curramntdv.setCreated(new Date());
								curramntdv.setComment("Арзишро система ҳисоб намудааст");
								curramntdv.setStoredBy(user.getName());
									curramntdv.setValue("288");
									currPer=1;
								dataValueService.addDataValue(curramntdv);	
							}
						}else{
							//не достиг порога 85% по качеству, но улучшил свои результаты не менее чем на 5% по сравнению с предыдущим суммарным баллом, кураторская команда РЦЗ выезжала в данный СЦЗ/ДЗ в предыдущем 
							//квартале минимум один раз, и по итогам надзорного выезда составлен отчет.
							QuarterlyPeriodType qpt = new QuarterlyPeriodType();
							Period prevQuarter = qpt.getPreviousPeriod(pbfReportPeriod.getQuarterPeriod());
							
							prevQualdv = dataValueService.getDataValue(quailityDataElement, prevQuarter, ou, deoc);
							try{
							System.out.println(prevQualdv.getValue());
							//		nabranie bal tekushiy				*100 / max bal - prejniy bal 							*100/max bal = 
							//if(((pbfCalculation.getTotalQualityScore()*100/qualityScore)-(Double.valueOf(prevQualdv.getValue())*100/qualityScore)>=5)){
								if((pbfCalculation.getTotalQualityScore()-Double.valueOf(prevQualdv.getValue()))*100/Double.valueOf(prevQualdv.getValue())>=5){
									System.out.println("score +5 " + (pbfCalculation.getTotalQualityScore()-Double.valueOf(prevQualdv.getValue())*100/Double.valueOf(prevQualdv.getValue())));
									System.out.println("score +5 prev qv " + prevQualdv.getValue());
								curramntdv = dataValueService.getDataValue(currationAmountDataElement, pbfReportPeriod.getQuarterPeriod(), ou, deoc);
								try{
									curramntdv.getValue();
									curramntdv.setStoredBy(user.getName());
									curramntdv.setLastUpdated(new Date());
									curramntdv.setComment("Арзишро система ҳисоб намудааст");
										curramntdv.setValue("288");
										currPer=1;
									dataValueService.updateDataValue(curramntdv);
								} catch (Exception ex){
									curramntdv = new DataValue();
									curramntdv.setDataElement(currationAmountDataElement);
									curramntdv.setSource(ou);
									curramntdv.setPeriod(pbfReportPeriod.getQuarterPeriod());
									curramntdv.setCategoryOptionCombo(deoc);
									curramntdv.setAttributeOptionCombo(categoryService.getDefaultCategoryOptionCombo());
									curramntdv.setCreated(new Date());
									curramntdv.setComment("Арзишро система ҳисоб намудааст");
									curramntdv.setStoredBy(user.getName());
										curramntdv.setValue("288");
										currPer=1;
									dataValueService.addDataValue(curramntdv);
								}
							}
							
							}catch (Exception ex){
								
							}

							
							
						}
						}
						} catch (NullPointerException npe){
							
						}
					
					
				}
				
				coun++;
			   
				curramntdv = dataValueService.getDataValue(currationAmountDataElement, pbfReportPeriod.getQuarterPeriod(), ou, deoc);
				try{
				pbfCalculation.setCurrationAmount(Double.valueOf(curramntdv.getValue()));
				pbfCalculation.setCurrationPerformed(currPer);
				}catch(NullPointerException npe){
					//pbfCalculation.setCurrationAmount(0d);
				}
				try{
					pbfCalculation.setTotalQualityScorePrevQuar(Double.valueOf(prevQualdv.getValue()));
				}catch (NullPointerException npe){
					
				}
//set discount amount
				BigDecimal value = formulaValues(tokens[4], ou, pbfReportPeriod.getMonthOne().getId(), pbfCalculation.getDiscountRate());
				pbfCalculation.setDiscountAmount(pbfCalculation.getTotalAmount()*value.doubleValue()/100);
//set total with discount
				pbfCalculation.setTotalAmountWithDiscount(pbfCalculation.getTotalAmount()+pbfCalculation.getDiscountAmount());
//set audit amount
			
				DataValue auditDv = dataValueService.getDataValue( auditDataElement, pbfReportPeriod.getQuarterPeriod(), ou, deoc );
				try{
					auditDv.getValue();
				}catch (NullPointerException npe){
					auditDv = new DataValue();
					auditDv.setValue("0");
				}
				pbfCalculation.setAuditAmount(Double.valueOf(auditDv.getValue()));
			
//set total plus audit amount				
				pbfCalculation.setTotalAmountWithDiscountPlusAudit(pbfCalculation.getTotalAmountWithDiscount()+pbfCalculation.getAuditAmount());
				
//set facility amount
				value = formulaValues(tokens[5], ou, pbfReportPeriod.getMonthOne().getId(), Double.valueOf(0));
				pbfCalculation.setFacilityAmount(pbfCalculation.getTotalAmountWithDiscountPlusAudit()*value.doubleValue()/100);
//set salary amount				
				pbfCalculation.setSalaryAmount(pbfCalculation.getTotalAmountWithDiscountPlusAudit()-pbfCalculation.getFacilityAmount());
//set tax % value
				value = formulaValues(tokens[2], ou, pbfReportPeriod.getMonthOne().getId(), Double.valueOf(0));
				pbfCalculation.setTaxPercent(value.doubleValue());
//set social tax % value
				value = formulaValues(tokens[3], ou, pbfReportPeriod.getMonthOne().getId(), Double.valueOf(0));
				pbfCalculation.setSocialPercent(value.doubleValue());
//set tax amount
				pbfCalculation.setTaxAmount(pbfCalculation.getSalaryAmount()*pbfCalculation.getTaxPercent()/100);
//set social tax amount
				pbfCalculation.setSocialTaxAmount(pbfCalculation.getSalaryAmount()*pbfCalculation.getSocialPercent()/100);
//set net gross
				pbfCalculation.setNetGross(pbfCalculation.getSalaryAmount()-pbfCalculation.getTaxAmount()-pbfCalculation.getSocialTaxAmount());

				
				pbfCalculation.setStoredBy(currentUserService.getCurrentUsername());
				pbfCalculation.setCreated(new Date());

				pbfService.saveOrUpdate(pbfCalculation);

				pbfReport.setReportCalcDetails("calculated");
				pbfService.updatePbfReport(pbfReport);
			}

		}

		pbfReports = pbfService.getPbfReportsByUserOrgUnits(pbfReportPeriod, organisationUnits);

        memberCount = pbfReports.size();

        return SUCCESS;
    }
	
	

    // -------------------------------------------------------------------------
    // Data Locking (Approval) Supportive methods
    // -------------------------------------------------------------------------

    private List<DataApproval> getApprovalsAsList( DataApprovalLevel dataApprovalLevel, DataSet dataSet,
        Period period, OrganisationUnit organisationUnit, boolean accepted, Date created, User creator )
    {
        List<DataApproval> approvals = new ArrayList<>();

        CategoryOptionCombo combo = categoryService.getDefaultCategoryOptionCombo();
        period = periodService.reloadPeriod( period );
        DataApprovalWorkflow workflow = dataSet.getWorkflow();
        approvals.add( new DataApproval( dataApprovalLevel, workflow, period, organisationUnit, combo, accepted, created, creator ) );
        
        return approvals;
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