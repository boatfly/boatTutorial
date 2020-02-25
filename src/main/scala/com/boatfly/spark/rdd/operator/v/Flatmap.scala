package com.boatfly.spark.rdd.operator.v

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Flatmap {
  def main(args: Array[String]): Unit = {
    //local模式
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val listRDD: RDD[List[Int]] = sc.makeRDD(Array(List(1, 2), List(3, 4)))
    val flatmapRDD: RDD[Int] = listRDD.flatMap(datas => datas)
    flatmapRDD.collect().foreach(println)
  }
}
