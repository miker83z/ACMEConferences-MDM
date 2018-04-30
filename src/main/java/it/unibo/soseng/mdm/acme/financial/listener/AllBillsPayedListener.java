package it.unibo.soseng.mdm.acme.financial.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import it.unibo.soseng.mdm.model.BillsCollection;

/**
 * The class AllBillsPayedListener, used for "All Bills Payed?" gateway to check if billsToPay is empty (all bills payed)
 * @author Mirko Zichichi
 */
public class AllBillsPayedListener implements ExecutionListener{

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.ExecutionListener#notify(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void notify(DelegateExecution execution) throws Exception {
		BillsCollection billsToPay = (BillsCollection) execution.getVariable("billsToPay");
		
		//Check if all bills payed
		boolean allBillsPayedTmp = false;
		if( billsToPay.getBills().isEmpty() )
			allBillsPayedTmp = true;
		execution.setVariable("allBillsPayedTmp", allBillsPayedTmp);
	}

}
