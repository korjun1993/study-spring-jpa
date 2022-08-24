package hello.core.singleton;

public class ClassLoaderTest {
    public static void main(String[] args) {

    }
}

class Single {
    // 1. 생성자
    public Single() {}

    // 2. 정적 변수 - final x

    // 3. 정적 변수 - final o

    // 4. 정적 메서드
    public static void getInstance() {}

    // 5. 정적 내부 클래스
    public static class Holder {
        public static Single instance;
    }
}
