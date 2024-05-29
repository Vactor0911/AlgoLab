package algolab;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.List;
import algolab.Frame.ScreenList;

public class Screens {
}

/**
 * 학습하기 메뉴
 */
class LearningScreen extends JPanel {
    private ComboBox comboAlgorithm = new ComboBox();
    private Button btnLearn = new Button("실습하기");
    private TabbedPane tabLearn = new TabbedPane();

    // 탭 패널에 추가 할 패널
    private JPanel definitionTabPanel = new JPanel();
    private JPanel viewCodeTabPanel = new JPanel();
    private JPanel timeComplexityTabPanel = new JPanel();

    // 탭 패널에 글씨 세팅해 줄 라벨
    private JLabel definitionLabel = new JLabel(""); // 정의
    private JLabel viewCodeLabel = new JLabel(""); // 코드 보기
    private JLabel timeComplexityLabel = new JLabel(""); // 시간 복잡도

    // View Code 안에 추가 할 콤보박스
    private ComboBox comboViewCode = new ComboBox();
    // View Code 안에 코드를 보여 줄 패널
    private JPanel viewCodePanel = new JPanel();

    // 차트 클래스
    Chart c = new Chart(3, 1);

    // 그래프 테스트
    SortingAnimation graph = new SortingAnimation(new int[] {5, 2, 4, 1, 3});

    // 구조체 접근을 위한 변수 초기화
    private Algorithms.Algorithm algo = Algorithms.BUBBLE_SORT;

    // 실습하기 버튼 구현 관련 변수
    private JPanel pnlContent;

    public LearningScreen(JPanel pnlContent) {
        this.pnlContent = pnlContent;
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

        // 탭 패널 초기화 및 스크롤 추가
        definitionTabPanel.setLayout(new BorderLayout());
        definitionTabPanel.add(definitionLabel);
        MyScrollPane definitionScroll = new MyScrollPane(definitionTabPanel);

        viewCodeTabPanel.setLayout(new BorderLayout());

        timeComplexityTabPanel.setLayout(new BorderLayout());
        timeComplexityTabPanel.add(timeComplexityLabel);
        MyScrollPane timeComplexityScroll = new MyScrollPane(timeComplexityTabPanel);

        tabLearn.addTab("정의", definitionScroll);
        tabLearn.addTab("코드", viewCodeTabPanel);
        tabLearn.addTab("시간 복잡도", timeComplexityScroll);

        // # ************** 스크롤 배경 색 및 스크롤 색상 변경 기능 ***************** #
        //tabLearnScroll.getVerticalScrollBar().setBackground(Color.BLACK);
        //tabLearnScroll.getHorizontalScrollBar().setBackground(Color.BLACK);
        //https://stackoverflow.com/questions/16373459/java-jscrollbar-design

        // 콤보 박스 초기화
        comboAlgorithm.setSelectedIndex(0);
        comboViewCode.setSelectedIndex(0);

        // 버블 정렬 화면으로 초기화
        definitionLabel.setText(CodeParser.parseCode(Algorithms.BUBBLE_SORT.DEFINITION));
        timeComplexityLabel.setText(Algorithms.BUBBLE_SORT.TIME_COMPLEXITY.BEST);
        c.put(new String[][] { { "O(n²)" }, { "O(n)" }, { "O(n²)" } });

        // 탭 패널 초기화
        viewCodeLabel.setText(CodeParser.parseCode(algo.CODE.PSEUDO));

        // 1행
        add(comboAlgorithm, GbcFactory.createGbc(0, 0, 0.5d, 0.1d));
        add(new JLabel(""), GbcFactory.createGbc(1, 0, 0.05d, 0.1d));
        add(btnLearn, GbcFactory.createGbc(2, 0, 0.1d, 0.1d));
        add(new JLabel(""), GbcFactory.createGbc(3, 0, 0.05, 0.1));
        add(new JLabel(""), GbcFactory.createGbc(4, 0, 0.3d, 0.1));

        // 2행
        add(new JLabel(""), GbcFactory.createGbc(0, 1, 1.0d, 0.05d, 5, 1));

        // 3행
        add(tabLearn, GbcFactory.createGbc(0, 2, 0.65d, 0.85d, 3, 2));
        add(new JLabel(""), GbcFactory.createGbc(3, 2, 0.05d, 0.85d, 1, 2));
        add(c, GbcFactory.createGbc(4, 2, 0.3d, 0.4d));

        // 4행
        add(graph, GbcFactory.createGbc(4, 3, 0.3d, 0.5d));

        // 차트 행 제목 초기화
        c.setRowTitle(new String[] { "최선", "최악", "평균" });

        // 실습하기 버튼 리스너 구현
        btnLearn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int comboItemIndex = comboAlgorithm.getSelectedIndex();

                CardLayout cardLayout = (CardLayout) pnlContent.getLayout();
                cardLayout.show(pnlContent, ScreenList.PRACTICE_SCREEN);

                ((PracticeScreen) pnlContent.getComponent(2)).setComboIndex(comboItemIndex);
            }
        });

        // 콤보박스 내용이 바뀌면 라벨 업데이트
        comboAlgorithm.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedAlgo = ((String[]) comboAlgorithm.getSelectedItem())[0];
                    switch (selectedAlgo) {
                        case "버블 정렬":
                            definitionLabel.setText(CodeParser.parseCode(Algorithms.BUBBLE_SORT.DEFINITION));
                            timeComplexityLabel.setText(Algorithms.BUBBLE_SORT.TIME_COMPLEXITY.BEST);
                            c.put(new String[][] { { "O(n²)" }, { "O(n)" }, { "O(n²)" } });
                            comboViewCode.setSelectedIndex(0);
                            algo = Algorithms.BUBBLE_SORT;
                            viewCodeLabel.setText(CodeParser.parseCode(algo.CODE.PSEUDO));
                            break;
                        case "선택 정렬":
                            definitionLabel.setText(CodeParser.parseCode(Algorithms.SELECTION_SORT.DEFINITION));
                            timeComplexityLabel.setText(Algorithms.SELECTION_SORT.TIME_COMPLEXITY.BEST);
                            c.put(new String[][] { { "O(n²)" }, { "O(n²)" }, { "O(n²)" } });
                            comboViewCode.setSelectedIndex(0);
                            algo = Algorithms.SELECTION_SORT;
                            viewCodeLabel.setText(CodeParser.parseCode(algo.CODE.PSEUDO));
                            break;
                        case "삽입 정렬":
                            definitionLabel.setText(CodeParser.parseCode(Algorithms.INSERTION_SORT.DEFINITION));
                            timeComplexityLabel.setText(Algorithms.INSERTION_SORT.TIME_COMPLEXITY.BEST);
                            c.put(new String[][] { { "O(n²)" }, { "O(n²)" }, { "O(n²)" } });
                            comboViewCode.setSelectedIndex(0);
                            algo = Algorithms.INSERTION_SORT;
                            viewCodeLabel.setText(CodeParser.parseCode(algo.CODE.PSEUDO));
                            break;
                        case "퀵 정렬":
                            definitionLabel.setText(CodeParser.parseCode(Algorithms.QUICK_SORT.DEFINITION));
                            timeComplexityLabel.setText(Algorithms.QUICK_SORT.TIME_COMPLEXITY.BEST);
                            c.put(new String[][] { { "O(n²)" }, { "O(nlogn)" }, { "O(nlogn)" } });
                            comboViewCode.setSelectedIndex(0);
                            algo = Algorithms.QUICK_SORT;
                            viewCodeLabel.setText(CodeParser.parseCode(algo.CODE.PSEUDO));
                            break;
                        case "병합 정렬":
                            definitionLabel.setText(CodeParser.parseCode(Algorithms.MERGE_SORT.DEFINITION));
                            timeComplexityLabel.setText(Algorithms.MERGE_SORT.TIME_COMPLEXITY.BEST);
                            c.put(new String[][] { { "O(nlogn)" }, { "O(nlogn)" }, { "O(nlogn)" } });
                            comboViewCode.setSelectedIndex(0);
                            algo = Algorithms.MERGE_SORT;
                            viewCodeLabel.setText(CodeParser.parseCode(algo.CODE.PSEUDO));
                            break;
                    }
                }
            }
        });

        // 코드 탭 리스너 추가
        tabLearn.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabLearn.getSelectedIndex();
                if (!tabLearn.getTitleAt(selectedIndex).equals("코드")) {
                    return;
                }
                JPanel viewCodeTabPanel = (JPanel) tabLearn.getComponentAt(selectedIndex);
                if (viewCodeTabPanel.getComponentCount() != 0) {
                    return;
                }
                // 콤보박스가 아직 추가되지 않았을 때만 추가
                viewCodePanel.setLayout(new BorderLayout());
                viewCodePanel.add(viewCodeLabel);
                MyScrollPane viewCodeScroll = new MyScrollPane(viewCodePanel); // 패널을 감싸는 스크롤 추가
                viewCodeScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // 가로 스크롤이 항상 보이게
                viewCodeTabPanel.setLayout(new GridBagLayout()); // 콤보박스 크기 조정을 위해 그리드백 레이아웃 적용
                viewCodeTabPanel.add(comboViewCode, GbcFactory.createGbc(0, 0, 1.0d, 0.12d));
                viewCodeTabPanel.add(viewCodeScroll, GbcFactory.createGbc(0, 1, 1.0d, 0.88d));
                viewCodeTabPanel.revalidate();
                viewCodeTabPanel.repaint();
            }
        });

        // 코드 탭 콤보박스 리스너 추가
        comboViewCode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED) {
                    return;
                }
                String selectedAlgo = ((String[]) comboAlgorithm.getSelectedItem())[0]; // 알고리즘 콤보박스에서 선택된 아이템
                String selectedviewCode = ((String[]) comboViewCode.getSelectedItem())[0]; // View Code 콤보박스에서 선택된 아이템
                if (selectedAlgo.equals(Algorithms.BUBBLE_SORT.NAME)) {
                    algo = Algorithms.BUBBLE_SORT;
                } else if (selectedAlgo.equals(Algorithms.SELECTION_SORT.NAME)) {
                    algo = Algorithms.SELECTION_SORT;
                } else if (selectedAlgo.equals(Algorithms.INSERTION_SORT.NAME)) {
                    algo = Algorithms.INSERTION_SORT;
                } else if (selectedAlgo.equals(Algorithms.QUICK_SORT.NAME)) {
                    algo = Algorithms.QUICK_SORT;
                } else if (selectedAlgo.equals(Algorithms.MERGE_SORT.NAME)) {
                    algo = Algorithms.MERGE_SORT;
                }
                switch (selectedviewCode) {
                    case "의사코드":
                        String code = algo.CODE.PSEUDO; // 코드를 저장해 변환 함수를 이용할 문자열
                        viewCodeLabel.setText(CodeParser.parseCode(code));
                        break;
                    case "C":
                        code = algo.CODE.C;
                        viewCodeLabel.setText(CodeParser.parseCode(code));
                        break;
                    case "C++":
                        code = algo.CODE.CPP;
                        viewCodeLabel.setText(CodeParser.parseCode(code));
                        break;
                    case "Java":
                        code = algo.CODE.JAVA;
                        viewCodeLabel.setText(CodeParser.parseCode(code));
                        break;
                    case "Python":
                        code = algo.CODE.PYTHON;
                        viewCodeLabel.setText(CodeParser.parseCode(code));
                        break;
                }
            }
        });

    } // 생성자

    private class MyScrollPane extends JScrollPane {
        private static final int SPEED = 20;

        public MyScrollPane(Component view) {
            super(view);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            getHorizontalScrollBar().setUnitIncrement(SPEED);
            getVerticalScrollBar().setUnitIncrement(SPEED);
        }
    }

    protected void setComboIndex(int index) {
        comboAlgorithm.setSelectedIndex(index);
    }
} // LearningScreen 클래스

/**
 * 실습하기 메뉴
 */
class PracticeScreen extends JPanel {
    private static final Exception ValueInvalidException = new Exception("배열에는 정수만 입력할 수 있습니다!");
    private static final Exception NumberTooBigException = new Exception("100 이하의 정수만 입력할 수 있습니다!");

    private ComboBox comboAlgorithm = new ComboBox();
    private Button btnLearn = new Button("학습하기");
    private SortingAnimation animation = new SortingAnimation();
    private SortManager manager;
    private JPanel pnlControl = new JPanel();
    private ListBox listBox = new ListBox(1);
    private Button btnInsert = new Button("입력하기");
    private Button btnStart = new Button("시작");
    private Button btnPause = new Button("일시정지");
    private Button btnResume = new Button("이어하기");
    private Button btnStop = new Button("중지");

    // 학습하기 버튼 구현 관련 변수
    private JPanel pnlContent;

    public PracticeScreen(JPanel pnlContent) {
        this.pnlContent = pnlContent;
        // 초기화
        comboAlgorithm.addItems(new String[][] {
                { "버블 정렬", "" },
                { "선택 정렬", "" },
                { "삽입 정렬", "" },
                { "퀵 정렬", "" },
                { "병합 정렬", "" }
        });
        animation.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pnlControl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
        add(animation, GbcFactory.createGbc(0, 2, 0.55d, 0.85d, 2, 2));
        add(new JLabel(""), GbcFactory.createGbc(2, 2, 0.05d, 0.85d, 1, 2));
        add(pnlControl, GbcFactory.createGbc(3, 2, 0.4d, 0.8d, 2, 1));

        //조작 패널
        JPanel pnlInsert = new JPanel();
        pnlInsert.setLayout( new GridBagLayout() );
        pnlInsert.add( listBox, GbcFactory.createGbc(0, 0, 1d, 0.9d) );
        pnlInsert.add( btnInsert, GbcFactory.createGbc(0, 1, 1d, 0.1d) );

        JPanel pnlProcess = new JPanel();
        pnlProcess.setLayout( new VerticalLayout() );
        pnlProcess.add(btnStart);
        pnlProcess.add(btnPause);
        pnlProcess.add(btnResume);
        pnlProcess.add(btnStop);

        pnlControl.setLayout( new CardLayout() );
        pnlControl.add(pnlInsert, "insert");
        pnlControl.add(pnlProcess, "process");

        // 학습하기 버튼
        btnLearn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int comboItemIndex = comboAlgorithm.getSelectedIndex();
                    CardLayout cardLayout = (CardLayout) pnlContent.getLayout();
                    cardLayout.show(pnlContent, ScreenList.LEARNING_SCREEN);
                    ((LearningScreen) pnlContent.getComponent(1)).setComboIndex(comboItemIndex);
            }
        });

        //입력하기 버튼
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //잘못된 값 예외 처리
                if (listBox.getLength() <= 0) { //배열에 값 없음
                    MessageBox.show(Main.getFrame(), "배열에 값을 입력해주세요!", MessageBox.btnOK, MessageBox.iconERROR);
                    return;
                }

                try {
                    boolean valueInvalidException = false;
                    boolean numberTooBidException = false;
                    for (int i=0; i<listBox.getLength(); i++) {
                        boolean flagError = false;
                        try {
                            int value = Integer.parseInt(listBox.get(i)[0]);
                            if (value > 100) {
                                flagError = true;
                                numberTooBidException = true;
                            }
                        }
                        catch (Exception exception) {
                            flagError = true;
                            valueInvalidException = true;
                        }

                        listBox.getContent(i).setAlert(flagError);
                    }

                    if (valueInvalidException) { //정수가 아닌 값 입력
                        throw ValueInvalidException;
                    }
                    else if (numberTooBidException) { //100 초과 값 입력
                        throw NumberTooBigException;
                    }
                }
                catch (Exception exception){
                    MessageBox.show(Main.getFrame(), exception.getMessage(), MessageBox.btnOK, MessageBox.iconERROR);
                    return;
                }

                //정상 값 입력
                int[] array = new int[listBox.getLength()];
                for (int i=0; i<array.length; i++) {
                    array[i] = Integer.parseInt(listBox.get(i)[0]);
                }

                //막대 그래프 초기화
                remove(animation);
                animation = new SortingAnimation(array);
                switch ( comboAlgorithm.getSelectedIndex() ) {
                    case 0: //버블 정렬
                        manager = new SortManager(animation, SortManager.BUBBLE_SORT);
                        break;
                    case 1: //선택 정렬
                        manager = new SortManager(animation, SortManager.SELECTION_SORT);
                        break;
                    case 2: //삽입 정렬
                        manager = new SortManager(animation, SortManager.INSERTION_SORT);
                        break;
                    case 3: //퀵 정렬
                        manager = new SortManager(animation, SortManager.QUICK_SORT);
                        break;
                    case 4: //합병 정렬
                        manager = new SortManager(animation, SortManager.MERGE_SORT);
                        break;
                    default:
                        break;
                }
                add(animation, GbcFactory.createGbc(0, 2, 0.55d, 0.85d, 2, 2));
                ( (CardLayout) pnlControl.getLayout() ).show(pnlControl, "process");
                comboAlgorithm.setEnabled(false);
            }
        }); //btnInsert 액션 리스너

        //조작 버튼
        MyControlListener listener = new MyControlListener();
        btnStart.addActionListener(listener);
        btnPause.addActionListener(listener);
        btnResume.addActionListener(listener);
        btnStop.addActionListener(listener);
    } // 생성자

    private class MyControlListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (manager == null) {
                reset();
                return;
            }

            if ( e.getSource().equals(btnStart) ) {
                manager.start();
            }
            else if ( e.getSource().equals(btnPause) ) {
                manager.pause();
            }
            else if ( e.getSource().equals(btnResume) ) {
                manager.resume();
            }
            else {
                manager.stop();
                manager = null;
                reset();
            }
        }

        private void reset() {
            remove(animation);
            animation = new SortingAnimation();
            add(animation, GbcFactory.createGbc(0, 2, 0.55d, 0.85d, 2, 2));
            ( (CardLayout) pnlControl.getLayout() ).show(pnlControl, "insert");
            comboAlgorithm.setEnabled(true);
        }
    } //MyControlListener 클래스

    protected void setComboIndex(int index) {
        comboAlgorithm.setSelectedIndex(index);
    }
} // PracticeScreen 클래스

/**
 * 퀴즈 시작 메뉴
 */
class QuizStartScreen extends JPanel {
    private JLabel lblQuiz = new JLabel("Quiz", SwingConstants.CENTER);
    private Button btnStart = new Button("Start");
    private JPanel pnlContent;

    public QuizStartScreen(JPanel pnlContent) {
        this.pnlContent = pnlContent;
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

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) pnlContent.getLayout();
                cardLayout.show(pnlContent, ScreenList.QUIZ_SCREEN);
            }
        });
    } // 생성자
} // QuizStartScreen 클래스

/**
 * 퀴즈 풀이 메뉴
 */
class QuizScreen extends JPanel {
    private JPanel panel = new JPanel();
    private JLabel quizLabel = new JLabel("", SwingConstants.CENTER);
    private JTextField answerEdit = new JTextField();
    private JButton answerBtn = new JButton("정답 제출");
    private HashMap<String, String> quizHashMap = new HashMap<String, String>(); // 퀴즈와 답을 담을 HashMap
    private Random random = new Random();
    private String randomQuestionStr = ""; // 랜덤으로 선정된 문제를 담을 문자열
    private String quizGetKeyStr = ""; // quizHashMap HashMap에서 key를 찾을 때 사용되는 문자
    private List<String> endQuestionList = new ArrayList<>(); // 출력 완료된 문제를 담을 리스트

    public QuizScreen() {
        setLayout(new GridBagLayout());
        panel.setLayout(new BorderLayout());
        panel.add(quizLabel, BorderLayout.CENTER);

        // 문제 추가 함수 호출
        putQuiz();
        // 랜덤 퀴즈 설정 함수 호출
        showrandomQuestionStr();

         // 1행
         add(panel, GbcFactory.createGbc(0, 0, 1d, 0.9d, 2, 1));

         // 2행
         add(answerEdit, GbcFactory.createGbc(0, 1, 0.7d, 0.1d));
         add(answerBtn, GbcFactory.createGbc(1, 1, 0.3d, 0.1d));

         answerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {          
                if (answerEdit.getText().toString().equals(quizGetKeyStr)){
                    MessageBox.show(Main.getFrame(), "정답입니다!", MessageBox.btnOK, MessageBox.iconINFORMATION);
                    answerEdit.setText(""); // 텍스트 필드 초기화
                    showrandomQuestionStr(); // 문제 출력 호출
                    return;
                }
                else {
                    MessageBox.show(Main.getFrame(), "오답입니다.", MessageBox.btnOK, MessageBox.iconERROR);
                }
            }
         });
                
    } // 생성자

    @Override
    public void add(Component component, Object constraints) {
        super.add(component, constraints);
        ((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void showrandomQuestionStr() { // 랜덤 퀴즈 함수
        // 퀴즈 크기만큼 랜덤 정수 생성
        int randomIndex = random.nextInt(quizHashMap.size());
        if (endQuestionList.size() == quizHashMap.size()) {
            // 모든 문제가 출제되었으면 메시지 출력
            int result = MessageBox.show(Main.getFrame(), "문제가 끝났습니다. 다시 푸시겠습니까?", MessageBox.btnOK_CANCEL, MessageBox.iconQUESTION);
            if (result == MessageBox.btnOK) { // OK 버튼을 누르면
                endQuestionList.clear(); // 완료된 문제들 리스트 초기화
                showrandomQuestionStr(); // 문제 출력 함수 호출
                return;
            }
            else {
                quizLabel.setText("문제가 더이상 없습니다.");
                return;
            }            
        }
        // 아직 출제되지 않은 문제를 찾을 때까지 반복
        while (true) {
            List<String> questionList = new ArrayList<>(quizHashMap.keySet()); // 해쉬맵에서 문제를 담을 리스트
            randomQuestionStr = questionList.get(randomIndex); // 랜덤으로 문제를 골라와 문자열에 대입
            if (!endQuestionList.contains(randomQuestionStr)) { // 출력 완료 문제를 담은 리스트에 랜덤 문제가 포함되면 break
                break;
            }
        }
        // 랜덤 문제를 quizHashMap에서 해당하는 key를 찾고 quizHashMapLabel에 설정
        quizGetKeyStr = quizHashMap.get(randomQuestionStr);
        quizLabel.setText(randomQuestionStr);
        endQuestionList.add(randomQuestionStr); // 출력 완료 문제 리스트에 랜덤 문제 추가
    }

    private void putQuiz() { // 문제 추가 메소드
        // 문제 및 답안 추가
        // 정의 문제
        quizHashMap.put("<html>서로 인접한 두 원소를 검사하여 오른쪽 리스트가 자동으로 정렬되는 방식의 알고리즘은?<br>'OO 정렬' 식으로 정답을 입력하세요.</html>", "버블 정렬");
        quizHashMap.put("<html>정렬되지 않은 데이터들에 대해 가장 작은 데이터를 찾아 가장 앞의 데이터와 교환하는 방식의 알고리즘은?<br>'OO 정렬' 식으로 정답을 입력하세요.</html>", "선택 정렬");
        quizHashMap.put("<html>자료 배열의 모든 요소를 앞에서부터 차례대로 이미 정렬된 배열 부분과 비교하여 자신의 위치를 삽입하는 방식의 알고리즘은?<br>'OO 정렬' 식으로 정답을 입력하세요.</html>", "삽입 정렬");
        quizHashMap.put("<html>피벗을 사용하는 알고리즘은?<br>'OO 정렬' 식으로 정답을 입력하세요.</html>", "퀵 정렬");
        quizHashMap.put("<html>리스트를 2개의 균등한 크기로 분할하고 분할된 부분 리스트를 다른 2개의 정렬된 부분 리스트를 합하는 방식의 알고리즘은?<br>'OO 정렬' 식으로 정답을 입력하세요.</html>", "병합 정렬");

        // 정렬 문제
        //quizHashMap.put("<html>[2, 30, 1, 13, 5]를 버블 정렬할 때 1회전 후 결과는?<br>2, 30, 1, 13, 5 식으로 정답을 입력하세요.</html>", "2, 1, 13, 5, 30");
        quizHashMap.put("<html>[2, 30, 1, 13, 5]를 버블 정렬할 때 2회전 후 결과는?<br>2, 30, 1, 13, 5 식으로 정답을 입력하세요.</html>", "1, 2, 5, 13, 30");
        //quizHashMap.put("<html>[37, 14, 17, 40, 35]를 선택 정렬을 이용해 오름차순 정렬할 때 1회전 후 결과는?<br>37, 14, 17, 40, 35 식으로 정답을 입력하세요.</html>", "14, 37, 17, 40, 35");
        //quizHashMap.put("<html>[37, 14, 17, 40, 35]를 선택 정렬을 이용해 오름차순 정렬할 때 2회전 후 결과는?<br>37, 14, 17, 40, 35 식으로 정답을 입력하세요.</html>", "14, 17, 37, 40, 35");
        quizHashMap.put("<html>[37, 14, 17, 40, 35]를 선택 정렬을 이용해 오름차순 정렬할 때 3회전 후 결과는?<br>37, 14, 17, 40, 35 식으로 정답을 입력하세요.</html>", "14, 17, 35, 40, 37");
        //quizHashMap.put("<html>[8, 3, 4, 9, 7]를 삽입 정렬을 이용해 오름차순 정렬할 때 1회전 후 결과는?<br>8, 3, 4, 9, 7 식으로 정답을 입력하세요.</html>", "3, 8, 4, 9, 7");
        quizHashMap.put("<html>[8, 3, 4, 9, 7]를 삽입 정렬을 이용해 오름차순 정렬할 때 2회전 후 결과는?<br>8, 3, 4, 9, 7 식으로 정답을 입력하세요.</html>", "3, 4, 8, 9, 7");
        //quizHashMap.put("<html>[8, 3, 4, 9, 7]를 삽입 정렬을 이용해 오름차순 정렬할 때 3회전 후 결과는?<br>8, 3, 4, 9, 7 식으로 정답을 입력하세요.</html>", "3, 4, 8, 9, 7");        
        quizHashMap.put("<html>[2, 14, 51, 80, 43]를 퀵 정렬을 이용해 오름차순 정렬할 때 1회전 후 결과는?(피벗은 51로 한다.)<br>2, 14, 51, 80, 43 식으로 정답을 입력하세요.</html>", "2, 14, 43, 51, 80");

        // 시간복잡도 문제
        quizHashMap.put("<html>버블, 선택, 삽입, 퀵, 합병 정렬 중 최선 시간 복잡도가 O(nlogn)인 정렬은?<br>ex) 'OO 정렬' 식으로 정답을 입력하세요.</html>", "병합 정렬");
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