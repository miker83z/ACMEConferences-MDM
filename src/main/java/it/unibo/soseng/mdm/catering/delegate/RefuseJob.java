package it.unibo.soseng.mdm.catering.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Send message for refusing the job offer.
 * 
 * @author Davide Marchi
 *
 */
public class RefuseJob implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {			
		// Send message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("catering_job_refused")
	    .processInstanceBusinessKey((String) execution.getVariable("acmeBusinessKey"))
	    .correlate();	
	}

}
