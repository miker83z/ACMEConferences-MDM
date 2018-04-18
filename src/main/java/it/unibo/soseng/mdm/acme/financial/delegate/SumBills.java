package it.unibo.soseng.mdm.acme.financial.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The Class SumBills, used for "Sum Bills" task to move bills present in otherBillsToPay to billsToPay.
 * @author Mirko Zichichi
 */
public class SumBills implements JavaDelegate{
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
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
