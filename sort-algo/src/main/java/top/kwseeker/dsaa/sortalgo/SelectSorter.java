package top.kwseeker.dsaa.sortalgo;

/**
 * 选择排序，人最容易想到的方式；和冒泡排序类似
 * 从最左边元素开始，依次与右边的元素对比，替换位置将较小的放在左边
 */
public class SelectSorter {

    public static void main(String[] args) {
        int[] array = {2, 8, 6, 0, 3, 7, 4, 2, 1, 9, 5};
        SelectSorter.sort(array);
        for(int item : array) {
            System.out.print(item + " ");
        }
    }

    //从小到大排序
    public static void sort(int[] array) {
        int len = array.length;
        for(int i=0; i<len; i++) {              //n
            for(int j=i+1; j<len; j++) {        //n-1
                if(array[i] > array[j]) {       //4
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }
    /**
     * 算法时间复杂度：
     * T(n) = n*(n-1)*4 => O(n^2)
     */
}
