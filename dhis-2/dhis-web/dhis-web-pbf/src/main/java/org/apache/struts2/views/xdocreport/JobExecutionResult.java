package org.apache.struts2.views.xdocreport;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public class JobExecutionResult extends AbstractVoidResult{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void executeJob(String jobName, ActionInvocation invocation)
			throws Exception {
        ValueStack stack = invocation.getStack();
        Object action = stack.findValue( ACTION_KEY );
        if ( action == null )
        {
            throw new Exception( "Cannot retrieve action instance in value stack with key=" + ACTION_KEY );
        }

        // Find the Response in context
        HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);
        PrintWriter out = null;
        response.setContentType("text/plain");
        out = response.getWriter();
        
        out.print("nothing for now");
        
        out.flush();

        if (out != null)
            out.close();

        
        ExecuteJobAware executeJobAware = (ExecuteJobAware) action;
       
        executeJobAware.executeJob();
	}

}
