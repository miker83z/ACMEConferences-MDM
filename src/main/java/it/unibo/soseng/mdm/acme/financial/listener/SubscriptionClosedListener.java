package it.unibo.soseng.mdm.acme.financial.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

/**
 * The class SufficientFundsListener, used for "Subscription Closed?" gateway to check if the subscriptions in the registration platform are closed or not
 * @author Mirko Zichichi
 */
public class SubscriptionClosedListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		Boolean subscriptionClosed = true;
		execution.setVariable("subscriptionClosed", subscriptionClosed);
	}
}
