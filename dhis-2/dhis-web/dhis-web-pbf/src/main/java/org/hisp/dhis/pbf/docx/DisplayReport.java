package org.hisp.dhis.pbf.docx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.views.xdocreport.PopulateContextAware;
import org.hisp.dhis.pbf.math.Expression;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.datavalue.DataValue;
import org.hisp.dhis.datavalue.DataValueService;
import org.hisp.dhis.pbf.docx.api.DocxService;
import org.hisp.dhis.indicator.Indicator;
import org.hisp.dhis.indicator.IndicatorService;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.period.PeriodType;
import org.hisp.dhis.user.CurrentUserService;
import org.hisp.dhis.user.User;

import com.opensymphony.xwork2.ActionSupport;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.FieldExtractor;
import fr.opensagres.xdocreport.template.FieldsExtractor;
import fr.opensagres.xdocreport.template.IContext;

public class DisplayReport extends ActionSupport implements PopulateContextAware  {

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

  public String execute() throws Exception {
    return SUCCESS;
  }

  public void populateContext(IXDocReport report, IContext context)
                        throws Exception {
	 extractor = FieldsExtractor.create();
	 report.extractFields(extractor);
	 Period sp = PeriodType.getPeriodFromIsoString( pe );
	 sp = periodService.reloadPeriod( sp );
	 context.put("period", sp.getDisplayName());
	 context.put("currentDate", new Date());
	 User user = currentUserService.getCurrentUser();
	 context.put("currentUser", user.getDisplayName());
	 
	 Period ep = PeriodType.getPeriodFromIsoString( endPe );
	 try{
	 ep = periodService.reloadPeriod( ep );
	 }catch(NullPointerException npe){
		 //do noting
	 }
	 OrganisationUnit oUnit = organisationUnitService.getOrganisationUnit(ou);
	 oUnit.getLevel();
	 //for PBF
	 context.put("facility", oUnit.getName());
	 OrganisationUnit dist = oUnit.getParent();
	 context.put("facilityParent", dist.getName());
	 OrganisationUnit prov = oUnit.getParent();
	 context.put("facilityParentParent", prov.getName());
	 // end PBF
	 
	 Pattern pattern = Pattern.compile("d[0-9]+.c[0-9]+");
	 for(int i=0; i<extractor.getFields().size();i++){
		 System.out.println(extractor.getFields().get(i).getName());
		 Matcher match = pattern.matcher(extractor.getFields().get(i).getName());
		 while (match.find()) {
			 String matched = match.group();
			 System.out.println(matched);
			 String[] ids = matched.split("\\.");
			 System.out.println(ids.length);
			 DataElement de = dataElementService.getDataElement(Integer.valueOf(ids[0].substring(1)));
			try{
				System.out.println(de.getName());
			 }catch (NullPointerException npe){
				 context.put(matched, "no de");
			 }
			CategoryOptionCombo deco = categoryService.getCategoryOptionCombo(Integer.valueOf(ids[1].substring(1))); 
			try{
			 System.out.println(deco.getName());
			 }catch (NullPointerException npe){
				 context.put(matched, "no co");
			 }
			 try{
			 DataValue dv = dataValueService.getDataValue(de, sp, oUnit, deco);
System.out.println(de.getId() +" "+ deco.getId() +" "+ sp.getId() +" "+ oUnit.getId());
			 if(dv.getValue().length()>0){
				 System.out.println(dv.getValue());
				 context.put(matched, Integer.valueOf(dv.getValue()));	 
			 }
			 }catch (NullPointerException npe){
				 context.put(matched, " - ");
			 }		 
		 }
		 Pattern indicatorPattern = Pattern.compile("i[0-9]+");
		 String formulae = "";
		 String matched = "";
		 String stringFinalValue = "-";
		 Matcher indicatorMatch = indicatorPattern.matcher(extractor.getFields().get(i).getName());
		 outerloop:
		 while (indicatorMatch.find()) {
			 matched = indicatorMatch.group();
			 System.out.println(matched); 
			 try{
			 Indicator ind = indicatorService.getIndicator(Integer.valueOf(matched.substring(1)));
			 String numer = ind.getNumerator().replaceAll("#\\{", "").replaceAll("\\}", "");
			 String denom = ind.getDenominator().replaceAll("#\\{", "").replaceAll("\\}", "");
			 formulae = "((".concat(numer).concat(")/(").concat(denom).concat("))*".concat(String.valueOf(ind.getIndicatorType().getFactor()))); 
			 System.out.println("Formulae init: " + formulae);
			 }catch (NullPointerException npe){
				 
				 stringFinalValue = "no ind";
				 break outerloop;
			 }
			 Pattern indValueHolder = Pattern.compile("[a-zA-Z0-9]{11}.[a-zA-Z0-9]{11}");
			 Matcher thisMatch = indValueHolder.matcher(formulae);
			 while (thisMatch.find()) {
				 String matcheddeco = thisMatch.group();
				 System.out.println(matcheddeco);
				 String[] decoids = matcheddeco.split("\\.");
				 System.out.println(decoids.length);
				 try{ 
				 DataElement de = dataElementService.getDataElement(decoids[0]);
				 CategoryOptionCombo deco = categoryService.getCategoryOptionCombo(decoids[1]); 
				 System.out.println(deco.getName());
				
				 DataValue dv = dataValueService.getDataValue(de, sp, oUnit, deco);//(de.getId(), deco.getId(), sp.getId(), oUnit.getId());
	System.out.println("ind de val "+de.getId() +" "+ deco.getId() +" "+ sp.getId() +" "+ oUnit.getId());
				
					 System.out.println(dv.getValue());
					 formulae = formulae.replace(matcheddeco, dv.getValue());			
			 }catch (NullPointerException npe){
				 System.out.println("Null occured");
				 formulae = formulae.replace(matcheddeco, "0");
				 //break outerloop;
			 }		 
		 }
			 System.out.println("Formulae before parse: "+ formulae);
			 Expression e = new Expression(formulae);
			 if(formulae!=""){
				BigDecimal finalValue = e.eval();
				stringFinalValue = String.valueOf(finalValue.intValueExact());
				System.out.println("formula "+ formulae +" value: " + stringFinalValue);
		   }			 
	    }
			context.put(matched, stringFinalValue);
	 }
  }
}