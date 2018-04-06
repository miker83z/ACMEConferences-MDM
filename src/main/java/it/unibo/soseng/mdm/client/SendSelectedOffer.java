package it.unibo.soseng.mdm.client;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

public class SendSelectedOffer implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    /* TODO: 
	     * 	1) Retrieve the 'chosenPartner' variable from Camunda engine
	     *  2) Send to ACME a message with the informations about the selected partner
	     */
		
		// Get the JSON variable from Camunda engine
		SpinJsonNode chosenPartner = (SpinJsonNode) execution.getVariable("chosenPartner");
				
		// Send to ACME the message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("accepted_offer")
	    .processInstanceBusinessKey(execution.getBusinessKey())
	    .setVariable("chosenPartner", chosenPartner)
	    .correlate();
	}

}
