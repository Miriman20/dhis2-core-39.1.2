package org.hisp.dhis.pbf.impex;

import java.util.ArrayList;
import java.util.List;

public class MetaObject {

	private Integer periodid;
	
	private Integer orgunitid;
	
	private Integer userid;

	private Integer datasetid;
	
	private String plan1;
	private String plan2;
	private String plan3;
	private String plan4;
	private String plan6;
	
	private String phccontrol;

	private String plan1de;
	private String plan2de;
	private String plan3de;
	private String plan4de;
	private String plan6de;
	
	private Integer phccontrolde;
	
	private String plan1ac;
	private String plan2ac;
	private String plan3ac;
	private String plan4ac;
	private String plan6ac;

	
	private String plan1cco;
	private String plan2cco;
	private String plan3cco;
	private String plan4cco;
	private String plan6cco;
	
	
	private String plan1ccn;
	private String plan2ccn;
	private String plan3ccn;
	private String plan4ccn;
	private String plan6ccn;
	
	private Integer phccontrolcc;
	
	private List<ValueHolder> values = new ArrayList<ValueHolder>();

	public MetaObject() {
		super();
	}

	public String getPlan4() {
		return plan4;
	}

	public void setPlan4(String plan4) {
		this.plan4 = plan4;
	}

	public String getPlan6() {
		return plan6;
	}

	public void setPlan6(String plan6) {
		this.plan6 = plan6;
	}

	public String getPlan4de() {
		return plan4de;
	}

	public void setPlan4de(String plan4de) {
		this.plan4de = plan4de;
	}

	public String getPlan6de() {
		return plan6de;
	}

	public void setPlan6de(String plan6de) {
		this.plan6de = plan6de;
	}

	public Integer getDatasetid() {
		return datasetid;
	}

	public void setDatasetid(Integer datasetid) {
		this.datasetid = datasetid;
	}

	public Integer getPeriodid() {
		return periodid;
	}

	public void setPeriodid(Integer periodid) {
		this.periodid = periodid;
	}

	public Integer getOrgunitid() {
		return orgunitid;
	}

	public void setOrgunitid(Integer orgunitid) {
		this.orgunitid = orgunitid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public List<ValueHolder> getValues() {
		return values;
	}

	public void setValues(List<ValueHolder> values) {
		this.values = values;
	}

	public String getPlan1() {
		return plan1;
	}

	public void setPlan1(String plan1) {
		this.plan1 = plan1;
	}

	public String getPlan2() {
		return plan2;
	}

	public void setPlan2(String plan2) {
		this.plan2 = plan2;
	}

	public String getPlan3() {
		return plan3;
	}

	public void setPlan3(String plan3) {
		this.plan3 = plan3;
	}

	public String getPhccontrol() {
		return phccontrol;
	}

	public void setPhccontrol(String phccontrol) {
		this.phccontrol = phccontrol;
	}

	public String getPlan1de() {
		return plan1de;
	}

	public void setPlan1de(String plan1de) {
		this.plan1de = plan1de;
	}

	public String getPlan2de() {
		return plan2de;
	}

	public void setPlan2de(String plan2de) {
		this.plan2de = plan2de;
	}

	public String getPlan3de() {
		return plan3de;
	}

	public void setPlan3de(String plan3de) {
		this.plan3de = plan3de;
	}

	public Integer getPhccontrolde() {
		return phccontrolde;
	}

	public void setPhccontrolde(Integer phccontrolde) {
		this.phccontrolde = phccontrolde;
	}

	public Integer getPhccontrolcc() {
		return phccontrolcc;
	}

	public void setPhccontrolcc(Integer phccontrolcc) {
		this.phccontrolcc = phccontrolcc;
	}

	public String getPlan1ac() {
		return plan1ac;
	}

	public void setPlan1ac(String plan1ac) {
		this.plan1ac = plan1ac;
	}

	public String getPlan2ac() {
		return plan2ac;
	}

	public void setPlan2ac(String plan2ac) {
		this.plan2ac = plan2ac;
	}

	public String getPlan3ac() {
		return plan3ac;
	}

	public void setPlan3ac(String plan3ac) {
		this.plan3ac = plan3ac;
	}

	public String getPlan4ac() {
		return plan4ac;
	}

	public void setPlan4ac(String plan4ac) {
		this.plan4ac = plan4ac;
	}

	public String getPlan6ac() {
		return plan6ac;
	}

	public void setPlan6ac(String plan6ac) {
		this.plan6ac = plan6ac;
	}

	public String getPlan1cco() {
		return plan1cco;
	}

	public void setPlan1cco(String plan1cco) {
		this.plan1cco = plan1cco;
	}

	public String getPlan2cco() {
		return plan2cco;
	}

	public void setPlan2cco(String plan2cco) {
		this.plan2cco = plan2cco;
	}

	public String getPlan3cco() {
		return plan3cco;
	}

	public void setPlan3cco(String plan3cco) {
		this.plan3cco = plan3cco;
	}

	public String getPlan4cco() {
		return plan4cco;
	}

	public void setPlan4cco(String plan4cco) {
		this.plan4cco = plan4cco;
	}

	public String getPlan6cco() {
		return plan6cco;
	}

	public void setPlan6cco(String plan6cco) {
		this.plan6cco = plan6cco;
	}

	public String getPlan1ccn() {
		return plan1ccn;
	}

	public void setPlan1ccn(String plan1ccn) {
		this.plan1ccn = plan1ccn;
	}

	public String getPlan2ccn() {
		return plan2ccn;
	}

	public void setPlan2ccn(String plan2ccn) {
		this.plan2ccn = plan2ccn;
	}

	public String getPlan3ccn() {
		return plan3ccn;
	}

	public void setPlan3ccn(String plan3ccn) {
		this.plan3ccn = plan3ccn;
	}

	public String getPlan4ccn() {
		return plan4ccn;
	}

	public void setPlan4ccn(String plan4ccn) {
		this.plan4ccn = plan4ccn;
	}

	public String getPlan6ccn() {
		return plan6ccn;
	}

	public void setPlan6ccn(String plan6ccn) {
		this.plan6ccn = plan6ccn;
	}
	
}
