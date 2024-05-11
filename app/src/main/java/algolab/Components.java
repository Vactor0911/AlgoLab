package algolab;

import java.awt.*;
import javax.swing.*;

/**
 * 버튼의 기본적인 속성을 지정해주는 클래스이다.
 */
class ButtonBase extends JButton {
    public ButtonBase(String text) {
        super(text);

        //공통 속성
        setContentAreaFilled(false);
        setFocusPainted(false);
    }
    public ButtonBase() {
        this("");
    }

    /**
     * 버튼의 배경을 칠하기 위한 그라데이션 재질을 가진 {@code Graphics2D} 객체를 반환한다.
     * @param g {@code paint()} 또는 {@code paintComponent()} 메소드의 인자로 포함된 {@code Graphics} 객체
     * @return {@code Graphics2D} 객체
     */
    public Graphics2D getGraphics2D(Graphics g) {
        ButtonModel model = getModel();
        Color color1 = Color.LIGHT_GRAY;
        Color color2 = Color.GRAY;

        if (model.isPressed()) { //눌렀을 때
            color1 = color1.darker();
            color2 = color2.darker();
        }
        else if ( model.isRollover() ) { //커서를 올려뒀을 때
            color1 = color1.brighter();
            color2 = color2.brighter();
        }

        //그라데이션 재질 적용
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(
            new GradientPaint(
                new Point(0, 0), color1, 
                new Point(0, getHeight() ), color2
            )
        );

        return g2;
    }
} //ButtonBase 클래스

/**
 * 사각형의 "누르는" 버튼을 구현한 클래스이다.
 */
class Button extends ButtonBase {
    public Button(String text) {
        super(text);
    }
    public Button() {
        this("");
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = getGraphics2D(g);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        super.paintComponent(g2);
    }

    @Override
    public void paintBorder(Graphics g) {
        g.setColor( Color.BLACK );
        g.drawRect(0, 0, getSize().width-1, getSize().height-1);
    }
} //Button 클래스

/**
 * 둥근 사각형의 "누르는" 버튼을 구현한 클래스이다.
 */
class RoundButton extends ButtonBase {
    private int radius;

    public RoundButton(String text, int radius) {
        super(text);
        this.radius = radius;
    }
    public RoundButton(String text) {
        this(text, 10);
    }
    public RoundButton() {
        this("", 10);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = getGraphics2D(g);
        g2.fillRoundRect(0, 0, getSize().width, getSize().height, radius, radius);
        super.paintComponent(g2);
    }

    @Override
    public void paintBorder(Graphics g) {
        g.setColor( Color.BLACK );
        g.drawRoundRect(0, 0, getSize().width-1, getSize().height-1, radius, radius);
    }
} //RoundButton 클래스