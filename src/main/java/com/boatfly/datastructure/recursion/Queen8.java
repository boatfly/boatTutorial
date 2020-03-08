package com.boatfly.datastructure.recursion;

/**
 * 八皇后
 */
public class Queen8 {
    private static int max = 8;

    private int[] array = new int[max];//每种摆法的结果，譬如：array={0,4,7,5,2,1,3}
    static int count = 0;

    public static void main(String[] args) {
        Queen8 queen = new Queen8();
        queen.check(0);
    }

    //放置第n个皇后
    public void check(int n) {
        if (n == max) {
            print();
            return;
        }
        for (int i = 0; i < max; i++) {
            //先把这个皇后n，放到改行的第一列
            array[n] = i;
            //判断是否冲突
            if (judge(n)) {
                //不冲突
                check(n + 1);
            }
        }
    }

    //检测新摆放的位置，是否和之前的皇后位置有冲突，直线、斜线冲突
    public boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            //array[i] == array[n] 同一列
            //Math.abs(n - i) == Math.abs(array[n] - array[i]) 同一斜线
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    public void print() {
        count++;
        for (int i = 0; i < max; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println(" " + count);
    }
}
