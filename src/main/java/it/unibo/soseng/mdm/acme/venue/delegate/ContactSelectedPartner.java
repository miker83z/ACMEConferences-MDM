package it.unibo.soseng.mdm.acme.venue.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.PartnerData;

public class ContactSelectedPartner implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// FIXME: togliere JSON
		// Get chosen partner
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("chosenPartner");
		PartnerData partner = new PartnerData();
		partner.defineValueFromJSON(jsonNode);
		
		// Get his businessKey
		String partnerNameWithoutWhitespaces = partner.retrieveNameWithoutWhitespaces();
		String partnerBusinessKey = (String) execution.getVariable(partnerNameWithoutWhitespaces + "BusinessKey");
		
		// Send message
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("job_accepted_" + partnerNameWithoutWhitespaces)
	    .processInstanceBusinessKey(partnerBusinessKey)
	    .correlate();
		
	}

}
