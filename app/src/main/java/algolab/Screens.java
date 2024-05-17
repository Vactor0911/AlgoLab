package algolab;

import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*; 

public class Screens {}

class QuizStartPanel extends JPanel {
    public QuizStartPanel() {
        setLayout( new GridBagLayout() );
        // 1행
        add(new JLabel(""), GbcFactory.createGbc(0, 0, 1d, 0.3d, 3, 1));
                    
        //2행
        add(new JLabel(""), GbcFactory.createGbc(0, 1, 0.33d, 0.2d));  
        add(new JLabel("Quiz", SwingConstants.CENTER), GbcFactory.createGbc(1, 1,0.34d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(2, 1, 0.33d, 0.2d));

        //3행
        add(new JLabel(""), GbcFactory.createGbc(0, 2, 1d, 0.3d, 3, 1));  

        //4행
        add(new JLabel(""), GbcFactory.createGbc(0, 3, 0.33d, 0.2d));  
        add(new Button("Start"), GbcFactory.createGbc(1, 3,0.34d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(2, 3, 0.33d, 0.2d));

        //5행
        add(new JLabel(""), GbcFactory.createGbc(0, 4, 1d, 0.3d, 3, 1)); 
    }
}

class QuizPanel extends JPanel {
    private JRadioButton jRadioButton1 = new JRadioButton();
    private JRadioButton jRadioButton2 = new JRadioButton(); 
    private JButton jButton = new JButton("Click");

    public QuizPanel() {
        setLayout( new GridBagLayout() );

        // 1행
        add( new JLabel("1. bubble"), GbcFactory.createGbc(0, 0, 0.16d, 0.2d) );  
        add(new JLabel(""), GbcFactory.createGbc(1, 0, 0.16d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(2,0, 0.16d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(3, 0, 0.16d, 0.2d));
        add(new JLabel("1/5 문제"), GbcFactory.createGbc(4, 0, 0.16d, 0.2d));

        //2행
        add(new JLabel(""), GbcFactory.createGbc(0, 1, 0.33d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(1, 1, 0.33d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(2, 1, 0.33d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(3, 1, 0.33d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(4, 1, 0.33d, 0.2d));  


        //3행
        add(new JLabel(""), GbcFactory.createGbc(0, 2, 1d, 0.3d, 3, 1));
        add(new JLabel(""), GbcFactory.createGbc(1, 2, 1d, 0.3d, 3, 1));    
        add(new JLabel(""), GbcFactory.createGbc(3, 2, 1d, 0.3d, 3, 1));    
        add(new JLabel(""), GbcFactory.createGbc(4, 2, 1d, 0.3d, 3, 1));    


        //4행
        add(new JLabel(""), GbcFactory.createGbc(0, 3, 0.33d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(1, 3,0.34d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(2, 3, 0.33d, 0.2d));

        //5행
        add(new JLabel(""), GbcFactory.createGbc(0, 4, 1d, 0.3d, 3, 1)); 
        jRadioButton1.setText("Test");
    }

    @Override
    public void add(Component component, Object constraints) {
        super.add(component, constraints);
        ((JComponent)component).setBorder( BorderFactory.createLineBorder(Color.BLACK) );
    }
}

class QuizTODO extends JPanel { 

	// Declaration of object of JRadioButton class. 
	JRadioButton jRadioButton1; 

	// Declaration of object of JRadioButton class. 
	JRadioButton jRadioButton2; 

	// Declaration of object of JButton class. 
	JButton jButton; 

	// Declaration of object of ButtonGroup class. 
	ButtonGroup G1; 

	// Declaration of object of JLabel class. 
	JLabel L1; 

	// Constructor of Demo class. 
	public QuizTODO()
	{ 

		// Setting layout as null of JFrame. 
		this.setLayout(null); 

		// Initialization of object of "JRadioButton" class. 
		jRadioButton1 = new JRadioButton(); 

		// Initialization of object of "JRadioButton" class. 
		jRadioButton2 = new JRadioButton(); 

		// Initialization of object of "JButton" class. 
		jButton = new JButton("Click"); 

		// Initialization of object of "ButtonGroup" class. 
		G1 = new ButtonGroup(); 

		// Initialization of object of " JLabel" class. 
		L1 = new JLabel("Qualification"); 

		// setText(...) function is used to set text of radio button. 
		// Setting text of "jRadioButton2". 
		jRadioButton1.setText("Under-Graduate"); 

		// Setting text of "jRadioButton4". 
		jRadioButton2.setText("Graduate"); 

		// Setting Bounds of "jRadioButton2". 
		jRadioButton1.setBounds(120, 30, 120, 50); 

		// Setting Bounds of "jRadioButton4". 
		jRadioButton2.setBounds(250, 30, 80, 50); 

		// Setting Bounds of "jButton". 
		jButton.setBounds(125, 90, 80, 30); 

		// Setting Bounds of JLabel "L2". 
		L1.setBounds(20, 30, 150, 50); 

		// "this" keyword in java refers to current object. 
		// Adding "jRadioButton2" on JFrame. 
		this.add(jRadioButton1); 

		// Adding "jRadioButton4" on JFrame. 
		this.add(jRadioButton2); 

		// Adding "jButton" on JFrame. 
		this.add(jButton); 

		// Adding JLabel "L2" on JFrame. 
		this.add(L1); 

		// Adding "jRadioButton1" and "jRadioButton3" in a Button Group "G2". 
		G1.add(jRadioButton1); 
		G1.add(jRadioButton2); 
	} 
}