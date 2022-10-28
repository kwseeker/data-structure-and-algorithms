package top.kwseeker.dsaa.basic.queue;

/**
 * 优先队列
 * 元素都有
 */
public class MyPriorityQueue<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    transient Object[] queue;

    private int size = 0;

    public MyPriorityQueue() {
        queue = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        // 先判断新元素能否存进去
        if (size + 1 > queue.length) {
            System.out.println("需要扩容");
            // 扩容 ...
        }

        int i = size;
        size += 1;

        if (i == 0) {
            queue[0] = e;   //第一个元素 idx=0 放在堆顶
        } else {
            //siftUpComparable(i, e);
            mySiftUpComparable(i, e);
        }
        return true;
    }

    private void siftUpComparable(int k, E x) {
        Comparable<? super E> key = (Comparable<? super E>) x;
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (key.compareTo((E) e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = key;
    }

    private void mySiftUpComparable(int k, E x) {
        Comparable<? super E> key = (Comparable<? super E>) x;
        while (k > 0) {
            //1）获取父节点索引
            int parent; //父节点索引
            if ((k & 1) == 1)           //奇数（左子节点索引）
                parent = (k - 1) >>> 1;
            else    // ((k & 1) == 0)   //偶数（右子节点索引）
                parent = (k - 2) >>> 1;
            //2）
            Object e = queue[parent];
            if (key.compareTo((E) e) >= 0)  // key >= e, 跳出循环，直接赋值 即（放在了靠后的位置）
                break;
            queue[k] = e;                   // key < e, 交换新节点x和父节点e的位置
            k = parent;                     //          现在x在parent位置，然后继续循环与祖父节点比较，直到比较优先级败出或到达根节点
        }
        queue[k] = key;
    }

    //非阻塞
    public E poll() {
        if (size == 0) {
            return null;
        }
        int s = --size;
        //出队，s为元素出队后最后一个值（优先级最低）的索引
        //从堆顶开始出队，但是堆顶空缺后要下面的元素依次补位
        E result = (E) queue[0];
        E x = (E) queue[s];
        queue[s] = null;
        if (s != 0)
            siftDownComparable(0, x);
        return result;
    }

    public E peek() {
        return (size == 0) ? null : (E) queue[0];
    }

    private void siftDownComparable(int k, E x) {
        Comparable<? super E> key = (Comparable<? super E>)x;
        int half = size >>> 1;        // loop while a non-leaf
        while (k < half) {
            int child = (k << 1) + 1; // assume left child is least
            Object c = queue[child];
            int right = child + 1;
            if (right < size &&
                    ((Comparable<? super E>) c).compareTo((E) queue[right]) > 0)
                c = queue[child = right];
            if (key.compareTo((E) c) <= 0)
                break;
            queue[k] = c;
            k = child;
        }
        queue[k] = key;
    }

    //k 从堆顶开始的索引，x是最后一个索引位置的元素值
    private void mySiftDownComparable(int k, E x) {
        //int k1 = (parent << 1) + 1;
        //Comparable<? super E> key1 = (Comparable<? super E>) k1;
        //Comparable<? super E> key2 = (Comparable<? super E>) x;
        //while (s > 0) {
        //
        //}
    }
}
