package org.hisp.dhis.pbf.docx;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.views.xdocreport.PopulateContextAware;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.datavalue.DataValueService;
import org.hisp.dhis.pbf.docx.api.DocxService;
import org.hisp.dhis.indicator.IndicatorService;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfCalculation;
import org.hisp.dhis.pbf.model.PbfReport;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.user.CurrentUserService;
import org.hisp.dhis.user.User;

import com.opensymphony.xwork2.ActionSupport;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.FieldExtractor;
import fr.opensagres.xdocreport.template.FieldsExtractor;
import fr.opensagres.xdocreport.template.IContext;

public class DisplayPbfCalcsReport extends ActionSupport  implements PopulateContextAware  {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
  private String docFullPass;
  
  private FieldsExtractor<FieldExtractor> extractor;

  private OrganisationUnitService organisationUnitService;

  public void setOrganisationUnitService( OrganisationUnitService organisationUnitService )
  {
      this.organisationUnitService = organisationUnitService;
  }

  private PbfService pbfService;

  public void setPbfService( PbfService pbfService )
  {
      this.pbfService = pbfService;
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

  private String id;

  public void setId( String id )
  {
      this.id = id;
  }
  
  public String getDocFullPass() {
	return docxService.getDocxReport(Integer.valueOf(ds)).getReportLocation();
  }	

  private int memberCount;
  
  public int getMemberCount(){
	  return memberCount;
  }

  public String execute() throws Exception {
    return SUCCESS;
  }

	
	 static final Comparator<PbfCalculation> NAME_ORDER = 
          new Comparator<PbfCalculation>() {
	public int compare(PbfCalculation e1, PbfCalculation e2) {
	return -e2.getSortOrder().compareTo(e1.getSortOrder());
	}
	 };
	 
	 
  public void populateContext(IXDocReport report, IContext context)
                        throws Exception {
	 extractor = FieldsExtractor.create();
	 report.extractFields(extractor);
	 
	 PbfReport pbfReport = pbfService.getPbfReport(Long.valueOf(id));
	
	 Period qtrp = pbfReport.getReportPeriod().getQuarterPeriod();
			 periodService.reloadPeriod( qtrp );
	 
	 List<PbfCalculation> pbfCalculations = (List<PbfCalculation>) pbfService.getPbfCalculationDetailsByQuarterPeriodByOrgUnit(qtrp, pbfReport.getOrgUnit());
	 Collections.sort(pbfCalculations, NAME_ORDER);
	 
	 Iterator<PbfCalculation> it = pbfCalculations.iterator();
	 
	 Double totalFasility=0d;
	 Double totalVerification=0d;
	 Double totalDifference=0d;
	 Double totalPayAmount=0d;
	 
	 Double totalDiscountAmount=0d;
	 
	 Double qualityScore = 0d;
	 Double totalQualityScore = 0d;
	 Double percentageScore = 0d;
	 Double percentageAdded = 0d;
	 Double currationAmount = 0d;
	 Integer currationPerformed = 0;
	 
	 while(it.hasNext()){
		 PbfCalculation pbfCalc = (PbfCalculation) it.next();
		 totalFasility += pbfCalc.getFacilityQuarterValue();
		 totalVerification += pbfCalc.getQuarterValueOrig();
		 totalPayAmount += pbfCalc.getQuarterAmount();
		 
		 totalDiscountAmount += pbfCalc.getDiscountAmount();
		 
		 qualityScore = pbfCalc.getQualityScore();
		 totalQualityScore = pbfCalc.getTotalQualityScore();	
		 percentageAdded = pbfCalc.getDiscountRate();
		 
		 currationAmount = pbfCalc.getCurrationAmount();
		 currationPerformed = pbfCalc.getCurrationPerformed();
	 }
	 
	 
	 totalDifference = 100*(totalVerification-totalFasility)/totalFasility;
	 
	 percentageScore = totalQualityScore*100/qualityScore;
	 
	 VelocityFormatHelper formatter = new VelocityFormatHelper();
	 
	 Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		calendar.setTime(pbfReport.getReportPeriod().getQuarterPeriod().getStartDate());
		
		int year = calendar.get(Calendar.YEAR);
		
     memberCount = pbfCalculations.size();
     
//     System.out.println("calcs "  + memberCount);
    
     context.put("totalFasility", totalFasility);
     context.put("totalVerification", totalVerification);
     context.put("totalDifference", totalDifference);
     context.put("totalPayAmount", totalPayAmount);
     
     context.put("totalDiscountAmount", totalDiscountAmount);
     
     context.put("qualityScore", qualityScore);
     context.put("totalQualityScore", totalQualityScore);
     context.put("percentageScore", percentageScore);
     context.put("percentageAdded", percentageAdded);
     
     context.put("currationAmount", currationAmount);
     context.put("currationPerformed", currationPerformed);
     
     context.put("memberCount", memberCount);
     context.put("quantityIndicators", pbfCalculations);
     context.put("pbfReport", pbfReport);

	 context.put("period", pbfReport.getReportPeriod().getPeriodName());
	 context.put("currentDate", new Date());
	 User user = currentUserService.getCurrentUser();
	 context.put("currentUser", user.getDisplayName());

	 OrganisationUnit oUnit = pbfReport.getOrgUnit();

	 context.put("facility", oUnit.getName());
	 OrganisationUnit dist = oUnit.getParent();
	 context.put("facilityParent", dist.getName());
	 OrganisationUnit prov = dist.getParent();
	 context.put("facilityParentParent", prov.getName());
	 
	 context.put("formatter",formatter);
	 context.put("year",year);
	 
  }
}