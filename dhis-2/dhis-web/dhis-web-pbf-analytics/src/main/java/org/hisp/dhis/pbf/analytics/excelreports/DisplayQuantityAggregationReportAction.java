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
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitGroupService;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.oust.manager.SelectionTreeManager;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfAnalyticsReportDetails;
import org.hisp.dhis.pbf.model.PbfDataElement;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.period.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;

/**
 * @author Latifov Murodillo Abdusamadovich
 */
public class DisplayQuantityAggregationReportAction
    implements Action
{
	
	 static Logger logger = LoggerFactory.getLogger(DisplayQuantityAggregationReportAction.class);

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
		return "variation_fac_intver_report.xlsx";
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

       // List<OrganisationUnit> units = (List<OrganisationUnit>) selectionTreeManager.getReloadedSelectedOrganisationUnits();
        List<OrganisationUnit> units  = new ArrayList<OrganisationUnit>( selectionTreeManager.getReloadedSelectedOrganisationUnits());
        
        //dataSetService.mergeWithCurrentUserOrganisationUnits( dataSet, units );

/*        for(OrganisationUnit unit : units){
        	System.out.println(unit.getName());
        }*/
          	    
  	    
  	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
  	    
  	    List<PbfAnalyticsReportDetails> facilityIndicatorData = new ArrayList<PbfAnalyticsReportDetails>();
  	    //header indicator names
  	    
	    List<AnalyticsHolder> facilities =  new ArrayList<AnalyticsHolder>();
	    
	    List<PbfAnalyticsReportDetails> indicatorValues = new ArrayList<PbfAnalyticsReportDetails>();
	    List<PbfAnalyticsReportDetails> facilityIndicatorValues = new ArrayList<PbfAnalyticsReportDetails>();
	    
	    indicatorValues = pbfService.getPbfAnalyticalReportDetails();
	    
	    //header indicator names
	    List<PbfAnalyticsReportDetails> indnames = new ArrayList<PbfAnalyticsReportDetails>();
  	    
	    
  	    Period start = PeriodType.getPeriodFromIsoString(pe);
	    start = periodService.reloadPeriod(start);
	    Period end = PeriodType.getPeriodFromIsoString(endPe);
	    end = periodService.reloadPeriod(end);
	    Date startDate = start.getStartDate();
	    Date endDate = end.getEndDate();
	    
	    
	    List<Period> periods = periodService.getPeriodsBetweenDates( PeriodType.getPeriodTypeByName("Quarterly"), startDate, endDate);
	    
	    OrganisationUnit orgUnit = organisationUnitService.getOrganisationUnit(ou);
	    
  	    //get values
	    //indicatorValues = pbfService.getPbfAnalyticalReportDetails();
	    indicatorValues = pbfService.getPbfAnalyticsQuantityDetailsForSelection(units, periods);
	    
	  //populate header
	    
	    //indnames = pbfService.getPbfDistinctQuantityIndicatorNames();
	    
	    List<PbfDataElement> dataElements = pbfService.getAllPbfDataElements();
	    PbfAnalyticsReportDetails paq = null;
	    for(PbfDataElement pde: dataElements){
	    	paq = new PbfAnalyticsReportDetails();
	    	paq.setIndicatorName(pde.getIntVerDataElement().getName());
	    	paq.setSortOrder(pde.getSortOrder());
	    	indnames.add(paq);
	    }
 	    Collections.sort(indnames, new Comparator<PbfAnalyticsReportDetails>() {
	        public int compare(PbfAnalyticsReportDetails o1, PbfAnalyticsReportDetails o2) {
	            //Sorts by sort property
	        	int x1 = ((PbfAnalyticsReportDetails) o1).getSortOrder();
	            int x2 = ((PbfAnalyticsReportDetails) o2).getSortOrder();

            	if(x1>x2)
                    return +1;
                else if(x1<x2)
                    return -1;
                else
                    return 0;
	        }
	    });
	    
  	    //set constants
 	    try{
	    PbfAnalyticsReportDetails pqd = indicatorValues.get(0);
  	    
  	    CategoryOptionCombo deoc = pqd.getOptionCombo();
  	    
  	    
	    //
	    Collections.sort(indicatorValues, new Comparator() {

	        public int compare(Object o1, Object o2) {

	        	 //Sorts by sort property
	        	Long x1 = ((PbfAnalyticsReportDetails) o1).getOrgUnit().getId();
	            Long x2 = ((PbfAnalyticsReportDetails) o2).getOrgUnit().getId();

            	if(x1>x2)
                    return +1;
                else if(x1<x2)
                    return -1;
                else{
                	Long y1 = ((PbfAnalyticsReportDetails) o1).getPeriodQuarter().getId();
    	            Long y2 = ((PbfAnalyticsReportDetails) o2).getPeriodQuarter().getId();
    	            if(y1>y2)
                        return +1;
                    else if(y1<y2)
                        return -1;
                    else 
                    	return 0;
                }
            }
	    });
  	    
  	    
  	    //populate data


  	    Integer sortOne = 0;
  	    Integer sortTwo = 0;
  	    
  	    Long unitCheck = 0L;
  	    Long periodCheck = 0L;
  	    
  	  PbfAnalyticsReportDetails aqd = null;
  	    int count=0;
  	    
  	    for(PbfAnalyticsReportDetails paqd: indicatorValues){
  		    
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
  		    	
  		    	aqd = new PbfAnalyticsReportDetails();
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
 	    }catch(Exception e){
 	    	System.out.println("Nasty exception");
 	    }
  	    	
  	    Map<String, Object> beans = new HashMap<String,  Object>();
  	   
  	    beans.put("indicatorNames", indnames);
  	    beans.put("facilities", facilityIndicatorValues);

  	    
  	    		logger.info("Opening input stream");
  	    String filePath = scontext.getRealPath("/");
  	    File file = new File(filePath + "/dhis-web-pbf-analytics/excelreports/", "variation_fac_intver_template.xlsx");
  	    
  	    try(InputStream is = new BufferedInputStream( new FileInputStream(file))) {
              
              try (OutputStream os = new FileOutputStream(filePath+ "/dhis-web-pbf-analytics/excelreports/variation_fac_intver_report.xlsx")) {
                  
              	logger.info("Start workbook process");
  	    	try
  	    	{
  	    		   ExcelTransformer transformer = new ExcelTransformer();
  	    		   transformer.setForceRecalculationOnOpening(true);
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

  	    fileInputStream = new FileInputStream(new File(filePath+ "/dhis-web-pbf-analytics/excelreports/variation_fac_intver_report.xlsx"));
  	    
  	    
        
        
        return SUCCESS;
    }
}
