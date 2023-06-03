package org.hisp.dhis.pbf.analytics.reportperiod;

/*
 * Copyright (c) 2004-2012, University of Oslo
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hisp.dhis.i18n.I18n;
import org.hisp.dhis.i18n.I18nFormat;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfAnalyticsReportDetails;
import org.hisp.dhis.pbf.model.PbfCalculation;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.user.CurrentUserService;
import org.hisp.dhis.user.User;
//import org.json.JSONArray;
import org.hisp.dhis.jsontree.JsonArray;

import com.opensymphony.xwork2.Action;

/**
 * @author Murodillo Latifov Abdusamadovich
 */
public class ShowCalculationAmountsAction
    implements Action
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private PbfService pbfService;

    public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
	}

    private PeriodService periodService;

    public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
	}
    
    private I18nFormat format;

    public void setFormat( I18nFormat format )
    {
        this.format = format;
    }

    private I18n i18n;

    public void setI18n( I18n i18n )
    {
        this.i18n = i18n;
    }
    
    private CurrentUserService currentUserService;

    public void setCurrentUserService( CurrentUserService currentUserService )
    {
        this.currentUserService = currentUserService;
    }
    
    // -------------------------------------------------------------------------
    // Input/output
    // -------------------------------------------------------------------------

    private int memberCount;

    public int getMemberCount()
    {
        return memberCount;
    }
    
    private JsonArray list;
    
    public JsonArray getList() {
		return list;
	}

	public void setList(JsonArray list) {
		this.list = list;
	}

	private String id;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private Long qtrPeriod;

	private Period periodQuarter;
	
	public Period getPeriodQuarter() {
		return periodQuarter;
	}

	public void setPeriodQuarter(Period periodQuarter) {
		this.periodQuarter = periodQuarter;
	}

	public Long getQtrPeriod() {
		return qtrPeriod;
	}

	public void setQtrPeriod(Long qtrPeriod) {
		this.qtrPeriod = qtrPeriod;
	}

	private List<PbfCalculation> pbfCalculations;

	public List<PbfCalculation> getPbfCalculations() {
		return pbfCalculations;
	}

	public void setPbfCalculations(List<PbfCalculation> pbfCalculations) {
		this.pbfCalculations = pbfCalculations;
	}
	
    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

	static final Comparator<PbfCalculation> NAME_ORDER = 
             new Comparator<PbfCalculation>() {
	public int compare(PbfCalculation e1, PbfCalculation e2) {
	return e1.getIndicatorName().compareTo(e2.getIndicatorName());
	}
	 };
	 
	public String execute()
    {
		pbfCalculations = new ArrayList<PbfCalculation>();

		setPeriodQuarter(periodService.getPeriod(qtrPeriod));
		
		User user = currentUserService.getCurrentUser();

		Collection<OrganisationUnit> organisationUnits = user.getOrganisationUnits();

		pbfCalculations = pbfService.getPbfCalculationDetailsByReportPeriod(periodQuarter);

		Collections.sort(pbfCalculations, NAME_ORDER);

        memberCount = pbfCalculations.size();
        
        return SUCCESS;
    }
}
