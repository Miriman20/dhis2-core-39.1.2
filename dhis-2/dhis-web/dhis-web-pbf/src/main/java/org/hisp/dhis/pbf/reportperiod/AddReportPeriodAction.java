package org.hisp.dhis.pbf.reportperiod;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hisp.dhis.pbf.api.PbfService;
import org.hisp.dhis.pbf.model.PbfReportPeriod;
import org.hisp.dhis.i18n.I18n;
import org.hisp.dhis.i18n.I18nFormat;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.period.PeriodService;
import org.hisp.dhis.period.PeriodType;
import org.hisp.dhis.period.QuarterlyPeriodType;
import org.hisp.dhis.user.CurrentUserService;

import com.opensymphony.xwork2.Action;

/**
 * @author Latifov Murodillo Abdusamadovich
 * @version $Id:
 */
public class AddReportPeriodAction
    implements Action
{
    private static final Log log = LogFactory.getLog( AddReportPeriodAction.class );

    private static final String SEPARATOR = "_";
    
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------


    private PbfService pbfService;

    public void setPbfService(PbfService pbfService) {
		this.pbfService = pbfService;
	}
    
    private CurrentUserService currentUserService;

    public void setCurrentUserService( CurrentUserService currentUserService )
    {
        this.currentUserService = currentUserService;
    }

    private PeriodService periodService;
    
    public void setPeriodService( PeriodService periodService )
    {
        this.periodService = periodService;
    }

    private I18nFormat format;

    public void setFormat( I18nFormat format )
    {
        this.format = format;
    }

    private I18n i18n;

    public void setI18n( I18n i18n )
    {
        this.i18n = i18n;
    }
    
    // -------------------------------------------------------------------------
    // Input
    // -------------------------------------------------------------------------
	
	private String periodName;
	
	private String monthOne;
	
	private String monthTwo;
	
	private String monthThree;
	
	private String quarterlyPeriod;
	
	private String anualPeriod;
	
	private Boolean locked;
	
	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public String getAnualPeriod() {
		return anualPeriod;
	}

	public void setAnualPeriod(String anualPeriod) {
		this.anualPeriod = anualPeriod;
	}

	public String getMonthOne() {
		return monthOne;
	}

	public void setMonthOne(String monthOne) {
		this.monthOne = monthOne;
	}

	public String getMonthTwo() {
		return monthTwo;
	}

	public void setMonthTwo(String monthTwo) {
		this.monthTwo = monthTwo;
	}

	public String getMonthThree() {
		return monthThree;
	}

	public void setMonthThree(String monthThree) {
		this.monthThree = monthThree;
	}

	public String getQuarterlyPeriod() {
		return quarterlyPeriod;
	}

	public void setQuarterlyPeriod(String quarterlyPeriod) {
		this.quarterlyPeriod = quarterlyPeriod;
	}

    public Period getPrevQuarter() {
		return prevQuarter;
	}

	public void setPrevQuarter(Period prevQuarter) {
		this.prevQuarter = prevQuarter;
	}

	// -------------------------------------------------------------------------
    // Output
    // -------------------------------------------------------------------------
	private Period firstMonth;
	private Period secondMonth;
	private Period thirdMonth;
	private Period quarter;
	private Period prevQuarter;
	private Period anual;
	
    // -------------------------------------------------------------------------
    // Action implementation
    // -------------------------------------------------------------------------

	public String execute()
    {

		   PbfReportPeriod pbfReport = new PbfReportPeriod();

		   if ( monthOne != null )
	        {
	       firstMonth = PeriodType.getPeriodFromIsoString( monthOne);
	       firstMonth = periodService.reloadPeriod( firstMonth );
	        if ( firstMonth == null )
	        {
	        	log.info( "Illegal period identifier: " + monthOne );
	        }
	        }
		   if ( monthTwo != null )
	        {
		       secondMonth = PeriodType.getPeriodFromIsoString( monthTwo);
		       secondMonth = periodService.reloadPeriod( secondMonth );
		        if ( secondMonth == null )
		        {
		             log.info( "Illegal period identifier: " + monthTwo );
		        }
	        }
		   if ( monthThree != null )
	        {
			       thirdMonth = PeriodType.getPeriodFromIsoString( monthThree);
			       thirdMonth = periodService.reloadPeriod( thirdMonth );
			        if ( thirdMonth == null )
			        {
			             log.info( "Illegal period identifier: " + monthThree );
			        }		
	        }
		   if ( quarterlyPeriod != null )
	        {
				       quarter = PeriodType.getPeriodFromIsoString( quarterlyPeriod);
				       quarter = periodService.reloadPeriod( quarter );
				        if ( quarter == null )
				        {
				             log.info( "Illegal period identifier: " + quarterlyPeriod );
				        }else{
				        	QuarterlyPeriodType qpt = new QuarterlyPeriodType();
							prevQuarter = qpt.getPreviousPeriod(quarter);
				        }
	        }
		   if ( anualPeriod != null )
	        {
				        anual = PeriodType.getPeriodFromIsoString( anualPeriod);
				        anual = periodService.reloadPeriod( anual );
				        if ( anual == null )
				        {
				             log.info( "Illegal period identifier: " + anualPeriod );
				        }			        
	        }
				        String storedBy = currentUserService.getCurrentUsername();

				        Date now = new Date();

				        if ( storedBy == null )
				        {
				            storedBy = "[unknown]";
				        }
				        
				        pbfReport.setPeriodName(periodName);
				        pbfReport.setMonthOne(firstMonth);
				        pbfReport.setMonthTwo(secondMonth);
				        pbfReport.setMonthThree(thirdMonth);
				        pbfReport.setQuarterPeriod(quarter);
				        pbfReport.setPrevQuarter(prevQuarter);
				        pbfReport.setStoredBy(storedBy);
				        pbfReport.setCreated(now);
				        pbfReport.setAnualPeriod(anual);
				        
				        pbfService.addPbfReportPeriod(pbfReport);
				        
        return SUCCESS;
    }

	private String formatDateString(String externalId){
		   final String[] id = externalId.split( SEPARATOR );

	       Calendar c = Calendar.getInstance();
	       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
		   Date convertedDate = dateFormat.parse(id[1]);
	       c.setTime(convertedDate);
	       int month = c.get(Calendar.MONTH);
	       if(id[0].equalsIgnoreCase("Monthly")){
	    	   c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));   
	       }else{
	    	   if(Calendar.MONTH >= Calendar.JANUARY && Calendar.MONTH <= Calendar.MARCH)
	    		   c.set(Calendar.MONTH, 2);
	    	       if(month >= Calendar.APRIL && month <= Calendar.JUNE)
	    	    	   c.set(Calendar.MONTH, 5);
		    	   if(month >= Calendar.JULY && month <= Calendar.SEPTEMBER)
		    	    	   c.set(Calendar.MONTH, 8);   
		    	   if(month >= Calendar.OCTOBER && month <= Calendar.DECEMBER)
		    	    	   c.set(Calendar.MONTH, 11);
		    	   
	    	   c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
	       }
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		 return externalId +"_"+ c.get(Calendar.YEAR) +"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
	}
}