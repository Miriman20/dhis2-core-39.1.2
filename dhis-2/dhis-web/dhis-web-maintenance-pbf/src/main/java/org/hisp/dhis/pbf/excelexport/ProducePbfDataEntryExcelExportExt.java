package org.hisp.dhis.pbf.excelexport;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.period.PeriodType;

import com.opensymphony.xwork2.Action;

public class ProducePbfDataEntryExcelExportExt 
			implements Action{
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
		return "excelDataEntryFiles.zip";
	}

	    List<String> filesListInDir = new ArrayList<String>();
	  
	
    // -------------------------------------------------------------------------
    // Action
    // -------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
	@Override
    public String execute()
        throws Exception
    {

    	ServletContext scontext = ServletActionContext.getServletContext();

  	    Map<String, Object> beans = new HashMap<String,  Object>();
  	   
        List<OrganisationUnit> units  = new ArrayList<OrganisationUnit>( organisationUnitService.getOrganisationUnitsAtLevel(3));
        
  	    Period start = PeriodType.getPeriodFromIsoString(pe);
	    start = periodService.reloadPeriod(start);
	    
  	    String filePath = scontext.getRealPath("/");
        
        
  	  File phcFile = new File(filePath + "/dhis-web-maintenance-pbf/reps/", "template 2021 EV PHC.xlsx");
  	  
  	  File phhFile = new File(filePath + "/dhis-web-maintenance-pbf/reps/", "template 2021 EV PHH.xlsx");
  	
  	
        for(OrganisationUnit ou : units){
        	  File file =  new File(filePath+ "/dhis-web-maintenance-pbf/exceloutsext/"+ ou.getDisplayName()+"/dummy.txt");;
        	  	file.getParentFile().mkdir();
        	
        	List<OrganisationUnit> children = new ArrayList<OrganisationUnit>( ou.getChildren());
        		for(OrganisationUnit o : children){
        			beans = new HashMap<String,  Object>();
        		  	beans.put("periodId", start.getId());
        		  	beans.put("ouId", o.getId());
        		  	beans.put("district", ou.getDisplayName());
        		  	beans.put("province", ou.getParent().getDisplayName());
        		  	beans.put("quarterName", start.getDisplayName());  
        		  	beans.put("facilityName", o.getDisplayName()); 
      
        		  	if(o.getPhoneNumber().equalsIgnoreCase("0")){
        		  		file = phcFile;
        		  	}else{
        		  		file = phhFile;
        		  	}
        		    try(InputStream is = new BufferedInputStream( new FileInputStream(file))) {
        	              
        		    	File outFile = new File(filePath+ "/dhis-web-maintenance-pbf/exceloutsext/"+ ou.getDisplayName()+"/"+o.getDisplayName()+".xlsx");
        		    	
        	              try (OutputStream os = new FileOutputStream(outFile)) {
        	                
        	  	    	try
        	  	    	{
        	  	    		   ExcelTransformer transformer = new ExcelTransformer();
        	  	    		   transformer.setForceRecalculationOnOpening(true);
        	  	    		   Workbook workbook = transformer.transform(is, beans);
        	  	    		   workbook.write(os);
        	  	    		   os.close();
        	  	    	}
        	  	    	catch (IOException e)
        	  	    	{
        	  	    	   System.err.println("IOException reading " + is + ": " + e.getMessage());
        	  	    	}
        	  	    	catch (InvalidFormatException e)
        	  	    	{
        	  	    	   System.err.println("InvalidFormatException reading " + is + ": " + e.getMessage());
        	  	    	}
        		}
        	}
  	      }
  	   }
        
        File dir = new File (filePath + "/dhis-web-maintenance-pbf/excelDataEntryFiles");
        String zipDirName = filePath + "/dhis-web-maintenance-pbf/excelDataEntryFiles/"+ start.getDisplayName() +"excelDataEntryFilesExtVer.zip";
        try{
        	dir.mkdir();
        }catch(Exception e){
        	
        }
        try {
        //put every file into list
        populateFilesList(new File(filePath+ "/dhis-web-maintenance-pbf/exceloutsext/"));
        //create ZipOutputStream to write to the zip file
        
     	FileOutputStream fos = new FileOutputStream(zipDirName);
        ZipOutputStream zos = new ZipOutputStream(fos);

        
        
  	  for(String fPath : filesListInDir){
          //System.out.println("Zipping "+fPath);
          //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
          ZipEntry ze = new ZipEntry(fPath.substring((filePath+ "/dhis-web-maintenance-pbf/exceloutsext/").length(), fPath.length()));
          zos.putNextEntry(ze);
          //read the file and write to ZipOutputStream
          FileInputStream fis = new FileInputStream(fPath);
          byte[] buffer = new byte[1024];
          int len;
          while ((len = fis.read(buffer)) > 0) {
              zos.write(buffer, 0, len);
          }
          zos.closeEntry();
          fis.close();
      }
      zos.close();
      fos.close();
  } catch (IOException e) {
      e.printStackTrace();
  }
        fileInputStream = new FileInputStream(dir+"/"+ start.getDisplayName() +"excelDataEntryFilesExtVer.zip");
        return SUCCESS;
    }

    /**
     * This method populates all the files in a directory to a List
     * @param dir
     * @throws IOException
     */
    private void populateFilesList(File dir) throws IOException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()) filesListInDir.add(file.getAbsolutePath());
            else populateFilesList(file);
            
            //System.out.println(file.getAbsolutePath());
        }
    }

}
