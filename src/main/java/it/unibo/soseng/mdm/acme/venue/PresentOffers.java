package it.unibo.soseng.mdm.acme.venue;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.acme.venue.model.PartnerData;

public class PresentOffers implements JavaDelegate {

	@SuppressWarnings("unchecked")
	public void execute(DelegateExecution execution) throws Exception {		
		// FIXME: c'è un warning fastidioso perchè Camunda non riesce a lavorare con gli ArrayList
		
		// Retrieve the partnerList setted before
		List<PartnerData> partnerList = new ArrayList<>();
		partnerList = (List<PartnerData>) execution.getVariable("partnerList");
		
		// Create a list with only the available partners
		List<PartnerData> availablePartners = new ArrayList<>();
		for (PartnerData partnerData : partnerList) {
			if (partnerData.getAvailable() && partnerData.getContacted()) {
				availablePartners.add(partnerData);
			}
		}
		
		// Serialize to JSON
		ObjectValue JSONavailablePartner = Variables
				.objectValue(availablePartners)
				.serializationDataFormat("application/json")
				.create();
		execution.setVariable("partnerList", JSONavailablePartner);
		
		// Send the message setting variables
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("offers")
	    .processInstanceBusinessKey("AB-123")
	    .setVariable("availablePartners", JSONavailablePartner)
	    .correlate(); 
	}
}