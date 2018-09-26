package top.kwseeker.util;

public class ArrayIns {
    private int[] theArray;
    private int nElems;         //number of data items

    public ArrayIns(int max) {
        theArray = new int[max];
        nElems = 0;
    }

    public void insert(int value) {
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

    public void swap(int dex1, int dex2) {
        int temp = theArray[dex1];
        theArray[dex1] = theArray[dex2];
        theArray[dex2] = temp;
    }

    //=============================================
    public int[] getTheArray() {
        return theArray;
    }

    public void setTheArray(int[] theArray) {
        this.theArray = theArray;
    }

    public int getnElems() {
        return nElems;
    }

    public void setnElems(int nElems) {
        this.nElems = nElems;
    }
}
