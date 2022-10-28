package top.kwseeker.dsaa.tree.trie;

/**
 * 字典树（前缀树)
 */
public class Trie {


    //节点
    static class Node {
        /** 子节点数组 */
        Node[] slot = new Node[26];
        /** 该节点存储的字符 */
        char c;
        /** 标识这个字符是否为一个单词的结尾 */
        boolean isWord;
        /** 当前字符组成的单词个数 */
        boolean count;
    }
}
