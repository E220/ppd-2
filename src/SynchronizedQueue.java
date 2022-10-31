import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedQueue<T> {
    private final ArrayList<T> array;
    private final int capacity;
    private int size = 0;
    private int index = 0;

    private final Lock mutex = new ReentrantLock();
    private final Condition notEmpty = mutex.newCondition();
    private final Condition notFull = mutex.newCondition();

    public SynchronizedQueue(int capacity) {
        this.capacity = capacity;
        this.array = new ArrayList<>(capacity);
    }

    public void enqueue(T item) {
        this.mutex.lock();
        try {
            while(this.size == this.capacity) this.notFull.await();
            this.array.set(index, item);
            this.size++;
            this.index = (this.index + 1) % this.capacity;
            this.notEmpty.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.mutex.unlock();
        }
    }

    public T dequeue() {
        this.mutex.lock();
        try {
            while (this.size == 0) {
                this.notEmpty.await();
            }
            int index = (this.index - this.size) % this.capacity;
            if (index < 0) index += this.capacity;
            final T item = this.array.get(index);
            this.size--;
            this.notFull.signal();
            return item;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.mutex.unlock();
        }
    }
}
