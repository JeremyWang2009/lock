/**
 * Created by shanguang.wang on 2019-04-02
 */
public class ThreadPriority {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 start");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2 start");
            }
        });
        t1.setPriority(10);
        t1.setPriority(1);
        t1.start();
        t2.start();
        System.out.println("main start");

    }


}
