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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfDataElement;
import org.hisp.dhis.user.CurrentUserService;

import com.opensymphony.xwork2.Action;

/**
 * @author Latifov Murodillo Abdusamadovich
 * @version $Id:
 */
public class AddDataElementDataSourceAction
    implements Action
{
//    private final Log log = LogFactory.getLog( AddDataElementSourceAction.class );
    
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private DataElementService dataElementService;

    public void setDataElementService( DataElementService dataElementService )
    {
        this.dataElementService = dataElementService;
    }
    
    private CategoryService dataElementCategoryService;

    public void setCategoryService( CategoryService dataElementCategoryService )
    {
        this.dataElementCategoryService = dataElementCategoryService;
    }

    private PbfService pbfService;

    public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
	}
    

    private CurrentUserService currentUserService;

    public void setCurrentUserService( CurrentUserService currentUserService )
    {
        this.currentUserService = currentUserService;
    }

    
    // -------------------------------------------------------------------------
    // Input
    // -------------------------------------------------------------------------

	private Integer deid;

    public void setDeid(Integer deid) {
		this.deid = deid;
	}

	private Integer decoid;
	
	public void setDecoid(Integer decoid) {
		this.decoid = decoid;
	}

	private String calculationFormulaRphc;
	
	public void setCalculationFormulaRphc(String calculationFormulaRphc) {
		this.calculationFormulaRphc = calculationFormulaRphc;
	}

	private String calculationFormulaPhc;
	
	public void setCalculationFormulaPhc(String calculationFormulaPhc) {
		this.calculationFormulaPhc = calculationFormulaPhc;
	}

    // -------------------------------------------------------------------------
    // Output
    // -------------------------------------------------------------------------

	private String criteriaRphc;
	
	public String getCriteriaRphc() {
		return criteriaRphc;
	}

	private String criteriaPhc;
	
	public String getCriteriaPhc() {
		return criteriaPhc;
	}	

	private List<PbfDataElement> pbfDataElements = new ArrayList<PbfDataElement>();

	public List<PbfDataElement> getPbfDataElements() {
		return pbfDataElements;
	}

    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

	public String execute()
    {
        DataElement dataElement = dataElementService.getDataElement(deid);
        CategoryOptionCombo dataElementCategoryOption = dataElementCategoryService.getCategoryOptionCombo(decoid);
        PbfDataElement pbfDataElement = pbfService.getPbfDataElementByDeCco(dataElement, dataElementCategoryOption);
            	
        if( pbfDataElement == null ) {
        	pbfDataElement = new PbfDataElement();
        	pbfDataElement.setIntVerDataElement(dataElement);
        	pbfDataElement.setOptionCombo(dataElementCategoryOption);
        	pbfDataElement.setCalculationFormulaRphc(calculationFormulaRphc);
        	pbfDataElement.setCalculationFormulaPhc(calculationFormulaPhc);
        	pbfDataElement.setStoredBy(currentUserService.getCurrentUsername());
        	pbfDataElement.setCreated(new Date());

        	pbfService.addPbfDataElement(pbfDataElement);
        
        } else {
        	pbfDataElement.setCalculationFormulaRphc(calculationFormulaRphc);
        	pbfDataElement.setCalculationFormulaPhc(calculationFormulaPhc);
        	pbfDataElement.setStoredBy(currentUserService.getCurrentUsername());
        	pbfDataElement.setLastUpdated(new Date());
        	
	        	pbfService.updatePbfDataElement(pbfDataElement);
        }

        criteriaRphc = pbfDataElement.getCalculationFormulaRphc();
        criteriaPhc = pbfDataElement.getCalculationFormulaPhc();
        
        pbfDataElements = pbfService.getAllPbfDataElements();
        
        return SUCCESS;
    }
}