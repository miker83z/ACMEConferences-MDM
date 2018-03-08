package it.unibo.soseng.mdm.acme.venue;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class NotifyAcceptedPartner implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// FIXME: contact Partner to notify the decision of the client to accept
		
		// FIXME: same fixme as other classes
		// Integer selectedPartnerName = (Integer) execution.getVariable("selectedPartnerName");
		// Integer selectedPartnerName = 0;
		
		// Send message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("job_accepted")
	    .processInstanceBusinessKey("AB-123")
	    .correlate();

	}

}
