package it.unibo.soseng.mdm.acme.financial;

import java.util.ArrayList;

import javax.xml.ws.WebServiceException;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import it.unibo.soseng.mdm.acme.generated.BankPort;
import it.unibo.soseng.mdm.acme.generated.BankPortService;
import it.unibo.soseng.mdm.acme.generated.TransferPayment;
import it.unibo.soseng.mdm.acme.generated.TransferPaymentResponse;
import it.unibo.soseng.mdm.acme.model.Bill;
import it.unibo.soseng.mdm.acme.model.BillsCollection;

public class InternalPayment implements JavaDelegate{
	
	//wsimport -s /home/miker/eclipse-workspace/ACMEConferences/src/main/java -p it.unibo.soseng.mdm.acme.generated -Xnocompile -b binding.xml -wsdllocation /server.wsdl server.wsdl
	
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("transferAttempts",((Integer) execution.getVariable("transferAttempts")) + 1 );
		try {
			BankPortService bankService = new BankPortService();
			BankPort bank = bankService.getBankPortServicePort();
			
			String bankID = (String)execution.getVariable("acmeBankID");
			BillsCollection bills = (BillsCollection) execution.getVariable("billsToPay");
			
			boolean allPaymentsCompletedflag = true;
			ArrayList<Bill> billsPayed = new ArrayList<Bill>();
			for(Bill bill : bills.getBills()) {
				TransferPayment transferRequest = new TransferPayment();
				transferRequest.setUserID(bankID);
				transferRequest.setQuantity(bill.getAmount());
				transferRequest.setReceiver(bill.getReceiver());
				TransferPaymentResponse transferResponse = bank.transferPayment(transferRequest);
				if( transferResponse.isFlag() ) {
					billsPayed.add(bill);
				}
				else {
					allPaymentsCompletedflag = false;
					bill.setErrorMessage(transferResponse.getMessage());
				}
			}
			BillsCollection payed = (BillsCollection) execution.getVariable("billsPayed");
			for(Bill bill : billsPayed) {
				execution.setVariable("sumPayed", (Double) execution.getVariable("sumPayed") + bill.getAmount());
				bills.removeBill(bill);
				payed.addBill(bill);
			}
			execution.setVariable("allPaymentsCompleted", allPaymentsCompletedflag);
		} catch(WebServiceException e) {
			throw new BpmnError("WEB_SERVICE_ERROR");
		}
	}
}
