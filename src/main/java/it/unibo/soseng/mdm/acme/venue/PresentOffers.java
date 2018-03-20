package it.unibo.soseng.mdm.acme.venue;

import static org.camunda.spin.Spin.JSON;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.SpinList;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.venue.model.PartnerData;

public class PresentOffers implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {		
		
		/*
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
		*/
		
		// Get the JSON variable from Camunda engine
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("partnerList");
		
		// Convert the JSON to a list of PartnerData
		List<PartnerData> partnerList = new ArrayList<>();
		partnerList = retrievePartnersFromJSON(jsonNode);
		
		// Create a list with only the available and contacted partners
		List<PartnerData> availablePartners = new ArrayList<>();
		for (PartnerData partnerData : partnerList) {
			if (partnerData.getAvailable() && partnerData.getContacted()) {
				availablePartners.add(partnerData);
			}
		}
		
		// FIXME: questo pezzo c'è anche in FindFeasiblePartners
		// Create the string with all partner informations
		String availablePartnersJSON = "[";
		for (int i = 0; i < availablePartners.size(); i++) {
			availablePartnersJSON += availablePartners.get(i).toJSON();
			if (i < availablePartners.size() - 1) {
				availablePartnersJSON += ", ";
			}
		}
		availablePartnersJSON += "]";
		// Convert the string in JSON
		SpinJsonNode availablePartnersJsonNode = JSON(availablePartnersJSON);	
				
		// Send the message setting variables
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("offers")
	    .processInstanceBusinessKey(execution.getBusinessKey())
	    .setVariable("availablePartners", availablePartnersJsonNode)
	    .correlate();					
	}
	
	/**
	 * 
	 * @param jsonNode
	 * @return
	 */
	private List<PartnerData> retrievePartnersFromJSON(SpinJsonNode jsonNode) {
		List<PartnerData> partnerList = new ArrayList<>();
		
		// Fetch a list of items when your property is an array of data
		@SuppressWarnings("rawtypes")
		SpinList partners = jsonNode.elements();
		for (int i = 0; i < partners.size(); i++) {
			// Get the i-th element
			SpinJsonNode partnerJSON = (SpinJsonNode) partners.get(i);
			// Convert to PartnerData object
			PartnerData partnerData = new PartnerData();
			partnerData.setValueFromJSON(partnerJSON);
			// Add to list
			partnerList.add(partnerData);
		}

		return partnerList;
	}
}