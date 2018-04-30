package it.unibo.soseng.mdm.acme.financial.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Bill;

/**
 * The Class SendDebtSettlementRequest, used for "Send Debt Settlement Request" message event to notify the client about his debt with ACME.
 * @author Mirko Zichichi
 */
public class SendDebtSettlementRequest implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    Bill clientBill = (Bill) execution.getVariable("clientDebt");
	    ObjectValue value = Variables.objectValue(clientBill).serializationDataFormat("application/json").create();
	    execution.setVariable("clientDebt", value);
	    
	    runtimeService.createMessageCorrelation("DebtSettlementRequest")
			.processInstanceBusinessKey((String) execution.getVariable("businessKeyClient"))
			.setVariable("ACMEBillToPay", value)
			.correlate();
	  }
	  
}