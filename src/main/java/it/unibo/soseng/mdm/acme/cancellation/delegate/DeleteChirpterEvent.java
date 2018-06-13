package it.unibo.soseng.mdm.acme.cancellation.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.services.chirpter.Event;

/**
 * The Class DeleteChirpterEvent, used in task Delete Chirp Event to delete a Chirp event for Conference in Chirpter.
 * @author Mirko Zichichi
 */
public class DeleteChirpterEvent implements JavaDelegate {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		int eventID = (Integer) execution.getVariable("chirpterEventID");
		Event event = new Event(eventID);
		try {
			event.delete();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
		
	}
	
}
	  



