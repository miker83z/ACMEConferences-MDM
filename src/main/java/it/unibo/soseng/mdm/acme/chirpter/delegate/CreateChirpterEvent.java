package it.unibo.soseng.mdm.acme.chirpter.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.ConferenceData;
import it.unibo.soseng.mdm.services.chirpter.Event;

/**
 * The Class CreateChirpterEvent, used in task Create Chirp Event to create a Chirp event for Conference in Chirpter.
 * @author Mirko Zichichi
 */
public class CreateChirpterEvent implements JavaDelegate {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("loopVar2", ((Integer) execution.getVariable("loopVar2")) + 1 );
		ConferenceData confData = (ConferenceData) execution.getVariable("conferenceData");
		String title = confData.getTitle();
		String token = (String) execution.getVariable("chirpterToken");
		Event event = new Event(token);
		try {
			int eventID = event.post(title);
			execution.setVariable("chirpterEventID", eventID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
	}
	
}
	  



