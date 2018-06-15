package it.unibo.soseng.mdm.acme.allin.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

/**
 * Set the flag to false when we are in the Venue Lane.
 * 
 * @author Davide Marchi
 *
 */
public class SetFlagToFalse implements ExecutionListener {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("itsCateringTime", false);

	}

}
