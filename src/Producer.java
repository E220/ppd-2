import java.util.List;

public class Producer implements Runnable {

    private final SynchronizedQueue<Integer> queue;

    private final List<Integer> array_a;
    private final List<Integer> array_b;

    public Producer(SynchronizedQueue<Integer> queue, List<Integer> array_a, List<Integer> array_b) {
        this.queue = queue;
        this.array_a = array_a;
        this.array_b = array_b;
    }

    @Override
    public void run() {
        if (array_a.size() != array_b.size()) {
            return;
        }
        for (int i = 0; i < array_a.size(); i++) {
            queue.enqueue(array_a.get(i) * array_b.get(i));
        }
        queue.enqueue(null);
    }
}
