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