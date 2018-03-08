package it.unibo.soseng.mdm.client;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendSelectedOffer implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
	    // FIXME: send to ACME a message with the name of the selected partner
		
		// FIXME: maybe we can create a single class for Refuse/Select offer in which 
		// 		  we use the variable ${partner_selected} to know the type of message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("accepted_offer")
	    .processInstanceBusinessKey("AB-123")
	    .setVariable("selectedPartnerName", String.valueOf(0))
	    .correlate();
	}

}
