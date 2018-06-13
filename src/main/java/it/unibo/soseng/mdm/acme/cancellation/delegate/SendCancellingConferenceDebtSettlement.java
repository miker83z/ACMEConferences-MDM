package it.unibo.soseng.mdm.acme.cancellation.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.util.RandomAlphanumericString;

/**
 * The Class SendCancellingConference, used for "Cancelling Conference" message event to notify the client the cancellation of the conference.
 * @author Mirko Zichichi
 */
public class SendCancellingConferenceDebtSettlement implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    
	    Bill clientBill = (Bill) execution.getVariable("clientDebt");
	    ObjectValue value = Variables.objectValue(clientBill).serializationDataFormat("application/json").create();
	    
	    // Set the new partner businessKey
		String businessKey = RandomAlphanumericString.generate();
		execution.setVariable("businessKeyClient", businessKey);
		
		runtimeService.createMessageCorrelation("DebtSettlementRequestStart")
		.processInstanceBusinessKey(businessKey)								// business key of the new instance
		.setVariable("businessKeyACME", execution.getBusinessKey())				// business key to communicate with ACME
		.setVariable("processInstantiator", execution.getVariable("clientId"))	// the username of client in Camunda
		.setVariable("ACMEBillToPay", value)									// the bill to pay
		.correlate();
	  }
	  
}