# B树

参考资料：

+ 《算法导论》C18
+ 《数据结构与算法分析: C语言描述》C4.7



测试Demo: tree/src/main/java/top/kwseeker/dsaa/tree/btree。
源码来自外网，是找到的实现中最通用且功能完整的示例代码。但是别把它当作最优的实现。
看源码感觉还是有些问题的（比如节点内多key存储选用的单纯的哈希桶[多个key要按key的顺序存储]，插入以及删除会有大量数据移动，读多写少还可以，如果度数较大且写较多生产环境可能换平衡二叉树好些）。

B树是为磁盘或其他直接存取的辅助存储设备而设计的一种平衡搜索树（树的高度低对应的磁盘IO就少，一个节点可存很多个数据，每个节点可能有很多子节点）。

B树定义（说结构特征更合适，来自维基百科）：

根据 Knuth 的定义，一个 *m* 阶的B树是一个有以下属性的树：

1. 每一个节点最多有 *m* 个子节点
2. 每一个非叶子节点（除根节点）最少有 ⌈*m*/2⌉ 个子节点
3. 如果根节点不是叶子节点，那么它至少有两个子节点
4. 有 *k* 个子节点的非叶子节点拥有 *k* − 1 个键
5. 所有的叶子节点都在同一层

> 还有一种使用最小度数 degree 描述的定义，节点的”度“指节点的子节点的个数，树的”度“指树中所有节点的子节点个数的最大值。最小度数是树中所有节点的子节点个数的最小值。
>
> 只不过上面的m阶表示B树最多有多少个子节点，最小度数表示B树节点最少有多少个子节点。对于B树的定义，最小度数t的B树和m阶的B树，m=2t。
>
> m阶的B树，节点最多有m-1个key。



## 实现原理

先抛几个自认为比较重要的几个问题并解答（按Demo中的分析）：

+ **B树节点是怎么存多个key和数据的？**

  下面只是一种实现：

  B树节点定义中由一个K-V哈希桶存储key和数据。

  Demo实现中多个key和value，排序存入哈希桶。

  > 使用哈希桶, 碰到写比较多的情况，性能可能会比较差每插入一个数据或删除一个数据后面的全部数据都要移动。

  ```java
  public class BTNode<K extends Comparable<K>, V> {
  	...
  	protected BTKeyValue<K, V>[] mKeys;
  }
  
  public class BTKeyValue<K extends Comparable<K>, V> {
      protected K mKey;
      protected V mValue;
      ...
  }
  
  //BNode定义
  public class BTNode<K extends Comparable, V> {
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
      //父节点引用, Demo中没有这个，
      protected BTNode parent;
  ｝
  ```

+ **B树增删节点及平衡机制？**

  这个平衡机制也是有论文支撑的，这个是B树的核心也是难点。论文不好看，但是看不懂论文，下面的代码就算看完也不知道为何这么做，当然也肯定背不下来，还没见到面试让解释B树平衡机制的。

  **插入**：

  遍历到叶子节点插入，节点key数量达到m-1 (2t-1) 时，节点会分裂；中间的key（设keyA）会合并到父节点，分裂出来的两个节点还是保持在当前层，位于keyA左右两侧；如果父节点也满了父节点继续分裂（递归处理）；根节点分裂，中间的key会新建一个节点作为根节点（层数增加）。

  **删除**：

  如果是删除叶子节点的key, 删除后满足B树性质结束；
  如果key少于m/2，向左右两边的兄弟借key（可以优先向左兄弟借，左兄弟不富余就向右兄弟借；优先向右兄弟借也可以规则不是死的只要满足B树性质即可），然后旋转（和平衡二叉树、红黑树的旋转类似，向右兄弟借就左旋转，向左兄弟借就右旋转）；

  如果左右两边兄弟都不富余就向父亲借，然后兄弟合并（可以优先合并左边也可以优先合并右边）；

  如果根节点只有一个key, 删除节点时被子节点借去，子节点合并，层数降低。

  ```java
  //kv插入
  public BTree insert(K key, V value) {
      if (mRoot == null) {
          mRoot = createNode();
      }
  
      ++mSize;
     	//根节点满了的话插不下去，根节点要分裂
      if (mRoot.mCurrentKeyNum == BTNode.UPPER_BOUND_KEYNUM) {
          BTNode<K, V> btNode = createNode();
          btNode.mIsLeaf = false;
          btNode.mChildren[0] = mRoot;
          mRoot = btNode;
          splitNode(mRoot, 0, btNode.mChildren[0]);
      }
  	//这时根节点是可以插入的
      insertKeyAtNode(mRoot, key, value);
      return this;
  }
  
  //节点key满了分裂：
  //中间的key（设keyA）合并到父节点，keyA左右分裂成两个新节点插入到keyA左右
  private void splitNode(BTNode parentNode, int nodeIdx, BTNode btNode) {
      int i;
  
      BTNode<K, V> newNode = createNode();
  
      newNode.mIsLeaf = btNode.mIsLeaf;
  
      // Since the node is full,
      // new node must share LOWER_BOUND_KEYNUM (aka t - 1) keys from the node
      newNode.mCurrentKeyNum = BTNode.LOWER_BOUND_KEYNUM;
  
      // Copy right half of the keys from the node to the new node
      for (i = 0; i < BTNode.LOWER_BOUND_KEYNUM; ++i) {
          newNode.mKeys[i] = btNode.mKeys[i + BTNode.MIN_DEGREE];
          btNode.mKeys[i + BTNode.MIN_DEGREE] = null;
      }
  
      // If the node is an internal node (not a leaf),
      // copy the its child pointers at the half right as well
      if (!btNode.mIsLeaf) {
          for (i = 0; i < BTNode.MIN_DEGREE; ++i) {
              newNode.mChildren[i] = btNode.mChildren[i + BTNode.MIN_DEGREE];
              btNode.mChildren[i + BTNode.MIN_DEGREE] = null;
          }
      }
  
      // The node at this point should have LOWER_BOUND_KEYNUM (aka min degree - 1) keys at this point.
      // We will move its right-most key to its parent node later.
      btNode.mCurrentKeyNum = BTNode.LOWER_BOUND_KEYNUM;
  
      // Do the right shift for relevant child pointers of the parent node
      // so that we can put the new node as its new child pointer
      for (i = parentNode.mCurrentKeyNum; i > nodeIdx; --i) {
          parentNode.mChildren[i + 1] = parentNode.mChildren[i];
          parentNode.mChildren[i] = null;
      }
      parentNode.mChildren[nodeIdx + 1] = newNode;
  
      // Do the right shift all the keys of the parent node the right side of the node index as well
      // so that we will have a slot for move a median key from the splitted node
      for (i = parentNode.mCurrentKeyNum - 1; i >= nodeIdx; --i) {
          parentNode.mKeys[i + 1] = parentNode.mKeys[i];
          parentNode.mKeys[i] = null;
      }
      parentNode.mKeys[nodeIdx] = btNode.mKeys[BTNode.LOWER_BOUND_KEYNUM];
      btNode.mKeys[BTNode.LOWER_BOUND_KEYNUM] = null;
      ++(parentNode.mCurrentKeyNum);
  }
  
  //从根节点往下遍历找到叶子节点插入，如果节点
  private void insertKeyAtNode(BTNode rootNode, K key, V value) {
      int i;
      int currentKeyNum = rootNode.mCurrentKeyNum;
  
      //key已经存在更新
      if (rootNode.mIsLeaf) {
          if (rootNode.mCurrentKeyNum == 0) {
              // Empty root
              rootNode.mKeys[0] = new BTKeyValue<K, V>(key, value);
              ++(rootNode.mCurrentKeyNum);
              return;
          }
  
          // Verify if the specified key doesn't exist in the node　校验key是否存在于某node, 存在则更新
          for (i = 0; i < rootNode.mCurrentKeyNum; ++i) {
              if (key.compareTo(rootNode.mKeys[i].mKey) == 0) {
                  // Find existing key, overwrite its value only
                  rootNode.mKeys[i].mValue = value;
                  --mSize;
                  return;
              }
          }
  
          i = currentKeyNum - 1;
          BTKeyValue<K, V> existingKeyVal = rootNode.mKeys[i];
          while ((i > -1) && (key.compareTo(existingKeyVal.mKey) < 0)) {
              rootNode.mKeys[i + 1] = existingKeyVal;
              --i;
              if (i > -1) {
                  existingKeyVal = rootNode.mKeys[i];
              }
          }
  
          i = i + 1;
          rootNode.mKeys[i] = new BTKeyValue<K, V>(key, value);
  
          ++(rootNode.mCurrentKeyNum);
          return;
      }
  
      // This is an internal node (i.e: not a leaf node)
      // So let find the child node where the key is supposed to belong
      i = 0;
      int numberOfKeys = rootNode.mCurrentKeyNum;
      BTKeyValue<K, V> currentKey = rootNode.mKeys[i];
      while ((i < numberOfKeys) && (key.compareTo(currentKey.mKey) > 0)) {
          ++i;
          if (i < numberOfKeys) {
              currentKey = rootNode.mKeys[i];
          } else {
              --i;
              break;
          }
      }
  
      if ((i < numberOfKeys) && (key.compareTo(currentKey.mKey) == 0)) {
          // The key already existed so replace its value and done with it
          currentKey.mValue = value;
          --mSize;
          return;
      }
  
      BTNode<K, V> btNode;
      if (key.compareTo(currentKey.mKey) > 0) {
          btNode = BTNode.getRightChildAtIndex(rootNode, i);
          i = i + 1;
      } else {
          if ((i - 1 >= 0) && (key.compareTo(rootNode.mKeys[i - 1].mKey) > 0)) {
              btNode = BTNode.getRightChildAtIndex(rootNode, i - 1);
          } else {
              btNode = BTNode.getLeftChildAtIndex(rootNode, i);
          }
      }
  
      if (btNode.mCurrentKeyNum == BTNode.UPPER_BOUND_KEYNUM) {
          // If the child node is a full node then handle it by splitting out
          // then insert key starting at the root node after splitting node
          splitNode(rootNode, i, btNode);
          insertKeyAtNode(rootNode, key, value);
          return;
      }
  
      insertKeyAtNode(btNode, key, value);
  }
  ```

+ **其他（遍历、搜索）**

  比较简单、略。