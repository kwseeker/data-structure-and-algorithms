# Leetcode题目中的一些小算法技巧

这里列举从Leetcode题目中总结的一些小的算法技巧。

代码： go-template/leecode

> 下面序列以S表示。



## 一些序列问题可以将将序列元素作为key存储哈希表，减少循环嵌套

+ [1. 两数之和](https://leetcode.cn/problems/two-sum/)

  将序列元素作为key存储哈希表，将S[currentIdex] + S[?] = T 求和转化成 T- S[currentIdex] 是否存在于哈希表的问题，可以减少一层循环嵌套。

+ [128. 最长连续序列](https://leetcode.cn/problems/longest-consecutive-sequence/)

+ [1577. 数的平方等于两数乘积的方法数](https://leetcode.cn/problems/number-of-ways-where-square-of-number-is-equal-to-product-of-two-numbers/)

##  从中间值双向遍历等效于从极值单项遍历

+ [128. 最长连续序列](https://leetcode.cn/problems/longest-consecutive-sequence/)

  比如序列 100, 4, 6, 200, 5, 3, 1, 2；判断连续值时从4开始先向上再向下统计，与从1只向上统计效果一样，所以代码中只需要选择一个方向统计。

  而且为了减少重复循环次数，选择从极值开始统计（非极值则直接跳过）。

