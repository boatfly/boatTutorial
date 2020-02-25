package com.boatfly.spark.rdd.operator.vv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 两个rdd集合的差集
 */
object Subtract {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    // map 算子
    val RDD1: RDD[Int] = sc.makeRDD(List(1, 2, 2, 2, 3, 3, 4, 5, 6))
    val RDD2: RDD[Int] = RDD1.map(_ * 2)

    val RDD3: RDD[Int] = RDD2.subtract(RDD1)
    RDD3.collect().foreach(println)
  }
}
