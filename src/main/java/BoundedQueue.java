import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shanguang.wang on 2019-04-10
 */
public class BoundedQueue<T> {

    private Object[] items;

    private int head;

    private int tail;

    private int count;

    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size) {
        items = new Object[size];
    }

    private void add(T t) throws InterruptedException {
        lock.lock();
        try {
            if (count == items.length) {
                notFull.await();
            }
            items[head] = t;
            if (++head == items.length) {
                head = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    private T remove() throws InterruptedException {
        lock.lock();
        try {
            if (count == 0) {
                notEmpty.await();
            }
            Object x = items[tail];
            if (++tail == items.length) {
                tail = 0;
            }
            --count;
            notFull.signal();
            return (T) x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BoundedQueue<Integer> boundedQueue = new BoundedQueue<>(5);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        boundedQueue.add(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        System.out.println(boundedQueue.remove());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }

}
