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

package org.hisp.dhis.pbf.api;

import java.util.Collection;
import java.util.List;

import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.common.IdentifiableObjectStore;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.pbf.model.PbfAnalyticsQualityDetails;
import org.hisp.dhis.pbf.model.PbfAnalyticsReport;
import org.hisp.dhis.pbf.model.PbfAnalyticsReportDetails;
import org.hisp.dhis.pbf.model.PbfCalculation;
import org.hisp.dhis.pbf.model.PbfDataElement;
import org.hisp.dhis.pbf.model.PbfReport;
import org.hisp.dhis.pbf.model.PbfReportPeriod;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.user.User;


/**
 * @author Latifov Murodillo
 * 
 * @version 
 */
public interface PbfStore
   // extends IdentifiableObjectStore<PbfReportPeriod>
{
    Long  addPbfReportPeriod( PbfReportPeriod pbfReport );

    void updatePbfReportPeriod( PbfReportPeriod pbfReport );

    void deletePbfReportPeriod( PbfReportPeriod pbfReport );
    
    //PbfReportPeriod getPbfReportPeriod( Long id );
    
    Collection<PbfReportPeriod> getAllPbfReportPeriods( );

	void addPbfCalculation(PbfCalculation pbfCalculation);

	PbfCalculation getPbfCalculation(Long caseId);

	void deletePbfCalculation(Long id);

	void updatePbfCalculation(PbfCalculation pbfCalculation);
	
	PbfDataElement getPbfDataElementByDeCco(DataElement dataElement,
			CategoryOptionCombo dataElementCategoryOption);

	void addPbfDataElement(PbfDataElement pbfDataElement);

	void updatePbfDataElement(PbfDataElement pbfDataElement);
	
	void deletePbfDataElement(PbfDataElement pbfDataElement);
	
	List<PbfDataElement> getAllPbfDataElements();

	List<PbfReportPeriod> getPbfReports();

	Collection<PbfReport> getPbfReportsByUserOrgUnits(PbfReportPeriod pbfReportPeriod,
			Collection<OrganisationUnit> organisationUnits);

	PbfReportPeriod getPbfReportPeriod(Long id);

	PbfReport getPbfReport(Long id);

	Collection<PbfCalculation> getPbfCalculationDetailsByReportPeriodByOrgUnit(
			PbfReportPeriod reportPeriod, OrganisationUnit orgUnit);

	PbfReport getReport(PbfReportPeriod pbfReportPeriod, OrganisationUnit ou, User user);

	Long addPbfReport(PbfReport pbfReport);

	PbfCalculation getPbfCalculationByPrimaryKeys(DataElement dataElement,
			OrganisationUnit ou,
			PbfReportPeriod reportPeriod);

	void saveOrUpdate(PbfCalculation pbfCalculation);

	void updatePbfReport(PbfReport pbfReport);

	Collection<PbfCalculation> getPbfCalculationDetailsByQuarterPeriodByOrgUnit(
			Period quarterPeriod, OrganisationUnit orgUnit);

	PbfAnalyticsReportDetails getPbfAnalyticalReportDetailsByPrimaryKeys(DataElement dataElement,
			CategoryOptionCombo optionCombo, OrganisationUnit ou, PbfReportPeriod reportPeriod);

	void saveOrUpdate(PbfAnalyticsReportDetails pbfAnalyticalReportDetails);

	PbfAnalyticsReport getAnalyticsReport(PbfReportPeriod pbfReportPeriod, OrganisationUnit ou, User user);

	Long addAnalyticsReport(PbfAnalyticsReport pbfAnalyticsReport);

	Collection<PbfAnalyticsReport> getPbfAnalyticsReportsByUserOrgUnits(PbfReportPeriod pbfReportPeriod,
			Collection<OrganisationUnit> organisationUnits);

	void updatePbfAnalyticsReport(PbfAnalyticsReport pbfAnalyticsReport);

	List<PbfAnalyticsReportDetails> getPbfAnalyticalReportDetails();

	PbfAnalyticsQualityDetails getPbfAnalyticsQualityDetailsByPrimaryKeys(DataElement dataElement,
			CategoryOptionCombo deoc, OrganisationUnit ou, PbfReportPeriod reportPeriod);

	void saveOrUpdate(PbfAnalyticsQualityDetails pbfAnQuDetails);

	List<PbfAnalyticsQualityDetails> getPbfAnalyticsQualityDetails();

	List<PbfAnalyticsQualityDetails> getPbfAnalyticsQualityDetailsForSelection(List<OrganisationUnit> units,
			List<Period> periods);

	List<PbfAnalyticsReportDetails> getPbfDistinctQuantityIndicatorNames();

	List<PbfAnalyticsReportDetails> getPbfAnalyticsQuantityDetailsForSelection(List<OrganisationUnit> units,
			List<Period> periods);

	DataElement getExtVerDataElementbyIntVerId(DataElement dataElement);

	List<PbfCalculation> getPbfQuantityDetailsForSelection(List<OrganisationUnit> units,
			Period period, DataElement de);

	List<PbfAnalyticsReportDetails> getPbfAnalyticsQuantityDetailsForSelection(List<OrganisationUnit> units,
			Period start);

	List<PbfCalculation> getPbfQuantityDetailsForSelection(List<OrganisationUnit> children, Period start);

	PbfDataElement getPbfDataElementById(DataElement de, CategoryOptionCombo deoc);

	List<PbfAnalyticsReportDetails> getPbfAnalyticalReportDetails(Collection<OrganisationUnit> organisationUnits);

	List<PbfCalculation> getPbfAnalyticalReportDetails(Collection<OrganisationUnit> organisationUnits,
			int i);

	List<PbfCalculation> getPbfCalculationDetailsByReportPeriod(Period qtrPeriod);

}
