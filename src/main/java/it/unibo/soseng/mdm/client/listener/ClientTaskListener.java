package it.unibo.soseng.mdm.client.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * THe Class ClientTaskListener, used in multiple user tasks to assign these ones to the right camunda User.
 * @author Mirko Zichichi
 */
public class ClientTaskListener implements TaskListener {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.TaskListener#notify(org.camunda.bpm.engine.delegate.DelegateTask)
	 */
	public void notify(DelegateTask delegateTask) {
		delegateTask.setAssignee((String) delegateTask.getVariable("processInstantiator"));
	}
}
