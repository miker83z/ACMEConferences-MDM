package it.unibo.soseng.mdm.client.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.PartnerData;

public class SendSelectedOffer implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// Get the variable from Camunda engine
		PartnerData chosenPartner = (PartnerData) execution.getVariable("chosenPartner");
		ObjectValue typedChosenPartner = Variables.objectValue(chosenPartner).serializationDataFormat("application/json").create();
		
		// Send to ACME the message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("accepted_offer")
	    .processInstanceBusinessKey((String) execution.getVariable("businessKeyACME"))
	    .setVariable("chosenPartner", typedChosenPartner)
	    .correlate();
	}

}
