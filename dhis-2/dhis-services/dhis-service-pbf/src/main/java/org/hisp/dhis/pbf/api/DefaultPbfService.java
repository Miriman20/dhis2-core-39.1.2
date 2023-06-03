package org.hisp.dhis.pbf.api;

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

import java.util.Collection;
import java.util.List;

import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.pbf.model.PbfAnalyticsReportDetails;
import org.hisp.dhis.pbf.model.PbfAnalyticsQualityDetails;
import org.hisp.dhis.pbf.model.PbfAnalyticsReport;
import org.hisp.dhis.pbf.model.PbfCalculation;
import org.hisp.dhis.pbf.model.PbfDataElement;
import org.hisp.dhis.pbf.model.PbfReport;
import org.hisp.dhis.pbf.model.PbfReportPeriod;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.user.User;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Latifov Murodillo Abdusamadovich
 * 
 * @version $Id$
 */
@Transactional
public class DefaultPbfService
    implements PbfService
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private PbfStore pbfStore;

    public void setPbfStore( PbfStore pbfStore )
    {
        this.pbfStore = pbfStore;
    }

	@Override
	public Long addPbfReportPeriod(PbfReportPeriod pbfReport) {
		return pbfStore.addPbfReportPeriod(pbfReport);
	}

	@Override
	public void updatePbfReportPeriod(PbfReportPeriod pbfReport) {
		pbfStore.updatePbfReportPeriod(pbfReport);
	}

	@Override
	public void deletePbfReportPeriod(PbfReportPeriod pbfReport) {
		pbfStore.deletePbfReportPeriod(pbfReport);
	}

	/*
	 * @Override public PbfReportPeriod getPbfReportPeriod(Long id) { return
	 * pbfStore.getPbfReportPeriod(id); }
	 */

	@Override
	public Collection<PbfReportPeriod> getAllPbfReportPeriods() {
		return pbfStore.getAllPbfReportPeriods();
	}

	@Override
	public void addPbfCalculation(PbfCalculation pbfCalculation) {
		pbfStore.addPbfCalculation(pbfCalculation);
	}

	@Override
	public PbfCalculation getPbfCalculation(Long pbfCalculationId) {
		return pbfStore.getPbfCalculation(pbfCalculationId);
	}

	@Override
	public void deletePbfCalculation(Long id) {
		pbfStore.deletePbfCalculation(id);
	}

	@Override
	public void updatePbfCalculation(PbfCalculation pbfCalculation) {
		pbfStore.updatePbfCalculation(pbfCalculation);
	}

	@Override
	public PbfDataElement getPbfDataElementByDeCco(DataElement dataElement,
			CategoryOptionCombo dataElementCategoryOption) {
		return pbfStore.getPbfDataElementByDeCco(dataElement, dataElementCategoryOption);
	}

	@Override
	public void addPbfDataElement(PbfDataElement pbfDataElement) {
		pbfStore.addPbfDataElement(pbfDataElement);
	}

	@Override
	public void updatePbfDataElement(PbfDataElement pbfDataElement) {
		pbfStore.updatePbfDataElement(pbfDataElement);
	}

	@Override
	public void deletePbfDataElementById(PbfDataElement pbfDataElement) {
		pbfStore.deletePbfDataElement(pbfDataElement);
	}

	@Override
	public List<PbfDataElement> getAllPbfDataElements() {
		return pbfStore.getAllPbfDataElements();
	}

	@Override
	public List<PbfReportPeriod> getPbfReports() {
		return pbfStore.getPbfReports();
	}

	@Override
	public Collection<PbfReport> getPbfReportsByUserOrgUnits(PbfReportPeriod pbfReportPeriod,
			Collection<OrganisationUnit> organisationUnits) {
		return pbfStore.getPbfReportsByUserOrgUnits(pbfReportPeriod, organisationUnits);
	}

	@Override
	public PbfReportPeriod getPbfReportPeriod(Long id) {
		return pbfStore.getPbfReportPeriod(id);
	}

	@Override
	public PbfReport getPbfReport(Long id) {
		return pbfStore.getPbfReport(id);
	}

	@Override
	public Collection<PbfCalculation> getPbfCalculationDetailsByReportPeriodByOrgUnit(
			PbfReportPeriod reportPeriod, OrganisationUnit orgUnit) {
		return pbfStore.getPbfCalculationDetailsByReportPeriodByOrgUnit(
				reportPeriod, orgUnit);
	}

	@Override
	public PbfReport getReport(PbfReportPeriod pbfReportPeriod,
			OrganisationUnit ou, User user) {
		return pbfStore.getReport(
				pbfReportPeriod, ou, user);
	}

	@Override
	public Long addPbfReport(PbfReport pbfReport) {
		return pbfStore.addPbfReport(pbfReport);
	}

	@Override
	public PbfCalculation getPbfCalculationByPrimaryKeys(
			DataElement dataElement, OrganisationUnit ou,
			PbfReportPeriod reportPeriod) {
		return pbfStore.getPbfCalculationByPrimaryKeys(
				dataElement, ou, reportPeriod);
	}

	@Override
	public void saveOrUpdate(PbfCalculation pbfCalculation) {
		pbfStore.saveOrUpdate(pbfCalculation);
	}

	@Override
	public void saveOrUpdate(PbfAnalyticsReportDetails pbfAnalyticalReportDetails) {
		pbfStore.saveOrUpdate(pbfAnalyticalReportDetails);
	}

	@Override
	public void updatePbfReport(PbfReport pbfReport) {
		pbfStore.updatePbfReport(pbfReport);
	}

	@Override
	public Collection<PbfCalculation> getPbfCalculationDetailsByQuarterPeriodByOrgUnit(
			Period quarterPeriod, OrganisationUnit orgUnit) {
		return pbfStore.getPbfCalculationDetailsByQuarterPeriodByOrgUnit(
				 quarterPeriod,  orgUnit);
	}

	@Override
	public PbfAnalyticsReportDetails getPbfAnalyticalReportDetailsByPrimaryKeys(DataElement dataElement,
			CategoryOptionCombo optionCombo, OrganisationUnit ou, PbfReportPeriod reportPeriod) {
		return pbfStore.getPbfAnalyticalReportDetailsByPrimaryKeys(dataElement, optionCombo, ou, reportPeriod
				 );
	}

	@Override
	public PbfAnalyticsReport getAnalyticsReport(PbfReportPeriod pbfReportPeriod, OrganisationUnit ou, User user) {
		return pbfStore.getAnalyticsReport(pbfReportPeriod, ou, user);
	}

	@Override
	public Long addAnalyticsReport(PbfAnalyticsReport pbfAnalyticsReport) {
		return pbfStore.addAnalyticsReport(pbfAnalyticsReport);
	}

	@Override
	public void updatePbfAnalyticsReport(PbfAnalyticsReport pbfAnalyticsReport) {
		pbfStore.updatePbfAnalyticsReport(pbfAnalyticsReport);
	}

	@Override
	public Collection<PbfAnalyticsReport> getPbfAnalyticsReportsByUserOrgUnits(PbfReportPeriod pbfReportPeriod,
			Collection<OrganisationUnit> organisationUnits) {
		return pbfStore.getPbfAnalyticsReportsByUserOrgUnits(pbfReportPeriod, organisationUnits);
	}

	@Override
	public List<PbfAnalyticsReportDetails> getPbfAnalyticalReportDetails() {
		return pbfStore.getPbfAnalyticalReportDetails();
	}

	@Override
	public PbfAnalyticsQualityDetails getPbfAnalyticsQualityDetailsByPrimaryKeys(DataElement dataElement,
			CategoryOptionCombo deoc, OrganisationUnit ou, PbfReportPeriod reportPeriod) {
		return pbfStore.getPbfAnalyticsQualityDetailsByPrimaryKeys(dataElement, deoc, ou, reportPeriod);
	}

	@Override
	public void saveOrUpdate(PbfAnalyticsQualityDetails pbfAnQuDetails) {
		pbfStore.saveOrUpdate(pbfAnQuDetails);
	}

	@Override
	public List<PbfAnalyticsQualityDetails> getPbfAnalyticsQualityDetails() {
		return pbfStore.getPbfAnalyticsQualityDetails();
	}

	@Override
	public List<PbfAnalyticsQualityDetails> getPbfAnalyticsQualityDetailsForSelection(
			List<OrganisationUnit> units, List<Period> periods) {
		return pbfStore.getPbfAnalyticsQualityDetailsForSelection(units, periods);
	}

	@Override
	public List<PbfAnalyticsReportDetails> getPbfDistinctQuantityIndicatorNames() {
		return pbfStore.getPbfDistinctQuantityIndicatorNames();
	}

	@Override
	public List<PbfAnalyticsReportDetails> getPbfAnalyticsQuantityDetailsForSelection(
			List<OrganisationUnit> units, List<Period> periods) {
		return pbfStore.getPbfAnalyticsQuantityDetailsForSelection(units, periods);
	}

	@Override
	public DataElement getExtVerDataElementbyIntVerId(DataElement dataElement) {
		return pbfStore.getExtVerDataElementbyIntVerId(dataElement);
	}

	@Override
	public List<PbfCalculation> getPbfQuantityDetailsForSelection(List<OrganisationUnit> units,
			Period period, DataElement de) {
		return pbfStore.getPbfQuantityDetailsForSelection(units, period, de);
	}

	@Override
	public List<PbfAnalyticsReportDetails> getPbfAnalyticsQuantityDetailsForSelection(List<OrganisationUnit> units,
			Period start) {
		return pbfStore.getPbfAnalyticsQuantityDetailsForSelection(units, start);
	}

	@Override
	public List<PbfCalculation> getPbfQuantityDetailsForSelection(List<OrganisationUnit> children,
			Period start) {
		return pbfStore.getPbfQuantityDetailsForSelection(children, start);
	}

	@Override
	public PbfDataElement getPbfDataElementById(DataElement de, CategoryOptionCombo deoc) {
		return pbfStore.getPbfDataElementById(de, deoc);
	}

	@Override
	public List<PbfAnalyticsReportDetails> getPbfAnalyticalReportDetails(
			Collection<OrganisationUnit> organisationUnits) {
		return pbfStore.getPbfAnalyticalReportDetails(organisationUnits);
	}

	@Override
	public List<PbfCalculation> getPbfAnalyticalReportDetails(Collection<OrganisationUnit> organisationUnits,
			int i) {
		return pbfStore.getPbfAnalyticalReportDetails(organisationUnits, i);
	}

	@Override
	public List<PbfCalculation> getPbfCalculationDetailsByReportPeriod(Period qtrPeriod) {
		return pbfStore.getPbfCalculationDetailsByReportPeriod(qtrPeriod);
	}

}
