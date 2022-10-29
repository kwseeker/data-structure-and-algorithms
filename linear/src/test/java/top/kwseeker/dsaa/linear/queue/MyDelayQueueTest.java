package top.kwseeker.dsaa.linear.queue;

import org.junit.Test;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelayQueueTest {

    @Test
    public void testDelayQueue() throws Exception {
        MyDelayQueue<Task> delayQueue = new MyDelayQueue<>();

        delayQueue.offer(new Task(5000));
        delayQueue.offer(new Task(1000));
        delayQueue.offer(new Task(3000));
        delayQueue.offer(new Task(2000));
        delayQueue.offer(new Task(4000));

        while (delayQueue.peek() != null) {
            //可以用take()替代
            Task task = delayQueue.poll();
            if (task != null) {
                task.run();
            }
            Thread.sleep(100);
        }
        System.out.println("Done!");
    }

    static class Task implements Runnable, Delayed {

        private long delayTime;
        private long begin;
        private TimeUnit unit = TimeUnit.MILLISECONDS;

        public Task(long delayTime) {
            this.delayTime = delayTime;
            this.begin = unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        public void run() {
            System.out.println("Exec after delay " + delayTime + " milliseconds, " + System.currentTimeMillis());
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long now = unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            long left = unit.convert(delayTime + this.begin, this.unit);
            return left - now;
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(getDelay(unit), o.getDelay(unit));
        }
    }
}