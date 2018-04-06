package it.unibo.soseng.mdm.acme.financial;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.model.Bill;
import it.unibo.soseng.mdm.acme.model.BillsCollection;

public class SumBills implements JavaDelegate{
	
	public void execute(DelegateExecution execution) throws Exception {
		if (execution.hasVariable("otherBillsToPay")) {
			BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");
			BillsCollection otherBillsToPay = (BillsCollection) execution.getVariable("otherBillsToPay");
			for(Bill bill : otherBillsToPay.getBills())
				bills.addBill(bill);
			otherBillsToPay.getBills().clear();
		}
	}
}
