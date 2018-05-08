package it.unibo.soseng.mdm.acme.chirpter.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.RelevantEvents;
import it.unibo.soseng.mdm.services.chirpter.Chirp;

/**
 * The Class CreateChirp, used in task Create Chirp to post a Chirp containing Relevant Event info in Chirpter.
 * @author Mirko Zichichi
 */
public class CreateChirp implements JavaDelegate {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("loopVar2", ((Integer) execution.getVariable("loopVar2")) + 1 );
		RelevantEvents relEv = (RelevantEvents) execution.getVariable("relevantEvents");
		String chirpMessage = relEv.retrieveCurrentEventDescription();
		String token = (String) execution.getVariable("chirpterToken");
		int eventID = (Integer) execution.getVariable("chirpterEventID");
		
		Chirp chirp = new Chirp(token, eventID);
		try{
			chirp.post(chirpMessage);		
		} catch (Exception e) {
			e.printStackTrace();
			throw new BpmnError("CONNECTION_ERROR");
		}
	}
	
}
	  



