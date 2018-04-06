package it.unibo.soseng.mdm.acme.financial.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import it.unibo.soseng.mdm.acme.model.Bill;
import it.unibo.soseng.mdm.acme.model.BillsCollection;

public class ManualPaymentStartListener implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");
		Double sumBeforeManualPayment = 0.0;
		for(Bill bill : bills.getBills())
			sumBeforeManualPayment += bill.getAmount();
		execution.setVariable("sumBeforeManualPayment", sumBeforeManualPayment);
	}

}
