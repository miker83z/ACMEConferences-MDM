package it.unibo.soseng.mdm.acme.venue.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.PartnerData;

public class ContactSelectedPartner implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get chosen partner
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("chosenPartner");
		PartnerData partner = new PartnerData();
		partner.setValueFromJSON(jsonNode);
		
		// Get his businessKey
		String partnerNameWithoutWhitespaces = partner.getNameWithoutWhitespaces();
		String partnerBusinessKey = (String) execution.getVariable(partnerNameWithoutWhitespaces + "BusinessKey");
		
		System.out.println("[PROVA2] NAME: " + partnerNameWithoutWhitespaces);
		System.out.println("[PROVA2] BK: " + partnerBusinessKey);
		
		// Send message
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("job_accepted_" + partnerNameWithoutWhitespaces)
	    .processInstanceBusinessKey(partnerBusinessKey)
	    .correlate();
		
	}

}
