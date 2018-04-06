package it.unibo.soseng.mdm.acme.financial.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendEmailRemainder implements JavaDelegate{
	
	//private final static Logger LOGGER = Logger.getLogger(SendEmailRemainder.class.getName());
	
	public void execute(DelegateExecution execution) throws Exception {
		String clientID = (String) execution.getVariable("clientId");
		String recipient = execution.getProcessEngineServices().getIdentityService().createUserQuery().userId(clientID).singleResult().getEmail();
		
		//Send Email
		/*
		Email email = new SimpleEmail();
		email.setHostName(HOST);
        email.setAuthentication(USER, PWD);

        try {
          email.setFrom("noreply@camunda.org");
          email.setSubject("Pay your bill");
          email.setMsg("Pay.");
          email.addTo(recipient);
          email.send();
          
          LOGGER.info("Task Assignment Email successfully sent to user '" + clientID + "' with address '" + recipient + "'.");           
        } catch (Exception e) {
          LOGGER.log(Level.WARNING, "Could not send email to assignee", e);
        }
        */
	}
}
