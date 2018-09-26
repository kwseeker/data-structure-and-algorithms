package top.kwseeker.algorithms.sort;

public class QuickSort {

    public static void main(String[] args) {
        int maxSize = 8;
        ArrayIns arr = new ArrayIns(maxSize);

        for (int i = 0; i < maxSize; i++) {
            long n = (int)(Math.random()*99);
            arr.insert(n);
        }

        arr.display();
        arr.quickSort();
        arr.display();
    }

    static class ArrayIns {
        private long[] theArray;
        private int nElems;         //number of data items

        public ArrayIns(int max) {
            theArray = new long[max];
            nElems = 0;
        }

        public void insert(long value) {
            theArray[nElems] = value;
            nElems++;
        }

        public void display() {
            System.out.println("A=");
            for (int i = 0; i < nElems; i++) {
                System.out.print(theArray[i] + " ");
            }
            System.out.println();
        }

        public void quickSort() {
            recQuickSort(0, nElems-1);
        }

        public void recQuickSort(int left, int right) {
            if(right-left <= 0) {
                return;
            } else {
                long pivot = theArray[right];
                int partition = partitionIt(left, right, pivot);
                display();
                System.out.println("left=" + left + ". right=" + right + ", partition=" + partition);
                recQuickSort(left, partition-1);
                recQuickSort(partition+1, right);
            }
        }

        public int partitionIt(int left, int right, long pivot) {
            int leftPtr = left - 1;
            int rightPtr = right;
            while (true){
                while(theArray[++leftPtr] < pivot);
                while(rightPtr > 0 && theArray[--rightPtr] > pivot);
                if(leftPtr >= rightPtr) {
                    break;
                } else {
                    swap(leftPtr, rightPtr);
                }
            }
            swap(leftPtr, right);
            return leftPtr;
        }

        public void swap(int dex1, int dex2) {
            long temp = theArray[dex1];
            theArray[dex1] = theArray[dex2];
            theArray[dex2] = temp;
        }
    }
}
