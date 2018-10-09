package top.kwseeker.datastructure.tree.bst;

import java.util.*;

//TODO: 除了下面方法解决的一些问题，还有其他一些问题可以实现，如
// rank()元素在树中的排名，
// 随便给出一个值，找出树中此值 ceil floor 对应的值
// 实现支持重复元素的BST(可以通过添加一个field记录这个重复值的个数)
public class BinarySearchTree<E extends Comparable<E>> {

    //二分搜索树的结构
    private Node<E> root;  //根节点
    private int size;   //节点个数

    public static void main(String[] args) {
        BinarySearchTree<String> bstString = new BinarySearchTree<>();
        bstString.add("Q");
        bstString.add("W");
        bstString.add("E");
        bstString.add("R");
        bstString.add("T");
        bstString.add("Y");
        bstString.add("U");
        bstString.add("I");
        bstString.add("O");
        bstString.add("P");

        System.out.println(bstString.order(Order.PRE));
        System.out.println(bstString.order(Order.IN));
        System.out.println(bstString.order(Order.POST));
        System.out.println(bstString.order(Order.LEVEL));
        //打印树的图表
        BinaryTreePrinter.printNode(bstString.root);

        //删除值为“W”的节点
        bstString.removeNR("W");
        BinaryTreePrinter.printNode(bstString.root);

        bstString.remove("U");
        BinaryTreePrinter.printNode(bstString.root);
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(E e) {
        root = add(root, e);
//        if(root == null) {
//            root = new Node(e);
//            size++;
//            return;
//        }
//        add(root. e);
    }

    /**
     * 向指定节点的子树中添加节点
     * @param node 指定节点
     * @param e 节点数据
     */
    public Node<E> add(Node<E> node, E e) {
        if(node == null){
            size ++;
            return new Node<>(e);
        }

        if(e.compareTo(node.e) < 0)
            node.left = add(node.left, e);
        else if(e.compareTo(node.e) > 0)
            node.right = add(node.right, e);

        return node;
//        if(e.equals(node.e)) {  //等值节点已存在
//            //已存在，直接退出
//        } else if(e.compareTo(node.e) < 0) {   //插入左子树
//            if(node.left == null) {
//                node.left = new Node(e);
//                size++;
//            } else {
//                add(node.left, e);
//            }
//        } else if(e.compareTo(node.e) > 0) {    //插入右子树
//            if(node.right == null) {
//                node.right = new Node(e);
//                size++;
//            } else {
//                add(node.right, e);
//            }
//        }
    }

    //搜索是否存在值为e的节点
    public boolean contains(E e) {
        return contains(root, e);
    }

    public boolean contains(Node<E> node, E e) {
        if(node == null) {
            return false;
        }

        if(e.compareTo(node.e) == 0) {
            return true;
        } else if(e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }

    //遍历：首先访问根结点然后遍历左子树，最后遍历右子树。
    public String order(Order order) {
        StringBuffer sb = new StringBuffer();
        switch (order) {
            case PRE:
                return preOrder(root, sb).toString();
            case IN:
                return inOrder(root, sb).toString();
            case POST:
                return postOrder(root, sb).toString();
            case LEVEL:
                return levelOrder(root, sb).toString();
        }
        return "";
    }

    public StringBuffer preOrder(Node node, StringBuffer stringBuffer) {
        if(node == null) {
            return stringBuffer.append("->");
        }
        preOrder(node.left, stringBuffer);
        stringBuffer.append(node.e.toString());
        preOrder(node.right, stringBuffer);

        return stringBuffer;
    }

    //中序遍历
    public StringBuffer inOrder(Node node, StringBuffer stringBuffer) {
        if(node == null) {
            return stringBuffer.append("->");
        }
        stringBuffer.append(node.e.toString());
        inOrder(node.left, stringBuffer);
        inOrder(node.right, stringBuffer);

        return stringBuffer;
    }

    //使用JDK Stack实现非递归中序遍历
    public StringBuffer inOrderNR(Node node, StringBuffer stringBuffer) {
        //TODO: Stack实现非递归中序遍历
        return null;
    }

    //后序遍历
    public StringBuffer postOrder(Node node, StringBuffer stringBuffer) {
        if(node == null) {
            return stringBuffer.append("->");
        }
        postOrder(node.left, stringBuffer);
        postOrder(node.right, stringBuffer);
        stringBuffer.append(node.e.toString());

        return stringBuffer;
    }

    //层序遍历:一层一层的遍历，使用Queue实现
    public StringBuffer levelOrder(Node node, StringBuffer stringBuffer) {
        if(node == null) {
            return stringBuffer.append("");
        }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node<E> levelNode = queue.remove();
            stringBuffer.append(levelNode.e.toString());

            if(levelNode.left != null) {
                queue.add(levelNode.left);
            }
            if(levelNode.right != null) {
                queue.add(levelNode.right);
            }
        }

        return stringBuffer;
    }

    //删除某个节点的原理：
    //1）如果这个节点是叶节点，则直接删除（将父节点指向这个节点的引用置为空,其实就是将这个节点置为null）；
    //2) 如果这个节点只包含左子树或右子树，将父节点指向这个节点的引用指向其左子树根节点或右子树根节点；
    //3) 如果这个节点同时包含左子树和右子树，查找此节点的前驱或后继节点替代此节点。
    //这是非递归的实现相对递归的实现有点复杂，但是比递归理解起来更直观。
    public void removeNR(E element) {
        //首先搜索到这个节点
        Node<E> node = getPNodeByElement(element);
        if(node == null){
            System.out.println("没有包含此元素的节点");
            return;
        }
//        Node<E> nodeToRemove = (node.left != null && node.left.e.compareTo(element) == 0)? node.left:node.right;

        if(node.left != null && node.left.e.compareTo(element) == 0) {  //左子树
            Node<E> nodeToRemove = node.left;
            if(node.left.left == null) {
                if(node.left.right == null) {
                    node.left = null;
                } else {
                    node.left = node.left.right;
                }
            } else {
                if(node.left.right == null) {
                    node.left = node.left.left;
                } else {
                    //找到以nodeToRemove左子树的前驱节点(也可以找后继节点)
                    Node<E> newChildRootNode = maximum(node.left.left);
                    removeMax(node.left.left);
                    newChildRootNode.left= node.left.left;
                    newChildRootNode.right = node.left.right;
                    node.left = newChildRootNode;
                }
            }
            nodeToRemove.left = nodeToRemove.right = null;    //消除对其他节点引用，不这样写，这个节点应该也会被回收吧，没有其他人引用它，虽然它引用了其他节点。
        } else {    //右子树
            Node<E> nodeToRemove = node.right;
            if(node.right.left == null) {
                if(node.right.right == null) {
                    node.right = null;
                } else {
                    node.right = node.right.right;
                }
            } else {
                if(node.right.right == null) {
                    node.right = node.right.left;
                } else {
                    //找到以nodeToRemove右子树的前驱节点(也可以找后继节点)
                    Node<E> newChildRootNode = maximum(node.right.left);
                    removeMax(node.right.left);
                    newChildRootNode.left= node.right.left;
                    newChildRootNode.right = node.right.right;
                    node.right = newChildRootNode;
                }
            }
            nodeToRemove.left = nodeToRemove.right = null;
        }
    }

    public void remove(E e){
        root = remove(root, e);
    }
    private Node<E> remove(Node<E> node, E e){

        if( node == null )
            return null;

        if( e.compareTo(node.e) < 0 ){
            node.left = remove(node.left , e);
            return node;
        } else if(e.compareTo(node.e) > 0 ){
            node.right = remove(node.right, e);
            return node;
        } else{   // e.compareTo(node.e) == 0
            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况
            // 找到待删除节点的后继，用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }

    public Node<E> getPNodeByElement(E element) {
        if(root != null && root.e.compareTo(element) == 0) {
            return null;
        }
        return getPNodeByElement(root, element);
    }
    public Node<E> getPNodeByElement(Node<E> node, E element) {
        if(node == null) {
            return null;
        }

        if(node.left.e.compareTo(element) == 0 || node.right.e.compareTo(element) == 0) {
            return node;
        } else if(node.left.e.compareTo(element) > 0){
            return getPNodeByElement(node.left, element);
        } else if(node.right.e.compareTo(element) < 0) {
            return getPNodeByElement(node.right, element);
        }
//        throw new Exception("No node contains this element");
        return null;
    }

    public E minimum() throws Exception {
        if(size == 0)
            throw new Exception("BST is empty!");

        return minimum(root).e;
    }
    private Node<E> minimum(Node<E> node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }
    public E maximum() throws Exception {
        if(size == 0)
            throw  new Exception("BST is empty!");
        return maximum(root).e;
    }
    public Node<E> maximum(Node<E> node) {
        if(node.right == null)
            return node;
        return maximum(node.right);
    }
    //删除最小值的节点
    public E removeMin() throws Exception {
        E e = minimum();
        root = removeMin(root);
        return e;
    }
    private Node<E> removeMin(Node<E> node){
        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }
    //删除最大值的节点
    public E removeMax() throws Exception {
        E e = maximum();
        root = removeMax(root);
        return e;
    }
    public Node<E> removeMax(Node<E> node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size --;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;
    }

    //===========================================================
    //打印树的结构
    //原理：先获取树的深度，然后确定每一行需要打印的空格数，然后一层一层的打印
    //还有一种方法可以借助后序遍历，打印一个躺着的树，实现上比下面这个简单一些
    static class BinaryTreePrinter {
        public static <E extends Comparable<E>> void printNode(Node<E> root) {
            int maxLevel = maxLevel(root);
            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        private static <E extends Comparable<E>> void printNodeInternal(List<Node<E>> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || isAllElementsNull(nodes))
                return;

            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

            printWhitespaces(firstSpaces);

            List<Node<E>> newNodes = new ArrayList<>();
            for (Node<E> node : nodes) {
                if (node != null) {
                    System.out.print(node.e);
                    newNodes.add(node.left);
                    newNodes.add(node.right);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                printWhitespaces(betweenSpaces);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).left != null)
                        System.out.print("/");
                    else
                        printWhitespaces(1);

                    printWhitespaces(i + i - 1);

                    if (nodes.get(j).right != null)
                        System.out.print("\\");
                    else
                        printWhitespaces(1);

                    printWhitespaces(endgeLines + endgeLines - i);
                }
                System.out.println("");
            }
            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++)
                System.out.print(" ");
        }

        //树的高度
        private static <E extends Comparable<E>> int maxLevel(Node<E> node) {
            if (node == null)
                return 0;
            return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
        }

        private static <E> boolean isAllElementsNull(List<E> list) {
            for (Object object : list) {
                if (object != null)
                    return false;
            }
            return true;
        }
    }
    //===========================================================
    //二分搜索树的节点
    static class Node<E extends Comparable<E>> {
        E e;                 //节点数据
        Node<E> left, right;    //左右子节点

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

    enum Order {
        PRE(1),
        IN(2),
        POST(3),
        LEVEL(4);

        int orderNo;

        Order(int orderNo) {
            this.orderNo = orderNo;
        }
    }
}
