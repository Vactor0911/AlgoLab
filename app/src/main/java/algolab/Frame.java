package algolab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Frame extends JFrame {
    //주 패널
    private JPanel pnlMenu = new JPanel();
    private JPanel pnlContent = new JPanel();

    //메뉴 버튼
    private Button btnLearning = new Button("학습하기");
    private Button btnTraining = new Button("실습하기");
    private Button btnQuiz = new Button("퀴즈 풀기");

    //화면 객체
    private LearningScreen learningScreen = new LearningScreen();
    private PracticeScreen practiceScreen = new PracticeScreen();
    private QuizStartScreen quizStartScreen = new QuizStartScreen();
    private QuizScreen quizScreen = new QuizScreen();

    //기타 객체
    private CardLayout cardLayout = new CardLayout(10, 10);

    private class ScreenList {
        public static final String NONE = "none";
        public static final String LEARNING_SCREEN = "LearningPanel";
        public static final String PRACTICE_SCREEN = "PracticePanel";
        public static final String QUIZ_START_SCREEN = "QuizStartPanel";
    }

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

        btnLearning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(pnlContent, ScreenList.LEARNING_SCREEN);
            }
        });
        btnTraining.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(pnlContent, ScreenList.PRACTICE_SCREEN);
            }
        });
        btnQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(pnlContent, ScreenList.QUIZ_START_SCREEN);
            }
        });

        add( pnlMenu, GbcFactory.createGbc(0, 0, 0.2d, 1.0d) );      

        //내용 패널
        pnlContent.setLayout(cardLayout);
        add( pnlContent, GbcFactory.createGbc(1, 0, 0.8d, 1.0d) );

        //내용 삽입
        pnlContent.add(new JPanel(), ScreenList.NONE);
        pnlContent.add(learningScreen, ScreenList.LEARNING_SCREEN);
        pnlContent.add(practiceScreen, ScreenList.PRACTICE_SCREEN);
        pnlContent.add(quizStartScreen, ScreenList.QUIZ_START_SCREEN);

        cardLayout.show(pnlContent, ScreenList.LEARNING_SCREEN);
        setVisible(true);
    }
}
