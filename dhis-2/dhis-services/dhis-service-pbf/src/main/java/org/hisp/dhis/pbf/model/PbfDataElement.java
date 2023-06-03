package org.hisp.dhis.pbf.model;

import java.io.Serializable;
import java.util.Date;

import org.hisp.dhis.common.BaseIdentifiableObject;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.period.Period;

public class PbfDataElement  extends BaseIdentifiableObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int sortOrder;
	//internal verification DE
	private DataElement intVerDataElement;
	//facility DE
	private DataElement facilityDataElement;
	//pay adjustment de
	private DataElement auditDataElement;
	//facility control by district de
	private DataElement currationDataElement;
	//amount to pay control
	private DataElement currationAmountDataElement;
	//quality total score de
	private DataElement qualityScoreDataElement;
	//external verification DE
	private DataElement ExtVerDataElement;
	
	//Demography data elements
	
	private DataElement poulationDataElement;
	private DataElement childrenUnderOneDataElement;
	private DataElement childrenOneFiveDataElement;
	private DataElement reprodWomanDataElement;
	private DataElement pregWomanDataElement;
	
	//
	private CategoryOptionCombo optionCombo;
	//optioncombo month one male
	private CategoryOptionCombo optionComboMom;
	//optioncombo month one female
	private CategoryOptionCombo optionComboMof;
	//optioncombo month two male
	private CategoryOptionCombo optionComboMtm;
	//optioncombo month two female
	private CategoryOptionCombo optionComboMtf;
	//optioncombo month three male
	private CategoryOptionCombo optionComboMhm;
	//optioncombo month three female
	private CategoryOptionCombo optionComboMhf;
	//data set for health center
	private DataSet facQtyDataSet;
	//data set for health house int ver
	private DataSet intVerQtyDataSet;
	//data set for health house ext ver
	private DataSet extVerQtyDataSet;
	//data set for health center internal verification
	private DataSet hcDataSetIntVer;
	//data set for health house internal verification
	private DataSet hhDataSetIntVer;
	//data set for health center external verification
	private DataSet hcDataSetExtVer;
	//data set for health house external verification
	private DataSet hhDataSetExtVer;
	// option combo for max quarter score
	private CategoryOptionCombo qScoreOptionCombo;
	// option combo for internal verification quarter score	
	private CategoryOptionCombo qIntOptionCombo;
	// option combo for external verification quarter score
	private CategoryOptionCombo qExtOptionCombo;
	//org unit that holds default global values
	private OrganisationUnit providerOrgUnit;
	//period quarter for max data set should be replaced
	private Period periodQuarterMax;
	
	private String calculationFormulaRphc;
	
	private String calculationFormulaPhc;
	
    private String storedBy;
    
	private Date created;

	public DataSet getFacQtyDataSet() {
		return facQtyDataSet;
	}

	public void setFacQtyDataSet(DataSet facQtyDataSet) {
		this.facQtyDataSet = facQtyDataSet;
	}

	public DataSet getIntVerQtyDataSet() {
		return intVerQtyDataSet;
	}

	public void setIntVerQtyDataSet(DataSet intVerQtyDataSet) {
		this.intVerQtyDataSet = intVerQtyDataSet;
	}

	public DataSet getExtVerQtyDataSet() {
		return extVerQtyDataSet;
	}

	public void setExtVerQtyDataSet(DataSet extVerQtyDataSet) {
		this.extVerQtyDataSet = extVerQtyDataSet;
	}

	public OrganisationUnit getProviderOrgUnit() {
		return providerOrgUnit;
	}

	public void setProviderOrgUnit(OrganisationUnit providerOrgUnit) {
		this.providerOrgUnit = providerOrgUnit;
	}

	public CategoryOptionCombo getqScoreOptionCombo() {
		return qScoreOptionCombo;
	}

	public void setqScoreOptionCombo(CategoryOptionCombo qScoreOptionCombo) {
		this.qScoreOptionCombo = qScoreOptionCombo;
	}

	public CategoryOptionCombo getqIntOptionCombo() {
		return qIntOptionCombo;
	}

	public void setqIntOptionCombo(CategoryOptionCombo qIntOptionCombo) {
		this.qIntOptionCombo = qIntOptionCombo;
	}

	public CategoryOptionCombo getqExtOptionCombo() {
		return qExtOptionCombo;
	}

	public void setqExtOptionCombo(CategoryOptionCombo qExtOptionCombo) {
		this.qExtOptionCombo = qExtOptionCombo;
	}

	public Period getPeriodQuarterMax() {
		return periodQuarterMax;
	}

	public void setPeriodQuarterMax(Period periodQuarterMax) {
		this.periodQuarterMax = periodQuarterMax;
	}

	public DataSet getHcDataSetIntVer() {
		return hcDataSetIntVer;
	}

	public void setHcDataSetIntVer(DataSet hcDataSetIntVer) {
		this.hcDataSetIntVer = hcDataSetIntVer;
	}

	public DataSet getHhDataSetIntVer() {
		return hhDataSetIntVer;
	}

	public void setHhDataSetIntVer(DataSet hhDataSetIntVer) {
		this.hhDataSetIntVer = hhDataSetIntVer;
	}

	public DataSet getHcDataSetExtVer() {
		return hcDataSetExtVer;
	}

	public void setHcDataSetExtVer(DataSet hcDataSetExtVer) {
		this.hcDataSetExtVer = hcDataSetExtVer;
	}

	public DataSet getHhDataSetExtVer() {
		return hhDataSetExtVer;
	}

	public void setHhDataSetExtVer(DataSet hhDataSetExtVer) {
		this.hhDataSetExtVer = hhDataSetExtVer;
	}

	public DataElement getPoulationDataElement() {
		return poulationDataElement;
	}

	public void setPoulationDataElement(DataElement poulationDataElement) {
		this.poulationDataElement = poulationDataElement;
	}

	public DataElement getChildrenUnderOneDataElement() {
		return childrenUnderOneDataElement;
	}

	public void setChildrenUnderOneDataElement(DataElement childrenUnderOneDataElement) {
		this.childrenUnderOneDataElement = childrenUnderOneDataElement;
	}

	public DataElement getChildrenOneFiveDataElement() {
		return childrenOneFiveDataElement;
	}

	public void setChildrenOneFiveDataElement(DataElement childrenOneFiveDataElement) {
		this.childrenOneFiveDataElement = childrenOneFiveDataElement;
	}

	public DataElement getReprodWomanDataElement() {
		return reprodWomanDataElement;
	}

	public void setReprodWomanDataElement(DataElement reprodWomanDataElement) {
		this.reprodWomanDataElement = reprodWomanDataElement;
	}

	public DataElement getPregWomanDataElement() {
		return pregWomanDataElement;
	}

	public void setPregWomanDataElement(DataElement pregWomanDataElement) {
		this.pregWomanDataElement = pregWomanDataElement;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public DataElement getIntVerDataElement() {
		return intVerDataElement;
	}

	public void setIntVerDataElement(DataElement intVerDataElement) {
		this.intVerDataElement = intVerDataElement;
	}

	public DataElement getExtVerDataElement() {
		return ExtVerDataElement;
	}

	public void setExtVerDataElement(DataElement extVerDataElement) {
		ExtVerDataElement = extVerDataElement;
	}

	public DataElement getFacilityDataElement() {
		return facilityDataElement;
	}

	public void setFacilityDataElement(DataElement facilityDataElement) {
		this.facilityDataElement = facilityDataElement;
	}

	public DataElement getAuditDataElement() {
		return auditDataElement;
	}

	public void setAuditDataElement(DataElement auditDataElement) {
		this.auditDataElement = auditDataElement;
	}

	public DataElement getCurrationDataElement() {
		return currationDataElement;
	}

	public void setCurrationDataElement(DataElement currationDataElement) {
		this.currationDataElement = currationDataElement;
	}

	public DataElement getCurrationAmountDataElement() {
		return currationAmountDataElement;
	}

	public void setCurrationAmountDataElement(DataElement currationAmountDataElement) {
		this.currationAmountDataElement = currationAmountDataElement;
	}


	public DataElement getQualityScoreDataElement() {
		return qualityScoreDataElement;
	}

	public void setQualityScoreDataElement(DataElement qualityScoreDataElement) {
		this.qualityScoreDataElement = qualityScoreDataElement;
	}

	public CategoryOptionCombo getOptionCombo() {
		return optionCombo;
	}

	public void setOptionCombo(CategoryOptionCombo optionCombo) {
		this.optionCombo = optionCombo;
	}

	public CategoryOptionCombo getOptionComboMom() {
		return optionComboMom;
	}

	public void setOptionComboMom(CategoryOptionCombo optionComboMom) {
		this.optionComboMom = optionComboMom;
	}

	public CategoryOptionCombo getOptionComboMof() {
		return optionComboMof;
	}

	public void setOptionComboMof(CategoryOptionCombo optionComboMof) {
		this.optionComboMof = optionComboMof;
	}

	public CategoryOptionCombo getOptionComboMtm() {
		return optionComboMtm;
	}

	public void setOptionComboMtm(CategoryOptionCombo optionComboMtm) {
		this.optionComboMtm = optionComboMtm;
	}

	public CategoryOptionCombo getOptionComboMtf() {
		return optionComboMtf;
	}

	public void setOptionComboMtf(CategoryOptionCombo optionComboMtf) {
		this.optionComboMtf = optionComboMtf;
	}

	public CategoryOptionCombo getOptionComboMhm() {
		return optionComboMhm;
	}

	public void setOptionComboMhm(CategoryOptionCombo optionComboMhm) {
		this.optionComboMhm = optionComboMhm;
	}

	public CategoryOptionCombo getOptionComboMhf() {
		return optionComboMhf;
	}

	public void setOptionComboMhf(CategoryOptionCombo optionComboMhf) {
		this.optionComboMhf = optionComboMhf;
	}

	public String getCalculationFormulaRphc() {
		return calculationFormulaRphc;
	}

	public void setCalculationFormulaRphc(String calculationFormulaRphc) {
		this.calculationFormulaRphc = calculationFormulaRphc;
	}

	public String getCalculationFormulaPhc() {
		return calculationFormulaPhc;
	}

	public void setCalculationFormulaPhc(String calculationFormulaPhc) {
		this.calculationFormulaPhc = calculationFormulaPhc;
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

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		
		result = prime * result
				+ ((intVerDataElement == null) ? 0 : intVerDataElement.hashCode());
		result = prime * result
				+ ((optionCombo == null) ? 0 : optionCombo.hashCode());
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
		PbfDataElement other = (PbfDataElement) obj;
		if (intVerDataElement == null) {
			if (other.intVerDataElement != null)
				return false;
		} else if (!intVerDataElement.equals(other.intVerDataElement))
			return false;
		if (optionCombo == null) {
			if (other.optionCombo != null)
				return false;
		} else if (!optionCombo.equals(other.optionCombo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PbfDataElement [intVerDataElement=" + intVerDataElement + ", optionCombo="
				+ optionCombo +  "]";
	}
	
	
}
