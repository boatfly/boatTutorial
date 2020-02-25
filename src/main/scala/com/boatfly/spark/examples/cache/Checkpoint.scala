package com.boatfly.spark.examples.cache

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 检查点
 */
object Checkpoint {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)
    sc.setCheckpointDir("checkpoint") //设定检查点的保存目录,生产环境应为hdfs://

    val rdd: RDD[String] = sc.parallelize(Array("ethereum"))

    val mapRDD: RDD[(String, Int)] = rdd.map((_, 1))

    //mapRDD.checkpoint()

    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)
    reduceRDD.checkpoint()
    reduceRDD.foreach(println)
    println(reduceRDD.toDebugString)

    //释放资源
    sc.stop()
  }
}
