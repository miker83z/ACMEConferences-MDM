package it.unibo.soseng.mdm.acme.venue.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.spin.json.SpinJsonNode;

import it.unibo.soseng.mdm.acme.model.PartnerDatas;

public class SetPartnerName implements ExecutionListener {

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		// Get loop counter
		Integer id = (Integer) delegateExecution.getVariable("loopCounter");
		
		// FIXME: togliere JSON
		// Get partner list
		SpinJsonNode jsonNode = (SpinJsonNode) delegateExecution.getVariable("contactedPartners");
		PartnerDatas partners = new PartnerDatas();
		partners.setPartnersFromJSON(jsonNode);
		
		// Get the correct partner name for this instance and remove whitespaces
		String partnerNameWithoutWhitespaces = partners.getPartnerList().get(id).getNameWithoutWhitespaces();
		
		// Set the partner name
		delegateExecution.setVariable("partnerName", partnerNameWithoutWhitespaces);
	}
	
}
