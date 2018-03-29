package it.unibo.soseng.mdm.partner;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class AnalyzeRequestListener implements TaskListener {
	
	public void notify(DelegateTask delegateTask) {
		// Execute custom identity lookups here
		// and then for example call following methods:
		delegateTask.setAssignee((String) delegateTask.getVariable("processTenant"));
	}

}
