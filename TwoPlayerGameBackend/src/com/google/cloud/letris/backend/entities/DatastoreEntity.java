/**
 * 
 */
package com.google.cloud.letris.backend.entities;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * @author Arpit
 * 
 */
public class DatastoreEntity implements Serializable {

	/**
	 * The default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Entity
	 */
	private Entity entity = null;

	/**
	 * Datastore property - Date Created
	 */
	public static final String DATECREATED_PROPERTY = "dateCreated";

	/**
	 * Constructor.
	 * 
	 * @param entity
	 *            the {@link Entity}.
	 * 
	 * @param isNew
	 *            indicates whether the entity is newly created or retrieved
	 *            from Datastore.
	 * 
	 * @throws IllegalArgumentException
	 *             if entity is null.
	 */
	protected DatastoreEntity(Entity entity, boolean isNew) {
		if (entity == null) {
			throw new IllegalArgumentException("entity cannot be null");
		}

		this.entity = entity;

		if (isNew) {
			this.setProperty(DATECREATED_PROPERTY, new Date());
		}
	}

	/**
	 * Gets the underlying Datastore entity.
	 * 
	 * @return {@link Entity}.
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Gets the entity key.
	 * 
	 * @return {@link Key}.
	 */
	public Key getKey() {
		return entity.getKey();
	}

	/**
	 * Gets the parent entity key.
	 * 
	 * @return {@link Key}.
	 */
	public Key getParentKey() {
		return entity.getParent();
	}

	/**
	 * Gets the numeric identifier of the entity key.
	 * 
	 * @return {@link Long}.
	 */
	public long getId() {
		return entity.getKey().getId();
	}

	/**
	 * Gets the date that the entity was created.
	 */
	public Date getDateCreated() {
		return getPropertyOfType(DATECREATED_PROPERTY);
	}
	
	/**
	 * Sets the property relevant to the game
	 * 
	 * @param Name of the property
	 * @param value of the property
	 * 
	 * @return void
	 */
	protected void setProperty(String propertyName, Object value) {
		entity.setProperty(propertyName, value);
	}

	/**
	 * Return the property value specified by the input
	 * 
	 * @param propertyName
	 * 
	 * @return Property value Object
	 */
	protected Object getProperty(String propertyName) {
		return entity.getProperty(propertyName);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getPropertyOfType(String propertyName) {
		return (T) getProperty(propertyName);
	}
}
