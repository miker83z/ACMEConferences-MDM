package it.unibo.soseng.mdm.catering.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class AnalyzeJobOffer implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		// Assing user task to the correct partner
		delegateTask.setAssignee((String) delegateTask.getVariable("processTenant"));
	}

}
