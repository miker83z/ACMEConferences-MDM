package it.unibo.soseng.mdm.services.django;

/**
 * The Class EventReservation, used to manage an EventRegistration in RegistrationPlatform.
 * @author Mirko Zichichi
 */
public class EventReservation {
	
	/** The reservation id. */
	private int reservationId;
	
	/** The event ID. */
	private int eventId;
	
	/** The user id. */
	private int userId;
	
	/** The bank user. */
	private String bankUser;
	
	/** The is staff. */
	private boolean isStaff;
	
	/**
	 * Instantiates a new event reservation.
	 *
	 * @param reservationId the reservation id
	 * @param eventId the event id
	 * @param userId the user id
	 * @param bankUser the bank user
	 * @param isStaff the is staff
	 */
	public EventReservation( int reservationId, int eventId, int userId, String bankUser, boolean isStaff ) {
		this.reservationId = reservationId;
		this.eventId = eventId;
		this.userId = userId;
		this.bankUser = bankUser;
		this.isStaff = isStaff;
	}

	/**
	 * Gets the reservation id.
	 *
	 * @return the reservation id
	 */
	public int getReservationId() {
		return reservationId;
	}

	/**
	 * Sets the reservation id.
	 *
	 * @param reservationId the new reservation id
	 */
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	/**
	 * Gets the event id.
	 *
	 * @return the event id
	 */
	public int getEventId() {
		return eventId;
	}

	/**
	 * Sets the event id.
	 *
	 * @param eventId the new event id
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the bank user.
	 *
	 * @return the bank user
	 */
	public String getBankUser() {
		return bankUser;
	}

	/**
	 * Sets the bank user.
	 *
	 * @param bankUser the new bank user
	 */
	public void setBankUser(String bankUser) {
		this.bankUser = bankUser;
	}

	/**
	 * Checks if is staff.
	 *
	 * @return true, if is staff
	 */
	public boolean isStaff() {
		return isStaff;
	}

	/**
	 * Sets the staff.
	 *
	 * @param isStaff the new staff
	 */
	public void setStaff(boolean isStaff) {
		this.isStaff = isStaff;
	}
	
}