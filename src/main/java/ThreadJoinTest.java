/**
 * Created by shanguang.wang on 2019-04-03
 */
public class ThreadJoinTest {

    public static void main(String[] args) throws Exception {
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child thread is begin.");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("child thread is end.");
            }
        });
        childThread.start();
        childThread.join();
        System.out.println("main thread is completed");
    }
}
