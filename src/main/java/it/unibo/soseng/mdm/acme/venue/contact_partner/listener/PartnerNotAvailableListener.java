package it.unibo.soseng.mdm.acme.venue.contact_partner.listener;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.venue.model.PartnerDatas;

public class PartnerNotAvailableListener implements ExecutionListener {
		
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		/*
		 * LEGGERE nella classe PartnerAvailableListener per sapere bene 
		 * la differenza tra allPartners e partnerList e per sapere come 
		 * queste vengono utilizzate.
		 */
		
		// Get my id
		Integer id = (Integer) execution.getVariable("loopCounter");
		
		// Get partner list
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("allPartners");
		
		// Convert the JSON to a list of PartnerData
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
		
		// Update partner values
		partners.getPartnerList().get(id).setAvailability(false);
		
		// Update partner list
		execution.setVariable("allPartners", JSON(partners.toJSON()));
		
		// Get the contacted partner list
		SpinJsonNode contactedJsonNode = (SpinJsonNode) execution.getVariable("partnerList");		
		PartnerDatas contactedPartners = new PartnerDatas();
		contactedPartners.setPartnersFromJSON(contactedJsonNode);
		
		// Update partner values
		contactedPartners.getPartnerList().get(id).setAvailability(false);
		
		// Update partner list
		execution.setVariable("partnerList", JSON(contactedPartners.toJSON()));
		
	}

}
