import java.util.*;
/**
 * Represents a player in the Snakes and Ladders game
 * 
 * @author Joyce Fang
 * @version 6/2/23
 * @author Assignment: AP CS Final Project
 */
public class Player {

    private String name;
    private int position;
    private boolean isComputer;

    /**
     * Constructs a Player
     * @param n player's name
     * @param comp true if the player is a compunter, false if human
     */
    public Player(String n, boolean comp)
    {
        name = n;
        position = 0;
        isComputer = comp;

    }
    /**
     * Returns the player's name
     * @return the player name
     */
    public String getPlayerName()
    {
        return name;
    }

    /**
     * Sets the player's position to a specified square on the game board
     * @param num square on the game board 
     */
    public void setPosition(int num)
    {
        position = num;
    }
    
    /**
     * Returns the player's current position on the board
     * @return the player's position, in the form of an int associated with a square on the board
     */
    public int getCurrentPosition()
    {
        return position;
    }

    /**
     * Returns whether the player is a computer or not
     * @return true if the player is a computer, false if human
     */
    public boolean isComputerPlayer()
    {
        return isComputer;
    }

    
}
