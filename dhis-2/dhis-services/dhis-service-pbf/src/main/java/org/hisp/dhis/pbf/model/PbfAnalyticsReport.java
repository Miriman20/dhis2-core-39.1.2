package org.hisp.dhis.pbf.model;

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

import java.io.Serializable;
import java.util.Date;

import org.hisp.dhis.common.BaseIdentifiableObject;
import org.hisp.dhis.organisationunit.OrganisationUnit;

/**
 * @author Latifov Murodillo Abdusamadovich
 * @version $Id:
 */
public class PbfAnalyticsReport extends BaseIdentifiableObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String reportName;
	
	private String reportCalcDetails;
	
	private PbfReportPeriod reportPeriod;

	private OrganisationUnit orgUnit;
	
	private String storedBy;
	
    private String updatedBy;
    
    private Date lastUpdated;
    
	private Date created;

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getStoredBy() {
		return storedBy;
	}

	public void setStoredBy(String storedBy) {
		this.storedBy = storedBy;
	}

	public Date getCreated() {
		return created;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public PbfReportPeriod getReportPeriod() {
		return reportPeriod;
	}

	public void setReportPeriod(PbfReportPeriod reportPeriod) {
		this.reportPeriod = reportPeriod;
	}

	public OrganisationUnit getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(OrganisationUnit orgUnit) {
		this.orgUnit = orgUnit;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getReportCalcDetails() {
		return reportCalcDetails;
	}

	public void setReportCalcDetails(String reportCalcDetails) {
		this.reportCalcDetails = reportCalcDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((created == null) ? 0 : created.hashCode());

		result = prime * result
				+ ((storedBy == null) ? 0 : storedBy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PbfAnalyticsReport other = (PbfAnalyticsReport) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (storedBy == null) {
			if (other.storedBy != null)
				return false;
		} else if (!storedBy.equals(other.storedBy))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PbfReport [orgUnit=" + ", orgUnitName="
				+ ", periodName=" + reportName + ", storedBy="
				+ storedBy + ", created=" + created + "]";
	}
}
