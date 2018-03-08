package it.unibo.soseng.mdm.acme.venue;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class NotifyRefusedPartners implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// FIXME: contact Partners to notify the decision of the client to refuse
		
		// FIXME: same fixme as other classes
		// Integer[] partnerList = (Integer[]) execution.getVariable("partnerList");
		// Integer partnerName = 0;
		
		// Send message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("job_refused")
	    .processInstanceBusinessKey("AB-123")
	    .correlate();
	}
}