package top.kwseeker.dsaa.sortalgo;

/**
 * 思想：
 * 从最左边开始临近的两个元素两两比较
 */
public class BubbleSorter {

    public static void main(String[] args) {
        int[] array = {2, 8, 6, 0, 3, 7, 4, 2, 1, 9, 5};
        //BubbleSorter.sort(array);
        BubbleSorter.optimizeSort(array);
        for(int item : array) {
            System.out.print(item + " ");
        }
    }

    public static void sort(int[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {             //n
            for (int j = 0; j < len-i-1; j++) {     //n/2
                if(array[j] > array[j+1]) {
                    int temp = array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
        }
    }
    /**
     * 算法时间复杂度：
     * T(n) = n*(n-1)*4 => O(n^2)
     */

    //可以将最好的情况的复杂度优化为O(n)
    public static void optimizeSort(int[] array) {
        int len = array.length;
        for (int i = 0; i < len; i++) {             //n
            boolean sorted = true;
            for (int j = 0; j < len-i-1; j++) {     //n/2
                if(array[j] > array[j+1]) {
                    int temp = array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                    System.out.println("swap");
                    sorted = false;                 //如果没有替换发生说明已经是有序的了
                }
            }
            if(sorted) {
                break;
            }
        }
    }
}
