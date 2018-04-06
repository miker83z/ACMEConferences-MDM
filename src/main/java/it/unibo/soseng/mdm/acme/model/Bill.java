package it.unibo.soseng.mdm.acme.model;

public class Bill {
	
	private String receiver;
	private Double amount;
	
	private String errorMessage;
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
	public String toString() {
		return "Bill [" + 
					"receiver=" + receiver + "," +
					"amount=" + amount + 
					"]";
	}
}
