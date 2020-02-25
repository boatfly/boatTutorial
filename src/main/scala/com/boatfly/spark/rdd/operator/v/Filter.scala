package com.boatfly.spark.rdd.operator.v

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Filter {
  def main(args: Array[String]): Unit = {
    //local模式
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val listRDD: RDD[Int] = sc.makeRDD(1 to 16, 4)

    val filterRDD: RDD[Int] = listRDD.filter(x => x % 2 == 0)

    filterRDD.collect().foreach(println)
  }
}
