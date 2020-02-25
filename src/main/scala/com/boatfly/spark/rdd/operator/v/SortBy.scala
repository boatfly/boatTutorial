package com.boatfly.spark.rdd.operator.v

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object SortBy {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    // map 算子
    val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 2, 2, 3, 3, 4, 5, 6))

    val sortbyRDD: RDD[Int] = listRDD.sortBy(x => x, false) //默认升序
    sortbyRDD.collect().foreach(println)
  }
}
