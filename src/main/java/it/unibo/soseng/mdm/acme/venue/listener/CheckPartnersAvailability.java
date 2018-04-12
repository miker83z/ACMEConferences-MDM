package it.unibo.soseng.mdm.acme.venue.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class CheckPartnersAvailability implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		// Get the variable from Camunda engine
		PartnerDatas partners = (PartnerDatas) execution.getVariable("contactedPartners");
		
		// Set gateway variable
		execution.setVariable("at_least_one_available", partners.atLeastOneAvailablePartner());
	}

}
