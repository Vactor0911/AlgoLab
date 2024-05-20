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

                    "void bubbleSort(int arr[], int n) {" +
                            "for (int i = 0; i < n-1; i++) {" +
                            "for (int j = 0; j < n-i-1; j++) {" +
                            "if (arr[j] > arr[j+1]) {" +
                            "int temp = arr[j];" +
                            "arr[j] = arr[j+1];" +
                            "arr[j+1] = temp;" +
                            "}" +
                            "}" +
                            "}" +
                            "}",

                    "void bubbleSort(std::vector<int> &arr) {" +
                            "int n = arr.size();" +
                            "for (int i = 0; i < n - 1; ++i) {" +
                            "for (int j = 0; j < n - i - 1; ++j) {" +
                            "if (arr[j] > arr[j + 1]) {" +
                            "std::swap(arr[j], arr[j + 1]);" +
                            "}" +
                            "}" +
                            "}" +
                            "}",

                    "public class BubbleSort {" +
                            "public static void bubbleSort(int[] arr) {" +
                            "int n = arr.length;" +
                            "for (int i = 0; i < n - 1; ++i) {" +
                            "for (int j = 0; j < n - i - 1; ++j) {" +
                            "if (arr[j] > arr[j + 1]) {" +
                            "int temp = arr[j];" +
                            "arr[j] = arr[j + 1];" +
                            "arr[j + 1] = temp;" +
                            "}" +
                            "}" +
                            "}" +
                            "}" +
                            "}",

                    "def bubble_sort(arr):" +
                            "n = len(arr)" +
                            "for i in range(n - 1):" +
                            "for j in range(n - i - 1):" +
                            "if arr[j] > arr[j + 1]:" +
                            "arr[j], arr[j + 1] = arr[j + 1], arr[j]"

            ),
            new Algorithm.TimeComplexity(
                    "버블 정렬 최선 시간복잡도",
                    "버블 정렬 최악 시간복잡도",
                    "버블 정렬 평균 시간복잡도"));

    protected static final Algorithm SELECTION_SORT = new Algorithm(
            "선택 정렬",
            "<html>선택 정렬이란?<br>1. 배열에서 최솟값을 찾는다.<br>2. 최솟값을 맨 앞에 위치한 값과 교체한다.<br>맨 처음 위치를 뺀 나머지 리스트를 같은 방법으로 교체한다.</html>",
            new Algorithm.Code(
                    "선택 정렬 의사코드",
                    
                    "void selectionSort(int arr[], int n) {" +
                            "int i, j, min_idx;" +
                            "for (i = 0; i < n-1; i++) {" +
                            "min_idx = i;" +
                            "for (j = i+1; j < n; j++) {" +
                            "if (arr[j] < arr[min_idx]) {" +
                            "min_idx = j;" +
                            "}" +
                            "}" +
                            "int temp = arr[min_idx];" +
                            "arr[min_idx] = arr[i];" +
                            "arr[i] = temp;" +
                            "}" +
                            "}",

                    "void selectionSort(std::vector<int> &arr) {" +
                            "int n = arr.size();" +
                            "for (int i = 0; i < n - 1; ++i) {" +
                            "int min_idx = i;" +
                            "for (int j = i + 1; j < n; ++j) {" +
                            "if (arr[j] < arr[min_idx]) {" +
                            "min_idx = j;" +
                            "}" +
                            "}" +
                            "std::swap(arr[i], arr[min_idx]);" +
                            "}" +
                            "}",

                    "public static void selectionSort(int[] arr) {" +
                            "int n = arr.length;" +
                            "for (int i = 0; i < n - 1; ++i) {" +
                            "int min_idx = i;" +
                            "for (int j = i + 1; j < n; ++j) {" +
                            "if (arr[j] < arr[min_idx]) {" +
                            "min_idx = j;" +
                            "}" +
                            "}" +
                            "int temp = arr[min_idx];" +
                            "arr[min_idx] = arr[i];" +
                            "arr[i] = temp;" +
                            "}" +
                            "}",

                    "def selection_sort(arr):" +
                            "n = len(arr)" +
                            "for i in range(n - 1):" +
                            "min_idx = i" +
                            "for j in range(i + 1, n):" +
                            "if arr[j] < arr[min_idx]:" +
                            "min_idx = j" +
                            "arr[i], arr[min_idx] = arr[min_idx], arr[i]"),
            new Algorithm.TimeComplexity(
                    "선택 정렬 최선 시간복잡도",
                    "선택 정렬 최악 시간복잡도",
                    "선택 정렬 평균 시간복잡도"));

    protected static final Algorithm INSERTION_SORT = new Algorithm(
            "삽입 정렬",
            "<html>삽입 정렬이란?<br>1. 배열의 모든 요소를 앞에서부터 이미 정렬된 배열 부분과 비교한다.<br>2. 자신의 위치를 찾아 삽입하면서 정렬을 완성한다.</html>",
            new Algorithm.Code(
                    "삽입 정렬 의사코드",

                    "void insertionSort(int arr[], int n) {" +
                            "int i, key, j;" +
                            "for (i = 1; i < n; i++) {" +
                            "key = arr[i];" +
                            "j = i - 1;" +
                            "while (j >= 0 && arr[j] > key) {" +
                            "arr[j + 1] = arr[j];" +
                            "j = j - 1;" +
                            "}" +
                            "arr[j + 1] = key;" +
                            "}" +
                            "}",

                    "void insertionSort(std::vector<int> &arr) {" +
                            "int n = arr.size();" +
                            "for (int i = 1; i < n; i++) {" +
                            "int key = arr[i];" +
                            "int j = i - 1;" +
                            "while (j >= 0 && arr[j] > key) {" +
                            "arr[j + 1] = arr[j];" +
                            "j = j - 1;" +
                            "}" +
                            "arr[j + 1] = key;" +
                            "}" +
                            "}",

                    "public static void insertionSort(int[] arr) {" +
                            "int n = arr.length;" +
                            "for (int i = 1; i < n; i++) {" +
                            "int key = arr[i];" +
                            "int j = i - 1;" +
                            "while (j >= 0 && arr[j] > key) {" +
                            "arr[j + 1] = arr[j];" +
                            "j = j - 1;" +
                            "}" +
                            "arr[j + 1] = key;" +
                            "}" +
                            "}",

                    "def insertion_sort(arr):" +
                            "n = len(arr)" +
                            "for i in range(1, n):" +
                            "key = arr[i]" +
                            "j = i - 1" +
                            "while j >= 0 and arr[j] > key:" +
                            "arr[j + 1] = arr[j]" +
                            "j = j - 1" +
                            "arr[j + 1] = key"),
            new Algorithm.TimeComplexity(
                    "삽입 정렬 최선 시간복잡도",
                    "삽입 정렬 최악 시간복잡도",
                    "삽입 정렬 평균 시간복잡도"));

    protected static final Algorithm QUICK_SORT = new Algorithm(
            "퀵 정렬",
            "<html>퀵 정렬이란?<br>1. 배열 가운데에서 원소(피벗)을 고른다.<br>2. 피벗 앞에는 피벗보다 작은 값이 오고 뒤에는 큰 값이 오도록 피벗을 기준으로 배열을 둘로 나눈다(분할 작업).<br>3. 2(분할 작업)의 작업을 재귀적으로 반복하여 배열의 크기가 0이나 1이 될 때까지 정렬한다.</html>",
            new Algorithm.Code(
                    "퀵 정렬 의사코드",

                    "void swap(int* a, int* b) {" +
                            "int t = *a;" +
                            "*a = *b;" +
                            "*b = t;" +
                            "}" +
                            "" +
                            "int partition(int arr[], int low, int high) {" +
                            "int pivot = arr[high];" +
                            "int i = low - 1;" +
                            "" +
                            "for (int j = low; j < high; j++) {" +
                            "if (arr[j] < pivot) {" +
                            "i++;" +
                            "swap(&arr[i], &arr[j]);" +
                            "}" +
                            "}" +
                            "swap(&arr[i + 1], &arr[high]);" +
                            "return (i + 1);" +
                            "}" +
                            "" +
                            "void quickSort(int arr[], int low, int high) {" +
                            "if (low < high) {" +
                            "int pi = partition(arr, low, high);" +
                            "quickSort(arr, low, pi - 1);" +
                            "quickSort(arr, pi + 1, high);" +
                            "}" +
                            "}",

                    "void swap(int& a, int& b) {" +
                            "int t = a;" +
                            "a = b;" +
                            "b = t;" +
                            "}" +
                            "" +
                            "int partition(int arr[], int low, int high) {" +
                            "int pivot = arr[high];" +
                            "int i = low - 1;" +
                            "" +
                            "for (int j = low; j < high; j++) {" +
                            "if (arr[j] < pivot) {" +
                            "i++;" +
                            "swap(arr[i], arr[j]);" +
                            "}" +
                            "}" +
                            "swap(arr[i + 1], arr[high]);" +
                            "return (i + 1);" +
                            "}" +
                            "" +
                            "void quickSort(int arr[], int low, int high) {" +
                            "if (low < high) {" +
                            "int pi = partition(arr, low, high);" +
                            "quickSort(arr, low, pi - 1);" +
                            "quickSort(arr, pi + 1, high);" +
                            "}" +
                            "}",

                    "public static void swap(int[] arr, int i, int j) {" +
                            "int temp = arr[i];" +
                            "arr[i] = arr[j];" +
                            "arr[j] = temp;" +
                            "}" +
                            "" +
                            "public static int partition(int[] arr, int low, int high) {" +
                            "int pivot = arr[high];" +
                            "int i = low - 1;" +
                            "for (int j = low; j < high; j++) {" +
                            "if (arr[j] < pivot) {" +
                            "i++;" +
                            "swap(arr, i, j);" +
                            "}" +
                            "}" +
                            "swap(arr, i + 1, high);" +
                            "return i + 1;" +
                            "}" +
                            "" +
                            "public static void quickSort(int[] arr, int low, int high) {" +
                            "if (low < high) {" +
                            "int pi = partition(arr, low, high);" +
                            "quickSort(arr, low, pi - 1);" +
                            "quickSort(arr, pi + 1, high);" +
                            "}" +
                            "}" +
                            "}",

                    "def partition(arr, low, high):" +
                            "pivot = arr[high];" +
                            "i = low - 1;" +
                            "for j in range(low, high):" +
                            "if arr[j] < pivot:" +
                            "i += 1" +
                            "arr[i], arr[j] = arr[j], arr[i]" +
                            "arr[i + 1], arr[high] = arr[high], arr[i + 1]" +
                            "return i + 1" +
                            "" +
                            "def quickSort(arr, low, high):" +
                            "if low < high:" +
                            "pi = partition(arr, low, high)" +
                            "quickSort(arr, low, pi - 1)" +
                            "quickSort(arr, pi + 1, high)"),
            new Algorithm.TimeComplexity(
                    "퀵 정렬 최선 시간복잡도",
                    "퀵 정렬 최악 시간복잡도",
                    "퀵 정렬 평균 시간복잡도"));

    protected static final Algorithm MERGE_SORT = new Algorithm(
            "병합 정렬",
            "<html>병합 정렬이란?<br>1. 정렬되지 않은 배열을 하나의 원소만 포함하는 n개의 부분 배열로 분할한다.<br>2. 부분 배열이 하나만 남을 때까지 계속하여 반복하여 정렬된 배열을 생성한다.</html>",
            new Algorithm.Code(
                    "병합 정렬 의사코드",

                    "void merge(int arr[], int l, int m, int r) {" +
                            "int i, j, k;" +
                            "int n1 = m - l + 1;" +
                            "int n2 = r - m;" +
                            "" +
                            "int L[n1], R[n2];" +
                            "" +
                            "for (i = 0; i < n1; i++)" +
                            "L[i] = arr[l + i];" +
                            "for (j = 0; j < n2; j++)" +
                            "R[j] = arr[m + 1 + j];" +
                            "" +
                            "i = 0;" +
                            "j = 0;" +
                            "k = l;" +
                            "while (i < n1 && j < n2) {" +
                            "if (L[i] <= R[j]) {" +
                            "arr[k] = L[i];" +
                            "i++;" +
                            "} else {" +
                            "arr[k] = R[j];" +
                            "j++;" +
                            "}" +
                            "k++;" +
                            "}" +
                            "" +
                            "while (i < n1) {" +
                            "arr[k] = L[i];" +
                            "i++;" +
                            "k++;" +
                            "}" +
                            "" +
                            "while (j < n2) {" +
                            "arr[k] = R[j];" +
                            "j++;" +
                            "k++;" +
                            "}" +
                            "}",

                    "void merge(vector<int>& arr, int l, int m, int r) {" +
                            "int i, j, k;" +
                            "int n1 = m - l + 1;" +
                            "int n2 = r - m;" +
                            "" +
                            "vector<int> L(n1), R(n2);" +
                            "" +
                            "for (i = 0; i < n1; i++)" +
                            "L[i] = arr[l + i];" +
                            "for (j = 0; j < n2; j++)" +
                            "R[j] = arr[m + 1 + j];" +
                            "" +
                            "i = 0;" +
                            "j = 0;" +
                            "k = l;" +
                            "while (i < n1 && j < n2) {" +
                            "if (L[i] <= R[j]) {" +
                            "arr[k] = L[i];" +
                            "i++;" +
                            "} else {" +
                            "arr[k] = R[j];" +
                            "j++;" +
                            "}" +
                            "k++;" +
                            "}" +
                            "" +
                            "while (i < n1) {" +
                            "arr[k] = L[i];" +
                            "i++;" +
                            "k++;" +
                            "}" +
                            "" +
                            "while (j < n2) {" +
                            "arr[k] = R[j];" +
                            "j++;" +
                            "k++;" +
                            "}" +
                            "}" +
                            "" +
                            "void mergeSort(vector<int>& arr, int l, int r) {" +
                            "if (l < r) {" +
                            "int m = l + (r - l) / 2;" +
                            "" +
                            "mergeSort(arr, l, m);" +
                            "mergeSort(arr, m + 1, r);" +
                            "" +
                            "merge(arr, l, m, r);" +
                            "}" +
                            "}",

                    "public class MergeSort {" +
                            "public static void merge(int arr[], int l, int m, int r) {" +
                            "int n1 = m - l + 1;" +
                            "int n2 = r - m;" +
                            "" +
                            "int L[] = new int[n1];" +
                            "int R[] = new int[n2];" +
                            "" +
                            "for (int i = 0; i < n1; ++i)" +
                            "L[i] = arr[l + i];" +
                            "for (int j = 0; j < n2; ++j)" +
                            "R[j] = arr[m + 1 + j];" +
                            "" +
                            "int i = 0, j = 0;" +
                            "int k = l;" +
                            "while (i < n1 && j < n2) {" +
                            "if (L[i] <= R[j]) {" +
                            "arr[k] = L[i];" +
                            "i++;" +
                            "} else {" +
                            "arr[k] = R[j];" +
                            "j++;" +
                            "}" +
                            "k++;" +
                            "}" +
                            "" +
                            "while (i < n1) {" +
                            "arr[k] = L[i];" +
                            "i++;" +
                            "k++;" +
                            "}" +
                            "" +
                            "while (j < n2) {" +
                            "arr[k] = R[j];" +
                            "j++;" +
                            "k++;" +
                            "}" +
                            "}" +
                            "" +
                            "public static void mergeSort(int arr[], int l, int r) {" +
                            "if (l < r) {" +
                            "int m = (l + r) / 2;" +
                            "" +
                            "mergeSort(arr, l, m);" +
                            "mergeSort(arr, m + 1, r);" +
                            "" +
                            "merge(arr, l, m, r);" +
                            "}" +
                            "}",

                    "def merge(arr, l, m, r):" +
                            "n1 = m - l + 1" +
                            "n2 = r - m" +
                            "" +
                            "L = arr[l:m + 1]" +
                            "R = arr[m + 1:r + 1]" +
                            "" +
                            "i = 0" +
                            "j = 0" +
                            "k = l" +
                            "" +
                            "while i < n1 and j < n2:" +
                            "if L[i] <= R[j]:" +
                            "arr[k] = L[i]" +
                            "i += 1" +
                            "else:" +
                            "arr[k] = R[j]" +
                            "j += 1" +
                            "k += 1" +
                            "" +
                            "while i < n1:" +
                            "arr[k] = L[i]" +
                            "i += 1" +
                            "k += 1" +
                            "" +
                            "while j < n2:" +
                            "arr[k] = R[j]" +
                            "j += 1" +
                            "k += 1" +
                            "" +
                            "def merge_sort(arr, l, r):" +
                            "if l < r:" +
                            "m = (l + r) // 2" +
                            "" +
                            "merge_sort(arr, l, m)" +
                            "merge_sort(arr, m + 1, r)" +
                            "" +
                            "merge(arr, l, m, r)"),
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
        } // TimeComplexity 클래스
    } // Algorithm 클래스
} // Algorithms 클래스