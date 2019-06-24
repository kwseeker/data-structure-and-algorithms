package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeSort {

    //public int[] mergeSort() {
    //    int tail = array.length - 1;
    //    return sort(0, tail);
    //}

    public static int[] mergeSort(int[] array) {
        int tail = array.length - 1;
        sort(array, 0, tail);
        return  array;
    }

    private static int[] sort(int[] array, int head, int tail) {
        if(tail > head) {   //继续拆
            int mid = (head+tail)/2;
            //左排序
            sort(array, head, mid);
            //右排序
            sort(array, mid+1, tail);
            merge(array, head, mid, tail);
        }
        return array;
    }

    //局部替换代替原来新建数组的写法，因为每次局部排序，数据都是相邻的不会造成数据丢失
    private static void merge(int[] array, int head, int mid, int tail) {
        // 定义一个辅助数组，所以该算法的空间复杂度为O（n）
        int[] temp = new int[tail-head+1];
        int i = head;
        int j = mid+1;
        int k = 0;
        // 找出较小值元素放入temp数组中
        while(i<=mid && j<=tail){
            if(array[i]<array[j])
                temp[k++] = array[i++];
            else
                temp[k++] = array[j++];
        }
        // 处理较长部分
        while(i<=mid){
            temp[k++] = array[i++];
        }
        while(j<=tail){
            temp[k++] = array[j++];
        }
        // 使用temp中的元素覆盖array中元素
        for (int k2 = 0; k2 < temp.length; k2++) {
            array[k2+head] = temp[k2];
        }
    }

    //public int[] sort(int head, int tail) {
    //    int size = tail - head + 1;
    //    if(size >= 2) {  //大于两个
    //        int mid = (head+tail)/2;
    //        //左排序
    //        int[] left = sort(head, mid);
    //        //右排序
    //        int[] right = sort(mid+1, tail);
    //        //归并
    //        return merge(left, right);
    //    } else {        // 1
    //        return new int[]{array[head]};
    //    }
    //}

    //private int[] merge(int[] left, int[] right) {
    //    int totalSize = left.length + right.length;
    //    int leftHead = 0;
    //    int rightHead = 0;
    //    int[] newArray = new int[totalSize];
    //    for (int i = 0; i < totalSize; i++) {
    //        if(leftHead < left.length && rightHead < right.length) {
    //            if(left[leftHead] < right[rightHead]) {
    //                newArray[i] = left[leftHead];
    //                leftHead++;
    //            } else {
    //                newArray[i] = right[rightHead];
    //                rightHead++;
    //            }
    //        } else if( leftHead == left.length ) {
    //            newArray[i] = right[rightHead];
    //            rightHead++;
    //        } else if( rightHead == right.length) {
    //            newArray[i] = left[leftHead];
    //            leftHead++;
    //        }
    //    }
    //    return newArray;
    //}

    public static void main(String[] args) {
        int[] array = {2, 8, 6, 0, 3, 7, 4, 2, 1, 9, 5};

        int[] result = MergeSort.mergeSort(array);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println();

        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(8);
        list.add(6);
        list.add(0);
        list.add(3);
        list.add(7);
        list.add(4);
        list.add(2);
        list.add(1);
        list.add(9);
        list.add(5);
        Collections.sort(list);
        for (Integer num: list) {
            System.out.print(num + " ");
        }
    }
}
