package com.boatfly.datastructure.tree.huffmancode;

import java.util.*;

/**
 *
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String str = "global pool specification definition instance";
        byte[] bytes = str.getBytes();
        System.out.println("content's length=" + bytes.length);
        List<Node> nodes = getNodes(bytes);
        System.out.println(nodes);
        System.out.println("霍夫曼树：");
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        //前序遍历
        preOrder(huffmanTreeRoot);

        //
        createHuffmanCode(huffmanTreeRoot, "", sb);
        System.out.println(huffmancodes);

        Map<Byte, String> huffmancode = getCodes(huffmanTreeRoot);
        System.out.println(huffmancode);
    }

    public static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        //处理左树
        createHuffmanCode(root.left, "0", sb);
        //处理右树
        createHuffmanCode(root.right, "1", sb);
        return huffmancodes;
    }

    //根据霍夫曼树生成霍夫曼码表
    //思路：1。将霍夫曼码存放在Map<Byte,String>中，如97->00010
    static Map<Byte, String> huffmancodes = new HashMap<>();
    // 2. 遍历过程中，用StringBuilder存放叶子节点的路径，向左0，向右1
    static StringBuilder sb = new StringBuilder();

    public static void createHuffmanCode(Node node, String code, StringBuilder sb) {
        StringBuilder stringBuffer = new StringBuilder(sb);
        stringBuffer.append(code);
        if (node != null) {
            if (node.data == null) {
                //非叶子节点
                //递归处理,向左
                createHuffmanCode(node.left, "0", stringBuffer);
                //递归处理,向右
                createHuffmanCode(node.right, "1", stringBuffer);
            } else {
                //叶子节点
                huffmancodes.put(node.data, stringBuffer.toString());
            }
        }
    }

    //前序遍历
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("霍夫曼树为空！");
        }
    }

    public static List<Node> getNodes(byte[] bytes) {
        ArrayList<Node> nodes = new ArrayList<>();
        //遍历bytes，统计每个字符出现的次数
        Map<Byte, Integer> counts = new HashMap<>();
        for (Byte b : bytes) {
            Integer i = counts.get(b);
            if (i == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, i + 1);
            }
        }

        //把每一个键值对转为Node
//        Iterator<Byte> iterator = counts.keySet().iterator();
//        while (iterator.hasNext()){
//            Byte next = iterator.next();
//            Node node = new Node(next,counts.get(next));
//            nodes.add(node);
//        }
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            nodes.add(node);
        }
        return nodes;
    }

    //通过List,创建对应的霍夫曼树
    public static Node createHuffmanTree(List<Node> nodes) {

        while (nodes.size() > 1) {
            //排序
            Collections.sort(nodes);
            //取出第一棵最小的二叉树
            Node leftnode = nodes.get(0);
            //取出第二棵最小的二叉树
            Node rightnode = nodes.get(1);
            //创建一棵新的二叉树，没有data，只有权值
            Node parent = new Node(null, leftnode.weight + rightnode.weight);
            parent.left = leftnode;
            parent.right = rightnode;
            //将已经处理过的二叉树从nodes中移除
            nodes.remove(leftnode);
            nodes.remove(rightnode);
            //将新的二叉树加入nodes
            nodes.add(parent);
        }
        //nodes最后的节点，就是霍夫曼树的根节点
        return nodes.get(0);
    }
}

//创建Node，带数据和权值
class Node implements Comparable<Node> {
    Byte data;//数据字符，'a'=97，' '=32
    int weight;//字符出现的次数

    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }


    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node[data=" + data + " weight=" + weight + "]";
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}

