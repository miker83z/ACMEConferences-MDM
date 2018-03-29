package it.unibo.soseng.mdm.acme.financial;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.generated.BankPort;
import it.unibo.soseng.mdm.acme.generated.BankPortService;
import it.unibo.soseng.mdm.acme.generated.TransferPayment;
import it.unibo.soseng.mdm.acme.generated.TransferPaymentResponse;
import it.unibo.soseng.mdm.acme.generated.UserLogin;
import it.unibo.soseng.mdm.acme.generated.UserLoginResponse;
import it.unibo.soseng.mdm.acme.generated.UserLogout;
import it.unibo.soseng.mdm.acme.generated.UserLogoutResponse;

public class InternalPayment implements JavaDelegate{
	
	public void execute(DelegateExecution execution) throws Exception {
		String username = "mirko";//(String) execution.getVariable("usrBank");
		String password = "mirko";//(String) execution.getVariable("pswBank");
		BankPortService bankService = new BankPortService();
		BankPort bank = bankService.getBankPortServicePort();
		UserLogin loginRequest = new UserLogin();
		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
		UserLoginResponse loginResponse = bank.userLogin(loginRequest);
		if(loginResponse.isFlag()) {
			execution.setVariable("loginResponse", loginResponse.getUserID());
			int tmpID = loginResponse.getUserID();
			
			TransferPayment transferRequest = new TransferPayment();
			transferRequest.setUserID(tmpID);
			transferRequest.setQuantity(1000.0);
			transferRequest.setReceiver("michele");
			TransferPaymentResponse transferResponse = bank.transferPayment(transferRequest);
			if( transferResponse.isFlag() )
				execution.setVariable("transferResponse", transferResponse.getMessage());
			
			UserLogout logoutRequest = new UserLogout();
			logoutRequest.setUserID(tmpID);
			UserLogoutResponse logoutResponse = bank.userLogout(logoutRequest);
			execution.setVariable("logoutResponse", logoutResponse.isFlag());
		}
	}
}
