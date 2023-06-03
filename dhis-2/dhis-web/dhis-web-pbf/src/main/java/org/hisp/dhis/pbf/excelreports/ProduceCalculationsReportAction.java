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
import java.util.Collections;
import java.util.Comparator;
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
public class ProduceCalculationsReportAction
    implements Action
{
	
	 static Logger logger = LoggerFactory.getLogger(ProduceCalculationsReportAction.class);

    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

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
		return "summary_calculations_report.xlsx";
	}

    // -------------------------------------------------------------------------
    // Action
    // -------------------------------------------------------------------------

	static final Comparator<PbfCalculation> NAME_ORDER = 
            new Comparator<PbfCalculation>() {
	public int compare(PbfCalculation e1, PbfCalculation e2) {
	return e1.getFacilityName().compareTo(e2.getFacilityName());
	}
	 };
	 
    @SuppressWarnings("unchecked")
	@Override
    public String execute()
        throws Exception
    {

    	ServletContext scontext = ServletActionContext.getServletContext();
        
        List<PbfCalculation> calcAmounts = new ArrayList<PbfCalculation>();
	    
  	    Period start = PeriodType.getPeriodFromIsoString(pe);
	    start = periodService.reloadPeriod(start);
		
  	    Map<String, Object> beans = new HashMap<String,  Object>();
  	    
  	  calcAmounts = pbfService.getPbfCalculationDetailsByReportPeriod(start);

  	  Collections.sort(calcAmounts, NAME_ORDER);
  	
  	long unitCheck = 0;
  	CalculationsHolder co = null;
	Double totalAmount = 0d;
	Double bonusAmount = 0d;
	Double totalWithBonus = 0d;
	Double indTotalQtyF = 0d;
	Double indTotalQtyV = 0d;
	
	//String unitType = "";
	Integer y = 0;
	PbfCalculation prevPc = new PbfCalculation();
	
	List<CalculationsHolder> calcs = new ArrayList<CalculationsHolder>();
	
	
	for (PbfCalculation pc : calcAmounts) {
		y++;
		
		// check if data for the same unit exists and period and create facility objects
		if (unitCheck != pc.getOrgUnit().getId()) {
			if(y>1) {

				co.setCountryName(prevPc.getCountryName());
				co.setProvinceName(prevPc.getProvinceName());
				co.setDistrictName(prevPc.getDistrictName());
				co.setFacilityName(prevPc.getFacilityName());
				co.setFacilityId(Long.valueOf(prevPc.getOrgUnit().getId()));
				co.setType(prevPc.getOrgUnit().getPhoneNumber());
				co.setPeriodQuarter(prevPc.getPeriodQuarter().getDisplayName());
				co.setBonusAmount(bonusAmount);
				co.setPaymentWithoutBonusAmount(totalAmount);
				co.setPaymentWithBonusAmount(totalWithBonus);
				co.setIndTotalQtyF(indTotalQtyF);
				co.setIndTotalQtyV(indTotalQtyV);
				co.setBonusPercent(prevPc.getDiscountRate());
				y = 0;
			
			calcs.add(co);
			}
			
			co = new CalculationsHolder();
			co.setCountryName(pc.getCountryName());
			co.setProvinceName(pc.getProvinceName());
			co.setDistrictName(pc.getDistrictName());
			co.setFacilityName(pc.getFacilityName());
			co.setFacilityId(Long.valueOf(pc.getOrgUnit().getId()));
			co.setType(pc.getOrgUnit().getPhoneNumber());
			co.setPeriodQuarter(pc.getPeriodQuarter().getDisplayName());
			co.setBonusAmount(pc.getDiscountAmount());
			co.setPaymentWithoutBonusAmount(pc.getQuarterAmount());
			co.setPaymentWithBonusAmount(pc.getTotalAmountWithDiscount());
			
			unitCheck = pc.getOrgUnit().getId();
			totalAmount = 0d;
			bonusAmount = 0d;
			totalWithBonus = 0d;
			indTotalQtyF = 0d;
			indTotalQtyV = 0d;
		}

		try {
			if (pc.getSortOrder() == 1) {
				co.setIndOneAmount(pc.getTotalAmount());
				co.setIndOneQtyF(pc.getFacilityQuarterValue());
				co.setIndOneQtyV(pc.getQuarterValueOrig());
				co.setIndOneThreV(pc.getThresholdValue());
				co.setIndOneBasV(pc.getBasisValue());
				
				totalAmount += pc.getTotalAmount();
				bonusAmount += pc.getDiscountAmount();
				totalWithBonus += pc.getTotalAmountWithDiscount();
				indTotalQtyF += pc.getFacilityQuarterValue();
				indTotalQtyV += pc.getQuarterValueOrig();
				
			} else if (pc.getSortOrder() == 2) {
				co.setIndTwoAmount(pc.getTotalAmount());
				co.setIndTwoQtyF(pc.getFacilityQuarterValue());
				co.setIndTwoQtyV(pc.getQuarterValueOrig());
				co.setIndTwoThreV(pc.getThresholdValue());
				co.setIndTwoBasV(pc.getBasisValue());
				
				totalAmount += pc.getTotalAmount();
				bonusAmount += pc.getDiscountAmount();
				totalWithBonus += pc.getTotalAmountWithDiscount();
				indTotalQtyF += pc.getFacilityQuarterValue();
				indTotalQtyV += pc.getQuarterValueOrig();
			} else if (pc.getSortOrder() == 3) {
				co.setIndThreeAmount(pc.getTotalAmount());
				co.setIndThreeQtyF(pc.getFacilityQuarterValue());
				co.setIndThreeQtyV(pc.getQuarterValueOrig());
				co.setIndThreeThreV(pc.getThresholdValue());
				co.setIndThreeBasV(pc.getBasisValue());
				
				totalAmount += pc.getTotalAmount();
				bonusAmount += pc.getDiscountAmount();
				totalWithBonus += pc.getTotalAmountWithDiscount();
				indTotalQtyF += pc.getFacilityQuarterValue();
				indTotalQtyV += pc.getQuarterValueOrig();
			} else if (pc.getSortOrder() == 4) {
				co.setIndFourAmount(pc.getTotalAmount());
				co.setIndFourQtyF(pc.getFacilityQuarterValue());
				co.setIndFourQtyV(pc.getQuarterValueOrig());
				co.setIndFourThreV(pc.getThresholdValue());
				co.setIndFourBasV(pc.getBasisValue());
				
				totalAmount += pc.getTotalAmount();
				bonusAmount += pc.getDiscountAmount();
				totalWithBonus += pc.getTotalAmountWithDiscount();
				indTotalQtyF += pc.getFacilityQuarterValue();
				indTotalQtyV += pc.getQuarterValueOrig();
			} else if (pc.getSortOrder() == 5) {
				co.setIndFiveAmount(pc.getTotalAmount());
				co.setIndFiveQtyF(pc.getFacilityQuarterValue());
				co.setIndFiveQtyV(pc.getQuarterValueOrig());
				co.setIndFiveThreV(pc.getThresholdValue());
				co.setIndFiveBasV(pc.getBasisValue());
				
				totalAmount += pc.getTotalAmount();
				bonusAmount += pc.getDiscountAmount();
				totalWithBonus += pc.getTotalAmountWithDiscount();
				indTotalQtyF += pc.getFacilityQuarterValue();
				indTotalQtyV += pc.getQuarterValueOrig();
			} else if (pc.getSortOrder() == 6) {
				co.setIndSixAmount(pc.getTotalAmount());
				co.setIndSixQtyF(pc.getFacilityQuarterValue());
				co.setIndSixQtyV(pc.getQuarterValueOrig());
				co.setIndSixThreV(pc.getThresholdValue());
				co.setIndSixBasV(pc.getBasisValue());
				
				totalAmount += pc.getTotalAmount();
				bonusAmount += pc.getDiscountAmount();
				totalWithBonus += pc.getTotalAmountWithDiscount();
				indTotalQtyF += pc.getFacilityQuarterValue();
				indTotalQtyV += pc.getQuarterValueOrig();
			} else if (pc.getSortOrder() == 7) {
				co.setIndSevenAmount(pc.getTotalAmount());
				co.setIndSevenQtyF(pc.getFacilityQuarterValue());
				co.setIndSevenQtyV(pc.getQuarterValueOrig());
				co.setIndSevenThreV(pc.getThresholdValue());
				co.setIndSevenBasV(pc.getBasisValue());
				
				totalAmount += pc.getTotalAmount();
				bonusAmount += pc.getDiscountAmount();
				totalWithBonus += pc.getTotalAmountWithDiscount();
				indTotalQtyF += pc.getFacilityQuarterValue();
				indTotalQtyV += pc.getQuarterValueOrig();
			}

			prevPc = pc;
			
		} catch (Exception e) {
			System.out.println("Null po e" + e);
		}
	}
	
  	    beans.put("calcs", calcs);
  	  
  	    		logger.info("Opening input stream");
  	    String filePath = scontext.getRealPath("/");
  	    File file = new File(filePath + "/dhis-web-pbf/excelreports/", "summary_calculations_template.xlsx");
  	    
  	    try(InputStream is = new BufferedInputStream( new FileInputStream(file))) {
              
              try (OutputStream os = new FileOutputStream(filePath+ "/dhis-web-pbf/excelreports/summary_calculations_report.xlsx")) {
                  
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

  	    fileInputStream = new FileInputStream(new File(filePath+ "/dhis-web-pbf/excelreports/summary_calculations_report.xlsx"));
        
        return SUCCESS;
    }
    
    public byte[] getExcelFile() {
    	// TODO Auto-generated method stub
    	return null;
    }
}
