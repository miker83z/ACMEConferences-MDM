package it.unibo.soseng.mdm.acme.venue.contactpartner;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AskForAvailability implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {		
		// FIXME: I need to send a message to Partners Service using Soap retrieving a name from the
		//		  partnerList setted before.
		// 		  In the message will be the informations about the conference and I will ask if 
		// 		  it can be available or not.
		
		// FIXME: Retrieve the partnerList setted before
		// Integer[] partnerList = (Integer[]) execution.getVariable("partnerList");
		// Integer partnerName = partnerList[0];
		
		// Send the message setting variables
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("ask_for_availability")
	    .processInstanceBusinessKey("AB-123")
	    .setVariable("partnerName", String.valueOf(0))
	    .correlate();
	}
}