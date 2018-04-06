package it.unibo.soseng.mdm.client;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendNOBillsNotification implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    String businessKeyA = (String) execution.getVariable("businessKeyACME");
	    
		runtimeService.createMessageCorrelation("NoClientBillsNotification")
		.processInstanceBusinessKey(businessKeyA)
		.correlate();
	  }
	  
}
