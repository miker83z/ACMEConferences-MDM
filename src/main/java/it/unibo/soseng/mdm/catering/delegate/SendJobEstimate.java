package it.unibo.soseng.mdm.catering.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendJobEstimate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {	
		// Get job estimate
		Double jobEstimate = (Double) execution.getVariable("estimate");
						
		// Send message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("catering_job_accepted")
	    .setVariable("jobEstimate", jobEstimate)
	    .processInstanceBusinessKey((String) execution.getVariable("acmeBusinessKey"))
	    .correlate();	

	}

}
