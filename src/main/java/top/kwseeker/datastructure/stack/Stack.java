package top.kwseeker.datastructure.stack;

//使用双向链表实现的栈
public class Stack<T extends Comparable<T>> {
    private Node<T> head;
    private Node<T> tail;

    public static void main(String[] args) {
        Stack<String> stringStack = new Stack<>();
        stringStack.push("C");
        stringStack.push("B");
        stringStack.push("F");
        stringStack.push("D");
        System.out.println("Pop data: ");
        System.out.println(stringStack.pop() + " "
                + stringStack.pop() + " "
                + stringStack.pop() + " "
                + stringStack.pop() + " ");
    }

    public Stack() {
        this.head = this.tail = null;
    }

    //入栈
    public void push(T element) {
        Node<T> newNode = new Node<>(element);
        if(this.head == null) {
            this.head = newNode;
        } else {
            Node<T> lastTail = this.tail;
            if(lastTail.getMaxValue().compareTo(newNode.getMaxValue()) >= 0) {
                newNode.maxValue = lastTail.getMaxValue();
            }
            newNode.pre = this.tail;
            this.tail.next = newNode;
        }
        this.tail = newNode;
    }

    //出栈
    public T pop() {
        if(this.tail == null) {
            System.out.println("No more elements to pop");
            return null;
        }

        T tmp = this.tail.getData();

        if(this.tail.pre != null) {
            this.tail.pre.next = null;
            this.tail = this.tail.pre;
        } else {
            this.head = this.tail = null;
        }

        return tmp;
    }

    //获取栈内最大值
    public T getMax() {
        return this.tail.maxValue;
    }

    //链表节点
    static class Node<T> {
        private T data;
        //添加一个数据, 存储最大值
        private T maxValue;
        Node<T> pre;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.maxValue = data;
            this.pre = null;
            this.next = null;
        }

        public T getData() {
            return data;
        }

        public T getMaxValue() {
            return maxValue;
        }
    }
}
