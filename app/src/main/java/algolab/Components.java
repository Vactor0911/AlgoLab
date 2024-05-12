package algolab;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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

/**
 * 수정 가능한 문자열 데이터를 저장할 수 있는 리스트 박스를 구현한 클래스이다.
 */
class ListBox extends JScrollPane {
    //상수
    private static final int GAP = 5;
    private static final int CONTENT_HEIGHT = 50;

    //컴포넌트
    private JPanel pnl = new JPanel();
    private JPanel pnlContent = new JPanel();
    private Button btnAdd = new Button("Add");

    //변수
    private ArrayList<ListContent> listContents = new ArrayList<>();
    private final int columns;

    /**
     * {@code columns}개의 열을 갖는 리스트 박스를 생성한다. {@code columns}는 리스트 박스가 생성된 이후에는 수정될 수 없다.
     * @param columns 리스트 박스의 열 개수를 지정한다. 1보다 작을 수 없다.
     */
    public ListBox(int columns) {
        this.columns = Math.max(columns, 1);

        pnl.setLayout( new VerticalLayout(5) );
        setViewportView(pnl);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getVerticalScrollBar().setUnitIncrement(20);
        getVerticalScrollBar().setValue(0);

        pnlContent.setLayout( new VerticalLayout(GAP) );
        pnl.add(pnlContent);

        btnAdd.setPreferredSize( new Dimension((int)getPreferredSize().getWidth(), CONTENT_HEIGHT) );
        btnAdd.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                put();
                revalidate();
            }
        } );
        pnl.add(btnAdd);
    } //생성자
    public ListBox() {
        this(1);
    } //생성자

    /**
     * 스크롤의 최대 위치 값을 반환한다.
     * @return 스크롤의 최대 값
     */
    public int getMaxValue() {
        JScrollBar vScrollBar = getVerticalScrollBar();
        return vScrollBar.getMaximum() - vScrollBar.getHeight();
    }

    /**
     * 스크롤이 더 이상 스크롤할 수 없는 끝 부분에 위치해있는지에 대한 여부를 반환한다.
     * @return 스크롤이 끝 부분에 있다면 {@code true}, 아니라면 {@code false}를 반환한다.
     */
    public boolean isScrollEnd() {
        return getVerticalScrollBar().getValue() == getMaxValue();
    }

    /**
     * 리스트 박스에 값을 삽입한다.
     * <p>리스트 박스를 생성할 때 지정한 {@code columns} 값에 영향을 받는다.
     * <ol>
     *  <li> {@code values}의 크기가 {@code columns} 보다 크다면 {@code columns} 보다 크거나 같은 인덱스의 원소는 삽입되지 않는다.
     *  <li> {@code values}의 크기가 {@code columns} 보다 작다면 부족한 값은 {@code 공백; ""}으로 삽입된다.
     * </ol>
     * @param values {@code String[]} 형태의 값
     */
    public void put(String[] values) {
        //마지막 ListContent 객체가 비어있다면 반환
        if (listContents.size() > 0) {
            if ( listContents.get(listContents.size() - 1).isEmpty() ) {
                return;
            }
        }

        //열 개수에 따른 values 매개변수 가공
        String[] fixedValues = new String[columns];
        for (int i=0; i<fixedValues.length; i++) {
            if (i < values.length) {
                fixedValues[i] = values[i];
            }
            else {
                fixedValues[i] = "";
            }
        }

        //ListContent 생성 및 삽입
        ListContent content = new ListContent(this, listContents.size(), fixedValues);
        Dimension prefSize = content.getPreferredSize();
        prefSize.height = CONTENT_HEIGHT;
        content.setPreferredSize(prefSize);

        listContents.add(content);
        pnlContent.add(content);
    } //put(String[] values)
    /**
     * 리스트 박스에 값을 삽입한다.
     * <p>리스트 박스를 생성할 때 지정한 {@code columns} 값에 영향을 받는다.
     * <p>{@code columns}가 1보다 크다면 {@code value}가 0번째에 삽입되고, 나머지 값은 {@code 공백; ""}으로 삽입된다.
     * @param value {@code String} 형태의 값
     */
    public void put(String value) {
        put( new String[]{value} );
    }
    /**
     * 리스트 박스에 비어있는 값을 삽입한다.
     * <p>리스트 박스를 생성할 때 지정한 {@code columns} 값에 영향을 받는다.
     * <p>{@code columns}개의 비어있는 값이 삽입된다.
     */
    public void put() {
        put( new String[]{""} );
    }

    /**
     * 리스트에 저장된 모든 값을 반환한다.
     * @return {@code String[][]} 형태의 문자열 배열
     */
    public String[][] get() {
        String[][] aryValues = new String[listContents.size()][columns];

        for (int i=0; i<listContents.size(); i++) {
            aryValues[i] = listContents.get(i).getTexts();
        }

        return aryValues;
    }

    /**
     * 리스트에 저장된 값 중 {@code index}번째 행의 값을 반환한다.
     * @param index 반환하고자 하는 행의 인덱스
     * @return {@code String[]} 형태의 문자열 배열
     */
    public String[] get(int index) {
        try {
            return listContents.get(index).getTexts();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 리스트에서 {@code index}번째 행을 삭제한다.
     * @param index 삭제하고자 하는 행의 인덱스
     */
    public void removeContent(int index) {
        pnlContent.remove( listContents.get(index) );
        listContents.remove(index);

        for (int i=index; i<listContents.size(); i++) {
            listContents.get(i).setIndex(i);
        }
        revalidate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        getVerticalScrollBar().setValue( getMaxValue() );
    }
} //ListBox 클래스

/**
 * 리스트 박스에 저장되는 데이터와 상호작용 할 수 있도록 구현한 클래스이다.
 */
class ListContent extends JPanel {
    //상수
    private final int INDEX_WIDTH;

    //컴포넌트
    private JPanel pnl = new JPanel();
    private JLabel lblIndex;
    private JTextField[] aryTextField;

    //변수
    private int index;

    public ListContent(ListBox root, int index, String[] values) {
        super();

        //초기화
        this.index = index;
        INDEX_WIDTH = (int)(getPreferredSize().getWidth() * 0.1d);
        aryTextField = new JTextField[values.length];
        setLayout( new GridBagLayout() );

        //인덱스 라벨
        lblIndex = new JLabel(Integer.toString(index + 1), SwingConstants.CENTER);
        lblIndex.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add( lblIndex, GbcFactory.createGbc(0, 0, 0.1d, 1.0d) );
        lblIndex.setPreferredSize( new Dimension( INDEX_WIDTH, (int)getPreferredSize().getHeight() ) );

        //콘텐츠 텍스트 필드
        pnl.setLayout( new GridLayout(1, 0) );

        for (int i=0; i<values.length; i++) {
            JTextField tf = new JTextField(0);
            Dimension prefferedSize = new Dimension( (int)getPreferredSize().getWidth(), getHeight() );

            tf.setPreferredSize(prefferedSize);
            tf.setHorizontalAlignment(JTextField.CENTER);
            tf.setText(values[i]);
            tf.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            tf.addFocusListener( new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    if ( isEmpty() ) {
                        root.removeContent( getIndex() );
                        revalidate();
                    }
                }
            } );
            tf.addKeyListener( new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        pnl.requestFocus();
                    }
                }
            });

            aryTextField[i] = tf;
            pnl.add(tf);
        }
        add(pnl, GbcFactory.createGbc(1, 0, 0.9d, 1.0d));
    } //생성자

    public void setIndex(int index) {
        this.index = index;
        lblIndex.setText( Integer.toString(index + 1) );
    }

    public int getIndex() {
        return index;
    }

    public void setTexts(String[] values) {
        for (int i=0; i<aryTextField.length; i++) {
            String strValue = "";
            if (i < values.length) {
                strValue = values[i];
            }
            aryTextField[i].setText(strValue);
        }
    }

    public String[] getTexts() {
        if (aryTextField.length <= 0) {
            return null;
        }

        String[] aryValue = new String[aryTextField.length];
        for (int i=0; i<aryTextField.length; i++) {
            aryValue[i] = aryTextField[i].getText();
        }
        return aryValue;
    }

    public boolean isEmpty() {
        for (JTextField tf : aryTextField) {
            if ( tf.getText().isBlank() == false ) {
                return false;
            }
        }
        return true;
    }

    public void showIndex(boolean b) {
        lblIndex.setVisible(b);
    }
} //ListContent 클래스