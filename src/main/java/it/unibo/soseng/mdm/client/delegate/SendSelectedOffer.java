package it.unibo.soseng.mdm.client.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.model.PartnerData;

public class SendSelectedOffer implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// Get the variable from Camunda engine
		PartnerData chosenPartner = (PartnerData) execution.getVariable("chosenPartner");
		
		// Send to ACME the message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("accepted_offer")
	    .processInstanceBusinessKey((String) execution.getVariable("businessKeyACME"))
	    .setVariable("chosenPartner", chosenPartner)
	    .correlate();
	}

}
