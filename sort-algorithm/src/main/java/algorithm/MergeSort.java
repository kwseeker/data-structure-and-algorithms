package algorithm;

public class MergeSort {

    private int[] array;

    public MergeSort setArray(int[] array) {
        this.array = array;
        return this;
    }

    public int[] mergeSort() {
        int tail = array.length - 1;
        return sort(0, tail);
    }

    public int[] sort(int head, int tail) {
        int size = tail - head + 1;
        if(size >= 2) {  //大于两个
            int mid = (head+tail)/2;
            //左排序
            int[] left = sort(head, mid);
            //右排序
            int[] right = sort(mid+1, tail);
            //归并
            return merge(left, right);
        } else {        // 1
            return new int[]{array[head]};
        }
    }

    private int[] merge(int[] left, int[] right) {
        int totalSize = left.length + right.length;
        int leftHead = 0;
        int rightHead = 0;
        int[] newArray = new int[totalSize];
        for (int i = 0; i < totalSize; i++) {
            if(leftHead < left.length && rightHead < right.length) {
                if(left[leftHead] < right[rightHead]) {
                    newArray[i] = left[leftHead];
                    leftHead++;
                } else {
                    newArray[i] = right[rightHead];
                    rightHead++;
                }
            } else if( leftHead == left.length ) {
                newArray[i] = right[rightHead];
                rightHead++;
            } else if( rightHead == right.length) {
                newArray[i] = left[leftHead];
                leftHead++;
            }
        }
        return newArray;
    }

    public static void main(String[] args) {
        int[] array = {2, 8, 6, 0, 3, 7, 4, 2, 1, 9, 5};
        int[] result = new MergeSort().setArray(array)
                .mergeSort();
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
    }
}
