package com.boatfly.datastructure.tree;

/**
 * 堆排序
 *
 * 要求升序，则生成大顶堆
 * 降序：小顶堆
 *
 * 核心：
 * 将数组形式的树，转换为大顶堆和小顶堆
 *
 *
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {4, 6, 8, 5, 9};
    }

    //堆排序方法
    public static void heapsort(int[] _arr) {
    }

    /**
     * 第一个非叶子节点：n/2-1
     * @param _arr 待调整的数组
     * @param i 非叶子节点在数组中的索引
     * @param length 对多少个元素进行调整，length是在逐渐减少
     */
    //将一个数组（二叉树）,调整为大顶堆
    public static void adjust2bigheap(int[] _arr,int i,int length){
        int temp = _arr[i];//先取出当前元素的值
    }
}
