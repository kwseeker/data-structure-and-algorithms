package top.kwseeker.dsaa.sortalgo;


/**
 * 插入排序
 * 思想：从数组开头开始，进行n轮插入，第k轮选择第k个元素依次和前面的值比较替换位置，直到前面的值小于它。
 * 优化：二分查找插入，因为目标元素前面的元素已经是有序的了。
 */
public class InsertSorter {

    public static void main(String[] args) {
        int[] array = {2, 8, 6, 0, 3, 7, 4, 2, 1, 9, 5};
        //InsertSorter.sort(array);
        InsertSorter.optimizeSort(array);
        for(int item : array) {
            System.out.print(item + " ");
        }
    }

    public static void sort(int[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {
            for (int j = i; j > 0 ; j--) {
                if(array[j] < array[j-1]) {
                    int temp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = temp;
                }
            }
        }
    }

    //二分插入优化
    public static void optimizeSort(int[] array) {
        int len = array.length;
        for (int i = 1; i < len; i++) {
            //0~i
            //首先确认插入位置
            int targetIndex=i;
            for(int head=0,tail=i-1;head>=0 && tail<i;) {
                int l = tail-head;
                if(l > 1) {
                    int mid = (head + tail)/2;
                    if(array[mid] < array[i]){
                        head = mid;
                    } else if(array[mid] > array[i]) {
                        tail = mid;
                    } else {    //相等则
                        targetIndex = mid + 1;
                        break;
                    }
                } else {    //差值为０或１
                    if(array[i] < array[head]){
                        targetIndex = head;
                    } else if(array[i] < array[tail]) {
                        targetIndex = tail;
                    } else {
                        targetIndex = tail + 1;
                    }
                    break;
                }
            }
            //数据插入，后面数据依次位移
            int left = array[i];
            while (targetIndex <= i) {
                int right = array[targetIndex];
                array[targetIndex] = left;
                left = right;
                targetIndex++;
            }
        }
    }
}
