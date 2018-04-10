package it.unibo.soseng.mdm.acme.venue.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.PartnerData;
import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class NotifyRefusedPartners implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get Camunda runtime service 
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		
	    // FIXME: togliere JSON
		// Get the JSON variable from Camunda engine (contacted partners)
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("contactedPartners");
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
		
		// FIXME: togliere JSON
		// Get the JSON variable from Camunda engine (chosen partner)
		SpinJsonNode chosenJsonNode = (SpinJsonNode) execution.getVariable("chosenPartner");
		PartnerData chosenPartner = new PartnerData();
		chosenPartner.setValueFromJSON(chosenJsonNode);;
		
		// Index of the chosen partner in contacted partners list
		Integer index = partners.indexOf(chosenPartner.getName());
				
		// Get my id
		Integer id = (Integer) execution.getVariable("loopCounter");
		
		// Send message to the partner, except for the chosen one and the not available
		if (index != id && partners.getPartnerList().get(id).getAvailable()) {
			// Get his businessKey
			String partnerNameWithoutWhitespaces = partners.getPartnerList().get(id).getNameWithoutWhitespaces();
			String partnerBusinessKey = (String) execution.getVariable(partnerNameWithoutWhitespaces + "BusinessKey");
						
			// Send message
		    runtimeService.createMessageCorrelation("job_refused_" + partnerNameWithoutWhitespaces)
		    .processInstanceBusinessKey(partnerBusinessKey)
		    .correlate();	
		} 
						
	}

}
