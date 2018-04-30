package it.unibo.soseng.mdm.acme.financial.payviawiretransfer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The class ManualPaymentStartListener, used at the start of "Manual Payment" task to compute the correct sum payed
 * @author Mirko Zichichi
 */
public class ManualPaymentStartListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");
		Double sum = 0.0;
		for(Bill bill : bills.getBills())
			sum += bill.getAmount();
		execution.setVariable("sumReservedForManualPayment", sum);
		
		ObjectValue value = Variables.objectValue(bills).serializationDataFormat("application/json").create();
		execution.setVariable("billsToPay", value);
		BillsCollection billsManuallyPayed = new BillsCollection();
		if( execution.hasVariable("billsManuallyPayed") )
			billsManuallyPayed = (BillsCollection) execution.getVariable("billsManuallyPayed");
		ObjectValue valueMan = Variables.objectValue(billsManuallyPayed).serializationDataFormat("application/json").create();
		execution.setVariable("billsManuallyPayed", valueMan);
	}

}
