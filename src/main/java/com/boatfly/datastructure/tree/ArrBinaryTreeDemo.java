package com.boatfly.datastructure.tree;

/**
 * 顺序存储二叉树：
 * 特点：
 * 1.通常只考虑完全二叉树
 * 2.第n个元素的左子节点为2*n+1
 * 3.第n个元素的右子节点为2*n+2
 * 4.第n个元素的父节点为(n-1)/2
 */
public class ArrBinaryTreeTest {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.preOrder();
    }
}

class ArrBinaryTree {
    private int[] arr;

    public ArrBinaryTree(int[] _arr) {
        this.arr = _arr;
    }

    /**
     * 完成顺序二叉树的前序遍历
     * @param index
     */
    public void preOrder(int index){
        if(arr==null||arr.length==0){
            System.out.println("数组为空！");
            return;
        }
        System.out.println(arr[index]);
        //左
        if((index*2+1)<arr.length){
            preOrder(index*2+1);
        }
        //右
        if((index*2+2)<arr.length){
            preOrder(index*2+2);
        }
    }

    public void preOrder() {
        this.preOrder(0);
    }
}

