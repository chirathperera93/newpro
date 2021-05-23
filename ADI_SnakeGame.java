import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ADI_SnakeGame extends JFrame implements WindowListener, KeyListener, ADI_SnakeAction {

    private ADI_GameBoard adi_gameBoard;

    private ADI_Snake adi_snake;
    private ADI_Prey adi_prey;
    private JLabel timeLabel;
    private JLabel speedLabel;

    // for timer and user controls
    private Timer countTimer, gameTimer;
    private ADI_Direction lastADIDirection, currentADIDirection;

    private int speed, score, elapsed, counts;
    private boolean gameStarted, messageRepeat;

    private JLabel topPlayersScore = new JLabel("TOP PLAYERS SCORE");
    private JLabel currentPlayerScore = new JLabel("CURRENT PLAYER SCORE");
    private JLabel SN = new JLabel("");
    private Image snakeImage = (new ImageIcon("resources/sn.png").getImage());
    private JLabel lblScore;

    private String username;

    private int storedHighScore = -1;

    ArrayList<String> stringsScores = new ArrayList<>();

    public ADI_SnakeGame(String username) {
        this.username = username;
        stringsScores = new ArrayList<>();
        setResizable(false);

        // for key press
        addKeyListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("My Snake Game");
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        initGamePanel();
        initStatusPanel();

        countTimer = new Timer(800, new CountTimerHandle());
        gameTimer = new Timer(1, new GameTimerHandle());

        setSpeed(150);

        pack();
        setSize(getWidth() + 30, getHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        score = 0;
        reset();
    }


    public void initGamePanel() {
        // init game panel
        getSCore();
        adi_gameBoard = new ADI_GameBoard();
        adi_gameBoard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        add(adi_gameBoard);
    }

    public void initStatusPanel() {
        JPanel newPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(18, 40, 18, 40);


        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        JPanel topPlayersScoreJPanel = new JPanel();
        CustomPanel topPlayersScoreCustomPanel = new CustomPanel(Color.white, topPlayersScore);
        topPlayersScoreJPanel.add(topPlayersScoreCustomPanel);
        topPlayersScoreJPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getSCore();
                JOptionPane.showMessageDialog(e.getComponent(), stringsScores.get(0), "Top Player Score", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        newPanel.add(topPlayersScoreJPanel, constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        JPanel currentPlayerScoreJPanel = new JPanel();
        CustomPanel currentPlayerScoreCustomPanel = new CustomPanel(Color.white, currentPlayerScore);
        currentPlayerScoreCustomPanel.add(currentPlayerScoreJPanel);
        currentPlayerScoreCustomPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(e.getComponent(), score + " x", "Current Player Score", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        newPanel.add(currentPlayerScoreCustomPanel, constraints);


        constraints.gridx = 0;
        constraints.gridy = 2;
        ImageIcon icon = new ImageIcon(snakeImage);
        SN = new JLabel(icon);
        newPanel.add(SN, constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;


        JPanel lblScoreJPanel = new JPanel();
        lblScore = new JLabel("PROG5001: 2021 \n  Dilshan");
        CustomPanel lblScoreScoreCustomPanel = new CustomPanel(Color.white, lblScore);
        lblScoreJPanel.add(lblScoreScoreCustomPanel);
        newPanel.add(lblScoreJPanel, constraints);


        constraints.gridx = 0;
        constraints.gridy = 4;
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });

        newPanel.add(quitButton, constraints);

        newPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));


        timeLabel = new JLabel("00:00", JLabel.CENTER);
        speedLabel = new JLabel("Medium", JLabel.CENTER);

        add(newPanel);


    }

    private class SpeedAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            setSpeed(Integer.parseInt(event.getActionCommand()));
            String speedText = ((JRadioButtonMenuItem) event.getSource()).getText();

            if (speedLabel == null)
                return;

            speedLabel.setText(speedText);
        }
    }


    public void reset() {
        adi_snake = new ADI_Snake();
        adi_snake.setSnakeActionListener(this);

// move to center
        adi_snake.moveTo(ADI_Config.snakeX, ADI_Config.snakeY);


        adi_prey = new ADI_Prey(ADI_Config.panelWidth, ADI_Config.panelHeight);
        adi_prey.escapeFrom(adi_snake);


        adi_gameBoard.setSnake(adi_snake);
        adi_gameBoard.setPrey(adi_prey);


        elapsed = 0;
        counts = 3;
        gameStarted = false;
        messageRepeat = false;
        updateElapsedTime();

        setFocusable(true);

        if (!gameStarted)
            countTimer.start();
    }

    public void setSpeed(int s) {
        speed = s;
        if (gameTimer == null)
            return;
        gameTimer.setDelay(speed);
    }

    public void start() {
        gameStarted = true;
        stringsScores = new ArrayList<>();
        countTimer.start();
    }

    public void stop() {
        gameTimer.stop();
        gameStarted = false;
        reset();
    }

    public void pause() {
        if (!gameStarted)
            return;

        if (gameTimer.isRunning())
            gameTimer.stop();
        else
            gameTimer.start();
    }

    public void startNewGame() {
        stringsScores = new ArrayList<>();
        score = 0;
        reset();
        start();
    }


    public void updateElapsedTime() {
        elapsed += speed;
        timeLabel.setText(toTime(elapsed));
    }

    public String toTime(int msecs) {
        int secs = msecs / 1000;
        return String.format("%02d:%02d", secs / 60, secs % 60);
    }


    public void exitGame() {
        countTimer.stop();
        score = 0;
        dispose();
    }


    private class CountTimerHandle implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!gameStarted) {
                if (messageRepeat)
                    adi_gameBoard.showMessage("Press Enter to Play!");
                else
                    adi_gameBoard.showMessage("");
                messageRepeat = !messageRepeat;
            } else {
                if (counts == 0) {
                    adi_gameBoard.removeMessage();
                    gameTimer.start();
                    countTimer.stop();
                    return;
                }
                adi_gameBoard.showMessage(Integer.toString(counts));
                counts--;
            }

        }
    }

    private class GameTimerHandle implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // check for last direction then step
            // if don't step if changed
            // and call step() on the key event?
            // this is way

            // the next way have some delay, because the next step
            // would wait the the timer to do
            updateElapsedTime();

            if (lastADIDirection == adi_snake.getDirection())
                adi_snake.step();
            lastADIDirection = adi_snake.getDirection();

            adi_gameBoard.repaint();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            //create the game with a game title as follow: The Snake Game (C) Your_Name
//            JFrame sgame = new ADI_SnakeGame();
//            sgame.setVisible(true);

            ADI_LoginForm adi_loginForm = new ADI_LoginForm();
            adi_loginForm.setVisible(true);
        });
    }

    private void checkStore() {

        String highScore = this.username + ":" + score;

        File scoreFile = new File("highScore.txt");


        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fileWriter = new FileWriter(scoreFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                if (storedHighScore < score) {
                    bufferedWriter.write(highScore);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {

            try {
                fileWriter = new FileWriter(scoreFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                if (storedHighScore < score) {
                    bufferedWriter.write(highScore);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }
    }

    public void getSCore() {
        stringsScores = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("highScore.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] x = line.split(":");
                storedHighScore = Integer.parseInt(x[1]);
                stringsScores.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void snakeHitsPrey() {
        adi_prey.escapeFrom(adi_snake);
        adi_snake.addNormalPart();
        ++score;
    }

    @Override
    public void snakeHitsItself() {
        stop();
        checkStore();
        JOptionPane.showMessageDialog(this, "Game Over!", "Try Again!", JOptionPane.ERROR_MESSAGE);

    }

    @Override
    public void snakeHitsBorders() {
        stop();
        checkStore();
        JOptionPane.showMessageDialog(this, "Game Over!", "Try Again!", JOptionPane.ERROR_MESSAGE);

    }

    @Override
    public void partOutOfBorders(ADI_SnakePart part) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentADIDirection = adi_snake.getDirection();
        ADI_Direction d = currentADIDirection;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                d = ADI_Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                d = ADI_Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                d = ADI_Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                d = ADI_Direction.RIGHT;
                break;
            case KeyEvent.VK_SPACE:
                pause();
                break;
            case KeyEvent.VK_ENTER:
                if (!gameStarted)
                    startNewGame();
                break;
            case KeyEvent.VK_ESCAPE:
                if (gameStarted)
                    stop();
                break;
            default:
                d = currentADIDirection;
                break;
        }

        // return if the new direction is the same or reverse direction
        if (d == currentADIDirection || d == adi_snake.reverseDirection(currentADIDirection))
            return;
        else {
            // this would response faster to user
            // actions instead of waiting for timer
            // to take the step
            // and make the timer step
            // if it's in the same direction only
            adi_snake.setDirection(d);
            adi_snake.step();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {
        countTimer.stop();
        gameTimer.stop();
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

class CustomPanel extends JPanel {


    @Override
    public Dimension getMinimumSize() {
        return new Dimension(100, 30);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 30);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(400, 30);
    }

    public CustomPanel(Color c, JLabel jLabel) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(Color.black, 1)));
        add(jLabel, JPanel.CENTER_ALIGNMENT);
        setBackground(c);
    }
}