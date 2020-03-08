package com.boatfly.datastructure.recursion;

public class Maze {
    public static void main(String[] args) {
        int[][] maze = new int[8][7];
        // 1 为墙
        for (int i = 0; i < 7; i++) {
            maze[0][i] = 1;
            maze[7][i] = 1;
        }
        for (int i = 0; i < 8; i++) {
            maze[i][0] = 1;
            maze[i][6] = 1;
        }

        maze[3][1] = 1;
        maze[3][2] = 1;

        go(maze, 1, 1);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * i,j 表示从哪个位置出发
     * 如果小球能走到maze[6][5] 则表示通路找到
     * 约定：
     * 1.maze[i][j]=0 表示没有走过
     * 2.maze[i][j]=1 表示墙，不能走
     * 3.maze[i][j]=2 表示可以走
     * 4.maze[i][j]=3 表示已经走过，但是不通
     * 5.走迷宫时策略，下>右>上>左
     *
     * @param maze
     * @param i
     * @param j
     */
    public static boolean go(int[][] maze, int i, int j) {
        if (maze[6][5] == 2) {
            return true;
        } else {
            if (maze[i][j] == 0) { //当前节点还没走过
                maze[i][j] = 2; //假定该节点可以走
                if (go(maze, i + 1, j)) {//下>右>上>左
                    return true;
                } else if (go(maze, i, j + 1)) {
                    return true;
                } else if (go(maze, i - 1, j)) {
                    return true;
                } else if (go(maze, i, j - 1)) {
                    return true;
                } else {
                    maze[i][j] = 3;
                    return false;
                }
            } else { //maze[i][j]!=0 可能= 1 2 3
                return false;
            }
        }
    }
}
