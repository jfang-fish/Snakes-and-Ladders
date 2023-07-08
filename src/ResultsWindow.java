import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * The third window of the program. Used for displaying results after all players have reached the finish line.
 * 
 * @author Joyce Fang
 * @version 6/2/23
 * @author Assignment: AP CS Final Project
 */
public class ResultsWindow extends JFrame {

    private JLabel[] placeText;
    private JLabel[] playerNames;
    private JLabel[] playerTokens;
    private JPanel contentPane;
    private GameInformation game;

        // x coordinate of top-left corner of the 1st place text
        private static final int TEXT_LBL_START_X = 190;
        // y coordinate of top-left corner of the 1st place text
        private static final int TEXT_LBL_START_Y = 230;
        private static final int TEXT_LBL_WIDTH = 50;
        private static final int TEXT_LBL_HEIGHT = 60;
    
        // vertical space between place text indicators
        private static final int TEXT_LBL_V_SPACE = 10;
        // x coordinate of top-left corner of the name of the first player
        private static final int PLAYER_NAME_START_X = 390;
        // y coordinate of top-left corner of the name of the first player
        private static final int PLAYER_NAME_START_Y = TEXT_LBL_START_Y;
        private static final int PLAYER_NAME_WIDTH = 400;
        private static final int PLAYER_NAME_HEIGHT = TEXT_LBL_HEIGHT;
       
        // space between player names
        private static final int PLAYER_NAME_V_SPACE = TEXT_LBL_V_SPACE;
        // x coordinate of top-left corner of the token of the first player
        private static final int PLAYER_TOKEN_START_X = 290;
        // y coordinate of top-left corner of the token of the first player
        private static final int PLAYER_TOKEN_START_Y = TEXT_LBL_START_Y + 5;
        private static final int PLAYER_TOKEN_WIDTH = 60;
        private static final int PLAYER_TOKEN_HEIGHT = 60;
        // vertical space between tokens
        private static final int PLAYER_TOKEN_V_SPACE = TEXT_LBL_V_SPACE;

    /**
     * Constructs a ResultsWindow
     * @param game the GameInformation passed in from the previous windows
     */
    public ResultsWindow(GameInformation g) 
    {
        game = g;
        setTitle("Results");
        initialize();

    }

    /**
     * Initalizes the JFrame for the results window with its components
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600, 200, 700, 700);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255,229,204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        // Use coordinates, no layout
        contentPane.setLayout(null);
        setResizable(false);

        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/Images/logov2.png")));
        logo.setBounds(150, 50, 400,100);
        contentPane.add(logo);
        JLabel diceText = new JLabel("Leaderboard");
        diceText.setFont(new Font("Century Gothic", Font.BOLD, 45));
        diceText.setBounds(200, 130, 400, 100);
        contentPane.add(diceText);
        diceText.setForeground(new Color(244,164,96));
        createPlayerLabels(game.numOfPlayers());

    JButton restart = new JButton("New Game?");
    restart.setBounds(240, 560, 200,40);
    contentPane.add(restart);
    restart.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            StartWindow s = new StartWindow();
            s.setVisible(true);
            ResultsWindow.this.dispose();
        }
    });
    }

    /**
     * Creates the leaderboard with player icons, names, and their ranking
     *
     * @param numOfPlayers number of players
     */
    private void createPlayerLabels(int numOfPlayers) {
        // Create the ranking text
        placeText = new JLabel[3];
        placeText[0] = new JLabel("1st!");
        placeText[0].setFont(new Font("Century Gothic", Font.BOLD, 40));
        placeText[0].setForeground(new Color(218,165,32));

        placeText[1] = new JLabel("2nd");
        placeText[1].setFont(new Font("Century Gothic", Font.BOLD,40));
        placeText[1].setForeground(new Color(192,192,192));

        placeText[2] = new JLabel("3rd");
        placeText[2].setFont(new Font("Century Gothic", Font.BOLD, 40));
        placeText[2].setForeground(new Color	(205,133,63));

        for (int i = 0; i < numOfPlayers; i++)
        {
            if (i == 3)
            {
                break;
            }
            placeText[i].setBounds(TEXT_LBL_START_X, TEXT_LBL_START_Y + i * (TEXT_LBL_HEIGHT + TEXT_LBL_V_SPACE), 100, 60);
            contentPane.add(placeText[i]);

        }
        ArrayList<Player> results = game.getGameResult();
        ArrayList<Player> pList = game.getPlayerList();

        // Create player names
        playerNames = new JLabel[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            JLabel name = new JLabel(results.get(i).getPlayerName());
            name.setFont(new Font("Century Gothic", Font.PLAIN, 25));
            Color t = new Color 		(219,112,147);
            name.setForeground(t);
            name.setBounds(PLAYER_NAME_START_X, PLAYER_NAME_START_Y + i * (PLAYER_NAME_HEIGHT + PLAYER_NAME_V_SPACE), PLAYER_NAME_WIDTH, PLAYER_NAME_HEIGHT);
            contentPane.add(name);
        }

        // Create player tokens
        playerTokens = new JLabel[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            playerTokens[i] = new JLabel("");
            int index = pList.indexOf(results.get(i)) + 1;
            playerTokens[i].setIcon(new ImageIcon(getClass().getResource("/Images/icon" + index + ".png")));
            playerTokens[i].setBounds(PLAYER_TOKEN_START_X, PLAYER_TOKEN_START_Y + i * (PLAYER_TOKEN_HEIGHT + PLAYER_TOKEN_V_SPACE), PLAYER_TOKEN_WIDTH, PLAYER_TOKEN_HEIGHT);
            contentPane.add(playerTokens[i]);
        }
    }
    
}
