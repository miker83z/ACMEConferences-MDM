package it.unibo.soseng.mdm.acme.allin.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.PartnerData;

/**
 * Send a message to the selected partner for venue.
 * 
 * @author Davide Marchi
 *
 */
public class ContactSelectedPartner implements JavaDelegate {
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get chosen partner		
		PartnerData partner = (PartnerData) execution.getVariable("chosenPartner");
		
		// Get his businessKey
		String partnerNameWithoutWhitespaces = partner.retrieveNameWithoutWhitespaces();
		String partnerBusinessKey = (String) execution.getVariable(partnerNameWithoutWhitespaces + "BusinessKey");
		
		// Send message
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("job_accepted_" + partnerNameWithoutWhitespaces)
	    .processInstanceBusinessKey(partnerBusinessKey)
	    .correlate();
	}
}
