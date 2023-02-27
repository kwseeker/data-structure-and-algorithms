package top.kwseeker.dsaa.tree;

import org.junit.Test;
import top.kwseeker.dsaa.tree.BinarySearchTree;

public class BinarySearchTreeTest {

    @Test
    public void test() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();

        bst.insert(6);
        bst.insert(3);
        bst.insert(8);
        bst.insert(0);
        bst.insert(4);
        bst.insert(9);
        bst.insert(2);
        bst.insert(5);
        bst.insert(7);
        bst.insert(1);

        bst.traverse();
    }
    //------------0
    //--------------------1
    //----------------2
    //--------3
    //------------4
    //----------------5
    //----6
    //------------7
    //--------8
    //------------9
}