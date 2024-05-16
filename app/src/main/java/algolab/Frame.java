package algolab;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    private JPanel pnlMenu = new JPanel();
    private TestPanel pnlContent = new TestPanel();

    private Button btnLearning = new Button("Learning");
    private Button btnTraining = new Button("Training");
    private Button btnQuiz = new Button("Quiz");

    
    // Sukhen
    private String[] algo = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Quick Sort", "Merge Sort"};
    private JComboBox algoCombo = new JComboBox<>(algo);

    private JButton btnLearn = new JButton("Go Learning");

    private JLabel grape = new JLabel("Grape");

    private JTabbedPane tabLearn = new JTabbedPane();

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

        grape.setBorder( BorderFactory.createLineBorder(Color.BLACK));

        // 1행
        pnlContent.add(algoCombo, GbcFactory.createGbc(0, 0, 0.2d, 0.1d,1,1));
        pnlContent.add(new JButton("학습하기"), GbcFactory.createGbc(3, 0, 0.25d, 0.1d, 1, 1));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(2, 0, 0.02d, 0.02d, 1, 1));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(4, 0, 0.4d, 0.1d, 1, 1));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(8, 0, 0.01d, 0.01d, 1, 1));

        // 2행
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(0, 1, 0.25d, 0.08d, 1, 1));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(1, 1, 0.25d, 0.08d, 1,1));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(2, 1, 0.02d, 0.08d, 1, 1));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(3, 1, 0.25d, 0.08d, 1, 1));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(8, 1, 0.01d, 0.08d, 1, 1));
        
        // 3행
        pnlContent.add(grape, GbcFactory.createGbc(0, 2, 0.8d, 0.5d,2,5));
        pnlContent.add(new ListBox(2), GbcFactory.createGbc(3, 2, 0.8d, 0.4d,3,3));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(2, 2, 0.04d, 0.1d, 1, 5));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(8, 2, 0.01d, 1d, 1, 1));

        //4행
        pnlContent.add(new JButton("실행하기"), GbcFactory.createGbc(3, 5, 0.01d, 0.02d, 3, 1));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(8, 3, 0.01d, 0.01d, 1, 1));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(8, 4, 0.01d, 1, 1, 1));

        add( pnlContent, GbcFactory.createGbc(1, 0, 0.8d, 1.0d) );
        setVisible(true);
    }
    @Override
    public void add(Component comp, Object cons) {
    super.add(comp, cons);
    ((JComponent) comp).setBorder( BorderFactory.createLineBorder(Color.BLACK, 1, true) );
}
private class TestPanel extends JPanel {
    @Override
    public void add(Component component, Object constraints) {
        super.add(component, constraints);
        //((JComponent)component).setBorder( BorderFactory.createLineBorder(Color.BLACK) );
    }
}
}
