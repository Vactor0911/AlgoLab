package algolab;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.*;

/**
 * 화면 크기에 따라 폰트의 크기가 유동적으로 변경되도록 구현한 라벨 클래스이다.
 */
class Label extends JLabel {
    private int fontSize = 12;

    public Label(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public Label(String text) {
        super(text);
    }

    public Label(ImageIcon imageIcon) {
        super(imageIcon);
    }

    public Label() {
        super();
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        fontSize = font.getSize();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        float newFontSize = (float)fontSize * Main.getFrame().getSizeMul();
        Font font = getFont().deriveFont(newFontSize);
        super.setFont(font);
    }
}

/**
 * 버튼의 기본적인 속성을 지정해주는 클래스이다.
 */
class ButtonBase extends JButton {
    private int fontSize = 12;

    public ButtonBase(String text) {
        super(text);

        // 공통 속성
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    public ButtonBase() {
        this("");
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        fontSize = font.getSize();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        float newFontSize = (float)fontSize * Main.getFrame().getSizeMul();
        Font font = getFont().deriveFont(newFontSize);
        super.setFont(font);
    }

    /**
     * 버튼의 배경을 칠하기 위한 그라데이션 재질을 가진 {@code Graphics2D} 객체를 반환한다.
     * 
     * @param g {@code paint()} 또는 {@code paintComponent()} 메소드의 인자로 포함된
     *          {@code Graphics} 객체
     * @return {@code Graphics2D} 객체
     */
    public Graphics2D getGraphics2D(Graphics g) {
        ButtonModel model = getModel();
        Color color1 = Color.LIGHT_GRAY;
        Color color2 = Color.GRAY;

        if (model.isPressed()) { // 눌렀을 때
            color1 = color1.darker();
            color2 = color2.darker();
        } else if (model.isRollover()) { // 커서를 올려뒀을 때
            color1 = color1.brighter();
            color2 = color2.brighter();
        }

        // 그라데이션 재질 적용
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(color1);

        return g2;
    }
} // ButtonBase 클래스

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
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
    }
} // Button 클래스

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
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, radius, radius);
    }
} // RoundButton 클래스

/**
 * 수정 가능한 문자열 데이터를 저장할 수 있는 리스트 박스를 구현한 클래스이다.
 */
class ListBox extends ScrollPane {
    // 상수
    private static final int GAP = 5;
    private static final int CONTENT_HEIGHT = 50;

    // 컴포넌트
    private static JPanel pnl = new JPanel();
    private JPanel pnlContent = new JPanel();
    private Button btnAdd = new Button("Add");

    // 변수
    private ArrayList<ListContent> listContents = new ArrayList<>();
    private final int columns;
    private boolean showIndex = true;

    /**
     * {@code columns}개의 열을 갖는 리스트 박스를 생성한다. {@code columns}는 리스트 박스가 생성된 이후에는 수정될 수
     * 없다.
     * 
     * @param columns 리스트 박스의 열 개수를 지정한다. 1보다 작을 수 없다.
     */
    public ListBox(int columns) {
        super(pnl);
        this.columns = Math.max(columns, 1);

        pnl.setLayout(new VerticalLayout(5));
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getVerticalScrollBar().setUnitIncrement(20);
        getVerticalScrollBar().setValue(0);

        pnlContent.setLayout(new VerticalLayout(GAP));
        pnl.add(pnlContent);

        btnAdd.setPreferredSize(new Dimension((int) getPreferredSize().getWidth(), CONTENT_HEIGHT));
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                put();
                revalidate();
            }
        });
        pnl.add(btnAdd);
    } // 생성자

    public ListBox() {
        this(1);
    } // 생성자

    /**
     * 스크롤의 최대 위치 값을 반환한다.
     * 
     * @return 스크롤의 최대 값
     */
    public int getMaxValue() {
        JScrollBar vScrollBar = getVerticalScrollBar();
        return vScrollBar.getMaximum() - vScrollBar.getHeight();
    }

    /**
     * 스크롤이 더 이상 스크롤할 수 없는 끝 부분에 위치해있는지에 대한 여부를 반환한다.
     * 
     * @return 스크롤이 끝 부분에 있다면 {@code true}, 아니라면 {@code false}를 반환한다.
     */
    public boolean isScrollEnd() {
        return getVerticalScrollBar().getValue() == getMaxValue();
    }

    /**
     * 리스트 박스에 값을 삽입한다.
     * <p>
     * 리스트 박스를 생성할 때 지정한 {@code columns} 값에 영향을 받는다.
     * <ol>
     * <li>{@code values}의 크기가 {@code columns} 보다 크다면 {@code columns} 보다 크거나 같은 인덱스의
     * 원소는 삽입되지 않는다.
     * <li>{@code values}의 크기가 {@code columns} 보다 작다면 부족한 값은 {@code 공백; ""}으로 삽입된다.
     * </ol>
     * 
     * @param values {@code String[]} 형태의 값
     */
    public void put(String[] values) {
        // 마지막 ListContent 객체가 비어있다면 반환
        if (listContents.size() > 0) {
            if (listContents.get(listContents.size() - 1).isEmpty()) {
                return;
            }
        }

        // 열 개수에 따른 values 매개변수 가공
        String[] fixedValues = new String[columns];
        for (int i = 0; i < fixedValues.length; i++) {
            if (i < values.length) {
                fixedValues[i] = values[i];
            } else {
                fixedValues[i] = "";
            }
        }

        // ListContent 생성 및 삽입
        ListContent content = new ListContent(this, listContents.size(), fixedValues, showIndex);
        Dimension prefSize = content.getPreferredSize();
        prefSize.height = CONTENT_HEIGHT;
        content.setPreferredSize(prefSize);

        listContents.add(content);
        pnlContent.add(content);
    } // put(String[] values)

    /**
     * 리스트 박스에 값을 삽입한다.
     * <p>
     * 리스트 박스를 생성할 때 지정한 {@code columns} 값에 영향을 받는다.
     * <p>
     * {@code columns}가 1보다 크다면 {@code value}가 0번째에 삽입되고, 나머지 값은 {@code 공백; ""}으로
     * 삽입된다.
     * 
     * @param value 삽입하고자 하는 {@code String} 형태의 값
     */
    public void put(String value) {
        put(new String[] { value });
    }

    /**
     * 리스트 박스에 비어있는 값을 삽입한다.
     * <p>
     * 리스트 박스를 생성할 때 지정한 {@code columns} 값에 영향을 받는다.
     * <p>
     * {@code columns}개의 비어있는 값이 삽입된다.
     */
    public void put() {
        put(new String[] { "" });
    }

    /**
     * 리스트에 저장된 모든 값을 반환한다.
     * 
     * @return {@code String[][]} 형태의 문자열 배열
     */
    public String[][] get() {
        String[][] aryValues = new String[listContents.size()][columns];

        for (int i = 0; i < listContents.size(); i++) {
            aryValues[i] = listContents.get(i).getTexts();
        }

        return aryValues;
    }

    /**
     * 리스트에 저장된 값 중 {@code index}번째 행의 값을 반환한다.
     * 
     * @param index 반환하고자 하는 행의 인덱스
     * @return {@code String[]} 형태의 문자열 배열
     */
    public String[] get(int index) {
        try {
            return listContents.get(index).getTexts();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 리스트에 저장된 모든 항목을 반환한다.
     * @return {@code ListContent[]} 형태의 항목 배열
     */
    public ListContent[] getContent() {
        ListContent[] aryContents = new ListContent[listContents.size()];
        return listContents.toArray(aryContents);
    }

    /**
     * 리스트에 저장된 값 중 {@code index}번째 행의 항목을 반환한다.
     * @param index 반환하고자 하는 행의 인덱스
     * @return {@code ListContent} 형태의 항목 객체
     */
    public ListContent getContent(int index) {
        return listContents.get(index);
    }

    public int getLength() {
        return listContents.size();
    }

    /**
     * 리스트에서 {@code index}번째 행을 삭제한다.
     * 
     * @param index 삭제하고자 하는 행의 인덱스
     */
    public void removeContent(int index) {
        pnlContent.remove(listContents.get(index));
        listContents.remove(index);

        for (int i = index; i < listContents.size(); i++) {
            listContents.get(i).setIndex(i);
        }
        revalidate();
    }

    /**
     * 저장된 데이터에 인덱스 번호를 표시할지 설정한다.
     * 
     * @param b {@code true}라면 인덱스 번호를 리스트 박스 왼쪽에 표시한다. {@code false}라면 인덱스 번호를 숨긴다.
     */
    public void showIndex(boolean b) {
        showIndex = b;
        for (ListContent comp : listContents) {
            comp.showIndex(b);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        getVerticalScrollBar().setValue(getMaxValue());
    }
} // ListBox 클래스

/**
 * 리스트 박스에 저장되는 데이터와 상호작용 할 수 있도록 구현한 클래스이다.
 */
class ListContent extends JPanel {
    // 상수
    private final int INDEX_WIDTH;

    // 컴포넌트
    private ListBox root;
    private JPanel pnl = new JPanel();
    private Label lblIndex;
    private JTextField[] aryTextField;

    // 변수
    private int index;

    public ListContent(ListBox root, int index, String[] values, boolean showIndex) {
        super();

        // 초기화
        this.root = root;
        this.index = index;
        INDEX_WIDTH = (int) (getPreferredSize().getWidth() * 0.1d);
        aryTextField = new JTextField[values.length];
        setLayout(new GridBagLayout());

        // 인덱스 라벨
        lblIndex = new Label(Integer.toString(index + 1), SwingConstants.CENTER);
        lblIndex.setOpaque(true);
        lblIndex.setBackground(Color.LIGHT_GRAY);
        lblIndex.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblIndex, GbcFactory.createGbc(0, 0, 0.1d, 1.0d));
        lblIndex.setPreferredSize(new Dimension(INDEX_WIDTH, (int) getPreferredSize().getHeight()));
        lblIndex.setVisible(showIndex);

        // 콘텐츠 텍스트 필드
        pnl.setLayout(new GridLayout(1, 0));

        for (int i = 0; i < values.length; i++) {
            JTextField tf = new JTextField(0);
            Dimension prefferedSize = new Dimension((int) getPreferredSize().getWidth(), getHeight());

            tf.setPreferredSize(prefferedSize);
            tf.setHorizontalAlignment(JTextField.CENTER);
            tf.setText(values[i]);
            tf.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            tf.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    if (isEmpty()) {
                        root.removeContent(getIndex());
                        revalidate();
                    }
                }
            });
            tf.addKeyListener(new KeyAdapter() {
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
    } // 생성자

    /**
     * 주어진 값으로 인덱스 값을 설정한다.
     * 
     * @param index 설정하고자 하는 인덱스 값
     */
    public void setIndex(int index) {
        this.index = index;
        lblIndex.setText(Integer.toString(index + 1));
    }

    /**
     * 저장된 인덱스 값을 반환한다.
     * 
     * @return 인덱스 값
     */
    public int getIndex() {
        return index;
    }

    /**
     * 리스트 박스에 저장된 값을 변경한다.
     * <p>
     * 리스트 박스를 생성할 때 지정한 {@code columns} 값에 영향을 받는다.
     * <ol>
     * <li>{@code values}의 크기가 {@code columns} 보다 크다면 {@code columns} 보다 크거나 같은 인덱스의
     * 원소는 변경되지 않는다.
     * <li>{@code values}의 크기가 {@code columns} 보다 작다면 부족한 값은 {@code 공백; ""}으로 변경된다.
     * </ol>
     * 
     * @param values 설정하고자 하는 {@code String[]} 형태의 값
     */
    public void setTexts(String[] values) {
        for (int i = 0; i < aryTextField.length; i++) {
            String strValue = "";
            if (i < values.length) {
                strValue = values[i];
            }
            aryTextField[i].setText(strValue);
        }
    }

    /**
     * 리스트 박스에 저장된 모든 값을 반환한다.
     * 
     * @return {@code String[]} 형태의 문자열 배열
     */
    public String[] getTexts() {
        if (aryTextField.length <= 0) {
            return null;
        }

        String[] aryValue = new String[aryTextField.length];
        for (int i = 0; i < aryTextField.length; i++) {
            aryValue[i] = aryTextField[i].getText();
        }
        return aryValue;
    }

    /**
     * 리스트 박스가 비어있는지에 대한 여부를 반환한다.
     * 
     * @return 리스트 박스가 비어있다면 {@code true}를, 아니라면 {@code false}를 반환한다.
     */
    public boolean isEmpty() {
        for (JTextField tf : aryTextField) {
            if (tf.getText().isBlank() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 저장된 데이터에 인덱스 번호를 표시할지 설정한다.
     * 
     * @param b {@code true}라면 인덱스 번호를 리스트 박스 왼쪽에 표시한다. {@code false}라면 인덱스 번호를 숨긴다.
     */
    public void showIndex(boolean b) {
        lblIndex.setVisible(b);
    }

    public void setAlert(boolean b) {
        Color color = (b == true ? Color.RED : Color.BLACK);
        Color backColor = (b == true ? Color.PINK : Color.LIGHT_GRAY);
        Border border = BorderFactory.createLineBorder(color);

        lblIndex.setBackground(backColor);
        lblIndex.setForeground(color);
        lblIndex.setBorder(border);

        for (JTextField tf : aryTextField) {
            tf.setForeground(color);
            tf.setBorder(border);
        }
    }
} // ListContent 클래스

/**
 * 수정이 불가능한 라벨과 버튼을 드롭 다운 메뉴 방식으로 결합한 객체를 구현한 클래스이다.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ComboBox extends JComboBox {
    private DefaultComboBoxModel model;

    public ComboBox() {
        model = new DefaultComboBoxModel();
        setModel(model);
        setRenderer( new ComboBoxRenderer(this) );
        setEditor( new ComboBoxEditor(this) );
        setUI(new ComboBoxUI(this));
        setEditable(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * 콤보 박스에 여러 개의 값을 삽입한다.
     * 
     * @param items 삽입 할 값 들이 저장된 {@code String[][]} 객체.
     *              <p>
     *              각각의 값은 크기 2의 {@code String[]} 객체로, {@code 텍스트} 와
     *              {@code 이미지 상대 경로} 를 가진다.
     */
    public void addItems(String[][] items) {
        for (String[] k : items) {
            model.addElement(k);
        }
    }

    /**
     * 콤보 박스에 한 개의 값을 삽입한다.
     * 
     * @param item 삽입 할 값 들이 저장된 {@code String[]} 객체.
     *             <p>
     *             크기는 2이며, {@code 텍스트} 와 {@code 이미지 상대 경로} 를 가진다.
     */
    public void addItem(String[] item) {
        model.addElement(item);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ComboBoxEditor editor = (ComboBoxEditor)getEditor();
        editor.update();
    }

} // ComboBox 클래스

/**
 * 콤보 박스에 저장된 원소들을 그려주는 Renderer이다.
 */
@SuppressWarnings("rawtypes")
class ComboBoxRenderer extends JPanel implements ListCellRenderer {
    private Label lblItem = new Label();
    private ComboBox comboBox;

    public ComboBoxRenderer(ComboBox comboBox) {
        this.comboBox = comboBox;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(1, 1, 1, 1);

        lblItem.setOpaque(true);
        lblItem.setHorizontalAlignment(Label.LEFT);

        add(lblItem, constraints);
        setBackground(Color.LIGHT_GRAY);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        String[] aryItem = (String[]) value;

        // 아이콘 설정
        ImageIcon image = new ImageIcon(Main.getPath(aryItem[1]));
        lblItem.setIcon(image);

        // 텍스트 설정
        lblItem.setText(aryItem[0]);

        // 크기 설정
        lblItem.setPreferredSize(new Dimension(getWidth(), comboBox.getHeight() / 2));

        if (isSelected) {
            lblItem.setBackground(Color.LIGHT_GRAY);
            lblItem.setForeground(Color.BLACK);
        }
        else {
            lblItem.setBackground(Color.WHITE);
            lblItem.setForeground(Color.BLACK);
        }

        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lblItem.setPreferredSize(new Dimension(getWidth(), comboBox.getHeight() / 2));
    }

} // ComboBoxRenderer 클래스

/**
 * 콤보 박스의 선택된 원소를 그려주는 Editor이다.
 */
class ComboBoxEditor extends BasicComboBoxEditor {
    private JPanel pnl = new JPanel();
    private Label lblItem = new Label();
    private String selectedValue;
    private ComboBox comboBox;

    public ComboBoxEditor(ComboBox comboBox) {
        this.comboBox = comboBox;

        pnl.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(2, 5, 2, 2);

        lblItem.setOpaque(false);
        lblItem.setHorizontalAlignment(Label.LEFT);
        lblItem.setForeground(Color.BLACK);

        pnl.add(lblItem, constraints);
        pnl.setBackground(Color.LIGHT_GRAY);
    }

    public Component getEditorComponent() {
        return pnl;
    }

    public Object getItem() {
        return selectedValue;
    }

    public void setItem(Object item) {
        if (item == null) {
            return;
        }

        String[] aryItem = (String[]) item;

        lblItem.setText(aryItem[0]);

        ImageIcon image = new ImageIcon(Main.getPath(aryItem[1]));
        lblItem.setIcon(image);
    }

    public void update() {
        Color color = Color.LIGHT_GRAY;
        if ( comboBox.isEnabled() == false ) {
            color = color.darker();
        }
        pnl.setBackground(color);
    }
} // ComboBoxEditor 클래스

/**
 * 콤보 박스에 사용되는 UI 객체이다.
 */
class ComboBoxUI extends BasicComboBoxUI {
    private ComboBox cb;

    public ComboBoxUI(ComboBox cb) {
        this.cb = cb;
    }

    @Override
    protected JButton createArrowButton() {
        return new MyArrowButton();
    }

    @Override
    protected Rectangle rectangleForCurrentValue() {
        int buttonWidth = Math.min((int) ((double) cb.getWidth() * 0.1d), cb.getHeight());
        arrowButton.setBounds(cb.getWidth() - buttonWidth - 1, 0, buttonWidth, cb.getHeight());
        return super.rectangleForCurrentValue();
    }

    private class MyArrowButton extends BasicArrowButton {
        public MyArrowButton() {
            super(BasicArrowButton.SOUTH, new Color(225, 225, 225), null, Color.GRAY.darker(), null);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
    }
} // ComboBoxUI 클래스

/**
 * 데이터를 읽거나 쓰고 이를 표로 시각화할 수 있도록 구현한 클래스이다.
 */
class Chart extends JPanel {
    private static Font fontBold = new Font("Dialog", Font.BOLD, 16);

    private Label[] aryRowLabel;
    private Label[] aryColumnLabel;
    private Label[][] aryContentLabel;

    public Chart(int rows, int columns) {
        aryRowLabel = new Label[rows];
        aryColumnLabel = new Label[columns];
        aryContentLabel = new Label[rows][columns];

        setLayout(new GridBagLayout());

        double weightX = 1d / (double) (columns + 1);
        double weightY = 0.8d / (double) rows;

        for (int i = 0; i < rows + 1; i++) { // 행
            for (int j = 0; j < columns + 1; j++) { // 열
                Label lbl = new Label("", SwingConstants.CENTER);
                lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                lbl.setVisible(false);

                if (i == 0 && j == 0) { // 시작점
                    add(lbl, GbcFactory.createGbc(j, i, weightX, 0.2d));
                } else if (j == 0) { // 행 제목
                    aryRowLabel[i - 1] = lbl;
                    add(lbl, GbcFactory.createGbc(j, i, weightX, weightY));
                    setLabelTitle(lbl);
                } else if (i == 0) { // 열 제목
                    aryColumnLabel[j - 1] = lbl;
                    add(lbl, GbcFactory.createGbc(j, i, weightX, 0.2d));
                    setLabelTitle(lbl);
                } else { // 내용
                    aryContentLabel[i - 1][j - 1] = lbl;
                    add(lbl, GbcFactory.createGbc(j, i, weightX, weightY));
                    lbl.setVisible(true);
                }
            }
        }
    } // 생성자

    // 가독성 향상용 공통 속성 제어 함수
    private void setLabelTitle(Label lbl) {
        lbl.setOpaque(true);
        lbl.setBackground(Color.LIGHT_GRAY);
        lbl.setFont(fontBold);
    }

    /**
     * 표에 값을 삽입한다.
     * 
     * @param aryContent 각 열에 넣고자 하는 값을 저장한 {@code String[]} 객체를 다시 행 별로 저장한
     *                   {@code String[][]} 객체
     *                   <p>
     *                   2차원 문자열 배열로써, N행 M열의 값은 {@code aryContent[N][M]}에 위치해야 한다.
     */
    public void put(String[][] aryContent) {
        for (int i = 0; i < aryContent.length; i++) {
            for (int j = 0; j < aryContent[i].length; j++) {
                try {
                    aryContentLabel[i][j].setText(aryContent[i][j]);
                    aryContentLabel[i][j].setVisible(true);
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 표에 행 제목을 삽입한다.
     * 
     * @param aryRowTitle 각 행마다 삽입하고자 하는 제목을 저장한 {@code String[]} 객체
     */
    public void setRowTitle(String[] aryRowTitle) {
        for (int i = 0; i < aryRowLabel.length; i++) {
            try {
                aryRowLabel[i].setText(aryRowTitle[i]);
            } catch (Exception e) {
                aryRowLabel[i].setText("");
            }
            aryRowLabel[i].setVisible(true);
        }
    }

    /**
     * 표에 열 제목을 삽입한다.
     * 
     * @param aryRowTitle 각 열마다 삽입하고자 하는 제목을 저장한 {@code String[]} 객체
     */
    public void setColumnTitle(String[] aryColumnTitle) {
        for (int i = 0; i < aryColumnLabel.length; i++) {
            try {
                aryColumnLabel[i].setText(aryColumnTitle[i]);
            } catch (Exception e) {
                aryColumnLabel[i].setText("");
            }
            aryColumnLabel[i].setVisible(true);
        }
    }

    /**
     * 표의 행 제목을 나타내거나 나타내지 않는다.
     * 
     * @param b 행 제목 표시 여부
     */
    public void showRowTitle(boolean b) {
        for (int i = 0; i < aryRowLabel.length; i++) {
            aryRowLabel[i].setVisible(b);
        }
    }

    /**
     * 표의 열 제목을 나타내거나 나타내지 않는다.
     * 
     * @param b 열 제목 표시 여부
     */
    public void showColumnTitle(boolean b) {
        for (int i = 0; i < aryColumnLabel.length; i++) {
            aryColumnLabel[i].setVisible(b);
        }
    }
} // Chart 클래스

class SortingAnimation extends JPanel {
    //설정
    private static final int TIMER_PERIOD = 10;
    private static final float LERP_TIME = 0.3f;
    private static final float BAR_H_GAP_MULTIPLY = 0.1f;
    private static final float BAR_MAX_HEIGHT_MULTIPLY = 0.8f;

    private static final Color LINE_COLOR = Color.GRAY;
    private static final Color BAR_COLOR_NORMAL = Color.GRAY;
    private static final Color TEXT_COLOR = Color.BLACK;

    //수정 금지
    private static final float LERP_STEP = (float)TIMER_PERIOD * 0.0001f / LERP_TIME;

    //변수
    private final int[] aryNumber;
    private Bar[] aryBar;

    public SortingAnimation(int[] aryNumber) {
        this.aryNumber = aryNumber;
        setBorder( BorderFactory.createLineBorder(Color.BLACK) );

        aryBar = new Bar[aryNumber.length];
        for (int i=0; i<aryNumber.length; i++) {
            Bar bar = new Bar(i, aryNumber[i]);
            aryBar[i] = bar;
        }

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (Bar bar : aryBar) {
                    bar.update();
                    repaint();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, TIMER_PERIOD);
    }
    public SortingAnimation() {
        this( new int[]{} );
    } //생성자

    private class Pointf {
        protected float x;
        protected float y;

        public Pointf(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    protected class Bar {
        private int value;
        private Pointf position;
        private Pointf positionOld;
        private float lerp;

        public Bar(int index, int value) {
            this.value = value;
            position = new Pointf( (float)index, 0f );
            positionOld = new Pointf( (float)index, 0f );
            lerp = 1f;
        }

        public int getValue() {
            return value;
        }

        public void moveTo(int index, boolean hide) {
            positionOld = new Pointf(position.x, position.y);
            position.x = (float)index;
            position.y = (hide ? 1f : 0f);
            lerp = 0f;
        }

        public void update() {
            if (lerp < 1f) {
                lerp = Math.min(lerp + LERP_STEP, 1f);
            }
        }

        public Pointf getPoint() {
            return lerpPointf(positionOld, position, lerp);
        }
    } //Bar 클래스

    public void swap(int from, int to) {
        sync();
        if (from == to) {
            return;
        }

        Bar barFrom = aryBar[from];
        Bar barTo = aryBar[to];
        barFrom.moveTo(to, false);
        barTo.moveTo(from, false);

        aryBar[from] = barTo;
        aryBar[to] = barFrom;

        sleep();
    } //swap()

    public void shift(int from, int to) {
        sync();
        if (from == to) {
            return;
        }

        Bar barPicked = aryBar[from];
        barPicked.moveTo(from, true);
        sleep();

        for (int i=from+1; i<=to; i++) {
            Bar bar1 = aryBar[i-1];
            Bar bar2 = aryBar[i];

            aryBar[i-1] = bar2;
            aryBar[i] = bar1;

            bar2.moveTo(i-1, false);
        }
        for (int i=from-1; i>=to; i--) {
            Bar bar1 = aryBar[i+1];
            Bar bar2 = aryBar[i];

            aryBar[i+1] = bar2;
            aryBar[i] = bar1;

            bar2.moveTo(i+1, false);
        }
        barPicked.moveTo(to, true);
        sleep();

        barPicked.moveTo(to, false);
        sleep();
    } //shift()

    public void mergeSort(int left, int right) {
        ArrayList<Bar> listBar = new ArrayList<>();
        for (int i=left; i<=right; i++) {
            listBar.add(aryBar[i]);
        }

        listBar.sort(new Comparator<Bar>() {
            @Override
            public int compare(Bar bar1, Bar bar2) {
                if ( bar1.getValue() > bar2.getValue() ) {
                    return 1;
                }
                else if ( bar1.getValue() < bar2.getValue() ) {
                    return -1;
                }
                return 0;
            }
        });

        for (int i=0; i<listBar.size(); i++) {
            sync();
            listBar.get(i).moveTo(left + i, true);
            sleep();
        }
        sync();
        for (int i=0; i<listBar.size(); i++) {
            aryBar[left + i] = listBar.get(i);
            listBar.get(i).moveTo(left + i, false);
        }
        sleep();
    }

    public int getLength() {
        return aryBar.length;
    }

    public int getValue(int index) {
        sync();
        return aryBar[index].getValue();
    }

    public int[] getValues() {
        int[] aryData = new int[aryBar.length];
        for(int i=0; i<aryData.length; i++) {
            aryData[i] = aryBar[i].getValue();
        }
        return aryData;
    }

    public Bar getBar(int index) {
        return aryBar[index];
    }

    public Bar[] getBars() {
        return aryBar;
    }

    public int getMaxData() {
        if ( aryBar.length <= 0 ) {
            return 0;
        }

        int max = aryBar[0].getValue();
        for (int i=1; i<aryBar.length; i++) {
            if (aryBar[i].getValue() > max) {
                max = aryBar[i].getValue();
            }
        }
        return max;
    }

    private Pointf lerpPointf(Pointf pointFrom, Pointf pointTo, float lerp) {
        float fixedLerp = Main.clamp(lerp, 0f, 1f);

        Pointf pointDelta = new Pointf(pointTo.x, pointTo.y);
        pointDelta.x -= pointFrom.x;
        pointDelta.y -= pointFrom.y;

        pointDelta.x *= fixedLerp;
        pointDelta.y *= fixedLerp;

        Pointf pointLerp = pointFrom;
        pointLerp.x += pointDelta.x;
        pointLerp.y += pointDelta.y;

        return pointLerp;
    }

    public void reset() {
        for (int i=0; i<aryNumber.length; i++) {
            aryBar[i] = new Bar(i, aryNumber[i]);
        }
        revalidate();
    }

    private void sleep() {
        try {
            Thread.sleep( (int)(LERP_TIME * 1100f) );
        }
        catch (Exception e) {}
    }

    private void sync() {
        while(SortManager.paused) {
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //구분선 그리기
        float maxY = 0f;
        for (int i=0; i<aryBar.length; i++) {
            maxY = Math.max(maxY, aryBar[i].getPoint().y);
        }
        float lineYMul = 0.7f - maxY * 0.2f;

        int lineY = (int)( (float)getHeight() * lineYMul );
        g.setColor(LINE_COLOR);
        g.fillRect(0, lineY, getWidth(), 3);

        //막대 그리기
        if ( aryBar.length <= 0 ) {
            return;
        }

        int barWidth = getWidth() / aryBar.length;
        int barHGap = (int)( (float)barWidth * BAR_H_GAP_MULTIPLY );
        int barFixedWidth = barWidth - barHGap * 2;

        int maxHeight = (int)( (float)lineY * BAR_MAX_HEIGHT_MULTIPLY );
        int barVGap = lineY - maxHeight;
        int maxData = getMaxData();
        int barHeightMul = 0;
        try {
            barHeightMul = maxHeight / maxData;
        }
        catch (Exception e) {}

        int fontSize = barFixedWidth / 2;
        Font font = new Font("Dialog", Font.BOLD, fontSize);

        for (int i=0; i<aryBar.length; i++) {
            Bar bar = aryBar[i];
            int barHeight = bar.getValue() * barHeightMul;

            int barYMul = maxHeight + barVGap;

            Pointf point = bar.getPoint();
            int barX = (int)(point.x * barWidth);
            int barY = lineY - barHeight + (int)(point.y * barYMul);

            g.setColor(BAR_COLOR_NORMAL);
            g.fillRect(barX + barHGap, barY, barFixedWidth, barHeight);
            g.setColor( BAR_COLOR_NORMAL.darker() );
            g.drawRect(barX + barHGap, barY, barFixedWidth, barHeight);

            //숫자 그리기
            String strValue = Integer.toString( bar.getValue() );
            int fontOffset = (int)Math.ceil((strValue.length()-1) * fontSize * 0.25d);
            int textX = barX + barFixedWidth / 2 - fontOffset;
            int textY = barY + barHeight - 10;
            g.setColor(TEXT_COLOR);
            g.setFont(font);
            g.drawString(Integer.toString( bar.getValue() ), textX, textY);
        }
    }
} //SortingAnimation 클래스

class TabbedPane extends JTabbedPane {
    private static final Color COLOR = new Color(220, 220, 220);

    public TabbedPane() {
        UIManager.put("TabbedPane.selected", Color.LIGHT_GRAY.darker() );
        setBackground( Color.LIGHT_GRAY );
        UIManager.put("TabbedPane.contentAreaColor", COLOR);
        UIManager.put("TabbedPane.shadow", COLOR);
        setUI( new javax.swing.plaf.basic.BasicTabbedPaneUI() );
        setBorder( BorderFactory.createLineBorder(Color.GRAY) );
    }
} //TabbedPane 클래스

/**
 * 팝업 메시지 박스를 구현한 클래스이다.
 */
class MessageBox extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	//버튼 타입 id
	static final int btnOK = 1;
	static final int btnOK_CANCEL = 2;
	static final int btnYES_NO = 3;
	//버튼 클릭 id
	static final int idOK = 1;
	static final int idCANCEL = 2;
	static final int idYES = 3;
	static final int idNO = 4;
	//아이콘 id
	static final int iconINFORMATION = 1;
	static final int iconQUESTION = 2;
	static final int iconEXCLAMATION = 3;
	static final int iconERROR = 4;
	
	private final ImageIcon iconInformation = new ImageIcon( Main.getPath("/Images/Information.png") );
	private final ImageIcon iconQuestion = new ImageIcon( Main.getPath("/Images/Question.png") );
	private final ImageIcon iconExclamation = new ImageIcon( Main.getPath("/Images/Exclamation.png") );
	private final ImageIcon iconError = new ImageIcon( Main.getPath("/Images/Error.png") );
	
	private final Font font = new Font("Dialog", Font.PLAIN, 15);
	
	private JPanel pnlSouth = new JPanel();
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlCenterMain = new JPanel();
	private MessageButton btnOk = new MessageButton("확인");
	private MessageButton btnCancel = new MessageButton("취소");
	private MessageButton btnYes = new MessageButton("예");
	private MessageButton btnNo = new MessageButton("아니오");
	
	private JFrame frame;
	private JDialog dialog;
	private int answer = 0;
	private String msg;
	private int btnType;
	private int iconType;
	
	/**
     * JFrame 위에 메시지 박스를 생성한다.
     * @param frame {@code JFrame} 형태의 부모 프레임 객체
     * @param msg 표시할 문자열
     * @param btnType 표시할 버튼 레이아웃 종류
     * @param iconType 표시할 아이콘 레이아웃 종류
     * @see {@link #btnOK}, {@link #btnOK_CANCEL}, {@link #btnYES_NO}
     * @see {@link #iconINFORMATION}, {@link #iconQUESTION}, {@link #iconEXCLAMATION}, {@link #iconERROR}
     */
	public MessageBox(JFrame frame, String msg, int btnType, int iconType) {
		super(frame, frame.getTitle(), true);
		this.frame = frame;
		this.msg = msg;
		this.btnType = btnType;
		this.iconType = iconType;
		setLocation( frame.getLocation() );
		draw();
	}
	
	/**
     * JDialog 위에 메시지 박스를 생성한다.
     * @param dialog {@code JDialog} 형태의 부모 프레임 객체
     * @param msg 표시할 문자열
     * @param btnType 표시할 버튼 레이아웃 종류
     * @param iconType 표시할 아이콘 레이아웃 종류
     * @see {@link #btnOK}, {@link #btnOK_CANCEL}, {@link #btnYES_NO}
     * @see {@link #iconINFORMATION}, {@link #iconQUESTION}, {@link #iconEXCLAMATION}, {@link #iconERROR}
     */
	public MessageBox(JDialog dialog, String msg, int btnType, int iconType) {
		super(dialog, dialog.getTitle(), true);
		this.dialog = dialog;
		this.msg = msg;
		this.btnType = btnType;
		this.iconType = iconType;
		setLocation( dialog.getLocation() );
		draw();
	}

    /**
     * 메시지 박스에 사용되는 버튼을 구현한 클래스이다.
     */
    private class MessageButton extends JButton {
        public MessageButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
        }

        /**
         * 버튼의 UI를 구성하는 Graphics 객체를 반환한다.
         * @param g {@code Graphics} 객체
         * @return 버튼의 UI를 구성하는 {@code Graphics2D}객체
         */
        public Graphics2D getGraphics2D(Graphics g) {
            ButtonModel model = getModel();
            Color color1 = Color.LIGHT_GRAY;
            Color color2 = Color.GRAY;
    
            if (model.isPressed()) { // 눌렀을 때
                color1 = color1.darker();
                color2 = color2.darker();
            } else if (model.isRollover()) { // 커서를 올려뒀을 때
                color1 = color1.brighter();
                color2 = color2.brighter();
            }
    
            // 그라데이션 재질 적용
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(color1);
    
            return g2;
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = getGraphics2D(g);
            g2.fillRect(0, 0, getSize().width, getSize().height);
            super.paintComponent(g2);
        }

        @Override
        public void paintBorder(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
        }
    } //MessageButton 클래스

	/**
	 * 메시지 박스 대화상자를 그린다.
	 */
	public void draw() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JPanel pnlBase = new JPanel();
		pnlBase.setLayout( new BorderLayout() );
		pnlBase.setBackground(null);
		add(pnlBase);
		
		//버튼 객체 설정
		MessageButton[] aryBtn = { btnOk, btnCancel, btnYes, btnNo };
		for (int i=0; i<aryBtn.length; i++) {
			Dimension size = new Dimension(80, 30);
			aryBtn[i].setPreferredSize(size);
			aryBtn[i].setMinimumSize(size);
			aryBtn[i].setFont(font);
			aryBtn[i].addActionListener(this);
		}

		//버튼
		pnlBase.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setLayout( new FlowLayout(FlowLayout.CENTER, 5, 10) );
		pnlSouth.setBackground( new Color(220, 220, 220) );
		switch (btnType) {
			case btnOK:
				pnlSouth.add(btnOk);
				break;
			case btnOK_CANCEL:
				pnlSouth.add(btnOk);
				pnlSouth.add(btnCancel);
				break;
			case btnYES_NO:
				pnlSouth.add(btnYes);
				pnlSouth.add(btnNo);
				break;
			default:
				return;
		}
		
		//메시지
		pnlCenterMain.setLayout( new BorderLayout(5, 0) );
		pnlCenterMain.setBorder( BorderFactory.createEmptyBorder(10, 20, 10, 20) );
		pnlBase.add(pnlCenterMain, BorderLayout.CENTER);
		
		String[] aryMsg = msg.split("\n");
		pnlCenter.setLayout( new GridLayout(aryMsg.length, 1, 0, 0) );
		pnlCenterMain.add(pnlCenter, BorderLayout.CENTER);
		
		for (int i=0; i<aryMsg.length; i++) {
			JLabel lbl = new JLabel(aryMsg[i], SwingConstants.LEFT);
			lbl.setFont(font);
			pnlCenter.add(lbl);
		}
		
		pack();
		
		//아이콘
		Image icon = null;
		switch (iconType) {
			case iconINFORMATION:
				icon = iconInformation.getImage();
				break;
			case iconQUESTION:
				icon = iconQuestion.getImage();
				break;
			case iconEXCLAMATION:
				icon = iconExclamation.getImage();
				break;
			case iconERROR:
				icon = iconError.getImage();
				break;
			default:
				return;
		}
		
		int size = pnlCenterMain.getHeight();
		icon = icon.getScaledInstance(size, size, Image.SCALE_SMOOTH);
		JLabel lblIcon = new JLabel( new ImageIcon(icon) );
		lblIcon.setPreferredSize( new Dimension(size, size) );
		pnlCenterMain.add(lblIcon, BorderLayout.WEST);
		
		pack();
		
		//대화상자를 화면 정중앙에 위치
		int x = 0;
		int y = 0;
		Window wnd;
		if (frame != null) { //부모가 JFrame
			wnd = frame;
		}
		else { //부모가 JDialog
			wnd = dialog;
		}
		x = wnd.getX() + (int)(wnd.getWidth() * 0.5) - (int)(getWidth() * 0.5);
		y = wnd.getY() + (int)(wnd.getHeight() * 0.5) - (int)(getHeight() * 0.5);
		setLocation(x, y);
		
		setVisible(true);
	} //draw()
	
	/**
	 * 메시지 박스 대화상자를 생성한다.
	 * @param frame - 대화상자를 귀속시킬 JFrame 프레임
	 * @param msg - 표시할 메시지
	 * @param btnType - 표시할 버튼 레이아웃
	 * @param iconType - 표시할 아이콘
	 * @return 클릭한 버튼의 id
     * @see {@link #btnOK}, {@link #btnOK_CANCEL}, {@link #btnYES_NO}
     * @see {@link #iconINFORMATION}, {@link #iconQUESTION}, {@link #iconEXCLAMATION}, {@link #iconERROR}
     * @see {@link #idOK}, {@link #idCANCEL}, {@link #idYES}, {@link #idNO}
	 */
	public static int show(JFrame frame, String msg, int btnType, int iconType) {
		MessageBox ob = new MessageBox(frame, msg, btnType, iconType);
		return ob.answer;
	}
	
	/**
	 * 메시지 박스 대화상자를 생성한다.
	 * @param frame - 대화상자를 귀속시킬 JFrame 프레임
	 * @param msg - 표시할 메시지
	 * @param btnType - 표시할 버튼 레이아웃
	 * @return 클릭한 버튼의 id
     * @see {@link #btnOK}, {@link #btnOK_CANCEL}, {@link #btnYES_NO}
     * @see {@link #idOK}, {@link #idCANCEL}, {@link #idYES}, {@link #idNO}
	 */
	public static int show(JFrame frame, String msg, int btnType) {
		return show(frame, msg, btnType, iconINFORMATION);
	}
	
	/**
	 * 메시지 박스 대화상자를 생성한다.
	 * @param frame - 대화상자를 귀속시킬 JFrame 프레임
	 * @param msg - 표시할 메시지
	 * @return 클릭한 버튼의 id
     * @see {@link #idOK}, {@link #idCANCEL}, {@link #idYES}, {@link #idNO}
	 */
	public static int show(JFrame frame, String msg) {
		return show(frame, msg, btnOK, iconINFORMATION);
	}
	
	/**
	 * 메시지 박스 대화상자를 생성한다.
	 * @param dialog - 대화상자를 귀속시킬 JDialog 프레임
	 * @param msg - 표시할 메시지
	 * @param btnType - 표시할 버튼 레이아웃
	 * @param iconType - 표시할 아이콘
	 * @return 클릭한 버튼의 id
     * @see {@link #btnOK}, {@link #btnOK_CANCEL}, {@link #btnYES_NO}
     * @see {@link #iconINFORMATION}, {@link #iconQUESTION}, {@link #iconEXCLAMATION}, {@link #iconERROR}
     * @see {@link #idOK}, {@link #idCANCEL}, {@link #idYES}, {@link #idNO}
	 */
	public static int show(JDialog dialog, String msg, int btnType, int iconType) {
		MessageBox ob = new MessageBox(dialog, msg, btnType, iconType);
		return ob.answer;
	}
	
	/**
	 * 메시지 박스 대화상자를 생성한다.
	 * @param dialog - 대화상자를 귀속시킬 JDialog 프레임
	 * @param msg - 표시할 메시지
	 * @param btnType - 표시할 버튼 레이아웃
	 * @return 클릭한 버튼의 id
     * @see {@link #btnOK}, {@link #btnOK_CANCEL}, {@link #btnYES_NO}
     * @see {@link #idOK}, {@link #idCANCEL}, {@link #idYES}, {@link #idNO}
	 */
	public static int show(JDialog dialog, String msg, int btnType) {
		return show(dialog, msg, btnType, iconINFORMATION);
	}
	
	/**
	 * 메시지 박스 대화상자를 생성한다.
	 * @param dialog - 대화상자를 귀속시킬 JDialog 프레임
	 * @param msg - 표시할 메시지
	 * @return 클릭한 버튼의 id
     * @see {@link #idOK}, {@link #idCANCEL}, {@link #idYES}, {@link #idNO}
	 */
	public static int show(JDialog dialog, String msg) {
		return show(dialog, msg, btnOK, iconINFORMATION);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals(btnOk) ) {
			answer = idOK;
		}
		else if ( e.getSource().equals(btnCancel) ) {
			answer = idCANCEL;
		}
		else if ( e.getSource().equals(btnYes) ) {
			answer = idYES;
		}
		else if ( e.getSource().equals(btnNo) ) {
			answer = idNO;
		}
		this.dispatchEvent( new WindowEvent(this, WindowEvent.WINDOW_CLOSING) );
	}
} //MessageBox 클래스

/**
 * 스크롤 바를 통해 스크롤이 가능한 화면을 표시하는 스크롤 패널을 구현한 클래스이다.
 */
class ScrollPane extends JScrollPane {
    private static final int SPEED = 20;
    
    public ScrollPane(Component view) {
        super(view);
        setHorizontalScrollBar( new ScrollBar(JScrollBar.HORIZONTAL) );
        setVerticalScrollBar( new ScrollBar(JScrollBar.VERTICAL) );

        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getHorizontalScrollBar().setUnitIncrement(SPEED);
        getVerticalScrollBar().setUnitIncrement(SPEED);
    }

    /**
     * 스크롤 바를 구현하는 클래스이다.
     */
    private class ScrollBar extends JScrollBar {
        public ScrollBar(int orientation) {
            setOrientation(orientation);
            setUI( new MyScrollPaneUI() );
            setPreferredSize( new Dimension(20, 20) );
            setForeground(Color.BLACK);
            setBackground(Color.LIGHT_GRAY);
        }

        /**
         * 스크롤 바의 UI를 구성하는 클래스이다.
         */
        private class MyScrollPaneUI extends BasicScrollBarUI {
            private static final Color colorTrack = new Color(210, 210, 210);
            private static final Color COLOR_THUMB_NORM = new Color(180, 180 ,180);
            private static final Color COLOR_THUMB_HOVER = new Color(160, 160 ,160);
            private static final Color COLOR_THUMB_DRAG = new Color(120, 120 ,120);

            @Override
            protected void paintTrack(Graphics g, JComponent comp, Rectangle rect) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
                g2.setColor(colorTrack);
                g2.fillRect(rect.x, rect.y, rect.width, rect.height);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent comp, Rectangle rect) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int x = rect.x;
                int y = rect.y;
                int width = rect.width;
                int height = rect.height;

                if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                    x += 2;
                    width -= 4;
                }
                else {
                    y += 2;
                    height -= 4;
                }

                Color colorThumb = COLOR_THUMB_NORM;
                if (isDragging) {
                    colorThumb = COLOR_THUMB_DRAG;
                }
                else if ( isThumbRollover() ) {
                    colorThumb = COLOR_THUMB_HOVER;
                }

                g2.setColor(colorThumb);
                g2.fillRect(x, y, width, height);
            }

            private JButton createButton(int orientation) {
                BasicArrowButton arrowButton = new BasicArrowButton( orientation, Color.LIGHT_GRAY, Color.GRAY.darker(),
                    Color.GRAY, Color.LIGHT_GRAY.brighter() );
                arrowButton.setPreferredSize( new Dimension(20, 20) );
                return arrowButton;
            }

            @Override
            protected JButton createIncreaseButton(int i) {
                return createButton(i);
            }
    
            @Override
            protected JButton createDecreaseButton(int i) {
                return createButton(i);
            }
        } //MyScrollPaneUI 클래스
    } //ScrollBar 클래스

} //ScrollPane 클래스