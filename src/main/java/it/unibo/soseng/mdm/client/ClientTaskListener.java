package it.unibo.soseng.mdm.client;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class ClientTaskListener implements TaskListener {
	
	public void notify(DelegateTask delegateTask) {
		// Execute custom identity lookups here
		// and then for example call following methods:
		delegateTask.setAssignee((String) delegateTask.getVariable("processInstantiator"));
	}
}
