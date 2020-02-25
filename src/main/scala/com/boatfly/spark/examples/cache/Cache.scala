package com.boatfly.spark.examples.cache

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Cache {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val rdd: RDD[String] = sc.parallelize(Array("ethereum"))

    val rdd2: RDD[String] = rdd.map(_ + System.currentTimeMillis()).cache()

    rdd2.collect().foreach(println)
    rdd2.collect().foreach(println)
    rdd2.collect().foreach(println)

    println(rdd2.toDebugString)

    //释放资源
    sc.stop()
  }
}
