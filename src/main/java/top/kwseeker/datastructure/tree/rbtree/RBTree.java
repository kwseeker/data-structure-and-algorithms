package top.kwseeker.datastructure.tree.rbtree;

/**
 * 对标2-3-4树实现的红黑树
 */
public class RBTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public static void main(String[] args){
        RBTree<Integer, Integer> rbTree = new RBTree();
        int[] arr = {66, 8, 38, 67, 56, 34, 52, 97, 4, 90, 7, 20, 21, 43, 12, 46};
        //TODO:为什么插入第14个数的时候红黑树就异常了？
        for (int i = 0; i < 16; i++) {
//            int randomNum = (int)(Math.random()*99);
//            System.out.print(" " + randomNum);
//            Integer key = new Integer(randomNum);
            System.out.println();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>> " + arr[i]);
            Integer key = arr[i];
            Integer value = key;
            rbTree.add(key, value);
        }

        System.out.println("生成的红黑树：");
        rbTree.printTree();
    }

    private Node root;
    private int size;

    public RBTree(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // 向红黑树中添加新的元素(key, value)
    public void add(K key, V value){
        // 1 插入数据
        int cmp;
        Node curParent = null;
        Node newNode = new Node(key, value);
        Node cur = this.root;

        while(cur != null) {
            curParent = cur;
            cmp = key.compareTo(cur.key);
            if(cmp < 0) {
                cur = cur.left;
            } else if(cmp > 0) {
                cur = cur.right;
            } else {
                cur.value = value;
                return;
            }
        }
        newNode.parent = curParent;
        if(curParent != null) {
            cmp = newNode.key.compareTo(curParent.key);
            if(cmp < 0) {
                curParent.left = newNode;
            } else {
                curParent.right = newNode;
            }
        } else {
            this.root = newNode;
        }

        newNode.color = RED;

        //2重新维持平衡
        Node gParentNode, parentNode;
        while((newNode != null) && newNode.color == RED && (parentNode = newNode.parent) != null && parentNode.color == RED) {
            gParentNode = parentNode.parent;
            if(gParentNode == null) {   //parent为根
                break;
            }

            if(parentNode == gParentNode.left) {
                if(gParentNode.right != null && gParentNode.right.color == RED) {   //叔节点为红色
                    //叔父节点均置为黑色，祖父节点置为红色，然后继续向上平衡
                    gParentNode.color = RED;
                    gParentNode.left.color = gParentNode.right.color = BLACK;
                } else {            //叔节点为黑色
                    // 先左旋转
                    if(newNode == parentNode.right) {
                        parentNode = leftRotate(parentNode);
                        gParentNode.left = parentNode;
                    }
                    //将父节点置为黑色，祖父节点置为红色
                    Node ggParentNode = gParentNode.parent;
                    Node gParentNodeTemp = gParentNode;
                    gParentNode.color = RED;
                    parentNode.color = BLACK;
                    gParentNode = rightRotate(gParentNode);
                    if(ggParentNode != null) {
                        if(ggParentNode.left == gParentNodeTemp)
                            ggParentNode.left = gParentNode;
                        else
                            ggParentNode.right = gParentNode;
                    } else {
                        this.root = gParentNode;
                    }
                }
            } else {
                if(gParentNode.left != null && gParentNode.left.color == RED) {   //叔节点为红色
                    //叔父节点均置为黑色，祖父节点置为红色，然后继续向上平衡
                    gParentNode.color = RED;
                    gParentNode.left.color = gParentNode.right.color = BLACK;
                } else {            //叔节点为黑色
                    // 先右旋转
                    if(newNode == parentNode.left) {
                        parentNode = rightRotate(parentNode);
                        gParentNode.right = parentNode;
                    }
                    //将父节点置为黑色，祖父节点置为红色
                    Node ggParentNode = gParentNode.parent;
                    Node gParentNodeTemp = gParentNode;
                    gParentNode.color = RED;
                    parentNode.color = BLACK;
                    gParentNode = leftRotate(gParentNode);
                    if(ggParentNode != null) {
                        if(ggParentNode.left == gParentNodeTemp)
                            ggParentNode.left = gParentNode;
                        else
                            ggParentNode.right = gParentNode;
                    } else {
                        this.root = gParentNode;
                    }
                }
            }

            newNode = gParentNode;
        }

        this.root.color = BLACK;
//        printTree();
    }

    //   node                     x
    //  /   \     左旋转         /  \
    // T1   x   --------->   node   T3
    //     / \              /   \
    //    T2 T3            T1   T2
    private Node leftRotate(Node node){
        Node pNode = node.parent;
        Node x = node.right;

        // 左旋转
        node.right = x.left;
        node.parent = x;
        x.left = node;
        x.parent = pNode;

        return x;
    }

    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   ------->   y   node
    //  / \                       /  \
    // y  T1                     T1  T2
    private Node rightRotate(Node node){
        Node pNode = node.parent;
        Node x = node.left;

        // 右旋转
        node.left = x.right;
        node.parent = x;
        x.right = node;
        x.parent = pNode;

        return x;
    }

    //获取树的深度
    public int hight() {
        return hight(root);
    }

    public int hight(Node node) {
        //递归实现
        if(node == null)
            return 0;

        return Math.max(hight(node.left)+1, hight(node.right)+1);
    }

    class Node{
        K key;
        V value;
        Node left, right, parent;
        boolean color;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.parent = null;
            this.color = RED;
        }
    }

    //===========================================================
    //打印树的结构
    public void printTree() {
        printTree(root, 0);
    }

    public void printTree(Node node, int hight) {
        int hightTmp = hight;

        if(node == null)
            return;

        printTree(node.right, hight+1);

        StringBuilder sb = new StringBuilder();
        while(hightTmp-- != 0) {
            sb.append("-------");
        }
        sb.append(node.key);
        if(node.color) {
            sb.append("R");
        } else {
            sb.append("B");
        }
        System.out.println(sb);

        printTree(node.left, hight+1);
    }
}
