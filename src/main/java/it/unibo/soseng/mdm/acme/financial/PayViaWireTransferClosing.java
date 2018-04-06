package it.unibo.soseng.mdm.acme.financial;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.Bill;
import it.unibo.soseng.mdm.acme.model.BillsCollection;

public class PayViaWireTransferClosing implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");
		Double sumPayed = (Double) execution.getVariable("sumPayed");
		if( execution.hasVariable("sumBeforeManualPayment") ) {
			//add to payed
			sumPayed += (Double) execution.getVariable("sumBeforeManualPayment");
			for(Bill bill : bills.getBills() ) 
				sumPayed -= bill.getAmount();
			execution.removeVariable("sumBeforeManualPayment");
		}
		
		BillsCollection otherBillsToPay = new BillsCollection();
		if (execution.hasVariable("otherBillsToPay"))
			otherBillsToPay = (BillsCollection) execution.getVariable("otherBillsToPay");
		for(Bill bill : otherBillsToPay.getBills())
			bills.addBill(bill);
		otherBillsToPay.getBills().clear();
			
		execution.removeVariable("loginResponse");
		execution.removeVariable("loginAttempts");
		execution.removeVariable("transferAttempts");
		execution.removeVariable("allPaymentsCompleted");
		execution.removeVariable("logoutResponse");
		execution.removeVariable("logoutAttempts");
	}
}
