package algolab;

import java.net.URL;

public class Main {
    private static Frame frame;

    public static void main(String[] args) {
        frame = new Frame();
    }
    
    /**
     * 리소스의 상대 경로를 입력받아 절대 경로를 반환한다.
     * @param path - 원하는 리소스의 파일 경로
     * @return {@link java.net.URL} 객체; 경로에서 찾을 수 있는 리소스가 없을 경우 {@code null} 반환
     * @throws NullPointerException {@code path}가 {@code null}인 경우
     */
    public static URL getPath(String path) {
        return Main.class.getResource(path);
    }

    public static Frame getFrame() {
        return frame;
    }

    /**
     * 특정한 숫자에 상한선과 하한선을 설정한 결과 값을 반환한다.
     * <p>결과 값의 범위 : min <= n <= max
     * @param n 상한선과 하한선을 설정하고자 하는 숫자
     * @param min 최소값 (하한선)
     * @param max 최대값 (상한선)
     * @return 상한선과 하한선 사이에 존재하는 숫자
     */
    public static double clamp(double n, double min, double max) {
        if (n < min) {
            return min;
        }
        else if (n > max) {
            return max;
        }
        return n;
    }

    /**
     * 특정한 숫자에 상한선과 하한선을 설정한 결과 값을 반환한다.
     * <p>결과 값의 범위 : min <= n <= max
     * @param n 상한선과 하한선을 설정하고자 하는 숫자
     * @param min 최소값 (하한선)
     * @param max 최대값 (상한선)
     * @return 상한선과 하한선 사이에 존재하는 숫자
     */
    public static float clamp(float n, float min, float max) {
        return (float)clamp( (double)n, (double)min, (double)max );
    }

    /**
     * 특정한 숫자에 상한선과 하한선을 설정한 결과 값을 반환한다.
     * <p>결과 값의 범위 : min <= n <= max
     * @param n 상한선과 하한선을 설정하고자 하는 숫자
     * @param min 최소값 (하한선)
     * @param max 최대값 (상한선)
     * @return 상한선과 하한선 사이에 존재하는 숫자
     */
    public static int clamp(int n, int min, int max) {
        return (int)clamp( (double)n, (double)min, (double)max );
    }
    
}