package org.hisp.dhis.pbf.docx.model;

import java.io.Serializable;
import java.util.Date;

import org.hisp.dhis.common.BaseIdentifiableObject;

/**
 * @author Latifov Murodillo Abdusamadovich
 * @version $Id:
 */
public class WordReport extends BaseIdentifiableObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String reportName;
	
	private Integer categoryId;
	
	private String categoryName;
	
	private String reportLocation;

	private String storedBy;
	
    private String updatedBy;
    
    private Date lastUpdated;
    
	private Date created;

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getReportLocation() {
		return reportLocation;
	}

	public void setReportLocation(String reportLocation) {
		this.reportLocation = reportLocation;
	}

	public String getStoredBy() {
		return storedBy;
	}

	public void setStoredBy(String storedBy) {
		this.storedBy = storedBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result
				+ ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result
				+ ((reportLocation == null) ? 0 : reportLocation.hashCode());
		result = prime * result
				+ ((reportName == null) ? 0 : reportName.hashCode());
		result = prime * result
				+ ((storedBy == null) ? 0 : storedBy.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
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
		WordReport other = (WordReport) obj;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (lastUpdated == null) {
			if (other.lastUpdated != null)
				return false;
		} else if (!lastUpdated.equals(other.lastUpdated))
			return false;
		if (reportLocation == null) {
			if (other.reportLocation != null)
				return false;
		} else if (!reportLocation.equals(other.reportLocation))
			return false;
		if (reportName == null) {
			if (other.reportName != null)
				return false;
		} else if (!reportName.equals(other.reportName))
			return false;
		if (storedBy == null) {
			if (other.storedBy != null)
				return false;
		} else if (!storedBy.equals(other.storedBy))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DocxReport [reportName=" + reportName + ", categoryId="
				+ categoryId + ", categoryName=" + categoryName
				+ ", reportLocation=" + reportLocation + ", storedBy="
				+ storedBy + ", updatedBy=" + updatedBy + ", lastUpdated="
				+ lastUpdated + ", created=" + created + "]";
	}

	@Override
	public String getName() {
		return reportName;
	}
}
