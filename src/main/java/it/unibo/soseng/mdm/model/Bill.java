package it.unibo.soseng.mdm.model;

/**
 * The Class Bill, used to encapsulate all the methods and attributes needed to manage a bill.
 * @author Mirko Zichichi
 */
public class Bill {
	
	/** The receiver. */
	private String receiver;
	
	/** The amount. */
	private Double amount;
	
	/** The optional error message returned by the Bank service during payments. */
	private String errorMessage;
	
	/**
	 * Gets the receiver.
	 *
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}
	
	/**
	 * Sets the receiver.
	 *
	 * @param receiver the new receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	
	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bill [" + 
					"receiver=" + receiver + "," +
					"amount=" + amount + 
					"]";
	}
}
