package it.unibo.soseng.mdm.client.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * The Class SendPaymentConfirmation, used for the "Send Payment Confirmation" message event to confirm debt payment.
 * @author Mirko Zichichi
 */
public class SendPaymentCancConfirmation implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    runtimeService.createMessageCorrelation("ClientPaymentCancNotification")
		.processInstanceBusinessKey((String) execution.getVariable("businessKeyACME"))
		.correlate();
	  }
	  
}
