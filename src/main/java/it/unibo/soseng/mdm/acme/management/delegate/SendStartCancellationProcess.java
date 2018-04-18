package it.unibo.soseng.mdm.acme.management.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import it.unibo.soseng.mdm.util.RandomAlphanumericString;

/**
 * The Class SendStartCancellationProcess, used for "Start Cancellation Process" message event to start the cancellation process
 * @author Mirko Zichichi
 */
public class SendStartCancellationProcess implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
     	runtimeService.createMessageCorrelation("StartCancellationProcess")
		.processInstanceBusinessKey(RandomAlphanumericString.generate())
		.setVariables(execution.getVariables())
		.correlate();
	  }
	  
}