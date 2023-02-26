package top.kwseeker.dsaa.slidewindow;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

/**
 * jd-hotkey 使用滑动窗口思想统计key访问次数判断key是否是热key的实现
 * 源码未变，只是修改了下注释
 */
public class JdHotkeySlidingWindow {
    // 滑动窗口共有多少个时间片
    private final int windowSize;
    // 滑动窗口每个时间片的时长，以毫秒为单位, 滑动窗口总时长 duration = windowSize * timeMillisPerSlice
    private final int timeMillisPerSlice;
    // 在一个完整窗口期内判定热key的最大阈值
    private final int threshold;
    // 该滑窗的起始创建时间，也就是第一次访问时的时间戳
    private long beginTimestamp;
    // 最后一次访问（上一次访问）的时间戳
    private long lastAddTimestamp;
    // 循环队列，装多个窗口时间片用，该数量是windowSize的2倍
    // 这里不明白为何要设置windowSize的两倍，按理说windowSize个就够了
    private AtomicLong[] timeSlices;
    // 队列的总长度
    private final int timeSliceSize;

    public static void main(String[] args) {
        //2秒中达到20次即为热key
        JdHotkeySlidingWindow window = new JdHotkeySlidingWindow(2, 20);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    boolean hot = window.addCount(2);
                    System.out.println(hot);
                }
            }).start();
        }
        //for (int i = 0; i < 10; i++) {
        //    boolean hot = window.addCount(2);
        //    System.out.println(hot);
        //}
    }

    public JdHotkeySlidingWindow(int duration, int threshold) {
        //超过10分钟的按10分钟
        if (duration > 600) {
            duration = 600;
        }
        //要求5秒内探测出来的，
        if (duration <= 5) {
            this.windowSize = 5;
            this.timeMillisPerSlice = duration * 200;
        } else {
            this.windowSize = 10;
            this.timeMillisPerSlice = duration * 100;
        }
        this.threshold = threshold;
        // 保证存储在至少两个window
        this.timeSliceSize = windowSize * 2;

        reset();
    }

    public JdHotkeySlidingWindow(int timeMillisPerSlice, int windowSize, int threshold) {
        this.timeMillisPerSlice = timeMillisPerSlice;
        this.windowSize = windowSize;
        this.threshold = threshold;
        // 保证存储在至少两个window
        this.timeSliceSize = windowSize * 2;

        reset();
    }

    /**
     * 增加count个数量
     */
    public synchronized boolean addCount(long count) {
        //当前自己所在的位置，是哪个小时间窗
        int index = locationIndex();
        System.out.println("index:" + index);
        //然后清空自己前面windowSize到2*windowSize之间的数据格的数据
        //譬如1秒分4个窗口，那么数组共计8个窗口
        //当前index为5时，就清空6、7、8、1。然后把2、3、4、5的加起来就是该窗口内的总和
        clearFromIndex(index);

        int sum = 0;
        //在当前时间片里继续+count
        sum += timeSlices[index].addAndGet(count);
        //加上前面windowSize个时间片
        for (int i = 1; i < windowSize; i++) {
            sum += timeSlices[(index - i + timeSliceSize) % timeSliceSize].get();
        }

        //lastAddTimestamp = SystemClock.now();
        lastAddTimestamp = System.currentTimeMillis();

        return sum >= threshold;
    }

    /**
     * 初始化｜重置
     */
    private void reset() {
        //beginTimestamp = SystemClock.now();
        beginTimestamp = System.currentTimeMillis();
        //窗口个数
        AtomicLong[] localTimeSlices = new AtomicLong[timeSliceSize];
        for (int i = 0; i < timeSliceSize; i++) {
            localTimeSlices[i] = new AtomicLong(0);
        }
        timeSlices = localTimeSlices;
    }

    /**
     * 计算当前所在的时间片的位置
     */
    private int locationIndex() {
        //long now = SystemClock.now();
        long now = System.currentTimeMillis();
        //如果当前的key已经超出一整个时间片了，那么就直接初始化就行了，不用去计算了
        if (now - lastAddTimestamp > (long) timeMillisPerSlice * windowSize) {
            reset();
        }

        int index = (int) (((now - beginTimestamp) / timeMillisPerSlice) % timeSliceSize);
        return Math.max(index, 0);
    }

    private void clearFromIndex(int index) {
        for (int i = 1; i <= windowSize; i++) {
            int j = index + i;
            if (j >= windowSize * 2) {
                j -= windowSize * 2;
            }
            timeSlices[j].set(0);
        }
    }
}
