package algolab;

import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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