package it.unibo.soseng.mdm.client.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * The Class SendNOBillsNotification, used for "Send NO Bills Notification" message event.
 * @author Mirko Zichichi
 */
public class SendNOBillsNotification implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    String businessKeyA = (String) execution.getVariable("businessKeyACME");
	    
		runtimeService.createMessageCorrelation("NoClientBillsNotification")
		.processInstanceBusinessKey(businessKeyA)
		.correlate();
	  }
	  
}
