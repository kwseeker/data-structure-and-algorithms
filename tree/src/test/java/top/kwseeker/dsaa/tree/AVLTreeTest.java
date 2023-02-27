package top.kwseeker.dsaa.tree;

import org.junit.Test;
import top.kwseeker.dsaa.tree.AVLTree;

public class AVLTreeTest {

    @Test
    public void testLeftRotate() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.insert(5);
        avlTree.insert(1);
        avlTree.insert(2);
        avlTree.insert(3);
        avlTree.insert(4);

        avlTree.traverse();
    }
}