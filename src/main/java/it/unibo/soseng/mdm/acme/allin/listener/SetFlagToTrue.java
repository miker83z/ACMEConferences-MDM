package it.unibo.soseng.mdm.acme.allin.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

/**
 * Set the flag to true when we are in the Catering Lane.
 * 
 * @author Davide Marchi
 *
 */
public class SetFlagToTrue implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("itsCateringTime", true);

	}

}
