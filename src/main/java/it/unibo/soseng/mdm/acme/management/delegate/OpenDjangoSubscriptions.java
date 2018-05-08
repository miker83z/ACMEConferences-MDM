package it.unibo.soseng.mdm.acme.management.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.RelevantEvents;
import it.unibo.soseng.mdm.services.django.Event;

/**
 * The Class OpenDjangoSubscriptions, used in task "Open Subscriptions" to open the subscriptions to an event in the registration platform.
 * @author Mirko Zichichi
 */
public class OpenDjangoSubscriptions implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("loopVar", ((Integer) execution.getVariable("loopVar")) + 1 );
		if( !execution.hasVariable("subscriptionsDeadline") )
			execution.setVariable("subscriptionsDeadline", ((RelevantEvents) execution.getVariable("relevantEvents")).getSubsDeadline());
		if( !execution.hasVariable("contributionsDeadline") )
			execution.setVariable("contributionsDeadline", ((RelevantEvents) execution.getVariable("relevantEvents")).getContDeadline());
		String token = (String) execution.getVariable("djangoToken");
		Event event = new Event(token);
		event.setEventID(((Integer) execution.getVariable("djangoEventID")));
		try {			
			event.put("{\"is_open\": true, \"is_open_contr\": true }");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
	}
	
}
	  



