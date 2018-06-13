package it.unibo.soseng.mdm.acme.allin.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.PartnerData;
import it.unibo.soseng.mdm.model.PartnerCollection;

/**
 * Used to notify all the refused partners when the client choose the best one.
 * 
 * @author Davide Marchi
 *
 */
public class NotifyRefusedPartners implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		// Get the JSON variable from Camunda engine (contacted partners)
		PartnerCollection partners = (PartnerCollection) execution.getVariable("contactedPartners");
		
		Integer index;
		if( execution.hasVariable("chosenPartner")) {
			// Get the JSON variable from Camunda engine (chosen partner)
			PartnerData chosenPartner = (PartnerData) execution.getVariable("chosenPartner");
			
			// Index of the chosen partner in contacted partners list
			index = partners.indexOf(chosenPartner.getName());
		}
		else
			// If client didn't choose any partner
			index = -1;
				
		// Get my id
		Integer id = (Integer) execution.getVariable("loopCounter");
		
		// Send message to the partner, except for the chosen one and the not available
		if (index != id && partners.getPartnerList().get(id).getAvailable()) {
			// Get his businessKey
			String partnerNameWithoutWhitespaces = partners.getPartnerList().get(id).retrieveNameWithoutWhitespaces();
			String partnerBusinessKey = (String) execution.getVariable(partnerNameWithoutWhitespaces + "BusinessKey");
						
			// Send message
			RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		    runtimeService.createMessageCorrelation("job_refused_" + partnerNameWithoutWhitespaces)
		    .processInstanceBusinessKey(partnerBusinessKey)
		    .correlate();	
		} 				
	}
}
