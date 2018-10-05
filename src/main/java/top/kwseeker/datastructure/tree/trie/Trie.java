package top.kwseeker.datastructure.tree.trie;

import top.kwseeker.datastructure.tree.bst.BSTSet;
import top.kwseeker.util.FileOperation;

import java.util.ArrayList;
import java.util.TreeMap;

public class Trie {

    public static void main(String[] args) {

        //比较BST和Trie存储读取大数据量字符串时的性能
        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("src/main/resources/text/pride-and-prejudice.txt", words)){

            long startTime = System.nanoTime();

            BSTSet<String> set = new BSTSet<>();
            for(String word: words)
                set.add(word);
            for(String word: words)
                set.contains(word);

            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1000000000.0;

            System.out.println("Total different words: " + set.getSize());
            System.out.println("BSTSet: " + time + " s");


            startTime = System.nanoTime();

            Trie trie = new Trie();
            for(String word: words)
                trie.add(word);
            for(String word: words)
                trie.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;

            System.out.println("Total different words: " + trie.getSize());
            System.out.println("Trie: " + time + " s");
        }
    }

    private Node root;
    private int size;

    public Trie(){
        root = new Node();
        size = 0;
    }

    // 获得Trie中存储的单词数量
    public int getSize(){
        return size;
    }

    // 向Trie中添加一个新的单词word
    // 一个字符一个字符地查询，存在则继续查下一个字符，否则创建新节点
    public void add(String word){

        Node cur = root;
        for(int i = 0 ; i < word.length() ; i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }

        if(!cur.isWord){
            cur.isWord = true;
            size ++;
        }
    }

    // 查询单词word是否在Trie中
    public boolean contains(String word){
        Node cur = root;
        for(int i = 0 ; i < word.length() ; i++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

    // 查询是否在Trie中有单词以prefix为前缀
    public boolean startsWith(String prefix){
        Node cur = root;
        for(int i = 0 ; i < prefix.length() ; i ++){
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        return true;
    }

    // 模式匹配查找字符串，如： pr.de 匹配 pride
    public boolean search(String word) {
        return match(root, word, 0);
    }

    // 模式匹配， “.”匹配任意字符
    private boolean match(Node node, String word, int index){

        if(index == word.length())
            return node.isWord;

        char c = word.charAt(index);

        if(c != '.') {
            if(node.next.get(c) == null)
                return false;
            return match(node.next.get(c), word, index + 1);
        } else {
            for(char nextChar: node.next.keySet())
                if(match(node.next.get(nextChar), word, index + 1))
                    return true;
            return false;
        }
    }

    //字典树节点, 使用TreeMap存储子节点
    private class Node{
        public boolean isWord;
        public TreeMap<Character, Node> next;

        public Node(boolean isWord){
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node(){
            this(false);
        }
    }
}

