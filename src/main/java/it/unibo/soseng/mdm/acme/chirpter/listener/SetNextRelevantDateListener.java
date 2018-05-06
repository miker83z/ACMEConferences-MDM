package it.unibo.soseng.mdm.acme.chirpter.listener;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.model.RelevantEvents;
/**
 * The class SetNextRelevantDateListener, used for "2 days before a Relevant Event" timer event to set the next relevant date
 * @author Mirko Zichichi
 */
public class SetNextRelevantDateListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		RelevantEvents relEv = (RelevantEvents) execution.getVariable("relevantEvents");
		Date tmp = relEv.retrieveNextRelevantEvent();
		if( tmp != null )
			execution.setVariable("nextRelevantDate", tmp);
		else
			//Set to "Infinite"
			execution.setVariable("nextRelevantDate", (Date) execution.getVariable("confLastDayPlus60"));
	}

}
