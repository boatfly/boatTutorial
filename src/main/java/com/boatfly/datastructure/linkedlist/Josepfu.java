package com.boatfly.datastructure.linkedlist;

import lombok.Data;

public class Josepfu {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.add(125);
        circleSingleLinkedList.list();

        //circleSingleLinkedList.count(1,2,5); // 2 4 1 5 3
        circleSingleLinkedList.count(10,20,125);//63
    }
}

class CircleSingleLinkedList {
    private Boy first = null;

    //添加小孩节点，构建环形链表
    public void add(int nums) {
        if (nums < 1) {
            System.out.println("nums的值不正确");
        }
        //使用for创建环形链表
        Boy cur = null;
        for (int i = 1; i <= nums; i++) {
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                first.setNext(first); //构成环
                cur = first;
            } else {
                cur.setNext(boy);
                boy.setNext(first);
                cur = boy;
            }
        }
    }

    public void list() {
        if (first == null) {
            System.out.println("环形列表为空！");
            return;
        }
        Boy temp = first;
        while (true) {
            System.out.println("-" + temp.getNo());
            if (temp.getNext() == first) {
                break;
            }
            temp = temp.getNext();
        }
    }

    //根据用户的输入，计算小孩出圈的顺序

    /**
     * @param start 从哪个小孩开始数数
     * @param m     数几下
     * @param nums  开始时共有多少小孩在圈
     */
    public void count(int start, int m, int nums) {
        if (first == null || start < 1 || start > nums) {
            System.out.println("输入有误！");
            return;
        }
        // 创建辅助指针helper，指向末尾，first的前一个
        Boy helper = first;
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }

        for (int i = 0; i < start - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        while (true) {
            if (helper == first) {
                //圈中只剩一个boy
                break;
            }
            for (int i = 0; i < m - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            //这时first所在的节点就是小孩出圈的节点
            System.out.println("-out " + first.getNo());
            first = first.getNext();
            helper.setNext(first);
        }
        System.out.println("-out last " + first.getNo());
    }
}


class Boy {
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "no=" + no +
                "}";
    }
}
