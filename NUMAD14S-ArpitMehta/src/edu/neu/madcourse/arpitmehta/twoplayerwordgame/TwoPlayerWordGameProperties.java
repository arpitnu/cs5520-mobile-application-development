package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

public class TwoPlayerWordGameProperties {
	private static TwoPlayerWordGameProperties gameProperties = null;
	/**
	 * Synchronous Game Play Properties Variables
	 */
	private String loginUsername;
	private String loginUsernameKey;
	private String inviterUsername;
	private String inviteeUsername;
	private String gameId;
	
	/**
	 * Default Constructor
	 */
	protected TwoPlayerWordGameProperties() {
		
	}
	
	public static synchronized TwoPlayerWordGameProperties getGamePropertiesInstance() {
		if(null == gameProperties) {
			gameProperties = new TwoPlayerWordGameProperties();
		}
		
		return gameProperties;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginUsernameKey() {
		return loginUsernameKey;
	}

	public void setLoginUsernameKey(String loginUsernameKey) {
		this.loginUsernameKey = loginUsernameKey;
	}

	public String getInviterUsername() {
		return inviterUsername;
	}

	public void setInviterUsername(String inviterUsername) {
		this.inviterUsername = inviterUsername;
	}

	public String getInviteeUsername() {
		return inviteeUsername;
	}

	public void setInviteeUsername(String inviteeUsername) {
		this.inviteeUsername = inviteeUsername;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
}
