package it.unibo.soseng.mdm.catering.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Accept the job and send the job estimate.
 * 
 * @author Davide Marchi
 *
 */
public class SendJobEstimate implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
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
