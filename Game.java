/**
 * Name: Jaden Lui
 * Date: Tuesday, January 14, 2020
 * Project Name: Lui_Jaden_CPT
 * Description: A game that tests the user's ability to type.  Offers 14 levels
 * of increasing difficulty.  Users are challenged to type a certain amount of
 * words that are shown on the screen in a certain amount of time to complete 
 * the level.  If the timer runs out, they fail the level.  All levels except
 * level 1 are initially locked and the user must complete all previous levels
 * to unlock new levels.  Users can see their stats, including words per minute,
 * accuracy, and time for the session in order to track their progress as they
 * improve at typing.
 */
package TurboType;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import stats.Accuracy;
import stats.Time;
import stats.WPM;

public class Game extends javax.swing.JFrame {

    private final ArrayList<String> words = new ArrayList<>();
    /* holds entire word pool of the game, List because exact number of words 
    is unknown */
    private ArrayList<String> wordsGame = new ArrayList<>();
    // holds temporary word pool for current level of the fame
    private Timer t;
    // allows levels to be timed
    private final Level[] levels = new Level[15];
    /* to be able to store multiple Levels in one location, cleans up code
    Level is self-created class */
    private Level currentLevel;
    // allows for use of Level methods from main
    private String word = "";
    // the word displayed to the user for them to type
    private boolean mouseListenerActive = true;
    // controls whether mouseListener is active or not
    private long elapsedSession;
    /* total time spent playing the game while the application is on, long 
    because it is in nanoseconds*/
    private final int[][] trackWordsKeystrokesErrors = new int[3][2];
    /* 2D array to keep track of amount of words user has entered correctly, 
    number of keystrokes they have made, and number of errors they have made, 
    if second index is 0 that int refers to current level only, if second index
    is 1, that int refers to the entire sessions and is not reset unless the
    program is turned off */

    // all time values in nanoseconds to time certain events
    private long mainStart;
    private long mainFinish;
    private long timeLeft;
    private long update;

    // game constructor
    public Game() {
        initComponents();
        otherGUI();
        getWords();
        createLevels();
    }

    /**
     * Description: Initializes GUI components for the graphics of the game.
     * This includes any JFrames, JPanels, JLabels, JButtons, or other graphical
     * components. pre-condition: none post-condition: All GUI components of the
     * game are initialized and added to a JFrame.
     */
    private void initComponents() {

        layer = new javax.swing.JLayeredPane();
        mainMenu = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        playButton = new javax.swing.JButton();
        statsButton = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();
        levelSelect = new javax.swing.JPanel();
        levelSelectTitle = new javax.swing.JLabel();
        level1 = new javax.swing.JButton();
        level2 = new javax.swing.JButton();
        level3 = new javax.swing.JButton();
        level4 = new javax.swing.JButton();
        level5 = new javax.swing.JButton();
        level6 = new javax.swing.JButton();
        level7 = new javax.swing.JButton();
        level8 = new javax.swing.JButton();
        level9 = new javax.swing.JButton();
        level10 = new javax.swing.JButton();
        level11 = new javax.swing.JButton();
        level12 = new javax.swing.JButton();
        level13 = new javax.swing.JButton();
        level14 = new javax.swing.JButton();
        level15 = new javax.swing.JButton();
        levelSelectBack = new javax.swing.JButton();
        gamePanel = new javax.swing.JPanel();
        showWord = new javax.swing.JLabel();
        input = new javax.swing.JTextField();
        pauseButton = new javax.swing.JButton();
        timerLabel = new javax.swing.JLabel();
        postGame = new javax.swing.JPanel();
        playAgain = new javax.swing.JButton();
        endLevelSelect = new javax.swing.JButton();
        endScreenTitle = new javax.swing.JLabel();
        endWPM = new javax.swing.JLabel();
        endAccuracy = new javax.swing.JLabel();
        endTime = new javax.swing.JLabel();
        endMainMenu = new javax.swing.JButton();
        nextLevel = new javax.swing.JButton();
        helpPanel = new javax.swing.JPanel();
        helpBackButton = new javax.swing.JButton();
        helpLabel = new javax.swing.JLabel();
        statsPanel = new javax.swing.JPanel();
        statsTitle = new javax.swing.JLabel();
        mainMenuStatsBack = new javax.swing.JButton();
        sessionLabel = new javax.swing.JLabel();
        sessionWPMLabel = new javax.swing.JLabel();
        sessionAccuracyLabel = new javax.swing.JLabel();
        sessionTimeLabel = new javax.swing.JLabel();
        pausePanel = new javax.swing.JPanel();
        resumeButton = new javax.swing.JButton();
        pauseHelpButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();
        helpPausePanel = new javax.swing.JPanel();
        levelHelpBack = new javax.swing.JButton();
        levelHelpLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        layer.setLayout(new java.awt.CardLayout());

        mainMenu.setBackground(new java.awt.Color(0, 0, 0));

        title.setBackground(new java.awt.Color(0, 0, 0));
        title.setFont(new java.awt.Font("OCR A Extended", 1, 144));
        title.setForeground(new java.awt.Color(0, 191, 255));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("TurboType");

        playButton.setBackground(new java.awt.Color(0, 0, 0));
        playButton.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        playButton.setText("PLAY");
        playButton.setBorder(new javax.swing.border.MatteBorder(null));
        playButton.setBorderPainted(false);
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        statsButton.setBackground(new java.awt.Color(0, 0, 0));
        statsButton.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        statsButton.setText("STATS");
        statsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statsButtonActionPerformed(evt);
            }
        });

        helpButton.setBackground(new java.awt.Color(0, 0, 0));
        helpButton.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        helpButton.setText("HELP");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainMenuLayout = new javax.swing.GroupLayout(mainMenu);
        mainMenu.setLayout(mainMenuLayout);
        mainMenuLayout.setHorizontalGroup(
                mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, 1202, Short.MAX_VALUE)
                        .addGroup(mainMenuLayout.createSequentialGroup()
                                .addGap(398, 398, 398)
                                .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(helpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(statsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainMenuLayout.setVerticalGroup(
                mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainMenuLayout.createSequentialGroup()
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(statsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(helpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layer.add(mainMenu, "card2");

        levelSelect.setBackground(new java.awt.Color(0, 0, 0));
        levelSelect.setForeground(new java.awt.Color(0, 191, 255));
        levelSelect.setPreferredSize(new java.awt.Dimension(150, 150));

        levelSelectTitle.setBackground(new java.awt.Color(0, 0, 0));
        levelSelectTitle.setFont(new java.awt.Font("OCR A Extended", 1, 96));
        levelSelectTitle.setForeground(new java.awt.Color(0, 191, 255));
        levelSelectTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        levelSelectTitle.setText("Level Select");

        level1.setBackground(new java.awt.Color(0, 191, 255));
        level1.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level1.setText("1");
        level1.setToolTipText("3 letter words");
        level1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        level1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        level1.setName("");
        level1.setPreferredSize(new java.awt.Dimension(150, 150));
        level1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level1ActionPerformed(evt);
            }
        });

        level2.setBackground(new java.awt.Color(0, 191, 255));
        level2.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level2.setDefaultCapable(false);
        level2.setEnabled(false);
        level2.setPreferredSize(new java.awt.Dimension(150, 150));
        level2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level2ActionPerformed(evt);
            }
        });

        level3.setBackground(new java.awt.Color(0, 191, 255));
        level3.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level3.setEnabled(false);
        level3.setPreferredSize(new java.awt.Dimension(150, 150));
        level3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level3ActionPerformed(evt);
            }
        });

        level4.setBackground(new java.awt.Color(0, 191, 255));
        level4.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level4.setEnabled(false);
        level4.setPreferredSize(new java.awt.Dimension(150, 150));
        level4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level4ActionPerformed(evt);
            }
        });

        level5.setBackground(new java.awt.Color(0, 191, 255));
        level5.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level5.setEnabled(false);
        level5.setPreferredSize(new java.awt.Dimension(150, 150));
        level5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level5ActionPerformed(evt);
            }
        });

        level6.setBackground(new java.awt.Color(0, 191, 255));
        level6.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level6.setEnabled(false);
        level6.setPreferredSize(new java.awt.Dimension(150, 150));
        level6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level6ActionPerformed(evt);
            }
        });

        level7.setBackground(new java.awt.Color(0, 191, 255));
        level7.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level7.setEnabled(false);
        level7.setPreferredSize(new java.awt.Dimension(150, 150));
        level7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level7ActionPerformed(evt);
            }
        });

        level8.setBackground(new java.awt.Color(0, 191, 255));
        level8.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level8.setEnabled(false);
        level8.setPreferredSize(new java.awt.Dimension(150, 150));
        level8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level8ActionPerformed(evt);
            }
        });

        level9.setBackground(new java.awt.Color(0, 191, 255));
        level9.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level9.setEnabled(false);
        level9.setPreferredSize(new java.awt.Dimension(150, 150));
        level9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level9ActionPerformed(evt);
            }
        });

        level10.setBackground(new java.awt.Color(0, 191, 255));
        level10.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level10.setEnabled(false);
        level10.setPreferredSize(new java.awt.Dimension(150, 150));
        level10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level10ActionPerformed(evt);
            }
        });

        level11.setBackground(new java.awt.Color(0, 191, 255));
        level11.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level11.setEnabled(false);
        level11.setPreferredSize(new java.awt.Dimension(150, 150));
        level11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level11ActionPerformed(evt);
            }
        });

        level12.setBackground(new java.awt.Color(0, 191, 255));
        level12.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level12.setEnabled(false);
        level12.setPreferredSize(new java.awt.Dimension(150, 150));
        level12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level12ActionPerformed(evt);
            }
        });

        level13.setBackground(new java.awt.Color(0, 191, 255));
        level13.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level13.setEnabled(false);
        level13.setPreferredSize(new java.awt.Dimension(150, 150));
        level13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level13ActionPerformed(evt);
            }
        });

        level14.setBackground(new java.awt.Color(0, 191, 255));
        level14.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        level14.setEnabled(false);
        level14.setPreferredSize(new java.awt.Dimension(150, 150));
        level14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level14ActionPerformed(evt);
            }
        });

        level15.setBackground(new java.awt.Color(0, 191, 255));
        level15.setFont(new java.awt.Font("OCR A Extended", 1, 14));
        level15.setText("Coming Soon!");
        level15.setEnabled(false);
        level15.setPreferredSize(new java.awt.Dimension(150, 150));
        level15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                level15ActionPerformed(evt);
            }
        });

        levelSelectBack.setBackground(new java.awt.Color(0, 191, 255));
        levelSelectBack.setFont(new java.awt.Font("OCR A Extended", 1, 24));
        levelSelectBack.setText("BACK");
        levelSelectBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelSelectBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout levelSelectLayout = new javax.swing.GroupLayout(levelSelect);
        levelSelect.setLayout(levelSelectLayout);
        levelSelectLayout.setHorizontalGroup(
                levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(levelSelectLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(levelSelectBack)
                                .addGap(54, 54, 54)
                                .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(levelSelectLayout.createSequentialGroup()
                                                .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, levelSelectLayout.createSequentialGroup()
                                                                .addComponent(level11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(level12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(level13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(level14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(levelSelectLayout.createSequentialGroup()
                                                                .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, levelSelectLayout.createSequentialGroup()
                                                                                .addComponent(level1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(level2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(levelSelectLayout.createSequentialGroup()
                                                                                .addComponent(level6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(level7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(levelSelectLayout.createSequentialGroup()
                                                                                .addComponent(level8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(level9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(levelSelectLayout.createSequentialGroup()
                                                                                .addComponent(level3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(level4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                .addGap(18, 18, 18)
                                                .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(level5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(level10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(level15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(levelSelectTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        levelSelectLayout.setVerticalGroup(
                levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(levelSelectLayout.createSequentialGroup()
                                .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(levelSelectLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(levelSelectTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(levelSelectLayout.createSequentialGroup()
                                                .addGap(54, 54, 54)
                                                .addComponent(levelSelectBack, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(22, 22, 22)
                                .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(level2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(level1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(level3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(level4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(level5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(level8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(level7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(level6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(level9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(level10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(level13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(levelSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(level11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(level12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(level14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(level15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        level1.getAccessibleContext().setAccessibleDescription("");
        level2.getAccessibleContext().setAccessibleDescription("");
        level3.getAccessibleContext().setAccessibleDescription("");
        level4.getAccessibleContext().setAccessibleDescription("");
        level5.getAccessibleContext().setAccessibleDescription("");
        level6.getAccessibleContext().setAccessibleDescription("");
        level7.getAccessibleContext().setAccessibleDescription("");
        level8.getAccessibleContext().setAccessibleDescription("");
        level9.getAccessibleContext().setAccessibleDescription("");
        level10.getAccessibleContext().setAccessibleDescription("");
        level11.getAccessibleContext().setAccessibleDescription("");
        level12.getAccessibleContext().setAccessibleDescription("");
        level13.getAccessibleContext().setAccessibleDescription("");
        level14.getAccessibleContext().setAccessibleDescription("");
        level15.getAccessibleContext().setAccessibleDescription("");

        layer.add(levelSelect, "card4");

        gamePanel.setBackground(new java.awt.Color(0, 191, 255));
        gamePanel.setForeground(new java.awt.Color(0, 191, 255));
        gamePanel.setPreferredSize(new java.awt.Dimension(1200, 900));

        showWord.setBackground(new java.awt.Color(0, 191, 255));
        showWord.setFont(new java.awt.Font("OCR A Extended", 1, 36));
        showWord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        showWord.setText("a");
        showWord.setOpaque(true);

        input.setFont(new java.awt.Font("OCR A Extended", 0, 18));
        input.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        input.setText("click here to start");
        input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputActionPerformed(evt);
            }
        });

        pauseButton.setToolTipText("");
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        timerLabel.setBackground(new java.awt.Color(0, 0, 0));
        timerLabel.setFont(new java.awt.Font("OCR A Extended", 0, 96));
        timerLabel.setForeground(new java.awt.Color(0, 191, 255));
        timerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerLabel.setOpaque(true);

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
                gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(gamePanelLayout.createSequentialGroup()
                                .addComponent(timerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(gamePanelLayout.createSequentialGroup()
                                                .addComponent(showWord, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(gamePanelLayout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap())))
        );
        gamePanelLayout.setVerticalGroup(
                gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(gamePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pauseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(showWord, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77))
                        .addComponent(timerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        input.getAccessibleContext().setAccessibleDescription("Click to start, after starting press enter when you type the correct word");

        layer.add(gamePanel, "card3");

        postGame.setPreferredSize(new java.awt.Dimension(1200, 800));

        playAgain.setFont(new java.awt.Font("OCR A Extended", 1, 14));
        playAgain.setText("PLAY AGAIN");
        playAgain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playAgainActionPerformed(evt);
            }
        });

        endLevelSelect.setFont(new java.awt.Font("OCR A Extended", 1, 14));
        endLevelSelect.setText("LEVEL SELECT");
        endLevelSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endLevelSelectActionPerformed(evt);
            }
        });

        endScreenTitle.setFont(new java.awt.Font("OCR A Extended", 1, 96));
        endScreenTitle.setForeground(new java.awt.Color(0, 191, 255));
        endScreenTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        endScreenTitle.setText("Level Completed!");

        endWPM.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        endWPM.setForeground(new java.awt.Color(0, 191, 255));
        endWPM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        endAccuracy.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        endAccuracy.setForeground(new java.awt.Color(0, 191, 255));
        endAccuracy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        endTime.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        endTime.setForeground(new java.awt.Color(0, 191, 255));
        endTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        endMainMenu.setFont(new java.awt.Font("OCR A Extended", 1, 14));
        endMainMenu.setText("MAIN MENU");
        endMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endMainMenuActionPerformed(evt);
            }
        });

        nextLevel.setFont(new java.awt.Font("OCR A Extended", 1, 14));
        nextLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextLevelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout postGameLayout = new javax.swing.GroupLayout(postGame);
        postGame.setLayout(postGameLayout);
        postGameLayout.setHorizontalGroup(
                postGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(endScreenTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1202, Short.MAX_VALUE)
                        .addComponent(endAccuracy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(endTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(postGameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(endWPM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(postGameLayout.createSequentialGroup()
                                .addGap(273, 273, 273)
                                .addComponent(playAgain, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nextLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(endLevelSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(endMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        postGameLayout.setVerticalGroup(
                postGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, postGameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(endScreenTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endWPM, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endAccuracy, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endTime, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(postGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(postGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(nextLevel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(postGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(playAgain, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(endLevelSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(endMainMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layer.add(postGame, "card5");

        helpBackButton.setBackground(new java.awt.Color(0, 191, 255));
        helpBackButton.setFont(new java.awt.Font("OCR A Extended", 1, 24));
        helpBackButton.setText("BACK");
        helpBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpBackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout helpPanelLayout = new javax.swing.GroupLayout(helpPanel);
        helpPanel.setLayout(helpPanelLayout);
        helpPanelLayout.setHorizontalGroup(
                helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(helpPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(helpBackButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpPanelLayout.createSequentialGroup()
                                .addGap(0, 105, Short.MAX_VALUE)
                                .addComponent(helpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1097, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        helpPanelLayout.setVerticalGroup(
                helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(helpPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(helpBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(helpLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE))
        );

        layer.add(helpPanel, "card6");

        statsPanel.setBackground(new java.awt.Color(0, 0, 0));

        statsTitle.setBackground(new java.awt.Color(0, 0, 0));
        statsTitle.setFont(new java.awt.Font("OCR A Extended", 1, 144));
        statsTitle.setForeground(new java.awt.Color(0, 191, 255));
        statsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statsTitle.setText("STATS");
        statsTitle.setOpaque(true);

        mainMenuStatsBack.setBackground(new java.awt.Color(0, 191, 255));
        mainMenuStatsBack.setFont(new java.awt.Font("OCR A Extended", 1, 24));
        mainMenuStatsBack.setText("BACK");
        mainMenuStatsBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuStatsBackActionPerformed(evt);
            }
        });

        sessionLabel.setBackground(new java.awt.Color(0, 0, 0));
        sessionLabel.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        sessionLabel.setForeground(new java.awt.Color(0, 191, 255));
        sessionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sessionLabel.setText("THIS SESSION");
        sessionLabel.setOpaque(true);

        sessionWPMLabel.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        sessionWPMLabel.setForeground(new java.awt.Color(0, 191, 255));
        sessionWPMLabel.setText("jLabel1");

        sessionAccuracyLabel.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        sessionAccuracyLabel.setForeground(new java.awt.Color(0, 191, 255));
        sessionAccuracyLabel.setText("jLabel2");

        sessionTimeLabel.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        sessionTimeLabel.setForeground(new java.awt.Color(0, 191, 255));
        sessionTimeLabel.setText("jLabel3");

        javax.swing.GroupLayout statsPanelLayout = new javax.swing.GroupLayout(statsPanel);
        statsPanel.setLayout(statsPanelLayout);
        statsPanelLayout.setHorizontalGroup(
                statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(sessionAccuracyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sessionWPMLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(statsPanelLayout.createSequentialGroup()
                                                .addComponent(mainMenuStatsBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(sessionTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(sessionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(statsTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE))))
                                .addContainerGap(123, Short.MAX_VALUE))
        );
        statsPanelLayout.setVerticalGroup(
                statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statsPanelLayout.createSequentialGroup()
                                .addGroup(statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(statsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(statsPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(mainMenuStatsBack, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sessionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sessionWPMLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sessionAccuracyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sessionTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(153, Short.MAX_VALUE))
        );

        layer.add(statsPanel, "card7");

        pausePanel.setBackground(new java.awt.Color(0, 0, 0));

        resumeButton.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        resumeButton.setText("RESUME");
        resumeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumeButtonActionPerformed(evt);
            }
        });

        pauseHelpButton.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        pauseHelpButton.setText("HELP");
        pauseHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseHelpButtonActionPerformed(evt);
            }
        });

        quitButton.setFont(new java.awt.Font("OCR A Extended", 1, 48));
        quitButton.setText("QUIT");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pausePanelLayout = new javax.swing.GroupLayout(pausePanel);
        pausePanel.setLayout(pausePanelLayout);
        pausePanelLayout.setHorizontalGroup(
                pausePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pausePanelLayout.createSequentialGroup()
                                .addGap(450, 450, 450)
                                .addGroup(pausePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(resumeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pauseHelpButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(quitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(452, Short.MAX_VALUE))
        );
        pausePanelLayout.setVerticalGroup(
                pausePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pausePanelLayout.createSequentialGroup()
                                .addContainerGap(275, Short.MAX_VALUE)
                                .addComponent(resumeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pauseHelpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(quitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(202, 202, 202))
        );

        layer.add(pausePanel, "card8");

        levelHelpBack.setBackground(new java.awt.Color(0, 191, 255));
        levelHelpBack.setFont(new java.awt.Font("OCR A Extended", 1, 24));
        levelHelpBack.setText("BACK");
        levelHelpBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelHelpBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout helpPausePanelLayout = new javax.swing.GroupLayout(helpPausePanel);
        helpPausePanel.setLayout(helpPausePanelLayout);
        helpPausePanelLayout.setHorizontalGroup(
                helpPausePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(helpPausePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(levelHelpBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(levelHelpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1091, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        helpPausePanelLayout.setVerticalGroup(
                helpPausePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpPausePanelLayout.createSequentialGroup()
                                .addComponent(levelHelpLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(helpPausePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(levelHelpBack, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(902, Short.MAX_VALUE))
        );

        layer.add(helpPausePanel, "card9");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(layer)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(layer)
        );

        pack();
    }

    /**
     * Description: Sets other GUI settings that are unrelated to the size or
     * position of the components. pre-condition: every GUI component and
     * trackWordsKeystrokesErrors has been instantiated post-condition: Every
     * GUI related setting is set.
     *
     */
    private void otherGUI() {

        Color lightBlue = new Color(0, 191, 255);
        // light blue colour seen in the game
        ImageIcon disabled = new ImageIcon("disabledIcon.png"); // lock
        ImageIcon pause = new ImageIcon("pause.png"); // pause icon 
        ImageIcon helpImage = new ImageIcon("help.png"); // help screen

        // putting lock picture on buttons when they are disabled
        level2.setDisabledIcon(disabled);
        level3.setDisabledIcon(disabled);
        level4.setDisabledIcon(disabled);
        level5.setDisabledIcon(disabled);
        level6.setDisabledIcon(disabled);
        level7.setDisabledIcon(disabled);
        level8.setDisabledIcon(disabled);
        level9.setDisabledIcon(disabled);
        level10.setDisabledIcon(disabled);
        level11.setDisabledIcon(disabled);
        level12.setDisabledIcon(disabled);
        level13.setDisabledIcon(disabled);
        level14.setDisabledIcon(disabled);
        nextLevel.setDisabledIcon(disabled);

        // setting images to appropriate components
        pauseButton.setIcon(pause);
        helpLabel.setIcon(helpImage);
        levelHelpLabel.setIcon(helpImage);

        // setting buttons to light blue colour
        level1.setBackground(lightBlue);
        level2.setBackground(lightBlue);
        level3.setBackground(lightBlue);
        level4.setBackground(lightBlue);
        level5.setBackground(lightBlue);
        level6.setBackground(lightBlue);
        level7.setBackground(lightBlue);
        level8.setBackground(lightBlue);
        level9.setBackground(lightBlue);
        level10.setBackground(lightBlue);
        level11.setBackground(lightBlue);
        level12.setBackground(lightBlue);
        level13.setBackground(lightBlue);
        level14.setBackground(lightBlue);
        playButton.setBackground(lightBlue);
        statsButton.setBackground(lightBlue);
        helpButton.setBackground(lightBlue);
        nextLevel.setBackground(lightBlue);
        playAgain.setBackground(lightBlue);
        nextLevel.setBackground(lightBlue);
        endLevelSelect.setBackground(lightBlue);
        endMainMenu.setBackground(lightBlue);
        resumeButton.setBackground(lightBlue);
        pauseHelpButton.setBackground(lightBlue);
        quitButton.setBackground(lightBlue);

        // setting labels to black colour
        endWPM.setBackground(Color.BLACK);
        endAccuracy.setBackground(Color.BLACK);
        endTime.setBackground(Color.BLACK);
        postGame.setBackground(Color.BLACK);
        endScreenTitle.setBackground(Color.BLACK);
        endWPM.setBackground(Color.BLACK);
        endAccuracy.setBackground(Color.BLACK);
        endTime.setBackground(Color.BLACK);
        statsTitle.setBackground(Color.BLACK);
        sessionLabel.setBackground(Color.BLACK);

        // timer for the game
        t = new Timer(1, new ActionListener() { // fires every ms
            public void actionPerformed(ActionEvent e) {
                DecimalFormat dfTimer = new DecimalFormat("###.###"); // sets value shown to 3 decimals
                long current = System.currentTimeMillis(); // finds current time in ms
                long elapsed = current - update; // time between now and last time label was updated
                timeLeft -= elapsed; // continuously lowers number on label
                update = current;
                timerLabel.setText("" + dfTimer.format(timeLeft / 1000.0)); // timeLeft is in ms, to get to seconds divide by 1000

                if (timeLeft <= 0) { // if time is up in the level
                    t.stop();
                    mainFinish = System.nanoTime(); //finds end time of the game
                    levelEnd(false); // goes to method for ending a level, "false" means user lost
                }
            }
        });

        KeyListener keyListener;
        keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (Character.isLetter(keyEvent.getKeyChar())) { //only considers typed letters to be keystrokes
                    trackWordsKeystrokesErrors[1][0]++;
                    trackWordsKeystrokesErrors[1][1]++;
                }
                if (trackWordsKeystrokesErrors[1][0] == 1) { // first and only the first typed key begins the timer
                    pauseButton.setEnabled(true); // allows game to be paused
                    mainStart = System.nanoTime(); // finds start time of the game for later use
                    update = System.currentTimeMillis(); // for use in timer
                    t.start();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                mouseListenerActive = false; // sets mouseListener inactive so it does not keep wiping textfield when user clicks on it
                String checkInput = input.getText();
                try {
                    if (checkInput.length() < word.length()) { // if user's input is not as long as word, no change in colour
                        showWord.setForeground(Color.BLACK);
                    } else {
                        if (checkInput.equals(word)) { // if user's input is correct, sets word green
                            showWord.setForeground(Color.GREEN);
                        } else {
                            showWord.setForeground(Color.RED); // if user's input is incorrect, set word red
                        }
                    }
                    if (!checkInput.equals(word.substring(0, checkInput.length()))) { // if user types something that is not a substring in the word, it is considered an error
                        trackWordsKeystrokesErrors[2][0]++; // current Level errors go up
                        trackWordsKeystrokesErrors[2][1]++; // total errors go up
                    }
                } catch (StringIndexOutOfBoundsException e) { // in case user enters more letters than are in the word
                }
            }

            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }
        };

        input.addKeyListener(keyListener);

        input.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e
            ) {
                if (mouseListenerActive) {
                    input.setText(""); // if mouseListener is active, clears text of input field when it is clicked
                }
            }
        }
        );
    }

    /**
     * Description: Gets word pool for the game. pre-condition: text file
     * containing the words is found in the project post-condition: A text file
     * containing the entire word pool of the game is read into the program.
     */
    private void getWords() {
        String filename = "words.txt"; // file containing all the words
        try {
            FileReader fr = new FileReader(filename);
            Scanner s = new Scanner(fr);

            while (s.hasNextLine()) { // while there are still lines in the file, continues to read it
                words.add(s.nextLine());
            }
        } catch (IOException e) {
            System.out.println("An error has occurred with FileReader.");
        }
    }

    /**
     * Description: Creates the levels of the game. pre-condition: levels was
     * instantiated in the main method post-condition: 15 Level objects are
     * created and stored into the levels array
     *
     */
    private void createLevels() {
        levels[0] = new Level(words, 1, 3, 10, 20, true, level2);
        levels[1] = new Level(words, 2, 4, 10, 20, false, level3);
        levels[2] = new Level(words, 3, 5, 10, 20, false, level4);
        levels[3] = new Level(words, 4, 6, 10, 20, false, level5);
        levels[4] = new Level(words, 5, 7, 10, 20, false, level6);
        levels[5] = new Level(words, 6, 8, 10, 25, false, level7);
        levels[6] = new Level(words, 7, 9, 10, 25, false, level8);
        levels[7] = new Level(words, 8, 10, 10, 25, false, level9);
        levels[8] = new Level(words, 9, 11, 10, 25, false, level10);
        levels[9] = new Level(words, 10, 12, 10, 25, false, level11);
        levels[10] = new Level(words, 11, 13, 10, 30, false, level12);
        levels[11] = new Level(words, 12, 14, 10, 30, false, level13);
        levels[12] = new Level(words, 13, 15, 10, 30, false, level14);
        levels[13] = new Level(words, 14, 16, 10, 30, false, level14);
        levels[14] = new Level(words, 15, 17, 10, 60, false, level14);
    }

    /**
     * Description: Resumes timer from the time it left off on. pre-condition:
     * Timer t has been instantiated post-condition: Timer resumes from the time
     * it was paused on
     */
    private void resumeTimer() {
        update = System.currentTimeMillis();
        t.start();
    }

    /**
     * Description: Resumes timer from the time it left off on. pre-condition:
     * Timer t has been instantiated post-condition: Timer resumes from the time
     * it was paused on
     */
    private void pauseTimer() {
        long now = System.currentTimeMillis();
        timeLeft -= (now - update); // finds timeLeft at last moment before pause
        t.stop();
    }

    /**
     * Description: Sets appropriate panel visible. pre-condition: panel has
     * been instantiated post-condition: the JPanel panel is show on screen and
     * no other JPanel is visible
     *
     * @param panel - the JPanel to be shown
     */
    private void switchPanels(JPanel panel) {
        layer.removeAll();
        layer.add(panel);
        layer.repaint();
        layer.revalidate();
    }

    /**
     * Description: Updates labels on the stats page to be accurate for the
     * current session pre-condition: trackWordsKeystrokesErrors is an
     * instantiated array object, trackWordsKeystrokesErrors[2][1] and
     * elapsedSession are above 0 post-condition: the session's stats of WPM,
     * accuracy, and time are correctly updated and displayed on the appropriate
     * label
     */
    private void updateStats() {
        Time sessionTime = new Time(0, elapsedSession);  // creates new Time object for the session
        sessionTime.printStat(sessionTimeLabel, sessionTime.getTime()); //prints correct time to correct label

        Accuracy sessionAccuracy = new Accuracy(trackWordsKeystrokesErrors[1][1], trackWordsKeystrokesErrors[2][1]); // creates new Accuracy object for the session
        sessionAccuracy.printStat(sessionAccuracyLabel, sessionAccuracy.getAccuracy()); //prints correct acccuracy to correct label

        WPM sessionWPM = new WPM(trackWordsKeystrokesErrors[0][1], sessionTime.getTime()); // creates new WPM object for the session
        sessionWPM.printStat(sessionWPMLabel, sessionWPM.getWpm()); //prints correct WPM to correct label
    }

    /**
     * Description: A set of things the program must do before every level.
     * pre-condition: curLevel is an instantiated Level object, GUI components
     * have been instantiated, other static variables have been declared
     * post-condition: characteristics depicting the correct level are displayed
     * in the game
     *
     * @param curLevel - the Level whose methods are used to set the appropriate
     * level in the game
     */
    private void levelStart(Level curLevel) {
        currentLevel = curLevel;
        timeLeft = currentLevel.getTimeAllowed() * 1000; // sets timeLeft to time allowed in the level in seconds times 1000 (ms)
        timerLabel.setText("" + currentLevel.getTimeAllowed());

        currentLevel.getProperWordLength(); // only takes appopriate word length
        currentLevel.numberOfWords(); // creates List with appropriate number of words
        wordsGame = currentLevel.getGameWords(); // sets main method wordsGame equal to those of the Level object
        word = currentLevel.getGameWords().get(0); // sets word equal to first element in the List

        showWord.setText(word);
        showWord.setForeground(Color.BLACK);
        input.setText("Once you begin typing, the timer will start!");
        mouseListenerActive = true; // sets mouseListener back to true since it is changed to false when clicked once
        pauseButton.setEnabled(false);
        switchPanels(gamePanel);
    }

    /**
     * Description: A set of things the program must do after every level.
     * pre-condition: GUI components have been instantiated, other static
     * variables have been declared, completed has been declared post-condition:
     * user is shown the appropriate messages whether they won or lost, user is
     * shown their stats for the level, appropriate buttons are unlocked for the
     * user if they won
     *
     * @param completed - represents if user completed level or not, true for if
     * they did, false if they did not
     */
    private void levelEnd(boolean completed) {
        Time levelEndTime = new Time(mainStart, mainFinish); // creates new Time object for the level
        currentLevel.setLevelTime(levelEndTime.getTime()); // sets current level's time of completion

        WPM levelWPM = new WPM(trackWordsKeystrokesErrors[0][0], levelEndTime.getTime()); // creates new WPM object for the level
        currentLevel.setLevelWPM(levelWPM.getWpm()); // sets current level's wpm score

        Accuracy levelAccuracy = new Accuracy(trackWordsKeystrokesErrors[1][0], trackWordsKeystrokesErrors[2][0]); // creates new Accuracy object for the level
        currentLevel.setLevelAccuracy(levelAccuracy.getAccuracy()); // sets current level's Accuracy score

        elapsedSession += mainFinish - mainStart; // keeping total count of time played in the session

        if (completed) { // if user completed the level
            endScreenTitle.setText("LEVEL COMPLETED!");
            nextLevel.setText("NEXT LEVEL");
            currentLevel.getUnlockedButton().setText("" + (currentLevel.getLevelNum() + 1)); // shows number of button instead of lock
            currentLevel.setCompleted(completed);
            currentLevel.getUnlockedButton().setEnabled(true); // unlocks button for next level
            nextLevel.setEnabled(true); // allows user to use Next Level button on end screen
        } else { // if user failed level
            endScreenTitle.setText("LEVEL FAILED!");
            nextLevel.setText(""); // Next Level is a lock image
            nextLevel.setEnabled(false); // Next Level cannot be clicked
        }

        levelEndTime.printStat(endTime, levelEndTime.getTime()); // prints user's time of completion
        levelWPM.printStat(endWPM, levelWPM.getWpm()); // prints user's wpm for the level
        levelAccuracy.printStat(endAccuracy, levelAccuracy.getAccuracy()); // prints user's accuracy for the level

        //sets user's words typed, keystrokes, and errors for one level back to 0 for it to be accurate for the next level
        trackWordsKeystrokesErrors[0][0] = 0;
        trackWordsKeystrokesErrors[1][0] = 0;
        trackWordsKeystrokesErrors[2][0] = 0;
        wordsGame.clear();
        switchPanels(postGame);
    }

    /**
     * Description: ActionPerformed functions for all GUI components.
     * pre-condition: GUI components have been instantiated, every Level object
     * has been instantiated 
     * post-condition: various
     *
     * @param evt - event that is fired
     */
    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(levelSelect); // allows user to select level
    }

    private void statsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        updateStats(); // updates stats to be accurate
        switchPanels(statsPanel); // shows user their stats for the session
    }

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(helpPanel); // shows help image
    }

    private void resumeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(gamePanel); // brings user back to game
        resumeTimer();
    }

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        pauseTimer();
        switchPanels(pausePanel); // shows pause screen
    }

    private void pauseHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(helpPausePanel); // shows user help image from paused screen    
    }

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(mainMenu); // quits back to main menu
        mainFinish = System.nanoTime(); // gets time of quit
        levelEnd(false); // user fails the level
    }

    // for level buttons, button starts level with characteristics of corresponding level 
    private void level1ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[0]);
    }

    private void level2ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[1]);
    }

    private void level3ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[2]);
    }

    private void level4ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[3]);
    }

    private void level5ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[4]);
    }

    private void level6ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[5]);
    }

    private void level7ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[6]);
    }

    private void level8ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[7]);
    }

    private void level9ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[8]);
    }

    private void level10ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[9]);
    }

    private void level11ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[10]);
    }

    private void level12ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[11]);
    }

    private void level13ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[12]);
    }

    private void level14ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[13]);
    }

    private void level15ActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[14]);
    }

    private void levelSelectBackActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(mainMenu); // back button on level select screen goes back to main menu
    }

    private void helpBackButtonActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(mainMenu); // ^
    }

    private void mainMenuStatsBackActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(mainMenu);
    }

    private void playAgainActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(currentLevel); // keeps characteristics of current level
    }

    private void nextLevelActionPerformed(java.awt.event.ActionEvent evt) {
        levelStart(levels[currentLevel.getLevelNum()]); // gets characteristics of next level
    }

    private void endLevelSelectActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(levelSelect);
    }

    private void endMainMenuActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(mainMenu);
    }

    private void levelHelpBackActionPerformed(java.awt.event.ActionEvent evt) {
        switchPanels(pausePanel); // getting help from pause page, back button shows pause screen
    }

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {
        String in = input.getText(); // to store user's input

        if (in.equals(word)) { // is user types correct word
            trackWordsKeystrokesErrors[0][0]++; // word count goes up by 1
            trackWordsKeystrokesErrors[0][1]++; // total word count goes up by 1
            if (trackWordsKeystrokesErrors[0][0] < wordsGame.size()) { // if user has not reached final word to be typed
                input.setText(""); // resets textfield so user does not have to do it themselves
                word = wordsGame.get(trackWordsKeystrokesErrors[0][0]); // sets word equal to next word in the List
                showWord.setText(word);
                showWord.setForeground(Color.BLACK);
            } else {  // user has typed the required number of words
                t.stop(); 
                mainFinish = System.nanoTime(); 
                levelEnd(true); // user completed the level
            }
        }
    }

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Game().setVisible(true);
        });

    }

    // Variables declaration                    
    private javax.swing.JLabel endAccuracy;
    private javax.swing.JButton endLevelSelect;
    private javax.swing.JButton endMainMenu;
    private javax.swing.JLabel endScreenTitle;
    private javax.swing.JLabel endTime;
    private javax.swing.JLabel endWPM;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JButton helpBackButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JPanel helpPanel;
    private javax.swing.JPanel helpPausePanel;
    private javax.swing.JTextField input;
    private javax.swing.JLayeredPane layer;
    private javax.swing.JButton level1;
    private javax.swing.JButton level10;
    private javax.swing.JButton level11;
    private javax.swing.JButton level12;
    private javax.swing.JButton level13;
    private javax.swing.JButton level14;
    private javax.swing.JButton level15;
    private javax.swing.JButton level2;
    private javax.swing.JButton level3;
    private javax.swing.JButton level4;
    private javax.swing.JButton level5;
    private javax.swing.JButton level6;
    private javax.swing.JButton level7;
    private javax.swing.JButton level8;
    private javax.swing.JButton level9;
    private javax.swing.JButton levelHelpBack;
    private javax.swing.JLabel levelHelpLabel;
    private javax.swing.JPanel levelSelect;
    private javax.swing.JButton levelSelectBack;
    private javax.swing.JLabel levelSelectTitle;
    private javax.swing.JPanel mainMenu;
    private javax.swing.JButton mainMenuStatsBack;
    private javax.swing.JButton nextLevel;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton pauseHelpButton;
    private javax.swing.JPanel pausePanel;
    private javax.swing.JButton playAgain;
    private javax.swing.JButton playButton;
    private javax.swing.JPanel postGame;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton resumeButton;
    private javax.swing.JLabel sessionAccuracyLabel;
    private javax.swing.JLabel sessionLabel;
    private javax.swing.JLabel sessionTimeLabel;
    private javax.swing.JLabel sessionWPMLabel;
    private javax.swing.JLabel showWord;
    private javax.swing.JButton statsButton;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JLabel statsTitle;
    private javax.swing.JLabel timerLabel;
    private javax.swing.JLabel title;
    // End of variables declaration                   
}
