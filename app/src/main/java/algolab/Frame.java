package algolab;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    private JPanel pnlMenu = new JPanel();
    private JPanel pnlContent = new JPanel();

    private Button btnLearning = new Button("Learning");
    private Button btnTraining = new Button("Training");
    private Button btnQuiz = new Button("Quiz");
    
    // SeonJin
    private String[] algo = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Quick Sort", "Merge Sort"};
    private JComboBox algoCombo = new JComboBox<>(algo);

    private JButton btnLearn = new JButton("Go Learning");

    private JTabbedPane tabLearn = new JTabbedPane();

    private JLabel trashLabel = new JLabel("trashLabel");
    private JLabel trashLabel2 = new JLabel("trashLabel2");
    private JLabel trashLabel3 = new JLabel("trashLabel3");


    public Frame() {
        setTitle("AlgoLab");
        setSize(600, 400);
        setMinimumSize( new Dimension(600, 400) );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout( new GridBagLayout() );

        //메뉴 패널
        pnlMenu.setLayout( new GridLayout(3, 1) );
        pnlMenu.setBackground(Color.LIGHT_GRAY);
        pnlMenu.add(btnLearning);
        pnlMenu.add(btnTraining);
        pnlMenu.add(btnQuiz);
        add( pnlMenu, GbcFactory.createGbc(0, 0, 0.2d, 1.0d) );        

        //내용 패널
        pnlContent.setLayout( new GridBagLayout() );
        tabLearn.addTab("definition", new JLabel(""));
        tabLearn.addTab("View Code", new JLabel(""));
        tabLearn.addTab("Time Complexity", new JLabel(""));

        // 아!!!!!!!!!!!!!!!!!왜 안되냐고!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // 1열
        pnlContent.add(algoCombo, GbcFactory.createGbc(0, 0, 0.4d, 0.1d));
        pnlContent.add(tabLearn, GbcFactory.createGbc(0, 1, 0.5d, 0.9d, 2, 2));  
        
        // 2열
        pnlContent.add(btnLearn, GbcFactory.createGbc(1, 0, 0.1d, 0.1d));
        
        // 3열
        pnlContent.add(trashLabel, GbcFactory.createGbc(2, 0, 0.5d, 0.1d));
        pnlContent.add(trashLabel2, GbcFactory.createGbc(2, 1, 0.1d, 0.4d));
        pnlContent.add(trashLabel3, GbcFactory.createGbc(2, 2, 0.1d, 0.5d));
        // 사진은 넣으면 이상하게 변해서 Label로 대체했음

        add( pnlContent, GbcFactory.createGbc(1, 0, 0.8d, 1.0d) );

        setVisible(true);
    }

}
