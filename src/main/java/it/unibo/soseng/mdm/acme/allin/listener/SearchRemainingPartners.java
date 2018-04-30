package it.unibo.soseng.mdm.acme.allin.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.model.PartnerCollection;

public class SearchRemainingPartners implements ExecutionListener {
	
	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {		
		// Get the JSON variable from Camunda engine
		SpinJsonNode jsonNode = (SpinJsonNode) delegateExecution.getVariable("allPartners");
		PartnerCollection partners = new PartnerCollection();
		partners.definePartnersFromJSON(jsonNode);
		
		// Remove contacted partners
		partners.removeContactedPartners();
		
		// Set gateway variable
		if (partners.retrieveNumberOfPartners() > 0) {
			delegateExecution.setVariable("remaining_partners", true);
		} else {
			delegateExecution.setVariable("remaining_partners", false);
		}
		
	}

}
