package it.unibo.soseng.mdm.client;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RefusePartnerOffers implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    // FIXME: send to ACME a message with a false
		
		// FIXME: maybe we can create a single class for Refuse/Select offer in which 
		// 		  we use the variable ${partner_selected} to know the type of message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("refuse_offers")
	    .processInstanceBusinessKey("AB-123")
	    .setVariable("response", false)
	    .correlate();

	}

}
