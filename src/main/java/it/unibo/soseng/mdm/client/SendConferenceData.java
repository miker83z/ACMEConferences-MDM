package it.unibo.soseng.mdm.client;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.model.ConferenceData;
import it.unibo.soseng.mdm.misc.RandomAlphanumericString;

public class SendConferenceData implements JavaDelegate {
	
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    ConferenceData conferenceData = (ConferenceData) execution.getVariable("conferenceData");
	    String clientId = (String) execution.getVariable("processInstantiator");
	    String businessKeyA = RandomAlphanumericString.generate();
	    execution.setVariable("businessKeyACME", businessKeyA);
	    
	    runtimeService.createMessageCorrelation("ConferenceData")
		.processInstanceBusinessKey(businessKeyA)
		.setVariable("businessKeyClient", execution.getBusinessKey())
		.setVariable("conferenceData", conferenceData)
		.setVariable("clientId", clientId)
		.correlate();         
	  }
	  
}
