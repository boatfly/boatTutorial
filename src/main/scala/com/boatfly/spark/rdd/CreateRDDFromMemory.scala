package com.boatfly.spark.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CreateRDDFromMemory {
  def main(args: Array[String]): Unit = {
    //local模式
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    //从内存中创建RDD
    //val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4)) //默认使用设置的分区数
    val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2) //使用自定义分区数
    val arrayRDD: RDD[Int] = sc.parallelize(Array(1, 2, 3, 4))

    //从外部文件
    val fileRDD: RDD[String] = sc.textFile("in") //默认两个分区

    listRDD.collect().foreach(println)
    arrayRDD.collect().foreach(println)

    listRDD.saveAsTextFile("out") //按分区进行保存，核数=分区数
  }
}
