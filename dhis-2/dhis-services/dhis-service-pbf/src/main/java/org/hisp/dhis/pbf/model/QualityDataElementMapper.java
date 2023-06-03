package org.hisp.dhis.pbf.model;

import java.io.Serializable;

import org.hisp.dhis.common.BaseIdentifiableObject;
import org.hisp.dhis.dataelement.DataElement;

public class QualityDataElementMapper extends BaseIdentifiableObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DataElement intVerDataElement;
	
	private DataElement extVerDataElement;

	public DataElement getIntVerDataElement() {
		return intVerDataElement;
	}

	public void setIntVerDataElement(DataElement intVerDataElement) {
		this.intVerDataElement = intVerDataElement;
	}

	public DataElement getExtVerDataElement() {
		return extVerDataElement;
	}

	public void setExtVerDataElement(DataElement extVerDataElement) {
		this.extVerDataElement = extVerDataElement;
	}

	@Override
	public String toString() {
		return "QualityDataElementMapper [intVerDataElement=" + intVerDataElement + ", extVerDataElement="
				+ extVerDataElement + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((extVerDataElement == null) ? 0 : extVerDataElement.hashCode());
		result = prime * result + ((intVerDataElement == null) ? 0 : intVerDataElement.hashCode());
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
		QualityDataElementMapper other = (QualityDataElementMapper) obj;
		if (extVerDataElement == null) {
			if (other.extVerDataElement != null)
				return false;
		} else if (!extVerDataElement.equals(other.extVerDataElement))
			return false;
		if (intVerDataElement == null) {
			if (other.intVerDataElement != null)
				return false;
		} else if (!intVerDataElement.equals(other.intVerDataElement))
			return false;
		return true;
	}
	
	
}
