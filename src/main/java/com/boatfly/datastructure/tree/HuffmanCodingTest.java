package com.boatfly.datastructure.tree;

/**
 * HuffmanCoding 赫夫曼编码
 * 应用于数据文件压缩
 * 赫夫曼码是可变字长编码VLC的一种
 *
 * char->ascii->binary
 *
 * 通信领域中信息的处理方式：变长编码(应注意前缀编码)->赫夫曼编码
 * 步骤：
 * 发送的内容->各个字符出现的次数->构建赫夫曼树->给各个字符规定编码（前缀编码，避免避免编码重复），向左的路径为0，向右的路径为1->根据新的编码规范将发送的内容重新编码
 *
 */
public class HuffmanCodingTest {
    public static void main(String[] args) {

    }
}
