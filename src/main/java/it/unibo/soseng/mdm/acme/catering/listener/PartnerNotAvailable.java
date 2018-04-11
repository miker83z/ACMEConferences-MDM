package it.unibo.soseng.mdm.acme.catering.listener;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.PartnerData;
import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class PartnerNotAvailable implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {		
		// Get the contacted partner
		SpinJsonNode contactedJsonNode = (SpinJsonNode) execution.getVariable("cateringPartner");		
		PartnerData contactedPartner = new PartnerData();
		contactedPartner.defineValueFromJSON(contactedJsonNode);
		
		// Update partner values
		contactedPartner.setAvailability(false);
		
		// Update partner 
		execution.setVariable("cateringPartner", JSON(contactedPartner.toJSON()));

		// Get partner list
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("allCatering");
		PartnerDatas cateringList = new PartnerDatas();
		cateringList.definePartnersFromJSON(jsonNode);
		
		// Get the index of the contacted partner
		Integer index = cateringList.indexOf(contactedPartner.getName());
		
		// Update partner values
		cateringList.getPartnerList().get(index).setAvailability(false);
		
		// Update partner list
		execution.setVariable("allCatering", JSON(cateringList.toJSON()));
		
		// Set gateway variable
		execution.setVariable("catering_available", false);
	}

}
