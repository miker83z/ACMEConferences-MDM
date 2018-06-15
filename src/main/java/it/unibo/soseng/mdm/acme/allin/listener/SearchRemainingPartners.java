package it.unibo.soseng.mdm.acme.allin.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.model.PartnerCollection;

/**
 * Check if there are other partners to contacted and set the next gateway.
 * 
 * @author Davide Marchi
 *
 */
public class SearchRemainingPartners implements ExecutionListener {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {		
		// Get the full partner list
		PartnerCollection partners = (PartnerCollection) delegateExecution.getVariable("allPartners");	
		
		// Remove contacted partners
		partners.removeContactedPartners();
		
		// Set gateway variable
		if (partners.retrieveNumberOfPartners() > 0) {
			delegateExecution.setVariable("remaining_partners", true);
		} else {
			delegateExecution.setVariable("remaining_partners", false);
		}
		
	}

}
