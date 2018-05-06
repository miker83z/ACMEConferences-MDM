package it.unibo.soseng.mdm.client.listener;

import javax.xml.ws.WebServiceException;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.services.generated.bank.BankPort;
import it.unibo.soseng.mdm.services.generated.bank.BankPortService;
import it.unibo.soseng.mdm.services.generated.bank.TransferPayment;
import it.unibo.soseng.mdm.services.generated.bank.TransferPaymentResponse;
import it.unibo.soseng.mdm.services.generated.bank.UserLogin;
import it.unibo.soseng.mdm.services.generated.bank.UserLoginResponse;
import it.unibo.soseng.mdm.services.generated.bank.UserLogout;
import it.unibo.soseng.mdm.services.generated.bank.UserLogoutResponse;

/**
 * The class DebtPaymentTaskListener, used for "Pay" task to let the user outside of ACME to pay a bill to ACME.
 * @author Mirko Zichichi
 */
public class DebtPaymentTaskListener implements TaskListener {
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.TaskListener#notify(org.camunda.bpm.engine.delegate.DelegateTask)
	 */
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
				@SuppressWarnings("unused")
				UserLogoutResponse logoutResponse = bank.userLogout(logoutRequest);
			}
		} catch(WebServiceException e) {
			throw new BpmnError("WEB_SERVICE_ERROR");
		} finally {
			delegateTask.setVariable("ACMEPaymentSuccesful", ACMEPaymentSuccesful);
		}
	}
}
