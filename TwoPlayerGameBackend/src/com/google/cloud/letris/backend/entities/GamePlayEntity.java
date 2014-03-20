package com.google.cloud.letris.backend.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class GamePlayEntity extends DatastoreEntity {

	/**
	 * The Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of GamePlay entity kind in Datastore. Public because it is also used
	 * for Datastore queries.
	 */
	public static final String KIND = "GamePlay";

	/**
	 * Names of properties in Datastore.
	 */
	private static final String PLAYERKEY_PROPERTY = "playerKey";
	private static final String ANSWERS_PROPERTY = "answers";
	private static final String FINISHED_PROPERTY = "finished";

	// private static final String TIMELEFT_PROPERTY = "timeLeft";

	/**
	 * Constructor that is used when retrieving an existing game player entity
	 * from Datastore.
	 * 
	 */
	public GamePlayEntity(Entity entity) {
		super(entity, false);
	}

	/**
	 * Constructor used to create a new game player entity.
	 * 
	 * @param playerKey
	 *            the key of the player.
	 * @param gameKey
	 *            the key of the game.
	 * @throws IllegalArgumentException
	 *             if playerKey is null.
	 */
	public GamePlayEntity(Key playerKey, Key gameKey) {
		super(new Entity(KIND, gameKey), true);

		if (playerKey == null) {
			throw new IllegalArgumentException("playerKey cannot be null");
		}

		setProperty(PLAYERKEY_PROPERTY, playerKey);
		setProperty(ANSWERS_PROPERTY, null);
		setProperty(FINISHED_PROPERTY, false);
		// setProperty(TIMELEFT_PROPERTY, 0L);
	}

	/**
	 * Submits words and records that the player has finished the game
	 * 
	 * @param words
	 *            the list of words.
	 * 
	 * @return void
	 */
	public void submitSelectedWords(ArrayList<String> words) {
		setProperty(FINISHED_PROPERTY, true);
		setProperty(ANSWERS_PROPERTY, words);
	}

	/**
	 * Gets the player's answers.
	 * 
	 * @return {@link ArrayList}&lt;{@link String}&gt; List of selected words
	 *         from the puzzle
	 */
	public ArrayList<String> getSelectedWords() {
		ArrayList<String> words = getPropertyOfType(ANSWERS_PROPERTY);
		return words;
	}

	/**
	 * Checks if the player finished the game.
	 * 
	 * @return true if the player finished the game; false otherwise.
	 */
	public boolean getFinished() {
		return getPropertyOfType(FINISHED_PROPERTY);
	}

	// TODO
	// /**
	// * Returns how much time was left in milliseconds when the player
	// finished.
	// */
	// public long getTimeLeft() {
	// return getPropertyOfType(TIMELEFT_PROPERTY);
	// }

	/**
	 * Gets the player's entity key.
	 * 
	 * @return {@link Key}.
	 */
	public Key getPlayerKey() {
		return getPropertyOfType(PLAYERKEY_PROPERTY);
	}
}
