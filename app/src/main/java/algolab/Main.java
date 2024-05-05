package algolab;

import java.net.URL;

public class Main {

    public static void main(String[] args) {
        new Frame();
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
    
}
