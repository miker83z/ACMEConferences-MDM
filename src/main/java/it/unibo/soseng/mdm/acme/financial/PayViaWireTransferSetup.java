package it.unibo.soseng.mdm.acme.financial;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.BillsCollection;

public class PayViaWireTransferSetup implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("loginResponse", false );
		execution.setVariable("loginAttempts", 0 );
		execution.setVariable("transferAttempts", 0 );
		execution.setVariable("allPaymentsCompleted", false);
		execution.setVariable("logoutResponse", false);
		execution.setVariable("logoutAttempts", 0 );
		
		if(!execution.hasVariable("billsPayed"))
			execution.setVariable("billsPayed", new BillsCollection());
	}
}
