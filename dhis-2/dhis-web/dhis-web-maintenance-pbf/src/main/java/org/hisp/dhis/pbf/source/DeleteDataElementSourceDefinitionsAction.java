package org.hisp.dhis.pbf.source;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfDataElement;

import com.opensymphony.xwork2.Action;

public class DeleteDataElementSourceDefinitionsAction implements Action {

	// -------------------------------------------------------------------------
	// Dependencies
	// -------------------------------------------------------------------------

    private DataElementService dataElementService;

    public void setDataElementService( DataElementService dataElementService )
    {
        this.dataElementService = dataElementService;
    }
    
    private CategoryService CategoryService;

    public void setDataElementCategoryService( CategoryService CategoryService )
    {
        this.CategoryService = CategoryService;
    }

    private PbfService pbfService;

    public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
	}

	// -------------------------------------------------------------------------
	// Input
	// -------------------------------------------------------------------------
	private Integer deid;

	private Integer decoid;

	public Integer getDeid() {
		return deid;
	}

	public void setDeid(Integer deid) {
		this.deid = deid;
	}

	public Integer getDecoid() {
		return decoid;
	}

	public void setDecoid(Integer decoid) {
		this.decoid = decoid;
	}

	// -------------------------------------------------------------------------
	// Output
	// -------------------------------------------------------------------------
	
	private List<PbfDataElement> pbfDataElements = new ArrayList<PbfDataElement>();

	public List<PbfDataElement> getPbfDataElements() {
		return pbfDataElements;
	}
	
	
	
	public String execute() {
        
		DataElement dataElement = dataElementService.getDataElement(deid);
        CategoryOptionCombo dataElementCategoryOption = CategoryService.getCategoryOptionCombo(decoid);
        PbfDataElement pbfDataElement = pbfService.getPbfDataElementByDeCco(dataElement, dataElementCategoryOption);
        
		try {
			pbfService.deletePbfDataElementById(pbfDataElement);			
			
		} catch(Exception ex) {			
			return ERROR;
		}
		
		pbfDataElements = pbfService.getAllPbfDataElements();
		
		return SUCCESS;
	}

}
