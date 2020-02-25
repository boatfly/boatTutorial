package com.boatfly.spark.rdd.operator.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 针对kv形式的类型只对v进行操作
 */
object MapValues {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val rdd1: RDD[(String, Int)] = sc.parallelize(List(("a", 3), ("b", 4), ("c", 8)), 2)

    val rdd2: RDD[(String, Int)] = rdd1.mapValues(_ + 10)
    rdd2.collect().foreach(tp => {
      println(tp._1 + "'s average=" + tp._2)
    })
  }
}
