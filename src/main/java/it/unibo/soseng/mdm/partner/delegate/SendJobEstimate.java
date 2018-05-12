package it.unibo.soseng.mdm.partner.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Accept the received job and send the job estimate.
 * 
 * @author Davide Marchi
 *
 */
public class SendJobEstimate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get my partner name
		String partnerName = (String) execution.getVariable("processTenant");
		
		// Get job estimate
		Double jobEstimate = (Double) execution.getVariable("estimate");
		
		// Get max seats
		Integer maxSeats = (Integer) execution.getVariable("maxSeats");
		
		// Set partner name variable for the next event-based gateway
		execution.setVariable("partnerName", partnerName);
		
		// Send message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("job_estimate_" + partnerName)
	    .setVariable("jobEstimate", jobEstimate)
	    .setVariable("maxSeats", maxSeats)
	    .processInstanceBusinessKey((String) execution.getVariable("acmeBusinessKey"))
	    .correlate();	

	}

}
