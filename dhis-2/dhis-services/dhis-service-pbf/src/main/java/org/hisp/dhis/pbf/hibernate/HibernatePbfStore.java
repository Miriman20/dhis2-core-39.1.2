package org.hisp.dhis.pbf.hibernate;

/*
 * Copyright (c) 2004-2009, University of Oslo
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hisp.dhis.common.hibernate.HibernateIdentifiableObjectStore;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.pbf.api.PbfStore;
import org.hisp.dhis.pbf.model.PbfAnalyticsQualityDetails;
import org.hisp.dhis.pbf.model.PbfAnalyticsReport;
import org.hisp.dhis.pbf.model.PbfAnalyticsReportDetails;
import org.hisp.dhis.pbf.model.PbfCalculation;
import org.hisp.dhis.pbf.model.PbfDataElement;
import org.hisp.dhis.pbf.model.PbfReport;
import org.hisp.dhis.pbf.model.PbfReportPeriod;
import org.hisp.dhis.pbf.model.QualityDataElementMapper;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.security.acl.AclService;
import org.hisp.dhis.user.CurrentUserService;
import org.hisp.dhis.user.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * @author Latifov Murodillo Abdusamadovich
 * 
 * @version $Id$
 */
public class HibernatePbfStore 
		extends HibernateIdentifiableObjectStore<PbfReportPeriod> 
		implements PbfStore
{

	public HibernatePbfStore(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate,
			ApplicationEventPublisher publisher, Class<PbfReportPeriod> clazz, CurrentUserService currentUserService,
			AclService aclService, boolean cacheable) {
		super(sessionFactory, jdbcTemplate, publisher, clazz, currentUserService, aclService, cacheable);
		// TODO Auto-generated constructor stub
	}
	
	// ----------------------------------------------------------------------
	// Dependencies
	// ----------------------------------------------------------------------

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long addPbfReportPeriod(PbfReportPeriod pbfReport) {
		Session session = getSession();
        return (Long) session.save( pbfReport );
	}

	@Override
	public void deletePbfReportPeriod(PbfReportPeriod pbfReport) {
        Session session = getSession();
        session.delete( pbfReport );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PbfReportPeriod> getAllPbfReportPeriods() {
        Session session = getSession();
        return session.createCriteria( PbfReportPeriod.class ).list();
	}

	@Override
	public void addPbfCalculation(PbfCalculation pbfCalculation) {
		Session session = getSession();
        session.save( pbfCalculation );
	}

	@Override
	public PbfCalculation getPbfCalculation(Long id) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.eq( "id", id ) );

        return (PbfCalculation) criteria.uniqueResult();
	}

	@Override
	public void deletePbfCalculation(Long id) {
        Session session = getSession();
        
        session.delete( getPbfCalculation(id) );
	}

	@Override
	public void updatePbfCalculation(PbfCalculation pbfCalculation) {
        Session session = getSession();
        
        session.update( pbfCalculation );
	}

	@Override
	public void updatePbfReportPeriod(PbfReportPeriod pbfReport) {
		 Session session = getSession();
	        
	        session.update( pbfReport );
	}

	/*
	 * @Override public PbfReportPeriod getPbfReportPeriod(Long id) { Session
	 * session = getSession();
	 * 
	 * Criteria criteria = session.createCriteria( PbfReportPeriod.class );
	 * criteria.add( Restrictions.eq( "id", id ) );
	 * 
	 * return (PbfReportPeriod) criteria.uniqueResult();
	 * 
	 * }
	 */

	@Override
	public PbfDataElement getPbfDataElementByDeCco(DataElement dataElement,
			CategoryOptionCombo dataElementCategoryOption) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfDataElement.class );
        criteria.add( Restrictions.eq( "dataElement", dataElement ) );
        criteria.add( Restrictions.eq( "optionCombo", dataElementCategoryOption ) );

        return (PbfDataElement) criteria.uniqueResult();
	}

	@Override
	public void addPbfDataElement(PbfDataElement pbfDataElement) {
		Session session = getSession();
        session.save( pbfDataElement );
	}

	@Override
	public void updatePbfDataElement(PbfDataElement pbfDataElement) {
        Session session = getSession();
        session.update( pbfDataElement );
	}

	@Override
	public void deletePbfDataElement(PbfDataElement pbfDataElement) {
		 Session session = getSession();
	        
	        session.delete( pbfDataElement );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PbfDataElement> getAllPbfDataElements() {
		Session session = getSession();
		Criteria criteria = session.createCriteria( PbfDataElement.class );
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PbfReportPeriod> getPbfReports() {
		Session session = getSession();
		Criteria criteria = session.createCriteria( PbfReportPeriod.class );
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PbfReport> getPbfReportsByUserOrgUnits(PbfReportPeriod pbfReportPeriod,
			Collection<OrganisationUnit> organisationUnits) {
		
		if (organisationUnits == null || organisationUnits.isEmpty() )
        {
            return Collections.emptySet();
        }
		
		Session session = getSession();
		Criteria criteria = session.createCriteria( PbfReport.class );
		criteria.add( Restrictions.eq( "reportPeriod", pbfReportPeriod ) );
		criteria.add( Restrictions.in( "orgUnit", organisationUnits ) );
        //criteria.add( Property.forName("orgUnit").in(organisationUnits));
		return criteria.list();
	}

	@Override
	public PbfReportPeriod getPbfReportPeriod(Long id) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfReportPeriod.class );
        criteria.add( Restrictions.eq( "id", id ) );

        return (PbfReportPeriod) criteria.uniqueResult();
	}

	@Override
	public PbfReport getPbfReport(Long id) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfReport.class );
        criteria.add( Restrictions.eq( "id", id ) );

        return (PbfReport) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PbfCalculation> getPbfCalculationDetailsByReportPeriodByOrgUnit(
			PbfReportPeriod reportPeriod, OrganisationUnit orgUnit) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.eq( "periodQuarter", reportPeriod ) );
        criteria.add( Restrictions.eq( "orgUnit", orgUnit ) );

        return criteria.list();
	}

	@Override
	public PbfReport getReport(PbfReportPeriod pbfReportPeriod,
			OrganisationUnit ou, User user) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfReport.class );
        criteria.add( Restrictions.eq( "reportPeriod", pbfReportPeriod ) );
        criteria.add( Restrictions.eq( "orgUnit", ou ) );
        
        PbfReport pbfr = (PbfReport) criteria.uniqueResult();
        
        if ( pbfr == null )
        {
        	pbfr = new PbfReport();
        	pbfr.setOrgUnit(ou);
        	pbfr.setReportName(ou.getName()+" - "+ pbfReportPeriod.getPeriodName());
        	pbfr.setReportPeriod(pbfReportPeriod);
        	pbfr.setReportCalcDetails("not_calculated");
        	pbfr.setCreated(new Date());
        	pbfr.setStoredBy(user.getUsername());
            addPbfReport( pbfr );

            return pbfr;
        }
        return pbfr;
	}

	@Override
	public Long addPbfReport(PbfReport pbfReport) {
		Session session = getSession();
        return (Long) session.save( pbfReport );
	}

	@Override
	public PbfCalculation getPbfCalculationByPrimaryKeys(
			DataElement dataElement, OrganisationUnit ou,
			PbfReportPeriod reportPeriod) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.eq( "dataElement", dataElement ) );
        
        criteria.add( Restrictions.eq( "orgUnit", ou ) );
        criteria.add( Restrictions.eq( "periodQuarter", reportPeriod ) );

        return (PbfCalculation) criteria.uniqueResult();
	}

	@Override
	public void saveOrUpdate(PbfCalculation pbfCalculation) {
		Session session = getSession();
        session.saveOrUpdate( pbfCalculation );
	}

	@Override
	public void updatePbfReport(PbfReport pbfReport) {
		Session session = getSession();
        session.update( pbfReport );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PbfCalculation> getPbfCalculationDetailsByQuarterPeriodByOrgUnit(
			Period quarterPeriod, OrganisationUnit orgUnit) {
Session session = getSession();

/*System.out.println("per: " + quarterPeriod.getId());
System.out.println("ou: " + orgUnit.getId());
*/
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.eq( "orgUnit", orgUnit ) );
        criteria.add( Restrictions.eq( "periodQuarter", quarterPeriod ) );

        return criteria.list();
	}

	@Override
	public PbfAnalyticsReportDetails getPbfAnalyticalReportDetailsByPrimaryKeys(DataElement dataElement,
			CategoryOptionCombo optionCombo, OrganisationUnit ou, PbfReportPeriod reportPeriod) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfAnalyticsReportDetails.class );
        criteria.add( Restrictions.eq( "dataElement", dataElement ) );
        criteria.add( Restrictions.eq( "optionCombo", optionCombo ) );
        criteria.add( Restrictions.eq( "orgUnit", ou ) );
        criteria.add( Restrictions.eq( "periodQuarter", reportPeriod ) );

        return (PbfAnalyticsReportDetails) criteria.uniqueResult();
	}

	@Override
	public void saveOrUpdate(PbfAnalyticsReportDetails pbfAnalyticalReportDetails) {
		Session session = getSession();
        session.saveOrUpdate( pbfAnalyticalReportDetails );
	}

	@Override
	public PbfAnalyticsReport getAnalyticsReport(PbfReportPeriod pbfReportPeriod, OrganisationUnit ou, User user) {
Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfAnalyticsReport.class );
        criteria.add( Restrictions.eq( "reportPeriod", pbfReportPeriod ) );
        criteria.add( Restrictions.eq( "orgUnit", ou ) );
        
        PbfAnalyticsReport pbfr = (PbfAnalyticsReport) criteria.uniqueResult();
        
        if ( pbfr == null )
        {
        	pbfr = new PbfAnalyticsReport();
        	pbfr.setOrgUnit(ou);
        	pbfr.setReportName(ou.getName()+" - "+ pbfReportPeriod.getPeriodName());
        	pbfr.setReportPeriod(pbfReportPeriod);
        	pbfr.setReportCalcDetails("not_calculated");
        	pbfr.setCreated(new Date());
        	pbfr.setStoredBy(user.getUsername());
            addAnalyticsReport( pbfr );
        }
        return pbfr;
	}
	
	@Override
	public Long addAnalyticsReport(PbfAnalyticsReport pbfAnalyticsReport) {
		Session session = getSession();
        return (Long) session.save( pbfAnalyticsReport );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PbfAnalyticsReport> getPbfAnalyticsReportsByUserOrgUnits(PbfReportPeriod pbfReportPeriod,
			Collection<OrganisationUnit> organisationUnits) {

		if (organisationUnits == null || organisationUnits.isEmpty() )
        {
            return Collections.emptySet();
        }
		
		Session session = getSession();
		Criteria criteria = session.createCriteria( PbfAnalyticsReport.class );
		criteria.add( Restrictions.eq( "reportPeriod", pbfReportPeriod ) );
		criteria.add( Restrictions.in( "orgUnit", organisationUnits ) );

		return criteria.list();
	}

	@Override
	public void updatePbfAnalyticsReport(PbfAnalyticsReport pbfAnalyticsReport) {
		Session session = getSession();
        session.update( pbfAnalyticsReport );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PbfAnalyticsReportDetails> getPbfAnalyticalReportDetails() {
		Session session = getSession();
		Criteria criteria = session.createCriteria( PbfAnalyticsReportDetails.class );
		return criteria.list();
	}

	@Override
	public PbfAnalyticsQualityDetails getPbfAnalyticsQualityDetailsByPrimaryKeys(DataElement dataElement,
			CategoryOptionCombo deoc, OrganisationUnit ou, PbfReportPeriod reportPeriod) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfAnalyticsReportDetails.class );
        criteria.add( Restrictions.eq( "dataElement", dataElement ) );
        criteria.add( Restrictions.eq( "optionCombo", deoc ) );
        criteria.add( Restrictions.eq( "orgUnit", ou ) );
        criteria.add( Restrictions.eq( "periodQuarter", reportPeriod ) );

        return (PbfAnalyticsQualityDetails) criteria.uniqueResult();
	}

	@Override
	public void saveOrUpdate(PbfAnalyticsQualityDetails pbfAnQuDetails) {
		Session session = getSession();
        session.saveOrUpdate( pbfAnQuDetails );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PbfAnalyticsQualityDetails> getPbfAnalyticsQualityDetails() {
		Session session = getSession();
		Criteria criteria = session.createCriteria( PbfAnalyticsQualityDetails.class );
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PbfAnalyticsQualityDetails> getPbfAnalyticsQualityDetailsForSelection(
			List<OrganisationUnit> units, List<Period> periods) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfAnalyticsQualityDetails.class );
        criteria.add( Restrictions.in( "id.orgUnit", units ) );
        criteria.add( Restrictions.in( "id.periodQuarter", periods ) );

        return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PbfAnalyticsReportDetails> getPbfDistinctQuantityIndicatorNames() {
Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfAnalyticsReportDetails.class );
        criteria.setProjection(Projections.distinct(Projections.property("indicatorName")));
        //criteria.add( Restrictions.in( "id.orgUnit", units ) );
        //criteria.add( Restrictions.in( "id.periodQuarter", periods ) );

        return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PbfAnalyticsReportDetails> getPbfAnalyticsQuantityDetailsForSelection(
			List<OrganisationUnit> units, List<Period> periods) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfAnalyticsReportDetails.class );
        criteria.add( Restrictions.in( "id.orgUnit", units ) );
        criteria.add( Restrictions.in( "id.periodQuarter", periods ) );

        return criteria.list();
	}

	@Override
	public DataElement getExtVerDataElementbyIntVerId(DataElement dataElement) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( QualityDataElementMapper.class );
        criteria.add( Restrictions.eq( "intVerDataElement", dataElement ) );
        
        QualityDataElementMapper mapp = (QualityDataElementMapper) criteria.uniqueResult(); 
        if(mapp!=null){
        return mapp.getExtVerDataElement();
        }else{
        	return null;
        }
	}

	@Override
	public List<PbfCalculation> getPbfQuantityDetailsForSelection(List<OrganisationUnit> units,
			Period period, DataElement de) {
		
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.in( "orgUnit", units ) );
        criteria.add( Restrictions.eq( "periodQuarter", period ) );
        criteria.add( Restrictions.eq( "dataElement", de ) );
        return criteria.list();
	}

	@Override
	public List<PbfAnalyticsReportDetails> getPbfAnalyticsQuantityDetailsForSelection(List<OrganisationUnit> units,
			Period start) {
		
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.in( "orgUnit", units ) );
        criteria.add( Restrictions.eq( "periodQuarter", start ) );
        return criteria.list();
	}

	@Override
	public List<PbfCalculation> getPbfQuantityDetailsForSelection(List<OrganisationUnit> children, Period start) {
		
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.in( "orgUnit", children ) );
        criteria.add( Restrictions.eq( "periodQuarter", start ) );
        return criteria.list();
	}

	@Override
	public PbfDataElement getPbfDataElementById(DataElement de, CategoryOptionCombo deoc) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfDataElement.class );
        criteria.add( Restrictions.eq( "intVerDataElement", de ) );
        criteria.add( Restrictions.eq( "optionCombo", deoc ) );

        return (PbfDataElement) criteria.uniqueResult();
	}

	@Override
	public List<PbfAnalyticsReportDetails> getPbfAnalyticalReportDetails(
			Collection<OrganisationUnit> organisationUnits) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.in( "orgUnit", organisationUnits ) );
        return criteria.list();
	}

	@Override
	public List<PbfCalculation> getPbfAnalyticalReportDetails(Collection<OrganisationUnit> organisationUnits,
			int i) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.in( "orgUnit", organisationUnits ) );
        criteria.add( Restrictions.eq( "sortOrder", i ) );
        return criteria.list();
	}

	@Override
	public List<PbfCalculation> getPbfCalculationDetailsByReportPeriod(Period qtrPeriod) {
		Session session = getSession();
		
        Criteria criteria = session.createCriteria( PbfCalculation.class );
        criteria.add( Restrictions.eq( "periodQuarter", qtrPeriod ) );
        return criteria.list();
	}

}
