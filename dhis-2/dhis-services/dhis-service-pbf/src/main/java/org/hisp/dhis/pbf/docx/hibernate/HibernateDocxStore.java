package org.hisp.dhis.pbf.docx.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hisp.dhis.common.hibernate.HibernateIdentifiableObjectStore;
import org.hisp.dhis.pbf.docx.api.DocxStore;
import org.hisp.dhis.pbf.docx.model.WordReport;
import org.hisp.dhis.security.acl.AclService;
import org.hisp.dhis.user.CurrentUserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * @author Latifov Murodillo Abdusamadovich
 * 
 * @version $Id$
 */
public class HibernateDocxStore 
		extends HibernateIdentifiableObjectStore<WordReport> 
		implements DocxStore
{


	public HibernateDocxStore(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate,
			ApplicationEventPublisher publisher, Class<WordReport> clazz, CurrentUserService currentUserService,
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
	public void deleteDocxReport(WordReport docxReport) {
		 Session session = sessionFactory.getCurrentSession();
	        
	        session.delete( docxReport );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WordReport> getAllDocxReports() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria( WordReport.class );
		return criteria.list();
	}

	@Override
	public WordReport getDocxReport(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		
        Criteria criteria = session.createCriteria( WordReport.class );
        criteria.add( Restrictions.eq( "id", id ) );

        return (WordReport) criteria.uniqueResult();
	}

	@Override
	public int addDocxReport(WordReport docxReport) {
		Session session = sessionFactory.getCurrentSession();
        return (Integer) session.save( docxReport );
	}

	@Override
	public void updateDocxReport(WordReport docxReport) {
		Session session = sessionFactory.getCurrentSession();
        session.update( docxReport );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WordReport> getAllDocxReportsOfCategory(int id) {
		Session session = sessionFactory.getCurrentSession();
		
        Criteria criteria = session.createCriteria( WordReport.class );
        criteria.add( Restrictions.eq( "categoryId", id ) );

        return criteria.list();
	}
}
