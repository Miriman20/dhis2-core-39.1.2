package org.hisp.dhis.pbf.docx.api;

import java.util.List;

import org.hisp.dhis.pbf.docx.model.WordReport;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Latifov Murodillo Abdusamadovich
 * 
 * @version $Id$
 */
@Transactional
public class DefaultDocxService
    implements DocxService
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private DocxStore docxStore;

    public void setDocxStore( DocxStore docxStore )
    {
        this.docxStore = docxStore;
    }

	@Override
	public void deleteDocxReport(WordReport docxReport) {
		docxStore.deleteDocxReport(docxReport);
	}

	@Override
	public List<WordReport> getAllDocxReports() {
		return docxStore.getAllDocxReports();
	}

	@Override
	public WordReport getDocxReport(Integer id) {
		return docxStore.getDocxReport(id);
	}

	@Override
	public int addDocxReport(WordReport docxReport) {
		return docxStore.addDocxReport(docxReport);
	}

	@Override
	public void updateDocxReport(WordReport docxReport) {
		docxStore.updateDocxReport(docxReport);
	}

	@Override
	public List<WordReport> getAllDocxReportsOfCategory(int id) {
		return docxStore.getAllDocxReportsOfCategory(id);
	}
}
