package org.hisp.dhis.pbf.analytics.excelreports;

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
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.dataset.Section;
import org.hisp.dhis.datavalue.DataValueService;
import org.hisp.dhis.indicator.IndicatorService;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.docx.api.DocxService;
import org.hisp.dhis.pbf.model.PbfAnalyticsQualityDetails;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.period.PeriodType;
import org.hisp.dhis.user.CurrentUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class IntVerExtVerQualityVariation extends ActionSupport {

	 static Logger logger = LoggerFactory.getLogger(IntVerExtVerQualityVariation.class);

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
  private String docFullPass;

  private PbfService pbfService;

  public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
	}

  private OrganisationUnitService organisationUnitService;

  public void setOrganisationUnitService( OrganisationUnitService organisationUnitService )
  {
      this.organisationUnitService = organisationUnitService;
  }

  private PeriodService periodService;

  public void setPeriodService( PeriodService periodService )
  {
      this.periodService = periodService;
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

  private DocxService docxService;

  public void setDocxService(DocxService docxService) {
		this.docxService = docxService;
	}

  private CurrentUserService currentUserService;

  public void setCurrentUserService( CurrentUserService currentUserService )
  {
      this.currentUserService = currentUserService;
  }

  private CategoryService categoryService;

  public void setCategoryService(CategoryService categoryService )
  {
      this.categoryService = categoryService;
  }

  private DataValueService dataValueService;

  public void setDataValueService( DataValueService dataValueService )
  {
      this.dataValueService = dataValueService;
  }

  // -------------------------------------------------------------------------
  // Input
  // -------------------------------------------------------------------------

  private String ds;

  public void setDs( String ds )
  {
      this.ds = ds;
  }

  private String pe;

  public void setPe( String pe )
  {
      this.pe = pe;
  }

  private String endPe;

  public void setEndPe( String endPe )
  {
      this.endPe = endPe;
  }

  private String ou;

  public void setOu( String ou )
  {
      this.ou = ou;
  }

  private boolean selectedUnitOnly;

  public boolean isSelectedUnitOnly()
  {
      return selectedUnitOnly;
  }

  public void setSelectedUnitOnly( boolean selectedUnitOnly )
  {
      this.selectedUnitOnly = selectedUnitOnly;
  }

  public String getDocFullPass() {
	return docxService.getDocxReport(Integer.valueOf(ds)).getReportLocation();
  }	

	private InputStream fileInputStream;
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	
	private String fileName;
	
	public String getFileName() {
		return "variation_intver_extver_report.xlsx";
	}

@SuppressWarnings("unchecked")
public String execute() throws Exception {

	  ServletContext scontext = ServletActionContext.getServletContext();
	 
	    
	    List<AnalyticsQualityIndicatorHolder> facilities =  new ArrayList<AnalyticsQualityIndicatorHolder>();
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
	    
	    List<PbfAnalyticsQualityDetails> indicatorValues = new ArrayList<PbfAnalyticsQualityDetails>();
	    List<PbfAnalyticsQualityDetails> facilityIndicatorValues = new ArrayList<PbfAnalyticsQualityDetails>();
	    List<PbfAnalyticsQualityDetails> facilityIndicatorData = new ArrayList<PbfAnalyticsQualityDetails>();
	    //header indicator names
	    List<AnalyticsQualityIndicatorHolder> sectionNames = new ArrayList<AnalyticsQualityIndicatorHolder>();
	    List<AnalyticsQualityIndicatorHolder> indnames = new ArrayList<AnalyticsQualityIndicatorHolder>();
	    
	    //get values
	    indicatorValues = pbfService.getPbfAnalyticsQualityDetails();
	    
	    
	    //set constants
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
	    //might not be needed
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
	    
	    Long unitCheck = 0l;
	    Long periodCheck = 0l;
	    
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
	    //beans.put("indicatorData", facilityIndicatorData);
	    
	    /*List<String> reportSheet = new ArrayList<String>();
	    		reportSheet.add("report");
	    List<Map<String, Object>> beansList = new ArrayList<Map<String, Object>>();
	    		beansList.add(beans);*/
	    
	    		logger.info("Opening input stream");
	    String filePath = scontext.getRealPath("/");
	    File file = new File(filePath + "/dhis-web-pbf/excelreports/", "variation_intver_extver_template.xlsx");
	    
	    try(InputStream is = new BufferedInputStream( new FileInputStream(file))) {
            
            try (OutputStream os = new FileOutputStream(filePath+ "/dhis-web-pbf/excelreports/variation_intver_extver_report.xlsx")) {
                
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

	    fileInputStream = new FileInputStream(new File(filePath+ "/dhis-web-pbf/excelreports/variation_intver_extver_report.xlsx"));
	    
	    
    return SUCCESS;
  }
}