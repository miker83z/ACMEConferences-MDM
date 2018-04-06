package it.unibo.soseng.mdm.client.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendPaymentConfirmation implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    runtimeService.createMessageCorrelation("ClientPaymentNotification")
		.processInstanceBusinessKey((String) execution.getVariable("businessKeyACME"))
		.correlate();
	  }
	  
}
