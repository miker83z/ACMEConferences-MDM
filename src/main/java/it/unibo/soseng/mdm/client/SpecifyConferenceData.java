package it.unibo.soseng.mdm.client;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.misc.RandomAlphanumericString;

public class SpecifyConferenceData implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    Double amount = (Double) execution.getVariable("amount");
	    String businessKeyA = RandomAlphanumericString.generate();
	    execution.setVariable("businessKeyA", businessKeyA);
		runtimeService.createMessageCorrelation("ConferenceData")
		.processInstanceBusinessKey(businessKeyA)
		.setVariable("bkACME", execution.getBusinessKey())
		.correlate();
	  }
	  
}
