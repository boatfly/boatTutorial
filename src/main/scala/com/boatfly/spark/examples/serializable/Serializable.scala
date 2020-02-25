package com.boatfly.spark.examples.serializable

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 序列化demo
 */
object Serializable {
  def main(args: Array[String]): Unit = {
    //local模式
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val rdd1: RDD[String] = sc.parallelize(Array("spark", "solidity", "scala", "java", "golang"))

    val search = new Search("s")

    val sRDD1: RDD[String] = search.getMatch1(rdd1) //传递一个方法
    sRDD1.collect().foreach(println) //error: Task not serializable

    //释放资源
    sc.stop()
  }
}
