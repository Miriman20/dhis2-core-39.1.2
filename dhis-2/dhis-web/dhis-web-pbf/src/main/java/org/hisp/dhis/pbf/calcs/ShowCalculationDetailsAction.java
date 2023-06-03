package org.hisp.dhis.pbf.calcs;

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

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.hisp.dhis.i18n.I18n;
import org.hisp.dhis.i18n.I18nFormat;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfCalculation;
import org.hisp.dhis.pbf.model.PbfReport;
import org.hisp.dhis.user.CurrentUserService;

import com.opensymphony.xwork2.Action;

/**
 * @author Murodillo Latifov Abdusamadovich
 */
public class ShowCalculationDetailsAction
    implements Action
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private PbfService pbfService;

    public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
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
    
    private String id;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private PbfReport pbfReport;
	
	public PbfReport getPbfReport() {
		return pbfReport;
	}

	public void setPbfReport(PbfReport pbfReport) {
		this.pbfReport = pbfReport;
	}

	private List<PbfCalculation> pbfCalculations;

	public List<PbfCalculation> getPbfCalculations() {
		return pbfCalculations;
	}

	public void setPbfCalculations(List<PbfCalculation> pbfCalculations) {
		this.pbfCalculations = pbfCalculations;
	}
	
	 Double totalFasility=0d;
	 
	 Double totalVerification=0d;
	 
	 Double totalDifference=0d;
	 
	 Double totalPayAmount=0d;
	 
	 Double totalDiscountAmount=0d;
	 
	 Double qualityScore = 0d;
	 
	 Double totalQualityScore = 0d;
	
	 Double curramnt = 0d;
	 
	 public Double getTotalFasility() {
		return totalFasility;
	}

	public void setTotalFasility(Double totalFasility) {
		this.totalFasility = totalFasility;
	}

	public Double getTotalVerification() {
		return totalVerification;
	}

	public void setTotalVerification(Double totalVerification) {
		this.totalVerification = totalVerification;
	}

	public Double getTotalDifference() {
		return totalDifference;
	}

	public void setTotalDifference(Double totalDifference) {
		this.totalDifference = totalDifference;
	}

	public Double getTotalPayAmount() {
		return totalPayAmount;
	}

	public void setTotalPayAmount(Double totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}

	public Double getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(Double totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public Double getQualityScore() {
		return qualityScore;
	}

	public void setQualityScore(Double qualityScore) {
		this.qualityScore = qualityScore;
	}

	public Double getTotalQualityScore() {
		return totalQualityScore;
	}

	public Double getCurramnt() {
		return curramnt;
	}

	public void setCurramnt(Double curramnt) {
		this.curramnt = curramnt;
	}

	public void setTotalQualityScore(Double totalQualityScore) {
		this.totalQualityScore = totalQualityScore;
	}

	static final Comparator<PbfCalculation> NAME_ORDER = 
             new Comparator<PbfCalculation>() {
	public int compare(PbfCalculation e1, PbfCalculation e2) {
	return -Integer.valueOf(e2.getSortOrder()).compareTo(Integer.valueOf(e1.getSortOrder()));
	}
	 };

    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

	public String execute()
    {
		pbfReport = pbfService.getPbfReport(Long.valueOf(id));
		
		pbfCalculations = (List<PbfCalculation>) pbfService.getPbfCalculationDetailsByQuarterPeriodByOrgUnit(pbfReport.getReportPeriod().getQuarterPeriod(), pbfReport.getOrgUnit());
		
		Collections.sort(pbfCalculations, NAME_ORDER);
        
		 Iterator it = pbfCalculations.iterator();
		 
		 while(it.hasNext()){
			 PbfCalculation pbfCalc = (PbfCalculation) it.next();
			 totalFasility += pbfCalc.getFacilityQuarterValue();
			 totalVerification += pbfCalc.getQuarterValueOrig();
			 totalPayAmount += pbfCalc.getQuarterAmount();
			 
			 totalDiscountAmount += pbfCalc.getDiscountAmount();
			 
			 qualityScore = pbfCalc.getQualityScore();
			 totalQualityScore = pbfCalc.getTotalQualityScore();
			 curramnt = pbfCalc.getCurrationAmount();
		 }
		 
		 totalDifference = 100*(totalVerification-totalFasility)/totalFasility;
		 
		  
		 
		memberCount = pbfCalculations.size();

        return SUCCESS;
    }
}
