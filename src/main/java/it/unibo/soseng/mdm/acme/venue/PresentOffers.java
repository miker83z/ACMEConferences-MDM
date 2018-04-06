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
		
		// Create the string in JSON format with all partner informations
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
	 * Convert the JSON with all partner's informations in a List<PartnerData> object
	 * @param jsonNode All partner informations in JSON
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