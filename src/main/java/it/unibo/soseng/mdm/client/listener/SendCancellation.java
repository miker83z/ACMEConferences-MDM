package it.unibo.soseng.mdm.client.listener;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * THe Class SendCancellation, used in "Conference Cancellation" tasks to send the message for conference cancellation.
 * @author Mirko Zichichi
 */
public class SendCancellation implements TaskListener {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.TaskListener#notify(org.camunda.bpm.engine.delegate.DelegateTask)
	 */
	public void notify(DelegateTask delegateTask) {
		RuntimeService runtimeService = delegateTask.getProcessEngineServices().getRuntimeService();
		
		String businessKeyA = (String) delegateTask.getVariable("businessKeyACME");
		runtimeService.createMessageCorrelation("CancellationMessage")
		.processInstanceBusinessKey(businessKeyA)
		.correlate();
	}
}
