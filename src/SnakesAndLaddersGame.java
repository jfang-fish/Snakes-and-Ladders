import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;


/**
 * The second window of the program. Used for the playing the game
 * 
 * @author Joyce Fang
 * @version 6/2/23
 * @author Assignment: AP CS Final Project
 */
public class SnakesAndLaddersGame extends JFrame {
    // x coordinate of top-left corner of whole JFrame
    private static final int FRAME_START_X = 300; 
    // y coordinate of top-left corner of whole JFrame
    private static final int FRAME_START_Y = 100;
    // width of entire JFrame
    private static final int FRAME_WIDTH = 1340;
    // height of entire JFrame
    private static final int FRAME_HEIGHT = 1200;
    // x coordinate of top-left corner of board
    private static final int BOARD_START_X = 480;
    // y coordinate of top-left corner of board 
    private static final int BOARD_START_Y = 40;

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 800;

    // x coordinate of top-left corner of square 1
    private static final int GRID_1_START_X = 480;
    // y coordinate of top-left corner of square 1
    private static final int GRID_1_START_Y = 760;

    private static final int GRID_WIDTH = 80;
    private static final int GRID_HEIGHT = 80;

    // x coordinate of top-left corner of rolling dice 
    private static final int DICE_IMG_START_X = 50;
    // y coordinate of top-left corner of rolling dice 
    private static final int DICE_IMG_START_Y = 710;
    private static final int DICE_IMG_WIDTH = 130;
    private static final int DICE_IMG_HEIGHT = 130;

    // x coordinate of top-left corner of dice button
    private static final int DICE_BTN_START_X = 65;
    // y coordinate of top-left corner of dice button
    private static final int DICE_BTN_START_Y = 640;

    private static final int DICE_BTN_WIDTH = 100;
    private static final int DICE_BTN_HEIGHT = 70;

    // x coordinate of top-left corner of the turn indicator of player 1
    private static final int TURN_LBL_START_X = 60;
    // y coordinate of top-left corner of the turn indicator of player 1
    private static final int TURN_LBL_START_Y = 230;
    private static final int TURN_LBL_WIDTH = 50;
    private static final int TURN_LBL_HEIGHT = 60;

    // vertical space between turn indicators
    private static final int TURN_LBL_V_SPACE = 10;
    // x coordinate of top-left corner of the name of player 1
    private static final int PLAYER_NAME_START_X = 190;
    // y coordinate of top-left corner of the name of player 1
    private static final int PLAYER_NAME_START_Y = TURN_LBL_START_Y;
    private static final int PLAYER_NAME_WIDTH = 400;
    private static final int PLAYER_NAME_HEIGHT = TURN_LBL_HEIGHT;
   
    // vertical space between player names
    private static final int PLAYER_NAME_V_SPACE = TURN_LBL_V_SPACE;
    // x coordinate of top-left corner of the token of player 1
    private static final int PLAYER_TOKEN_START_X = 110;
    // y coordinate of top-left corner of the token of player 1
    private static final int PLAYER_TOKEN_START_Y = TURN_LBL_START_Y;
    private static final int PLAYER_TOKEN_WIDTH = 60;
    private static final int PLAYER_TOKEN_HEIGHT = 60;
    // vertical space between tokens
    private static final int PLAYER_TOKEN_V_SPACE = TURN_LBL_V_SPACE;
    
    // animation delay --> smaller = faster
    private static final long ANIMATION_DELAY = 400L;

    // contains all UI components
    private JPanel contentPane;

    // JLabel for game board 
    private JLabel board;
    // JLabels grids[1]-grids[100] for 100 squares on the board. grids[0] is for the starting position (off the board)
    private JLabel[] grids = new JLabel[101];

    // JLabel for dice images
    private JLabel diceImage;
    // 6 images for 6 sides of the dice
    private ImageIcon[] rollingDiceIcons = new ImageIcon[6];

    // JButton for rolling the dice
    private JButton diceBtn;

    // Images for turn indicator
    private ImageIcon[] turnIcons;
    // JLabels for turn indicators
    private JLabel[] turnIndicators;
    // JLabels for players
    private JLabel[] playerNames;
    // JLabels for tokens
    private JLabel[] playerTokens;

    // Timer for delayed animation task
    Timer timer = new Timer("Timer");

    // GameInformation for this round
    private GameInformation game;

    /**
     * Constructs a SnakesAndLaddersGame
     */
    public SnakesAndLaddersGame(GameInformation g) {
        game = g;
        setTitle("Snakes and Ladders");
        initialize();
    }

    /**
     * Initalizes the JFrame for the game window with its components
     */
    private void initialize() {
        // Set content pane which is a JPanel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(FRAME_START_X, FRAME_START_Y, FRAME_WIDTH, FRAME_HEIGHT);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255,229,204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        // Use coordinates, no layout
        contentPane.setLayout(null);


        // Create board grid
        createGrids();
        // Create rolling dice image 1-6
        createRollingDiceIcons();
        // Create player information labels
        createPlayerLabels(game.numOfPlayers());

        // Create dice button
        diceBtn = new JButton(new ImageIcon(getClass().getResource("/Images/dice.png")));
        diceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Disable the dice button until current player is done
                diceBtn.setEnabled(false);
                // Roll dice
                rollDice();
            }
        });
        diceBtn.setBounds(DICE_BTN_START_X, DICE_BTN_START_Y, DICE_BTN_WIDTH, DICE_BTN_HEIGHT);
        contentPane.add(diceBtn);

        // Place to show rolling dice animation
        diceImage = new JLabel("");
        diceImage.setHorizontalAlignment(SwingConstants.CENTER);
        diceImage.setBounds(DICE_IMG_START_X, DICE_IMG_START_Y, DICE_IMG_WIDTH, DICE_IMG_HEIGHT);
        contentPane.add(diceImage);

        // Game board background
        board = new JLabel("");
        board.setIcon(new ImageIcon(getClass().getResource("/Images/scale2.png")));
        board.setBounds(BOARD_START_X, BOARD_START_Y, BOARD_WIDTH, BOARD_HEIGHT);
        contentPane.add(board);

        // misc stuff (text and other images)
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/Images/logov2.png")));
        logo.setBounds(50, 50, 400,100);
        contentPane.add(logo);
        JLabel text = new JLabel("Player Turn");
        text.setFont(new Font("Century Gothic", Font.BOLD, 45));
        text.setBounds(60, 140, 400, 100);
        contentPane.add(text);
        Color t = new Color (244,164,96);
        text.setForeground(t);

        
    JLabel diceText = new JLabel("Roll the dice!");
    diceText.setFont(new Font("Century Gothic", Font.BOLD, 45));
    diceText.setBounds(60, 550, 400, 100);
    contentPane.add(diceText);
    diceText.setForeground(t);

    JLabel snake = new JLabel(new ImageIcon(getClass().getResource("/Images/flippedsnake.png")));
    snake.setBounds(20, 520, 600, 60);
    contentPane.add(snake);


    JLabel sq = new JLabel(new ImageIcon(getClass().getResource("/Images/pink.png")));
    sq.setBounds(40, 195, 400,350);
    contentPane.add(sq);

    
    JLabel sq1 = new JLabel(new ImageIcon(getClass().getResource("/Images/orange.png")));
    sq1.setBounds(40, 605, 400,235);
    contentPane.add(sq1);

  
    JButton restartFresh = new JButton("Return to home screen");
    restartFresh.setBounds(1030, 10, 250,20);
    restartFresh.setFont(new Font("Century Gothic", Font.BOLD, 15));
    contentPane.add(restartFresh);

    restartFresh.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            StartWindow s = new StartWindow();
            s.setVisible(true);
            SnakesAndLaddersGame.this.dispose();
        }
    });

    // if player 1 is a computer, auto click to start the game
    if(game.isCurrentPlayerComputer())
    {
        diceBtn.doClick();
    }

    }

    /**
     * Create image icon for rolling dice animations
     */
    private void createRollingDiceIcons() {
        for (int i = 0; i < 6; i++) {
            String location = "/Images/rolling-dice-" + String.valueOf(i + 1) + ".gif";
            rollingDiceIcons[i] = new ImageIcon(getClass().getResource(location));
        }
    }

    /**
     * Create 10x10 small grid by using JLabels. This will be placed on top of
     * the game board to show player token movement.
     */
    private void createGrids() {
        int index = 1;
        int grid_x = GRID_1_START_X;
        int grid_y = GRID_1_START_Y;
        int increasing = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grids[index] = new JLabel("");
                grids[index].setHorizontalAlignment(SwingConstants.CENTER);
                grids[index].setBounds(grid_x, grid_y, GRID_WIDTH, GRID_HEIGHT);
                contentPane.add(grids[index]);
                index++;
                grid_x += GRID_WIDTH * increasing;
            }
            increasing *= -1;
            grid_x += GRID_WIDTH * increasing;
            grid_y -= GRID_HEIGHT;
        }
        // For starting position, off of the game board (square zero)
        grids[0] = new JLabel("");
    }

    /**
     * Creates the visual with turn indicators, player names, and tokens.
     *
     * @param numOfPlayers number of players
     */
    private void createPlayerLabels(int numOfPlayers) {
        // For turn indicators
        turnIcons = new ImageIcon[numOfPlayers];
        turnIndicators = new JLabel[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            turnIndicators[i] = new JLabel("");
            turnIndicators[i].setHorizontalAlignment(SwingConstants.CENTER);
            turnIndicators[i].setBounds(TURN_LBL_START_X, TURN_LBL_START_Y + i * (TURN_LBL_HEIGHT + TURN_LBL_V_SPACE), TURN_LBL_WIDTH, TURN_LBL_HEIGHT);
            turnIcons[i] = new ImageIcon(getClass().getResource("/Images/right-arrow.gif"));
            turnIndicators[i].setIcon(turnIcons[i]);
            turnIndicators[i].setVisible(false);
            contentPane.add(turnIndicators[i]);
        }
        turnIndicators[0].setVisible(true);

        // For player names
        playerNames = new JLabel[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            JLabel name = new JLabel(game.getPlayerName(i));
            playerNames[i] = name;
            playerNames[i].setFont(new Font("Century Gothic", Font.PLAIN, 25));
            Color t = new Color 		(219,112,147);
            name.setForeground(t);
            playerNames[i].setBounds(PLAYER_NAME_START_X, PLAYER_NAME_START_Y + i * (PLAYER_NAME_HEIGHT + PLAYER_NAME_V_SPACE), PLAYER_NAME_WIDTH, PLAYER_NAME_HEIGHT);
            contentPane.add(playerNames[i]);
        }

        // For player tokens
        playerTokens = new JLabel[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            playerTokens[i] = new JLabel("");
            playerTokens[i].setIcon(new ImageIcon(getClass().getResource("/Images/icon" + String.valueOf(i + 1) + ".png")));
            playerTokens[i].setBounds(PLAYER_TOKEN_START_X, PLAYER_TOKEN_START_Y + i * (PLAYER_TOKEN_HEIGHT + PLAYER_TOKEN_V_SPACE), PLAYER_TOKEN_WIDTH, PLAYER_TOKEN_HEIGHT);
            contentPane.add(playerTokens[i]);
        }
    }

    /**
     * Called when dice button is clicked
     */
    private void rollDice() {
        // Generate a random number
        Random rn = new Random();
        int diceNumber = rn.nextInt(6) + 1;

        // Set corresponding rolling dice animation
        rollingDiceIcons[diceNumber - 1].getImage().flush();
        diceImage.setIcon(rollingDiceIcons[diceNumber - 1]);

        // Let the rolling dice animation finish, then move player's token on the board.
        TimerTask task = new TimerTask() {
            public void run() {
                EventQueue.invokeLater(() -> {
                    moveToken(diceNumber);
                    /*try {
                        moveToken(diceNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                });
            }
        };
        // Time delay to allow rolling dice animation to finish
        timer.schedule(task, 3000L);
    }

    /**
     * Move the turn indicator to the new current player
     */
    private void changeCurrentPlayer() {
        // Hide current turn indicator
        turnIndicators[game.currentPlayer()].setVisible(false);
        // Change current play to the next player
        game.nextCurrentPlayer();
        // Show the indicator to the new current player
        turnIcons[game.currentPlayer()].getImage().flush();
        turnIndicators[game.currentPlayer()].setVisible(true);
    }

    /**
     * Changing the player
     *
     * @param delay delay in milliseconds before task is to be executed.
     */
        private void changeCurrentPlayerTask(long delay) {
            TimerTask task = new TimerTask() {
                public void run() {
                    EventQueue.invokeLater(() -> {
                        try {
                            // Move the turn indicator to the new current player
                            changeCurrentPlayer();
                            
                            if (game.isGameEnd()) {
                                ResultsWindow rw = new ResultsWindow(game);
                                rw.setVisible(true);
                                SnakesAndLaddersGame.this.dispose();

                            } else {
                                // Re-enable dice button for the new player
                                diceBtn.setEnabled(true);
                                if (game.isCurrentPlayerComputer()) {
                                    diceBtn.doClick();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            };
            timer.schedule(task, delay);
    }

    /**
     * Task of jumping across snakes or ladders
     *
     * @param from      player's old position
     * @param to        player's new position
     * @param playIndex index of the player, zero based.
     * @param delay     delay in milliseconds before task is to be executed
     */
    private void jumpToTask(int from, int to, int playIndex, long delay) {
        TimerTask jumpTask = new TimerTask() {
            public void run() {
                EventQueue.invokeLater(() -> {
                    try {
                        //System.out.println("Jump from " + from + " to " + to);
                        // Hide grid for the old position
                        hideGrid(from);
                        // Show grid for the new position
                        showGrid(to, playIndex);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        timer.schedule(jumpTask, delay);
    }

    /**
     * Move the player token on the game board
     *
     * @param diceNumber dice number from 1 to 6
     */
    private void moveToken(int diceNumber) {
        int from = game.getCurrentPlayerPosition();

        // Check if it goes over 100, then it needs to turn back the overflow amount
        boolean isTurnedAround = false;
        if (game.getCurrentPlayerPosition() + diceNumber > 100) {
            game.setCurrentPlayerPosition(200 - game.getCurrentPlayerPosition() - diceNumber);
            isTurnedAround = true;
            //System.out.println("Turned around");
        } else {
            game.setCurrentPlayerPosition(game.getCurrentPlayerPosition() + diceNumber);
        }

        int to = game.getCurrentPlayerPosition();

        // Check if it needs to jump
        boolean hasJump = false;
        int pos = game.checkJump(game.getCurrentPlayerPosition());
        if (game.getCurrentPlayerPosition() != pos) {
            game.setCurrentPlayerPosition(pos);
            hasJump = true;
        }

        if (hasJump) {
            moveGrid(from, to, game.getCurrentPlayerPosition(), game.currentPlayer(), isTurnedAround);
        } else {
            moveGrid(from, game.getCurrentPlayerPosition(), -1, game.currentPlayer(), isTurnedAround);
        }
    }

    /**
     * Hide the given square on the grid so that it will show:
     * 1. The game board -- if no other players in the same square
     * Or
     * 2. The given board but with other players token
     *
     * @param x square number from 1 to 100
     */
    private void hideGrid(int x) {
        //System.out.println("hideGrid: " + x);
        // Hide the square first
        grids[x].setVisible(false);
        // Show the square again with other players token if needed
        restoreGrid(x);
    }

    /**
     * Show the given square on the grid with specific player's token
     *
     * @param x           square number from 1 to 100
     * @param playerIndex player's index (zero based)
     */
    private void showGrid(int x, int playerIndex) {
        //System.out.println("showGrid: " + x);
        String location = "/Images/icon" + (playerIndex + 1) + ".png";
        grids[x].setVisible(true);
        grids[x].setIcon(new ImageIcon(getClass().getResource(location)));
    }

    /**
     * Re-show the given square with other player's token if there is other player in the same grid
     *
     * @param x square number from 1 to 100
     */
    private void restoreGrid(int x) {
        for (int i = 0; i < game.numOfPlayers(); i++) {
            if (game.getPlayerPosition(i) == x && game.getPlayerPosition(i) != 0 && game.currentPlayer() != i) {
                showGrid(x, i);
                //System.out.println("Player matched with player " + String.valueOf(i + 1));
            }
        }
    }

    /**
     * Animation of player tokens
     *
     * @param from           player's original position
     * @param to             player's new position after rolling dice
     * @param jumpTo         player's new position if to-position encounters a snake or ladder; negative int for no jump
     * @param playIndex      player's index 
     * @param isTurnedAround if true (when current position + rolled dice > 100), then turn back
     */
    private void moveGrid(int from, int to, int jumpTo, int playIndex, boolean isTurnedAround) {
        long delay = ANIMATION_DELAY;
        int middle = isTurnedAround ? 100 : to;

        // Move up
        for (int i = from; i < middle; i++) {
            int finalI = i;
            TimerTask task = new TimerTask() {
                public void run() {
                    EventQueue.invokeLater(() -> {
                        try {
                            hideGrid(finalI);
                            showGrid(finalI + 1, playIndex);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            };
            timer.schedule(task, delay);
            delay += ANIMATION_DELAY;
        }

        if (isTurnedAround) {
            for (int i = 100; i > to; i--) {
                int finalI = i;
                TimerTask task = new TimerTask() {
                    public void run() {
                        EventQueue.invokeLater(() -> {
                            try {
                                hideGrid(finalI);
                                showGrid(finalI - 1, playIndex);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                };
                timer.schedule(task, delay);
                delay += ANIMATION_DELAY;
            }
        }

        // Jump if needed
        if (jumpTo > 0) {
            jumpToTask(to, jumpTo, playIndex, delay);
            delay += ANIMATION_DELAY;
        }

        // Change to the new current player
        changeCurrentPlayerTask(delay);
    }
}