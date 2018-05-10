package it.unibo.soseng.mdm.acme.financial.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.services.django.Event;

/**
 * The class SufficientFundsListener, used for "Subscription Closed?" gateway to check if the subscriptions in the registration platform are closed or not
 * @author Mirko Zichichi
 */
public class SubscriptionClosedListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		if( (Boolean) execution.getVariable("subsOpened") ) {
			boolean tmp = true;
			String token = (String) execution.getVariable("djangoToken");
			int eventID = (Integer) execution.getVariable("djangoEventID");
			Event event = new Event(token, eventID);
			try{
				event.get();
				tmp = event.getIsOpen();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				execution.setVariable("subscriptionClosed", !tmp);
			}
		}
	}
}