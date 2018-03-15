package it.unibo.soseng.mdm.acme.venue.model;

import java.util.ArrayList;
import java.util.List;

public class PartnerData {

	protected String name;
	protected String type;
	protected List<Address> addresses = new ArrayList<Address>();
	protected Boolean available;
	protected Boolean contacted;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
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
	public Boolean isAvailable() {
		return available;
	}
	public void setAvailability(Boolean available) {
		this.available = available;
	}
	public Boolean isContacted() {
		return contacted;
	}
	public void setContacted(Boolean contacted) {
		this.contacted = contacted;
	}
	
	public String toString() {
		return "PartnerData ["
				+ "name=" + name + ", "
				+ "type=" + type + ", "
				+ addresses.toString() + ", "
				+ "available=" + available + ", "
				+ "contacted=" + contacted
				+ "]";
	}

}
