package top.kwseeker.algorithms.sort;

import top.kwseeker.util.ArrayIns;

//重点在于最大堆、最小堆的实现
public class HeapSort {

    public static void main(String[] args) {
        int size = 16;
        ArrayIns arr = new ArrayIns(size);
        for (int i=0; i<size; i++) {
            arr.insert((int)(Math.random()*99));
        }
        int[] array = arr.getTheArray();

        //创建堆并插入数据
        Heap theHeap = new Heap(size);
        for(int j=0; j<size; j++) {
            Node newNode = new Node(array[j]);
            theHeap.insertAt(j, newNode);
            theHeap.incrementSize();
        }

        //
        System.out.print("Random: ");
        theHeap.displayArray();
        for(int j= size/2-1; j>=0; j--) {
            theHeap.trickleDown(j);
        }
        System.out.print("Heap: ");
        theHeap.displayArray();
        //显示堆树
        theHeap.displayHeap();

        for(int j=size-1; j>=0; j--) {
            Node biggestNode = theHeap.remove();
            theHeap.insertAt(j, biggestNode);
        }
        System.out.print("Sorted: ");
        theHeap.displayArray();
    }

    //最大堆、最小堆
    static class Heap {
        private Node[] heapArray;
        private int maxSize;
        private int currentSize;

        public Heap(int mx) {
            maxSize = mx;
            currentSize = 0;
            heapArray = new Node[maxSize];
        }

        public Node remove() {
            Node root = heapArray[0];
            heapArray[0] = heapArray[--currentSize];
            trickleDown(0);
            return root;
        }

        public void trickleDown(int index) {
            int largerChild;
            Node top = heapArray[index];
            while (index < currentSize/2) {
                int leftChild = 2*index + 1;
                int rightChild = leftChild + 1;

                if(rightChild < currentSize && heapArray[leftChild].getKey() < heapArray[rightChild].getKey()) {
                    largerChild = rightChild;
                } else {
                    largerChild = leftChild;
                }

                if(top.getKey() >= heapArray[largerChild].getKey()){
                    break;
                }

                heapArray[index] = heapArray[largerChild];
                index = largerChild;
            }
            heapArray[index] = top;
        }

        public void displayHeap() {
            int nBlanks = 32;
            int itemsPerRow = 1;
            int column = 0;
            int j = 0;
            String dots = "..........................";
            System.out.println(dots+dots);

            while (currentSize > 0) {
                if (column == 0) {
                    for (int k = 0; k < nBlanks; k++) {
                        System.out.print(' ');
                    }
                }
                System.out.print(heapArray[j].getKey());

                if(++j == currentSize)
                    break;
                if(++column == itemsPerRow) {
                    nBlanks /= 2;
                    itemsPerRow *= 2;
                    column = 0;
                    System.out.println();
                } else {
                    for(int k=0; k<nBlanks*2-2; k++)
                        System.out.print(' ');
                }
            }
            System.out.println("\n"+dots+dots);
        }

        public void displayArray() {
            for (int i = 0; i < maxSize; i++) {
                System.out.print(heapArray[i].getKey() + " ");
            }
            System.out.println();
        }

        public void insertAt(int index, Node newNode) {
            heapArray[index] = newNode;
        }

        public void incrementSize() {
            currentSize++;
        }
    }

    static class Node {
        private int iData;

        public Node(int key) {
            iData = key;
        }
        public int getKey() {
            return iData;
        }
    }
}
