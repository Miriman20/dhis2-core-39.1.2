package org.hisp.dhis.pbf.impex;

/*
 * Copyright (c) 2004-2016, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.category.CategoryOptionCombo;
import org.hisp.dhis.category.CategoryService;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.datavalue.DataValue;
import org.hisp.dhis.datavalue.DataValueService;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.scheduling.SchedulingManager;
import org.hisp.dhis.system.notification.Notifier;

import org.hisp.dhis.user.CurrentUserService;
import org.hisp.dhis.user.User;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.ReaderConfig;
import org.jxls.reader.XLSReadMessage;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;


/**
 * @author Latifov Murodillo A.
 */
public class ImportDataValueAction
    implements Action
{
    private static final Log log = LogFactory.getLog( ImportDataValueAction.class );

    @Autowired
    private DataValueService dataValueService;

    @Autowired
    private DataElementService dataElementService;

    @Autowired
    private CategoryService dataElementCategoryService;
    
    @Autowired
    private PeriodService periodService;
    
    @Autowired
    private OrganisationUnitService organisationUnitService;

    @Autowired
    private CurrentUserService currentUserService;
    
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private SchedulingManager scheduler;

    @Autowired
    private Notifier notifier;

    // -------------------------------------------------------------------------
    // Input
    // -------------------------------------------------------------------------

    private File upload;

    public void setUpload( File upload )
    {
        this.upload = upload;
    }

    
    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

    @Override
    public String execute()
        throws Exception
    {

    	User user =  currentUserService.getCurrentUser();
        
    	ServletContext scontext = ServletActionContext.getServletContext();
    	
    	String filePath = scontext.getRealPath("/");
  	    File xmlConfig0 = new File(filePath + "/dhis-web-pbf/impex/", "xmlConfig0.xml");

        
        InputStream inputXML0 = new BufferedInputStream(new FileInputStream(xmlConfig0));
        XLSReader mainReader0 = ReaderBuilder.buildFromXML( inputXML0 );
        InputStream inputXLS0 = new BufferedInputStream(new FileInputStream( upload ));
        
        
        MetaObject metaobject = new MetaObject();
        
        //List<ValueHolder> values = new ArrayList<ValueHolder>();
        
        Map beans0 = new HashMap();
        beans0.put("metaobject", metaobject);
        //Map beans1 = new HashMap();
        //beans1.put("values", values);
        //beans1.put("metaobject", metaobject);
        
        XLSReadStatus readStatus0 = mainReader0.read( inputXLS0, beans0);

  	    File xmlConfig1 = new File(filePath + "/dhis-web-pbf/impex/", "xmlConfig1.xml");

  	    ReaderConfig.getInstance().setUseDefaultValuesForPrimitiveTypes( true );
        
        InputStream inputXML1 = new BufferedInputStream(new FileInputStream(xmlConfig1));
        XLSReader mainReader1 = ReaderBuilder.buildFromXML( inputXML1 );
        InputStream inputXLS1 = new BufferedInputStream(new FileInputStream( upload ));
        
        XLSReadStatus readStatus1 = mainReader1.read( inputXLS1, beans0);

  	    File xmlConfig2 = new File(filePath + "/dhis-web-pbf/impex/", "xmlConfig2.xml");

        InputStream inputXML2 = new BufferedInputStream(new FileInputStream(xmlConfig2));
        XLSReader mainReader2 = ReaderBuilder.buildFromXML( inputXML2 );
        InputStream inputXLS2 = new BufferedInputStream(new FileInputStream( upload ));
        
        XLSReadStatus readStatus2 = mainReader2.read( inputXLS2, beans0);


  	    File xmlConfig3 = new File(filePath + "/dhis-web-pbf/impex/", "xmlConfig3.xml");

        InputStream inputXML3 = new BufferedInputStream(new FileInputStream(xmlConfig3));
        XLSReader mainReader3 = ReaderBuilder.buildFromXML( inputXML3 );
        InputStream inputXLS3 = new BufferedInputStream(new FileInputStream( upload ));
        
        XLSReadStatus readStatus3 = mainReader3.read( inputXLS3, beans0);

  	    File xmlConfig4 = new File(filePath + "/dhis-web-pbf/impex/", "xmlConfig4.xml");

        InputStream inputXML4 = new BufferedInputStream(new FileInputStream(xmlConfig4));
        XLSReader mainReader4 = ReaderBuilder.buildFromXML( inputXML4 );
        InputStream inputXLS4 = new BufferedInputStream(new FileInputStream( upload ));
        
        XLSReadStatus readStatus4 = mainReader4.read( inputXLS4, beans0);
             
  	    File xmlConfig5 = new File(filePath + "/dhis-web-pbf/impex/", "xmlConfig5.xml");

        InputStream inputXML5 = new BufferedInputStream(new FileInputStream(xmlConfig5));
        XLSReader mainReader5 = ReaderBuilder.buildFromXML( inputXML5 );
        InputStream inputXLS5 = new BufferedInputStream(new FileInputStream( upload ));
        
        XLSReadStatus readStatus5 = mainReader5.read( inputXLS5, beans0);
        
		/*
		 * System.out.println(metaobject.getOrgunitid());
		 * 
		 * System.out.println(metaobject.getValues().size());
		 * 
		 * System.out.println("va1 " + metaobject.getPlan1() +" de1 "
		 * +metaobject.getPlan1de()); System.out.println("va2 " + metaobject.getPlan2()
		 * +" de2 " +metaobject.getPlan2de()); System.out.println("va3 " +
		 * metaobject.getPlan3() +" de3 " +metaobject.getPlan3de());
		 * System.out.println("pc3 " + metaobject.getPhccontrol() +" de3 " +
		 * metaobject.getPhccontrolde());
		 */
        
        Period period = periodService.getPeriod(metaobject.getPeriodid());
        OrganisationUnit source = organisationUnitService.getOrganisationUnit(metaobject.getOrgunitid());	

    	//Save plan1
    	
    	DataElement dep1 = dataElementService.getDataElement(Integer.valueOf(metaobject.getPlan1de()));
    	
    	CategoryOptionCombo decop1 = null; 
    			
    	CategoryOptionCombo deacop1 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan1ac()));	
    	
    	if(source.getComment().equalsIgnoreCase("N")){
    		 decop1 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan1ccn()));	
		}else {
			 decop1 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan1cco()));
		}
    	
    	
    	DataValue dv = null;
    	
    	try{
    		dv = dataValueService.getDataValue(dep1, period, source, decop1);
    		
    		dv.getDataElement().getId();

    		//dataValueService.deleteDataValue(dv);
    		dv.setValue(metaobject.getPlan1());
    		dv.setComment("system reimport excel");
    		dv.setLastUpdated(new Date());
    		dv.setDeleted(false);
    		dataValueService.updateDataValue(dv);
    		
    	} catch (NullPointerException npe){
    		dv = new DataValue();
    		dv.setAttributeOptionCombo(deacop1);
    		dv.setDataElement(dep1);
    		dv.setCategoryOptionCombo(decop1);
    		dv.setComment("system import excel");
    		dv.setCreated(new Date());
    		dv.setLastUpdated(new Date());
    		dv.setPeriod(period);
    		dv.setSource(source);
    		dv.setStoredBy(user.getDisplayName());
    		dv.setValue(metaobject.getPlan1());
    		dv.setFollowup(false);
    		dv.setDeleted(false);

    		//System.out.println("de " + metaobject.getPlan1de() + " cc " + metaobject.getPlan1cc() + " val " + metaobject.getPlan1());
    		dataValueService.addDataValue(dv);	
    	} 

//Save plan2
    	dv = null;
        	DataElement dep2 = dataElementService.getDataElement(Integer.valueOf(metaobject.getPlan2de()));
        	
        	CategoryOptionCombo decop2 = null; 
        			
        	CategoryOptionCombo deacop2 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan2ac()));	
        	
        	if(source.getComment().equalsIgnoreCase("N")){
        		 decop2 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan2ccn()));	
    		}else {
    			 decop2 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan2cco()));
    		}    	
        	
        	try{    
            	
            	dv = dataValueService.getDataValue(dep2, period, source, decop2);

            		dv.getDataElement().getId();
            		dataValueService.deleteDataValue(dv);
            		dv.setValue(metaobject.getPlan2());
            		dv.setComment("system reimport excel");
            		dv.setLastUpdated(new Date());
            		dv.setDeleted(false);
            		dataValueService.updateDataValue(dv);
            		
        	} catch (NullPointerException npe){
        		dv = new DataValue();
        		dv.setAttributeOptionCombo(deacop2);
        		dv.setDataElement(dep2);
        		dv.setCategoryOptionCombo(decop2);
        		dv.setComment("system import excel");
        		dv.setCreated(new Date());
        		dv.setLastUpdated(new Date());
        		dv.setPeriod(period);
        		dv.setSource(source);
        		dv.setStoredBy(user.getDisplayName());
        		dv.setValue(metaobject.getPlan2());
        		dv.setFollowup(false);
        		dv.setDeleted(false);

        		//System.out.println("de " + metaobject.getPlan2de() + " cc " + metaobject.getPlan2cco() + " val " + metaobject.getPlan2());
        		dataValueService.addDataValue(dv);	
        	} 
        	
//Save plan3

        	DataElement dep3 = dataElementService.getDataElement(Integer.valueOf(metaobject.getPlan3de()));
        	
        	CategoryOptionCombo decop3 = null; 
        			
        	CategoryOptionCombo deacop3 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan3ac()));	
        	
        	if(source.getComment().equalsIgnoreCase("N")){
        		 decop3 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan3ccn()));	
    		}else {
    			 decop3 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan3cco()));
    		} 
        	
        	dv = null;
        	
        	try{
        		dv = dataValueService.getDataValue(dep3, period, source, decop3);

        		dv.getDataElement().getId();
        		dataValueService.deleteDataValue(dv);
        		dv.setValue(metaobject.getPlan3());
        		dv.setComment("system reimport excel");
        		dv.setLastUpdated(new Date());
        		dv.setDeleted(false);
        		dataValueService.updateDataValue(dv);	
        		
        	} catch (NullPointerException npe){
        		dv = new DataValue();
        		dv.setAttributeOptionCombo(deacop3);
        		dv.setDataElement(dep3);
        		dv.setCategoryOptionCombo(decop3);
        		dv.setComment("system import excel");
        		dv.setCreated(new Date());
        		dv.setLastUpdated(new Date());
        		dv.setPeriod(period);
        		dv.setSource(source);
        		dv.setStoredBy(user.getDisplayName());
        		dv.setValue(metaobject.getPlan3());
        		dv.setFollowup(false);
        		dv.setDeleted(false);

        		//System.out.println("de " + metaobject.getPlan3de() + " cc " + metaobject.getPlan3cco() + " val " + metaobject.getPlan3());
        		dataValueService.addDataValue(dv);	
        	} 
        	
        	
//Save plan4

        	DataElement dep4 = dataElementService.getDataElement(Integer.valueOf(metaobject.getPlan4de()));
        	
        	CategoryOptionCombo decop4 = null; 
        			
        	CategoryOptionCombo deacop4 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan4ac()));	
        	
        	if(source.getComment().equalsIgnoreCase("N")){
        		 decop4 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan4ccn()));	
    		}else {
    			 decop4 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan4cco()));
    		}
        	
        	dv = null;
        	
        	try{
        		dv = dataValueService.getDataValue(dep4, period, source, decop4);

        		dv.getDataElement().getId();
        		dataValueService.deleteDataValue(dv);
        		dv.setValue(metaobject.getPlan4());
        		dv.setComment("system reimport excel");
        		dv.setLastUpdated(new Date());
        		dv.setDeleted(false);
        		dataValueService.updateDataValue(dv);	
        		
        	} catch (NullPointerException npe){
        		dv = new DataValue();
        		dv.setAttributeOptionCombo(deacop4);
        		dv.setDataElement(dep4);
        		dv.setCategoryOptionCombo(decop4);
        		dv.setComment("system import excel");
        		dv.setCreated(new Date());
        		dv.setLastUpdated(new Date());
        		dv.setPeriod(period);
        		dv.setSource(source);
        		dv.setStoredBy(user.getDisplayName());
        		dv.setValue(metaobject.getPlan4());
        		dv.setFollowup(false);
        		dv.setDeleted(false);

        		//System.out.println("de " + metaobject.getPlan4de() + " cc " + metaobject.getPlan4cc() + " val " + metaobject.getPlan4());
        		dataValueService.addDataValue(dv);	
        	} 
        	
        	
//Save plan6

        	DataElement dep6 = dataElementService.getDataElement(Integer.valueOf(metaobject.getPlan6de()));
        	
        	CategoryOptionCombo decop6 = null; 
        			
        	CategoryOptionCombo deacop6 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan6ac()));	
        	
        	if(source.getComment().equalsIgnoreCase("N")){
        		 decop6 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan6ccn()));	
    		}else {
    			 decop6 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(metaobject.getPlan6cco()));
    		}
        	dv = null;
        	
        	try{
        		dv = dataValueService.getDataValue(dep6, period, source, decop6);

        		dv.getDataElement().getId();
        		dataValueService.deleteDataValue(dv);
        		dv.setValue(metaobject.getPlan6());
        		dv.setComment("system reimport excel");
        		dv.setLastUpdated(new Date());
        		dv.setDeleted(false);
        		dataValueService.updateDataValue(dv);	
        		
        	} catch (NullPointerException npe){
        		dv = new DataValue();
        		dv.setAttributeOptionCombo(deacop6);
        		dv.setDataElement(dep6);
        		dv.setCategoryOptionCombo(decop6);
        		dv.setComment("system import excel");
        		dv.setCreated(new Date());
        		dv.setLastUpdated(new Date());
        		dv.setPeriod(period);
        		dv.setSource(source);
        		dv.setStoredBy(user.getDisplayName());
        		dv.setValue(metaobject.getPlan6());
        		dv.setFollowup(false);
        		dv.setDeleted(false);

        		//System.out.println("de " + metaobject.getPlan4de() + " cc " + metaobject.getPlan4cc() + " val " + metaobject.getPlan4());
        		dataValueService.addDataValue(dv);	
        	} 
        	
        	//Save control values
        	
        	DataElement depc = dataElementService.getDataElement(metaobject.getPhccontrolde());
        	CategoryOptionCombo decopc = dataElementCategoryService.getCategoryOptionCombo(metaobject.getPhccontrolcc());
        	dv = null;
        	String truFalse = "";
        	try{
            	dv = dataValueService.getDataValue(depc, period, source, decopc);

            	if(metaobject.getPhccontrol().equalsIgnoreCase("1")){
            		truFalse="true";
            	}else{
            		truFalse="false";
            	}
            	
        		dv.getDataElement().getId();
        		dataValueService.deleteDataValue(dv);
        		dv.setValue(truFalse);
        		dv.setComment("system reimport excel");
        		dv.setLastUpdated(new Date());
        		dv.setDeleted(false);
        		dataValueService.updateDataValue(dv);

        	} catch (NullPointerException npe){
        		dv = new DataValue();
        		dv.setAttributeOptionCombo(decopc);
        		dv.setDataElement(depc);
        		dv.setCategoryOptionCombo(decopc);
        		dv.setComment("system import excel");
        		dv.setCreated(new Date());
        		dv.setLastUpdated(new Date());
        		dv.setPeriod(period);
        		dv.setSource(source);
        		dv.setStoredBy(user.getDisplayName());
        		dv.setValue(truFalse);
        		dv.setFollowup(false);
        		dv.setDeleted(false);

        		System.out.println("de " + metaobject.getPhccontrolde() + " cc " + metaobject.getPhccontrolcc() + " val " + metaobject.getPhccontrol());
        		dataValueService.addDataValue(dv);	
        	} 
        		        
        
        for (ValueHolder val : metaobject.getValues()){
        	System.out.println(val.getAtrcatcomboid() + "  " + val.getDatavalue1());

        	if(val.getDataelementid()!=null){
            	DataElement de = dataElementService.getDataElement(Integer.valueOf(val.getDataelementid()));
            	CategoryOptionCombo decoa = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(val.getAtrcatcomboid()));
        	DataValue dv1 = new DataValue();
        	
        	CategoryOptionCombo decoc1 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(val.getCatcomboid1()));

        	if(decoc1!=null){
        	dv1.setAttributeOptionCombo(decoa);
        	dv1.setDataElement(de);
        	dv1.setCategoryOptionCombo(decoc1);
        	dv1.setComment("system import excel");
        	dv1.setCreated(new Date());
        	dv1.setLastUpdated(new Date());
        	dv1.setPeriod(period);
        	dv1.setSource(source);
        	dv1.setStoredBy(user.getDisplayName());
        	dv1.setValue(val.getDatavalue1());
        	dv1.setFollowup(false);
        	dv1.setDeleted(false);
        	
        	 dv = null;
        	dv = dataValueService.getDataValue(de, period, source, decoc1);
        	try{
        		dv.getDataElement();
        		dv.setValue(dv1.getValue());
        		dv.setComment("system reimport excel");
        		dv.setLastUpdated(new Date());
        		dv.setDeleted(false);
        		dataValueService.updateDataValue(dv);	
        	} catch (NullPointerException npe){
        		System.out.println("system reimport excel de " + val.getDataelementid() + " cc " + val.getCatcomboid1() + " val " + val.getDatavalue1());
        		dataValueService.addDataValue(dv1);	
        	}
        	}
        	
        	
        	
        	if(val.getDatavalue2()!=null){
            	DataValue dv2 = new DataValue();
            	
            	CategoryOptionCombo decoc2 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(val.getCatcomboid2()));
            	if(decoc2!=null){
            	dv2.setAttributeOptionCombo(decoa);
            	dv2.setDataElement(de);
            	dv2.setCategoryOptionCombo(decoc2);
            	dv2.setComment("system import excel");
            	dv2.setCreated(new Date());
            	dv2.setLastUpdated(new Date());
            	dv2.setPeriod(period);
            	dv2.setSource(source);
            	dv2.setStoredBy(user.getDisplayName());
            	dv2.setValue(val.getDatavalue2());
            	dv2.setFollowup(false);
            	dv2.setDeleted(false);
            	
            	 dv = null;
            	dv = dataValueService.getDataValue(de, period, source, decoc2);
            	try{
            		dv.getDataElement();
            		dv.setValue(dv2.getValue());
            		dv.setComment("system reimport excel");
            		dv.setLastUpdated(new Date());
            		dv.setDeleted(false);
            		dataValueService.updateDataValue(dv);	
            	} catch (NullPointerException npe){
            		System.out.println("system reimport excel de " + val.getDataelementid() + " cc " + val.getCatcomboid2() + " val " + val.getDatavalue2());
            		dataValueService.addDataValue(dv2);	
            	}
            	
            	}
        	}
        	
			if(val.getDatavalue3()!=null){
	        	DataValue dv3 = new DataValue();
	        	
	        	CategoryOptionCombo decoc3 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(val.getCatcomboid3()));
	        	if(decoc3!=null){
	        	dv3.setAttributeOptionCombo(decoa);
	        	dv3.setDataElement(de);
	        	dv3.setCategoryOptionCombo(decoc3);
	        	dv3.setComment("system import excel");
	        	dv3.setCreated(new Date());
	        	dv3.setLastUpdated(new Date());
	        	dv3.setPeriod(period);
	        	dv3.setSource(source);
	        	dv3.setStoredBy(user.getDisplayName());
	        	dv3.setValue(val.getDatavalue3());
	        	dv3.setFollowup(false);
	        	dv3.setDeleted(false);
	        	
	        	 dv = null;
            	dv = dataValueService.getDataValue(de, period, source, decoc3);
            	try{
            		dv.getDataElement();
            		dv.setValue(dv3.getValue());
            		dv.setComment("system reimport excel");
            		dv.setLastUpdated(new Date());
            		dv.setDeleted(false);
            		dataValueService.updateDataValue(dv);	
            	} catch (NullPointerException npe){
            		System.out.println("system reimport excel de " + val.getDataelementid() + " cc " + val.getCatcomboid3() + " val " + val.getDatavalue3());
            		dataValueService.addDataValue(dv3);	
            	}    	
	        	}
			        	}
			if(val.getDatavalue4()!=null){
	        	DataValue dv4 = new DataValue();
	        	
	        	CategoryOptionCombo decoc4 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(val.getCatcomboid4()));
	        	if(decoc4!=null){
	        	dv4.setAttributeOptionCombo(decoa);
	        	dv4.setDataElement(de);
	        	dv4.setCategoryOptionCombo(decoc4);
	        	dv4.setComment("system import excel");
	        	dv4.setCreated(new Date());
	        	dv4.setLastUpdated(new Date());
	        	dv4.setPeriod(period);
	        	dv4.setSource(source);
	        	dv4.setStoredBy(user.getDisplayName());
	        	dv4.setValue(val.getDatavalue4());
	        	dv4.setFollowup(false);
	        	dv4.setDeleted(false);
	        	
	        	 dv = null;
            	dv = dataValueService.getDataValue(de, period, source, decoc4);
            	try{
            		dv.getDataElement();
            		dv.setValue(dv4.getValue());
            		dv.setComment("system reimport excel");
            		dv.setLastUpdated(new Date());
            		dv.setDeleted(false);
            		dataValueService.updateDataValue(dv);	
            	} catch (NullPointerException npe){
            		System.out.println("system reimport excel de " + val.getDataelementid() + " cc " + val.getCatcomboid4() + " val " + val.getDatavalue4());
            		dataValueService.addDataValue(dv4);	
            	} 
			}
			}
			if(val.getDatavalue5()!=null){
	        	DataValue dv5 = new DataValue();
	        	
	        	CategoryOptionCombo decoc5 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(val.getCatcomboid5()));
	        	if(decoc5!=null){
	        	dv5.setAttributeOptionCombo(decoa);
	        	dv5.setDataElement(de);
	        	dv5.setCategoryOptionCombo(decoc5);
	        	dv5.setComment("system import excel");
	        	dv5.setCreated(new Date());
	        	dv5.setLastUpdated(new Date());
	        	dv5.setPeriod(period);
	        	dv5.setSource(source);
	        	dv5.setStoredBy(user.getDisplayName());
	        	dv5.setValue(val.getDatavalue5());
	        	dv5.setFollowup(false);
	        	dv5.setDeleted(false);
	        	
	        	 dv = null;
            	dv = dataValueService.getDataValue(de, period, source, decoc5);
            	try{
            		dv.getDataElement();
            		dataValueService.deleteDataValue(dv);
            		dv.setValue(dv5.getValue());
            		dv.setComment("system reimport excel");
            		dv.setLastUpdated(new Date());
            		dv.setDeleted(false);
            		dataValueService.updateDataValue(dv);	
            	} catch (NullPointerException npe){
            		System.out.println("system reimport excel de " + val.getDataelementid() + " cc " + val.getCatcomboid5() + " val " + val.getDatavalue5());
            		dataValueService.addDataValue(dv5);	
            	} 
	        	}
			}
			if(val.getDatavalue6()!=null){
	        	DataValue dv6 = new DataValue();
	        	
	        	CategoryOptionCombo decoc6 = dataElementCategoryService.getCategoryOptionCombo(Integer.valueOf(val.getCatcomboid6()));
	        	if(decoc6!=null){
	        	dv6.setAttributeOptionCombo(decoa);
	        	dv6.setDataElement(de);
	        	dv6.setCategoryOptionCombo(decoc6);
	        	dv6.setComment("system import excel");
	        	dv6.setCreated(new Date());
	        	dv6.setLastUpdated(new Date());
	        	dv6.setPeriod(period);
	        	dv6.setSource(source);
	        	dv6.setStoredBy(user.getDisplayName());
	        	dv6.setValue(val.getDatavalue6());
	        	dv6.setFollowup(false);
	        	dv6.setDeleted(false);
	        	
	        	 dv = null;
            	dv = dataValueService.getDataValue(de, period, source, decoc6);
            	try{
            		dv.getDataElement();
            		dataValueService.deleteDataValue(dv);
            		dv.setValue(dv6.getValue());
            		dv.setComment("system reimport excel");
            		dv.setLastUpdated(new Date());
            		dv.setDeleted(false);
            		dataValueService.updateDataValue(dv);	
            	} catch (NullPointerException npe){
            		System.out.println("system reimport excel de " + val.getDataelementid() + " cc " + val.getCatcomboid6() + " val " + val.getDatavalue6());
            		dataValueService.addDataValue(dv6);	
            	} 
	        	}
			}
			
        	}
        }
        System.out.println("metaobject.getPhccontrol() " + metaobject.getPhccontrol());
        System.out.println("de " + metaobject.getPhccontrolde() + " cc " + metaobject.getPhccontrolcc() + " val " + metaobject.getPhccontrol());
        
        for (Object mes : readStatus0.getReadMessages()){
        	XLSReadMessage  m = (XLSReadMessage) mes;
        	System.out.println(m.getMessage());
        }
        
        return SUCCESS;
    }
}
