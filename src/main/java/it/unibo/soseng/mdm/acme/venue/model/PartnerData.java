package it.unibo.soseng.mdm.acme.venue.model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class PartnerData implements Serializable {
	
	protected String name;
	protected String type;
	protected String email;
	protected String phoneNumber;
	protected List<Address> addresses = new ArrayList<Address>();
	protected Boolean available;
	protected Boolean contacted;
	
	public PartnerData() {
		
	}
	
	public PartnerData(String name, String type, String email, String phoneNumber, List<Address> addresses) {
		this.name = name;
		this.type = type;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.addresses = addresses;
		this.available = true;
		this.contacted = false;
	}
	
	public PartnerData(
			String name, String type, String email, String phoneNumber, List<Address> addresses, 
			Boolean available, Boolean contacted) {
		this(name, type, email, phoneNumber, addresses);
		this.available = available;
		this.contacted = contacted;
	}
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	@Override
	public String toString() {
		return "PartnerData ["
				+ "name=" + name + ", "
				+ "type=" + type + ", "
				+ "email=" + email + ", "
				+ "phoneNumber=" + phoneNumber + ", "
				+ addresses.toString() + ", "
				+ "available=" + available + ", "
				+ "contacted=" + contacted
				+ "]";
	}

}
