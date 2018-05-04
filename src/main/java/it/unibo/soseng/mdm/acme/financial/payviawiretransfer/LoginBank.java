package it.unibo.soseng.mdm.acme.financial.payviawiretransfer;

import javax.xml.ws.WebServiceException;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.services.generated.bank.BankPort;
import it.unibo.soseng.mdm.services.generated.bank.BankPortService;
import it.unibo.soseng.mdm.services.generated.bank.UserLogin;
import it.unibo.soseng.mdm.services.generated.bank.UserLoginResponse;

/**
 * The Class LoginBank, used for Login task to login to the Bank service.
 * @author Mirko Zichichi
 */
public class LoginBank implements JavaDelegate{
	
	/** The bank username. */
	private String username = "ACME";
	
	/** The bank password. */
	private String password = "ACME";
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
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
			else
				throw new WebServiceException();
		}
		catch(WebServiceException e) {
			throw new BpmnError("WEB_SERVICE_ERROR");
		}
	}

	/**
	 * Gets the bank username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the bank username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the bank password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the bank password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
