package it.unibo.soseng.mdm.acme.financial.delegate;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * The Class SendNoBillsNotificationAndRemainingFundsInfo, used for "Send No Bills to Pay Notification and Info on Remaining funds" 
 * message event to notify that there are no more bills to pay and informs him about the state of funds.
 * @author Mirko Zichichi
 */
public class SendNoBillsNotificationAndRemainingFundsInfo implements JavaDelegate {

	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
	    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    Double availableFunds = (Double) execution.getVariable("availableFunds");
	    Double sumPayed = (Double) execution.getVariable("sumPayed");
	    Double remainingFundsCount = availableFunds - sumPayed;
	    execution.setVariable("remainingFundsCount", remainingFundsCount);
	    
	    runtimeService.createMessageCorrelation("NoBillsToPayAndRemainingFundsInfo")
			.processInstanceBusinessKey((String) execution.getVariable("businessKeyClient"))
			.setVariable("remainingFundsCount", remainingFundsCount)
			.correlate();
	  }
	  
}