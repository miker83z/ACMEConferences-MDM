package it.unibo.soseng.mdm.acme.venue.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class PartnerNotAvailableListener implements ExecutionListener {
		
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		/*
		 * LEGGERE nella classe PartnerAvailableListener per sapere bene 
		 * la differenza tra allPartners e contactedPartners e per sapere come 
		 * queste vengono utilizzate.
		 */
		
		// Get my id
		Integer id = (Integer) execution.getVariable("loopCounter");
		
		// Get partner list
		PartnerDatas partners = (PartnerDatas) execution.getVariable("allPartners");
		
		// Update partner values
		partners.getPartnerList().get(id).setAvailable(false);
		
		// Update partner list
		execution.setVariable("allPartners", partners);
		
		// Get the contacted partner list
		PartnerDatas contactedPartners = (PartnerDatas) execution.getVariable("contactedPartners");
		
		// Update partner values
		contactedPartners.getPartnerList().get(id).setAvailable(false);
		
		// Update partner list
		execution.setVariable("contactedPartners", contactedPartners);
		
	}

}
