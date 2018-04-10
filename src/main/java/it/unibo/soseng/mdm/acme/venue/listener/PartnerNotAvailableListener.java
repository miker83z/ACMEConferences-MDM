package it.unibo.soseng.mdm.acme.venue.listener;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.PartnerDatas;

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
		
		// FIXME: togliere JSON
		// Get partner list
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("allPartners");
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
		
		// Update partner values
		partners.getPartnerList().get(id).setAvailability(false);
		
		// FIXME: togliere JSON
		// Update partner list
		execution.setVariable("allPartners", JSON(partners.toJSON()));
		
		// FIXME: togliere JSON
		// Get the contacted partner list
		SpinJsonNode contactedJsonNode = (SpinJsonNode) execution.getVariable("contactedPartners");		
		PartnerDatas contactedPartners = new PartnerDatas();
		contactedPartners.setPartnersFromJSON(contactedJsonNode);
		
		// Update partner values
		contactedPartners.getPartnerList().get(id).setAvailability(false);
		
		// FIXME: togliere JSON
		// Update partner list
		execution.setVariable("contactedPartners", JSON(contactedPartners.toJSON()));
		
	}

}
