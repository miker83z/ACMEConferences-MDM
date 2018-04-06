package it.unibo.soseng.mdm.acme.financial.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class SubscriptionClosedListener implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		Boolean subscriptionClosed = true;
		execution.setVariable("subscriptionClosed", subscriptionClosed);
	}
}
