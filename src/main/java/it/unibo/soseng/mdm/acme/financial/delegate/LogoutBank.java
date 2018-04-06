package it.unibo.soseng.mdm.acme.financial.delegate;

import javax.xml.ws.WebServiceException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.generated.bank.BankPort;
import it.unibo.soseng.mdm.acme.generated.bank.BankPortService;
import it.unibo.soseng.mdm.acme.generated.bank.UserLogout;
import it.unibo.soseng.mdm.acme.generated.bank.UserLogoutResponse;

public class LogoutBank implements JavaDelegate{
	
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("logoutAttempts",((Integer) execution.getVariable("logoutAttempts")) + 1 );
		try {
			BankPortService bankService = new BankPortService();
			BankPort bank = bankService.getBankPortServicePort();
			UserLogout logoutRequest = new UserLogout();
			logoutRequest.setUserID(((String)execution.getVariable("acmeBankID")));
			UserLogoutResponse logoutResponse = bank.userLogout(logoutRequest);
			execution.setVariable("logoutResponse", logoutResponse.isFlag());
		} catch(WebServiceException e) {
			System.out.println("WebServiceException");
		}
	}
}
