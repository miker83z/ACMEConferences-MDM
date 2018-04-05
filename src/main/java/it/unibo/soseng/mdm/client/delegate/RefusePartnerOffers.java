package it.unibo.soseng.mdm.client.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RefusePartnerOffers implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    // Send to ACME a empty message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("refuse_offers")
	    .processInstanceBusinessKey(execution.getBusinessKey())
	    .correlate();
	    
	}

}
