import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shanguang.wang on 2019-04-10
 */
public class ConditionTest {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void conditionWait() {
        try {
            lock.lock();
            System.out.println("conditionWait begin.");
            condition.await();
            System.out.println("conditionWait end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() {
        try {
            lock.lock();
            System.out.println("conditionSignal begin.");
            condition.signal();
            System.out.println("conditionSignal end.");
            Thread.sleep(1000);
            System.out.println("conditionSignal lock exit.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionTest conditionTest = new ConditionTest();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                conditionTest.conditionWait();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                conditionTest.conditionSignal();
            }
        });
        t1.start();
        Thread.sleep(2000);
        t2.start();
    }
}
