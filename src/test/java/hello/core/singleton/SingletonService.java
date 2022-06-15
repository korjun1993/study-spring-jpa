package hello.core.singleton;

public class SingletonService {
//    private static final SingletonService instance = new SingletonService();
//
//    public static SingletonService getInstance() {
//        return instance;
//    }
//
//    private SingletonService() {
//        // 외부에서 생성자 호출하는 것을 막는다
//    }
//
//    public void logic() {
//        System.out.println("싱글톤 객체 로직 호출");
//    }
//    private SingletonService() {}

    private static SingletonService instance;

    static {
        System.out.println("haha");
        instance = new SingletonService();
    }

    private SingletonService() {

    }

    public static SingletonService getInstance() {
        return instance;
    }
}