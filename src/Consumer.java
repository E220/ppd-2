public class Consumer implements Runnable {

    private final SynchronizedQueue<Integer> queue;

    public Consumer(SynchronizedQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int sum = 0;
        Integer item = queue.dequeue();
        while(item != null) {
            sum += item;
            item = queue.dequeue();
        }
        System.out.println(sum);
    }
}
