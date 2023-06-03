package org.hisp.dhis.pbf.source;

/*
 * Copyright (c) 2004-2011, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the HISP project nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
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

import java.util.List;

import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.dataset.DataSetService;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfDataElement;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Latifov Murodillo Abdusamadovich
 * @version $Id: 
 */
public class AddUpdateDataElementSourceAction
    extends ActionSupport
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -872435424217034765L;

    private PbfService pbfService;

    public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
	}

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
    
    private DataSetService dataSetService;

    public void setDataSetService( DataSetService dataSetService )
    {
        this.dataSetService = dataSetService;
    }
    
    // -------------------------------------------------------------------------
    // Input
    // -------------------------------------------------------------------------


    private Integer deid;

    private Integer coid;

    public void setDeid(Integer deid) {
		this.deid = deid;
	}

	public void setCoid(Integer coid) {
		this.coid = coid;
	}

    // -------------------------------------------------------------------------
    // Output
    // -------------------------------------------------------------------------

	private PbfDataElement pbfDataElement;

    public PbfDataElement getPbfDataElement()
    {
        return pbfDataElement;
    }

	public void setPbfDataElement(PbfDataElement pbfDataElement) {
		this.pbfDataElement = pbfDataElement;
	}

	private List<DataElement> des;
	
	private List<CategoryOptionCombo> ocs;

	private List<DataSet> dss;
		
	public void setDes(List<DataElement> des) {
		this.des = des;
	}

	public void setOcs(List<CategoryOptionCombo> ocs) {
		this.ocs = ocs;
	}

	public void setDss(List<DataSet> dss) {
		this.dss = dss;
	}

	public List<DataElement> getDes() {
		return des;
	}

	public List<CategoryOptionCombo> getOcs() {
		return ocs;
	}

	public List<DataSet> getDss() {
		return dss;
	}

    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

	public String execute()
    {
		DataElement de = dataElementService.getDataElement(deid);
		CategoryOptionCombo deoc = CategoryService.getCategoryOptionCombo(coid);
		
		pbfDataElement = pbfService.getPbfDataElementById( de, deoc );
        
		des = dataElementService.getAllDataElements();
		
		ocs = CategoryService.getAllCategoryOptionCombos();
		
		dss = dataSetService.getAllDataSets();
		
		return SUCCESS;
    }
}
