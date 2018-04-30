package it.unibo.soseng.mdm.acme.financial.payviawiretransfer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The Class PayViaWireTransferManualEnd, used at the end of "Pay via Wire Transfer" sub-process to compute sumPayed, retrieve otherBillsToPay 
 * and clean sub-process variables.
 * @author Mirko Zichichi
 */
public class PayViaWireTransferManualEnd implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		BillsCollection billsManuallyPayed = (BillsCollection) execution.getVariable("billsManuallyPayed");
		BillsCollection billsPayed = (BillsCollection) execution.getVariable("billsPayed");
		double tmp = 0.0;
		
		//add to payed
		for(Bill bill : billsManuallyPayed.getBills() )
			if(!billsPayed.getBills().contains(bill)) {
				tmp += bill.getAmount();
				billsPayed.addBill(bill);
			}
		execution.setVariable("sumPayed",((Double) execution.getVariable("sumPayed")) + tmp);
		execution.setVariable("sumReservedForManualPayment", 0.0);
			
		//Clear sub-process variables
		BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");
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
