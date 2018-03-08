package it.unibo.soseng.mdm.partners;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendJobEstimate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// FIXME: This can't be done here, must be an external service who communicates using Soap
		
		// FIXME: Retrieve the partnerList setted before
		// Integer[] partnerList = (Integer[]) execution.getVariable("partnerList");
		Integer partnerName = 0;
		
		// Send a fake job estimate
		Integer price = 100;
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("job_estimate")
	    .processInstanceBusinessKey("AB-123")
	    .setVariable("price-" + String.valueOf(partnerName), price)
	    .correlate();
	}

}
