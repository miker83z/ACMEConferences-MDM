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
		
		// Get the JSON variable from Camunda engine (contacted partners)
		SpinJsonNode jsonNode = (SpinJsonNode) execution.getVariable("partnerList");
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
				
		// Get the JSON variable from Camunda engine (chosen partner)
		SpinJsonNode chosenJsonNode = (SpinJsonNode) execution.getVariable("chosenPartner");
		PartnerData chosenPartner = new PartnerData();
		chosenPartner.setValueFromJSON(chosenJsonNode);;
		
		// Index of the chosen partner in contacted partners list
		Integer index = partners.indexOf(chosenPartner.getName());
		
		// Send message to all contacted partners, except for the chosen one and the not available
		for (int i = 0; i < partners.getPartnerList().size(); i++) {
			
			System.out.println("[PROVA2] PARTNER: " + i);
			System.out.println("[PROVA2] INFO: " + partners.getPartnerList().get(i).toString());
			
			if (index != i && partners.getPartnerList().get(i).getAvailable()) {
				// Get his businessKey
				String partnerNameWithoutWhitespaces = partners.getPartnerList().get(i).getNameWithoutWhitespaces();
				String partnerBusinessKey = (String) execution.getVariable(partnerNameWithoutWhitespaces + "BusinessKey");
				
				System.out.println("[PROVA2] NAME: " + partnerNameWithoutWhitespaces);
				System.out.println("[PROVA2] BK: " + partnerBusinessKey);
				
				// Send message
			    runtimeService.createMessageCorrelation("job_refused_" + partnerNameWithoutWhitespaces)
			    .processInstanceBusinessKey(partnerBusinessKey)
			    .correlate();	
			} else {
				System.out.println("[PROVA2] SALTATO");
			}
		}
						
	}

}
