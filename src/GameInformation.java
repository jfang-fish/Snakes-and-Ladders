import java.util.*;

/**
 * Class that stores information about game logic
 * 
 * @author Joyce Fang 
 * @version 6/2/23
 * @author Assignment: AP CS Final Project
 */
public class GameInformation {
    private int currentPlayer = 0;
    private boolean isGameEnd = false;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Player> finishLine = new ArrayList<>();

    // Map the entrance and exit of snakes and ladders
    Map<Integer, Integer> jumpMap = new HashMap<Integer, Integer>();

    /**
     * Constructs a GameInformation
     */
    public GameInformation() {

        createJumpMap();

    }

    /**
     * Adds a player to the game
     * @param p player to add
     */
    public void addPlayer(Player p)
    {
        players.add(p);

    }

    
    /**
     * Returns the number of players
     * @return the number of players
     */
 public int numOfPlayers() {
        return players.size();
    }

    
    /**
     * Returns the name of the player with the specified player index number
     * @param playerIndex player index number
     * @return the name of the player with that index
     */
    public String getPlayerName(int playerIndex) {
        return players.get(playerIndex).getPlayerName();
    }

        /**
     * Returns the position of the player with the specified player index number
     * @param playerIndex player index number
     * @return the position of the player with that index
     */
    public int getPlayerPosition(int playerIndex) {
        return players.get(playerIndex).getCurrentPosition();
    }

        /**
     * Returns the player index of the current player
     * @return the index of the current player
     */
    public int currentPlayer() {
        return currentPlayer;
    }

        /**
     * Returns the position of the current player
     * @return the position of the current player
     */
    public int getCurrentPlayerPosition() {
        return players.get(currentPlayer).getCurrentPosition();
    }

            /**
     * Sets the position of the current player
     * @param position position to set the current player at
     */
    public void setCurrentPlayerPosition(int position) {
        players.get(currentPlayer).setPosition(position);
        if (players.get(currentPlayer).getCurrentPosition() == 100) {
            finishLine.add(players.get(currentPlayer));
        }
    }
    /**
     * Returns whether the current player is a computer or not
     * @return true if the player is a computer, false if human
     */
    public boolean isCurrentPlayerComputer() {
        return players.get(currentPlayer).isComputerPlayer();
    }

  /**
     * Choose the next eligible player as current player to play
     */
    public void nextCurrentPlayer() {
        int index;
        for (index = 0; index < players.size(); index++) {
            currentPlayer++;
            if (currentPlayer == players.size()) {
                currentPlayer = 0;
            }
            if (players.get(currentPlayer).getCurrentPosition() != 100) {
                break;
            }
        }
        // All players positions @ 100 --> game ends
        if (index == players.size()) {
            isGameEnd = true;
        }
    }


    /**
     * Check if the given position is the entrance of a snake or ladder
     *
     * @param position player's position
     * @return a new position if there is a jump: the exit of snake or ladder,
     * or same position as the input position if there is no jump.
     */
    public int checkJump(int position) {
        Integer jumpTo = jumpMap.get(position);
        if (jumpTo != null) {
            return jumpTo;
        } else {
            return position;
        }
    }

    /**
     * Check if game has ended: all players reach the final grid
     *
     * @return true if game is over, false otherwise
     */
    public boolean isGameEnd() {
        return isGameEnd;
    }

    /**
     * Stores information about the entrance and exit points of snakes and ladders
     */
    private void createJumpMap() {
        //ladders
        jumpMap.put(21, 42);
        jumpMap.put(4, 15);
        jumpMap.put(9, 30);
        jumpMap.put(13, 65);
        jumpMap.put(57, 63);
        jumpMap.put(69, 87);
        jumpMap.put(71, 91);
        jumpMap.put(80, 100);
        //snakes
        jumpMap.put(24,6);
        jumpMap.put(62,18);
        jumpMap.put(75, 25);
        jumpMap.put(32, 11);
        jumpMap.put(54, 50);
        jumpMap.put(94, 67);
        jumpMap.put(98,78);
    }

    /**
     * Gets the game results
     * @return list of players, in order of when they reached the finish line
     */
    public ArrayList<Player> getGameResult() {
        return finishLine;
}

    /**
     * Returns the list of players in the game
     * @return list of players in the game
     */
public ArrayList<Player> getPlayerList()
{
    return players;
}

}

