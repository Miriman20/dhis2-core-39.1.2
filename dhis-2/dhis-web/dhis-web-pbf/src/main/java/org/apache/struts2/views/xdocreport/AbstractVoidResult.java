package org.apache.struts2.views.xdocreport;


import com.opensymphony.xwork2.ActionInvocation;

public abstract class AbstractVoidResult  extends org.apache.struts2.result.StrutsResultSupport
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final String ACTION_KEY = "#action";
    
	@Override
	protected void doExecute(String jobName, ActionInvocation invocation)
			throws Exception {
		// TODO Auto-generated method stub
		
		Object action = invocation.getStack().findValue( ACTION_KEY );
		ExecuteJobAware initializer = getExecuteJobAware( action );
         if ( initializer != null )
         {
             initializer.executeJob();
         }
	}

    protected ExecuteJobAware getExecuteJobAware( Object action )
    {
        if ( action == null )
        {
            return null;
        }
            return  (ExecuteJobAware) action ;
    }

    protected abstract void executeJob( String jobName,
                                             ActionInvocation invocation )
        throws Exception;
}
