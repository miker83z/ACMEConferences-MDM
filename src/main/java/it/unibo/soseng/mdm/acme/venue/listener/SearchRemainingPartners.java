package it.unibo.soseng.mdm.acme.venue.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class SearchRemainingPartners implements ExecutionListener {
	
	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {		
		// Get the JSON variable from Camunda engine
		SpinJsonNode jsonNode = (SpinJsonNode) delegateExecution.getVariable("allPartners");
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
		
		// Remove contacted partners
		partners.removeContactedPartners();
		
		// Set gateway variable
		if (partners.getNumberOfPartners() > 0) {
			delegateExecution.setVariable("remaining_partners", true);
		} else {
			delegateExecution.setVariable("remaining_partners", false);
		}
		
	}

}
