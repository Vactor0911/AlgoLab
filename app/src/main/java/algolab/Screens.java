package algolab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Screens {
}

/**
 * 학습하기 메뉴
 */
class LearningScreen extends JPanel {
    private ComboBox comboAlgorithm = new ComboBox();
    private Button btnLearn = new Button("실습하기");
    private JTabbedPane tabLearn = new JTabbedPane();

    JScrollPane tabLearnScroll = new JScrollPane(tabLearn); // 스크롤 패널

    // 탭 패널에 글씨 세팅해 줄 라벨
    private JLabel definitionLabel = new JLabel(""); // 정의
    private JLabel viewCodeLabel = new JLabel("Select Code"); // 코드 보기
    private JLabel timeComplexityLabel = new JLabel(""); // 시간 복잡도

    // View Code 안에 추가 할 콤보박스
    private ComboBox comboViewCode = new ComboBox();

    // 정의 배열 읽어오기
    Variable var = new Variable();

    public LearningScreen() {
        // 초기화
        comboAlgorithm.addItems(new String[][] {
                { "버블 정렬", "" },
                { "선택 정렬", "" },
                { "삽입 정렬", "" },
                { "퀵 정렬", "" },
                { "병합 정렬", "" }
        });
        comboViewCode.addItems(new String[][] {
            { "의사코드", "" },
            { "C", "" },
            { "C++", "" },
            { "Java", "" },
            { "Python", "" }
        });

        // 화면 구성
        setLayout(new GridBagLayout());

        // 탭 패널에 탭 추가
        tabLearn.addTab("정의", definitionLabel);
        tabLearn.addTab("코드", new JPanel(new BorderLayout()));
        tabLearn.addTab("시간 복잡도", timeComplexityLabel);

        // 1행
        add(comboAlgorithm, GbcFactory.createGbc(0, 0, 0.5d, 0.1d));
        add(new JLabel(""), GbcFactory.createGbc(1, 0, 0.05d, 0.1d));
        add(btnLearn, GbcFactory.createGbc(2, 0, 0.1d, 0.1d));
        add(new JLabel(""), GbcFactory.createGbc(3, 0, 0.05, 0.1));
        add(new JLabel(""), GbcFactory.createGbc(4, 0, 0.3d, 0.1));

        // 2행
        add(new JLabel(""), GbcFactory.createGbc(0, 1, 1.0d, 0.05d, 5, 1));

        // 3행
        add(tabLearnScroll, GbcFactory.createGbc(0, 2, 0.65d, 0.85d, 3, 2));
        add(new JLabel(""), GbcFactory.createGbc(3, 2, 0.05d, 0.85d, 1, 2));
        add(new JLabel("table"), GbcFactory.createGbc(4, 2, 0.05d, 0.4d));

        // 4행
        add(new JLabel("graph"), GbcFactory.createGbc(4, 3, 0.3d, 0.45d));

        // 학습하기 버튼 리스너 구현
        btnLearn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedAlgo = ((String[])comboAlgorithm.getSelectedItem())[0]; // 알고리즘 콤보박스에서 선택된 아이템
                definitionLabel.setText(selectedAlgo.toString());
                switch (selectedAlgo) {
                    case "버블 정렬":
                        definitionLabel.setText(var.algorithm[0]);
                        timeComplexityLabel.setText("버블 정렬의 시간 복잡도");
                        break;
                    case "선택 정렬":
                        definitionLabel.setText(var.algorithm[1]);
                        timeComplexityLabel.setText("선택 정렬의 시간 복잡도: ?");
                        break;
                    case "삽입 정렬":
                        definitionLabel.setText(var.algorithm[2]);
                        timeComplexityLabel.setText("삽입 정렬 시간 복잡도: ?");
                        break;
                    case "퀵 정렬":
                        definitionLabel.setText(var.algorithm[3]);
                        timeComplexityLabel.setText("퀵 정렬 시간 복잡도: ?");
                        break;
                    case "병합 정렬":
                        definitionLabel.setText(var.algorithm[4]);
                        timeComplexityLabel.setText("병합 정렬 시간 복잡도: ?");
                        break;
                }

            }
        });

        // View Code 탭 마우스 리스너 추가
        tabLearn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = tabLearn.getSelectedIndex();
                if (tabLearn.getTitleAt(selectedIndex).equals("코드")) {
                    JPanel viewCodePanel = (JPanel) tabLearn.getComponentAt(selectedIndex);
                    if (viewCodePanel.getComponentCount() == 0) { // 콤보박스가 아직 추가되지 않았을 때만 추가
                        JScrollPane scrollPane = new JScrollPane(viewCodeLabel); // 라벨을 감싸는 스크롤 추가
                        viewCodePanel.add(comboViewCode, BorderLayout.NORTH);
                        viewCodePanel.add(scrollPane, BorderLayout.CENTER); // 스크롤 패널 추가
                        viewCodePanel.revalidate();
                        viewCodePanel.repaint();
                    }
                }
            }
        });
        // View Code의 콤보박스 리스너 추가
        comboViewCode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedAlgo = ((String[])comboAlgorithm.getSelectedItem())[0]; // 알고리즘 콤보박스에서 선택된 아이템
                    String selectedviewCode = ((String[])comboViewCode.getSelectedItem())[0]; // View Code 콤보박스에서 선택된 아이템
                    switch (selectedAlgo) {
                        case "버블 정렬":
                            switch (selectedviewCode) {
                                case "C":
                                    viewCodeLabel.setText("버블 정렬의 C 코드");
                                    break;
                                case "C++":
                                    viewCodeLabel.setText("버블 정렬의 C++ 코드");
                                    break;
                                case "Java":
                                    viewCodeLabel.setText("버블 정렬의 Java를 Java봐!");
                                    break;
                                case "의사코드":
                                    viewCodeLabel.setText("버블 정렬의 의사코드");
                                    break;
                            }
                            break;
                        case "선택 정렬":
                            switch (selectedviewCode) {
                                case "C":
                                    viewCodeLabel.setText("선택 정렬의 C 코드");
                                    break;
                                case "C++":
                                    viewCodeLabel.setText("선택 정렬의 C++ 코드");
                                    break;
                                case "Java":
                                    viewCodeLabel.setText("선택 정렬의 Java를 Java봐!");
                                    break;
                                case "의사코드":
                                    viewCodeLabel.setText("선택 정렬의 의사코드");
                                    break;
                            }
                            break;
                        case "삽입 정렬":
                            switch (selectedviewCode) {
                                case "C":
                                    viewCodeLabel.setText("삽입 정렬의 C 코드");
                                    break;
                                case "C++":
                                    viewCodeLabel.setText("삽입 정렬의 C++ 코드");
                                    break;
                                case "Java":
                                    viewCodeLabel.setText("삽입 정렬의 Java를 Java봐!");
                                    break;
                                case "의사코드":
                                    viewCodeLabel.setText("삽입 정렬의 의사코드");
                                    break;
                            }
                            break;
                        case "퀵 정렬":
                            switch (selectedviewCode) {
                                case "C":
                                    viewCodeLabel.setText("퀵 정렬의 C 코드");
                                    break;
                                case "C++":
                                    viewCodeLabel.setText("퀵 정렬의 C++ 코드");
                                    break;
                                case "Java":
                                    viewCodeLabel.setText("퀵 정렬의 Java를 Java봐!");
                                    break;
                                case "의사코드":
                                    viewCodeLabel.setText("퀵 정렬의 의사코드");
                                    break;
                            }
                            break;
                        case "병합 정렬":
                            switch (selectedviewCode) {
                                case "C":
                                    viewCodeLabel.setText("병합 정렬의 C 코드");
                                    break;
                                case "C++":
                                    viewCodeLabel.setText("병합 정렬의 C++ 코드");
                                    break;
                                case "Java":
                                    viewCodeLabel.setText("병합 정렬의 Java를 Java봐!");
                                    break;
                                case "의사코드":
                                    viewCodeLabel.setText("병합 정렬의 의사코드");
                                    break;
                            }
                            break;
                    }
                }
            }
        });

    } // 생성자
} // LearningScreen 클래스

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
        // 초기화
        comboAlgorithm.addItems(new String[][] {
                { "버블 정렬", "" },
                { "선택 정렬", "" },
                { "삽입 정렬", "" },
                { "퀵 정렬", "" },
                { "병합 정렬", "" }
        });
        graph.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // 화면 구성
        setLayout(new GridBagLayout());

        // 1행
        add(comboAlgorithm, GbcFactory.createGbc(0, 0, 0.45d, 0.1d));
        add(new JLabel(""), GbcFactory.createGbc(1, 0, 0.1d, 0.1d));
        add(new JLabel(""), GbcFactory.createGbc(2, 0, 0.05d, 0.1d));
        add(btnLearn, GbcFactory.createGbc(3, 0, 0.2d, 0.1d));
        add(new JLabel(""), GbcFactory.createGbc(4, 0, 0.2d, 0.1d));

        // 2행
        add(new JLabel(""), GbcFactory.createGbc(0, 1, 1.0d, 0.05d, 5, 1));

        // 3행
        add(graph, GbcFactory.createGbc(0, 2, 0.55d, 0.85d, 2, 2));
        add(new JLabel(""), GbcFactory.createGbc(2, 2, 0.05d, 0.85d, 1, 2));
        add(listBoxArray, GbcFactory.createGbc(3, 2, 0.4d, 0.75d, 2, 1));

        // 4행
        add(controlPanel, GbcFactory.createGbc(3, 3, 0.04d, 0.1d, 2, 1));
    } // 생성자
} // PracticeScreen 클래스

/**
 * 퀴즈 시작 메뉴
 */
class QuizStartScreen extends JPanel {
    private JLabel lblQuiz = new JLabel("Quiz", SwingConstants.CENTER);
    private Button btnStart = new Button("Start");

    public QuizStartScreen() {
        setLayout(new GridBagLayout());
        // 1행
        add(new JLabel(""), GbcFactory.createGbc(0, 0, 1d, 0.3d, 3, 1));

        // 2행
        add(new JLabel(""), GbcFactory.createGbc(0, 1, 0.33d, 0.2d));
        add(lblQuiz, GbcFactory.createGbc(1, 1, 0.34d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(2, 1, 0.33d, 0.2d));

        // 3행
        add(new JLabel(""), GbcFactory.createGbc(0, 2, 1d, 0.3d, 3, 1));

        // 4행
        add(new JLabel(""), GbcFactory.createGbc(0, 3, 0.33d, 0.2d));
        add(btnStart, GbcFactory.createGbc(1, 3, 0.34d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(2, 3, 0.33d, 0.2d));

        // 5행
        add(new JLabel(""), GbcFactory.createGbc(0, 4, 1d, 0.3d, 3, 1));
    } // 생성자
} // QuizStartScreen 클래스

/**
 * 퀴즈 풀이 메뉴
 */
class QuizScreen extends JPanel {
    private JRadioButton jRadioButton1 = new JRadioButton();
    private JRadioButton jRadioButton2 = new JRadioButton();
    private JButton jButton = new JButton("Click");

    public QuizScreen() {
        setLayout(new GridBagLayout());

        // 1행
        add(new JLabel("1. bubble"), GbcFactory.createGbc(0, 0, 0.16d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(1, 0, 0.16d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(2, 0, 0.16d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(3, 0, 0.16d, 0.2d));
        add(new JLabel("1/5 문제"), GbcFactory.createGbc(4, 0, 0.16d, 0.2d));

        // 2행
        add(new JLabel(""), GbcFactory.createGbc(0, 1, 0.33d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(1, 1, 0.33d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(2, 1, 0.33d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(3, 1, 0.33d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(4, 1, 0.33d, 0.2d));

        // 3행
        add(new JLabel(""), GbcFactory.createGbc(0, 2, 1d, 0.3d, 3, 1));
        add(new JLabel(""), GbcFactory.createGbc(1, 2, 1d, 0.3d, 3, 1));
        add(new JLabel(""), GbcFactory.createGbc(3, 2, 1d, 0.3d, 3, 1));
        add(new JLabel(""), GbcFactory.createGbc(4, 2, 1d, 0.3d, 3, 1));

        // 4행
        add(new JLabel(""), GbcFactory.createGbc(0, 3, 0.33d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(1, 3, 0.34d, 0.2d));
        add(new JLabel(""), GbcFactory.createGbc(2, 3, 0.33d, 0.2d));

        // 5행
        add(new JLabel(""), GbcFactory.createGbc(0, 4, 1d, 0.3d, 3, 1));
        jRadioButton1.setText("Test");
    } // 생성자

    @Override
    public void add(Component component, Object constraints) {
        super.add(component, constraints);
        ((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
} // QuizScreen 클래스

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
    public QuizTODO() {

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
    } // 생성자
} // QuizTODO 클래스