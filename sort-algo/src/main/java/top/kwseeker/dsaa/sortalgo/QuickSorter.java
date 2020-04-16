package top.kwseeker.dsaa.sortalgo;

import java.util.Random;

/**
 * 快速排序
 *
 * 三个标量：基准元素M（初始为第一个元素）, leftPointer（初始指向第二个元素）, rightPointer（初始指向最后一个元素）。
 * １）从leftPointer开始往后依次查找（leftPointer++）大于M的元素和rightPointer元素交换（交换后rightPointer-1），然后循环继续这一操作，直到leftPointer>rightPointer，然后把M与rightPointer位置元素互换; 这时会发现以M为基准，小于M的元素全部在左边大于M的元素全部在右边。
 * ２）然后开始分治，Ｍ左边一块右边一块，分别重新选定各自新的基准元素M,重复执行上面的操作。
 *
 * {2, 8, 6, 0, 3, 7, 4, 2, 1, 9, 5}
 *     l                          r
 *                          r <---
 * {2, 1, 6, 0, 3, 7, 4, 2, 8, 9, 5}
 *        l              r
 *           r <---------
 * {2, 1, 0, 6, 3, 7, 4, 2, 8, 9, 5}
 * {0, 1, 2, 6, 3, 7, 4, 2, 8, 9, 5}
 * 　　　　M
 * 分治：
 * {0, 1}   {6, 3, 7, 4, 2, 8, 9, 5}
 * {0, 1}   {6, 3, 5, 4, 2, 8, 9, 7}
 *          {2, 3, 5, 4, 6, 8, 9, 7}
 *          　　　　　　　 M
 * 分治：
 *          {2, 3, 5, 4}   {8, 9, 7}
 *           M             {8, 7, 9}
 *                         {7, 8, 9}
 *                             M
 * 分治：
 *             {3, 5, 4}   {7}   {9}
 *              M
 * 分治：
 *                {5, 4}
 *                {4, 5}
 * 最终结果：
 * {0, 1, 2, 2, 3, 4, 5, 6, 7, 8, 9}
 */
public class QuickSorter {

    public static void main(String[] args) {
        //int[] array = {2, 8, 6, 0, 3, 7, 4, 2, 1, 9, 5};
        int[] array = new int[50];
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            array[i] = random.nextInt(100);
        }
        int len =array.length;
        QuickSorter.sort(array, 0, len-1);
        for(int item : array) {
            System.out.print(item + " ");
        }
    }

    //TODO: 写的有点乱，优化
    public static void sort(int[] array, int left, int right) {
        if(left>=right) {   //当只有一个值时直接退出
            return;
        }
        int M = array[left];
        int oldLeft = left;
        int oldRight = right;
        left++;
        while (left < right) {
            int leftElement = array[left];
            while (leftElement < M && left <= right && left < oldRight) {
                leftElement = array[++left];
            }
            int rightElement=array[right];
            while(rightElement >= M && left <= right && right > oldLeft+1) {
                rightElement = array[--right];
            }
            if(left < right) {
                array[left] = rightElement;
                array[right] = leftElement;
                left++; right--;
            }
        }
        //M与array[right]互换
        if(M > array[right]) {
            array[oldLeft]= array[right];
            array[right] = M;
        }
        //左区间
        sort(array, oldLeft, right-1);
        //右区间
        sort(array, left, oldRight);
    }
}
