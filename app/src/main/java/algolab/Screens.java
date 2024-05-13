package algolab;

import java.awt.*;
import javax.swing.*;

public class Screens {
}

class LearningScreen extends JPanel {
    // SeonJin
    private String[] algo = { "Selection Sort", "Insertion Sort", "Bubble Sort", "Quick Sort", "Merge Sort" };
    private JComboBox algoCombo = new JComboBox<>(algo);

    private JButton btnLearn = new JButton("Go Learning");

    private JTabbedPane tabLearn = new JTabbedPane();

    public LearningScreen() {
        setLayout(new GridBagLayout());

        tabLearn.addTab("definition", new JLabel(""));
        tabLearn.addTab("View Code", new JLabel(""));
        tabLearn.addTab("Time Complexity", new JLabel(""));

        // 아!!!!!!!!!!!!!!!!!왜 안되냐고!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // 1행
        add(algoCombo, GbcFactory.createGbc(0, 0, 0.5d, 0.1d, 1, 1));
        add(new JLabel(""), GbcFactory.createGbc(1, 0, 0.05d, 0.1d, 1, 1));
        add(btnLearn, GbcFactory.createGbc(2, 0, 0.1d, 0.1d, 1, 1));
        add(new JLabel(""), GbcFactory.createGbc(3, 0, 0.05, 0.1, 1, 1));
        add(new JLabel(""), GbcFactory.createGbc(4, 0, 0.3d, 0.1, 1, 1));

        // 2행
        add(new JLabel(""), GbcFactory.createGbc(0, 1, 0.65d, 0.05d, 3, 1));
        add(new JLabel(""), GbcFactory.createGbc(3, 1, 0.05d, 0.05d, 1, 1));
        add(new JLabel(""), GbcFactory.createGbc(4, 1, 0.3d, 0.05d, 1, 1));

        // 3행
        add(tabLearn, GbcFactory.createGbc(0, 2, 0.65d, 0.85d, 3, 2));
        add(new JLabel(""), GbcFactory.createGbc(3, 2, 0.05d, 0.4d, 1, 1));
        add(new JLabel("table"), GbcFactory.createGbc(4, 2, 0.05d, 0.4d, 1, 1));

        // 4행
        add(new JLabel(""), GbcFactory.createGbc(3, 3, 0.05d, 0.5d, 1, 1));
        add(new JLabel("graph"), GbcFactory.createGbc(4, 3, 0.3d, 0.45d, 1, 1));

        // 사진은 넣으면 이상하게 변해서 Label로 대체했음
    }
}