package it.unibo.soseng.mdm.acme.financial.delegate;

import javax.xml.ws.WebServiceException;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.generated.bank.BankPort;
import it.unibo.soseng.mdm.acme.generated.bank.BankPortService;
import it.unibo.soseng.mdm.acme.generated.bank.UserLogin;
import it.unibo.soseng.mdm.acme.generated.bank.UserLoginResponse;

public class LoginBank implements JavaDelegate{
	
	private String username = "ACME";
	private String password = "ACME";
	
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("loginAttempts",((Integer) execution.getVariable("loginAttempts")) + 1 );
		try {
			BankPortService bankService = new BankPortService();
			BankPort bank = bankService.getBankPortServicePort();
			UserLogin loginRequest = new UserLogin();
			loginRequest.setUsername(username);
			loginRequest.setPassword(password);
			UserLoginResponse loginResponse = bank.userLogin(loginRequest);
	
			execution.setVariable("loginResponse", loginResponse.isFlag());
			if(loginResponse.isFlag()) {
				execution.setVariable("acmeBankID", loginResponse.getUserID());
			}
		}
		catch(WebServiceException e) {
			throw new BpmnError("WEB_SERVICE_ERROR");
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
