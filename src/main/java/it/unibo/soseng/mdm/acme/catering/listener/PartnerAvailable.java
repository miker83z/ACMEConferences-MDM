package it.unibo.soseng.mdm.acme.catering.listener;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.PartnerData;
import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class PartnerAvailable implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		// Get job estimate
		Double jobEstimate = (Double) execution.getVariable("jobEstimate");

		// Get the contacted partner
		SpinJsonNode contactedJsonNode = (SpinJsonNode) execution.getVariable("cateringPartner");		
		PartnerData contactedPartner = new PartnerData();
		contactedPartner.setValueFromJSON(contactedJsonNode);
		
		// Update partner values
		contactedPartner.setAvailability(true);
		contactedPartner.setPrice(jobEstimate);
		
		// Update partner list
		execution.setVariable("cateringPartner", JSON(contactedPartner.toJSON()));
		
		// Get the full partner list
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("allCatering");		
		PartnerDatas cateringList = new PartnerDatas();
		cateringList.setPartnersFromJSON(jsonNode);
		
		// Get the id of the contacted partner
		Integer index = cateringList.indexOf(contactedPartner.getName());
		
		// Update partner values
		cateringList.getPartnerList().get(index).setAvailability(true);
		cateringList.getPartnerList().get(index).setPrice(jobEstimate);
		
		// Update partner list
		execution.setVariable("allPartners", JSON(cateringList.toJSON()));
		
		// Set gateway variable
		execution.setVariable("catering_available", true);
	
	}

}
