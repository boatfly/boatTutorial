package com.boatfly.spark.rdd.operator.v

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 数据被打乱重组了 shuffle（将一个分区的数据打乱重组到其他分组的操作）
 */
object Distinct {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    // map 算子
    val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 2, 2, 3, 3, 4, 5, 6))

    val distinctRDD: RDD[Int] = listRDD.distinct() //不改变分区，但是由于去重之后可能会有多余的分区，因此可以加入参数来重新分配分区
    distinctRDD.collect().foreach(println)
  }
}
