package com.boatfly.spark.rdd.operator.v

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 缩减分区数，用于大数据过滤后，提高小数据及的执行效率
 */
object Coalesce {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    // map 算子
    val listRDD: RDD[Int] = sc.makeRDD(1 to 16, 4)
    println("缩减分区前的分区数量："+listRDD.partitions.size)
    val coalesce: RDD[Int] = listRDD.coalesce(3)
    println("缩减分区后的分区数量："+coalesce.partitions.size)

  }
}
