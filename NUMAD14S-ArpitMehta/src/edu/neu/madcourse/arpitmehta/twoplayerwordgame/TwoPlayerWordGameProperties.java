package edu.neu.madcourse.arpitmehta.twoplayerwordgame;

public class TwoPlayerWordGameProperties {
	private static TwoPlayerWordGameProperties gameProperties = null;
	/**
	 * Synchronous Game Play Properties Variables
	 */
	private String loginUsername;
	private String loginUsernameKey;
	private String opponentUsername;
	private String invitedByUsername;
	private String gameId;
	private Boolean isGameIdGenerated;
	private int myScore;
	private int opponentScore;
	private Boolean isInvited;
	private Boolean isBoardUpdated;
	
	/**
	 * Async Game Play Properties
	 */
	private String tbLoginUsername;
	private String tbOpponentUsername;
	private String tbInvitedByUsername;
	private String tbGameId;
	private Boolean isTbGameIdGenerated;
	private int myTbScore;
	private int opponentTbScore;
	private Boolean isInvitedTb;
	private int numMovesLeft;
	private Boolean isTbGameOver;
	
	/**
	 * The Puzzle
	 */
	private String puzzle = "HAPPYNGREATERHKITCHENMONEYRBURGERS";
	
	/**
	 * Default Constructor
	 */
	protected TwoPlayerWordGameProperties() {
		
	}
	
	/**
	 * Returns a synchronized TwoPlayerWordGameProperties object
	 * 
	 * @return gameProperties synchronized {@link TwoPlayerWordGameProperties} 
	 */
	public static synchronized TwoPlayerWordGameProperties getGamePropertiesInstance() {
		if(null == gameProperties) {
			gameProperties = new TwoPlayerWordGameProperties();
		}
		
		return gameProperties;
	}
	
	/**
	 * Getter & Setter Functions
	 */

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

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getMyScore() {
		return myScore;
	}

	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}

	public int getOpponentScore() {
		return opponentScore;
	}

	public void setOpponentScore(int opponentScore) {
		this.opponentScore = opponentScore;
	}

	public Boolean getIsInvited() {
		return isInvited;
	}

	public void setIsInvited(Boolean isInvited) {
		this.isInvited = isInvited;
	}

	public String getOpponentUsername() {
		return opponentUsername;
	}

	public void setOpponentUsername(String opponentUsername) {
		this.opponentUsername = opponentUsername;
	}

	public String getInvitedByUsername() {
		return invitedByUsername;
	}

	public void setInvitedByUsername(String invitedByUsername) {
		this.invitedByUsername = invitedByUsername;
	}

	public Boolean getIsGameIdGenerated() {
		return isGameIdGenerated;
	}

	public void setIsGameIdGenerated(Boolean isGameIdGenerated) {
		this.isGameIdGenerated = isGameIdGenerated;
	}

	public String getTbLoginUsername() {
		return tbLoginUsername;
	}

	public void setTbLoginUsername(String tbLoginUsername) {
		this.tbLoginUsername = tbLoginUsername;
	}

	public String getTbOpponentUsername() {
		return tbOpponentUsername;
	}

	public void setTbOpponentUsername(String tbOpponentUsername) {
		this.tbOpponentUsername = tbOpponentUsername;
	}

	public String getTbInvitedByUsername() {
		return tbInvitedByUsername;
	}

	public void setTbInvitedByUsername(String tbInvitedByUsername) {
		this.tbInvitedByUsername = tbInvitedByUsername;
	}

	public String getTbGameId() {
		return tbGameId;
	}

	public void setTbGameId(String tbGameId) {
		this.tbGameId = tbGameId;
	}

	public Boolean getIsTbGameIdGenerated() {
		return isTbGameIdGenerated;
	}

	public void setIsTbGameIdGenerated(Boolean isTbGameIdGenerated) {
		this.isTbGameIdGenerated = isTbGameIdGenerated;
	}

	public int getMyTbScore() {
		return myTbScore;
	}

	public void setMyTbScore(int myTbScore) {
		this.myTbScore = myTbScore;
	}

	public int getOpponentTbScore() {
		return opponentTbScore;
	}

	public void setOpponentTbScore(int opponentTbScore) {
		this.opponentTbScore = opponentTbScore;
	}

	public Boolean getIsInvitedTb() {
		return isInvitedTb;
	}

	public void setIsInvitedTb(Boolean isInvitedTb) {
		this.isInvitedTb = isInvitedTb;
	}

	public int getNumMovesLeft() {
		return numMovesLeft;
	}

	public void setNumMovesLeft(int numMovesLeft) {
		this.numMovesLeft = numMovesLeft;
	}

	public Boolean getIsTbGameOver() {
		return isTbGameOver;
	}

	public void setIsTbGameOver(Boolean isTbGameOver) {
		this.isTbGameOver = isTbGameOver;
	}

	public String getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(String puzzle) {
		this.puzzle = puzzle;
	}

	public Boolean getIsBoardUpdated() {
		return isBoardUpdated;
	}

	public void setIsBoardUpdated(Boolean isBoardUpdated) {
		this.isBoardUpdated = isBoardUpdated;
	}
	
}
