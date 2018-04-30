package it.unibo.soseng.mdm.acme.financial.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The class ReceiveClientBillsListener, used for "Receive Bills" message event to set bills to pay.
 * @author Mirko Zichichi
 */
public class ReceiveClientBillsListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		BillsCollection clientBills = (BillsCollection) execution.getVariable("clientBillsToPay");
		BillsCollection otherBillsToPay = new BillsCollection();
		if(execution.hasVariable("otherBillsToPay"))
			otherBillsToPay = (BillsCollection) execution.getVariable("otherBillsToPay");
		for (Bill bill : clientBills.getBills())
			otherBillsToPay.addBill(bill);
		ObjectValue typedOBillsValue = Variables.objectValue(otherBillsToPay).serializationDataFormat("application/json").create();
		execution.setVariable("otherBillsToPay", typedOBillsValue);
		execution.removeVariable("clientBillsToPay");
	}

}
