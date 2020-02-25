package com.boatfly.spark.rdd.operator.v

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Glom {
  def main(args: Array[String]): Unit = {
    //local模式
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val listRDD: RDD[Int] = sc.makeRDD(1 to 16, 4)

    //将一个分区的数据放入一个数组中
    val glomRDD: RDD[Array[Int]] = listRDD.glom()

    glomRDD.collect().foreach(array => {
      println(array.mkString(","))
    })
  }
}
