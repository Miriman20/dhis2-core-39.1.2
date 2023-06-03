package org.hisp.dhis.pbf.analytics.excelreports;

/*
 * Copyright (c) 2004-2015, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jett.transform.ExcelTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.dataset.Section;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitGroupService;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.oust.manager.SelectionTreeManager;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfAnalyticsQualityDetails;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.period.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;

/**
 * @author Latifov Murodillo Abdusamadovich
 */
public class DisplayQualityAggregationReportAction
    implements Action
{
	
	 static Logger logger = LoggerFactory.getLogger(DisplayQualityAggregationReportAction.class);

    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private SelectionTreeManager selectionTreeManager;

    public void setSelectionTreeManager( SelectionTreeManager selectionTreeManager )
    {
        this.selectionTreeManager = selectionTreeManager;
    }

    private OrganisationUnitGroupService organisationUnitGroupService;

    public void setOrganisationUnitGroupService( OrganisationUnitGroupService organisationUnitGroupService )
    {
        this.organisationUnitGroupService = organisationUnitGroupService;
    }

    private OrganisationUnitService organisationUnitService;

    public void setOrganisationUnitService( OrganisationUnitService organisationUnitService )
    {
        this.organisationUnitService = organisationUnitService;
    }

    private PbfService pbfService;

    public void setPbfService(PbfService pbfService) {
  		this.pbfService = pbfService;
  	}

    private PeriodService periodService;

    public void setPeriodService( PeriodService periodService )
    {
        this.periodService = periodService;
    }

    // -------------------------------------------------------------------------
    // Input & Output
    // -------------------------------------------------------------------------

    private Integer dataSetId;

    public void setDataSetId( Integer dataSetId )
    {
        this.dataSetId = dataSetId;
    }
    
    private String ds;

	public void setDs(String ds) {
		this.ds = ds;
	}

	private String pe;

	public void setPe(String pe) {
		this.pe = pe;
	}

	private String endPe;

	public void setEndPe(String endPe) {
		this.endPe = endPe;
	}

	private String ou;

	public void setOu(String ou) {
		this.ou = ou;
	}

	private InputStream fileInputStream;
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	
	private String fileName;
	
	public String getFileName() {
		return "variation_intver_extver_report.xlsx";
	}

    // -------------------------------------------------------------------------
    // Action
    // -------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
	@Override
    public String execute()
        throws Exception
    {


    	ServletContext scontext = ServletActionContext.getServletContext();
    	 
    	//DataSet dataSet = dataSetService.getDataSet( dataSetId );

        //Collection<OrganisationUnit> units = selectionTreeManager.getReloadedSelectedOrganisationUnits();
        List<OrganisationUnit> units  = new ArrayList<OrganisationUnit>( selectionTreeManager.getReloadedSelectedOrganisationUnits());
        //dataSetService.mergeWithCurrentUserOrganisationUnits( dataSet, units );

/*        for(OrganisationUnit unit : units){
        	System.out.println(unit.getName());
        }*/
          	    
  	    List<AnalyticsQualityIndicatorHolder> facilities =  new ArrayList<AnalyticsQualityIndicatorHolder>();
  	    
  	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
  	    
  	    List<PbfAnalyticsQualityDetails> indicatorValues = new ArrayList<PbfAnalyticsQualityDetails>();
  	    List<PbfAnalyticsQualityDetails> facilityIndicatorValues = new ArrayList<PbfAnalyticsQualityDetails>();
  	    List<PbfAnalyticsQualityDetails> facilityIndicatorData = new ArrayList<PbfAnalyticsQualityDetails>();
  	    //header indicator names
  	    List<AnalyticsQualityIndicatorHolder> sectionNames = new ArrayList<AnalyticsQualityIndicatorHolder>();
  	    List<AnalyticsQualityIndicatorHolder> indnames = new ArrayList<AnalyticsQualityIndicatorHolder>();
  	    
  	    Period start = PeriodType.getPeriodFromIsoString(pe);
	    start = periodService.reloadPeriod(start);
	    Period end = PeriodType.getPeriodFromIsoString(endPe);
	    end = periodService.reloadPeriod(end);
	    Date startDate = start.getStartDate();
	    Date endDate = end.getEndDate();
	    
	    
	    List<Period> periods = periodService.getPeriodsBetweenDates( PeriodType.getPeriodTypeByName("Quarterly"), startDate, endDate);

/*        for(Period unit : periods){
        	System.out.println(unit.getName());
        }*/
	    
	   // OrganisationUnit orgUnit = organisationUnitService.getOrganisationUnit(ou);
	    
  	    //get values
  	    //indicatorValues = pbfService.getPbfAnalyticsQualityDetails();
	    indicatorValues = pbfService.getPbfAnalyticsQualityDetailsForSelection(units, periods);
	    
  	    //set constants
	    try{
  	    PbfAnalyticsQualityDetails pqd = indicatorValues.get(0);
  	    
  	    CategoryOptionCombo deoc = pqd.getOptionCombo();
  	    DataSet dataSetMax = pqd.getDataSetMax();
  	    
  	    //populate header
  	    for(Section sec: dataSetMax.getSections()){
  	    	AnalyticsQualityIndicatorHolder pqdn = new AnalyticsQualityIndicatorHolder();
  	    	pqdn.setSectionName(sec.getName());
  	    	pqdn.setSectionId(sec.getId());
  	    	pqdn.setSortOrder(sec.getSortOrder());
  	    	
  	    int i =1;
  	    for(DataElement de: sec.getDataElements()){
  	    	AnalyticsQualityIndicatorHolder pqdd = new AnalyticsQualityIndicatorHolder();
  	    	pqdd.setSectionName(de.getName());
  	    	pqdd.setSectionId(sec.getId());
  	    	pqdd.setSortOrder(i);
  	    	indnames.add(pqdd);
  	    	pqdn.getSections().add(pqdd);
  	    	i++;
  	    }
  	    pqdn.setSectionSize(pqdn.getSections().size()*6);
  	    sectionNames.add(pqdn);
  	    }
  	    
  	    
  	    //populate data
  	    //sort data
  	    Collections.sort(indicatorValues, new Comparator() {

  	        public int compare(Object o1, Object o2) {

  	        	 //Sorts by sort property
  	        	Long x1 = ((PbfAnalyticsQualityDetails) o1).getOrgUnit().getId();
  	            Long x2 = ((PbfAnalyticsQualityDetails) o2).getOrgUnit().getId();

              	if(x1>x2)
                      return +1;
                  else if(x1<x2)
                      return -1;
                  else{
                  	Long y1 = ((PbfAnalyticsQualityDetails) o1).getPeriodQuarter().getId();
      	            Long y2 = ((PbfAnalyticsQualityDetails) o2).getPeriodQuarter().getId();
      	            if(y1>y2)
                          return +1;
                      else if(y1<y2)
                          return -1;
                      else 
                      	return 0;
                  }
              }
  	    });

  	    Integer sortOne = 0;
  	    Integer sortTwo = 0;
  	    
  	    Long unitCheck = 0L;
  	    Long periodCheck = 0L;
  	    
  	    PbfAnalyticsQualityDetails aqd = null;
  	    int count=0;
  	    
  	    for(PbfAnalyticsQualityDetails paqd: indicatorValues){
  		    
  	    	System.out.println("All " +paqd.getPeriodQuarter().getId()+ "  " + paqd.getOrgUnit().getId());
  	    	
  		    //check if data for the same unt and period and create facility objects
  		    if(unitCheck!=paqd.getOrgUnit().getId() || periodCheck!=paqd.getPeriodQuarter().getId()){
  		    	try{
  		    		System.out.println(paqd.getPeriodQuarter().getId()+ "  " + paqd.getOrgUnit().getId());
  		    		aqd.getCountryName();
  		    		facilityIndicatorValues.add(aqd);	
  		    		System.out.println("added iteration banch");
  		    	}catch(NullPointerException npe){
  		    		System.out.println("Null po e");
  		    	}
  		    	
  		    	aqd = new PbfAnalyticsQualityDetails();
  				aqd = paqd;
  				unitCheck=paqd.getOrgUnit().getId();
  				periodCheck=paqd.getPeriodQuarter().getId();
  				System.out.println(paqd.getPeriodQuarter().getId()+ " - " +periodCheck +" "+ paqd.getOrgUnit().getId()+ " - "+ unitCheck);
  		    }
  		    //create indicator values for given org unit and period
  		    aqd.getIndicators().add(paqd);
  	    	
  	    }
  	    //facilityIndicatorData.add(aqd);
  	    //facilityIndicatorValues.add(aqd);
  	    facilityIndicatorValues.add(aqd);
  	    System.out.println("added last banch");
  	    //Set<DataElement> orderedDataElements = dataElementService.getGroupedDataElementsByCategoryCombo( des );

  	    
  	    Collections.sort(sectionNames, new Comparator<AnalyticsQualityIndicatorHolder>() {
  	        public int compare(AnalyticsQualityIndicatorHolder o1, AnalyticsQualityIndicatorHolder o2) {
  	            //Sorts by sort property
  	        	int x1 = ((AnalyticsQualityIndicatorHolder) o1).getSortOrder();
  	            int x2 = ((AnalyticsQualityIndicatorHolder) o2).getSortOrder();
  	            
  	            	if(x1>x2)
  	                    return +1;
  	                else if(x1<x2)
  	                    return -1;
  	                else
  	                    return 0;
  	        }	        
  	    });
	    }catch (Exception e){
	    	System.out.println("no value exception");
	    }
	    
  	    List<String> headerRep = new ArrayList<String>();
  	    	for(int x=0; x<indnames.size();x++){
  	    	headerRep.add("Балл");
  	    	headerRep.add("ВВ");
  	    	headerRep.add("НВ");
  	    	headerRep.add("Разн. Б/ВВ");
  	    	headerRep.add("Разн. Б/НВ");
  	    	headerRep.add("Разн. ВВ/НВ");
  	    	}
  	    	
  	    Map<String, Object> beans = new HashMap<String,  Object>();
  	    beans.put("sectionNames", sectionNames);
  	    beans.put("indicatorNames", indnames);
  	    beans.put("headerRep", headerRep);
  	    beans.put("facilities", facilityIndicatorValues);

  	    
  	    		logger.info("Opening input stream");
  	    String filePath = scontext.getRealPath("/");
  	    File file = new File(filePath + "/dhis-web-pbf-analytics/excelreports/", "variation_intver_extver_template.xlsx");
  	    
  	    try(InputStream is = new BufferedInputStream( new FileInputStream(file))) {
              
              try (OutputStream os = new FileOutputStream(filePath+ "/dhis-web-pbf-analytics/excelreports/variation_intver_extver_report.xlsx")) {
                  
              	logger.info("Start workbook process");
  	    	try
  	    	{
  	    		   ExcelTransformer transformer = new ExcelTransformer();
  	    		   Workbook workbook = transformer.transform(is, beans);
  	    		   workbook.write(os);
  	    		   os.close();
  	    		   
  	            logger.info("Complete");
  	            
  	    	}
  	    	catch (IOException e)
  	    	{
  	    	   System.err.println("IOException reading " + is + ": " + e.getMessage());
  	    	}
  	    	catch (InvalidFormatException e)
  	    	{
  	    	   System.err.println("InvalidFormatException reading " + is + ": " + e.getMessage());
  	    	}

  	            logger.info("written to file");
  	            
  	        }
  	    }

  	    fileInputStream = new FileInputStream(new File(filePath+ "/dhis-web-pbf-analytics/excelreports/variation_intver_extver_report.xlsx"));
        
        return SUCCESS;
    }
    
    public byte[] getExcelFile() {
    	// TODO Auto-generated method stub
    	return null;
    }
}
