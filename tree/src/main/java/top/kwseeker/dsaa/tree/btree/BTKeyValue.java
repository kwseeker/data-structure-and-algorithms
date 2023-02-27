package top.kwseeker.dsaa.tree.btree;

/**
 * B树节点存储的key-value数据结构
 * @author tnguyen
 */
public class BTKeyValue<K extends Comparable, V> {
    protected K mKey;
    protected V mValue;

    public BTKeyValue(K key, V value) {
        mKey = key;
        mValue = value;
    }
}
