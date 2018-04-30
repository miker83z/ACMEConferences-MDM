package it.unibo.soseng.mdm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class BillsCollection, used to store multiple @see it.unibo.soseng.mdm.acme.model.Bill objects.
 * @author Mirko Zichichi
 */
public class BillsCollection {
	
	/** The bills. */
	private List<Bill> bills = new ArrayList<Bill>();

	/**
	 * Obtain total sum of bills.
	 *
	 * @return double tot sum
	 */
	public Double obtainTotSum() {
		Double tot = 0.0;
		for (Bill bill : bills)
			tot += bill.getAmount();
		return tot;
	}
	
	/**
	 * Gets the bill.
	 *
	 * @param i the i
	 * @return the bill
	 */
	public Bill getBill(int i) {
		return bills.get(i);
	}
	
	/**
	 * Adds the bill.
	 *
	 * @param bill the bill
	 */
	public void addBill(Bill bill) {
		bills.add(bill);
	}
	
	/**
	 * Removes the bill.
	 *
	 * @param i the i
	 */
	public void removeBill(int i) {
		bills.remove(i);
	}
	
	/**
	 * Removes the bill.
	 *
	 * @param bill the bill
	 */
	public void removeBill(Bill bill) {
		bills.remove(bill);
	}
	
	/**
	 * Gets the bills.
	 *
	 * @return the bills
	 */
	public List<Bill> getBills() {
		return bills;
	}

	/**
	 * Sets the bills.
	 *
	 * @param bills the new bills
	 */
	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClientBills [" + 
					"bills=" + bills + 
					"]";
	}

}
