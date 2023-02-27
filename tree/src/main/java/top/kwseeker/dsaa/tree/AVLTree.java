package top.kwseeker.dsaa.tree;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    //插入
    @Override
    public Node<T> insert(T e) {
        Node<T> node = new Node<>(e);
        Node<T> addedNode = (Node<T>) super.insert(node);

        //再平衡
        reBalance(addedNode);

        return addedNode;
    }

    //删除

    //再平衡
    //1) 从新增节点出发，反向层层更新父节点的高度，直到不平衡节点
    //2) 找到不平衡节点（平衡因子：-2/2），
    //  2.1) 平衡因子-2：计算不平衡节点的右子节点的平衡因子M
    //      2.1.1) M为>0, 需要对不平衡节点的右子树RT先右旋
    //      2.1.2) M为<=0, 对右子树RT不处理
    //      2.1.3) 对不平衡节点为根的树进行左旋
    //  2.2) 平衡因子2：计算不平衡节点的左子节点的平衡因子M
    //      2.1.1) M为<0, 需要对不平衡节点的左子树LT先左旋
    //      2.1.2) M为>=0, 对左子树LT不处理
    //      2.1.3) 对不平衡节点为根的树进行右旋
    private void reBalance(Node<T> node) {
        while (node.parent != null) {
            Node<T> parentNode = (Node<T>) node.parent;

            // 计算父节点的平衡因子
            int factor = calculateFactor(parentNode);

            switch (factor) {
                case -2:    //右型
                    //先看右子树是否是左型
                    Node<T> rt = (Node<T>) parentNode.right;
                    int rtFactor = calculateFactor(rt);
                    if (rtFactor > 0) {     //左型，右旋
                        Node<T> temp = rightRotate(rt);
                        refreshHeight((Node<T>) temp.right);
                        refreshHeight(temp);
                        node.right = temp;
                    }
                    //左旋
                    Node<T> temp = leftRotate(parentNode);
                    refreshHeight((Node<T>) temp.left);
                    refreshHeight(temp);
                    break;
                case 2:     //左型
                    //先看左子树是否是右型
                    Node<T> lt = (Node<T>) parentNode.left;
                    int ltFactor = calculateFactor(lt);
                    if (ltFactor < 0) {     //右型，左旋

                    }
                    //左旋
                    break;
                default:
                    parentNode.height += 1;
            }

            node = parentNode;
        }
    }

    private int calculateFactor(Node<T> node) {
        int lHeight = ((Node<T>)node.left).height + ((node == node.left) ? 1 : 0);
        int rHeight = ((Node<T>)node.right).height + ((node != node.left) ? 1 : 0);
        return lHeight - rHeight;
    }

    //更新节点高度
    private void refreshHeight(Node<T> node) {
        int leftHeight = (node.left == null) ? -1 : ((Node<T>)node.left).height;
        int rightHeight = (node.right == null) ? -1 : ((Node<T>)node.right).height;
        node.height = 1 + Math.max(leftHeight, rightHeight);
    }

    //左旋
    private Node<T> leftRotate(Node<T> node) {
        Node<T> temp = (Node<T>) node.right;
        temp.parent = node.parent;

        node.right = temp.left;
        if (node.right != null) {
            node.right.parent = node;
        }

        temp.left = node;
        node.parent = temp;

        if (temp.parent == null) {
            this.root = temp;
        } else {
            if (temp.parent.left == node) {
                temp.parent.left = temp;
            } else {
                temp.parent.right = temp;
            }
        }
        return temp;
    }

    //右旋
    private Node<T> rightRotate(Node<T> node) {
        Node<T> temp = (Node<T>) node.left;
        temp.parent = node.parent;

        node.left = temp.right;
        if (node.left != null) {
            node.left.parent = node;
        }

        temp.right = node;
        node.parent = temp;

        if (temp.parent == null) {
            root = temp;
        } else {
            if (temp.parent.left == node) {
                temp.parent.left = temp;
            } else {
                temp.parent.right = temp;
            }
        }
        return temp;
    }

    static class Node<T extends Comparable<T>> extends BinarySearchTree.Node<T> {
        //public T value;
        //public Node<T> left;
        //public Node<T> right;
        //public Node<T> parent;
        public int height;

        public Node(T value) {
            super(value);
        }
    }
}