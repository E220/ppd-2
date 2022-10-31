import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final List<Integer> array_a = Arrays.asList(
                1, 2, 3, 4, 5
        );
        final List<Integer> array_b = Arrays.asList(
                2, 3, 4, 5, 6
        );
        final SynchronizedQueue<Integer> queue = new SynchronizedQueue<>(3);

        final Thread producer = new Thread(new Producer(queue, array_a, array_b));
        final Thread consumer = new Thread(new Consumer(queue));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}