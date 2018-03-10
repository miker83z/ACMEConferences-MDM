package it.unibo.soseng.mdm.acme.venue.model;

import java.util.ArrayList;
import java.util.List;

public class PartnerData {

	protected String name;
	protected String type;
	protected List<Address> addresses = new ArrayList<Address>();
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public String toString() {
		return "PartnerData ["
				+ "name=" + name + ", "
				+ "type=" + type + ", "
				+ "addresses=" + addresses 
				+ "]";
	}
	
}
