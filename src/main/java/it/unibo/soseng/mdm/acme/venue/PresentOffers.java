package it.unibo.soseng.mdm.acme.venue;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class PresentOffers implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// FIXME: Take the available partner list and send to the client
		// FIXME: The client, as the partners, can't be inside this project but it must be 
		//		  some kind of external service
		
		// FIXME: Where is saved the available partner list?
		
		// Send a fake list
		Integer numberOfAvailablePartners = 1;
		Integer[] availablePartnersList = new Integer[numberOfAvailablePartners];
		for (int i = 0; i < numberOfAvailablePartners; i++) {
			availablePartnersList[i] = i;
		}
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("offers")
	    .processInstanceBusinessKey("AB-123")
	    .setVariable("availablePartnersList", availablePartnersList)
	    .setVariable("partnerName", "Yuppi")
	    .correlate();
	    
		
	}
}