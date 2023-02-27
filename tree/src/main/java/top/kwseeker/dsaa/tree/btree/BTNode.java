package top.kwseeker.dsaa.tree.btree;

/**
 * B树节点
 *
 * @author tnguyen
 */
public class BTNode<K extends Comparable, V> {
    //最小度数，非叶子节点和非根节点的最小子节点个数
    public final static int MIN_DEGREE = 5;
    //非叶子节点和非根节点的最小key个数
    public final static int LOWER_BOUND_KEYNUM = MIN_DEGREE - 1;
    //非叶子节点和非根节点的最大key个数
    public final static int UPPER_BOUND_KEYNUM = (MIN_DEGREE * 2) - 1;

    //是否是叶子节点
    protected boolean mIsLeaf;
    //B树的节点可以存储多个key和value, 通过BTKeyValue哈希桶存储
    protected BTKeyValue[] mKeys;
    //当前节点key的数量
    protected int mCurrentKeyNum;
    //子节点的引用
    protected BTNode[] mChildren;


    public BTNode() {
        mIsLeaf = true;
        mCurrentKeyNum = 0;
        mKeys = new BTKeyValue[UPPER_BOUND_KEYNUM];
        mChildren = new BTNode[UPPER_BOUND_KEYNUM + 1];
    }

    protected static BTNode getChildNodeAtIndex(BTNode btNode, int keyIdx, int nDirection) {
        if (btNode.mIsLeaf) {
            return null;
        }

        keyIdx += nDirection;
        if ((keyIdx < 0) || (keyIdx > btNode.mCurrentKeyNum)) {
            return null;
        }

        return btNode.mChildren[keyIdx];
    }


    protected static BTNode getLeftChildAtIndex(BTNode btNode, int keyIdx) {
        return getChildNodeAtIndex(btNode, keyIdx, 0);
    }


    protected static BTNode getRightChildAtIndex(BTNode btNode, int keyIdx) {
        return getChildNodeAtIndex(btNode, keyIdx, 1);
    }


    protected static BTNode getLeftSiblingAtIndex(BTNode parentNode, int keyIdx) {
        return getChildNodeAtIndex(parentNode, keyIdx, -1);
    }


    protected static BTNode getRightSiblingAtIndex(BTNode parentNode, int keyIdx) {
        return getChildNodeAtIndex(parentNode, keyIdx, 1);
    }
}
