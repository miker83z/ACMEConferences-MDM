package it.unibo.soseng.mdm.acme.financial.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;

import it.unibo.soseng.mdm.util.EmailSender;

/**
 * The Class SendEmailRemainder, used for "Send email remainder" task.
 * @author Mirko Zichichi
 */
public class SendEmailRemainder implements JavaDelegate{
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		String clientID = (String) execution.getVariable("clientId");
		User client = execution.getProcessEngineServices().getIdentityService().createUserQuery().userId(clientID).singleResult();
		
		EmailSender emailSender = new EmailSender("provecamundaisos@gmail.com", "camundaisos", "ACME Conferences");
		emailSender.configureConnection();

		// Create and send an email to the client
		String emailMessage = "Dear " + client.getFirstName() + " " + client.getLastName() + ",\n"
				+ "\n"
				+ "Pay your Debt\n"
				+ "\n"
				+ "\n"
				+ "Best regards,\n"
				+ "Demo demo\n"
				+ "President of ACME Conferences";
	
		emailSender.send(client.getLastName() + " Pay your Debt", emailMessage, "provecamundaisos@gmail.com" /*client.getEmail()*/);
	}
}
