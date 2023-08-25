# 二叉搜索树（BST）

二叉搜索树（Binary Search Tree，又叫有序二叉树（Ordered Binary tree）、排序二叉树（Sorted Binary Tree）。

**特点：**

- 若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
- 若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
- 任意节点的左、右子树也分别为二叉查找树；

**使用场景：**

+ 基于组合模式实现的规则引擎

**优缺点：**

缺点（都是相对的）：非平衡，最坏的情况下可能变成链表，查询时间复杂度O(n)。

**节点定义：**

```
public Integer value;
public Node parent;
public Node left;
public Node right;
```

