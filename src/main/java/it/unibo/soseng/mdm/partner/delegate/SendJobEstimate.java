package it.unibo.soseng.mdm.partner.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendJobEstimate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get my partner name
		String partnerName = (String) execution.getVariable("processTenant");
		
		// Get job estimate
		Double jobEstimate = (Double) execution.getVariable("estimate");
		
		// Set partner name variable for the next event-based gateway
		execution.setVariable("partnerName", partnerName);
		
		// Send message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("job_estimate_" + partnerName)
	    .setVariable("jobEstimate", jobEstimate)
	    .processInstanceBusinessKey((String) execution.getVariable("acmeBusinessKey"))
	    .correlate();	

	}

}
