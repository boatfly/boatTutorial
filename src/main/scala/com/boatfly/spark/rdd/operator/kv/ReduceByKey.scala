package com.boatfly.spark.rdd.operator.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 使用指定的reduce函数，将相同key的值聚合在一起
 *
 * groupbykey 和 reducebykey 的区别
 * reducebykey：按照key进行聚合，在shuffle之前有combine（预聚合）操作，返回结果是RDD[k,v]  （shuffle之前有combine的话，性能会有提升）
 * groupbykey: 按照key进行分组，直接进行shuffle
 *
 */
object ReduceByKey {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val words = Array("book", "spark", "spark", "scala", "golang", "solidity", "ethereum")
    val wordPairsRDD: RDD[(String, Int)] = sc.parallelize(words).map(x => (x, 1))
    val value: RDD[(String, Int)] = wordPairsRDD.reduceByKey(_ + _)
    value.collect().foreach(t => {
      println(t._1 + "'s nums=" + t._2)
    })
  }
}
