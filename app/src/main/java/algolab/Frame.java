package algolab;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    private JPanel pnlMenu = new JPanel();
    private JPanel pnlContent = new JPanel();

    private Button btnLearning = new Button("Learning");
    private Button btnTraining = new Button("Training");
    private Button btnQuiz = new Button("Quiz");

    public Frame() {
        setTitle("AlgoLab");
        setSize(600, 400);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());

        // 메뉴 패널
        pnlMenu.setLayout(new GridLayout(3, 1));
        pnlMenu.setBackground(Color.LIGHT_GRAY);
        pnlMenu.add(btnLearning);
        pnlMenu.add(btnTraining);
        pnlMenu.add(btnQuiz);
        add(pnlMenu, GbcFactory.createGbc(0, 0, 0.2d, 1.0d));

        // 내용 패널
        pnlContent.setLayout(new BorderLayout());
        pnlContent.add(new LearningScreen());

        add(pnlContent, GbcFactory.createGbc(1, 0, 0.8d, 1.0d));

        setVisible(true);
    }

}
