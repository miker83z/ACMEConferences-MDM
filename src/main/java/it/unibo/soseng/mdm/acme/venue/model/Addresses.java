package it.unibo.soseng.mdm.acme.venue.model;

import java.util.ArrayList;
import java.util.List;

// import java.io.Serializable;
import it.unibo.soseng.mdm.acme.venue.model.Address;

public class Addresses { // implements Serializable {
	
	/**
	 * 
	 */
	
	private List<Address> addresses = new ArrayList<Address>();
	
	public Addresses() {
		
	}
	
	public Addresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public List<Address> getAddresses(){
		return addresses;
	}
	
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public void add(Address address) {
		this.addresses.add(address);
	}
	
	public String toString() {
		return "Addresses ["
				+ "addresses=" + addresses
				+ "]";
	}
	
}