# 排序算法

### 归并排序（合并排序）MergeSort

工作原理：

![归并排序](https://images2018.cnblogs.com/blog/741682/201805/741682-20180531230154409-830091394.gif)

将x轴作为索引，y轴作为值的大小，排序流程则如上所示。

将整个区间一分为二，再对每个小区间一分为二，逐渐地每一个小区间只包含一个或两个元素，然后进行排序和归并。
拆分的过程是递归实现，而合并的过程是代码主体。

使用案例：

+ Collections.sort() （TimSort / MergeSort）

    Collections.sort() 只适用于List集合，Collectios.sort()调用不同类型List集合的sort()方法，
    不同类型list的集合类排序都是通过Arrays.sort()接口对内部数组进行操作
    所以使用的算法都是一样的。
    
    比如针对 ArrayList 的操作是先获取 ArrayList 中的数组；然后再判断系统环境变量是否设置适用传统的归并排序，
    如果没有配置的话则使用TimSort算法排序（一种结合归并排序算法和插入排序算法的稳定高效的排序算法）。
    ```
    // elementData 是ArrayList的内部数组成员，0：起始排序位置， size：数组的大小， c：Comparator比较器的实例
    Arrays.sort((E[]) elementData, 0, size, c);
    ```
    
    ```
    //Java 1.8 Array.sort()
    public static <T> void sort(T[] a, int fromIndex, int toIndex,
                                Comparator<? super T> c) {
        if (c == null) {
            sort(a, fromIndex, toIndex);
        } else {
            rangeCheck(a.length, fromIndex, toIndex);
            if (LegacyMergeSort.userRequested)
                legacyMergeSort(a, fromIndex, toIndex, c);
            else
                TimSort.sort(a, fromIndex, toIndex, c, null, 0, 0);
        }
    }
    ```
    
    ```
    // 环境变量配置 java.util.Arrays.useLegacyMergeSort
    static final class LegacyMergeSort {
        private static final boolean userRequested =
            java.security.AccessController.doPrivileged(
                new sun.security.action.GetBooleanAction(
                    "java.util.Arrays.useLegacyMergeSort")).booleanValue();
    }
    ```
    
    某些博客或书籍可能比较滞后，还在说Collection.sort()使用的是归并排序。
    
### TimSort

TimSort是Python List.sort()的默认实现，2002年的时候就被创造出来，在现实中拥有很好的效率。
Java SE7 开始引入这一实现。

