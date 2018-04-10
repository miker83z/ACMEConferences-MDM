package it.unibo.soseng.mdm.acme.venue.delegate;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class PresentOffers implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {		
		// FIXME: togliere JSON
		// Get the JSON variable from Camunda engine
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("contactedPartners");
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);

		// Create a list with only the available and contacted partners
		partners.getAvailablePartners();
		
		// FIXME: togliere JSON
		// Send the message setting variables
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("offers")
	    .processInstanceBusinessKey(execution.getBusinessKey())
	    .setVariable("availablePartners", JSON(partners.toJSON()))
	    .correlate();					
	}
	
}