package it.unibo.soseng.mdm.model;

/**
 * The Class Invoice, used to model an invoice.
 */
public class Invoice {
	
	/** The bills payed. */
	private BillsCollection billsPayed;
	
	/** The registration funds. */
	private Double registrationFunds;
	
	/** The client debt payed. */
	private Double clientDebtPayed;
	
	/** The acme services costs. */
	private Double acmeServicesCosts;
	
	/** The remaining funds. */
	private Double remainingFunds;
	
	/**
	 * Instantiates a new invoice.
	 */
	public Invoice() {
		//
	}
	
	/**
	 * Instantiates a new invoice.
	 *
	 * @param billsPayed the bills payed
	 * @param registrationFunds the registration funds
	 * @param clientDebtPayed the client debt payed
	 * @param acmeServicesCosts the acme services costs
	 * @param remainingFunds the remaining funds
	 */
	public Invoice(BillsCollection billsPayed, Double registrationFunds, Double clientDebtPayed,
			Double acmeServicesCosts, Double remainingFunds) {
		this.billsPayed = billsPayed;
		this.registrationFunds = registrationFunds;
		this.clientDebtPayed = clientDebtPayed;
		this.acmeServicesCosts = acmeServicesCosts;
		this.remainingFunds = remainingFunds;
	}

	/**
	 * Gets the bills payed.
	 *
	 * @return the bills payed
	 */
	public BillsCollection getBillsPayed() {
		return billsPayed;
	}

	/**
	 * Sets the bills payed.
	 *
	 * @param billsPayed the new bills payed
	 */
	public void setBillsPayed(BillsCollection billsPayed) {
		this.billsPayed = billsPayed;
	}

	/**
	 * Gets the registration funds.
	 *
	 * @return the registration funds
	 */
	public Double getRegistrationFunds() {
		return registrationFunds;
	}

	/**
	 * Sets the registration funds.
	 *
	 * @param registrationFunds the new registration funds
	 */
	public void setRegistrationFunds(Double registrationFunds) {
		this.registrationFunds = registrationFunds;
	}

	/**
	 * Gets the client debt payed.
	 *
	 * @return the client debt payed
	 */
	public Double getClientDebtPayed() {
		return clientDebtPayed;
	}

	/**
	 * Sets the client debt payed.
	 *
	 * @param clientDebtPayed the new client debt payed
	 */
	public void setClientDebtPayed(Double clientDebtPayed) {
		this.clientDebtPayed = clientDebtPayed;
	}

	/**
	 * Gets the acme services costs.
	 *
	 * @return the acme services costs
	 */
	public Double getAcmeServicesCosts() {
		return acmeServicesCosts;
	}

	/**
	 * Sets the acme services costs.
	 *
	 * @param acmeServicesCosts the new acme services costs
	 */
	public void setAcmeServicesCosts(Double acmeServicesCosts) {
		this.acmeServicesCosts = acmeServicesCosts;
	}

	/**
	 * Gets the remaining funds.
	 *
	 * @return the remaining funds
	 */
	public Double getRemainingFunds() {
		return remainingFunds;
	}

	/**
	 * Sets the remaining funds.
	 *
	 * @param remainingFunds the new remaining funds
	 */
	public void setRemainingFunds(Double remainingFunds) {
		this.remainingFunds = remainingFunds;
	}
	
}
