package it.unibo.soseng.mdm.acme.management.delegate;

import java.net.ConnectException;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.services.django.Event;

/**
 * The Class CloseDjangoContributions, used in task "Open Contributions" to close the contributions submitting to an event in the registration platform.
 * @author Mirko Zichichi
 */
public class CloseDjangoContributions implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("loopVar3", ((Integer) execution.getVariable("loopVar3")) + 1 );
		String token = (String) execution.getVariable("djangoToken");
		Event event = new Event(token);
		event.setEventID(((Integer) execution.getVariable("djangoEventID")));
		try {
			event.put("{\"is_open_contr\": false");
		} catch (ConnectException e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
	}
	
}
	  



