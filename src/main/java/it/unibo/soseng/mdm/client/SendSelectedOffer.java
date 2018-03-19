package it.unibo.soseng.mdm.client;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendSelectedOffer implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    /* TODO: 
	     * 	1) Retrieve the 'chosenPartner' variable from Camunda engine
	     *  2) Send to ACME a message with the informations about the selected partner
	     */
		
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("accepted_offer")
	    .processInstanceBusinessKey("AB-123")
	    .setVariable("selectedPartnerName", String.valueOf(0))
	    .correlate();
	}

}
