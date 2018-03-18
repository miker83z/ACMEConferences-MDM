package it.unibo.soseng.mdm.acme.venue.model;

// import java.io.Serializable;
import it.unibo.soseng.mdm.acme.venue.model.Addresses;

public class PartnerData { // implements Serializable {
	
	/**
	 * 
	 */
	private String name;
	private String type;
	private String email;
	private String phoneNumber;
	private Addresses addresses;
	private Boolean available;
	private Boolean contacted;
	
	public PartnerData() {
		
	}
	
	public PartnerData(String name, String type, String email, String phoneNumber, Addresses addresses) {
		this.name = name;
		this.type = type;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.addresses = addresses;
		this.available = true;
		this.contacted = false;
	}
	
	public PartnerData(String name, String type, String email, String phoneNumber, Addresses addresses, 
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
	public Addresses getAddresses() {
		return addresses;
	}
	public void setAddresses(Addresses addresses) {
		this.addresses = addresses;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailability(Boolean available) {
		this.available = available;
	}
	public Boolean getContacted() {
		return contacted;
	}
	public void setContacted(Boolean contacted) {
		this.contacted = contacted;
	}
	
	public String toString() {
		return "PartnerData ["
				+ "name=" + name + ", "
				+ "type=" + type + ", "
				+ "email=" + email + ", "
				+ "phoneNumber=" + phoneNumber + ", "
				+ "addresses=" + addresses + ", "
				+ "available=" + available + ", "
				+ "contacted=" + contacted
				+ "]";
	}

}
