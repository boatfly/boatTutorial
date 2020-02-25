package com.boatfly.spark.rdd.action

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 行动算子
 * reduce 化简
 * 通过函数聚集rdd中的所有元素，先聚合分区内数据，再聚合分区间数据。
 */
object Reduce {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    //v
    val rdd1: RDD[Int] = sc.parallelize(1 to 16, 2)
    val rdd2: Int = rdd1.reduce(_ + _)
    //println(rdd2)
    //kv
    val rdd3: RDD[(Int, String)] = sc.parallelize(Array((1, "a"), (2, "b")))
    val rdd4: (Int, String) = rdd3.reduce((x, y) => (x._1 + y._1, x._2 + y._2))
    println(rdd4)
  }
}
