package com.boatfly.datastructure.linkedlist;

import lombok.Data;
import org.junit.Test;

/**
 * 链表是有序的列表：
 * - 以节点的方式存储
 * - 每个节点包含：data，next下一个节点
 * - 各个节点在内存中并不是连续存放（存储）的。
 * - 带头节点，不带头节点
 */
public class SingleLinkedListDemo {
    /**
     * 按id顺序添加
     */
    @Test
    public void test1() {
        HeroNode hero1 = new HeroNode(1, "宋江", "a");
        HeroNode hero2 = new HeroNode(5, "晁盖", "b");
        HeroNode hero3 = new HeroNode(30, "无用", "c");
        HeroNode hero4 = new HeroNode(4, "林冲", "d");

        SingleLinkedList singleLinkedList = new SingleLinkedList();

        singleLinkedList.add1(hero1);
        singleLinkedList.add1(hero2);
        singleLinkedList.add1(hero3);
        singleLinkedList.add1(hero4);

        HeroNode heroupdate = new HeroNode(30, "无用abc", "c");


        singleLinkedList.update(heroupdate);

        singleLinkedList.list();
    }

    /**
     * 没考虑按id顺序添加
     */
    @Test
    public void test() {
        HeroNode hero1 = new HeroNode(1, "宋江", "a");
        HeroNode hero2 = new HeroNode(2, "晁盖", "b");
        HeroNode hero3 = new HeroNode(3, "无用", "c");
        HeroNode hero4 = new HeroNode(4, "林冲", "d");

        SingleLinkedList singleLinkedList = new SingleLinkedList();

        singleLinkedList.add(hero1);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero4);

        singleLinkedList.list();
    }

    public static void main(String[] args) {

    }
}

class SingleLinkedList {
    private HeroNode head = new HeroNode(0, "", "");

    public void delete(HeroNode node) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == node.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.next = temp.next.next;
        } else {
            System.out.println("未找到要删除的节点");
        }
    }

    public void update(HeroNode node) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == node.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.name = node.name;
            temp.nickName = node.nickName;
        } else {
            System.out.println("未找到要修改的节点");
        }
    }

    //按id的升序插入节点
    public void add1(HeroNode node) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            } else if (temp.next.no > node.no) {//位置找到
                break;
            } else if (temp.next.no == node.no) {
                flag = true;//编号存在
                break;
            }
            temp = temp.next;
        }

        if (flag) {
            System.out.printf("编号[id=%d]重复！\n", node.getNo());
        } else {
//            node.setNext(temp.getNext());
//            temp.setNext(node);
            node.next = temp.next;
            temp.next = node;
        }
    }

    public void add(HeroNode node) {
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        //退出while循环后，tmep指向了最后一个节点
        temp.next = node;
    }

    public void list() {
        if (head.getNext() == null) {
            System.out.println("链表为空！");
            return;
        }
        HeroNode temp = head;
        while (true) {
            if (temp.getNext() == null) {
                System.out.println("已到链表结尾！");
                break;
            }
            temp = temp.getNext();
            System.out.println("-" + temp);
        }
    }
}

@Data
class HeroNode {
    public int no;
    public String name;
    public String nickName;
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", next=" + next +
                '}';
    }
}