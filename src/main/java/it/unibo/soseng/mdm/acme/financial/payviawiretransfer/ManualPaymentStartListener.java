package it.unibo.soseng.mdm.acme.financial.payviawiretransfer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The class ManualPaymentStartListener, used at the start of "Manual Payment" task to compute the correct sum payed (sumBeforeManualPayment)
 * @author Mirko Zichichi
 */
public class ManualPaymentStartListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");
		BillsCollection billsPayed = (BillsCollection) execution.getVariable("billsPayed");
		Double sumBeforeManualPayment = 0.0;
		for(Bill bill : bills.getBills()) {
			sumBeforeManualPayment += bill.getAmount();
			billsPayed.addBill(bill);
		}
		execution.setVariable("sumBeforeManualPayment", sumBeforeManualPayment);
	}

}
