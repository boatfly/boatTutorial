package com.boatfly.datastructure.sparsearray;

import org.junit.Test;

/**
 * 11*11的二维数据 -> 稀疏数组
 * ---row   col  num
 * 0  11   11    2
 * 1  1    2     1
 * 2  2    3     2
 */
public class SparseArrayTest {

    @Test
    public void testNormalToSparse() {
        int[][] chessBoard = new int[11][11];
        chessBoard[1][2] = 1;
        chessBoard[2][3] = 2;

        for (int[] rows : chessBoard) {
            for (int row : rows) {
                System.out.printf("%d\t", row);
            }
            System.out.printf("\n");
        }

        //先遍历二维数据得到非零数据的个数
        int sum = 0;
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] != 0) {
                    sum += 1;
                }
            }
        }
        System.out.println("sum=" + sum);
        //2
        int[][] sparseArray = new int[sum + 1][3];
        sparseArray[0][0] = chessBoard.length;
        sparseArray[0][1] = chessBoard[0].length;
        sparseArray[0][2] = sum;

        //3
        int num = 1;
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j] != 0) {
                    sparseArray[num][0] = i;
                    sparseArray[num][1] = j;
                    sparseArray[num][2] = chessBoard[i][j];
                    num++;
                }
            }
        }
        //打印稀疏数组
        for (int[] rows : sparseArray) {
            for (int row : rows) {
                System.out.printf("%d\t", row);
            }
            System.out.printf("\n");
        }
    }

    @Test
    public void testSparseToNormal() {
        //int[][] chessBoard = new int[11][11];
        int[][] chessBoard = null;

        int[][] sparseArray = new int[3][3];
        //chessboard的大小，以及共有多少个值，11*11的棋盘，共有2个旗子
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = 2;

        //第一个棋子的位置 和  值
        sparseArray[1][0] = 1;
        sparseArray[1][1] = 2;
        sparseArray[1][2] = 1;

        //第二个棋子的位置 和  值
        sparseArray[2][0] = 2;
        sparseArray[2][1] = 3;
        sparseArray[2][2] = 2;

        for (int i = 0; i < sparseArray.length; i++) {
            int[] temp = sparseArray[i];
            if (i == 0) {
                chessBoard = new int[temp[0]][temp[1]];
                continue;
            }
            chessBoard[temp[0]][temp[1]] = sparseArray[i][2];
        }

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                System.out.print(chessBoard[i][j] + "\t");
            }
            System.out.println("\n");
        }

    }
}
