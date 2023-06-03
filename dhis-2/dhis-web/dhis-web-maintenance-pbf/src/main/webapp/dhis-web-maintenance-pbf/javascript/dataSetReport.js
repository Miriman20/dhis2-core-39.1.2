var currentPeriodOffset = 0;
var periodTypeFactory = new PeriodType();

//------------------------------------------------------------------------------
// Get and set methods
//------------------------------------------------------------------------------

function getDataSetReport()
{
    var dataSetReport = {
        ds: $( "#dataSetId" ).val(),
        periodType: $( "#periodType" ).val(),
        pe: $( "#periodId" ).val(),
        offset: currentPeriodOffset
    };
    
    var groups = "";
    
    $( "[name='groupSet']" ).each( function( index, value ) {
    	var item = $( this ).val();
    	if ( item )
    	{
    		groups += item + ";";
    	}
    } );
    
    if ( groups )
    {
    	dataSetReport["groups"] = groups;
    }
    
    return dataSetReport;
}

function setDataSetReport( dataSetReport )
{
	$( "#dataSetId" ).val( dataSetReport.dataSet );
	$( "#periodType" ).val( dataSetReport.periodType );
	
	currentPeriodOffset = dataSetReport.offset;
	
	displayPeriods();
	$( "#periodId" ).val( dataSetReport.period );
	
	selectionTreeSelection.setMultipleSelectionAllowed( false );
	selectionTree.buildSelectionTree();
		
	$( "body" ).on( "oust.selected", function() 
	{
		$( "body" ).off( "oust.selected" );
		validateDataSetReport();
	} );
}

//------------------------------------------------------------------------------
// Period
//------------------------------------------------------------------------------

function displayPeriods()
{
    var periodType = $( "#periodType" ).val();
    var periods = periodTypeFactory.get( periodType ).generatePeriods( currentPeriodOffset );
    periods = periodTypeFactory.reverse( periods );
    periods = periodTypeFactory.filterFuturePeriodsExceptCurrent( periods );

    $( "#periodId" ).removeAttr( "disabled" );
    clearListById( "periodId" );

    for ( i in periods )
    {
        addOptionById( "periodId", periods[i].iso, periods[i].name );
    }
}


function displayEndPeriods()
{
    var periodType = $( "#endPeriodType" ).val();
    var periods = periodTypeFactory.get( periodType ).generatePeriods( currentPeriodOffset );
    periods = periodTypeFactory.reverse( periods );
    periods = periodTypeFactory.filterFuturePeriodsExceptCurrent( periods );

    $( "#endPeriodId" ).removeAttr( "disabled" );
    clearListById( "endPeriodId" );

    for ( i in periods )
    {
        addOptionById( "endPeriodId", periods[i].iso, periods[i].name );
    }
}


function displayNextPeriods()
{
    if ( currentPeriodOffset < 0 ) // Cannot display future periods
    {
        currentPeriodOffset++;
        displayPeriods();
    }
}

function displayPreviousPeriods()
{
    currentPeriodOffset--;
    displayPeriods();
}


function displayNextEndPeriods()
{
    if ( currentPeriodOffset < 0 ) // Cannot display future periods
    {
        currentPeriodOffset++;
        displayEndPeriods();
    }
}

function displayPreviousEndPeriods()
{
    currentPeriodOffset--;
    displayEndPeriods();
}

//PVF monthly periods


function displayMonthlyPeriods()
{
    var periodType = "Monthly";
    var periods = periodTypeFactory.get( periodType ).generatePeriods( currentPeriodOffset );
    periods = periodTypeFactory.reverse( periods );
    periods = periodTypeFactory.filterFuturePeriodsExceptCurrent( periods );

    $( "#monthOne" ).removeAttr( "disabled" );
    clearListById( "monthOne" );

    $( "#monthTwo" ).removeAttr( "disabled" );
    clearListById( "monthTwo" );
	
    $( "#monthThree" ).removeAttr( "disabled" );
    clearListById( "monthThree" );
	
    for ( i in periods )
    {
        addOptionById( "monthOne", periods[i].iso, periods[i].name );
		addOptionById( "monthTwo", periods[i].iso, periods[i].name );
		addOptionById( "monthThree", periods[i].iso, periods[i].name );
    }
}


function displayNextMonthlyPeriods()
{
    if ( currentPeriodOffset < 0 ) // Cannot display future periods
    {
        currentPeriodOffset++;
        displayMonthlyPeriods();
    }
}

function displayPreviousMonthlyPeriods()
{
    currentPeriodOffset--;
    displayMonthlyPeriods();
}


//PVF Quarterly periods


function displayQuarterlyPeriods()
{
    var periodType = "Quarterly";
    var periods = periodTypeFactory.get( periodType ).generatePeriods( currentPeriodOffset );
    periods = periodTypeFactory.reverse( periods );
    periods = periodTypeFactory.filterFuturePeriodsExceptCurrent( periods );

    $( "#quarterlyPeriod" ).removeAttr( "disabled" );
    clearListById( "quarterlyPeriod" );

    for ( i in periods )
    {
        addOptionById( "quarterlyPeriod", periods[i].iso, periods[i].name );
    }
}


function displayNextQuarterlyPeriods()
{
    if ( currentPeriodOffset < 0 ) // Cannot display future periods
    {
        currentPeriodOffset++;
        displayQuarterlyPeriods();
    }
}

function displayPreviousQuarterlyPeriods()
{
    currentPeriodOffset--;
    displayQuarterlyPeriods();
}



//------------------------------------------------------------------------------
// Run report
//------------------------------------------------------------------------------

function validateDataSetReport()
{
	var dataSetReport = getDataSetReport();
	
    if ( !dataSetReport.ds )
    {
        setHeaderMessage( i18n_select_data_set );
        return false;
    }
    if ( !dataSetReport.pe )
    {
        setHeaderMessage( i18n_select_period );
        return false;
    }
    if ( !selectionTreeSelection.isSelected() )
    {
        setHeaderMessage( i18n_select_organisation_unit );
        return false;
    }
    
    hideHeaderMessage();
    hideCriteria();
    hideContent();
    showLoader();
	
    delete dataSetReport.periodType;
    delete dataSetReport.offset;
    
    $.get( 'generateDirectDataSetReport.action', dataSetReport, function( data ) {
    	$( '#content' ).html( data );
    	hideLoader();
    	showContent();
    	setTableStyles();
    } );
}


//------------------------------------------------------------------------------
//Run direct report
//------------------------------------------------------------------------------

function validateDirectDataSetReport()
{
	var dataSetReport = getDataSetReport();
	
  if ( !dataSetReport.ds )
  {
      setHeaderMessage( i18n_select_data_set );
      return false;
  }
  if ( !dataSetReport.pe )
  {
      setHeaderMessage( i18n_select_period );
      return false;
  }
  if ( !selectionTreeSelection.isSelected() )
  {
      setHeaderMessage( i18n_select_organisation_unit );
      return false;
  }
  
  hideHeaderMessage();
  hideCriteria();
  hideContent();
  showLoader();
	
  delete dataSetReport.periodType;
  delete dataSetReport.offset;
  
  $.get( 'generateDirectDataSetReport.action', dataSetReport, function( data ) {
  	$( '#content' ).html( data );
  	hideLoader();
  	showContent();
  	setTableStyles();
  } );
}

//------------------------------------------------------------------------------
//Run displayQualityAggregationReport action
//------------------------------------------------------------------------------

function prepareQualityAggregationReport()
{
	var dataSetReport = getDataSetReport();
	
//if ( !dataSetReport.ds )
//{
//  setHeaderMessage( i18n_select_data_set );
//  return false;
//}
if ( !dataSetReport.pe )
{
  setHeaderMessage( i18n_select_period );
  return false;
}
if ( !selectionTreeSelection.isSelected() )
{
  setHeaderMessage( i18n_select_organisation_unit );
  return false;
}

delete dataSetReport.periodType;
delete dataSetReport.offset;

var url = "displayQualityAggregationReport.action" + 
  "?pe=" + dataSetReport.pe +
  "&endPe=" + dataSetReport.endPe +
  "&selectedUnitOnly=" + dataSetReport.selectedUnitOnly +
  "&ou=" + dataSetReport.ou;
  
window.location.href = url;

}


//------------------------------------------------------------------------------
//Run displayQuantityAggregationReport action
//------------------------------------------------------------------------------

function prepareQualityAggregationReport()
{
	var dataSetReport = getDataSetReport();
	
//if ( !dataSetReport.ds )
//{
//  setHeaderMessage( i18n_select_data_set );
//  return false;
//}
if ( !dataSetReport.pe )
{
  setHeaderMessage( i18n_select_period );
  return false;
}
if ( !selectionTreeSelection.isSelected() )
{
  setHeaderMessage( i18n_select_organisation_unit );
  return false;
}

delete dataSetReport.periodType;
delete dataSetReport.offset;

var url = "produceControlReport.action" + 
  "?pe=" + dataSetReport.pe +
  "&endPe=" + dataSetReport.endPe +
  "&selectedUnitOnly=" + dataSetReport.selectedUnitOnly +
  "&ou=" + dataSetReport.ou;
  
window.location.href = url;

}


//------------------------------------------------------------------------------
//Run produceQuarterlySummaryReport action
//------------------------------------------------------------------------------

function produceQuarterlySummaryReport()
{
	var dataSetReport = getDataSetReport();
	
if ( !dataSetReport.pe )
{
  setHeaderMessage( i18n_select_period );
  return false;
}

delete dataSetReport.periodType;
delete dataSetReport.offset;

var url = "produceQuarterlySummaryReport.action" + 
  "?pe=" + dataSetReport.pe;
  
window.location.href = url;

}

//------------------------------------------------------------------------------
//Run prepareExcelDataEntryFilesExt action
//------------------------------------------------------------------------------

function prepareExcelDataEntryFilesExt()
{
	var dataSetReport = getDataSetReport();
	
if ( !dataSetReport.pe )
{
  setHeaderMessage( i18n_select_period );
  return false;
}

delete dataSetReport.periodType;
delete dataSetReport.offset;

var url = "produceExcelDataEntryExt.action" + 
  "?pe=" + dataSetReport.pe;
  
window.location.href = url;

}

//------------------------------------------------------------------------------
//Run prepareExcelDataEntryFiles action
//------------------------------------------------------------------------------

function prepareExcelDataEntryFiles()
{
	var dataSetReport = getDataSetReport();
	
if ( !dataSetReport.pe )
{
  setHeaderMessage( i18n_select_period );
  return false;
}

delete dataSetReport.periodType;
delete dataSetReport.offset;

var url = "produceExcelDataEntry.action" + 
  "?pe=" + dataSetReport.pe;
  
window.location.href = url;

}
//------------------------------------------------------------------------------
//Run docx report
//------------------------------------------------------------------------------

function validateSummaryReportUnitReport()
{
	var dataSetReport = getDataSetReport();
	
if ( !dataSetReport.ds )
{
  setHeaderMessage( i18n_select_data_set );
  return false;
}
if ( !dataSetReport.pe )
{
  setHeaderMessage( i18n_select_period );
  return false;
}
if ( !selectionTreeSelection.isSelected() )
{
  setHeaderMessage( i18n_select_organisation_unit );
  return false;
}
	
delete dataSetReport.periodType;
delete dataSetReport.offset;

var url = "displaySummaryReportUnit.action" + 
	"?ds=" + dataSetReport.ds +
  "&pe=" + dataSetReport.pe +
  "&endPe=" + dataSetReport.endPe +
  "&selectedUnitOnly=" + dataSetReport.selectedUnitOnly +
  "&ou=" + dataSetReport.ou;
  
window.location.href = url;

}


//------------------------------------------------------------------------------
//Run docx report
//------------------------------------------------------------------------------

function validateDocxReport()
{
	var dataSetReport = getDataSetReport();
	
if ( !dataSetReport.ds )
{
    setHeaderMessage( i18n_select_data_set );
    return false;
}
if ( !dataSetReport.pe )
{
    setHeaderMessage( i18n_select_period );
    return false;
}
if ( !selectionTreeSelection.isSelected() )
{
    setHeaderMessage( i18n_select_organisation_unit );
    return false;
}

//hideHeaderMessage();
//hideCriteria();
//hideContent();
//showLoader();
	
delete dataSetReport.periodType;
delete dataSetReport.offset;

var url = "displaySummaryReport.action" + 
	"?ds=" + dataSetReport.ds +
    "&pe=" + dataSetReport.pe +
    "&endPe=" + dataSetReport.endPe +
    "&selectedUnitOnly=" + dataSetReport.selectedUnitOnly +
    "&ou=" + dataSetReport.ou;
    
window.location.href = url;

}

//------------------------------------------------------------------------------
//Run jagg action
//------------------------------------------------------------------------------

function validateJaggParams()
{
	var dataSetReport = getDataSetReport();
	
if ( !dataSetReport.ds )
{
    setHeaderMessage( i18n_select_data_set );
    return false;
}
if ( !dataSetReport.pe )
{
    setHeaderMessage( i18n_select_period );
    return false;
}
if ( !selectionTreeSelection.isSelected() )
{
    setHeaderMessage( i18n_select_organisation_unit );
    return false;
}

//hideHeaderMessage();
//hideCriteria();
//hideContent();
//showLoader();
	
delete dataSetReport.periodType;
delete dataSetReport.offset;

var url = "displayJaggResults.action" + 
	"?ds=" + dataSetReport.ds +
    "&pe=" + dataSetReport.pe +
    "&endPe=" + dataSetReport.endPe +
    "&selectedUnitOnly=" + dataSetReport.selectedUnitOnly +
    "&ou=" + dataSetReport.ou;
    
window.location.href = url;

}

function exportDataSetReport( type )
{
	var dataSetReport = getDataSetReport();
	
	var url = "generateDataSetReport.action" + 
		"?ds=" + dataSetReport.ds +
	    "&pe=" + dataSetReport.pe +
	    "&selectedUnitOnly=" + dataSetReport.selectedUnitOnly +
	    "&ou=" + dataSetReport.ou +
	    "&type=" + type;
	    
	window.location.href = url;
}

function setUserInfo( username )
{
	$( "#userInfo" ).load( "../dhis-web-commons-ajax-html/getUser.action?username=" + username, function() {
		$( "#userInfo" ).dialog( {
	        modal : true,
	        width : 350,
	        height : 350,
	        title : "User"
	    } );
	} );	
}

function showCriteria()
{
	$( "#criteria" ).show( "fast" );
}

function hideCriteria()
{
	$( "#criteria" ).hide( "fast" );
}

function showContent()
{
	$( "#content" ).show( "fast" );
	$( ".downloadButton" ).show();
	$( "#interpretationArea" ).autogrow();
}

function hideContent()
{
	$( "#content" ).hide( "fast" );
	$( ".downloadButton" ).hide();
}

function showAdvancedOptions()
{
	$( "#advancedOptionsLink" ).hide();
	$( "#advancedOptions" ).show();
}

//------------------------------------------------------------------------------
// Share
//------------------------------------------------------------------------------

function viewShareForm() // Not in use
{
	$( "#shareForm" ).dialog( {
		modal : true,
		width : 550,
		resizable: false,
		title : i18n_share_your_interpretation
	} );
}

function shareInterpretation()
{
	var dataSetReport = getDataSetReport();
    var text = $( "#interpretationArea" ).val();
    
    if ( text.length && $.trim( text ).length )
    {
    	text = $.trim( text );
    	
	    var url = "../api/interpretations/dataSetReport/" + $( "#currentDataSetId" ).val() +
	    	"?pe=" + dataSetReport.pe +
	    	"&ou=" + dataSetReport.ou;
	    	    
	    $.ajax( url, {
	    	type: "POST",
	    	contentType: "text/html",
	    	data: text,
	    	success: function() {	    		
	    		$( "#interpretationArea" ).val( "" );
	    		setHeaderDelayMessage( i18n_interpretation_was_shared );
	    	}    	
	    } );
    }
}
