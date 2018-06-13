package it.unibo.soseng.mdm.acme.cancellation.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import it.unibo.soseng.mdm.services.django.Event;

/**
 * The Class DeleteRegistrationPlatformEvent, used in task "Delete Event in Registration Platform" to delete an event in the registration platform.
 * @author Mirko Zichichi
 */
public class DeleteRegistrationPlatformEvent implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		String token = (String) execution.getVariable("djangoToken");
		Event event = new Event(token, (int) execution.getVariable("djangoEventID"));
		try {			
			event.delete();		
		} catch (Exception e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
	}
	
}
	 