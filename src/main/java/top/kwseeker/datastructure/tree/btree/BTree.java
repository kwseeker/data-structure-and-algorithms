package top.kwseeker.datastructure.tree.btree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 为了更通用地实现B树，采用键值对都用泛型表示
 *
 * 参考《算法导论》上B树的伪代码实现Java B树。
 */
public class BTree<K extends Comparable<K>, V> {    //TODO: 这是个很好的实现

    //最小度数
    private static final int DEFAULT_MINDEGREE = 2; //B树的最小度数为2. B树节点中包含的关键字数量 t-1 <= n <= 2t-1
    private int minDegree = DEFAULT_MINDEGREE;
    //根节点
    private Node<K, V> rootNode;
    //键的数量的上下界
    private int minKeySize;
    private int maxKeySize;
    //键的比较函数对象
//    private Comparator<K> kComparator;

    //B树构造函数,即创建根节点
    public BTree() {
        // rootNode = new Node<K, V>();
        rootNode = new Node<>();
        rootNode.setLeaf(true);
    }

    public BTree(int minDegree) {
        this();
        this.minDegree = minDegree;
        minKeySize = this.minDegree - 1;
        maxKeySize = 2*this.minDegree - 1;
    }

//    public BTree(Comparator<K> kComparator) {
//        // rootNode = new Node<K, V>(kComparator);
//        rootNode = new Node<>(kComparator);
//        rootNode.setLeaf(true);
//        this.kComparator = kComparator;
//    }
//
//    public BTree(Comparator<K> kComparator, int minDegree) {
//        this(kComparator);
//        this.minDegree = minDegree;
//        minKeySize = minDegree - 1;
//        maxKeySize = 2*minDegree - 1;
//    }

    //常用操作

    //用于查询的递归函数
    //获取节点所有键，看key是否存在于这里面，如果存在就取出其值，不存在则获取键的范围进而获取子节点，到子节点继续搜索
    private V search(Node<K, V> node, K key) {
        NodeSearchRet<K, V> nodeSearchRet = node.searchNode(key);
        if(nodeSearchRet.isExist()) {  //键在当前节点的键值对链表
            return nodeSearchRet.getEntry().getValue();
        }
        //键不在当前节点的键值对链表
        if(node.isLeaf()) {
            return null;
        }
        return search(nodeSearchRet.getChildNode(), key);
    }
    /**
     * 查找键的值 (递归查询，直到叶节点)
     */
    public V searchValue(K key) {
        //从根节点开始
        return search(rootNode, key);
    }

    // TODO: 逻辑实现
    private boolean insert(Node<K, V> node, K key, V value) {
        if(node.size() == maxKeySize) {         //节点已插满，分裂

        } else {                                //未插满，直接插入

        }

        return false;
    }
    //查找要插入的叶节点
    private Node<K, V> searchLeafNode(K key) {

        return null;
    }
    /**
     * 插入键值对
     * 参考《算法导论》 伪代码
     *
     * 首先搜索到要插入的叶节点，然后判断叶节点是否已满
     * 1) 已满，分裂
     *    将最中间的关键字提升到原来的满节点的父节点中，原来的满节点剩余的元素分列为两个含有t-1个键的节点；
     *    分裂过后要检查父节点是否是满的，如果满了要继续分裂，为了确保每次子节点分裂前父节点都不是满的；
     * 2) 未满，则直接按顺序插入即可
     */
    public boolean insert(K key, V value) {

        return false;
    }

    /**
     * 删除键值对
     *
     * 《算法导论》上给出了5种情况下的删除规则
     */

    /**
     * B树遍历，打印出所有键值对
     */

    //=========================================================

    /**
     * 节点中的键值对
     *
     * K: 键的类型
     * V: 值的类型
     */
    private class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    /**
     * 节点：
     *
     * 父节点引用
     * 子节点引用链表
     * 键值对链表
     * 键的比较函数对象（不同的键类型比较方法不同）
     */
    private class Node<K extends Comparable<K>, V> {
        //父节点
        private Node<K, V> parentNode;
        //子节点
        private List<Node<K, V>> childNodes;
        //节点的键值对链表,升序排列
        private List<Entry<K, V>> entrys;
        //是否为叶子节点
        private boolean leaf;
        //键的比较函数对象
//        private Comparator<K> kComparator;

        private Node() {
            childNodes = new ArrayList<Node<K, V>>();   // n+1 个
            entrys = new ArrayList<Entry<K, V>>();      // n 个
        }

//        private Node(Comparator<K> kComparator) {
//            this();
//            this.kComparator = kComparator;
//        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public int size() {
            return entrys.size();
        }

        //泛型比较 less than | equal | greater than
//        @SuppressWarnings("unchecked")
        public int compare(K key1, K key2) {
            //常见的类型都已经提供了此 Comparable接口的实现，需要对未提供此接口实现的类型自定义比较逻辑
//            return kComparator == null?((Comparable<K>)key1.compareTo(key2)) : kComparator.compare(key1, key2);
            return key1.compareTo(key2);
        }

        //最小二分法查找节点中键为key的键值对
        //结果有两种，能查到就把键值对放到NodeSearchRet对象，不能查到就把子节点放到这个对象
        public NodeSearchRet<K, V> searchNode(K key) {
            NodeSearchRet<K, V> nodeSearchRet = new NodeSearchRet<>();
            int len;    //存储键值对链表的长度
            int indexBegin, indexEnd, indexMid=0; //最小二分索引
            indexBegin = 0;
            indexEnd = entrys.size() - 1;
            int compareRet = 0;
            while(indexBegin <= indexEnd) {
                indexMid = (indexBegin + indexEnd)/2;
                compareRet = compare(entrys.get(indexMid).getKey(), key);
                if(compareRet < 0) {
                    indexBegin = indexMid+1;
                } else if (compareRet == 0) {
                    nodeSearchRet.setExist(true);
                    nodeSearchRet.setEntry(entrys.get(indexMid));
                    return nodeSearchRet;
                } else {
                    indexEnd = indexMid-1;
                }
            }
            if(compareRet < 0) {
                nodeSearchRet.setExist(false);
                nodeSearchRet.setChildNode(childNodes.get(indexMid+1));
            } else if(compareRet > 0) {
                nodeSearchRet.setExist(false);
                nodeSearchRet.setChildNode(childNodes.get(indexMid));
            } else {
                System.out.println("Error: node keys size should not be 0");
            }
            return nodeSearchRet;
        }
    }

    //通过键搜索节点的结果，要反馈： 键是否存在于当前的节点，键值对， 子节点
    private class NodeSearchRet<K extends Comparable<K>, V> {
        //键是否存在于当前节点
        private boolean isExist;
        //键值对
        private Entry<K, V> entry;
        //子节点
        private Node<K, V> childNode;

        public boolean isExist() {
            return isExist;
        }

        public void setExist(boolean exist) {
            isExist = exist;
        }

        public Entry<K, V> getEntry() {
            return entry;
        }

        public void setEntry(Entry<K, V> entry) {
            this.entry = entry;
        }

        public Node<K, V> getChildNode() {
            return childNode;
        }

        public void setChildNode(Node<K, V> childNode) {
            this.childNode = childNode;
        }
    }
}
