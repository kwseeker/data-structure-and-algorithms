package top.kwseeker.dsaa.sortalgo;

/**
 * 归并排序
 * 思想：对于二路归并排序就是，每次将当前数组平均拆分为两块，递归，在拆分，直到子数组只有一个或者两个元素；然后排序，递归返回，再排序...
 * 优点：易于理解，高效，占内存空间少，稳定，还可以并发处理
 */
public class MergeSorter {

    public static void main(String[] args) {
        int[] array = {2, 8, 6, 0, 3, 7, 4, 2, 1, 9, 5};
        //BubbleSorter.sort(array);
        MergeSorter.sort(array);
        for(int item : array) {
            System.out.print(item + " ");
        }
    }

    /**
     * 包一层更安全,同时注意设置final
     */
    public static void sort(final int[] array) {
        sort(array, 0, array.length-1);
    }

    /**
     * @param array
     * @param left  当前操作的左边界
     * @param right　当前操作的右边界
     */
    private static void sort(int[] array, int left, int right) {
        if(right - left == 1) {  //不超过两个元素
            //不用再继续递归了
            if(array[right] - array[left] < 0) {
                int tmp = array[left];
                array[left] = array[right];
                array[right] = tmp;
            }
        } else if(right-left > 1) { //递归继续拆分
            int mid = (right + left)/2;
            //左排序
            sort(array, left, mid);
            //右排序
            sort(array, mid+1, right);
            //递归返回后还要做整体排序, 这时左边和右边都是有序数组
            //不创建辅助数组实现,这种实现不好理解，而且性能应该没有使用辅助数组高效。
            for(int i=left, j=mid+1, middle=mid; i<=middle && j<=right;) {
                if(array[i] > array[j]) {
                    int tmp = array[i];
                    array[i] = array[j];
                    //向右位移
                    for (int start = j; start > i; start--) {
                        if(start == i+1) {
                            array[start] =tmp;
                        } else {
                            array[start] =array[start-1];
                        }
                    }
                    middle++;
                    i++; j++;
                } else {
                    i++;
                }
            }
        }

    }
}
