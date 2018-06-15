package it.unibo.soseng.mdm.partner.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Refuse the received job offer.
 * 
 * @author Davide Marchi
 *
 */
public class RefuseJob implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// Get my partnerName
		String partnerName = (String) execution.getVariable("processTenant");
				
		// Send message
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    runtimeService.createMessageCorrelation("partner_not_available_" + partnerName)
	    .processInstanceBusinessKey((String) execution.getVariable("acmeBusinessKey"))
	    .correlate();	
	}

}
