package algolab;

import java.awt.*;
import javax.swing.*;

public class Screens {}

/**
 * 학습하기 메뉴
 */
class LearningScreen extends JPanel {
    private ComboBox comboAlgorithm = new ComboBox();
    private Button btnLearn = new Button("실습하기");
    private JTabbedPane tabLearn = new JTabbedPane();

    public LearningScreen() {
        //초기화
        comboAlgorithm.addItems(new String[][] {
            {"버블 정렬", ""},
            {"선택 정렬", ""},
            {"삽입 정렬", ""},
            {"퀵 정렬", ""},
            {"병합 정렬", ""}
        });

        //화면 구성
        setLayout( new GridBagLayout() );

        for (String str : new String[] {"정의", "코드", "시간 복잡도"}) {
            tabLearn.addTab( str, new JLabel() );
        }

        // 1행
        add( comboAlgorithm, GbcFactory.createGbc(0, 0, 0.5d, 0.1d) );
        add( new JLabel(""), GbcFactory.createGbc(1, 0, 0.05d, 0.1d) );
        add( btnLearn, GbcFactory.createGbc(2, 0, 0.1d, 0.1d) );
        add( new JLabel(""), GbcFactory.createGbc(3, 0, 0.05, 0.1) );
        add( new JLabel(""), GbcFactory.createGbc(4, 0, 0.3d, 0.1) );

        // 2행
        add( new JLabel(""), GbcFactory.createGbc(0, 1, 1.0d, 0.05d, 5, 1) );

        // 3행
        add( tabLearn, GbcFactory.createGbc(0, 2, 0.65d, 0.85d, 3, 2) );
        add( new JLabel(""), GbcFactory.createGbc(3, 2, 0.05d, 0.85d, 1, 2) );
        add( new JLabel("table"), GbcFactory.createGbc(4, 2, 0.05d, 0.4d) );

        // 4행
        add( new JLabel("graph"), GbcFactory.createGbc(4, 3, 0.3d, 0.45d) );
    } //생성자
} //LearningScreen 클래스

/**
 * 실습하기 메뉴
 */
class PracticeScreen extends JPanel {
    private ComboBox comboAlgorithm = new ComboBox();
    private Button btnLearn = new Button("학습하기");
    private JLabel graph = new JLabel("막대 그래프");
    private ListBox listBoxArray = new ListBox(1);
    private JLabel controlPanel = new JLabel("조작 패널");

    public PracticeScreen() {
        //초기화
        comboAlgorithm.addItems(new String[][] {
            {"버블 정렬", ""},
            {"선택 정렬", ""},
            {"삽입 정렬", ""},
            {"퀵 정렬", ""},
            {"병합 정렬", ""}
        });
        graph.setBorder( BorderFactory.createLineBorder(Color.BLACK) );
        controlPanel.setBorder( BorderFactory.createLineBorder(Color.BLACK) );

        //화면 구성
        setLayout( new GridBagLayout() );

        // 1행
        add( comboAlgorithm, GbcFactory.createGbc(0, 0, 0.45d, 0.1d) );
        add( new JLabel(""), GbcFactory.createGbc(1, 0, 0.1d, 0.1d) );
        add( new JLabel(""), GbcFactory.createGbc(2, 0, 0.05d, 0.1d) );
        add( btnLearn, GbcFactory.createGbc(3, 0, 0.2d, 0.1d) );
        add( new JLabel(""), GbcFactory.createGbc(4, 0, 0.2d, 0.1d) );

        // 2행
        add( new JLabel(""), GbcFactory.createGbc(0, 1, 1.0d, 0.05d, 5, 1) );
        
        // 3행
        add( graph, GbcFactory.createGbc(0, 2, 0.55d, 0.85d,2,2) );
        add( new JLabel(""), GbcFactory.createGbc(2, 2, 0.05d, 0.85d, 1, 2) );
        add( listBoxArray, GbcFactory.createGbc(3, 2, 0.4d, 0.75d,2,1) );

        //4행
        add( controlPanel, GbcFactory.createGbc(3, 3, 0.04d, 0.1d, 2, 1) );
    } //생성자
} //PracticeScreen 클래스

/**
 * 퀴즈 시작 메뉴
 */
class QuizStartScreen extends JPanel {
    private JLabel lblQuiz = new JLabel("Quiz", SwingConstants.CENTER);
    private Button btnStart = new Button("Start");

    public QuizStartScreen() {
        setLayout( new GridBagLayout() );
        // 1행
        add(new JLabel(""), GbcFactory.createGbc(0, 0, 1d, 0.3d, 3, 1));
                    
        //2행
        add(new JLabel(""), GbcFactory.createGbc(0, 1, 0.33d, 0.2d));  
        add(lblQuiz, GbcFactory.createGbc(1, 1,0.34d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(2, 1, 0.33d, 0.2d));

        //3행
        add(new JLabel(""), GbcFactory.createGbc(0, 2, 1d, 0.3d, 3, 1));  

        //4행
        add(new JLabel(""), GbcFactory.createGbc(0, 3, 0.33d, 0.2d));  
        add(btnStart, GbcFactory.createGbc(1, 3,0.34d, 0.2d));  
        add(new JLabel(""), GbcFactory.createGbc(2, 3, 0.33d, 0.2d));

        //5행
        add(new JLabel(""), GbcFactory.createGbc(0, 4, 1d, 0.3d, 3, 1)); 
    } //생성자
} //QuizStartScreen 클래스

/**
 * 퀴즈 풀이 메뉴
 */
class QuizScreen extends JPanel {
    private JRadioButton jRadioButton1 = new JRadioButton();
    private JRadioButton jRadioButton2 = new JRadioButton(); 
    private JButton jButton = new JButton("Click");

    public QuizScreen() {
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
    } //생성자

    @Override
    public void add(Component component, Object constraints) {
        super.add(component, constraints);
        ((JComponent)component).setBorder( BorderFactory.createLineBorder(Color.BLACK) );
    }
} //QuizScreen 클래스

/**
 * 라디오 버튼 연습
 */
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
	} //생성자
} //QuizTODO 클래스
