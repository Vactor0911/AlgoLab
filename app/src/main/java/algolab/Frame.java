package algolab;

import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*; 

public class Frame extends JFrame {
    private JPanel pnlMenu = new JPanel();
    private TestPanel pnlContent = new TestPanel();

    private Button btnLearning = new Button("Learning");
    private Button btnTraining = new Button("Training");
    private Button btnQuiz = new Button("Quiz");
    JRadioButton jRadioButton1; 
    JRadioButton jRadioButton2; 
    JButton jButton;

    // Yerim2

    public Frame() {
        setTitle("AlgoLab");
        setSize(600, 400);
        setMinimumSize( new Dimension(600, 400) );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout( new GridBagLayout() );

        jRadioButton1 = new JRadioButton();
        jRadioButton2 = new JRadioButton(); 
        jButton = new JButton("Click");

        //메뉴 패널
        pnlMenu.setLayout( new GridLayout(3, 1) );
        pnlMenu.setBackground(Color.LIGHT_GRAY);
        pnlMenu.add(btnLearning);
        pnlMenu.add(btnTraining);
        pnlMenu.add(btnQuiz);
        add( pnlMenu, GbcFactory.createGbc(0, 0, 0.2d, 1.0d) );      

        //내용 패널
        pnlContent.setLayout( new GridBagLayout() );

        // 1행
        pnlContent.add(new JLabel("1. bubble"), GbcFactory.createGbc(0, 0, 0.16d, 0.2d));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(1, 0, 0.16d, 0.2d));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(2,0, 0.16d, 0.2d));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(3, 0, 0.16d, 0.2d));
        pnlContent.add(new JLabel("1/5 문제"), GbcFactory.createGbc(4, 0, 0.16d, 0.2d));
        
        //2행
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(0, 1, 0.33d, 0.2d));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(1, 1, 0.33d, 0.2d));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(2, 1, 0.33d, 0.2d));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(3, 1, 0.33d, 0.2d));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(4, 1, 0.33d, 0.2d));  
    

        //3행
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(0, 2, 1d, 0.3d, 3, 1));
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(1, 2, 1d, 0.3d, 3, 1));    
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(3, 2, 1d, 0.3d, 3, 1));    
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(4, 2, 1d, 0.3d, 3, 1));    
    

        //4행
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(0, 3, 0.33d, 0.2d));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(1, 3,0.34d, 0.2d));  
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(2, 3, 0.33d, 0.2d));

        //5행
        pnlContent.add(new JLabel(""), GbcFactory.createGbc(0, 4, 1d, 0.3d, 3, 1)); 
        jRadioButton1.setText("Test");
        
        add( pnlContent, GbcFactory.createGbc(1, 0, 0.8d, 1.0d) );
        setVisible(true);
    }
    
    private class TestPanel extends JPanel {
        @Override
        public void add(Component component, Object constraints) {
            super.add(component, constraints);
            ((JComponent)component).setBorder( BorderFactory.createLineBorder(Color.BLACK) );
        }
    }
}