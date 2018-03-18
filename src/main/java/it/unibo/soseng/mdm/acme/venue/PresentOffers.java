package it.unibo.soseng.mdm.acme.venue;

import static org.camunda.spin.Spin.JSON;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.acme.venue.model.PartnerData;

public class PresentOffers implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// Retrieve the partnerList setted before
		List<PartnerData> partnerList = new ArrayList<>();
		
		// FIXME: non funziona questa lettura, non riesce a convertire da JSON ad ArrayList
		ObjectValue typedPartnerList = execution.getVariableTyped("partnerList");
		String JSONpartnerList = typedPartnerList.getValueSerialized();
		// partnerList = JSON(JSONpartnerList).mapTo("java.util.ArrayList<it.unibo.soseng.mdm.acme.venue.model.PartnerData>");
		
		
		// // Create a list with only the available partners
		// List <PartnerData> availablePartners = new ArrayList<>();
		// for (PartnerData partnerData : partnerList) {
		// 	if (partnerData.isAvailable()) {
		// 		availablePartners.add(partnerData);
		// 	}
		// }
		
		// Send the message setting variables
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("offers")
	    .processInstanceBusinessKey("AB-123")
	    .setVariable("JSONpartnerList", JSONpartnerList)
	    .correlate();
	    // .setVariable("availablePartners", availablePartners)
	    // .correlate();	 
	}
}