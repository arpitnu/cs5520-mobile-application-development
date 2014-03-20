package com.google.cloud.letris.backend.entities;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Entity;

public class BoardEntity extends DatastoreEntity implements Serializable {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the Board entity kind in Datastore. Public because it is also
	 * used for Datastore queries.
	 */
	public static final String KIND = "Board";

	/**
	 * Names of properties in Datastore.
	 */
	private static final String GRID_DEFINITION_PROPERTY = "gridDefinition";
	private static final String RIDDLES_PROPERTY = "riddles";
	private static final String ANSWERS_PROPERTY = "answers";
	private static final String LEVEL_PROPERTY = "level";
	private static final String ALLOTTEDTIME_PROPERTY = "allottedTime";

	/**
	 * Constructor that is used when retrieving an existing Board entity from
	 * Datastore.
	 * 
	 * @param entity
	 *            {@link Entity} to populate the model with.
	 */
	public BoardEntity(Entity entity) {
		super(entity, false);
	}

	/**
	 * Gets the board level.
	 */
	public int getLevel() {
		Long level = getPropertyOfType(LEVEL_PROPERTY);
		return level.intValue();
	}

	/**
	 * Gets the board definition.
	 */
	public List<String> getBoardDefinition() {
		return getPropertyOfType(GRID_DEFINITION_PROPERTY);
	}

	/**
	 * Gets the list of riddles.
	 */
	public List<String> getClues() {
		return getPropertyOfType(RIDDLES_PROPERTY);
	}

	/**
	 * Gets the list of answers.
	 */
	public List<String> getAnswers() {
		return getPropertyOfType(ANSWERS_PROPERTY);
	}

	/**
	 * Gets the time allotted to complete the game in milliseconds.
	 */
	public long getAllottedTime() {
		return getPropertyOfType(ALLOTTEDTIME_PROPERTY);
	}

}
