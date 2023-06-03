package org.hisp.dhis.pbf.excelreports;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jett.transform.ExcelTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitGroupService;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.oust.manager.SelectionTreeManager;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfCalculation;
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
public class ProduceControlReportAction
    implements Action
{
	
	 static Logger logger = LoggerFactory.getLogger(ProduceControlReportAction.class);

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
		return "controlpay_report.xlsx";
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
    	 
        //Collection<OrganisationUnit> units = selectionTreeManager.getReloadedSelectedOrganisationUnits();
        List<OrganisationUnit> units  = new ArrayList<OrganisationUnit>( selectionTreeManager.getReloadedSelectedOrganisationUnits());
        
        List<PbfCalculation> facilityCurrValues = new ArrayList<PbfCalculation>();
	    
  	    Period start = PeriodType.getPeriodFromIsoString(pe);
	    start = periodService.reloadPeriod(start);
	    
		List<PbfDataElement> dataElements = pbfService.getAllPbfDataElements();

		PbfDataElement pde = dataElements.get(0);
		DataElement de = pde.getIntVerDataElement();
		
  	    //get values
	    facilityCurrValues = pbfService.getPbfQuantityDetailsForSelection(units, start, de);
  	    	System.out.println("Size of obj " + facilityCurrValues.size());
  	    Map<String, Object> beans = new HashMap<String,  Object>();
  	    
  	    beans.put("facilities", facilityCurrValues);
  	  PbfCalculation pbfc = null;
  	  
  	    Iterator it = facilityCurrValues.iterator();
  	    	if(it.hasNext()){
  	    		pbfc = (PbfCalculation) it.next();
  	  
  	    	
  	    //PbfCalculation pbfc = facilityCurrValues.get(0);
  	  
  	  	beans.put("country", pbfc.getCountryName());
  	  	beans.put("province", pbfc.getProvinceName());
  	  	beans.put("district", pbfc.getDistrictName());

  	  beans.put("quarter", pbfc.getPeriodQuarter().getDisplayName());
  	    	}
  	    		logger.info("Opening input stream");
  	    String filePath = scontext.getRealPath("/");
  	    File file = new File(filePath + "/dhis-web-pbf/excelreports/", "controlpay_template.xlsx");
  	    
  	    try(InputStream is = new BufferedInputStream( new FileInputStream(file))) {
              
              try (OutputStream os = new FileOutputStream(filePath+ "/dhis-web-pbf/excelreports/controlpay_report.xlsx")) {
                  
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

  	    fileInputStream = new FileInputStream(new File(filePath+ "/dhis-web-pbf/excelreports/controlpay_report.xlsx"));
        
        return SUCCESS;
    }
    
    public byte[] getExcelFile() {
    	// TODO Auto-generated method stub
    	return null;
    }
}
