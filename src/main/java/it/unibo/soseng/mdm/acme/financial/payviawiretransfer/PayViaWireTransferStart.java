package it.unibo.soseng.mdm.acme.financial.payviawiretransfer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The Class PayViaWireTransferStart, used at the start of " Pay via Wire Transfer" sub-process to setup variables.
 * @author Mirko Zichichi
 */
public class PayViaWireTransferStart implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		//Set sub-proces variables
		execution.setVariable("loginResponse", false );
		execution.setVariable("loginAttempts", 0 );
		execution.setVariable("transferAttempts", 0 );
		execution.setVariable("allPaymentsCompleted", false);
		execution.setVariable("logoutResponse", false);
		execution.setVariable("logoutAttempts", 0 );

		execution.setVariable("payLock", true );		
	}
}
