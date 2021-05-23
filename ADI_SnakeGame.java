import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ADI_SnakeGame extends JFrame {

    private ADI_GameBoard adi_gameBoard;
    private ADI_Snake adi_snake;
    private ADI_Prey adi_prey;

    private JLabel topPlayersScore = new JLabel("TOP PLAYERS SCORE");
    private JLabel currentPlayerScore = new JLabel("CURRENT PLAYER SCORE");
    private JLabel SN = new JLabel("");
    private Image snakeImage = (new ImageIcon("resources/sn.png").getImage());
    private JLabel lblScore;

    public ADI_SnakeGame() {
        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("My Snake Game");

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        initGamePanel();
        initStatusPanel();

        pack();
        setSize(getWidth() + 30, getHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reset();
    }


    public void initGamePanel() {
        // init game panel
        adi_gameBoard = new ADI_GameBoard();
        adi_gameBoard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        add(adi_gameBoard);
    }


    public void initStatusPanel() {
        JPanel newPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(20, 50, 20, 50);


        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        JPanel topPlayersScoreJPanel = new JPanel();
        CustomPanel topPlayersScoreCustomPanel = new CustomPanel(Color.white, topPlayersScore);
        topPlayersScoreJPanel.add(topPlayersScoreCustomPanel);
        newPanel.add(topPlayersScoreJPanel, constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        JPanel currentPlayerScoreJPanel = new JPanel();
        CustomPanel currentPlayerScoreCustomPanel = new CustomPanel(Color.white, currentPlayerScore);
        currentPlayerScoreCustomPanel.add(currentPlayerScoreJPanel);
        newPanel.add(currentPlayerScoreCustomPanel, constraints);


        constraints.gridx = 0;
        constraints.gridy = 2;
//        JPanel snakeImageJPanel = new JPanel();
        ImageIcon icon = new ImageIcon(snakeImage);
        SN = new JLabel(icon);
//        CustomPanel snakeImageCustomPanel = new CustomPanel(Color.white, SN);
//        snakeImageJPanel.add(snakeImageCustomPanel);
        newPanel.add(SN, constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;


        JPanel lblScoreJPanel = new JPanel();
        lblScore = new JLabel("Score: 0");
        CustomPanel lblScoreScoreCustomPanel = new CustomPanel(Color.white, lblScore);
        lblScoreJPanel.add(lblScoreScoreCustomPanel);
        newPanel.add(lblScoreJPanel, constraints);


        constraints.gridx = 0;
        constraints.gridy = 4;
        newPanel.add(new JButton("Quit"), constraints);

        newPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        add(newPanel);
    }


    public void reset() {
        adi_snake = new ADI_Snake();

// move to center
        adi_snake.moveTo(ADI_Config.snakeX, ADI_Config.snakeY);


        adi_prey = new ADI_Prey(ADI_Config.panelWidth, ADI_Config.panelHeight);
        adi_prey.escapeFrom(adi_snake);


        adi_gameBoard.setSnake(adi_snake);
        adi_gameBoard.setPrey(adi_prey);


        setFocusable(true);
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
}

class CustomPanel extends JPanel {


    @Override
    public Dimension getMinimumSize() {
        return new Dimension(100, 40);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 40);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(400, 40);
    }

    public CustomPanel(Color c, JLabel jLabel) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(Color.black, 1)));
        add(jLabel, JPanel.CENTER_ALIGNMENT);
        setBackground(c);
    }
}