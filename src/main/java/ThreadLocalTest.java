/**
 * Created by shanguang.wang on 2019-04-03
 */
public class ThreadLocalTest {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static ThreadLocal<String> threadLocalOne = new ThreadLocal<String>();

    public static ThreadLocal<String> threadLocalTwo = new ThreadLocal<String>();

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        for (int i = 0; i < 10; i++) {
            final int temp = i;
            final Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        threadLocal.set(String.valueOf(temp));
                        Thread.sleep(1000);
                        System.out.println("thread:" + temp + "-" + threadLocal.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        threadLocal.remove();
                    }
                }
            });
            t.start();
        }
    }

}
