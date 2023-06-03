package org.hisp.dhis.pbf.source;

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfDataElement;

import com.opensymphony.xwork2.Action;

public class GetDataElementSourceDefinitionsAction  implements Action {

	// -------------------------------------------------------------------------
	// Dependencies
	// -------------------------------------------------------------------------

    private PbfService pbfService;

    public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
	}

	// -------------------------------------------------------------------------
	// Input
	// -------------------------------------------------------------------------

	// -------------------------------------------------------------------------
	// Output
	// -------------------------------------------------------------------------

	private List<PbfDataElement> pbfDataElements = new ArrayList<PbfDataElement>();

	public List<PbfDataElement> getPbfDataElements() {
		return pbfDataElements;
	}
	

	public String execute()
	{

		pbfDataElements = pbfService.getAllPbfDataElements();

		return SUCCESS;
	}
}
