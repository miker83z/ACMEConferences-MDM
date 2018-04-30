package it.unibo.soseng.mdm.acme.management.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * The Class NotifyManagementStarted, used for "Notify Management Started" message event to notify the client that the Conference management has started
 * @author Mirko Zichichi
 */
public class NotifyManagementStarted implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    //Cleaning
		execution.removeVariable("itsCateringTime");
		execution.removeVariable("at_least_one_available");
		execution.removeVariable("catering_available");
		execution.removeVariable("jobEstimate");
		execution.removeVariable("maxSeats");
		execution.removeVariable("numberOfPartners");
		execution.removeVariable("remaining_partners");
	    
     	runtimeService.createMessageCorrelation("ManagementStartedNotification")
		.processInstanceBusinessKey((String) execution.getVariable("businessKeyClient"))
		.correlate();
	  }
	  
}