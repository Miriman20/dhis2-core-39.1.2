package org.hisp.dhis.pbf.analytics.excelreports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.datavalue.DataValueService;
import org.hisp.dhis.indicator.IndicatorService;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.docx.api.DocxService;
import org.hisp.dhis.pbf.model.PbfAnalyticsReportDetails;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.user.CurrentUserService;
import org.jxls.area.XlsArea;
import org.jxls.command.Command;
import org.jxls.command.EachCommand;
import org.jxls.command.IfCommand;
import org.jxls.common.AreaRef;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class FacilityIntVerVariation extends ActionSupport {

	 static Logger logger = LoggerFactory.getLogger(FacilityIntVerVariation.class);

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

  public void setCategoryService( CategoryService categoryService )
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
		return "variation_fac_intver_report.xls";
	}

@SuppressWarnings("unchecked")
public String execute() throws Exception {

	  ServletContext scontext = ServletActionContext.getServletContext();
	 
	    String filePath = scontext.getRealPath("/");
	    File file = new File(filePath + "/dhis-web-pbf/excelreports/", "variation_fac_intver_template.xls");

	    List<AnalyticsHolder> facilities =  new ArrayList<AnalyticsHolder>();
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
	    
	    List<PbfAnalyticsReportDetails> indicatorValues = new ArrayList<PbfAnalyticsReportDetails>();
	    List<PbfAnalyticsReportDetails> facilityIndicatorValues = new ArrayList<PbfAnalyticsReportDetails>();
	    
	    indicatorValues = pbfService.getPbfAnalyticalReportDetails();
	    
	    //header indicator names
	    List<PbfAnalyticsReportDetails> indnames = new ArrayList<PbfAnalyticsReportDetails>();
	    
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
	    
	    AnalyticsHolder ah = null;
	    int i = 0;
	    int y = 0;
	    for(PbfAnalyticsReportDetails ppp: indicatorValues){
	    System.out.println(ppp.getPeriodQuarter().getId()+ "  " + ppp.getOrgUnit().getId());	
    	if(i==0){
    		ah = new AnalyticsHolder(
    	    		ppp.getOrgUnit().getId(),
    	    	    ppp.getFacilityName(),
    	    	    ppp.getDistrictName(),
    	    	    ppp.getProvinceName(),
    	    	    ppp.getCountryName(),	    
    	    	    ppp.getPeriodQuarter().getIsoDate(),
    	    	    ppp.getOrgUnit().getPhoneNumber()
    	    		);
    	}
	    
	    if(i==9){
       	 i=0;
       	ah = new AnalyticsHolder(
	    		ppp.getOrgUnit().getId(),
	    	    ppp.getFacilityName(),
	    	    ppp.getDistrictName(),
	    	    ppp.getProvinceName(),
	    	    ppp.getCountryName(),	    
	    	    ppp.getPeriodQuarter().getIsoDate(),
	    	    ppp.getOrgUnit().getPhoneNumber()
	    		);
	    }
    	if(i<9){
    		ah.getIndicators().add(ppp);
    		facilityIndicatorValues.add(ppp);
    	}
    	if(i==8){

    		if(y==0)
   		 indnames.addAll(facilityIndicatorValues);
    		System.out.println("added indicator names");
       	 y=1;


 	    Collections.sort(ah.getIndicators(), new Comparator<PbfAnalyticsReportDetails>() {
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
    	 facilities.add(ah);
    	 System.out.println("added facility names");
    	 facilityIndicatorValues = new ArrayList<PbfAnalyticsReportDetails>();

    	}
		i++;
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
	    
	    logger.info("Opening input stream");
	    try(InputStream is = new FileInputStream(file)) {
	        try (OutputStream os = new FileOutputStream(filePath+ "/dhis-web-pbf/excelreports/variation_fac_intver_report.xls")) {
	            Transformer transformer = TransformerFactory.createTransformer(is, os);
	            
	            System.out.println("Creating area");
	            XlsArea xlsArea = new XlsArea("Template!A1:O7", transformer);
	            XlsArea facilityArea = new XlsArea("Template!A5:O5", transformer);
	            XlsArea indicatorArea = new XlsArea("Template!H5:O5", transformer);
	            
	            XlsArea summaryFormulasArea = new XlsArea("Template!H4:O6", transformer);
	            
	            
	            //XlsArea ifArea = new XlsArea("Template!A18:F18", transformer);
	            //XlsArea elseArea = new XlsArea("Template!A9:F9", transformer);
	            //IfCommand ifCommand = new IfCommand("employee.payment <= 2000",
	            //        ifArea,
	            //        elseArea);
	            //ifArea.addAreaListener(new SimpleAreaListener(ifArea));
	            // elseArea.addAreaListener(new SimpleAreaListener(elseArea));
	            //employeeArea.addCommand(new AreaRef("Template!A9:F9"), ifCommand);
	            
	            EachCommand facilityEachCommand = new EachCommand("facility", "facilities", facilityArea);
	            EachCommand indicatorEachCommand = new EachCommand("indicator", "facility.indicators", indicatorArea);
	            
	            //indicator name header
	            XlsArea indicatorNameArea = new XlsArea("Template!H1:O6", transformer);
	            EachCommand indicatorNameEachCommand = new EachCommand("indicatorname", "indicatornames", indicatorNameArea);
	            xlsArea.addCommand(new AreaRef("Template!H1:O6"), indicatorNameEachCommand);
	            
	            
	            //facility and indicator recursion 
	            facilityArea.addCommand(new AreaRef("Template!H5:O5"), indicatorEachCommand);
	            xlsArea.addCommand(new AreaRef("Template!A5:G5"), facilityEachCommand);
	            
	            
	            Context context = new Context();
	            context.putVar("facilities", facilities);
	            context.putVar("indicatornames", indnames);
	            logger.info("Applying at cell " + new CellRef("report!A1"));
	            logger.info("Setting EachCommand direction to Right");
	            indicatorNameEachCommand.setDirection(EachCommand.Direction.RIGHT);
	            facilityEachCommand.setDirection(EachCommand.Direction.DOWN);
	            indicatorEachCommand.setDirection(EachCommand.Direction.RIGHT);
	            
	            xlsArea.applyAt(new CellRef("report!A1"), context);
	            xlsArea.processFormulas();
	            
	            logger.info("Complete");
	            transformer.write();
	            logger.info("written to file");
	            
	            
	        }
	    }

	    fileInputStream = new FileInputStream(new File(filePath+ "/dhis-web-pbf/excelreports/variation_fac_intver_report.xls"));
	    
	    
    return SUCCESS;
  }
}