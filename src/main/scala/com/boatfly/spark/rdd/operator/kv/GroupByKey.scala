package com.boatfly.spark.rdd.operator.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 对每一个key进行操作，只生成一个sequence
 */
object GroupByKey {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val words = Array("book", "spark", "spark", "scala", "golang", "solidity", "ethereum")
    val wordPairsRDD: RDD[(String, Int)] = sc.parallelize(words).map(x => (x, 1))
    val groupbykeyRDD: RDD[(String, Iterable[Int])] = wordPairsRDD.groupByKey()
    val value: RDD[(String, Int)] = groupbykeyRDD.map(t => (t._1, t._2.sum))
    value.collect().foreach(t => {
      println(t._1 + "'s nums=" + t._2)
    })
  }
}
