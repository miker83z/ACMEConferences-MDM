package it.unibo.soseng.mdm.acme.allin.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.model.PartnerCollection;

/**
 * Set the variable partnerName with the proper value to send and/or receive
 * messages from the correct partner pool.
 * 
 * @author Davide Marchi
 *
 */
public class SetPartnerName implements ExecutionListener {

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		// Get loop counter
		Integer id = (Integer) delegateExecution.getVariable("loopCounter");
		
		// Get partner list
		PartnerCollection partners = (PartnerCollection) delegateExecution.getVariable("contactedPartners");
		
		// Get the correct partner name for this instance and remove whitespace
		String partnerNameWithoutWhitespaces = partners.getPartnerList().get(id).retrieveNameWithoutWhitespaces();
		
		// Set the partner name
		delegateExecution.setVariable("partnerName", partnerNameWithoutWhitespaces);
	}
	
}
