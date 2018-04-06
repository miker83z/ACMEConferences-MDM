package it.unibo.soseng.mdm.client;

import javax.xml.ws.WebServiceException;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import it.unibo.soseng.mdm.acme.generated.BankPort;
import it.unibo.soseng.mdm.acme.generated.BankPortService;
import it.unibo.soseng.mdm.acme.generated.TransferPayment;
import it.unibo.soseng.mdm.acme.generated.TransferPaymentResponse;
import it.unibo.soseng.mdm.acme.generated.UserLogin;
import it.unibo.soseng.mdm.acme.generated.UserLoginResponse;
import it.unibo.soseng.mdm.acme.generated.UserLogout;
import it.unibo.soseng.mdm.acme.generated.UserLogoutResponse;
import it.unibo.soseng.mdm.acme.model.Bill;

public class DebtPaymentTaskListener implements TaskListener {
	
	public void notify(DelegateTask delegateTask) {
		boolean ACMEPaymentSuccesful = false;
		String username = (String) delegateTask.getVariable("bankUsername");
		String password = (String) delegateTask.getVariable("bankPassword");
		try {
			BankPortService bankService = new BankPortService();
			BankPort bank = bankService.getBankPortServicePort();
			
			UserLogin loginRequest = new UserLogin();
			loginRequest.setUsername(username);
			loginRequest.setPassword(password);
			UserLoginResponse loginResponse = bank.userLogin(loginRequest);
			
			if(loginResponse.isFlag()) {
				String bankID = loginResponse.getUserID();
				Bill bill = (Bill) delegateTask.getVariable("ACMEBillToPay");
				TransferPayment transferRequest = new TransferPayment();
				transferRequest.setUserID(bankID);
				transferRequest.setQuantity(bill.getAmount());
				transferRequest.setReceiver(bill.getReceiver());
				TransferPaymentResponse transferResponse = bank.transferPayment(transferRequest);
				ACMEPaymentSuccesful = transferResponse.isFlag();
				UserLogout logoutRequest = new UserLogout();
				logoutRequest.setUserID(bankID);
				UserLogoutResponse logoutResponse = bank.userLogout(logoutRequest);
			}
		} catch(WebServiceException e) {
			throw new BpmnError("WEB_SERVICE_ERROR");
		} finally {
			delegateTask.setVariable("ACMEPaymentSuccesful", ACMEPaymentSuccesful);
		}
	}
}
