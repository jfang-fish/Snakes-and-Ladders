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
 * The first window of the program. Used for allowing players to choose game
 * options before starting the game.
 * 
 * @author Joyce Fang
 * @version 6/2/23
 * @author Assignment: AP CS Final Project
 */
public class StartWindow
    extends JFrame
{
    private JPanel           contentPane;
    private JLabel[]         playerNum;
    private JTextField[]     playerName;
    private JSpinner         numOfPlayersSpin;
    private int              numOfPlayers;
    private JRadioButton[][] modeBtn;
    private JLabel[]         playerTokens;
    private ButtonGroup[]    btnGrp;
    private JLabel[]         playerNoLbl;

    private static final int PLAYER_NAME_START_X  = 530;
    // y coordinate of top-left corner of the name of player 1
    private static final int PLAYER_NAME_START_Y  = 360;
    private static final int PLAYER_NAME_WIDTH    = 200;
    private static final int PLAYER_NAME_HEIGHT   = 60;
    private static final int PLAYER_NAME_V_SPACE  = 10;

    private static final int PLAYER_MODE_START_X  = 760;
    // y coordinate of top-left corner of the player mode of player 1
    private static final int PLAYER_MODE_START_Y  = PLAYER_NAME_START_Y;
    // width of the player mode
    private static final int PLAYER_MODE_WIDTH    = 100;
    // height of the player mode
    private static final int PLAYER_MODE_HEIGHT   = 60;
    // vertical space between player modes
    private static final int PLAYER_MODE_V_SPACE  = 10;

    // x coordinate of top-left corner of the token of player 1
    private static final int PLAYER_TOKEN_START_X = 330;
    // y coordinate of top-left corner of the token of player 1
    private static final int PLAYER_TOKEN_START_Y = PLAYER_NAME_START_Y;
    private static final int PLAYER_TOKEN_WIDTH   = 60;
    private static final int PLAYER_TOKEN_HEIGHT  = 60;
    // vertical space between tokens
    private static final int PLAYER_TOKEN_V_SPACE = 10;

    // x coordinate of top-left corner of the player # of player 1
    private static final int PLAYER_NO_START_X    = 410;
    // y coordinate of top-left corner of the player # of player 1
    private static final int PLAYER_NO_START_Y    = 370;
    private static final int PLAYER_NO_WIDTH      = 70;
    private static final int PLAYER_NO_HEIGHT     = 35;
    // vertical space between player #
    private static final int PLAYER_NO_V_SPACE    = 35;

    /**
     * Constructs a StartWindow
     */
    public StartWindow()
    {
        setTitle("Welcome to Snakes and Ladders!");
        initialize();

    }


    private void initialize()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 100, 1340, 900);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 229, 204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        // Use coordinates, no layout
        contentPane.setLayout(null);
        setResizable(false);

        JLabel bg = new JLabel("");
        bg.setIcon(new ImageIcon(getClass().getResource("/Images/larger.jpg")));

        JLabel welcome = new JLabel("Welcome to Snakes and Ladders!");
        welcome.setFont(new Font("Century Gothic", Font.BOLD, 55));
        welcome.setBounds(220, 100, 1000, 200);
        welcome.setForeground(new Color(205, 92, 92));
        contentPane.add(welcome);
        JLabel snake = new JLabel(new ImageIcon(getClass().getResource("/Images/snake.png")));
        snake.setBounds(360, 290, 600, 60);
        contentPane.add(snake);

        JLabel playerText = new JLabel("Set the number of players (min 1, max 4)");
        playerText.setFont(new Font("Century Gothic", Font.BOLD, 25));
        playerText.setBounds(410, 180, 1000, 200);
        contentPane.add(playerText);

        JTextField players = new JTextField("Enter number here");

        players.setBounds(350, 400, 250, 100);
        players.setFont(new Font("Century Gothic", Font.PLAIN, 25));
        contentPane.add(players);

        JLabel tryAgain = new JLabel("Please enter a number between 1 and 4.");
        tryAgain.setFont(new Font("Century Gothic", Font.BOLD, 30));
        tryAgain.setBounds(367, 450, 1000, 200);
        tryAgain.setVisible(false);
        contentPane.add(tryAgain);

        JButton confirm = new JButton("Confirm number?");
        confirm.setFont(new Font("Century Gothic", Font.BOLD, 20));
        confirm.setBounds(660, 400, 300, 100);
        contentPane.add(confirm);

        bg.setBounds(0, 0, 1340, 1200);
        contentPane.add(bg);

        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                numOfPlayers = Integer.valueOf(players.getText()).intValue();
                if (numOfPlayers >= 1 && numOfPlayers <= 4)
                {
                    tryAgain.setVisible(false);
                    contentPane.remove(bg);
                    contentPane.revalidate();
                    contentPane.repaint();
                    players.setVisible(false);
                    playerText.setVisible(false);
                    confirm.setVisible(false);

                    createPlayerMenu(numOfPlayers);
                    JLabel sq1 =
                        new JLabel(new ImageIcon(getClass().getResource("/Images/grey.png")));
                    sq1.setBounds(275, 340, 760, 320);
                    contentPane.add(sq1);
                    sq1.setVisible(false);
                    sq1.setVisible(true);

                    createStartBtn();
                    createResetButton();
                    bg.setBounds(0, 0, 1340, 1200);
                    contentPane.add(bg);

                }
                else
                {
                    tryAgain.setVisible(true);
                }

            }
        });

        bg.setBounds(0, 0, 1340, 1200);
        contentPane.add(bg);
    }


    /**
     * Creates the menu for players to enter their names and select whether they
     * are a human or a computer player
     * 
     * @param numPlayers
     *            the selected number of players
     */
    public void createPlayerMenu(int numPlayers)
    {
        JLabel settingText = new JLabel("Enter your names and choose player options:");
        settingText.setFont(new Font("Century Gothic", Font.PLAIN, 25));
        settingText.setBounds(395, 180, 1000, 200);
        settingText.setVisible(true);
        contentPane.add(settingText);

        playerTokens = new JLabel[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++)
        {
            playerTokens[i] = new JLabel("");
            playerTokens[i]
                .setIcon(new ImageIcon(getClass().getResource("/Images/icon" + (i + 1) + ".png")));
            playerTokens[i].setBounds(
                PLAYER_TOKEN_START_X,
                PLAYER_TOKEN_START_Y + i * (PLAYER_TOKEN_HEIGHT + PLAYER_TOKEN_V_SPACE),
                PLAYER_TOKEN_WIDTH,
                PLAYER_TOKEN_HEIGHT);
            contentPane.add(playerTokens[i]);

        }
        playerNoLbl = new JLabel[4];
        playerNoLbl[0] = new JLabel("P1");
        playerNoLbl[0].setFont(new Font("Century Gothic", Font.BOLD, 40));
        playerNoLbl[0].setForeground(new Color(255, 20, 147));

        playerNoLbl[1] = new JLabel("P2");
        playerNoLbl[1].setFont(new Font("Century Gothic", Font.BOLD, 40));
        playerNoLbl[1].setForeground(new Color(255, 140, 0));

        playerNoLbl[2] = new JLabel("P3");
        playerNoLbl[2].setFont(new Font("Century Gothic", Font.BOLD, 40));
        playerNoLbl[2].setForeground(Color.BLUE);

        playerNoLbl[3] = new JLabel("P4");
        playerNoLbl[3].setFont(new Font("Century Gothic", Font.BOLD, 40));
        playerNoLbl[3].setForeground(new Color(139, 0, 139));

        for (int i = 0; i < numOfPlayers; i++)
        {
            playerNoLbl[i].setBounds(
                PLAYER_NO_START_X,
                PLAYER_NO_START_Y + i * (PLAYER_NO_HEIGHT + PLAYER_NO_V_SPACE),
                PLAYER_NO_WIDTH,
                PLAYER_NO_HEIGHT);
            contentPane.add(playerNoLbl[i]);
        }

        playerName = new JTextField[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++)
        {
            playerName[i] = new JTextField("Name");
            playerName[i].setBounds(
                PLAYER_NAME_START_X,
                PLAYER_NAME_START_Y + i * (PLAYER_NAME_HEIGHT + PLAYER_NAME_V_SPACE),
                PLAYER_NAME_WIDTH,
                PLAYER_NAME_HEIGHT);
            playerName[i].setFont(new Font("Century Gothic", Font.PLAIN, 20));
            contentPane.add(playerName[i]);
        }

        modeBtn = new JRadioButton[numOfPlayers][2];
        btnGrp = new ButtonGroup[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++)
        {
            modeBtn[i][0] = new JRadioButton("Human");
            modeBtn[i][0].setSelected(true);
            modeBtn[i][0].setBounds(
                PLAYER_MODE_START_X,
                PLAYER_MODE_START_Y + i * (PLAYER_MODE_HEIGHT + PLAYER_MODE_V_SPACE),
                PLAYER_MODE_WIDTH,
                PLAYER_MODE_HEIGHT);
            contentPane.add(modeBtn[i][0]);
            modeBtn[i][1] = new JRadioButton("Computer");
            modeBtn[i][1].setBounds(
                PLAYER_MODE_START_X + PLAYER_MODE_WIDTH + 20,
                PLAYER_MODE_START_Y + i * (PLAYER_MODE_HEIGHT + PLAYER_MODE_V_SPACE),
                PLAYER_MODE_WIDTH,
                PLAYER_MODE_HEIGHT);
            contentPane.add(modeBtn[i][1]);
            btnGrp[i] = new ButtonGroup();
            btnGrp[i].add(modeBtn[i][0]);
            btnGrp[i].add(modeBtn[i][1]);
        }
    }


    /**
     * Creates the button to start the game once all the player settings have
     * been decided
     */
    private void createStartBtn()
    {
        // Start game button
        JButton startGameBtn = new JButton("Start Game");
        startGameBtn.setBackground(new Color(255, 255, 255));
        startGameBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
        startGameBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                GameInformation game = new GameInformation();
                for (int i = 0; i < playerName.length; i++)
                {
                    Player player = new Player(playerName[i].getText(), modeBtn[i][1].isSelected());
                    game.addPlayer(player);
                }
                SnakesAndLaddersGame frame = new SnakesAndLaddersGame(game);
                frame.setVisible(true);
                dispose();
            }
        });
        startGameBtn.setBounds(720, 680, 200, 50);
        contentPane.add(startGameBtn);
    }


    /**
     * Creates the button to return to the home page
     */
    private void createResetButton()
    {
        JButton reset = new JButton("Return to home page");
        reset.setBackground(new Color(255, 255, 255));
        reset.setFont(new Font("Century Gothic", Font.BOLD, 18));
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                StartWindow s = new StartWindow();
                s.setVisible(true);
                StartWindow.this.dispose();
            }
        });
        reset.setBounds(400, 680, 300, 50);
        contentPane.add(reset);

    }


    /**
     * Creates the StartWindow that leads to the actual game
     * 
     * @param args
     *            not used
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> {
            try
            {
                StartWindow gameSetup = new StartWindow();
                gameSetup.setVisible(true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

}
