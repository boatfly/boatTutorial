package com.boatfly.spark.rdd.operator.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 *
 */
object Join {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val rdd1: RDD[(String, Int)] = sc.parallelize(List(("a", 3), ("b", 4), ("c", 8)), 2)
    val rdd4: RDD[(String, String)] = sc.parallelize(List(("a", "4a"), ("b", "6b"), ("c", "2c")), 2)

    val rdd2: RDD[(String, (Int, String))] = rdd1.join(rdd4)
    rdd2.collect().foreach(tp => {
      println(tp._1 + "'s join=(" + tp._2._1 + "," + tp._2._2 + ")")
    })
  }
}
