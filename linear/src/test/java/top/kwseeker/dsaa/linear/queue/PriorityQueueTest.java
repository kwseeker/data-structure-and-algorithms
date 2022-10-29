package top.kwseeker.dsaa.linear.queue;

import org.junit.Test;

/**
 * java.util.PriorityQueue 测试
 */
public class PriorityQueueTest {

    @Test
    public void testPriorityQueue() {
        //PriorityQueue<String> queue = new PriorityQueue<>();
        MyPriorityQueue<Integer> queue = new MyPriorityQueue<>();

        queue.offer(1);
        queue.offer(11);
        queue.offer(3);
        queue.offer(22);
        queue.offer(33);
        queue.offer(9);
        queue.offer(6);

        while (queue.peek() != null) {
            System.out.println(queue.poll());
        }
    }
}