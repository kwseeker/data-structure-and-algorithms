package top.kwseeker.dsaa.tree.trie;

import org.junit.Test;

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