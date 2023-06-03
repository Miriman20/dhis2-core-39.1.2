package org.hisp.dhis.pbf.docx.api;

import java.util.List;

import org.hisp.dhis.common.IdentifiableObjectStore;
import org.hisp.dhis.pbf.docx.model.WordReport;


/**
 * @author Latifov Murodillo Abdusamadovich
 * 
 * @version 
 */
public interface DocxStore
    extends IdentifiableObjectStore<WordReport>
{
	void deleteDocxReport(WordReport docxReport);

	List<WordReport> getAllDocxReports();

	WordReport getDocxReport(Integer id);
    
	int addDocxReport(WordReport docxReport);
    
	void updateDocxReport(WordReport docxReport);

	List<WordReport> getAllDocxReportsOfCategory(int id);

}
