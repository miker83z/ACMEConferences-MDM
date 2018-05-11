package it.unibo.soseng.mdm.acme.management.delegate;

import java.net.ConnectException;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.services.django.Event;

/**
 * The Class CloseDjangoSubscriptions, used in task "Close Subscriptions" to close the subscriptions to an event in the registration platform.
 * @author Mirko Zichichi
 */
public class CloseDjangoSubscriptions implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("loopVar", ((Integer) execution.getVariable("loopVar")) + 1 );
		String token = (String) execution.getVariable("djangoToken");
		int eventID = (Integer) execution.getVariable("djangoEventID");
		Event event = new Event(token, eventID);
		try {
			event.put("{\"is_open\": false}");
		} catch (ConnectException e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
	}
	
}
	  



