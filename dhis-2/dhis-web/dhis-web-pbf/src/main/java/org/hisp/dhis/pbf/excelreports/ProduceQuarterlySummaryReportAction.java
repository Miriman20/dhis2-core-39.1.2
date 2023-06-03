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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jett.transform.ExcelTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitGroupService;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.oust.manager.SelectionTreeManager;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfAnalyticsReportDetails;
import org.hisp.dhis.pbf.model.PbfCalculation;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.period.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;

/**
 * @author Latifov Murodillo Abdusamadovich
 */
public class ProduceQuarterlySummaryReportAction implements Action {

	static Logger logger = LoggerFactory.getLogger(ProduceQuarterlySummaryReportAction.class);

	// -------------------------------------------------------------------------
	// Dependencies
	// -------------------------------------------------------------------------

	private SelectionTreeManager selectionTreeManager;

	public void setSelectionTreeManager(SelectionTreeManager selectionTreeManager) {
		this.selectionTreeManager = selectionTreeManager;
	}

	private OrganisationUnitGroupService organisationUnitGroupService;

	public void setOrganisationUnitGroupService(OrganisationUnitGroupService organisationUnitGroupService) {
		this.organisationUnitGroupService = organisationUnitGroupService;
	}

	private OrganisationUnitService organisationUnitService;

	public void setOrganisationUnitService(OrganisationUnitService organisationUnitService) {
		this.organisationUnitService = organisationUnitService;
	}

	private PbfService pbfService;

	public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
	}

	private PeriodService periodService;

	public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
	}

	// -------------------------------------------------------------------------
	// Input & Output
	// -------------------------------------------------------------------------

	private String pe;

	public void setPe(String pe) {
		this.pe = pe;
	}

	private InputStream fileInputStream;

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	private String fileName;

	public String getFileName() {
		return "fac_intver_quantity_summary_report.xlsx";
	}

	// -------------------------------------------------------------------------
	// Action
	// -------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {

		List<String> templateSheetNames = new ArrayList<String>();
		List<String> sheetNames = new ArrayList<String>();
		List<Map<String, Object>> beansList = new ArrayList<Map<String, Object>>();

		ServletContext scontext = ServletActionContext.getServletContext();

		List<OrganisationUnit> units = organisationUnitService.getOrganisationUnitsAtLevel(3);

		int i = 1;
		// for each district org unit compute facility values.

		for (OrganisationUnit out : units) {
			// create sheets with district names
			templateSheetNames.add("sheet" + i);
			sheetNames.add(out.getName());

				beansList.add(prepareBeans(out));

			i++;
		}

		logger.info("Opening input stream");
		String filePath = scontext.getRealPath("/");
		File file = new File(filePath + "/dhis-web-pbf/excelreports/", "fac_intver_quantity_summary_template.xlsx");

		try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {

			try (OutputStream os = new FileOutputStream(
					filePath + "/dhis-web-pbf/excelreports/fac_intver_quantity_summary_report.xlsx")) {

				logger.info("Start workbook process");
				try {
					ExcelTransformer transformer = new ExcelTransformer();
					transformer.setForceRecalculationOnOpening(true);
					// Workbook workbook = transformer.transform(is, beans);
					Workbook workbook = transformer.transform(is, templateSheetNames, sheetNames, beansList);
					workbook.write(os);
					os.close();

					logger.info("Complete");

				} catch (IOException e) {
					System.err.println("IOException reading " + is + ": " + e.getMessage());
				} catch (InvalidFormatException e) {
					System.err.println("InvalidFormatException reading " + is + ": " + e.getMessage());
				}

				logger.info("written to file");

			}
		}

		fileInputStream = new FileInputStream(
				new File(filePath + "/dhis-web-pbf/excelreports/fac_intver_quantity_summary_report.xlsx"));

		return SUCCESS;
	}

	// Method to populate data in map of string objects

	private Map<String, Object> prepareBeans(OrganisationUnit ou) {

		Map<String, Object> beans = new HashMap<String, Object>();
		
		List<OrganisationUnit> children = new ArrayList<OrganisationUnit>(ou.getChildren());

		List<PbfCalculation> indicatorValues = new ArrayList<PbfCalculation>();

		Period start = PeriodType.getPeriodFromIsoString(pe);
		start = periodService.reloadPeriod(start);

		indicatorValues = pbfService.getPbfQuantityDetailsForSelection(children, start);
		System.out.println("ind value size " + indicatorValues.size());

		/*
		 * Collections.sort(indicatorValues, new Comparator<PbfCalculation>() { public
		 * int compare(PbfCalculation o1, PbfCalculation o2) { // Sorts by org unit id
		 * property long x1 = ((PbfCalculation) o1).getOrgUnit().getId(); long x2 =
		 * ((PbfCalculation) o2).getOrgUnit().getId();
		 * 
		 * if (x1 > x2) return +1; else if (x1 < x2) return -1; else return 0; } });
		 */

		order(indicatorValues);
		
		long unitCheck = 0;
		List<SummaryObject> sosh = new ArrayList<SummaryObject>();
		List<SummaryObject> sosc = new ArrayList<SummaryObject>();

		SummaryObject so = null;
		Double totalAmount = 0d;
		Double bonusAmount = 0d;
		Double facQty = 0d;
		Double verQty = 0d;
		Double payAmount = 0d;
		String unitType = "";
		Integer y = 0;
		Integer i = 0;
		
		PbfCalculation prevPc = new PbfCalculation();
		
		for (PbfCalculation pc : indicatorValues) {
			y++;
			
			
			System.out.println("indicator sort " + pc.getSortOrder());
			// check if data for the same unit exists and period and create facility objects
			if (unitCheck != pc.getOrgUnit().getId()) {
				
				if(y>1) {
					so.setBonusAmount(bonusAmount);
					so.setFacIndQtyTotal(facQty);
					so.setVerIndQtyTotal(verQty);
					so.setQualityScore(prevPc.getTotalQualityScore());
					so.setQualityScorePercent(prevPc.getDiscountRate());
					so.setIndAmntTotal(totalAmount);
					so.setPayAmount(payAmount);
					so.setFacilityName(prevPc.getFacilityName());
					so.setDistrictName(prevPc.getDistrictName());
					so.setProvinceName(prevPc.getProvinceName());
					so.setCountryName(prevPc.getCountryName());
					so.setFacTypeId(Integer.valueOf(prevPc.getOrgUnit().getPhoneNumber()));
					so.setFacType(prevPc.getOrgUnit().getPhoneNumber());
					
					System.out.println("ALL indicators total value " + totalAmount);
					
					y = 0;
					if (prevPc.getOrgUnit().getPhoneNumber().equalsIgnoreCase("0")) {
						sosh.add(so);
					} else {
						sosc.add(so);
					}
				}
				
				so = new SummaryObject();
				unitType = pc.getOrgUnit().getPhoneNumber();
				so.setFacilityName(pc.getFacilityName());
				so.setDistrictName(pc.getDistrictName());
				so.setProvinceName(pc.getProvinceName());
				so.setCountryName(pc.getCountryName());
				so.setFacTypeId(Integer.valueOf(pc.getOrgUnit().getPhoneNumber()));
				so.setFacType(pc.getOrgUnit().getPhoneNumber());
				so.setQualityScore(pc.getTotalQualityScore());
				so.setQualityScorePercent(pc.getDiscountRate());

				unitCheck = pc.getOrgUnit().getId();
				totalAmount = 0d;
				bonusAmount = 0d;
				facQty = 0d;
				verQty = 0d;
				payAmount = 0d;
			}

			try {
				if (pc.getSortOrder() == 1) {

					so.setFacIndQty1(pc.getFacilityQuarterValue());
					so.setVerIndQty1(pc.getQuarterValueOrig());
					so.setIndThreshold1(pc.getBasisValue());
					so.setIndThresholdQty1(pc.getThresholdValue());
					so.setIndAmnt1(pc.getTotalAmount());
					totalAmount += pc.getTotalAmount();
					bonusAmount += pc.getDiscountAmount();
					facQty += pc.getFacilityQuarterValue();
					verQty += pc.getQuarterValueOrig();
					payAmount += pc.getTotalAmountWithDiscount();

					System.out.println("indicator " + pc.getSortOrder() + " value " + pc.getTotalAmount() + " total value " + totalAmount);
					
				} else if (pc.getSortOrder() == 2) {

					so.setFacIndQty2(pc.getFacilityQuarterValue());
					so.setVerIndQty2(pc.getQuarterValueOrig());
					so.setIndAmnt2(pc.getTotalAmount());
					so.setIndThreshold2(pc.getBasisValue());
					so.setIndThresholdQty2(pc.getThresholdValue());
					totalAmount += pc.getTotalAmount();
					bonusAmount += pc.getDiscountAmount();
					facQty += pc.getFacilityQuarterValue();
					verQty += pc.getQuarterValueOrig();
					payAmount += pc.getTotalAmountWithDiscount();
					System.out.println("indicator " + pc.getSortOrder()  + " value " + pc.getTotalAmount() + " total value " + totalAmount);
				} else if (pc.getSortOrder() == 3) {

					so.setFacIndQty3(pc.getFacilityQuarterValue());
					so.setVerIndQty3(pc.getQuarterValueOrig());
					so.setIndAmnt3(pc.getTotalAmount());
					so.setIndThreshold3(pc.getBasisValue());
					so.setIndThresholdQty3(pc.getThresholdValue());
					totalAmount += pc.getTotalAmount();
					bonusAmount += pc.getDiscountAmount();
					facQty += pc.getFacilityQuarterValue();
					verQty += pc.getQuarterValueOrig();
					payAmount += pc.getTotalAmountWithDiscount();
					System.out.println("indicator " + pc.getSortOrder()  + " value " + pc.getTotalAmount() + " total value " + totalAmount);
				} else if (pc.getSortOrder() == 4) {

					so.setFacIndQty4(pc.getFacilityQuarterValue());
					so.setVerIndQty4(pc.getQuarterValueOrig());
					so.setIndAmnt4(pc.getTotalAmount());
					so.setIndThreshold4(pc.getBasisValue());
					so.setIndThresholdQty4(pc.getThresholdValue());
					totalAmount += pc.getTotalAmount();
					bonusAmount += pc.getDiscountAmount();
					facQty += pc.getFacilityQuarterValue();
					verQty += pc.getQuarterValueOrig();
					payAmount += pc.getTotalAmountWithDiscount();
					System.out.println("indicator " + pc.getSortOrder()  + " value " + pc.getTotalAmount() + " total value " + totalAmount);
				} else if (pc.getSortOrder() == 5) {

					so.setFacIndQty5(pc.getFacilityQuarterValue());
					so.setVerIndQty5(pc.getQuarterValueOrig());
					so.setIndAmnt5(pc.getTotalAmount());
					so.setIndThreshold5(pc.getBasisValue());
					so.setIndThresholdQty5(pc.getThresholdValue());
					totalAmount += pc.getTotalAmount();
					bonusAmount += pc.getDiscountAmount();
					facQty += pc.getFacilityQuarterValue();
					verQty += pc.getQuarterValueOrig();
					payAmount += pc.getTotalAmountWithDiscount();
					System.out.println("indicator " + pc.getSortOrder()  + " value " + pc.getTotalAmount() + " total value " + totalAmount);
				} else if (pc.getSortOrder() == 6) {

					so.setFacIndQty6(pc.getFacilityQuarterValue());
					so.setVerIndQty6(pc.getQuarterValueOrig());
					so.setIndAmnt6(pc.getTotalAmount());
					so.setIndThreshold6(pc.getBasisValue());
					so.setIndThresholdQty6(pc.getThresholdValue());
					totalAmount += pc.getTotalAmount();
					bonusAmount += pc.getDiscountAmount();
					facQty += pc.getFacilityQuarterValue();
					verQty += pc.getQuarterValueOrig();
					payAmount += pc.getTotalAmountWithDiscount();
					System.out.println("indicator " + pc.getSortOrder()  + " value " + pc.getTotalAmount() + " total value " + totalAmount);
				} else if (pc.getSortOrder() == 7) {

					so.setFacIndQty7(pc.getFacilityQuarterValue());
					so.setVerIndQty7(pc.getQuarterValueOrig());
					so.setIndAmnt7(pc.getTotalAmount());
					so.setIndThreshold7(pc.getBasisValue());
					so.setIndThresholdQty7(pc.getThresholdValue());
					totalAmount += pc.getTotalAmount();
					bonusAmount += pc.getDiscountAmount();
					facQty += pc.getFacilityQuarterValue();
					verQty += pc.getQuarterValueOrig();
					payAmount += pc.getTotalAmountWithDiscount();
					System.out.println("indicator " + pc.getSortOrder()  + " value " + pc.getTotalAmount() + " total value " + totalAmount);
				}

				prevPc = pc;
				
				if(i++ == indicatorValues.size() - 1){
			        // Last iteration
					so.setBonusAmount(bonusAmount);
					so.setFacIndQtyTotal(facQty);
					so.setVerIndQtyTotal(verQty);
					so.setQualityScore(prevPc.getTotalQualityScore());
					so.setQualityScorePercent(prevPc.getDiscountRate());
					so.setIndAmntTotal(totalAmount);
					so.setPayAmount(payAmount);
					so.setFacilityName(prevPc.getFacilityName());
					so.setDistrictName(prevPc.getDistrictName());
					so.setProvinceName(prevPc.getProvinceName());
					so.setCountryName(prevPc.getCountryName());
					so.setFacTypeId(Integer.valueOf(prevPc.getOrgUnit().getPhoneNumber()));
					so.setFacType(prevPc.getOrgUnit().getPhoneNumber());
					
					System.out.println("ALL indicators total value " + totalAmount);
					
					y = 0;
					if (prevPc.getOrgUnit().getPhoneNumber().equalsIgnoreCase("0")) {
						sosh.add(so);
					} else {
						sosc.add(so);
					}
			    }
				
				/*if (y == 7) {

					so.setBonusAmount(bonusAmount);
					so.setFacIndQtyTotal(facQty);
					so.setVerIndQtyTotal(verQty);
					so.setQualityScore(pc.getTotalQualityScore());
					so.setQualityScorePercent(pc.getDiscountRate());
					so.setIndAmntTotal(totalAmount);
					so.setPayAmount(payAmount);
					so.setFacilityName(pc.getFacilityName());
					so.setDistrictName(pc.getDistrictName());
					so.setProvinceName(pc.getProvinceName());
					so.setCountryName(pc.getCountryName());
					so.setFacTypeId(Integer.valueOf(pc.getOrgUnit().getPhoneNumber()));
					so.setFacType(pc.getOrgUnit().getPhoneNumber());
					
					System.out.println("ALL indicators total value " + totalAmount);
					
					y = 0;
					if (pc.getOrgUnit().getPhoneNumber().equalsIgnoreCase("0")) {
						sosh.add(so);
					} else {
						sosc.add(so);
					}
				}*/
			} catch (Exception e) {
				System.out.println("Null po e" + e);
			}
		}
		beans.put("sosh", sosh);
		beans.put("sosc", sosc);

		System.out.println("sosh size " + sosh.size());
		System.out.println("sosc size " + sosc.size());

		System.out.println("beans size " + beans.size());

		return beans;
	}
	
	@SuppressWarnings("unchecked")
	private static void order(List<PbfCalculation> indicators) {

	    Collections.sort(indicators, new Comparator() {

	        public int compare(Object o1, Object o2) {

	            Long x1 = ((PbfCalculation) o1).getOrgUnit().getId();
	            Long x2 = ((PbfCalculation) o2).getOrgUnit().getId();
	            int sComp = x1.compareTo(x2);

	            if (sComp != 0) {
	               return sComp;
	            }

	            Integer i1 = ((PbfCalculation) o1).getSortOrder();
	            Integer i2 = ((PbfCalculation) o2).getSortOrder();
	            return i1.compareTo(i2);
	    }});
	}
}