package it.unibo.soseng.mdm.acme.financial.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendACMEInvoice implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    runtimeService.createMessageCorrelation("ACMEInvoice")
			.processInstanceBusinessKey((String) execution.getVariable("businessKeyClient"))
			.setVariable("ACMEInvoice", "invoice")
			.correlate();
	  }
	  
}