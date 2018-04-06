package it.unibo.soseng.mdm.acme.financial.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.model.Bill;

public class SendDebtSettlementRequest implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    Bill clientBill = (Bill) execution.getVariable("clientDebt");
	    
	    runtimeService.createMessageCorrelation("DebtSettlementRequest")
			.processInstanceBusinessKey((String) execution.getVariable("businessKeyClient"))
			.setVariable("ACMEBillToPay", clientBill)
			.correlate();
	  }
	  
}