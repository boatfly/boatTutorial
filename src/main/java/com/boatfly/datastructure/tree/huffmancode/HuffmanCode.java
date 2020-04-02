package com.boatfly.datastructure.tree.huffmancode;

import java.util.*;

/**
 *
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String str = "global pool specification definition instance";
        byte[] bytes = str.getBytes();
//        System.out.println("content's length=" + bytes.length);
//        List<Node> nodes = getNodes(bytes);
//        System.out.println(nodes);
//        System.out.println("霍夫曼树：");
//        Node huffmanTreeRoot = createHuffmanTree(nodes);
//        //前序遍历
//        preOrder(huffmanTreeRoot);
//
//        //
//        createHuffmanCode(huffmanTreeRoot, "", sb);
//        System.out.println(huffmancodes);
//
//        Map<Byte, String> huffmancode = getCodes(huffmanTreeRoot);
//        System.out.println(huffmancode);
//
//        byte[] hufumanCodeBytes = zip(bytes, huffmancode);
//        System.out.println("hufumanCodeBytes=" + Arrays.toString(hufumanCodeBytes));

        byte[] bytes1 = huffmancodeZip(bytes);
        System.out.println("压缩前长度" + bytes.length + ",压缩后长度：" + bytes1.length);
        String s = Byte2bitstring(false, (byte) -1);
//        String s = Byte2bitstring(true, (byte) 1);
        System.out.println(s);
        byte[] bytes2 = decodeHuffmanCode(huffmancodes, bytes1);
        System.out.println(new String(bytes2));
    }

    /**
     * 完成数据的解压
     * 1.将huffmancodes重新转换成 霍夫曼编码对应的二进制字符串
     * 2.将二进制字符串 对照霍夫曼编码 解码
     */
    public void unzip() {

    }

    /**
     * 完成对压缩数据的解码
     */
    public static byte[] decodeHuffmanCode(Map<Byte,String> huffmancodes,byte[] huffmanBytes){
        // 先得到huffmanBytes对应的二进制字符串
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<huffmanBytes.length;i++){
            boolean flag = (i == huffmanBytes.length-1);
            stringBuilder.append(Byte2bitstring(!flag,huffmanBytes[i]));
        }
        System.out.println("霍夫曼字节数组转换后的二进制字符串："+stringBuilder.toString());

        //把得到的二进制字符串按照指定的霍夫曼编码进行解码
        //把霍夫曼编码表进行调转，因为反向查询
        Map<String,Byte> map = new HashMap<>();
        for(Map.Entry<Byte,String> entry:huffmancodes.entrySet()){
            map.put(entry.getValue(),entry.getKey());
        }
        System.out.println("map="+map);

        //创建集合，存放byte
        List<Byte> list = new ArrayList<>();
        for(int i=0;i<stringBuilder.length();){
            int count =1;
            boolean flag = true;
            Byte b = null;
            while(flag){
                String key = stringBuilder.substring(i,i+count);
                b = map.get(key);
                if(b!=null){
                    flag = false;
                }else{
                    count++;
                }
            }
            list.add(b);
            i+=count;
        }

        byte b[] = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i]= list.get(i);
        }
        return b;
    }

    /**
     * 将一个Byte转为二进制字符串，需补充知识点：二进制的原码，补码，反码
     *
     * @param b 传入的byte
     * @param flag 是否需要补高位 true：补高位  false：不补(如果是最后一个字节，无须补高位)
     * @return b 对应的二进制字符串 （按补码返回）
     */
    public static String Byte2bitstring(boolean flag,byte b) {
        int temp = b;
        if(flag) {
            //如果是正数，需要补高位
            temp |= 256;//按位与 256=>1 0000 0000 | 0000 0001 => 1 0000 0001
        }
        String s = Integer.toBinaryString(temp);//temp对应的时temp的二进制补码
//        System.out.println(s);
//        System.out.println(s.substring(s.length() - 8));
        if(flag){
            return s.substring(s.length() - 8);
        }else{
            return s;
        }

    }

    /**
     * 统一封装
     *
     * @param src
     * @return
     */
    public static byte[] huffmancodeZip(byte[] src) {
        List<Node> nodes = getNodes(src);
        //创建霍夫曼树
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        //生成霍夫曼码表
        Map<Byte, String> huffmancode = getCodes(huffmanTreeRoot);
        byte[] hufumanCodeBytes = zip(src, huffmancode);
        return hufumanCodeBytes;
    }

    //将指定的byte[]，通过霍夫曼编码，转换为压缩后的byte[]
    //补充知识点：二进制 原码 反码 和 补码
    public static byte[] zip(byte[] src, Map<Byte, String> hufumancode) {
        StringBuilder sb = new StringBuilder();
        for (Byte b : src) {
            sb.append(hufumancode.get(b));
//            sb.append(" ");
        }
        System.out.println(sb);
        //将sb转为byte[]
        int len;
        if (sb.length() % 8 == 0) {
            len = sb.length() / 8;
        } else {
            len = sb.length() / 8 + 1;
        }//上述if/else等价于len = (sb.length()+7)/8

        //创建存储压缩后的byte[]
        byte[] retBytes = new byte[len];
        int index = 0;
        for (int i = 0; i < sb.length(); i += 8) {//每8位一个byte，所以步长+8
            String temp;
            if (i + 8 > sb.length()) {
                temp = sb.substring(i);
            } else
                temp = sb.substring(i, i + 8);
            //将temp转成一个byte，放入retBytes
            retBytes[index] = (byte) Integer.parseInt(temp, 2);
            index++;
        }

        return retBytes;
    }

    //生成霍夫曼编码
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

