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
		BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");
		BillsCollection billsPayed = (BillsCollection) execution.getVariable("billsPayed");
		
		//add to payed
		Double tmp = (Double) execution.getVariable("sumBeforeManualPayment");
		for(Bill bill : bills.getBills() ) {
			tmp -= bill.getAmount();
			billsPayed.removeBill(bill);
		}
		execution.setVariable("sumPayed",((Double) execution.getVariable("sumPayed")) + tmp);
		execution.removeVariable("sumBeforeManualPayment");
		
		//Add other bills to pay to billsToPay
		BillsCollection otherBillsToPay = new BillsCollection();
		if (execution.hasVariable("otherBillsToPay"))
			otherBillsToPay = (BillsCollection) execution.getVariable("otherBillsToPay");
		for(Bill bill : otherBillsToPay.getBills())
			bills.addBill(bill);
		otherBillsToPay.getBills().clear();
			
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
	}
}
