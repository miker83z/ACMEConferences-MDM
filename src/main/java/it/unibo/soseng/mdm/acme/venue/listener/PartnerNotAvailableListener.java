package it.unibo.soseng.mdm.acme.venue.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.PartnerData;
import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class PartnerNotAvailableListener implements ExecutionListener {
		
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// Venue/catering flag
		Boolean itsCateringTime = (Boolean) execution.getVariable("itsCateringTime");

		// Get the full partner list
		PartnerDatas partners = (PartnerDatas) execution.getVariable("allPartners");	
		
		if (!itsCateringTime) {
			// Get my id
			Integer id = (Integer) execution.getVariable("loopCounter");
			
			// Get the contacted partner list
			PartnerDatas contactedPartners = (PartnerDatas) execution.getVariable("contactedPartners");
			
			// Update partner values
			contactedPartners.getPartnerList().get(id).setAvailable(false);
			
			// Update partner list
			execution.setVariable("contactedPartners", contactedPartners);

			// Update partner values
			partners.getPartnerList().get(id).setAvailable(false);
		}
		else {
			// Get the contacted partner
			PartnerData contactedPartner = (PartnerData) execution.getVariable("contactedPartner");
						
			// Update partner values
			contactedPartner.setAvailable(false);
			
			// Update partner
			execution.setVariable("cateringPartner", contactedPartner);
			
			// Get the id of the contacted partner
			Integer index = partners.indexOf(contactedPartner.getName());

			// Update partner values
			partners.getPartnerList().get(index).setAvailable(false);
			
			// Set gateway variable
			execution.setVariable("catering_available", false);
		}
		
		// Update partner list
		execution.setVariable("allPartners", partners);
		
	}

}
