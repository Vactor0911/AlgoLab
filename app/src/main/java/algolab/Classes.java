package algolab;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class GbcFactory {
    private static GridBagConstraints gbc = new GridBagConstraints();

    /**
     * 주어진 속성으로 설정된 {@code GridBagConstraints} 객체를 반환한다.
     * 
     * @param x       객체가 위치할 열 번호
     * @param y       객체가 위치할 행 번호
     * @param weightX 열이 차지할 비율
     * @param weightY 행이 차지할 비율
     * @param width   객체가 차지할 열 개수
     * @param height  객체가 차지할 행 개수
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
     * 
     * @param x       객체가 위치할 열 번호
     * @param y       객체가 위치할 행 번호
     * @param weightX 열이 차지할 비율
     * @param weightY 행이 차지할 비율
     * @return {@code GridBagConstraints} 객체
     */
    public static GridBagConstraints createGbc(int x, int y, double weightX, double weightY) {
        return createGbc(x, y, weightX, weightY, 1, 1);
    }

    /**
     * 주어진 속성으로 설정된 {@code GridBagConstraints} 객체를 반환한다.
     * 
     * @param x      객체가 위치할 열 번호
     * @param y      객체가 위치할 행 번호
     * @param width  객체가 차지할 열 개수
     * @param height 객체가 차지할 행 개수
     * @return {@code GridBagConstraints} 객체
     */
    public static GridBagConstraints createGbc(int x, int y, int width, int height) {
        return createGbc(x, y, 1, 1, width, height);
    }

    /**
     * 주어진 속성으로 설정된 {@code GridBagConstraints} 객체를 반환한다.
     * 
     * @param x 객체가 위치할 열 번호
     * @param y 객체가 위치할 행 번호
     * @return {@code GridBagConstraints} 객체
     */
    public static GridBagConstraints createGbc(int x, int y) {
        return createGbc(x, y, 1, 1, 1, 1);
    }
} // GbcFactory 클래스

/**
 * 객체를 세로로 배치할 수 있는 배치 관리자이다.
 */
class VerticalLayout implements LayoutManager {
    private final Dimension MINIMUM_SIZE = new Dimension();
    private int gapY;

    /**
     * 객체를 세로로 배치할 수 있는 배치 관리자이다.
     * 
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
        Dimension dimension = new Dimension(getTotalPaddingWidth(parent), getTotalPaddingHeight(parent));
        MINIMUM_SIZE.height = dimension.height;
        for (Component c : parent.getComponents()) {
            if (!c.isVisible()) {
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
        for (Component c : parent.getComponents()) {
            if (c.isVisible()) {
                c.setBounds(padding.left, y, width, c.getPreferredSize().height);
                y += c.getHeight() + gapY;
            }
        }
    }
} // VerticalLayout 클래스

/**
 * DocumentListener 리스너를 상속받는 클래스이다.
 * 필요한 메소드만 오버라이드하여 사용한다.
 */
class DocumentAdapter implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
} // DocumentAdapter 클래스

/**
 * FocusListener 리스너를 상속받는 클래스이다.
 * 필요한 메소드만 오버라이드하여 사용한다.
 */
class FocusAdapter implements FocusListener {
    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
    }
} // FocusAdapter 클래스

/**
 * 여러 알고리즘의 세부 정보가 저장된 클래스이다.
 */
class Algorithms {
    protected static final Algorithm BUBBLE_SORT = new Algorithm(
            "버블 정렬",
            "<html>버블 정렬이란?<br>1. 배열의 두 수(𝑎, 𝑏)를 선택한다.<br>2. 만약 그 두 수가 정렬되었다면 놔두고 아니라면 두 수를 바꾸는 방식으로 진행한다.</html>",
            new Algorithm.Code(
                    "버블 정렬 의사코드",
                    
                    "void bubbleSort(int arr[], int n) {\n" +
                        "\tfor (int i = 0; i < n-1; i++) {\n" +
                            "\t\tfor (int j = 0; j < n-i-1; j++) {\n" +
                                "\t\t\tif (arr[j] > arr[j+1]) {\n" +
                                    "\t\t\t\tint temp = arr[j];\n" +
                                    "\t\t\t\tarr[j] = arr[j+1];\n" +
                                    "\t\t\t\tarr[j+1] = temp;\n" +
                                "\t\t\t}\n" +
                            "\t\t}\n" +
                        "\t}\n" +
                    "}",

                    "void bubbleSort(std::vector<int> &arr) {\n" +
                        "\tint n = arr.size();\n" +
                        "\tfor (int i = 0; i < n - 1; ++i) {\n" +
                            "\t\tfor (int j = 0; j < n - i - 1; ++j) {\n" +
                                "\t\t\tif (arr[j] > arr[j + 1]) {\n" +
                                    "\t\t\t\tstd::swap(arr[j], arr[j + 1]);\n" +
                                "\t\t\t}\n" +
                            "\t\t}\n" +
                        "\t}\n" +
                    "}",

                    "public class BubbleSort {\n" +
                        "\tpublic static void bubbleSort(int[] arr) {\n" +
                            "\t\tint n = arr.length;\n" +
                            "\t\tfor (int i = 0; i < n - 1; ++i) {\n" +
                                "\t\t\tfor (int j = 0; j < n - i - 1; ++j) {\n" +
                                    "\t\t\t\tif (arr[j] > arr[j + 1]) {\n" +
                                        "\t\t\t\t\t\tint temp = arr[j];\n" +
                                        "\t\t\t\t\t\tarr[j] = arr[j + 1];\n" +
                                        "\t\t\t\t\t\tarr[j + 1] = temp;\n" +
                                    "\t\t\t\t}\n" +
                                "\t\t\t}\n" +
                            "\t\t}\n" +
                        "\t}\n" +
                    "}",

                    "def bubble_sort(arr):\n" +
                        "\tn = len(arr)\n" +
                        "\tfor i in range(n - 1):\n" +
                            "\t\tfor j in range(n - i - 1):\n" +
                                "\t\t\tif arr[j] > arr[j + 1]:\n" +
                                    "\t\t\t\tarr[j], arr[j + 1] = arr[j + 1], arr[j]"

            ),
            new Algorithm.TimeComplexity(
                    "버블 정렬 최선 시간복잡도",
                    "버블 정렬 최악 시간복잡도",
                    "버블 정렬 평균 시간복잡도"));

    protected static final Algorithm SELECTION_SORT = new Algorithm(
            "선택 정렬",
            "<html>선택 정렬이란?<br/>1. 배열에서 최솟값을 찾는다.<br/>2. 최솟값을 맨 앞에 위치한 값과 교체한다.<br/>맨 처음 위치를 뺀 나머지 리스트를 같은 방법으로 교체한다.</html>",
            new Algorithm.Code(
                    "선택 정렬 의사코드",
                    
                    "void selectionSort(int arr[], int n) {\n" +
                        "\tint i, j, min_idx;\n" +
                        "\tfor (i = 0; i < n-1; i++) {\n" +
                            "\t\tmin_idx = i;\n" +
                                "\t\tfor (j = i+1; j < n; j++) {\n" +
                                    "\t\t\tif (arr[j] < arr[min_idx]) {\n" +
                                        "\t\t\t\tmin_idx = j;\n" +
                                    "\t\t\t}\n" +
                                "}\t\t\n" +
                            "\t\tint temp = arr[min_idx];\n" +
                            "\t\tarr[min_idx] = arr[i];\n" +
                            "\t\tarr[i] = temp;\n" +
                        "\t}\n" +
                    "}",

                    "void selectionSort(std::vector<int> &arr) {\n" +
                        "\tint n = arr.size();\n" +
                        "\tfor (int i = 0; i < n - 1; ++i) {\n" +
                            "\t\tint min_idx = i;\n" +
                            "\t\tfor (int j = i + 1; j < n; ++j) {\n" +
                                "\t\t\tif (arr[j] < arr[min_idx]) {\n" +
                                    "\t\t\t\tmin_idx = j;\n" +
                                "\t\t\t}\n" +
                            "\t\t}\n" +
                            "\t\tstd::swap(arr[i], arr[min_idx]);\n" +
                        "\t}\n" +
                    "}",

                    "public static void selectionSort(int[] arr) {\n" +
                        "\tint n = arr.length;;\n" +
                        "\tfor (int i = 0; i < n - 1; ++i) {;\n" +
                            "\t\tint min_idx = i;;\n" +
                            "\t\tfor (int j = i + 1; j < n; ++j) {\n" +
                                "\t\t\tif (arr[j] < arr[min_idx]) {\n" +
                                    "\t\t\t\tmin_idx = j;\n" +
                                "\t\t\t}\n" +
                            "\t\t}\n" +
                            "\t\tint temp = arr[min_idx];\n" +
                            "\t\tarr[min_idx] = arr[i];\n" +
                            "\t\tarr[i] = temp;\n" +
                        "\t}\n" +
                    "}",

                    "def selection_sort(arr):\n" +
                        "\tn = len(arr)\n" +
                        "\tfor i in range(n - 1):\n" +
                            "\t\tmin_idx = i\n" +
                            "\t\tfor j in range(i + 1, n):\n" +
                                "\t\t\tif arr[j] < arr[min_idx]:\n" +
                                    "\t\t\t\tmin_idx = j\n" +
                            "\t\tarr[i], arr[min_idx] = arr[min_idx], arr[i]"),
            new Algorithm.TimeComplexity(
                    "선택 정렬 최선 시간복잡도",
                    "선택 정렬 최악 시간복잡도",
                    "선택 정렬 평균 시간복잡도"));

    protected static final Algorithm INSERTION_SORT = new Algorithm(
            "삽입 정렬",
            "<html>삽입 정렬이란?<br>1. 배열의 모든 요소를 앞에서부터 이미 정렬된 배열 부분과 비교한다.<br>2. 자신의 위치를 찾아 삽입하면서 정렬을 완성한다.</html>",
            new Algorithm.Code(
                    "삽입 정렬 의사코드",

                    "void insertionSort(int arr[], int n) {\n" +
                        "\tint i, key, j;\n" +
                        "\tfor (i = 1; i < n; i++) {\n" +
                            "\t\tkey = arr[i];\n" +
                            "\t\tj = i - 1;\n" +
                            "\t\twhile (j >= 0 && arr[j] > key) {\n" +
                                "\t\t\tarr[j + 1] = arr[j];\n" +
                                "\t\t\tj = j - 1;\n" +
                            "\t\t}\n" +
                        "\tarr[j + 1] = key;\n" +
                        "\t}\n" +
                    "}",

                    "void insertionSort(std::vector<int> &arr) {\n" +
                        "\tint n = arr.size();\n" +
                        "\tfor (int i = 1; i < n; i++) {\n" +
                            "\t\tint key = arr[i];\n" +
                            "\t\tint j = i - 1;\n" +
                            "\t\twhile (j >= 0 && arr[j] > key) {\n" +
                                "\t\t\tarr[j + 1] = arr[j];\n" +
                                "\t\t\tj = j - 1;\n" +
                            "\t\t}\n" +
                            "\t\tarr[j + 1] = key;\n" +
                        "\t}\n" +
                    "}",

                    "public static void insertionSort(int[] arr) {\n" +
                        "\tint n = arr.length;\n" +
                        "\tfor (int i = 1; i < n; i++) {\n" +
                            "\t\tint key = arr[i];\n" +
                            "\t\tint j = i - 1;\n" +
                            "\t\twhile (j >= 0 && arr[j] > key) {\n" +
                                "\t\t\tarr[j + 1] = arr[j];\n" +
                                "\t\t\tj = j - 1;\n" +
                            "\t\t}\n" +
                            "\t\tarr[j + 1] = key;\n" +
                        "\t}\n" +
                    "}",

                    "def insertion_sort(arr):\n" +
                        "\tn = len(arr)\n" +
                        "\tfor i in range(1, n):\n" +
                            "\t\tkey = arr[i]\n" +
                            "\t\tj = i - 1\n" +
                            "\t\twhile j >= 0 and arr[j] > key:\n" +
                                "\t\t\tarr[j + 1] = arr[j]\n" +
                                "\t\t\tj = j - 1\n" +
                                "\t\t\tarr[j + 1] = key"),
            new Algorithm.TimeComplexity(
                    "삽입 정렬 최선 시간복잡도",
                    "삽입 정렬 최악 시간복잡도",
                    "삽입 정렬 평균 시간복잡도"));

    protected static final Algorithm QUICK_SORT = new Algorithm(
            "퀵 정렬",
            "<html>퀵 정렬이란?<br>1. 배열 가운데에서 원소(피벗)을 고른다.<br>2. 피벗 앞에는 피벗보다 작은 값이 오고 뒤에는 큰 값이 오도록 피벗을 기준으로 배열을 둘로 나눈다(분할 작업).<br>3. 2(분할 작업)의 작업을 재귀적으로 반복하여 배열의 크기가 0이나 1이 될 때까지 정렬한다.</html>",
            new Algorithm.Code(
                    "퀵 정렬 의사코드",

                    "void swap(int* a, int* b) {\n" +
                        "\tint t = *a;\n" +
                        "\t*a = *b;\n" +
                        "\t*b = t;\n" +
                    "}\n" +
                    "int partition(int arr[], int low, int high) {\n" +
                        "\tint pivot = arr[high];\n" +
                        "\tint i = low - 1;\n" +
                        "\n" +
                        "\tfor (int j = low; j < high; j++) {\n" +
                            "\t\tif (arr[j] < pivot) {\n" +
                            "\t\ti++;\n" +
                            "\t\tswap(&arr[i], &arr[j]);\n" +
                            "\t\t}\n" +
                        "\t}\n" +
                        "\tswap(&arr[i + 1], &arr[high]);\n" +
                        "\treturn (i + 1);\n" +
                    "}\n" +
                    "\n" +
                    "void quickSort(int arr[], int low, int high) {\n" +
                        "\tif (low < high) {\n" +
                            "\t\tint pi = partition(arr, low, high);\n" +
                            "\t\tquickSort(arr, low, pi - 1);\n" +
                            "\t\tquickSort(arr, pi + 1, high);\n" +
                        "\t}\n" +
                    "}\n",

                    "void swap(int& a, int& b) {\n" +
                        "\tint t = a;\n" +
                        "\ta = b;\n" +
                        "\tb = t;\n" +
                    "}\n" +
                    "int partition(int arr[], int low, int high) {\n" +
                        "\tint pivot = arr[high];\n" +
                        "\tint i = low - 1;\n" +
                        "\n" +
                        "\tfor (int j = low; j < high; j++) {\n" +
                            "\t\tif (arr[j] < pivot) {\n" +
                            "\t\ti++;\n" +
                            "\t\tswap(arr[i], arr[j]);\n" +
                            "\t\t}\n" +
                        "\t}\n" +
                        "\tswap(arr[i + 1], arr[high]);\n" +
                        "\treturn (i + 1);\n" +
                    "}\n" +
                    "\n" +
                    "void quickSort(int arr[], int low, int high) {\n" +
                        "\tif (low < high) {\n" +
                            "\t\tint pi = partition(arr, low, high);\n" +
                            "\t\tquickSort(arr, low, pi - 1);\n" +
                            "\t\tquickSort(arr, pi + 1, high);\n" +
                        "\t}\n" +
                    "}",

                    "public static void swap(int[] arr, int i, int j) {\n" +
                        "\tint temp = arr[i];\n" +
                        "\tarr[i] = arr[j];\n" +
                        "\tarr[j] = temp;\n" +
                    "}\n" +
                    "\n" +
                    "public static int partition(int[] arr, int low, int high) {\n" +
                        "\tint pivot = arr[high];\n" +
                        "\tint i = low - 1;\n" +
                        "\tfor (int j = low; j < high; j++) {\n" +
                            "\t\tif (arr[j] < pivot) {\n" +
                                "\t\t\ti++;\n" +
                                "\t\t\tswap(arr, i, j);\n" +
                            "\t\t}\n" +
                        "\t}\n" +
                        "\tswap(arr, i + 1, high);\n" +
                        "\treturn i + 1;\n" +
                    "}\n" +
                    "\n" +
                    "public static void quickSort(int[] arr, int low, int high) {\n" +
                        "\tif (low < high) {\n" +
                            "\t\tint pi = partition(arr, low, high);\n" +
                            "\t\tquickSort(arr, low, pi - 1);\n" +
                            "\t\tquickSort(arr, pi + 1, high);\n" +
                        "\t}\n" +
                    "}\n",

                    "def partition(arr, low, high):\n" +
                        "\tpivot = arr[high];\n" +
                        "\ti = low - 1;\n" +
                        "\tfor j in range(low, high):\n" +
                            "\t\tif arr[j] < pivot:\n" +
                                "\t\t\ti += 1\n" +
                                "\t\t\tarr[i], arr[j] = arr[j], arr[i]\n" +
                                "\t\t\tarr[i + 1], arr[high] = arr[high], arr[i + 1]\n" +
                            "\t\treturn i + 1\n" +
                            "\n" +
                    "def quickSort(arr, low, high):\n" +
                        "\tif low < high:\n" +
                            "\t\tpi = partition(arr, low, high)\n" +
                            "\t\tquickSort(arr, low, pi - 1)\n" +
                            "\t\tquickSort(arr, pi + 1, high)"),
            new Algorithm.TimeComplexity(
                    "퀵 정렬 최선 시간복잡도",
                    "퀵 정렬 최악 시간복잡도",
                    "퀵 정렬 평균 시간복잡도"));

    protected static final Algorithm MERGE_SORT = new Algorithm(
            "병합 정렬",
            "<html>병합 정렬이란?<br>1. 정렬되지 않은 배열을 하나의 원소만 포함하는 n개의 부분 배열로 분할한다.<br>2. 부분 배열이 하나만 남을 때까지 계속하여 반복하여 정렬된 배열을 생성한다.</html>",
            new Algorithm.Code(
                    "병합 정렬 의사코드",

                    "void merge(int arr[], int l, int m, int r) {\n" +
                        "\tint i, j, k;\n" +
                        "\tint n1 = m - l + 1;\n" +
                        "\tint n2 = r - m;\n" +
                        "\n" +
                        "\tint L[n1], R[n2];\n" +
                        "\n" +
                        "\tfor (i = 0; i < n1; i++)\n" +
                            "\t\tL[i] = arr[l + i];\n" +
                        "\tfor (j = 0; j < n2; j++)\n" +
                            "\t\tR[j] = arr[m + 1 + j];\n" +
                        "\n" +
                        "\ti = 0;\n" +
                        "\tj = 0;\n" +
                        "\tk = l;\n" +
                        "\twhile (i < n1 && j < n2) {\n" +
                            "\t\tif (L[i] <= R[j]) {\n" +
                                "\t\t\tarr[k] = L[i];\n" +
                                "\t\t\ti++;\n" +
                            "\t\t} else {\n" +
                                "\t\t\tarr[k] = R[j];\n" +
                                "\t\t\tj++;\n" +
                            "\t\t}\n" +
                            "\t\tk++;\n" +
                        "\t}\n" +
                        "\n" +
                        "\twhile (i < n1) {\n" +
                            "\t\tarr[k] = L[i];\n" +
                            "\t\ti++;\n" +
                            "\t\tk++;\n" +
                        "\t}\n" +
                        "\n" +
                        "\twhile (j < n2) {\n" +
                            "\t\tarr[k] = R[j];\n" +
                            "\t\tj++;\n" +
                            "\t\tk++;\n" +
                        "\t}\n" +
                    "}",

                    "void merge(vector<int>& arr, int l, int m, int r) {\n" +
                        "\tint i, j, k;\n" +
                        "\tint n1 = m - l + 1;\n" +
                        "\tint n2 = r - m;\n" +
                        "\n" +
                        "\tvector<int> L(n1), R(n2);\n" +
                        "\n" +
                        "\tfor (i = 0; i < n1; i++)\n" +
                            "\t\tL[i] = arr[l + i];\n" +
                        "\tfor (j = 0; j < n2; j++)\n" +
                            "\t\tR[j] = arr[m + 1 + j];\n" +
                        "\n" +
                        "\ti = 0;\n" +
                        "\tj = 0;\n" +
                        "\tk = l;\n" +
                        "\twhile (i < n1 && j < n2) {\n" +
                            "\t\tif (L[i] <= R[j]) {\n" +
                                "\t\t\tarr[k] = L[i]\n;" +
                                "\t\t\ti++;\n" +
                            "\t\t} else {\n" +
                                "\t\t\tarr[k] = R[j];\n" +
                                "\t\t\tj++;\n" +
                            "\t\t}\n" +
                            "\t\tk++;\n" +
                        "\t}\n" +
                        "\n" +
                        "\twhile (i < n1) {\n" +
                            "\t\tarr[k] = L[i];\n" +
                            "\t\ti++;\n" +
                            "\t\tk++;\n" +
                        "\t}\n" +
                        "\n" +
                        "\twhile (j < n2) {\n" +
                            "\t\tarr[k] = R[j];\n" +
                            "\t\tj++;\n" +
                            "\t\tk++;\n" +
                        "\t}\n" +
                    "}\n" +
                    "\n" +
                    "void mergeSort(vector<int>& arr, int l, int r) {\n" +
                        "\tif (l < r) {\n" +
                            "\t\tint m = l + (r - l) / 2;\n" +
                            "\n" +
                            "\t\tmergeSort(arr, l, m);\n" +
                            "\t\tmergeSort(arr, m + 1, r);\n" +
                            "\n" +
                            "\t\tmerge(arr, l, m, r);\n" +
                        "\t}\n" +
                    "}",

                    "public static void merge(int arr[], int l, int m, int r) {\n" +
                        "\tint n1 = m - l + 1;\n" +
                        "\tint n2 = r - m;\n" +
                        "\n" +
                        "\tint L[] = new int[n1];\n" +
                        "\tint R[] = new int[n2];\n" +
                        "\n" +
                        "\tfor (int i = 0; i < n1; ++i)\n" +
                            "\t\tL[i] = arr[l + i];\n" +
                        "\tfor (int j = 0; j < n2; ++j)\n" +
                            "\t\tR[j] = arr[m + 1 + j];\n" +
                        "\n" +
                        "\tint i = 0, j = 0;\n" +
                        "\tint k = l;\n" +
                        "\twhile (i < n1 && j < n2) {\n" +
                            "\t\tif (L[i] <= R[j]) {\n" +
                                "\t\t\tarr[k] = L[i];\n" +
                                "\t\t\ti++;\n" +
                            "\t\t} else {\n" +
                                "\t\t\tarr[k] = R[j];\n" +
                                "\t\t\tj++;\n" +
                            "\t\t}\n" +
                            "\t\tk++;\n" +
                            "\t}\n" +
                            "\n" +
                        "\twhile (i < n1) {\n" +
                                "\t\tarr[k] = L[i];\n" +
                                "\t\ti++;\n" +
                                "\t\tk++;\n" +
                        "\t}\n" +
                        "\n" +
                        "\twhile (j < n2) {\n" +
                                "\t\tarr[k] = R[j];\n" +
                                "\t\tj++;\n" +
                                "\t\tk++;\n" +
                        "\t}\n" +
                    "}\n" +
                    "\n" +
                    "public static void mergeSort(int arr[], int l, int r) {\n" +
                        "\tif (l < r) {\n" +
                            "\t\tint m = (l + r) / 2;\n" +
                            "\n" +
                            "\t\tmergeSort(arr, l, m);\n" +
                            "\t\tmergeSort(arr, m + 1, r);\n" +
                            "\n" +
                            "\t\tmerge(arr, l, m, r);\n" +
                        "\t}\n" +
                    "}",

                    "def merge(arr, l, m, r):\n" +
                        "\tn1 = m - l + 1\n" +
                        "\tn2 = r - m\n" +
                        "\n" +
                        "\tL = arr[l:m + 1]\n" +
                        "\tR = arr[m + 1:r + 1]\n" +
                        "\n" +
                        "\ti = 0\n" +
                        "\tj = 0\n" +
                        "\tk = l\n" +
                        "\n" +
                        "\twhile i < n1 and j < n2:\n" +
                            "\t\tif L[i] <= R[j]:\n" +
                                "\t\t\tarr[k] = L[i]\n" +
                                "\t\t\ti += 1\n" +
                            "\t\telse:\n" +
                                "\t\t\tarr[k] = R[j]\n" +
                                "\t\t\tj += 1\n" +
                            "\t\tk += 1\n" +
                            "\n" +
                            "\twhile i < n1:\n" +
                                "\t\tarr[k] = L[i]\n" +
                                "\t\ti += 1\n" +
                                "\t\tk += 1\n" +
                            "\n" +
                            "\twhile j < n2:\n" +
                                "\t\tarr[k] = R[j]\n" +
                                "\t\tj += 1\n" +
                                "\t\tk += 1\n" +
                            "\n" +
                    "def merge_sort(arr, l, r):\n" +
                        "\tif l < r:\n" +
                            "\t\tm = (l + r) // 2\n" +
                            "\n" +
                            "\t\tmerge_sort(arr, l, m)\n" +
                            "\t\tmerge_sort(arr, m + 1, r)\n" +
                            "\n" +
                            "\t\tmerge(arr, l, m, r)"),
            new Algorithm.TimeComplexity(
                    "병합 정렬 최선 시간복잡도",
                    "병합 정렬 최악 시간복잡도",
                    "병합 정렬 평균 시간복잡도"));

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
         * 
         * @param name           알고리즘의 이름
         * @param definition     알고리즘의 정의
         * @param code           {@code Code} 형태의 알고리즘 예시 코드
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
             * 
             * @param pseudo 의사코드
             * @param c      C 언어 코드
             * @param cpp    C++ 언어 코드
             * @param java   Java 언어 코드
             * @param python Python 언어 코드
             */
            private Code(String pseudo, String c, String cpp, String java, String python) {
                PSEUDO = pseudo;
                C = c;
                CPP = cpp;
                JAVA = java;
                PYTHON = python;
            }
        } // Code 클래스

        /**
         * 알고리즘의 세부 정보 중 시간복잡도의 구조를 구현한 클래스이다.
         */
        protected static class TimeComplexity {
            protected final String BEST;
            protected final String WORST;
            protected final String AVERAGE;

            /**
             * 새로운 시간복잡도 데이터를 생성한다.
             * 
             * @param best    최선 시간복잡도
             * @param worst   최악 시간복잡도
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

/**
 * JLabel에서 줄바꿈과 들여쓰기를 구현하기 위해 문자열을 HTML 문법으로 파싱하는 클래스이다.
 */
class CodeParser {
    public static final int INDENT_SIZE = 4;

    /**
     * JLabel에 삽입되는 문자열에 포함된 \n과 \t 문자를 HTML 문법으로 변환하여 출력한다.
     * @param code {@code \n}과 {@code \t}가 포함된 JLabel 텍스트
     * <ul>
     * <li>{@code \n} : 줄 바꿈을 의미하는 문자이다.
     * <li>{@code \t} : 한 칸 들여쓰기를 의미하는 문자이다.
     * </ul>
     * @return HTML 문법의 코드
     */
    public static String parseCode(String code) {
        String result = code;
        String strIndent = "";
        for (int i=0; i<INDENT_SIZE; i++) {
            strIndent += "&nbsp;";
        }

        result = result.replace("\n", "<br>");
        result = result.replace("\t", strIndent);
        
        return "<html><body>" + result + "</body></html>";
    }
} //CodeParser 클래스

/**
 * 정렬 애니메이션을 구동시키기 위한 작동부가 구현된 클래스이다.
 */
class SortManager {
    public static final int BUBBLE_SORT = 1;
    public static final int SELECTION_SORT = 2;
    public static final int INSERTION_SORT = 3;
    public static final int QUICK_SORT = 4;
    public static final int MERGE_SORT = 5;

    private SortingAnimation animation;
    private SortingRunnable runnable;

    public SortManager(SortingAnimation animation, int sortType) {
        this.animation = animation;
        switch (sortType) {
            case BUBBLE_SORT:
                runnable = new BubbleSort(animation);
                break;
            default:
                break;
        }
    }

    public void start() {
        new Thread(runnable).start();
    }

    public void pause() {
        runnable.pause();
    }

    public void resume() {
        runnable.resume();
    }

    public void stop() {
        runnable.stop();
    }

    private abstract class SortingRunnable implements Runnable {
        protected volatile boolean isRunning = true;
        protected volatile boolean paused = false;
        protected final Object pauseLock = new Object();

        protected SortingAnimation animation;

        public SortingRunnable(SortingAnimation animation) {
            this.animation = animation;
        }

        @Override
        public abstract void run();

        public void pause() {
            paused = true;
        }

        public void resume() {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll();
            }
        }

        public void stop() {
            isRunning = false;
            resume();
        }
    } //SortAlgoritm 클래스

    private class BubbleSort extends SortingRunnable {

        public BubbleSort(SortingAnimation anim) {
            super(anim);
        }

        @Override
        public void run() {
            int i = 0;
            while (isRunning) {
                synchronized (pauseLock) {
                    if (!isRunning) {
                        break;
                    }
                    if (paused) {
                        try {
                            pauseLock.wait();
                        } catch (InterruptedException ex) {
                            break;
                        }
                        if (!isRunning) {
                            break;
                        }
                    }
                }
    
                //구현부
                int n = animation.getLength();
                if (i < n-1) {
                    for (int j = 0; j < n-i-1; j++) {
                        if ( animation.getValue(j) > animation.getValue(j+1) ) {
                            animation.swap(j, j+1);
                        }
                    }
                }
                i++;
            }
        }
    } //BubbleSort 클래스
    
} //SortManager 클래스

// int n = array.length;
// for (int i = 0; i < n-1; i++) {
//     for (int j = 0; j < n-i-1; j++) {
//         if (array[j] > array[j+1]) {
//             // 현재 요소와 다음 요소의 위치를 바꿉니다
//             int temp = array[j];
//             array[j] = array[j+1];
//             array[j+1] = temp;
//         }
//     }
// }