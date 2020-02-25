package com.boatfly.spark.rdd.operator.vv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 将两个rdd组合成key/value形式的rdd，其中两个rdd的partition和元素数量均要相同，否则抛异常
 */
object Zip {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val rdd1: RDD[Int] = sc.parallelize(Array(1, 2, 3), 3)
    val rdd2: RDD[String] = sc.parallelize(Array("a", "b", "c"), 3)

    val zipRDD: RDD[(Int, String)] = rdd1.zip(rdd2)
    zipRDD.collect().foreach(tp=>{
      println(tp._1+","+tp._2)
    })
  }
}
