package com.google.cloud.letris.backend.entities;

import com.google.appengine.api.datastore.Entity;

public class PlayerEntity extends DatastoreEntity {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Names of properties in Datastore. Public if used in queries. Private
	 * otherwise.
	 */
	public static final String USERNAME_PROPERTY = "username";
	public static final String ACTIVE_PROPERTY = "active";
	private static final String NUMBER_OF_MULTIPLAYER_GAMES_WON = "multiplayerGamesWon";
	private static final String NUMBER_OF_MULTIPLAYER_GAMES_PLAYED = "multiplayerGamesPlayed";

	/**
	 * Name of Player entity kind in Datastore. Public because it is also used
	 * for Datastore queries.
	 */
	public static final String KIND = "Player";

	/**
	 * Constructor used to create a new player entity.
	 * 
	 * @param username {@link String}
	 * 
	 * @throws IllegalArgumentException if the username string is null
	 */
	public PlayerEntity(String uname) {
		super(new Entity(KIND), true);
		
		if(null == uname) {
			throw new IllegalArgumentException("user cannot be null");
		}
		
		// TODO Check for empty string
		
		setProperty(USERNAME_PROPERTY, uname);
		setProperty(ACTIVE_PROPERTY, false);
		setProperty(NUMBER_OF_MULTIPLAYER_GAMES_PLAYED, 0);
	    setProperty(NUMBER_OF_MULTIPLAYER_GAMES_WON, 0);
	}
	
	/**
	   * Gets the number of multiplayer games won by this player.
	   */
	  public int getNumberOfMultiplayerGamesWon() {
	    Long gamesWon = getPropertyOfType(NUMBER_OF_MULTIPLAYER_GAMES_WON);

	    return gamesWon.intValue();
	  }
	  
	  /**
	   * Gets the number of multiplayer games played.
	   */
	  public int getNumberOfMultiplayerGamesPlayed() {
	    Long gamesPlayed = getPropertyOfType(NUMBER_OF_MULTIPLAYER_GAMES_PLAYED);

	    return gamesPlayed.intValue();
	  }
	  
	  /**
	   * Deactivates the player.
	   */
	  public void deactivate() {
	    setProperty(ACTIVE_PROPERTY, false);
	  }

	  /**
	   * Activates the player.
	   */
	  public void activate() {
	    setProperty(ACTIVE_PROPERTY, true);
	  }
	  
	  /**
	   * Updates the players multiplayer statistics.
	   */
	  public void wonGame() {

	    playedGame();

	    setProperty(NUMBER_OF_MULTIPLAYER_GAMES_WON, getNumberOfMultiplayerGamesWon() + 1);
	  }

	  /**
	   * Updates the players multiplayer statistics.
	   */
	  public void lostGame() {
	    playedGame();
	  }

	  /**
	   * Increases the number of multiplayer games played.
	   */
	  private void playedGame() {
	    setProperty(NUMBER_OF_MULTIPLAYER_GAMES_PLAYED, getNumberOfMultiplayerGamesPlayed() + 1);
	  }
	  
	  /**
	   * Get the players username
	   * 
	   * @return Player's username {@link String}
	   */
	  public String getUsername() {
		  return getPropertyOfType(USERNAME_PROPERTY);
	  }
}
