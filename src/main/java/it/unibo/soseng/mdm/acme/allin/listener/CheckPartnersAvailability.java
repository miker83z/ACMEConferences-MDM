package it.unibo.soseng.mdm.acme.allin.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.model.PartnerCollection;

public class CheckPartnersAvailability implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		// Get the variable from Camunda engine
		PartnerCollection partners = (PartnerCollection) execution.getVariable("contactedPartners");
		
		// Set gateway variable
		execution.setVariable("at_least_one_available", partners.atLeastOneAvailablePartner());
	}

}
