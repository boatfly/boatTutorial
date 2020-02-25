package com.boatfly.spark.examples.wcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Wcount {
  def main(args: Array[String]): Unit = {

    //local模式
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    //读取文件，将文件一行一行的读取出来
    val lines: RDD[String] = sc.textFile("in") //只写目录，则读取目录下所有文件
    //将行数据分解成单词
    val words: RDD[String] = lines.flatMap(_.split(" "))
    //为了统计翻遍，将单词的结构进行转换
    val word2one: RDD[(String, Int)] = words.map((_, 1))
    //对转换结构后的数据进行分组聚合
    val word2sum: RDD[(String, Int)] = word2one.reduceByKey(_ + _)

    val result: Array[(String, Int)] = word2sum.collect()

    //将统计结果打印到控制台
    result.foreach(println)

    //释放资源
    sc.stop()
  }
}
