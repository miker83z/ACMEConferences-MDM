package it.unibo.soseng.mdm.acme.model;

import java.util.ArrayList;
import java.util.List;

public class BillsCollection {
	
	private List<Bill> bills = new ArrayList<Bill>();

	public Bill getBill(int i) {
		return bills.get(i);
	}
	
	public void addBill(Bill bill) {
		bills.add(bill);
	}
	
	public void removeBill(int i) {
		bills.remove(i);
	}
	
	public void removeBill(Bill bill) {
		bills.remove(bill);
	}
	
	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	
	@Override
	public String toString() {
		return "BillsCollection [bills=" + bills + "]";
	}

}
