package it.unibo.soseng.mdm.acme.financial.payviawiretransfer;

import java.util.ArrayList;

import javax.xml.ws.WebServiceException;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.model.Bill;
import it.unibo.soseng.mdm.model.BillsCollection;
import it.unibo.soseng.mdm.services.generated.bank.BankPort;
import it.unibo.soseng.mdm.services.generated.bank.BankPortService;
import it.unibo.soseng.mdm.services.generated.bank.TransferPayment;
import it.unibo.soseng.mdm.services.generated.bank.TransferPaymentResponse;

/**
 * The Class InternalPayment, used for Pay task to pay bills through the Bank service. After @see it.unibo.soseng.mdm.acme.financial.delegate.LoginBank it execute the payment process.
 * @author Mirko Zichichi
 */
public class InternalPayment implements JavaDelegate{
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("transferAttempts",((Integer) execution.getVariable("transferAttempts")) + 1 );
		try {
			BankPortService bankService = new BankPortService();
			BankPort bank = bankService.getBankPortServicePort();
			
			String bankID = (String)execution.getVariable("acmeBankID");
			BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");	//The main collection used to store bills to pay through this class
			
			boolean allPaymentsCompletedflag = true;
			ArrayList<Bill> billsPayed = new ArrayList<Bill>();	//Local, it's used to remove the bills payed from billsToPay
			//Pay bills from billsToPay
			for(Bill bill : bills.getBills()) {
				TransferPayment transferRequest = new TransferPayment();
				transferRequest.setUserID(bankID);
				transferRequest.setQuantity(bill.getAmount());
				transferRequest.setReceiver(bill.getReceiver());
				TransferPaymentResponse transferResponse = bank.transferPayment(transferRequest);
				if( transferResponse.isFlag() ) {
					billsPayed.add(bill);
				}
				else {	//Not all bills are payed
					allPaymentsCompletedflag = false;
					bill.setErrorMessage(transferResponse.getMessage());
				}
			}
			//Remove payed bills from billsToPay and add them to the global variable billsPayed
			BillsCollection payed = (BillsCollection) execution.getVariable("billsPayed");
			for(Bill bill : billsPayed) {
				execution.setVariable("sumPayed", (Double) execution.getVariable("sumPayed") + bill.getAmount());
				bills.removeBill(bill);
				payed.addBill(bill);
			}
			execution.setVariable("allPaymentsCompleted", allPaymentsCompletedflag);
		} catch(WebServiceException e) {
			e.printStackTrace();
			throw new BpmnError("WEB_SERVICE_ERROR");
		}
	}
}
