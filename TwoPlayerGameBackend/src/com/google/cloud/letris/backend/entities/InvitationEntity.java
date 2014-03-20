package com.google.cloud.letris.backend.entities;

import java.io.Serializable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class InvitationEntity extends DatastoreEntity implements Serializable {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of Invitation entity kind in Datastore. Public because it is also
	 * used for Datastore queries.
	 */
	public static final String KIND = "Invitation";

	/**
	 * Names of properties in Datastore.
	 */
	private static final String SENDER_KEY_PROPERTY = "senderKey";
	private static final String INVITEE_KEY_PROPERTY = "inviteeKey";
	private static final String STATUS_PROPERTY = "status";

	/**
	 * Invitation Status.
	 */
	public enum Status {
		SENT, ACCEPTED, DECLINED, CANCELED;
	}

	/**
	 * Constructor that is used when retrieving an existing invitation entity
	 * from Datastore.
	 * 
	 * @param entity
	 *            {@link Entity}.
	 */
	public InvitationEntity(Entity entity) {
		super(entity, false);
	}

	public InvitationEntity(Key senderKey, Key inviteeKey, Key gameKey) {
		super(new Entity(KIND, gameKey), true);

		if (senderKey == null) {
			throw new IllegalArgumentException("senderKey cannot be null");
		}

		if (inviteeKey == null) {
			throw new IllegalArgumentException("inviteeKey cannot be null");
		}

		setProperty(SENDER_KEY_PROPERTY, senderKey);
		setProperty(INVITEE_KEY_PROPERTY, inviteeKey);
		setStatus(Status.SENT);
	}

	/**
	 * Accepts the invitation.
	 * 
	 * @return true if the invitation can be accepted; false otherwise.
	 */
	public boolean accept() {
		boolean valid = false;

		switch (this.getStatus()) {
		case SENT:
			setStatus(Status.ACCEPTED);
			valid = true;
			break;
		case ACCEPTED:
			valid = true; // Still valid, but not changing state.
			break;
		default:
			break;
		}

		return valid;
	}

	/**
	 * Declines the invitation.
	 * 
	 * @return true if the invitation can be declined; false otherwise.
	 */
	public boolean decline() {
		boolean valid = false;

		switch (this.getStatus()) {
		case SENT:
			setStatus(Status.DECLINED);
			valid = true;
			break;
		case DECLINED:
		case CANCELED:
			valid = true; // Still valid, but not changing state.
			break;
		default:
			break;
		}

		return valid;
	}

	/**
	 * Cancels the invitation.
	 * 
	 * @return true if the invitation can be cancelled; false otherwise.
	 */
	public boolean cancel() {
		boolean valid = false;

		switch (this.getStatus()) {
		case SENT:
			setStatus(Status.CANCELED);
			valid = true;
			break;
		case CANCELED:
		case DECLINED:
			// Still valid, but not changing state.
			valid = true;
			break;
		default:
			break;
		}

		return valid;
	}

	/**
	 * Gets the sender entity key.
	 */
	public Key getSenderKey() {
		return getPropertyOfType(SENDER_KEY_PROPERTY);
	}

	/**
	 * Gets the invitee entity key.
	 */
	public Key getInviteeKey() {
		return getPropertyOfType(INVITEE_KEY_PROPERTY);
	}

	/**
	 * Gets the status of the invitation.
	 */
	public Status getStatus() {
		long status = this.getPropertyOfType(STATUS_PROPERTY);
		return Status.values()[(int) status];
	}

	/**
	 * Returns true if the invitation was accepted.
	 * 
	 * @return {@link Boolean}
	 */
	public Boolean wasAccepted() {
		return getStatus() == Status.ACCEPTED;
	}

	private void setStatus(Status status) {
		this.setProperty(STATUS_PROPERTY, (long) status.ordinal());
	}

}
