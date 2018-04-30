package it.unibo.soseng.mdm.acme.financial.payviawiretransfer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The Class PayViaWireTransferEnd, used at the end of "Pay via Wire Transfer" sub-process to retrieve otherBillsToPay 
 * and clean sub-process variables.
 * @author Mirko Zichichi
 */
public class PayViaWireTransferEnd implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");
		BillsCollection billsPayed = (BillsCollection) execution.getVariable("billsPayed");
			
		//Clear sub-process variables
		ObjectValue typedBillsValue = Variables.objectValue(bills).serializationDataFormat("application/json").create();
		execution.setVariable("billsToPay", typedBillsValue);
		ObjectValue typedBillsPValue = Variables.objectValue(billsPayed).serializationDataFormat("application/json").create();
		execution.setVariable("billsPayed", typedBillsPValue);
		execution.removeVariable("loginResponse");
		execution.removeVariable("loginAttempts");
		execution.removeVariable("transferAttempts");
		execution.removeVariable("allPaymentsCompleted");
		execution.removeVariable("logoutResponse");
		execution.removeVariable("logoutAttempts");
		execution.setVariable("payLock", false);
	}
}
