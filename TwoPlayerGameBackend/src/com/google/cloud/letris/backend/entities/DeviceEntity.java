package com.google.cloud.letris.backend.entities;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class DeviceEntity extends DatastoreEntity {

	/**
	 * The Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of Device entity kind in Datastore
	 */
	public static final String KIND = "Device";

	/**
	 * Names of properties in Datastore. Public if used in queries. Private
	 * otherwise.
	 */
	public static final String DEVICEID_PROPERTY = "deviceId";
	private static final String DEVICETYPE_PROPERTY = "deviceType";

	/**
	 * Android device type.
	 */
	public static final int DEVICE_TYPE_ANDROID = 1;

	/**
	 * Constructor used to create a new device Datastore entity.
	 * 
	 * @param deviceId
	 *            the ID of the device, typically assigned by the push
	 *            notifications system.
	 * @param deviceType
	 *            the type of device.
	 * @param playerKey
	 *            the key of the player who is using the device.
	 */
	public DeviceEntity(String deviceId, Integer deviceType, Key playerKey) {
		super(new Entity(KIND, playerKey), true);

		setProperty(DEVICEID_PROPERTY, deviceId);
		setProperty(DEVICETYPE_PROPERTY, deviceType);
	}

	/**
	 * Constructor used when retrieving an existing Device entity from Datastore
	 * 
	 * @param entity
	 *            {@link Entity} to populate the model with.
	 */
	public DeviceEntity(Entity entity) {
		super(entity, false);
	}

	/**
	 * Gets the device type.
	 * 
	 */
	public int getDeviceType() {
		Long deviceType = getPropertyOfType(DEVICETYPE_PROPERTY);
		return deviceType.intValue();
	}

	/**
	 * Updates the device ID.
	 * 
	 * @param newDeviceId
	 *            the ID this device should be changed to.
	 */
	public void updateDeviceId(String newDeviceId) {
		setProperty(DEVICEID_PROPERTY, newDeviceId);
	}
}
