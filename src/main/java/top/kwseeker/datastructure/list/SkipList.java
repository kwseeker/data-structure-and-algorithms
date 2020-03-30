package top.kwseeker.datastructure.list;

import java.util.Random;

/**
 * 跳表一般能拥有和平衡树一样的效率，但是实现上确比较简单，所以很多程序使用跳表替代平衡树
 *
 * Redis中有序集合就是使用跳表实现的。
 * 一方面它需要一个 hash 结构存储 value 和 score 的对应关系,另一方面需要提供按照 score 排序的功能，
 * 还需要能够指定 score 的范围来获取 value列表的功能
 */
public class SkipList {

    private static final int MAX_LEVEL = 64;

    //最高层的链表头部
    private Node header = new Node();
    //跳表层高
    private int level=1;

    //跳跃列表插入节点
    public void add(Integer element) {
        int eleLevel = randomLevel();
        if(eleLevel > level) {  //拓展新层
            level = eleLevel;
            //头节点nexts数组拓展
            Node[] newNexts = new Node[level];
            for (int i = 0; i < header.level; i++) {
                newNexts[i] = header.nexts[i];
            }
            header.level = level;
            header.nexts = newNexts;
        }
        //从eleLevel的header开始逐层插入
        Node newNode = new Node(element, eleLevel);
        for (int i = eleLevel-1; i >= 0 ; i--) {
            Node current = header;
            insert(current, i, newNode);
        }
    }

    //index=level-1
    private void insert(Node current,int index, Node newNode) {
        Node next = current.nexts[index];
        if(next == null || next.value > newNode.value) {
            //将新元素插入当前节点node和next之间
            current.nexts[index] = newNode;
            newNode.nexts[index] = next;
        } else if(next.value < newNode.value) {
            //继续递归
            insert(next, index, newNode);
        } else {
            //暂时不支持插入重复数据
            //如果要插入重复数据，层数较高的一定要放在层数低的节点前面
            throw new RuntimeException("已经存在节点的值当前插入的值相同");
        }
    }

    //查询数据(TODO: 比较简单，后续实现)

    //删除数据(TODO: 比较简单，后续实现)

    //显示跳表中
    public void display(){
        for (int i = level-1; i >= 0; i--) {
            Node head = header;
            while (head.nexts[i] != null) {
                System.out.print(head.nexts[i].value + " ");
                head = head.nexts[i];
            }
            System.out.println();
        }
    }

    int randomLevel() {
        int k = 1;
        Random random = new Random();
        while (1 == random.nextInt(2)) {
            if( k > MAX_LEVEL ) {
                return MAX_LEVEL;
            }
            k++;
        }
        return k;
    }

    final class Node {
        //节点的值
        public Integer value;
        public Object Data;
        //层高
        public int level = 1;
        //所在每一层的下一个节点
        public Node[] nexts;

        public Node() {
            this.nexts = new Node[this.level];
        }

        public Node(Integer value, int level) {
            this.value = value;
            this.level = level;
            this.nexts = new Node[level];
        }
    }
}
