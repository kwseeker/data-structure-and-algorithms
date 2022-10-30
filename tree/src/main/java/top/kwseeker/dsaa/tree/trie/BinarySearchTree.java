package top.kwseeker.dsaa.tree.trie;

public class BinarySearchTree<T extends Comparable<T>> {

    Node<T> root;
    private int size;

    /**
     * 插入元素
     * @return 插入的节点
     */
    public Node<T> insert(T e) {
        Node<T> node = new Node<T>(e);
        return insert(node);
    }

    //为了兼容子类型(如AVLTree)插入节点
    public Node<T> insert(Node<T> node) {
        if (root == null) {
            root = node;
            size++;
            return root;
        }

        Node<T> parent = root;
        while (true) {
            T eVal = node.value;
            if (eVal.compareTo(parent.value) < 0) {    // 左
                Node<T> left = parent.left;
                if (left == null) {
                    parent.left = node;
                    node.parent = parent;
                    size++;
                    return parent.left;
                }
                parent = left;
            } else {                                // 右
                Node<T> right = parent.right;
                if (right == null) {
                    parent.right = node;
                    node.parent = parent;
                    size++;
                    return parent.right;
                }
                parent = right;
            }
        }
    }

    /**
     * 遍历
     */
    public void traverse() {
        if (root == null) {
            return;
        }

        int depth = 0;  //为了参数只是为了打印时好看
        Node<T> parent = root;
        // 左->中->右
        doTraverse(parent, depth);
    }

    private void doTraverse(Node<T> parent, int depth) {
        depth++;

        Node<T> left = parent.left;
        if (left != null) {
            doTraverse(parent.left, depth);
        }

        System.out.println(depthLine(depth) + parent.value.toString());

        Node<T> right = parent.right;
        if (right != null) {
            doTraverse(parent.right, depth);
        }
    }

    public String depthLine(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("----");
        }
        return sb.toString();
    }

    static class Node<T extends Comparable<T>> {
        public T value;
        public Node<T> left;
        public Node<T> right;
        //AVL树才需要
        public Node<T> parent;

        public Node(T value) {
            this.value = value;
        }
    }
}
