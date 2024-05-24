package algolab;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

class GbcFactory {
    private static GridBagConstraints gbc = new GridBagConstraints();

    /**
     * 주어진 속성으로 설정된 {@code GridBagConstraints} 객체를 반환한다.
     * @param x 객체가 위치할 열 번호
     * @param y 객체가 위치할 행 번호
     * @param weightX 열이 차지할 비율
     * @param weightY 행이 차지할 비율
     * @param width 객체가 차지할 열 개수
     * @param height 객체가 차지할 행 개수
     * @return {@code GridBagConstraints} 객체
     */
    public static GridBagConstraints createGbc(int x, int y, double weightX, double weightY, int width, int height) {
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightX;
        gbc.weighty = weightY;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        return gbc;
    }

    /**
     * 주어진 속성으로 설정된 {@code GridBagConstraints} 객체를 반환한다.
     * @param x 객체가 위치할 열 번호
     * @param y 객체가 위치할 행 번호
     * @param weightX 열이 차지할 비율
     * @param weightY 행이 차지할 비율
     * @return {@code GridBagConstraints} 객체
     */
    public static GridBagConstraints createGbc(int x, int y, double weightX, double weightY) {
        return createGbc(x, y, weightX, weightY, 1, 1);
    }

    /**
     * 주어진 속성으로 설정된 {@code GridBagConstraints} 객체를 반환한다.
     * @param x 객체가 위치할 열 번호
     * @param y 객체가 위치할 행 번호
     * @param width 객체가 차지할 열 개수
     * @param height 객체가 차지할 행 개수
     * @return {@code GridBagConstraints} 객체
     */
    public static GridBagConstraints createGbc(int x, int y, int width, int height) {
        return createGbc(x, y, 1, 1, width, height);
    }

    /**
     * 주어진 속성으로 설정된 {@code GridBagConstraints} 객체를 반환한다.
     * @param x 객체가 위치할 열 번호
     * @param y 객체가 위치할 행 번호
     * @return {@code GridBagConstraints} 객체
     */
    public static GridBagConstraints createGbc(int x, int y) {
        return createGbc(x, y, 1, 1, 1, 1);
    }
} //GbcFactory 클래스

/**
 * 객체를 세로로 배치할 수 있는 배치 관리자이다.
 */
class VerticalLayout implements LayoutManager {
    private final Dimension MINIMUM_SIZE = new Dimension();
    private int gapY;

    /**
     * 객체를 세로로 배치할 수 있는 배치 관리자이다.
     * @param gapY 객체 사이의 간격
     */
    public VerticalLayout(int gapY) {
        super();
        this.gapY = gapY;
    }
    /**
     * 객체를 세로로 배치할 수 있는 배치 관리자이다.
     */
    public VerticalLayout() {
        this(0);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }
    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Dimension dimension = new Dimension( getTotalPaddingWidth(parent), getTotalPaddingHeight(parent) );
        MINIMUM_SIZE.height = dimension.height;
        for ( Component c :parent.getComponents() ) {
            if ( !c.isVisible() ) {
                continue;
            }

            Dimension preferredSize = c.getPreferredSize();
            dimension.height += preferredSize.getHeight() + gapY;
            if (preferredSize.width > dimension.width) {
                dimension.width = preferredSize.width;
            }
        }
        dimension.height -= gapY;
        MINIMUM_SIZE.width = dimension.width;
        return dimension;
    }

    private int getTotalPaddingWidth(Container container) {
        Insets padding = container.getInsets();
        return padding.left + padding.right;
    }
    private int getTotalPaddingHeight(Container container) {
        Insets padding = container.getInsets();
        return padding.top + padding.bottom;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return MINIMUM_SIZE;
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets padding = parent.getInsets();
        int y = padding.top;
        int width = parent.getWidth() - padding.left - padding.right;
        for ( Component c : parent.getComponents() ) {
            if ( c.isVisible() ) {
                c.setBounds(padding.left, y, width, c.getPreferredSize().height);
                y += c.getHeight() + gapY;
           }
        }
    }
} //VerticalLayout 클래스

/**
 * DocumentListener 리스너를 상속받는 클래스이다.
 * 필요한 메소드만 오버라이드하여 사용한다.
 */
class DocumentAdapter implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) { }

    @Override
    public void removeUpdate(DocumentEvent e) { }

    @Override
    public void changedUpdate(DocumentEvent e) { }
} //DocumentAdapter 클래스

/**
 * FocusListener 리스너를 상속받는 클래스이다.
 * 필요한 메소드만 오버라이드하여 사용한다.
 */
class FocusAdapter implements FocusListener {
    @Override
    public void focusGained(FocusEvent e) { }

    @Override
    public void focusLost(FocusEvent e) { }
} //FocusAdapter 클래스

/**
 * 여러 알고리즘의 세부 정보가 저장된 클래스이다.
 */
class Algorithms {
    protected static final Algorithm BUBBLE_SORT = new Algorithm(
        "버블 정렬",
        "버블 정렬 정의",
        new Algorithm.Code(
            "버블 정렬 의사코드",
            "버블 정렬 C 언어",
            "버블 정렬 C++ 언어",
            "버블 정렬 Java 언어",
            "버블 정렬 Python 언어"
        ),
        new Algorithm.TimeComplexity(
            "버블 정렬 최선 시간복잡도",
            "버블 정렬 최악 시간복잡도",
            "버블 정렬 평균 시간복잡도"
        )
    );

    /**
     * 알고리즘이 저장할 세부 정보의 구조를 구현한 클래스이다.
     */
    protected static class Algorithm {
        protected final String NAME;
        protected final String DEFINITION;
        protected final Code CODE;
        protected final TimeComplexity TIME_COMPLEXITY;

        /**
         * 새로운 알고리즘 데이터를 생성한다.
         * @param name 알고리즘의 이름
         * @param definition 알고리즘의 정의
         * @param code {@code Code} 형태의 알고리즘 예시 코드
         * @param timeComplexity {@code TimeComplexity} 형태의 알고리즘 시간복잡도
         */
        private Algorithm(String name, String definition, Code code, TimeComplexity timeComplexity) {
            NAME = name;
            DEFINITION = definition;
            CODE = code;
            TIME_COMPLEXITY = timeComplexity;
        }

        /**
         * 알고리즘의 세부 정보 중 예시 코드의 구조를 구현한 클래스이다.
         */
        protected static class Code {
            protected final String PSEUDO;
            protected final String C;
            protected final String CPP;
            protected final String JAVA;
            protected final String PYTHON;

            /**
             * 새로운 예시 코드 데이터를 생성한다.
             * @param pseudo 의사코드
             * @param c C 언어 코드
             * @param cpp C++ 언어 코드
             * @param java Java 언어 코드
             * @param python Python 언어 코드
             */
            private Code(String pseudo, String c, String cpp, String java, String python) {
                PSEUDO = pseudo;
                C = c;
                CPP = cpp;
                JAVA = java;
                PYTHON = python;
            }
        } //Code 클래스

        /**
         * 알고리즘의 세부 정보 중 시간복잡도의 구조를 구현한 클래스이다.
         */
        protected static class TimeComplexity {
            protected final String BEST;
            protected final String WORST;
            protected final String AVERAGE;

            /**
             * 새로운 시간복잡도 데이터를 생성한다.
             * @param best 최선 시간복잡도
             * @param worst 최악 시간복잡도
             * @param average 평균 시간복잡도
             */
            private TimeComplexity(String best, String worst, String average) {
                BEST = best;
                WORST = worst;
                AVERAGE = average;
            }
        } //TimeComplexity 클래스
    } //Algorithm 클래스
} //Algorithms 클래스