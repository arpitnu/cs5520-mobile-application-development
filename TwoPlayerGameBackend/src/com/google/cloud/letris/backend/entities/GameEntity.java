package com.google.cloud.letris.backend.entities;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class GameEntity extends DatastoreEntity {

	/**
	 * The name of the game in the Datastore
	 */
	public static final String KIND = "Game";

	/**
	 * The names of properties in Datastore
	 */
	private static final String BOARDKEY_PROPERTY = "boardKey";
	private static final String PLAYERKEYWON_PROPERTY = "playerKeyWon";

	/**
	 * The Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is used when retrieving an existing game entity from
	 * Datastore.
	 * 
	 * @param entity
	 *            {@link Entity}.
	 */
	public GameEntity(Entity entity) {
		super(entity, false);
	}

	/**
	 * Constructor used to create a new game entity.
	 * 
	 * @param boardKey
	 *            {@link Key}
	 * @throws IllegalArgumentException
	 *             if entity is null.
	 */
	public GameEntity(Key boardKey) {
		super(new Entity(KIND), true);

		if (boardKey == null) {
			throw new IllegalArgumentException("boardKey cannot be null");
		}

		setProperty(BOARDKEY_PROPERTY, boardKey);
		setProperty(PLAYERKEYWON_PROPERTY, null);
	}

	/**
	 * Determines which player has won the game and update the game entity with
	 * that winner player's key.
	 * 
	 * @param players
	 *            list of players from this game.
	 * @return true if the winner was determined; false if the game is not
	 *         finished yet.
	 * @throws IllegalArgumentException
	 *             if player list is null or empty.
	 */
	public Boolean determineWinner(List<GamePlayEntity> players) {
		if ((null == players) || (players.size() <= 1)) {
			throw new IllegalArgumentException(
					"players list must contain at least two players");
		}

		if (!isGameFinished(players)) {
			return false;
		}

		// winner logic
		GamePlayEntity currentWinner = players.get(0);
		int currentWinnerWordCnt = currentWinner.getSelectedWords().size();
		GamePlayEntity currentLooser = players.get(1);
		int currentLooserWordCnt = currentLooser.getSelectedWords().size();
		
		if(currentLooserWordCnt > currentWinnerWordCnt) {
			currentWinner = currentLooser;
			currentLooserWordCnt = currentWinnerWordCnt;
		}
		else if(currentLooserWordCnt == currentWinnerWordCnt) {
			//TODO
		}

		return false;
	}

	/**
	 * Gets the player key who won the game.
	 */
	public Key getWinnerKey() {
		return getPropertyOfType(PLAYERKEYWON_PROPERTY);
	}

	/**
	 * Gets the board entity key.
	 * 
	 * @return {@link Key}.
	 */
	public Key getBoardKey() {
		return getPropertyOfType(BOARDKEY_PROPERTY);
	}

	/**
	 * Checks if the player is the winner of this game.
	 * 
	 * @param playerKey
	 *            the key of the player.
	 * @return true if the player is the winner; false otherwise.
	 */
	public boolean isWinner(Key playerKey) {
		Key key = getWinnerKey();

		return (key != null && key.compareTo(playerKey) == 0);
	}

	/**
	 * Determines if the game play is finished
	 * 
	 * @param players
	 *            the list of players
	 * 
	 * @return boolean
	 */
	private boolean isGameFinished(List<GamePlayEntity> players) {
		for (GamePlayEntity player : players) {
			if (!player.getFinished()
					|| player.getParentKey().compareTo(this.getKey()) != 0) {
				return false;
			}
		}

		return true;
	}
}
